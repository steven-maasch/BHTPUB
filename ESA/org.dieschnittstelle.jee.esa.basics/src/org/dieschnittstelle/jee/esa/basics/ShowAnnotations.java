package org.dieschnittstelle.jee.esa.basics;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.jee.esa.basics.annotations.StockItemProxyImpl;

public class ShowAnnotations {

	// the logger
	protected static Logger logger = Logger
			.getLogger(ShowAnnotations.class);
	
	public static void main(String[] args) {
		// we initialise the collection
		StockItemCollection collection = new StockItemCollection(
				"stockitems_annotations.xml", new AnnotatedStockItemBuilder());
		// we load the contents into the collection
		collection.load();

		for (IStockItem consumable : collection.getStockItems()) {
			;
			showAttributes(((StockItemProxyImpl)consumable).getProxiedObject());
		}

		// we initialise a consumer
		Consumer consumer = new Consumer();
		// ... and let them consume
		consumer.doShopping(collection.getStockItems());
	}

	/*
	 * UE BAS2 
	 */
	private static void showAttributes(Object consumable) {
		final String classSimpleName = consumable.getClass().getSimpleName();
		final Field[] classFields = consumable.getClass().getDeclaredFields();
		final int classFieldsCount = classFields.length;
		final int lastFieldIndex = classFieldsCount-1;
		
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(classSimpleName);
		sb.append(" ");
		for (int i = 0; i < classFieldsCount; ++i) {
			sb.append(classFields[i].getName());
			sb.append(":");
			sb.append(invokeGetterForField(classFields[i], consumable));
			if (i < lastFieldIndex) {
				sb.append(", ");
			}
		}
		sb.append("}");
		System.out.println(sb);
	}
	
	private static Object invokeGetterForField(final Field field, final Object object) {
		try {
			assert object.getClass().getField(field.getName()) != null;
			final List<Method> getMethods = getOnlyGetMethods(object.getClass());
			for (Method method: getMethods) {
				if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
					return method.invoke(object);
				}
			}
			logger.warn("Class doesn't provide getter for field. Returning empty string.");
			return "";
		} catch (Exception e) {
			logger.error("Could not invoke getter.", e);
			throw new RuntimeException(e);
		}
	} 

	private static List<Method> getOnlyGetMethods(final Class<?> c) {
		final List<Method> result = new ArrayList<Method>();
		for (Method method: c.getMethods()) {
			if (method.getName().startsWith("get")) {
				result.add(method);
			}
		}
		return result;
	}

}

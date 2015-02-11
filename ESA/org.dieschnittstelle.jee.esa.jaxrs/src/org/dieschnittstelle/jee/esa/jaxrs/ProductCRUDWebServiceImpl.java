package org.dieschnittstelle.jee.esa.jaxrs;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;

/*
UE JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDWebServiceImpl implements IProductCRUDWebService {

	protected static Logger logger = Logger.getLogger(ProductCRUDWebServiceImpl.class);
	
	private GenericCRUDExecutor<AbstractProduct> productCRUD;
	
	public ProductCRUDWebServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		logger.info("<constructor>: " + servletContext + "/" + request);
		// read out the dataAccessor
		this.productCRUD = (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");
		
		logger.debug("read out the productCRUD from the servlet context: " + this.productCRUD);
	}

	@Override
	public IndividualisedProductItem createProduct(AbstractProduct product) {
		return (IndividualisedProductItem) productCRUD.createObject(product);
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return (List<AbstractProduct>) productCRUD.readAllObjects();
	}

	@Override
	public AbstractProduct readProduct(int id) {
		return (AbstractProduct) productCRUD.readObject(id);
	}

	@Override
	public AbstractProduct updateProduct(int id, AbstractProduct product) {
		return (AbstractProduct) productCRUD.updateObject(product);
	}

	@Override
	public boolean deleteProduct(int id) {
		return productCRUD.deleteObject(id);
	}

}

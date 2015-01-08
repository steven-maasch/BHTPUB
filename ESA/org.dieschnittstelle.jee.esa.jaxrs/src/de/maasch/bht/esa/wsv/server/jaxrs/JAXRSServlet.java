package de.maasch.bht.esa.wsv.server.jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

import org.codehaus.jackson.map.ObjectMapper;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.jaxrs.ITouchpointCRUDWebService;

public class JAXRSServlet extends HttpServlet {

	private static final long serialVersionUID = -8884768970268827047L;
	
	private static final Logger logger = Logger.getLogger(JAXRSServlet.class.getSimpleName());

	private final Class<?> intfs = ITouchpointCRUDWebService.class;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final GenericCRUDExecutor<AbstractTouchpoint> touchpointCRUD = (GenericCRUDExecutor<AbstractTouchpoint>) 
				getServletContext().getAttribute("touchpointCRUD");
		
		
		final Method[] intfsMethods = intfs.getMethods();
		final List<Method> methodsWithAnnotation = getMethodsWithAnnoation(intfsMethods, POST.class);
		assert methodsWithAnnotation.size() == 1;
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final GenericCRUDExecutor<AbstractTouchpoint> touchpointCRUD = (GenericCRUDExecutor<AbstractTouchpoint>) 
				getServletContext().getAttribute("touchpointCRUD");
		
		logger.info(req.getRequestURL().toString());
		
	}
	
	private List<Method> getMethodsWithAnnoation(final Method[] methods, final Class<? extends Annotation> annotation) {
		final List<Method> foundMethods = new ArrayList<Method>(methods.length);
		for (Method method : methods) {
			if (method.getAnnotation(annotation) != null) {
				foundMethods.add(method);
			}
		}
		return foundMethods;
	}
	
	
	
}

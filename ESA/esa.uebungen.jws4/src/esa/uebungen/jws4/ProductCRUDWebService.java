package esa.uebungen.jws4;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.jboss.logging.Logger;

/*
 * UE JWS4: machen Sie die Funktionalitaet dieser Klasse als Web Service verfuegbar und verwenden Sie fuer 
 * die Umetzung der beiden Methoden die Instanz von GenericCRUDExecutor<AbstractProduct>, 
 * die Sie aus dem ServletContext auslesen koennen
 */
@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws4", serviceName = "ProductCRUDWebService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ProductCRUDWebService {
	
	private static Logger logger = Logger.getLogger(ProductCRUDWebService.class);
	
	@Resource
	private WebServiceContext wscontext;
	
	@WebMethod
	public List<AbstractProduct> readAllProducts() {
		logger.info(">> readAllProducts()");

		ServletContext ctx = (ServletContext) wscontext.getMessageContext()
				.get(MessageContext.SERVLET_CONTEXT);
		logger.info("readAllProducts(): servlet context is: " + ctx);
		// we also read out the http request
		HttpServletRequest httpRequest = (HttpServletRequest) wscontext
				.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		logger.info("readAllProducts(): servlet request is: " + httpRequest);

		@SuppressWarnings("unchecked")
		GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ctx
				.getAttribute("productCRUD");
		logger.info("readAllProducts(): read prductCRUD from servletContext: "
				+ productCRUD);

		logger.info("<< readAllProducts()");
		
		return productCRUD.readAllObjects();

	}

	@WebMethod
	public AbstractProduct createProduct(AbstractProduct product) {				
		logger.info(">> createProduct()");
		
		ServletContext ctx = (ServletContext) wscontext.getMessageContext()
				.get(MessageContext.SERVLET_CONTEXT);
		logger.info("createProduct(): servlet context is: " + ctx);
		// we also read out the http request
		HttpServletRequest httpRequest = (HttpServletRequest) wscontext
				.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		logger.info("createProduct(): servlet request is: " + httpRequest);

		@SuppressWarnings("unchecked")
		GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ctx
				.getAttribute("productCRUD");
		logger.info("createProduct(): read productCRUD from servletContext: "
				+ productCRUD);
		
		logger.info("<< createProduct()");
		
		return (IndividualisedProductItem) productCRUD.createObject(product);
	}
	
}

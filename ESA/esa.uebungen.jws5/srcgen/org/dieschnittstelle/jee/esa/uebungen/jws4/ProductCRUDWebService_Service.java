package org.dieschnittstelle.jee.esa.uebungen.jws4;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2014-11-25T16:27:18.183+01:00
 * Generated source version: 2.4.6
 * 
 */
@WebServiceClient(name = "ProductCRUDWebService", 
                  wsdlLocation = "http://localhost:8080/esa.uebungen.jws4/ProductCRUDWebService?wsdl",
                  targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws4") 
public class ProductCRUDWebService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://dieschnittstelle.org/jee/esa/uebungen/jws4", "ProductCRUDWebService");
    public final static QName ProductCRUDWebServicePort = new QName("http://dieschnittstelle.org/jee/esa/uebungen/jws4", "ProductCRUDWebServicePort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/esa.uebungen.jws4/ProductCRUDWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ProductCRUDWebService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/esa.uebungen.jws4/ProductCRUDWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ProductCRUDWebService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ProductCRUDWebService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ProductCRUDWebService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ProductCRUDWebService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ProductCRUDWebService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ProductCRUDWebService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns ProductCRUDWebService
     */
    @WebEndpoint(name = "ProductCRUDWebServicePort")
    public ProductCRUDWebService getProductCRUDWebServicePort() {
        return super.getPort(ProductCRUDWebServicePort, ProductCRUDWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ProductCRUDWebService
     */
    @WebEndpoint(name = "ProductCRUDWebServicePort")
    public ProductCRUDWebService getProductCRUDWebServicePort(WebServiceFeature... features) {
        return super.getPort(ProductCRUDWebServicePort, ProductCRUDWebService.class, features);
    }

}

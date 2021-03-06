package org.dieschnittstelle.jee.esa.uebungen.add3;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2015-01-23T00:16:05.793+01:00
 * Generated source version: 2.4.6
 * 
 */
@WebServiceClient(name = "StockSystemEjbWebService", 
                  wsdlLocation = "http://localhost:8080/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/StockSystemEjbWebService/StockSystem?wsdl",
                  targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/add3") 
public class StockSystemEjbWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add3", "StockSystemEjbWebService");
    public final static QName StockSystemPort = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add3", "StockSystemPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/StockSystemEjbWebService/StockSystem?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(StockSystemEjbWebService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/StockSystemEjbWebService/StockSystem?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public StockSystemEjbWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public StockSystemEjbWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StockSystemEjbWebService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public StockSystemEjbWebService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public StockSystemEjbWebService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public StockSystemEjbWebService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns StockSystem
     */
    @WebEndpoint(name = "StockSystemPort")
    public StockSystem getStockSystemPort() {
        return super.getPort(StockSystemPort, StockSystem.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockSystem
     */
    @WebEndpoint(name = "StockSystemPort")
    public StockSystem getStockSystemPort(WebServiceFeature... features) {
        return super.getPort(StockSystemPort, StockSystem.class, features);
    }

}


package org.dieschnittstelle.jee.esa.uebungen.add2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.dieschnittstelle.jee.esa.uebungen.add2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ReadAllProductsResponse_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "readAllProductsResponse");
    private final static QName _ReadProductResponse_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "readProductResponse");
    private final static QName _CreateProductResponse_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "createProductResponse");
    private final static QName _UpdateProductResponse_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "updateProductResponse");
    private final static QName _UpdateProduct_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "updateProduct");
    private final static QName _CreateProduct_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "createProduct");
    private final static QName _ReadProduct_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "readProduct");
    private final static QName _DeleteProduct_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "deleteProduct");
    private final static QName _DeleteProductResponse_QNAME = new QName("http://dieschnittstelle.org/jee/esa/uebungen/add2", "deleteProductResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.dieschnittstelle.jee.esa.uebungen.add2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AbstractProductArray }
     * 
     */
    public AbstractProductArray createAbstractProductArray() {
        return new AbstractProductArray();
    }

    /**
     * Create an instance of {@link IndividualisedProductItem }
     * 
     */
    public IndividualisedProductItem createIndividualisedProductItem() {
        return new IndividualisedProductItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractProductArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "readAllProductsResponse")
    public JAXBElement<AbstractProductArray> createReadAllProductsResponse(AbstractProductArray value) {
        return new JAXBElement<AbstractProductArray>(_ReadAllProductsResponse_QNAME, AbstractProductArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "readProductResponse")
    public JAXBElement<AbstractProduct> createReadProductResponse(AbstractProduct value) {
        return new JAXBElement<AbstractProduct>(_ReadProductResponse_QNAME, AbstractProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "createProductResponse")
    public JAXBElement<AbstractProduct> createCreateProductResponse(AbstractProduct value) {
        return new JAXBElement<AbstractProduct>(_CreateProductResponse_QNAME, AbstractProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "updateProductResponse")
    public JAXBElement<AbstractProduct> createUpdateProductResponse(AbstractProduct value) {
        return new JAXBElement<AbstractProduct>(_UpdateProductResponse_QNAME, AbstractProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "updateProduct")
    public JAXBElement<AbstractProduct> createUpdateProduct(AbstractProduct value) {
        return new JAXBElement<AbstractProduct>(_UpdateProduct_QNAME, AbstractProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "createProduct")
    public JAXBElement<AbstractProduct> createCreateProduct(AbstractProduct value) {
        return new JAXBElement<AbstractProduct>(_CreateProduct_QNAME, AbstractProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "readProduct")
    public JAXBElement<Integer> createReadProduct(Integer value) {
        return new JAXBElement<Integer>(_ReadProduct_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "deleteProduct")
    public JAXBElement<Integer> createDeleteProduct(Integer value) {
        return new JAXBElement<Integer>(_DeleteProduct_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", name = "deleteProductResponse")
    public JAXBElement<Boolean> createDeleteProductResponse(Boolean value) {
        return new JAXBElement<Boolean>(_DeleteProductResponse_QNAME, Boolean.class, null, value);
    }

}

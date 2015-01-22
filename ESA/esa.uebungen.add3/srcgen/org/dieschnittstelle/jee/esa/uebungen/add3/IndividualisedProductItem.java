
package org.dieschnittstelle.jee.esa.uebungen.add3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for individualisedProductItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="individualisedProductItem">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dieschnittstelle.org/jee/esa/uebungen/add3}abstractProduct">
 *       &lt;sequence>
 *         &lt;element name="expirationAfterStocked" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productType" type="{http://dieschnittstelle.org/jee/esa/uebungen/add3}productType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "individualisedProductItem", propOrder = {
    "expirationAfterStocked",
    "productType"
})
public class IndividualisedProductItem
    extends AbstractProduct
{

    protected int expirationAfterStocked;
    protected ProductType productType;

    /**
     * Gets the value of the expirationAfterStocked property.
     * 
     */
    public int getExpirationAfterStocked() {
        return expirationAfterStocked;
    }

    /**
     * Sets the value of the expirationAfterStocked property.
     * 
     */
    public void setExpirationAfterStocked(int value) {
        this.expirationAfterStocked = value;
    }

    /**
     * Gets the value of the productType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductType }
     *     
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductType }
     *     
     */
    public void setProductType(ProductType value) {
        this.productType = value;
    }

}

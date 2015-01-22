
package org.dieschnittstelle.jee.esa.uebungen.add3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getTotalUnitsOnStock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTotalUnitsOnStock">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://dieschnittstelle.org/jee/esa/uebungen/add3}abstractProduct" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTotalUnitsOnStock", propOrder = {
    "arg0"
})
public class GetTotalUnitsOnStock {

    protected AbstractProduct arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link AbstractProduct }
     *     
     */
    public AbstractProduct getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractProduct }
     *     
     */
    public void setArg0(AbstractProduct value) {
        this.arg0 = value;
    }

}


package org.dieschnittstelle.jee.esa.crm.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.dieschnittstelle.jee.esa.jaxws.Customer;
import org.dieschnittstelle.jee.esa.jaxws.MobileTouchpoint;


/**
 * <p>Java class for abstractTouchpoint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="abstractTouchpoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="erpPointOfSaleId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customers" type="{http://dieschnittstelle.org/jee/esa/jaxws}customer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractTouchpoint", propOrder = {
    "id",
    "erpPointOfSaleId",
    "name",
    "customers"
})
@XmlSeeAlso({
    MobileTouchpoint.class,
    StationaryTouchpoint.class
})
public abstract class AbstractTouchpoint {

    protected int id;
    protected int erpPointOfSaleId;
    protected String name;
    @XmlElement(nillable = true)
    protected List<Customer> customers;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the erpPointOfSaleId property.
     * 
     */
    public int getErpPointOfSaleId() {
        return erpPointOfSaleId;
    }

    /**
     * Sets the value of the erpPointOfSaleId property.
     * 
     */
    public void setErpPointOfSaleId(int value) {
        this.erpPointOfSaleId = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the customers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Customer }
     * 
     * 
     */
    public List<Customer> getCustomers() {
        if (customers == null) {
            customers = new ArrayList<Customer>();
        }
        return this.customers;
    }

}

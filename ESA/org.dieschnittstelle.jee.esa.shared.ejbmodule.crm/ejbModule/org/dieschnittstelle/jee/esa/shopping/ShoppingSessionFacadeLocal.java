package org.dieschnittstelle.jee.esa.shopping;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

@Local
public interface ShoppingSessionFacadeLocal {

	void setTouchpoint(AbstractTouchpoint touchpoint);

	void setCustomer(Customer customer);

	void addProduct(AbstractProduct product, int units);

	void verifyCampaigns(); 
	
	void purchase();
	
}

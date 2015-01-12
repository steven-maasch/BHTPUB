package org.dieschnittstelle.jee.esa.shopping;

import javax.ejb.Remote;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

@Remote
public interface ShoppingSessionFacadeRemote {

	void setTouchpoint(AbstractTouchpoint touchpoint);

	void setCustomer(Customer customer);

	void addProduct(AbstractProduct product, int units);

	void verifyCampaigns(); 
	
	void purchase();
}

package org.dieschnittstelle.jee.esa.ejbs.client;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.*;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.shopping.ShoppingSessionFacadeRemote;

public class ShoppingDelegate implements ShoppingSessionFacadeRemote {

	protected static Logger logger = Logger.getLogger(ShoppingDelegate.class);

	private ShoppingSessionFacadeRemote proxy;
	
	public ShoppingDelegate() throws Exception {
		logger.info(">> ShoppingDelegate()");
		Context context = new InitialContext();
		this.proxy = (ShoppingSessionFacadeRemote) context
				.lookup(SHOPPING_SESSION_FACADE_BEAN);
		logger.info("<< ShoppingDelegate()");
	}
	
	public void setTouchpoint(AbstractTouchpoint touchpoint) {
		this.proxy.setTouchpoint(touchpoint);
	}

	public void setCustomer(Customer customer) {
		this.proxy.setCustomer(customer);
	}

	public void addProduct(AbstractProduct product, int units) {
		this.proxy.addProduct(product, units);
	}

	public void verifyCampaigns() {
		this.proxy.verifyCampaigns();
	}

	public void purchase() {
		this.proxy.purchase();
	}

}

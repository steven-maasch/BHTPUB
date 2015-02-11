package org.dieschnittstelle.jee.esa.shopping;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.CustomerTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingException;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingException.ShoppingSessionExceptionReason;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.jboss.logging.Logger;

@Stateful
@StatefulTimeout(value=10, unit=TimeUnit.MINUTES)
public class ShoppingSessionFacadeStateful implements ShoppingSessionFacadeRemote {

	protected static Logger logger = Logger.getLogger(ShoppingSessionFacadeStateful.class);
	
	@EJB
	private ShoppingCartLocal shoppingCart;

	@EJB
	private CustomerTrackingLocal customerTracking;

	@EJB
	private CampaignTrackingLocal campaignTracking;

	@EJB
	private StockSystemLocal stockSystem;
	
	
	/**
	 * the customer
	 */
	private Customer customer;

	/**
	 * the touchpoint
	 */
	private AbstractTouchpoint touchpoint;

	public ShoppingSessionFacadeStateful() {
		logger.info("<constructor>");
	}
	
	public void setTouchpoint(AbstractTouchpoint touchpoint) {
		this.touchpoint = touchpoint;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void addProduct(AbstractProduct product, int units) {
		this.shoppingCart.addProductBundle(new CrmProductBundle(product.getId(),
				units, product instanceof Campaign));
	}

	/*
	 * verify whether campaigns are still valid
	 */
	public void verifyCampaigns() throws ShoppingException {
		if (this.customer == null || this.touchpoint == null) {
			logger.warn("cannot verify campaigns! No touchpoint has been set!");
			throw new ShoppingException(ShoppingSessionExceptionReason.INCOMPLETE_SESSION_DATA);
		}
		
		for (CrmProductBundle productBundle : this.shoppingCart
				.getProductBundles()) {
			if (productBundle.isCampaign()) {
				int availableCampaigns = this.campaignTracking
						.existsValidCampaignExecutionAtTouchpoint(
								productBundle.getErpProductId(),
								this.touchpoint);
				logger.info("got available campaigns for product " + productBundle.getErpProductId() + ": " + availableCampaigns);
				// we check whether we have sufficient campaign items available
				if (availableCampaigns < productBundle.getUnits()) {
					logger.warn("verifyCampaigns() failed for productBundle "
							+ productBundle + " at touchpoint "
							+ this.touchpoint + "! Need " + productBundle.getUnits() 
							+ " instances of campaign, but only got: " + availableCampaigns);
					throw new ShoppingException(ShoppingSessionExceptionReason.CAMPAIGN_INVALID);
				}
			}
		}
	}

	public void purchase() {
		logger.info(">> purchase()");

		if (this.customer == null || this.touchpoint == null) {
			logger.warn(
					"cannot commit shopping session! Either customer or touchpoint has not been set: "
							+ this.customer + "/" + this.touchpoint);
			throw new ShoppingException(ShoppingSessionExceptionReason.INCOMPLETE_SESSION_DATA);
		}
		
		// verify the campaigns
		verifyCampaigns();

		final List<CrmProductBundle> allProductBundles = this.shoppingCart.getProductBundles();
		final List<AbstractProduct> allProductsInStock = stockSystem.getAllProductsOnStock();
		
		for (CrmProductBundle productBundle : allProductBundles) {
			
			AbstractProduct product = null;
			for (AbstractProduct stockItem : allProductsInStock) {
				if (productBundle.getErpProductId() == stockItem.getId()) {
					product = stockItem;
					break;
				}
			}
			
			if (product == null) {
				logger.warn("Product not in stock => ProductId: " + productBundle.getErpProductId());
				throw new ShoppingException(ShoppingSessionExceptionReason.CAMPAIGN_INVALID);
			}
			
			final int unitsInStock = stockSystem.getUnitsOnStock2(
					productBundle.getErpProductId(), 
					touchpoint.getErpPointOfSaleId());
			
			if (unitsInStock < productBundle.getUnits()) {
				logger.error("Not enough untis in stock.");
				throw new ShoppingException(ShoppingSessionExceptionReason.STOCK_EXCEEDED);
			}
			
			stockSystem.removeFromStock(product, 
					touchpoint.getErpPointOfSaleId(), 
					productBundle.getUnits());
			
		}

		// iterate over the products and purchase the campaigns
		for (CrmProductBundle productBundle : allProductBundles) {
			if (productBundle.isCampaign()) {
				this.campaignTracking.purchaseCampaignAtTouchpoint(
						productBundle.getErpProductId(), this.touchpoint,
						productBundle.getUnits());
			}
			
		}

		// then we add a new customer transaction for the current purchase
		CustomerTransaction transaction = new CustomerTransaction(
				this.customer, this.touchpoint, allProductBundles);
		transaction.setCompleted(true);
		customerTracking.createTransaction(transaction);

		logger.info("<< purchase()");
	}
}
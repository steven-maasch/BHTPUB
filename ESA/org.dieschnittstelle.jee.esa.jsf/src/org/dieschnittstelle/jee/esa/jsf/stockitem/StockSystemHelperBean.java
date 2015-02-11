package org.dieschnittstelle.jee.esa.jsf.stockitem;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerTransactionCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.CampaignExecution;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.crm.entities.Gender;
import org.dieschnittstelle.jee.esa.crm.entities.MobileTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductBundle;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.shopping.ShoppingSessionFacadeLocal;
import org.jboss.logging.Logger;

@Named
@ApplicationScoped
public class StockSystemHelperBean {

	protected static Logger logger = Logger.getLogger(StockSystemHelperBean.class);

	@Resource(mappedName="java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/ShoppingSessionFacadeStateful!org.dieschnittstelle.jee.esa.shopping.ShoppingSessionFacadeLocal")
	private ShoppingSessionFacadeLocal shoppingSession;
	
	// the entities
	private StationaryTouchpoint TOUCHPOINT_1;

	private StationaryTouchpoint TOUCHPOINT_2;

	private MobileTouchpoint TOUCHPOINT_3;

	private IndividualisedProductItem PRODUCT_1;

	private IndividualisedProductItem PRODUCT_2;

	private Campaign CAMPAIGN_1;

	private Campaign CAMPAIGN_2;

	private Customer CUSTOMER_1;

	private Customer CUSTOMER_2;

	// instantiate the constants
	public StockSystemHelperBean() {

		Address addr1 = new Address("Luxemburger Strasse", "10", "13353",
				"Berlin");
		TOUCHPOINT_1 = new StationaryTouchpoint(0, "BHT Mensa", addr1);

		Address addr2 = new Address("Leopoldplatz", "1", "13353", "Berlin");
		TOUCHPOINT_2 = new StationaryTouchpoint(0, "U Leopoldplatz", addr2);

		TOUCHPOINT_3 = new MobileTouchpoint("01778896571");
		TOUCHPOINT_3.setName("Mobiler Verkaufsstand");

		PRODUCT_1 = new IndividualisedProductItem("Schrippe", ProductType.ROLL,
				720);
		PRODUCT_2 = new IndividualisedProductItem("Kirschplunder",
				ProductType.PASTRY, 1080);

		CAMPAIGN_1 = new Campaign();
		CAMPAIGN_1.addBundle(new ProductBundle(PRODUCT_1, 5));
		CAMPAIGN_1.addBundle(new ProductBundle(PRODUCT_2, 2));

		CAMPAIGN_2 = new Campaign();
		CAMPAIGN_2.addBundle(new ProductBundle(PRODUCT_2, 3));

		CUSTOMER_1 = new Customer("Anna", "Musterfrau", Gender.W);
		CUSTOMER_1.setAddress(new Address("Kopernikusstrasse", "11", "10245",
				"Berlin"));
		CUSTOMER_1.setEmail("anna@example.com");

		CUSTOMER_2 = new Customer("Benedikt", "Mustermann", Gender.M);
		CUSTOMER_2.setAddress(new Address("Corinthstrasse", "44", "10245",
				"Berlin"));
		CUSTOMER_2.setEmail("bene@example.com");
	}

	// the ejbs

	// declare the attributes that will be instantiated with the ejb clients
	// TODO: use your StockSystemRemote and ProductCRUDRemote EJB interfaces that have been developed in the exercises for JPA - the names for ejbs and interfaces used here are the ones from the demos, change where necessary
	@Resource(mappedName = "java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/ProductCRUDStateless!org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote")
	private ProductCRUDRemote productCRUD;
	@Resource(mappedName = "java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/TouchpointAccessStateless!org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessRemote")
	private TouchpointAccessRemote touchpointAccess;
	@Resource(mappedName = "java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/StockSystem!org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote")
	private StockSystemRemote stockSystem;
	@Resource(mappedName = "java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/CustomerCRUDStateless!org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDRemote")
	private CustomerCRUDRemote customerCRUD;
	@Resource(mappedName = "java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/CampaignTrackingSingleton!org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote")
	private CampaignTrackingRemote campaignTracking;
	@Resource(mappedName = "java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/CustomerTransactionCRUDStateless!org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerTransactionCRUDRemote")
	private CustomerTransactionCRUDRemote transactionCRUD;

	public void createProducts() {
		// create products
		PRODUCT_1.setId(productCRUD.createProduct(PRODUCT_1).getId());
		PRODUCT_2.setId(productCRUD.createProduct(PRODUCT_2).getId());

		// TODO: consider campaigns
		// CAMPAIGN_1.setId(productCRUD.createProduct(CAMPAIGN_1).getId());
		// CAMPAIGN_2.setId(productCRUD.createProduct(CAMPAIGN_2).getId());

		System.out.println("\n***************** created products\n");
	}

	public void createTouchpoints() {
		// create touchpoints
		AbstractTouchpoint tp1 = touchpointAccess
				.createTouchpoint(TOUCHPOINT_1);
		TOUCHPOINT_1.setId(tp1.getId());
		TOUCHPOINT_1.setErpPointOfSaleId(tp1.getErpPointOfSaleId());
		AbstractTouchpoint tp2 = touchpointAccess
				.createTouchpoint(TOUCHPOINT_2);
		TOUCHPOINT_2.setId(tp2.getId());
		TOUCHPOINT_2.setErpPointOfSaleId(tp2.getErpPointOfSaleId());

		System.out.println("\n***************** created touchpoints\n");
	}

	public void createStock() {
		// create stock
		stockSystem.addToStock(PRODUCT_1, TOUCHPOINT_1.getErpPointOfSaleId(),
				100);
		stockSystem.addToStock(PRODUCT_1, TOUCHPOINT_2.getErpPointOfSaleId(),
				110);
		stockSystem.addToStock(PRODUCT_2, TOUCHPOINT_1.getErpPointOfSaleId(),
				200);
		stockSystem.addToStock(PRODUCT_2, TOUCHPOINT_2.getErpPointOfSaleId(),
				220);

		System.out.println("\n***************** created stock\n");
	}

	public void prepareCampaigns() {
		// create campaign executions
		campaignTracking.addCampaignExecution(new CampaignExecution(
				TOUCHPOINT_1, CAMPAIGN_1.getId(), 10, -1));
		campaignTracking.addCampaignExecution(new CampaignExecution(
				TOUCHPOINT_1, CAMPAIGN_2.getId(), 5, -1));

		logger.info("campaigns are: "
				+ campaignTracking.getAllCampaignExecutions());

		System.out.println("\n***************** created campaign executions\n");
	}

	public void createCustomers() {
		// create customers
		CUSTOMER_1.setId(customerCRUD.createCustomer(CUSTOMER_1).getId());
		CUSTOMER_2.setId(customerCRUD.createCustomer(CUSTOMER_2).getId());

		System.out.println("\n***************** created customers\n");
	}

	/**
	 * this must be used for JSF6
	 * 
	 * TODO: access your ShoppingSessionFacade here - you should declare it as a @Resource obtaining a client stub via dependency injection
	 */
	public void doShopping() {
		try {
			// here, the shopping session facade should be accessed
			logger.info("SHOPPING SESSION : "+shoppingSession);
			
			 // add a customer and a touchpoint
			 shoppingSession.setCustomer(CUSTOMER_1);
			 shoppingSession.setTouchpoint(TOUCHPOINT_1);
			
			 // now add items
			 shoppingSession.addProduct(PRODUCT_1, 2);
			 shoppingSession.addProduct(PRODUCT_1, 3);
			 shoppingSession.addProduct(PRODUCT_2, 2);
			 shoppingSession.addProduct(CAMPAIGN_1, 1);
			 shoppingSession.addProduct(CAMPAIGN_2, 2);
			
			 // now try to commit the session
			 shoppingSession.purchase();
		} catch (Exception e) {
			logger.error("got exception during shopping: " + e, e);
		}
	}

	public void showTransactions() {
		System.out.println("\n***************** show transactions\n");

		Collection<CustomerTransaction> trans = transactionCRUD
				.readAllTransactionsForTouchpoint(TOUCHPOINT_1);
		logger.info("transactions for touchpoint are: " + trans);
		trans = transactionCRUD.readAllTransactionsForCustomer(CUSTOMER_1);
		logger.info("transactions for customer are: " + trans);
		trans = transactionCRUD.readAllTransactionsForTouchpointAndCustomer(
				TOUCHPOINT_1, CUSTOMER_1);
		logger.info("transactions for touchpoint and customer are: " + trans);
		// now try to read out the transactions by obtaining the customer
		// and retrieving getTransactions()
		trans = CUSTOMER_1.getTransactions();
		logger.info("transactions on local customer object are: " + trans);

		Customer cust = customerCRUD.readCustomer(CUSTOMER_1.getId());
		logger.info("read remote customer object: " + cust);
		trans = cust.getTransactions();
		logger.info("read transactions from remote object: " + trans);

	}

	@PostConstruct
	public void prepareData() {
		createProducts();
		createTouchpoints();
		createStock();
		prepareCampaigns();
		createCustomers();
	}

}


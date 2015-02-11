package org.dieschnittstelle.jee.esa.ejbs.client;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.*;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.CampaignExecution;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.CampaignTrackingClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.CustomerCRUDClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.CustomerTransactionCRUDClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.ProductCRUDClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.StockSystemClient;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.TouchpointAndPointOfSaleCRUDClient;

public class TotalUsecase {

	protected static Logger logger = Logger.getLogger(TotalUsecase.class);

	public static void main(String[] args) {
		try {
			(new TotalUsecase()).runAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// declare the session as stepping or not
	private boolean stepping = true;

	// declare the attributes that will be instantiated with the ejb clients
	private ProductCRUDClient productCRUD;
	private TouchpointAndPointOfSaleCRUDClient touchpointCRUD;
	private StockSystemClient stockSystemCRUD;
	private CustomerCRUDClient customerCRUD;
	private CampaignTrackingClient campaignTracking;
	private CustomerTransactionCRUDClient transactionCRUD;

	public TotalUsecase() throws Exception {
		instantiateClients();
	}
	
	public void runAll() {

		try {
			createProducts();
			createTouchpoints();
			createStock();
			prepareCampaigns();
			createCustomers();

			doShopping();

			showTransactions();
		} catch (Exception e) {
			logger.error("got exception: " + e, e);
		}
	}

	public void instantiateClients() throws Exception {
		// instantiate the clients
		productCRUD = new ProductCRUDClient();
		touchpointCRUD = new TouchpointAndPointOfSaleCRUDClient();
		stockSystemCRUD = new StockSystemClient();
		customerCRUD = new CustomerCRUDClient();
		campaignTracking = new CampaignTrackingClient();
		transactionCRUD = new CustomerTransactionCRUDClient();

		System.out.println("\n***************** instantiated clients\n");
	}

	public void createProducts() {
		// create products
		productCRUD.createProduct(PRODUCT_1);
		productCRUD.createProduct(PRODUCT_2);
		productCRUD.createProduct(CAMPAIGN_1);
		productCRUD.createProduct(CAMPAIGN_2);

		System.out.println("\n***************** created products\n");
	}

	public void createTouchpoints() {
		// create touchpoints
		touchpointCRUD.createTouchpoint(TOUCHPOINT_1);
		touchpointCRUD.createTouchpoint(TOUCHPOINT_2);

		System.out.println("\n***************** created touchpoints\n");
	}

	public void createStock() {
		// create stock
		stockSystemCRUD.addToStock(PRODUCT_1,
				TOUCHPOINT_1.getErpPointOfSaleId(), 100);
		stockSystemCRUD.addToStock(PRODUCT_1,
				TOUCHPOINT_2.getErpPointOfSaleId(), 100);
		stockSystemCRUD.addToStock(PRODUCT_2,
				TOUCHPOINT_1.getErpPointOfSaleId(), 100);
		stockSystemCRUD.addToStock(PRODUCT_2,
				TOUCHPOINT_2.getErpPointOfSaleId(), 100);

		System.out.println("\n***************** created stock\n");
	}

	public void prepareCampaigns() {
		// create campaign executions
		campaignTracking.addCampaignExecution(new CampaignExecution(
				Constants.TOUCHPOINT_1, Constants.CAMPAIGN_1.getId(), 10, -1));
		campaignTracking.addCampaignExecution(new CampaignExecution(
				Constants.TOUCHPOINT_1, Constants.CAMPAIGN_2.getId(), 5, -1));

		logger.info("campaigns are: "
				+ campaignTracking.getAllCampaignExecutions());

		System.out.println("\n***************** created campaign executions\n");
	}

	public void createCustomers() {
		// create customers
		customerCRUD.createCustomer(CUSTOMER_1);
		customerCRUD.createCustomer(CUSTOMER_2);

		System.out.println("\n***************** created customers\n");
	}

	public void doShopping() {
		try {
			while (true) {
				try {
					// create a shopping session and initialise it such that
					// it can access the required beans
					ShoppingDelegate session = new ShoppingDelegate(); /*
																	 * new
																	 * ShoppingSessionFacadeClient
																	 * ();
																	 */

					// add a customer and a touchpoint
					session.setCustomer(Constants.CUSTOMER_1);
					session.setTouchpoint(Constants.TOUCHPOINT_1);

					// now add items
					session.addProduct(Constants.PRODUCT_1, 2);
					session.addProduct(Constants.PRODUCT_1, 3);
					session.addProduct(Constants.PRODUCT_2, 2);
					session.addProduct(Constants.CAMPAIGN_1, 1);
					session.addProduct(Constants.CAMPAIGN_2, 2);

					if (this.stepping) Util.step();

					// now try to commit the session
					session.purchase();
					
//					throw new Exception("Stop Test");
					
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					// throwing exceptions out of main is bad style, yet we
					// need it to interrupt shopping in TotalUsecase
					throw new RuntimeException(e);
				}
//				if (this.stepping) Util.step();
			}
		} catch (Exception e) {
			logger.error("got exception during shopping: " + e, e);
		}
	}

	public void showTransactions() {
		System.out.println("\n***************** show transactions\n");

		Collection<CustomerTransaction> trans = transactionCRUD
				.readAllTransactionsForTouchpoint(Constants.TOUCHPOINT_1);
		logger.info("transactions for touchpoint are: " + trans);
		trans = transactionCRUD
				.readAllTransactionsForCustomer(Constants.CUSTOMER_1);
		logger.info("transactions for customer are: " + trans);
		trans = transactionCRUD.readAllTransactionsForTouchpointAndCustomer(
				Constants.TOUCHPOINT_1, Constants.CUSTOMER_1);
		logger.info("transactions for touchpoint and customer are: " + trans);
		// now try to read out the transactions by obtaining the customer
		// and retrieving getTransactions()
		trans = Constants.CUSTOMER_1.getTransactions();
		logger.info("transactions on local customer object are: " + trans);

		Customer cust = customerCRUD.readCustomer(Constants.CUSTOMER_1.getId());
		logger.info("read remote customer object: " + cust);
		trans = cust.getTransactions();
		logger.info("read transactions from remote object: " + trans);

	}

	public void setStepping(boolean stepping) {
		this.stepping = stepping;
	}

}

package org.dieschnittstelle.jee.esa.jsf.stockitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.xml.bind.ValidationException;

import org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.Gender;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

@ManagedBean(name = "vc")
@ApplicationScoped
public class StockSystemViewController {

	protected static Logger logger = Logger
			.getLogger(StockSystemViewController.class);

	/*
	 * TODO declare a dependency to the stock system ejb via a new local interface,
	 * using the @Resource annotation and the mappedName:
	 * java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle
	 * .jee.esa
	 * .shared.ejbmodule.erp/StockSystemSingleton!org.dieschnittstelle.jee
	 * .esa.erp.ejbs.StockSystemLocal
	 */
	
	@Resource(mappedName="java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/StockSystemSingleton!org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal")
	private StockSystemLocal stockSystem;

	/*
	 * use the helper bean - this is needed for JSF6
	 */
	@Inject
	private StockSystemHelperBean stockSystemHelper;

	/*
	 * TODO: later on (after only displaying the list), we also need access to the
	 * touchpoints ejb, using the @Resource annotation and the mappedName:
	 * java:global/org.dieschnittstelle.jee
	 * .esa.ejbs/org.dieschnittstelle.jee.esa
	 * .shared.ejbmodule.crm/TouchpointAccessStateless
	 * !org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessLocal
	 */
	
	@Resource(mappedName="java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.crm/TouchpointAccessStateless!org.dieschnittstelle.jee.esa.crm.ejbs.TouchpointAccessLocal")
	private TouchpointAccessLocal touchpointBean;
	
	/*
	 * these are local structures created from the data read out from the beans
	 */
	/* the map of stock items */
	private Map<String, StockItemWrapper> stockItemsMap = new TreeMap<String, StockItemWrapper>();

	/* a map of touchpoints using the erpPointOfSaleId as key */
	private Map<Integer, AbstractTouchpoint> touchpointsMap = new HashMap<Integer, AbstractTouchpoint>();

	private ArrayList<StockItemWrapper> stockItems = new ArrayList<StockItemWrapper>();
	/*
	 * TODO: return the values of the stockItemsMap. Note that you might need to create a new Collection, e.g. ArrayList to add the values
	 */
	public Collection<StockItemWrapper> getStockItems() {
		return stockItems;
	}

	/*
	 * the action method for updating the units of a stockItem
	 */
	public String updateUnits() {
		/*
		 * TODO: we accesss the parameters from the FacesContext.getCurrentInstance()
		 * .getExternalContext().getRequestParameterMap()
		 */
		Map<String, String> args = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		/* TODO: read out the parameter(s) that we need */
		String siId = (String)args.get("siId");
		
		logger.info("updateUnits() with siId: "+siId);

		/*
		 * TODO: we read out the StockItemWrapper from the local map
		 */
		StockItemWrapper siWrapper = stockItemsMap.get(siId);
		
		logger.info("siWrapper.units: "+siWrapper.getUnits());
		logger.info("siWrapper.item.units: "+siWrapper.item.getUnits());

		/*
		 * TODO: we use the units diff on StockItemWrapper for determining the number
		 * of units to add and call the add method on stockSystem
		 */
		int unitsToAdd = siWrapper.getUnitsDiff();
		logger.info("UNITS TO ADD:" +unitsToAdd);
		stockSystem.addToStock(siWrapper.getProduct(), siWrapper.getPos().getId(),unitsToAdd);

		/*
		 * TODO: once we are done we call the updateMethods on the item to set the new
		 * value of units on the StockItem object itself
		 */
		siWrapper.updateUnits();

		/* returning the empty string here results in keeping the current view */
		return "";
	}
	
	/*
	 * TODO: add a method for updating the price of a stock item
	 */
	public String updatePrice() {
		/*
		 * TODO: we accesss the parameters from the FacesContext.getCurrentInstance()
		 * .getExternalContext().getRequestParameterMap()
		 */
		Map<String, String> args = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		/* TODO: read out the parameter(s) that we need */
		String siId = (String)args.get("siId");
		
		logger.info("updatePrice() with siId: "+siId);

		/*
		 * TODO: we read out the StockItemWrapper from the local map
		 */
		StockItemWrapper siWrapper = stockItemsMap.get(siId);
		
		logger.info("siWrapper.price: "+siWrapper.getPrice());
		logger.info("siWrapper.item.price: "+siWrapper.item.getPrice());

		/*
		 * TODO: we use the units diff on StockItemWrapper for determining the number
		 * of units to add and call the add method on stockSystem
		 */
		stockSystem.updateSpecificStockItem(siWrapper.getProduct(), siWrapper.getPos().getId());
		/*
		 * TODO: once we are done we call the updateMethods on the item to set the new
		 * value of units on the StockItem object itself
		 */
		siWrapper.updatePrice();

		/* returning the empty string here results in keeping the current view */
		return "";
	}
	
	/*
	 * TODO: add a method that calls the doShopping() method on stockSystemHelper
	 */

	/*
	 * a method for refreshing the view
	 */
	public void refreshView() {
		logger.info("refreshView()");
		// we call loadData
		loadData();
	}
	
	public void doShopping() throws ValidatorException{
		stockSystemHelper.doShopping();
	}

	/*
	 * a method loadData() that loads all stock items and uses some wrapper
	 * classes to provide an integrated view on the data enriching the items
	 * with touchpoint names from crm
	 * 
	 * TODO: should be called once this bean is created
	 */
	@PostConstruct
	public void loadData() {

		logger.info("@postConstruct: helper is: " + stockSystemHelper);

		// TODO: remove the comments (you might start with the first for statement and keep the second one commented until access to the touchpoints bean is done)
		this.stockItemsMap.clear();
		stockItems.clear();

		/*
		 * read out the stock items and create the stock items and touchpoints
		 * map
		 */
		logger.info("getCompleteStock(): "+stockSystem.getAllProductsOnStock());
		for (StockItem item : stockSystem.getAllProductsOnStock()) {
			StockItemWrapper wrapper = new StockItemWrapper(item);
			this.stockItemsMap.put(wrapper.getId(), wrapper);
			System.out.println("PREIS: "+item.getProduct().getPrice());
		}
		stockItems.addAll(stockItemsMap.values());

		/* also read out the touchpoints */
		for (AbstractTouchpoint tp : touchpointBean.readAllTouchpoints()) {
			if (!this.touchpointsMap.containsKey(tp.getErpPointOfSaleId())) {
				this.touchpointsMap.put(tp.getErpPointOfSaleId(), tp);
			}
		}
	}

	/*
	 * an inner class that is wrapped around a stock item and provides the data
	 * we need to display it and handle updates
	 * 
	 * you might use this one or implement an individual solution
	 */
	public class StockItemWrapper {

		private StockItem item;

		/*
		 * the units that will be set via the gui, which we need to keep
		 * distinct from the actual units of the StockItem
		 */
		private int units;

		/*
		 * use an id that combines the ids of touchpoint and product -- this is
		 * required for retrieving an item from the list
		 */
		public String getId() {
			return item.getPos().getId() + "_" + item.getProduct().getId();
		}

		/* constructor */
		public StockItemWrapper(StockItem item) {
			this.item = item;
			this.units = item.getUnits();
		}

		public String getTouchpointName() {
			return touchpointsMap.get(item.getPos().getId()).getName();
		}

		public PointOfSale getPos() {
			return item.getPos();
		}

		public int getPrice() {
			if (this.item.getPrice() > 0) {
				return this.item.getPrice();
			}
			return this.item.getProduct().getPrice();
		}

		public void setPrice(int price) {
			logger.info("setPrice(): " + price);
			this.item.setPrice(price);
		}

		public IndividualisedProductItem getProduct() {
			return (IndividualisedProductItem) this.item.getProduct();
		}

		public int getUnits() {
			return this.units;
		}

		public void setUnits(int units) {
			this.units = units;
		}

		public int getUnitsDiff() {
			return this.units - this.item.getUnits();
		}

		/*
		 * update the units on the item after successful stock item update on
		 * the ejb
		 */
		public void updateUnits() {
			this.item.setUnits(this.units);
		}
		
		public void updatePrice() {
			this.item.setPrice(this.getProduct().getPrice());;
		}

	}

}


package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductBundle;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/add3", serviceName = "StockSystemEjbWebService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@Singleton
public class StockSystem implements StockSystemRemote, StockSystemLocal {
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@EJB
	private StockItemCRUDLocal stockItemCRUD;
	
	@EJB
	private PointOfSaleCRUDLocal pointOfSaleCRUD;
	
	@WebMethod
	@Override
	public void addToStock(AbstractProduct product, int pointOfSaleId, int units) {
		
		final PointOfSale pointOfSale = pointOfSaleCRUD.readPointOfSale(pointOfSaleId);
		
		if (pointOfSale != null) {
			final StockItem stockItem = stockItemCRUD.getStockItem(product, pointOfSaleId);
			if (stockItem != null) {
				stockItem.setUnits(units + stockItem.getUnits());
			} else {
				// TODO: Q: Warum hier merge?
				final StockItem newStockItem = new StockItem(em.merge(product), pointOfSale, units);
				stockItemCRUD.createStockItem(newStockItem);
			}
		} else {
			// TODO: if PointOfSale not exists? Create??
		}
	}

	@WebMethod
	@Override
	public List<AbstractProduct> getProductsOnStock(int pointOfSaleId) {
		final List<AbstractProduct> productItems = 
				new LinkedList<AbstractProduct>();
		
		for (StockItem stockItem : stockItemCRUD.getAllStockItemsByPosId(pointOfSaleId)) {
			final AbstractProduct product = stockItem.getProduct();
			// TODO: Campaigns ????!!!!!!!!!!!????????
			if (product instanceof IndividualisedProductItem) {
				productItems.add((IndividualisedProductItem) product);
			} else if (product instanceof Campaign) {
				Collection<ProductBundle> productBundles = ((Campaign) product).getBundles();
				for (ProductBundle pb : productBundles) {
					productItems.add(pb.getProduct());
				}
			}
		}
		return productItems;
	}

	@WebMethod
	@Override
	public List<AbstractProduct> getAllProductsOnStock() {
		final List<AbstractProduct> productItems = new LinkedList<AbstractProduct>();
		
		for (StockItem stockItem : stockItemCRUD.getAllStockItems()) {
			final AbstractProduct product = stockItem.getProduct();
			// TODO: Campaigns ????!!!!!!!!!!!????????
			if (product instanceof IndividualisedProductItem) {
				productItems.add((IndividualisedProductItem) product);
			} else if (product instanceof Campaign) {
				Collection<ProductBundle> productBundles = ((Campaign) product).getBundles();
				for (ProductBundle pb : productBundles) {
					productItems.add(pb.getProduct());
				}
			}
		}
		return productItems;
	}

	@WebMethod
	@Override
	public int getUnitsOnStock(AbstractProduct product, int pointOfSaleId) {
		return stockItemCRUD.getUnitsOnStock(product, pointOfSaleId);
	}
	
	@WebMethod(operationName="fuckYou")
	@Override
	public int getUnitsOnStock2(int productId, int pointOfSaleId) {
		return stockItemCRUD.getUnitsOnStock(productId, pointOfSaleId);
	}

	@WebMethod
	@Override
	public int getTotalUnitsOnStock(AbstractProduct product) {
		// TODO: Maybe change return type to long
		return (int) stockItemCRUD.getSumTotalUnits(product);
	}

	@WebMethod
	@Override
	public List<Integer> getPointsOfSale(AbstractProduct product) {
		final List<Integer> pointOfSaleIds = new LinkedList<Integer>();

		for (StockItem stockItem : stockItemCRUD.getStockItemsByProduct(product)) {
			pointOfSaleIds.add(stockItem.getPos().getId());
		}
		return pointOfSaleIds;
	}

	@WebMethod
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void removeFromStock(AbstractProduct product, int pointOfSaleId,
			int units) {
		final StockItem stockItem = stockItemCRUD.getStockItem(product, pointOfSaleId);
		if (stockItem != null) {
			int newUnits = stockItem.getUnits() - units;
			stockItem.setUnits(newUnits < 0 ? 0 : newUnits);
			em.merge(stockItem);
			// TODO: Q: IF 0 DELETE?
		}
	}

}

package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Singleton
public class StockSystem implements StockSystemRemote {
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@EJB
	private StockItemCRUDLocal stockItemCRUD;
	
	@EJB
	private PointOfSaleCRUDLocal pointOfSaleCRUD;
	
	@Override
	public void addToStock(final IndividualisedProductItem product,
			final int pointOfSaleId, final int units) {
		
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

	@Override
	public void removeFromStock(IndividualisedProductItem product,
			int pointOfSaleId, int units) {
		
		final StockItem stockItem = stockItemCRUD.getStockItem(product, pointOfSaleId);
		if (stockItem != null) {
			int newUnits = stockItem.getUnits() - units;
			stockItem.setUnits(newUnits < 0 ? 0 : newUnits);
			em.merge(stockItem);
			// TODO: Q: IF 0 DELETE?
		}
	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId) {
		final List<IndividualisedProductItem> productItems = 
				new LinkedList<IndividualisedProductItem>();
		
		for (StockItem stockItem : stockItemCRUD.getAllStockItemsByPosId(pointOfSaleId)) {
			final AbstractProduct product = stockItem.getProduct();
			// TODO: Campaigns ????!!!!!!!!!!!????????
			if (product instanceof IndividualisedProductItem) {
				productItems.add((IndividualisedProductItem) product);
			}
		}
		return productItems;
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		final List<IndividualisedProductItem> productItems = new LinkedList<IndividualisedProductItem>();
		
		for (StockItem stockItem : stockItemCRUD.getAllStockItems()) {
			final AbstractProduct product = stockItem.getProduct();
			// TODO: Campaigns ????!!!!!!!!!!!????????
			if (product instanceof IndividualisedProductItem) {
				productItems.add((IndividualisedProductItem) product);
			}
		}
		return productItems;
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product, int pointOfSaleId) {
		return stockItemCRUD.getUnitsOnStock(product, pointOfSaleId);
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		// TODO: Maybe change return type to long
		return (int) stockItemCRUD.getSumTotalUnits(product);
	}

	@Override
	public List<Integer> getPointsOfSale(IndividualisedProductItem product) {
		final List<Integer> pointOfSaleIds = new LinkedList<Integer>();

		for (StockItem stockItem : stockItemCRUD.getStockItemsByProduct(product)) {
			pointOfSaleIds.add(stockItem.getPos().getId());
		}
		return pointOfSaleIds;
	}

}

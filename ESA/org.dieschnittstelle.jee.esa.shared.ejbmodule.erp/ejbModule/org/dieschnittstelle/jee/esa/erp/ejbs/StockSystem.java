package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

@Singleton
public class StockSystem implements StockSystemRemote {
	
	private final static Logger logger = Logger.getLogger(StockSystem.class);
	
	private final Map<Integer, Map<String, StockItem>> DB = new HashMap<Integer, Map<String, StockItem>>();
	
	@Override
	public void addToStock(IndividualisedProductItem product,
			int pointOfSaleId, int units) {
		
		final Map<String, StockItem> stockItemsOfPoint = DB.get(pointOfSaleId);
		
		if (stockItemsOfPoint != null) {
			final StockItem stockItem = stockItemsOfPoint.get(product.getName());
			if (stockItem != null) {
				stockItem.setUnits(stockItem.getUnits() + units);
			} else {
				final StockItem stockItem1 = new StockItem();
				stockItem1.setProduct(product);
				stockItem1.setUnits(units);
				stockItemsOfPoint.put(product.getName(), stockItem1);
			}
		} else {	
			final StockItem stockItem = new StockItem();
			stockItem.setProduct(product);
			stockItem.setUnits(units);
			
			final Map<String, StockItem> stockItemEntry = 
					new HashMap<String, StockItem>(1);
			stockItemEntry.put(product.getName(), stockItem);
			DB.put(pointOfSaleId, stockItemEntry);
		}
		logger.info("DB size: " + DB.size());
	}

	@Override
	public void removeFromStock(IndividualisedProductItem product,
			int pointOfSaleId, int units) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product,
			int pointOfSaleId) {
		int unitsOnStock = 0;
		
		final Map<String, StockItem> stockItemsOfPoint = DB.get(pointOfSaleId);
		if (stockItemsOfPoint != null) {
			final StockItem stockItem = stockItemsOfPoint.get(product.getName());
			if (stockItem != null) {
				unitsOnStock = stockItem.getUnits();
			}
		}
		return unitsOnStock;
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getPointsOfSale(IndividualisedProductItem product) {
		// TODO Auto-generated method stub
		return null;
	}

}

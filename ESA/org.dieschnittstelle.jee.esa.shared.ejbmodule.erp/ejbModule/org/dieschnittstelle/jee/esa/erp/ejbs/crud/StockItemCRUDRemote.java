package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Remote;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Remote
public interface StockItemCRUDRemote {

	StockItem createStockItem(StockItem item);
	
	List<StockItem> getAllStockItems();
	
	StockItem getStockItem(AbstractProduct product, int pointOfSaleId);
	
	List<StockItem> getAllStockItemsByPosId(int posId);
	
	List<StockItem> getStockItemsByProduct(AbstractProduct product);
	
	long getSumTotalUnits(AbstractProduct product);
	
	int getUnitsOnStock(AbstractProduct product, int pointOfSaleId);
	
	int getUnitsOnStock(int productId, int pointOfSaleId);
}

package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Local
public interface StockItemCRUDLocal {

	public StockItem createStockItem(StockItem item);
	
	public List<StockItem> getAllStockItems();
	
	public StockItem getStockItem(AbstractProduct product, int pointOfSaleId);
	
	public List<StockItem> getAllStockItemsByPosId(int posId);
	
	public List<StockItem> getStockItemsByProduct(AbstractProduct product);
}

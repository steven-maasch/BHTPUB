package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.erp.entities.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Local
public interface StockItemCRUDLocal {

	public StockItem createStockItem(StockItem item);
	
	public List<StockItem> readAllStockItems();
	
	public StockItem readStockItem(ProductAtPosPK posId);
	
	public boolean deleteStockItem(ProductAtPosPK posId);
	
}

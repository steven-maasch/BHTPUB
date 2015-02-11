package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.STOCK_SYSTEM_BEAN;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class StockSystemClient implements StockSystemRemote, StockSystemLocal {

	private StockSystemRemote proxy;
	
	public StockSystemClient() throws Exception {
		Context context = new InitialContext();
		
		this.proxy = (StockSystemRemote) context
				.lookup(STOCK_SYSTEM_BEAN);
	}
	
	@Override
	public void addToStock(AbstractProduct product, int pointOfSaleId, int units) {
		this.proxy.addToStock(product, pointOfSaleId, units);
	}

	@Override
	public void removeFromStock(AbstractProduct product, int pointOfSaleId,
			int units) {
		this.proxy.removeFromStock(product, pointOfSaleId, units);
	}

	@Override
	public List<AbstractProduct> getProductsOnStock(int pointOfSaleId) {
		return this.proxy.getProductsOnStock(pointOfSaleId);
	}

	@Override
	public List<AbstractProduct> getAllProductsOnStock() {
		return this.proxy.getAllProductsOnStock();
	}

	@Override
	public int getUnitsOnStock(AbstractProduct product, int pointOfSaleId) {
		return this.proxy.getUnitsOnStock(product, pointOfSaleId);
	}
	
	@Override
	public int getUnitsOnStock2(int productId, int pointOfSaleId) {
		return this.proxy.getUnitsOnStock2(productId, pointOfSaleId);
	}
	
	@Override
	public int getTotalUnitsOnStock(AbstractProduct product) {
		return this.proxy.getTotalUnitsOnStock(product);
	}

	@Override
	public List<Integer> getPointsOfSale(AbstractProduct product) {
		return this.proxy.getPointsOfSale(product);
	}


	@Override
	public List<StockItem> getAllStockItems() {
		return this.proxy.getAllStockItems();
	}

}

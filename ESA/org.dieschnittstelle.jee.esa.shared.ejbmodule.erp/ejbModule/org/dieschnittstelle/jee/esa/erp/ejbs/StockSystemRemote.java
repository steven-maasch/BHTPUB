package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.List;

import javax.ejb.Remote;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

@Remote
public interface StockSystemRemote {
	
		/**
		 * adds some units of a product to the stock of a point of sale
		 *
		 * @param product
		 * @param pointOfSaleId
		 * @param units
		 */
		void addToStock(AbstractProduct product,int pointOfSaleId,int units);

		/**
		 * removes some units of a product from the stock of a point of sale
		 *	
		 * @param product
		 * @param pointOfSaleId
		 * @param units
		 */
		void removeFromStock(AbstractProduct product,int pointOfSaleId,int units);
		
		/**
		 * returns all products on stock of some pointOfSale
		 * 
		 * @param pointOfSaleId
		 * @return
		 */
		List<AbstractProduct> getProductsOnStock(int pointOfSaleId);

		/**
		 * returns all products on stock
		 * 
		 * @return
		 */
		List<AbstractProduct> getAllProductsOnStock();

		/**
		 * returns the units on stock for a product at some point of sale
		 * 
		 * @param product
		 * @param pointOfSaleId
		 * @return
		 */
		int getUnitsOnStock(AbstractProduct product, int pointOfSaleId);
		
		int getUnitsOnStock(int productId, int pointOfSaleId);

		/**
		 * returns the total number of units on stock for some product
		 * 
		 * @param product
		 * @return
		 */
		int getTotalUnitsOnStock(AbstractProduct product);
		
		/**
		 * returns the points of sale where some product is available
		 * 
		 * @param product
		 * @return
		 */
		List<Integer> getPointsOfSale(AbstractProduct product);

}

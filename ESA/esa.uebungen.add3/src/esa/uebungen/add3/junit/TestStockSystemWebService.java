package esa.uebungen.add3.junit;

import static org.junit.Assert.*;

import org.dieschnittstelle.jee.esa.uebungen.add3.AbstractProduct;
import org.dieschnittstelle.jee.esa.uebungen.add3.StockSystem;
import org.dieschnittstelle.jee.esa.uebungen.add3.StockSystemEjbWebService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/*
 * 
 */
public class TestStockSystemWebService {
	
	private StockSystem serviceClient;
	
	
	@Before
	public void checkStockCreated() {
		// TODO instantiate the serviceClient by instantiating the generated web service class and getting the port for StockSystemRemote
		StockSystemEjbWebService service = new StockSystemEjbWebService();
		
		serviceClient = service.getStockSystemPort();	
	}
		
	@Test
	public void stockSystemServiceWorks() {
		
		/* 
		 * IMPORTANT !!
		 * Call TestStockSystem 
		 * in /org.dieschnittstelle.jee.esa.ejbs.client/src/org/dieschnittstelle/jee/esa/ejbs/client/junit/TestStockSystem.java
		 * to create stock items
		 */
		
		// read out all products
		List<AbstractProduct> products = serviceClient.getAllProductsOnStock();
		assertTrue("stock exists and can be read", products.size() > 0);		
		
		// we are using the first product for the tests...
		AbstractProduct testprod = products.get(0);

		// obtain the poss where the first product is available
		List<Integer> poss = serviceClient.getPointsOfSale(testprod);
		assertTrue("selected product is associated with at least one point of sale", poss.size() > 0);		
		
		// we are using the first pos for the tests...
		int testpos = poss.get(0);
		
		// obtain the local and total units
		int unitsAtPos = serviceClient.getUnitsOnStock(testprod, testpos);
		int unitsTotal = serviceClient.getTotalUnitsOnStock(testprod);
		
		// add units for the first pos
		int unitsToAdd = 5;
		serviceClient.addToStock(testprod, testpos, unitsToAdd);
		
		// compare the final units
		assertEquals("after adding units, units at pos correctly incremented", unitsAtPos + unitsToAdd, serviceClient.getUnitsOnStock(testprod, testpos));
		assertEquals("after adding units, total units correctly incremented", unitsTotal + unitsToAdd, serviceClient.getTotalUnitsOnStock(testprod));
		
		
	}
	
}

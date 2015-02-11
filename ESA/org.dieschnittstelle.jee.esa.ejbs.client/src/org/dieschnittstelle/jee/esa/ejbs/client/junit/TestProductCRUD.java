package org.dieschnittstelle.jee.esa.ejbs.client.junit;

import java.util.List;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.*;

import org.dieschnittstelle.jee.esa.ejbs.client.Constants;
import org.dieschnittstelle.jee.esa.ejbs.client.ejbclients.ProductCRUDClient;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestProductCRUD {
	
	private ProductCRUDClient client;
	
	@Before
	public void prepareContext() throws Exception {
		client = new ProductCRUDClient();
		Constants.resetEntities();	
	}
		
	@Test
	public void crudWorksForindividualisedProductItems() {
		List<AbstractProduct> prodlistBefore;
		// read all products
		assertNotNull("product list can be read",prodlistBefore = client.readAllProducts());
		
		/* CREATE + READ */
		// create and use the id
		PRODUCT_1.setId(client.createProduct(PRODUCT_1).getId());
		
		assertEquals("product list is appended on create",1,client.readAllProducts().size()-prodlistBefore.size());		

		// read the products and check whether they are equivalent
		AbstractProduct testProduct = client.readProduct(PRODUCT_1.getId());
		
		assertNotNull("new product can be read",testProduct);
		assertEquals("new product name is correct",PRODUCT_1.getName(),testProduct.getName());

		/* UPDATE */
		// change the local name
		PRODUCT_1.setName(PRODUCT_1.getName() + " " + PRODUCT_1.getName());		
		// update the product on the server-side
		client.updateProduct(PRODUCT_1);
		
		// read out the product and compare the names
		testProduct = client.readProduct(PRODUCT_1.getId());
		assertEquals("product name is updated correctly",PRODUCT_1.getName(), testProduct.getName());
		
		/* DELETE */
		assertTrue("product can be deleted",  client.deleteProduct(PRODUCT_1.getId()));
		assertNull("deleted product does not exist anymore", client.readProduct(PRODUCT_1.getId()));
		assertEquals("product list is reduced on delete",prodlistBefore.size(),client.readAllProducts().size());
		
		// for jsf
//		client.createProduct(PRODUCT_2);

	}
	
	@Test
	public void crudWorksForCampaigns() {
		
		List<AbstractProduct> prodlistBefore = client.readAllProducts();
		
		// first create the individual items
		PRODUCT_1.setId(client.createProduct(PRODUCT_1).getId());
		PRODUCT_2.setId(client.createProduct(PRODUCT_2).getId());
		
		/* CREATE */
		CAMPAIGN_1.setId(client.createProduct(CAMPAIGN_1).getId());		
		assertEquals("product list is appended on create for campaigns",3, client.readAllProducts().size()-prodlistBefore.size());		
		
		Campaign testCampaign = (Campaign)client.readProduct(CAMPAIGN_1.getId());
		assertEquals("campaign contains correct number of bundles",testCampaign.getBundles().size(),2);		

		/* DELETE */
		client.deleteProduct(CAMPAIGN_1.getId());
		assertEquals("product list is reduced by 1 on delete for campaigns",2, client.readAllProducts().size()-prodlistBefore.size());		

		assertNull("deleted campaign does not exist anymore",client.readProduct(CAMPAIGN_1.getId()));
		assertNotNull("individual products still exist after campaign deletion",client.readProduct(PRODUCT_1.getId()));
	}

}

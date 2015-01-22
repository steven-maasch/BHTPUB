package esa.uebungen.add2.junit;

import java.util.List;

import org.dieschnittstelle.jee.esa.uebungen.add2.AbstractProduct;
import org.dieschnittstelle.jee.esa.uebungen.add2.ProductCRUDStateless;
import org.dieschnittstelle.jee.esa.uebungen.add2.ProductEjbCRUDWebService;

public class ProductCRUDClient {

	private ProductCRUDStateless proxy;

	public ProductCRUDClient() throws Exception {
		
		ProductEjbCRUDWebService service = new ProductEjbCRUDWebService();
		
		proxy = service.getProductCRUDStatelessPort();
		
	}

	public AbstractProduct createProduct(AbstractProduct prod) {
		AbstractProduct created = proxy.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	public List<AbstractProduct> readAllProducts() {
		return proxy.readAllProducts().getItem();
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
		return proxy.updateProduct(update);
	}

	public AbstractProduct readProduct(int productID) {
		return proxy.readProduct(productID);
	}

	public boolean deleteProduct(int productID) {
		return proxy.deleteProduct(productID);
	}

}

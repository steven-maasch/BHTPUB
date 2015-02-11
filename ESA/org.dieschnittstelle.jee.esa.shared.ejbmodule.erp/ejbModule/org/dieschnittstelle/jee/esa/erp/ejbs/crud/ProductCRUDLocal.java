package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

@Local
public interface ProductCRUDLocal {

		AbstractProduct createProduct(AbstractProduct prod);

		List<AbstractProduct> readAllProducts();

		AbstractProduct updateProduct(AbstractProduct update);

		AbstractProduct readProduct(int productID);

		boolean deleteProduct(int productID);

}

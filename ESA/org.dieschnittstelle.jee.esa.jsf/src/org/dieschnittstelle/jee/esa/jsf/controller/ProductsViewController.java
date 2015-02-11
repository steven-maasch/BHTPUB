package org.dieschnittstelle.jee.esa.jsf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDStateless;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.jsf.Constants;
import org.dieschnittstelle.jee.esa.jsf.model.ShoppingCartModel;
import org.jboss.logging.Logger;

@ManagedBean(name = "productsVC")
@SessionScoped
public class ProductsViewController {

	protected static Logger logger = Logger
			.getLogger(ProductsViewController.class);


	public ProductsViewController() {
		logger.info("<constructor>");
	}

	/**
	 * use the shopping cart
	 */
	@ManagedProperty(value = "#{shoppingCartModel}")
	private ShoppingCartModel shoppingCartModel;
	
	/**
	 * accessor for the shopping cart used for injecting the value
	 */
	public void setShoppingCartModel(ShoppingCartModel shoppingCartModel) {
		logger.info("setShoppingCartModel(): " + shoppingCartModel);
		this.shoppingCartModel = shoppingCartModel;
	}
	
	/*
	 * UE JSF1: use the products bean instead of the local list of products
	 */
//	private List<AbstractProduct> products = new ArrayList<AbstractProduct>();

	@EJB(mappedName="java:global/org.dieschnittstelle.jee.esa.ejbs/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/ProductCRUDStateless!org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal")
	private ProductCRUDLocal products;
	
	/*
	 * we need getters and setters for the bean attributes accessed via facelets
	 */
	public List<AbstractProduct> getProducts() {
		logger.debug(products.readAllProducts());
		if (products.readAllProducts() != null) {
			return products.readAllProducts();
		} else {
			return null;
		}

	}

	public void setProducts(List<AbstractProduct> products) {
		
//		this.products = products;
	}

	@PostConstruct
	public void startup() {
		logger.info("@PostConstruct: " + shoppingCartModel);
		// we add test products here
//		products.createProduct(Constants.PRODUCT_1);
//		products.add(Constants.PRODUCT_2);
	}
	
	/**
	 * action method that updates the product cart. The information which product shall be added is passed by a request parameter that is set by the 
	 * @return
	 */
	public String addProductToCart() {
		logger.info("addProductToCart()");
		
		// read out the id of the product to be added by the <f:param> Attribute of the commandButton in products.xhtml 
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		final String id = params.get("id");

		logger.info("addProductToCart(): got id " + id + " in params: "
				+ params);

		// add the product to the cart
		logProductBundleKlass();
		
		
//		AbstractProduct product = findProduct(Integer.parseInt(id));
		AbstractProduct product = products.readProduct(Integer.parseInt(id));
		CrmProductBundle productBundle = new CrmProductBundle(product.getId(),
				1);
		productBundle.setProductObj(product);
		shoppingCartModel.addProduct(productBundle);

		return "";
	}
	
	private void logProductBundleKlass() {
		StringBuffer log = new StringBuffer();
		log.append(CrmProductBundle.class + "\n");
		ClassLoader cl = CrmProductBundle.class.getClassLoader();
		do {
			log.append("\t"+ cl + "\n");
			cl = cl.getParent();
		}
		while (cl != null);
		
		logger.info("class loader hierarchy of CrmProductBundle is: \n" + log);	
	}

	/**
	 * this is a rather trivial action method, which could have been dealt with
	 * by an explicit link in the action element
	 * 
	 * @return
	 */
	public String gotoCart() {
		return "shoppingCart";
	}

	/**
	 * helper method
	 * @param id
	 * @return
	 */
//	private AbstractProduct findProduct(int id) {
//		for (AbstractProduct prod : this.products) {
//			if (prod.getId() == id)
//				return prod;
//		}
//
//		return null;
//	}

}

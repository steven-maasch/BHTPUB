package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/add2", serviceName = "ProductEjbCRUDWebService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@Stateless
public class ProductCRUDStateless implements ProductCRUDRemote {
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@WebMethod
	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		em.persist(prod);
		return prod;
	}

	@WebMethod
	@Override
	public List<AbstractProduct> readAllProducts() {
		final TypedQuery<AbstractProduct> query = 
			em.createQuery("SELECT p FROM AbstractProduct p", AbstractProduct.class);
		return query.getResultList();
	}

	@WebMethod
	@Override
	public AbstractProduct updateProduct(AbstractProduct update) {
		em.merge(update);
		return update;
	}

	@WebMethod
	@Override
	public AbstractProduct readProduct(int productID) {
		return em.find(AbstractProduct.class, productID);
	}

	@WebMethod
	@Override
	public boolean deleteProduct(int productID) {
		em.remove(em.find(AbstractProduct.class, productID));
		return true;
	}

}

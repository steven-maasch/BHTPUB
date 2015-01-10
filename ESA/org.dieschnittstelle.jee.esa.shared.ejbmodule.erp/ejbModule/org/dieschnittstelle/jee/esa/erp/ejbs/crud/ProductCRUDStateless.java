package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

@Stateless
public class ProductCRUDStateless implements ProductCRUDRemote {
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		em.persist(prod);
		return prod;
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		final TypedQuery<AbstractProduct> query = 
			em.createQuery("SELECT p FROM AbstractProduct p", AbstractProduct.class);
		return query.getResultList();
	}

	@Override
	public AbstractProduct updateProduct(AbstractProduct update) {
		em.merge(update);
		return update;
	}

	@Override
	public AbstractProduct readProduct(int productID) {
		return em.find(AbstractProduct.class, productID);
	}

	@Override
	public boolean deleteProduct(int productID) {
		em.remove(em.find(AbstractProduct.class, productID));
		return true;
	}

}

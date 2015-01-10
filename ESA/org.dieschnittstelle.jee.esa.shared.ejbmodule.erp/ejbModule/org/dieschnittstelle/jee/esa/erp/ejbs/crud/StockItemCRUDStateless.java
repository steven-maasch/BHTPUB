package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.erp.entities.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Stateless
public class StockItemCRUDStateless implements StockItemCRUDLocal {

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@Override
	public StockItem createStockItem(StockItem item) {
		em.persist(item);
		return item;
	}

	@Override
	public List<StockItem> readAllStockItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockItem readStockItem(ProductAtPosPK posId) {
		return em.find(StockItem.class, posId);
	}

	@Override
	public boolean deleteStockItem(ProductAtPosPK posId) {
		em.remove(em.find(StockItem.class, posId));
		return true;
	}

}

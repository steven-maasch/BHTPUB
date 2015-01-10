package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

@Stateless
public class StockItemCRUDStateless implements StockItemCRUDLocal {

	
	private final Logger logger = Logger.getLogger(StockItemCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@Override
	public StockItem createStockItem(StockItem item) {
		em.persist(item);
		return item;
	}

	@Override
	public List<StockItem> getAllStockItems() {
		final TypedQuery<StockItem> query = 
				em.createNamedQuery("StockItem.findAll", StockItem.class);
			return query.getResultList();
	}

	@Override
	public List<StockItem> getAllStockItemsByPosId(int posId) {
		final TypedQuery<StockItem> query = 
				em.createNamedQuery("StockItem.findAllByPosId", StockItem.class);
		return query.setParameter("pos_id", posId).getResultList();
	}

	@Override
	public StockItem getStockItem(AbstractProduct product, int pointOfSaleId) {
		final TypedQuery<StockItem> query = em.createNamedQuery("StockItem.findByProductAndPosId", StockItem.class);
		
		final List<StockItem> stockItem = query.setParameter("product_id", product.getId())
				.setParameter("pos_id", pointOfSaleId)
				.getResultList();
		
		if (stockItem.size() == 1) {
			return stockItem.get(0);
		} else {
			logger.info("----- No StockItem found");
			return null;
		}
	}

	@Override
	public List<StockItem> getStockItemsByProduct(AbstractProduct product) {
		final TypedQuery<StockItem> query = em.createNamedQuery("StockItem.findByProductId", StockItem.class);
		return query.setParameter("product_id", product.getId()).getResultList();
	}
	
	

}

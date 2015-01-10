package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.jboss.logging.Logger;

@Entity
@Access(AccessType.FIELD)
@Table(name = "stock")
@IdClass(ProductAtPosPK.class)
@NamedQueries({
	@NamedQuery(name="StockItem.findAll", query="SELECT s FROM StockItem s"),
	@NamedQuery(name="StockItem.findByProductAndPosId", 
		query="SELECT s FROM StockItem s WHERE s.product.id = :product_id AND s.pos.id = :pos_id"),
	@NamedQuery(name="StockItem.findByProductId", 
		query="SELECT s FROM StockItem s WHERE s.product.id = :product_id"),
	@NamedQuery(name="StockItem.findAllByPosId", 
		query="SELECT s FROM StockItem s WHERE s.pos.id = :pos_id")
})
public class StockItem implements Serializable {

	private static final long serialVersionUID = -523570877265085869L;

	protected static Logger logger = Logger.getLogger(StockItem.class);

	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private PointOfSale pos;

	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private AbstractProduct product;

	private int price;
	
	private int units;

	public StockItem() {

	}

	public StockItem(AbstractProduct product,
			PointOfSale pos, int units) {
		this.product = product;
		this.pos = pos;
		this.units = units;
	}

	public PointOfSale getPos() {
		return pos;
	}

	public void setPos(PointOfSale pos) {
		this.pos = pos;
	}

	public AbstractProduct getProduct() {
		return product;
	}

	public void setProduct(AbstractProduct product) {
		this.product = product;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/*
	 * the lifecycle log messages
	 */

	@PostLoad
	public void onPostLoad() {
		logger.info("onPostLoad(): " + this);
	}

	@PostPersist
	public void onPostPersist() {
		logger.info("onPostPersist(): " + this);
	}

	@PostRemove
	public void onPostRemove() {
		logger.info("onPostRemove(): " + this);
	}

	@PostUpdate
	public void onPostUpdate() {
		logger.info("onPostUpdate(): " + this);
	}

	@PrePersist
	public void onPrePersist() {
		logger.info("onPrePersist(): " + this);
	}

	@PreRemove
	public void onPreRemove() {
		logger.info("onPreRemove(): " + this);
	}

	@PreUpdate
	public void onPreUpdate() {
		logger.info("onPreUpdate(): " + this);
	}

	public String toString() {
		return "{StockItemEntity " + this.price + ", " + this.product + "@"
				+ this.pos + "}";
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

}

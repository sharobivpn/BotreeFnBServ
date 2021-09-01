package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.botree.restaurantapp.model.rm.MetricUnit;
import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_t_raw_stock_out_items_c
 * 
 */
@XmlRootElement
@Entity
// @ManagedBean(name = "orderitem")
@Table(name = "im_t_raw_stock_out_items_c")
public class InventoryStockOutItem implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "stock_out_id")
	private InventoryStockOut inventoryStockOut;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private InventoryItems inventoryItems;

	@Expose
	@Column(name = "item_quantity")
	private Double itemQuantity;

	@Expose
	@Column(name = "current_stock")
	private double currentStock;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@ManyToOne
	@JoinColumn(name = "unit_id")
	private MetricUnit unit;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "to_whom")
	private String toWhom;

	@Expose
	@Column(name = "time")
	private String time;

	@Expose
	@Column(name = "created_by")
	private String createdBy;

	@Expose
	@Column(name = "created_date")
	private String createdDate;

	@Expose
	@Column(name = "updated_by")
	private String updatedBy;

	@Expose
	@Column(name = "updated_date")
	private String updatedDate;

	@Expose
	@Transient
	private int itemQty;

	@Expose
	@Transient
	private double currentStockOutQuantityEdpWise;

	@Expose
	@Transient
	private double edpQuantity;
	
	//added on 31.07.2019
	@Expose
	@Column(name = "item_rate")
	private Double itemRate;
	
	@Expose
	@Column(name = "item_total_price")
	private Double itemTotalPrice;

	@Expose
	@Column(name = "disc_per")
	private double discPer;
	
	@Expose
	@Column(name = "disc_amt")
	private double discAmt;
	
	@Expose
	@Column(name = "is_tax_exclusive")
	private String isTaxExclusive="Y";
	
	@Expose
	@Column(name = "tax_rate")
	private Double taxRate;

	@Expose
	@Column(name = "tax_amount")
	private Double taxAmount;

	@Expose
	@Column(name = "item_net_amount")
	private Double itemNetAmount;

	
	
	

	public double getEdpQuantity() {
		return edpQuantity;
	}

	public void setEdpQuantity(double edpQuantity) {
		this.edpQuantity = edpQuantity;
	}

	public double getCurrentStockOutQuantityEdpWise() {
		return currentStockOutQuantityEdpWise;
	}

	public void setCurrentStockOutQuantityEdpWise(
			double currentStockOutQuantityEdpWise) {
		this.currentStockOutQuantityEdpWise = currentStockOutQuantityEdpWise;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InventoryItems getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(InventoryItems inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public InventoryStockOut getInventoryStockOut() {
		return inventoryStockOut;
	}

	public void setInventoryStockOut(InventoryStockOut inventoryStockOut) {
		this.inventoryStockOut = inventoryStockOut;
	}

	public String getToWhom() {
		return toWhom;
	}

	public void setToWhom(String toWhom) {
		this.toWhom = toWhom;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(double currentStock) {
		this.currentStock = currentStock;
	}

	public MetricUnit getUnit() {
		return unit;
	}

	public void setUnit(MetricUnit unit) {
		this.unit = unit;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public double getDiscPer() {
		return discPer;
	}

	public void setDiscPer(double discPer) {
		this.discPer = discPer;
	}

	public double getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
	}

	public String getIsTaxExclusive() {
		return isTaxExclusive;
	}

	public void setIsTaxExclusive(String isTaxExclusive) {
		this.isTaxExclusive = isTaxExclusive;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getItemNetAmount() {
		return itemNetAmount;
	}

	public void setItemNetAmount(Double itemNetAmount) {
		this.itemNetAmount = itemNetAmount;
	}
	
	

}
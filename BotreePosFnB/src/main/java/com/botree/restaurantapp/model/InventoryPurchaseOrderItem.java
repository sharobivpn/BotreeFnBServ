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
 * Entity implementation class for Entity: im_t_raw_purchase_order_items_c
 * 
 */
@XmlRootElement
@Entity
// @ManagedBean(name = "orderitem")
@Table(name = "im_t_raw_purchase_order_items_c")
public class InventoryPurchaseOrderItem implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "estimate_daily_prod_id")
	private int estimateDailyProdId;

	@ManyToOne
	@JoinColumn(name = "purchase_order_id")
	private InventoryPurchaseOrder inventoryPurchaseOrder;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private InventoryItems inventoryItems;

	@Expose
	@Column(name = "vendor_id")
	private int vendorId;

	@Expose
	@Transient
	private String vendorName;

	@Expose
	@Column(name = "item_quantity")
	private Double itemQuantity;

	@Expose
	@Column(name = "unit_id")
	private int unitId;

	@Expose
	@Column(name = "rate")
	private Double rate;

	@Expose
	@Column(name = "item_total_price")
	private Double itemTotalPrice;

	@Expose
	@Column(name = "shipping_charge")
	private Double shippingCharge;

	@Expose
	@Column(name = "old_stock")
	private Double oldStock;

	@Expose
	@Column(name = "required_quantity")
	private Double requiredQuantity;
	/*
	 * @Expose
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "Item_Id") private MenuItem item;
	 */

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

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
	
	//newly added for accounting 19.04.2018
	@Expose
	@Column(name = "is_tax_exclusive")
	private String isTaxExclusive="Y";
	
	@Expose
	@Column(name = "tax_rate")
	private Double taxRate;
	
	@Expose
	@Column(name = "tax_amt")
	private Double taxAmt;
	
	@Expose
	@Column(name = "total_amt")
	private Double totalAmt;
	
	@Expose
	@Transient
	private MetricUnit metricUnit;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InventoryPurchaseOrder getInventoryPurchaseOrder() {
		return inventoryPurchaseOrder;
	}

	public void setInventoryPurchaseOrder(
			InventoryPurchaseOrder inventoryPurchaseOrder) {
		this.inventoryPurchaseOrder = inventoryPurchaseOrder;
	}

	/*
	 * public int getItemId() { return itemId; }
	 * 
	 * public void setItemId(int itemId) { this.itemId = itemId; }
	 */

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public InventoryItems getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(InventoryItems inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public Double getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public Double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(Double shippingCharge) {
		this.shippingCharge = shippingCharge;
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

	public Double getOldStock() {
		return oldStock;
	}

	public void setOldStock(Double oldStock) {
		this.oldStock = oldStock;
	}

	public Double getRequiredQuantity() {
		return requiredQuantity;
	}

	public void setRequiredQuantity(Double requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public int getEstimateDailyProdId() {
		return estimateDailyProdId;
	}

	public void setEstimateDailyProdId(int estimateDailyProdId) {
		this.estimateDailyProdId = estimateDailyProdId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
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

	public Double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public MetricUnit getMetricUnit() {
		return metricUnit;
	}

	public void setMetricUnit(MetricUnit metricUnit) {
		this.metricUnit = metricUnit;
	}
	
	

}
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
 * Entity implementation class for Entity: im_t_raw_stock_in_items_c
 * 
 */
@XmlRootElement
@Entity
// @ManagedBean(name = "orderitem")
@Table(name = "im_t_raw_stock_in_items_c")
public class InventoryStockInItem implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "stock_in_id")
	private InventoryStockIn inventoryStockIn;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private InventoryItems inventoryItems;

	@Expose
	@Column(name = "item_quantity")
	private Double itemQuantity;

	@Expose
	@Transient
	private Double edpQuantity;

	@Expose
	@Transient
	private Double currentStockInQuantityEdpWise;

	@Expose
	@Column(name = "item_total_price")
	private Double itemTotalPrice;

	@Expose
	@Column(name = "item_rate")
	private Double itemRate;

	@Expose
	@Column(name = "tax_rate")
	private Double taxRate;

	@Expose
	@Column(name = "tax_amount")
	private Double taxAmount;

	@Expose
	@Column(name = "item_gross_amount")
	private Double itemGrossAmount;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "po_id")
	private int poId;

	@Expose
	@Column(name = "po_date")
	private String poDate;

	@Expose
	@Column(name = "bill_no")
	private String billNo;

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

	@Expose
	@Column(name = "unit_id")
	private int unitId;

	@Expose
	@Transient
	private MetricUnit metricUnit;
	
	//newly added for accounting 26.04.2018
		@Expose
		@Column(name = "is_tax_exclusive")
		private String isTaxExclusive="Y";
		
		@Expose
		@Column(name = "disc_per")
		private double discPer;
		
		@Expose
		@Column(name = "disc_amt")
		private double discAmt;
		

	private static final long serialVersionUID = 1L;

	public MetricUnit getMetricUnit() {
		return metricUnit;
	}

	public void setMetricUnit(MetricUnit metricUnit) {
		this.metricUnit = metricUnit;
	}

	public Double getEdpQuantity() {
		return edpQuantity;
	}

	public void setEdpQuantity(Double edpQuantity) {
		this.edpQuantity = edpQuantity;
	}

	public Double getCurrentStockInQuantityEdpWise() {
		return currentStockInQuantityEdpWise;
	}

	public void setCurrentStockInQuantityEdpWise(
			Double currentStockInQuantityEdpWise) {
		this.currentStockInQuantityEdpWise = currentStockInQuantityEdpWise;
	}

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

	public Double getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
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

	public InventoryStockIn getInventoryStockIn() {
		return inventoryStockIn;
	}

	public void setInventoryStockIn(InventoryStockIn inventoryStockIn) {
		this.inventoryStockIn = inventoryStockIn;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
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

	public Double getItemGrossAmount() {
		return itemGrossAmount;
	}

	public void setItemGrossAmount(Double itemGrossAmount) {
		this.itemGrossAmount = itemGrossAmount;
	}

	public String getIsTaxExclusive() {
		return isTaxExclusive;
	}

	public void setIsTaxExclusive(String isTaxExclusive) {
		this.isTaxExclusive = isTaxExclusive;
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

	
}
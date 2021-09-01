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

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

/**
 * Entity implementation class for Entity: im_m_inv_type_items_c
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_m_inv_type_items_c")
public class InventoryItems implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@ManyToOne
	@JoinColumn(name = "type_id")
	private InventoryType inventoryType;

	/*
	 * @Expose
	 * 
	 * @Column(name = "type_id") private int typeId;
	 */

	@Expose
	@Column(name = "code")
	private String code;

	@Expose
	@Column(name = "name")
	private String name;

	@Expose
	@Column(name = "quantity")
	private double quantity;

	@Expose
	@Column(name = "unit")
	private String unit;

	@Expose
	@Column(name = "rate")
	private double rate;

	@Expose
	@Column(name = "barcode")
	private String barcode;

	@Expose
	@Column(name = "vendor_id")
	private int vendorId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "metric_unit_id")
	private int metricUnitId;

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
	@Column(name = "shipping_charge")
	private double shippingCharge;

	@Expose
	@Transient
	private String vendorName;

	@Expose
	@Column(name = "daily_requirement")
	private double dailyRequirement;

	@Expose
	@Column(name = "tax")
	private String tax;
	
	@Expose
	@Column(name = "is_tax_exclusive")
	private String isTaxExclusive="Y";
	
	@Expose
	@Column(name = "tax_rate")
	private double taxRate;
	
	@Expose
	@Column(name = "is_stockable")
	private String isStockable="Y";
	
	@Expose
	@Transient
	private double rawQty;
	

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public InventoryType getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public double getDailyRequirement() {
		return dailyRequirement;
	}

	public void setDailyRequirement(double dailyRequirement) {
		this.dailyRequirement = dailyRequirement;
	}

	public int getMetricUnitId() {
		return metricUnitId;
	}

	public void setMetricUnitId(int metricUnitId) {
		this.metricUnitId = metricUnitId;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getIsTaxExclusive() {
		return isTaxExclusive;
	}

	public void setIsTaxExclusive(String isTaxExclusive) {
		this.isTaxExclusive = isTaxExclusive;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public String getIsStockable() {
		return isStockable;
	}

	public void setIsStockable(String isStockable) {
		this.isStockable = isStockable;
	}

	public double getRawQty() {
		return rawQty;
	}

	public void setRawQty(double rawQty) {
		this.rawQty = rawQty;
	}

	@Override
	public String toString() {
		return "InventoryItems [id=" + id + ", inventoryType=" + inventoryType + ", code=" + code + ", name=" + name
				+ ", quantity=" + quantity + ", unit=" + unit + ", rate=" + rate + ", barcode=" + barcode
				+ ", vendorId=" + vendorId + ", storeId=" + storeId + ", metricUnitId=" + metricUnitId + ", deleteFlag="
				+ deleteFlag + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", shippingCharge=" + shippingCharge + ", vendorName=" + vendorName
				+ ", dailyRequirement=" + dailyRequirement + ", tax=" + tax + ", isTaxExclusive=" + isTaxExclusive
				+ ", taxRate=" + taxRate + ", isStockable=" + isStockable + ", rawQty=" + rawQty + "]";
	}
	
	

}
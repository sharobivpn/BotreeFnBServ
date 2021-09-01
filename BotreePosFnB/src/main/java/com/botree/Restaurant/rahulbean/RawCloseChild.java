package com.botree.Restaurant.rahulbean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.botree.restaurantapp.model.InventoryItems;
import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "im_t_raw_clse_c")
public class RawCloseChild implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@Expose
	@Column(name = "raw_clse_id")
	private int rawClseId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "item_id")
	private int itemId;

	@Expose
	@Column(name = "stock_in_quantity")
	private double stockInQuantity;

	@Expose
	@Column(name = "unit_id")
	private int unitId;

	@Expose
	@Column(name = "stock_out_quantity")
	private double stockOutQuantity;

	@Expose
	@Column(name = "calculated_stock")
	private double calculatedStock;

	@Expose
	@Column(name = "physical_stock")
	private double physicalStock;

	@Expose
	@Column(name = "waste")
	private double waste;

	@Expose
	@Column(name = "consume")
	private double consume;

	@Expose
	@Column(name = "varience")
	private double varience;

	@Expose
	@Column(name = "frm_date")
	private String frmDate;

	@Expose
	@Column(name = "to_date")
	private double toDate;

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
	@Transient
	private String approved;

	@Expose
	@Transient
	private InventoryItems inventoryItems;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getStockInQuantity() {
		return stockInQuantity;
	}

	public void setStockInQuantity(double stockInQuantity) {
		this.stockInQuantity = stockInQuantity;
	}

	public double getStockOutQuantity() {
		return stockOutQuantity;
	}

	public void setStockOutQuantity(double stockOutQuantity) {
		this.stockOutQuantity = stockOutQuantity;
	}

	public double getCalculatedStock() {
		return calculatedStock;
	}

	public void setCalculatedStock(double calculatedStock) {
		this.calculatedStock = calculatedStock;
	}

	public double getPhysicalStock() {
		return physicalStock;
	}

	public void setPhysicalStock(double physicalStock) {
		this.physicalStock = physicalStock;
	}

	public double getWaste() {
		return waste;
	}

	public void setWaste(double waste) {
		this.waste = waste;
	}

	public double getConsume() {
		return consume;
	}

	public void setConsume(double consume) {
		this.consume = consume;
	}

	public double getVarience() {
		return varience;
	}

	public void setVarience(double varience) {
		this.varience = varience;
	}

	public String getFrmDate() {
		return frmDate;
	}

	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}

	public double getToDate() {
		return toDate;
	}

	public void setToDate(double toDate) {
		this.toDate = toDate;
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getRawClseId() {
		return rawClseId;
	}

	public void setRawClseId(int rawClseId) {
		this.rawClseId = rawClseId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

}

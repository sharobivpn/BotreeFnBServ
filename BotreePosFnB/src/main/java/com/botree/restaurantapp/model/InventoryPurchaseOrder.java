package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_t_raw_purchase_order
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_raw_purchase_order")
public class InventoryPurchaseOrder implements Serializable {
	
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
	@Column(name = "estimate_daily_prod_id")
	private int estimateDailyProdId;

	@Expose
	@Column(name = "time")
	private String time;

	@Expose
	@Column(name = "user_id")
	private String userId;

	@Expose
	@Column(name = "shipping_charge")
	private double shippingCharge;

	@Expose
	@Column(name = "total_quantity")
	private Double totalQuantity;

	@Expose
	@Column(name = "total_price")
	private double totalPrice;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "po_status")
	private String poStatus;

	@Expose
	@Column(name = "po_by")
	private String poBy;

	@Expose
	@Column(name = "approved_by")
	private String approvedBy;

	@Expose
	@Column(name = "approved")
	private String approved;

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
	@OneToMany(mappedBy = "inventoryPurchaseOrder", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<InventoryPurchaseOrderItem> inventoryPurchaseOrderItems;
	
	//newly added for accounting 19.04.2018
	@Expose
	@Column(name = "vendor_id")
	private int vendorId;

	@Expose
	@Transient
	private String vendorName;
	
	@Expose
	@Column(name = "item_total")
	private double itemTotal;
	
	@Expose
	@Column(name = "tax_amt")
	private double taxAmt;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * public Time getTime() { return time; }
	 * 
	 * public void setTime(Time time) { this.time = time; }
	 */

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<InventoryPurchaseOrderItem> getInventoryPurchaseOrderItems() {
		return inventoryPurchaseOrderItems;
	}

	public void setInventoryPurchaseOrderItems(
			List<InventoryPurchaseOrderItem> inventoryPurchaseOrderItems) {
		this.inventoryPurchaseOrderItems = inventoryPurchaseOrderItems;
	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public String getPoBy() {
		return poBy;
	}

	public void setPoBy(String poBy) {
		this.poBy = poBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public int getEstimateDailyProdId() {
		return estimateDailyProdId;
	}

	public void setEstimateDailyProdId(int estimateDailyProdId) {
		this.estimateDailyProdId = estimateDailyProdId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}
	
	

}
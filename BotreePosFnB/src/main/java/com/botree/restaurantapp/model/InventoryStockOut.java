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
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_t_raw_stock_out
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_raw_stock_out")
public class InventoryStockOut implements Serializable {
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
	@Column(name = "time")
	private String time;

	@Expose
	@Column(name = "user_id")
	private String userId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "edp_id")
	private int edpId;

	@Expose
	@Column(name = "total_quantity")
	private Double totalQuantity;

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
	@OneToMany(mappedBy = "inventoryStockOut", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<InventoryStockOutItem> inventoryStockOutItems;

	@Expose
	@Column(name = "approved")
	private String approved;

	@Expose
	@Column(name = "approved_by")
	private String approvedBy;
	
	@Expose
	@Column(name = "item_total")
	private double itemTotal;
	
	@Expose
	@Column(name = "disc_per")
	private double discPer;
	
	@Expose
	@Column(name = "disc_amt")
	private double discAmt;
	
	@Expose
	@Column(name = "tax_amt")
	private double taxAmt;
	
	@Expose
	@Column(name = "round_off_amt")
	private Double roundOffAmt;
	
	@Expose
	@Column(name = "total_price")
	private double totalPrice;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<InventoryStockOutItem> getInventoryStockOutItems() {
		return inventoryStockOutItems;
	}

	public void setInventoryStockOutItems(
			List<InventoryStockOutItem> inventoryStockOutItems) {
		this.inventoryStockOutItems = inventoryStockOutItems;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getEdpId() {
		return edpId;
	}

	public void setEdpId(int edpId) {
		this.edpId = edpId;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(double itemTotal) {
		this.itemTotal = itemTotal;
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

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Double getRoundOffAmt() {
		return roundOffAmt;
	}

	public void setRoundOffAmt(Double roundOffAmt) {
		this.roundOffAmt = roundOffAmt;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	

}
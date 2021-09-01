package com.botree.restaurantapp.model.inv;

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

@XmlRootElement
@Entity
@Table(name = "im_t_fg_stock_in")
public class FgStockIn implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "edp_id")
	private int edpId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;
	
	@Expose
	@Column(name = "stock_in_type")
	private String stockInType;

	@Expose
	@Column(name = "estimated_items")
	private int estimatedItems;

	@Expose
	@Column(name = "current_items")
	private int currentItems;

	@Expose
	@Column(name = "approved")
	private String approved;

	@Expose
	@Column(name = "approved_by")
	private String approvedBy;

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
	@OneToMany(mappedBy = "fgStockInId", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<FgStockInItemsChild> fgStockInItemsChilds;
	
	//newly added on 17.06.2019
	@Expose
	@Column(name = "vendor_id")
	private int vendorId=0;
	
	@Expose
	@Transient
	private String vendorName;
	
	@Expose
	@Column(name = "bill_no")
	private String billNo;
	
	@Expose
	@Column(name = "bill_date")
	private String billDate;
	
	@Expose
	@Column(name = "item_total")
	private double itemTotal=0.0;
	
	@Expose
	@Column(name = "disc_per")
	private double discPer=0.0;
	
	@Expose
	@Column(name = "disc_amt")
	private double discAmt=0.0;
	
	@Expose
	@Column(name = "vat_amt")
	private double vatAmt=0.0;
	
	@Expose
	@Column(name = "service_tax_amt")
	private double serviceTaxAmt=0.0;
	
	@Expose
	@Column(name = "round_off_amt")
	private Double roundOffAmt=0.0;
	
	@Expose
	@Column(name = "total_price")
	private double totalPrice=0.0;

	private static final long serialVersionUID = 1L;

	public int getEdpId() {
		return edpId;
	}

	public void setEdpId(int edpId) {
		this.edpId = edpId;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getEstimatedItems() {
		return estimatedItems;
	}

	public void setEstimatedItems(int estimatedItems) {
		this.estimatedItems = estimatedItems;
	}

	public int getCurrentItems() {
		return currentItems;
	}

	public void setCurrentItems(int currentItems) {
		this.currentItems = currentItems;
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

	public List<FgStockInItemsChild> getFgStockInItemsChilds() {
		return fgStockInItemsChilds;
	}

	public void setFgStockInItemsChilds(
			List<FgStockInItemsChild> fgStockInItemsChilds) {
		this.fgStockInItemsChilds = fgStockInItemsChilds;
	}

	public String getStockInType() {
		return stockInType;
	}

	public void setStockInType(String stockInType) {
		this.stockInType = stockInType;
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

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
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

	public double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
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

	@Override
	public String toString() {
		return "FgStockIn [id=" + id + ", edpId=" + edpId + ", storeId=" + storeId + ", date=" + date + ", stockInType="
				+ stockInType + ", estimatedItems=" + estimatedItems + ", currentItems=" + currentItems + ", approved="
				+ approved + ", approvedBy=" + approvedBy + ", deleteFlag=" + deleteFlag + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", fgStockInItemsChilds=" + fgStockInItemsChilds + ", vendorId=" + vendorId + ", vendorName="
				+ vendorName + ", billNo=" + billNo + ", billDate=" + billDate + ", itemTotal=" + itemTotal
				+ ", discPer=" + discPer + ", discAmt=" + discAmt + ", vatAmt=" + vatAmt + ", serviceTaxAmt="
				+ serviceTaxAmt + ", roundOffAmt=" + roundOffAmt + ", totalPrice=" + totalPrice + "]";
	}
	
	
	

}

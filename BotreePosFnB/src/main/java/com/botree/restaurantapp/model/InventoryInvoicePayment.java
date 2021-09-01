package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.botree.restaurantapp.model.account.JournalEntry;
import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_t_raw_invoice_payment
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_raw_invoice_payment")
public class InventoryInvoicePayment implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "po_id")
	private int poId;

	@Expose
	@Column(name = "stock_in_id")
	private int stockInId;

	@Expose
	@Column(name = "stock_in_date")
	private String stockInDate;

	@Expose
	@Column(name = "vendor_id")
	private int vendorId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "bill_amount")
	private double billAmount;

	@Expose
	@Column(name = "paid_amount")
	private double paidAmount;

	@Expose
	@Column(name = "amount_to_pay")
	private double amountToPay;

	@Expose
	@Column(name = "credit_limit")
	private double creditLimit;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "is_return")
	private String isReturn;

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
	private double returnAmount;
	
	@Expose
	@Transient
	private double cr_amount;
	@Expose
	@Transient
	private double dr_amount;
	@Expose
	@Transient
	private int cr_legder_id;
	@Expose
	@Transient
	private int dr_legder_id;
	@Expose
	@Transient
	private String qs;
	
	@Expose
	@Transient
	private String is_account ;
	@Expose
	@Transient
	private List<JournalEntry> jes;
	
	private static final long serialVersionUID = 1L;

	public double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	public String getStockInDate() {
		return stockInDate;
	}

	public void setStockInDate(String stockInDate) {
		this.stockInDate = stockInDate;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public int getStockInId() {
		return stockInId;
	}

	public void setStockInId(int stockInId) {
		this.stockInId = stockInId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
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

	public double getCr_amount() {
		return cr_amount;
	}

	public void setCr_amount(double cr_amount) {
		this.cr_amount = cr_amount;
	}

	public double getDr_amount() {
		return dr_amount;
	}

	public void setDr_amount(double dr_amount) {
		this.dr_amount = dr_amount;
	}

	public int getCr_legder_id() {
		return cr_legder_id;
	}

	public void setCr_legder_id(int cr_legder_id) {
		this.cr_legder_id = cr_legder_id;
	}

	public int getDr_legder_id() {
		return dr_legder_id;
	}

	public void setDr_legder_id(int dr_legder_id) {
		this.dr_legder_id = dr_legder_id;
	}

	public String getQs() {
		return qs;
	}

	public void setQs(String qs) {
		this.qs = qs;
	}

	public String getIs_account() {
		return is_account;
	}

	public void setIs_account(String is_account) {
		this.is_account = is_account;
	}

	public List<JournalEntry> getJes() {
		return jes;
	}

	public void setJes(List<JournalEntry> jes) {
		this.jes = jes;
	}

	@Override
	public String toString() {
		return "InventoryInvoicePayment [id=" + id + ", poId=" + poId + ", stockInId=" + stockInId + ", stockInDate="
				+ stockInDate + ", vendorId=" + vendorId + ", storeId=" + storeId + ", billAmount=" + billAmount
				+ ", paidAmount=" + paidAmount + ", amountToPay=" + amountToPay + ", creditLimit=" + creditLimit
				+ ", deleteFlag=" + deleteFlag + ", isReturn=" + isReturn + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", returnAmount=" + returnAmount + ", cr_amount=" + cr_amount + ", dr_amount=" + dr_amount
				+ ", cr_legder_id=" + cr_legder_id + ", dr_legder_id=" + dr_legder_id + ", qs=" + qs + ", is_account="
				+ is_account + ", jes=" + jes + "]";
	}

}
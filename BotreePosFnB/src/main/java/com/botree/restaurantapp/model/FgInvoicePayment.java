/**
 * 
 */
package com.botree.restaurantapp.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;


/**
 * @author Habib
 *
 */
@XmlRootElement
@Entity
@Table(name = "im_t_fg_invoice_payment")
public class FgInvoicePayment implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "fg_stock_in_id")
	private int fgStockInId;

	@Expose
	@Column(name = "fg_stock_in_date")
	private String fgStockInDate;

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
	
	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFgStockInId() {
		return fgStockInId;
	}

	public void setFgStockInId(int fgStockInId) {
		this.fgStockInId = fgStockInId;
	}

	public String getFgStockInDate() {
		return fgStockInDate;
	}

	public void setFgStockInDate(String fgStockInDate) {
		this.fgStockInDate = fgStockInDate;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
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

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
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

	public double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}

	@Override
	public String toString() {
		return "FgInvoicePayment [id=" + id + ", fgStockInId=" + fgStockInId + ", fgStockInDate=" + fgStockInDate
				+ ", vendorId=" + vendorId + ", storeId=" + storeId + ", billAmount=" + billAmount + ", paidAmount="
				+ paidAmount + ", amountToPay=" + amountToPay + ", creditLimit=" + creditLimit + ", deleteFlag="
				+ deleteFlag + ", isReturn=" + isReturn + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", returnAmount=" + returnAmount + "]";
	}

	
}

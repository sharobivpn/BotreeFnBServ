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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.botree.restaurantapp.model.account.JournalEntry;
import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_t_sales_return
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_sales_return")
public class SalesReturn implements Serializable {
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
	@Column(name = "user_id")
	private String userId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "order_id")
	private int orderId;

	@Expose
	@Column(name = "customer_name")
	private String customerName;
	
	@Expose
	@Column(name = "customer_contact")
	private String customerContact;
	
	@Expose
	@Column(name = "delivery_address")
	private String deliveryAddress;
	
	@Expose
	@Column(name = "delivery_person_name")
	private String deliveryPersonName;
	
	@Expose
	@Column(name = "waiter_name")
	private String waiterName;
	
	@Expose
	@Column(name = "store_cust_id")
	private int storeCustId;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "return_type_id")
	private ReturnTypes returnTypes;
	
	@Expose
	@Column(name = "item_total")
	private Double itemTotal;
	
	@Expose
	@Column(name = "dis_per")
	private Double disPer;
	
	@Expose
	@Column(name = "dis_amt")
	private Double disAmt;
	
	@Expose
	@Column(name = "service_charge")
	private Double serviceCharge;
	
	@Expose
	@Column(name = "service_charge_rate")
	private Double serviceChargeRate;
	
	@Expose
	@Column(name = "vat_amt")
	private Double vatAmt;
	
	@Expose
	@Column(name = "service_tax_amt")
	private Double serviceTaxAmt;
	
	@Expose
	@Column(name = "net_amt")
	private Double netAmt;

	@Expose
	@Column(name = "approved")
	private String approved;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;
	
	@Expose
	@Column(name = "remark")
	private String remark;

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
	@Column(name = "is_adjusted")
	private String isAdjusted;
	

	@Expose
	@OneToMany(mappedBy = "salesReturn", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<SalesReturnItem> salesReturnItems;

	
	//for account
	@Expose
	@Transient
	private int service_charge_ledger_id;
	@Expose
	@Transient
	private String qs;
	
	@Expose
	@Transient
	private int duties_ledger_id;
	@Expose
	@Transient
	private int round_ledger_id;
	@Expose
	@Transient
	private int sale_ledger_id;
	@Expose
	@Transient
	private int discount_ledger_id;
	@Expose
	@Transient
	private int debitor_ledger_id;
	@Expose
	@Transient
	private int debitor_cash_ledger_id;
	@Expose
	@Transient
	private int card_ledger_id;
	
	//13.07.2018
		@Expose
		@Transient
		private double netTotal;
		@Expose
		@Transient
		private double grossAmt;
		@Expose
		@Transient
		private double discAmt;
		@Expose
		@Transient
		private double taxVatAmt;
		@Expose
		@Transient
		private double serviceChargeAmt;
	
	@Expose
	@Transient
	private String is_account ;
	
	@Expose
	@Transient
	private List<JournalEntry> jes;

	
	@Expose
	@Transient
	private String orderNo;
	

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryPersonName() {
		return deliveryPersonName;
	}

	public void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
	}

	public String getWaiterName() {
		return waiterName;
	}

	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}

	public int getStoreCustId() {
		return storeCustId;
	}

	public void setStoreCustId(int storeCustId) {
		this.storeCustId = storeCustId;
	}

	public Double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public Double getDisPer() {
		return disPer;
	}

	public void setDisPer(Double disPer) {
		this.disPer = disPer;
	}

	public Double getDisAmt() {
		return disAmt;
	}

	public void setDisAmt(Double disAmt) {
		this.disAmt = disAmt;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(Double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public Double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(Double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public Double getNetAmt() {
		return netAmt;
	}

	public void setNetAmt(Double netAmt) {
		this.netAmt = netAmt;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getIsAdjusted() {
		return isAdjusted;
	}

	public void setIsAdjusted(String isAdjusted) {
		this.isAdjusted = isAdjusted;
	}

	public List<SalesReturnItem> getSalesReturnItems() {
		return salesReturnItems;
	}

	public void setSalesReturnItems(List<SalesReturnItem> salesReturnItems) {
		this.salesReturnItems = salesReturnItems;
	}

	public ReturnTypes getReturnTypes() {
		return returnTypes;
	}

	public void setReturnTypes(ReturnTypes returnTypes) {
		this.returnTypes = returnTypes;
	}

	public Double getServiceChargeRate() {
		return serviceChargeRate;
	}

	public void setServiceChargeRate(Double serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}


	public int getService_charge_ledger_id() {
		return service_charge_ledger_id;
	}

	public void setService_charge_ledger_id(int service_charge_ledger_id) {
		this.service_charge_ledger_id = service_charge_ledger_id;
	}

	public String getQs() {
		return qs;
	}

	public void setQs(String qs) {
		this.qs = qs;
	}

	public int getDuties_ledger_id() {
		return duties_ledger_id;
	}

	public void setDuties_ledger_id(int duties_ledger_id) {
		this.duties_ledger_id = duties_ledger_id;
	}

	public int getRound_ledger_id() {
		return round_ledger_id;
	}

	public void setRound_ledger_id(int round_ledger_id) {
		this.round_ledger_id = round_ledger_id;
	}

	public int getSale_ledger_id() {
		return sale_ledger_id;
	}

	public void setSale_ledger_id(int sale_ledger_id) {
		this.sale_ledger_id = sale_ledger_id;
	}

	public int getDiscount_ledger_id() {
		return discount_ledger_id;
	}

	public void setDiscount_ledger_id(int discount_ledger_id) {
		this.discount_ledger_id = discount_ledger_id;
	}

	public int getDebitor_ledger_id() {
		return debitor_ledger_id;
	}

	public void setDebitor_ledger_id(int debitor_ledger_id) {
		this.debitor_ledger_id = debitor_ledger_id;
	}

	public int getDebitor_cash_ledger_id() {
		return debitor_cash_ledger_id;
	}

	public void setDebitor_cash_ledger_id(int debitor_cash_ledger_id) {
		this.debitor_cash_ledger_id = debitor_cash_ledger_id;
	}

	public int getCard_ledger_id() {
		return card_ledger_id;
	}

	public void setCard_ledger_id(int card_ledger_id) {
		this.card_ledger_id = card_ledger_id;
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

	public double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(double netTotal) {
		this.netTotal = netTotal;
	}

	public double getGrossAmt() {
		return grossAmt;
	}

	public void setGrossAmt(double grossAmt) {
		this.grossAmt = grossAmt;
	}

	public double getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
	}

	public double getTaxVatAmt() {
		return taxVatAmt;
	}

	public void setTaxVatAmt(double taxVatAmt) {
		this.taxVatAmt = taxVatAmt;
	}

	public double getServiceChargeAmt() {
		return serviceChargeAmt;
	}

	public void setServiceChargeAmt(double serviceChargeAmt) {
		this.serviceChargeAmt = serviceChargeAmt;
	}
	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "SalesReturn [id=" + id + ", date=" + date + ", userId=" + userId + ", storeId=" + storeId + ", orderId="
				+ orderId + ", customerName=" + customerName + ", customerContact=" + customerContact
				+ ", deliveryAddress=" + deliveryAddress + ", deliveryPersonName=" + deliveryPersonName
				+ ", waiterName=" + waiterName + ", storeCustId=" + storeCustId + ", returnTypes=" + returnTypes
				+ ", itemTotal=" + itemTotal + ", disPer=" + disPer + ", disAmt=" + disAmt + ", serviceCharge="
				+ serviceCharge + ", serviceChargeRate=" + serviceChargeRate + ", vatAmt=" + vatAmt + ", serviceTaxAmt="
				+ serviceTaxAmt + ", netAmt=" + netAmt + ", approved=" + approved + ", deleteFlag=" + deleteFlag
				+ ", remark=" + remark + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", isAdjusted=" + isAdjusted + ", salesReturnItems="
				+ salesReturnItems + ", service_charge_ledger_id=" + service_charge_ledger_id + ", qs=" + qs
				+ ", duties_ledger_id=" + duties_ledger_id + ", round_ledger_id=" + round_ledger_id
				+ ", sale_ledger_id=" + sale_ledger_id + ", discount_ledger_id=" + discount_ledger_id
				+ ", debitor_ledger_id=" + debitor_ledger_id + ", debitor_cash_ledger_id=" + debitor_cash_ledger_id
				+ ", card_ledger_id=" + card_ledger_id + ", netTotal=" + netTotal + ", grossAmt=" + grossAmt
				+ ", discAmt=" + discAmt + ", taxVatAmt=" + taxVatAmt + ", serviceChargeAmt=" + serviceChargeAmt
				+ ", is_account=" + is_account + ", jes=" + jes + ", orderNo=" + orderNo + "]";
	}

	



}
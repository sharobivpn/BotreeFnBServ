package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

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

import com.botree.restaurantapp.model.account.JournalEntry;
import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: bp_t_fo_orders_payment
 * 
 */
@XmlRootElement
@Entity
@Table(name = "bp_t_fo_orders_payment")
public class Payment implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderMaster orderPayment;

	@Expose
	@Column(name = "amount")
	private Double amount = 0.00;

	@Expose
	@Column(name = "paid_amount")
	private Double paidAmount = 0.00;

	@Expose
	@Column(name = "amount_to_pay")
	private Double amountToPay = 0.00;

	@Expose
	@Column(name = "tender_amount")
	private Double tenderAmount = 0.00;
	
	@Expose
	@Column(name = "tips_amount")
	private Double tipsAmount = 0.00;

	@Expose
	@Column(name = "card_last_four_digits")
	private String cardLastFourDigits;

	@Expose
	@Column(name = "remarks")
	private String remarks;

	@Expose
	@Column(name = "payment_mode")
	private String paymentMode;

	@Expose
	@Column(name = "created_by")
	private String createdBy;

	@Expose
	@Column(name = "creation_date")
	private String creationDate;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "source")
	private String source;

	@Transient
	private Double changeAmt = 0.00;

	@Expose
	@Transient
	private int orderId;

	@Expose
	@Transient
	private Double customerDiscount;

	@Expose
	@Transient
	private Double discountPercentage;
	
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
	private int service_charge_ledger_id;
	@Expose
	@Transient
	private String qs;
	
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
	
	
	@Expose
	@Transient
	private String is_account ;
	
	@Expose
	@Transient
	private List<JournalEntry> jes;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrderMaster getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(OrderMaster orderPayment) {
		this.orderPayment = orderPayment;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(Double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public Double getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(Double tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public String getCardLastFourDigits() {
		return cardLastFourDigits;
	}

	public void setCardLastFourDigits(String cardLastFourDigits) {
		this.cardLastFourDigits = cardLastFourDigits;
	}

	public Double getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(Double changeAmt) {
		this.changeAmt = changeAmt;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Double getCustomerDiscount() {
		return customerDiscount;
	}

	public void setCustomerDiscount(Double customerDiscount) {
		this.customerDiscount = customerDiscount;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Double getTipsAmount() {
		return tipsAmount;
	}

	public void setTipsAmount(Double tipsAmount) {
		this.tipsAmount = tipsAmount;
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

	public int getService_charge_ledger_id() {
		return service_charge_ledger_id;
	}

	public void setService_charge_ledger_id(int service_charge_ledger_id) {
		this.service_charge_ledger_id = service_charge_ledger_id;
	}

	public double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(double netTotal) {
		this.netTotal = netTotal;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderPayment=" + orderPayment + ", amount=" + amount + ", paidAmount="
				+ paidAmount + ", amountToPay=" + amountToPay + ", tenderAmount=" + tenderAmount + ", tipsAmount="
				+ tipsAmount + ", cardLastFourDigits=" + cardLastFourDigits + ", remarks=" + remarks + ", paymentMode="
				+ paymentMode + ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", storeId=" + storeId
				+ ", source=" + source + ", changeAmt=" + changeAmt + ", orderId=" + orderId + ", customerDiscount="
				+ customerDiscount + ", discountPercentage=" + discountPercentage + ", cr_amount=" + cr_amount
				+ ", dr_amount=" + dr_amount + ", cr_legder_id=" + cr_legder_id + ", dr_legder_id=" + dr_legder_id
				+ ", service_charge_ledger_id=" + service_charge_ledger_id + ", qs=" + qs + ", netTotal=" + netTotal
				+ ", grossAmt=" + grossAmt + ", discAmt=" + discAmt + ", taxVatAmt=" + taxVatAmt + ", serviceChargeAmt="
				+ serviceChargeAmt + ", duties_ledger_id=" + duties_ledger_id + ", round_ledger_id=" + round_ledger_id
				+ ", sale_ledger_id=" + sale_ledger_id + ", discount_ledger_id=" + discount_ledger_id
				+ ", debitor_ledger_id=" + debitor_ledger_id + ", debitor_cash_ledger_id=" + debitor_cash_ledger_id
				+ ", card_ledger_id=" + card_ledger_id + ", is_account=" + is_account + ", jes=" + jes + "]";
	}

}
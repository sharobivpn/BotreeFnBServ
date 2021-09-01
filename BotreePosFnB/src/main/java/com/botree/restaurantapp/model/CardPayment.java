package com.botree.restaurantapp.model;

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
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: bp_t_card_payment
 * 
 */
@XmlRootElement
@Entity
@Table(name = "bp_t_card_payment")
public class CardPayment implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "order_amount")
	private Double orderAmount;

	@Expose
	@Column(name = "order_id")
	private int orderId;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date")
	private Date orderDate;

	@Expose
	@Column(name = "order_time")
	private String orderTime;

	@Expose
	@Column(name = "paid_amount")
	private Double paidAmount;

	@Expose
	@Column(name = "gateway_name")
	private String gatewayName;

	@Expose
	@Column(name = "payment_id")
	private String paymentId;

	@Expose
	@Column(name = "payment_currency_code")
	private String paymentCurrencyCode;

	@Expose
	@Column(name = "payment_remark")
	private String paymentRemark;

	@Expose
	@Column(name = "payment_country")
	private String paymentCountry;

	@Expose
	@Column(name = "payment_merchant_name")
	private String paymentMerchantName;

	@Expose
	@Column(name = "payment_status")
	private String paymentStatus;

	@Expose
	@Column(name = "return_url")
	private String returnUrl;

	@Expose
	@Column(name = "customer_ph_no")
	private String customerPhNo;

	@Expose
	@Column(name = "customer_name")
	private String customerName;

	@Expose
	@Column(name = "customer_email")
	private String customerEmail;

	@Expose
	@Column(name = "auth_code")
	private int authCode;

	@Expose
	@Column(name = "transaction_ref_no")
	private String transactionRefNo;

	@Expose
	@Column(name = "card_type")
	private String cardType;

	@Expose
	@Column(name = "card_holder_name")
	private String cardHolderName;

	@Expose
	@Column(name = "cust_card_masked_number")
	private String custCardMaskedNumber;

	@Expose
	@Column(name = "transaction_response_code")
	private int transactionResponseCode;

	@Expose
	@Column(name = "request_string")
	private String requestSring;

	@Expose
	@Column(name = "response_string")
	private String responseString;

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
	@Column(name = "calling_device_name")
	private String callingDeviceName;

	private static final long serialVersionUID = 1L;

	public String getCallingDeviceName() {
		return callingDeviceName;
	}

	public void setCallingDeviceName(String callingDeviceName) {
		this.callingDeviceName = callingDeviceName;
	}

	public String getRequestSring() {
		return requestSring;
	}

	public void setRequestSring(String requestSring) {
		this.requestSring = requestSring;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentCurrencyCode() {
		return paymentCurrencyCode;
	}

	public void setPaymentCurrencyCode(String paymentCurrencyCode) {
		this.paymentCurrencyCode = paymentCurrencyCode;
	}

	public String getPaymentRemark() {
		return paymentRemark;
	}

	public void setPaymentRemark(String paymentRemark) {
		this.paymentRemark = paymentRemark;
	}

	public String getPaymentCountry() {
		return paymentCountry;
	}

	public void setPaymentCountry(String paymentCountry) {
		this.paymentCountry = paymentCountry;
	}

	public String getPaymentMerchantName() {
		return paymentMerchantName;
	}

	public void setPaymentMerchantName(String paymentMerchantName) {
		this.paymentMerchantName = paymentMerchantName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getCustomerPhNo() {
		return customerPhNo;
	}

	public void setCustomerPhNo(String customerPhNo) {
		this.customerPhNo = customerPhNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public int getAuthCode() {
		return authCode;
	}

	public void setAuthCode(int authCode) {
		this.authCode = authCode;
	}

	public String getTransactionRefNo() {
		return transactionRefNo;
	}

	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCustCardMaskedNumber() {
		return custCardMaskedNumber;
	}

	public void setCustCardMaskedNumber(String custCardMaskedNumber) {
		this.custCardMaskedNumber = custCardMaskedNumber;
	}

	public int getTransactionResponseCode() {
		return transactionResponseCode;
	}

	public void setTransactionResponseCode(int transactionResponseCode) {
		this.transactionResponseCode = transactionResponseCode;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
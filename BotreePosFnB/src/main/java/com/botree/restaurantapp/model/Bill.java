package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: bp_t_bill
 * 
 */
@XmlRootElement
@Entity
@Table(name = "bp_t_bill")
public class Bill implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "bill_amt")
	private Double billAmount;

	@OneToOne
	@JoinColumn(name = "Order_Id")
	private OrderMaster orderbill;

	@OneToOne
	@JoinColumn(name = "tax_Id")
	private Tax tax;

	@Expose
	@Column(name = "service_tax_amt")
	private Double serviceTaxAmt=0.0;

	@Expose
	@Column(name = "vat_amt")
	private Double vatAmt=0.0;

	@Expose
	@Column(name = "gross_amt")
	private Double grossAmt;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "total_discount")
	private Double totalDiscount;

	@Expose
	@Column(name = "customer_discount")
	private Double customerDiscount;

	@Expose
	@Column(name = "round_off_amt")
	private Double roundOffAmt;

	@Expose
	@Column(name = "discount_percentage")
	private Double discountPercentage;

	@Expose
	@Column(name = "service_charge_amt")
	private Double serviceChargeAmt;
	
	@Expose
	@Column(name = "sub_total_amt")
	private Double subTotalAmt;
	
	@Expose
	@Column(name = "discount_reason")
	private String discountReason;
	
	@Expose
	@Column(name = "is_nonchargeable")
	private String isNonchargeable;
	
	@Expose
	@Column(name = "service_charge_rate")
	private Double serviceChargeRate;

	private static final long serialVersionUID = 1L;
	
	
	public String getIsNonchargeable() {
		return isNonchargeable;
	}

	public void setIsNonchargeable(String isNonchargeable) {
		this.isNonchargeable = isNonchargeable;
	}

	public String getDiscountReason() {
		return discountReason;
	}

	public void setDiscountReason(String discountReason) {
		this.discountReason = discountReason;
	}

	public Double getSubTotalAmt() {
		return subTotalAmt;
	}

	public void setSubTotalAmt(Double subTotalAmt) {
		this.subTotalAmt = subTotalAmt;
	}

	public Double getServiceChargeAmt() {
		return serviceChargeAmt;
	}

	public void setServiceChargeAmt(Double serviceChargeAmt) {
		this.serviceChargeAmt = serviceChargeAmt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getBillAmount() {
		/*
		 * System.out.println("in get getBillAmount");
		 * System.out.println("getBillAmount"+ billAmount.doubleValue());
		 * DecimalFormat decimalFormat= new DecimalFormat("0.00");
		 * 
		 * String billAmt=decimalFormat.format(billAmount.doubleValue());
		 * System.out.println("billAmt: "+billAmt);
		 */

		// return Double.parseDouble(billAmt);
		return billAmount;

	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public OrderMaster getOrderbill() {
		return orderbill;
	}

	public void setOrderbill(OrderMaster orderbill) {
		this.orderbill = orderbill;
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

	public Double getServiceTaxAmt() {
		System.out.println("service tax: " + serviceTaxAmt.doubleValue());
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(Double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public Double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(Double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public Double getGrossAmt() {

		return grossAmt;
	}

	public void setGrossAmt(Double grossAmt) {
		this.grossAmt = grossAmt;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Double getCustomerDiscount() {
		return customerDiscount;
	}

	public void setCustomerDiscount(Double customerDiscount) {
		this.customerDiscount = customerDiscount;
	}

	public Double getRoundOffAmt() {
		return roundOffAmt;
	}

	public void setRoundOffAmt(Double roundOffAmt) {
		this.roundOffAmt = roundOffAmt;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Double getServiceChargeRate() {
		return serviceChargeRate;
	}

	public void setServiceChargeRate(Double serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}
	
	

}
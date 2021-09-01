package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class TaxesForAllItems implements Serializable {

	@Expose
	private int storeId;

	@Expose
	private String vatTaxText;

	@Expose
	private String serviceTaxText;

	@Expose
	private String discountText;

	@Expose
	private double vatAmt;

	@Expose
	private double serviceTaxAmt;

	@Expose
	private double discountValue;

	private static final long serialVersionUID = 1L;

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getVatTaxText() {
		return vatTaxText;
	}

	public void setVatTaxText(String vatTaxText) {
		this.vatTaxText = vatTaxText;
	}

	public String getServiceTaxText() {
		return serviceTaxText;
	}

	public void setServiceTaxText(String serviceTaxText) {
		this.serviceTaxText = serviceTaxText;
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

	public String getDiscountText() {
		return discountText;
	}

	public void setDiscountText(String discountText) {
		this.discountText = discountText;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

}

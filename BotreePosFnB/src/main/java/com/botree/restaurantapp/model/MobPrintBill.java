package com.botree.restaurantapp.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;


public class MobPrintBill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	private String status; // server or mobile

	@Expose
	private String strName;

	@Expose
	private String address;

	@Expose
	private String emailId;

	@Expose
	private String phoneNumbr;

	@Expose
	private String vatReg;

	@Expose
	private String serviceTax;

	@Expose
	private String invoice;

	@Expose
	private String dateTable;

	@Expose
	private String mapToHoldItems;

	@Expose
	private String nonVatItemOrdrAmt;

	@Expose
	private String vatItemOrdrAmt;

	@Expose
	private String totalAmt;

	/*
	 * @Expose private String vatAmtLst;
	 * 
	 * @Expose private String servcTaxLst;
	 */

	// arryToHoldElems[14] = discntLst;
	// arryToHoldElems[15] = grossLst;
	@Expose
	private String tableNoStrng;

	// arryToHoldElems[17] = paidAmtLst;
	// arryToHoldElems[18] = amtToPayLst;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumbr() {
		return phoneNumbr;
	}

	public void setPhoneNumbr(String phoneNumbr) {
		this.phoneNumbr = phoneNumbr;
	}

	public String getVatReg() {
		return vatReg;
	}

	public void setVatReg(String vatReg) {
		this.vatReg = vatReg;
	}

	public String getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getDateTable() {
		return dateTable;
	}

	public void setDateTable(String dateTable) {
		this.dateTable = dateTable;
	}

	public String getMapToHoldItems() {
		return mapToHoldItems;
	}

	public void setMapToHoldItems(String mapToHoldItems) {
		this.mapToHoldItems = mapToHoldItems;
	}

	public String getNonVatItemOrdrAmt() {
		return nonVatItemOrdrAmt;
	}

	public void setNonVatItemOrdrAmt(String nonVatItemOrdrAmt) {
		this.nonVatItemOrdrAmt = nonVatItemOrdrAmt;
	}

	public String getVatItemOrdrAmt() {
		return vatItemOrdrAmt;
	}

	public void setVatItemOrdrAmt(String vatItemOrdrAmt) {
		this.vatItemOrdrAmt = vatItemOrdrAmt;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getTableNoStrng() {
		return tableNoStrng;
	}

	public void setTableNoStrng(String tableNoStrng) {
		this.tableNoStrng = tableNoStrng;
	}

}
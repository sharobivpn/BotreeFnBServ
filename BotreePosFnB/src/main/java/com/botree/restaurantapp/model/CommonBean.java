package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class CommonBean implements Serializable {

	@Expose private int orderId;
	
	@Expose private int storeId;

	@Expose private String billPrintReason;

	@Expose private String lang;

	@Expose private static final long serialVersionUID = 1L;
	
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

	public String getBillPrintReason() {
		return billPrintReason;
	}

	public void setBillPrintReason(String billPrintReason) {
		this.billPrintReason = billPrintReason;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
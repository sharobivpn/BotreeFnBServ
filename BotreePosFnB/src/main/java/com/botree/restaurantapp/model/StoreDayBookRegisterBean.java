package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
@XmlRootElement
public class StoreDayBookRegisterBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Expose
	private int id;
	@Expose
	private Date orderDate;
	@Expose
	private int openBy;
	@Expose
	private int storeId;
	@Expose
	private String userText;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOpenBy() {
		return openBy;
	}

	public void setOpenBy(int openBy) {
		this.openBy = openBy;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getUserText() {
		return userText;
	}

	public void setUserText(String userText) {
		this.userText = userText;
	}

}

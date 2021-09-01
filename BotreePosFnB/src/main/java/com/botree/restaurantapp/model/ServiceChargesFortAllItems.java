package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class ServiceChargesFortAllItems implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Expose
	private int storeId;

	@Expose
	private int orderTypeId;

	@Expose
	private String orderTypeName;
	
	@Expose
	private String orderTypeShortName;
	
	@Expose
	private float scValue;

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(int orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getOrderTypeShortName() {
		return orderTypeShortName;
	}

	public void setOrderTypeShortName(String orderTypeShortName) {
		this.orderTypeShortName = orderTypeShortName;
	}

	public float getScValue() {
		return scValue;
	}

	public void setScValue(float scValue) {
		this.scValue = scValue;
	}	
}

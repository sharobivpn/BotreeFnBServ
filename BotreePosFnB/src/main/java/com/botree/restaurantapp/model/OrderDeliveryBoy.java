package com.botree.restaurantapp.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class OrderDeliveryBoy {
	
	@Expose
	private int id;
	
	@Expose
	private int orderId;
	
	@Expose
	private String deliveryPersonName;
	
	/*@Expose
	private String deliveryPersonAddress;
	
	@Expose
	private String deliveryPersonphone_no;
	
	@Expose
	private String deliveryPersonemail;
	
	@Expose
	private Date DOB;
	
	@Expose
	private Date DOJ;
	
	@Expose
	private String uniqueId;*/
	
	@Expose
	private String store_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDeliveryPersonName() {
		return deliveryPersonName;
	}

	public void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
	}

	/*public String getDeliveryPersonAddress() {
		return deliveryPersonAddress;
	}

	public void setDeliveryPersonAddress(String deliveryPersonAddress) {
		this.deliveryPersonAddress = deliveryPersonAddress;
	}

	public String getDeliveryPersonphone_no() {
		return deliveryPersonphone_no;
	}

	public void setDeliveryPersonphone_no(String deliveryPersonphone_no) {
		this.deliveryPersonphone_no = deliveryPersonphone_no;
	}

	public String getDeliveryPersonemail() {
		return deliveryPersonemail;
	}

	public void setDeliveryPersonemail(String deliveryPersonemail) {
		this.deliveryPersonemail = deliveryPersonemail;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public Date getDOJ() {
		return DOJ;
	}

	public void setDOJ(Date dOJ) {
		DOJ = dOJ;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}*/

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	
}

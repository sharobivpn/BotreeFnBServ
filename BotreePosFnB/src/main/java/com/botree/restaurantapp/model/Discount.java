package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;


@XmlRootElement
public class Discount implements Serializable {

	@Expose private int orderId;

	@Expose private int storeId;

	@Expose private Double customerDiscount;

	@Expose private Double discountPercentage;

	@Expose private String discountReason;
	
	@Expose private String isNonchargeable;
	
	@Expose private String orderTypeShortName;

	@Expose private static final long serialVersionUID = 1L;
	
	
	public String getOrderTypeShortName() {
		return orderTypeShortName;
	}

	public void setOrderTypeShortName(String orderTypeShortName) {
		this.orderTypeShortName = orderTypeShortName;
	}

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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
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

}
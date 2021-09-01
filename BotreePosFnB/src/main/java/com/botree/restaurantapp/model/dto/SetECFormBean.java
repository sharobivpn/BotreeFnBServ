package com.botree.restaurantapp.model.dto;

public class SetECFormBean {
	private String itemDescription;
	private String currency;
	private double totalAmount;

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double amount) {
		this.totalAmount = amount;
	}
}

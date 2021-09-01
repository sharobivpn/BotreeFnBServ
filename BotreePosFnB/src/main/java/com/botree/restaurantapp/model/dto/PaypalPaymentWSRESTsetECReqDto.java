package com.botree.restaurantapp.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class PaypalPaymentWSRESTsetECReqDto {
	@Expose private String itemDescription;
	@Expose private String currency;
	@Expose private double totalAmount;
	@Expose private String redirectUrl;

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

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/*
	public String getGsonString() {

		PaypalPaymentWSRESTsetECReqDto dto = new PaypalPaymentWSRESTsetECReqDto();
		dto.setItemDescription("Sample description");
		dto.setCurrency("USD");
		dto.setTotalAmount(23.9f);
		dto.setRedirectUrl("http://localhost");

		Gson gson = new Gson();
		return gson.toJson(dto);
	}
	*/
}

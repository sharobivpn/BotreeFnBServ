package com.botree.restaurantapp.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class PaypalPaymentWSRESTsetECResDto {
	@Expose private String returnUrl;

	public PaypalPaymentWSRESTsetECResDto(String returnUrl) {

		this.returnUrl = returnUrl;
	}

	public String getReturnUrl() {

		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {

		this.returnUrl = returnUrl;
	}
}

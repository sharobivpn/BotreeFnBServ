package com.botree.restaurantapp.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class PaypalPaymentWSRESTdoECReqDto {
	@Expose private String token;
	@Expose private String prayerId;
	@Expose private String totalAmount;

	public String getToken() {

		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPrayerId() {
		return prayerId;
	}

	public void setPrayerId(String prayerId) {
		this.prayerId = prayerId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		
		return this.token + "/" + this.prayerId + "/" + this.totalAmount;
	}

}

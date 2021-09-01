package com.botree.restaurantapp.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class PaypalPaymentWSRESTdoECResDto {
	@Expose private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public PaypalPaymentWSRESTdoECResDto(boolean status) {
		this.status = status;
	}
}

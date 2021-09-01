package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class ListHolder implements Serializable {

	@Expose
	@Transient
	private List<PaymentType> paymentTypes;

	@Expose
	@Transient
	private int size;

	private static final long serialVersionUID = 1L;

	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
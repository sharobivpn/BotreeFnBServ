package com.botree.restaurantapp.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class StatusMessage {

	public static String SUCCESS = "SUCCESS";
	public static String FAILURE = "FAILURE";

	@Expose private String message = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

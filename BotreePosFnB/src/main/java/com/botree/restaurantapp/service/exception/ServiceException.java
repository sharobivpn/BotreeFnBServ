package com.botree.restaurantapp.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 4664456874499611218L;

	private String message = "";

	private Exception ex = null;

	public ServiceException(String message, Exception ex) {
		
		this.message = message;
		this.ex = ex;
	}

	public Exception getEx() {
		return ex;
	}

	public String getMessage() {
		return message;
	}
}

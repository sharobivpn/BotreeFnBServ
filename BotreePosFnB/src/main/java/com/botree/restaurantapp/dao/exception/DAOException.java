package com.botree.restaurantapp.dao.exception;

public class DAOException extends Exception{
	
	private static final long serialVersionUID = 4664456874499611218L;
	
	private String message="";
	private Exception exp = null;

	public DAOException(String message, Exception exp) {
		
		this.message=message;
		this.exp=exp;
	}
	public Exception getEx() {
		return exp;
	}

	public String getMessage() {
		return message;
	}

}

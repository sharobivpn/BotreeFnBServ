package com.botree.restaurantapp.service.exception;

import com.botree.restaurantapp.commonUtil.MenuUploadFile;

public class FileUplodException extends Exception {

	private static final long serialVersionUID = 4664456874499611218L;

	private MenuUploadFile menuUploadFile = null;

	private Exception ex = null;

	private String message = "";

	public FileUplodException(MenuUploadFile menuUploadFile, String message) {

		this.message = message;
		this.menuUploadFile = menuUploadFile;
	}

	public MenuUploadFile getMenuUploadFile() {
		return menuUploadFile;
	}

	public void setMenuUploadFile(MenuUploadFile menuUploadFile) {
		this.menuUploadFile = menuUploadFile;
	}

	public Exception getEx() {
		return ex;
	}

	public void setEx(Exception ex) {
		this.ex = ex;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

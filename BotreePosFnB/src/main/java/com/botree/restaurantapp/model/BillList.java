package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;


public class BillList implements Serializable {

	@Expose
	private List<BillSplitER> billSplitERs = null;
	
	@Expose
	private int orderId;
	private static final long serialVersionUID = 1L;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<BillSplitER> getBillSplitERs() {
		return billSplitERs;
	}

	public void setBillSplitERs(List<BillSplitER> billSplitERs) {
		this.billSplitERs = billSplitERs;
	}

}
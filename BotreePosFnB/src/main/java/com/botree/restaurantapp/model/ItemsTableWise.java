package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

public class ItemsTableWise implements Serializable {

	private String tableNo;
	private List<OrderItem> orderitem;
	private Double billAmount;

	private static final long serialVersionUID = 1L;

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public List<OrderItem> getOrderitem() {
		return orderitem;
	}

	public void setOrderitem(List<OrderItem> orderitem) {
		this.orderitem = orderitem;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	@Override
	public String toString() {
		return "ItemsTableWise [tableNo=" + tableNo + ", orderitem=" + orderitem + ", billAmount=" + billAmount + "]";
	}

}
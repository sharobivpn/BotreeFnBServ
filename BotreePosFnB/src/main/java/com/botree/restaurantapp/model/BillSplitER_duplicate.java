package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class BillSplitER_duplicate implements Serializable {
	@Expose
	private int id;

	@Expose
	private int BillId;

	@Expose
	private String mode;

	@Expose
	private int BillNo;

	@Expose
	private String printBillNumber;

	@Expose
	private int orderId;

	@Expose
	private Double billAmount;

	@Expose
	private String creationDate;

	@Expose
	private String type;

	@Expose
	private int totalBillNo;

	@Expose
	private String deliveryType;

	@Expose
	private List<BillSplitER> billSplitERs = null;

	@Expose
	private String modeValue;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getBillAmount() {
		return billAmount;

	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public int getBillId() {
		return BillId;
	}

	public void setBillId(int billId) {
		BillId = billId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getBillNo() {
		return BillNo;
	}

	public void setBillNo(int billNo) {
		BillNo = billNo;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTotalBillNo() {
		return totalBillNo;
	}

	public void setTotalBillNo(int totalBillNo) {
		this.totalBillNo = totalBillNo;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

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

	public String getPrintBillNumber() {
		return printBillNumber;
	}

	public void setPrintBillNumber(String printBillNumber) {
		this.printBillNumber = printBillNumber;
	}

	public String getModeValue() {
		return modeValue;
	}

	public void setModeValue(String modeValue) {
		this.modeValue = modeValue;
	}

}
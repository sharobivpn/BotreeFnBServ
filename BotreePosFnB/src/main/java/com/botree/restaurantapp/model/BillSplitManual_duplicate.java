package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class BillSplitManual_duplicate implements Serializable {
	@Expose
	private int id;

	@Expose
	private int billId;

	@Expose
	private String mode;

	@Expose
	private int billNo;

	@Expose
	private String printBillNumber;

	@Expose
	private int orderId;

	@Expose
	private String itemName;

	@Expose
	private Double itemRate;

	@Expose
	private int itemQuantity;

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
	private List<BillSplitManual> billSplitManuals = null;

	@Expose
	private int itemId;
	
	@Expose
	private int categoryId;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
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

	public List<BillSplitManual> getBillSplitManuals() {
		return billSplitManuals;
	}

	public void setBillSplitManuals(List<BillSplitManual> billSplitManuals) {
		this.billSplitManuals = billSplitManuals;
	}

	public String getPrintBillNumber() {
		return printBillNumber;
	}

	public void setPrintBillNumber(String printBillNumber) {
		this.printBillNumber = printBillNumber;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	
}
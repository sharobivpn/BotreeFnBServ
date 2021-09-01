package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: bp_t_bill_split_manual_c
 * 
 */
@XmlRootElement
@Entity
@Table(name = "bp_t_bill_split_manual_c")
public class BillSplitManual implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "bp_t_bill_id")
	private int billId;

	@Expose
	@Column(name = "mode")
	private String mode;

	@Expose
	@Column(name = "bill_number")
	private int billNo;

	@Expose
	@Column(name = "print_bill_number")
	private String printBillNumber;

	@Expose
	@Column(name = "order_id")
	private int orderId;

	@Expose
	@Column(name = "food_item_name")
	private String itemName;

	@Expose
	@Column(name = "food_item_rate")
	private Double itemRate;

	@Expose
	@Column(name = "food_item_quantity")
	private int itemQuantity;

	@Expose
	@Column(name = "amount")
	private Double billAmount;

	@Expose
	@Column(name = "creation_date")
	private String creationDate;

	@Expose
	@Column(name = "type")
	private String type;

	@Expose
	@Column(name = "total_bill_no")
	private int totalBillNo;

	@Expose
	@Column(name = "delivery_type")
	private String deliveryType;

	@Expose
	@Column(name = "mode_value")
	private String modeValue;

	@Transient
	private Double total;

	@Transient
	private List<BillSplitManual_duplicate> billSplitManualList;

	@Expose
	@Column(name = "item_id")
	private int itemId;

	@Expose
	@Column(name = "category_id")
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<BillSplitManual_duplicate> getBillSplitManualList() {
		return billSplitManualList;
	}

	public void setBillSplitManualList(
			List<BillSplitManual_duplicate> billSplitManualList) {
		this.billSplitManualList = billSplitManualList;
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
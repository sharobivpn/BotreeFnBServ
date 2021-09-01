package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class CustomerInfo implements Serializable {

	@Expose 
	private String custId;
	
	@Expose
	private String custName;

	@Expose
	private String custContact;

	@Expose
	private String delivAddr;

	@Expose
	private String delivPersonName;

	@Expose
	private Date delivTime;
	
	@Expose
	private String custVatRegNo;

	@Expose
	private StoreCustomer creditCust = null;
	
	@Expose
	private String location;
	
	@Expose
	private String street;
	
	@Expose
	private String houseNo;
	
	@Expose
	private String carNo;
	
	@Expose
	private String anniversary_date; 
	
	@Expose
	private String dob;
	
	@Expose
	private String last_payment_Date;
	
	@Expose
	private String last_Amount_amount;
	
	@Expose
	private String custEmailId;
	
	@Expose
	private String last_Visit_Date;
	
	@Expose
	private String dayDiff;
	
	@Expose
	private String total_Spent_Amount;

	private static final long serialVersionUID = 1L;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustContact() {
		return custContact;
	}

	public void setCustContact(String custContact) {
		this.custContact = custContact;
	}

	public StoreCustomer getCreditCust() {
		return creditCust;
	}

	public void setCreditCust(StoreCustomer creditCust) {
		this.creditCust = creditCust;
	}

	public String getDelivAddr() {
		return delivAddr;
	}

	public void setDelivAddr(String delivAddr) {
		this.delivAddr = delivAddr;
	}

	public String getDelivPersonName() {
		return delivPersonName;
	}

	public void setDelivPersonName(String delivPersonName) {
		this.delivPersonName = delivPersonName;
	}

	public Date getDelivTime() {
		return delivTime;
	}

	public void setDelivTime(Date delivTime) {
		this.delivTime = delivTime;
	}

	public String getCustVatRegNo() {
		return custVatRegNo;
	}

	public void setCustVatRegNo(String custVatRegNo) {
		this.custVatRegNo = custVatRegNo;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getAnniversary_date() {
		return anniversary_date;
	}

	public void setAnniversary_date(String anniversary_date) {
		this.anniversary_date = anniversary_date;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getLast_payment_Date() {
		return last_payment_Date;
	}

	public void setLast_payment_Date(String last_payment_Date) {
		this.last_payment_Date = last_payment_Date;
	}

	public String getLast_Amount_amount() {
		return last_Amount_amount;
	}

	public void setLast_Amount_amount(String last_Amount_amount) {
		this.last_Amount_amount = last_Amount_amount;
	}

	public String getCustEmailId() {
		return custEmailId;
	}

	public void setCustEmailId(String custEmailId) {
		this.custEmailId = custEmailId;
	}

	public String getLast_Visit_Date() {
		return last_Visit_Date;
	}

	public void setLast_Visit_Date(String last_Visit_Date) {
		this.last_Visit_Date = last_Visit_Date;
	}

	public String getDayDiff() {
		return dayDiff;
	}

	public void setDayDiff(String dayDiff) {
		this.dayDiff = dayDiff;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getTotal_Spent_Amount() {
		return total_Spent_Amount;
	}

	public void setTotal_Spent_Amount(String total_Spent_Amount) {
		this.total_Spent_Amount = total_Spent_Amount;
	}
	
	
}
package com.botree.restaurantapp.model.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class BookTableFormBean {
	@Expose private int storeId;
	@Expose private String phoneNo;
	@Expose private int userId;
	@Expose private String name;
	@Expose private String address;
	@Expose private int seats;
	@Expose private String bookingDate;
	@Expose private int hour;
	@Expose private int minute;
	@Expose private int period; // AM/PM AM=0, PM=1
	@Expose private double longitude;
	@Expose private double latitude;
	@Expose private List<Table> table;

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public List<Table> getTable() {
		return table;
	}

	public void setTable(List<Table> table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return "BookTableFormBean [storeId=" + storeId + ", phoneNo=" + phoneNo + ", userId=" + userId + ", name=" + name + ", address=" + address + ", seats=" + seats + ", bookingDate=" + bookingDate + ", hour=" + hour + ", minute=" + minute + ", period=" + period + ", longitude=" + longitude + ", latitude=" + latitude + ", table=" + table + "]";
	}	
}
package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: st_t_day_book_reg
 * 
 */
@XmlRootElement
@Entity
@Table(name = "st_t_day_book_reg")
public class StoreDayBookRegister implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date")
	private Date orderDate;
	
	@Expose
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "open_time")
	private Timestamp openTime;	
	
	@Expose
	@Column(name = "open_by")
	private int openBy;
	
	@Expose
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_time")
	private Timestamp closeTime;	
	
	@Expose
	@Column(name = "close_by")
	private int closeBy;

	@Expose
	@Column(name = "store_id")
	private int storeId;
	
	@Expose
	@Transient
	private String userText;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Timestamp getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Timestamp openTime) {
		this.openTime = openTime;
	}

	public int getOpenBy() {
		return openBy;
	}

	public void setOpenBy(int openBy) {
		this.openBy = openBy;
	}

	public Timestamp getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}

	public int getCloseBy() {
		return closeBy;
	}

	public void setCloseBy(int closeBy) {
		this.closeBy = closeBy;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	public String getUserText() {
		return userText;
	}

	public void setUserText(String userText) {
		this.userText = userText;
	}


}
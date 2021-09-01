package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fb_t_feedback
 * 
 */
@XmlRootElement
@Entity
@Table(name = "fb_t_feedback")
public class Feedback implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "description")
	private String description;

	@Expose
	@Column(name = "feedback_rating")
	private String feedbackRating;

	@Column(name = "store_id")
	private int storeId;

	@Expose
	@ManyToOne
	@JoinColumn(name = "Person_Id")
	private Customer customer;

	@Expose
	@Column(name = "customer_name")
	private String customerName;

	@ManyToOne
	@JoinColumn(name = "order_master_Id")
	private OrderMaster orders;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "feedback_date")
	private Date feedbackDate;

	@Expose
	@Column(name = "contact_no")
	private String contactNumber;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeedbackRating() {
		return feedbackRating;
	}

	public void setFeedbackRating(String feedbackRating) {
		this.feedbackRating = feedbackRating;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public OrderMaster getOrders() {
		return orders;
	}

	public void setOrders(OrderMaster orders) {
		this.orders = orders;
	}

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", description=" + description + ", feedbackRating=" + feedbackRating + ", storeId=" + storeId + ", customer=" + customer + ", customerName=" + customerName + ", orders=" + orders + ", feedbackDate=" + feedbackDate + "]";
	}
}
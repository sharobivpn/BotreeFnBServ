package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: ci_t_customer
 * 
 */

@XmlRootElement
@Entity
@Table(name = "ci_t_customer")
public class Customer implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	@Expose
	@Column(name = "name")
	private String name;
	@Expose
	@Column(name = "address")
	private String address;
	@Expose
	@Column(name = "contact_no")
	private String contactNo;
	@Expose
	@Column(name = "email_id")
	private String emailId;
	@Expose
	@Column(name = "user_id")
	private String userId;
	@Expose
	@Column(name = "password")
	private String password;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "type")
	private String type;

	@Expose
	@Column(name = "designation")
	private String designation;

	@OneToMany(mappedBy = "customers")
	private List<OrderMaster> order;

	// @OneToMany(mappedBy="customer")
	// private List<Coupon> coupon;

	@Transient
	private double mobileLatitude;

	@Transient
	private String city;

	@Transient
	private String cuisineType;

	@Expose
	@Column(name = "status")
	private String status;

	@Expose
	@Transient
	private String message;

	@Transient
	private RestaurantMaster restaurant1;

	@Transient
	private StoreMaster storeMaster;
	
	@Expose
	@Transient
	private String newPasword;
	
	@Expose
	@Transient
	private String location;
	
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getMobileLatitude() {
		return mobileLatitude;
	}

	public void setMobileLatitude(double mobileLatitude) {
		this.mobileLatitude = mobileLatitude;
	}

	public double getMobileLongitude() {
		return mobileLongitude;
	}

	public void setMobileLongitude(double mobileLongitude) {
		this.mobileLongitude = mobileLongitude;
	}

	@Transient
	private double mobileLongitude;

	@OneToMany(mappedBy = "customer")
	private List<Feedback> feedback;

	@OneToMany(mappedBy = "customer")
	private List<RewardPoint> rewards;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public List<OrderMaster> getOrder() {
		return order;
	}

	public void setOrder(List<OrderMaster> order) {
		this.order = order;
	}*/

	public List<Coupon> getCoupon() {
		return null;
	}

	public void setCoupon(List<Coupon> coupon) {
		// this.coupon = coupon;
	}

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}

	public List<RewardPoint> getRewards() {
		return rewards;
	}

	public void setRewards(List<RewardPoint> rewards) {
		this.rewards = rewards;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCuisineType() {
		return cuisineType;
	}

	public void setCuisineType(String cuisineType) {
		this.cuisineType = cuisineType;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RestaurantMaster getRestaurant1() {
		return restaurant1;
	}

	public void setRestaurant1(RestaurantMaster restaurant1) {
		this.restaurant1 = restaurant1;
	}

	public StoreMaster getStoreMaster() {
		return storeMaster;
	}

	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}

	public String getNewPasword() {
		return newPasword;
	}

	public void setNewPasword(String newPasword) {
		this.newPasword = newPasword;
	}
	
	

}
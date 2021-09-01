package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: rg_t_st_user
 * 
 */

@XmlRootElement
@Entity
@Table(name = "rg_t_st_user")
@ManagedBean(name = "user")

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "UserName")
	private String userName;

	@Expose
	@Column(name = "Password")
	private String password;

	@Expose
	@Column(name = "UserId")
	private String userId;

	@Expose
	@Column(name = "status")
	private String status;

	@Expose
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private RestaurantMaster restMaster;

	@Expose
	@Column(name = "designation")
	private String designation;

	@Expose
	@Column(name = "contact_no")
	private String contact;
	
	@Expose
	@Column(name = "store_id")
	private int storeId;
	
	@Expose
	@Transient
	private String newPassword;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public RestaurantMaster getRestMaster() {
		return restMaster;
	}

	public void setRestMaster(RestaurantMaster restMaster) {
		this.restMaster = restMaster;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	
}
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: rg_t_st_user
 * 
 */

@XmlRootElement
@Entity
@Table(name = "st_m_admin_user")
@ManagedBean(name = "stuser")

public class StUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "Password")
	private String password;

	@Column(name = "UserId")
	private String userId;

	@Column(name = "status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private RestaurantMaster restMaster;

	@Column(name = "designation")
	private String designation;

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

	

}
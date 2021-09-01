package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fo_m_order_type
 * 
 */
@XmlRootElement

@Entity
@Table(name = "fo_m_order_type")
public class OrderType implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	
	@Expose
	@Column(name = "ordertype_name")
	private String orderTypeName;
	
	@Expose
	@Column(name = "flag")
	private String statusFlag;
	
	@Expose
	@Column(name = "store_id")
	private int storeId;
	
	@Expose
	@Column(name="sc_value")
	private float serviceChargeValue;
	
	/*@OneToMany(mappedBy = "ordertype")
	private List<OrderType> orderType;*/
	@Expose
	@Column(name = "ordertype_short_name")
	private String ordertypeShortName;
	
	private static final long serialVersionUID = 1L;
	
	public String getOrdertypeShortName() {
		return ordertypeShortName;
	}

	public void setOrdertypeShortName(String ordertypeShortName) {
		this.ordertypeShortName = ordertypeShortName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public float getServiceChargeValue() {
		return serviceChargeValue;
	}

	public void setServiceChargeValue(float serviceChargeValue) {
		this.serviceChargeValue = serviceChargeValue;
	}
}
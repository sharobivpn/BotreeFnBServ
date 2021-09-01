package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fo_t_orders
 * 
 */
@Embeddable
@XmlRootElement
public class OrderMasterPK implements Serializable {
	
	public OrderMasterPK() {}
	
	public OrderMasterPK(int storeId) {
		this.storeId = storeId;
	}
	
	public OrderMasterPK(int id, int storeId) {
		super();
		this.id = id;
		this.storeId = storeId;
	}


	@Expose
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	
	@Expose
	@Column(name = "store_id")
	private int storeId;

	private static final long serialVersionUID = 1L;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

}
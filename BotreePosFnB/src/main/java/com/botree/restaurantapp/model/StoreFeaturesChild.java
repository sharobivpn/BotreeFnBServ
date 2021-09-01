package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fm_m_rest_menu_options_c
 * 
 */

@XmlRootElement
@Entity
@Table(name = "fm_m_rest_menu_options_c")

public class StoreFeaturesChild implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private StoreFeatures storeFeatures;

	@Column(name = "restaurant_id")
	private int restaurantId;

	@Column(name = "store_id")
	private int storeId;

	private int orders;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StoreFeatures getStoreFeatures() {
		return storeFeatures;
	}

	public void setStoreFeatures(StoreFeatures storeFeatures) {
		this.storeFeatures = storeFeatures;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

}
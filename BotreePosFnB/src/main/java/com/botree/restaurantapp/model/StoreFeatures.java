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
 * Entity implementation class for Entity: fm_m_menu_options
 * 
 */

@XmlRootElement
@Entity
@Table(name = "fm_m_menu_options")

public class StoreFeatures implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "menu_name")
	private String menuName;
	
	@Expose
	@Column(name = "store_id")
	private int storeId;

	
	/*@OneToMany(mappedBy = "storeFeatures")
	private List<StoreFeaturesChild> storeFeaturesChilds;*/


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public int getStoreId() {
		return storeId;
	}


	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}


	/*public List<StoreFeaturesChild> getMobileMenuChilds() {
		return storeFeaturesChilds;
	}


	public void setMobileMenuChilds(List<StoreFeaturesChild> storeFeaturesChilds) {
		this.storeFeaturesChilds = storeFeaturesChilds;
	}*/
	
	
}
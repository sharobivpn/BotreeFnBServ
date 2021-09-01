package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: sl_m_restaurant
 * 
 */

@XmlRootElement
@Entity

@ManagedBean(name = "restaurantMaster")
@Table(name = "sl_m_restaurant")
public class RestaurantMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "name")
	private String name;

	@Expose
	@Column(name = "address")
	private String address;

	@Expose
	@Column(name = "locality")
	private String locality;

	@Expose
	@Column(name = "city")
	private String city;

	@Expose
	@Column(name = "state")
	private String state;

	@Expose
	@Column(name = "country")
	private String country;

	@Expose
	@Column(name = "phone")
	private String phone;

	@Expose
	@Column(name = "mobile")
	private String mobile;

	@Expose
	@Column(name = "category")
	private String category;

	@Expose
	@Column(name = "url")
	private String url;

	@Expose
	@Column(name = "ip")
	private String ip;

	@Expose
	@Column(name = "machine_os")
	private String operatingSystem;

	@Expose
	@Column(name = "machine_ram")
	private String ram;

	@Expose
	@Column(name = "status")
	private String status;

	@Expose
	@Column(name = "active_flag")
	private String activeFlag;

	/*@OneToMany(mappedBy = "restMaster")
	private List<User> userList;*/

	/*@OneToMany(mappedBy = "restaurantMaster")
	private List<StoreFeaturesChild> menuChildList;*/

	@OneToMany(mappedBy = "restaurant")
	private List<StoreMaster> storeList;

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

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/*public List<StoreMaster> getStoreList() {
		return storeList;
	}*/

	public void setStoreList(List<StoreMaster> storeList) {
		this.storeList = storeList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeFlag == null) ? 0 : activeFlag.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + id;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((locality == null) ? 0 : locality.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((operatingSystem == null) ? 0 : operatingSystem.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((ram == null) ? 0 : ram.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((storeList == null) ? 0 : storeList.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestaurantMaster other = (RestaurantMaster) obj;

		if (id != other.id)
			return false;

		return true;
	}

	/*public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}*/

	/*public List<StoreFeaturesChild> getMenuChildList() {
		return menuChildList;
	}

	public void setMenuChildList(List<StoreFeaturesChild> menuChildList) {
		this.menuChildList = menuChildList;
	}*/

}
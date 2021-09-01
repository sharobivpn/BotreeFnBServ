/**
 * 
 */
package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author ChanchalN
 *
 */
@XmlRootElement
@Entity
@Table(name = "delivery_boy")
public class DeliveryBoy implements Serializable{

	private static final long serialVersionUID = 1L;
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iddelivery_boy")
	private int id;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "address")
	private String address;
	
	@Expose
	@Column(name = "phone_no")
	private String phone_no;
	
	@Expose
	@Column(name = "email")
	private String email;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dob")
	private Date DOB;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "doj")
	private Date DOJ;
	
	@Expose
	@Column(name = "uniqueId")
	private String uniqueId;
	
	@Expose
	@Column(name = "store_id")
	private String store_id;
	
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
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDOB() {
		if(DOB==null) {
			return null;
		}
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public Date getDOJ() {
		if(DOJ==null) {
			return null;
		}
		return DOJ;
	}
	public void setDOJ(Date dOJ) {
		DOJ = dOJ;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	
}

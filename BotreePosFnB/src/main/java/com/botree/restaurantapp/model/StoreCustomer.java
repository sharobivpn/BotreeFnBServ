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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: ci_m_store_customer
 * 
 */

@XmlRootElement
@Entity
@Table(name = "ci_m_store_customer")
public class StoreCustomer implements Serializable {
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
	@Column(name = "contact_no")
	private String contactNo;
	@Expose
	@Column(name = "email_id")
	private String emailId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "credit_customer")
	private String creditCustomer;

	@Expose
	@Column(name = "credit_limit")
	private int creditLimit;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "created_by")
	private String createdBy;

	@Expose
	@Column(name = "created_date")
	private String createdDate;

	@Expose
	@Column(name = "updated_by")
	private String updatedBy;

	@Expose
	@Column(name = "updated_date")
	private String updatedDate;
	
	@Expose
	@Column(name = "user_name")
	private String userName;

	@Expose
	@Column(name = "password")
	private String password;
	
	@Expose
	@Column(name = "type")
	private String type;
	
	@Expose
	 @Column(name = "cust_vat_reg_no ")
	 private String cust_vat_reg_no;

	 @Expose
	 @Column(name = "location")
	 private String location;

	 @Expose
	 @Column(name = "house_no")
	 private String house_no;

	 @Expose
	 @Column(name = "street")
	 private String street;

	 @Expose
	 @Column(name = "car_no")
	 private String car_no;

	 @Expose
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "anniversary_date")
	 private Date anniversary;

	 @Expose
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "dob ")
	 private Date dob;
	 
	 @Expose
	 @Column(name = "state")
	 private String state;
	 
	 @Expose
	 @Column(name = "waiterName")
	 private String waiterName;
	 
	 @Expose
	 @Transient
	 private int createdByid;
	 
	 @Expose
	 @Column(name = "uniqueid_type")
	 private int uniqueidType=0;
	 
	 @Expose
	 @Column(name = "uniqueid_no")
	 private String uniqueidNo;
	 
	 @Expose
	 @Column(name = "gender")
	 private String gender;
	 
	private static final long serialVersionUID = 1L;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getCreditCustomer() {
		return creditCustomer;
	}

	public void setCreditCustomer(String creditCustomer) {
		this.creditCustomer = creditCustomer;
	}

	public int getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCust_vat_reg_no() {
		return cust_vat_reg_no;
	}

	public void setCust_vat_reg_no(String cust_vat_reg_no) {
		this.cust_vat_reg_no = cust_vat_reg_no;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHouse_no() {
		return house_no;
	}

	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public Date getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(Date anniversary) {
		this.anniversary = anniversary;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWaiterName() {
		return waiterName;
	}

	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}

	public int getCreatedByid() {
		return createdByid;
	}

	public void setCreatedByid(int createdByid) {
		this.createdByid = createdByid;
	}

	public int getUniqueidType() {
		return uniqueidType;
	}

	public void setUniqueidType(int uniqueidType) {
		this.uniqueidType = uniqueidType;
	}

	public String getUniqueidNo() {
		return uniqueidNo;
	}

	public void setUniqueidNo(String uniqueidNo) {
		this.uniqueidNo = uniqueidNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "StoreCustomer [id=" + id + ", name=" + name + ", address=" + address + ", contactNo=" + contactNo
				+ ", emailId=" + emailId + ", storeId=" + storeId + ", creditCustomer=" + creditCustomer
				+ ", creditLimit=" + creditLimit + ", deleteFlag=" + deleteFlag + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", userName=" + userName + ", password=" + password + ", type=" + type + ", cust_vat_reg_no="
				+ cust_vat_reg_no + ", location=" + location + ", house_no=" + house_no + ", street=" + street
				+ ", car_no=" + car_no + ", anniversary=" + anniversary + ", dob=" + dob + ", state=" + state
				+ ", waiterName=" + waiterName + ", createdByid=" + createdByid + ", uniqueidType=" + uniqueidType
				+ ", uniqueidNo=" + uniqueidNo + ", gender=" + gender + "]";
	}
	
	
	
}
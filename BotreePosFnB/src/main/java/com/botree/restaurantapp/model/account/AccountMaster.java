package com.botree.restaurantapp.model.account;

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

import net.sf.resultsetmapper.MapToData;

/**
 * Entity implementation class for Entity: in_m_category
 * 
 */

@XmlRootElement
@Entity
@Table(name = "acc_m_account")
public class AccountMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Expose
	@Column(name = "group_id")
	private int groupId;

	@Expose
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "code")
	private String code;
	
	@Expose
	@Column(name = "city_id")
	private int cityId;
	
	@Expose
	@Column(name = "zone_id")
	private int zoneId;
	
	@Expose
	@Column(name = "area_id")
	private int areaId;
	
	@Expose
	@Column(name = "address")
	private String address;

	@Expose
	@Column(name = "phone")
	private String phone;

	@Expose
	@Column(name = "pin")
	private String pin;

	@Expose
	@Column(name = "email")
	private String email;
	
	@Expose
	@Column(name = "pan_no")
	private String panNo;
	
	@Expose
	@Column(name = "gst_registration_no")
	private String gstRegistrationNo;
	
	@Expose
	@Column(name = "bcda_registration_no")
	private String bcdaRegistrationNo;
	
	@Expose
	@Column(name = "dl_no")
	private String dlNo;
	
	@Expose
	@Column(name = "cash_discount_percentage")
	private double cashDiscountPercentage;
	
	@Expose
	@Column(name = "trans_limit")
	private double transLimit;
	
	@Expose
	@Column(name = "is_active")
	private int isActive;

	@Expose
	@Column(name = "company_id")
	private int companyId;
	
	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "is_deleted")
	private int isDeleted;

	@Expose
	@Column(name = "created_by")
	private int createdBy;

	@Expose
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Expose
	@Column(name = "updated_by")
	private int updatedBy;

	@Expose
	@Column(name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@Expose
	@Transient
	private String lang;
	
	@Expose
	@Transient
	private double opBalance;
	
	@Expose
	@Transient
	private String asOnDate;
	
	@Expose
	@Transient
	private int finyrId;
	
	@Expose
	@MapToData(columnAliases = { "pst_type_id"})
	@Transient
	private int pst_type_id;
	
	@Expose
	private String group_code;
	
	@Expose
	private String reference_type;
	
	@Expose
	private int reference_id;
	
    @Expose
    @Column(name = "due_days" )
    private int dueDays;

    @Expose
    @Column(name = "due_percentage" )
    private double duePer;
    
	public AccountMaster() {
		// TODO Auto-generated constructor stub
	}
	
	public int getFinyrId() {
		return finyrId;
	}

	public void setFinyrId(int finyrId) {
		this.finyrId = finyrId;
	}

	public String getAsOnDate() {
		return asOnDate;
	}

	public void setAsOnDate(String asOnDate) {
		this.asOnDate = asOnDate;
	}

	public double getOpBalance() {
		return opBalance;
	}

	public void setOpBalance(double opBalance) {
		this.opBalance = opBalance;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getGstRegistrationNo() {
		return gstRegistrationNo;
	}

	public void setGstRegistrationNo(String gstRegistrationNo) {
		this.gstRegistrationNo = gstRegistrationNo;
	}

	public String getBcdaRegistrationNo() {
		return bcdaRegistrationNo;
	}

	public void setBcdaRegistrationNo(String bcdaRegistrationNo) {
		this.bcdaRegistrationNo = bcdaRegistrationNo;
	}

	public String getDlNo() {
		return dlNo;
	}

	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}

	public double getCashDiscountPercentage() {
		return cashDiscountPercentage;
	}

	public void setCashDiscountPercentage(double cashDiscountPercentage) {
		this.cashDiscountPercentage = cashDiscountPercentage;
	}

	public double getTransLimit() {
		return transLimit;
	}

	public void setTransLimit(double transLimit) {
		this.transLimit = transLimit;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPst_type_id() {
		return pst_type_id;
	}

	public void setPst_type_id(int pst_type_id) {
		this.pst_type_id = pst_type_id;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getReference_type() {
		return reference_type;
	}

	public void setReference_type(String reference_type) {
		this.reference_type = reference_type;
	}

	public int getReference_id() {
		return reference_id;
	}

	public void setReference_id(int reference_id) {
		this.reference_id = reference_id;
	}

	public int getDueDays() {
		return dueDays;
	}

	public void setDueDays(int dueDays) {
		this.dueDays = dueDays;
	}

	public double getDuePer() {
		return duePer;
	}

	public void setDuePer(double duePer) {
		this.duePer = duePer;
	}

	@Override
	public String toString() {
		return "AccountMaster [id=" + id + ", groupId=" + groupId + ", name=" + name + ", code=" + code + ", cityId="
				+ cityId + ", zoneId=" + zoneId + ", areaId=" + areaId + ", address=" + address + ", phone=" + phone
				+ ", pin=" + pin + ", email=" + email + ", panNo=" + panNo + ", gstRegistrationNo=" + gstRegistrationNo
				+ ", bcdaRegistrationNo=" + bcdaRegistrationNo + ", dlNo=" + dlNo + ", cashDiscountPercentage="
				+ cashDiscountPercentage + ", transLimit=" + transLimit + ", isActive=" + isActive + ", companyId="
				+ companyId + ", storeId=" + storeId + ", isDeleted=" + isDeleted + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", lang=" + lang + ", opBalance=" + opBalance + ", asOnDate=" + asOnDate + ", finyrId=" + finyrId
				+ ", pst_type_id=" + pst_type_id + ", group_code=" + group_code + ", reference_type=" + reference_type
				+ ", reference_id=" + reference_id + ", dueDays=" + dueDays + ", duePer=" + duePer + "]";
	}

}
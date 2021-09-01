package com.botree.restaurantapp.model.account;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;


@XmlRootElement
public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@MapToData(columnAliases = { "id"})
	private int id;
	
	@Expose
	@MapToData(columnAliases = { "group_id"})
	private int groupId;

	@Expose
	@MapToData(columnAliases = { "group_name"})
	private String groupName;
	
	@Expose
	@MapToData(columnAliases = { "name"})
	private String name;
	
	@Expose
	@MapToData(columnAliases = { "code"})
	private String code;
	
	@Expose
	@MapToData(columnAliases = { "city_id"})
	private int cityId;
	
	@Expose
	@MapToData(columnAliases = { "city_name"})
	private String cityName;
	
	@Expose
	@MapToData(columnAliases = { "zone_id"})
	private int zoneId;
	
	@Expose
	@MapToData(columnAliases = { "zone_name"})
	private String zoneName;
	
	@Expose
	@MapToData(columnAliases = { "area_id"})
	private int areaId;
	
	@Expose
	@MapToData(columnAliases = { "area_name"})
	private String areaName;
	
	@Expose
	@MapToData(columnAliases = { "address"})
	private String address;

	@Expose
	@MapToData(columnAliases = { "phone"})
	private String phone;

	@Expose
	@MapToData(columnAliases = { "pin"})
	private String pin;

	@Expose
	@MapToData(columnAliases = { "email"})
	private String email;
	
	@Expose
	@MapToData(columnAliases = { "pan_no"})
	private String panNo;
	
	@Expose
	@MapToData(columnAliases = { "gst_registration_no"})
	private String gstRegistrationNo;
	
	@Expose
	@MapToData(columnAliases = { "bcda_registration_no"})
	private String bcdaRegistrationNo;
	
	@Expose
	@MapToData(columnAliases = { "dl_no"})
	private String dlNo;
	
	@Expose
	@MapToData(columnAliases = { "cash_discount_percentage"})
	private double cashDiscountPercentage;
	
	@Expose
	@MapToData(columnAliases = { "trans_limit"})
	private double transLimit;
	
	@Expose
	@MapToData(columnAliases = { "is_active"})
	private int isActive;

	@Expose
	@MapToData(columnAliases = { "op_balance"})
	private double opBalance;
	
	@Expose
	@MapToData(columnAliases = { "outstanding_amount"})
	private double outstandingAmount;

	@Expose
	@MapToData(columnAliases = { "pst_type_id"})
	private int pst_type_id;
	
	@Expose
	@MapToData(columnAliases = { "group_name"})
	private String group_name;
	
	@Expose
	@MapToData(columnAliases = { "pst_type_code"})
	private String pst_type_code;
	
	@Expose
	@MapToData(columnAliases = { "group_code"})
	private String group_code;
	
	@Expose
	@MapToData(columnAliases = { "aboveScheme"})
	private double aboveScheme;
	
	@Expose
	@MapToData(columnAliases = { "state_id"})
	private int  state_id;
	
	@Expose
	@MapToData(columnAliases = { "country_id"})
    private int  country_id;
	
	@Expose
	@MapToData(columnAliases = { "state_name"})
	private String state_name ; 

	@Expose
	@MapToData(columnAliases = { "country_name"})
    private String country_name;
	
	@Expose
	@MapToData(columnAliases = { "reference_type"})
	private String reference_type;
	
	@Expose
	@MapToData(columnAliases = { "reference_id"})
	private int reference_id;
	
	@Expose
	@MapToData(columnAliases = { "type_name"})
	private String type_name;
	
    @Expose
    @MapToData(columnAliases = { "due_days" })
    private int dueDays;

    @Expose
    @MapToData(columnAliases = { "due_percentage" })
    private double duePer;
	
	public AccountDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public double getOpBalance() {
		return opBalance;
	}

	public void setOpBalance(double opBalance) {
		this.opBalance = opBalance;
	}

	public double getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public int getPst_type_id() {
		return pst_type_id;
	}

	public void setPst_type_id(int pst_type_id) {
		this.pst_type_id = pst_type_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getPst_type_code() {
		return pst_type_code;
	}

	public void setPst_type_code(String pst_type_code) {
		this.pst_type_code = pst_type_code;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public double getAboveScheme() {
		return aboveScheme;
	}

	public void setAboveScheme(double aboveScheme) {
		this.aboveScheme = aboveScheme;
	}

	public int getState_id() {
		return state_id;
	}

	public void setState_id(int state_id) {
		this.state_id = state_id;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
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

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
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
		return "AccountDTO [id=" + id + ", groupId=" + groupId + ", groupName=" + groupName + ", name=" + name
				+ ", code=" + code + ", cityId=" + cityId + ", cityName=" + cityName + ", zoneId=" + zoneId
				+ ", zoneName=" + zoneName + ", areaId=" + areaId + ", areaName=" + areaName + ", address=" + address
				+ ", phone=" + phone + ", pin=" + pin + ", email=" + email + ", panNo=" + panNo + ", gstRegistrationNo="
				+ gstRegistrationNo + ", bcdaRegistrationNo=" + bcdaRegistrationNo + ", dlNo=" + dlNo
				+ ", cashDiscountPercentage=" + cashDiscountPercentage + ", transLimit=" + transLimit + ", isActive="
				+ isActive + ", opBalance=" + opBalance + ", outstandingAmount=" + outstandingAmount + ", pst_type_id="
				+ pst_type_id + ", group_name=" + group_name + ", pst_type_code=" + pst_type_code + ", group_code="
				+ group_code + ", aboveScheme=" + aboveScheme + ", state_id=" + state_id + ", country_id=" + country_id
				+ ", state_name=" + state_name + ", country_name=" + country_name + ", reference_type=" + reference_type
				+ ", reference_id=" + reference_id + ", type_name=" + type_name + ", dueDays=" + dueDays + ", duePer="
				+ duePer + "]";
	}

}
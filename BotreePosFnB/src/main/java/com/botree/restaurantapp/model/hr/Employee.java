package com.botree.restaurantapp.model.hr;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: hr_m_emp
 * 
 */
@XmlRootElement
@Entity
@Table(name = "hr_m_emp")
public class Employee implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "code")
	private String code;

	@Expose
	@Column(name = "name")
	private String name;

	@Expose
	@Column(name = "permanent_address")
	private String permanentAddress;

	@Expose
	@Column(name = "present_address")
	private String presentAddress;

	@Expose
	@Column(name = "mobile_no")
	private String mobileNo;

	@Expose
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Expose
	@Column(name = "email")
	private String emailId;

	@Expose
	@ManyToOne
	@JoinColumn(name = "emp_type_id")
	private EmployeeType employeeType;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department dept;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "designation_id")
	private Designation designation;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "joining_date")
	private Date joiningDate;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "leaving_date")
	private Date leavingDate;
	
	@Expose
	@Column(name = "is_resigned")
	private String isResigned="N";

	@Expose
	@Column(name = "over_time_enable")
	private String overTimeEnable="N";

	@Expose
	@Column(name = "photo")
	private String photo;

	@Expose
	@Column(name = "id_proof_name")
	private String idProofName;

	@Expose
	@Column(name = "id_proof_doc_image")
	private String idProofDocImage;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag="N";
	
	@Expose
	@Column(name = "casual_leave")
	private double casualLeave;
	
	@Expose
	@Column(name = "sick_leave")
	private double sickLeave;
	
	@Expose
	@Column(name = "earned_leave")
	private double earnedLeave;
	
	@Expose
	@Column(name = "misc_leave")
	private double miscLeave;
	
	@Expose
	@Column(name = "gross_monthly_sal")
	private double grossMonthlySal;
	
	@Expose
	@Column(name = "net_monthly_sal")
	private double netMonthlySal;

	@Expose
	@Column(name = "created_by")
	private String createdBy;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Expose
	@Column(name = "updated_by")
	private String updatedBy;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getIsResigned() {
		return isResigned;
	}

	public void setIsResigned(String isResigned) {
		this.isResigned = isResigned;
	}

	public String getOverTimeEnable() {
		return overTimeEnable;
	}

	public void setOverTimeEnable(String overTimeEnable) {
		this.overTimeEnable = overTimeEnable;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIdProofName() {
		return idProofName;
	}

	public void setIdProofName(String idProofName) {
		this.idProofName = idProofName;
	}

	public String getIdProofDocImage() {
		return idProofDocImage;
	}

	public void setIdProofDocImage(String idProofDocImage) {
		this.idProofDocImage = idProofDocImage;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public double getCasualLeave() {
		return casualLeave;
	}

	public void setCasualLeave(double casualLeave) {
		this.casualLeave = casualLeave;
	}

	public double getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(double sickLeave) {
		this.sickLeave = sickLeave;
	}

	public double getEarnedLeave() {
		return earnedLeave;
	}

	public void setEarnedLeave(double earnedLeave) {
		this.earnedLeave = earnedLeave;
	}

	public double getMiscLeave() {
		return miscLeave;
	}

	public void setMiscLeave(double miscLeave) {
		this.miscLeave = miscLeave;
	}

	public double getGrossMonthlySal() {
		return grossMonthlySal;
	}

	public void setGrossMonthlySal(double grossMonthlySal) {
		this.grossMonthlySal = grossMonthlySal;
	}

	public double getNetMonthlySal() {
		return netMonthlySal;
	}

	public void setNetMonthlySal(double netMonthlySal) {
		this.netMonthlySal = netMonthlySal;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", storeId=" + storeId + ", code=" + code + ", name=" + name
				+ ", permanentAddress=" + permanentAddress + ", presentAddress=" + presentAddress + ", mobileNo="
				+ mobileNo + ", phoneNo=" + phoneNo + ", emailId=" + emailId + ", employeeType=" + employeeType
				+ ", dept=" + dept + ", designation=" + designation + ", joiningDate=" + joiningDate + ", leavingDate="
				+ leavingDate + ", isResigned=" + isResigned + ", overTimeEnable=" + overTimeEnable + ", photo=" + photo
				+ ", idProofName=" + idProofName + ", idProofDocImage=" + idProofDocImage + ", deleteFlag=" + deleteFlag
				+ ", casualLeave=" + casualLeave + ", sickLeave=" + sickLeave + ", earnedLeave=" + earnedLeave
				+ ", miscLeave=" + miscLeave + ", grossMonthlySal=" + grossMonthlySal + ", netMonthlySal="
				+ netMonthlySal + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + "]";
	}

	

}
/**
 * 
 */
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
 * @author Habib
 *
 */
@XmlRootElement
@Entity
@Table(name = "hr_t_emp_shift_schedule")
public class EmpShiftSchedule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;

	@Expose
	@Column(name = "duty_shift_id")
	private int dutyShiftId;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "from_date")
	private Date fromDate;
	
	@Expose
	@Column(name = "from_time")
	private String fromTime;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "to_date")
	private Date toDate;
	
	@Expose
	@Column(name = "to_time")
	private String toTime;
	
	@Expose
	@Column(name = "working_hour")
	private double workingHour;
	
	@Expose
	@Column(name = "shifting_no")
	private int shiftingNo;
	
	@Expose
	@Column(name = "is_canceled")
	private int isCanceled;
	
	@Expose
	@Column(name = "is_ammend")
	private int isAmmend;
	
	@Expose
	@Column(name = "ref_shift_id")
	private int refShiftId;
	
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getDutyShiftId() {
		return dutyShiftId;
	}

	public void setDutyShiftId(int dutyShiftId) {
		this.dutyShiftId = dutyShiftId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public int getShiftingNo() {
		return shiftingNo;
	}

	public void setShiftingNo(int shiftingNo) {
		this.shiftingNo = shiftingNo;
	}

	public int getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(int isCanceled) {
		this.isCanceled = isCanceled;
	}

	public int getIsAmmend() {
		return isAmmend;
	}

	public void setIsAmmend(int isAmmend) {
		this.isAmmend = isAmmend;
	}

	public int getRefShiftId() {
		return refShiftId;
	}

	public void setRefShiftId(int refShiftId) {
		this.refShiftId = refShiftId;
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

	public double getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(double workingHour) {
		this.workingHour = workingHour;
	}

	@Override
	public String toString() {
		return "EmpShiftSchedule [id=" + id + ", storeId=" + storeId + ", employee=" + employee + ", dutyShiftId="
				+ dutyShiftId + ", fromDate=" + fromDate + ", fromTime=" + fromTime + ", toDate=" + toDate + ", toTime="
				+ toTime + ", workingHour=" + workingHour + ", shiftingNo=" + shiftingNo + ", isCanceled=" + isCanceled
				+ ", isAmmend=" + isAmmend + ", refShiftId=" + refShiftId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}

	
	

}

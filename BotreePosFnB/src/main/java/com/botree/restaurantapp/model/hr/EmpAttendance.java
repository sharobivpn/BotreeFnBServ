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
@Table(name = "hr_t_emp_attendance")
public class EmpAttendance implements Serializable{
	
	
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
	@Temporal(TemporalType.DATE)
	@Column(name = "date_in")
	private Date dateIn;
	
	@Expose
	@Column(name = "time_in")
	private String timeIn;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "date_out")
	private Date dateOut;
	
	@Expose
	@Column(name = "time_out")
	private String timeOut;
	
	@Expose
	@Column(name = "worked_hour")
	private double workedHour;
	
	@Expose
	@Column(name = "in_office")
	private String inOffice="Y";
	
	@Expose
	@Column(name = "leave_id")
	private int leaveId;
	
	@Expose
	@Column(name = "is_off_day")
	private int isOffDay;
	
	@Expose
	@Column(name = "remarks")
	private String remarks;
	
	@Expose
	@Column(name = "shift_schedule_id")
	private int shiftScheduleId;
	
	@Expose
	@Column(name = "over_time")
	private int overTime;
	
	@Expose
	@Column(name = "grant_over_time")
	private String grantOverTime="N";
	
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

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public double getWorkedHour() {
		return workedHour;
	}

	public void setWorkedHour(double workedHour) {
		this.workedHour = workedHour;
	}

	public String getInOffice() {
		return inOffice;
	}

	public void setInOffice(String inOffice) {
		this.inOffice = inOffice;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	

	public int getIsOffDay() {
		return isOffDay;
	}

	public void setIsOffDay(int isOffDay) {
		this.isOffDay = isOffDay;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getShiftScheduleId() {
		return shiftScheduleId;
	}

	public void setShiftScheduleId(int shiftScheduleId) {
		this.shiftScheduleId = shiftScheduleId;
	}

	public int getOverTime() {
		return overTime;
	}

	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}

	public String getGrantOverTime() {
		return grantOverTime;
	}

	public void setGrantOverTime(String grantOverTime) {
		this.grantOverTime = grantOverTime;
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

	@Override
	public String toString() {
		return "EmpAttendance [id=" + id + ", storeId=" + storeId + ", employee=" + employee + ", dateIn=" + dateIn
				+ ", timeIn=" + timeIn + ", dateOut=" + dateOut + ", timeOut=" + timeOut + ", workedHour=" + workedHour
				+ ", inOffice=" + inOffice + ", leaveId=" + leaveId + ", isOffDay=" + isOffDay + ", remarks=" + remarks
				+ ", shiftScheduleId=" + shiftScheduleId + ", overTime=" + overTime + ", grantOverTime=" + grantOverTime
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + "]";
	}

	
	
	
}

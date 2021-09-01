/**
 * 
 */
package com.botree.restaurantapp.model.hr;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

/**
 * @author Habib
 *
 */
@XmlRootElement
public class EmpLeaveCal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	@MapToData(columnAliases = { "emp_id"})
	private int empId;
	
	@Expose
	@MapToData(columnAliases = { "emp_name"})
	private String empName;
	
	@Expose
	@MapToData(columnAliases = { "Jan"})
	private double janLeave;
	
	@Expose
	@MapToData(columnAliases = { "Feb"})
	private double febLeave;
	
	@Expose
	@MapToData(columnAliases = { "Mar"})
	private double marLeave;
	
	@Expose
	@MapToData(columnAliases = { "Apr"})
	private double aprLeave;
	
	@Expose
	@MapToData(columnAliases = { "May"})
	private double mayLeave;
	
	@Expose
	@MapToData(columnAliases = { "Jun"})
	private double junLeave;
	
	@Expose
	@MapToData(columnAliases = { "Jul"})
	private double julLeave;
	
	@Expose
	@MapToData(columnAliases = { "Aug"})
	private double augLeave;
	
	@Expose
	@MapToData(columnAliases = { "Sep"})
	private double sepLeave;
	
	@Expose
	@MapToData(columnAliases = { "Oct"})
	private double octLeave;
	
	@Expose
	@MapToData(columnAliases = { "Nov"})
	private double novLeave;
	
	@Expose
	@MapToData(columnAliases = { "Dec"})
	private double decLeave;
	
	@Expose
	@MapToData(columnAliases = { "total_leave"})
	private double totalLeave;
	
	@Expose
	@MapToData(columnAliases = { "alloted_leave"})
	private double allotedLeave;
	
	@Expose
	@MapToData(columnAliases = { "leave_balance"})
	private double leaveBalance;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getJanLeave() {
		return janLeave;
	}

	public void setJanLeave(double janLeave) {
		this.janLeave = janLeave;
	}

	public double getFebLeave() {
		return febLeave;
	}

	public void setFebLeave(double febLeave) {
		this.febLeave = febLeave;
	}

	public double getMarLeave() {
		return marLeave;
	}

	public void setMarLeave(double marLeave) {
		this.marLeave = marLeave;
	}

	public double getAprLeave() {
		return aprLeave;
	}

	public void setAprLeave(double aprLeave) {
		this.aprLeave = aprLeave;
	}

	public double getMayLeave() {
		return mayLeave;
	}

	public void setMayLeave(double mayLeave) {
		this.mayLeave = mayLeave;
	}

	public double getJunLeave() {
		return junLeave;
	}

	public void setJunLeave(double junLeave) {
		this.junLeave = junLeave;
	}

	public double getJulLeave() {
		return julLeave;
	}

	public void setJulLeave(double julLeave) {
		this.julLeave = julLeave;
	}

	public double getAugLeave() {
		return augLeave;
	}

	public void setAugLeave(double augLeave) {
		this.augLeave = augLeave;
	}

	public double getSepLeave() {
		return sepLeave;
	}

	public void setSepLeave(double sepLeave) {
		this.sepLeave = sepLeave;
	}

	public double getOctLeave() {
		return octLeave;
	}

	public void setOctLeave(double octLeave) {
		this.octLeave = octLeave;
	}

	public double getNovLeave() {
		return novLeave;
	}

	public void setNovLeave(double novLeave) {
		this.novLeave = novLeave;
	}

	public double getDecLeave() {
		return decLeave;
	}

	public void setDecLeave(double decLeave) {
		this.decLeave = decLeave;
	}

	public double getTotalLeave() {
		return totalLeave;
	}

	public void setTotalLeave(double totalLeave) {
		this.totalLeave = totalLeave;
	}

	public double getAllotedLeave() {
		return allotedLeave;
	}

	public void setAllotedLeave(double allotedLeave) {
		this.allotedLeave = allotedLeave;
	}

	public double getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(double leaveBalance) {
		this.leaveBalance = leaveBalance;
	}
	
	
	
	
	

}

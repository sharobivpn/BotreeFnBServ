/**
 * 
 */
package com.botree.restaurantapp.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

/**
 * @author Habib
 *
 */
public class DashTopCustomer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	@MapToData(columnAliases = { "name"})
	private String custName;
	@Expose
	@MapToData(columnAliases = { "mobile_no"})
	private String custPhone;
	@Expose
	@MapToData(columnAliases = { "email_id"})
	private String custEmail;
	@Expose
	@MapToData(columnAliases = { "amount"})
	private Double amtSpent;
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public Double getAmtSpent() {
		return amtSpent;
	}
	public void setAmtSpent(Double amtSpent) {
		this.amtSpent = amtSpent;
	}
	
	

}

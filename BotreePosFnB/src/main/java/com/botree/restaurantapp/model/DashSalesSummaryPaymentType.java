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
public class DashSalesSummaryPaymentType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	@MapToData(columnAliases = { "payment_type"})
	private String paymentType;
	@Expose
	@MapToData(columnAliases = { "amount"})
	private Double amt;
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	
	

}

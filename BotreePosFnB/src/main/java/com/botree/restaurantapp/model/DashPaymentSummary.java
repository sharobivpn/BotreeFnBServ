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
public class DashPaymentSummary implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	@MapToData(columnAliases = { "paid_amount"})
	private Double paidAmt;
	@Expose
	@MapToData(columnAliases = { "unpaid_amount"})
	private Double unpaidAmt;
	@Expose
	@MapToData(columnAliases = { "credit_amount"})
	private Double creditAmt;
	@Expose
	@MapToData(columnAliases = { "refund_amount"})
	private Double refundAmt;
	@Expose 
	@MapToData(columnAliases = { "cancelled_amount"})
	private Double cancelAmt;
	public Double getPaidAmt() {
		return paidAmt;
	}
	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}
	public Double getUnpaidAmt() {
		return unpaidAmt;
	}
	public void setUnpaidAmt(Double unpaidAmt) {
		this.unpaidAmt = unpaidAmt;
	}
	
	public Double getCreditAmt() {
		return creditAmt;
	}
	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}
	public Double getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(Double refundAmt) {
		this.refundAmt = refundAmt;
	}
	public Double getCancelAmt() {
		return cancelAmt;
	}
	public void setCancelAmt(Double cancelAmt) {
		this.cancelAmt = cancelAmt;
	}
	
	

}

/**
 * 
 */
package com.botree.restaurantapp.model;

import java.io.Serializable;

/**
 * @author Habib
 *
 */
public class DashBoardData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double paidAmt;
	private Double unpaidAmt;
	private Double creditAmt;
	private Double refundAmt;
	private Double cancelAmt;
	private Double cashAmt;
	private Double cardAmt;
	private Double otherAmt;
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
	public Double getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(Double cashAmt) {
		this.cashAmt = cashAmt;
	}
	public Double getCardAmt() {
		return cardAmt;
	}
	public void setCardAmt(Double cardAmt) {
		this.cardAmt = cardAmt;
	}
	public Double getOtherAmt() {
		return otherAmt;
	}
	public void setOtherAmt(Double otherAmt) {
		this.otherAmt = otherAmt;
	}
	@Override
	public String toString() {
		return "DashBoardData [paidAmt=" + paidAmt + ", unpaidAmt=" + unpaidAmt + ", creditAmt=" + creditAmt
				+ ", refundAmt=" + refundAmt + ", cancelAmt=" + cancelAmt + ", cashAmt=" + cashAmt + ", cardAmt="
				+ cardAmt + ", otherAmt=" + otherAmt + "]";
	}
	
	

}

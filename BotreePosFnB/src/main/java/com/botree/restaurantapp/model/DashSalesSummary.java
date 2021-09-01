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
public class DashSalesSummary implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Expose
	@MapToData(columnAliases = { "total_sales_amount"})
	private Double totSale;
	@Expose
	@MapToData(columnAliases = { "no_of_order"})
	private Integer noofOrder;
	@Expose
	@MapToData(columnAliases = { "no_of_customer"})
	private Integer noofCust;
	@Expose
	@MapToData(columnAliases = { "total_purchase_amount"})
	private Double totPurchase;
	@Expose
	@MapToData(columnAliases = { "total_paid_amount"})
	private Double totPaidAmt;
	@Expose
	@MapToData(columnAliases = { "paid_order_no"})
	private Double noofPaidOrder;
	@Expose
	@MapToData(columnAliases = { "total_credit_amount"})
	private Double totCreditAmt;
	@Expose
	@MapToData(columnAliases = { "credit_order_no"})
	private Double noofCreditOrder;
	
	public Double getTotSale() {
		return totSale;
	}
	public void setTotSale(Double totSale) {
		this.totSale = totSale;
	}
	public Integer getNoofOrder() {
		return noofOrder;
	}
	public void setNoofOrder(Integer noofOrder) {
		this.noofOrder = noofOrder;
	}
	public Integer getNoofCust() {
		return noofCust;
	}
	public void setNoofCust(Integer noofCust) {
		this.noofCust = noofCust;
	}
	public Double getTotPurchase() {
		return totPurchase;
	}
	public void setTotPurchase(Double totPurchase) {
		this.totPurchase = totPurchase;
	}
	public Double getTotPaidAmt() {
		return totPaidAmt;
	}
	public void setTotPaidAmt(Double totPaidAmt) {
		this.totPaidAmt = totPaidAmt;
	}
	public Double getNoofPaidOrder() {
		return noofPaidOrder;
	}
	public void setNoofPaidOrder(Double noofPaidOrder) {
		this.noofPaidOrder = noofPaidOrder;
	}
	public Double getTotCreditAmt() {
		return totCreditAmt;
	}
	public void setTotCreditAmt(Double totCreditAmt) {
		this.totCreditAmt = totCreditAmt;
	}
	public Double getNoofCreditOrder() {
		return noofCreditOrder;
	}
	public void setNoofCreditOrder(Double noofCreditOrder) {
		this.noofCreditOrder = noofCreditOrder;
	}
	
	

}

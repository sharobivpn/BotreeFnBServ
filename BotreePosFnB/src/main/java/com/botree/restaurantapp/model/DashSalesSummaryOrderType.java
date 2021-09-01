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
public class DashSalesSummaryOrderType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose 
	@MapToData(columnAliases = { "order_type"})
	private String orderType;
	@Expose
	@MapToData(columnAliases = { "sales_amount"})
	private Double saleAmt;
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Double getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(Double saleAmt) {
		this.saleAmt = saleAmt;
	}
	
	

}

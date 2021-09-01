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
public class DashTopItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	@MapToData(columnAliases = { "item"})
	private String itemName;
	@Expose 
	@MapToData(columnAliases = { "qty"})
	private Double qty;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	

}

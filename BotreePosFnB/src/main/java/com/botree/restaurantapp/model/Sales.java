package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;

public class Sales implements Serializable {

	private int id;
	private Date salesFrmDate = new Date();
	private Date salesToDate = new Date();
	private Date salesDate = new Date();
	private String salesReportPeriodType = "daily";
	private String reportTitle = "Sales Report";
	private int storeId;
	private String storeName;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSalesFrmDate() {
		return salesFrmDate;
	}

	public void setSalesFrmDate(Date salesFrmDate) {
		this.salesFrmDate = salesFrmDate;
	}

	public Date getSalesToDate() {
		return salesToDate;
	}

	public void setSalesToDate(Date salesToDate) {
		this.salesToDate = salesToDate;
	}

	public String getSalesReportPeriodType() {
		return salesReportPeriodType;
	}

	public void setSalesReportPeriodType(String salesReportPeriodType) {
		this.salesReportPeriodType = salesReportPeriodType;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

}
package com.botree.restaurantapp.model;

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
 * Entity implementation class for Entity: im_t_sales_return_items_c
 * 
 */
/**
 * @author rajarshi
 *
 */
@XmlRootElement
@Entity
@Table(name = "im_t_sales_return_items_c")
public class SalesReturnItem implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "return_id")
	private SalesReturn salesReturn;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private MenuItem menuItem;
	
	@Expose
	@Column(name = "order_id")
	private int orderId;

	@Expose
	@Column(name = "barcode")
	private String barcode;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "item_sold_quantity")
	private Double itemSoldQuantity;
	
	@Expose
	@Column(name = "item_return_quantity")
	private Double itemReturnQuantity;

	@Expose
	@Column(name = "item_rate")
	private Double itemRate;

	@Expose
	@Column(name = "item_return_rate")
	private Double itemReturnRate;

	@Expose
	@Column(name = "item_price")
	private Double itemPrice;
	
	@Expose
	@Column(name = "dis_per")
	private Double disPer;
	
	@Expose
	@Column(name = "dis_amt")
	private Double disAmt;
	
	@Expose
	@Column(name = "vat")
	private Double vat;

	@Expose
	@Column(name = "vat_amt")
	private Double vatAmt;
	
	@Expose
	@Column(name = "service_tax")
	private Double serviceTax;

	@Expose
	@Column(name = "service_tax_amt")
	private Double serviceTaxAmt;

	@Expose
	@Column(name = "item_return_price")
	private Double itemReturnPrice;

	@Expose
	@Column(name = "unit_id")
	private int unitId=0;
	
	@Expose
	@Column(name = "unit_name")
	private String unitName;

	@Expose
	@ManyToOne
	@JoinColumn(name = "return_type")
	private ReturnTypes returnTypes;

	@Expose
	@Column(name = "remarks")
	private String remarks;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

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

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SalesReturn getSalesReturn() {
		return salesReturn;
	}

	public void setSalesReturn(SalesReturn salesReturn) {
		this.salesReturn = salesReturn;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Double getItemSoldQuantity() {
		return itemSoldQuantity;
	}

	public void setItemSoldQuantity(Double itemSoldQuantity) {
		this.itemSoldQuantity = itemSoldQuantity;
	}

	public Double getItemReturnQuantity() {
		return itemReturnQuantity;
	}

	public void setItemReturnQuantity(Double itemReturnQuantity) {
		this.itemReturnQuantity = itemReturnQuantity;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public Double getItemReturnRate() {
		return itemReturnRate;
	}

	public void setItemReturnRate(Double itemReturnRate) {
		this.itemReturnRate = itemReturnRate;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Double getDisPer() {
		return disPer;
	}

	public void setDisPer(Double disPer) {
		this.disPer = disPer;
	}

	public Double getDisAmt() {
		return disAmt;
	}

	public void setDisAmt(Double disAmt) {
		this.disAmt = disAmt;
	}

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public Double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(Double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public Double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public Double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(Double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public Double getItemReturnPrice() {
		return itemReturnPrice;
	}

	public void setItemReturnPrice(Double itemReturnPrice) {
		this.itemReturnPrice = itemReturnPrice;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public ReturnTypes getReturnTypes() {
		return returnTypes;
	}

	public void setReturnTypes(ReturnTypes returnTypes) {
		this.returnTypes = returnTypes;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	

}
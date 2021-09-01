/**
 * 
 */
package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.botree.restaurantapp.model.inv.FgStockIn;
import com.google.gson.annotations.Expose;


/**
 * @author Habib
 *
 */
/**
 * Entity implementation class for Entity: im_t_fg_purchase_return_items_c
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_fg_purchase_return_items_c")
public class FgReturnItem implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "fg_stock_in_id")
	private FgStockIn fgStockIn;

	@ManyToOne
	@JoinColumn(name = "return_id")
	private FgReturn fgReturn;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private MenuItem menuItem;

	@Expose
	@Column(name = "item_quantity")
	private Double itemQuantity=0.0;
	
	@Expose
	@Column(name = "item_invoice_quantity")
	private Double itemInvoiceQuantity=0.0;

	@Expose
	@Column(name = "item_total_price")
	private Double itemTotalPrice=0.0;

	@Expose
	@Column(name = "item_rate")
	private Double itemRate=0.0;

	@Expose
	@Column(name = "item_invoice_rate")
	private Double itemInvoiceRate=0.0;
	
	@Expose
	@Column(name = "dis_per")
	private Double disPer=0.0;
	
	@Expose
	@Column(name = "dis_amt")
	private Double disAmt=0.0;

	@Expose
	@Column(name = "vat_rate")
	private double vatRate=0.0;
	
	@Expose
	@Column(name = "vat_amt")
	private double vatAmt=0.0;
	
	@Expose
	@Column(name = "service_tax_rate")
	private double serviceTaxRate=0.0;
	
	@Expose
	@Column(name = "service_tax_amt")
	private double serviceTaxAmt=0.0;

	@Expose
	@Column(name = "total_price")
	private double totalPrice=0.0;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "unit")
	private String unit;

	@Expose
	@Column(name = "bill_no")
	private String billNo;


	@Expose
	@ManyToOne
	@JoinColumn(name = "return_type")
	private ReturnTypes returnTypes;

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

	@Expose
	@Column(name = "remarks")
	private String remarks;
	
	@Expose
	@Transient
	private double curStock=0.0;
	
	@Expose
	@Transient
	private int fgStockInId=0;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FgStockIn getFgStockIn() {
		return fgStockIn;
	}

	public void setFgStockIn(FgStockIn fgStockIn) {
		this.fgStockIn = fgStockIn;
	}

	public FgReturn getFgReturn() {
		return fgReturn;
	}

	public void setFgReturn(FgReturn fgReturn) {
		this.fgReturn = fgReturn;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Double getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Double getItemInvoiceQuantity() {
		return itemInvoiceQuantity;
	}

	public void setItemInvoiceQuantity(Double itemInvoiceQuantity) {
		this.itemInvoiceQuantity = itemInvoiceQuantity;
	}

	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public Double getItemInvoiceRate() {
		return itemInvoiceRate;
	}

	public void setItemInvoiceRate(Double itemInvoiceRate) {
		this.itemInvoiceRate = itemInvoiceRate;
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

	public double getVatRate() {
		return vatRate;
	}

	public void setVatRate(double vatRate) {
		this.vatRate = vatRate;
	}

	public double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public double getServiceTaxRate() {
		return serviceTaxRate;
	}

	public void setServiceTaxRate(double serviceTaxRate) {
		this.serviceTaxRate = serviceTaxRate;
	}

	public double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public ReturnTypes getReturnTypes() {
		return returnTypes;
	}

	public void setReturnTypes(ReturnTypes returnTypes) {
		this.returnTypes = returnTypes;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public double getCurStock() {
		return curStock;
	}

	public void setCurStock(double curStock) {
		this.curStock = curStock;
	}

	
	public int getFgStockInId() {
		return fgStockInId;
	}

	public void setFgStockInId(int fgStockInId) {
		this.fgStockInId = fgStockInId;
	}

	@Override
	public String toString() {
		return "FgReturnItem [id=" + id + ", fgStockIn=" + fgStockIn + ", fgReturn=" + fgReturn + ", menuItem="
				+ menuItem + ", itemQuantity=" + itemQuantity + ", itemInvoiceQuantity=" + itemInvoiceQuantity
				+ ", itemTotalPrice=" + itemTotalPrice + ", itemRate=" + itemRate + ", itemInvoiceRate="
				+ itemInvoiceRate + ", disPer=" + disPer + ", disAmt=" + disAmt + ", vatRate=" + vatRate + ", vatAmt="
				+ vatAmt + ", serviceTaxRate=" + serviceTaxRate + ", serviceTaxAmt=" + serviceTaxAmt + ", totalPrice="
				+ totalPrice + ", storeId=" + storeId + ", unit=" + unit + ", billNo=" + billNo + ", returnTypes="
				+ returnTypes + ", deleteFlag=" + deleteFlag + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", remarks=" + remarks
				+ ", curStock=" + curStock + ", fgStockInId=" + fgStockInId + "]";
	}

	
	

	
	
}
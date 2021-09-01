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

import com.botree.restaurantapp.model.rm.MetricUnit;
import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_t_raw_purchase_return_items_c
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_raw_purchase_return_items_c")
public class InventoryReturnItem implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "stock_in_id")
	private InventoryStockIn inventoryStockIn;

	@ManyToOne
	@JoinColumn(name = "return_id")
	private InventoryReturn inventoryReturn;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private InventoryItems inventoryItems;

	@Expose
	@Column(name = "item_quantity")
	private Double itemQuantity;
	
	@Expose
	@Column(name = "item_invoice_quantity")
	private Double itemInvoiceQuantity;

	@Expose
	@Column(name = "item_total_price")
	private Double itemTotalPrice;

	@Expose
	@Column(name = "item_rate")
	private Double itemRate;

	@Expose
	@Column(name = "item_invoice_rate")
	private Double itemInvoiceRate;
	
	@Expose
	@Column(name = "dis_per")
	private Double disPer;
	
	@Expose
	@Column(name = "dis_amt")
	private Double disAmt;
	
	@Expose
	@Column(name = "is_tax_exclusive")
	private String isTaxExclusive;

	@Expose
	@Column(name = "tax_rate")
	private Double taxRate;

	@Expose
	@Column(name = "tax_amount")
	private Double taxAmount;

	@Expose
	@Column(name = "item_gross_amount")
	private Double itemGrossAmount;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "po_id")
	private int poId;

	@Expose
	@Column(name = "po_date")
	private String poDate;

	@Expose
	@Column(name = "unit_id")
	private int unitId;

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
	private MetricUnit metricUnit;

	@Expose
	@Transient
	private InventoryStockIn inventoryStckIn;

	private static final long serialVersionUID = 1L;

	
	public MetricUnit getMetricUnit() {
		return metricUnit;
	}

	public void setMetricUnit(MetricUnit metricUnit) {
		this.metricUnit = metricUnit;
	}

	public InventoryStockIn getInventoryStckIn() {
		return inventoryStckIn;
	}

	public void setInventoryStckIn(InventoryStockIn inventoryStckIn) {
		this.inventoryStckIn = inventoryStckIn;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getItemInvoiceRate() {
		return itemInvoiceRate;
	}

	public void setItemInvoiceRate(Double itemInvoiceRate) {
		this.itemInvoiceRate = itemInvoiceRate;
	}

	public ReturnTypes getReturnTypes() {
		return returnTypes;
	}

	public void setReturnTypes(ReturnTypes returnTypes) {
		this.returnTypes = returnTypes;
	}

	public InventoryReturn getInventoryReturn() {
		return inventoryReturn;
	}

	public void setInventoryReturn(InventoryReturn inventoryReturn) {
		this.inventoryReturn = inventoryReturn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public InventoryItems getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(InventoryItems inventoryItems) {
		this.inventoryItems = inventoryItems;
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

	public String getIsTaxExclusive() {
		return isTaxExclusive;
	}

	public void setIsTaxExclusive(String isTaxExclusive) {
		this.isTaxExclusive = isTaxExclusive;
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

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
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

	public InventoryStockIn getInventoryStockIn() {
		return inventoryStockIn;
	}

	public void setInventoryStockIn(InventoryStockIn inventoryStockIn) {
		this.inventoryStockIn = inventoryStockIn;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getItemGrossAmount() {
		return itemGrossAmount;
	}

	public void setItemGrossAmount(Double itemGrossAmount) {
		this.itemGrossAmount = itemGrossAmount;
	}

}
package com.botree.restaurantapp.model.inv;

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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.botree.restaurantapp.model.MenuItem;
import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "im_t_fg_stock_in_items_c")
public class FgStockInItemsChild implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private MenuItem menuItem;
	// private int itemId;

	//@Expose
	@ManyToOne//(targetEntity = FgStockIn.class)
	@JoinColumn(name = "fg_stock_in_id")
	private FgStockIn fgStockInId;

	@Expose
	@Column(name = "fg_status")
	private String fgStatus;

	@Expose
	@Column(name = "estimated_quantity")
	private double edProdAmount;
	// private double estimatedQuantity;

	@Expose
	@Column(name = "stock_in_quantity")
	private double stockInQuantity;

	@Expose
	@Column(name = "old_stock_in_quantity")
	private double oldStockInQuantity;

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
	
	//added on 17.06.2019
	@Expose
	@Column(name = "unit")
	private String unit;
	
	@Expose
	@Column(name = "item_rate")
	private double itemRate=0.0;
	
	@Expose
	@Column(name = "item_total")
	private double itemTotal=0.0;
	
	@Expose
	@Column(name = "disc_per")
	private double discPer=0.0;
	
	@Expose
	@Column(name = "disc_amt")
	private double discAmt=0.0;
	
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
	@Transient
	private double curStock=0.0;
	
	@Expose
	@Transient
	private double prevretQty=0.0;
	
	@Expose
	@Transient
	private int stockId;
	
	@Expose
	@Transient
	private Date stockDate;
	
	@Expose
	@Transient
	private int vndrId;
	
	@Expose
	@Transient
	private String vndrName;
	
	@Expose
	@Transient
	private String invNo;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getFgStatus() {
		return fgStatus;
	}

	public void setFgStatus(String fgStatus) {
		this.fgStatus = fgStatus;
	}

	/*
	 * public double getEstimatedQuantity() { return estimatedQuantity; }
	 * 
	 * public void setEstimatedQuantity(double estimatedQuantity) {
	 * this.estimatedQuantity = estimatedQuantity; }
	 */

	public double getStockInQuantity() {
		return stockInQuantity;
	}

	public double getEdProdAmount() {
		return edProdAmount;
	}

	public void setEdProdAmount(double edProdAmount) {
		this.edProdAmount = edProdAmount;
	}

	public void setStockInQuantity(double stockInQuantity) {
		this.stockInQuantity = stockInQuantity;
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

	/*
	 * public int getItemId() { return itemId; }
	 * 
	 * public void setItemId(int itemId) { this.itemId = itemId; }
	 */

	public FgStockIn getFgStockInId() {
		return fgStockInId;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public void setFgStockInId(FgStockIn fgStockInId) {
		this.fgStockInId = fgStockInId;
	}

	public double getOldStockInQuantity() {
		return oldStockInQuantity;
	}

	public void setOldStockInQuantity(double oldStockInQuantity) {
		this.oldStockInQuantity = oldStockInQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getItemRate() {
		return itemRate;
	}

	public void setItemRate(double itemRate) {
		this.itemRate = itemRate;
	}

	public double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public double getDiscPer() {
		return discPer;
	}

	public void setDiscPer(double discPer) {
		this.discPer = discPer;
	}

	public double getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
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

	
	public double getCurStock() {
		return curStock;
	}

	public void setCurStock(double curStock) {
		this.curStock = curStock;
	}
	
	

	public double getPrevretQty() {
		return prevretQty;
	}

	public void setPrevretQty(double prevretQty) {
		this.prevretQty = prevretQty;
	}

	
	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public int getVndrId() {
		return vndrId;
	}

	public void setVndrId(int vndrId) {
		this.vndrId = vndrId;
	}

	public String getVndrName() {
		return vndrName;
	}

	public void setVndrName(String vndrName) {
		this.vndrName = vndrName;
	}
	

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	@Override
	public String toString() {
		return "FgStockInItemsChild [id=" + id + ", storeId=" + storeId + ", menuItem=" + menuItem + ", fgStockInId="
				+ fgStockInId + ", fgStatus=" + fgStatus + ", edProdAmount=" + edProdAmount + ", stockInQuantity="
				+ stockInQuantity + ", oldStockInQuantity=" + oldStockInQuantity + ", deleteFlag=" + deleteFlag
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", unit=" + unit + ", itemRate=" + itemRate + ", itemTotal="
				+ itemTotal + ", discPer=" + discPer + ", discAmt=" + discAmt + ", vatRate=" + vatRate + ", vatAmt="
				+ vatAmt + ", serviceTaxRate=" + serviceTaxRate + ", serviceTaxAmt=" + serviceTaxAmt + ", totalPrice="
				+ totalPrice + ", curStock=" + curStock + ", prevretQty=" + prevretQty + ", stockId=" + stockId
				+ ", stockDate=" + stockDate + ", vndrId=" + vndrId + ", vndrName=" + vndrName + ", invNo=" + invNo
				+ "]";
	}

	

}

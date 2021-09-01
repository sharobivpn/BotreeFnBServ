package com.botree.Restaurant.rahulbean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "im_t_fg_clse_c")
public class FgCloseChild implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "item_id")
	private int itemId;

	@Expose
	@Column(name = "fg_clse_id")
	private int fgClseId;

	@Expose
	@Column(name = "stock_in_quantity")
	private double stockInQuantity;

	@Expose
	@Column(name = "sale_out_quantity")
	private double saleOutQuantity;

	@Expose
	@Column(name = "edp_quantity")
	private double edpQuantity;

	@Expose
	@Column(name = "current_stock")
	private double currentStock;

	@Expose
	@Column(name = "waste")
	private double waste;

	@Expose
	@Column(name = "consume")
	private double consume;

	@Expose
	@Column(name = "re_stock_in")
	private double reStockIn;

	@Expose
	@Column(name = "variance")
	private double variance;

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
	@Transient
	private String approved;
	
	@Expose
	@Transient
	private String itemName;
	
	@Expose
	@Transient
	private String categoryName;
	
	@Expose
	@Transient
	private String subCategoryName;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getConsume() {
		return consume;
	}

	public void setConsume(double consume) {
		this.consume = consume;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public double getStockInQuantity() {
		return stockInQuantity;
	}

	public void setStockInQuantity(double stockInQuantity) {
		this.stockInQuantity = stockInQuantity;
	}

	public double getSaleOutQuantity() {
		return saleOutQuantity;
	}

	public void setSaleOutQuantity(double saleOutQuantity) {
		this.saleOutQuantity = saleOutQuantity;
	}

	public double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(double currentStock) {
		this.currentStock = currentStock;
	}

	public double getWaste() {
		return waste;
	}

	public void setWaste(double waste) {
		this.waste = waste;
	}

	public double getReStockIn() {
		return reStockIn;
	}

	public void setReStockIn(double reStockIn) {
		this.reStockIn = reStockIn;
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

	public double getEdpQuantity() {
		return edpQuantity;
	}

	public void setEdpQuantity(double edpQuantity) {
		this.edpQuantity = edpQuantity;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getFgClseId() {
		return fgClseId;
	}

	public void setFgClseId(int fgClseId) {
		this.fgClseId = fgClseId;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
}

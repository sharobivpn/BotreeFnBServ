package com.botree.restaurantapp.model.rm;

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

import com.botree.restaurantapp.model.InventoryItems;
import com.botree.restaurantapp.model.MenuItem;
import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_t_recipe_ingredient
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_recipe_ingredient")
public class Ingredient implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private MenuItem item;

	@Expose
	@ManyToOne
	@JoinColumn(name = "inv_type_item_id")
	private InventoryItems inventoryItem;

	@Expose
	@Column(name = "cooking_amount")
	private Double cookingAmount;

	@Expose
	@ManyToOne
	@JoinColumn(name = "cooking_unit_id")
	private MetricUnit cookingUnit;

	@Expose
	@Column(name = "metric_amount")
	private Double metricAmount;

	@Expose
	@ManyToOne
	@JoinColumn(name = "metric_unit_id")
	private MetricUnit metricUnit;

	@Expose
	@Column(name = "cost_amount")
	private Double costAmount;

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
	private double edpQuantity;

	@Expose
	@Transient
	private double currentQuantity; // stock in quantity

	@Expose
	@Transient
	private double currentStockInQuantityEdpWise; // stock in quantity edp wise

	@Expose
	@Transient
	private double currentStockOutQuantityEdpWise; // stock out quantity edp
													// wise

	@Expose
	@Transient
	private int poId;

	@Expose
	@Transient
	private int vendorId;

	private static final long serialVersionUID = 1L;

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public double getCurrentStockOutQuantityEdpWise() {
		return currentStockOutQuantityEdpWise;
	}

	public void setCurrentStockOutQuantityEdpWise(
			double currentStockOutQuantityEdpWise) {
		this.currentStockOutQuantityEdpWise = currentStockOutQuantityEdpWise;
	}

	public double getEdpQuantity() {
		return edpQuantity;
	}

	public void setEdpQuantity(double edpQuantity) {
		this.edpQuantity = edpQuantity;
	}

	public double getCurrentStockInQuantityEdpWise() {
		return currentStockInQuantityEdpWise;
	}

	public void setCurrentStockInQuantityEdpWise(
			double currentStockInQuantityEdpWise) {
		this.currentStockInQuantityEdpWise = currentStockInQuantityEdpWise;
	}

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

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public InventoryItems getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(InventoryItems inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	public Double getCookingAmount() {
		return cookingAmount;
	}

	public void setCookingAmount(Double cookingAmount) {
		this.cookingAmount = cookingAmount;
	}

	public MetricUnit getCookingUnit() {
		return cookingUnit;
	}

	public void setCookingUnit(MetricUnit cookingUnit) {
		this.cookingUnit = cookingUnit;
	}

	public Double getMetricAmount() {
		return metricAmount;
	}

	public void setMetricAmount(Double metricAmount) {
		this.metricAmount = metricAmount;
	}

	public MetricUnit getMetricUnit() {
		return metricUnit;
	}

	public void setMetricUnit(MetricUnit metricUnit) {
		this.metricUnit = metricUnit;
	}

	public Double getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}

	public double getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(double currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", storeId=" + storeId + ", item=" + item + ", inventoryItem=" + inventoryItem
				+ ", cookingAmount=" + cookingAmount + ", cookingUnit=" + cookingUnit + ", metricAmount=" + metricAmount
				+ ", metricUnit=" + metricUnit + ", costAmount=" + costAmount + ", deleteFlag=" + deleteFlag
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", edpQuantity=" + edpQuantity + ", currentQuantity="
				+ currentQuantity + ", currentStockInQuantityEdpWise=" + currentStockInQuantityEdpWise
				+ ", currentStockOutQuantityEdpWise=" + currentStockOutQuantityEdpWise + ", poId=" + poId
				+ ", vendorId=" + vendorId + "]";
	}

}
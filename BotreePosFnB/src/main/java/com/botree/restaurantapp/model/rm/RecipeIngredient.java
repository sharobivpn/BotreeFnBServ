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
import javax.xml.bind.annotation.XmlRootElement;

import com.botree.restaurantapp.model.InventoryItems;
import com.botree.restaurantapp.model.MenuItem;
import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "im_t_recipe_ingredient")
public class RecipeIngredient implements Serializable {

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

	@Expose
	@ManyToOne
	@JoinColumn(name = "inv_type_item_id")
	private InventoryItems inventoryItems;

	@Expose
	@ManyToOne
	@JoinColumn(name = "cooking_unit_id")
	private CookingUnit cookingUnit;

	@Expose
	@ManyToOne
	@JoinColumn(name = "metric_unit_id")
	private MetricUnit metricUnit;

	@Expose
	@Column(name = "cooking_amount")
	private double cookingAmount;

	@Expose
	@Column(name = "metric_amount")
	private double metricAmount;

	@Expose
	@Column(name = "cost_amount")
	private double costAmount;

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

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public double getCookingAmount() {
		return cookingAmount;
	}

	public void setCookingAmount(double cookingAmount) {
		this.cookingAmount = cookingAmount;
	}

	public double getMetricAmount() {
		return metricAmount;
	}

	public void setMetricAmount(double metricAmount) {
		this.metricAmount = metricAmount;
	}

	public double getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(double costAmount) {
		this.costAmount = costAmount;
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

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public InventoryItems getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(InventoryItems inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public CookingUnit getCookingUnit() {
		return cookingUnit;
	}

	public void setCookingUnit(CookingUnit cookingUnit) {
		this.cookingUnit = cookingUnit;
	}

	public MetricUnit getMetricUnit() {
		return metricUnit;
	}

	public void setMetricUnit(MetricUnit metricUnit) {
		this.metricUnit = metricUnit;
	}

}

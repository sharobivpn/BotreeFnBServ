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

import com.botree.restaurantapp.model.MenuItem;
import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "im_t_estimate_daily_prod_item_c")
public class EstimateDailyProdItem implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@ManyToOne//(targetEntity = EstimateDailyProd.class)
	@JoinColumn(name = "estimate_daily_prod_id")
	private EstimateDailyProd estimateDailyProdId;

	/*
	 * @Expose
	 * 
	 * @Column(name = "item_id") private int itemId;
	 */

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private MenuItem menuItem;

	@Expose
	@Column(name = "old_stock")
	private Double oldStock;

	@Expose
	@Column(name = "ed_prod_amount")
	private int edProdAmount;

	@Expose
	@Column(name = "min_quantity")
	private String minDuantity;

	@Expose
	@Column(name = "required_quantity")
	private String requiredQuantity;
	
	@Expose
	@Column(name = "edp_status")
	private String edpStatus;

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

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getEdProdAmount() {
		return edProdAmount;
	}

	public void setEdProdAmount(int edProdAmount) {
		this.edProdAmount = edProdAmount;
	}

	public String getMinDuantity() {
		return minDuantity;
	}

	public void setMinDuantity(String minDuantity) {
		this.minDuantity = minDuantity;
	}

	public String getRequiredQuantity() {
		return requiredQuantity;
	}

	public void setRequiredQuantity(String requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	public double getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(double costAmount) {
		this.costAmount = costAmount;
	}

	public EstimateDailyProd getEstimateDailyProdId() {
		return estimateDailyProdId;
	}

	public void setEstimateDailyProdId(EstimateDailyProd estimateDailyProdId) {
		this.estimateDailyProdId = estimateDailyProdId;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Double getOldStock() {
		return oldStock;
	}

	public void setOldStock(Double oldStock) {
		this.oldStock = oldStock;
	}

	public String getEdpStatus() {
		return edpStatus;
	}

	public void setEdpStatus(String edpStatus) {
		this.edpStatus = edpStatus;
	}
	
	

}

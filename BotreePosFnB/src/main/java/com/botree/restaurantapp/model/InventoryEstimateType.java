package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: im_m_estimate_type
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_m_estimate_type")
public class InventoryEstimateType implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "remarks")
	private String remarks;

	@Expose
	@Column(name = "approved")
	private String approved;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Column(name = "created_by")
	private Double createdBy;

	@Column(name = "created_date")
	private Double createdDate;

	@Column(name = "updated_by")
	private Double updatedBy;

	@Expose
	@Column(name = "updated_date")
	private int updatedDate;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Double getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Double createdBy) {
		this.createdBy = createdBy;
	}

	public Double getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Double createdDate) {
		this.createdDate = createdDate;
	}

	public Double getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Double updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(int updatedDate) {
		this.updatedDate = updatedDate;
	}

}
package com.botree.restaurantapp.model;

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

/**
 * Entity implementation class for Entity: rg_m_pos_modules
 * 
 */

@XmlRootElement
@Entity
@Table(name = "rg_m_pos_modules")
public class PosModules implements Serializable, Comparable<PosModules> {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "module_name")
	private String moduleName;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "order")
	private int order;

	@Expose
	@Column(name = "status")
	private String status;

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
	@Column(name = "is_report")
	private String isReport;

	@Expose
	@Column(name = "admin")
	private String admin;

	@Expose
	@Column(name = "report_size")
	private String reportSize;

	// @Expose
	@Transient
	private boolean present;

	@Expose
	@Transient
	private String modPresent;

	@Expose
	@Column(name = "report_code")
	private String reportCode;

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getModPresent() {
		return modPresent;
	}

	public void setModPresent(String modPresent) {
		this.modPresent = modPresent;
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public String getReportSize() {
		return reportSize;
	}

	public void setReportSize(String reportSize) {
		this.reportSize = reportSize;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public int compareTo(PosModules o) {
		// TODO Auto-generated method stub

		if (this.getOrder() < o.getOrder()) {
			return -1;
		} else {
			return 1;
		}
	}

	/*
	 * @OneToMany(mappedBy = "storeFeatures") private List<StoreFeaturesChild>
	 * storeFeaturesChilds;
	 */

}
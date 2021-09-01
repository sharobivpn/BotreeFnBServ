package com.botree.restaurantapp.model.account;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;
@XmlRootElement
public class AccountGroupDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	@Expose
	@MapToData(columnAliases = { "id" })
	private int id;

	@Expose
	@MapToData(columnAliases = { "name" })
	private String name;
	
	@Expose
	@MapToData(columnAliases = { "code" })
	private String code;

	@Expose
	@MapToData(columnAliases = { "description" })
	private String description;
	
	@Expose
	@MapToData(columnAliases = { "account_type_id" })
	private int accountTypeId;

	@Expose
	@MapToData(columnAliases = { "company_id" })
	private int companyId;
	
	@Expose
	@MapToData(columnAliases = { "store_id" })
	private int storeId;

	@Expose
	@MapToData(columnAliases = { "created_by" })
	private int createdBy;

	@Expose
	@MapToData(columnAliases = { "created_date" })
	private Date createdDate;

	@Expose
	@MapToData(columnAliases = { "updated_by" })
	private int updatedBy;

	@Expose
	@MapToData(columnAliases = { "updated_date" })
	private Date updatedDate;
	
	@Expose
	@MapToData(columnAliases = { "account_type_name" })
	private String accountTypeName;
	
	//16.02.2018
	@Expose
	@MapToData(columnAliases = { "is_system" })
	private int is_system;

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getIs_system() {
		return is_system;
	}

	public void setIs_system(int is_system) {
		this.is_system = is_system;
	}

	@Override
	public String toString() {
		return "AccountGroupDTO [id=" + id + ", name=" + name + ", code=" + code + ", description=" + description
				+ ", accountTypeId=" + accountTypeId + ", companyId=" + companyId + ", storeId=" + storeId
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", accountTypeName=" + accountTypeName + ", is_system=" + is_system
				+ "]";
	}
	
}

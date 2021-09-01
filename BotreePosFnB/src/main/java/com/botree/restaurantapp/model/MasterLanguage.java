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
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: lang_m_restaurant
 * 
 */

@XmlRootElement
@Entity
@Table(name = "lang_m_restaurant")
public class MasterLanguage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private StoreMaster store;

	@Expose
	@Column(name = "language")
	private String language;

	@Expose
	@Column(name = "code_android")
	private String codeAndroid;

	@Expose
	@Column(name = "code_ios")
	private String codeIOS;

	@Expose
	@Column(name = "status")
	private String status;

	@Column(name = "del_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "code_web")
	private String codeWeb;

	public String getCodeWeb() {
		return codeWeb;
	}

	public void setCodeWeb(String codeWeb) {
		this.codeWeb = codeWeb;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StoreMaster getStore() {
		return store;
	}

	public void setStore(StoreMaster store) {
		this.store = store;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCodeAndroid() {
		return codeAndroid;
	}

	public void setCodeAndroid(String codeAndroid) {
		this.codeAndroid = codeAndroid;
	}

	public String getCodeIOS() {
		return codeIOS;
	}

	public void setCodeIOS(String codeIOS) {
		this.codeIOS = codeIOS;
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

	@Override
	public String toString() {
		return "MasterLanguage [id=" + id + ", store=" + store + ", language="
				+ language + ", codeAndroid=" + codeAndroid + ", codeIOS="
				+ codeIOS + ", status=" + status + ", deleteFlag=" + deleteFlag
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeAndroid == null) ? 0 : codeAndroid.hashCode());
		result = prime * result + ((codeIOS == null) ? 0 : codeIOS.hashCode());
		result = prime * result
				+ ((deleteFlag == null) ? 0 : deleteFlag.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterLanguage other = (MasterLanguage) obj;

		return true;
	}

}
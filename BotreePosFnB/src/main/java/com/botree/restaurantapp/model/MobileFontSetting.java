package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: setting_m_mob_font
 * 
 */

@XmlRootElement
@Entity
@Table(name = "setting_m_mob_font")

public class MobileFontSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "normal")
	private String normalFont;

	@Expose
	@Column(name = "bold")
	private String boldFont;

	@Column(name = "del_flag")
	private String deleteFlag;

	@OneToOne
	@JoinColumn(name = "store_id")
	private StoreMaster storeMaster;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNormalFont() {
		return normalFont;
	}

	public void setNormalFont(String normalFont) {
		this.normalFont = normalFont;
	}

	public String getBoldFont() {
		return boldFont;
	}

	public void setBoldFont(String boldFont) {
		this.boldFont = boldFont;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public StoreMaster getStoreMaster() {
		return storeMaster;
	}

	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}

	@Override
	public String toString() {
		return "MobileFontSetting [id=" + id + ", normalFont=" + normalFont + ", boldFont=" + boldFont + ", deleteFlag=" + deleteFlag + ", storeMaster=" + storeMaster + "]";
	}
}
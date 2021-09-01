/**
 * 
 */
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
 * @author habib
 *
 */
/**
 * Entity implementation class for Entity: ac_m_expenditure_type 
 * 
 */
@XmlRootElement
@Entity
@Table(name = "ac_m_expenditure_type")
public class DailyExpenditureType implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "type_name")
	private String typeName;

	@Expose
	@Column(name = "active_flag")
	private String activeFlag;

	

	private static final long serialVersionUID = 1L;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTypeName() {
		return typeName;
	}



	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}



	public String getActiveFlag() {
		return activeFlag;
	}



	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	
}
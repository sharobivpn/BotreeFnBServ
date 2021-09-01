package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: bp_m_tax
 * 
 */
@XmlRootElement
@Entity
@Table(name = "bp_m_tax")
public class Tax implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "service_tax")
	private Double serviceTax;

	@Expose
	@Column(name = "vat")
	private Double vat;

	@Expose
	@OneToOne(mappedBy = "tax")
	private Bill billTax;
	
	@Expose
	@Column(name = "store_id")
	private int storeId;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public Bill getBillTax() {
		return billTax;
	}

	public void setBillTax(Bill billTax) {
		this.billTax = billTax;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	
}
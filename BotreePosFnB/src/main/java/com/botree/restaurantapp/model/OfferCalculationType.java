package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: offercalculation_type
 *
 */
@XmlRootElement
@Entity
@Table(name = "offercalculation_type")
public class OfferCalculationType implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	  private int id;
	@Column(name = "calculationtype_name")
	private String calculationTypeName;
	
	@OneToMany(mappedBy="offercalc")
	private List<OfferItem> offeritem;
	
	  
	  private static final long serialVersionUID = 1L;
	
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCalculationTypeName() {
		return calculationTypeName;
	}


	public void setCalculationTypeName(String calculationTypeName) {
		this.calculationTypeName = calculationTypeName;
	}


	public List<OfferItem> getOfferitem() {
		return offeritem;
	}


	public void setOfferitem(List<OfferItem> offeritem) {
		this.offeritem = offeritem;
	}

	@Override
	public String toString() {
		return "OfferCalculationType [id=" + id + ", calculationTypeName="
				+ calculationTypeName + ", offeritem=" + offeritem + "]";
	}

	
	  
	  
}
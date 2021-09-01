package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: offer_type
 *
 */
@XmlRootElement
@Entity
@Table(name = "offer_type")
public class OfferType implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	  private int id;
	@Column(name = "OfferType_name")
	private String offerTypeName;
	
	//@OneToMany(mappedBy="offertype")
	@Column(name = "offer")
	private Offer offer;
	  
	private static final long serialVersionUID = 1L;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOfferTypeName() {
		return offerTypeName;
	}

	public void setOfferTypeName(String offerTypeName) {
		this.offerTypeName = offerTypeName;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	@Override
	public String toString() {
		return "OfferType [id=" + id + ", offerTypeName=" + offerTypeName
				+ ", offer=" + offer + "]";
	}
	  
}
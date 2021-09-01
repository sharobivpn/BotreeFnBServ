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

/**
 * Entity implementation class for Entity: offer_item
 *
 */
@XmlRootElement
@Entity
@Table(name = "offer_item")
public class OfferItem implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	  private int id;
	@Column(name = "offer_value")
	  private String offerValue;
	
	@ManyToOne
	  @JoinColumn(name="Item_Id")
	  private MenuItem item;
	  
	  @ManyToOne
	  @JoinColumn(name="Item_type_Id")
	  private MenuCategory menucategory;
	  
	  @ManyToOne
	  @JoinColumn(name="Offer_Id")
	  private Offer offer;
	  
	  @ManyToOne
	  @JoinColumn(name="OfferCalculation_type_Id")
	  private OfferCalculationType offercalc;
	  
	  @Column(name = "offercalculaton_type_id")
	  private int offerCalcTypeId;
	  
	  private static final long serialVersionUID = 1L;
	  
	  

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(String offerValue) {
		this.offerValue = offerValue;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public MenuCategory getMenucategory() {
		return menucategory;
	}

	public void setMenucategory(MenuCategory menucategory) {
		this.menucategory = menucategory;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public OfferCalculationType getOffercalc() {
		return offercalc;
	}

	public void setOffercalc(OfferCalculationType offercalc) {
		this.offercalc = offercalc;
	}

	public int getOfferCalcTypeId() {
		return offerCalcTypeId;
	}

	public void setOfferCalcTypeId(int offerCalcTypeId) {
		this.offerCalcTypeId = offerCalcTypeId;
	}

	
	@Override
	public String toString() {
		return "OfferItem [id=" + id + ", offerValue=" + offerValue + ", item="
				+ item + ", menucategory=" + menucategory + ", offer=" + offer
				+ ", offercalc=" + offercalc + ", offerCalcTypeId="
				+ offerCalcTypeId + "]";
	}
	
	  
}
package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: offer
 *
 */
@XmlRootElement
@Entity
@Table(name = "offer")
public class Offer implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	  private int id;
	@ManyToOne
	@JoinColumn(name="Offer_type_Id")
	private OfferType offertype;
	
	@OneToMany(mappedBy="offer")
	private List<OrderMaster> order;
	
	@OneToMany(mappedBy="offer")
	private List<OfferItem> offeritem;
	
	@OneToMany(mappedBy="offer")
	private List<Coupon> coupon;
	  
	  private static final long serialVersionUID = 1L;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OfferType getOffertype() {
		return offertype;
	}

	public void setOffertype(OfferType offertype) {
		this.offertype = offertype;
	}

	public List<OrderMaster> getOrder() {
		return order;
	}

	public void setOrder(List<OrderMaster> order) {
		this.order = order;
	}

	public List<OfferItem> getOfferitem() {
		return offeritem;
	}

	public void setOfferitem(List<OfferItem> offeritem) {
		this.offeritem = offeritem;
	}

	public List<Coupon> getCoupon() {
		return coupon;
	}

	public void setCoupon(List<Coupon> coupon) {
		this.coupon = coupon;
	}

	
	@Override
	public String toString() {
		return "Offer [id=" + id + ", offertype=" + offertype + ", order="
				+ order + ", offeritem=" + offeritem + ", coupon=" + coupon
				+ "]";
	}

	
	  
	  
}
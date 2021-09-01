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
 * Entity implementation class for Entity: coupon
 *
 */
@XmlRootElement
@Entity
@Table(name = "coupon")
public class Coupon implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	  private int id;
	@Column(name = "coupon_value")
	  private String couponValue;
	
	@ManyToOne
	  @JoinColumn(name="Person_Id")
	  private Customer customers;
	@ManyToOne
	  @JoinColumn(name="Offer_Id")
	  private Offer offer;
	
	@OneToMany(mappedBy="coupon")
	private List<OrderMaster> ordermaster;
	  
	  private static final long serialVersionUID = 1L;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCouponValue() {
		return couponValue;
	}

	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}

	public Customer getCustomer() {
		return customers;
	}

	public void setCustomer(Customer customers) {
		this.customers = customers;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public List<OrderMaster> getOrdermaster() {
		return ordermaster;
	}

	public void setOrdermaster(List<OrderMaster> ordermaster) {
		this.ordermaster = ordermaster;
	}

	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", couponValue=" + couponValue
				+ ", customers=" + customers + ", offer=" + offer
				+ ", ordermaster=" + ordermaster + "]";
	}

	
	  
	  
}
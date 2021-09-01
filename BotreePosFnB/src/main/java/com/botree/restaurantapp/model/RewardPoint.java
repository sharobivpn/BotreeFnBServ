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
 * Entity implementation class for Entity: Reward_point
 * 
 */

@XmlRootElement
@Entity
@Table(name = "reward_point")
public class RewardPoint implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	@Column(name = "points_value")
	private double pointsValue;
	
	@ManyToOne
	@JoinColumn(name="Person_id")
	private Customer customer;
	
	@OneToMany(mappedBy="reward")
	private List<OrderMaster> order;
	
	private static final long serialVersionUID = 1L;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPointsValue() {
		return pointsValue;
	}

	public void setPointsValue(double pointsValue) {
		this.pointsValue = pointsValue;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderMaster> getOrder() {
		return order;
	}

	public void setOrder(List<OrderMaster> order) {
		this.order = order;
	}

	
	@Override
	public String toString() {
		return "RewardPoint [id=" + id + ", pointsValue=" + pointsValue
				+ ", customer=" + customer + ", order=" + order + "]";
	}
	

}
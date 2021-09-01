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
 * Entity implementation class for Entity: order_table
 *
 */
@XmlRootElement
@Entity
@Table(name = "order_table")
public class OrderTable implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	  private int id;
	@ManyToOne
	@JoinColumn(name="Order_Id")
	private OrderMaster order;
	  
	  private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrderMaster getOrder() {
		return order;
	}

	public void setOrder(OrderMaster order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderTable [id=" + id + ", order=" + order + "]";
	}

	
	  
	  
}
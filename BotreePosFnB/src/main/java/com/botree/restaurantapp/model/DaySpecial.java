package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fm_t_food_specials
 * 
 */
@XmlRootElement
@Entity
@Table(name = "fm_t_food_specials")
public class DaySpecial implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date")
	private Date fromdate;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_date")
	private Date todate;

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id")
	private MenuItem item;

	/*@Expose
	@OneToMany(mappedBy = "item")
	private List<DaySpecial> specialList;*/

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	/*@Expose
	@Column(name = "promotion_desc")
	private String promotionDesc;
	
	@Expose
	@Column(name = "promotion_value")
	private double promotionValue;
	
	@Expose
	@Column(name = "promotion_flag")
	private String promotionFlag;
	
	@Expose
	@Column(name = "food_option1")
	private String foodOption1;*/

	private static final long serialVersionUID = 1L;

	public DaySpecial() {
		super();
	}

	public DaySpecial(MenuItem item) {
		super();
		this.item = item;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	/*public List<DaySpecial> getSpecialList() {
		return specialList;
	}

	public void setSpecialList(List<DaySpecial> specialList) {
		this.specialList = specialList;
	}*/

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/*public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public double getPromotionValue() {
		return promotionValue;
	}

	public void setPromotionValue(double promotionValue) {
		this.promotionValue = promotionValue;
	}

	public String getPromotionFlag() {
		return promotionFlag;
	}

	public void setPromotionFlag(String promotionFlag) {
		this.promotionFlag = promotionFlag;
	}

	public String getFoodOption1() {
		return foodOption1;
	}

	public void setFoodOption1(String foodOption1) {
		this.foodOption1 = foodOption1;
	}*/

}
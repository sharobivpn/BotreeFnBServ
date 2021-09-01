package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Lazy;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fo_t_orders_item
 * 
 */
@XmlRootElement
@Entity
@ManagedBean(name = "orderitem")
@Table(name = "fo_t_orders_item")
@Lazy(value = false)
public class OrderItem implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "quantityof_item")
	private String quantityOfItem;

	@Expose
	@Column(name = "Total_price_by_item")
	private Double totalPriceByItem;

	@Expose
	@ManyToOne
	@JoinColumn(name = "Item_Id")
	private MenuItem item;

	@ManyToOne
	@JoinColumn(name = "Order_Id")
	private OrderMaster orders;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "food_option1")
	private String foodOption1;

	@Expose
	@Column(name = "food_option2")
	private String foodOption2;

	@Expose
	@Column(name = "promotion_value")
	private double promotionValue;

	@Expose
	@Column(name = "promotion_discount_amt")
	private double promotionDiscountAmt;

	@Expose
	@Column(name = "order_time")
	private String orderTime;

	@Expose
	@Column(name = "special_note")
	private String specialNote;

	@Expose
	@Column(name = "Order_type_Id")
	private int ordertype;

	@Transient
	private String orderTypeName;

	@Expose
	@Column(name = "previous_quantity")
	private String previousQuantity;

	@Expose
	@Column(name = "table_no")
	private String tableNo;

	@Expose
	@Column(name = "change_note")
	private String changeNote;

	@Expose
	@Column(name = "customer_name")
	private String customerName;

	@Expose
	@Column(name = "kitchen_name")
	private String kitchenName;

	@Expose
	@Column(name = "kitchen_print")
	private String kitchenPrint;

	@Expose
	@Column(name = "chef_name")
	private String chefName;

	@Expose
	@Column(name = "cooking_status")
	private String cookingStatus;

	@Expose
	@Column(name = "cooking_time")
	private String cookingTime;

	@Expose
	@Column(name = "cooking_start_time")
	private String cookingStartTime;

	@Expose
	@Column(name = "cooking_end_time")
	private String cookingEndTime;

	@Expose
	@Column(name = "serve_status")
	private String serveStatus;

	@Expose
	@Column(name = "kitchen_out")
	private String kitchenOut;

	@Expose
	@Transient
	private int ordrId;
	
	@Expose
	@Transient
	private String orderNo;

	@Expose
	@Column(name = "rate")
	private double rate;

	@Expose
	@Column(name = "service_tax")
	private double serviceTax;

	@Expose
	@Column(name = "vat")
	private double vat;

	@Expose
	@Column(name = "net_price")
	private double netPrice;
	
	@Expose
	@Transient
	private String lang;
	
	@Expose
	@Transient
	private int itemId;
	
	@Expose
	@Column(name = "discount_percentage")
	private double discountPercentage;
	
	@Expose
	@Column(name = "customer_discount")
	private double customerDiscount;

	private static final long serialVersionUID = 1L;
	
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuantityOfItem() {
		return quantityOfItem;
	}

	public void setQuantityOfItem(String quantityOfItem) {
		this.quantityOfItem = quantityOfItem;
	}

	public Double getTotalPriceByItem() {
		return totalPriceByItem;
	}

	public void setTotalPriceByItem(Double totalPriceByItem) {
		this.totalPriceByItem = totalPriceByItem;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public OrderMaster getOrders() {
		return orders;
	}

	public void setOrders(OrderMaster orders) {
		this.orders = orders;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getFoodOption1() {
		return foodOption1;
	}

	public void setFoodOption1(String foodOption1) {
		this.foodOption1 = foodOption1;
	}

	public String getFoodOption2() {
		return foodOption2;
	}

	public void setFoodOption2(String foodOption2) {
		this.foodOption2 = foodOption2;
	}

	public double getPromotionValue() {
		return promotionValue;
	}

	public void setPromotionValue(double promotionValue) {
		this.promotionValue = promotionValue;
	}

	public double getPromotionDiscountAmt() {
		return promotionDiscountAmt;
	}

	public void setPromotionDiscountAmt(double promotionDiscountAmt) {
		this.promotionDiscountAmt = promotionDiscountAmt;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
	}

	public int getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getPreviousQuantity() {
		return previousQuantity;
	}

	public void setPreviousQuantity(String previousQuantity) {
		this.previousQuantity = previousQuantity;
	}

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getKitchenName() {
		return kitchenName;
	}

	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}

	public String getKitchenPrint() {
		return kitchenPrint;
	}

	public void setKitchenPrint(String kitchenPrint) {
		this.kitchenPrint = kitchenPrint;
	}

	public String getChefName() {
		return chefName;
	}

	public void setChefName(String chefName) {
		this.chefName = chefName;
	}

	public String getCookingStatus() {
		return cookingStatus;
	}

	public void setCookingStatus(String cookingStatus) {
		this.cookingStatus = cookingStatus;
	}

	public String getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getCookingStartTime() {
		return cookingStartTime;
	}

	public void setCookingStartTime(String cookingStartTime) {
		this.cookingStartTime = cookingStartTime;
	}

	public String getCookingEndTime() {
		return cookingEndTime;
	}

	public void setCookingEndTime(String cookingEndTime) {
		this.cookingEndTime = cookingEndTime;
	}

	public String getServeStatus() {
		return serveStatus;
	}

	public void setServeStatus(String serveStatus) {
		this.serveStatus = serveStatus;
	}

	public String getKitchenOut() {
		return kitchenOut;
	}

	public void setKitchenOut(String kitchenOut) {
		this.kitchenOut = kitchenOut;
	}

	public int getOrdrId() {
		return ordrId;
	}

	public void setOrdrId(int ordrId) {
		this.ordrId = ordrId;
	}
	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public double getCustomerDiscount() {
		return customerDiscount;
	}

	public void setCustomerDiscount(double customerDiscount) {
		this.customerDiscount = customerDiscount;
	}
	
	

}
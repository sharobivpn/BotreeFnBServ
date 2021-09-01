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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fm_m_food_items
 * 
 */
/**
 * @author rajarshi
 * 
 */
@XmlRootElement
@Entity
@ManagedBean(name = "item")
@Table(name = "fm_m_food_items")
public class MenuItem implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "name")
	private String name;

	@Expose
	@Column(name = "description")
	private String description;

	@Expose
	@Column(name = "price")
	private double price;

	@ManyToOne
	@JoinColumn(name = "Item_type_Id")
	private MenuCategory menucategory;

	@Expose
	@OneToOne
	@JoinColumn(name = "item_time_id")
	private ItemTime itemtime;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "veg")
	private String veg = "N";

	@Expose
	@Column(name = "promotion_desc")
	private String promotionDesc = "";

	@Expose
	@Column(name = "promotion_value")
	private int promotionValue;

	@Expose
	@Column(name = "promotion_flag")
	private String promotionFlag = "N";

	@Expose
	@Column(name = "food_option1")
	private String foodOption1;

	@Expose
	@Column(name = "food_option2")
	private String foodOption2;

	@Expose
	@Column(name = "food_option_flag")
	private String foodOptionFlag;

	@Expose
	@Column(name = "beverages")
	private String beverages = "N";

	@Expose
	@Column(name = "desert")
	private String desert = "N";

	@Transient
	private String subCategory;

	@Expose
	@Column(name = "spicy")
	private String spicy = "N";

	@Expose
	@Column(name = "house_special")
	private String houseSpecial = "N";

	@Expose
	@Column(name = "code")
	private String code;

	@Expose
	@Column(name = "vat")
	private double vat;

	@Expose
	@Column(name = "service_tax")
	private double serviceTax;

	@Expose
	@Column(name = "daily_min_qty")
	private int dailyMinQty;

	@Expose
	@Transient
	private int oldStock;

	@Expose
	@Transient
	private int edpQuantity;

	@Expose
	@Transient
	private int requiredQuantity;

	@Expose
	@Column(name = "unit")
	private String unit;

	@Expose
	@Column(name = "packaged")
	private String packaged;

	@Expose
	@Column(name = "production")
	private String production;

	@Expose
	@Column(name = "cooking_time_in_mins")
	private int cookingTimeInMins;

	@Expose
	@Column(name = "printer_id")
	private int printerId;

	@Expose
	@Column(name = "creation_date")
	private String creationDate;

	@Expose
	@Column(name = "creation_time")
	private String creationTime;

	@Expose
	@Column(name = "updation_date")
	private String updationDate;

	@Expose
	@Column(name = "updation_time")
	private String updationTime;

	@Expose
	@Transient
	private String specialNote = "N";

	@Expose
	@Transient
	private int subCategoryId;

	@Expose
	@Transient
	private String subCategoryName;

	@Expose
	@Transient
	private int categoryId;

	@Expose
	@Transient
	private String categoryName;

	@Expose
	@Column(name = "printer_list")
	private String printerList;

	@Expose
	@Column(name = "spot_discount")
	private String spotDiscount="Y";

	@Expose
	@Column(name = "bg_color")
	private String bgColor;

	@Expose
	@Transient
	private double currentStock;

	@Expose
	@Transient
	private String translatedName;

	@Expose
	@Transient
	private String translatedDesc;
	
	@Expose
	@Column(name = "is_kot_print")
	private String isKotPrint="Y";
	
	@Expose
	@Column(name = "purchase_price")
	private double purchasePrice=0.0;

	private static final long serialVersionUID = 1L;
	
	public String getIsKotPrint() {
		return isKotPrint;
	}

	public void setIsKotPrint(String isKotPrint) {
		this.isKotPrint = isKotPrint;
	}

	public String getTranslatedName() {
		return translatedName;
	}

	public void setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
	}

	public String getTranslatedDesc() {
		return translatedDesc;
	}

	public void setTranslatedDesc(String translatedDesc) {
		this.translatedDesc = translatedDesc;
	}

	public double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(double currentStock) {
		this.currentStock = currentStock;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getSpotDiscount() {
		return spotDiscount;
	}

	public void setSpotDiscount(String spotDiscount) {
		this.spotDiscount = spotDiscount;
	}

	public String getPrinterList() {
		return printerList;
	}

	public void setPrinterList(String printerList) {
		this.printerList = printerList;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public MenuCategory getMenucategory() {
		return menucategory;
	}

	public void setMenucategory(MenuCategory menucategory) {
		this.menucategory = menucategory;
	}

	public ItemTime getItemtime() {
		return itemtime;
	}

	public void setItemtime(ItemTime itemtime) {
		this.itemtime = itemtime;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getVeg() {
		return veg;
	}

	public void setVeg(String veg) {
		this.veg = veg;
	}

	public String getPromotionDesc() {

		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public int getPromotionValue() {
		return promotionValue;
	}

	public void setPromotionValue(int promotionValue) {
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
	}

	public String getFoodOption2() {
		return foodOption2;
	}

	public void setFoodOption2(String foodOption2) {
		this.foodOption2 = foodOption2;
	}

	public String getFoodOptionFlag() {
		return foodOptionFlag;
	}

	public void setFoodOptionFlag(String foodOptionFlag) {
		this.foodOptionFlag = foodOptionFlag;
	}

	public String getBeverages() {
		return beverages;
	}

	public void setBeverages(String beverages) {
		this.beverages = beverages;
	}

	public String getDesert() {
		return desert;
	}

	public void setDesert(String desert) {
		this.desert = desert;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getSpicy() {
		return spicy;
	}

	public void setSpicy(String spicy) {
		this.spicy = spicy;
	}

	public String getHouseSpecial() {
		return houseSpecial;
	}

	public void setHouseSpecial(String houseSpecial) {
		this.houseSpecial = houseSpecial;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public int getDailyMinQty() {
		return dailyMinQty;
	}

	public void setDailyMinQty(int dailyMinQty) {
		this.dailyMinQty = dailyMinQty;
	}

	public int getOldStock() {
		return oldStock;
	}

	public void setOldStock(int oldStock) {
		this.oldStock = oldStock;
	}

	public int getEdpQuantity() {
		return edpQuantity;
	}

	public void setEdpQuantity(int edpQuantity) {
		this.edpQuantity = edpQuantity;
	}

	public int getRequiredQuantity() {
		return requiredQuantity;
	}

	public void setRequiredQuantity(int requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPackaged() {
		return packaged;
	}

	public void setPackaged(String packaged) {
		this.packaged = packaged;
	}

	public int getCookingTimeInMins() {
		return cookingTimeInMins;
	}

	public void setCookingTimeInMins(int cookingTimeInMins) {
		this.cookingTimeInMins = cookingTimeInMins;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public int getPrinterId() {
		return printerId;
	}

	public void setPrinterId(int printerId) {
		this.printerId = printerId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(String updationDate) {
		this.updationDate = updationDate;
	}

	public String getUpdationTime() {
		return updationTime;
	}

	public void setUpdationTime(String updationTime) {
		this.updationTime = updationTime;
	}

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
	}
	
	

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", menucategory=" + menucategory + ", itemtime=" + itemtime + ", deleteFlag=" + deleteFlag
				+ ", storeId=" + storeId + ", veg=" + veg + ", promotionDesc=" + promotionDesc + ", promotionValue="
				+ promotionValue + ", promotionFlag=" + promotionFlag + ", foodOption1=" + foodOption1
				+ ", foodOption2=" + foodOption2 + ", foodOptionFlag=" + foodOptionFlag + ", beverages=" + beverages
				+ ", desert=" + desert + ", subCategory=" + subCategory + ", spicy=" + spicy + ", houseSpecial="
				+ houseSpecial + ", code=" + code + ", vat=" + vat + ", serviceTax=" + serviceTax + ", dailyMinQty="
				+ dailyMinQty + ", oldStock=" + oldStock + ", edpQuantity=" + edpQuantity + ", requiredQuantity="
				+ requiredQuantity + ", unit=" + unit + ", packaged=" + packaged + ", production=" + production
				+ ", cookingTimeInMins=" + cookingTimeInMins + ", printerId=" + printerId + ", creationDate="
				+ creationDate + ", creationTime=" + creationTime + ", updationDate=" + updationDate + ", updationTime="
				+ updationTime + ", specialNote=" + specialNote + ", subCategoryId=" + subCategoryId
				+ ", subCategoryName=" + subCategoryName + ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", printerList=" + printerList + ", spotDiscount=" + spotDiscount + ", bgColor="
				+ bgColor + ", currentStock=" + currentStock + ", translatedName=" + translatedName
				+ ", translatedDesc=" + translatedDesc + ", isKotPrint=" + isKotPrint + ", purchasePrice="
				+ purchasePrice + "]";
	}

	

}
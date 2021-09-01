package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

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

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fm_m_food_items_lang_c
 * 
 */
/**
 * @author rajarshi
 * 
 */
@XmlRootElement
@Entity
@ManagedBean(name = "itemLang")
@Table(name = "fm_m_food_items_lang_c")
public class MenuItemLangMap implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private MenuItem menuItem;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "language")
	private String language;

	@Expose
	@Column(name = "Name")
	private String name;

	@Expose
	@Column(name = "Description")
	private String description;

	@Column(name = "promotion_desc")
	private String promotionDesc;

	@Column(name = "food_option1")
	private String foodOption1;

	@Column(name = "food_option2")
	private String foodOption2;

	@Column(name = "language_id")
	private String languageId;

	@Expose
	@Transient
	private String englishName;

	@Expose
	@Transient
	private String englishDesc;

	@Expose
	@Transient
	private List<MenuItemLangMap> itemLangMaps;

	@Expose
	@Transient
	private int itemId;

	private static final long serialVersionUID = 1L;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public List<MenuItemLangMap> getItemLangMaps() {
		return itemLangMaps;
	}

	public void setItemLangMaps(List<MenuItemLangMap> itemLangMaps) {
		this.itemLangMaps = itemLangMaps;
	}

	public String getEnglishDesc() {
		return englishDesc;
	}

	public void setEnglishDesc(String englishDesc) {
		this.englishDesc = englishDesc;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
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

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	@Override
	public String toString() {
		return "MenuItemLangMap [id=" + id + ", menuItem=" + menuItem
				+ ", storeId=" + storeId + ", language=" + language + ", name="
				+ name + ", description=" + description + ", promotionDesc="
				+ promotionDesc + ", foodOption1=" + foodOption1
				+ ", foodOption2=" + foodOption2 + ", languageId=" + languageId
				+ "]";
	}

}
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
 * Entity implementation class for Entity: fm_m_food_items
 * 
 */
/**
 * @author rajarshi
 * 
 */
@XmlRootElement
@Entity
@ManagedBean(name = "categoryTypeLang")
@Table(name = "fm_m_food_types_lang_c")
public class MenuCategoryLangMap implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "food_type_id")
	private MenuCategory menucategory;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "lang")
	private String language;

	@Expose
	@Column(name = "Menu_Item_name")
	private String menuItemName;

	@Expose
	@Column(name = "lang_id")
	private String languageId;

	@Expose
	@Transient
	private String englishName;

	@Expose
	@Transient
	private List<MenuCategoryLangMap> categoryLangMaps;

	@Expose
	@Transient
	private int catSubCatId;

	private static final long serialVersionUID = 1L;

	public int getCatSubCatId() {
		return catSubCatId;
	}

	public void setCatSubCatId(int catSubCatId) {
		this.catSubCatId = catSubCatId;
	}

	public List<MenuCategoryLangMap> getCategoryLangMaps() {
		return categoryLangMaps;
	}

	public void setCategoryLangMaps(List<MenuCategoryLangMap> categoryLangMaps) {
		this.categoryLangMaps = categoryLangMaps;
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

	public MenuCategory getMenucategory() {
		return menucategory;
	}

	public void setMenucategory(MenuCategory menucategory) {
		this.menucategory = menucategory;
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

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	@Override
	public String toString() {
		return "MenuCategoryLangMap [id=" + id + ", menucategory="
				+ menucategory + ", storeId=" + storeId + ", language="
				+ language + ", menuItemName=" + menuItemName + ", languageId="
				+ languageId + "]";
	}

}
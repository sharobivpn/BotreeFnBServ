package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fm_m_food_types
 * 
 */
@XmlRootElement
@Entity
@Table(name = "fm_m_food_types")
@ManagedBean(name = "categoryType")
public class MenuCategory implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "menu_item_name")
	private String menuCategoryName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Parent_item_type_Id")
	private MenuCategory menutype;

	@Expose
	@OneToMany(mappedBy = "menutype", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<MenuCategory> menucategory;

	@Expose
	@OneToMany(mappedBy = "menucategory", fetch = FetchType.EAGER)
	@OrderBy("name ASC")
	@Where(clause = "delete_flag='N'")
	private List<MenuItem> items;

	@Expose
	@Column(name = "CatId")
	private String categoryId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "type")
	private String type;

	@Expose
	@Column(name = "bg_color")
	private String bgColor;

	@Expose
	@Column(name = "direct_cat")
	private String directCat;

	@Expose
	@Column(name = "print_status")
	private String printStatus;

	@Expose
	@Transient
	private int dataFileSerialNo;

	@Expose
	@Transient
	private String parentCatId;

	private static final long serialVersionUID = 1L;

	public String getParentCatId() {
		return parentCatId;
	}

	public void setParentCatId(String parentCatId) {
		this.parentCatId = parentCatId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuCategoryName() {
		return menuCategoryName;
	}

	public void setMenuCategoryName(String menuCategoryName) {
		this.menuCategoryName = menuCategoryName;
	}

	// @JsonIgnore
	public MenuCategory getMenutype() {
		return menutype;
	}

	public void setMenutype(MenuCategory menutype) {
		this.menutype = menutype;
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public void setItems(List<MenuItem> items) {
		this.items = items;
	}

	public List<MenuCategory> getMenucategory() {
		return menucategory;
	}

	public void setMenucategory(List<MenuCategory> menucategory) {
		this.menucategory = menucategory;
	}

	public String getCategoryId() {
		// System.out.println("In getCategoryId");
		int id1 = getId();
		// System.out.println("Category Id ===> " + id1);
		categoryId = Integer.toString(id1);
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getDirectCat() {
		return directCat;
	}

	public void setDirectCat(String directCat) {
		this.directCat = directCat;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

}
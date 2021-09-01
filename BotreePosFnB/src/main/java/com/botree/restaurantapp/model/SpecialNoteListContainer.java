package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class SpecialNoteListContainer implements Serializable {
	@Expose private List<MenuItemNote> menuItemNotes = null;
	@Expose private int itemId;
	@Expose private int storeId;
	@Expose private static final long serialVersionUID = 1L;

	public List<MenuItemNote> getMenuItemNotes() {
		return menuItemNotes;
	}

	public void setMenuItemNotes(List<MenuItemNote> menuItemNotes) {
		this.menuItemNotes = menuItemNotes;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

}
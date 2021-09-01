package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.MenuItemNote;

public interface SpecialNoteDao {
	
	public List<MenuItemNote> getSpecialNoteByFoodItem(String foodItemIds, String storeId)
			throws DAOException;
	
	
	public String addSpecialItem(String foodItemIds, String foodItemNoteId)
			throws DAOException ;

}

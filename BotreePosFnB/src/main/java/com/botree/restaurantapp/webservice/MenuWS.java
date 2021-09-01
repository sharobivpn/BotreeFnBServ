package com.botree.restaurantapp.webservice;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuCategoryLangMap;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.model.SpecialNoteListContainer;
import com.botree.restaurantapp.model.TaxesForAllItems;

public interface MenuWS {

	String getMenu();

	String getMenuByStoreId(String storeId, String language);

	String getMenuItemById(String id, String language);

	String updateMenuItem(MenuItem menuItem);

	String getLanguagesByStore(String storeId);

	String getCategoryByStore(String storeId);

	String getSubCategoryByStore(String storeId, String catId);

	String getMenuItemsByStore(String storeId, String subcatId);

	String addCategory(String name, String storeId, String bgColor);

	String addCategoryPost(MenuCategory category);

	String updateCategory(MenuCategory category);

	String getCategoryDetailsById(String id, String storeId);

	String deleteCategory(MenuCategory category);

	String addSubCategory(String name, String storeId, String bgColor,
			String catId);

	String updateSubCategory(MenuCategory category);

	String deleteSubCategory(MenuCategory category);

	String getSubCategoryDetailsById(String id, String storeId, String catId);

	String addMenuItem(MenuItem item);

	String deleteMenuItem(MenuItem item);

	String getItemDetailsById(Integer id, Integer storeId);

	String updateOrderedItem(String id, String itemId, String orderId,
			String quantity, String changeNote);

	public String getMenuItemsBySubCatName(String id, String name);

	String getSpecialNoteByFoodItem(String foodItemIds, String storeId);

	String addSpecialItem(String foodItemIds, String foodItemNoteId);

	String getMenuByStoreIdAll(String storeId, String language);

	String getMenuSpecialNote(String storeId, String language);

	String getMenuSpecialNoteByItemId(String storeId, String language,
			String itemId);

	String updateSpecialNote(SpecialNoteListContainer noteListContainer);

	String getDirectCategoryDetailsByStore(String storeId) throws DAOException;

	public String updateTaxesForAllItems(TaxesForAllItems taxesForAllItems);

	public String assignPrinterToItem(String itemId, String printerList);

	public String getAllColors();

	public String getSubCategoryForSpecialNote(String storeId);

	public String getMenuItemsAll(String id);

	public String getCategoryByStoreExcludeSpclNote(String id);

	public String getMenuLayoffByStoreId(String storeId, String language);

	public String addSubCategoryPost(MenuCategory category);

	public String getItemByCode(String barcode,
			String storeId,
			String lang);

	public String getTranslatedMenuItem(
			String storeId,
			String language);

	public String getTranslatedMenuCatNSubcat(
			String storeId,
			String language);

	public String addTranslatedMenuItems(MenuItemLangMap itemLangMap);
	public String addTranslatedMenuCatNSubCat(MenuCategoryLangMap categoryLangMap);
	
	public String getDirectCategoryByStore(
			String storeId);
	
	String getRootMenuByStoreId(String storeId);

}

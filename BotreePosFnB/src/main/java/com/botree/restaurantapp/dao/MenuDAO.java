package com.botree.restaurantapp.dao;

import java.io.InputStream;
import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.ColorMaster;
import com.botree.restaurantapp.model.DaySpecial;
import com.botree.restaurantapp.model.MasterLanguage;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuCategoryLangMap;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.SpecialNoteListContainer;
import com.botree.restaurantapp.model.TaxesForAllItems;

public interface MenuDAO {

	public int addItemType(MenuCategory itemType) throws DAOException;

	public int addItem(MenuItem item) throws DAOException;

	public void updateItemType(MenuCategory itemType) throws DAOException;

	public void updateItem(MenuItem item) throws DAOException;

	public List<MenuCategory> getAllItemTypes() throws DAOException;

	public MenuCategory getMenu() throws DAOException;

	public MenuCategory getMenuByStoreId(String storeId, String language)
			throws DAOException;
	
	/*
	 * public MenuCategory getMenuByCategoryAndStoreId(String storeId, String
	 * categoryId, String language) throws DAOException;
	 */

	/* public List<Item> getItemByType (Item_Type itemType); */
	public void deleteItemByType(MenuCategory itemType) throws DAOException;

	public void deleteItem(MenuItem item) throws DAOException;

	public List<MenuItem> getAllItems() throws DAOException;

	public MenuCategory getCategoryById(int categoryId) throws DAOException;

  public List<MenuItem> getMenuItems(Integer storeTd, List<Integer> itemIds) throws DAOException;
  
	public MenuItem getMenuItemById(int itemId) throws DAOException;

	public String updateMenuItem(MenuItem item) throws DAOException;

	public List<MenuItemLangMap> getTranslatedMenuItem(int storeId, String language)
			throws DAOException;
	
	public List<MenuCategoryLangMap> getTranslatedMenuCatNSubcat(int storeId, String language)
			throws DAOException;
	
	public MenuItemLangMap getMenuItemLang(int itemId, String language)
			throws DAOException;

	public List<MasterLanguage> getLanguageListByStoreId(String storeId)
			throws DAOException;

	public List<ColorMaster> getAllColors() throws DAOException;

	public List<MenuCategory> getRootMenu() throws DAOException;

	public List<MenuCategory> getAllSubCategories() throws DAOException;

	public int addSubCategory(MenuCategory itemType) throws DAOException;

	public List<DaySpecial> getDaySpecialbyItemId(int itemId)
			throws DAOException;

	public List<MasterLanguage> getAllLanguages() throws DAOException;

	public List<MenuCategory> getCategoryByStore(String storeId)
			throws DAOException;
	
	public List<MenuCategory> getCategoryByStoreExcludeSpclNote(String storeId)
			throws DAOException;

	public List<MenuCategory> getSubCategoryByStore(String storeId, String catId)
			throws DAOException;

	public List<MenuItem> getMenuItemsByStore(String storeId, String subcatId)
			throws DAOException;

	public List<MenuItem> getMenuItemsBySubCatName(String storeId, String name)
			throws DAOException;

	public String addCategory(String name, String storeId, String bgColor)
			throws DAOException;
	
	public String addCategoryPost(MenuCategory category)
			throws DAOException;

	public String updateCategory(MenuCategory itemType) throws DAOException;

	public MenuCategory getCategoryDetailsById(String id, String storeId)
			throws DAOException;

	public String deleteCategory(MenuCategory itemType) throws DAOException;

	public String addSubCategory(String name, String storeId, String bgColor,
			String catId) throws DAOException;
	
	public String addSubCategoryPost(MenuCategory category) throws DAOException;
	
	public MenuItem getItemByCode(String code, String storeId,String lang)
			throws DAOException;

	public String updateSubCategory(MenuCategory itemType) throws DAOException;

	public String deleteSubCategory(MenuCategory itemType) throws DAOException;

	public MenuCategory getSubCategoryDetailsById(String id, String storeId,
			String catId) throws DAOException;

	public String addMenuItem(MenuItem item) throws DAOException;

	public String deleteMenuItem(MenuItem item) throws DAOException;

	public MenuItem getItemDetailsById(Integer id, Integer storeId)
			throws DAOException;

	public List<MenuItem> getAllItemDetails(int storeId) throws DAOException;

	/*public String updateOrderedItem(String id, String itemId, String orderId,
			String quantity, String changeNote) throws DAOException;*/
	
	public String updateOrderedItem(OrderItem orderItem) throws DAOException;

	public MenuCategory getMenuByStoreIdAll(String storeId, String language)
			throws DAOException;

	public MenuCategory getMenuSpecialNote(String storeId, String language)
			throws DAOException;

	public MenuCategory getMenuSpecialNoteByItemId(String storeId,
			String language, String itemId) throws DAOException;

	public String updateSpecialNote(SpecialNoteListContainer noteListContainer)
			throws DAOException;

	public List<MenuCategory> getDirectCategoryDetailsByStore(String storeId)
			throws DAOException;
	
	public List<MenuCategory> getDirectCategoryByStore(String storeId)
			throws DAOException;

	public String updateTaxesForAllItems(TaxesForAllItems taxesForAllItems)
			throws DAOException;

	public String uploadImageItemPOS(String itemId, String fileName,
			InputStream inputStream) throws Exception;

	public String assignPrinterToItem(String itemId, String printerList)
			throws DAOException;

	public MenuCategory getSubCategoryForSpecialNote(String storeId)
			throws DAOException;
	
	public List<MenuItem> getMenuItemsAll(String storeId)
			throws DAOException;
	
	public String addTranslatedMenuItems(MenuItemLangMap itemLangMap)
			throws DAOException;
	
	public String addTranslatedMenuCatNSubCat(MenuCategoryLangMap categoryLangMap)
			throws DAOException;
	
	public MenuCategory getRootMenuByStoreId(String storeId)
			throws DAOException;
}

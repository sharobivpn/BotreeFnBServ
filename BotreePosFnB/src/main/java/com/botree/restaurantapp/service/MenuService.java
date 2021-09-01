package com.botree.restaurantapp.service;

import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.MenuDAO;
import com.botree.restaurantapp.dao.SpecialNoteDao;
import com.botree.restaurantapp.dao.SpecialNoteDaoImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.ColorMaster;
import com.botree.restaurantapp.model.MasterLanguage;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuCategoryLangMap;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.model.MenuItemNote;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.SpecialNoteListContainer;
import com.botree.restaurantapp.model.TaxesForAllItems;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class MenuService {
	private final static Logger logger = LogManager.getLogger(MenuService.class);
	
	private MenuCategory category_Type = null;
	
  @Autowired
	private MenuDAO menuDAO;
  
  @Autowired
	private SpecialNoteDao specialNoteDao;

	public MenuService() {

	}

	/*
	 * public void addItemType(MenuCategory itemType) throws ServiceException {
	 * try {
	 * 
	 * System.out.println("Enter MenuService.addItemType "); // create a new
	 * item type menuDAO.addItemType(itemType);
	 * System.out.println("exit MenuService.addItemType ");
	 * 
	 * } catch (DAOException e) {
	 * 
	 * throw new ServiceException(
	 * "problem occurred while trying to add a new item type", e);
	 * 
	 * }
	 * 
	 * }
	 */

	public int addItemType(MenuCategory itemType) throws ServiceException {

		int maxCatId;
		try {

			System.out.println("Enter MenuService.addItemType ");
			// create a new item type
			maxCatId = menuDAO.addItemType(itemType);
			System.out.println("exit MenuService.addItemType ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to add a new item type", e);

		}

		return maxCatId;

	}

	public int addSubCategory(MenuCategory itemType) throws ServiceException {

		int maxCatId;
		try {

			System.out.println("Enter MenuService.addSubCategory ");
			// create a new item type
			maxCatId = menuDAO.addSubCategory(itemType);
			System.out.println("exit MenuService.addSubCategory ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to add a new addSubCategory",
					e);

		}

		return maxCatId;

	}

	public int addItem(MenuItem item) throws ServiceException {

		int maxItemId;

		try {

			System.out.println("Enter MenuService.addItem ");
			// create a new item
			maxItemId = menuDAO.addItem(item);
			System.out.println("exit MenuService.addItem ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to add a new item ", e);

		}

		return maxItemId;

	}

	public void updateItemType(MenuCategory itemType) throws ServiceException {
		try {

			System.out.println("Enter MenuService.updateItemType ");
			// update item type
			menuDAO.updateItemType(itemType);
			System.out.println("exit MenuService.updateItemType ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update an item type", e);

		}

	}

	public void updateItem(MenuItem item) throws ServiceException {
		try {

			System.out.println("Enter MenuService.updateItem ");
			// update an item
			menuDAO.updateItem(item);
			System.out.println("exit MenuService.updateItem ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to update an item ", e);

		}

	}

	public List<MenuCategory> getAllItemTypes() throws ServiceException {

		List<MenuCategory> category_Types = null;
		try {

			System.out.println("Enter MenuService.getAllItemTypes ");
			// get all item types
			category_Types = menuDAO.getAllItemTypes();
			System.out.println("exit MenuService.getAllItemTypes ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to get all item types ", e);

		}

		return category_Types;
	}

	public List<MenuCategory> getAllSubCategories() throws ServiceException {

		List<MenuCategory> sub_category_Types = null;
		try {

			System.out.println("Enter MenuService.getAllSubCategories ");
			// get all item types
			sub_category_Types = menuDAO.getAllSubCategories();
			System.out.println("exit MenuService.getAllSubCategories ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to get all item types ", e);

		}

		return sub_category_Types;
	}

	public List<MenuCategory> getRootMenu() throws ServiceException {

		List<MenuCategory> rootMenu = null;
		try {

			System.out.println("Enter MenuService.getRootMenu ");
			// get all item types
			rootMenu = menuDAO.getRootMenu();
			System.out.println("exit MenuService.getRootMenu ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to get all item types ", e);

		}

		return rootMenu;
	}

	public MenuCategory getMenu() throws ServiceException {

		MenuCategory category_Type = null;
		try {

			System.out.println("Enter MenuService.getMenu ");
			// get all menu
			category_Type = menuDAO.getMenu();
			System.out.println("exit MenuService.getMenu ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all menu ", e);

		}

		return category_Type;
	}

	public MenuCategory getMenuByStoreId(String storeId, String language)
			throws ServiceException {

		MenuCategory category_Type = null;
		try {

			// System.out.println("Enter MenuService.getMenuByStoreId ");
			// get all menu
			category_Type = menuDAO.getMenuByStoreId(storeId, language);
			// System.out.println("exit MenuService.getMenuByStoreId ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all menu by store ID ",
					e);

		}

		return category_Type;
	}
	
	
	public MenuCategory getMenuByStoreIdAll(String storeId, String language)
			throws ServiceException {

		MenuCategory category_Type = null;
		try {

			System.out.println("Enter MenuService.getMenuByStoreIdAll ");
			// get all menu
			category_Type = menuDAO.getMenuByStoreIdAll(storeId, language);
			System.out.println("exit MenuService.getMenuByStoreIdAll ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all menu by store ID ",
					e);

		}

		return category_Type;
	}

	public MenuCategory getMenuSpecialNote(String storeId, String language)
			throws ServiceException {

		MenuCategory category_Type = null;
		try {

			category_Type = menuDAO.getMenuSpecialNote(storeId, language);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all menu by store ID ",
					e);

		}

		return category_Type;
	}

	public MenuCategory getMenuSpecialNoteByItemId(String storeId,
			String language, String itemId) throws ServiceException {

		MenuCategory category_Type = null;
		try {

			category_Type = menuDAO.getMenuSpecialNoteByItemId(storeId,
					language, itemId);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all menu by store ID ",
					e);

		}

		return category_Type;
	}

  public List<MenuItem> getMenuItems(Integer storedId, List<Integer> ids) throws ServiceException {
    List<MenuItem> menuItems = null;

    try {
      System.out.println("Enter MenuService.getMenuItems ");
      menuItems = menuDAO.getMenuItems(storedId, ids);
      System.out.println("exit MenuService.getMenuItems ");
      logger.debug("Successfull: {}", menuItems);
    } catch (DAOException e) {
      logger.error("Ooops ...", e);
      throw new ServiceException(
          "Problem occurred while trying to get all menu item by IDs ", e);
    }
    
    return menuItems;
  }

	public MenuItem getMenuItemById(int id) throws ServiceException {
		MenuItem menuItem = null;

		try {
			System.out.println("Enter MenuService.getMenuItemById ");
			menuItem = menuDAO.getMenuItemById(id);
			System.out.println("exit MenuService.getMenuItemById ");
			logger.debug("Successfull: {}", menuItem);
		} catch (DAOException e) {
			logger.error("Ooops ...", e);
			throw new ServiceException(
					"problem occurred while trying to get all menu item by ID ",
					e);
		}
		return menuItem;
	}

	public MenuItemLangMap getMenuItemLang(int id, String language)
			throws ServiceException {
		MenuItemLangMap menuItem = null;

		try {
			System.out.println("Enter MenuService.getMenuItemLang ");
			menuItem = menuDAO.getMenuItemLang(id, language);
			System.out.println("exit MenuService.getMenuItemLang ");
			logger.debug("Successfull: {}", menuItem);
		} catch (DAOException e) {
			logger.error("Ooops ...", e);
			throw new ServiceException(
					"problem occurred while trying to get all menu item lang by ID ",
					e);
		}
		return menuItem;
	}
	
	public List<MenuItemLangMap> getTranslatedMenuItem(int id, String language)
			throws ServiceException {
		List<MenuItemLangMap> itemLangMaps = null;

		try {
			itemLangMaps = menuDAO.getTranslatedMenuItem(id, language);
		
		} catch (DAOException e) {
			logger.error("Ooops ...", e);
			throw new ServiceException(
					"problem occurred while trying to get all menu item lang by ID ",
					e);
		}
		return itemLangMaps;
	}
	
	public List<MenuCategoryLangMap> getTranslatedMenuCatNSubcat(int id, String language)
			throws ServiceException {
		List<MenuCategoryLangMap> catLangMaps = null;

		try {
			catLangMaps = menuDAO.getTranslatedMenuCatNSubcat(id, language);
		
		} catch (DAOException e) {
			logger.error("Ooops ...", e);
			throw new ServiceException(
					"problem occurred while trying to get all menu item lang by ID ",
					e);
		}
		return catLangMaps;
	}

	public void deleteItemByType(MenuCategory itemType) throws ServiceException {
		try {

			System.out.println("Enter MenuService.deleteItemByType ");
			// delete item type
			menuDAO.deleteItemByType(itemType);
			System.out.println("exit MenuService.deleteItemByType ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to delete an item type", e);

		}

	}

	public void deleteItem(MenuItem item) throws ServiceException {
		try {

			System.out.println("Enter MenuService.deleteItem ");
			// delete item type
			menuDAO.deleteItem(item);
			System.out.println("exit MenuService.deleteItem ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to delete an item ", e);

		}

	}

	public List<MenuItem> getAllItems() throws ServiceException {

		List<MenuItem> items = null;
		try {

			System.out.println("Enter MenuService.getAllItems ");
			// get all items
			items = menuDAO.getAllItems();
			System.out.println("exit MenuService.getAllItems ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to get all item types ", e);

		}

		return items;
	}

	public List<MasterLanguage> getAllLanguages() throws ServiceException {

		List<MasterLanguage> languages = null;
		try {

			System.out.println("Enter MenuService.getAllLanguages ");
			// get all languages
			languages = menuDAO.getAllLanguages();
			System.out.println("exit MenuService.getAllLanguages ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to get all languages ", e);

		}

		return languages;
	}

	public MenuCategory getCategoryById(int categoryId) throws ServiceException {
		try {
			System.out.println("Enter MenuService.getCategoryById");
			category_Type = menuDAO.getCategoryById(categoryId);
			System.out.println("Exit MenuService.getCategoryById");
		} catch (DAOException e) {
			System.out.println("in Menuservice Exception");
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all Categories by categoryId",
					e);

		}
		return category_Type;

	}

	public String updateMenuItem(MenuItem item) throws ServiceException {

		String status = "";
		try {
			System.out.println("Enter MenuService.updateMenuItem ");
			// Update an item
			status = menuDAO.updateMenuItem(item);
			System.out.println("exit MenuService.updateMenuItem ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update an item ", e);
		}
		return status;
	}

	public String updateSpecialNote(SpecialNoteListContainer noteListContainer)
			throws ServiceException {

		String status = "";
		try {
			status = menuDAO.updateSpecialNote(noteListContainer);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update an item ", e);
		}
		return status;
	}

	public String addCategory(String name, String storeId, String bgColor)
			throws ServiceException {

		String status;
		try {

			System.out.println("Enter MenuService.updateMenuItem ");
			// update an item
			status = menuDAO.addCategory(name, storeId, bgColor);
			System.out.println("exit MenuService.updateMenuItem ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to addCategory ", e);

		}
		return status;

	}
	
	public String addCategoryPost(MenuCategory category)
			throws ServiceException {

		String status;
		try {

			System.out.println("Enter MenuService.addCategoryPost ");
			// update an item
			status = menuDAO.addCategoryPost(category);
			System.out.println("exit MenuService.addCategoryPost ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to addCategory ", e);

		}
		return status;

	}
	
	public String addTranslatedMenuItems(MenuItemLangMap itemLangMap)
			throws ServiceException {

		String status;
		try {

			// update an item
			status = menuDAO.addTranslatedMenuItems(itemLangMap);
			
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to addCategory ", e);

		}
		return status;

	}
	
	public String addTranslatedMenuCatNSubCat(MenuCategoryLangMap categoryLangMap)
			throws ServiceException {

		String status;
		try {

			// update an item
			status = menuDAO.addTranslatedMenuCatNSubCat(categoryLangMap);
			
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to addCategory ", e);

		}
		return status;

	}

	public String addSubCategory(String name, String storeId, String bgColor,
			String catId) throws ServiceException {

		String status;
		try {
			// update an item
			status = menuDAO.addSubCategory(name, storeId, bgColor, catId);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to addSubCategory ", e);

		}
		return status;

	}
	
	public String addSubCategoryPost(MenuCategory category) throws ServiceException {

		String status;
		try {
			// update an item
			status = menuDAO.addSubCategoryPost(category);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to addSubCategory ", e);

		}
		return status;

	}

	public List<MasterLanguage> getLanguageListByStoreId(String storeId)
			throws ServiceException {

		List<MasterLanguage> languageList = null;
		try {
			//System.out.println("Enter MenuService.getLanguageListByStoreId ");
			languageList = menuDAO.getLanguageListByStoreId(storeId);
			//System.out.println("exit MenuService.getLanguageListByStoreId ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getLanguages by storeId ",
					e);
		}
		return languageList;
	}

	public List<ColorMaster> getAllColors() throws ServiceException {

		List<ColorMaster> colorList = null;
		try {

			colorList = menuDAO.getAllColors();

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getLanguages by storeId ",
					e);
		}
		return colorList;
	}

	public List<MenuCategory> getCategoryByStore(String storeId)
			throws ServiceException {

		List<MenuCategory> catList = null;
		try {

			catList = menuDAO.getCategoryByStore(storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getCategoryByStore by storeId ",
					e);
		}
		return catList;
	}
	
	public List<MenuCategory> getCategoryByStoreExcludeSpclNote(String storeId)
			throws ServiceException {

		List<MenuCategory> catList = null;
		try {

			catList = menuDAO.getCategoryByStoreExcludeSpclNote(storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getCategoryByStore by storeId ",
					e);
		}
		return catList;
	}

	public MenuCategory getCategoryDetailsById(String id, String storeId)
			throws ServiceException {

		MenuCategory category = null;
		try {

			category = menuDAO.getCategoryDetailsById(id, storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getCategoryDetailsById by storeId and id ",
					e);
		}
		return category;
	}

	public List<MenuCategory> getDirectCategoryDetailsByStore(String storeId)
			throws ServiceException {

		List<MenuCategory> category = null;
		try {

			category = menuDAO.getDirectCategoryDetailsByStore(storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getDirectCategoryDetailsByStore by storeId and id ",
					e);
		}
		return category;
	}
	
	public List<MenuCategory> getDirectCategoryByStore(String storeId)
			throws ServiceException {

		List<MenuCategory> category = null;
		try {

			category = menuDAO.getDirectCategoryByStore(storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getDirectCategoryDetailsByStore by storeId and id ",
					e);
		}
		return category;
	}

	public List<MenuCategory> getSubCategoryByStore(String storeId, String catId)
			throws ServiceException {

		List<MenuCategory> subCatList = null;
		try {

			subCatList = menuDAO.getSubCategoryByStore(storeId, catId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getSubCategoryByStore by storeId ",
					e);
		}
		return subCatList;
	}

	public List<MenuItem> getMenuItemsByStore(String storeId, String subcatId)
			throws ServiceException {

		List<MenuItem> itemList = null;
		try {

			itemList = menuDAO.getMenuItemsByStore(storeId, subcatId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getMenuItemsByStore by storeId ",
					e);
		}
		return itemList;
	}

	public List<MenuItem> getMenuItemsBySubCatName(String storeId, String name)
			throws ServiceException {

		List<MenuItem> itemList = null;
		try {

			itemList = menuDAO.getMenuItemsBySubCatName(storeId, name);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getMenuItemsByStore by storeId ",
					e);
		}
		return itemList;
	}

	public String updateCategory(MenuCategory category) throws ServiceException {
		String status = "";
		try {

			// update item type
			status = menuDAO.updateCategory(category);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update an category", e);

		}
		return status;

	}

	public String deleteCategory(MenuCategory category) throws ServiceException {
		String status = "";
		try {

			// update item type
			status = menuDAO.deleteCategory(category);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update an category", e);

		}
		return status;

	}

	public String updateSubCategory(MenuCategory category)
			throws ServiceException {
		String status = "";
		try {

			// update sub category
			status = menuDAO.updateSubCategory(category);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to updateSubCategory", e);

		}
		return status;

	}

	public String deleteSubCategory(MenuCategory category)
			throws ServiceException {
		String status = "";
		try {

			// update item type
			status = menuDAO.deleteSubCategory(category);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update an category", e);

		}
		return status;

	}

	public MenuCategory getSubCategoryDetailsById(String id, String storeId,
			String catId) throws ServiceException {

		MenuCategory category = null;
		try {
			category = menuDAO.getSubCategoryDetailsById(id, storeId, catId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getSubCategoryDetailsById by storeId and id ",
					e);
		}
		return category;
	}
	
	public MenuCategory getSubCategoryForSpecialNote(String storeId) throws ServiceException {

		MenuCategory category = null;
		try {
			category = menuDAO.getSubCategoryForSpecialNote(storeId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getSubCategoryDetailsById by storeId and id ",
					e);
		}
		return category;
	}

	public String addMenuItem(MenuItem item) throws ServiceException {
		String status = "";
		try {

			// add new item
			status = menuDAO.addMenuItem(item);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to addMenuItem", e);

		}
		return status;

	}

	public String updateTaxesForAllItems(TaxesForAllItems taxesForAllItems)
			throws ServiceException {
		String status = "";
		try {

			// add new TaxesForAllItems
			status = menuDAO.updateTaxesForAllItems(taxesForAllItems);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to addMenuItem", e);

		}
		return status;

	}

	public String uploadImageItemPOS(String itemId, String fileName,
			InputStream inputStream) throws Exception {
		String status = "";
		try {

			status = menuDAO.uploadImageItemPOS(itemId, fileName, inputStream);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to addMenuItem", e);

		}
		return status;

	}

	public String deleteMenuItem(MenuItem item) throws ServiceException {

		String status = "";
		try {
			// delete item
			status = menuDAO.deleteMenuItem(item);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to delete an item ", e);

		}
		return status;

	}

	public MenuItem getItemDetailsById(Integer id, Integer storeId)
			throws ServiceException {

		MenuItem item = null;
		try {
			item = menuDAO.getItemDetailsById(id, storeId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getItemDetailsById by storeId and id ",
					e);
		}
		return item;
	}
	
	public MenuItem getItemByCode(String code, String storeId,String lang)
			throws ServiceException {
		MenuItem menuItem = null;

		try {
			menuItem = menuDAO.getItemByCode(code, storeId,lang);
			logger.debug("Successfull: {}", menuItem);
		} catch (DAOException e) {
			logger.error("Ooops ...", e);
			throw new ServiceException(
					"problem occurred while trying to get all menu item by ID ",
					e);
		}
		return menuItem;
	}

	public String updateOrderedItem(OrderItem orderItem) throws ServiceException {
		String status = "";
		try {

			// update ordered item
			status = menuDAO.updateOrderedItem(orderItem);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update order item", e);

		}
		return status;

	}

	public List<MenuItemNote> getSpecialNoteByFoodItem(String foodItemIds,
			String storeId) throws ServiceException {
		List<MenuItemNote> menuItemNoteList = null;

		specialNoteDao = new SpecialNoteDaoImpl();
		try {

			menuItemNoteList = specialNoteDao.getSpecialNoteByFoodItem(
					foodItemIds, storeId);

		} catch (DAOException e) {
			logger.error("Ooops ...", e);
			throw new ServiceException(
					"problem occurred while trying to get all menu item by foodItemId ",
					e);
		}
		return menuItemNoteList;
	}

	public String addSpecialItem(String foodItemIds, String foodItemNoteId)
			throws Exception {

		String status;
		try {

			System.out.println("Enter MenuService.updateSpecialItem ");
			// update an item
			status = specialNoteDao.addSpecialItem(foodItemIds, foodItemNoteId);

			System.out.println("exit MenuService.updateSpecialItem ");

		} catch (DAOException e) {
			throw new Exception("problem occurred while trying to add ", e);

		}
		return status;

	}

	public String assignPrinterToItem(String itemId, String printerList)
			throws Exception {

		String status;
		try {
			// update an item
			status = menuDAO.assignPrinterToItem(itemId, printerList);

		} catch (DAOException e) {
			throw new Exception("problem occurred while trying to add ", e);

		}
		return status;

	}
	
	public List<MenuItem> getMenuItemsAll(String storeId)
			throws ServiceException {

		List<MenuItem> itemList = null;
		try {

			itemList = menuDAO.getMenuItemsAll(storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to getMenuItemsByStore by storeId ",
					e);
		}
		return itemList;
	}
	
	public MenuCategory getRootMenuByStoreId(String storeId)
			throws ServiceException {

		MenuCategory category_Type = null;
		try {
			category_Type = menuDAO.getRootMenuByStoreId(storeId);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get root menu by store ID ",
					e);

		}

		return category_Type;
	}

	public MenuCategory getCategory_Type() {
		return category_Type;
	}

	public void setCategory_Type(MenuCategory category_Type) {
		this.category_Type = category_Type;
	}

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

}

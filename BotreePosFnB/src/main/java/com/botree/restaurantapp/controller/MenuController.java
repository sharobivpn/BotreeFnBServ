package com.botree.restaurantapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.model.MasterLanguage;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.service.MenuService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class MenuController {

	private MenuCategory categoryType = null;
	private MenuService menuService = null;
	private MenuItem item = null;
	private List<MenuCategory> categoryList = null;
	private List<MenuItem> itemList = null;

	private boolean showCatAddPane = false;
	private boolean showItemAddPane = false;
	private List<SelectItem> selectCategories = new ArrayList<SelectItem>();
	private List<SelectItem> selectCategors = new ArrayList<SelectItem>();
	private List<SelectItem> selectsubCategors = new ArrayList<SelectItem>();
	private List<MenuCategory> catList = new ArrayList<MenuCategory>();
	private List<MenuCategory> cateList = new ArrayList<MenuCategory>();
	private List<MenuCategory> subcateList = new ArrayList<MenuCategory>();
	private MenuCategory menuCategory;
	private MenuItem menuItem;
	private Part part;
	private SelectItem selectItem;

	private static String dbImageUrl = ""; // "http://192.168.1.100:9999/Restaurant/rest/image/fooditem/get?name=121";
	private static String catImageUrl = "";
	private Map<Integer, Boolean> select = new HashMap<Integer, Boolean>();
	private List<MasterLanguage> langList = null;
	private MasterLanguage language = null;
	private MenuItemLangMap menuItemLangMap = new MenuItemLangMap();

	public Map<Integer, Boolean> getSelect() {
		return select;
	}

	public void setSelect(Map<Integer, Boolean> select) {
		this.select = select;
	}

	public String getCatImageUrl() {
		return catImageUrl;
	}

	public void setCatImageUrl(String catImageUrl) {
		// MenuController.catImageUrl = catImageUrl;
	}

	public String getDbImageUrl() {
		return dbImageUrl;
	}

	public void setDbImageUrl(String dbImageUrl) {
		// this.dbImageUrl = dbImageUrl;
	}

	private String imageFilename = "D:\\photosss\\three.jpg";

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		// this.imageFilename = imageFilename;
	}

	public MenuController() {

	}

	@PostConstruct
	public void postConstruct() {

		try {

			System.out.println("Enter getCategories");
			System.out.println("select items size:" + selectCategories.size());

			cateList = menuService.getAllItemTypes();
			subcateList = menuService.getAllSubCategories();
			catList = menuService.getRootMenu();

			// System.out.println("number of categoreies:" + catList.size());
			Iterator<MenuCategory> iterator = catList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				MenuCategory menuCat = (MenuCategory) iterator.next();

				si.setValue(menuCat.getId());
				// System.out.println("cat id" + menuCat.getId());
				si.setLabel(menuCat.getMenuCategoryName());
				// System.out.println("categories: " +
				// menuCat.getMenuCategoryName());

				selectCategories.add(si);

			}
			Iterator<MenuCategory> catiterator = cateList.iterator();
			while (catiterator.hasNext()) {
				SelectItem si = new SelectItem();

				MenuCategory menuCat = (MenuCategory) catiterator.next();

				si.setValue(menuCat.getId());
				// System.out.println("cat id" + menuCat.getId());
				si.setLabel(menuCat.getMenuCategoryName());
				// System.out.println("categories: " +
				// menuCat.getMenuCategoryName());

				selectCategors.add(si);

			}

			Iterator<MenuCategory> subCatiterator = subcateList.iterator();
			while (subCatiterator.hasNext()) {
				SelectItem si = new SelectItem();

				MenuCategory menuCat = (MenuCategory) subCatiterator.next();

				si.setValue(menuCat.getId());
				// System.out.println("cat id" + menuCat.getId());
				si.setLabel(menuCat.getMenuCategoryName());
				// System.out.println("categories: " +
				// menuCat.getMenuCategoryName());

				selectsubCategors.add(si);

			}
			// code to get all menu items
			getAllMenuItems();
			// code to get all languages by store id
			getAllLanguages();

		} catch (Exception e) {
			// throw new DAOException("Check data to be inserted", e);
			e.printStackTrace();
			System.out.println("test8");
		}

	}

	public String translateItems() {
		System.out.println("In translateItems");
		language.getLanguage();

		/*
		 * try { for (OrderMaster order : orderLists) { if
		 * (select.get(order.getId()).booleanValue()) {
		 * System.out.println("OrderController.updateDeliveryStatus1111");
		 * orderService.updateDeliveryStatus(order);
		 * System.out.println("OrderController.updateDeliveryStatus222222"); } }
		 * System.out.println("menucontroller.translateItems"); select.clear();
		 * } catch (ServiceException e) {
		 * 
		 * e.printStackTrace(); }
		 */
		System.out.println("Out translateItems");

		return "";

	}

	public void getTranslatedItems() {

		language.getLanguage();

		// return null;

	}

	public String showCategoryPane() {
		System.out.println(" in showCategoryPane");
		menuCategory = new MenuCategory();
		System.out.println("show cat pane:" + showCatAddPane);
		showCatAddPane = true;
		return "";

	}

	public String showItemPane() {
		System.out.println(" in showItemPane");

		menuItem = new MenuItem();
		System.out.println("show item pane:" + showItemAddPane);
		showItemAddPane = true;
		return "";

	}

	public String editCategory() {
		CommonProerties commonProerties = new CommonProerties();
		String redirect = "";
		System.out.println("In editCategory");
		System.out.println("parent cate id:  "
				+ menuCategory.getMenutype().getId());
		System.out.println("cat name:  " + menuCategory.getMenuCategoryName());
		catImageUrl = commonProerties.getMainServerUrl()
				+ "/rest/image/get?name=" + menuCategory.getId();
		redirect = "edit_category";

		// return "/page/edit_category.xhtml?faces-redirect=true";
		return redirect;
	}

	public String editSubCategory() {
		CommonProerties commonProerties = new CommonProerties();
		String redirect = "";
		System.out.println("In editSubCategory");
		System.out.println("parent cate id:  "
				+ menuCategory.getMenutype().getId());
		System.out.println("cat name:  " + menuCategory.getMenuCategoryName());
		catImageUrl = commonProerties.getMainServerUrl()
				+ "/rest/image/get?name=" + menuCategory.getId();
		redirect = "edit_subcategory";

		return "/page/edit_subcategory.xhtml?faces-redirect=true";
		// return redirect;
	}

	/*
	 * @PostConstruct public void getMenuCategory() {
	 * 
	 * try {
	 * 
	 * System.out.println("Enter MenuController.getMainCategory "); // get all
	 * menu categoryList = menuService.getAllItemTypes();
	 * System.out.println("No. of Categories :" + categoryList.size());
	 * System.out.println("exit MenuController.getMainCategory ");
	 * 
	 * System.out.println("Enter MenuController.getAllMenuItems ");
	 * getAllMenuItems();
	 * System.out.println("Exit MenuController.getAllMenuItems ");
	 * 
	 * } catch (ServiceException e) {
	 * 
	 * e.printStackTrace(); System.out.println("In menuController Exception"); }
	 * 
	 * //return "";
	 * 
	 * }
	 */

	public void addItemType() throws ServiceException {
		try {
			System.out.println("Enter MenuController.addItemType ");
			// create a new item type
			menuService.addItemType(categoryType);
			System.out.println("exit MenuController.addItemType ");
		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occurred while trying to add a new item type", e);
		}

	}

	/*
	 * public void updateItemType() throws ServiceException { try {
	 * System.out.println("Enter MenuController.updateItemType "); // update
	 * item type menuService.updateItemType(categoryType);
	 * System.out.println("exit MenuController.updateItemType "); } catch
	 * (ServiceException e) { throw new
	 * ServiceException("problem occurred while trying to add a new item type",
	 * e); }
	 * 
	 * }
	 */

	public String updateItemType() throws ServiceException {

		System.out.println("Enter MenuController.updateItemType ");
		int catId = menuCategory.getId();
		System.out.println("cat id :" + catId);
		System.out.println("cat name :" + menuCategory.getMenuCategoryName());
		System.out.println("parent cat id :"
				+ menuCategory.getMenutype().getId());
		try {

			System.out.println("Enter MenuController.updateItemType ");
			// update item type
			menuService.updateItemType(menuCategory);
			System.out.println("exit MenuController.updateItemType ");
			// upload image
			uploadImage(catId);

		} catch (ServiceException e) {

			throw new ServiceException(
					"problem occurred while trying to update item type", e);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In update category IOException");
			e.printStackTrace();
		}
		return "/page/disp_categories.xhtml?faces-redirect=true";

	}

	public String updateSubMenuCategory() throws ServiceException {

		System.out.println("Enter MenuController.updateItemType ");
		int catId = menuCategory.getId();
		System.out.println("cat id :" + catId);
		System.out.println("cat name :" + menuCategory.getMenuCategoryName());
		System.out.println("parent cat id :"
				+ menuCategory.getMenutype().getId());
		try {

			System.out.println("Enter MenuController.updateItemType ");
			// update item type
			menuService.updateItemType(menuCategory);
			System.out.println("exit MenuController.updateItemType ");
			// upload image
			uploadImage(catId);

		} catch (ServiceException e) {

			throw new ServiceException(
					"problem occurred while trying to update item type", e);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In update category IOException");
			e.printStackTrace();
		}
		return "/page/disp_subcategories.xhtml?faces-redirect=true";

	}

	public String uploadImage(int maxCatId) throws IOException {
		System.out.println("enter uploadImage");
		// Extract file name from content-disposition header of file part
		if (part != null) {
			String fileName = getFileName(part);
			System.out.println("***** fileName: " + fileName);
			String[] parts = fileName.split("\\.");

			String beforeDot = parts[0];
			String afterDot = parts[1];

			String changedFileName = "" + maxCatId + "." + afterDot;
			// String basePath = "C:" + File.separator + "Program Files (x86)" +
			// File.separator + "Apache Software Foundation" + File.separator +
			// "Tomcat 7.0" + File.separator + "webapps" + File.separator +
			// "images" + File.separator;
			String basePath = "/home/ubuntu/.resturant/images";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				System.out
						.println("menucontroller:windows machine: upload image: ");
				basePath = "C:/restaurantImages/menu";
			}
			// String basePath = "C:" +
			// File.separator+"Program Files (x86)"+File.separator +
			// "images"+File.separator;
			// String basePath = "D:" + File.separator+"temp"+File.separator;
			System.out.println("basePath :" + basePath);
			File outputFilePath = new File(basePath + "/" + changedFileName);

			// Copy uploaded file to destination path
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = part.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);

				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				// statusMessage = "File upload successful !!";
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("In uploadFile exception");
				// statusMessage = "File upload failed !!";
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return null; // return to same page
	}

	public String uploadImageItem(int maxItemId) throws IOException {
		System.out.println("enter uploadImageItem");
		// Extract file name from content-disposition header of file part
		if (part != null) {
			String fileName = getFileName(part);
			System.out.println("***** fileName: " + fileName);
			String[] parts = fileName.split("\\.");

			String beforeDot = parts[0];
			String afterDot = parts[1];
			// String extension=afterDot;

			String changedFileName = "" + maxItemId + "." + afterDot;
			// String basePath = "C:" + File.separator + "Program Files (x86)" +
			// File.separator + "Apache Software Foundation" + File.separator +
			// "Tomcat 7.0" + File.separator + "webapps" + File.separator +
			// "images" + File.separator;
			String basePath = "/home/ubuntu/.resturant/fooditems";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				System.out
						.println("menucontroller:windows machine: upload image: ");
				basePath = "C:/restaurantImages/fooditems";
			}
			// String basePath = "C:" +
			// File.separator+"Program Files (x86)"+File.separator +
			// "images"+File.separator;
			// String basePath = "D:" + File.separator+"temp"+File.separator;
			System.out.println("basePath :" + basePath);
			File outputFilePath = new File(basePath + "/" + changedFileName);

			// Copy uploaded file to destination path
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = part.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);

				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				// statusMessage = "File upload successful !!";
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("In uploadFile exception");
				// statusMessage = "File upload failed !!";
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return null; // return to same page
	}
	
	

	private String getFileName(Part part) {
		try {

			final String partHeader = part.getHeader("content-disposition");
			System.out.println("***** partHeader: " + partHeader);
			for (String content : part.getHeader("content-disposition").split(
					";")) {
				if (content.trim().startsWith("filename")) {
					return content.substring(content.indexOf('=') + 1).trim()
							.replace("\"", "");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("In getFileName exception");

		}
		return null;
	}

	/*
	 * public String deleteItemByType() throws ServiceException { try {
	 * System.out.println("Enter MenuController.deleteItemByType "); // delete
	 * item type menuService.deleteItemByType(categoryType);
	 * System.out.println("exit MenuController.deleteItemByType "); } catch
	 * (ServiceException e) { throw new
	 * ServiceException("problem occurred while trying to delete an item type",
	 * e); }
	 * 
	 * return "/page/disp_categories.xhtml?faces-redirect=true"; }
	 */

	public String deleteItemByType() throws ServiceException {
		System.out.println("cat id: " + menuCategory.getId());

		try {

			System.out.println("Enter MenuController.deleteItemByType ");
			System.out.println("cat id: " + menuCategory.getId());

			// delete item type
			menuService.deleteItemByType(menuCategory);
			System.out.println("exit MenuController.deleteItemByType ");

		} catch (ServiceException e) {

			throw new ServiceException(
					"problem occurred while trying to delete an item type", e);

		}

		return "/page/disp_categories.xhtml?faces-redirect=true";

	}

	public String addItem() throws ServiceException {
		try {
			System.out.println("Enter MenuController.addItem ");

			/*
			 * System.out.println("name: " + item.getName());
			 * System.out.println("desc: " + item.getDescription());
			 * System.out.println("price: " + item.getPrice());
			 * System.out.println("parent: " + item.getMenucategory().getId());
			 */
			System.out.println("name: " + menuItem.getName());
			System.out.println("desc: " + menuItem.getDescription());
			System.out.println("price: " + menuItem.getPrice());
			System.out.println("parent: " + menuItem.getMenucategory().getId());
			// create a new item
			// menuService.addItem(item);
			int maxItemId = menuService.addItem(menuItem);

			// upload image for Item
			uploadImageItem(maxItemId);
			System.out.println("exit MenuController.addItem ");
		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occurred while trying to add a new item ", e);
		} catch (IOException e) {
			System.out.println("In add item IOException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/page/disp_menuitems.xhtml?faces-redirect=true";
	}

	public String editMenuItems() {
		System.out.println("in editMenuItems");
		CommonProerties commonProerties = new CommonProerties();
		int itemId = item.getId();
		dbImageUrl = commonProerties.getMainServerUrl()
				+ "/rest/image/fooditem/get?name=" + itemId;
		System.out.println(dbImageUrl);
		return "/page/edit_menuitems.xhtml?faces-redirect=true";
	}

	public String updateItem() throws ServiceException {
		try {
			int itemId = item.getId();
			System.out.println("updateItem:: " + itemId);
			System.out.println("updateItem:: promotion value:  "
					+ item.getPromotionValue());
			System.out.println("Enter MenuController.updateItem ");
			// update an item
			menuService.updateItem(item);

			// upload image
			uploadImageItem(itemId);
			System.out.println("exit MenuController.updateItem ");
		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occurred while trying to update an item ", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In update category IOException");
			e.printStackTrace();
		}
		return "/page/disp_menuitems.xhtml?faces-redirect=true";
	}

	public String deleteItem() throws ServiceException {
		try {
			System.out.println("Enter MenuService.deleteItem ");
			// delete item
			menuService.deleteItem(item);
			System.out.println("exit MenuService.deleteItem ");
		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occurred while trying to delete an item ", e);
		}
		return "/page/disp_menuitems.xhtml?faces-redirect=true";
	}

	/*
	 * public List<MenuCategory> getAllItemTypes() throws ServiceException {
	 * 
	 * 
	 * try {
	 * 
	 * System.out.println("Enter MenuController.getAllItemTypes "); // get all
	 * item types categoryList = menuService.getAllItemTypes();
	 * System.out.println("exit MenuController.getAllItemTypes ");
	 * 
	 * } catch (ServiceException e) {
	 * 
	 * throw new ServiceException(
	 * "problem occurred while trying to get all item types ", e);
	 * 
	 * }
	 * 
	 * return categoryList; }
	 */

	public MenuCategory getMenu() throws ServiceException {

		MenuCategory categoryType = null;
		try {

			System.out.println("Enter MenuController.getMenu ");
			// get all menu
			categoryType = menuService.getMenu();
			System.out.println("exit MenuController.getMenu ");

		} catch (ServiceException e) {

			throw new ServiceException(
					"problem occurred while trying to get all menu ", e);

		}

		return categoryType;
	}

	public String dispMenuCategory() {
		System.out.println("in dispMenuCategory");
		return "disp_menu_category";

	}

	public void getAllMenuItems() {

		try {
			System.out.println("Enter getAllMenuItems method");

			itemList = menuService.getAllItems();

			System.out.println("number of items:" + itemList.size());

			System.out.println("exit getAllMenuItems method");

		} catch (ServiceException e) {

			e.printStackTrace();
			System.out.println("In getAllMenuItems ServiceException");
		}

	}

	public void getAllLanguages() {

		try {
			System.out.println("Enter getAllLanguages method");

			langList = menuService.getAllLanguages();

			System.out.println("number of languages:" + langList.size());

			System.out.println("exit getAllLanguages method");

		} catch (ServiceException e) {

			e.printStackTrace();
			System.out.println("In getAllLanguages ServiceException");
		}

	}

	public String dispMenuItems() {
		System.out.println("in dispMenuItems");
		return "disp_menu_items";

	}

	public String dispMenuItemsForTranslation() {
		System.out.println("in dispMenuItemsForTranslation");
		return "/page/translate_menuitems.xhtml?faces-redirect=true";

	}

	public String dispCategories() {
		System.out.println(" in dispCategories");

		return "disp_menu_categories";
	}

	public String dispSubCategories() {
		System.out.println(" in dispSubCategories");

		return "disp_menu_sub_categories";
	}

	public String addCategory() {
		System.out.println("enter addCategory");
		try {
			System.out.println("enter addCategory");
			int maxCatId = menuService.addItemType(menuCategory);
			System.out.println("max cat id :" + maxCatId);
			System.out.println("exit addCategory");
			// upload image
			uploadImage(maxCatId);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("In add category IOException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/page/disp_categories.xhtml?faces-redirect=true";
	}

	public String addSubCategory() {
		System.out.println("enter addSubCategory");
		try {
			System.out.println("enter addSubCategory");
			int maxCatId = menuService.addSubCategory(menuCategory);
			System.out.println("max cat id :" + maxCatId);
			System.out.println("exit addSubCategory");
			// upload image
			uploadImage(maxCatId);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("In add addSubCategory IOException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/page/disp_subcategories.xhtml?faces-redirect=true";
	}

	public String translateSelectedItems() {
		System.out.println("In translateSelectedItems");
		language.getLanguage();
		// System.out.println("OrderID for Status:" + orderId);
		try {
			for (MenuItem item : itemList) {
				if (select.get(item.getId()).booleanValue()) {
					System.out.println("menuController.translateSelectedItems");
					System.out.println("item translated:: " + item.getId());
					// orderService.updateDeliveryStatus(order);
					System.out.println("menuController.translateSelectedItems");
				}
			}

			select.clear();
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("Out translateSelectedItems");

		// return "/page/disp_order.xhtml?faces-redirect=true";
		return "";

	}

	public MenuCategory getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(MenuCategory categoryType) {
		this.categoryType = categoryType;
	}

	public MenuItem getItem() {
		dbImageUrl = "http://192.168.1.100:9999/Restaurant/rest/image/fooditem/get?name="
				+ item.getId();
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public List<MenuCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<MenuCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public List<MenuItem> getItemList() {
		System.out.println("list of items is ::" + itemList.size());
		return itemList;
	}

	public void setItemList(List<MenuItem> itemList) {
		this.itemList = itemList;
	}

	public boolean isShowCatAddPane() {
		return showCatAddPane;
	}

	public void setShowCatAddPane(boolean showCatAddPane) {
		this.showCatAddPane = showCatAddPane;
	}

	public boolean isShowItemAddPane() {
		return showItemAddPane;
	}

	public void setShowItemAddPane(boolean showItemAddPane) {
		this.showItemAddPane = showItemAddPane;
	}

	public List<SelectItem> getSelectCategories() {
		return selectCategories;
	}

	public void setSelectCategories(List<SelectItem> selectCategories) {
		this.selectCategories = selectCategories;
	}

	public List<MenuCategory> getCatList() {
		return catList;
	}

	public void setCatList(List<MenuCategory> catList) {
		this.catList = catList;
	}

	public MenuCategory getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(MenuCategory menuCategory) {
		this.menuCategory = menuCategory;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public SelectItem getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(SelectItem selectItem) {
		this.selectItem = selectItem;
	}

	public List<MenuCategory> getCateList() {
		return cateList;
	}

	public void setCateList(List<MenuCategory> cateList) {
		this.cateList = cateList;
	}

	public List<MenuCategory> getSubcateList() {
		return subcateList;
	}

	public void setSubcateList(List<MenuCategory> subcateList) {
		this.subcateList = subcateList;
	}

	public List<SelectItem> getSelectCategors() {
		return selectCategors;
	}

	public void setSelectCategors(List<SelectItem> selectCategors) {
		this.selectCategors = selectCategors;
	}

	public List<SelectItem> getSelectsubCategors() {
		return selectsubCategors;
	}

	public void setSelectsubCategors(List<SelectItem> selectsubCategors) {
		this.selectsubCategors = selectsubCategors;
	}

	public List<MasterLanguage> getLangList() {
		System.out.println("no. of languages:: " + langList.size());
		return langList;
	}

	public void setLangList(List<MasterLanguage> langList) {
		this.langList = langList;
	}

	public MasterLanguage getLanguage() {
		return language;
	}

	public void setLanguage(MasterLanguage language) {
		this.language = language;
	}

	public MenuItemLangMap getMenuItemLangMap() {
		return menuItemLangMap;
	}

	public void setMenuItemLangMap(MenuItemLangMap menuItemLangMap) {
		this.menuItemLangMap = menuItemLangMap;
	}

}

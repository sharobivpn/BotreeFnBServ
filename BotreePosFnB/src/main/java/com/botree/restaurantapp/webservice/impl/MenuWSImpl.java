package com.botree.restaurantapp.webservice.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.botree.Restaurant.rahulbean.FgSaleOutItemsChild;
import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.dao.RecipeManagementDAO;
import com.botree.restaurantapp.dao.RecipeManagementDAOImpl;
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
import com.botree.restaurantapp.service.MenuService;
import com.botree.restaurantapp.service.exception.ServiceException;
import com.botree.restaurantapp.webservice.MenuWS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/menu")
public class MenuWSImpl implements MenuWS {

  @Autowired
	private MenuService menuService;

	@Override
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "text/plain")
	public String getMenu() {
		MenuCategory menu = null;
		try {
			menu = menuService.getMenu();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menu, MenuCategory.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getMenu", method = RequestMethod.GET, produces = "text/plain")
	public String getMenuByStoreId(@RequestParam(value = "id") String storeId,
			@RequestParam(value = "language") String language) {

		long st = System.currentTimeMillis();
		MenuCategory menuCategory = null;

		try {

			menuCategory = menuService.getMenuByStoreId(storeId, language);

		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuCategory, MenuCategory.class);

		if (CommonProerties.test) {
			long end = System.currentTimeMillis();
			System.out.println("Performance on Menu: " + (end - st)
					+ "milliisec");
		}

		return json;
	}

	@Override
	@RequestMapping(value = "/getMenuLayoff", method = RequestMethod.GET, produces = "text/plain")
	public String getMenuLayoffByStoreId(
			@RequestParam(value = "id") String storeId,
			@RequestParam(value = "language") String language) {

		long st = System.currentTimeMillis();
		MenuCategory menuCategory = null;
		try {
			//System.out.println("getMenuLayoff calld..");
			menuCategory = menuService.getMenuByStoreId(storeId, language);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String ystrdayDate = format1.format(getCalculatedDate(1));
		System.out.println("ystrday ..." + ystrdayDate);
		// yesterdays current stock
		Map<Integer, FgSaleOutItemsChild> map = new HashMap<Integer, FgSaleOutItemsChild>();
		try {
			RecipeManagementDAO dao = new RecipeManagementDAOImpl();
			System.out.println("yestrday getFgSaleOutByDate calld..");
			List<FgSaleOutItemsChild> fgSaleOutList = dao.getFgSaleOutByDate(Integer.parseInt(storeId), ystrdayDate);
			Iterator<FgSaleOutItemsChild> iterator = fgSaleOutList.iterator();
			while (iterator.hasNext()) {
				FgSaleOutItemsChild fgSaleOutItemsChild = (FgSaleOutItemsChild) iterator
						.next();
				fgSaleOutItemsChild
						.setCurrentStock(""
								+ (fgSaleOutItemsChild.getStockInQuantity() - fgSaleOutItemsChild
										.getSaleOutQuantity()));
				map.put(fgSaleOutItemsChild.getItemId(), fgSaleOutItemsChild);

				System.out.println("so - " + fgSaleOutItemsChild.getItemId()
						+ " " + fgSaleOutItemsChild.getCurrentStock());
			}

		} catch (DAOException e) {
			e.printStackTrace();
		}
		// Date ystrdayDate=getCalculatedDate(1);

		List<MenuCategory> catList = menuCategory.getMenucategory();
		Iterator<MenuCategory> iterator = catList.iterator();
		while (iterator.hasNext()) {
			MenuCategory cat = iterator.next(); // category
			List<MenuCategory> subcatList = cat.getMenucategory();
      Iterator<MenuCategory> iterator2 = subcatList.iterator();
			while (iterator2.hasNext()) {
				MenuCategory subCat = iterator2.next(); // subcategory
				List<MenuItem> itemList = subCat.getItems();
				Iterator<MenuItem> iterator3 = itemList.iterator();

				while (iterator3.hasNext()) {
					try {
						MenuItem item = iterator3.next(); // item

						//System.out.println(item.getId());
						// layoff
						item.setDailyMinQty(0);
						String c = null;

						if (map.containsKey(item.getId())) {

							c = map.get(item.getId()).getCurrentStock();
							if (c.trim().length() > 0)

								// item.setEdpQuantity(Integer.parseInt(c.trim()));
								item.setEdpQuantity(((Float) (Float.parseFloat(c.trim()))).intValue());
							if (item.getEdpQuantity() == 0) {
								iterator3.remove();
							}

						} else {
							iterator3.remove();
						}

					} catch (Exception ex) {
						// ex.printStackTrace();
						System.out.println("mesage " + ex.getMessage());
					}
				}
			}
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuCategory, MenuCategory.class);

		if (CommonProerties.test) {
			long end = System.currentTimeMillis();
			System.out.println("Performance on Menu: " + (end - st)
					+ "milliisec");
			//System.out.println(json);

		}

		return json;
	}

	public Date getCalculatedDate(int noofdaysbefore) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -noofdaysbefore);
		return c.getTime();
	}

	/*
	 * @Override
	 * 
	 * @RequestMapping(value = "/getMenuByCategory")
	 * 
	 * method = RequestMethod.GET,
	 * 
	 * produces = "text/plain") public String getMenuByCategoryAndStoreId(
	 * 
	 * @RequestParam(value = "id") String storeId,
	 * 
	 * @RequestParam(value = "categoryId") String categoryId,
	 * 
	 * @RequestParam(value = "language") String language) { long st =
	 * System.currentTimeMillis(); MenuCategory menuCategory = null; try {
	 * menuCategory = menuService.getMenuByCategoryAndStoreId(storeId,
	 * categoryId, language); } catch (ServiceException e) {
	 * e.printStackTrace(); } Gson gson = new
	 * GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); String
	 * json = gson.toJson(menuCategory, MenuCategory.class);
	 * 
	 * if (CommonProerties.test) { long end = System.currentTimeMillis();
	 * System.out.println("Performance on Menu: " + (end - st) + "milliisec");
	 * //System.out.println(json); } return json; }
	 */

	@Override
	@RequestMapping(value = "/getMenuAll", method = RequestMethod.GET,
	produces = "text/plain")
	public String getMenuByStoreIdAll(@RequestParam(value = "id") String storeId,
			@RequestParam(value = "language") String language) {

		MenuCategory menuCategory = null;
		try {
			menuCategory = menuService.getMenuByStoreIdAll(storeId, language);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuCategory, MenuCategory.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getMenuSpecialNote", method = RequestMethod.GET,
	produces = "text/plain")
	public String getMenuSpecialNote(@RequestParam(value = "id") String storeId,
			@RequestParam(value = "language") String language) {

		MenuCategory menuCategory = null;
		try {
			menuCategory = menuService.getMenuSpecialNote(storeId, language);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuCategory, MenuCategory.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getMenuSpecialNoteByItemId", method = RequestMethod.GET, produces = "text/plain")
	public String getMenuSpecialNoteByItemId(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "language") String language,
			@RequestParam(value = "itemId") String itemId) {

		MenuCategory menuCategory = null;
		try {
			menuCategory = menuService.getMenuSpecialNoteByItemId(storeId,
					language, itemId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuCategory, MenuCategory.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getMenuById", method = RequestMethod.GET, produces = "text/plain")
	public String getMenuItemById(@RequestParam(value = "id") String id,
			@RequestParam(value = "language") String language) {

		MenuItem menuItem = null;
		MenuItemLangMap menuItemLangMap = null;
		try {
			int itemId = Integer.parseInt(id);
			menuItem = menuService.getMenuItemById(itemId);
			menuItemLangMap = menuService.getMenuItemLang(itemId, language);// child

			if (menuItemLangMap != null) {
				if (menuItemLangMap.getDescription() != null
						&& menuItemLangMap.getDescription().length() > 0)
					menuItem.setDescription(menuItemLangMap.getDescription());
				if (menuItemLangMap.getName() != null
						&& menuItemLangMap.getName().length() > 0)
					menuItem.setName(menuItemLangMap.getName());
				if (menuItemLangMap.getPromotionDesc() != null
						&& menuItemLangMap.getPromotionDesc().length() > 0)
					menuItem.setPromotionDesc(menuItemLangMap
							.getPromotionDesc());
				if (menuItemLangMap.getFoodOption1() != null
						&& menuItemLangMap.getFoodOption1().length() > 0)
					menuItem.setFoodOption1(menuItemLangMap.getFoodOption1());
				if (menuItemLangMap.getFoodOption2() != null
						&& menuItemLangMap.getFoodOption2().length() > 0)
					menuItem.setFoodOption2(menuItemLangMap.getFoodOption2());
			}

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuItem, MenuItem.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getTranslatedMenuItem", method = RequestMethod.GET, produces = "text/plain")
	public String getTranslatedMenuItem(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "language") String language) {

		List<MenuItemLangMap> menuItemLangMapList = null;
		try {
			int storeid = Integer.parseInt(storeId);

			menuItemLangMapList = menuService.getTranslatedMenuItem(storeid,
					language);// child

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.serializeNulls().create();
		java.lang.reflect.Type type = new TypeToken<List<MenuItemLangMap>>() {
		}.getType();
		String json = gson.toJson(menuItemLangMapList, type);
		// System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getTranslatedMenuCatNSubcat", method = RequestMethod.GET, produces = "text/plain")
	public String getTranslatedMenuCatNSubcat(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "language") String language) {

		List<MenuCategoryLangMap> menuCatLangMapList = null;
		try {
			int storeid = Integer.parseInt(storeId);

			menuCatLangMapList = menuService.getTranslatedMenuCatNSubcat(
					storeid, language);// child

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.serializeNulls().create();
		java.lang.reflect.Type type = new TypeToken<List<MenuCategoryLangMap>>() {
		}.getType();
		String json = gson.toJson(menuCatLangMapList, type);
		// System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/updateItem", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateMenuItem(@RequestBody MenuItem menuItem) {

		String status = "";
		try {
			//System.out.println("enter updateMenuItem webservice");
			status = menuService.updateMenuItem(menuItem);
			//System.out.println("exit updateMenuItem webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		/*
		 * Gson gson = new
		 * GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); String
		 * json = gson.toJson(item, MenuItem.class); System.out.println(json);
		 */
		return status;

	}

	@Override
	@RequestMapping(value = "/updateSpecialNotes", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateSpecialNote(@RequestBody SpecialNoteListContainer noteListContainer) {

		String status = "";
		try {
			status = menuService.updateSpecialNote(noteListContainer);
		} catch (Exception x) {
			x.printStackTrace();
		}
		/*
		 * Gson gson = new
		 * GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); String
		 * json = gson.toJson(item, MenuItem.class); System.out.println(json);
		 */
		return status;

	}

	@Override
	@RequestMapping(value = "/getLanguagesByStore", method = RequestMethod.GET, produces = "text/plain")
	public String getLanguagesByStore(@RequestParam(value = "id") String id) {
		List<MasterLanguage> languageList = null;
		try {
			languageList = menuService.getLanguageListByStoreId(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MasterLanguage>>() {
		}.getType();
		String json = gson.toJson(languageList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllColors", method = RequestMethod.GET, produces = "text/plain")
	public String getAllColors() {
		List<ColorMaster> colorList = null;
		try {
			colorList = menuService.getAllColors();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<ColorMaster>>() {
		}.getType();
		String json = gson.toJson(colorList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getCategoryByStore", method = RequestMethod.GET, produces = "text/plain")
	public String getCategoryByStore(@RequestParam(value = "id") String id) {
		List<MenuCategory> catList = null;
		try {
			catList = menuService.getCategoryByStore(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuCategory>>() {
		}.getType();
		String json = gson.toJson(catList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getCategoryByStoreExcludeSpclNote", method = RequestMethod.GET, produces = "text/plain")
	public String getCategoryByStoreExcludeSpclNote(
			@RequestParam(value = "id") String id) {
		List<MenuCategory> catList = null;
		try {
			catList = menuService.getCategoryByStoreExcludeSpclNote(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuCategory>>() {
		}.getType();
		String json = gson.toJson(catList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getSubCategoryByStore", method = RequestMethod.GET, produces = "text/plain")
	public String getSubCategoryByStore(@RequestParam(value = "id") String id,
			@RequestParam(value = "catId") String catId) {
		List<MenuCategory> subCatList = null;
		try {
			subCatList = menuService.getSubCategoryByStore(id, catId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuCategory>>() {
		}.getType();
		String json = gson.toJson(subCatList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getMenuItemsByStore", method = RequestMethod.GET, produces = "text/plain")
	public String getMenuItemsByStore(@RequestParam(value = "id") String id,
			@RequestParam(value = "subcatId") String subcatId) {
		List<MenuItem> itemList = null;
		try {
			itemList = menuService.getMenuItemsByStore(id, subcatId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuItem>>() {
		}.getType();
		String json = gson.toJson(itemList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getMenuItemsBySubCatName", method = RequestMethod.GET, produces = "text/plain")
	public String getMenuItemsBySubCatName(@RequestParam(value = "id") String id,
			@RequestParam(value = "name") String name) {

		List<MenuItem> itemList = null;
		try {

			itemList = menuService.getMenuItemsBySubCatName(id, name);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuItem>>() {
		}.getType();
		String json = gson.toJson(itemList, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String addCategory(@RequestParam(value = "name") String name,
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "bgColor") String bgColor) {

		String status = "";
		try {
			status = menuService.addCategory(name, storeId, bgColor);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/addCategoryPost", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addCategoryPost(@RequestBody MenuCategory category) {

		String status = "";
		try {
			status = menuService.addCategoryPost(category);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/addTranslatedMenuItems", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addTranslatedMenuItems(@RequestBody MenuItemLangMap itemLangMap) {

		String status = "";
		try {
			status = menuService.addTranslatedMenuItems(itemLangMap);

		} catch (Exception x) {
			status="failure";
			x.printStackTrace();

		}
		return status;
	}
	
	@Override
	@RequestMapping(value = "/addTranslatedMenuCatNSubCat", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addTranslatedMenuCatNSubCat(@RequestBody MenuCategoryLangMap categoryLangMap) {

		String status = "";
		try {
			status = menuService.addTranslatedMenuCatNSubCat(categoryLangMap);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateCategory(@RequestBody MenuCategory category) {

		String status = "";
		try {
			status = menuService.updateCategory(category);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/getCategoryDetailsById", method = RequestMethod.GET, produces = "text/plain")
	public String getCategoryDetailsById(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		MenuCategory category = null;
		try {
			category = menuService.getCategoryDetailsById(id, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(category, MenuCategory.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getDirectCategoryDetailsByStore", method = RequestMethod.GET, produces = "text/plain")
	public String getDirectCategoryDetailsByStore(
			@RequestParam(value = "storeId") String storeId) {
		List<MenuCategory> categoryList = null;
		try {
			categoryList = menuService.getDirectCategoryDetailsByStore(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuCategory>>() {
		}.getType();
		String json = gson.toJson(categoryList, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getDirectCategoryByStore", method = RequestMethod.GET, produces = "text/plain")
	public String getDirectCategoryByStore(
			@RequestParam(value = "storeId") String storeId) {
		List<MenuCategory> categoryList = null;
		try {
			categoryList = menuService.getDirectCategoryByStore(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuCategory>>() {
		}.getType();
		String json = gson.toJson(categoryList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String deleteCategory(@RequestBody MenuCategory category) {

		String status = "";
		try {
			status = menuService.deleteCategory(category);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/addSubCategory", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String addSubCategory(@RequestParam(value = "name") String name,
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "bgColor") String bgColor,
			@RequestParam(value = "catId") String catId) {

		String status = "";
		try {
			status = menuService.addSubCategory(name, storeId, bgColor, catId);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/addSubCategoryPost", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addSubCategoryPost(@RequestBody MenuCategory category) {

		String status = "";
		try {
			status = menuService.addSubCategoryPost(category);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/updateSubCategory", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateSubCategory(@RequestBody MenuCategory category) {

		String status = "";
		try {
			status = menuService.updateSubCategory(category);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/deleteSubCategory", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String deleteSubCategory(@RequestBody MenuCategory category) {

		String status = "";
		try {
			status = menuService.deleteSubCategory(category);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/getSubCategoryDetailsById", method = RequestMethod.GET, produces = "text/plain")
	public String getSubCategoryDetailsById(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "catId") String catId) {
		MenuCategory category = null;
		try {
			category = menuService
					.getSubCategoryDetailsById(id, storeId, catId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(category, MenuCategory.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getSubCategoryForSpecialNote", method = RequestMethod.GET, produces = "text/plain")
	public String getSubCategoryForSpecialNote(
			@RequestParam(value = "storeId") String storeId) {
		MenuCategory category = null;
		try {
			category = menuService.getSubCategoryForSpecialNote(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(category, MenuCategory.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/addItem", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addMenuItem(@RequestBody MenuItem item) {

		System.out.println("In addMenuItem..");

		String status = "";
		try {
			status = menuService.addMenuItem(item);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String deleteMenuItem(@RequestBody MenuItem item) {
		String status = "";
		try {
			status = menuService.deleteMenuItem(item);

		} catch (Exception x) {
			x.printStackTrace();
		}
		return status;

	}

	@Override
	@RequestMapping(value = "/getItemDetailsById", method = RequestMethod.GET, produces = "text/plain")
	public String getItemDetailsById(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId) {
		MenuItem item = null;
		try {
			item = menuService.getItemDetailsById(id, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(item, MenuItem.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getItemByCode", method = RequestMethod.GET, produces = "text/plain")
	public String getItemByCode(@RequestParam(value = "code") String code,
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "lang") String lang) {

		MenuItem menuItem = null;

		try {
			menuItem = menuService.getItemByCode(code, storeId,lang);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuItem, MenuItem.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/updateOrderedItem", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "text/plain")
	public String updateOrderedItem(@RequestParam(value = "id") String id,
			@RequestParam(value = "itemId") String itemId,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "quantity") String quantity,
			@RequestParam(value = "changeNote") String changeNote) {

		String status = "";
		try {
			//status = menuService.updateOrderedItem(id, itemId, orderId,quantity, changeNote);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}
	
	
	@RequestMapping(value = "/updateOrderedItemPost", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateOrderedItemPost(@RequestBody OrderItem orderItem) {

		String status = "";
		/*int id=0;
		int itemId=0;
		int orderId=0;
		String quantity="";
		String changeNote="";*/
		try {
			/*id=orderItem.getId();
			itemId=orderItem.getItemId();
			orderId=orderItem.getOrdrId();
			quantity=orderItem.getQuantityOfItem();
			changeNote=orderItem.getChangeNote();
			status = menuService.updateOrderedItem(""+id, ""+itemId, ""+orderId,
					quantity, changeNote);*/
			status = menuService.updateOrderedItem(orderItem);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/getSpecialNoteByFoodItem", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getSpecialNoteByFoodItem(
			@RequestParam(value = "foodItemId") String foodItemIds,
			@RequestParam(value = "storeId") String storeId) {
		List<MenuItemNote> itemNoteList = null;
		try {
			itemNoteList = menuService.getSpecialNoteByFoodItem(foodItemIds,
					storeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuItemNote>>() {
		}.getType();
		String json = gson.toJson(itemNoteList, type);
		// return json;
		return json;
	}

	@Override
	@RequestMapping(value = "/addSpecialItem", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String addSpecialItem(
			@RequestParam(value = "foodItemIds") String foodItemIds,
			@RequestParam(value = "foodItemNoteId") String foodItemNoteId) {

		String status = "";
		try {
			status = menuService.addSpecialItem(foodItemIds, foodItemNoteId);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/assignPrinterToItem", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String assignPrinterToItem(
			@RequestParam(value = "itemId") String itemId,
			@RequestParam(value = "printerList") String printerList) {

		String status = "";
		try {
			status = menuService.assignPrinterToItem(itemId, printerList);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;
	}

	@Override
	@RequestMapping(value = "/updateTaxesForAllItems", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateTaxesForAllItems(@RequestBody TaxesForAllItems taxesForAllItems) {

		String status = "";
		try {
			status = menuService.updateTaxesForAllItems(taxesForAllItems);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	/*@RequestMapping(value = "/uploadImageItemPOS", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String uploadImageItemPOS(
	    @RequestParam("itemId") String itemId,
	    @RequestParam("fileName") String fileName,
	    @RequestParam("inputStream") InputStream inputStream) {

		String status = "";
		try {
			System.out.println("uploadImageItemPOS called...");
			System.out.println("itemId ..." + itemId);
			System.out.println("fileName ..." + fileName);
			status = menuService.uploadImageItemPOS(itemId, fileName, inputStream);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}*/
	
	@RequestMapping(value = "/uploadImageItemPOS", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String uploadImageItemPOS(
	    @RequestPart("file") MultipartFile  file,
	    @RequestPart("menuFile") String menuFile) {
		
		String status = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			System.out.println("uploadImageItemPOS called...");
			/*System.out.println("itemId ..." + itemId);
			System.out.println("fileName ..." + fileName);*/
			JsonNode mf = objectMapper.readTree(menuFile);
			String itemId=mf.get("id").toString();
			String fileName=mf.get("fileName").toString();
			status = menuService.uploadImageItemPOS(itemId, fileName, file.getInputStream());

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/getMenuItemsAll", method = RequestMethod.GET, produces = "text/plain")
	public String getMenuItemsAll(@RequestParam(value = "storeId") String id) {
		List<MenuItem> itemList = null;
		try {
			itemList = menuService.getMenuItemsAll(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MenuItem>>() {
		}.getType();
		String json = gson.toJson(itemList, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getRootMenu", method = RequestMethod.GET, produces = "text/plain")
	public String getRootMenuByStoreId(@RequestParam(value = "id") String storeId) {
		MenuCategory menuCategory = null;
		try {
			menuCategory = menuService.getRootMenuByStoreId(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(menuCategory, MenuCategory.class);
		return json;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}

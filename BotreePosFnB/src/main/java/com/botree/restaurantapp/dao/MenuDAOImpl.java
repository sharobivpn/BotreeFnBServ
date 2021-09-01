package com.botree.restaurantapp.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.ColorMaster;
import com.botree.restaurantapp.model.DaySpecial;
import com.botree.restaurantapp.model.MasterLanguage;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuCategoryLangMap;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.model.MenuItemNote;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.model.SpecialNoteListContainer;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.TaxesForAllItems;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.OrderService;

@Component("menuDAO")
public class MenuDAOImpl implements MenuDAO {
	private final static Logger logger = LogManager
			.getLogger(MenuDAOImpl.class);
	
	private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	/*
	 * @Override public void addItemType(MenuCategory itemType) throws
	 * DAOException {
	 * 
	 * MenuCategory menutype = new MenuCategory(); try { entityManagerFactory =
	 * PersistenceListener.getEntityManager(); EntityManager em =
	 * entityManagerFactory.createEntityManager(); em.getTransaction().begin();
	 * menutype.setMenuCategoryName(itemType.getMenuCategoryName());
	 * em.persist(itemType); em.getTransaction().commit();
	 * System.out.print("Menu Category added successfully..."); } catch
	 * (Exception e) { throw new DAOException("Check data to be inserted", e); }
	 * }
	 */

	@Override
	public int addItemType(MenuCategory itemType) throws DAOException {
		// TODO Auto-generated method stub
		int maxCatId;
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			String catName = itemType.getMenuCategoryName();
			int parentCatId = itemType.getMenutype().getId();

			

			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();
			Query query = em
					.createNativeQuery("INSERT INTO fm_m_food_types (Menu_Item_name, Parent_item_type_Id, store_id,type) "
							+ " VALUES(?, ?,?,?)");
			query.setParameter(1, catName);
			query.setParameter(2, parentCatId);
			query.setParameter(3, storeId);
			query.setParameter(4, 'c');
			query.executeUpdate();

			Query maxIdQuery = em
					.createNativeQuery("select max(Id) as max_id from fm_m_food_types");
			//System.out.println("heloo11");
			Integer maxId = (Integer) maxIdQuery.getSingleResult();
			//System.out.println("heloo33");
			maxCatId = maxId.intValue();
			//System.out.println("heloo44");
			System.out.println("max cat id:" + maxCatId);

			em.getTransaction().commit();

			System.out.print("Menu Category added successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return maxCatId;
	}

	@Override
	public int addSubCategory(MenuCategory itemType) throws DAOException {
		// TODO Auto-generated method stub
		int maxCatId;
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			String catName = itemType.getMenuCategoryName();
			int parentCatId = itemType.getMenutype().getId();

			

			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();
			Query query = em
					.createNativeQuery("INSERT INTO fm_m_food_types (Menu_Item_name, Parent_item_type_Id, store_id,type) "
							+ " VALUES(?, ?,?,?)");
			query.setParameter(1, catName);
			query.setParameter(2, parentCatId);
			query.setParameter(3, storeId);
			query.setParameter(4, 's');
			query.executeUpdate();

			Query maxIdQuery = em
					.createNativeQuery("select max(Id) as max_id from fm_m_food_types");
			//System.out.println("heloo11");
			Integer maxId = (Integer) maxIdQuery.getSingleResult();
			//System.out.println("heloo33");
			maxCatId = maxId.intValue();
			//System.out.println("heloo44");
			System.out.println("max cat id:" + maxCatId);

			em.getTransaction().commit();

			System.out.print("Menu Category added successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return maxCatId;
	}

	@Override
	public int addItem(MenuItem item) throws DAOException {

		int maxItemId;

		MenuItem menuItm = new MenuItem();
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			menuItm.setName(item.getName());
			menuItm.setDescription(item.getDescription());
			menuItm.setPrice(item.getPrice());
			menuItm.setVat(item.getVat());
			menuItm.setServiceTax(item.getServiceTax());
			menuItm.setMenucategory(item.getMenucategory());
			menuItm.setDeleteFlag("N");
			menuItm.setVeg(item.getVeg());
			menuItm.setStoreId(storeId);
			menuItm.setPromotionDesc(item.getPromotionDesc());
			menuItm.setPromotionFlag(item.getPromotionFlag());
			menuItm.setPromotionValue(item.getPromotionValue());
			menuItm.setBeverages(item.getBeverages());
			menuItm.setDesert(item.getDesert());
			menuItm.setFoodOption1(item.getFoodOption1());
			menuItm.setFoodOption2(item.getFoodOption2());
			menuItm.setFoodOptionFlag("N");
			menuItm.setSpicy(item.getSpicy());
			menuItm.setHouseSpecial(item.getHouseSpecial());
			menuItm.setVat(item.getVat());
			menuItm.setCookingTimeInMins(10);
			menuItm.setProduction("Y");

			// set current date and time
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNTime = dateFormat.format(date);

			// start calculate time of order
			if (dateNTime != null && dateNTime.length() > 0) {
				String[] tempURL1 = dateNTime.split(" ");
				String creationDate = tempURL1[0];
				String creationTime = tempURL1[1];
				String[] timefull = creationTime.split(":");
				String hr = timefull[0];
				String mins = timefull[1];
				String hrMins = hr + ":" + mins;

				menuItm.setCreationDate(creationDate);
				menuItm.setCreationTime(hrMins);
			}
			// end calculate time of order

			em.persist(menuItm);

			Query maxIdQuery = em
					.createNativeQuery("select max(Id) as max_id from fm_m_food_items");
			//System.out.println("heloo11");
			Integer maxId = (Integer) maxIdQuery.getSingleResult();
			//System.out.println("heloo33");
			maxItemId = maxId.intValue();
			//System.out.println("heloo44");
			System.out.println("max item id:" + maxItemId);

			em.getTransaction().commit();
			
			System.out.print("Menu Category added successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		return maxItemId;

	}

	@Override
	public void updateItemType(MenuCategory itemType) throws DAOException {

		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuCategory itemType1 = em.find(MenuCategory.class,
					itemType.getId());
			itemType1.setMenuCategoryName(itemType.getMenuCategoryName());
			em.merge(itemType1);
			em.getTransaction().commit();
			System.out.print("Menu Category updated successfully....");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public void updateItem(MenuItem item) throws DAOException {
		logger.debug("Updating menu Item: {}", item);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuItem item1 = em.find(MenuItem.class, item.getId());
			item1.setName(item.getName());
			item1.setDescription(item.getDescription());
			item1.setPrice(item.getPrice());
			item1.setVat(item.getVat());
			item1.setServiceTax(item.getServiceTax());
			item1.setVeg(item.getVeg());
			item1.setSpicy(item.getSpicy());
			item1.setHouseSpecial(item.getHouseSpecial());
			item1.setPromotionDesc(item.getPromotionDesc());
			item1.setPromotionFlag(item.getPromotionFlag());
			item1.setPromotionValue(item.getPromotionValue());
			item1.setBeverages(item.getBeverages());
			item1.setDesert(item.getDesert());
			item1.setFoodOption1(item.getFoodOption1());
			item1.setFoodOption2(item.getFoodOption2());
			item1.setCookingTimeInMins(item.getCookingTimeInMins());
			item1.setProduction(item.getProduction());

			// set current date and time
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNTime = dateFormat.format(date);

			// start calculate time of order
			if (dateNTime != null && dateNTime.length() > 0) {
				String[] tempURL1 = dateNTime.split(" ");
				String updationDate = tempURL1[0];
				String updationTime = tempURL1[1];
				String[] timefull = updationTime.split(":");
				String hr = timefull[0];
				String mins = timefull[1];
				String hrMins = hr + ":" + mins;

				item1.setUpdationDate(updationDate);
				item1.setUpdationTime(hrMins);
			}
			// end calculate time of order
			em.getTransaction().commit();
			System.out.print("MenuItem updated successfully....");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check MenuItem to be updated", e);
		} finally {
			if(em != null) em.close();
		}
	}

	@Override
	public String updateMenuItem(MenuItem item) throws DAOException {

		MenuItem item1 = null;
		EntityManager em = null;
		String status = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			item1 = em.find(MenuItem.class, item.getId());
			item1.setName(item.getName());
			item1.setDescription(item.getDescription());
			item1.setPrice(item.getPrice());
			item1.setVeg(item.getVeg());
			item1.setSpicy(item.getSpicy());
			item1.setHouseSpecial(item.getHouseSpecial());
			item1.setPromotionDesc(item.getPromotionDesc());
			item1.setPromotionFlag(item.getPromotionFlag());
			item1.setPromotionValue(item.getPromotionValue());
			item1.setBeverages(item.getBeverages());
			item1.setDesert(item.getDesert());
			item1.setFoodOption1(item.getFoodOption1());
			item1.setFoodOption2(item.getFoodOption2());
			item1.setServiceTax(item.getServiceTax());
			item1.setVat(item.getVat());
			item1.setCookingTimeInMins(item.getCookingTimeInMins());
			item1.setProduction(item.getProduction());
			item1.setDailyMinQty(item.getDailyMinQty());
			if (item.getUnit() == null || item.getUnit().equalsIgnoreCase("")) {
				item1.setUnit("plate");
			} else {
				item1.setUnit(item.getUnit());
			}
			item1.setPrinterId(item.getPrinterId());
      if (item.getCode() != null) {
        item1.setCode(item.getCode());
      }

			// set current date and time
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNTime = dateFormat.format(date);

			// start calculate time of order
			if (dateNTime != null && dateNTime.length() > 0) {
				String[] tempURL1 = dateNTime.split(" ");
				String updationDate = tempURL1[0];
				String updationTime = tempURL1[1];
				String[] timefull = updationTime.split(":");
				String hr = timefull[0];
				String mins = timefull[1];
				String hrMins = hr + ":" + mins;

				item1.setUpdationDate(updationDate);
				item1.setUpdationTime(hrMins);
			}
			if (item.getSpotDiscount() == null || item.getSpotDiscount() == "") {
				item1.setSpotDiscount("Y");
			} else {
				item1.setSpotDiscount(item.getSpotDiscount());
			}

			if (item.getBgColor() == null || item.getBgColor() == "") {
				item1.setBgColor("#ffffff");
			} else {
				item1.setBgColor(item.getBgColor());
			}
			item1.setIsKotPrint(item.getIsKotPrint());
			item1.setPurchasePrice(item.getPurchasePrice());
			// end calculate time of order

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check MenuItem to be updated", e);
		} finally {
			if(em != null) em.close();
		}

		return status;
	}

	@Override
	public List<MenuCategory> getAllItemTypes() throws DAOException {

		List<MenuCategory> menuList = null;
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuCategory> qry = em
					.createQuery("SELECT m FROM MenuCategory m where m.storeId=:storeId and m.deleteFlag='N' and m.type='c'",
					    MenuCategory.class);
			qry.setParameter("storeId", storeId);
			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.menutype=1");
			menuList = qry.getResultList();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return menuList;
	}

	@Override
	public List<MenuCategory> getAllSubCategories() throws DAOException {

		List<MenuCategory> menuList = null;
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuCategory> qry = em
					.createQuery("SELECT m FROM MenuCategory m where m.storeId=:storeId and m.deleteFlag='N' and m.type='s'",
					    MenuCategory.class);
			qry.setParameter("storeId", storeId);
			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.menutype=1");
			menuList = qry.getResultList();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return menuList;
	}

	@Override
	public List<MenuCategory> getRootMenu() throws DAOException {

		List<MenuCategory> menuList = null;
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuCategory> qry = em
					.createQuery("SELECT m FROM MenuCategory m where m.storeId=:storeId and m.deleteFlag='N' and m.type='r'",
					    MenuCategory.class);
			qry.setParameter("storeId", storeId);
			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.menutype=1");
			menuList = qry.getResultList();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) if(em != null) em.close();
		}
		return menuList;
	}

	public MenuCategory getMenu() throws DAOException {
		MenuCategory parentMenuCat = new MenuCategory();
		List<MenuCategory> finalMenuCatList = new ArrayList<MenuCategory>();
		List<MenuCategory> finalMenuCatList1 = new ArrayList<MenuCategory>();
		List<Integer> subCategoryIdList = new ArrayList<Integer>();
		boolean chkId = false;

		parentMenuCat.setId(0);
		parentMenuCat.setMenuCategoryName("Menu");
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuCategory> qry = em.createQuery("SELECT m FROM MenuCategory m WHERE LOWER(m.menuCategoryName) != :menu",
			    MenuCategory.class);
      String menuItmName = "menu";
      qry.setParameter("menu", menuItmName);
			
			List<MenuCategory> menulist = qry.getResultList();

			Iterator<MenuCategory> iterator = menulist.iterator();
			while (iterator.hasNext()) {
				finalMenuCatList.add(iterator.next());
			}
			Iterator<MenuCategory> iterator3 = finalMenuCatList.iterator();
			while (iterator3.hasNext()) {
				chkId = false;
				MenuCategory menuCategory = (MenuCategory) iterator3.next();
				int menuCatId = menuCategory.getId();
				System.out.println("menu cat id:" + menuCatId);
				TypedQuery<Integer> query = em
						.createQuery("SELECT distinct m.id FROM MenuCategory m, MenuItem i WHERE m.id=i.menucategory AND m.id = :menuCatId", 
						    Integer.class);
        query.setParameter("menuCatId", menuCatId);
				subCategoryIdList = query.getResultList();
				Iterator<Integer> iterator2 = subCategoryIdList.iterator();
				while (iterator2.hasNext()) {
					int subCatId = (Integer) iterator2.next().intValue();
					// System.out.println("sub cat id:" + subCatId);
					if (subCatId == menuCatId) {
						chkId = true;
						break;
					}
				}
				if (!chkId) {
					finalMenuCatList1.add(menuCategory);
				}
			}

			// finalMenuCatList1.add(parentMenuCat);
			parentMenuCat.setMenucategory(finalMenuCatList1);
			
			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}
		System.out.println("done fetching menu data");
		return parentMenuCat;
	}

	@Override
	public MenuCategory getMenuByStoreIdAll(String storeId, String language)
			throws DAOException {

		MenuCategory parentMenuCat = new MenuCategory();
		List<MenuCategory> finalMenuCatList = new ArrayList<MenuCategory>();
		List<MenuCategory> finalMenuCatList1 = new ArrayList<MenuCategory>();
		List<Integer> subCategoryIdList = new ArrayList<Integer>();
		boolean chkId = false;
		EntityManager em = null;
		List<MenuCategoryLangMap> menuLanglist = null;
		List<MenuItemLangMap> menuItemLangList = null;

		parentMenuCat.setId(0);
		parentMenuCat.setMenuCategoryName("Menu");

		int storeid = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// String specialNote = "Special Note";
			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid");
      String menuItmName = "menu";
			TypedQuery<MenuCategory> qry = em
					.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid AND m.deleteFlag='N' AND LOWER(m.menuCategoryName) != :menu", MenuCategory.class);
			qry.setParameter("storeid", storeid);
      qry.setParameter("menu", menuItmName);

      List<MenuCategory> menulist = (List<MenuCategory>) qry.getResultList();

			Iterator<MenuCategory> iterator = menulist.iterator();
			while (iterator.hasNext()) {
				finalMenuCatList.add(iterator.next());
			}

			// All but Menu
			Iterator<MenuCategory> iterator3 = finalMenuCatList.iterator();
			while (iterator3.hasNext()) {
				chkId = false;
				// common type
				MenuCategory menuCategory = (MenuCategory) iterator3.next();
				int menuCatId = menuCategory.getId();
				//System.out.println("menu cat id:" + menuCatId);
				TypedQuery<Integer> query = em
						.createQuery("SELECT distinct m.id FROM MenuCategory m, MenuItem i WHERE m.id=i.menucategory AND m.deleteFlag='N' AND m.id = :menuCatId",
						    Integer.class); //Check me
        query.setParameter("menuCatId", menuCatId);
				subCategoryIdList = (List<Integer>) query.getResultList();
				Iterator<Integer> iterator2 = subCategoryIdList.iterator();
				while (iterator2.hasNext()) {
					int subCatId = (Integer) iterator2.next().intValue();
					// System.out.println("sub cat id:" + subCatId);
					if (subCatId == menuCatId) {
						chkId = true;
						break;
					}
				}
				
				if (!chkId) {
					// check sub category
					if (menuCategory.getMenucategory() != null
							&& menuCategory.getMenucategory().size() > 0) {

						boolean isAddable = false;
						for (int c = 0; c < menuCategory.getMenucategory()
								.size(); c++) {

							MenuCategory subMenucategory = menuCategory
									.getMenucategory().get(c);
							if (subMenucategory.getItems() != null
									&& subMenucategory.getItems().size() > 0) {
								isAddable = true;
							} else if (subMenucategory.getItems() == null
									|| subMenucategory.getItems().size() == 0) {
								menuCategory.getMenucategory().remove(
										subMenucategory);// remove sub cat
															// without item
							}
						}

						if (isAddable)
							finalMenuCatList1.add(menuCategory);
					}

				}
			}

			// finalMenuCatList1.add(parentMenuCat);
			parentMenuCat.setMenucategory(finalMenuCatList1);
			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		if (language!=null) {
			if (language.trim().length()>0 && !language.equalsIgnoreCase("english")) {
				// Translation
				try {
					em = entityManagerFactory.createEntityManager();
					em.getTransaction().begin();
					// query to get all categories and sub-categories
					TypedQuery<MenuCategoryLangMap> qryCatTypelang = em
							.createQuery("SELECT m FROM MenuCategoryLangMap m WHERE m.storeId=:storeid and m.language=:language",
							    MenuCategoryLangMap.class);
					qryCatTypelang.setParameter("storeid", storeid);
					qryCatTypelang.setParameter("language", language);
					menuLanglist = qryCatTypelang.getResultList();

					// query to get all items
					TypedQuery<MenuItemLangMap> qryItemlang = em
							.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.storeId=:storeid and i.language=:language",
							    MenuItemLangMap.class);
					qryItemlang.setParameter("storeid", storeid);
					qryItemlang.setParameter("language", language);
					menuItemLangList = qryItemlang.getResultList();
				} catch (Exception e) {
					e.printStackTrace();
					throw new DAOException("Check data to be inserted", e);

				} finally {
					if(em != null) em.close();
				}
				languageTransForCatNItem(parentMenuCat, menuLanglist,
						menuItemLangList);
			}
		}
		System.out.println("done fetching menu data");
		return parentMenuCat;
	}

	@Override
	public MenuCategory getMenuByStoreId(String storeId, String language)
			throws DAOException {

		MenuCategory parentMenuCat = new MenuCategory();
		List<MenuCategory> finalMenuCatList = new ArrayList<MenuCategory>();
		List<MenuCategory> finalMenuCatList1 = new ArrayList<MenuCategory>();
		List<Integer> subCategoryIdList = new ArrayList<Integer>();
		boolean chkId = false;
		EntityManager em = null;
		List<MenuCategoryLangMap> menuLanglist = null;
		List<MenuItemLangMap> menuItemLangList = null;
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;

		parentMenuCat.setId(0);
		parentMenuCat.setMenuCategoryName("Menu");

		int storeid = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
      Session ses = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
          .getSessionFactory();
      connection = sessionFactory.getConnectionProvider().getConnection();
			em.getTransaction().begin();
			String specialNote = "Special Note";
      String menuItmName = "menu";
			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid");
			TypedQuery<MenuCategory> qry = em
          .createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid and m.deleteFlag='N' and m.menuCategoryName!=:specialnote AND LOWER(m.menuCategoryName) != :menu", MenuCategory.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("specialnote", specialNote);
      qry.setParameter("menu", menuItmName);
			List<MenuCategory> menulist = qry.getResultList();

			Iterator<MenuCategory> iterator = menulist.iterator();
			while (iterator.hasNext()) {
			  MenuCategory menuCat = iterator.next();
				finalMenuCatList.add(menuCat);// both cat = sum of sub cat
			}

			// All but Menu
			Iterator<MenuCategory> iterator3 = finalMenuCatList.iterator();
			while (iterator3.hasNext()) {
				chkId = false;
				MenuCategory menuCategory = iterator3.next();// common type
				int menuCatId = menuCategory.getId();
				// System.out.println("menu cat id:" + menuCatId);
				TypedQuery<Integer> query = em
            .createQuery("SELECT distinct m.id FROM MenuCategory m, MenuItem i WHERE m.id=i.menucategory and m.deleteFlag='N' AND m.id = :menuCatId",
                Integer.class);
        query.setParameter("menuCatId", menuCatId);
				subCategoryIdList = query.getResultList();
				
				Iterator<Integer> iterator2 = subCategoryIdList.iterator();
				while (iterator2.hasNext()) {
					int subCatId = iterator2.next().intValue();
					// System.out.println("sub cat id:" + subCatId);
					if (subCatId == menuCatId) {
						chkId = true;
						break;
					}
				}
				
				if (!chkId) {
					// check sub category
					if (menuCategory.getMenucategory() != null
							&& menuCategory.getMenucategory().size() > 0) {

						boolean isAddable = false;
						for (int c = 0; c < menuCategory.getMenucategory()
								.size(); c++) {

							MenuCategory subMenucategory = menuCategory
									.getMenucategory().get(c);
							if (subMenucategory.getItems() != null
									&& subMenucategory.getItems().size() > 0) {
								isAddable = true;
							} else if (subMenucategory.getItems() == null
									|| subMenucategory.getItems().size() == 0) {
								menuCategory.getMenucategory().remove(
										subMenucategory);// remove sub cat
															// without item
							}
						}

						if (isAddable)
							finalMenuCatList1.add(menuCategory);
					}

				}
			}

			// finalMenuCatList1.add(parentMenuCat);
			parentMenuCat.setMenucategory(finalMenuCatList1);
			// finalMenuCat.setMenucategory(finalMenuCatList1);

			// calculate current stock and put in menu item
			/*StoreMaster store = addressDAO.getStoreByStoreId(Integer
					.parseInt(storeId));
			String isDisplayCrntStck = store.getDisplayCurrentStockMenu();
			if (isDisplayCrntStck.equalsIgnoreCase("Y")) {
				Map<Integer, Integer> mapHoldItemCrntStck = new HashMap<>();
				String curntStockProc = "{call get_fg_item_wise_cur_stock(?,?)}";
				callableStatement = connection.prepareCall(curntStockProc);
				callableStatement.setInt(1, Integer.parseInt(storeId));
				callableStatement.setInt(2, 0);

				callableStatement.execute();
				rs = callableStatement.getResultSet();

				while (rs.next()) {
					int itemid = rs.getInt("item_id");
					int curStock = rs.getInt("cur_stock");
					mapHoldItemCrntStck.put(itemid, curStock);
				}

				List<MenuCategory> catList = parentMenuCat.getMenucategory();
				Iterator<MenuCategory> iterator1 = catList.iterator();
				while (iterator1.hasNext()) {
					MenuCategory cat = iterator1.next(); // category
					List<MenuCategory> subcatList = cat.getMenucategory();
					Iterator<MenuCategory> iterator2 = subcatList.iterator();
					while (iterator2.hasNext()) {
						MenuCategory subCat = iterator2.next(); // subcategory
						List<MenuItem> itemList = subCat.getItems();
						Iterator<MenuItem> iterator4 = itemList.iterator();

						while ( iterator4.hasNext()) {
							try {
								MenuItem item = iterator4.next(); // item
								double crntStck = mapHoldItemCrntStck.get(item.getId());
								item.setCurrentStock(crntStck);

							} catch (Exception ex) {
								// ex.printStackTrace();
								System.out.println("mesage " + ex.getMessage());
							}
						}
					}
				}
			}*/

			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		// Translation
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// query to get all categories and sub-categories
			TypedQuery<MenuCategoryLangMap> qryCatTypelang = em
					.createQuery("SELECT m FROM MenuCategoryLangMap m WHERE m.storeId=:storeid and m.language=:language",
					    MenuCategoryLangMap.class);
			qryCatTypelang.setParameter("storeid", storeid);
			qryCatTypelang.setParameter("language", language);
			menuLanglist = qryCatTypelang.getResultList();

			// query to get all items
			TypedQuery<MenuItemLangMap> qryItemlang = em
					.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.storeId=:storeid and i.language=:language",
					    MenuItemLangMap.class);
			qryItemlang.setParameter("storeid", storeid);
			qryItemlang.setParameter("language", language);
			menuItemLangList = qryItemlang.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		languageTransForCatNItem(parentMenuCat, menuLanglist, menuItemLangList);
		// System.out.println("done fetching menu data");

		return parentMenuCat;
	}

	@Override
	public MenuCategory getMenuSpecialNote(String storeId, String language)
			throws DAOException {

		MenuCategory parentMenuCat = new MenuCategory();
		List<MenuCategory> finalMenuCatList = new ArrayList<MenuCategory>();
		List<MenuCategory> finalMenuCatList1 = new ArrayList<MenuCategory>();
		List<Integer> subCategoryIdList = new ArrayList<Integer>();
		boolean chkId = false;
		EntityManager em = null;
		List<MenuCategoryLangMap> menuLanglist = null;
		List<MenuItemLangMap> menuItemLangList = null;

		parentMenuCat.setId(0);
		parentMenuCat.setMenuCategoryName("Menu");

		int storeid = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String specialNote = "Special Note";
      String menuItmName = "menu";
			
			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid");
			TypedQuery<MenuCategory> qry = em
					.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid and m.deleteFlag='N' and m.menuCategoryName=:specialnote AND LOWER(m.menuCategoryName) != :menu",
					    MenuCategory.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("specialnote", specialNote);
      qry.setParameter("menu", menuItmName);
			List<MenuCategory> menulist = qry.getResultList();

			Iterator<MenuCategory> iterator = menulist.iterator();
			while (iterator.hasNext()) {
				finalMenuCatList.add(iterator.next());
			}

			// All but Menu
			Iterator<MenuCategory> iterator3 = finalMenuCatList.iterator();
			while (iterator3.hasNext()) {
				chkId = false;
				MenuCategory menuCategory = (MenuCategory) iterator3.next();// common
																			// type
				int menuCatId = menuCategory.getId();
				//System.out.println("menu cat id:" + menuCatId);
				TypedQuery<Integer> query = em
						.createQuery("SELECT distinct m.id FROM MenuCategory m, MenuItem i WHERE m.id=i.menucategory and m.deleteFlag='N' AND m.id = :menuCatId",
						    Integer.class);
        query.setParameter("menuCatId", menuCatId);
				subCategoryIdList = query.getResultList();
				Iterator<Integer> iterator2 = subCategoryIdList.iterator();
				while (iterator2.hasNext()) {
					int subCatId = (Integer) iterator2.next().intValue();
					// System.out.println("sub cat id:" + subCatId);
					if (subCatId == menuCatId) {
						chkId = true;
						break;
					}
				}
				
				if (!chkId) {
					// check sub category
					if (menuCategory.getMenucategory() != null
							&& menuCategory.getMenucategory().size() > 0) {

						boolean isAddable = false;
						for (int c = 0; c < menuCategory.getMenucategory()
								.size(); c++) {

							MenuCategory subMenucategory = menuCategory
									.getMenucategory().get(c);
							if (subMenucategory.getItems() != null
									&& subMenucategory.getItems().size() > 0) {
								isAddable = true;
							} else if (subMenucategory.getItems() == null
									|| subMenucategory.getItems().size() == 0) {
								menuCategory.getMenucategory().remove(
										subMenucategory);// remove sub cat
															// without item
							}
						}

						if (isAddable)
							finalMenuCatList1.add(menuCategory);
					}

				}
			}

			// finalMenuCatList1.add(parentMenuCat);
			parentMenuCat.setMenucategory(finalMenuCatList1);

			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		// Translation
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// query to get all categories and sub-categories
			TypedQuery<MenuCategoryLangMap> qryCatTypelang = em
					.createQuery("SELECT m FROM MenuCategoryLangMap m WHERE m.storeId=:storeid and m.language=:language",
					    MenuCategoryLangMap.class);
			qryCatTypelang.setParameter("storeid", storeid);
			qryCatTypelang.setParameter("language", language);
			menuLanglist = qryCatTypelang.getResultList();

			// query to get all items
			TypedQuery<MenuItemLangMap> qryItemlang = em
					.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.storeId=:storeid and i.language=:language",
					    MenuItemLangMap.class);
			qryItemlang.setParameter("storeid", storeid);
			qryItemlang.setParameter("language", language);
			menuItemLangList = qryItemlang.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		languageTransForCatNItem(parentMenuCat, menuLanglist, menuItemLangList);
		System.out.println("done fetching menu data");
		return parentMenuCat;
	}

	@Override
	public MenuCategory getMenuSpecialNoteByItemId(String storeId,
			String language, String itemId) throws DAOException {

		MenuCategory parentMenuCat = getMenuSpecialNote(storeId, language);

		EntityManager em = null;
		List<MenuItemNote> menuItemNoteList = null;
		try {
			int storeId1 = Integer.parseInt(storeId);
			int itemId1 = Integer.parseInt(itemId);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuItemNote> qry = em
					.createQuery("SELECT i FROM MenuItemNote i WHERE i.storeId=:storeId and i.foodItemIds=:foodItemIds",
					    MenuItemNote.class);
			qry.setParameter("storeId", storeId1);
			qry.setParameter("foodItemIds", itemId1);
			menuItemNoteList = qry.getResultList();
			if (menuItemNoteList != null) {
				List<MenuCategory> catList = parentMenuCat.getMenucategory();
				List<MenuCategory> subCatList = catList.get(0).getMenucategory();
				if (subCatList.size() == 1) {

					List<MenuItem> items = subCatList.get(0).getItems();
					for (int i = 0; i < items.size(); i++) {
						MenuItem item = items.get(i);
						for (int j = 0; j < menuItemNoteList.size(); j++) {
							MenuItemNote itemNote = menuItemNoteList.get(j);
							if (itemNote.getMenuItem().getId() == item.getId()) {
								item.setSpecialNote("Y");
								items.remove(item);
								items.add(0, item);
								break;
							}
						}
					}
				}
			}
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if(em != null) em.close();
		}

		return parentMenuCat;
	}

	/**
	 * @param parentMenuCat
	 * @param menuLanglist
	 * @param menuItemLangList
	 */
	public void languageTransForCatNItem(MenuCategory parentMenuCat,
			List<MenuCategoryLangMap> menuLanglist,
			List<MenuItemLangMap> menuItemLangList) {
		// replace categories for language
		List<MenuCategory> menuCategories = parentMenuCat.getMenucategory();
		if (menuCategories != null && menuCategories.size() > 0) {
			Iterator<MenuCategory> iterator = menuCategories.iterator();
			while (iterator.hasNext()) {
				MenuCategory menuCategory = (MenuCategory) iterator.next();
				int catId = menuCategory.getId();
				// System.out.println("menu cat name::: " + menucatnme);
				Iterator<MenuCategoryLangMap> iterator2 = menuLanglist.iterator();
				while (iterator2.hasNext()) {
					MenuCategoryLangMap menuCategoryLangMap = iterator2.next();
					MenuCategory category = menuCategoryLangMap.getMenucategory();
					int catLangId = category.getId();
					if (catId == catLangId) {
						String catName = menuCategoryLangMap.getMenuItemName();
						if (catName != null && catName.length() > 0) {
							menuCategory.setMenuCategoryName(catName);
						}
					}
				}

				// replace sub-categories for language
				List<MenuCategory> subCatLst = menuCategory.getMenucategory();
				if (subCatLst != null && subCatLst.size() > 0) {
					Iterator<MenuCategory> subcatIterator = subCatLst.iterator();
					while (subcatIterator.hasNext()) {
						MenuCategory subCategory = subcatIterator.next();
						int subcatId = subCategory.getId();

						// System.out.println("sub cat name::: " + subCatName);
						if (menuLanglist != null && menuLanglist.size() > 0) {
							Iterator<MenuCategoryLangMap> iterator3 = menuLanglist
									.iterator();
							while (iterator3.hasNext()) {
								MenuCategoryLangMap menuCategoryLangMap = iterator3.next();
								MenuCategory category = menuCategoryLangMap.getMenucategory();
								int subCatLangId = category.getId();
								if (subcatId == subCatLangId) {
									String subCatlangName = menuCategoryLangMap.getMenuItemName();
									if (subCatlangName != null && subCatlangName.length() > 0) {
										subCategory.setMenuCategoryName(subCatlangName);
									}
								}
							}
						}
					}

					// replace items for language
					Iterator<MenuCategory> subcatIter = subCatLst.iterator();
					while (subcatIter.hasNext()) {
						MenuCategory subCategory = (MenuCategory) subcatIter
								.next();
						List<MenuItem> itemLst = subCategory.getItems();
						if (itemLst != null && itemLst.size() > 0) {
							Iterator<MenuItem> itemIter = itemLst.iterator();
							while (itemIter.hasNext()) {
								MenuItem menuItem = (MenuItem) itemIter.next();
								int itemId = menuItem.getId();
								if (menuItemLangList != null && menuItemLangList.size() > 0) {
									Iterator<MenuItemLangMap> iterItem = menuItemLangList.iterator();
									while (iterItem.hasNext()) {
										MenuItemLangMap menuItemLangMap = (MenuItemLangMap) iterItem.next();
										MenuItem item = menuItemLangMap.getMenuItem();
										int itemLangId = item.getId();
										if (itemId == itemLangId) {
											String itemlangName = menuItemLangMap.getName();
											String itemlanDesc = menuItemLangMap.getDescription();
											if (itemlangName != null && itemlangName.length() > 0) {
												menuItem.setName(itemlangName);
											}
											if (itemlanDesc != null	&& itemlanDesc.length() > 0) {
												menuItem.setDescription(itemlanDesc);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void deleteItemByType(MenuCategory itemType) throws DAOException {

		List<MenuItem> itemList = null;
		List<MenuCategory> menuList = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				// System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuCategory itemType1 = em.find(MenuCategory.class,
					itemType.getId());
			itemType1.setDeleteFlag("Y");

			int cat_subcat_id = itemType1.getId();
			// check if there is any data with this id in the cat/sub-cat table
			TypedQuery<MenuCategory> qryGetCat = em
					.createQuery("SELECT m FROM MenuCategory m where m.storeId=:storeId and m.deleteFlag='N' and m.menutype.id=:parentcatId",
					    MenuCategory.class);
			qryGetCat.setParameter("storeId", storeId);
			qryGetCat.setParameter("parentcatId", cat_subcat_id);
			menuList = qryGetCat.getResultList();
			if (menuList != null && menuList.size() > 0) {
				// deleting element is a category, so delete all sub-categories
				Iterator<MenuCategory> cateIterator = menuList.iterator();
				// get each sub-category and delete corresponding items
				while (cateIterator.hasNext()) {
					MenuCategory subCategory = (MenuCategory) cateIterator
							.next();
					int subCatId = subCategory.getId();
					subCategory.setDeleteFlag("Y");
					TypedQuery<MenuItem> qry = em
							.createQuery("SELECT i FROM MenuItem i WHERE i.deleteFlag= 'N' and i.storeId=:storeId and i.menucategory.id=:id",
							    MenuItem.class);
					qry.setParameter("storeId", storeId);
					qry.setParameter("id", subCatId);
					itemList = qry.getResultList();
					Iterator<MenuItem> itemIterator = itemList.iterator();
					while (itemIterator.hasNext()) {
						MenuItem menuItem = (MenuItem) itemIterator.next();
						menuItem.setDeleteFlag("Y");
					}
				}
			} else {
				// delete items for the sub-category
				TypedQuery<MenuItem> qry = em
						.createQuery("SELECT i FROM MenuItem i WHERE i.deleteFlag= 'N' and i.storeId=:storeId and i.menucategory.id=:id",
						    MenuItem.class);
				qry.setParameter("storeId", storeId);
				qry.setParameter("id", cat_subcat_id);
				itemList = qry.getResultList();
				Iterator<MenuItem> itemIterator = itemList.iterator();
				while (itemIterator.hasNext()) {
					MenuItem menuItem = (MenuItem) itemIterator.next();
					menuItem.setDeleteFlag("Y");
				}
			}
			em.getTransaction().commit();
			System.out.print("Menu Category deleted successfully....");
		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public void deleteItem(MenuItem item) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuItem item1 = em.find(MenuItem.class, item.getId());
			item1.setDeleteFlag("Y");
			List<DaySpecial> daySpecialList = getDaySpecialbyItemId(item
					.getId());
			Iterator<DaySpecial> itrSpecial = daySpecialList.iterator();
			while (itrSpecial.hasNext()) {
				DaySpecial daySpecial = (DaySpecial) itrSpecial.next();
				DaySpecial special = em.find(DaySpecial.class,
						daySpecial.getId());
				special.setDeleteFlag("Y");
				//System.out.println("special Item deleted SuccessFully");
			}
			em.getTransaction().commit();
			System.out.print("MenuItem deleted successfully....");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check MenuItem to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
	}

	@Override
	public List<MenuItem> getAllItems() throws DAOException {

		List<MenuItem> itemList = null;
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuItem> qry = em
					.createQuery("SELECT i FROM MenuItem i WHERE i.deleteFlag= 'N' and i.storeId=:storeId order by i.name",
					    MenuItem.class);
			qry.setParameter("storeId", storeId);
			itemList = qry.getResultList();

			// add sub category name of each item to the item list
			Iterator<MenuItem> itemIterator = itemList.iterator();
			while (itemIterator.hasNext()) {
				try {
					MenuItem menuItem = itemIterator.next();
					int subcatId = menuItem.getMenucategory().getId();
					// get sub category for the item
					TypedQuery<MenuCategory> qrysubcat = em
							.createQuery("SELECT c FROM MenuCategory c WHERE c.id=:id",
							    MenuCategory.class);
					qrysubcat.setParameter("id", subcatId);
					MenuCategory subCat = qrysubcat.getSingleResult();
					String subcatName = subCat.getMenuCategoryName();
					menuItem.setSubCategory(subcatName);
				} catch (Exception ex) {

				}
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to fetch", e);
		} finally {
			if(em != null) em.close();
		}
		
		return itemList;
	}

	@Override
	public MenuCategory getCategoryById(int categoryId) throws DAOException {

		MenuCategory category = null;
		EntityManager em = null;
		try {
			System.out.println("in MenuDAOImpl....");
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("menu 111");
			em.getTransaction().begin();
			//System.out.println("menu 222");
			TypedQuery<MenuCategory> qry = em
					.createQuery("SELECT m FROM MenuCategory m WHERE m.id=:categoryid",
					    MenuCategory.class);
			//System.out.println("menu 333");
			qry.setParameter("categoryid", categoryId);
			//System.out.println("menu 444");
			category = qry.getSingleResult();
			
			em.getTransaction().commit();
		}

		catch (Exception e) {
			//System.out.println("menu 888");
			e.printStackTrace();
			throw new DAOException("problem occured in MenuDAOImpl", e);

		} finally {
			if(em != null) em.close();
		}
		return category;
	}

  public List<MenuItem> getMenuItems(Integer storeTd, List<Integer> itemIds) throws DAOException {

    List<MenuItem> menuItems = null;
    EntityManager em = null;
    try {
      System.out.println("in MenuDAOImpl : getMenuItems");
      
      em = entityManagerFactory.createEntityManager();
      em.getTransaction().begin();
      TypedQuery<MenuItem> qry = em
          .createQuery("SELECT m FROM MenuItem m WHERE m.deleteFlag='N' AND m.storeId=:storeId AND m.id IN (:itemIds)",
              MenuItem.class);
      qry.setParameter("storeId", storeTd);
      qry.setParameter("itemIds", itemIds);
      menuItems = qry.getResultList();
      //System.out.println("menu 555");

      em.getTransaction().commit();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new DAOException(
          "Problem occured in MenuDAOImpl: getMenuItems", e);

    } finally {
      if(em != null) em.close();
    }
    
    return menuItems;
  }

	public MenuItem getMenuItemById(int itemId) throws DAOException {

		MenuItem menuItem = null;
		EntityManager em = null;
		try {
			System.out.println("in MenuDAOImpl : getMenuItemById");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuItem> qry = em
					.createQuery("SELECT i FROM MenuItem i WHERE i.id=:itemId",
					    MenuItem.class);
			qry.setParameter("itemId", itemId);
			menuItem = (MenuItem) qry.getSingleResult();
			//System.out.println("menu 555");

			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getMenuItemById", e);

		} finally {
			if(em != null) em.close();
		}
		
		return menuItem;
	}

	public MenuItem getItemByCode(String code, String storeId, String lang)
			throws DAOException {

		MenuItem menuItem = null;
		MenuItemLangMap itemLangMap=null;
		EntityManager em = null;
		try {
			int storeid = Integer.parseInt(storeId);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuItem> qry = em
					.createQuery("SELECT i FROM MenuItem i WHERE i.code=:code and i.storeId=:storeId and i.deleteFlag='N'",
					    MenuItem.class);
			qry.setParameter("code", code);
			qry.setParameter("storeId", storeid);
			menuItem = qry.getSingleResult();
			
			int itemId=menuItem.getId();
			
			try {
				TypedQuery<MenuItemLangMap> qry1 = em
						.createQuery("SELECT l FROM MenuItemLangMap l WHERE l.menuItem.id=:id and l.storeId=:storeId and l.language=:language",
						    MenuItemLangMap.class);
				qry1.setParameter("id", itemId);
				qry1.setParameter("storeId", storeid);
				qry1.setParameter("language", lang);
				itemLangMap = qry1.getSingleResult();
				String name=itemLangMap.getName();
				String desc=itemLangMap.getDescription();
				menuItem.setName(name);
				menuItem.setDescription(desc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			em.getTransaction().commit();
		}
		catch (Exception e) {

			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getMenuItemById", e);

		} finally {
			if(em != null) em.close();
		}
		return menuItem;
	}

	@Override
	public List<MenuCategory> getCategoryByStore(String storeId)
			throws DAOException {
		List<MenuCategory> catList = new ArrayList<MenuCategory>();
		int storeid1 = Integer.parseInt(storeId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EntityManager em = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			Session session = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
			con = sessionFactory.getConnectionProvider().getConnection();
      
			String type1 = "c";
			String delFlag = "N";

			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.deleteFlag='N' AND m.storeId=:param and m.type='c'");
			String GET_CAT_LIST = "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=?";

			ps = con.prepareStatement(GET_CAT_LIST);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setString(3, delFlag);
			rs = ps.executeQuery();

			while (rs.next()) {
				MenuCategory menuCategory = new MenuCategory();
				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));

				catList.add(menuCategory);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getCategoryByStore", e);

		}
    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (con != null) {
          con.close();
        }
        if(em != null) em.close();
      }
      catch(Exception e) {
      }
    }
		
		return catList;
	}

	@Override
	public List<MenuCategory> getCategoryByStoreExcludeSpclNote(String storeId)
			throws DAOException {
		List<MenuCategory> catList = new ArrayList<MenuCategory>();
		int storeid1 = Integer.parseInt(storeId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EntityManager em = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			String type1 = "c";
			String delFlag = "N";

			String GET_CAT_LIST = "select * from fm_m_food_types where store_id = ? and type=? and menu_item_name not like '%SPECIAL NOTE%' and delete_flag=?";

			ps = con.prepareStatement(GET_CAT_LIST);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setString(3, delFlag);
			rs = ps.executeQuery();

			while (rs.next()) {
				MenuCategory menuCategory = new MenuCategory();
				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));

				catList.add(menuCategory);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getCategoryByStore", e);
		}
    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (con != null) {
          con.close();
        }
        if(em != null) em.close();
      }
      catch(Exception e) {
      }
    }
		
		return catList;
	}

	@Override
	public MenuCategory getCategoryDetailsById(String id, String storeId)
			throws DAOException {
		MenuCategory menuCategory = new MenuCategory();
		int storeid1 = Integer.parseInt(storeId);
		int id1 = Integer.parseInt(id);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			String type1 = "c";
			String delFlag = "N";

			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.deleteFlag='N' AND m.storeId=:param and m.type='c'");
			String GET_CAT_DETAILS = "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=? and Id=?";

			ps = con.prepareStatement(GET_CAT_DETAILS);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setString(3, delFlag);
			ps.setInt(4, id1);
			rs = ps.executeQuery();

			while (rs.next()) {

				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));
			}

			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if(em != null) em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getCategoryDetailsById", e);

		}
		return menuCategory;
	}

	@Override
	public List<MenuCategory> getDirectCategoryDetailsByStore(String storeId)
			throws DAOException {
		List<MenuCategory> menuCategoryList = new ArrayList<MenuCategory>();
		int storeid1 = Integer.parseInt(storeId);
		// int id1 = Integer.parseInt(id);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EntityManager em = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			String type1 = "c";
			String delFlag = "N";
			String directCat = "Y";

			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.deleteFlag='N' AND m.storeId=:param and m.type='c'");
			String GET_CAT_DETAILS = "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=? and direct_cat=?";

			ps = con.prepareStatement(GET_CAT_DETAILS);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setString(3, delFlag);
			ps.setString(4, directCat);
			rs = ps.executeQuery();

			while (rs.next()) {
				MenuCategory menuCategory = new MenuCategory();

				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));
				menuCategory.setDirectCat(rs.getString("direct_cat"));

				menuCategoryList.add(menuCategory);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getCategoryDetailsById", e);

		}
    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (con != null) {
          con.close();
        }
        if(em != null) em.close();
      }
      catch(Exception e) {
      }
    }
		
		return menuCategoryList;
	}
	
	@Override
	public List<MenuCategory> getDirectCategoryByStore(String storeId)
			throws DAOException {
		List<MenuCategory> menuCategoryList = new ArrayList<MenuCategory>();
		int storeid1 = Integer.parseInt(storeId);
		// int id1 = Integer.parseInt(id);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EntityManager em = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			String type1 = "c";
			String delFlag = "N";

			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.deleteFlag='N' AND m.storeId=:param and m.type='c'");
			String GET_CAT_DETAILS = "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=?";

			ps = con.prepareStatement(GET_CAT_DETAILS);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setString(3, delFlag);
			//ps.setString(4, directCat);
			rs = ps.executeQuery();

			while (rs.next()) {
				MenuCategory menuCategory = new MenuCategory();

				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));
				menuCategory.setDirectCat(rs.getString("direct_cat"));

				menuCategoryList.add(menuCategory);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getCategoryDetailsById", e);

		}
    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (con != null) {
          con.close();
        }
        if(em != null) em.close();
      }
      catch(Exception e) {
      }
    }
		
		return menuCategoryList;
	}

	@Override
	public List<MenuCategory> getSubCategoryByStore(String storeId, String catId)
			throws DAOException {
		List<MenuCategory> subCatList = new ArrayList<MenuCategory>();
		int storeid1 = Integer.parseInt(storeId);
		int catId1 = Integer.parseInt(catId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EntityManager em = null;
		    
		try {
			
			em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			String type1 = "s";
			String delFlag = "N";
			String GET_SUBCAT_LIST=null;
			if(!"0".equals(catId)) {
			 GET_SUBCAT_LIST = "select * from fm_m_food_types where store_id = ? and type=? and Parent_item_type_Id=? and delete_flag=?";
			}else {
			 GET_SUBCAT_LIST = "select * from fm_m_food_types where store_id = ? and type=? and Parent_item_type_Id<>? and delete_flag=?";
			}
			ps = con.prepareStatement(GET_SUBCAT_LIST);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setInt(3, catId1);
			ps.setString(4, delFlag);
			rs = ps.executeQuery();

			while (rs.next()) {
				MenuCategory menuCategory = new MenuCategory();
				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));
				menuCategory.setCategoryId(Integer.toString((rs
						.getInt("Parent_item_type_Id"))));

				subCatList.add(menuCategory);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getCategoryByStore", e);

		}
		finally {
		  try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (con != null) {
          con.close();
        }
        if(em != null) em.close();
		  }
		  catch(Exception e) {
		  }
		}
		
		return subCatList;
	}

	@Override
	public MenuItemLangMap getMenuItemLang(int itemId, String language)
			throws DAOException {
		MenuItemLangMap menuItem = null;
		EntityManager em = null;
		try {
			//System.out.println("in MenuDAOImpl : getMenuItemLang");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<MenuItemLangMap> qry = em
					.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.menuItem.id=:itemId and i.language=:language",
					    MenuItemLangMap.class);
			qry.setParameter("itemId", itemId);
			qry.setParameter("language", language);
			menuItem = qry.getSingleResult();
			//System.out.println("menu 555");

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getMenuItemById", e);

		} finally {
			if(em != null) em.close();
		}
		return menuItem;
	}

	@Override
	public List<MenuItemLangMap> getTranslatedMenuItem(int storeId,
			String language) throws DAOException {
		List<MenuItemLangMap> itemLangMaps = new ArrayList<MenuItemLangMap>();
		EntityManager em = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String delFlag = "N";
		try {

			
			em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			em.getTransaction().begin();

			String GET_TRANSLATED_MENU = "SELECT fi.id as item_id,fl.id as id, fi.name as eng_name,fi.description as eng_desc,fl.store_id, fl.language, fl.name, fl.description FROM fm_m_food_items fi LEFT OUTER JOIN fm_m_food_items_lang_c fl ON fi.id = fl.item_id and fi.store_id=fl.store_id and fl.language=? where fi.store_id = ? and fi.delete_flag=?";
			ps = con.prepareStatement(GET_TRANSLATED_MENU);
			ps.setString(1, language);
			ps.setInt(2, storeId);
			ps.setString(3, delFlag);
			rs = ps.executeQuery();

			while (rs.next()) {
				MenuItemLangMap itemLangMap = new MenuItemLangMap();
				itemLangMap.setId(rs.getInt("id"));
				itemLangMap.setItemId(rs.getInt("item_id"));
				itemLangMap.setStoreId(storeId);
				itemLangMap.setLanguage(rs.getString("language"));
				itemLangMap.setName(rs.getString("name"));
				itemLangMap.setDescription(rs.getString("description"));
				itemLangMap.setEnglishName(rs.getString("eng_name"));
				itemLangMap.setEnglishDesc(rs.getString("eng_desc"));

				itemLangMaps.add(itemLangMap);

			}

			em.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getMenuItemById", e);

		}

		finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(em != null) em.close();
		}
		return itemLangMaps;
	}

	@Override
	public List<MenuCategoryLangMap> getTranslatedMenuCatNSubcat(int storeId,
			String language) throws DAOException {
		List<MenuCategoryLangMap> catLangMaps = new ArrayList<MenuCategoryLangMap>();
		EntityManager em = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String delFlag = "N";
		try {

			
			em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			em.getTransaction().begin();

			String GET_TRANSLATED_MENU_CAT = "SELECT fl.id, fi.id cat_subcat_id, fi.menu_item_name eng_name,fl.store_id,fl.lang, fl.menu_item_name FROM fm_m_food_types fi LEFT OUTER JOIN fm_m_food_types_lang_c fl ON fi.id = fl.food_type_id and fi.store_id=fl.store_id and fl.lang=? where fi.store_id = ? and fi.delete_flag=?";
			ps = con.prepareStatement(GET_TRANSLATED_MENU_CAT);
			ps.setString(1, language);
			ps.setInt(2, storeId);
			ps.setString(3, delFlag);
			rs = ps.executeQuery();

			while (rs.next()) {
				MenuCategoryLangMap catLangMap = new MenuCategoryLangMap();
				catLangMap.setId(rs.getInt("id"));
				catLangMap.setCatSubCatId(rs.getInt("cat_subcat_id"));
				catLangMap.setStoreId(storeId);
				catLangMap.setMenuItemName(rs.getString("menu_item_name"));
				catLangMap.setEnglishName(rs.getString("eng_name"));
				catLangMap.setLanguage(rs.getString("lang"));

				catLangMaps.add(catLangMap);

			}

			em.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getMenuItemById", e);

		}

		finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(em != null) em.close();
		}
		return catLangMaps;
	}

	@Override
	public List<DaySpecial> getDaySpecialbyItemId(int itemId)
			throws DAOException {
		List<DaySpecial> specialItemList = null;
		EntityManager em = null;
		try {
			//System.out.println("in MenuDAOImpl : getDaySpecialbyItemId");
			
			em = entityManagerFactory.createEntityManager();
			TypedQuery<DaySpecial> qry = em
					.createQuery("SELECT d FROM DaySpecial d WHERE d.item.id=:itemId",
					    DaySpecial.class);
			qry.setParameter("itemId", itemId);
			specialItemList = qry.getResultList();
			//System.out.println("daySpecial fetched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getDaySpecialbyItemId", e);
		} finally {
			if(em != null) em.close();
		}
		
		return specialItemList;
	}

	@Override
	public List<MasterLanguage> getAllLanguages() throws DAOException {
		List<MasterLanguage> languages = new ArrayList<MasterLanguage>();
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			//System.out.println("in MenuDAOImpl : getAllLanguages");
			
			em = entityManagerFactory.createEntityManager();
			TypedQuery<MasterLanguage> qry = em
					.createQuery("SELECT l FROM MasterLanguage l WHERE l.store.id=:storeId",
					    MasterLanguage.class);
			qry.setParameter("storeId", storeId);
			languages = qry.getResultList();
			System.out.println("languages fetched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getAllLanguages", e);
		} finally {
			if(em != null) em.close();
		}
		return languages;
	}

	@Override
	public List<MasterLanguage> getLanguageListByStoreId(String storeId)
			throws DAOException {
		List<MasterLanguage> languageList = null;
		int storeid1 = Integer.parseInt(storeId);
		StoreMaster storeMaster = new StoreMaster();
		storeMaster.setId(storeid1);
		try {
			//System.out.println("MenuDAOImpl.getLanguageListByStoreId called.");
			
			EntityManager em = entityManagerFactory.createEntityManager();
			TypedQuery<MasterLanguage> qry = em
					.createQuery("SELECT m FROM MasterLanguage m WHERE m.deleteFlag='Y' AND m.store=:param",
					    MasterLanguage.class);
			qry.setParameter("param", storeMaster);
			languageList = qry.getResultList();
			if(em != null) em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getLanguageListByStoreId",
					e);

		}
		return languageList;
	}

	@Override
	public List<ColorMaster> getAllColors() throws DAOException {
		List<ColorMaster> colorList = null;
		EntityManager em = null;

		try {
			
			em = entityManagerFactory.createEntityManager();
			TypedQuery<ColorMaster> qry = em
					.createQuery("SELECT c FROM ColorMaster c WHERE c.deleteFlag='N'",
					    ColorMaster.class);

			colorList = qry.getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getLanguageListByStoreId",
					e);
		} finally {
			if(em != null) em.close();
		}
		return colorList;
	}

	@Override
	public List<MenuItem> getMenuItemsByStore(String storeId, String subcatId)
			throws DAOException {
	  
		List<MenuItem> itemList = null;
		int storeid1 = Integer.parseInt(storeId);
		int subcatId1 = Integer.parseInt(subcatId);
		StoreMaster storeMaster = new StoreMaster();
		storeMaster.setId(storeid1);
		
    EntityManager em = null;
    
		try {
			
			em = entityManagerFactory.createEntityManager();
			TypedQuery<MenuItem> qry = em
					.createQuery("SELECT m FROM MenuItem m WHERE m.deleteFlag='N' AND m.storeId=:param and m.menucategory.id=:subcatId",
					    MenuItem.class);
			qry.setParameter("param", storeid1);
			qry.setParameter("subcatId", subcatId1);
			itemList = qry.getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getLanguageListByStoreId",
					e);

		}
		finally {
      if(em != null) em.close();
		}
		return itemList;
	}

	public List<MenuItem> getMenuItemsByStoreId(String storeId)
			throws DAOException {
		List<MenuItem> itemList = null;
		int storeid1 = Integer.parseInt(storeId);
		StoreMaster storeMaster = new StoreMaster();
		storeMaster.setId(storeid1);
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			TypedQuery<MenuItem> qry = em
					.createQuery("SELECT m FROM MenuItem m WHERE m.deleteFlag='N' AND m.storeId=:param", 
					    MenuItem.class);
			qry.setParameter("param", storeid1);
			itemList = qry.getResultList();
			if(em != null) em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getLanguageListByStoreId",
					e);

		}
		return itemList;
	}

	@Override
	public List<MenuItem> getMenuItemsBySubCatName(String storeId, String name)
			throws DAOException {
		List<MenuItem> itemList = null;
		int storeid1 = Integer.parseInt(storeId);
		int subCatId;
		String nameWithoutSpace = name.trim();
		// int subcatId1 = Integer.parseInt(subcatId);
		StoreMaster storeMaster = new StoreMaster();
		storeMaster.setId(storeid1);
		try {

			
			EntityManager em = entityManagerFactory.createEntityManager();

			// get the parent type id for this category
			Query qry = em
					.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeId and m.deleteFlag='N' and m.menuCategoryName=:name");
			qry.setParameter("storeId", storeid1);
			qry.setParameter("name", nameWithoutSpace);
			MenuCategory category = (MenuCategory) qry.getSingleResult();
			// get the id
			subCatId = category.getId();

			itemList = getMenuItemsByStore(storeid1 + "", subCatId + "");
			if(em != null) em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getMenuItemsBySubCatName",
					e);

		}
		return itemList;
	}

	@Override
	public String addCategory(String name, String storeId, String bgColor)
			throws DAOException {
		// TODO Auto-generated method stub
		String status = "";
		int parentCatId = 0;
		EntityManager em = null;
		try {

			int storeid = Integer.parseInt(storeId);
			String bg_color = null;
			if (bgColor != null && bgColor != "")
				bg_color = "#" + bgColor;
			String catName = name;

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get the parent type id for this category
			Query qry = em
					.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeId and m.deleteFlag='N' and m.type='r'");
			qry.setParameter("storeId", storeid);
			MenuCategory category = (MenuCategory) qry.getSingleResult();
			// get the id
			parentCatId = category.getId();

			Query query = em
					.createNativeQuery("INSERT INTO fm_m_food_types (Menu_Item_name, Parent_item_type_Id, store_id,type,bg_color) "
							+ " VALUES(?, ?,?,?,?)");
			query.setParameter(1, catName);
			query.setParameter(2, parentCatId);
			query.setParameter(3, storeid);
			query.setParameter(4, 'c');
			query.setParameter(5, bg_color);
			query.executeUpdate();

			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String addCategoryPost(MenuCategory category1) throws DAOException {
		// TODO Auto-generated method stub
		String status = "";
		int parentCatId = 0;
		EntityManager em = null;
		try {

			int storeid = category1.getStoreId();
			String bg_color = null;
			bg_color = category1.getBgColor();
			if (bg_color != null && bg_color != "")
				bg_color = "#" + bg_color;
			String catName = category1.getMenuCategoryName();

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get the parent type id for this category
			Query qry = em
					.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeId and m.deleteFlag='N' and m.type='r'");
			qry.setParameter("storeId", storeid);
			MenuCategory category = (MenuCategory) qry.getSingleResult();
			// get the id
			parentCatId = category.getId();

			Query query = em
					.createNativeQuery("INSERT INTO fm_m_food_types (Menu_Item_name, Parent_item_type_Id, store_id,type,bg_color) "
							+ " VALUES(?, ?,?,?,?)");
			query.setParameter(1, catName);
			query.setParameter(2, parentCatId);
			query.setParameter(3, storeid);
			query.setParameter(4, 'c');
			query.setParameter(5, bg_color);
			query.executeUpdate();

			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String addTranslatedMenuItems(MenuItemLangMap itemLangMap)
			throws DAOException {
		// TODO Auto-generated method stub
		String status = "";
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			List<MenuItemLangMap> itemLangMaps = itemLangMap.getItemLangMaps();
			Iterator<MenuItemLangMap> iterator = itemLangMaps.iterator(); 
			while (iterator.hasNext()) {
				MenuItemLangMap menuItemLangMap = iterator.next();
				int id = menuItemLangMap.getId();
				MenuItemLangMap ietmLang = em.find(MenuItemLangMap.class, id);
				if (ietmLang == null) {
					em.persist(menuItemLangMap);
				} else {
					ietmLang.setDescription(menuItemLangMap.getDescription());
					ietmLang.setEnglishDesc(menuItemLangMap.getEnglishDesc());
					ietmLang.setEnglishName(menuItemLangMap.getEnglishName());
					ietmLang.setLanguage(menuItemLangMap.getLanguage());
					ietmLang.setName(menuItemLangMap.getName());
					em.merge(ietmLang);
				}
				
			}
			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public String addTranslatedMenuCatNSubCat(MenuCategoryLangMap catLangMap)
			throws DAOException {
		// TODO Auto-generated method stub
		String status = "";
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			List<MenuCategoryLangMap> catLangMaps = catLangMap.getCategoryLangMaps();
			Iterator<MenuCategoryLangMap> iterator = catLangMaps.iterator();
			while ( iterator.hasNext()) {
				MenuCategoryLangMap menuCatLangMap = iterator.next();
				int id = menuCatLangMap.getId();
				MenuCategoryLangMap catLang = em.find(MenuCategoryLangMap.class, id);
				if (catLang == null) {
					em.persist(menuCatLangMap);
				} else {
					catLang.setEnglishName(menuCatLangMap.getEnglishName());
					catLang.setLanguage(menuCatLangMap.getLanguage());
					catLang.setMenuItemName(menuCatLangMap.getMenuItemName());
					em.merge(catLang);
				}
				
			}
			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return status;
	}

	@Override
	public String updateCategory(MenuCategory itemType) throws DAOException {

		String status = "";
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuCategory itemType1 = em.find(MenuCategory.class,
					itemType.getId());
			itemType1.setMenuCategoryName(itemType.getMenuCategoryName());
			itemType1.setBgColor(itemType.getBgColor());
			em.merge(itemType1);
			em.getTransaction().commit();
			status = "success";
			System.out.print("Menu Category updated successfully....");

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public String deleteCategory(MenuCategory itemType) throws DAOException {

		List<MenuItem> itemList = null;
		List<MenuCategory> menuList = null;
		String status = "";
		EntityManager em = null;
		
		try {
			int storeId = itemType.getStoreId();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuCategory itemType1 = em.find(MenuCategory.class, itemType.getId());
			itemType1.setDeleteFlag("Y");

			int cat_subcat_id = itemType1.getId();
			// check if there is any data with this id in the cat/sub-cat table
			TypedQuery<MenuCategory> qryGetCat = em
					.createQuery("SELECT m FROM MenuCategory m where m.storeId=:storeId and m.deleteFlag='N' and m.menutype.id=:parentcatId",
					    MenuCategory.class);
			qryGetCat.setParameter("storeId", storeId);
			qryGetCat.setParameter("parentcatId", cat_subcat_id);
			menuList = qryGetCat.getResultList();
			if (menuList != null && menuList.size() > 0) {
				// deleting element is a category, so delete all sub-categories
				Iterator<MenuCategory> cateIterator = menuList.iterator();
				// get each sub-category and delete corresponding items
				while (cateIterator.hasNext()) {
					MenuCategory subCategory = cateIterator.next();
					int subCatId = subCategory.getId();
					subCategory.setDeleteFlag("Y");
					TypedQuery<MenuItem> qry = em
							.createQuery("SELECT i FROM MenuItem i WHERE i.deleteFlag= 'N' and i.storeId=:storeId and i.menucategory.id=:id",
							    MenuItem.class);
					qry.setParameter("storeId", storeId);
					qry.setParameter("id", subCatId);
					itemList = qry.getResultList();
					Iterator<MenuItem> itemIterator = itemList.iterator();
					while (itemIterator.hasNext()) {
						MenuItem menuItem = itemIterator.next();
						menuItem.setDeleteFlag("Y");
					}
				}
			} else {
				// delete items for the sub-category
				TypedQuery<MenuItem> qry = em
						.createQuery("SELECT i FROM MenuItem i WHERE i.deleteFlag= 'N' and i.storeId=:storeId and i.menucategory.id=:id",
						    MenuItem.class);
				qry.setParameter("storeId", storeId);
				qry.setParameter("id", cat_subcat_id);
				itemList = qry.getResultList();
				Iterator<MenuItem> itemIterator = itemList.iterator();
				while (itemIterator.hasNext()) {
					MenuItem menuItem = itemIterator.next();
					menuItem.setDeleteFlag("Y");
				}
			}
			em.getTransaction().commit();
			status = "success";
			System.out.print("Menu Category deleted successfully....");
		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		}
		finally {
		  if(em != null) em.close();
		}

		return status;

	}

	@Override
	public String addSubCategory(String name, String storeId, String bgColor,
			String catId) throws DAOException {
		// TODO Auto-generated method stub
		String status = "";
		int parentCatId = Integer.parseInt(catId);
		EntityManager em = null;
		try {

			int storeid = Integer.parseInt(storeId);
			String bg_color = "#" + bgColor;
			String subCatName = name;

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			/*
			 * //get the parent type id for this category Query qry =
			 * em.createQuery(
			 * "SELECT m FROM MenuCategory m WHERE m.storeId=:storeId and m.deleteFlag='N' and m.type='r'"
			 * ); qry.setParameter("storeId", storeid); MenuCategory category =
			 * (MenuCategory) qry.getSingleResult(); //get the id parentCatId =
			 * category.getId();
			 */

			Query query = em
					.createNativeQuery("INSERT INTO fm_m_food_types (Menu_Item_name, Parent_item_type_Id, store_id,type,bg_color) "
							+ " VALUES(?, ?,?,?,?)");
			query.setParameter(1, subCatName);
			query.setParameter(2, parentCatId);
			query.setParameter(3, storeid);
			query.setParameter(4, 's');
			query.setParameter(5, bg_color);
			query.executeUpdate();

			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String addSubCategoryPost(MenuCategory subCat) throws DAOException {
		// TODO Auto-generated method stub
		String status = "";
		int parentCatId = Integer.parseInt(subCat.getParentCatId());
		EntityManager em = null;
		try {

			int storeid = subCat.getStoreId();
			String bg_color = "#" + subCat.getBgColor();
			String subCatName = subCat.getMenuCategoryName();

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("INSERT INTO fm_m_food_types (Menu_Item_name, Parent_item_type_Id, store_id,type,bg_color) "
							+ " VALUES(?, ?,?,?,?)");
			query.setParameter(1, subCatName);
			query.setParameter(2, parentCatId);
			query.setParameter(3, storeid);
			query.setParameter(4, 's');
			query.setParameter(5, bg_color);
			query.executeUpdate();

			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String updateSubCategory(MenuCategory itemType) throws DAOException {

		String status = "";
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuCategory itemType1 = em.find(MenuCategory.class,
					itemType.getId());
			itemType1.setMenuCategoryName(itemType.getMenuCategoryName());
			itemType1.setBgColor(itemType.getBgColor());
			em.merge(itemType1);
			em.getTransaction().commit();
			status = "success";
			System.out.print("Menu Category updated successfully....");

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public String deleteSubCategory(MenuCategory itemType) throws DAOException {

		List<MenuItem> itemList = null;
		List<MenuCategory> menuList = null;
		String status = "";
		EntityManager em = null;
		try {
			int storeId = itemType.getStoreId();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuCategory itemType1 = em.find(MenuCategory.class,itemType.getId());
			itemType1.setDeleteFlag("Y");

			int cat_subcat_id = itemType1.getId();
			// check if there is any data with this id in the cat/sub-cat table
			TypedQuery<MenuCategory> qryGetCat = em
					.createQuery("SELECT m FROM MenuCategory m where m.storeId=:storeId and m.deleteFlag='N' and m.menutype.id=:parentcatId",
					    MenuCategory.class);
			qryGetCat.setParameter("storeId", storeId);
			qryGetCat.setParameter("parentcatId", cat_subcat_id);
			menuList = qryGetCat.getResultList();
			if (menuList != null && menuList.size() > 0) {
				// deleting element is a category, so delete all sub-categories
				Iterator<MenuCategory> cateIterator = menuList.iterator();
				// get each sub-category and delete corresponding items
				while (cateIterator.hasNext()) {
					MenuCategory subCategory = cateIterator.next();
					int subCatId = subCategory.getId();
					subCategory.setDeleteFlag("Y");
					TypedQuery<MenuItem> qry = em
							.createQuery("SELECT i FROM MenuItem i WHERE i.deleteFlag= 'N' and i.storeId=:storeId and i.menucategory.id=:id",
							    MenuItem.class);
					qry.setParameter("storeId", storeId);
					qry.setParameter("id", subCatId);
					itemList = qry.getResultList();
					Iterator<MenuItem> itemIterator = itemList.iterator();
					while (itemIterator.hasNext()) {
						MenuItem menuItem = itemIterator.next();
						menuItem.setDeleteFlag("Y");
					}
				}

			} else {
				// delete items for the sub-category
			  TypedQuery<MenuItem> qry = em
						.createQuery("SELECT i FROM MenuItem i WHERE i.deleteFlag= 'N' and i.storeId=:storeId and i.menucategory.id=:id",
						    MenuItem.class);
				qry.setParameter("storeId", storeId);
				qry.setParameter("id", cat_subcat_id);
				itemList = qry.getResultList();
				Iterator<MenuItem> itemIterator = itemList.iterator();
				while (itemIterator.hasNext()) {
					MenuItem menuItem = itemIterator.next();
					menuItem.setDeleteFlag("Y");
				}
			}
			em.getTransaction().commit();
			status = "success";
			System.out.print("Sub Category deleted successfully....");
		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be deleted", e);
		}
		finally {
		  if(em != null) em.close();
		}

		return status;

	}

	@Override
	public MenuCategory getSubCategoryDetailsById(String id, String storeId,
			String catId) throws DAOException {
		MenuCategory menuCategory = new MenuCategory();
		int storeid1 = Integer.parseInt(storeId);
		int id1 = Integer.parseInt(id);
		int catId1 = Integer.parseInt(catId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			String type1 = "s";
			String delFlag = "N";

			String GET_SUB_CAT_DETAILS = "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=? and Id=? and Parent_item_type_Id=?";

			ps = con.prepareStatement(GET_SUB_CAT_DETAILS);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setString(3, delFlag);
			ps.setInt(4, id1);
			ps.setInt(5, catId1);
			rs = ps.executeQuery();

			while (rs.next()) {

				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));
				// MenuCategory parentCat = new MenuCategory();
				// parentCat.setId(rs.getInt("Parent_item_type_Id"));
				menuCategory.setCategoryId(Integer.toString(rs
						.getInt("Parent_item_type_Id")));
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getSubCategoryDetailsById",
					e);

		}
    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (con != null) {
          con.close();
        }
        if(em != null) em.close();
      }
      catch(Exception e) {
      }
    }
		return menuCategory;
	}

	@Override
	public MenuCategory getSubCategoryForSpecialNote(String storeId)
			throws DAOException {
		MenuCategory menuCategory = new MenuCategory();
		int storeid1 = Integer.parseInt(storeId);
		// int id1 = Integer.parseInt(id);
		// int catId1 = Integer.parseInt(catId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		String menuName = "REQUEST EXTRA";
		
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			String type1 = "s";
			String delFlag = "N";

			String GET_SUB_CAT_DETAILS = "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=? and menu_item_name like 'REQUEST EXTRA'";

			ps = con.prepareStatement(GET_SUB_CAT_DETAILS);
			ps.setInt(1, storeid1);
			ps.setString(2, type1);
			ps.setString(3, delFlag);

			rs = ps.executeQuery();

			while (rs.next()) {

				menuCategory.setId(rs.getInt("Id"));
				menuCategory
						.setMenuCategoryName(rs.getString("Menu_Item_name"));
				menuCategory.setBgColor(rs.getString("bg_color"));
				menuCategory.setDeleteFlag(rs.getString("delete_flag"));
				menuCategory.setType(rs.getString("type"));
				menuCategory.setDirectCat(rs.getString("direct_cat"));
				menuCategory.setPrintStatus(rs.getString("print_status"));
				menuCategory.setStoreId(rs.getInt("store_id"));
				// MenuCategory parentCat = new MenuCategory();
				// parentCat.setId(rs.getInt("Parent_item_type_Id"));
				menuCategory.setCategoryId(Integer.toString(rs
						.getInt("Parent_item_type_Id")));
			}

			/*
			 * Query qry = em .createQuery(
			 * "SELECT m FROM MenuCategory m WHERE m.storeId=:storeId and m.menuCategoryName=:menuCategoryName"
			 * ); qry.setParameter("storeId", storeid1);
			 * qry.setParameter("menuCategoryName", menuName); menuCategory =
			 * (MenuCategory) qry.getSingleResult();
			 */

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getSubCategoryDetailsById",
					e);

		}
		finally {
		  try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (con != null) {
          con.close();
        }
        if(em != null) em.close();
		  }
		  catch(Exception e) {
		  }
		}

		return menuCategory;
	}

	@Override
	public String addMenuItem(MenuItem item) throws DAOException {
		String status = "";

		MenuItem menuItm = new MenuItem();
		EntityManager em = null;
		try {

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			menuItm.setName(item.getName());
			menuItm.setDescription(item.getDescription());
			menuItm.setPrice(item.getPrice());
			menuItm.setMenucategory(item.getMenucategory());
			menuItm.setDeleteFlag("N");
			menuItm.setVeg(item.getVeg());
			menuItm.setStoreId(item.getStoreId());
			menuItm.setPromotionDesc(item.getPromotionDesc());
			menuItm.setPromotionFlag(item.getPromotionFlag());
			menuItm.setPromotionValue(item.getPromotionValue());
			menuItm.setBeverages(item.getBeverages());
			menuItm.setDesert(item.getDesert());
			menuItm.setFoodOption1(item.getFoodOption1());
			menuItm.setFoodOption2(item.getFoodOption2());
			menuItm.setFoodOptionFlag("N");
			menuItm.setSpicy(item.getSpicy());
			menuItm.setHouseSpecial(item.getHouseSpecial());
			menuItm.setServiceTax(item.getServiceTax());
			menuItm.setVat(item.getVat());
			menuItm.setCookingTimeInMins(item.getCookingTimeInMins());

			if ((item.getUnit() == null)
					|| (item.getUnit().equalsIgnoreCase(""))) {
				menuItm.setUnit("plate");
			} else {
				menuItm.setUnit(item.getUnit());
			}
			menuItm.setProduction("Y");
			menuItm.setPrinterId(item.getPrinterId());

			if (item.getCode() != null) {
				menuItm.setCode(item.getCode());
			}

			// set current date and time

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNTime = dateFormat.format(date);

			// start calculate time of order
			if (dateNTime != null && dateNTime.length() > 0) {
				String[] tempURL1 = dateNTime.split(" ");
				String creationDate = tempURL1[0];
				String creationTime = tempURL1[1];
				String[] timefull = creationTime.split(":");
				String hr = timefull[0];
				String mins = timefull[1];
				String hrMins = hr + ":" + mins;

				menuItm.setCreationDate(creationDate);
				menuItm.setCreationTime(hrMins);
			}
			// end calculate time of order

			if (item.getSpotDiscount() == null || item.getSpotDiscount() == "") {
				menuItm.setSpotDiscount("Y");
			} else {
				menuItm.setSpotDiscount(item.getSpotDiscount());
			}

			if (item.getBgColor() == null || item.getBgColor() == "") {
				menuItm.setBgColor("#ffffff");
			} else {
				menuItm.setBgColor(item.getBgColor());
			}
			menuItm.setIsKotPrint(item.getIsKotPrint());
			menuItm.setPurchasePrice(item.getPurchasePrice());
			em.persist(menuItm);
			if (item.getCode() == null || item.getCode().trim().length() == 0) {
				menuItm.setCode("" + menuItm.getId());
				em.merge(menuItm);
			}

			em.getTransaction().commit();
			status = "success";
			System.out.print("Menu item added successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		return status;

	}

	@Override
	public String updateTaxesForAllItems(TaxesForAllItems taxesForAllItems)
			throws DAOException {
		String status = "";
		EntityManager em = null;
		StoreAddressDAOImpl addressDAOImpl = new StoreAddressDAOImpl();
		try {

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get store
			int storeId = taxesForAllItems.getStoreId();
			// set taxes for all items in the store
			String vatTaxText = taxesForAllItems.getVatTaxText();
			String serviceTaxText = taxesForAllItems.getServiceTaxText();
			StoreMaster storeMaster = addressDAOImpl.getStoreByStoreId(storeId);
			if (vatTaxText != null && !vatTaxText.equalsIgnoreCase("")
					&& vatTaxText.length() > 0) {
				storeMaster.setVatTaxText(taxesForAllItems.getVatTaxText());
				storeMaster.setVatAmt(taxesForAllItems.getVatAmt());

			}
			if (serviceTaxText != null && !serviceTaxText.equalsIgnoreCase("")
					&& serviceTaxText.length() > 0) {
				storeMaster.setServiceTaxText(taxesForAllItems
						.getServiceTaxText());
				storeMaster.setServiceTaxAmt(taxesForAllItems
						.getServiceTaxAmt());
			}

			em.merge(storeMaster);

			if (vatTaxText != null && !vatTaxText.equalsIgnoreCase("")
					&& vatTaxText.length() > 0) {

				Query query = em
						.createNativeQuery("update fm_m_food_items set vat=? where store_id=? and Price>0.0");
				query.setParameter(1, taxesForAllItems.getVatAmt());
				query.setParameter(2, storeId);
				query.executeUpdate();

			}

			if (serviceTaxText != null && !serviceTaxText.equalsIgnoreCase("")
					&& serviceTaxText.length() > 0) {

				Query query = em
						.createNativeQuery("update fm_m_food_items set service_tax=? where store_id=? and Price>0.0");
				query.setParameter(1, taxesForAllItems.getServiceTaxAmt());
				query.setParameter(2, storeId);
				query.executeUpdate();

			}
			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		return status;

	}

	@Override
	public String deleteMenuItem(MenuItem item) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			MenuItem item1 = em.find(MenuItem.class, item.getId());
			item1.setDeleteFlag("Y");
			List<DaySpecial> daySpecialList = getDaySpecialbyItemId(item
					.getId());
			if (daySpecialList != null && daySpecialList.size() > 0) {
				Iterator<DaySpecial> itrSpecial = daySpecialList.iterator();
				while (itrSpecial.hasNext()) {
					DaySpecial daySpecial = (DaySpecial) itrSpecial.next();
					DaySpecial special = em.find(DaySpecial.class,
							daySpecial.getId());
					special.setDeleteFlag("Y");
					System.out.println("special Item deleted SuccessFully");
				}
			}
			em.getTransaction().commit();
			status = "success";
			System.out.print("MenuItem deleted successfully....");
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check MenuItem to be deleted", e);
		} finally {
			if(em != null) em.close();
		}

		return status;
	}

	public MenuItem getItemDetailsById(Integer itemId, Integer storeId)	throws DAOException {

		MenuItem menuItem = null;
		EntityManager em = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			
			em.getTransaction().begin();
			TypedQuery<MenuItem> qry = em.createQuery("SELECT i FROM MenuItem i WHERE i.id=:itemId and i.storeId=:storeId",
					    MenuItem.class);
			qry.setParameter("itemId", itemId);
			qry.setParameter("storeId", storeId);
			menuItem = qry.getSingleResult();

			// calculate current stock and put in menu item
			/*
			 * StoreMaster
			 * store=addressDAO.getStoreByStoreId(Integer.parseInt(storeId));
			 * String isDisplayCrntStck=store.getDisplayCurrentStockMenu();
			 * if(isDisplayCrntStck.equalsIgnoreCase("Y")){ Map<Integer, Double>
			 * mapHoldItemCrntStck=new HashMap<Integer, Double>(); String
			 * curntStockProc = "{call get_item_wise_cur_stock(?,?)}";
			 * callableStatement = connection.prepareCall(curntStockProc);
			 * callableStatement.setInt(1, Integer.parseInt(storeId));
			 * callableStatement.setInt(2, menuItem.getId());
			 * 
			 * callableStatement.execute(); rs=callableStatement.getResultSet();
			 * 
			 * while (rs.next()) { int itemid=rs.getInt("item_id");
			 * System.out.println(" itm id: "+itemid); double
			 * curStock=rs.getDouble("cur_stock");
			 * menuItem.setCurrentStock(curStock); }
			 * 
			 * 
			 * }
			 */

			em.getTransaction().commit();

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getItemDetailsById", e);
		} finally {
			if(em != null) em.close();
		}
		return menuItem;
	}
	
  public List<MenuItem> getAllItemDetails(int storeId) throws DAOException {

    List<MenuItem> menuItems = null;
    EntityManager em = null;
    
    try {
      
      em = entityManagerFactory.createEntityManager();
      
      em.getTransaction().begin();
      TypedQuery<MenuItem> qry = em.createQuery("SELECT i FROM MenuItem i WHERE i.storeId=:storeId",
              MenuItem.class);
      qry.setParameter("storeId", storeId);
      menuItems = qry.getResultList();

      // calculate current stock and put in menu item
      /*
       * StoreMaster
       * store=addressDAO.getStoreByStoreId(Integer.parseInt(storeId));
       * String isDisplayCrntStck=store.getDisplayCurrentStockMenu();
       * if(isDisplayCrntStck.equalsIgnoreCase("Y")){ Map<Integer, Double>
       * mapHoldItemCrntStck=new HashMap<Integer, Double>(); String
       * curntStockProc = "{call get_item_wise_cur_stock(?,?)}";
       * callableStatement = connection.prepareCall(curntStockProc);
       * callableStatement.setInt(1, Integer.parseInt(storeId));
       * callableStatement.setInt(2, menuItem.getId());
       * 
       * callableStatement.execute(); rs=callableStatement.getResultSet();
       * 
       * while (rs.next()) { int itemid=rs.getInt("item_id");
       * System.out.println(" itm id: "+itemid); double
       * curStock=rs.getDouble("cur_stock");
       * menuItem.setCurrentStock(curStock); }
       * 
       * 
       * }
       */

      em.getTransaction().commit();

    }

    catch (Exception e) {
      e.printStackTrace();
      throw new DAOException(
          "problem occured in MenuDAOImpl: getItemDetailsById", e);
    } finally {
      if(em != null) em.close();
    }
    return menuItems;
  }

	@Override
	public String updateOrderedItem(OrderItem orderItemNew) throws DAOException {

		String status = "";
		OrdersDAO ordersDAO = new OrdersDAOImpl();
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		StoreMaster storeMaster = new StoreMaster();

		try {
			
			Double totalItemPriceWithoutTax = 0.0;
			Double totalBillAmt = 0.0;
			String previousQnty = "";
			Double newQntity = Double.parseDouble(orderItemNew.getQuantityOfItem());
			double newTotalPrice = 0.0;
			double totalDiscount = 0.0;
			double totalserviceTax = 0.0;
			double totalVat = 0.0;
			double netPriceEachItem = 0.0;
			double totalServceChrgAmt=0.0;
			double serviceChrgRate=0.0;
			// double olbGrossBillamt = 0.0;
			// double grossBillAmt = 0.0;
			// Bill oldBill = null;
			// Payment oldPay = null;
			//OrdersDAO ordersDao = new OrdersDAOImpl();
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			OrderItem orderItem = em.find(OrderItem.class, orderItemNew.getId());
			previousQnty = orderItem.getQuantityOfItem();
			// get the rate of the item
			storeMaster = addressDAO.getStoreByStoreId(orderItem.getStoreId());
			MenuItem item = getMenuItemById(orderItemNew.getItemId());
			double rate = orderItem.getRate();
			
			newTotalPrice = rate * newQntity;
			orderItem.setPreviousQuantity(previousQnty);
			orderItem.setQuantityOfItem(orderItemNew.getQuantityOfItem());
			orderItem.setTotalPriceByItem(newTotalPrice);
			orderItem.setChangeNote(orderItemNew.getChangeNote());

			// update taxes start
			Double itemServiceTax1 = orderItem.getServiceTax();
			Double itemDisc1=0.0;
			if("Y".equalsIgnoreCase(item.getPromotionFlag()))
			{
				itemDisc1=newTotalPrice*orderItem.getPromotionValue()/100;
			}
			Double serviceTaxForThsItem1 = (itemServiceTax1 * (newTotalPrice - itemDisc1)) / 100;

			Double itemVat1 = orderItem.getVat();
			Double vatForThsItem1 = (itemVat1 * (newTotalPrice - itemDisc1)) / 100;

			netPriceEachItem = newTotalPrice + serviceTaxForThsItem1
					+ vatForThsItem1-itemDisc1;
			orderItem.setPromotionDiscountAmt(itemDisc1);
			orderItem.setNetPrice(netPriceEachItem);
			// update taxes end

			em.merge(orderItem);
			em.getTransaction().commit();

			em.getTransaction().begin();
			Bill oldBill = null;
			Query qryBill = em
					.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id=:orderid");
			qryBill.setParameter("orderid", orderItemNew.getOrdrId());
			oldBill = (Bill) qryBill.getSingleResult();
			OrderMaster order = ordersDAO.getOrderById(orderItemNew.getOrdrId());
			List<OrderItem> orderItemList = order.getOrderitem();
			Iterator<OrderItem> iterator = orderItemList.iterator();
			Double itemPrice=0.00;
			serviceChrgRate=oldBill.getServiceChargeRate();
			while (iterator.hasNext()) {
				OrderItem orderItem1 = (OrderItem) iterator.next();

				// calculate total price for each item
				MenuItem item1 = orderItem1.getItem();
				Double itemRate=item1.getPrice();
				if(orderItem1.getRate()>0)
				{
					itemRate=orderItem1.getRate();
				}
				itemPrice = Double.parseDouble((orderItem1
						.getQuantityOfItem())) * itemRate;
				Double itemDisc=0.0;
				// check for any promotion
				if (item1.getPromotionFlag().equalsIgnoreCase("y")) {

					itemDisc = ((itemPrice) * (item1.getPromotionValue())) / 100;
					//System.out.println("discount:: " + discount);
					totalDiscount = totalDiscount + itemDisc;
				}
				double itemServiceChrg=0.0;
				itemServiceChrg=(itemPrice - itemDisc)*serviceChrgRate/100;
				//Double itemServiceTax = item1.getServiceTax();
				Double itemServiceTax = orderItem1.getServiceTax();
				Double serviceTaxForThsItem = (itemServiceTax * ((itemPrice - itemDisc)+itemServiceChrg)) / 100;
				totalserviceTax = totalserviceTax + serviceTaxForThsItem;

				// vat calculation for each item
				//Double itemVat = item1.getVat();
				Double itemVat = orderItem1.getVat();
				Double vatForThsItem = (itemVat * ((itemPrice - itemDisc)+itemServiceChrg)) / 100;
				totalVat = totalVat + vatForThsItem;

				// calculate item price with taxes
				Double itemPriceWithTax = itemPrice + vatForThsItem
						+ serviceTaxForThsItem;

				DecimalFormat df = new DecimalFormat("00.00");

				String itemPrceWithTax = df.format(itemPriceWithTax);

				itemPriceWithTax = Double.parseDouble(itemPrceWithTax);
				// calculate total item price without tax
				totalItemPriceWithoutTax = totalItemPriceWithoutTax + itemPrice;

				totalServceChrgAmt=totalServceChrgAmt+itemServiceChrg;
				//System.out.println("total discount:: " + totalDiscount);

			}

			// total item price to 2 decimal places
			DecimalFormat df = new DecimalFormat("00.00");
			String totalItemPriceWithoutTaxUptoTwoDecimal = df
					.format(totalItemPriceWithoutTax);
			// calculate total vat upto 2 decimal places
			String totalVatUptoTwoDecimal = df.format(totalVat);
			// calculate total service tax upto 2 decimal places
			String totalServiceTaxUptoTwoDecimal = df.format(totalserviceTax);
			String totalServiceChrgUptoTwoDecimal = df.format(totalServceChrgAmt);
			totalBillAmt = Double
					.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal)
					+Double.parseDouble(totalServiceChrgUptoTwoDecimal)
					+ Double.parseDouble(totalVatUptoTwoDecimal)
					+ Double.parseDouble(totalServiceTaxUptoTwoDecimal)
					-totalDiscount;
			/**
			 * update Service Chage
			 */
			// check if there an old bill
			int orderid = order.getId();
			int storeId = order.getStoreId();
			
			Payment oldPay = null;

			
			//System.out.println("old bill :" + oldBill);
			String formate1 = df.format(totalBillAmt); 
			double finalValue1 = Double.parseDouble(formate1);
			double grossBillAmt=0.0;
			String roundOffStatus = storeMaster.getRoundOffTotalAmtStatus();
			String roundOffDouble = storeMaster.getDoubleRoundOff();
			if (roundOffStatus != null
					&& !roundOffStatus.equalsIgnoreCase("")
					&& roundOffStatus.equalsIgnoreCase("Y")) {
				grossBillAmt = new Double(Math.round(totalBillAmt));
			} else if (roundOffDouble != null
					&& !roundOffDouble.equalsIgnoreCase("")
					&& roundOffDouble.equalsIgnoreCase("Y")) {
				//String formvl = df.format(grossBillAmt);//added on 29.05.2018
				String formvl = df.format(totalBillAmt);
				grossBillAmt = Double.parseDouble(formvl);
				grossBillAmt = new Double(
						CommonProerties.roundOffUptoTwoPlacesDouble(
								grossBillAmt, 1)); // round off double
			}
			
			
			// update old bill
			oldBill.setBillAmount(finalValue1);
			oldBill.setVatAmt(Double.parseDouble(totalVatUptoTwoDecimal));
			oldBill.setServiceTaxAmt(Double
					.parseDouble(totalServiceTaxUptoTwoDecimal));
			oldBill.setTotalDiscount(totalDiscount);
			oldBill.setCustomerDiscount(0.0);
			oldBill.setDiscountPercentage(0.0);
			oldBill.setGrossAmt(grossBillAmt);
			oldBill.setServiceChargeAmt(Double.parseDouble(totalServiceChrgUptoTwoDecimal));
			oldBill.setServiceChargeRate(serviceChrgRate);
			//oldBill.setSubTotalAmt(itemPrice);
			oldBill.setSubTotalAmt(Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal));
			// update old payment object
			Query qryPay = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid");
			qryPay.setParameter("orderid", orderid);
			oldPay = (Payment) qryPay.getSingleResult();
			oldPay.setAmount(grossBillAmt);
			oldPay.setAmountToPay(grossBillAmt);

			em.merge(oldBill);

			/*********************************************************************************************************/

			em.getTransaction().commit();
			status = "success";
			System.out.print("ordered item updated successfully....");

			// check if kot print true , then print kot update
			OrderService orderService = new OrderService();
			String kotPrint = storeMaster.getKitchenPrint();
			if (kotPrint != null) {
        if (kotPrint.equalsIgnoreCase("Y")) {
          if(storeId!=37 && storeId!=38){
          orderService.printUpdateKot(orderid, storeId,
             Integer.valueOf(newQntity.intValue()), Integer.parseInt(previousQnty),
              item.getName());
          }
        }
			}

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public String updateSpecialNote(SpecialNoteListContainer noteListContainer)
			throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		String status = "failure";
		MenuDAO dao = new MenuDAOImpl();

		try {
			System.out.println("starting.....updateSpecialNote");
			int itemId = noteListContainer.getItemId();
			int storeId = noteListContainer.getStoreId();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				Query qry = em
						.createQuery("DELETE FROM MenuItemNote i WHERE i.foodItemIds=:itemId and i.storeId=:storeId");
				qry.setParameter("itemId", itemId);
				qry.setParameter("storeId", storeId);
				qry.executeUpdate();
				em.getTransaction().commit();
				System.out.println("starting insert.....");
				em.getTransaction().begin();
				List<MenuItemNote> itemNotes = noteListContainer
						.getMenuItemNotes();

				if (itemNotes != null)
					for (int i = 0; i < itemNotes.size(); i++) {
						MenuItemNote itemNote = itemNotes.get(i);
						MenuItem item = itemNote.getMenuItem();
						itemNote.setMenuItem(dao.getMenuItemById(item.getId()));
						itemNote.setDeleteFlag("N");
						em.persist(itemNote);
					}

				em.getTransaction().commit();
				//System.out.println("commit.....");
				status = "success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getItemDetailsById", e);
		} finally {
			if(em != null) em.close();
		}

		return status;
	}

	@Override
	public String uploadImageItemPOS(String itemId, String fileName,
			InputStream inputStream) throws IOException {
		System.out.println("enter uploadImageItemPOS");
		String status = "";
		// Extract file name from content-disposition header of file part
		if (inputStream != null) {
			// String fileName = getFileName(part);
			System.out.println("***** fileName: " + fileName);

			// String extension=afterDot;
			int itemid = Integer.parseInt(itemId);
			// String changedFileName = "" + itemid + "." + afterDot; //
			// commented to upload any file type
			String changedFileName = "" + itemid + "." + "png";
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
				// basePath = "D:/2016-02-22";
			}
			// String basePath = "C:" +
			// File.separator+"Program Files (x86)"+File.separator +
			// "images"+File.separator;
			// String basePath = "D:" + File.separator+"temp"+File.separator;
			System.out.println("basePath :" + basePath);
			File outputFilePath = new File(basePath + "/" + changedFileName);

			// Copy uploaded file to destination path
			// inputStream = null;
			OutputStream outputStream = null;
			try {
				// inputStream = part.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);

				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				status = "Success";
				// statusMessage = "File upload successfull !!";
			} catch (IOException e) {
				e.printStackTrace();
				//System.out.println("In uploadFile exception");
				status = "Failure";
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
		return status; // return to same page
	}

	@Override
	public String assignPrinterToItem(String itemId, String printerList)
			throws DAOException {
		MenuItem menuItem = null;
		EntityManager em = null;
		String status = "failure";
		try {
			int itemid = Integer.parseInt(itemId);
			System.out.println("in MenuDAOImpl : getMenuItemById");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT i FROM MenuItem i WHERE i.id=:itemId");
			qry.setParameter("itemId", itemid);
			menuItem = (MenuItem) qry.getSingleResult();
			// add printer
			menuItem.setPrinterList(printerList);

			em.getTransaction().commit();
			status = "Success";

		}

		catch (Exception e) {

			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl: getMenuItemById", e);

		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public List<MenuItem> getMenuItemsAll(String storeId) throws DAOException {
		List<MenuItem> itemList = null;
		int storeid1 = Integer.parseInt(storeId);

		StoreMaster storeMaster = new StoreMaster();
		storeMaster.setId(storeid1);
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			TypedQuery<MenuItem> qry = em
					.createQuery("SELECT m FROM MenuItem m WHERE m.deleteFlag='N' AND m.storeId=:param", 
					    MenuItem.class);
			qry.setParameter("param", storeid1);

			itemList = qry.getResultList();

			Iterator<MenuItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				MenuItem menuItem = (MenuItem) iterator.next();

				// set category name, sub category name
				int id = menuItem.getId();
				String[] values = CommonProerties.getCatSubcatNames(id,
						storeid1);
				String catId = values[2];
				String catName = values[0];
				String subCatId = values[3];
				String subCatName = values[1];
				if (catId != null) {
					menuItem.setCategoryId(Integer.parseInt(catId));
				}
				if (catName != null) {
					menuItem.setCategoryName(catName);
				}
				if (subCatId != null) {
					menuItem.setSubCategoryId(Integer.parseInt(subCatId));
				}
				if (subCatName != null) {
					menuItem.setSubCategoryName(subCatName);
				}

				// set metric unit in item
				/*
				 * int unitId = menuItem.getMetricUnitId(); Iterator<MetricUnit>
				 * iterator3 = metricUnits.iterator(); while
				 * (iterator3.hasNext()) { MetricUnit metricUnit = (MetricUnit)
				 * iterator3.next(); int unitid = metricUnit.getId(); if (unitId
				 * == unitid) { menuItem.setMetricUnit(metricUnit); break; }
				 * 
				 * }
				 */

			}
			if(em != null) em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getLanguageListByStoreId",
					e);

		}
		return itemList;
	}
	
	@Override
	public MenuCategory getRootMenuByStoreId(String storeId)
			throws DAOException {

		MenuCategory parentMenuCat =null;
		EntityManager em = null;
		int storeid = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String specialNote = "Special Note";
			String menuItmName = "menu";
			// Query qry =
			// em.createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid");
			TypedQuery<MenuCategory> qry = em
          .createQuery("SELECT m FROM MenuCategory m WHERE m.storeId=:storeid and m.deleteFlag='N' and m.menuCategoryName!=:specialnote AND LOWER(m.menuCategoryName) = :menu", MenuCategory.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("specialnote", specialNote);
			qry.setParameter("menu", menuItmName);
			parentMenuCat = qry.getSingleResult();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}
		return parentMenuCat;
	}

}

package com.botree.restaurantapp.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.DaySpecial;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component("specialsDAO")
public class SpecialsDAOImpl implements SpecialsDAO {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public List<DaySpecial> getSpecialItem() throws DAOException {

		List<DaySpecial> specialitem = null;
		EntityManager em = null;
		Date currDate = new Date();
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			System.out.println("curr date:" + currDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(currDate);
			System.out.println("date is:" + curDate);
			Date date = dateFormat.parse(curDate);
			System.out.println("today date:" + date);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<DaySpecial> qry = em.createQuery("SELECT d FROM DaySpecial d where d.todate >= :currDate and d.storeId=:storeId and d.deleteFlag='N'",
			    DaySpecial.class);
			qry.setParameter("storeId", storeId);
			qry.setParameter("currDate", date);
			System.out.println("helloo22");
			specialitem = qry.getResultList();
			System.out.println("helloo33");

			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<DaySpecial>>() {}.getType();
			String json = gson.toJson(specialitem, type);

			em.getTransaction().commit();
			//em.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return specialitem;
	}

	/**
	 * @return
	 */
	/*public int getStoreId() {
		StoreMaster storeMaster = (StoreMaster) context.getExternalContext().getSessionMap().get("selectedstore");

		int storeId = 0;
		if (storeMaster != null) {
			int restId = storeMaster.getRestaurantId();
			System.out.println("rest id:  " + restId);
			storeId = storeMaster.getId();
		}
		return storeId;
	}*/

	@Override
	public List<DaySpecial> getSpecialItemByStoreId(String storeId,
													String language) throws DAOException {

		List<DaySpecial> specialitem = null;
		Date currDate = new Date();
		EntityManager em = null;
		List<MenuItemLangMap> menuItemLangList = null;
		try {

			int storeid = Integer.parseInt(storeId);
			System.out.println("Enter SpecialsDAOImpl: getSpecialItemByStoreId: store id  " + storeid);
			System.out.println("curr date:" + currDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(currDate);
			System.out.println("date is:" + curDate);
			Date date = dateFormat.parse(curDate);
			System.out.println("today date:" + date);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<DaySpecial> qry = em.createQuery("SELECT d FROM DaySpecial d where d.todate >= :currDate and d.storeId = :storeid and d.deleteFlag='N'",
			    DaySpecial.class);
			System.out.println("helloo11");
			qry.setParameter("currDate", date);
			qry.setParameter("storeid", storeid);
			System.out.println("helloo22");
			//query to get all items
			TypedQuery<MenuItemLangMap> qryItemlang = em.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.storeId=:storeid and i.language=:language",
			    MenuItemLangMap.class);
			qryItemlang.setParameter("storeid", storeid);
			qryItemlang.setParameter("language", language);
			menuItemLangList = qryItemlang.getResultList();

			specialitem = qry.getResultList();
			if (specialitem != null) {
				System.out.println("specialitem size " + specialitem.size());
			}

			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<DaySpecial>>() {}.getType();
			String json = gson.toJson(specialitem, type);

			em.getTransaction().commit();
			//em.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}

		//replace items for language

		if (specialitem != null && specialitem.size() > 0) {
			Iterator<DaySpecial> spclIter = specialitem.iterator();
			while (spclIter.hasNext()) {
				DaySpecial special = (DaySpecial) spclIter.next();
				MenuItem menuItem = special.getItem();
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
							if (itemlanDesc != null && itemlanDesc.length() > 0) {

								menuItem.setDescription(itemlanDesc);

							}

						}

					}
				}

			}
		}

		System.out.println("Exit SpecialsDAOImpl: getSpecialItemByStoreId");
		return specialitem;
	}

	/*****************************
	 * Get special item as object******************************
	 * 
	 */

	@Override
	public DaySpecial getSpecialItemAsObj() throws DAOException {

		List<DaySpecial> specialitem = null;
		DaySpecial daySpecial = new DaySpecial();
		Date currDate = new Date();
		EntityManager em = null;
		try {
			System.out.println("curr date:" + currDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(currDate);
			System.out.println("date is:" + curDate);
			Date date = dateFormat.parse(curDate);
			System.out.println("today date:" + date);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<DaySpecial> qry = em.createQuery("SELECT d FROM DaySpecial d where d.todate >= :currDate",
			    DaySpecial.class);
			System.out.println("helloo11");
			qry.setParameter("currDate", date);
			System.out.println("helloo22");
			specialitem = qry.getResultList();
			System.out.println("helloo33");

			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<DaySpecial>>() {}.getType();
			String json = gson.toJson(specialitem, type);

			em.getTransaction().commit();
			//em.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		//daySpecial.setSpecialList(specialitem);
		return daySpecial;
	}

	@Override
	public void addSpeciaItem(List<DaySpecial> specialItemlist) throws DAOException {

		DaySpecial item1 = new DaySpecial();
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}
			
			em = entityManagerFactory.createEntityManager();

			Iterator<DaySpecial> itrMenu = specialItemlist.iterator();
			while (itrMenu.hasNext()) {
				em.getTransaction().begin();
				//MenuItem item2 = new MenuItem();
				DaySpecial item = (DaySpecial) itrMenu.next();
				item1.setFromdate(item.getFromdate());
				item1.setTodate(item.getTodate());
				item1.setItem(item.getItem());
				item1.setStoreId(storeId);
				item1.setDeleteFlag("N");
				em.persist(item1);
				em.getTransaction().commit();
			}
			//em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}

	}

	/*@Override
	public void deleteSpecialItem(List<DaySpecial> specialItemlist) throws DAOException {

		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			Iterator<DaySpecial> itrSpcl = specialItemlist.iterator();
			while (itrSpcl.hasNext()) {
				DaySpecial item = (DaySpecial) itrSpcl.next();
				em.getTransaction().begin();
				DaySpecial item1 = em.find(DaySpecial.class, item.getId());
				em.remove(item1);
				em.getTransaction().commit();
			}
			em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be deleted", e);
		}

	}*/

	@Override
	public void deleteSpecialItem(List<DaySpecial> specialItemlist) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			Iterator<DaySpecial> itrSpcl = specialItemlist.iterator();
			while (itrSpcl.hasNext()) {
				DaySpecial item = (DaySpecial) itrSpcl.next();
				em.getTransaction().begin();
				DaySpecial item1 = em.find(DaySpecial.class, item.getId());
				item1.setDeleteFlag("Y");
				//em.remove(item1);
				em.getTransaction().commit();
			}
			//em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}

	}

}

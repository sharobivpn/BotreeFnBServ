package com.botree.restaurantapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.MenuItemNote;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("specialNoteDao")
public class SpecialNoteDaoImpl implements SpecialNoteDao {
  
	//private final static Logger LOGGER = LogManager.getLogger(MenuDAOImpl.class);
	
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
	
	List<MenuItemNote> itemNoteList = null;
	EntityManager em = null;

	@Override
	public List<MenuItemNote> getSpecialNoteByFoodItem(String foodItemIds,
			String storeId) throws DAOException {
		// List<MenuItemNote> itemNoteList = null;
		int foodItemId1 = Integer.parseInt(foodItemIds);
		int storeId1 = Integer.parseInt(storeId);
		String delFlag="N";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<MenuItemNote> qry = em
					.createQuery("SELECT m FROM MenuItemNote m WHERE m.foodItemIds=:foodItemId1 and m.storeId=:storeId and m.deleteFlag=:delFlag",
					    MenuItemNote.class);
			qry.setParameter("foodItemId1", foodItemId1);
			qry.setParameter("storeId", storeId1);
			qry.setParameter("delFlag", delFlag);
			itemNoteList = qry.getResultList();
		
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"Problem occured in MenuDAOImpl.getSpecialNoteByFoodItems",	e);
		}
		finally {
			em.close();
		}
		return itemNoteList;
	}

	@Override
	public String addSpecialItem(String foodItemIds, String foodItemNoteId)
			throws DAOException {

		String status = "";

		int fooditemNoteId = 0;
		int fooditemNoteId1 = 0;
		EntityManager em = null;

		int foodItemIds1 = Integer.parseInt(foodItemIds);
		int foodItemNoteId1 = Integer.parseInt(foodItemNoteId);
		try {

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<MenuItemNote> qry = em
					.createQuery("SELECT m FROM MenuItemNote m WHERE m.foodItemIds1=:foodItemIds and m.foodItemNoteId1=foodItemNoteId and m.deleteFlag='N'",
					    MenuItemNote.class);
			qry.setParameter("foodItemIds", foodItemIds1);
			qry.setParameter("foodItemNoteId", foodItemNoteId1);
			MenuItemNote itemNote = (MenuItemNote) qry.getSingleResult();
			// get the id
			fooditemNoteId = itemNote.getFoodItemIds();
			//fooditemNoteId1 = itemNote.getFoodItemNoteId();

			Query query = em
					.createNativeQuery("INSERT INTO fm_m_food_items_note (food_item_id, food_item_note_id) "
							+ " VALUES(?, ?)");
			query.setParameter(1, fooditemNoteId);
			query.setParameter(2, fooditemNoteId1);

			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return status;
	}

}

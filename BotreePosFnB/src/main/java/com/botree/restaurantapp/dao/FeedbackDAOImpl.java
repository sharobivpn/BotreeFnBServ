package com.botree.restaurantapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Feedback;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("feedbackDAO")
public class FeedbackDAOImpl implements FeedbackDAO {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public void addFeedback(Feedback feedback) throws DAOException {

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(feedback);
			em.getTransaction().commit();
			System.out.print("Customer feedback saved successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

	}

	@Override
	public List<Feedback> getFeedbacks() throws DAOException {

		List<Feedback> feedbackList = null;
		EntityManager em = null;
		try {
			/*FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}*/
			
			em = entityManagerFactory.createEntityManager();
			TypedQuery<Feedback> qry = em
					.createQuery("SELECT f FROM Feedback f WHERE f.storeId=:strId order by f.id desc limit 20",
					    Feedback.class);
			qry.setParameter("strId", 0);
			feedbackList = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return feedbackList;
	}

	@Override
	public List<Feedback> getFeedbackByStore(String storeid)
			throws DAOException {

		List<Feedback> feedbackList = null;
		int strId = Integer.parseInt(storeid);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			TypedQuery<Feedback> qry = em
					.createQuery("SELECT f FROM Feedback f WHERE f.storeId=:strId",
					    Feedback.class);
			qry.setParameter("strId", strId);
			feedbackList = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return feedbackList;
	}

	@Override
	public void delete(Feedback feedback) throws DAOException {

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Feedback feedback1 = em.find(Feedback.class, feedback.getId());
			em.remove(feedback1);
			em.getTransaction().commit();
			System.out.print("Customer data deleted successfully....");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
	}
}

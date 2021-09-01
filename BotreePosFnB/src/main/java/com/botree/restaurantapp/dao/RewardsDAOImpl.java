package com.botree.restaurantapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.RewardPoint;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("rewardsDAO")
public class RewardsDAOImpl implements RewardsDAO {
	
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public void createReward() throws DAOException {
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//em.persist();
			em.getTransaction().commit();
			System.out.print("Coupon saved successfully...");
			//em.close();
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			em.close();
		}
		
	}

	@Override
	public void updateReward(RewardPoint reward) throws DAOException {
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.getTransaction().commit();
			System.out.print("Coupon updated successfully....");
			//em.close();
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			em.close();
		}
		
	}

	@Override
	public void deleteReward(RewardPoint reward) throws DAOException {
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.getTransaction().commit();
			System.out.print("Coupon deleted successfully....");
			//em.close();
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			em.close();
		}
		
	}

	

}

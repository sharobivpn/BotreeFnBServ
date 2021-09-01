package com.botree.restaurantapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Coupon;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("couponsDAO")
public class CouponsDAOImpl implements CouponsDAO{
	
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public void createCoupon(String coupon) throws DAOException {
		
		Coupon coupon1=new Coupon();
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			coupon1.setCouponValue(coupon);
			em.persist(coupon1);
			em.getTransaction().commit();
			System.out.print("Coupon saved successfully...");
			//em.close();
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			if(em != null) em.close();
		}
		
	}

	@Override
	public void updateCoupon(Coupon coupon) throws DAOException {
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Coupon coupon1=em.find(Coupon.class,coupon.getId());
			coupon1.setCouponValue(coupon.getCouponValue());
			em.merge(coupon1);
			em.getTransaction().commit();
			System.out.print("Coupon updated successfully....");
			//em.close();
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			if(em != null) em.close();
		}
		
	}

	@Override
	public void deleteCoupon(Coupon coupon) throws DAOException {
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Coupon coupon1=em.find(Coupon.class,coupon.getId());
			em.remove(coupon1);
			em.getTransaction().commit();
			System.out.print("Coupon deleted successfully....");
			//em.close();
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			if(em != null) em.close();
		}
		
	}

	@Override
	public List<Coupon> getCoupons() throws DAOException {
		
		List<Coupon> coupons=null;
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Coupon> qry = em.createQuery("SELECT c FROM Coupon c", Coupon.class);
			coupons = qry.getResultList();
			//em.close();
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			if(em != null) em.close();
		}
		return coupons;
	}

	@Override
	public void sendCoupon(Coupon coupon) throws DAOException {
		
		EntityManager em=null;
		try{
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			if(em != null) em.close();
		}
		
	}

	
}

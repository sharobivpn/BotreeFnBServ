/**
 * 
 */
package com.botree.restaurantapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.DeliveryBoy;
import com.botree.restaurantapp.model.util.PersistenceListener;

/**
 * @author ChanchalN
 *
 */
@Component("deliveryBoyDAO")
public class DeliveryBoyDAOImpl implements DeliveryBoyDAO{

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
  
	//private final static Logger LOGGER = LogManager.getLogger(DeliveryBoyDAOImpl.class);
	
	@Override
	public String createDeliveryBoy(DeliveryBoy deliveryBoy) throws DAOException {
		DeliveryBoy dBoy = new DeliveryBoy();
		EntityManager em=null;
		String status="failure";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			dBoy.setName(deliveryBoy.getName());
			dBoy.setAddress(deliveryBoy.getAddress());
			dBoy.setPhone_no(deliveryBoy.getPhone_no());
			dBoy.setEmail(deliveryBoy.getEmail());
			dBoy.setDOB(deliveryBoy.getDOB());
			dBoy.setDOJ(deliveryBoy.getDOJ());
			dBoy.setStore_id(deliveryBoy.getStore_id());
			dBoy.setUniqueId(deliveryBoy.getUniqueId());
			em.persist(dBoy);
			em.getTransaction().commit();
			System.out.println("Delivery Boy inserted successfully.");
			status="success";
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		}
		finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String updateDeliveryBoy(DeliveryBoy deliveryBoy) throws DAOException {
		DeliveryBoy dBoy = new DeliveryBoy();
		//DeliveryBoy delBoy = new DeliveryBoy();
		EntityManager em=null;
		String status="failure";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			int id = deliveryBoy.getId();
			TypedQuery<DeliveryBoy> qry = em
					.createQuery("SELECT d FROM DeliveryBoy d WHERE d.id=:id",
					    DeliveryBoy.class);
			qry.setParameter("id", id);
			dBoy = qry.getSingleResult();
			
			dBoy.setName(deliveryBoy.getName());
			dBoy.setAddress(deliveryBoy.getAddress());
			dBoy.setPhone_no(deliveryBoy.getPhone_no());
			dBoy.setEmail(deliveryBoy.getEmail());
			dBoy.setDOB(deliveryBoy.getDOB());
			dBoy.setDOJ(deliveryBoy.getDOJ());
			dBoy.setUniqueId(deliveryBoy.getUniqueId());
			dBoy.setStore_id(deliveryBoy.getStore_id());
			em.merge(dBoy);
			em.getTransaction().commit();
			System.out.println("Delivery Boy updated successfully.");
			status="success";
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be updated", e);
		}
		finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String deleteDeliveryBoy(int id, String storeId) throws DAOException {
		DeliveryBoy dBoy = new DeliveryBoy();
		EntityManager em=null;
		String status="failure";
		try {
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//DeliveryBoy dboy = em.find(DeliveryBoy.class, deliveryBoy.getId());
			TypedQuery<DeliveryBoy> qry = em
					.createQuery("SELECT d FROM DeliveryBoy d WHERE d.id=:Id and d.store_id=:storeId",
					    DeliveryBoy.class);
			qry.setParameter("Id", id);
			qry.setParameter("storeId", storeId);
			dBoy = qry.getSingleResult();
			
			em.remove(dBoy);
			em.getTransaction().commit();
			System.out.println("Delivery Boy data deleted successfully.");
			status = "success";
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		}
		finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public DeliveryBoy getDeliveryBoyById(int id) throws DAOException {
		DeliveryBoy dboy = null;
		EntityManager em=null;
		
		try {
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<DeliveryBoy> qry = em
					.createQuery("SELECT d FROM DeliveryBoy d WHERE d.id=:Id",
					    DeliveryBoy.class);
			qry.setParameter("Id", id);
			
			dboy = qry.getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		}
		finally {
			if(em != null) em.close();
		}
		return dboy;
	}

	@Override
	public List<DeliveryBoy> getAllDeliveryBoy(String store_id) throws DAOException {

		List<DeliveryBoy> deliveryBoyList = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			TypedQuery<DeliveryBoy> qry = em
					.createQuery("SELECT d FROM DeliveryBoy d WHERE d.store_id=:Id",
					    DeliveryBoy.class);
			qry.setParameter("Id", store_id);
			
			deliveryBoyList = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return deliveryBoyList;
	}

}

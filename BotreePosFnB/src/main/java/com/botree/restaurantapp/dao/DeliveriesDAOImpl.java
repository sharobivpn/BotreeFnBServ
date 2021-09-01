package com.botree.restaurantapp.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("deliveriesDAO")
public class DeliveriesDAOImpl implements DeliveriesDAO {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
  
	@Override
	public void updateDeliveryStatus(List<OrderMaster> orderList) throws DAOException{
		
		OrderMaster orders=new OrderMaster();
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			
			Iterator<OrderMaster> itrOrder=orderList.iterator();
			while (itrOrder.hasNext()) {
			orders=(OrderMaster) itrOrder.next();
			em.getTransaction().begin();
			OrderMaster order=em.find(OrderMaster.class,orders.getId());
			order.setFromTime(orders.getFromTime());
			order.setToTime(orders.getToTime());
			order.setDeliveryTime(orders.getDeliveryTime());
			order.setDeliveryAddress(orders.getDeliveryAddress());
			order.setFlag(orders.getFlag());
			//em.merge(order);
			em.getTransaction().commit();
			}
			
			System.out.print("deliverystatus data updated successfully....");
			//em.close();
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			if(em != null) em.close();
		}
	}


}

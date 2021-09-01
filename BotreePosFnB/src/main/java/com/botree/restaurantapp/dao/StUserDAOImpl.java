package com.botree.restaurantapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.StUser;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("stUserDAO")
public class StUserDAOImpl implements StUserDAO {
  
	private final static Logger LOGGER = LogManager.getLogger(StUserDAOImpl.class);
	
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public List<StUser> getAllUsers() throws DAOException {

		List<StUser> userList = null;
		EntityManager em=null;
		try {
			LOGGER.debug("Bingo...st user");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StUser> qry = em.createQuery("SELECT u FROM StUser u", StUser.class);
			userList = qry.getResultList();
			em.getTransaction().commit();
			//em.close();
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw new DAOException("Check st User data", e);
		} catch (Throwable e) {
			System.out.println("hello 4b");
			e.printStackTrace();
		}finally{
			em.close();
		}
		return userList;
	}

	/*public User getUserById(String userId) throws DAOException {

		User user = null;

		String userid;

		try {
			userid = userId;
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em.createQuery("SELECT u FROM User u WHERE u.userId= :userId");
			qry.setParameter("userId", userid);

			// userLst = (List<User>) qry.getResultList();
			user = (User) qry.getSingleResult();

			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		}
		return user;
	}

	@Override
	public void createUser(User user) throws DAOException {

		User user1 = new User();
		EntityManager em = null;
		try {

			
			System.out.println("hello 111");
			em = entityManagerFactory.createEntityManager();
			System.out.println("hello 222");
			em.getTransaction().begin();
			System.out.println("hello 333");
			user1.setUserName(user.getUserName());
			user1.setUserId(user.getUserId());
			user1.setPassword(user.getPassword());
			user1.setDesignation(user.getDesignation());
			user1.setRestMaster(user.getRestMaster());
			em.persist(user1);
			em.getTransaction().commit();
			System.out.println("Name:" + user1.getUserName());
			System.out.println("helloo 444");
			System.out.print("User data saved successfully...");
			//em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void update(User user) throws DAOException {

		try {
			System.out.println("In UserDAOImpl.update");
			System.out.println("user id is:" + user.getId());
			System.out.println("user name is:" + user.getUserName());
			System.out.println("user userid is:" + user.getUserId());
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			User user1 = em.find(User.class, user.getId());
			System.out.println("user1:" + user1);
			user1.setUserName(user.getUserName());
			user1.setUserId(user.getUserId());
			user1.setDesignation(user.getDesignation());
			user1.setRestMaster(user.getRestMaster());
			// user1.setPassword(user.getPassword());
			em.getTransaction().commit();
			System.out.print("User data updated successfully....");
			em.close();
		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		}
	}

	@Override
	public void delete(User user) throws DAOException {

		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			User userToDelete = em.find(User.class, user.getId());
			em.remove(userToDelete);
			em.getTransaction().commit();
			System.out.print("User data deleted successfully....");
			em.close();
		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		}

	}*/

	@Override
	public StUser login(StUser user) throws DAOException {

		StUser loggedUser = null;
		EntityManager em=null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StUser> qry = em.createQuery("SELECT u FROM StUser u WHERE u.userId= :userid AND u.password= :pass", StUser.class);

			System.out.println("user id logged:" + user.getUserId());

			qry.setParameter("userid", user.getUserId());
			qry.setParameter("pass", user.getPassword());

			loggedUser = qry.getSingleResult();
			System.out.println("login successfull......");

			em.getTransaction().commit();
			System.out.print("User logged in scessfully....");
			//em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check User data", e);
		}finally{
			em.close();
		}
		return loggedUser;

	}

	/*@Override
	public List<StoreMaster> getStoresByUser(int id) throws DAOException {

		logger.debug("In UserDAOImpl: getStoresByUser", id);
		List<StoreMaster> strList = null;
		try {
			System.out.println("user id:  " + id);

			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em.createQuery("select u.store.id from UserTransaction u where u.userId =:userid and u.status=:status");
			qry.setParameter("userid", id);
			qry.setParameter("status", "Y");
			List<Integer> stLst = qry.getResultList();
			Iterator<Integer> iterator = stLst.iterator();
			while (iterator.hasNext()) {
				int id1 = iterator.next();
				System.out.println("str id:  " + id1);

			}
			//System.out.println("number of stores for admin: "+noOfStrs);
			Query qry1 = em.createQuery("SELECT s FROM StoreMaster s WHERE s.id in (select u.store.id from UserTransaction u where u.userId =:userid and u.status=:status)");
			qry1.setParameter("userid", id);
			qry1.setParameter("status", "Y");
			int stroes = qry1.getResultList().size();
			strList = qry1.getResultList();
			Iterator<StoreMaster> iterator2 = strList.iterator();
			while (iterator2.hasNext()) {
				StoreMaster storeMaster = (StoreMaster) iterator2.next();
				System.out.println("store name;  " + storeMaster.getStoreName());
			}
			System.out.println("number of stores : " + stroes);

			em.getTransaction().commit();
			System.out.print("User logged in scessfully....");
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check User data", e);
		}
		return strList;
		// TODO Auto-generated method stub

	}*/

}

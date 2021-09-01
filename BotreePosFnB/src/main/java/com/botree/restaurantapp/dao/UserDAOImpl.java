package com.botree.restaurantapp.dao;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

//import org.apache.catalina.deploy.ContextTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.model.UserTransaction;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("userDAO")
public class UserDAOImpl implements UserDAO {
  
	private final static Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
	
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public List<User> getAllUsers() throws DAOException {

		List<User> userList = null;
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<User> qry = null;
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			if (storeMaster != null) {
				int resId = storeMaster.getRestaurantId();
				qry = em.createQuery("SELECT u FROM User u where u.restMaster.id=:resId", User.class);
				qry.setParameter("resId", resId);
			} else {
				qry = em.createQuery("SELECT u FROM User u", User.class);
			}

			userList = qry.getResultList();
			em.getTransaction().commit();
			// em.close();
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw new DAOException("Check User data", e);
		} catch (Throwable e) {
			//System.out.println("hello 4b");
			e.printStackTrace();
		} finally {
			if(em!=null)
			em.close();
		}
		return userList;
	}

	public User getUserById(String userId) throws DAOException {

		User user = null;

		String userid;
		EntityManager em = null;
		try {
			userid = userId;
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT u FROM User u WHERE u.userId= :userId");
			qry.setParameter("userId", userid);

			// userLst = (List<User>) qry.getResultList();
			user = (User) qry.getSingleResult();

			em.getTransaction().commit();
			// em.close();

		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}
		return user;
	}

	public Customer getCustomerByContactNo(String conctNo) throws DAOException {

		Customer customer = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.contactNo= :contactNo");
			qry.setParameter("contactNo", conctNo);
			customer = (Customer) qry.getSingleResult();
			em.getTransaction().commit();

		} catch (Exception e) {

			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}
		return customer;
	}

	@Override
	public void createUser(User user) throws DAOException {

		User user1 = new User();
		UserTransaction transaction = new UserTransaction();
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			int restId = 0;
			if (storeMaster != null) {
				restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			//System.out.println("hello 111");
			em = entityManagerFactory.createEntityManager();
			//System.out.println("hello 222");
			em.getTransaction().begin();
			//System.out.println("hello 333");
			user1.setUserName(user.getUserName());
			user1.setUserId(user.getUserId());
			user1.setPassword(user.getPassword());

			// new restaurant master object
			RestaurantMaster master = new RestaurantMaster();
			master.setId(restId);

			user1.setDesignation(user.getDesignation());
			user1.setRestMaster(master);
			user1.setContact(user.getContact());
			em.persist(user1);

			Customer cust = new Customer();
			cust.setName(user.getUserName());
			// cust.setAddress(customer.getAddress());
			cust.setContactNo(user.getContact());
			// cust.setEmailId(customer.getEmailId());
			// cust.setUserId(customer.getUserId());
			cust.setPassword(user.getPassword());
			cust.setDesignation(user.getDesignation());
			cust.setStoreId(storeId);
			cust.setStatus("Y");

			em.persist(cust);

			Customer customer = em.find(Customer.class, cust.getId());
			customer.setType("w");

			// create a user transaction object
			transaction.setUserId(user1.getId());
			transaction.setRestaurantId(restId);
			transaction.setStatus("Y");
			transaction.setStore(storeMaster);

			em.persist(transaction);

			em.getTransaction().commit();
			System.out.println("Name:" + user1.getUserName());
			//System.out.println("helloo 444");
			System.out.print("User data saved successfully...");
			// em.getTransaction().commit();

		}

		catch (Exception e) {

			//System.out.println("in dao: createUser:");

			Throwable cause = e.getCause();

			if (cause instanceof ConstraintViolationException) {
				System.out.println("Integrity constraint");
				throw new DAOException("Integrity Constraint violation", e);
			}

			/*
			 * if(e instanceof PersistenceException){
			 * 
			 * 
			 * if(((SQLException)e).getSQLState()=="23000"){
			 * 
			 * 
			 * } }
			 */

			e.printStackTrace();
			throw new DAOException("Check User data", e);
		}

		finally {
			em.close();
		}

	}

	@Override
	public void createUserST(User user, UserTransaction transaction)
			throws DAOException {

		User user1 = new User();
		EntityManager em = null;
		try {
			//System.out.println("hello 111");
			em = entityManagerFactory.createEntityManager();
			//System.out.println("hello 222");
			em.getTransaction().begin();
			//System.out.println("hello 333");
			user1.setUserName(user.getUserName());
			user1.setUserId(user.getUserId());
			user1.setPassword(user.getPassword());

			// new restaurant master object
			RestaurantMaster master = new RestaurantMaster();
			master.setId(user.getRestMaster().getId());

			user1.setDesignation(user.getDesignation());
			user1.setRestMaster(user.getRestMaster());
			user1.setContact(user.getContact());
			em.persist(user1);

			User user2 = em.find(User.class, user1.getId());

			transaction.setUserId(user2.getId());
			transaction.setRestaurantId(user2.getRestMaster().getId());
			transaction.setStatus("Y");
			// transaction.setStore(store);
			em.persist(transaction);

			em.getTransaction().commit();
			System.out.println("Name:" + user1.getUserName());
			//System.out.println("helloo 444");
			System.out.print("User data saved successfully...");
			// em.getTransaction().commit();
		}

		catch (Exception e) {

			//System.out.println("in dao: createUser:");

			/*
			 * Throwable cause = e.getCause();
			 * 
			 * if (cause instanceof ConstraintViolationException) {
			 * System.out.println("Integrity constraint"); throw new
			 * DAOException("Integrity Constraint violation", e); }
			 */

			/*
			 * if(e instanceof PersistenceException){
			 * 
			 * 
			 * if(((SQLException)e).getSQLState()=="23000"){
			 * 
			 * 
			 * } }
			 */

			e.printStackTrace();
			throw new DAOException("Check User data", e);
		}

		finally {
			em.close();
		}

	}

	@Override
	public void createTabletUserST(Customer user) throws DAOException {

		Customer user1 = new Customer();
		EntityManager em = null;
		try {

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			user1.setName(user.getName());
			user1.setPassword(user.getPassword());
			user1.setAddress(user.getAddress());
			user1.setContactNo(user.getContactNo());
			user1.setDesignation(user.getDesignation());
			user1.setType("w");
			user1.setStoreId(user.getStoreMaster().getId());
			user1.setStatus("Y");

			em.persist(user1);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void update(User user) throws DAOException {
		EntityManager em = null;
		try {
			System.out.println("In UserDAOImpl.update");
			/*System.out.println("user id is:" + user.getId());
			System.out.println("user name is:" + user.getUserName());
			System.out.println("user userid is:" + user.getUserId());*/
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			User user1 = em.find(User.class, user.getId());
			//System.out.println("user1:" + user1);
			user1.setUserName(user.getUserName());
			user1.setUserId(user.getUserId());
			user1.setDesignation(user.getDesignation());
			user1.setRestMaster(user.getRestMaster());
			user1.setContact(user.getContact());
			// user1.setPassword(user.getPassword());
			em.getTransaction().commit();
			System.out.print("User data updated successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(User user) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			User userToDelete = em.find(User.class, user.getId());
			em.remove(userToDelete);
			em.getTransaction().commit();
			System.out.print("User data deleted successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}

	}

	@Override
	public User login(User user) throws DAOException {

		User loggedUser = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT u FROM User u WHERE u.userId= :userid AND u.password= :pass");

			System.out.println("user id logged:" + user.getUserId());

			qry.setParameter("userid", user.getUserId());

			qry.setParameter("pass", user.getPassword());

			loggedUser = (User) qry.getSingleResult();
			//System.out.println("login successfull......");

			em.getTransaction().commit();
			System.out.print("User logged in scessfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}
		return loggedUser;

	}
	
	@Override
	public User loginToCheckAdmin(User user) throws DAOException {

		User loggedUser = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT u FROM User u WHERE u.userId= :userid AND u.password= :pass");

			System.out.println("user id logged:" + user.getUserId());

			qry.setParameter("userid", user.getUserId());

			qry.setParameter("pass", user.getPassword());

			loggedUser = (User) qry.getSingleResult();
			//System.out.println("login successfull......");

			em.getTransaction().commit();
			System.out.print("User logged in scessfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}
		return loggedUser;

	}

	@Override
	public List<StoreMaster> getStoresByUser(int id) throws DAOException {

		LOGGER.debug("In UserDAOImpl: getStoresByUser", id);
		List<StoreMaster> strList = null;
		EntityManager em = null;
		try {
			System.out.println("user id:  " + id);
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			/*
			TypedQuery<Integer> qry = em
					.createQuery("select u.store.id from UserTransaction u where u.userId =:userid and u.status=:status",
					    Integer.class);
			qry.setParameter("userid", id);
			qry.setParameter("status", "Y");
			List<Integer> stLst = qry.getResultList();
			Iterator<Integer> iterator = stLst.iterator();
			while (iterator.hasNext()) {
				int id1 = iterator.next();
				//System.out.println("str id:  " + id1);
			}
			*/
			
			// System.out.println("number of stores for admin: "+noOfStrs);
			TypedQuery<StoreMaster> qry1 = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.id in (select u.store.id from UserTransaction u where u.userId =:userid and u.status=:status) and s.activeFlag=:activeFlag",
					    StoreMaster.class);
			qry1.setParameter("userid", id);
			qry1.setParameter("status", "Y");
			qry1.setParameter("activeFlag", "YES");
			int stroes = qry1.getResultList().size();
			strList = qry1.getResultList();
			/*
			Iterator<StoreMaster> iterator2 = strList.iterator();
			while (iterator2.hasNext()) {
				StoreMaster storeMaster = iterator2.next();
				//System.out.println("store name;  " + storeMaster.getStoreName());
			}
			*/
			System.out.println("number of stores : " + stroes);

			em.getTransaction().commit();
			//System.out.print("User logged in scessfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check User data", e);
		} finally {
			em.close();
		}
		
		return strList;
	}

	@Override
	public List<StoreMaster> getStoreByRestaurantId(int id) throws DAOException {
		// TODO Auto-generated method stub
		List<StoreMaster> storeList = null;
		EntityManager em = null;
		try {
			RestaurantMaster restaurant = new RestaurantMaster();
			restaurant.setId(id);
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getStoreByRestaurantId 111");
			em.getTransaction().begin();
			//System.out.println("getStoreByRestaurantId 222");
			TypedQuery<StoreMaster> qry = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.restaurant=:rest",
					    StoreMaster.class);
			//System.out.println("getStoreByRestaurantId 333");
			qry.setParameter("rest", restaurant);
			//System.out.println("getStoreByRestaurantId 444");
			storeList = qry.getResultList();
			//System.out.println("getStoreByRestaurantId 555");
			em.getTransaction().commit();
			//System.out.println("getStoreByRestaurantId 666");
			// em.close();
			//System.out.println("getStoreByRestaurantId 777");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In getStoreByRestaurantId DAOException", e);
		} finally {
			em.close();
		}
		return storeList;
	}

	@Override
	public List<Customer> getAllTabletUsers() throws DAOException {

		List<Customer> tabletUser = null;
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Customer> qry = null;
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			if (storeMaster != null) {
				int strId = storeMaster.getId();
				qry = em.createQuery("SELECT tu FROM Customer tu where tu.storeId=:strId",
				    Customer.class);
				qry.setParameter("strId", strId);
			}
			else {
				qry = em.createQuery("SELECT tu FROM Customer tu",
				    Customer.class);
			}

			tabletUser = (List<Customer>) qry.getResultList();
			em.getTransaction().commit();
			// em.close();
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw new DAOException("Check User data", e);
		} catch (Throwable e) {
			//System.out.println("hello 4b");
			e.printStackTrace();
		} finally {
			em.close();
		}
		return tabletUser;
	}

	@Override
	public Customer getTabletUserById(String tabletUserId) throws DAOException {

		Customer tabUser = null;

		String tabletUserid;
		EntityManager em = null;
		try {
			tabletUserid = tabletUserId;
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT u FROM Customer u WHERE u.contactNo= :tabletUserId");
			qry.setParameter("tabletUserId", tabletUserid);

			// userLst = (List<User>) qry.getResultList();
			tabUser = (Customer) qry.getSingleResult();

			em.getTransaction().commit();
			// em.close();

		} catch (Exception e) {
			throw new DAOException("Check Customer data", e);
		} finally {
			em.close();
		}
		return tabUser;
	}

	@Override
	public void updateTabletUser(Customer tabletUser) throws DAOException {
		EntityManager em = null;
		try {
			System.out.println("In UserDAOImpl.updateTabletUser");
			
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Customer tabletUser1 = em.find(Customer.class, tabletUser.getId());
			System.out.println("tabletUser1:" + tabletUser1);
			tabletUser1.setName(tabletUser.getName());
			tabletUser1.setUserId(tabletUser.getUserId());
			tabletUser1.setDesignation(tabletUser.getDesignation());
			/* tabletUser1.setRestMaster(tabletUser.get()); */
			tabletUser1.setContactNo(tabletUser.getContactNo());
			tabletUser1.setPassword(tabletUser.getPassword());
			em.getTransaction().commit();
			System.out.print("tabletUser data updated successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check tabletUser data", e);
		} finally {
			em.close();
		}
	}

}

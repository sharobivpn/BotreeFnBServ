package com.botree.restaurantapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.commonUtil.DateUtil;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.PosModules;
import com.botree.restaurantapp.model.PosModulesChild;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Component("loginDAO")
public class LoginDAOImpl implements LoginDAO {
  
	private final static Logger logger = LogManager.getLogger(LoginDAOImpl.class);

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
  
	EntityManager em = null;
	List<Customer> listCust = new ArrayList<Customer>();

	@Override
	public Customer getUserByCredential(Customer customer) throws DAOException {

		

		Customer loggedCust = null;
		StoreMaster storeMaster = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		System.out.println("date::::  " + date);
		
		/*
		 * try { curDate = sdf.parse(date); Date expiryDate =
		 * sdf.parse("2017-05-27"); if (curDate.compareTo(expiryDate) > 0) {
		 * loggedCust = new Customer(); loggedCust.setId(0); loggedCust
		 * .setMessage("License expired!, please contact info@sharobitech.com");
		 * loggedCust.setStatus("N"); return loggedCust;
		 * 
		 * } } catch (ParseException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */

		try {

			

			em = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			Query qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.contactNo= :cont AND c.password= :pass and c.status='Y'");
			qry.setParameter("cont", customer.getContactNo());
			qry.setParameter("pass", customer.getPassword());
			loggedCust = (Customer) qry.getSingleResult();
			if (loggedCust != null) {

				loggedCust.setStatus("Y");
				int storeId = loggedCust.getStoreId();
				// get the store
				// em.getTransaction().begin();
				Query qrygetstore = em
						.createQuery("SELECT s FROM StoreMaster s WHERE s.id=:storeid and s.status='Y'");

				qrygetstore.setParameter("storeid", storeId);
				storeMaster = (StoreMaster) qrygetstore.getSingleResult();
			}

			/*Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			String json = gson.toJson(loggedCust, Customer.class);*/

			transaction.commit();
			logger.debug("Reached EOM: {}", loggedCust);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			//e.printStackTrace();
			if (loggedCust == null) {
				loggedCust = new Customer();
				loggedCust.setId(0);
				loggedCust.setMessage("Invalid Credentials/Register First");
				loggedCust.setStatus("N");
				return loggedCust;
			} else if (storeMaster == null) {

				loggedCust = new Customer();
				loggedCust.setId(0);
				loggedCust
						.setMessage("Store Registration Expired.Please Register Again To Continue.");
				loggedCust.setStatus("N");
				return loggedCust;

			}
			// throw new DAOException("Check data to be Valid or not", e);
		} finally {
			if (em != null)
				em.close();
		}
		// license

		
		return loggedCust;
	}

	@Override
	public Customer getUserByContantNo(Customer customer) throws DAOException {
		Customer loggedCust = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			Query qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.id= :id");
			qry.setParameter("id", customer.getId());
			loggedCust = (Customer) qry.getSingleResult();
			transaction.commit();

			logger.info("Data fetched: {}", loggedCust.getName());
			return loggedCust;
		} catch (Exception e) {
			logger.error("Exception:", e);

			return null;
		} finally {
			em.close();
		}

	}

	@Override
	public List<Customer> getAllWaiters(String storeId) throws DAOException {
		List<Customer> waiterList = null;
		int storeid1 = Integer.parseInt(storeId);

		try {
			System.out.println("LoginDAOImpl.getAllWaiters called.");
			
			EntityManager em = entityManagerFactory.createEntityManager();
			TypedQuery<Customer> qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.storeId=:storeId and c.type='w'",
					    Customer.class);
			qry.setParameter("storeId", storeid1);
			waiterList = qry.getResultList();
			em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in LoginDAOImpl.getAllWaiters", e);

		}
		finally {
		  
		}
		return waiterList;
	}

	@Override
	public List<PosModules> getPosModulesByUserId(String userId, String storeId)
			throws DAOException {
		List<PosModulesChild> posModuleList = new ArrayList<PosModulesChild>();
		List<PosModules> posModulList = new ArrayList<PosModules>();
		int storeid1 = Integer.parseInt(storeId);
		// int userid = Integer.parseInt(userId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			Session session = (Session) em.getDelegate();
	    SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session
	          .getSessionFactory();
	    con = sessionFactory.getConnectionProvider().getConnection();
			//con = session.connection();
	    
			String status = "Y";

			// "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=?";
			String GET_POS_MODULE_LIST = "SELECT c.id,p.module_name,c.login_id,c.module_id,c.order1 FROM rg_m_pos_modules_c c,rg_m_pos_modules p where p.status=? and c.status=? and c.store_id=? and p.is_report=? and c.module_id=p.id";

			ps = con.prepareStatement(GET_POS_MODULE_LIST);
			// ps.setInt(1, userid);
			ps.setString(1, status);
			ps.setString(2, status);
			ps.setInt(3, storeid1);
			ps.setString(4, "N");
			rs = ps.executeQuery();

			while (rs.next()) {
				PosModulesChild posModule = new PosModulesChild();
				posModule.setId(rs.getInt("id"));
				posModule.setModuleName(rs.getString("module_name"));
				posModule.setLoginId(rs.getInt("login_id"));
				posModule.setModuleId(rs.getInt("module_id"));
				posModule.setOrder(rs.getInt("order1"));

				posModuleList.add(posModule);

			}

			// get all pos modules
			/*
			 * Query qry = em .createQuery(
			 * "SELECT p FROM PosModules p WHERE p.storeId=:storeId and p.status='Y' and p.isReport='N'"
			 * ); qry.setParameter("storeId", storeid1); posModulList =
			 * (List<PosModules>) qry.getResultList();
			 * 
			 * Iterator<PosModulesChild> iterator=posModuleList.iterator();
			 * while (iterator.hasNext()) { PosModulesChild posModulesChild =
			 * (PosModulesChild) iterator .next(); int
			 * moduleId=posModulesChild.getModuleId(); Iterator<PosModules>
			 * iterator2=posModulList.iterator(); while (iterator2.hasNext()) {
			 * PosModules posModule = (PosModules) iterator2.next(); int
			 * id=posModule.getId(); if(id==moduleId){
			 * posModulesChild.setPosModules(posModule); break; }
			 * 
			 * }
			 * 
			 * }
			 */

			TypedQuery<PosModules> qry = em
					.createQuery("SELECT p FROM PosModules p WHERE p.status='Y' and p.isReport='N'",
					    PosModules.class);

			posModulList = qry.getResultList();

			if (posModuleList.size() == 0) {
				Iterator<PosModules> iterator3 = posModulList.iterator();
				while (iterator3.hasNext()) {
					PosModules posModule = (PosModules) iterator3.next();
					posModule.setModPresent("Y");

				}
			}

			if (posModuleList.size() > 0) {
				Iterator<PosModules> iterator3 = posModulList.iterator();
				while (iterator3.hasNext()) {
					PosModules posModule = (PosModules) iterator3.next();
					posModule.setModPresent("N");

				}
				Iterator<PosModulesChild> iterator = posModuleList.iterator();
				while (iterator.hasNext()) {
					PosModulesChild posModulesChild = (PosModulesChild) iterator
							.next();
					int moduleId = posModulesChild.getModuleId();
					Iterator<PosModules> iterator2 = posModulList.iterator();
					while (iterator2.hasNext()) {
						PosModules posModule = (PosModules) iterator2.next();
						// posModule.setModPresent("N");
						int id = posModule.getId();
						if (id == moduleId) {
							posModule.setModPresent("Y");
							break;
						}

					}

				}
			}

			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<PosModulesChild>>() {
			}.getType();
			String json = gson.toJson(posModuleList, type);

			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("problem occured in getPosModulesByUserId",
					e);

		}
		Collections.sort(posModulList);
		return posModulList;
	}

	@Override
	public List<PosModules> getReportByStore(String userId, String storeId)
			throws DAOException {
		List<PosModulesChild> posModuleList = new ArrayList<PosModulesChild>();
		List<PosModules> posModulList = new ArrayList<PosModules>();
		int storeid1 = Integer.parseInt(storeId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PosModules> posModulesFinal=new ArrayList<PosModules>();
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session
            .getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			//con = session.connection();
			String status = "Y";

			// "select * from fm_m_food_types where store_id = ? and type=? and delete_flag=?";
			String GET_POS_MODULE_LIST = "SELECT c.id,p.module_name,c.login_id,c.module_id,c.order1 FROM rg_m_pos_modules_c c,rg_m_pos_modules p where p.status=? and c.status=? and c.store_id=? and p.is_report=? and c.module_id=p.id";

			ps = con.prepareStatement(GET_POS_MODULE_LIST);
			ps.setString(1, status);
			ps.setString(2, status);
			ps.setInt(3, storeid1);
			ps.setString(4, "Y");
			rs = ps.executeQuery();

			while (rs.next()) {
				PosModulesChild posModule = new PosModulesChild();
				posModule.setId(rs.getInt("id"));
				posModule.setModuleName(rs.getString("module_name"));
				posModule.setLoginId(rs.getInt("login_id"));
				posModule.setModuleId(rs.getInt("module_id"));
				posModule.setOrder(rs.getInt("order1"));

				posModuleList.add(posModule);

			}

			TypedQuery<PosModules> qry = em
					.createQuery("SELECT p FROM PosModules p WHERE p.status='Y' and p.isReport='Y'",
					    PosModules.class);
			
			posModulList = qry.getResultList();

			Iterator<PosModules> iterator3 = posModulList.iterator();
			while (iterator3.hasNext()) {
				PosModules posModule = (PosModules) iterator3.next();
				posModule.setModPresent("N");

			}

			Iterator<PosModulesChild> iterator = posModuleList.iterator();
			while (iterator.hasNext()) {
				PosModulesChild posModulesChild = (PosModulesChild) iterator
						.next();
				int moduleId = posModulesChild.getModuleId();
				Iterator<PosModules> iterator2 = posModulList.iterator();
				while (iterator2.hasNext()) {
					PosModules posModule = (PosModules) iterator2.next();
					int id = posModule.getId();
					if (id == moduleId) {
						posModule.setAdmin(null);
						posModule.setModPresent("Y");
						posModulesFinal.add(posModule);
						break;
					}

				}

			}

			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<PosModules>>() {
			}.getType();
			String json = gson.toJson(posModulesFinal, type);

			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			em.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("problem occured in getPosModulesByUserId",
					e);

		}
		Collections.sort(posModulesFinal);
		return posModulesFinal;
	}

}

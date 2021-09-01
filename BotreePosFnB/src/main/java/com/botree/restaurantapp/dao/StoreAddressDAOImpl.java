package com.botree.restaurantapp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
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

import com.botree.restaurantapp.commonUtil.StoredProcedures;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.DashBoardData;
import com.botree.restaurantapp.model.DashPaymentSummary;
import com.botree.restaurantapp.model.DashSalesSummary;
import com.botree.restaurantapp.model.DashSalesSummaryOrderType;
import com.botree.restaurantapp.model.DashSalesSummaryPaymentType;
import com.botree.restaurantapp.model.DashTopCustomer;
import com.botree.restaurantapp.model.DashTopItem;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MobileFontSetting;
import com.botree.restaurantapp.model.PosModules;
import com.botree.restaurantapp.model.PosModulesChild;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreDayBookRegister;
import com.botree.restaurantapp.model.StoreDayBookRegisterBean;
import com.botree.restaurantapp.model.StoreFeatures;
import com.botree.restaurantapp.model.StoreFeaturesChild;
import com.botree.restaurantapp.model.StoreLocator;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.StoreSMSConfiguration;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.model.UserTransaction;
import com.botree.restaurantapp.model.account.AccountDTO;
import com.botree.restaurantapp.model.dto.TbTableMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import net.sf.resultsetmapper.ReflectionResultSetMapper;

@Component("storeAddressDAO")
public class StoreAddressDAOImpl implements StoreAddressDAO {
  
	private final static Logger LOGGER = LogManager.getLogger(StoreAddressDAOImpl.class);
	
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public void createStore(StoreLocator store) throws DAOException {

		StoreLocator store1 = new StoreLocator();
		EntityManager em = null;
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			store1.setAddress(store.getAddress());
			em.persist(store1);
			em.getTransaction().commit();
			System.out.print("Store Address saved successfully...");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void createTable(TbTableMaster table) throws DAOException {

		EntityManager em = null;
		FacesContext context = FacesContext.getCurrentInstance();
		StoreMaster storeMaster = (StoreMaster) context.getExternalContext()
				.getSessionMap().get("selectedstore");

		int storeId = 0;
		if (storeMaster != null) {
			int restId = storeMaster.getRestaurantId();
			System.out.println("rest id:  " + restId);
			storeId = storeMaster.getId();
		}

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			table.setStoreId(storeId);
			table.setAvailableForBooking(true);
			em.persist(table);
			em.getTransaction().commit();
			System.out.print("Tbale saved successfully...");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}

	}

	@Override
	public List<StoreMaster> getAllStore() throws DAOException {

		List<StoreMaster> storeList = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreMaster> qry = em
					//.createQuery("SELECT s FROM StoreLocator s where s.status='Y'",
          .createQuery("SELECT s FROM StoreMaster s where s.status='Y'",
					    StoreMaster.class);
			storeList = qry.getResultList();
			em.getTransaction().commit();
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return storeList;
	}

	@Override
	public List<PosModules> getAllModules() throws DAOException {

		List<PosModules> moduleList = null;
		List<PosModulesChild> moduleChldList = null;
		EntityManager em = null;

		StoreMaster storeMaster = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			storeMaster = (StoreMaster) context.getExternalContext()
					.getSessionMap().get("store");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<PosModules> qry = em
					.createQuery("SELECT m FROM PosModules m where m.deleteFlag='N'",
					    PosModules.class);
			moduleList = (List<PosModules>) qry.getResultList();

			TypedQuery<PosModulesChild> qry1 = em
					.createQuery("SELECT m FROM PosModulesChild m where m.storeId=:storeId and m.deleteFlag='N'",
					    PosModulesChild.class);
			qry1.setParameter("storeId", storeId);
			moduleChldList = (List<PosModulesChild>) qry1.getResultList();

			Iterator<PosModules> iterator = moduleList.iterator();
			while (iterator.hasNext()) {
				PosModules posModule = (PosModules) iterator.next();
				int parentId = posModule.getId();
				Iterator<PosModulesChild> iterator1 = moduleChldList.iterator();
				while (iterator1.hasNext()) {
					PosModulesChild posModulesChild = (PosModulesChild) iterator1
							.next();
					int childId = posModulesChild.getModuleId();
					if (parentId == childId) {
						if (posModulesChild.getStatus().equalsIgnoreCase("Y")) {
							posModule.setPresent(true);
							posModule.setModPresent("yes");
							break;
						}
					}

				}

			}
			em.getTransaction().commit();
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return moduleList;
	}

	@Override
	public void update(StoreLocator store) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			StoreLocator store1 = em.find(StoreLocator.class, store.getId());
			store1.setAddress(store.getAddress());
			// em.merge(store1);
			em.getTransaction().commit();
			System.out.print("Store Address updated successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be updated", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void updateModule(List<PosModules> modList) throws DAOException {
		EntityManager em = null;
		List<Integer> idLst = new ArrayList<Integer>();
		List<Integer> idLst1 = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		String delFlag = "N";
		String status = "Y";
		String user = "admin";
		int maxId = 0;

		try {
			String pattern = "dd-MM-yyyy";
			String dateInString = new SimpleDateFormat(pattern)
					.format(new Date());
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session
          .getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("store");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}
			Iterator<PosModules> iterator = modList.iterator();
			while (iterator.hasNext()) {
				PosModules posModules = (PosModules) iterator.next();
				String modPresent = posModules.getModPresent();
				boolean isPresent = posModules.isPresent();
				if (modPresent != null) {
					if (modPresent.equalsIgnoreCase("yes")
							&& isPresent == false) {

						idLst.add(posModules.getId());
					}
				}

				else if (modPresent == null) {
					if (isPresent == true) {
						idLst1.add(posModules.getId());
					}
				}

			}

			Iterator<Integer> iterator2 = idLst.iterator();
			while (iterator2.hasNext()) {
				Integer modId = (Integer) iterator2.next();
				String update_module_status = "update rg_m_pos_modules_c set status='N' where store_id = ? and module_id=? and delete_flag=?";
				ps = con.prepareStatement(update_module_status);
				ps.setInt(1, storeId);
				ps.setInt(2, modId);
				ps.setString(3, delFlag);

				ps.executeUpdate();
			}

			String select_max_id = "SELECT max(id) as max FROM rg_m_pos_modules_c";
			PreparedStatement ps2 = con.prepareStatement(select_max_id);
			ResultSet rs1 = ps2.executeQuery(select_max_id);
			while (rs1.next()) {
				maxId = rs1.getInt("max");

			}

			Iterator<Integer> iterator3 = idLst1.iterator();
			while (iterator3.hasNext()) {
				Integer modId = (Integer) iterator3.next();
				int id = maxId + 1;
				String insert_module_status = "insert into rg_m_pos_modules_c (id,store_id,login_id,module_id,order1,status,delete_flag,created_by,created_date,updated_by,updated_date) values (?,?,?,?,?,?,?,?,?,?,?)";
				ps1 = con.prepareStatement(insert_module_status);
				ps1.setInt(1, id);
				ps1.setInt(2, storeId);
				ps1.setInt(3, 88);
				ps1.setInt(4, modId.intValue());
				ps1.setInt(5, modId.intValue());
				ps1.setString(6, status);
				ps1.setString(7, delFlag);
				ps1.setString(8, user);
				ps1.setString(9, dateInString);
				ps1.setString(10, user);
				ps1.setString(11, dateInString);

				ps1.execute();
				maxId = id;
			}
			con.commit();
			em.getTransaction().commit();
			/*
			 * Query qry1 = em .createQuery(
			 * "SELECT m FROM PosModulesChild m where m.storeId=:storeId and m.moduleId IN (:idLst) and m.deleteFlag='N'"
			 * ); qry1.setParameter("storeId", storeId);
			 * qry1.setParameter("idLst", idLst); moduleChldList =
			 * (List<PosModulesChild>) qry1.getResultList();
			 * Iterator<PosModulesChild> iterator2=moduleChldList.iterator();
			 * while (iterator2.hasNext()) { PosModulesChild posModulesChild =
			 * (PosModulesChild) iterator2 .next();
			 * posModulesChild.setStatus("N"); em.merge(posModulesChild);
			 * 
			 * }
			 */

			if (con != null) {

				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (ps1 != null) {
				ps1.close();
			}
			if (ps2 != null) {
				ps2.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
			System.out.println("before commit");

			// StoreLocator store1 = em.find(StoreLocator.class, store.getId());
			// store1.setAddress(store.getAddress());
			// em.merge(store1);
			// em.getTransaction().commit();
			System.out.print("Store Address updated successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be updated", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void delete(StoreLocator store) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			StoreLocator store1 = em.find(StoreLocator.class, store.getId());
			em.remove(store1);
			em.getTransaction().commit();
			System.out.print("Store Address deleted successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}

	}

	@Override
	public List<StoreMaster> getByStoreId(Integer storeId) throws DAOException {

		List<StoreMaster> storeList = null;
		// int storeid=Integer.parseInt(storeId);
		RestaurantMaster master1 = new RestaurantMaster();
		master1.setId(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getByStoreId 111");
			em.getTransaction().begin();
			TypedQuery<StoreMaster> qry = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.restaurant=:storeid",
					    StoreMaster.class);
			System.out.println("getByStoreId 222");
			qry.setParameter("storeid", master1);
			//System.out.println("getByStoreId 333");
			storeList = qry.getResultList();
			//System.out.println("getByStoreId 444");
			System.out.println("Number of stores: " + storeList.size());
			//System.out.println("getByStoreId 555");
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<RestaurantMaster>>() {
			}.getType();
			String json = gson.toJson(storeList, type);
			//System.out.println("===>" + json);

			em.getTransaction().commit();
			//System.out.println("getByStoreId 666");
			// em.close();
		} catch (Exception e) {
			//System.out.println("getByStoreId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return storeList;
	}

	@Override
	public StoreDayBookRegisterBean insertOpenTime(Integer storeId,
			String userId, String opentimeId) throws DAOException,
			ParseException {

		int userid = Integer.parseInt(userId);
		int opentimeid = Integer.parseInt(opentimeId);
		String status = "";
		
		// StoreDayBookRegister storeDayBookRegister = new
		// StoreDayBookRegister();

		Date currDate = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		EntityManager em = null;
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		if (opentimeid == 0) {
			currDate = new Date();
		} else {
			StoreDayBookRegister register = em.find(StoreDayBookRegister.class,
					opentimeid);
			currDate = register.getOrderDate();
		}
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date prevDate = cal.getTime();
		System.out.println("prevDate=" + prevDate);
		TypedQuery<StoreDayBookRegister> qry1 = em
				.createQuery("SELECT sbr FROM StoreDayBookRegister sbr WHERE sbr.orderDate=:prevDate AND sbr.storeId=:storeid",
				    StoreDayBookRegister.class);
		qry1.setParameter("storeid", storeId);
		qry1.setParameter("prevDate",
				dateFormat.parse(dateFormat.format(prevDate)));
		List<StoreDayBookRegister> storeDayBookRegisterListprev = qry1.getResultList();

		if (!storeDayBookRegisterListprev.isEmpty()) {
			for (StoreDayBookRegister storeDayBookRegister : storeDayBookRegisterListprev) {
				if (null == storeDayBookRegister.getCloseTime()) {
					Calendar cal1 = Calendar.getInstance();
					// remove next line if you're always using the current time.
					cal1.setTime(new Date());
					//cal1.add(Calendar.HOUR, -1);
					cal1.add(Calendar.MINUTE, -1);
					Date prevhour = cal1.getTime();
					storeDayBookRegister.setCloseBy(userid);
					Timestamp timestamp = new java.sql.Timestamp(
							prevhour.getTime());
					storeDayBookRegister.setCloseTime(timestamp);
					entityManagerFactory = PersistenceListener
							.getEntityManager();
					em.persist(storeDayBookRegister);
					/*
					 * EntityManager em1 =
					 * entityManagerFactory.createEntityManager();
					 * em1.getTransaction().begin();
					 * em1.persist(storeDayBookRegister);
					 * 
					 * em1.getTransaction().commit();
					 */
				}
			}
		}

		TypedQuery<StoreDayBookRegister> qry = em
				.createQuery("SELECT sbr FROM StoreDayBookRegister sbr WHERE sbr.orderDate=:currdate AND sbr.storeId=:storeid",
				    StoreDayBookRegister.class);
		qry.setParameter("storeid", storeId);
		qry.setParameter("currdate", dateFormat.parse(dateFormat.format(currDate)));
		
		List<StoreDayBookRegister> storeDayBookRegisterList = qry.getResultList();
		if (storeDayBookRegisterList.isEmpty()) {
			try {
				StoreDayBookRegister register = new StoreDayBookRegister();
				register.setOrderDate(currDate);
				register.setStoreId(storeId);
				register.setOpenBy(userid);
				Timestamp timestamp = new java.sql.Timestamp(currDate.getTime());
				register.setOpenTime(timestamp);
				

				em.persist(register);

				em.getTransaction().commit();

				// status = "Restaurant successfully open.";
				StoreDayBookRegisterBean storeDayBookRegisterBean = new StoreDayBookRegisterBean();
				storeDayBookRegisterBean.setId(register.getId());
				storeDayBookRegisterBean.setOpenBy(register.getOpenBy());
				storeDayBookRegisterBean.setOrderDate(register.getOrderDate());
				storeDayBookRegisterBean.setStoreId(register.getStoreId());
				storeDayBookRegisterBean
						.setUserText("Restaurant successfully open.");
				Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation().create();
				java.lang.reflect.Type type = new TypeToken<StoreDayBookRegisterBean>() {
				}.getType();
				String json = gson.toJson(storeDayBookRegisterBean, type);
				//System.out.println("StoreDayBookRegister===>" + json);
				return storeDayBookRegisterBean;

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check data to be saved", e);
			} finally {
				em.close();
			}

		} else {
			StoreDayBookRegisterBean storeDayBookRegisterBean = new StoreDayBookRegisterBean();
			for (StoreDayBookRegister storeDayBookRegister : storeDayBookRegisterList) {
				status = getCustomer(em, storeDayBookRegister);
				StoreDayBookRegister register = em.find(
						StoreDayBookRegister.class,
						storeDayBookRegister.getId());
				// storeDayBookRegister = register;

				storeDayBookRegisterBean.setId(register.getId());
				storeDayBookRegisterBean.setOpenBy(register.getOpenBy());
				storeDayBookRegisterBean.setOrderDate(register.getOrderDate());
				storeDayBookRegisterBean.setStoreId(register.getStoreId());
				storeDayBookRegisterBean.setUserText(status);
				/*Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation().create();
				java.lang.reflect.Type type = new TypeToken<StoreDayBookRegisterBean>() {
				}.getType();
				String json = gson.toJson(storeDayBookRegisterBean, type);*/

			}

			return storeDayBookRegisterBean;
		}

	}

	@Override
	public StoreDayBookRegisterBean checkRestaurantOpenStatus(Integer storeId)
			throws DAOException, ParseException {

		String status = "";
		EntityManager em = null;
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		StoreDayBookRegisterBean storeDayBookRegisterBean = new StoreDayBookRegisterBean();
		StoreDayBookRegister storeDayBookRegister =null;
		Date currDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			TypedQuery<StoreDayBookRegister> qry = em
					.createQuery("SELECT s FROM StoreDayBookRegister s WHERE s.closeTime IS NULL and s.storeId=:storeId order by s.id desc",
					    StoreDayBookRegister.class); // limit 1
			qry.setParameter("storeId", storeId);
			storeDayBookRegister = qry.setMaxResults(1).getSingleResult();

			status = getCustomer(em, storeDayBookRegister);

			storeDayBookRegisterBean.setId(storeDayBookRegister.getId());
			storeDayBookRegisterBean
					.setOpenBy(storeDayBookRegister.getOpenBy());

			storeDayBookRegisterBean.setOrderDate(storeDayBookRegister
					.getOrderDate());
			storeDayBookRegisterBean.setStoreId(storeDayBookRegister
					.getStoreId());
			storeDayBookRegisterBean.setUserText(status);

		} catch (Exception e) {
			e.printStackTrace();
			TypedQuery<StoreDayBookRegister> qry = em
					.createQuery("SELECT s FROM StoreDayBookRegister s WHERE s.orderDate=:orderDate and s.storeId=:storeId",
					    StoreDayBookRegister.class);
			qry.setParameter("orderDate", dateFormat.parse(dateFormat.format(currDate)));
			qry.setParameter("storeId", storeId);
			List<StoreDayBookRegister> storeDayBookRegisterList = qry.getResultList();
			
			if(!storeDayBookRegisterList.isEmpty()){
				storeDayBookRegister=storeDayBookRegisterList.get(0);
				//get customer
				status = getCustomer(em, storeDayBookRegister);
				storeDayBookRegisterBean.setId(storeDayBookRegister.getId());
				storeDayBookRegisterBean
						.setOpenBy(storeDayBookRegister.getOpenBy());

				storeDayBookRegisterBean.setOrderDate(storeDayBookRegister
						.getOrderDate());
				storeDayBookRegisterBean.setStoreId(storeDayBookRegister
						.getStoreId());
				storeDayBookRegisterBean.setUserText(status);
			}
			else {
					storeDayBookRegister= new StoreDayBookRegister();
					storeDayBookRegisterBean.setId(0);
					storeDayBookRegisterBean
							.setUserText("Restaurant not open yet, please start the restaurant first.");
			}
		}
		finally {
			em.close();
		}

		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<StoreDayBookRegisterBean>() {
		}.getType();
		String json = gson.toJson(storeDayBookRegisterBean, type);

		return storeDayBookRegisterBean;
	}

	private String getCustomer(EntityManager em,
			StoreDayBookRegister storeDayBookRegister) {
		String status;
		Customer customer = em.find(Customer.class,
				storeDayBookRegister.getOpenBy());
		status = "Restaurant Already open for today by "
				+ customer.getContactNo();
		return status;
	}

	@Override
	public String insertCloseTime(Integer storeId, String userId,
			String opentimeId) throws DAOException, ParseException {

		int userid = Integer.parseInt(userId);
		String status = "";
		Date currDate = new Date();
		EntityManager em = null;
    
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		// commented for other logic
		/*
		 * Query qry = em .createQuery(
		 * "SELECT sbr FROM StoreDayBookRegister sbr WHERE sbr.orderDate=:currdate AND sbr.storeId=:storeid"
		 * ); qry.setParameter("storeid", storeid); qry.setParameter("currdate",
		 * dateFormat.parse( dateFormat.format(currDate)));
		 * List<StoreDayBookRegister> storeDayBookRegisterList =
		 * (List<StoreDayBookRegister>) qry .getResultList(); if
		 * (storeDayBookRegisterList.isEmpty()) { status =
		 * "Restaurant not open yet, please start the restaurant first."; }else{
		 * 
		 * try { StoreDayBookRegister register =
		 * em.find(StoreDayBookRegister.class,
		 * storeDayBookRegisterList.get(0).getId());
		 * register.setCloseBy(userid); Timestamp timestamp = new
		 * java.sql.Timestamp(currDate.getTime());
		 * register.setCloseTime(timestamp); entityManagerFactory =
		 * PersistenceListener.getEntityManager();
		 * 
		 * em.persist(register);
		 * 
		 * em.getTransaction().commit();
		 * 
		 * status = "Restaurant successfully close.";
		 * 
		 * } catch (Exception e) { e.printStackTrace(); throw new
		 * DAOException("Check data to be saved", e); } finally { em.close(); }
		 * }
		 */

		try {
			System.out.println("opentimeId=" + opentimeId);
			if (Integer.valueOf(opentimeId) == 0) {

				status = "Restaurant not open yet, please start the restaurant first.";
			} else {
				StoreDayBookRegister register = em
						.find(StoreDayBookRegister.class,
								Integer.valueOf(opentimeId));
				register.setCloseBy(userid);
				Timestamp timestamp = new java.sql.Timestamp(currDate.getTime());
				register.setCloseTime(timestamp);
				

				em.persist(register);

				em.getTransaction().commit();

				status = "Restaurant successfully close.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be saved", e);
		} finally {
			em.close();
		}
		return status;
	}

	@Override
	public List<StoreMaster> getAllStores() throws DAOException {
		// TODO Auto-generated method stub
		List<StoreMaster> storeList = null;
		/*
		 * RestaurantMaster master = new RestaurantMaster(); int storeid =
		 * Integer.parseInt(storeId); master.setId(storeid);
		 */

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getByStoreId 111");
			em.getTransaction().begin();
			TypedQuery<StoreMaster> qry = em.createQuery("SELECT s FROM StoreMaster s",
			    StoreMaster.class);// where s.restaurantId=:restId

			storeList = qry.getResultList();
			em.getTransaction().commit();
			System.out.println("numb of stores: " + storeList.size());

		} catch (Exception e) {
			//System.out.println("getByStoreId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if (em != null)
				em.close();
		}
		return storeList;
	}

	@Override
	public List<StoreMaster> getAllStoresByStoreId() throws DAOException {
		// TODO Auto-generated method stub
		List<StoreMaster> storeList = null;
		/*
		 * RestaurantMaster master = new RestaurantMaster(); int storeid =
		 * Integer.parseInt(storeId); master.setId(storeid);
		 */

		FacesContext context = FacesContext.getCurrentInstance();
		StoreMaster storeMaster = (StoreMaster) context.getExternalContext()
				.getSessionMap().get("selectedstore");

		int storeId = 0;
		if (storeMaster != null) {
			int restId = storeMaster.getRestaurantId();
			System.out.println("rest id:  " + restId);
			storeId = storeMaster.getId();
		}

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getByStoreId 111");
			em.getTransaction().begin();
			// Query qry =
			// em.createQuery("SELECT s FROM StoreMaster s where s.restaurantId=:restId");//where
			// s.restaurantId=:restId
			TypedQuery<StoreMaster> qry = em
					.createQuery("SELECT s FROM StoreMaster s where s.id=:storeId and s.status='Y'",
					    StoreMaster.class);
			qry.setParameter("storeId", storeId);
			storeList = qry.getResultList();
			em.getTransaction().commit();
			System.out.println("numb of stores: " + storeList.size());
		} catch (Exception e) {
			//System.out.println("getByStoreId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if (em != null)
				em.close();
		}
		return storeList;
	}

	@Override
	public List<TbTableMaster> getAllTablesByStoreId() throws DAOException {
		// TODO Auto-generated method stub
		List<TbTableMaster> tabList = null;

		FacesContext context = FacesContext.getCurrentInstance();
		StoreMaster storeMaster = (StoreMaster) context.getExternalContext()
				.getSessionMap().get("selectedstore");

		int storeId = 0;
		if (storeMaster != null) {
			int restId = storeMaster.getRestaurantId();
			System.out.println("rest id:  " + restId);
			storeId = storeMaster.getId();
		}

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<TbTableMaster> qry = em
					.createQuery("SELECT t FROM TbTableMaster t where t.storeId=:storeId",
					    TbTableMaster.class);
			qry.setParameter("storeId", storeId);
			tabList = qry.getResultList();
			em.getTransaction().commit();
			System.out.println("numb of tables: " + tabList.size());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if (em != null)
				em.close();
		}
		return tabList;
	}

	@Override
	public List<StoreMaster> getStoresByCity(String city, String cuisineType)
			throws DAOException {
		// TODO Auto-generated method stub
		List<StoreMaster> storeList = null;

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getByStoreId 111");
			em.getTransaction().begin();
			TypedQuery<StoreMaster> qry = em
					.createQuery("SELECT s FROM StoreMaster s where s.city=:city",
					    StoreMaster.class);// where
																					// s.restaurantId=:restId
			qry.setParameter("city", city);
			storeList = qry.getResultList();
			em.getTransaction().commit();
			System.out.println("numb of stores: " + storeList.size());
		} catch (Exception e) {
			//System.out.println("getByStoreId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if (em != null)
				em.close();
		}
		return storeList;
	}

	@Override
	public List<String> getAllCitiesForStores() throws DAOException {
		// TODO Auto-generated method stub
		List<String> cityList = null;

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			System.out.println("getByStoreId 111");
			em.getTransaction().begin();
			TypedQuery<String> qry = em
					.createQuery("SELECT s.city FROM StoreMaster s where s.status='y' group by s.city",
					    String.class);// where s.restaurantId=:restId

			cityList = qry.getResultList();
			em.getTransaction().commit();
			System.out.println("numb of stores: " + cityList.size());
		} catch (Exception e) {
			//System.out.println("getByStoreId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if (em != null)
				em.close();
		}
		return cityList;
	}

	@Override
	public List<RestaurantMaster> getAllRestaurant() throws DAOException {

		List<RestaurantMaster> restaurantList = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getAllRestaurant 000");
			TypedQuery<RestaurantMaster> qry = em
					.createQuery("SELECT r FROM RestaurantMaster r WHERE r.status='Y' AND r.activeFlag='YES'",
					    RestaurantMaster.class);
			//System.out.println("getAllRestaurant 111");
			//System.out.println("getAllRestaurant 222");
			restaurantList = (List<RestaurantMaster>) qry.getResultList();
			System.out.println("No. of Restaurants : " + restaurantList.size());
			//System.out.println("getAllRestaurant 333");
			//System.out.println("getAllRestaurant 444");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In getAllRestaurant DAOException", e);
		} finally {
			em.close();
		}
		return restaurantList;
	}

	@Override
	public List<RestaurantMaster> getRestaurants() throws DAOException {
		// TODO Auto-generated method stub
		List<RestaurantMaster> restaurantList = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getAllRestaurant 000");
			TypedQuery<RestaurantMaster> qry = em
					.createQuery("SELECT r FROM RestaurantMaster r WHERE r.status='Y'",
					    RestaurantMaster.class);
			//System.out.println("getAllRestaurant 111");
			//System.out.println("getAllRestaurant 222");
			restaurantList = qry.getResultList();
			System.out.println("No. of Restaurants : " + restaurantList.size());
			//System.out.println("getAllRestaurant 333");
			//System.out.println("getAllRestaurant 444");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In Restaurants DAOException", e);
		} finally {
			em.close();
		}
		return restaurantList;
	}

	@Override
	public List<StoreMaster> getAllStoresWithLatLong(Customer customerParam)
			throws DAOException {

		ArrayList<StoreMaster> sortedStoresList = new ArrayList<StoreMaster>();
		TreeMap<Double, StoreMaster> sortedStoreMap = new TreeMap<Double, StoreMaster>();
		try {
			List<StoreMaster> stores = getAllStores();
			System.out.println("No of Stores: " + stores.size());
			for (StoreMaster store : stores) {
				double degToRad = Math.PI / 180.0;
				double phi1 = customerParam.getMobileLatitude() * degToRad;
				double phi2 = store.getLatitude() * degToRad;
				double lam1 = customerParam.getMobileLongitude() * degToRad;
				double lam2 = store.getLongitude() * degToRad;

				double d = 6371.01 * Math.acos(Math.sin(phi1) * Math.sin(phi2)
						+ Math.cos(phi1) * Math.cos(phi2)
						* Math.cos(lam2 - lam1));
				//System.out.println("distance is:  " + d);

				if (d < 100.0) {
					DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(2);
					String val = df.format(d);
					val = val.replaceAll(",", "");

					double key = Double.valueOf(val) + ((double) store.getId())
							/ 10000;
					//System.out.println(key + " - " + store.getRestaurantId());

					sortedStoreMap.put(new Double(key), store);
				}
			}

			Set<Entry<Double, StoreMaster>> set = sortedStoreMap.entrySet();
			Iterator<Entry<Double, StoreMaster>> i = set.iterator();
			while (i.hasNext()) {
			  Entry<Double, StoreMaster> me = i.next();
				if (!sortedStoresList.contains(me.getValue()))
					sortedStoresList.add((StoreMaster) (me.getValue()));
			}

			System.out.println("sorted stres: " + sortedStoresList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In getAllRestaurant DAOException", e);
		}

		return sortedStoresList;
	}

	public List<StoreMaster> getAllStoresByCity(Customer customerParam)
			throws DAOException {

		ArrayList<StoreMaster> sortedStoresList = new ArrayList<StoreMaster>();
		TreeMap<Double, StoreMaster> sortedStoreMap = new TreeMap<Double, StoreMaster>();
		try {
			List<StoreMaster> stores = getStoresByCity(customerParam.getCity(),
					customerParam.getCuisineType());
			System.out.println("No of Stores: " + stores.size());
			for (StoreMaster store : stores) {
				double degToRad = Math.PI / 180.0;
				double phi1 = customerParam.getMobileLatitude() * degToRad;
				double phi2 = store.getLatitude() * degToRad;
				double lam1 = customerParam.getMobileLongitude() * degToRad;
				double lam2 = store.getLongitude() * degToRad;

				double d = 6371.01 * Math.acos(Math.sin(phi1) * Math.sin(phi2)
						+ Math.cos(phi1) * Math.cos(phi2)
						* Math.cos(lam2 - lam1));

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				String val = df.format(d);
				val = val.replaceAll(",", "");

				double key = Double.valueOf(val) + ((double) store.getId())
						/ 10000;
				//System.out.println(key + " - " + store.getRestaurantId());

				sortedStoreMap.put(new Double(key), store);
			}

			Set<Entry<Double, StoreMaster>> set = sortedStoreMap.entrySet();
			Iterator<Entry<Double, StoreMaster>> i = set.iterator();
			while (i.hasNext()) {
			  Entry<Double, StoreMaster> me = i.next();
				if (!sortedStoresList.contains(me.getValue()))
					sortedStoresList.add(me.getValue());
			}

			System.out.println("sorted stres: " + sortedStoresList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In getAllRestaurant DAOException", e);
		}

		return sortedStoresList;
	}

	@Override
	public List<RestaurantMaster> getAllRestaurantWithLatLong(
			Customer customerParam) throws DAOException {

		List<RestaurantMaster> restaurantList = null;
		List<RestaurantMaster> sortedRestaurantList = new ArrayList<RestaurantMaster>();
		ArrayList<Integer> ress = new ArrayList<Integer>();

		TreeMap<Double, Integer> sortedStoreMap = new TreeMap<Double, Integer>();
		
		try {
			List<StoreMaster> stores = getAllStores();
			System.out.println("No of Stores: " + stores.size());
			for (StoreMaster store : stores) {
				double degToRad = Math.PI / 180.0;
				double phi1 = customerParam.getMobileLatitude() * degToRad;
				double phi2 = store.getLatitude() * degToRad;
				double lam1 = customerParam.getMobileLongitude() * degToRad;
				double lam2 = store.getLongitude() * degToRad;

				double d = 6371.01 * Math.acos(Math.sin(phi1) * Math.sin(phi2)
						+ Math.cos(phi1) * Math.cos(phi2)
						* Math.cos(lam2 - lam1));

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				String val = df.format(d);
				val = val.replaceAll(",", "");

				double key = Double.valueOf(val) + ((double) store.getId())
						/ 10000;
				//System.out.println(key + " - " + store.getRestaurantId());
				sortedStoreMap.put(new Double(key),
						new Integer(store.getRestaurantId()));
			}

			Set<Entry<Double, Integer>> set = sortedStoreMap.entrySet();
			Iterator<Entry<Double, Integer>> i = set.iterator();
			while (i.hasNext()) {
			  Entry<Double, Integer> me = i.next();
				if (!ress.contains(me.getValue())) {
					ress.add((Integer) (me.getValue()));
				}
			}

			System.out.println(ress);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In getAllRestaurant DAOException", e);
		}
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//System.out.println("getAllRestaurant 000");
			TypedQuery<RestaurantMaster> qry = em
					.createQuery("SELECT r FROM RestaurantMaster r where r.id IN (:ids) and r.status='Y' AND r.activeFlag='YES'",
					    RestaurantMaster.class);
			qry.setParameter("ids", ress);
			restaurantList = qry.getResultList();
			System.out.println("numb of rest: " + restaurantList.size());
			em.getTransaction().commit();
			// sort the restaurant list in the ress order

			Iterator<Integer> orderIterator = ress.iterator();
			while (orderIterator.hasNext()) {
				Integer order = (Integer) orderIterator.next();
				Iterator<RestaurantMaster> resturantIterator = restaurantList.iterator();
				while (resturantIterator.hasNext()) {
					RestaurantMaster restaurant = resturantIterator.next();
					if (order.intValue() == restaurant.getId()) {
						// add the restaurant to the sorted list
						sortedRestaurantList.add(restaurant);
						break;

					}

				}

			}
			//System.out.println("getAllRestaurant 333");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In getAllRestaurant DAOException", e);
		} finally {
			if (em != null)
				em.close();
		}
		return sortedRestaurantList;
	}

	@Override
	public List<StoreFeaturesChild> getStoreMenuOptionsById(Integer storeId)
			throws DAOException {
		// TODO Auto-generated method stub
		List<StoreFeaturesChild> storeFeaturesChilds = null;
		EntityManager em = null;

		/*
		 * StoreMaster storeMaster1= new StoreMaster();
		 * storeMaster1.setId(storeid);
		 */

		try {
			/*
			 * int storeid = Integer.parseInt(storeId); StoreMaster storeMaster
			 * = new StoreMaster(); storeMaster.setId(storeid);
			 */
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getStoreMenuOptionsById 11111");
			em.getTransaction().begin();
			//System.out.println("getStoreMenuOptionsById 22222");
			TypedQuery<StoreFeaturesChild> qry = em
					.createQuery("SELECT s FROM StoreFeaturesChild s WHERE s.storeId=:storeid order by s.orders",
					    StoreFeaturesChild.class);
			//System.out.println("getStoreMenuOptionsById 33333");
			qry.setParameter("storeid", storeId);
			//System.out.println("getStoreMenuOptionsById 44444");
			storeFeaturesChilds = qry.getResultList();
			//System.out.println("getStoreMenuOptionsById 55555");
			System.out.println("numb of stores: " + storeFeaturesChilds.size());
			//System.out.println("getStoreMenuOptionsById 66666");

			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<StoreFeaturesChild>>() {
			}.getType();
			String json = gson.toJson(storeFeaturesChilds, type);
			//System.out.println("===>" + json);

			em.getTransaction().commit();
			//System.out.println("getStoreMenuOptionsById 77777");
			// em.close();
			System.out.println("getStoreMenuOptionsById 88888");
		} catch (Exception e) {
			System.out.println("getStoreMenuOptionsById Exception");

			e.printStackTrace();

			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return storeFeaturesChilds;
	}

	@Override
	public void addNewRestaurant(RestaurantMaster restaurant)
			throws DAOException {
		// TODO Auto-generated method stub
		RestaurantMaster restaurantMaster = new RestaurantMaster();
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//System.out.print("addNewRestaurant 111");
			restaurantMaster.setName(restaurant.getName());
			restaurantMaster.setAddress(restaurant.getAddress());
			restaurantMaster.setLocality(restaurant.getLocality());
			restaurantMaster.setCity(restaurant.getCity());
			restaurantMaster.setState(restaurant.getState());
			restaurantMaster.setCountry(restaurant.getCountry());
			restaurantMaster.setPhone(restaurant.getPhone());
			restaurantMaster.setMobile(restaurant.getMobile());
			restaurantMaster.setCategory(restaurant.getCategory());
			restaurantMaster.setUrl(restaurant.getUrl());
			restaurantMaster.setIp(restaurant.getIp());
			restaurantMaster
					.setOperatingSystem(restaurant.getOperatingSystem());
			restaurantMaster.setRam(restaurant.getRam());
			restaurantMaster.setStatus("Y");
			restaurantMaster.setActiveFlag("YES");
			//System.out.print("addNewRestaurant 222");
			em.persist(restaurantMaster);
			//System.out.print("addNewRestaurant 333");
			em.getTransaction().commit();
			System.out.print("Restaurant details saved successfully...");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In addNewRestaurant DAOException", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void addNewStore(StoreMaster storeMaster) throws DAOException {
		// TODO Auto-generated method stub
		StoreMaster store = new StoreMaster();
		EntityManager em = null;
		List<User> userList = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("addNewStore 111");

			//System.out.println("addNewStore 222");

			//System.out.println("addNewStore 333");
			em.getTransaction().begin();
			//System.out.println("addNewStore 444");

			//System.out.println("addNewStore 555");
			System.out.println("Restaurant Id : "
					+ storeMaster.getRestaurant().getId());
			store.setRestaurant(storeMaster.getRestaurant());
			//System.out.println("addNewStore 666");
			store.setAddress(storeMaster.getAddress());
			store.setCategory(storeMaster.getCategory());
			store.setChainName(storeMaster.getChainName());
			store.setCity(storeMaster.getCity());
			store.setCountry(storeMaster.getCountry());
			store.setCurrency(storeMaster.getCurrency());
			store.setDetailsSearch(storeMaster.getDetailsSearch());
			store.setEmailId(storeMaster.getEmailId());
			store.setIp(storeMaster.getIp());
			store.setLatitude(storeMaster.getLatitude());
			store.setLongitude(storeMaster.getLongitude());
			store.setMobileNo(storeMaster.getMobileNo());
			store.setNameSearch(storeMaster.getNameSearch());
			store.setOperatingSystem(storeMaster.getOperatingSystem());
			store.setPhoneNo(storeMaster.getPhoneNo());
			store.setRam(storeMaster.getRam());
			store.setState(storeMaster.getState());
			store.setStatus(storeMaster.getStatus());
			store.setStoreName(storeMaster.getStoreName());
			store.setUrl(storeMaster.getUrl());
			store.setLicense("Y");
			store.setActiveFlag("YES");
			store.setFreeDelivery("y");
			store.setRestaurantId(storeMaster.getRestaurant().getId());

			store.setQuality(storeMaster.getQuality());
			store.setOpenTime(storeMaster.getOpenTime());
			store.setCloseTime(storeMaster.getCloseTime());
			store.setDeliveryTime(storeMaster.getDeliveryTime());
			store.setMinOrderAmt(storeMaster.getMinOrderAmt());
			store.setFreeDelivery(storeMaster.getFreeDelivery());
			store.setPromotionDesc(storeMaster.getPromotionDesc());
			store.setPromotionValue(storeMaster.getPromotionValue());
			store.setPromotionFlag(storeMaster.getPromotionFlag());
			store.setKitchenPrint(storeMaster.getKitchenPrint());

			store.setTableFlag(storeMaster.getTableFlag());
			store.setActiveFlag(storeMaster.getActiveFlag());
			store.setOpenTimeWeekend(storeMaster.getOpenTimeWeekend());
			store.setCloseTimeWeekend(storeMaster.getCloseTimeWeekend());
			store.setParcelFlag(storeMaster.getParcelFlag());
			store.setMpayment(storeMaster.getMpayment());
			store.setKitchenPrintPreview(storeMaster.getKitchenPrintPreview());
			store.setPosmanualPrint(storeMaster.getPosmanualPrint());
			store.setBillPrint(storeMaster.getPosmanualPrint());
			store.setCashPayment(storeMaster.getCashPayment());
			store.setCreditPayment(storeMaster.getCreditPayment());
			store.setStockStatus(storeMaster.getStockStatus());
			store.setFromDes(storeMaster.getFromDes());
			store.setToDes(storeMaster.getToDes());
			store.setOpenTimeHoliday(storeMaster.getOpenTimeHoliday());
			store.setCloseTimeHoliday(storeMaster.getCloseTimeHoliday());
			store.setKotResTitleFont(12);
			store.setKotTextFont(9);
			store.setKotDateTimeFont(12);
			store.setKotTableFont(12);
			store.setKotNoOfPersonFont(9);
			store.setKotItemTitleFont(10);
			store.setKotItemFont(10);
			store.setHomeDeliveryFlag("Y");
			store.setMobBillPrint("N");
			store.setPoPrintServer("N");
			store.setBillPrintBt("N");
			store.setKitchenPrintBt("N");
			store.setParcelServiceTax("Y");
			store.setParcelVat("Y");
			store.setRoundOffTotalAmtStatus("Y");
			store.setKotCount(1);
			store.setDiscountPercentage("Y");
			store.setVatTaxText("VAT");
			store.setServiceTaxText("S.TAX");
			store.setKotPrintType("category");
			store.setBillTextFont(12);
			store.setBillHeaderFont(12);
			store.setPrintPaidBill("Y");
			store.setParcelAddress("Y");
			store.setMultiOrderTable("N");
			store.setCreditSale("N");
			store.setCustomerDisplay("-");
			store.setParcelText("PARCEL");
			store.setKotDefaultPrinter("Y");
			store.setCookingUnit("cooking_us");
			store.setSmartIm("N");
			store.setDoubleRoundOff("N");
			store.setInvoiceText("Cash/Bill");
			store.setRateInBill("N");
			store.setBillFontType("SANSSERIF");

			LOGGER.debug("Store to save: {}", storeMaster);

			em.persist(store);
			//System.out.println("addNewStore 777");
			em.getTransaction().commit();

			StoreMaster storeCreated = em
					.find(StoreMaster.class, store.getId());

			// create home features
			if (storeCreated != null) {

				Map<String, StoreFeatures> storeFeatrsMap = new HashMap<String, StoreFeatures>();
				List<StoreFeaturesChild> featuresChildsLst = new ArrayList<StoreFeaturesChild>();

				StoreFeatures storeFeatures = new StoreFeatures();
				storeFeatures.setId(1);
				storeFeatrsMap.put(storeFeatures.getId() + "", storeFeatures);

				StoreFeatures storeFeatures1 = new StoreFeatures();
				storeFeatures1.setId(4);
				storeFeatrsMap.put(storeFeatures1.getId() + "", storeFeatures1);

				StoreFeatures storeFeatures2 = new StoreFeatures();
				storeFeatures2.setId(8);
				storeFeatrsMap.put(storeFeatures2.getId() + "", storeFeatures2);

				StoreFeatures storeFeatures3 = new StoreFeatures();
				storeFeatures3.setId(6);
				storeFeatrsMap.put(storeFeatures3.getId() + "", storeFeatures3);

				StoreFeatures storeFeatures4 = new StoreFeatures();
				storeFeatures4.setId(9);
				storeFeatrsMap.put(storeFeatures4.getId() + "", storeFeatures4);

				StoreFeatures storeFeatures5 = new StoreFeatures();
				storeFeatures5.setId(10);
				storeFeatrsMap.put(storeFeatures5.getId() + "", storeFeatures5);

				StoreFeatures storeFeatures6 = new StoreFeatures();
				storeFeatures6.setId(5);
				storeFeatrsMap.put(storeFeatures6.getId() + "", storeFeatures6);

				Set<String> keys = storeFeatrsMap.keySet();
				Iterator<String> keyItr = keys.iterator();
				while (keyItr.hasNext()) {
					String key = (String) keyItr.next();
					StoreFeatures features = storeFeatrsMap.get(key);
					StoreFeaturesChild featuresChild = new StoreFeaturesChild();
					featuresChild.setRestaurantId(storeCreated.getRestaurant()
							.getId());
					featuresChild.setStoreFeatures(features);
					featuresChild.setStoreId(storeCreated.getId());
					featuresChildsLst.add(featuresChild);

				}

				Iterator<StoreFeaturesChild> itrChild = featuresChildsLst
						.iterator();
				while (itrChild.hasNext()) {
					StoreFeaturesChild storeFeaturesChild = (StoreFeaturesChild) itrChild
							.next();
					if (storeFeaturesChild.getStoreFeatures().getId() == 1) {
						storeFeaturesChild.setOrders(1);

					} else if (storeFeaturesChild.getStoreFeatures().getId() == 4) {
						storeFeaturesChild.setOrders(2);

					} else if (storeFeaturesChild.getStoreFeatures().getId() == 8) {
						storeFeaturesChild.setOrders(4);

					} else if (storeFeaturesChild.getStoreFeatures().getId() == 6) {
						storeFeaturesChild.setOrders(6);

					} else if (storeFeaturesChild.getStoreFeatures().getId() == 9) {
						storeFeaturesChild.setOrders(7);

					} else if (storeFeaturesChild.getStoreFeatures().getId() == 10) {
						storeFeaturesChild.setOrders(8);

					} else if (storeFeaturesChild.getStoreFeatures().getId() == 5) {
						storeFeaturesChild.setOrders(5);

					}
				}

				em.getTransaction().begin();
				Iterator<StoreFeaturesChild> featureIterator = featuresChildsLst
						.iterator();
				while (featureIterator.hasNext()) {
					StoreFeaturesChild storeFeaturesChild = (StoreFeaturesChild) featureIterator
							.next();
					em.persist(storeFeaturesChild);

				}

				// add a new Menu(root) into category/sub-category table
				MenuCategory root = new MenuCategory();
				root.setMenuCategoryName("Menu");
				root.setStoreId(storeCreated.getId());
				root.setDeleteFlag("N");
				root.setType("r");
				em.persist(root);

				// add user access information for the selected restaurant for
				// which the store is added
				int restId = storeCreated.getRestaurant().getId();
				TypedQuery<User> qry = em
						.createQuery("SELECT u FROM User u where u.restMaster.id=:restId",
						    User.class);
				qry.setParameter("restId", restId);
				userList = qry.getResultList();
				Iterator<User> userItr = userList.iterator();
				while (userItr.hasNext()) {
					User user = userItr.next();
					UserTransaction transaction = new UserTransaction();
					transaction.setRestaurantId(restId);
					transaction.setStatus("Y");
					transaction.setStore(storeCreated);
					transaction.setUserId(user.getId());
					
					em.persist(transaction);
				}

				EntityTransaction tx = em.getTransaction();
				tx.commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In addNewStore DAOException", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void updateRestaurant(RestaurantMaster restaurant)
			throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//System.out.print("updateRestaurant 111");
			RestaurantMaster restaurantMaster = em.find(RestaurantMaster.class,
					restaurant.getId());
			restaurantMaster.setName(restaurant.getName());
			restaurantMaster.setAddress(restaurant.getAddress());
			restaurantMaster.setLocality(restaurant.getLocality());
			restaurantMaster.setCity(restaurant.getCity());
			restaurantMaster.setState(restaurant.getState());
			restaurantMaster.setCountry(restaurant.getCountry());
			restaurantMaster.setPhone(restaurant.getPhone());
			restaurantMaster.setMobile(restaurant.getMobile());
			restaurantMaster.setCategory(restaurant.getCategory());
			restaurantMaster.setUrl(restaurant.getUrl());
			restaurantMaster.setIp(restaurant.getIp());
			restaurantMaster
					.setOperatingSystem(restaurant.getOperatingSystem());
			restaurantMaster.setRam(restaurant.getRam());
			//System.out.print("updateRestaurant 222");
			//System.out.print("updateRestaurant 333");
			em.getTransaction().commit();
			System.out.print("Restaurant details updated successfully...");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(" In updateRestaurant DAOException", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void deleteRestaurant(RestaurantMaster restaurant)
			throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//System.out.print("deleteRestaurant 111");
			RestaurantMaster restaurantMaster = em.find(RestaurantMaster.class,
					restaurant.getId());
			restaurantMaster.setStatus("N");
			System.out.print("Restaurant id ==> " + restaurant.getId());

			List<StoreMaster> storeList = getStoreByRestaurantId(restaurant);
			System.out.print("Store Size : " + storeList.size());
			Iterator<StoreMaster> iterator = storeList.iterator();
			while (iterator.hasNext()) {
				StoreMaster storeMaster = (StoreMaster) iterator.next();
				StoreMaster store = em.find(StoreMaster.class,
						storeMaster.getId());
				store.setStatus("N");
			}
			em.getTransaction().commit();
			System.out.print("Restaurant details deleted successfully...");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(" In deleteRestaurant DAOException", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void setRestaurantActive(RestaurantMaster restaurant)
			throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("setRestaurantActive 111");
			//System.out.println("setRestaurantActive 222");
			em.getTransaction().begin();
			//System.out.println("setRestaurantActive 333");
			RestaurantMaster restaurant1 = em.find(RestaurantMaster.class,
					restaurant.getId());
			//System.out.println("setRestaurantActive 444");
			restaurant1.setActiveFlag("YES");
			//System.out.println("setRestaurantActive 555");

			List<StoreMaster> storeList = getStoreByRestaurantId(restaurant);
			Iterator<StoreMaster> iterator = storeList.iterator();
			while (iterator.hasNext()) {
				StoreMaster storeMaster = (StoreMaster) iterator.next();
				StoreMaster store = em.find(StoreMaster.class,
						storeMaster.getId());
				store.setActiveFlag("YES");
			}

			em.getTransaction().commit();
			//System.out.println("setRestaurantActive 666");
			// em.close();
			//System.out.println("setRestaurantActive 777");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In setRestaurantActive DAOException", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void setRestaurantInActive(RestaurantMaster restaurant)
			throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("setRestaurantInActive 111");
			//System.out.println("setRestaurantInActive 222");
			em.getTransaction().begin();
			//System.out.println("setRestaurantInActive 333");
			RestaurantMaster restaurant1 = em.find(RestaurantMaster.class,
					restaurant.getId());
			//System.out.println("setRestaurantInActive 444");
			restaurant1.setActiveFlag("NO");
			//System.out.println("setRestaurantInActive 555");

			List<StoreMaster> storeList = getStoreByRestaurantId(restaurant);
			Iterator<StoreMaster> iterator = storeList.iterator();
			while (iterator.hasNext()) {
				StoreMaster storeMaster = (StoreMaster) iterator.next();
				StoreMaster store = em.find(StoreMaster.class,
						storeMaster.getId());
				store.setActiveFlag("NO");
			}

			em.getTransaction().commit();
			//System.out.println("setRestaurantInActive 666");
			// em.close();
			//System.out.println("setRestaurantInActive 777");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In setRestaurantInActive DAOException", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void updateStore(StoreMaster storeMaster) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			
			em.getTransaction().begin();
			//System.out.println("updateStore 444");
			StoreMaster store = em.find(StoreMaster.class, storeMaster.getId());
			
			store.setAddress(storeMaster.getAddress());
			store.setCategory(storeMaster.getCategory());
			store.setChainName(storeMaster.getChainName());
			store.setCity(storeMaster.getCity());
			store.setCountry(storeMaster.getCountry());
			store.setCurrency(storeMaster.getCurrency());
			store.setDetailsSearch(storeMaster.getDetailsSearch());
			store.setEmailId(storeMaster.getEmailId());
			store.setIp(storeMaster.getIp());
			store.setLatitude(storeMaster.getLatitude());
			store.setLongitude(storeMaster.getLongitude());
			store.setMobileNo(storeMaster.getMobileNo());
			store.setNameSearch(storeMaster.getNameSearch());
			store.setOperatingSystem(storeMaster.getOperatingSystem());
			store.setPhoneNo(storeMaster.getPhoneNo());
			store.setRam(storeMaster.getRam());
			store.setState(storeMaster.getState());

			store.setQuality(storeMaster.getQuality());
			store.setOpenTime(storeMaster.getOpenTime());
			store.setCloseTime(storeMaster.getCloseTime());
			store.setDeliveryTime(storeMaster.getDeliveryTime());
			store.setMinOrderAmt(storeMaster.getMinOrderAmt());
			store.setFreeDelivery(storeMaster.getFreeDelivery());
			store.setPromotionDesc(storeMaster.getPromotionDesc());
			store.setPromotionValue(storeMaster.getPromotionValue());
			store.setPromotionFlag(storeMaster.getPromotionFlag());

			store.setOpenTimeWeekend(storeMaster.getOpenTimeWeekend());
			store.setCloseTimeWeekend(storeMaster.getCloseTimeWeekend());
			store.setOpenTimeHoliday(storeMaster.getOpenTimeHoliday());
			store.setCloseTimeHoliday(storeMaster.getCloseTimeHoliday());
			store.setTableFlag(storeMaster.getTableFlag());

			store.setActiveFlag(storeMaster.getActiveFlag());
			store.setParcelFlag(storeMaster.getParcelFlag());
			store.setMpayment(storeMaster.getMpayment());
			store.setKitchenPrintPreview(storeMaster.getKitchenPrintPreview());
			store.setPosmanualPrint(storeMaster.getPosmanualPrint());
			store.setBillPrint(storeMaster.getBillPrint());
			store.setCashPayment(storeMaster.getCashPayment());
			store.setCreditPayment(storeMaster.getCreditPayment());
			store.setStockStatus(storeMaster.getStockStatus());
			store.setFromDes(storeMaster.getFromDes());
			store.setToDes(storeMaster.getToDes());
			store.setOpenTimeHoliday(storeMaster.getOpenTimeHoliday());
			store.setCloseTimeHoliday(storeMaster.getCloseTimeHoliday());
			store.setKotResTitleFont(storeMaster.getKotResTitleFont());
			store.setKotTextFont(storeMaster.getKotTextFont());
			store.setKotDateTimeFont(storeMaster.getKotDateTimeFont());
			store.setKotTableFont(storeMaster.getKotTableFont());
			store.setKotNoOfPersonFont(storeMaster.getKotNoOfPersonFont());
			store.setKotItemTitleFont(storeMaster.getKotItemTitleFont());
			store.setKotItemFont(storeMaster.getKotItemFont());

			// store.setStatus("Y");
			store.setStoreName(storeMaster.getStoreName());
			store.setUrl(storeMaster.getUrl());
			store.setKitchenPrint(storeMaster.getKitchenPrint());
			//System.out.println("updateStore 777");
			em.getTransaction().commit();
			//System.out.println("updateStore 888");
			// em.close();
			

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception", e);
			throw new DAOException("In updateStore DAOException", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void deleteStore(StoreMaster storeMaster) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			
			em.getTransaction().begin();
			//System.out.println("deleteStore 333");
			StoreMaster store = em.find(StoreMaster.class, storeMaster.getId());
			//System.out.println("deleteStore 444");
			store.setStatus("N");
			//System.out.println("deleteStore 555");
			em.getTransaction().commit();
			//System.out.println("deleteStore 666");
			// em.close();
			//System.out.println("deleteStore 777");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In deleteStore DAOException", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void setStoreAsActive(StoreMaster storeMaster) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			
			em.getTransaction().begin();
			//System.out.println("setStoreAsActive 333");
			StoreMaster store = em.find(StoreMaster.class, storeMaster.getId());
			//System.out.println("setStoreAsActive 444");
			store.setActiveFlag("YES");
			//System.out.println("setStoreAsActive 555");
			em.getTransaction().commit();
			//System.out.println("setStoreAsActive 666");
			// em.close();
			//System.out.println("setStoreAsActive 777");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In setStoreAsActive DAOException", e);
		} finally {
			em.close();
		}

	}

	@Override
	public void setStoreAsInActive(StoreMaster storeMaster) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			
			em.getTransaction().begin();
			//System.out.println("setStoreAsInActive 333");
			StoreMaster store = em.find(StoreMaster.class, storeMaster.getId());
			//System.out.println("setStoreAsInActive 444");
			store.setActiveFlag("NO");
			//System.out.println("setStoreAsInActive 555");
			em.getTransaction().commit();
			//System.out.println("setStoreAsInActive 666");
			// em.close();
			//System.out.println("setStoreAsInActive 777");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In setStoreAsInActive DAOException", e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<StoreMaster> getStoreByRestaurantId(RestaurantMaster restaurant)
			throws DAOException {
		// TODO Auto-generated method stub
		List<StoreMaster> storeList = null;
		EntityManager em = null;
		try {
			
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
			storeList = (List<StoreMaster>) qry.getResultList();
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
	public List<StoreMaster> getAllStoresByRestaurantId(String id)
			throws DAOException {
		// TODO Auto-generated method stub
		List<StoreMaster> storeList = null;
		EntityManager em = null;
		try {
			int restId = Integer.parseInt(id);
			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<StoreMaster> qry = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.restaurant.id=:id",
					    StoreMaster.class);

			qry.setParameter("id", restId);
			storeList = qry.getResultList();

			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<StoreMaster>>() {
			}.getType();
			String json = gson.toJson(storeList, type);
			//System.out.println("===>" + json);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("problem getting all stores by restaurant",
					e);
		} finally {
			em.close();
		}
		return storeList;
	}

	@Override
	public StoreMaster getStoreByStoreId(int storeId) throws DAOException {

		StoreMaster storeMaster = null;
		EntityManager em = null;
		System.out.println("store id :" + storeId);
		try {
			// int storeid = Integer.parseInt(storeId);
			
			em = entityManagerFactory.createEntityManager();
			System.out.println("enter StoreAddressDAOImpl: getStoreByStoreId  "
					+ storeId);
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.id=:storeid");
			//System.out.println("getByStoreId 222");
			qry.setParameter("storeid", storeId);
			//System.out.println("getByStoreId 333");
			storeMaster = (StoreMaster) qry.getSingleResult();
			//System.out.println("getByStoreId 444");

			// em.getTransaction().commit();
			//System.out.println("getByStoreId 666");
			// em.close();
		} catch (Exception e) {
			//System.out.println("getByStoreId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		System.out.println("exit StoreAddressDAOImpl: getStoreByStoreId:::"+storeMaster);
		return storeMaster;
	}

	@Override
	public MobileFontSetting getMobileFontByStore(Integer storeId)
			throws DAOException {
		System.out.println("StoreAddressDAOImpl: getMobileFontByStore called");
		MobileFontSetting fontSetting = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT m FROM MobileFontSetting m WHERE m.deleteFlag='N' AND m.storeMaster.id=:param");
			qry.setParameter("param", storeId);
			fontSetting = (MobileFontSetting) qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		System.out.println("StoreAddressDAOImpl: getMobileFontByStore exit");
		return fontSetting;
	}
	
	@Override
	public StoreSMSConfiguration getStoreSMSConfiguration(int storeId) throws DAOException {

		StoreSMSConfiguration storeSMS = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreSMSConfiguration> qry = em.createQuery("SELECT s FROM StoreSMSConfiguration s WHERE s.storeId=:storeid and s.deleteFlag='N'",StoreSMSConfiguration.class);
			qry.setParameter("storeid", storeId);
			storeSMS = qry.getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return storeSMS;
	}
	
	@Override
	public DashSalesSummary getDashSaleSummary(Integer storeId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		DashSalesSummary salesSummary=null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_DASH_SALES_SUMMARY);
			callableStatement.setInt(1, storeId);
			callableStatement.setString(2, fromDate);
			callableStatement.setString(3, toDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<DashSalesSummary> resultSetMapper1 = new ReflectionResultSetMapper<DashSalesSummary>(
					DashSalesSummary.class);

			while (rs.next()) {
				 salesSummary=new DashSalesSummary();
				 salesSummary = resultSetMapper1.mapRow(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return salesSummary;
	}
	
	@Override
	public DashPaymentSummary getDashPaymentSummary(Integer storeId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		DashPaymentSummary paymentSummary=null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_DASH_PAYMENT_SUMMARY);
			callableStatement.setInt(1, storeId);
			callableStatement.setString(2, fromDate);
			callableStatement.setString(3, toDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<DashPaymentSummary> resultSetMapper1 = new ReflectionResultSetMapper<DashPaymentSummary>(
					DashPaymentSummary.class);

			while (rs.next()) {
				paymentSummary=new DashPaymentSummary();
				paymentSummary = resultSetMapper1.mapRow(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return paymentSummary;
	}
	
	@Override
	public List<DashSalesSummaryOrderType> getDashSaleSummaryOrderType(Integer storeId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<DashSalesSummaryOrderType> salesSummaryOrderTypeList=new ArrayList<>();
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_DASH_SALES_SUMMARY_ORDERTYPE);
			callableStatement.setInt(1, storeId);
			callableStatement.setString(2, fromDate);
			callableStatement.setString(3, toDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<DashSalesSummaryOrderType> resultSetMapper1 = new ReflectionResultSetMapper<DashSalesSummaryOrderType>(
					DashSalesSummaryOrderType.class);

			while (rs.next()) {
				DashSalesSummaryOrderType salesSummaryOrderType=new DashSalesSummaryOrderType();
				salesSummaryOrderType = resultSetMapper1.mapRow(rs);
				salesSummaryOrderTypeList.add(salesSummaryOrderType);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return salesSummaryOrderTypeList;
	}
	
	@Override
	public List<DashSalesSummaryPaymentType> getDashSaleSummaryPaymentType(Integer storeId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<DashSalesSummaryPaymentType> salesSummaryPaymentTypeList=new ArrayList<>();
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_DASH_SALES_SUMMARY_PAYMENTTYPE);
			callableStatement.setInt(1, storeId);
			callableStatement.setString(2, fromDate);
			callableStatement.setString(3, toDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<DashSalesSummaryPaymentType> resultSetMapper1 = new ReflectionResultSetMapper<DashSalesSummaryPaymentType>(
					DashSalesSummaryPaymentType.class);

			while (rs.next()) {
				DashSalesSummaryPaymentType salesSummaryPaymentType=new DashSalesSummaryPaymentType();
				salesSummaryPaymentType = resultSetMapper1.mapRow(rs);
				salesSummaryPaymentTypeList.add(salesSummaryPaymentType);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return salesSummaryPaymentTypeList;
	}
	
	@Override
	public List<DashTopCustomer> getDashTopCustomer(Integer storeId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<DashTopCustomer> topCustomerList=new ArrayList<>();
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_DASH_TOP_CUST);
			callableStatement.setInt(1, storeId);
			callableStatement.setString(2, fromDate);
			callableStatement.setString(3, toDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<DashTopCustomer> resultSetMapper1 = new ReflectionResultSetMapper<DashTopCustomer>(
					DashTopCustomer.class);

			while (rs.next()) {
				DashTopCustomer topCustomer=new DashTopCustomer();
				topCustomer = resultSetMapper1.mapRow(rs);
				topCustomerList.add(topCustomer);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return topCustomerList;
	}
	
	@Override
	public List<DashTopItem> getDashTopItem(Integer storeId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<DashTopItem> topItemList=new ArrayList<>();
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_DASH_TOP_ITEM);
			callableStatement.setInt(1, storeId);
			callableStatement.setString(2, fromDate);
			callableStatement.setString(3, toDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<DashTopItem> resultSetMapper1 = new ReflectionResultSetMapper<DashTopItem>(
					DashTopItem.class);

			while (rs.next()) {
				DashTopItem topItem=new DashTopItem();
				topItem = resultSetMapper1.mapRow(rs);
				topItemList.add(topItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return topItemList;
	}
	
	@Override
	public DashBoardData getDashBoardData(Integer storeId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		DashBoardData dbData=null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_DASH_BOARD_DATA);
			callableStatement.setInt(1, storeId);
			callableStatement.setString(2, fromDate);
			callableStatement.setString(3, toDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			while (rs.next()) {
				DashBoardData dData=new DashBoardData();
				dData.setPaidAmt(rs.getDouble(1));
				dData.setUnpaidAmt(rs.getDouble(2));
				dData.setCreditAmt(rs.getDouble(3));
				dData.setRefundAmt(rs.getDouble(4));
				dData.setCancelAmt(rs.getDouble(5));
				dData.setCashAmt(rs.getDouble(6));
				dData.setCardAmt(rs.getDouble(7));
				dData.setOtherAmt(rs.getDouble(8));
				dbData=dData;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return dbData;
	}
}

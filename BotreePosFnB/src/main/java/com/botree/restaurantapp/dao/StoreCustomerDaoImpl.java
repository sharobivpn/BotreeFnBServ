package com.botree.restaurantapp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.OrderType;
import com.botree.restaurantapp.model.ServiceChargesFortAllItems;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("storeCustomerDAO")
public class StoreCustomerDaoImpl implements StoreCustomerDAO {

	private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
	
	private final static Logger logger = LogManager.getLogger(StoreCustomerDaoImpl.class);

	@Override
	public void createStoreCustomer(StoreCustomer storeCustomer)
			throws DAOException {

		StoreCustomer storeCustomerObj = new StoreCustomer();
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			storeCustomerObj.setName(storeCustomer.getName());
			storeCustomerObj.setContactNo(storeCustomer.getContactNo());
			storeCustomerObj.setAddress(storeCustomer.getAddress());
			storeCustomerObj.setEmailId(storeCustomer.getEmailId());
			storeCustomerObj.setStoreId(storeCustomer.getStoreId());
			storeCustomerObj.setCreditCustomer(storeCustomer
					.getCreditCustomer());
			storeCustomerObj.setType(storeCustomer.getType());
			storeCustomerObj.setCreditLimit(storeCustomer.getCreditLimit());
			storeCustomerObj.setDeleteFlag(storeCustomer.getDeleteFlag());
			storeCustomerObj.setCreatedBy(storeCustomer.getCreatedBy());
			storeCustomerObj.setCreatedDate(storeCustomer.getCreatedDate());
			storeCustomerObj.setUpdatedBy(storeCustomer.getUpdatedBy());
			storeCustomerObj.setUpdatedDate(storeCustomer.getUpdatedDate());
			storeCustomerObj.setCust_vat_reg_no(storeCustomer
					.getCust_vat_reg_no());
			storeCustomerObj.setLocation(storeCustomer.getLocation());
			storeCustomerObj.setHouse_no(storeCustomer.getHouse_no());
			storeCustomerObj.setStreet(storeCustomer.getStreet());
			storeCustomerObj.setCar_no(storeCustomer.getCar_no());
			storeCustomerObj.setAnniversary(storeCustomer.getAnniversary());
			storeCustomerObj.setDob(storeCustomer.getDob());
			storeCustomerObj.setDeleteFlag("N");
			em.persist(storeCustomerObj);
			em.getTransaction().commit();
			logger.info("storeCustomerObj saved Successfully"
					+ storeCustomerObj.getId() + " "
					+ storeCustomerObj.getName());
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof ConstraintViolationException) {
				System.out.println("Integrity constraint");
				throw new DAOException("Integrity Constraint violation", e);
			}
			e.printStackTrace();
			throw new DAOException("Check StoreCustomer data", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void updateStoreCustomer(StoreCustomer storeCustomer)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			StoreCustomer storeCustomerObj = em.find(StoreCustomer.class,
					storeCustomer.getId());
			storeCustomerObj.setName(storeCustomer.getName());
			storeCustomerObj.setContactNo(storeCustomer.getContactNo());
			storeCustomerObj.setAddress(storeCustomer.getAddress());
			storeCustomerObj.setEmailId(storeCustomer.getEmailId());
			storeCustomerObj.setStoreId(storeCustomer.getStoreId());
			storeCustomerObj.setCreditCustomer(storeCustomer
					.getCreditCustomer());
			storeCustomerObj.setType(storeCustomer.getType());
			storeCustomerObj.setCreditLimit(storeCustomer.getCreditLimit());
			storeCustomerObj.setDeleteFlag(storeCustomer.getDeleteFlag());
			storeCustomerObj.setCreatedBy(storeCustomer.getCreatedBy());
			storeCustomerObj.setCreatedDate(storeCustomer.getCreatedDate());
			storeCustomerObj.setUpdatedBy(storeCustomer.getUpdatedBy());
			storeCustomerObj.setUpdatedDate(storeCustomer.getUpdatedDate());
			storeCustomerObj.setCust_vat_reg_no(storeCustomer
					.getCust_vat_reg_no());
			storeCustomerObj.setLocation(storeCustomer.getLocation());
			storeCustomerObj.setHouse_no(storeCustomer.getHouse_no());
			storeCustomerObj.setStreet(storeCustomer.getStreet());
			storeCustomerObj.setCar_no(storeCustomer.getCar_no());
			storeCustomerObj.setAnniversary(storeCustomer.getAnniversary());
			storeCustomerObj.setDob(storeCustomer.getDob());
			storeCustomerObj.setDeleteFlag("N");
			em.getTransaction().commit();
			logger.info("storeCustomerObj Updated Successfully"
					+ storeCustomerObj.getId() + " "
					+ storeCustomerObj.getName());
		} catch (Exception e) {
			throw new DAOException("Check data to be updated", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteStoreCustomer(StoreCustomer storeCustomer)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			StoreCustomer storeCustomerObj = em.find(StoreCustomer.class,
					storeCustomer.getId());
			em.remove(storeCustomerObj);
			em.getTransaction().commit();
			logger.info("StoreCustomer data deleted successfully");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public CustomerInfo getStoreCustomerByPhNmbr(String contact, String storeId)
			throws DAOException {

		StoreCustomer storeCustomerObj = null;
		CustomerInfo customerInfo = new CustomerInfo();
		EntityManager em = null;
		String deliveryPersonName = null;
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			/*
			 * Query qry = em .createQuery(
			 * "SELECT u FROM StoreCustomer u WHERE u.contactNo like :contact and u.storeId=:storeId "
			 * ); qry.setParameter("contact", contact + "%");
			 * qry.setParameter("storeId", storeId1);
			 */
			Query qry = em
					.createQuery("SELECT o FROM StoreCustomer o WHERE o.contactNo=:customerContact and o.storeId=:storeId and o.creditCustomer = 'N' and o.deleteFlag = 'N'");
			qry.setParameter("customerContact", contact);
			qry.setParameter("storeId", storeId1);
			storeCustomerObj = (StoreCustomer) qry.setMaxResults(1)
					.getSingleResult();
			int storeCustomerId = storeCustomerObj.getId();
			try {
				deliveryPersonName = fetchDeliveryPersonName(storeCustomerId);
			} catch (Exception e) {
				deliveryPersonName = "";
			}
			String anniversary_date = "";
			String dob = "";
			// get customer info
			String custName = storeCustomerObj.getName();
			String custContact = storeCustomerObj.getContactNo();
			String delAddr = storeCustomerObj.getAddress();
			// Date delTime = getPhNoStoreCustomer.getDeliveryTime();
			String custVatRegNo = storeCustomerObj.getCust_vat_reg_no();
			String loc = storeCustomerObj.getLocation();
			String strt = storeCustomerObj.getStreet();
			String hNo = storeCustomerObj.getHouse_no();
			String carNo = storeCustomerObj.getCar_no();
			try {
				anniversary_date = storeCustomerObj.getAnniversary().toString();
			} catch (NullPointerException e) {
				anniversary_date = "";
			}
			try {
				dob = storeCustomerObj.getDob().toString();
			} catch (NullPointerException e1) {
				dob = "";
			}
			int id = storeCustomerObj.getId();
			customerInfo.setCustId(String.valueOf(id));
			customerInfo.setCustContact(custContact);
			customerInfo.setCustName(custName);
			customerInfo.setDelivAddr(delAddr);
			customerInfo.setDelivPersonName(deliveryPersonName);
			// customerInfo.setDelivTime(delTime);
			customerInfo.setCreditCust(storeCustomerObj);
			customerInfo.setCustVatRegNo(custVatRegNo);
			customerInfo.setLocation(loc);
			customerInfo.setStreet(strt);
			customerInfo.setHouseNo(hNo);
			customerInfo.setCarNo(carNo);
			customerInfo.setAnniversary_date(anniversary_date);
			customerInfo.setDob(dob);
			em.getTransaction().commit();
			logger.info("successfully : ");
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return customerInfo;
	}
	
	
	public StoreCustomer getCreditCustomerByPhNmbr(String contact, String storeId)
			throws DAOException {

		StoreCustomer storeCustomerObj = null;
		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
		
			Query qry = em
					.createQuery("SELECT o FROM StoreCustomer o WHERE o.contactNo=:customerContact and o.storeId=:storeId and o.creditCustomer = 'Y' and o.deleteFlag = 'N'");
			qry.setParameter("customerContact", contact);
			qry.setParameter("storeId", storeId1);
			storeCustomerObj = (StoreCustomer) qry.setMaxResults(1)
					.getSingleResult();
			
			em.getTransaction().commit();
			logger.info("successfully : ");
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return storeCustomerObj;
	}

	/**
	 * @Desc getStoreCustomerByCustId
	 * @param custId
	 * @param storeId
	 * @return
	 * @throws DAOException
	 */
	@Override
	public CustomerInfo getStoreCustomerByCustId(int custId, String storeId)
			throws DAOException {

		StoreCustomer storeCustomerObj = null;
		CustomerInfo customerInfo = new CustomerInfo();
		EntityManager em = null;
		String deliveryPersonName = null;
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			/*
			 * Query qry = em .createQuery(
			 * "SELECT u FROM StoreCustomer u WHERE u.contactNo like :contact and u.storeId=:storeId "
			 * ); qry.setParameter("contact", contact + "%");
			 * qry.setParameter("storeId", storeId1);
			 */
			Query qry = em
					.createQuery("SELECT o FROM StoreCustomer o WHERE o.id=:id and o.storeId=:storeId and o.creditCustomer = 'N' and o.deleteFlag = 'N'");
			qry.setParameter("id", custId);
			qry.setParameter("storeId", storeId1);
			storeCustomerObj = (StoreCustomer) qry.setMaxResults(1)
					.getSingleResult();
			int storeCustomerId = storeCustomerObj.getId();
			try {
				deliveryPersonName = fetchDeliveryPersonName(storeCustomerId);
			} catch (Exception e) {
				deliveryPersonName = "";
			}
			// get customer info
			String custName = storeCustomerObj.getName();
			String custContact = storeCustomerObj.getContactNo();
			String delAddr = storeCustomerObj.getAddress();
			// Date delTime = getPhNoStoreCustomer.getDeliveryTime();
			String custVatRegNo = storeCustomerObj.getCust_vat_reg_no();
			String loc = storeCustomerObj.getLocation();
			String strt = storeCustomerObj.getStreet();
			String hNo = storeCustomerObj.getHouse_no();
			String carNo = storeCustomerObj.getCar_no();
			String anniversary_date = "";
			String dob = "";
			try {
				anniversary_date = storeCustomerObj.getAnniversary().toString();
			} catch (NullPointerException e) {
				anniversary_date = null;
			}
			try {
				dob = storeCustomerObj.getDob().toString();
			} catch (NullPointerException e) {
				dob = null;
			}

			customerInfo.setCustContact(custContact);
			customerInfo.setCustName(custName);
			customerInfo.setDelivAddr(delAddr);
			customerInfo.setDelivPersonName(deliveryPersonName);
			// customerInfo.setDelivTime(delTime);
			customerInfo.setCreditCust(storeCustomerObj);
			customerInfo.setCustVatRegNo(custVatRegNo);
			customerInfo.setLocation(loc);
			customerInfo.setStreet(strt);
			customerInfo.setHouseNo(hNo);
			customerInfo.setCarNo(carNo);
			customerInfo.setAnniversary_date(anniversary_date);
			customerInfo.setDob(dob);
			em.getTransaction().commit();
			logger.info("successfully : ");
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return customerInfo;
	}

	public String fetchDeliveryPersonName(int storeCustomerId)
			throws DAOException {
		EntityManager em = null;
		OrderMaster master = null;
		String deliveryPersonName = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeCustomerId=:storeCustomerId ");
			qry.setParameter("storeCustomerId", storeCustomerId);

			master = (OrderMaster) qry.setMaxResults(1).getSingleResult();
			deliveryPersonName = master.getDeliveryPersonName();
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);

		} finally {
			em.close();
		}
		return deliveryPersonName;
	}

	@Override
	public List<StoreCustomer> getAllStoreCustomerByPhNmbr(String contact,
			String storeId) throws DAOException {

		List<StoreCustomer> custList = new ArrayList<StoreCustomer>();
		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreCustomer> qry = em
					.createQuery("SELECT u FROM StoreCustomer u WHERE u.contactNo like :contact and u.storeId=:storeId and u.creditCustomer = 'N' and u.deleteFlag = 'N'",
					    StoreCustomer.class);
			qry.setParameter("contact", contact + "%");
			qry.setParameter("storeId", storeId1);
			custList = qry.getResultList();
			em.getTransaction().commit();
			logger.info("successfully : ");
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return custList;
	}

	@Override
	public List<StoreCustomer> getAllStoreCustomerByName(String storeId,
			String name1) throws DAOException {

		EntityManager em = null;
		List<StoreCustomer> custList = new ArrayList<StoreCustomer>();
		int storeId1 = Integer.parseInt(storeId);
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreCustomer> qry = em
					.createQuery("SELECT u FROM StoreCustomer u WHERE u.name like :name and u.storeId=:storeId and u.creditCustomer = 'N' and u.deleteFlag = 'N' and u.name != ' ' order by u.name asc",
					    StoreCustomer.class);
			qry.setParameter("name", name1 + "%");
			qry.setParameter("storeId", storeId1);
			custList = qry.getResultList();
			em.getTransaction().commit();
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return custList;

	}
	
	@Override
	public List<StoreCustomer> getAllStoreCustomerByStoreId(String storeId) throws DAOException {

		EntityManager em = null;
		List<StoreCustomer> custList = new ArrayList<StoreCustomer>();
		int storeId1 = Integer.parseInt(storeId);
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreCustomer> qry = em
					.createQuery("SELECT u FROM StoreCustomer u WHERE u.storeId=:storeId and u.deleteFlag = 'N' and u.name != ' ' ",
					    StoreCustomer.class);
			qry.setParameter("storeId", storeId1);
			custList = qry.getResultList();
			em.getTransaction().commit();
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return custList;

	}
	
	//added on 31.10.2019
	@Override
	public List<StoreCustomer> getAllRBStoreCustomerByPhNmbr(String contact,
			String storeId) throws DAOException {

		List<StoreCustomer> custList = new ArrayList<StoreCustomer>();
		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreCustomer> qry = em
					.createQuery("SELECT u FROM StoreCustomer u WHERE u.contactNo like :contact and u.storeId=:storeId and u.deleteFlag = 'N'",
					    StoreCustomer.class);
			qry.setParameter("contact", contact + "%");
			qry.setParameter("storeId", storeId1);
			custList = qry.getResultList();
			em.getTransaction().commit();
			logger.info("successfully : ");
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return custList;
	}

	@Override
	public List<StoreCustomer> getAllRBStoreCustomerByName(String storeId,
			String name1) throws DAOException {

		EntityManager em = null;
		List<StoreCustomer> custList = new ArrayList<StoreCustomer>();
		int storeId1 = Integer.parseInt(storeId);
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreCustomer> qry = em
					.createQuery("SELECT u FROM StoreCustomer u WHERE u.name like :name and u.storeId=:storeId and u.deleteFlag = 'N' and u.name != ' ' order by u.name asc",
					    StoreCustomer.class);
			qry.setParameter("name", name1 + "%");
			qry.setParameter("storeId", storeId1);
			custList = qry.getResultList();
			em.getTransaction().commit();
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return custList;

	}
	
	@Override
	public String convertToCreditCustomer(String custId, String storeId)
			throws DAOException {

		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		int custid = Integer.parseInt(custId);
		String status="failure";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			StoreCustomer storeCustomerObj= em.find(StoreCustomer.class, custid);
			storeCustomerObj.setCreditCustomer("Y");
			em.getTransaction().commit();
			status="success";		
		} catch (Exception e) {
			throw new DAOException("Cant covert credit customer", e);
		} finally {
			em.close();
		}
		return status;
	}

	@Override
	public double getTotaltransactionPerCustomer(int storeCustomerId,
			String storeId) throws DAOException {
		EntityManager em = null;
		EntityManager em1 = null;
		Double grossAmount = null;
		double sumBillDetails = 0;
		List<Double> doubleValueList = new ArrayList<Double>();
		List<OrderMaster> orderMastersList = new ArrayList<OrderMaster>();
		Bill bill = new Bill();
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT u FROM OrderMaster u WHERE u.storeCustomerId =:storeCustomerId and u.storeId=:storeId",
					    OrderMaster.class);
			qry.setParameter("storeCustomerId", storeCustomerId);
			qry.setParameter("storeId", storeId1);
			orderMastersList = qry.getResultList();
			em.getTransaction().commit();

			for (OrderMaster orderMasterListObj : orderMastersList) {
				int orderId = (orderMasterListObj.getId());
				em1 = entityManagerFactory.createEntityManager();
				em1.getTransaction().begin();
				TypedQuery<Bill> qry1 = em
						.createQuery("SELECT u FROM Bill u WHERE u.orderbill.id =:orderbill",
						    Bill.class);
				qry1.setParameter("orderbill", orderId);
				System.out.println(qry1.getSingleResult());
				bill = qry1.getSingleResult();
				em1.getTransaction().commit();
				grossAmount = bill.getGrossAmt();
				doubleValueList.add(grossAmount);
				
				em1.close();
			}

			for (int i = 0; i < doubleValueList.size(); i++)
				sumBillDetails += doubleValueList.get(i);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return sumBillDetails;
	}

	@Override
	public List<ArrayList<String>> getCustomerMostPurchaseItem(
			int storeCustomerId, String storeId) throws DAOException {

		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		ArrayList<String> mostPurchaseItem = new ArrayList<String>();
		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String queryStr = "Select  x.store_customer_id,x.Id,x.Item_Id,x.Item_Name,x.Most_Purchase From( SELECT  o.store_customer_id,o.Id,t.Item_Id,i.Name Item_Name,SUM(t.quantityof_item) AS Most_Purchase from fo_t_orders o inner join fo_t_orders_item t on o.Id = t.Order_Id inner join fm_m_food_items i on t.item_Id = i.Id where o.store_customer_id=? and o.store_id =? group By o.store_customer_id ,o.Id ,t.Item_Id ,i.Name )x order by x.Most_Purchase desc limit 3;";
			Query qry = em.createNativeQuery(queryStr);
			qry.setParameter(1, storeCustomerId);
			qry.setParameter(2, storeId1);
			List<Object> resultList = qry.getResultList();
			Iterator<Object> it = resultList.iterator();
			while (it.hasNext()) {
				Object[] order = (Object[]) it.next();
				String customer_Id = (String) order[0].toString();
				String order_Id = (String) order[1].toString();
				String item_Id = (String) order[2].toString();
				String item_Name = (String) order[3].toString();
				String most_Purchase_Count = (String) order[4].toString();

				mostPurchaseItem.add("customer_Id : " + customer_Id);
				mostPurchaseItem.add("order_Id : " + order_Id);
				mostPurchaseItem.add("item_Id : " + item_Id);
				mostPurchaseItem.add("item_Name : " + item_Name);
				mostPurchaseItem.add("most_Purchase_Count : "
						+ most_Purchase_Count);
				finalList.add(mostPurchaseItem);
				mostPurchaseItem = new ArrayList<>();
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return finalList;
	}

	@Override
	public List<String> getCustomerLastVisitDate(int storeCustomerId,
			String storeId) throws DAOException {
		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		List<String> lastVisitDetailsList = new ArrayList<String>();
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String queryStr = "select t.store_customer_id  , MAX(t.order_date) AS order_date  ,Datediff(current_date(),MAX(t.order_date) ) AS daysdiff from fo_t_orders t 	INNER JOIN  ci_m_store_customer c ON t.store_customer_id = c.id  where t.store_customer_id=? and t.store_id =? group by c.name ,t.store_customer_id ";
			Query qry = em.createNativeQuery(queryStr);
			// Query qry =
			// em.createQuery("select c.name AS customer_name    ,t.storeCustomerId  , MAX(t.orderDate) AS order_date  ,Datediff(current_date(),MAX(t.orderDate) ) AS daysdiff from OrderMaster t 	INNER JOIN  StoreCustomer c ON t.storeCustomerId = c.id  where t.storeCustomerId=:storeCustomerId and t.storeId =:storeId group by c.name ,t.storeCustomerId ");
			qry.setParameter(1, storeCustomerId);
			qry.setParameter(2, storeId1);
			List<Object> resultList = qry.getResultList();
			Iterator<Object> it = resultList.iterator();
			while (it.hasNext()) {
				Object[] order = (Object[]) it.next();
				String customer_Id = (String) order[0].toString();
				String last_Visit_Date = (String) order[1].toString();
				String day_Diff = (String) order[2].toString();
				lastVisitDetailsList.add("customer_Id : " + customer_Id);
				lastVisitDetailsList
						.add("last_Visit_Date : " + last_Visit_Date);
				lastVisitDetailsList.add("day_Diff : " + day_Diff);
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return lastVisitDetailsList;
	}

	@Override
	public List<String> getCustomerTotalSpendAmount(int storeCustomerId,
			String storeId) throws DAOException {

		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		List<String> customerTotalSpendAmountListObj = new ArrayList<String>();
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String queryStr = "SELECT  SUM(b.gross_amt) AS Total_Spent From fo_t_orders o inner join bp_t_bill b ON b.Order_Id = o.Id where o.store_customer_id=? and o.store_id =? ";
			Query qry = em.createNativeQuery(queryStr);
			qry.setParameter(1, storeCustomerId);
			qry.setParameter(2, storeId1);
			customerTotalSpendAmountListObj = qry.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return customerTotalSpendAmountListObj;
	}

	@Override
	public List<ArrayList<CustomerInfo>> getTopCustomer(String storeId)
			throws DAOException {

		EntityManager em = null;
		CustomerInfo customerInfo = new CustomerInfo();
		int storeId1 = Integer.parseInt(storeId);

		ArrayList<CustomerInfo> topCustomerList = new ArrayList<CustomerInfo>();
		List<ArrayList<CustomerInfo>> finalList = new ArrayList<ArrayList<CustomerInfo>>();
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// String
			// queryStr="SELECT c.name,SUM(b.gross_amt) AS Total_Spent From fo_t_orders o inner join bp_t_bill b ON b.Order_Id = o.Id  inner join ci_m_store_customer c ON o.store_customer_id = c.Id where  o.flag ='N' and o.store_customer_id != 0 and o.store_id =? group by o.store_customer_id, c.name order by SUM(b.gross_amt) desc limit 5 ";
			String queryStr = "SELECT c.id ,c.name ,c.email_id,c.anniversary_date,c.dob,SUM(b.gross_amt) AS Total_Spent , MAX(o.order_date) order_date ,Datediff(current_date(),MAX(o.order_date) ) daysdiff From fo_t_orders o inner join bp_t_bill b ON b.Order_Id = o.Id  inner join ci_m_store_customer c ON o.store_customer_id = c.Id where  o.flag ='N' and o.store_customer_id != 0 and o.store_id =? and c.credit_customer='N' group by o.store_customer_id, c.name order by SUM(b.gross_amt) desc limit 5 ";
			Query qry = em.createNativeQuery(queryStr);
			qry.setParameter(1, storeId1);
			// topCustomerList= qry.getResultList();
			List<Object> resultList = qry.getResultList();
			Iterator<Object> it = resultList.iterator();
			while (it.hasNext()) {
				String custId = null;
				String custName = null;
				String custEmailId = null;
				String anniversary_date = null;
				String dob = null;
				String total_Spent = null;
				String lastVisitDate = null;
				String dayDiff = null;
				Object[] order = (Object[]) it.next();
				try {
					custId = (String) order[0].toString();
				} catch (NullPointerException e) {
					custId = "0";
				}
				try {
					custName = (String) order[1].toString();
				} catch (NullPointerException e) {
					custName = "null";
				}
				try {
					custEmailId = (String) order[2].toString();
				} catch (NullPointerException e) {
					custEmailId = "null";
				}
				try {
					anniversary_date = (String) order[3].toString();
				} catch (NullPointerException e) {
					anniversary_date = "null";
				}
				try {
					dob = (String) order[4].toString();
				} catch (NullPointerException e) {
					dob = "null";
				}
				try {
					total_Spent = (String) order[5].toString();
				} catch (NullPointerException e) {
					total_Spent = "null";
				}
				try {
					lastVisitDate = (String) order[6].toString();
				} catch (NullPointerException e) {
					lastVisitDate = "null";
				}
				try {
					lastVisitDate = (String) order[6].toString();
				} catch (NullPointerException e) {
					lastVisitDate = "null";
				}
				try {
					dayDiff = (String) order[7].toString();
				} catch (NullPointerException e) {
					dayDiff = "null";
				}

				customerInfo.setCustId(custId);
				customerInfo.setCustName(custName);
				customerInfo.setCustEmailId(custEmailId);
				customerInfo.setAnniversary_date(anniversary_date);
				customerInfo.setDob(dob);
				customerInfo.setTotal_Spent_Amount(total_Spent);
				customerInfo.setLast_Visit_Date(lastVisitDate);
				customerInfo.setDayDiff(dayDiff);
				// topCustomerList.add("name : "+name);
				// topCustomerList.add("Total_Spent : "+total_Spent);
				topCustomerList.add(customerInfo);
				customerInfo = new CustomerInfo();
				finalList.add(topCustomerList);
				topCustomerList = new ArrayList<>();
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return finalList;
	}

	@Override
	public List<ArrayList<String>> getCustomerTransactionSummery(
			int storeCustomerId, String storeId) throws DAOException {

		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		ArrayList<String> customerTransactionSummeryStringList = new ArrayList<String>();
		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String queryStr = "SELECT p.order_id,o.order_no,p.amount ,p.paid_amount ,p.amount_to_pay ,o.order_date FROM bp_t_fo_orders_payment p INNER JOIN fo_t_orders o ON p.order_id = o.id WHERE o.store_customer_id != 0 and o.store_customer_id =? and o.store_id =? group by p.amount,p.paid_amount,p.amount_to_pay order by o.order_date desc";
			Query qry = em.createNativeQuery(queryStr);
			qry.setParameter(1, storeCustomerId);
			qry.setParameter(2, storeId1);
			List<Object> resultList = qry.getResultList();
			Iterator<Object> it = resultList.iterator();
			while (it.hasNext()) {
				Object[] order = (Object[]) it.next();
				String order_ID = (String) order[0].toString();
				String order_NO = (String) order[1].toString();
				String amount = (String) order[2].toString();
				String paid_amount = (String) order[3].toString();
				String amount_to_pay = (String) order[4].toString();
				String order_date = (String) order[5].toString();
				customerTransactionSummeryStringList.add("Order_Id : "
						+ order_ID);
				customerTransactionSummeryStringList.add("Order_No : "
						+ order_NO);
				customerTransactionSummeryStringList.add("amount : " + amount);
				customerTransactionSummeryStringList.add("paid_amount : "
						+ paid_amount);
				customerTransactionSummeryStringList.add("amount_to_pay : "
						+ amount_to_pay);
				customerTransactionSummeryStringList.add("order_date : "
						+ order_date);
				finalList.add(customerTransactionSummeryStringList);
				customerTransactionSummeryStringList = new ArrayList<String>();
				;
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			em.close();
		}
		return finalList;
	}

	@Override
	public List<ArrayList<String>> getCustomerPaymentSummery(
			int storeCustomerId, String storeId) throws DAOException {

		EntityManager em = null;
		int storeId1 = Integer.parseInt(storeId);
		ArrayList<String> customerPaymentSummeryStringList = new ArrayList<String>();
		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		try {

			try {
				
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				// String
				// queryStr="SELECT SUM(p.paid_amount) FROM fo_t_orders o INNER JOIN bp_t_fo_orders_payment P ON p.order_id = o.Id where o.store_customer_id=? and o.store_id =? and o.flag ='Y'";
				String queryStr = "SELECT x.Id,x.order_date,x.paid_amount,y.Total_paid_amount,Datediff(current_date(),MAX(x.order_date) ) daysdiff FROM ( SELECT o.Id , o.order_date, o.order_time, o.time, sum(p.paid_amount) paid_amount FROM fo_t_orders o  INNER JOIN bp_t_fo_orders_payment P ON o.id = p.order_id WHERE o.store_customer_id=? and o.store_id =? group by o.Id , o.order_date , o.order_time , o.time ) x  inner join  ( SELECT SUM(p.paid_amount) Total_paid_amount FROM fo_t_orders o INNER JOIN bp_t_fo_orders_payment P ON p.order_id = o.Id  where o.store_customer_id=? and o.store_id =?  )y order by x.order_time desc";
				Query qry = em.createNativeQuery(queryStr);
				qry.setParameter(1, storeCustomerId);
				qry.setParameter(2, storeId1);
				qry.setParameter(3, storeCustomerId);
				qry.setParameter(4, storeId1);
				// BigDecimal value=(BigDecimal)
				// qry.setMaxResults(1).getSingleResult();
				Object resultList = qry.setMaxResults(1).getSingleResult();
				Object[] order = (Object[]) resultList;
				customerPaymentSummeryStringList.add("Order_Id :"
						+ (String) order[0].toString());
				customerPaymentSummeryStringList.add("Last_Order_Date :"
						+ (String) order[1].toString());
				customerPaymentSummeryStringList.add("Last_Order_Paid_Amount :"
						+ (String) order[2].toString());
				customerPaymentSummeryStringList
						.add("Total_Paid_Amount_Summary :"
								+ (String) order[3].toString());
				customerPaymentSummeryStringList.add("DayDiff :"
						+ (String) order[4].toString());
			} catch (NullPointerException e) {
				logger.info("No Payment Found");
			}
			finalList.add(customerPaymentSummeryStringList);
			customerPaymentSummeryStringList = new ArrayList<>();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);

		} finally {
			em.close();
		}
		return finalList;
	}

	/**
	 * @Desc Store ServiceCharges Value For Single Object
	 */
	public String updateServiceChargesForAllOrderTypes(int storeID,
			String orderTypeShortName, String scVal) throws DAOException {
		String status = "";
		EntityManager em = null;
		OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// get store
			int storeId = storeID;
			// set taxes for all items in the store
			String orderTypeName = orderTypeShortName;
			float serviceChargesVal = Float.parseFloat(scVal);
			List<OrderType> orderType = ordersDAOImpl .getOrderTypeByStore(storeId);
			if (orderType.size() > 0) {
				if (serviceChargesVal >= 0) {
					for (OrderType orderType2 : orderType) {
						String shortName = orderType2.getOrdertypeShortName();
						if (shortName.equals(orderTypeShortName)
								|| shortName == orderTypeName) {
							orderType2.setServiceChargeValue(serviceChargesVal);
						}
						em.merge(orderType2);
					}
				}
			}
			if (serviceChargesVal >= 0) {
				Query query = em
						.createNativeQuery("UPDATE restaurant.fo_m_order_type SET sc_value =? where store_id =? and ordertype_name = ? and flag = 'Y';");
				query.setParameter(1, serviceChargesVal);
				query.setParameter(2, storeId);
				query.setParameter(3, orderTypeName);
				query.executeUpdate();
			}
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

	/**
	 * @Desc Store ServiceCharges Value For List Object
	 */
	@Override
	public String updateAllServiceChargesForAllOrderTypes(
			List<ServiceChargesFortAllItems> serviceChargesFortAllItems)
			throws DAOException {
		String status = "";
		EntityManager em = null;
		OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// for (int i = 0; i < serviceChargesFortAllItems.size(); i++) {
			for (ServiceChargesFortAllItems serviceChargesFortAllItemsObj : serviceChargesFortAllItems) {
				// get store
				int storeId = serviceChargesFortAllItemsObj.getStoreId();
				// set taxes for all items in the store
				String orderTypeShortName = serviceChargesFortAllItemsObj
						.getOrderTypeShortName();
				float serviceChargesVal = serviceChargesFortAllItemsObj
						.getScValue();
				List<OrderType> orderType = ordersDAOImpl.getOrderTypeByStore(storeId);
				if (orderType.size() > 0) {
					if (serviceChargesVal >= 0) {
						for (OrderType orderType2 : orderType) {
							String shortName = orderType2
									.getOrdertypeShortName();
							if (shortName.equals(orderTypeShortName)
									|| shortName == orderTypeShortName) {
								orderType2
										.setServiceChargeValue(serviceChargesVal);
							}
							em.merge(orderType2);
						}
					}
				}
				if (serviceChargesVal >= 0) {
					Query query = em
							.createNativeQuery("UPDATE restaurant.fo_m_order_type SET sc_value =? where store_id =? and ordertype_name = ? and flag = 'Y';");
					query.setParameter(1, serviceChargesVal);
					query.setParameter(2, storeId);
					query.setParameter(3, orderTypeShortName);
					query.executeUpdate();
				}
				em.getTransaction().commit();
			}// end loop
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

	/**
	 * @Desc Find ServiceCharge Details
	 * @param ordertypeShortName
	 * @param storeId
	 * @return
	 */
	public OrderType getOrderType(int id, int storeId) {
		OrderType orderType = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT o FROM OrderType o WHERE o.storeId=:storeId and o.id=:id ");
			qry.setParameter("storeId", storeId);
			qry.setParameter("id", id);

			orderType = (OrderType) qry.setMaxResults(1).getSingleResult();
			em.getTransaction().commit();
			logger.info("successfully : ");
		} catch (Exception e) {
			logger.info("Check data to be shown", e);
		} finally {
			em.close();
		}
		return orderType;
	}
}

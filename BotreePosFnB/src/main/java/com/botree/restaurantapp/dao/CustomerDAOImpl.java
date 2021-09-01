package com.botree.restaurantapp.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.commonUtil.ResponseObj;
import com.botree.restaurantapp.commonUtil.ReturnConstant;
import com.botree.restaurantapp.commonUtil.StoredProcedures;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.model.util.PersistenceListener;


@Component("customerDAO")
public class CustomerDAOImpl implements CustomerDAO {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public void createCustomer(Customer customer) throws DAOException {

		Customer cust = new Customer();
		EntityManager em = null;
		try {
			
			//System.out.println("hello11");
			em = entityManagerFactory.createEntityManager();
			//System.out.println("hello22");
			em.getTransaction().begin();
			//System.out.println("hello33");
			cust.setName(customer.getName());
			cust.setAddress(customer.getAddress());
			cust.setContactNo(customer.getContactNo());
			cust.setEmailId(customer.getEmailId());
			cust.setUserId(customer.getUserId());
			cust.setPassword(customer.getPassword());
			cust.setType("g");
			//System.out.println("Name:" + customer.getName());
			em.persist(cust);
			//System.out.println("heloo 444");
			// em.getTransaction().commit();
			//System.out.println("Name:" + cust.getContactNo());
			//System.out.println("heloo 555");
			System.out.print("Customer data saved successfully...");
			em.getTransaction().commit();
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

	}

	@Override
	public List<Customer> getCustomerByName(String custName)
			throws DAOException {

		List<Customer> success = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Customer> qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.name=?1",
					    Customer.class);
			qry.setParameter(1, custName);
			success = qry.getResultList();
			/*
			if (success.size() != 0) {
				Iterator<Customer> itr = success.iterator();
				while (itr.hasNext()) {
					Customer customer = (Customer) itr.next();
					System.out.println("Customer Name:" + customer.getName());
					System.out.println("Customer Address:"
							+ customer.getAddress());
					System.out.println("Customer Contact No.:"
							+ customer.getContactNo());
					System.out.println("Customer Email ID:"
							+ customer.getEmailId());
					System.out.println("Customer User ID:"
							+ customer.getUserId());
					System.out.println("Customer Password:"
							+ customer.getPassword());
				}
			} else {
				System.out.println("Record not found.");
			}
			*/
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return success;
	}

	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Customer cust = em.find(Customer.class, customer.getId());
			cust.setName(customer.getName());
			cust.setAddress(customer.getAddress());
			cust.setContactNo(customer.getContactNo());
			cust.setEmailId(customer.getEmailId());
			cust.setUserId(customer.getUserId());
			cust.setPassword(customer.getPassword());
			// em.merge(cust);
			em.getTransaction().commit();
			System.out.print("Customer data updated successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be updated", e);
		} finally {
			if(em != null) em.close();
		}
	}

	@Override
	public void deleteCustomer(Customer customer) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Customer cust = em.find(Customer.class, customer.getId());
			em.remove(cust);
			em.getTransaction().commit();
			System.out.print("Customer data deleted successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
	}

	@Override
	public Customer getCustomerByEmail(String email) throws DAOException {

		Customer getmail = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.emailId=?1");
			qry.setParameter(1, email);
			getmail = (Customer) qry.getSingleResult();
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return getmail;

	}

	@Override
	public Customer getCustomerByPhNmbr(String phNum) throws DAOException {

		Customer getphno = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.contactNo=?1");
			qry.setParameter(1, phNum);
			getphno = (Customer) qry.getSingleResult();
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return getphno;

	}

	@Override
	public Customer getCustomerById(int id) throws DAOException {

		Customer cust = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.id=?1");
			qry.setParameter(1, id);
			cust = (Customer) qry.getSingleResult();
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return cust;

	}

	@Override
	public List<StoreCustomer> getCustomerByStore(String id)
			throws DAOException {

		List<StoreCustomer> custList = null;
		EntityManager em = null;
		int storeId = Integer.parseInt(id);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreCustomer> qry = em
					.createQuery("SELECT c FROM StoreCustomer c WHERE c.storeId=:storeId and c.deleteFlag='N'",
					    StoreCustomer.class);
			qry.setParameter("storeId", storeId);
			custList = qry.getResultList();
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		
		return custList;

	}

	public List<Customer> getUsersByStore(String id) throws DAOException {

		List<Customer> custList = null;
		EntityManager em = null;
		int storeId = Integer.parseInt(id);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Customer> qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.storeId=:storeId and c.status='Y'",
					    Customer.class);
			qry.setParameter("storeId", storeId);
			custList = qry.getResultList();
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return custList;

	}

	@Override
	public List<StoreCustomer> getCreditCustomerByStore(String id)
			throws DAOException {

		List<StoreCustomer> custList = null;
		EntityManager em = null;
		int storeId = Integer.parseInt(id);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<StoreCustomer> qry = em
					.createQuery("SELECT c FROM StoreCustomer c WHERE c.storeId=:storeId and c.creditCustomer='Y'and c.deleteFlag='N'",
					    StoreCustomer.class);
			qry.setParameter("storeId", storeId);
			custList = qry.getResultList();
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return custList;

	}

	@Override
	public StoreCustomer getCustomerById(String id, String storeId)
			throws DAOException {

		StoreCustomer cust = null;
		EntityManager em = null;
		int custId = Integer.parseInt(id);
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM StoreCustomer c WHERE c.id=:id and c.storeId=:storeId and c.deleteFlag='N'");
			qry.setParameter("id", custId);
			qry.setParameter("storeId", storeId1);
			cust = (StoreCustomer) qry.getSingleResult();
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return cust;

	}

	
	
	@Override
	public String getDataDump(String storeId, String user, String pwd)
			throws DAOException {

		String status = "success";
		StoreAddressDAO storeAddressDAO = new StoreAddressDAOImpl();

		try {
			int storeid = Integer.parseInt(storeId);
			StoreMaster store = storeAddressDAO.getStoreByStoreId(storeid);
			String dbPath = store.getDbPath();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// get current date time with Date()
			//System.out.println(dateFormat.format(date));

			// get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String dateTime = dateFormat.format(cal.getTime());
			//System.out.println(dateTime);
			String[] arr = dateTime.split(" ");
			String[] time = arr[1].split(":");

			String fileName = "restaurant" + arr[0] + time[0] + time[1]
					+ time[2];

			// check if specified directory exists
			File theDir = new File("C:\\sql_backup");
			//File theDir = new File("D:\\sql_backup");

			// if the directory does not exist, create it
			if (!theDir.exists()) {

				boolean result = false;

				try {
					theDir.mkdir();
					result = true;
				} catch (SecurityException se) {
					// handle it
				}
				if (result) {
					System.out.println("DIR created");
				}
			}

			String path = "C:\\sql_backup\\" + fileName.toString() + ".sql";
			//String path = "D:\\sql_backup\\" + fileName.toString() + ".sql";
			File fbackup = new File(path);
			// execute mysqldump command
			String dbName = "restaurant";
			String goToDbDirCmd = "cd " + dbPath.trim();
			String mysqlDmpCmd = "mysqldump.exe --quick --lock-tables --user="
					+ user + " " + "--password=" + pwd + " " + dbName;
			String[] command1 = new String[] { "cmd.exe", "/c", goToDbDirCmd };
			// String[] command = new String[] {"cmd.exe", "/c",
			// "mysqldump.exe --quick --lock-tables --user=root --password=root restaurant"};
			String[] command = new String[] { "cmd.exe", "/c", mysqlDmpCmd };
			final Process process = Runtime.getRuntime().exec(command);
			final Process process1 = Runtime.getRuntime().exec(command1);

			if (process1 != null) {
//				String s = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
//				BufferedReader stdError = new BufferedReader(new InputStreamReader(process1.getErrorStream()));

				// read any errors from the attempted command
				System.out
						.println("Here is the standard error of the command (if any):\n");
//				while ((s = stdError.readLine()) != null) {
//					//System.out.println(s);
//				}

				reader.close();

			}

			if (process1 != null && process1.waitFor() == 0) {
				// success ...
			} else {
				// failed
			}

			if (process != null) {
				String line = null;
//				String s = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//				BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter(fbackup));
				while ((line = reader.readLine()) != null) {
					writer.write(line);
					writer.newLine();
				}
				// read any errors from the attempted command
				System.out
						.println("Here is the standard error of the command (if any):\n");
//				while ((s = stdError.readLine()) != null) {
//					//System.out.println(s);
//				}

				reader.close();
				writer.close();

			}
			if (process != null && process.waitFor() == 0) {
				// success ...
			} else {
				// failed
			}

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be shown", e);
		}

		return status;

	}

	@Override
	public CustomerInfo getCustomerByContact(String contact, String storeId)
			throws DAOException {

		StoreCustomer cust = null;
		CustomerInfo customerInfo = new CustomerInfo();
		OrderMaster order = null;
		EntityManager em = null;
		// int contactNo = Integer.parseInt(contact);
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.customerContact=:customerContact and o.storeId=:storeId and o.cancel='N' order by o.id desc limit 1");
			qry.setParameter("customerContact", contact);
			qry.setParameter("storeId", storeId1);
			order = (OrderMaster) qry.setMaxResults(1).getSingleResult();
			// get customer info
			String custName = order.getCustomerName();
			String custContact = order.getCustomerContact();
			String delAddr = order.getDeliveryAddress();
			String delPrsnName = order.getDeliveryPersonName();
			Date delTime = order.getDeliveryTime();
			String custVatRegNo = order.getCustVatRegNo();
			String loc = order.getLocation();
			String strt = order.getStreet();
			String hNo = order.getHouseNo();

			// check if credit cust
			int creditCustId = order.getStoreCustomerId();

			if (creditCustId > 0) {

				Query qry1 = em
						.createQuery("SELECT s FROM StoreCustomer s WHERE s.contactNo=:contactNo and s.storeId=:storeId");
				qry1.setParameter("contactNo", contact);
				qry1.setParameter("storeId", storeId1);
				cust = (StoreCustomer) qry1.getSingleResult();
			}

			customerInfo.setCustContact(custContact);
			customerInfo.setCustName(custName);
			customerInfo.setDelivAddr(delAddr);
			customerInfo.setDelivPersonName(delPrsnName);
			customerInfo.setDelivTime(delTime);
			customerInfo.setCreditCust(cust);
			customerInfo.setCustVatRegNo(custVatRegNo);
			customerInfo.setLocation(loc);
			customerInfo.setStreet(strt);
			customerInfo.setHouseNo(hNo);

			em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return customerInfo;

	}
	
	@Override
	public StoreCustomer authenticateOnlineCustomer(StoreCustomer customer)
			throws DAOException {
		
		StoreCustomer cust1 = null;
		EntityManager em = null;
	
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				Query qry = em
						.createQuery("SELECT c FROM StoreCustomer c WHERE c.userName=:userName and c.password=:password and c.type='o'");
				qry.setParameter("userName", customer.getUserName());
				qry.setParameter("password", customer.getPassword());
				cust1 = (StoreCustomer) qry.getSingleResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return cust1;

	}
	
	@Override
	public String registerOnlineCustomer(StoreCustomer customer)
			throws DAOException {
		
		String status = "";
		EntityManager em = null;
	
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			customer.setStoreId(0);
			customer.setType("o");
			customer.setDeleteFlag("N");
			customer.setCreditLimit(0);
			em.persist(customer);
			em.getTransaction().commit();
			status="success";
		
		} catch (Exception e) {
			status="failure";
			throw new DAOException("Check data to be saved", e);
		} finally {
			if(em != null) em.close();
		}
		return status;

	}

	@Override
	public List<String> getAllCustomerPhoneNo(String storeId)
			throws DAOException {
	
		EntityManager em = null;
		List<String> contactLst=null;
		// int contactNo = Integer.parseInt(contact);
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<String> qry = em
					.createQuery("SELECT distinct(o.customerContact) FROM OrderMaster o WHERE o.storeId=:storeId and o.customerContact is not null and LENGTH(o.customerContact) > 0 and o.customerContact !=:customerContact",
					    String.class);
			qry.setParameter("storeId", storeId1);
			qry.setParameter("customerContact", "null");
			contactLst = qry.getResultList();
		
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		
		return contactLst;

	}
	
	@Override
	public List<Customer> getAllCustomerLocation(String storeId,String location)
			throws DAOException {
	
		EntityManager em = null;
		List<Customer> locLst=new ArrayList<Customer>();
		List<String> locLst1=null;
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String locLike=location+"%";
		
			String queryStr="SELECT distinct o.location FROM restaurant.fo_t_orders o where o.store_id=? and o.location like '"+locLike+"' order by o.location asc";
			//System.out.println("query str:: "+queryStr);
			TypedQuery<String> query1 = em.createQuery(queryStr, String.class);
			
			query1.setParameter(1, storeId1);
			locLst1 = query1.getResultList();
			
		
			Iterator<String> it=locLst1.iterator();
			while (it.hasNext()) {
				String loc = (String) it.next();
				Customer customer=new Customer();
				customer.setLocation(loc);
				locLst.add(customer);
			}
			
			em.getTransaction().commit();
		
		} catch (Exception e) {
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return locLst;

	}
	
	@Override
	public List<Customer> getCustomerDetails(String storeId)
			throws DAOException {
	
		EntityManager em = null;
		List<Customer> custList=new ArrayList<Customer>();
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			Query query = em
					.createNativeQuery("SELECT distinct o.customer_name ,o.customer_contact ,o.delivery_address FROM restaurant.fo_t_orders o where o.store_id=? and o.customer_name != ' ' order by o.customer_name asc");
			
			query.setParameter(1, storeId);
			List<Object> resultList = query.getResultList();
			
			Iterator<Object> it=resultList.iterator();
			while (it.hasNext()) {
				Object[] order = (Object[]) it.next();
				Customer customer=new Customer();
				String name=(String)order[0];
				String phone=(String)order[1];
				String address=(String)order[2];
				customer.setName(name);
				customer.setContactNo(phone);
				customer.setAddress(address);
				custList.add(customer);
				
			}
		
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return custList;

	}
	
	@Override
	public List<Customer> getAllCustomerByName(String storeId,String name1)
			throws DAOException {
	
		EntityManager em = null;
		List<Customer> custList=new ArrayList<Customer>();
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String nameLike=name1+"%";
			/*Query qry = em
					.createQuery("SELECT distinct o FROM OrderMaster o WHERE o.storeId=:storeId and o.customerName is not null and LENGTH(o.customerName) > 0");
			qry.setParameter("storeId", storeId1);*/
			String queryStr="SELECT distinct o.customer_name ,o.customer_contact,o.delivery_address FROM restaurant.fo_t_orders o where o.store_id=? and o.customer_name != ' ' and o.customer_name like '"+nameLike+"' order by o.customer_name asc";
			System.out.println("query str:: "+queryStr);
			Query query1 = em.createNativeQuery(queryStr);
			
			query1.setParameter(1, storeId);
			List<Object> resultList = query1.getResultList();
		
			Iterator<Object> it=resultList.iterator();
			while (it.hasNext()) {
				Object[] order = (Object[]) it.next();
				Customer customer=new Customer();
				String name=(String)order[0];
				String phone=(String)order[1];
				String addr=(String)order[2];
				
				customer.setName(name);
				customer.setContactNo(phone);
				customer.setAddress(addr);
				custList.add(customer);
				
			}
		
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return custList;

	}
	
	@Override
	public List<Customer> getAllCustomerByPhone(String storeId,String phone)
			throws DAOException {
	
		EntityManager em = null;
		List<Customer> custList=new ArrayList<Customer>();
		int storeId1 = Integer.parseInt(storeId);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String phLike=phone+"%";
			
			String queryStr="SELECT distinct o.customer_name ,o.customer_contact,o.delivery_address FROM restaurant.fo_t_orders o where o.store_id=? and o.customer_contact != ' ' and o.customer_contact is not null and o.customer_contact!='null' and o.customer_contact like '"+phLike+"' order by o.customer_contact asc";
			System.out.println("query str:: "+queryStr);
			Query query1 = em.createNativeQuery(queryStr);
			
			query1.setParameter(1, storeId1);
			List<Object> resultList = query1.getResultList();
		
			Iterator<Object> it=resultList.iterator();
			while (it.hasNext()) {
				Object[] order = (Object[]) it.next();
				Customer customer=new Customer();
				String name=(String)order[0];
				String phone1=(String)order[1];
				String addr=(String)order[2];
				
				customer.setName(name);
				customer.setContactNo(phone1);
				customer.setAddress(addr);
				custList.add(customer);
				
			}
		
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return custList;

	}

	@Override
	public String addCustomer(StoreCustomer customer) throws DAOException {
		EntityManager em = null;
		String status = "";
		StoreCustomer cust = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			customer.setDeleteFlag("N");
			String contactNo = customer.getContactNo();
			int storeId = customer.getStoreId();
			// check if customer already exists
			try {
				Query qry = em
						.createQuery("SELECT c FROM StoreCustomer c WHERE c.contactNo=:contactNo and c.storeId=:storeId");
				qry.setParameter("contactNo", contactNo);
				qry.setParameter("storeId", storeId);
				cust = (StoreCustomer) qry.getSingleResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (cust == null) {

				em.persist(customer);
				em.getTransaction().commit();
				status = "success";
			}

			else {
				status = CommonProerties.duplicate_phone_number;
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		insertCustomerAccount(customer);
		
		return status;

	}

	@Override
	public String updateCustomer(StoreCustomer customer) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			em.merge(customer);
			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be updated", e);
		} finally {
			if(em != null) em.close();
		}
		updateCustomerAccount(customer);
		return status;
	}

	@Override
	public String deleteCustomer(String id, String storeId) throws DAOException {
		EntityManager em = null;
		String status = "";
		ResponseObj resobj = new ResponseObj();
		try {
			StoreCustomer cust = null;
			int id1 = Integer.parseInt(id);
			int storeId1 = Integer.parseInt(storeId);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM StoreCustomer c WHERE c.id=:id and c.storeId=:storeId");
			qry.setParameter("id", id1);
			qry.setParameter("storeId", storeId1);
			cust = (StoreCustomer) qry.getSingleResult();
			cust.setDeleteFlag("Y");

			em.getTransaction().commit();
			
			resobj = deleteCustomerNVendorAccountJournal(id,storeId);
			
			if(resobj.getId()==1) {
				status = "success";
				System.out.print("Customer data deleted successfully....");
			}
			else {
				status = "inuse";
				System.out.print("Customer data not deleted successfully inuse....");
			}

		} catch (Exception e) {
			em.getTransaction().rollback();
			status = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
		//deleteCustomerNVendorAccountJournal(id,storeId);
		return status;
	}
	
	@Override
	public String changePassword(String id, String storeId, String oldPassword,
			String newPassword) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			Customer cust = null;
			int id1 = Integer.parseInt(id);
			int storeId1 = Integer.parseInt(storeId);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM Customer c WHERE c.id=:id and c.storeId=:storeId and c.password=:password");
			qry.setParameter("id", id1);
			qry.setParameter("storeId", storeId1);
			qry.setParameter("password", oldPassword);
			cust = (Customer) qry.getSingleResult();
			cust.setPassword(newPassword);
			em.merge(cust);

			em.getTransaction().commit();
			status = "success";
			System.out.print("Customer password changed successfully....");

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data", e);
		} finally {
			if(em != null) em.close();
		}

		return status;
	}

	@Override
	public String changeAdminPassword(String userId, String storeId,
			String oldPassword, String newPassword) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			User user = null;
			// int id1 = Integer.parseInt(id);
			int storeId1 = Integer.parseInt(storeId);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT c FROM User c WHERE c.userId=:userId and c.storeId=:storeId and c.password=:password");
			qry.setParameter("userId", userId);
			qry.setParameter("storeId", storeId1);
			qry.setParameter("password", oldPassword);
			user = (User) qry.getSingleResult();
			user.setPassword(newPassword);
			em.merge(user);

			em.getTransaction().commit();
			status = "success";
			System.out.print("Admin password changed successfully....");

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data", e);
		} finally {
			if(em != null) em.close();
		}

		return status;
	}

	//13.07.2018
	public ResponseObj deleteCustomerNVendorAccountJournal(String id, String storeId) throws DAOException {
		EntityManager em = null;
		Connection connection = null;
		CallableStatement callableStatement = null;
		int result = 0;
		ResponseObj responseObj=new ResponseObj();
		try {
			int custid = Integer.parseInt(id);
			int storeId1 = Integer.parseInt(storeId);

			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_DELETE_CUSTOMER_N_VENDOR);
			callableStatement.setInt(1, storeId1);
			callableStatement.setInt(2, custid);
			callableStatement.setString(3, "SDE");

			callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

			callableStatement.execute();
			result = callableStatement.getInt(4);
			
			if (result >0) {
				
				responseObj.setStatus(ReturnConstant.SUCCESS);
				responseObj.setId(result);
				responseObj.setReason("customer account deleted successfully.");
				
			} else {
				
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(result);
				responseObj.setReason("customer account not deleted successfully.");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("customer account not deleted successfully.");
			throw new DAOException("Check data to be deleted", e);
		}

		finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	
	public ResponseObj insertCustomerAccount(StoreCustomer customer)
			throws DAOException {
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_ONLY_INSERT_ACCOUNT);
				callableStatement.setString(1, customer.getName());
				callableStatement.setString(2, "");
				callableStatement.setInt(3, 0);
				callableStatement.setString(4, "SDE");
				callableStatement.setInt(5, 0);
				callableStatement.setInt(6, 0);
				callableStatement.setInt(7, 0);
				callableStatement.setString(8, customer.getAddress());
				callableStatement.setString(9, "");
				callableStatement.setString(10, customer.getContactNo());
				callableStatement.setString(11, customer.getEmailId());
				callableStatement.setString(12, "");
				callableStatement.setString(13, customer.getCust_vat_reg_no());
				callableStatement.setString(14, "");
				callableStatement.setString(15, "");
				callableStatement.setDouble(16, 0);
				callableStatement.setDouble(17, customer.getCreditLimit());
				callableStatement.setInt(18, 1);
				callableStatement.setInt(19, 0);
				callableStatement.setDouble(20, 0);
				callableStatement.setString(21, customer.getCreatedDate());
				callableStatement.setInt(22, 0);
				callableStatement.setInt(23, customer.getStoreId());
				callableStatement.setInt(24, 0);
				callableStatement.setInt(25, customer.getCreatedByid());
				callableStatement.setDouble(26, 0);
				callableStatement.setInt(27, 0);
				callableStatement.setInt(28, customer.getId());
				callableStatement.registerOutParameter(29,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(29);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;

	}
	
	public ResponseObj updateCustomerAccount(StoreCustomer customer)
			throws DAOException {
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_ONLY_UPDATE_ACCOUNT);
				callableStatement.setString(1, customer.getName());
				callableStatement.setString(2, "");
				callableStatement.setInt(3, 0);
				callableStatement.setString(4, "SDE");
				callableStatement.setInt(5, 0);
				callableStatement.setInt(6, 0);
				callableStatement.setInt(7, 0);
				callableStatement.setString(8, customer.getAddress());
				callableStatement.setString(9, "");
				callableStatement.setString(10, customer.getContactNo());
				callableStatement.setString(11, customer.getEmailId());
				callableStatement.setString(12, "");
				callableStatement.setString(13, customer.getCust_vat_reg_no());
				callableStatement.setString(14, "");
				callableStatement.setString(15, "");
				callableStatement.setDouble(16, 0);
				callableStatement.setDouble(17, customer.getCreditLimit());
				callableStatement.setInt(18, 1);
				callableStatement.setInt(19, 0);
				callableStatement.setDouble(20, 0);
				callableStatement.setString(21, customer.getCreatedDate());
				callableStatement.setInt(22, 0);
				callableStatement.setInt(23, customer.getStoreId());
				callableStatement.setInt(24, 0);
				callableStatement.setInt(25, customer.getCreatedByid());
				callableStatement.setDouble(26, 0);
				callableStatement.setInt(27, 0);
				callableStatement.setInt(28, customer.getId());
				callableStatement.registerOutParameter(29,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(29);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;

	}

	
}

package com.botree.restaurantapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.StoreCustomerDAO;
import com.botree.restaurantapp.dao.StoreCustomerDaoImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.ServiceChargesFortAllItems;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.service.exception.ServiceException;

/**
 * 
 * @author Anup Mallick
 * 
 */
@Service
public class StoreCustomerService {

  @Autowired
	private StoreCustomerDAO storeCustomerDAO;

	public StoreCustomerService() {

	}

	/**
	 * @Desc use for Serching by StoreCustomerByPhNmbr
	 * @param contact
	 * @param storeId
	 * @return CustomerInfo
	 * @throws ServiceException
	 */
	public CustomerInfo getStoreCustomerByPhNmbr(String contact, String storeId)
			throws ServiceException {

		CustomerInfo cust = null;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getStoreCustomerByPhNmbr(contact, storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}
	
	public StoreCustomer getCreditCustomerByPhNmbr(String contact, String storeId)
			throws ServiceException {

		StoreCustomer cust = null;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getCreditCustomerByPhNmbr(contact, storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view credit customer", e);

		}

		return cust;
	}
	/**
	 * @Desc getStoreCustomerByCustId
	 * @param custId
	 * @param storeId
	 * @return
	 * @throws ServiceException
	 */
	public CustomerInfo getStoreCustomerByCustId(int custId, String storeId)
			throws ServiceException {

		CustomerInfo cust = null;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getStoreCustomerByCustId(custId, storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}
	
	/**
	 * @Desc use for Serching by StoreCustomerByPhNmbr
	 * @param contact
	 * @param storeId
	 * @return StoreCustomer List
	 * @throws ServiceException
	 */
	public List<StoreCustomer> getAllStoreCustomerByPhNmbr(String contact,
			String storeId) throws ServiceException {

		List<StoreCustomer> cust = null;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getAllStoreCustomerByPhNmbr(contact,
					storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}

	/**
	 * @Desc use for Searching by StoreCustomerByName
	 * @param storeId
	 * @param name
	 * @return StoreCustomer List
	 * @throws ServiceException
	 */
	public List<StoreCustomer> getAllStoreCustomerByName(String storeId,
			String name) throws ServiceException {

		List<StoreCustomer> cust = null;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getAllStoreCustomerByName(storeId, name);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get all customer by name",
					e);
		}
		return cust;
	}
	
	public List<StoreCustomer> getAllStoreCustomerByStoreId(String storeId) throws ServiceException {

		List<StoreCustomer> cust = null;
		try {
			//storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getAllStoreCustomerByStoreId(storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get all customer by store id",
					e);
		}
		return cust;
	}
	
	public List<StoreCustomer> getAllRBStoreCustomerByPhNmbr(String contact,
			String storeId) throws ServiceException {

		List<StoreCustomer> cust = null;
		try {
			//storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getAllRBStoreCustomerByPhNmbr(contact,
					storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view all rb customer", e);

		}

		return cust;
	}
	
	public List<StoreCustomer> getAllRBStoreCustomerByName(String storeId,
			String name) throws ServiceException {

		List<StoreCustomer> cust = null;
		try {
			//storeCustomerDAO = new StoreCustomerDaoImpl();
			cust = storeCustomerDAO.getAllRBStoreCustomerByName(storeId, name);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get all rb customer by name",
					e);
		}
		return cust;
	}
	
	public String convertToCreditCustomer(String custId, String storeId)
			throws ServiceException {

		String status = "failure";
		try {
			status = storeCustomerDAO.convertToCreditCustomer(custId, storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to convert customer", e);

		}

		return status;
	}

	/**
	 * @Desc use for get perCustomerPerBillDetail
	 * @param storeCustomerId
	 * @param storeId
	 * @return double value
	 * @throws ServiceException
	 */
	public double getTotaltransactionPerCustomer(int storeCustomerId, String storeId)
			throws ServiceException {
		double sumBillDetails = 0;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			sumBillDetails=storeCustomerDAO.getTotaltransactionPerCustomer(storeCustomerId,
					storeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sumBillDetails;
	}
	/**
	 * @Desc use for Most Purchase Item of Particular Customer
	 * @param storeCustomerId
	 * @param storeId
	 * @return
	 * @throws ServiceException
	 */
	public List<ArrayList<String>> getCustomerMostPurchaseItem(int storeCustomerId, String storeId)
			throws ServiceException {
		//List<String> customerMostPurchaseItem= new ArrayList<String>();
		List<ArrayList<String>> customerMostPurchaseItem=new ArrayList<ArrayList<String>>();
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			customerMostPurchaseItem=storeCustomerDAO.getCustomerMostPurchaseItem(storeCustomerId,
					storeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return customerMostPurchaseItem;
	}
	/**
	 * @Desc Use for Customer Last Visit Date
	 * @param storeCustomerId
	 * @param storeId
	 * @return
	 * @throws ServiceException
	 */
	public List<String> getCustomerLastVisitDate(int storeCustomerId, String storeId)
			throws ServiceException {
		
		List<String> customerLastVisitDateListObj= new ArrayList<String>();
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			customerLastVisitDateListObj=storeCustomerDAO.getCustomerLastVisitDate(storeCustomerId,
					storeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return customerLastVisitDateListObj;
	}
	
	/**
	 * @Descuse Use for Total amount Spend for Particular Customer 
	 * @param storeCustomerId
	 * @param storeId
	 * @return
	 * @throws ServiceException
	 */
	public List<String> getCustomerTotalSpendAmount(int storeCustomerId, String storeId)
			throws ServiceException {
		
		List<String> customerTotalSpendAmountListObj= new ArrayList<String>();
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			customerTotalSpendAmountListObj=storeCustomerDAO.getCustomerTotalSpendAmount(storeCustomerId,
					storeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return customerTotalSpendAmountListObj;
	}
	
	/**
	 * @Desc use for get Top Customer
	 * @param storeCustomerId
	 * @param storeId
	 * @return
	 * @throws ServiceException
	 */
	public List<ArrayList<CustomerInfo>> getTopCustomer(String storeId)
			throws ServiceException {
		List<ArrayList<CustomerInfo>> topCustomerList=new ArrayList<ArrayList<CustomerInfo>>();
		//List<String> topCustomerList= new ArrayList<String>();
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			topCustomerList=storeCustomerDAO.getTopCustomer(storeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return topCustomerList;
	}
	
	/**
	 * @Desc use for transaction Summary of Customer
	 * @param storeCustomerId
	 * @param storeId
	 * @return
	 * @throws ServiceException
	 */
	public List<ArrayList<String>> getCustomerTransactionSummery(int storeCustomerId, String storeId)
			throws ServiceException {
		List<ArrayList<String>> customerTransactionSummery=new ArrayList<ArrayList<String>>();;
		//List<String> customerTransactionSummery= new ArrayList<String>();
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			customerTransactionSummery=storeCustomerDAO.getCustomerTransactionSummery(storeCustomerId,
					storeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return customerTransactionSummery;
	}
	
	/**
	 * @Desc Use for payment Summary of customer
	 * @param storeCustomerId
	 * @param storeId
	 * @return
	 * @throws ServiceException
	 */
	public List<ArrayList<String>> getCustomerPaymentSummery(int storeCustomerId, String storeId)
			throws ServiceException {
		List<ArrayList<String>> customerPaymentSummery=new ArrayList<ArrayList<String>>();
		//List<String> customerPaymentSummery= new ArrayList<String>();
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			customerPaymentSummery=storeCustomerDAO.getCustomerPaymentSummery(storeCustomerId,
					storeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return customerPaymentSummery;
	}
	
	public String storeServiceCharges(int storeId,String orderTypeShortName, String scVal)
			throws ServiceException {

		String status=null;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			storeCustomerDAO.updateServiceChargesForAllOrderTypes(storeId,orderTypeShortName,scVal);
			status="success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);
		}
		return status;
	}
	
	public String storeAllServiceCharges(List<ServiceChargesFortAllItems>serviceChargesFortAllItems)
			throws ServiceException {

		String status=null;
		try {
			storeCustomerDAO = new StoreCustomerDaoImpl();
			storeCustomerDAO.updateAllServiceChargesForAllOrderTypes(serviceChargesFortAllItems);
			status="success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);
		}
		return status;
	}
}

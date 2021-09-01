package com.botree.restaurantapp.dao;

import java.util.ArrayList;
import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.ServiceChargesFortAllItems;
import com.botree.restaurantapp.model.StoreCustomer;

public interface StoreCustomerDAO {

	void createStoreCustomer(StoreCustomer StoreCustomer) throws DAOException;

	void updateStoreCustomer(StoreCustomer storeCustomer) throws DAOException;

	void deleteStoreCustomer(StoreCustomer storeCustomer) throws DAOException;

	CustomerInfo getStoreCustomerByPhNmbr(String contact,
			String storeId) throws DAOException;

	List<StoreCustomer> getAllStoreCustomerByName(String storeId, String name1)
			throws DAOException;

	List<StoreCustomer> getAllStoreCustomerByPhNmbr(String contact,
			String storeId) throws DAOException;
	
	List<StoreCustomer> getAllStoreCustomerByStoreId(String storeId)
			throws DAOException;
	
	//added on 31.10.2019
	List<StoreCustomer> getAllRBStoreCustomerByName(String storeId, String name1)
			throws DAOException;

	List<StoreCustomer> getAllRBStoreCustomerByPhNmbr(String contact,
			String storeId) throws DAOException;

	double getTotaltransactionPerCustomer(int storeCustomerId, String storeId)
			throws DAOException;

	List<ArrayList<String>> getCustomerMostPurchaseItem(int storeCustomerId, String storeId)
			throws DAOException;

	List<String> getCustomerLastVisitDate(int storeCustomerId, String storeId)
			throws DAOException;

	List<String> getCustomerTotalSpendAmount(int storeCustomerId, String storeId)
			throws DAOException;

	List<ArrayList<CustomerInfo>> getTopCustomer(String storeId)
			throws DAOException;

	List<ArrayList<String>> getCustomerTransactionSummery(int storeCustomerId,
			String storeId) throws DAOException;

	List<ArrayList<String>> getCustomerPaymentSummery(int storeCustomerId, String storeId)
			throws DAOException;

	String updateAllServiceChargesForAllOrderTypes(
			List<ServiceChargesFortAllItems> serviceChargesFortAllItems)
			throws DAOException;

	CustomerInfo getStoreCustomerByCustId(int custId, String storeId)
			throws DAOException;
	
  StoreCustomer getCreditCustomerByPhNmbr(String contact, String storeId)
	    throws DAOException;
  
  String updateServiceChargesForAllOrderTypes(int storeID,
      String orderTypeShortName, String scVal) throws DAOException;

  String convertToCreditCustomer(String custId, String storeId)
			throws DAOException;

}

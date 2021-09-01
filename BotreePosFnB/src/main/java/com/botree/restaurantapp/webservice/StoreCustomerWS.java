package com.botree.restaurantapp.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StoreCustomerWS {

	String getStoreCustomerByPhNmbr(String storeId, String phone);

	String getAllStoreCustomerByName(String storeId, String name);

	String getAllStoreCustomerByPhNmbr(String storeId, String phone);

	String getTotaltransactionPerCustomer(int storeCustomerId, String storeId);

	String getCustomerMostPurchaseItem(int storeCustomerId, String storeId);

	String getCustomerLastVisitDate(int storeCustomerId, String storeId);

	String getTopCustomer(String storeId);

	String getCustomerTransactionSummery(int storeCustomerId, String storeId);

	String getCustomerTotalSpendAmount(int storeCustomerId, String storeId);

	String getCustomerPaymentSummery(int storeCustomerId, String storeId);

	void reportTop5CustomerDetails(Integer storeId, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	void reportCustomerDetails(Integer storeId, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	 void reportOldVsNewCustomerDetails(Integer storeId, String orderFrom, String orderTo, Integer reportType,
	      HttpServletRequest request, HttpServletResponse response);

	 void reportOrderItemDetails(Integer storeId, String orderFrom, String orderTo, Integer reportType,
	      HttpServletRequest request, HttpServletResponse response);
	 //added on 04.04.2019
	 String getAllStoreCustomerByStoreId(String storeId);
	 //added on 31.10.2019
	 String getAllRBStoreCustomerByName(String storeId, String name);
	 String getAllRBStoreCustomerByPhNmbr(String storeId, String phone);
	 String convertToCreditCustomer(String storeId,String custId);
}

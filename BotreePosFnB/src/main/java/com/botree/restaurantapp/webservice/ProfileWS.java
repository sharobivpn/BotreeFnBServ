package com.botree.restaurantapp.webservice;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.StatusMessage;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.model.User;

public interface ProfileWS {

	Customer getUserProfile(Customer user);

	StatusMessage updateUserProfile(Customer customer);

	StatusMessage createUserProfile(Customer customer);

	public String getCustomerByStore(String id);

	public String getCustomerById(String id, String storeId);

	public String addCustomer(StoreCustomer customer);

	public String updateCustomer(StoreCustomer customer);

	public String deleteCustomer(String id, String storeId);

	public String getCreditCustomerByStore(String id);

	public String getCustomerByContact(String contact, String storeId);

	public String changePassword(Customer customer);

	public String changeAdminPassword(User user);

	public String getDataDump(String storeId, String user, String pwd);

	public String getAllCustomerPhoneNo(String storeId);
	
	public String getCustomerDetails(String storeId);
	public String authenticateOnlineCustomer(StoreCustomer customerParam);
	public String registerOnlineCustomer(StoreCustomer customerParam);
	public String getAllCustomerByName(
			String storeId,
			String name);
	public String getAllCustomerLocation(
			String storeId,
			String location);
	public String getAllCustomerByPhone(
			String storeId,
			String phone);
	
	
}

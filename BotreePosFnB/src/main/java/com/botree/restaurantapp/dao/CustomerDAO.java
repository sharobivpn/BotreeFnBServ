package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.StoreCustomer;


public interface CustomerDAO {
	public void createCustomer(Customer customer) throws DAOException;

	public List<Customer> getCustomerByName(String custName)
			throws DAOException;

	public Customer getCustomerByEmail(String email) throws DAOException;

	public Customer getCustomerByPhNmbr(String phNum) throws DAOException;

	public void updateCustomer(Customer customer) throws DAOException;

	public void deleteCustomer(Customer customer) throws DAOException;

	public Customer getCustomerById(int id) throws DAOException;

	
	
	public List<StoreCustomer> getCustomerByStore(String id)
			throws DAOException;

	public List<StoreCustomer> getCreditCustomerByStore(String id)
			throws DAOException;


	
	public StoreCustomer getCustomerById(String id, String storeId)
			throws DAOException;

	public String getDataDump(String storeId, String user, String pwd)
			throws DAOException;

	public CustomerInfo getCustomerByContact(String contact, String storeId)
			throws DAOException;
	
	public StoreCustomer authenticateOnlineCustomer(StoreCustomer customer)
			throws DAOException;
	
	public String registerOnlineCustomer(StoreCustomer customer)
			throws DAOException;

	public List<Customer> getAllCustomerLocation(String storeId,String location)
			throws DAOException;
	
	public List<String> getAllCustomerPhoneNo(String storeId)
			throws DAOException;
	
	public List<Customer> getAllCustomerByPhone(String storeId,String name)
			throws DAOException;
	
	public List<Customer> getAllCustomerByName(String storeId,String name)
			throws DAOException;
	
	public List<Customer> getCustomerDetails(String storeId)
			throws DAOException;

	public String addCustomer(StoreCustomer customer) throws DAOException;

	public String updateCustomer(StoreCustomer customer) throws DAOException;

	public String deleteCustomer(String id, String storeId) throws DAOException;

	public String changePassword(String id, String storeId, String oldPassword,
			String newPassword) throws DAOException;

	public String changeAdminPassword(String id, String storeId,
			String oldPassword, String newPassword) throws DAOException;
}

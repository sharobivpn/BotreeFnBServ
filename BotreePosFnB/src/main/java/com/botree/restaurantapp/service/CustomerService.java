package com.botree.restaurantapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.CustomerDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.service.exception.ServiceException;


@Service
public class CustomerService {

  @Autowired
	private CustomerDAO customerDAO;

	public CustomerService() {

	}

	public void createCustomer(Customer customer) throws ServiceException {

		try {

			System.out.println("Enter CustomerService.createCustomer ");
			// create a new customer
			customerDAO.createCustomer(customer);
			System.out.println("exit CustomerService.createCustomer ");
		} catch (DAOException e) {

			throw new ServiceException("registration error", e);

		}

	}

	public String addCustomer(StoreCustomer customer) throws ServiceException {

		String status = "";
		try {
			// create a new customer
			status=customerDAO.addCustomer(customer);

		} catch (DAOException e) {

			throw new ServiceException("registration error", e);

		}

		return status;

	}

	public String updateCustomer(StoreCustomer customer)
			throws ServiceException {

		String status = "";
		try {
			// update a customer
			status=customerDAO.updateCustomer(customer);

		} catch (DAOException e) {
			throw new ServiceException("update error", e);

		}

		return status;

	}

	public String deleteCustomer(String id, String storeId)
			throws ServiceException {

		String status = "";
		try {
			// delete a customer
			status = customerDAO.deleteCustomer(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("update error", e);

		}

		return status;

	}

	public String changePassword(String id, String storeId, String oldPassword,
			String newPassword) throws ServiceException {

		String status = "";
		try {
			// delete a customer
			status=customerDAO.changePassword(id, storeId,oldPassword,newPassword);

		} catch (DAOException e) {
			throw new ServiceException("update error", e);

		}

		return status;

	}
	
	public String changeAdminPassword(String id, String storeId, String oldPassword,
			String newPassword) throws ServiceException {

		String status = "";
		try {
			// delete a customer
			status=customerDAO.changeAdminPassword(id, storeId,oldPassword,newPassword);

		} catch (DAOException e) {
			throw new ServiceException("update error", e);

		}

		return status;

	}

	public void updateCustomer(Customer customer) throws ServiceException {
		try {

			System.out.println("Enter CustomerService.updateCustomer ");
			// update customer
			customerDAO.updateCustomer(customer);
			System.out.println("exit CustomerService.updateCustomer ");

		} catch (DAOException e) {

			throw new ServiceException("profile updation error", e);

		}

	}

	public List<Customer> getCustomerByName(String custName)
			throws ServiceException {
		List<Customer> custList = null;
		try {

			System.out.println("Enter CustomerService.getCustomerByName ");
			custList = customerDAO.getCustomerByName(custName);
			System.out.println("exit CustomerService.getCustomerByName ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while displaying customers", e);
		}

		return custList;
	}

	public Customer getCustomerByEmail(String email) throws ServiceException {
		Customer cust = null;
		try {

			System.out.println("Enter CustomerService.getCustomerByEmail ");
			cust = customerDAO.getCustomerByEmail(email);
			System.out.println("exit CustomerService.getCustomerByEmail ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}

	public Customer getCustomerByPhNmbr(String phNum) throws ServiceException {

		Customer cust = null;
		try {

			System.out.println("Enter CustomerService.getCustomerByPhNmbr ");
			cust = customerDAO.getCustomerByPhNmbr(phNum);
			System.out.println("exit CustomerService.getCustomerByPhNmbr ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}

	
	
	public List<StoreCustomer> getCustomerByStore(String id)
			throws ServiceException {

		List<StoreCustomer> custList = null;
		try {
			custList = customerDAO.getCustomerByStore(id);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return custList;
	}

	public List<StoreCustomer> getCreditCustomerByStore(String id)
			throws ServiceException {

		List<StoreCustomer> custList = null;
		try {
			custList = customerDAO.getCreditCustomerByStore(id);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return custList;
	}
	
	public StoreCustomer getCustomerById(String id, String storeId)
			throws ServiceException {

		StoreCustomer cust = null;
		try {
			cust = customerDAO.getCustomerById(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}
	
	public String getDataDump(String storeId,String user, String pwd)
			throws ServiceException {

		String status = null;
		try {
			status = customerDAO.getDataDump(storeId,user, pwd);
			
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return status;
	}

	public CustomerInfo getCustomerByContact(String contact, String storeId)
			throws ServiceException {

		CustomerInfo cust = null;
		try {
			cust = customerDAO.getCustomerByContact(contact, storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}
	
	public StoreCustomer authenticateOnlineCustomer(StoreCustomer customer)
			throws ServiceException {

		StoreCustomer cust = null;
		try {
			cust = customerDAO.authenticateOnlineCustomer(customer);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to login online customer", e);

		}

		return cust;
	}
	
	public String registerOnlineCustomer(StoreCustomer customer)
			throws ServiceException {

		String status = "";
		try {
			status = customerDAO.registerOnlineCustomer(customer);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to register online customer", e);

		}

		return status;
	}
	
	public List<Customer> getAllCustomerLocation(String storeId,String location)
			throws ServiceException {

		List<Customer> cust = null;
		try {
			cust = customerDAO.getAllCustomerLocation(storeId,location);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer location", e);

		}

		return cust;
	}
	
	public List<String> getAllCustomerPhoneNo(String storeId)
			throws ServiceException {

		List<String> cust = null;
		try {
			cust = customerDAO.getAllCustomerPhoneNo(storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}
	
	public List<Customer> getCustomerDetails(String storeId)
			throws ServiceException {

		List<Customer> cust = null;
		try {
			cust = customerDAO.getCustomerDetails(storeId);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get all customer", e);

		}

		return cust;
	}
	
	public List<Customer> getAllCustomerByPhone(String storeId,String phone)
			throws ServiceException {

		List<Customer> cust = null;
		try {
			cust = customerDAO.getAllCustomerByPhone(storeId,phone);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get all customer by phone", e);

		}

		return cust;
	}
	
	public List<Customer> getAllCustomerByName(String storeId,String name)
			throws ServiceException {

		List<Customer> cust = null;
		try {
			cust = customerDAO.getAllCustomerByName(storeId,name);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get all customer by name", e);

		}

		return cust;
	}


	public void deleteCustomer(Customer customer) throws ServiceException {
		try {

			System.out.println("Enter CustomerService.deleteCustomer ");
			customerDAO.deleteCustomer(customer);
			System.out.println("exit CustomerService.deleteCustomer ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to delete customer", e);

		}

	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

}

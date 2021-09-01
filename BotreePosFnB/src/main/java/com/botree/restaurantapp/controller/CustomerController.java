package com.botree.restaurantapp.controller;

import java.util.List;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.service.CustomerService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class CustomerController {

	private Customer customer = null;
	private CustomerService customerService = null;

	public CustomerController() {
		
	}

	public List<Customer> getCustomerByName() throws ServiceException {
		List<Customer> custList = null;
		try {

			String custName = customer.getName();
			System.out.println("Enter CustomerController.getCustomerByName ");
			custList = customerService.getCustomerByName(custName);
			System.out.println("exit CustomerController.getCustomerByName ");

		} catch (ServiceException e) {

			throw new ServiceException(
					"problem occured while displaying customers", e);
		}

		return custList;
	}

	public Customer getCustomerByEmail() throws ServiceException {
		Customer cust = null;
		try {

			String email = customer.getEmailId();
			System.out.println("Enter CustomerService.getCustomerByEmail ");
			cust = customerService.getCustomerByEmail(email);
			System.out.println("exit CustomerService.getCustomerByEmail ");

		} catch (ServiceException e) {

			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}

	public Customer getCustomerByPhNmbr() throws ServiceException {

		Customer cust = null;
		try {

			String phNum = customer.getContactNo();
			System.out.println("Enter CustomerService.getCustomerByPhNmbr ");
			cust = customerService.getCustomerByPhNmbr(phNum);
			System.out.println("exit CustomerService.getCustomerByPhNmbr ");

		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occured while trying to view customer", e);

		}

		return cust;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}

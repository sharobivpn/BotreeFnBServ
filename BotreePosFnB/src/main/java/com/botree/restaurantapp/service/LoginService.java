package com.botree.restaurantapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.LoginDAO;
import com.botree.restaurantapp.dao.MenuDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.PosModules;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class LoginService {

  @Autowired
	private LoginDAO loginDAO;
  
  @Autowired
	private MenuDAO menuDAO;

	public Customer getUserByCredential(Customer customer)
			throws ServiceException {
		Customer loggedinUser = null;
		try {
			//System.out.println("Enter LoginService.getUserByCredential ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			loggedinUser = loginDAO.getUserByCredential(customer);

			//System.out.println("Enter LoginService.getUserByCredential ");
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occured while trying to login",
					e);
		}
		return loggedinUser;
	}

	public List<Customer> getAllWaiters(String storeId) throws ServiceException {
		List<Customer> waiters = null;
		try {

			//System.out.println("Enter LoginService.getAllWaiters ");

			waiters = loginDAO.getAllWaiters(storeId);
			//System.out.println("exit LoginService.getAllWaiters ");

		} catch (DAOException e) {
			throw new ServiceException("get all waiters error", e);

		}
		return waiters;
	}

	public List<PosModules> getPosModulesByUserId(String userId, String storeId)
			throws ServiceException {
		List<PosModules> modules = null;
		try {

			modules = loginDAO.getPosModulesByUserId(userId, storeId);

		} catch (DAOException e) {
			throw new ServiceException("get all getPosModulesByUserId error", e);

		}
		return modules;
	}

	public List<PosModules> getReportByStore(String userId, String storeId)
			throws ServiceException {
		List<PosModules> modules = null;
		try {
			modules = loginDAO.getReportByStore(userId, storeId);

		} catch (DAOException e) {
			throw new ServiceException("get all getReportByStore error", e);

		}
		return modules;
	}

	public LoginDAO getLoginDAO() {
		return loginDAO;
	}

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

}

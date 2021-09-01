package com.botree.restaurantapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.AccountDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.DailyExpenditure;
import com.botree.restaurantapp.model.DailyExpenditureType;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class AccountService {

  @Autowired
	private AccountDAO accountDAO;

	public AccountService() {

	}

	/*public int createOrder(OrderMaster order, HttpServletRequest request)
			throws ServiceException {
		int orderId;
		try {

			System.out.println("Enter OrderService.createOrder ");
			// create a new order
			orderId = ordersDAO.createOrder(order, request);
			System.out.println("exit OrderService.createOrder ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to save an order", e);

		}
		return orderId;

	}*/

	public int addDailyExpenditure(DailyExpenditure dailyExpenditure)
			throws ServiceException {
		int dailyExpId = 0;
		try {

			dailyExpId = accountDAO.addDailyExpenditure(dailyExpenditure);

		} catch (DAOException e) {
			throw new ServiceException("error creating PO", e);

		}
		return dailyExpId;
	}

	public List<DailyExpenditure> getDailyExpenditureByDate(String date,
			String storeId) throws ServiceException {

		List<DailyExpenditure> dailyExpenditureList = null;
		try {

			// get list of all expenditures
			dailyExpenditureList = accountDAO.getDailyExpenditureByDate(date,
					storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all order Types", e);

		}
		return dailyExpenditureList;
	}

	public List<DailyExpenditure> getDailyExpenditureByPeriod(String date,String toDate,
			String storeId) throws ServiceException {

		List<DailyExpenditure> dailyExpenditureList = null;
		try {
			// get list of all expenditures by Period 
			dailyExpenditureList = accountDAO.getDailyExpenditureByPeriod(date,toDate,
					storeId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all order Types", e);
		}
		return dailyExpenditureList;
	}
	
	public String deleteDailyExpenditure(DailyExpenditure dailyExpenditure) throws ServiceException {

		String status = "";
		try {
			// delete daily exp
			status = accountDAO.deleteDailyExpenditure(dailyExpenditure);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to delete an daily exp ", e);

		}
		return status;

	}
	
	public String updateDailyExpenditure(DailyExpenditure dailyExpenditure) throws ServiceException {

		String status = "";
		try {
			// update daily exp
			status = accountDAO.updateDailyExpenditure(dailyExpenditure);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to update an daily exp ", e);

		}
		return status;

	}
	
	//added on 15.05.2018
	public List<DailyExpenditureType> getExpenditureTypes() throws ServiceException {

		List<DailyExpenditureType> dailyExpenditureTypeList = null;
		try {

			// get list of all expenditures types
			dailyExpenditureTypeList = accountDAO.getExpenditureTypes();

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all expenditure Types", e);

		}
		return dailyExpenditureTypeList;
	}

	
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	/*public OrdersDAO getOrdersDAO() {
		return ordersDAO;
	}

	public void setOrdersDAO(OrdersDAO ordersDAO) {
		this.ordersDAO = ordersDAO;
	}*/

}

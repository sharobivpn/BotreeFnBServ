package com.botree.restaurantapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.dao.StUserDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.StUser;
import com.botree.restaurantapp.service.exception.ServiceException;

public class StUserService {
	
	private final static Logger logger = LogManager.getLogger(StUserService.class);

	private StUserDAO stUserDAO;

	public StUserService() {}

	public List<StUser> getAllUsers() throws ServiceException {
		logger.info("Enter stUserService.getAllUsers ");
		
		List<StUser> userList = null;

		try {
			System.out.println("Enter stUserService.getAllUsers ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			userList = stUserDAO.getAllUsers();
			System.out.println("Exit stUserService.getAllUsers ");
		} catch (DAOException e) {
			throw new ServiceException("problem occured while trying to get all users", e);
		}
		
		logger.info("Exit stUserService.getAllUsers ");
		return userList;
	}

	/*public User getUserById(String userId) throws ServiceException {

		User user = null;

		try {
			System.out.println("Enter UserService.getUserById ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			user = stUserDAO.getUserById(userId);

			System.out.println("Exit UserService.getUserById ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to get user", e);
		}
		return user;

	}

	public void update(User user) throws ServiceException {

		try {
			System.out.println("Enter UserService.update ");

			stUserDAO.update(user);

			System.out.println("Exit UserService.update ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to update user", e);
		}

	}

	public void delete(User user) throws ServiceException {

		try {
			System.out.println("Enter UserService.delete ");

			stUserDAO.delete(user);

			System.out.println("Exit UserService.delete ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to delete user", e);
		}

	}
*/
	public StUser login(StUser user) throws ServiceException {

		StUser loggedInUser = null;
		try {
			System.out.println("Enter StUserService.login ");

			loggedInUser = stUserDAO.login(user);

			System.out.println("Exit StUserService.login ");
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occured while trying to login st user", e);
		}

		return loggedInUser;

	}

	

	/*public void createUser(User user) throws ServiceException {

		User createUser = null;

		try {
			System.out.println("Enter UserService.createUser ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			stUserDAO.createUser(user);

			System.out.println("Exit UserService.createUser ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to create user", e);
		}
	}

	public List<StoreMaster> getStoresByUser(int id) throws ServiceException {
		List<StoreMaster> strList = null;

		try {
			logger.debug("In UserService: getStoresByUser", id);
			strList = stUserDAO.getStoresByUser(id);
		} catch (DAOException e) {
			logger.error("DAOException: ", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("In UserService: getStoresByUser", id);
		return strList;

	}*/

	public StUserDAO getStUserDAO() {
		return stUserDAO;
	}

	public void setStUserDAO(StUserDAO stUserDAO) {
		this.stUserDAO = stUserDAO;
	}

}

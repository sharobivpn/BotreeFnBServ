package com.botree.restaurantapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.UserDAO;
import com.botree.restaurantapp.dao.UserDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.model.UserTransaction;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class UserService {
	private final static Logger logger = LogManager.getLogger(UserService.class);

	@Autowired
	private UserDAO userDAO;

	public UserService() {

	}

	public List<User> getAllUsers() throws ServiceException {

		List<User> userList = null;

		try {
			System.out.println("Enter UserService.getAllUsers ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			userList = userDAO.getAllUsers();

			System.out.println("Exit UserService.getAllUsers ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to get all users", e);
		}
		return userList;

	}

	public List<Customer> getAllTabletUsers() throws ServiceException {

		List<Customer> tabletUser = null;

		try {
			System.out.println("Enter UserService.getAllTabletUsers ");
			tabletUser = userDAO.getAllTabletUsers();

			System.out.println("Exit UserService.getAllTabletUsers ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to get all TabletUsers", e);
		}
		return tabletUser;

	}
	
	public Customer getTabletUserById(String tabletUserId) throws ServiceException {

		Customer tabletUser = null;

		try {
			System.out.println("Enter UserService.getTabletUserById ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			tabletUser = userDAO.getTabletUserById(tabletUserId);

			System.out.println("Exit UserService.getUserById ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to get tabletUser", e);
		}
		return tabletUser;

	}

	public User getUserById(String userId) throws ServiceException {

		User user = null;

		try {
			System.out.println("Enter UserService.getUserById ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			user = userDAO.getUserById(userId);

			System.out.println("Exit UserService.getUserById ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to get user", e);
		}
		return user;

	}

	public void update(User user) throws ServiceException {

		try {
			System.out.println("Enter UserService.update ");

			userDAO.update(user);

			System.out.println("Exit UserService.update ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to update user", e);
		}

	}
	
	public void updateTabletUser(Customer tabletUser) throws ServiceException {

		try {
			System.out.println("Enter UserService.updateTabletUser ");

			userDAO.updateTabletUser(tabletUser);

			System.out.println("Exit UserService.updateTabletUser ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to update tabletUser", e);
		}

	}

	public void delete(User user) throws ServiceException {

		try {
			System.out.println("Enter UserService.delete ");

			userDAO.delete(user);

			System.out.println("Exit UserService.delete ");
		} catch (DAOException e) {

			throw new ServiceException("problem occured while trying to delete user", e);
		}

	}

	public User login(User user) throws ServiceException {

		User loggedInUser = null;
		try {
			System.out.println("Enter UserService.login ");

			loggedInUser = userDAO.login(user);

			System.out.println("Exit UserService.login ");
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occured while trying to login user", e);
		}

		return loggedInUser;

	}
	
	public User loginToCheckAdmin(User user) throws ServiceException {

		User loggedInUser = null;
		UserDAO dao=new UserDAOImpl();
		try {
			System.out.println("Enter UserService.login ");

			loggedInUser = dao.loginToCheckAdmin(user);

			System.out.println("Exit UserService.login ");
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occured while trying to login user", e);
		}

		return loggedInUser;

	}

	public void createUser(User user) throws ServiceException {

		try {
			System.out.println("Enter UserService.createUser ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			userDAO.createUser(user);

			System.out.println("Exit UserService.createUser ");
		} catch (DAOException e) {
			System.out.println("service:: createUser: " + e.getMessage());
			e.printStackTrace();

			throw new ServiceException(e.getMessage(), e);
		}
	}

	public void createUserST(User user, UserTransaction transaction) throws ServiceException {

		try {
			System.out.println("Enter UserService.createUserST ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			userDAO.createUserST(user, transaction);

			System.out.println("Exit UserService.createUserST ");
		} catch (DAOException e) {
			System.out.println("service:: createUserST: " + e.getMessage());
			e.printStackTrace();

			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	public void createTabletUserST(Customer user) throws ServiceException {

		try {
			userDAO.createTabletUserST(user);
		} catch (DAOException e) {
			System.out.println("service:: createUserST: " + e.getMessage());
			e.printStackTrace();

			throw new ServiceException(e.getMessage(), e);
		}
	}

	public List<StoreMaster> getStoresByUser(int id) throws ServiceException {
		List<StoreMaster> strList = null;

		try {
			logger.debug("In UserService: getStoresByUser", id);
			strList = userDAO.getStoresByUser(id);
		} catch (DAOException e) {
			logger.error("DAOException: ", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("In UserService: getStoresByUser", id);
		return strList;

	}

	public List<StoreMaster> getStoreByRestaurantId(int id) throws ServiceException {
		List<StoreMaster> strList = null;

		try {
			logger.debug("In UserService: getStoreByRestaurantId", id);
			strList = userDAO.getStoreByRestaurantId(id);
		} catch (DAOException e) {
			logger.error("DAOException: ", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("In UserService: getStoreByRestaurantId", id);
		return strList;

	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}

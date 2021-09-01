package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.StUser;

public interface StUserDAO {

	public List<StUser> getAllUsers() throws DAOException;

	/*public User getUserById(String userId) throws DAOException;

	public void createUser(User user) throws DAOException;

	public void update(User user) throws DAOException;

	public void delete(User user) throws DAOException;*/

	public StUser login(StUser user) throws DAOException;

	//public List<StoreMaster> getStoresByUser(int id) throws DAOException;
}

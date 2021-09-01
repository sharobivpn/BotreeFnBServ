package com.botree.restaurantapp.dao;

import com.botree.restaurantapp.dao.exception.DAOException;

public interface MaintenenceJobDAO {

	public String cleanLogByPeriod(String tomcatDir, String days)
			throws DAOException;

}

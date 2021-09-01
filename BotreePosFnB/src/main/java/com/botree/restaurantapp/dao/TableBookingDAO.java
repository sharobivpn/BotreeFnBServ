package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.TableMaster;

public interface TableBookingDAO {

	public String updateTableStatus(TableMaster table) throws DAOException;

	public String addTable(TableMaster tableMaster) throws DAOException;

	public String updateTable(TableMaster tableMaster) throws DAOException;

	public String deleteTable(String id, String storeId) throws DAOException;

	public String addMultipleTable(String noOfTable, String capacity,
			String storeId) throws DAOException;

	public String setMultiOrder(String id, String status, String storeId)
			throws DAOException;

	public String updateTablePosition(List<TableMaster> tableList)
			throws DAOException;

}

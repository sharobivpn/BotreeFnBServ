package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.OrderMaster;

public interface DeliveriesDAO {

	public void updateDeliveryStatus(List<OrderMaster> orderList) throws DAOException;
	
	
	
}

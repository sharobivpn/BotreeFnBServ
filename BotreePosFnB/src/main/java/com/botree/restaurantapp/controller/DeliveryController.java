package com.botree.restaurantapp.controller;

import java.util.List;

import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.service.DeliveryService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class DeliveryController {

	private List<OrderMaster> orderList = null;
	private DeliveryService deliveryService = null;

	public DeliveryController() {
		
	}

	public void updateDeliveryStatus() throws ServiceException {
		try {

			System.out.println("Enter DeliveryService.updateDeliveryStatus ");
			// update delivery status
			deliveryService.updateDeliveryStatus(orderList);
			System.out.println("exit DeliveryService.updateDeliveryStatus ");

		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occured while trying to update delivery status", e);

		}

	}

	public List<OrderMaster> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderMaster> orderList) {
		this.orderList = orderList;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

}

/**
 * 
 */
package com.botree.restaurantapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.DeliveryBoyDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.DeliveryBoy;
import com.botree.restaurantapp.service.exception.ServiceException;
/**
 * @author chanchalN
 *
 */
@Service
public class DeliveryBoyService {

	public DeliveryBoyService() {}

	@Autowired
	private DeliveryBoyDAO deliveryBoyDAO;
	
	private final static Logger logger = LogManager.getLogger(DeliveryBoyService.class);
	
	public DeliveryBoyDAO getDeliveryBoyDAO() {
		return deliveryBoyDAO;
	}
	public void setDeliveryBoyDAO(DeliveryBoyDAO deliveryBoyDAO) {
		this.deliveryBoyDAO = deliveryBoyDAO;
	}
	
	public String addDeliveryBoy(DeliveryBoy deliveryBoy) throws ServiceException{
		String status = "failure";
		try {
			logger.info("Service Call for Adding Delivery Boy ");
			deliveryBoyDAO.createDeliveryBoy(deliveryBoy);
			logger.info("Delivery Boy Saved Successfully ");
			status = "success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add delivery boy", e);
		}
		return status;
	}
	public String editDeliveryBoy(DeliveryBoy deliveryBoy) throws ServiceException{
		String status = "failure";
		try {
			logger.info("Service Call for Adding Delivery Boy ");
			deliveryBoyDAO.updateDeliveryBoy(deliveryBoy);
			logger.info("Delivery Boy Updated Successfully ");
			status = "success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to edit delivery boy", e);
		}
		return status;
	}
	public String deleteDeliveryBoy(int id, String storeId) throws ServiceException{
		String status = "failure";
		try {
			logger.info("Service Call for deleting Delivery Boy ");
			deliveryBoyDAO.deleteDeliveryBoy(id,storeId);
			logger.info("Delivery Boy deleted Successfully ");
			status = "success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to delete delivery boy", e);
		}
		return status;
	}
	public DeliveryBoy getDeliveryBoyById(int id) throws ServiceException{
		DeliveryBoy d=null;
		try {
			logger.info("Service Call to get delivery Boy by id");
			d=deliveryBoyDAO.getDeliveryBoyById(id);
			logger.info("Delivery Boy by Id got Successfully ");
		} catch(DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get delivery boy by Id", e);
		}
		return d;
	}
	public List<DeliveryBoy> fetchAllDeliveryBoy(String store_id) throws ServiceException{
		List<DeliveryBoy> deliveryBoyList = null;
		try {
			logger.info("Service Call to get all delivery Boy");
			deliveryBoyList = deliveryBoyDAO.getAllDeliveryBoy(store_id);
			logger.info("Fetch all Delivery Boy Successfully ");
		}catch(DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get delivery boy by Id", e);
		}
		return deliveryBoyList;
	}
}

/**
 * 
 */
package com.botree.restaurantapp.webservice.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.DeliveryBoy;
import com.botree.restaurantapp.service.DeliveryBoyService;
import com.botree.restaurantapp.webservice.DeliveryBoyWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/deliveryboy")
public class DeliveryBoyWSImpl implements DeliveryBoyWS{
	
  @Autowired
  private DeliveryBoyService deliveryBoyService;
	
	private final static Logger logger = LogManager.getLogger(DeliveryBoyWSImpl.class);
	public DeliveryBoyService getDeliveryBoyService() {
		return deliveryBoyService;
	}
	public void setDeliveryBoyService(DeliveryBoyService deliveryBoyService) {
		this.deliveryBoyService = deliveryBoyService;
	}

	
	
	@Override
	@RequestMapping(value = "/addDeliveryBoy", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addDeliveryBoy(@RequestBody DeliveryBoy deliveryBoy) throws DAOException {
		String status="";
		try {
			logger.info("WebService call for adding delivery boy");
			status=deliveryBoyService.addDeliveryBoy(deliveryBoy);
			//status="success";
			logger.info("Adding successfull.");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Error while adding.");
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/editDeliveryBoy", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String editDeliveryBoy(@RequestBody DeliveryBoy deliveryBoy) throws DAOException {
		String status="";
		try {
			logger.info("WebService call for editing delivery boy");
			status = deliveryBoyService.editDeliveryBoy(deliveryBoy);
			//status="success";
			logger.info("Updating successfull.");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Error while editing.");
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/deleteDeliveryBoy", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String deleteDeliveryBoy(@RequestParam(value = "id") int id,
			@RequestParam(value = "storeId") String storeId) throws DAOException {
		String status="";
		try {
			logger.info("WebService call for deleting delivery boy");
			status = deliveryBoyService.deleteDeliveryBoy(id,storeId);
			//status="success";
			logger.info("Deleting successfull.");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Error while deleting.");
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/getDeliveryBoyById", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getDeliveryBoyById(@RequestParam(value = "id") int id) throws DAOException {
		DeliveryBoy deliveryBoyList = null;
		try {
			logger.info("web-Service calling to get delivery boy by id");
			deliveryBoyList =  deliveryBoyService.getDeliveryBoyById(id);
		} catch (Exception x) {
			logger.error("ERROR While Get Delivery Boy");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<DeliveryBoy>() {
		}.getType();
		String json = gson.toJson(deliveryBoyList, type);
		//System.out.println("Response; "+json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllDeliveryBoy",method = RequestMethod.GET,produces = "application/json")
	public String getAllDeliveryBoy(@RequestParam(value = "storeId") String storeid) throws DAOException {
		List<DeliveryBoy> deliveryBoyList = null;
		try {
			logger.info("web-Service calling For Get ALL Delivery boy");
			deliveryBoyList = (List<DeliveryBoy>) deliveryBoyService.fetchAllDeliveryBoy(storeid);
		} catch (Exception x) {
			logger.error("ERROR While Get Delivery Boy");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DeliveryBoy>>() {
		}.getType();
		String json = gson.toJson(deliveryBoyList, type);
		
		/*Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(deliveryBoyList, DeliveryBoy.class);*/
		//System.out.println("Get all deliveryboy resp: "+json);

		return json;
	}

}

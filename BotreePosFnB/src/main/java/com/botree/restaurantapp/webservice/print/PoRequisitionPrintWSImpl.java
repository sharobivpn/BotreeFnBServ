package com.botree.restaurantapp.webservice.print;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.dao.InventoryDAO;
import com.botree.restaurantapp.model.InventoryPurchaseOrder;
import com.botree.restaurantapp.print.PrintKotMaster;
import com.botree.restaurantapp.print.PrinterMaster;
import com.botree.restaurantapp.service.InventoryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/prints")
public class PoRequisitionPrintWSImpl implements PoRequisitionPrintWS {

  @Autowired
	private InventoryService inventoryService;

  @Autowired
	private InventoryDAO inventoryDAO;

	@Override
	@RequestMapping(value = "/printPoServer", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String printPurchaseOrderById(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "id") Integer id) {
		List<InventoryPurchaseOrder> inventoryPoOrders = null;

		inventoryService.setInventoryDAO(inventoryDAO);
		try {
			inventoryPoOrders = inventoryService.getPurchaseOrdersById(storeid, id);

			inventoryService.poPrintInThreeInch(inventoryPoOrders);

			// check if PO print is true for the store

			/*
			 * if (poPrintServerFlag.equalsIgnoreCase("Y")) {
			 * 
			 * poPrintInThreeInch(inventoryPoOrders, store); }
			 */

		} catch (Exception x) {
			x.printStackTrace();
		}

		return "";
	}

	@Override
	@RequestMapping(value = "/getAllInstalledPrinters", method = RequestMethod.GET, produces = "text/plain")
	public String getAllInstalledPrinters() {

		List<PrinterMaster> listPrinter = null;
		try {
			listPrinter = inventoryService.getAllInstalledPrinters();

		} catch (Exception x) {
			x.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<PrinterMaster>>() {
		}.getType();
		String json = gson.toJson(listPrinter, type);
		System.out.println(json);
		return json;

		
	}

	@Override
	@RequestMapping(value = "/getAllServerPrinters", method = RequestMethod.GET, produces = "text/plain")
	public String getAllServerPrinters(
			@RequestParam(value = "storeid") Integer storeid) {
		List<PrintKotMaster> printers = null;
		try {
			inventoryService.setInventoryDAO(inventoryDAO);
			printers = inventoryService.getAllServerPrinters(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<PrintKotMaster>>() {
		}.getType();
		String json = gson.toJson(printers, type);
		System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/assignPrinter", method = RequestMethod.POST,	consumes = "application/json", produces = "application/json")
	public String assignPrinter(@RequestBody PrintKotMaster kotMaster) {
		int printerAssignedId = 0;
		try {
			inventoryService.setInventoryDAO(inventoryDAO);
			printerAssignedId = inventoryService.assignPrinter(kotMaster);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return "" + printerAssignedId;
	}

	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

}

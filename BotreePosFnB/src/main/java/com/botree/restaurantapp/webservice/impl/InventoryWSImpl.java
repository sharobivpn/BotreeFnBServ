package com.botree.restaurantapp.webservice.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.commonUtil.CommonResultSetMapper;
import com.botree.restaurantapp.commonUtil.ResponseObj;
import com.botree.restaurantapp.commonUtil.ReturnConstant;
import com.botree.restaurantapp.model.FgInvoicePayment;
import com.botree.restaurantapp.model.FgItemCurrentStock;
import com.botree.restaurantapp.model.FgReturn;
import com.botree.restaurantapp.model.InventoryInvoicePayment;
import com.botree.restaurantapp.model.InventoryItems;
import com.botree.restaurantapp.model.InventoryPurchaseOrder;
import com.botree.restaurantapp.model.InventoryPurchaseOrderItem;
import com.botree.restaurantapp.model.InventoryReturn;
import com.botree.restaurantapp.model.InventoryStockIn;
import com.botree.restaurantapp.model.InventoryStockInItem;
import com.botree.restaurantapp.model.InventoryStockOut;
import com.botree.restaurantapp.model.InventoryStockOutItem;
import com.botree.restaurantapp.model.InventoryType;
import com.botree.restaurantapp.model.InventoryVendor;
import com.botree.restaurantapp.model.ItemCurrentStock;
import com.botree.restaurantapp.model.ReturnTypes;
import com.botree.restaurantapp.model.SalesReturn;
import com.botree.restaurantapp.model.account.AccountDTO;
import com.botree.restaurantapp.model.account.AccountGroupDTO;
import com.botree.restaurantapp.model.account.AccountMaster;
import com.botree.restaurantapp.model.account.AccountTypeDTO;
import com.botree.restaurantapp.model.account.ChartOfAccountDTO;
import com.botree.restaurantapp.model.account.JournalDTO;
import com.botree.restaurantapp.model.account.JournalListDTO;
import com.botree.restaurantapp.model.account.ParaJournalTypeMaster;
import com.botree.restaurantapp.model.inv.FgStockIn;
import com.botree.restaurantapp.model.inv.FgStockInItemsChild;
import com.botree.restaurantapp.model.rm.EstimateType;
import com.botree.restaurantapp.model.rm.RecipeIngredient;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.InventoryService;
import com.botree.restaurantapp.webservice.InventoryWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Controller
@ResponseBody
@RequestMapping(value = "/inventory")
public class InventoryWSImpl implements InventoryWS {

	@Autowired
	private InventoryService inventoryService;
	
	/*******
	 * @param storeid
	 * @return createAccountGroup
	 */
	@Override
	@RequestMapping(value = "/getInventoryType",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getInventoryType(@RequestParam(value = "storeid") Integer storeid) {
		List<InventoryType> inventoryTypes = null;
		try {
			inventoryTypes = inventoryService.getInventoryType(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryType>>() {
		}.getType();
		String json = gson.toJson(inventoryTypes, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getInventoryItems",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getInventoryItems(
			@RequestParam(value = "storeid") Integer storeid) {
		List<InventoryItems> inventoryItems = null;
		try {
			inventoryItems = inventoryService.getInventoryItems(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryItems>>() {
		}.getType();
		String json = gson.toJson(inventoryItems, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getInventoryItemsByName",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getInventoryItemsByName(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "name") String name) {
		List<InventoryItems> inventoryItems = null;
		try {
			inventoryItems = inventoryService.getInventoryItemsByName(storeid,
					name);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryItems>>() {
		}.getType();
		String json = gson.toJson(inventoryItems, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getInventoryItemsByCode",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getInventoryItemsByCode(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "code") String code) {
		InventoryItems inventoryItems = null;
		try {
			inventoryItems = inventoryService.getInventoryItemsByCode(storeid,
					code);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(inventoryItems, InventoryItems.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getVendors",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getVendors(@RequestParam(value = "storeid") Integer storeid) {
		List<InventoryVendor> inventoryVendors = null;
		try {
			inventoryVendors = inventoryService.getVendors(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryVendor>>() {
		}.getType();
		String json = gson.toJson(inventoryVendors, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getPurchaseOrdersByDate",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getPurchaseOrdersByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<InventoryPurchaseOrder> inventoryPoOrders = null;
		try {
			inventoryPoOrders = inventoryService.getPurchaseOrdersByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryPurchaseOrder>>() {
		}.getType();
		String json = gson.toJson(inventoryPoOrders, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getPurchaseOrdersById",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getPurchaseOrdersById(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "id") Integer id) {
		List<InventoryPurchaseOrder> inventoryPoOrders = null;
		try {
			inventoryPoOrders = inventoryService.getPurchaseOrdersById(storeid,
					id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryPurchaseOrder>>() {
		}.getType();
		String json = gson.toJson(inventoryPoOrders, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/createPurchaseOrder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createPO(@RequestBody InventoryPurchaseOrder purchaseOrder) {
		int purchaseOrderId = 0;
		try {
			purchaseOrderId = inventoryService.createPO(purchaseOrder);
		} catch (Exception x) {
			x.printStackTrace();
		}
		return String.valueOf(purchaseOrderId);
	}

	@Override
	@RequestMapping(value = "/getInventoryStockIn", method = RequestMethod.GET, produces = "text/plain")
	public String getInventoryStockIn(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<InventoryStockIn> inventoryStockIns = null;
		try {
			inventoryStockIns = inventoryService.getInventoryStockIn(storeid,
					date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryStockIn>>() {
		}.getType();
		String json = gson.toJson(inventoryStockIns, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getInventoryStockInById", method = RequestMethod.GET, produces = "text/plain")
	public String getInventoryStockInById(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "id") Integer id) {
		List<InventoryStockIn> inventoryStockIns = null;
		try {
			inventoryStockIns = inventoryService.getInventoryStockInById(
					storeid, id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryStockIn>>() {
		}.getType();
		String json = gson.toJson(inventoryStockIns, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getInventoryStockOut", method = RequestMethod.GET, produces = "text/plain")
	public String getInventoryStockOut(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<InventoryStockOut> inventoryStockOut = null;
		try {
			inventoryStockOut = inventoryService.getInventoryStockOut(storeid,
					date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryStockOut>>() {
		}.getType();
		String json = gson.toJson(inventoryStockOut, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/approvePO", method = RequestMethod.GET, produces = "text/plain")
	public String approvePO(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "approvedBy") String approvedBy,
			@RequestParam(value = "updatedBy") String updatedBy,
			@RequestParam(value = "updatedDate") String updatedDate) {
		String status = "";
		try {
			status = inventoryService.approvePO(id, approvedBy, updatedBy,
					updatedDate);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/approveRawStockOut", method = RequestMethod.GET, produces = "text/plain")
	public String approveRawStockOut(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "approvedBy") String approvedBy,
			@RequestParam(value = "updatedBy") String updatedBy,
			@RequestParam(value = "updatedDate") String updatedDate) {
		String status = "";
		try {
			status = inventoryService.approveRawStockOut(id, storeId,
					approvedBy, updatedBy, updatedDate);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/approveFgStockIn", method = RequestMethod.GET, produces = "text/plain")
	public String approveFgStockIn(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "by") String by) {
		String status = "";
		try {
			status = inventoryService.approveFgStockIn(storeId, id, by);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/closeStockIn",
	method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String closeStockIn(InventoryStockIn inventoryStockIn) {
		String status = "";
		try {
			status = inventoryService.closeStockIn(inventoryStockIn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updatePO", method = RequestMethod.GET, produces = "text/plain")
	public String updatePO(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "poBy") String poBy,
			@RequestParam(value = "updatedBy") String updatedBy,
			@RequestParam(value = "updatedDate") String updatedDate) {
		String status = "";
		try {
			status = inventoryService
					.updatePO(id, poBy, updatedBy, updatedDate);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updatePOItem",
	method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updatePOItem(@RequestBody InventoryPurchaseOrder purchaseOrder) {
		int purchaseOrderId = 0;
		try {
			purchaseOrderId = inventoryService.updatePOItem(purchaseOrder);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(purchaseOrderId);
	}

	@Override
	@RequestMapping(value = "/updateStockInItem",
	method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateStockInItem(@RequestBody InventoryStockIn inventoryStockIn) {
		int invStckInId = 0;
		try {
			invStckInId = inventoryService.updateStockInItem(inventoryStockIn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(invStckInId);
	}

	@Override
	@RequestMapping(value = "/updateEachStockInItem",
	method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateEachStockInItem(@RequestBody InventoryStockInItem inventoryStockInItem) {
		String messaString = "";
		try {
			messaString = inventoryService
					.updateEachStockInItem(inventoryStockInItem);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/updateEachStockOutItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateEachRawStockOutItem(@RequestBody InventoryStockOutItem inventoryStockOutItem) {
		String messaString = "";
		try {
			messaString = inventoryService
					.updateEachRawStockOutItem(inventoryStockOutItem);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/updateEachPOItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateEachPOItem(@RequestBody InventoryPurchaseOrderItem inventoryPurchaseOrderItem) {
		String messaString = "";
		try {
			messaString = inventoryService
					.updateEachPOItem(inventoryPurchaseOrderItem);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/updateFgStockInItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateFgStockInItem(@RequestBody FgStockInItemsChild fgStockInItemsChild) {
		String messaString = "";
		try {
			messaString = inventoryService
					.updateFgStockInItem(fgStockInItemsChild);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/deletePOItem", method = RequestMethod.GET, produces = "text/plain")
	public String deletePOItem(@RequestParam(value = "poId") Integer poId,
			@RequestParam(value = "poItemId") Integer poItemId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deletePOItem(poId, poItemId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}
	
	//added on 23.04.2018
	@Override
	@RequestMapping(value = "/deletePO", method = RequestMethod.GET, produces = "text/plain")
	public String deletePO(@RequestParam(value = "poId") Integer poId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deletePO(poId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/deleteStockInItem",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String deleteStockInItem(
			@RequestParam(value = "stockInId") Integer stockInId,
			@RequestParam(value = "stockInItemId") Integer stockInItemId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteStockInItem(stockInId,
					stockInItemId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}
	
	//added on 30.04.2018
	@Override
	@RequestMapping(value = "/deleteStockIn",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String deleteStockIn(
			@RequestParam(value = "stockInId") Integer stockInId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteStockIn(stockInId,
					 storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/deleteStockOutItem",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String deleteStockOutItem(
			@RequestParam(value = "stockOutId") Integer stockOutId,
			@RequestParam(value = "stockOutItemId") Integer stockOutItemId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteStockOutItem(stockOutId,
					stockOutItemId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}
	
	//added on 16.01.2020
		@Override
		@RequestMapping(value = "/deleteStockOut",
		method = RequestMethod.GET,
		produces = "text/plain")
		public String deleteStockOut(
				@RequestParam(value = "stockOutId") Integer stockOutId,
				@RequestParam(value = "storeId") Integer storeId) {
			String message = "";
			try {
				message = inventoryService.deleteStockOut(stockOutId,
						 storeId);
			} catch (Exception x) {
				x.printStackTrace();
			}

			return message;
		}

	@Override
	@RequestMapping(value = "/createStockIn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createStockIn(@RequestBody InventoryStockIn inventoryStockIn) {
		int stockInId = 0;
		try {
			stockInId = inventoryService.createStockIn(inventoryStockIn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(stockInId);
	}

	@Override
	@RequestMapping(value = "/createFgStockIn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createFgStockIn(@RequestBody FgStockIn fgStockIn) {
		int fgStockInId = 0;
		try {
			fgStockInId = inventoryService.createFgStockIn(fgStockIn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(fgStockInId);
	}

	@Override
	@RequestMapping(value = "/createStockOut", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createStockOut(@RequestBody InventoryStockOut inventoryStockOut) {
		String stockOutId = "";
		try {
			stockOutId = inventoryService.createStockOut(inventoryStockOut);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return stockOutId;
	}

	@Override
	@RequestMapping(value = "/getInventoryStockOutById", method = RequestMethod.GET, produces = "text/plain")
	public String getInventoryStockOutById(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "id") Integer id) {
		List<InventoryStockOut> inventoryStockOuts = null;
		try {
			inventoryStockOuts = inventoryService.getInventoryStockOutById(
					storeid, id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryStockOut>>() {
		}.getType();
		String json = gson.toJson(inventoryStockOuts, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getCurrentStockByItem", method = RequestMethod.GET, produces = "text/plain")
	public String getCurrentStockByItem(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "itemId") Integer itemId) {
		List<ItemCurrentStock> itemCurrentStockList = null;
		try {
			itemCurrentStockList = inventoryService
					.getCurrentStockByItem(storeid, itemId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<ItemCurrentStock>>() {
		}.getType();
		String json = gson.toJson(itemCurrentStockList, type);
		return json;
	}

	@Override
	@RequestMapping(value = "/addVendor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addVendor(@RequestBody InventoryVendor inventoryVendor) {
		int vendorId = 0;
		try {
			vendorId = inventoryService.addVendor(inventoryVendor);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(vendorId);
	}

	@Override
	@RequestMapping(value = "/updateVendor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateVendor(@RequestBody InventoryVendor inventoryVendor) {
		String messaString = "";
		try {
			messaString = inventoryService.updateVendor(inventoryVendor);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/updateInvntryType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateInvntryType(@RequestBody InventoryType inventoryType) {
		String messaString = "";
		try {
			messaString = inventoryService.updateInvntryType(inventoryType);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/deleteVendor", method = RequestMethod.GET, produces = "text/plain")
	public String deleteVendor(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteVendor(id, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/deleteInvntryType", method = RequestMethod.GET, produces = "text/plain")
	public String deleteInvntryType(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteInvntryType(id, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/deleteInvntryTypeItem", method = RequestMethod.GET, produces = "text/plain")
	public String deleteInvntryTypeItem(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteInvntryTypeItem(id, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/addInvntoryType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addInvntoryType(@RequestBody InventoryType inventoryType) {
		int inventryTypeId = 0;
		try {
			inventryTypeId = inventoryService.addInvntoryType(inventoryType);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(inventryTypeId);
	}

	@Override
	@RequestMapping(value = "/addInventoryItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addNewInventoryItem(@RequestBody InventoryItems inventoryItems) {
		int itemId = 0;
		try {
			itemId = inventoryService.addNewInventoryItem(inventoryItems);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(itemId);
	}

	@Override
	@RequestMapping(value = "/updateInventoryItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateInventoryItem(@RequestBody InventoryItems inventoryItems) {
		String maeesage = "";
		try {
			maeesage = inventoryService.updateInventoryItem(inventoryItems);
		} catch (Exception x) {
			maeesage = "Updation Failed.";
			x.printStackTrace();
		}

		return maeesage;
	}

	// daily Purchase Order for day

	@Override
	@RequestMapping(value = "/reportInventoryPurchaseOrder/day", method = RequestMethod.GET)
	public void getTodaysPOReport(@RequestParam(value = "date") String date,
			@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		String totalShippingCharge = null;

		java.sql.Statement st1 = null;
		java.sql.ResultSet rs1 = null;
    InputStream is = null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();

			//dateformatter = new Dateformatter();
			//dateFormat = dateformatter.dateFormat(date);

			String selectSQLForTotalShipping = "SELECT sum(shipping_charge) as shipping_charge FROM restaurant.im_t_raw_purchase_order where store_id="
					+ storeId + " and date = '" + date + "' ";

			// totalShippingCharge =
			// String.valueOf(getTotalshippingCharge(dateFormat, storeId));

			st1 = connection.createStatement();
			rs1 = st1.executeQuery(selectSQLForTotalShipping);
			while (rs1.next()) {
				totalShippingCharge = rs1.getString("shipping_charge");

			}

			if (totalShippingCharge == null) {
				totalShippingCharge = "0.00";
			}

			/*
			 * if (totalShippingCharge == null) {
			 * 
			 * totalShippingCharge = "0.00"; }
			 */

			
			
			if((storeId)==59 || (storeId)==63){
			  baseFileName = "restaurant_purchaseOrdor_daily_tp";
				jasperFile = "restaurant_purchaseOrdor_daily_tp.jrxml";
			}
			else{
			  baseFileName = "restaurant_purchaseOrdor_daily";
			  jasperFile = "restaurant_purchaseOrdor_daily.jrxml";
			}
			
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("W_Date", date);
			// parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_TotalShipCh", totalShippingCharge);
			
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Report PO periodwise.
	@Override
	@RequestMapping(value = "/reportInventoryPO", method = RequestMethod.GET)
	public void getInventoryPOReport(@RequestParam(value = "type") String type,
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		String totalShippingChargePeriodwise = null;
    InputStream is = null;

		java.sql.Statement st = null;
		java.sql.ResultSet rs = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, Object> parameters = new HashMap<>();

			//dateformatter = new Dateformatter();
			//fromDateFormat = dateformatter.dateFormat(frmdate);
			//toDateFormat = dateformatter.dateFormat(todate);

			String selectSQLForTotalShipping = "SELECT sum(shipping_charge) as shipping_charge FROM restaurant.im_t_raw_purchase_order where store_id="
					+ storeId
					+ " and date between '"
					+ frmdate
					+ "' and '" + todate + "' ";
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForTotalShipping);
			while (rs.next()) {
				totalShippingChargePeriodwise = rs.getString("shipping_charge");

			}

			if (totalShippingChargePeriodwise == null) {
				totalShippingChargePeriodwise = "0.00";
			}

			// call the corresponding report file depending on the report type
			if (type.equalsIgnoreCase(CommonProerties.salesDaily)) {
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				parameters.put("W_TotalShipCh", totalShippingChargePeriodwise);
				
				
				if((storeId)==59 || (storeId)==63){
				  baseFileName = "restaurant_purchaseOrdor_peiod_daily_tp";
					jasperFile = "restaurant_purchaseOrdor_peiod_daily_tp.jrxml";
				}
				else{
				  baseFileName = "restaurant_purchaseOrdor_peiod_daily";
				  jasperFile = "restaurant_purchaseOrdor_peiod_daily.jrxml";
				}
				
			}
			else if (type.equalsIgnoreCase(CommonProerties.salesYearly)) {
				System.out.println("weekly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				parameters.put("W_TotalShipCh", totalShippingChargePeriodwise);
				
				baseFileName = "restaurant_purchaseOrdor_peiod_yearly.jrxml";
				jasperFile = "restaurant_purchaseOrdor_peiod_yearly.jrxml";
			}

			else if (type.equalsIgnoreCase(CommonProerties.salesMonthly)) {
				System.out.println("monthly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				parameters.put("W_TotalShipCh", totalShippingChargePeriodwise);
				
				baseFileName = "restaurant_purchaseOrdor_peiod_monthly";
				jasperFile = "restaurant_purchaseOrdor_peiod_monthly.jrxml";
			}

			// call method to generate pdf
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
		  try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
		}
	}

	//inventory raw stock in register
	@Override
	@RequestMapping(value = "/getRawStockinRegister", method = RequestMethod.GET)
	public void getRawStockinRegister(
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "vendorId") Integer vendorId,
			@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
        InputStream is = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "raw_purchase_register";
			jasperFile = "raw_purchase_register.jrxml";
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_startDate", frmdate);
			parameters.put("W_endDate", todate);
			parameters.put("W_storeID",storeId);
			parameters.put("W_distributorID",vendorId);
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	//added on 01.08.2019
	@Override
	@RequestMapping(value = "/getRawStockoutRegister", method = RequestMethod.GET)
	public void getRawStockoutRegister(
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
        InputStream is = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "raw_stockout_register";
			jasperFile = "raw_stockout_register.jrxml";
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_startDate", frmdate);
			parameters.put("W_endDate", todate);
			parameters.put("W_storeID",storeId);
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	@Override
	@RequestMapping(value = "/reportEDPById", method = RequestMethod.GET)
	public void getEdpReportById(
			@RequestParam(value = "edpId") String edpId,
			@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
    InputStream is = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "restaurant_inventory_edp_by_id";
			jasperFile = "restaurant_inventory_edp_by_id.jrxml";
			
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_EdpId", edpId);
			parameters.put("W_StoreId", String.valueOf(storeId));
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	@Override
	@RequestMapping(value = "/reportPOById", method = RequestMethod.GET)
	public void getPoReportById(
			@RequestParam(value = "poId") Integer poId,
			@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "restaurant_inventory_po_by_id";
			jasperFile = "restaurant_inventory_po_by_id.jrxml";
			
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_ReqId", poId);
			parameters.put("W_StoreId", String.valueOf(storeId));
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	

	//Raw Stock Status Report daywise based on EDP
	@Override
	@RequestMapping(value = "/reportRawStockStatusByEdp/day", method = RequestMethod.GET)
	public void getRawStockStatusReportByEdp(
			@RequestParam(value = "date") String date,
			@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			Date dateStandard=formatter.parse(date);
			String date1=sdf.format(dateStandard);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			
			
			baseFileName = "restaurant_raw_stock_status_daywise";
			jasperFile = "restaurant_raw_stock_status_daywise.jrxml";
			
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_DateStandard", date);
			parameters.put("W_Date", date1);
			// parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}

	// Report for raw stock in summary.
	@Override
	@RequestMapping(value = "/getRawStockinSummary", method = RequestMethod.GET)
	public void getRawStockinSummary(
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "vendorId") Integer vendorId,
			@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_startDate", frmdate);
			parameters.put("W_endDate", todate);
			parameters.put("W_storeID",storeId);
			parameters.put("W_distributorID",vendorId);
				
			baseFileName = "raw_purchase_summary";
			jasperFile = "raw_purchase_summary.jrxml";

			// call method to generate pdf
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	//added on 01.08.2019
	@Override
	@RequestMapping(value = "/getRawStockoutSummary", method = RequestMethod.GET)
	public void getRawStockoutSummary(
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		InputStream is = null;
		String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_startDate", frmdate);
			parameters.put("W_endDate", todate);
			parameters.put("W_storeID",storeId);
				
			baseFileName = "raw_stockout_summary";
			jasperFile = "raw_stockout_summary.jrxml";

			// call method to generate pdf
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}

	// daily inventory stock

	@Override
	@RequestMapping(value = "/reportGetCurrentStock", method = RequestMethod.GET)
	public void getCurrentStock(@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
      @RequestParam(value = "curDate") String curDate,@RequestParam(value = "itemId") Integer itemId,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			
			if((storeId)==59 || (storeId)==63){
			  baseFileName = "restaurant_current stock_tp";
				jasperFile = "restaurant_current stock_tp.jrxml";
			}
			else{
			  baseFileName = "restaurant_current stock";
			  jasperFile = "restaurant_current stock.jrxml";
			}
			Map<String, Object> parameters = new HashMap<>();
			// parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_Date", curDate);
			parameters.put("W_ItemId", String.valueOf(itemId));
			
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	@Override
	@RequestMapping(value = "/reportGstInOut", method = RequestMethod.GET)
	public void getGstInOutReportPeriodWise(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			
			baseFileName = "restaurant_gst";
			jasperFile = "restaurant_gst.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			
			// parameters.put("W_EndDate", todate);
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	//added on 23.05.2018
	@Override
	@RequestMapping(value = "/reportPLsatement", method = RequestMethod.GET)
	public void getPLstatementReport(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			
			baseFileName = "restaurant_pl_statement";
			jasperFile = "restaurant_pl_statement.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			
			// parameters.put("W_EndDate", todate);
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	//added on 05.07.2018
		@Override
		@RequestMapping(value = "/reportVendorPayment", method = RequestMethod.GET)
		public void getVendorPaymentReport(
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "vendorId") Integer vendorId,
	            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
	    InputStream is = null;
	    String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();

				FacesContext context = FacesContext.getCurrentInstance();
				
				baseFileName = "vendor_payment_details";
				jasperFile = "vendor_payment_details.jrxml";
				Map<String, Object> parameters = new HashMap<>();
				
				// parameters.put("W_EndDate", todate);
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				parameters.put("W_VendorId", String.valueOf(vendorId));
				
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}

	/*
	 * // method to get total shipping charge for PO daywise
	 * 
	 * public double getTotalshippingCharge(String date, String storeId) {
	 * 
	 * double totalShipping = 0.00; EntityManager em = null;
	 * 
	 * try {
	 * 
	 * EntityManagerFactory entityManagerFactory; entityManagerFactory =
	 * PersistenceListener.getEntityManager(); em =
	 * entityManagerFactory.createEntityManager();
	 * 
	 * Query qry = em.createQuery(
	 * "SELECT sum(i.shippingCharge) FROM InventoryPurchaseOrder i WHERE i.storeId=:storeid and i.date='"
	 * + date + "'"); qry.setParameter("storeid", storeId);
	 * //qry.setParameter("date", date); totalShipping = (double)
	 * qry.getSingleResult(); } catch (Exception e) { e.printStackTrace(); }
	 * finally { em.close(); } return totalShipping;
	 * 
	 * }
	 */

	// method to get total shipping charge for PO daywise

	/*
	 * public double getTotalshippingChargePeriodwise(String fromdate, String
	 * todate, String storeId) { Connection connection = null; double
	 * totalShipping = 0.00; EntityManager em = null;
	 * 
	 * try {
	 * 
	 * 
	 * Query qry = em.createQuery(
	 * "SELECT sum(i.shippingCharge)  FROM InventoryPurchaseOrder i WHERE i.storeId=:storeid and i.date BETWEEN '"
	 * + fromdate + "' and '" + todate + "'"); connection =
	 * sessionFactory.getConnectionProvider().getConnection(); st =
	 * connection.createStatement(); rs =
	 * st.executeQuery(selectSQLForCustomerDisc); while (rs.next()) {
	 * totalCustDiscount = rs.getString("total_cust_disc");
	 * 
	 * } //qry.setParameter("fromdate", fromdate); //qry.setParameter("todate",
	 * todate); totalShipping=(double)qry.getSingleResult();
	 * System.out.println("tot::::::"+(double)qry.getSingleResult()); } catch
	 * (Exception e) { e.printStackTrace(); } finally { em.close(); } return
	 * totalShipping;
	 * 
	 * }
	 */

	@Override
	@RequestMapping(value = "/getRecipeIngredients", method = RequestMethod.GET, produces = "text/plain")
	public String getRecipeIngredients(
			@RequestParam(value = "storeid") Integer storeid) {
		List<RecipeIngredient> recipeIngredients = null;
		try {
			recipeIngredients = inventoryService.getRecipeIngredients(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RecipeIngredient>>() {
		}.getType();
		String json = gson.toJson(recipeIngredients, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getEstimateTypes", method = RequestMethod.GET, produces = "text/plain")
	public String getEstimateTypes(@RequestParam(value = "storeid") Integer storeid) {
		List<EstimateType> estimateType = null;
		try {
			estimateType = inventoryService.getEstimateTypes(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EstimateType>>() {
		}.getType();
		String json = gson.toJson(estimateType, type);
		//System.out.println(json);
		return json;
	}
	
	//added on 02.05.2018
	@Override
	@RequestMapping(value = "/getCreditInfoByVendor",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getCreditInfoByVendor(
			@RequestParam(value = "vendorId") Integer vendorId,
			@RequestParam(value = "storeId") Integer storeId) {
		List<InventoryInvoicePayment> invoicePayments = null;
		try {
			invoicePayments = inventoryService.getCreditInfoByVendor(vendorId,
					storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryInvoicePayment>>() {
		}.getType();
		String json = gson.toJson(invoicePayments, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/invoicePayment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String invoicePayment(@RequestBody InventoryInvoicePayment payment) {
		String status = "";
		try {
			status = inventoryService.invoicePayment(payment);
		} catch (Exception x) {
			status = "failure";
			x.printStackTrace();
		}

		return status;
	}
	
	//added on 06.06.2018
	@Override
	@RequestMapping(value = "/getReturnCauses", method = RequestMethod.GET, produces = "text/plain")
	public String getReturnCauses(@RequestParam(value = "storeId") Integer storeId) {
		//System.out.println("/getReturnCauses storeId = "+storeId);
		List<ReturnTypes> returnTypes = null;
		try {
			returnTypes = inventoryService.getReturnCauses(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<ReturnTypes>>() {
		}.getType();
		String json = gson.toJson(returnTypes, type);
		//System.out.println("/getReturnCauses json = "+json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/createSalesReturn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createSalesReturn(@RequestBody SalesReturn salesReturn) {
		int salereturnId = 0;
		try {
			salereturnId = inventoryService.createSalesReturn(salesReturn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(salereturnId);
	}
	
	@Override
	@RequestMapping(value = "/getSalesReturn", method = RequestMethod.GET, produces = "text/plain")
	public String getSalesReturn(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId) {
		SalesReturn salesReturn = null;
		try {
			salesReturn = inventoryService.getSalesReturn(id, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(salesReturn, SalesReturn.class);
		return json;
	}
	
	//added on 14.06.2018
	@Override
	@RequestMapping(value = "/getSalesReturnByOrderId", method = RequestMethod.GET, produces = "text/plain")
	public String getSalesReturnByOrderId(@RequestParam(value = "orderId") Integer orderId,
			@RequestParam(value = "storeId") Integer storeId) {
		SalesReturn salesReturn = null;
		try {
			salesReturn = inventoryService.getSalesReturnByOrderId(orderId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(salesReturn, SalesReturn.class);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/adjustReturnSales", method = RequestMethod.GET, produces = "text/plain")
	public String adjustReturnSales(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId) {
		String status = "";
		try {
			status = inventoryService.adjustReturnSales(id, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}
	
	//added on 14.06.2018
	@Override
	@RequestMapping(value = "/approveSalesReturn", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String approveSalesReturn(@RequestBody SalesReturn salesReturn) {
		String status = "";
		try {
			status = inventoryService.approveSalesReturn(salesReturn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}
	
	@Override
	@RequestMapping(value = "/deleteSalesReturn", method = RequestMethod.GET, produces = "text/plain")
	public String deleteSalesReturn(
			@RequestParam(value = "returnId") Integer returnId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteSalesReturn(returnId,
					 storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}
	
  @Override
  @RequestMapping(value = "/printRefundBill", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
  public String printRefundBill(@RequestParam(value = "orderid") String orderid,
      @RequestParam(value = "storeid") Integer storeId) {

    String status = "";
    // MobPrintBill mobPrintBill = null;
    try {
      System.out.println("Print refund bill started...");
      status = inventoryService.printRefundBill(orderid, storeId);

    } catch (Exception x) {
      x.printStackTrace();
      status = "Failure";
    }
    System.out.println("Refund Print done..." + status);
    return status;
  }
	
	/*START FOR ACCOUNT SECTION*/
	
	@RequestMapping(value = "/getAllAccountType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String getAllAccountType(@RequestBody CommonResultSetMapper mapper) {
		List<AccountTypeDTO> list = null;

		try {

			list = inventoryService.getAllAccountType(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AccountTypeDTO>>() {
		}.getType();
		String json = gson.toJson(list, type);

		return json;
	}
	
	@RequestMapping(value = "/getAllAccountGroup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String getAllAccountGroup(@RequestBody CommonResultSetMapper mapper) {
		List<AccountGroupDTO> list = null;

		try {

			list = inventoryService.getAllAccountGroup(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AccountGroupDTO>>() {
		}.getType();
		String json = gson.toJson(list, type);

		return json;
	}
	
	
	@RequestMapping(value = "/updateAccountGroup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateAccountGroup(@RequestBody AccountGroupDTO accountGroupDTO) {
		// String status = "";
		System.out.println("updateAccountGroup accountGroupDTO = "+accountGroupDTO);
		ResponseObj responseObj = new ResponseObj();
		try {
			responseObj = inventoryService.updateAccountGroup(accountGroupDTO);

		} catch (Exception x) {
			x.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(responseObj, ResponseObj.class);
		return json;
	}
	
	@RequestMapping(value = "/deleteAccountGroup", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String deleteAccountGroup(@RequestBody CommonResultSetMapper mapper) {
		ResponseObj responseObj = new ResponseObj();
		try {
			responseObj = inventoryService.deleteAccountGroup(mapper);

		} catch (Exception x) {
			x.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj
					.setReason("deleteAccountGroup not deleted successfully.");
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(responseObj, ResponseObj.class);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/createAccountGroup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createAccountGroup(@RequestBody AccountGroupDTO accountGroupDTO) {
		// String status = "";
		ResponseObj responseObj = new ResponseObj();
		try {
			responseObj = inventoryService.createAccountGroup(accountGroupDTO);

		} catch (Exception x) {
			x.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(responseObj, ResponseObj.class);
		return json;
	}
	
	@RequestMapping(value = "/getAllAccounts", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String getAllAccounts(@RequestBody CommonResultSetMapper mapper) {
		System.out.println("getAllAccounts mapper = "+mapper);
		List<AccountDTO> accnts = null;

		try {

			accnts = inventoryService.getAllAccounts(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AccountDTO>>() {
		}.getType();
		String json = gson.toJson(accnts, type);

		return json;
	}
	
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createAccount(@RequestBody AccountMaster master) {
		System.out.println("/createAccount json = "+master);
		ResponseObj responseObj = new ResponseObj();
		try {
			responseObj = inventoryService.createAccount(master);
		} catch (Exception x) {
			x.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(responseObj, ResponseObj.class);
		return json;
	}
	
	@RequestMapping(value = "/getDuplicateAccounts", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getDuplicateAccounts(@RequestBody CommonResultSetMapper mapper) {
		//List<AccountDTO> accnts = null;
		ResponseObj responseObj = null;
		System.out.println("getDuplicateAccounts mapper = "+mapper);
		try {

			responseObj = inventoryService.getDuplicateAccounts(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//java.lang.reflect.Type type = new TypeToken<List<ResponseObj>>() {}.getType();
		String json = gson.toJson(responseObj, ResponseObj.class);

		return json;
	}
	
	@RequestMapping(value = "/updateAccounts", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String updateAccounts(@RequestBody AccountMaster accountMaster) {
		//List<AccountDTO> accnts = null;
		ResponseObj responseObj = null;
		System.out.println("update accounts mapper = "+accountMaster);
		try {

			responseObj = inventoryService.updateAccounts(accountMaster);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//java.lang.reflect.Type type = new TypeToken<List<ResponseObj>>() {}.getType();
		String json = gson.toJson(responseObj, ResponseObj.class);
		System.out.println("returning status "+json);
		return json;
	}
	
	@RequestMapping(value = "/deleteAccounts", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String deleteAccounts(@RequestBody CommonResultSetMapper mapper) {
		//List<AccountDTO> accnts = null;
		ResponseObj responseObj = null;
		System.out.println("delete accounts mapper = "+mapper);
		try {

			responseObj = inventoryService.deleteAccounts(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//java.lang.reflect.Type type = new TypeToken<List<ResponseObj>>() {}.getType();
		String json = gson.toJson(responseObj, ResponseObj.class);

		return json;
	}
	
	@RequestMapping(value = "/getChartOfAccount", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getChartOfAccount(@RequestBody CommonResultSetMapper mapper) {
		List<ChartOfAccountDTO> accnts = null;
		//ResponseObj responseObj = null;
		//CommonResultSetMapper mapper = new CommonResultSetMapper(); 
		System.out.println("getChartOfAccount mapper = "+mapper);
		try {

			accnts = inventoryService.getChartOfAccount(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<ChartOfAccountDTO>>() {
		}.getType();
		String json = gson.toJson(accnts, type);
		return json;
	}
	
	
	@RequestMapping(value = "/getJournalTypeByQS", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getJournalTypeByQS(@RequestBody CommonResultSetMapper mapper) {
		List<ParaJournalTypeMaster> pjtms = null;
		//ResponseObj responseObj = null;
		System.out.println("getJournalTypeByQS mapper = "+mapper);
		try {

			pjtms = inventoryService.getJournalTypeByQS(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<ParaJournalTypeMaster>>() {
		}.getType();
		String json = gson.toJson(pjtms, type);
		System.out.println("returning status");
		return json;
	}
	
	@RequestMapping(value = "/addJournal", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addJournal(@RequestBody JournalListDTO journallistDTO) {
		// String status = "";
		System.out.println("add journal = "+journallistDTO);
		ResponseObj responseObj = new ResponseObj();
//		String responseObj="";
		String stat = "";
		try {
			stat = inventoryService.addJournal(journallistDTO);
			if(!stat.equals("0"))
			{
				responseObj.setStatus(ReturnConstant.SUCCESS);
				responseObj.setId(Integer.parseInt(stat));
				responseObj.setReason("Journal added successfully");
			}

		} catch (Exception x) {
			x.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not added successfully.");
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(responseObj, ResponseObj.class);
		return json;
		
		//return responseObj;
	}
	
	@RequestMapping(value = "/getAccountsAutocomplete", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getAccountsAutocomplete(@RequestBody CommonResultSetMapper mapper) {
		List<AccountDTO> accnts = null;
		//ResponseObj responseObj = null;
		System.out.println("getAccountsAutocomplete mapper = "+mapper);
		try {

			accnts = inventoryService.getAccountsAutocomplete(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AccountDTO>>() {
		}.getType();
		String json = gson.toJson(accnts, type);
		//System.out.println("returning status");
		return json;
	}
	
	@RequestMapping(value = "/deleteJournal", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String deleteJournal(@RequestBody CommonResultSetMapper mapper) {
		//List<AccountDTO> accnts = null;
		ResponseObj responseObj = null;
		System.out.println("delete journal mapper = "+mapper);
		try {

			responseObj = inventoryService.deleteJournal(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//java.lang.reflect.Type type = new TypeToken<List<ResponseObj>>() {}.getType();
		String json = gson.toJson(responseObj, ResponseObj.class);

		return json;
	}
	
	@RequestMapping(value = "/getAllJournalByType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getAllJournalByType(@RequestBody CommonResultSetMapper mapper) {
		List<JournalDTO> journals = null;
		//ResponseObj responseObj = null;
		System.out.println("getAllJournalByType mapper = "+mapper);
		try {

			journals = inventoryService.getAllJournalByType(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<JournalDTO>>() {
		}.getType();
		String json = gson.toJson(journals, type);
		System.out.println("returning status");
		return json;
	}
	
	@RequestMapping(value = "/getJournalById", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getJournalById(@RequestBody CommonResultSetMapper mapper) {
		List<JournalListDTO> journals = null;
		//ResponseObj responseObj = null;
		System.out.println("getJournalById mapper = "+mapper);
		try {

			journals = inventoryService.getJournalById(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//java.lang.reflect.Type type = new TypeToken<List<JournalListDTO>>() {}.getType();
		String json = gson.toJson(journals.get(0), JournalListDTO.class);
		//System.out.println("returning status");
		return json;
	}
	
	@RequestMapping(value = "/updateJournal", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateJournal(@RequestBody JournalListDTO journallistDTO) {
		// String status = "";
		System.out.println("update journal = "+journallistDTO);
		ResponseObj responseObj = new ResponseObj();
		//String responseObj="";
		//ResponseObj responseObj = new ResponseObj();
		String stat = "";
		try {
			stat = inventoryService.addJournal(journallistDTO);
			if(!stat.equals("0"))
			{
				responseObj.setStatus(ReturnConstant.SUCCESS);
				responseObj.setId(Integer.parseInt(stat));
				responseObj.setReason("Journal updated successfully");
			}

		} catch (Exception x) {
			x.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not updated successfully.");
		}
		System.out.println("update journal status = "+responseObj);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(responseObj, ResponseObj.class);
		return json;
		
//		return responseObj;
	}
	
	@RequestMapping(value = "/getAccountsAutocompleteByGroup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getAccountsAutocompleteByGroup(@RequestBody CommonResultSetMapper mapper) {
		List<AccountDTO> accnts = null;
		//ResponseObj responseObj = null;
		System.out.println("getAccountsAutocompleteByGroup mapper = "+mapper);
		try {

			accnts = inventoryService.getAccountsAutocompleteByGroup(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AccountDTO>>() {
		}.getType();
		String json = gson.toJson(accnts, type);
		//System.out.println("returning status");
		return json;
	}
	
	@RequestMapping(value = "/getLedgerDetailsByCode", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String getLedgerDetailsByCode(@RequestBody CommonResultSetMapper mapper) {
		System.out.println("getLedgerDetailsByCode Mapper = "+mapper);
		List<AccountDTO> list = null;
		
		try {

			list = inventoryService.getLedgerDetailsByCode(mapper);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AccountDTO>>() {
		}.getType();
		String json = gson.toJson(list, type);

		return json;
	}
	
	/*END FOR ACCOUNT SECTION*/
	
	
	@Override
	@RequestMapping(value = "/reportGRNById", method = RequestMethod.GET)
	public void getGRNReportById(
			@RequestParam(value = "stkId") String stkId,
			@RequestParam(value = "storeId") String storeId,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			fileName = "restaurant_grn_details.pdf";
			jasperFile = "restaurant_grn_details.jrxml";
			
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StkId", stkId);
			parameters.put("W_StoreId", storeId);
			generatePDF(context, request, response, fileName, parameters,
					connection, jasperFile);

			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/"
					+ fileName);
			InputStream is = new FileInputStream(file);
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ fileName + "\"");
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//added on 09.07.2018
	@Override
	@RequestMapping(value = "/createPurchaseReturn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createPurchaseReturn(@RequestBody InventoryReturn inventoryReturn) {
		int returnId = 0;
		try {
			returnId = inventoryService.createPurchaseReturn(inventoryReturn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(returnId);
	}
	
	@Override
	@RequestMapping(value = "/updatePurchaseReturn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updatePurchaseReturn(@RequestBody InventoryReturn inventoryReturn) {
		int returnId = 0;
		try {
			returnId = inventoryService.updatePurchaseReturn(inventoryReturn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(returnId);
	}
	
	@Override
	@RequestMapping(value = "/deletePurchaseReturn", method = RequestMethod.GET, produces = "text/plain")
	public String deletePurchaseReturn(
			@RequestParam(value = "returnId") Integer returnId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deletePurchaseReturn(returnId,
					 storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}
	
	@Override
	@RequestMapping(value = "/approvePurchaseReturn", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String approvePurchaseReturn(@RequestBody InventoryReturn inventoryReturn) {
		String status = "";
		try {
			status = inventoryService.approvePurchaseReturn(inventoryReturn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}
	
	@Override
	@RequestMapping(value = "/getPurchaseReturn", method = RequestMethod.GET, produces = "text/plain")
	public String getPurchaseReturn(
			@RequestParam(value = "storeId") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<InventoryReturn> inventoryReturns = null;
		try {
			inventoryReturns = inventoryService.getPurchaseReturn(storeid,date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryReturn>>() {
		}.getType();
		String json = gson.toJson(inventoryReturns, type);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getPurchaseReturnById",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getPurchaseReturnById(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId) {
		InventoryReturn inventoryReturn = null;
		try {
			inventoryReturn = inventoryService.getPurchaseReturnById(id, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(inventoryReturn, InventoryReturn.class);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getSimpleFgStockInByDate", method = RequestMethod.GET, produces = "text/plain")
	public String getSimpleFgStockInByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<FgStockIn> simpleFgStockIns = null;
		try {
			simpleFgStockIns = inventoryService.getSimpleFgStockInByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<FgStockIn>>() {
		}.getType();
		String json = gson.toJson(simpleFgStockIns, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getFgCurrentStockByItem", method = RequestMethod.GET, produces = "text/plain")
	public String getFgCurrentStockByItem(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "itemId") Integer itemId) {
		List<FgItemCurrentStock> fgItemCurrentStockList = null;
		try {
			fgItemCurrentStockList = inventoryService
					.getFgCurrentStockByItem(storeid, itemId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<FgItemCurrentStock>>() {
		}.getType();
		String json = gson.toJson(fgItemCurrentStockList, type);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getFgStockInById", method = RequestMethod.GET, produces = "text/plain")
	public String getFgStockInById(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "id") Integer id) {
		FgStockIn fgStockIn = null;
		try {
			fgStockIn = inventoryService.getFgStockInById(
					storeid, id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<FgStockIn>() {
		}.getType();
		String json = gson.toJson(fgStockIn, type);
		//System.out.println(json);
		return json;
	}
	
	//added on 15.11.2019
	@Override
	@RequestMapping(value = "/getFgStockInItemsByItemId", method = RequestMethod.GET, produces = "text/plain")
	public String getFgStockInItemsByItemId(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "itemId") Integer itemId) {
		List<FgStockInItemsChild> fgStockInItems = null;
		try {
			fgStockInItems = inventoryService.getFgStockInItemsByItemId(storeid, itemId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<FgStockInItemsChild>>() {
		}.getType();
		String json = gson.toJson(fgStockInItems, type);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/updateFgStockIn",
	method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateFgStockIn(@RequestBody FgStockIn fgStockIn) {
		int fgStckInId = 0;
		try {
			fgStckInId = inventoryService.updateFgStockIn(fgStockIn);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return String.valueOf(fgStckInId);
	}
	
	@Override
	@RequestMapping(value = "/deleteFgStockIn",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String deleteFgStockIn(
			@RequestParam(value = "fgstockInId") Integer fgstockInId,
			@RequestParam(value = "storeId") Integer storeId) {
		String message = "";
		try {
			message = inventoryService.deleteFgStockIn(fgstockInId,
					 storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}
	
	//added on 17.06.2019
		@Override
		@RequestMapping(value = "/createFgReturn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
		public String createFgReturn(@RequestBody FgReturn fgReturn) {
			int fgreturnId = 0;
			try {
				fgreturnId = inventoryService.createFgReturn(fgReturn);
			} catch (Exception x) {
				x.printStackTrace();
			}

			return String.valueOf(fgreturnId);
		}
	
		@Override
		@RequestMapping(value = "/updateFgReturn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
		public String updateFgReturn(@RequestBody FgReturn fgReturn) {
			int fgreturnId = 0;
			try {
				fgreturnId = inventoryService.updateFgReturn(fgReturn);
			} catch (Exception x) {
				x.printStackTrace();
			}

			return String.valueOf(fgreturnId);
		}
		
		@Override
		@RequestMapping(value = "/deleteFgReturn", method = RequestMethod.GET, produces = "text/plain")
		public String deleteFgReturn(
				@RequestParam(value = "fgreturnId") Integer fgreturnId,
				@RequestParam(value = "storeId") Integer storeId) {
			String message = "";
			try {
				message = inventoryService.deleteFgReturn(fgreturnId,
						 storeId);
			} catch (Exception x) {
				x.printStackTrace();
			}

			return message;
		}
		
		@Override
		@RequestMapping(value = "/approveFgReturn", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
		public String approveFgReturn(@RequestBody FgReturn fgReturn) {
			String status = "";
			try {
				status = inventoryService.approveFgReturn(fgReturn);
			} catch (Exception x) {
				x.printStackTrace();
			}

			return status;
		}
		
		@Override
		@RequestMapping(value = "/getFgReturn", method = RequestMethod.GET, produces = "text/plain")
		public String getFgReturn(
				@RequestParam(value = "storeId") String storeid,
				@RequestParam(value = "date") String date) {
			List<FgReturn> fgReturns = null;
			try {
				fgReturns = inventoryService.getFgReturn(Integer.parseInt(storeid),date);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			java.lang.reflect.Type type = new TypeToken<List<FgReturn>>() {
			}.getType();
			String json = gson.toJson(fgReturns, type);
			return json;
		}
		
		@Override
		@RequestMapping(value = "/getFgReturnById",
		method = RequestMethod.GET,
		produces = "text/plain")
		public String getFgReturnById(@RequestParam(value = "id") String id,
				@RequestParam(value = "storeId") String storeId) {
			FgReturn fgReturn = null;
			try {
				fgReturn = inventoryService.getFgReturnById(Integer.parseInt(id), Integer.parseInt(storeId));
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			String json = gson.toJson(fgReturn, FgReturn.class);
			return json;
		}
		
		//added on 18.06.2019
		@Override
		@RequestMapping(value = "/getFgCreditInfoByVendor",method = RequestMethod.GET,produces = "text/plain")
		public String getFgCreditInfoByVendor(
				@RequestParam(value = "vendorId") Integer vendorId,
				@RequestParam(value = "storeId") Integer storeId) {
			List<FgInvoicePayment> invoicePayments = null;
			try {
				invoicePayments = inventoryService.getFgCreditInfoByVendor(vendorId,
						storeId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			java.lang.reflect.Type type = new TypeToken<List<FgInvoicePayment>>() {
			}.getType();
			String json = gson.toJson(invoicePayments, type);
			//System.out.println(json);
			return json;
		}
		
		@Override
		@RequestMapping(value = "/invoiceFgPayment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
		public String invoiceFgPayment(@RequestBody FgInvoicePayment payment) {
			String status = "";
			try {
				status = inventoryService.invoiceFgPayment(payment);
			} catch (Exception x) {
				status = "failure";
				x.printStackTrace();
			}

			return status;
		}
		// added on 30.07.2019
		@Override
		@RequestMapping(value = "/getRawItemQtytobeSoldOut",method = RequestMethod.GET,produces = "text/plain")
		public String getRawItemQtytobeSoldOut(
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "date") String date) {
			List<InventoryItems> inventoryItems = null;
			try {
				inventoryItems = inventoryService.getRawItemQtytobeSoldOut(storeId,
						date);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			java.lang.reflect.Type type = new TypeToken<List<InventoryItems>>() {
			}.getType();
			String json = gson.toJson(inventoryItems, type);
			//System.out.println(json);
			return json;
		}
		
		//added on 19.06.2019
		@RequestMapping(value = "/getRawStockinItemWise", method = RequestMethod.GET)
		public void getRawStockinItemWise(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "itemId") Integer itemId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "raw_item_wise_purchase";
				jasperFile = "raw_item_wise_purchase.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_itemID",itemId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		//added on 01.08.2019
		@RequestMapping(value = "/getRawStockoutItemWise", method = RequestMethod.GET)
		public void getRawStockoutItemWise(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "itemId") Integer itemId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "raw_stockout_itemwise";
				jasperFile = "raw_stockout_itemwise.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_itemID",itemId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getRawStockinReturnRegister", method = RequestMethod.GET)
		public void getRawStockinReturnRegister(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "vendorId") Integer vendorId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "raw_purchase_return_register";
				jasperFile = "raw_purchase_return_register.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_distributorID",vendorId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getRawStockinReturnSummary", method = RequestMethod.GET)
		public void getRawStockinReturnSummary(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "vendorId") Integer vendorId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
	    InputStream is = null;
	    String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();
				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_distributorID",vendorId);
					
				baseFileName = "raw_purchase_return_summary";
				jasperFile = "raw_purchase_return_summary.jrxml";

				// call method to generate pdf
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getRawStockinReturnItemWise", method = RequestMethod.GET)
		public void getRawStockinReturnItemWise(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "itemId") Integer itemId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "raw_item_wise_purchase_return";
				jasperFile = "raw_item_wise_purchase_return.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_itemID",itemId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getFgStockinRegister", method = RequestMethod.GET)
		public void getFgStockinRegister(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "vendorId") Integer vendorId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "fg_purchase_register";
				jasperFile = "fg_purchase_register.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_distributorID",vendorId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getFgStockinSummary", method = RequestMethod.GET)
		public void getFgStockinSummary(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "vendorId") Integer vendorId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
	    InputStream is = null;
	    String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();
				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_distributorID",vendorId);
					
				baseFileName = "fg_purchase_summary";
				jasperFile = "fg_purchase_summary.jrxml";

				// call method to generate pdf
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getFgStockinItemWise", method = RequestMethod.GET)
		public void getFgStockinItemWise(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "itemId") Integer itemId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "fg_item_wise_purchase";
				jasperFile = "fg_item_wise_purchase.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_itemID",itemId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getFgStockinReturnRegister", method = RequestMethod.GET)
		public void getFgStockinReturnRegister(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "vendorId") Integer vendorId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "fg_purchase_return_register";
				jasperFile = "fg_purchase_return_register.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_distributorID",vendorId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getFgStockinReturnSummary", method = RequestMethod.GET)
		public void getFgStockinReturnSummary(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "vendorId") Integer vendorId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
	    InputStream is = null;
	    String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();
				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_distributorID",vendorId);
					
				baseFileName = "fg_purchase_return_summary";
				jasperFile = "fg_purchase_return_summary.jrxml";

				// call method to generate pdf
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		@RequestMapping(value = "/getFgStockinReturnItemWise", method = RequestMethod.GET)
		public void getFgStockinReturnItemWise(
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "itemId") Integer itemId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
			String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
	        InputStream is = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "fg_item_wise_purchase_return";
				jasperFile = "fg_item_wise_purchase_return.jrxml";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID",storeId);
				parameters.put("W_itemID",itemId);
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
		
		//added on 24.06.2019
		@Override
		@RequestMapping(value = "/reportFgVendorPayment", method = RequestMethod.GET)
		public void getFgVendorPaymentReport(
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "todate") String todate,
				@RequestParam(value = "vendorId") Integer vendorId,
	            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) {
			
	    InputStream is = null;
	    String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();

				FacesContext context = FacesContext.getCurrentInstance();
				
				baseFileName = "fg_vendor_payment_details";
				jasperFile = "fg_vendor_payment_details.jrxml";
				Map<String, Object> parameters = new HashMap<>();
				
				// parameters.put("W_EndDate", todate);
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				parameters.put("W_VendorId", String.valueOf(vendorId));
				
				
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
	      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      try {
	        if(is != null) { is.close(); }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
		}
	
	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	// Method to generate pdf
  public void generatePDF(FacesContext context, HttpServletRequest request,
      HttpServletResponse response, String fileName,
      Map<String, Object> parameters, Connection connection,
      String jasperFile) throws FileNotFoundException, IOException {

    try {
      JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/jasper") + "/" + jasperFile);
      JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
      JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/jasper") + "/" + fileName);
    }
    catch (JRException e) {
      
      e.printStackTrace();
    } finally {

    }
  }

  // Method to generate Excel report
  public void generateXLSX(FacesContext context, HttpServletRequest request,
      HttpServletResponse response, String fileName,
      Map<String, Object> parameters, Connection connection,
      String jasperFile) throws FileNotFoundException, IOException {

    
    try {
      JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + jasperFile);
      JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
      
      File xlsxFile = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      JRXlsxExporter xlsxExporter = new JRXlsxExporter();

      xlsxExporter.setExporterInput(new SimpleExporterInput(print));
      xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxFile));
      
      SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
      configuration.setDetectCellType(true);
      configuration.setCollapseRowSpan(false);
      xlsxExporter.setConfiguration(configuration);
      
//      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.JASPER_PRINT, print);
//      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.OUTPUT_FILE, xlsxFile);
//      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
      
      xlsxExporter.exportReport(); //File is generated Correctly
    }
    catch (JRException e) {
      
      e.printStackTrace();
    } finally {

    }
  }
  
}

package com.botree.restaurantapp.webservice.impl;

import java.sql.Time;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.dao.LoginDAO;
import com.botree.restaurantapp.dao.LoginDAOImpl;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.TableMaster;
import com.botree.restaurantapp.model.dto.BookTableBean;
import com.botree.restaurantapp.model.dto.BookTableFormBean;
import com.botree.restaurantapp.model.dto.Table;
import com.botree.restaurantapp.service.TableBookingService;
import com.google.gson.Gson;

@Controller
@ResponseBody
@RequestMapping(value = "/tableBookingWSREST")
public class TableBookingWSREST {
	private final static Logger logger = LogManager.getLogger(TableBookingWSREST.class);
	
  @Autowired
	private TableBookingService tableBookingService;
  
	private static Gson gson = new Gson();

	@RequestMapping(value = "/isTablesAvailablePost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

	public String isTableAvailableWSPost(@RequestBody String param) {
		logger.debug("Post fired.");
		return this.isTableAvailableWS(param);
	}

	@RequestMapping(value = "/isTablesAvailableGet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String isTableAvailableWSGet(@RequestParam("param") String param) {
		logger.debug("Get fired.");
		return this.isTableAvailableWS(param);
	}

	private String isTableAvailableWS(String param) {
		logger.info("Req param: {}", param);
		BookTableFormBean bookTableFormBean = null;
		BookTableBean bookTableBean;

		String response = "[]";
		try {
			bookTableFormBean = gson.fromJson(param, BookTableFormBean.class);
			bookTableBean = new BookTableBean(bookTableFormBean);

			List<Table> tables = tableBookingService.getAvailableTables(
					bookTableBean.getBookingDateObjFrom(),
					bookTableBean.getBookingDateObjTo());
			tables = tableBookingService
					.getSuggestedTables(bookTableBean.getSeat(), tables);

			response = gson.toJson(tables);
			logger.debug("Response JSON: {}", response);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return response;
	}

	@RequestMapping(value = "/confirmBookingPost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String confirmBookingWSPost(@RequestBody String param) {
		logger.info("Req param: {}", param);
		BookTableFormBean bookTableFormBean = null;
		BookTableBean bookTableBean = null;

		List<Table> tables;
		String response = "{\"status\":false}";

		try {
			bookTableFormBean = gson.fromJson(param, BookTableFormBean.class);
			logger.debug(bookTableFormBean);
			bookTableBean = new BookTableBean(bookTableFormBean);

			tables = tableBookingService.getSuggestedTables(bookTableBean.getSeat(),
					tableBookingService.getAvailableTables(
							bookTableBean.getBookingDateObjFrom(),
							bookTableBean.getBookingDateObjTo()));
			bookTableFormBean.setTable(tables);

			LoginDAO loginDao = new LoginDAOImpl();
			Customer customer = new Customer();
			customer.setId(bookTableFormBean.getUserId());
			customer = loginDao.getUserByContantNo(customer);

			response = "{\"status\":"
					+ tableBookingService.confirmTableBooking(bookTableFormBean)
					+ ", 'userId':'" + bookTableFormBean.getUserId()
					+ "', 'userName':'" + customer.getName() + "', 'phoneNo':'"
					+ bookTableFormBean.getPhoneNo() + "', 'bookingDate':'"
					+ bookTableFormBean.getBookingDate() + "', 'bookingTime':'"
					+ new Time(bookTableBean.getBookingDateObjFrom().getTime())
					+ "' }";
			logger.debug("Response: {}", response);
			return response;
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return response;
	}

	@RequestMapping(value = "/getTableListPost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTableListByStoreIdPost(@RequestBody String storeId) {
		logger.debug("Post fired");
		return this.getTableListByStoreId(storeId);
	}

	// For mobile
	@RequestMapping(value = "/getTableListGet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTableListByStoreIdGet(@RequestParam("storeId") String storeId) {
		logger.debug("Get fired");
		return this.getTableListByStoreId(storeId);
	}

	@RequestMapping(value = "/updateTableStatus", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateTableStatus(@RequestBody TableMaster tableMaster) {

		String status = "";
		try {
			status = tableBookingService.updateTableStatus(tableMaster);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@RequestMapping(value = "/addTable", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addTable(@RequestBody TableMaster tableMaster) {
		String message = "";
		try {
			message = tableBookingService.addTable(tableMaster);
		} catch (Exception x) {
			message = "Table Creation Failed.";
			x.printStackTrace();
		}

		return message;
	}

	@RequestMapping(value = "/updateTable", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateTable(@RequestBody TableMaster tableMaster) {
	  logger.debug("com.sharobi.restaurantapp.webservice.impl.TableBookingWSREST.updateTable(TableMaster)++ " + tableMaster);
		String message = "";
		try {
			message = tableBookingService.updateTable(tableMaster);
		} catch (Exception x) {
			message = "Table Updation Failed.";
			x.printStackTrace();
		}

		return message;
	}

	@RequestMapping(value = "/updateTablePosition", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateTablePosition(@RequestBody TableMaster table) {
		String message = "";
		try {
			// get list
			List<TableMaster> tableList = table.getTableList();
			message = tableBookingService.updateTablePosition(tableList);
		} catch (Exception x) {
			message = "Table position Updation Failed.";
			x.printStackTrace();
		}

		return message;
	}

	@RequestMapping(value = "/setMultiOrder",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String setMultiOrder(@RequestParam(value = "id") String id,
			@RequestParam(value = "status") String status,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = tableBookingService.setMultiOrder(id, status, storeId);
		} catch (Exception x) {
			message = "Table Updation Failed.";
			x.printStackTrace();
		}

		return message;
	}

	@RequestMapping(value = "/deleteTable",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String deleteTable(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = tableBookingService.deleteTable(id, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}

	@RequestMapping(value = "/addMultipleTable",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String addMultipleTable(
			@RequestParam(value = "noOfTable") String noOfTable,
			@RequestParam(value = "capacity") String capacity,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = tableBookingService.addMultipleTable(noOfTable, capacity,storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return message;
	}

	private String getTableListByStoreId(String storeId) {
		logger.info("Req param: {}", storeId);

		String response = "[]";
		try {
			response = gson.toJson(tableBookingService.getTableList(Integer
					.parseInt(storeId)));
			return response;
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return response;
	}
}
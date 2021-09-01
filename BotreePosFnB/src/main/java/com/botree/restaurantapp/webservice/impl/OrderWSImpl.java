package com.botree.restaurantapp.webservice.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.botree.license.util.Constants;
import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.commonUtil.test.POITest;
import com.botree.restaurantapp.dao.StoreAddressDAO;
import com.botree.restaurantapp.dao.StoreAddressDAOImpl;
import com.botree.restaurantapp.dao.TableBookingDAOImpl;
import com.botree.restaurantapp.model.CommonBean;
import com.botree.restaurantapp.model.ListHolder;
import com.botree.restaurantapp.model.MenuFile;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.model.OrderDeliveryBoy;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.OrderType;
import com.botree.restaurantapp.model.PaymentType;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.TableMaster;
import com.botree.restaurantapp.model.util.Dateformatter;
import com.botree.restaurantapp.model.util.MonthLastDate;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.OrderService;
import com.botree.restaurantapp.service.StoreAddressService;
import com.botree.restaurantapp.service.exception.ServiceException;
import com.botree.restaurantapp.webservice.OrderWS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping(value = "/order")
public class OrderWSImpl implements OrderWS {

  @Autowired
	private OrderService orderService;
  
  @Autowired
	private StoreAddressService storeAddressService;
	
	private String selectSQLForCategoryName = null;
	
	private Dateformatter dateformatter = new Dateformatter();
	
	//previously this service return the created order id but from 19.07.2018 it will return the created order obj
	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createOrder(@RequestBody OrderMaster order, HttpServletRequest request) {
	  
		OrderMaster createdOrder = null;
		
		long startTime = System.currentTimeMillis();

		// parcel new
    if (order.getTable_no().equalsIgnoreCase("0")) {
      // parcel new
      if (order.getId() == 0) {
        order.setId(-1);
      }
      
      // parcel clubbing
      try {
        //orderId = orderService.createOrder(order, request);
    	  createdOrder = orderService.createOrder(order, request);
      } catch (ServiceException e) {
        e.printStackTrace();
      }
      
      // update
      //return String.valueOf(orderId);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = gson.toJson(createdOrder, OrderMaster.class);
		return json;
    }

		boolean isOrderExistsOnTable = false;
		try {
			System.out.println("order created in orderwsimpl:" + order.getId());
			// orderId=orderService.createOrder(order);

			int oldOrderId = order.getId();
			//OrderMaster oldOrder = orderService.getOrderById(oldOrderId);
			//if (oldOrder == null) {// New Order
	    if (oldOrderId == 0) {// New Order
				// Multi Table
				StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
				TableBookingDAOImpl tableBookingDAOImpl = new TableBookingDAOImpl();
				TableMaster table = tableBookingDAOImpl.getTableById(
						order.getTable_no(), order.getStoreId() + "");

				StoreMaster store = addressDAO.getStoreByStoreId(order
						.getStoreId());

				// multi order mode
				if ("Y".equalsIgnoreCase(store.getMultiOrderTable())
						&& "Y".equalsIgnoreCase(table.getMultiOrder())) { 
					order.setId(-1);
                // new order for multi-order table
					//orderId = orderService.createOrder(order, request);
					createdOrder = orderService.createOrder(order, request);
				}
				else { 
				  // Single order mode
					OrderMaster existingOrderOnTable = orderService.chkOrderExistsOnTable(order);

					if (existingOrderOnTable == null) {
						order.setId(-1);
						// new single mode table
						//orderId = orderService.createOrder(order, request);
						createdOrder = orderService.createOrder(order, request);
					}
					else {
						isOrderExistsOnTable = true;
						throw new Exception();
					}
				}
			}
	    // update order
	    else {
				order.setId(oldOrderId);
				//orderId = orderService.createOrder(order, request);// update
				createdOrder = orderService.createOrder(order, request);// update
			}

			System.out.println("order created in orderwsimpl:"
					+ order.getCustomers().getId());
		}
		catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
			if (isOrderExistsOnTable)
				return "001"; // order already exists for that table, so order could not be created
			else {
				return "002"; // order could not be created, for other reason
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("[/create] Elapsed time::: " + (endTime - startTime));
		//return "" + orderId; // order successfully created
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = gson.toJson(createdOrder, OrderMaster.class);
		return json; // order successfully created
	}
	
	
	
	@RequestMapping(value = "/createAdvOrder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createAdvOrder(@RequestBody OrderMaster order, HttpServletRequest request) {
		int orderId = 0;
		long startTime = System.currentTimeMillis();
		if ((order.getId() == 0 && order.getTable_no().equalsIgnoreCase("0"))) {// parcel
																				// new

			order.setId(-1);
			try {
				orderId = orderService.createAdvOrder(order, request);
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
			return "" + orderId;
		} else if ((order.getId() != 0 && order.getTable_no().equalsIgnoreCase(
				"0"))) {// parcel
						// clubbing

			try {
				int oldOrderId = order.getId();
				order.setId(oldOrderId);
				orderId = orderService.createAdvOrder(order, request);
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}// update
			return "" + orderId;
		}

		boolean isOrderExistsOnTable = false;
		try {
			System.out.println("order created in orderwsimpl:" + order.getId());
			// orderId=orderService.createOrder(order);

			int oldOrderId = order.getId();
			OrderMaster oldOrder = orderService.getOrderById(oldOrderId);
			if (oldOrder == null) {// New Order

				// Multi Table
				StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
				TableBookingDAOImpl tableBookingDAOImpl = new TableBookingDAOImpl();
				TableMaster table = tableBookingDAOImpl.getTableById(
						order.getTable_no(), order.getStoreId() + "");

				StoreMaster store = addressDAO.getStoreByStoreId(order
						.getStoreId());

				if (store.getMultiOrderTable().equalsIgnoreCase("Y")
						&& table.getMultiOrder().equalsIgnoreCase("Y")) { // multi
																			// order
																			// mode
					order.setId(-1);
					orderId = orderService.createAdvOrder(order, request);// new
																		// order
																		// for
																		// multi
																		// order
																		// table
				}

				else { // Single order mode

					OrderMaster existingOrderOnTable = orderService
							.chkAdvOrderExistsOnTable(order);

					if (existingOrderOnTable == null) {
						order.setId(-1);
						orderId = orderService.createAdvOrder(order, request);// new
																			// single
																			// mode
																			// table
					} else {
						isOrderExistsOnTable = true;
						throw new Exception();
					}
				}

			} else {// update order
				OrderMaster  existingOrderOnTable = orderService
						.chkAdvOrderExistsOnTable(order);
				/*if(!order.getTable_no().equals(oldOrder.getTable_no()))
				{
					 existingOrderOnTable = orderService
							.chkAdvOrderExistsOnTable(order);
				}else{
					existingOrderOnTable = orderService
							.chkAdvOrderExistsOnTable(order);
					
				}*/
				
				if (existingOrderOnTable == null) {
					order.setId(oldOrderId);
					orderId = orderService.createAdvOrder(order, request);// update
					
				}
				else if(existingOrderOnTable != null && order.getId()==existingOrderOnTable.getId())
				{
					order.setId(oldOrderId);
					orderId = orderService.createAdvOrder(order, request);// update
				}
				
				else
				{
					isOrderExistsOnTable = true;
					throw new Exception();
				}
				
			}

			
		} catch (Exception x) {
			
			x.printStackTrace();
			if (isOrderExistsOnTable == true)
				return "001"; // order already exists for that table, so order
								// could not be created
			else {
				return "002"; // order could not be created, for other reason
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Elapsed time::: " + (endTime - startTime));
		return "" + orderId; // order successfully created
	}

	@Override
	@RequestMapping(value = "/getOrderType", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getOrderTypes() {
		List<OrderType> orderTypes = null;
		try {

			orderTypes = orderService.getOrderType();

		} catch (Exception x) {
			//System.out.println("in webservice.......");
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderType>>() {
		}.getType();
		String json = gson.toJson(orderTypes, type);

		return json;
	}
	
	
	@RequestMapping(value = "/getOrderTypeByStore", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "application/json")
	public String getOrderTypeByStore(@RequestParam(value = "storeid") Integer storeId) {
	  
    long startTime = System.currentTimeMillis();
    
		List<OrderType> orderTypes = null;
		try {
			orderTypes = orderService.getOrderTypeByStore(storeId);
		} catch (Exception x) {
			//System.out.println("in webservice.......");
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderType>>() {}.getType();
		String json = gson.toJson(orderTypes, type);

    long endTime = System.currentTimeMillis();
    System.out.println("[getOrderTypeByStore] Elapsed time::: " + (endTime - startTime));
    
		return json;
	}
	
	
	@Override
	@RequestMapping(value = "/getPaymentType", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getPaymentTypes() {
		List<PaymentType> paymentTypes = null;
		try {

			paymentTypes = orderService.getPaymentType();

		} catch (Exception x) {
			//System.out.println("in webservice.......");
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderType>>() {
		}.getType();
		String json = gson.toJson(paymentTypes, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/getPaymentTypeByStore", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getPaymentTypeByStore(@RequestParam(value = "id") Integer id) {
		ListHolder holder = null;
		try {

			holder = orderService.getPaymentTypeByStore(id);

		} catch (Exception x) {
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(holder, ListHolder.class);

		return json;
	}

	@Override
	@RequestMapping(value = "/id", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "text/plain")
	public String getItemsByOrderId(@RequestParam(value = "id") String id,
			@RequestParam(value = "language") String language) {

		List<OrderItem> items = null;
		try {
			//System.out.println("enter getItemsByOrderId webservice");
			items = orderService.getItemsByOrderId(id, language);
			//System.out.println("exit getItemsByOrderId webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderItem>>() {
		}.getType();
		String json = gson.toJson(items, type);
		// return json;
		return json;
	}

	@Override
	@RequestMapping(value = "/getItemsByOrderIdInRest", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getItemsByOrderIdInRest(@RequestParam(value = "id") String id,
			@RequestParam(value = "language") String language) {

		List<OrderItem> items = null;
		try {

			items = orderService.getItemsByOrderIdInRest(id, language);

		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderItem>>() {
		}.getType();
		String json = gson.toJson(items, type);
		// return json;
		return json;
	}

	@Override
	@RequestMapping(value = "/getItemsByOrderIdParcel", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getItemsByOrderIdParcel(@RequestParam(value = "id") String id,
			@RequestParam(value = "language") String language) {

		List<OrderItem> items = null;
		try {
			items = orderService.getItemsByOrderIdParcel(id, language);

		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderItem>>() {
		}.getType();
		String json = gson.toJson(items, type);
		// return json;
		return json;
	}

	@Override
	@RequestMapping(value = "/allUnpaidOrdersByStoreId", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "text/plain")
	public String getAllUnpaidOrdersByStoreId(
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "date") String date) {

		List<OrderMaster> orders = null;
		try {
			orders = orderService.getAllUnpaidOrdersByStoreId(id, date);

		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		// return json;
		return json;
	}
	
	@Override
	@RequestMapping(value = "/allUnpaidOrdersByDate", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllUnpaidOrdersByDate(
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "date") String date) {

		List<OrderMaster> orders = null;
		try {
			orders = orderService.getAllUnpaidOrdersByDate(id, date);

		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		// return json;
		return json;
	}

	@RequestMapping(value = "/allUnpaidOrdersByDateRange", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllUnpaidOrdersByDateRange(
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "fromdate") String date,
			@RequestParam(value = "todate") String date2) {

		List<OrderMaster> orders = null;
		try {
			orders = orderService.getAllUnpaidOrdersByDateRange(id,date,date2);

		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		// return json;
		return json;
	}
	
	@RequestMapping(value = "/getAllAdvanceOrders", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllAdvanceOrders(
			@RequestParam(value = "storeId") Integer storeId) {

		List<OrderMaster> orders = null;
		
		try {
			orders = orderService.getAllAdvanceOrders(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		
		// return json;
		return json;
	}
	
	@RequestMapping(value = "/getNoOfAdvanceOrders", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getNoOfAdvanceOrders(
			@RequestParam(value = "storeId") Integer storeId) {

		int noofadvorders=0;
		
		try {
			noofadvorders = orderService.getNoOfAdvanceOrders(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		/*
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);*/
		
		// return json;
		return ""+noofadvorders;
	}
	
	@RequestMapping(value = "/allUnpaidOrdersTranslatedByStoreId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String allUnpaidOrdersTranslatedByStoreId(
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "lang") String lang) {

		long st = System.currentTimeMillis();
		List<OrderMaster> orders = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String json = null;
		try {
			int storeId = id;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			orders = orderService.getAllUnpaidOrdersByStoreId(id, date);
			if (!lang.equalsIgnoreCase(Constants.DEFAULT_LANG)) {
				// start translation
				doTranslation(lang, orders, em, storeId);
			}
			
	    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
	        .create();
	    java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
	    }.getType();
	    json = gson.toJson(orders, type);
	    
	    if (CommonProerties.test) {
			long end = System.currentTimeMillis();
			System.out.println("Performance on getallunpaid orders: " + (end - st)
					+ "milliisec");
		}
		} 
		catch (Exception x) {
			x.printStackTrace();
		}
		finally {
	    if(em != null) em.close();
		}
		// return json;
		
    
		return json;
	}

	private void doTranslation(String lang, List<OrderMaster> orders, EntityManager em, int storeid) {
		// start translation
		for (Iterator<OrderMaster> iterator = orders.iterator(); iterator.hasNext();) {
			List<Integer> itemIdList = new ArrayList<Integer>();
			Map<Integer, MenuItem> itemMap = new HashMap<Integer, MenuItem>();
			OrderMaster order = iterator.next();
			List<OrderItem> orderItemList = order.getOrderitem();
			for (Iterator<OrderItem> iterator2 = orderItemList.iterator(); iterator2.hasNext();) {
				OrderItem orderItem = iterator2.next();
				MenuItem item = orderItem.getItem();
				itemIdList.add(item.getId());
				itemMap.put(item.getId(), item);
			}
			
			TypedQuery<MenuItemLangMap> qry = em
					.createQuery("SELECT l FROM MenuItemLangMap l WHERE l.storeId=:storeid and l.language=:language and l.menuItem.id IN (:itemIdLst)", 
					    MenuItemLangMap.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("language", lang);
			qry.setParameter("itemIdLst", itemIdList);
			List<MenuItemLangMap> itemlangList = qry.getResultList();

			for (Iterator<MenuItemLangMap> iterator2 = itemlangList.iterator(); iterator2.hasNext();) {
				try {
					MenuItemLangMap menuItemLangMap = (MenuItemLangMap) iterator2.next();
					int itemId = menuItemLangMap.getMenuItem().getId();
					MenuItem itemTranslated = itemMap.get(itemId);
					itemTranslated.setName(menuItemLangMap.getName());
					itemTranslated.setDescription(menuItemLangMap.getDescription());
				} catch (Exception e) {
					
					e.printStackTrace();
				}

			}

		}
		// end translation
	}

	@Override
	@RequestMapping(value = "/allPaidOrdersByStoreId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllPaidOrdersByStoreId(
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "date") String date) {

		List<OrderMaster> orders = null;
		try {
			// System.out.println("enter getAllUnpaidOrdersByStoreId webservice");
			orders = orderService.getAllPaidOrdersByStoreId(id, date);
			// System.out.println("exit getAllUnpaidOrdersByStoreId webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		// return json;
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getPaidOrderById", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getPaidOrderById(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") Integer storeId) {

		List<OrderMaster> orders = new ArrayList<OrderMaster>();
		OrderMaster order = null;
		try {
			order = orderService.getPaidOrderById(id, storeId);
			orders.add(order);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getPaidOrderByNo", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getPaidOrderByNo(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") Integer storeId) {

		List<OrderMaster> orders = new ArrayList<OrderMaster>();
		OrderMaster order = null;
		try {
			order = orderService.getPaidOrderByNo(id, storeId);
			orders.add(order);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		return json;
	}

	@Override
	@RequestMapping(value = "/orderById", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "text/plain")
	public String getOrderById(@RequestParam(value = "id") String id) {

		OrderMaster order = null;
		try {
			int orderId = Integer.parseInt(id);
			//System.out.println("enter getOrderById webservice");
			order = orderService.getOrderById(orderId);
			//System.out.println("exit getOrderById webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(order, OrderMaster.class);
		//System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getUnpaidOrderById", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getUnpaidOrderById(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "lang") String lang) {

		OrderMaster order = null;
		try {
			int orderId = id;
			order = orderService.getUnpaidOrderById(orderId, storeId);

		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(order, OrderMaster.class);
		//System.out.println(json);

		return json;
	}
	//added on 18.07.2018
	@Override
	@RequestMapping(value = "/getUnpaidOrderByNo", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getUnpaidOrderByNo(@RequestParam(value = "orderNo") String orderNo,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "lang") String lang) {

		OrderMaster order = null;
		try {
			order = orderService.getUnpaidOrderByNo(orderNo, storeId);

		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(order, OrderMaster.class);
		//System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getOrderWithPaymentInfo", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getOrderWithPaymentInfo(
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "lang", required = false) String lang) {

		OrderMaster order = null;
		try {
			int orderId1 = Integer.parseInt(orderId);
			int storeId1 = storeId;

			order = orderService.getOrderWithPaymentInfo(orderId1, storeId1);

		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(order, OrderMaster.class);
		//System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/orderByIdWithLang", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getOrderById(@RequestParam(value = "id") String id,
			@RequestParam(value = "lang") String lang) {

		OrderMaster order = null;
		try {
			int orderId = Integer.parseInt(id);
			order = orderService.getOrderById(orderId, lang);
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(order, OrderMaster.class);
		//System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/orderByIdForBillSplit", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String orderByIdForBillSplit(@RequestParam(value = "id") String id) {

		OrderMaster order = null;
		try {
			int orderId = Integer.parseInt(id);
			order = orderService.orderByIdForBillSplit(orderId);

		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(order, OrderMaster.class);
		//System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getCreditOrderByCustomerId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCreditOrderByCustomerId(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "id") String id) {

		List<OrderMaster> orders = null;
		try {

			orders = orderService.getCreditOrderByCustomerId(storeId, id);

		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderMaster>>() {
		}.getType();
		String json = gson.toJson(orders, type);
		// return json;
		return json;
	}

	@Override
	@RequestMapping(value = "/billReqByOrderId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String reqBillByOrderId(@RequestParam(value = "id") String id,
			@RequestParam(value = "billReqTime") String billReqTime) {

		String status = null;
		try {
			//System.out.println("enter reqBillByOrderId webservice");

			// check if request is already processed
			status = orderService.chkBillReq(id);
			if (status.equalsIgnoreCase("Yes"))
				return "failure";
			else {

				orderService.reqBillByOrderId(id, billReqTime);
				return "success";

			}

		} catch (Exception x) {
			x.printStackTrace();
			return "failure";
		}

	}

	@Override
	@RequestMapping(value = "/updateOrderByItemId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String updateOrderByItemId(
			@RequestParam(value = "orderitemid") String orderitemid,
			@RequestParam(value = "quantity") String quantity) {

		String status = "";
		try {
			//System.out.println("enter updateOrderByItemId webservice");
			orderService.updateOrderByItemId(orderitemid, quantity);
			status = "success";
			//System.out.println("exit updateOrderByItemId webservice");
		} catch (Exception x) {
			x.printStackTrace();
			status = "failure";
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/cancelOrderById", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String cancelOrderById(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeid") Integer storeId,
			@RequestParam(value = "cancelremrk") String cancelremrk) {

		String status = "";
		try {
			System.out.println("enter cancelOrderById webservice");
			orderService.cancelOrderById(id, storeId, cancelremrk);
			status = "success";
			System.out.println("exit cancelOrderById webservice");
		} catch (Exception x) {
			x.printStackTrace();
			status = "failure";
		}
		return status;
	}

	@RequestMapping(value = "/cancelOrderByIdPost", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String cancelOrderByIdPost(@RequestBody OrderMaster orderMaster) {

		String status = "";
		int id = 0;
		int storeId = 0;
		String cancelRemrk = "";
		try {
			id = orderMaster.getId();
			storeId = orderMaster.getStoreId();
			cancelRemrk = orderMaster.getCancelRemark();
			orderService.cancelOrderById("" + id, storeId, cancelRemrk);
			status = "success";

		} catch (Exception x) {
			x.printStackTrace();
			status = "failure";
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/reportsales/day", method = RequestMethod.GET)
	public void getTodaysSalesReport(@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") String storeId,
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
			baseFileName = "restaurant_sales_daily";
			jasperFile = "restaurant_sales_daily.jrxml";

			Map<String, Object> parameters = new HashMap<>();
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
        if (is != null) { is.close(); }
      } catch (IOException e) {
        
        e.printStackTrace();
      }
		}
	}

	@RequestMapping(value = "/reportsalesdaily", method = RequestMethod.GET)
	public void getTodaysSalesReportCelavi(
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
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "DailySalesReport_celavi";
			jasperFile = "DailySalesReport_celavi.jrxml";

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", date);
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
	@RequestMapping(value = "/reportsales/paymentmode", method = RequestMethod.GET)
	public void getSalesReportPaymentMode(
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
			baseFileName = "day_settlement_report";
			jasperFile = "cluster_report_payement_mode.jrxml";

			Map<String, Object> parameters = new HashMap<>();
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

	@RequestMapping(value = "/reportsales/user/paymentmode/period", method = RequestMethod.GET)
	public void getSalesReportUserWisePaymentMode(
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
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
			baseFileName = "totalPayment";
			jasperFile = "totalPayment.jrxml";

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", startDate);
			parameters.put("W_EndDate", endDate);
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
	@RequestMapping(value = "/reportCategoryWiseSales/period", method = RequestMethod.GET)
	public void getTodaysCategoryWiseSalesReport(
			@RequestParam(value = "date") String date,
			@RequestParam(value = "date1") String date1,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "category") String category,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		String catName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		java.sql.Statement st = null;
		java.sql.ResultSet rs = null;
		Integer catInt = 0;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			catInt = Integer.valueOf(category);

			FacesContext context = FacesContext.getCurrentInstance();

			selectSQLForCategoryName = "SELECT menu_item_name as categoryname FROM fm_m_food_types where id = "
					+ catInt + " ";
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCategoryName);
			while (rs.next()) {
				catName = rs.getString("categoryname");

			}

			
			baseFileName = "restaurant_catgorywise_periodwise";
			jasperFile = "restaurant_catgorywise_periodwise.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_OrderDate", date);
			parameters.put("W_OrderDate2", date1);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_CatId", category);
			parameters.put("W_CatName", catName);
			
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

	// daily sales report with tax and payment mode

	@Override
	@RequestMapping(value = "/reportsalesWithPayment/day", method = RequestMethod.GET)
	public void getTodaysSalesWithPaymentReport(
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
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "restaurant_sales_daily_paymentmode";
			jasperFile = "restaurant_sales_daily_paymentmode.jrxml";

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", date);
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

	// daily sales report with tax breakup

	@Override
	@RequestMapping(value = "/reportsalesWithTax/day", method = RequestMethod.GET)
	public void getTodaysSalesReportWithTax(
			@RequestParam(value = "date") String date,
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
			baseFileName = "restaurant_sales_daily_taxbreakup";
			jasperFile = "restaurant_sales_daily_taxbreakup.jrxml";

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", date);
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
	}

	@RequestMapping(value = "/reportsales/tp/day", method = RequestMethod.GET)
	public void getTodaysSalesReportTP(@RequestParam(value = "date") String date,
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

			if (storeId == 59 || storeId == 63) {
			  baseFileName = "subcatwisesales_gb";
				jasperFile = "subcatwisesales_gb.jrxml";
			} else {
			  baseFileName = "subcatwisesales_sb";
				jasperFile = "subcatwisesales_sb.jrxml";
			}

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", date);
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
      }
      catch (IOException e) {
        e.printStackTrace();
      }
		}
	}

	//for saravanaa
	@RequestMapping(value = "/reportsales/tp/daysummary", method = RequestMethod.GET)
	public void getTodaysSalesSummaryReportTP(
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
		String taxText = "";
		double taxVal = 0.0;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			if (storeId == 59 || storeId == 63) {
			  baseFileName = "summary_gb";
				jasperFile = "summary_gb.jrxml";
			} else {
			  baseFileName = "summary_sb";
				jasperFile = "summary_sb.jrxml";
			}

			StoreMaster store = new StoreMaster();
			try {
				store = storeAddressService.getStoreByStoreId(storeId);
				taxText = store.getVatTaxText();
				taxVal = store.getVatAmt();

			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", date);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_Taxvalue", Double.toString(taxVal));
			parameters.put("W_Taxtext", taxText);
			
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

	@RequestMapping(value = "/reportsales/tp/monthlysummary", method = RequestMethod.GET)
	public void getMonthlySalesSummaryReportTP(
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
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
		String taxText = "";
		double taxVal = 0.0;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			if (storeId == 59 || storeId == 63) {
			  baseFileName = "monthly_summary_gb";
				jasperFile = "monthly_summary_gb.jrxml";
			} else {
			  baseFileName = "monthly_summary_sb";
				jasperFile = "monthly_summary_sb.jrxml";
			}

			StoreMaster store = new StoreMaster();
			try {
				store = storeAddressService.getStoreByStoreId(storeId);
				taxText = store.getVatTaxText();
				taxVal = store.getVatAmt();

			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", startDate);
			parameters.put("W_EndDate", endDate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_Taxvalue", Double.toString(taxVal));
			parameters.put("W_Taxtext", taxText);
			
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

  // for saravanaa
	@RequestMapping(value = "/reportsales/tp/hourly", method = RequestMethod.GET)
	public void getSalesReportByHour(@RequestParam(value = "frmdate") String frmdate,
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

			/*
			 * String[] dtArry = date.split("-"); String year = dtArry[0];
			 * String month = dtArry[1]; String day = dtArry[2];
			 * 
			 * String formattedDate = day + "/" + month + "/" + year; String
			 * strtDateTime = formattedDate + " " + "08:00:00"; String
			 * endDateTime = formattedDate + " " + "23:59:59";
			 */
			if (storeId == 59 || storeId == 63) {
			  baseFileName = "hourlysales_gb";
				jasperFile = "hourlysales_gb.jrxml";
			} else if (storeId == 37 || storeId == 38){
			  baseFileName = "hourlysales_sb";
				jasperFile = "hourlysales_sb.jrxml";
			} else {
			  baseFileName = "daily_hourlysales";
				jasperFile = "daily_hourlysales.jrxml";
			}

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			// parameters.put("W_StartDate", strtDateTime);
			// parameters.put("W_EndDate", endDateTime);
			
			
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

	// daily Category wise sales report

	@Override
	@RequestMapping(value = "/reportCategoryWiseSales/day", method = RequestMethod.GET)
	public void getTodaysCategoryWiseSalesReport(
			@RequestParam(value = "date") String date,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "category") String category,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		String catName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		java.sql.Statement st = null;
		java.sql.ResultSet rs = null;
		Integer catInt = 0;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			catInt = Integer.valueOf(category);

			FacesContext context = FacesContext.getCurrentInstance();

			selectSQLForCategoryName = "SELECT menu_item_name as categoryname FROM restaurant.fm_m_food_types where id = "
					+ catInt + " ";
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCategoryName);
			while (rs.next()) {
				catName = rs.getString("categoryname");

			}
			/*
			 * MenuCategory category1 = new MenuCategory();
			 * 
			 * try { catInt=Integer.valueOf(category); category1 =
			 * menuService.getCategoryById(catInt);
			 * 
			 * } catch (ServiceException e) { 
			 * e.printStackTrace(); }
			 * 
			 * catName=category1.getMenuCategoryName();
			 */

			
			baseFileName = "restaurant_catgorywise";
			jasperFile = "restaurant_catgorywise.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_OrderDate", date);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_CatId", category);
			parameters.put("W_CatName", catName);
			
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

	// daily user wise sales report

	@Override
	@RequestMapping(value = "/reportUserWiseSales/day", method = RequestMethod.GET)
	public void getTodaysUserWiseSalesReport(
			@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "user") String user,
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

			// catInt = Integer.valueOf(category);

			FacesContext context = FacesContext.getCurrentInstance();

			/*
			 * selectSQLForCategoryName =
			 * "SELECT menu_item_name as categoryname FROM restaurant.fm_m_food_types where id = "
			 * + catInt + " "; st = connection.createStatement(); rs =
			 * st.executeQuery(selectSQLForCategoryName); while (rs.next()) {
			 * catName = rs.getString("categoryname");
			 * 
			 * }
			 */

			/*
			 * MenuCategory category1 = new MenuCategory();
			 * 
			 * try { catInt=Integer.valueOf(category); category1 =
			 * menuService.getCategoryById(catInt);
			 * 
			 * } catch (ServiceException e) { 
			 * e.printStackTrace(); }
			 * 
			 * catName=category1.getMenuCategoryName();
			 */

			
			baseFileName = "restaurant_sales_daily_user_wise";
			jasperFile = "restaurant_sales_daily_user_wise.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			// parameters.put("W_CatId", category);
			parameters.put("W_User", user);
			
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
		  if(em != null) em.close();
		}
	}

	@Override
	@RequestMapping(value = "/reportitem/day", method = RequestMethod.GET)
	public void getTodaysItemReport(@RequestParam(value = "date") String date,
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
			baseFileName = "mob_restaurant_item_daily";
			jasperFile = "mob_restaurant_item_daily.jrxml";
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", date);
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
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
		}
	}

	@Override
	@RequestMapping(value = "/reportitem/periodwise", method = RequestMethod.GET)
	public void getPeriodwiseItemReport(
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
			baseFileName = "restaurant_itemwise_daily";
			jasperFile = "restaurant_itemwise_daily.jrxml";
			
			Map<String, Object> parameters = new HashMap<>();
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
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
		}
	}

	/*
	 * //periodwise item sales report
	 * 
	 * @Override
	 * 
	 * @RequestMapping(value = "/reportitem/periodwise")
	 * 
	 * method = RequestMethod.GET, public void getPeriodwiseItemReport(
	 * 
	 * @RequestParam(value = "frmdate") String frmdate,
	 * 
	 * @RequestParam(value = "todate") String todate,
	 * 
	 * @RequestParam(value = "storeId") Integer storeId,
	 * 
	 * HttpServletRequest request,
	 * 
	 * HttpServletResponse response) {  String
	 * fileName = null; Connection connection = null; EntityManagerFactory
	 * entityManagerFactory; EntityManager em = null; String
	 * selectSQLForCustomerDisc = null; String selectSQLForCustomerCancl = null;
	 * String selectSQLForRoundoff = null; String jasperFile = null; Statement
	 * st = null; ResultSet rs = null; Statement st1 = null; ResultSet rs1 =
	 * null; Statement st2 = null; ResultSet rs2 = null; String
	 * totalCustDiscount = null; String totalCanclAmount = null; String
	 * totalRoundAmount = null; try { entityManagerFactory =
	 * PersistenceListener.getEntityManager(); em =
	 * entityManagerFactory.createEntityManager();
	 * 
	 * // get connection object from entity manager Session ses = (Session)
	 * em.getDelegate(); SessionFactoryImpl sessionFactory =
	 * (SessionFactoryImpl) ses .getSessionFactory();
	 * 
	 * connection = sessionFactory.getConnectionProvider().getConnection();
	 * 
	 * selectSQLForCustomerDisc =
	 * "SELECT sum(bill.customer_discount) as total_cust_disc FROM restaurant.bp_t_bill bill inner join fo_t_orders o on bill.order_id=o.id where o.store_id="
	 * + storeId + " AND o.order_date BETWEEN'" + frmdate +"'  AND '" + todate +
	 * "' group by o.order_date";
	 * 
	 * st = connection.createStatement(); rs =
	 * st.executeQuery(selectSQLForCustomerDisc); while (rs.next()) {
	 * totalCustDiscount = rs.getString("total_cust_disc");
	 * 
	 * }
	 * 
	 * selectSQLForCustomerCancl =
	 * "select sum(oi.net_price) as total from  restaurant.fo_t_orders_item oi inner join fo_t_orders o on oi.order_id=o.id where o.store_id="
	 * + storeId + " AND o.order_date BETWEEN'" + frmdate +"'  AND '" + todate +
	 * "' and  o.cancel='Y'"; st1 = connection.createStatement(); rs1 =
	 * st.executeQuery(selectSQLForCustomerCancl);
	 * System.out.println("selectSQLForCustomerCancl:::" +
	 * selectSQLForCustomerCancl); while (rs1.next()) { totalCanclAmount =
	 * rs1.getString("total");
	 * 
	 * }
	 * 
	 * if (totalCanclAmount == null) {
	 * 
	 * totalCanclAmount = "0.00"; }
	 * 
	 * selectSQLForRoundoff =
	 * "SELECT sum(b.round_off_amt) as roundoff FROM restaurant.bp_t_bill b left join fo_t_orders o on o.id=b.order_id where BETWEEN'"
	 * + frmdate +"'  AND '" + todate + "' and o.store_id=" + storeId + "";
	 * 
	 * st2 = connection.createStatement(); rs2 =
	 * st.executeQuery(selectSQLForRoundoff); System.out
	 * .println("selectSQLForRoundoff:::" + selectSQLForRoundoff); while
	 * (rs2.next()) { totalRoundAmount = rs2.getString("roundoff");
	 * 
	 * }
	 * 
	 * if (totalRoundAmount == null) {
	 * 
	 * totalRoundAmount = "0.00"; }
	 * 
	 * System.out.println("round off::  " + totalRoundAmount); FacesContext
	 * context = FacesContext.getCurrentInstance(); fileName =
	 * "restaurant_item_period.pdf"; jasperFile =
	 * "restaurant_item_period.jrxml"; Map<String, Object> parameters = new
	 * HashMap(); parameters.put("W_StartDate", frmdate);
	 * parameters.put("W_EndDate", todate); parameters.put("W_CustDisc",
	 * totalCustDiscount); parameters.put("W_CancPrice", totalCanclAmount);
	 * parameters.put("W_roundoff", totalRoundAmount);
	 * parameters.put("W_StoreId", String.valueOf(storeId)); generatePDF(context, request,
	 * response, fileName, parameters, connection, jasperFile);
	 * 
	 * File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
	 * InputStream is = new FileInputStream(file); response.reset();
	 * response.setHeader("Content-Type", "application/pdf");
	 * response.setHeader("Content-Length", String.valueOf(file.length()));
	 * response.setHeader("Content-Disposition", "inline; filename=\"" +
	 * fileName + "\""); List<Byte> buf = new ArrayList<Byte>(); int ch = -1;
	 * while ((ch = is.read()) != -1) { buf.add((byte) ch); } byte[] array = new
	 * byte[buf.size()]; for (int i = 0; i < buf.size(); i++) { array[i] =
	 * buf.get(i); } ServletOutputStream os = response.getOutputStream();
	 * os.write(array); os.flush(); os.close(); is.close(); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } catch (SQLException e) { e.printStackTrace(); }
	 * }
	 */

  // not used
	@Override
	@RequestMapping(value = "/reportorder/day", method = RequestMethod.GET)
	public void getTodaysOrderReport(@RequestParam(value = "date") String date,
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
		String selectSQLForCustomerDisc = null;
		String selectSQLForCustomerCancl = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String totalCustDiscount = null;
		String totalCanclAmount = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			selectSQLForCustomerDisc = "SELECT sum(bill.customer_discount) as total_cust_disc FROM restaurant.bp_t_bill bill inner join fo_t_orders o on bill.order_id=o.id where o.store_id="
					+ storeId
					+ " and o.order_date='"
					+ date
					+ "' group by o.order_date";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			while (rs.next()) {
				totalCustDiscount = rs.getString("total_cust_disc");
			}
			if (totalCustDiscount == null) {
				totalCustDiscount = "0.00";
			}
			st.close();

			selectSQLForCustomerCancl = "select sum(oi.net_price) as total from  restaurant.fo_t_orders_item oi inner join fo_t_orders o on oi.order_id=o.id where o.store_id="
					+ storeId
					+ " and o.order_date='"
					+ date
					+ "' and  o.cancel='Y'";
			st = connection.createStatement();
			rs1 = st.executeQuery(selectSQLForCustomerCancl);
			System.out.println("selectSQLForCustomerCancl:::"
					+ selectSQLForCustomerCancl);
			while (rs1.next()) {
				totalCanclAmount = rs1.getString("total");
			}

			if (totalCanclAmount == null) {
				totalCanclAmount = "0.00";
			}
			st.close();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "mob_restaurant_order_daily";
			jasperFile = "mob_restaurant_item_daily.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", date);
			parameters.put("W_CustDisc", totalCustDiscount);
			parameters.put("W_CancPrice", totalCanclAmount);
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
        if(rs != null) rs.close();
        if(rs1 != null) rs1.close();
        if(st != null) st.close();
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
		}
	}

	@Override
	@RequestMapping(value = "/reportOrder", method = RequestMethod.GET)
	public void getOrderReportPeriodWise(
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
			
			baseFileName = "restaurant_order";
			jasperFile = "restaurant_order.jrxml";
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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}

	@Override
	@RequestMapping(value = "/reportitem/month", method = RequestMethod.GET)
	public void getMonthlyItemReport(@RequestParam(value = "year") String year,
			@RequestParam(value = "month") String month,
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
		String selectSQLForCustomerDisc = null;
		Statement st = null;
		ResultSet rs = null;
		String totalCustDiscount = null;
		MonthLastDate monthLastDate = null;

		try {
			int year1 = Integer.parseInt(year);
			int month1 = Integer.parseInt(month);
			int monthdigits = month1;
			int noOfdigit = 0;
			String StartDate = null;
			while (monthdigits > 0) {
				noOfdigit++;
				monthdigits = monthdigits / 10;
			}
			monthLastDate = MonthLastDate.getInstance();
			String monthname = monthLastDate.getmonthName(month1 - 1);
			String endDate = monthLastDate.getDate(month1, year1);
			if (noOfdigit == 2) {
				StartDate = year + "-" + month + "-" + "01";
			} else {
				StartDate = year + "-0" + month + "-" + "01";
			}
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			selectSQLForCustomerDisc = "SELECT sum(bill.customer_discount) as total_cust_disc FROM restaurant.bp_t_bill bill inner join fo_t_orders o on bill.order_id=o.id where o.store_id="
					+ storeId
					+ " and o.order_date between '"
					+ StartDate
					+ "' and '" + endDate + "'group by o.order_date";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			while (rs.next()) {
				totalCustDiscount = rs.getString("total_cust_disc");
			}

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "mob_restaurant_order_monthly";
			jasperFile = "mob_restaurant_order_monthly.jrxml";
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", StartDate);
			parameters.put("W_EndDate", endDate);
			parameters.put("W_CustDisc", totalCustDiscount);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_year", year);
			parameters.put("W_month", monthname);

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
          if(rs != null) rs.close();
          if(st != null) st.close();
          if(connection != null) connection.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
		}
	}

	@Override
	@RequestMapping(value = "/reportsales", method = RequestMethod.GET)
	public void getSalesReport(@RequestParam(value = "type") String type,
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

			// call the corresponding report file depending on the report type
			if (type.equalsIgnoreCase(CommonProerties.salesDaily)) {
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				baseFileName = "mob_sales_report";
				jasperFile = "mob_restaurant_sales.jrxml";
			}
			else if (type.equalsIgnoreCase(CommonProerties.salesYearly)) {
				System.out.println("yearly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				baseFileName = "mob_sales_report_yearly";
				jasperFile = "mob_restaurant_sales_yearly.jrxml";
			}
			else if (type.equalsIgnoreCase(CommonProerties.salesMonthly)) {
				System.out.println("monthly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));
				baseFileName = "mob_sales_report_monthly";
				jasperFile = "mob_restaurant_sales_monthly.jrxml";
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
		  if(em != null) em.close();
		  try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
		}
	}

  // for saravanaa
	@RequestMapping(value = "/reportsales/tp/date", method = RequestMethod.GET)
	public void getSalesReportByDate(
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

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			if (storeId == 59 || storeId == 63) {
			  baseFileName = "catwisesales_gb";
				jasperFile = "catwisesales_gb.jrxml";
			} else {
			  baseFileName = "catwisesales_sb";
				jasperFile = "catwisesales_sb.jrxml";
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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	// credit sale report
	@Override
	@RequestMapping(value = "/reportcreditSale", method = RequestMethod.GET)
	public void getCreditSaleReport(
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
			Map<String, Object> parameters = new HashMap<>();

			
			// fromDateFormat = dateformatter.dateFormat(frmdate);
			// toDateFormat = dateformatter.dateFormat(todate);
			// call the corresponding report file depending on the report type
			
			baseFileName = "restaurant_credit_sell_daterange";
			jasperFile = "restaurant_credit_sell_daterange.jrxml";

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

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
      if(em != null) em.close();
      try {
        if(is != null) is.close();
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	// cancelation report

	@Override
	@RequestMapping(value = "/reportCancelOrder", method = RequestMethod.GET)
	public void getCanceledOrderReport(
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
			Map<String, Object> parameters = new HashMap<>();

			
			// fromDateFormat = dateformatter.dateFormat(frmdate);
			// toDateFormat = dateformatter.dateFormat(todate);
			// call the corresponding report file depending on the report type
			
			baseFileName = "CancelOrderDetails";
			jasperFile = "CancelOrderDetails.jrxml";

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
      if(em != null) em.close();
      try {
        if(connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
	}

	@RequestMapping(value = "/expenditureDetailsReport", method = RequestMethod.GET)
	public void expenditureDetailsReport(
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
			Map<String, Object> parameters = new HashMap<>();

			
			// fromDateFormat = dateformatter.dateFormat(frmdate);
			// toDateFormat = dateformatter.dateFormat(todate);
			// call the corresponding report file depending on the report type
			
			baseFileName = "ExpenditureDetailsReport.pdf";
			jasperFile = "ExpenditureDetailsReport.jrxml";

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
      if(em != null) em.close();
      try {
        if(is != null) is.close();
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	@RequestMapping(value = "/reportdiscount", method = RequestMethod.GET)
	public void getDiscountReportCelavi(
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

			

			baseFileName = "discountreport";
			jasperFile = "discountreport.jrxml";

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	@RequestMapping(value = "/reportnonchargeable", method = RequestMethod.GET)
	public void getNonChargeableReportCelavi(
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

			

			baseFileName = "nonchargeble_bill";
			jasperFile = "nonchargeble_bill.jrxml";

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	@RequestMapping(value = "/reportpayment", method = RequestMethod.GET)
	public void getPaymentReportCelavi(
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

			

			baseFileName = "PaymentReport_celavi";
			jasperFile = "PaymentReport_celavi.jrxml";

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	@RequestMapping(value = "/reportdailysalesinclusivetaxes", method = RequestMethod.GET)
	public void getDailySalesInclusiveTaxesReportCelavi(
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

			

			baseFileName = "DailySalesInclusiveTaxesReport_celavi";
			jasperFile = "DailySalesInclusiveTaxesReport_celavi.jrxml";

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	@RequestMapping(value = "/reportmonthlysummary", method = RequestMethod.GET)
	public void getMonthlySummaryReportCelavi(
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

			

			baseFileName = "monthly_summary_celavi";
			jasperFile = "monthly_summary_celavi.jrxml";

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	// NOC KOT report

	@Override
	@RequestMapping(value = "/reportNocOrder", method = RequestMethod.GET)
	public void getNocKotReport(@RequestParam(value = "frmdate") String frmdate,
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

			
			// fromDateFormat = dateformatter.dateFormat(frmdate);
			// toDateFormat = dateformatter.dateFormat(todate);
			// call the corresponding report file depending on the report type
			
			baseFileName = "nonchargeble_kot";
			jasperFile = "nonchargeble_kot.jrxml";

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));

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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}
	
	// PACKAGING DETAILS report

		@Override
		@RequestMapping(value = "/reportPackagingDetail", method = RequestMethod.GET)
		public void getPackagingDetailReport(@RequestParam(value = "frmdate") String frmdate,
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

				
				// fromDateFormat = dateformatter.dateFormat(frmdate);
				// toDateFormat = dateformatter.dateFormat(todate);
				// call the corresponding report file depending on the report type
				
				baseFileName = "packaging_details";
				jasperFile = "packaging_details.jrxml";

				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", String.valueOf(storeId));

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
	      if(em != null) em.close();
	      try {
	        if(is != null) { is.close(); }
	        if(connection != null) connection.close();
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
		}

	// Method to generate pdf
	public void generatePDF(FacesContext context, HttpServletRequest request,
			HttpServletResponse response, String fileName,
			Map<String, Object> parameters, Connection connection,
			String jasperFile) throws FileNotFoundException, IOException {

		try {
			JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + jasperFile);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
			JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
		}

		catch (JRException e) {
			e.printStackTrace();
		} finally {

		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following
		// exception in the logs:
		// java.lang.IllegalStateException: Cannot forward after response has
		// been committed.
		// context.responseComplete();
	}

	// daily inventory stock out for All Category

	@Override
	@RequestMapping(value = "/reportTimelySales/workingHours", method = RequestMethod.GET)
	public void getTimelySalesReport(@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "workingHours") String workingHours,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		String startDateTime = null;
		String endDateTime = null;
		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();

			StoreMaster store = new StoreMaster();
			try {
				store = storeAddressService.getStoreByStoreId(storeId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			String startingTime = store.getOpenTime();
			int workingTime = Integer.parseInt(workingHours);
			startDateTime = dateformatter.getStartDateTime(frmdate, startingTime);
			endDateTime = dateformatter.changeFormat(todate, startingTime,
					workingTime);

			String[] dateArray = startDateTime.split(" ");
			String date1 = dateArray[0];
			endDateTime = date1 + " " + "23:59";

			
			baseFileName = "restaurant_sales_time";
			jasperFile = "restaurant_sales_time.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StartDateTime", startDateTime);
			parameters.put("W_EndDateTime", endDateTime);

			// parameters.put("W_StartDateTime", startDate);
			// parameters.put("W_EndDateTime", endDate);
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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	@RequestMapping(value = "/reportCustomerDetails", method = RequestMethod.GET)
	public void reportCustomerDetails(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer rptType,
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

      jasperFile = "Customer details.jrxml";
      baseFileName = "Customer details";

      Map<String, Object> parameters = new HashMap<>();
      parameters.put("W_StoreId", String.valueOf(storeId));

      if(rptType == null) rptType = 1;
      String contentType = (rptType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((rptType == 1) ? ".pdf" :  ".xlsx");

      if (rptType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}

	@Override
	@RequestMapping(value = "/getLastOrder", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getLastOrder(@RequestParam(value = "storeId") Integer storeId) {

		String orderDtls = "";
		try {
			System.out.println("enter getLastOrder webservice");

			orderDtls = orderService.getLastOrder(storeId);
			return orderDtls;

		} catch (Exception x) {
			x.printStackTrace();
			return orderDtls;

		}

	}

	@Override
	@RequestMapping(value = "/getAllKitchenInItemsWithLang", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllKitchenInItems(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "lang") String lang) {

		List<OrderItem> items = null;
		
		try {

			items = orderService.getAllKitchenInItems(storeId, date, lang);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderItem>>() {
		}.getType();
		String json = gson.toJson(items, type);
		// return json;
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllKitchenInItems", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllKitchenInItems(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "date") String date) {

		List<OrderItem> items = null;
		try {
			items = orderService.getAllKitchenInItems(storeId, date, "");

		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<OrderItem>>() {
		}.getType();
		String json = gson.toJson(items, type);
		// return json;
		return json;
	}

	@Override
	@RequestMapping(value = "/updateCookingStatus", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String updateCookingStatus(
			@RequestParam(value = "orderid") String orderid,
			@RequestParam(value = "orderitemid") String orderitemid,
			@RequestParam(value = "starttime") String starttime) {

		String status = "";
		try {
			status = orderService.updateCookingStatus(orderid, orderitemid, starttime);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}
		
		return status;
	}

	@Override
	@RequestMapping(value = "/updateNoOfPersons", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String updateNoOfPersons(
			@RequestParam(value = "orderId") String orderid,
			@RequestParam(value = "noOfPersons") String noOfPersons) {

		String status = "";
		try {
			status = orderService.updateNoOfPersons(orderid, noOfPersons);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}
		
		return status;
	}

	@Override
	@RequestMapping(value = "/updateTableNo", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateTableNo(@RequestBody OrderMaster orderMaster) {

		String status = "";
		try {
			status = orderService.updateTableNo(orderMaster);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/cookingEndStatus", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String cookingEndStatus(
			@RequestParam(value = "orderid") String orderid,
			@RequestParam(value = "orderitemid") String orderitemid,
			@RequestParam(value = "endtime") String endtime) {

		String status = "";
		try {

			status = orderService.cookingEndStatus(orderid, orderitemid,
					endtime);

		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/updateKitchenOutStatus", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String updateKitchenOutStatus(
			@RequestParam(value = "orderid") String orderid,
			@RequestParam(value = "orderitemid") String orderitemid) {

		String status = "";
		try {
			status = orderService.updateKitchenOutStatus(orderid, orderitemid);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}
		return status;

	}

	@Override
	@RequestMapping(value = "/printBill", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String printBill(@RequestParam(value = "orderid") String orderid,
			@RequestParam(value = "storeid") Integer storeId) {

		String status = "";
		// MobPrintBill mobPrintBill = null;
		try {
			System.out.println("Print started...");
			status = orderService.printBill(orderid, storeId);

		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}
		System.out.println("Print done..." + status);
		return status;
	}

	@Override
	@RequestMapping(value = "/printSplitBill", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String printSplitBill(@RequestParam(value = "orderid") String orderid,
			@RequestParam(value = "storeid") Integer storeId) {

		String status = "";
		// MobPrintBill mobPrintBill = null;
		try {
			status = orderService.printSplitBill(orderid, storeId);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/printKot", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String printKot(@RequestParam(value = "orderid") Integer orderid,
			@RequestParam(value = "storeid") Integer storeid) {

		String status = "";

		try {
			status = orderService.printKot(orderid, storeid);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updateKot", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String updateKot(@RequestParam(value = "orderid") Integer orderid,
			@RequestParam(value = "storeid") Integer storeid) {

		String status = "";

		try {
			status = orderService.printKot(orderid, storeid);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/printUpdateKot", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String printUpdateKot(@RequestParam(value = "orderid") Integer orderid,
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "crntQnty") int crntQnty,
			@RequestParam(value = "prevQnty") int prevQnty,
			@RequestParam(value = "itemName") String itemName) {

		String status = "";

		try {
			status = orderService.printUpdateKot(orderid, storeid, crntQnty, prevQnty, itemName);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updateBillPrintCount", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateBillPrintCount(@RequestBody CommonBean bean) {

		String status = "";
		try {
			status = orderService.updateBillPrintCount(bean);
		} catch (Exception x) {
			x.printStackTrace();
			status = "failure";
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/getBillPrintCount", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getBillPrintCount(
			@RequestParam(value = "orderid") Integer orderid,
			@RequestParam(value = "storeid") Integer storeId) {

		String status = "";
		try {
			status = orderService.getBillPrintCount(orderid, storeId);

		} catch (Exception x) {
			x.printStackTrace();
			status = "" + 0;
		}

		return status;
	}

	/*
	 * @Override
	 * 
	 * @RequestMapping(value = "/insertMenu")
	 * 
	 * method = RequestMethod.POST,
	 * 
	 * consumes = MediaType.MULTIPART_FORM_DATA)
	 * 
	 * produces = "text/plain") public String insertMenu(MenuFile menuFile) {
	 * 
	 * String status = ""; POITest poiTest=new POITest(); int
	 * storeid=menuFile.getStoreId(); String filename=menuFile.getFileName();
	 * byte[] data=menuFile.getData(); //JSONArray jsonArray=new JSONArray();
	 * 
	 * 
	 * try {
	 * 
	 * status = poiTest.dataInsert(filename, storeid, data);
	 * 
	 * } catch (FileUplodException x) {
	 * System.out.println("serial no.----- "+x.getMenuUploadFile
	 * ().getSerialNo()); x.printStackTrace(); status = "failure"; }
	 * 
	 * return status; }
	 */

	/*@RequestMapping(value = "/insertMenu", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String submit(
	    @RequestParam("file") InputStream fileInputStream,
	    @RequestParam("menuFile") MenuFile menuFile) {

		String status = "";
		try {
			// String status = "";
			POITest poiTest = new POITest();
			int storeid = menuFile.getStoreId();
			String filename = menuFile.getFileName();
			byte[] data = convertInputStreamToByteArrary(fileInputStream);
			int lenth = data.length;
			System.out.println("data sise:: " + lenth);
			status = poiTest.dataInsert(filename, storeid, data);

		} catch (Exception ex) {
			status = "failure";
			// return Response.status(600).entity(ex.getMessage()).build();
		}
		return status;
	}*/
	@RequestMapping(value = "/insertMenu", method = RequestMethod.POST , consumes = {"multipart/form-data"})//MediaType.MULTIPART_FORM_DATA
	@ResponseBody
	public String submit(@RequestPart("file")  MultipartFile  file,
	                     @RequestPart("menuFile") String menuFile) {
	  
		String status = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		  
		    JsonNode mf = objectMapper.readTree(menuFile);
		    POITest poiTest = new POITest();
			int storeid = Integer.parseInt(mf.get("storeId").toString());// .getStoreId();
			String filename = mf.get("fileName").toString();// .getFileName();
            byte[] data = convertInputStreamToByteArrary(file.getInputStream());
			int lenth = data.length;
			System.out.println("data sise:: " + lenth);
			status = poiTest.dataInsert(filename, storeid, data);

		} catch (Exception ex) {
			status = "failure";
			// return Response.status(600).entity(ex.getMessage()).build();
		}
		return status;
	}

	
	
	
	
	
	public static byte[] convertInputStreamToByteArrary(InputStream in)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		final int BUF_SIZE = 1024;
		byte[] buffer = new byte[BUF_SIZE];
		int bytesRead = -1;
		while ((bytesRead = in.read(buffer)) > -1) {
			out.write(buffer, 0, bytesRead);
		}
		in.close();
		byte[] byteArray = out.toByteArray();
		return byteArray;
	}

	@Override
	@RequestMapping(value = "/updateCreditSaleStatus", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String updateCreditSaleStatus(
			@RequestParam(value = "orderId") String orderid,
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "storeCustomerId") String storeCustomerId) {

		String status = "";
		try {
			status = orderService.updateCreditSaleStatus(orderid, storeId, storeCustomerId);
		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}
		return status;
	}
	/*
	 * @author ChanchalN
	 * Webservice for adding Delivery boy to a particular order.
	 * @see com.sharobi.restaurantapp.webservice.OrderWS#assignDeliveryBoy(com.sharobi.restaurantapp.model.OrderDeliveryBoy)
	 */
	@Override
	@RequestMapping(value = "/assignDeliveryBoy", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String assignDeliveryBoy(@RequestBody OrderDeliveryBoy orderDeliveryBoy) {
		String status="failure";
		try {
			status=orderService.assignDeliveryBoy(orderDeliveryBoy);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	//added on 04.04.2019 for order sync to server
	@RequestMapping(value = "/syncOrder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String syncOrder(@RequestBody OrderMaster order, HttpServletRequest request) {
		int orderId = 0;
		long startTime = System.currentTimeMillis();
		if ((order.getId() == 0)) {
			order.setId(-1);
			try {
				orderId = orderService.syncOrder(order, request);
			} catch (ServiceException e) {	
				e.printStackTrace();
				return "002"; // order could not be created, for other reason
			}
		} 
		long endTime = System.currentTimeMillis();
		System.out.println("Elapsed time::: " + (endTime - startTime));
		return "" + orderId; // order successfully created
	}
	
	//added on 07.04.2021 for setting remarks notes to order table
		@Override
		@RequestMapping(value = "/setOrderRemark", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
		public String setOrderRemark(@RequestBody OrderMaster orderMaster) {

			String status = "";
			try {
				orderService.setOrderRemark(orderMaster);
				status = "success";
			} catch (Exception x) {
				x.printStackTrace();
				status = "failure";
			}
			return status;
		}
	
	//added on 07.04.2021 for setting packaging notes/special notes to order table
	@Override
	@RequestMapping(value = "/setPackagingNote", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String setPackagingNote(@RequestBody OrderMaster orderMaster) {

		String status = "";
		try {
			orderService.setPackagingNote(orderMaster);
			status = "success";
		} catch (Exception x) {
			x.printStackTrace();
			status = "failure";
		}
		return status;
	}
	
	/* Webservice to generate report of DeliveryBoy on the basis of StoreId
	 * @author ChanchalN
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.OrderWS#reportOrderdeliveryBoy(java.lang.Integer, java.lang.String, java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@RequestMapping(value = "/reportOrderdeliveryBoy", method = RequestMethod.GET)
	public void reportOrderdeliveryBoy(@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "orderFrom") String orderFrom,
			@RequestParam(value = "orderTo") String orderTo,
			@RequestParam(value = "deliveryBoyId") Integer deliveryBoyId,
			@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response){
		
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

			
			baseFileName = "DeliveryPersonDetails";
			jasperFile = "DeliveryPersonDetails.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			

			parameters.put("W_StoreID", storeId);
			parameters.put("W_OrderFrom", orderFrom);
			parameters.put("W_OrderTo", orderTo);
			parameters.put("W_DelboyID", deliveryBoyId);
			
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
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}
	
	//added on 21.06.2018
		@Override
		@RequestMapping(value = "/reportRefundSummary", method = RequestMethod.GET)
		public void getRefundSummaryReport(
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
				
				baseFileName = "refund_summary";
				jasperFile = "refund_summary.jrxml";
				Map<String, Object> parameters = new HashMap<>();
				
				// parameters.put("W_EndDate", todate);
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID", storeId);
				
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
		@RequestMapping(value = "/reportRefundDetails", method = RequestMethod.GET)
		public void getRefundDetailsReport(
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
				
				baseFileName = "refund_details";
				jasperFile = "refund_details.jrxml";
				Map<String, Object> parameters = new HashMap<>();
				
				// parameters.put("W_EndDate", todate);
				parameters.put("W_startDate", frmdate);
				parameters.put("W_endDate", todate);
				parameters.put("W_storeID", storeId);
				
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
	
		// Method to generate xls
		public void generateXLS(FacesContext context,HttpServletRequest request,
				HttpServletResponse response, String fileName,
				Map<String, Object> parameters, Connection connection,
				String jasperFile) throws FileNotFoundException, IOException {
			

			try {
				String xlsPath=request.getSession().getServletContext().getRealPath("/") + "/jasper/"+fileName;

				JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + jasperFile);
				JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
				
        JRXlsxExporter exporterXls = new JRXlsxExporter();
	      
        exporterXls.setExporterInput(new SimpleExporterInput(print));
        exporterXls.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsPath));
	      
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
        configuration.setOffsetX(0);
        configuration.setOffsetY(0);
        configuration.setIgnoreCellBorder(false);
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        configuration.setCollapseRowSpan(false);
	      
        exporterXls.setConfiguration(configuration);
	      
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.JASPER_PRINT, print);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.OUTPUT_FILE_NAME, xlsPath);
//	      
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.OFFSET_X,0);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.OFFSET_Y,0);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, false);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
//	      exporterXls.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
	      //exporterXls.setParameter(JRXlsExporterParameter.is, true);
				 
				exporterXls.exportReport();
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
	      
//	      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.JASPER_PRINT, print);
//	      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.OUTPUT_FILE, xlsxFile);
//	      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	      
	      xlsxExporter.exportReport(); //File is generated Correctly
	    }
	    catch (JRException e) {
	      
	      e.printStackTrace();
	    } finally {

	    }
	  }
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public StoreAddressService getStoreAddressService() {
		return storeAddressService;
	}

	public void setStoreAddressService(StoreAddressService storeAddressService) {
		this.storeAddressService = storeAddressService;
	}
    
	
	// new added
	@Override
	@RequestMapping(value = "/taxsummarydata/report", method = RequestMethod.GET)
	public void getTaxSummaryReport(@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") String storeId,
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
			baseFileName = "Tax_Summary";
			jasperFile = "Tax_Summary.jrxml";

			Map<String, Object> parameters = new HashMap<>();
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
        if (is != null) { is.close(); }
      } catch (IOException e) {
        
        e.printStackTrace();
      }
		}
	}
	


}

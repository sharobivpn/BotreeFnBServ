package com.botree.restaurantapp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;

import com.botree.restaurantapp.dao.OrdersDAO;
import com.botree.restaurantapp.dao.OrdersDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.OrderType;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.model.PaymentType;
import com.botree.restaurantapp.model.Sales;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.Tax;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.MenuService;
import com.botree.restaurantapp.service.OrderService;
import com.botree.restaurantapp.service.exception.ServiceException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@ManagedBean
public class OrderController {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	private String actionBtn;
	private OrderService orderService;
	private MenuService menuService;
	private OrdersDAO ordersDAO;
	private boolean showUserPane = false;
	private List<OrderMaster> orderLists = new ArrayList<OrderMaster>();
	private List<OrderMaster> orderdeliveredList = new ArrayList<OrderMaster>();
	private List<OrderType> orderTypeList = new ArrayList<OrderType>();
	private List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
	private List<OrderMaster> orderLst;
	private OrderMaster order;
	private OrderMaster orderMaster;
	private OrderMaster orderAdmin;
	private Bill bill;
	private Tax tax;
	private Customer customer;
	private MenuItem item;
	private OrderItem orderItem;
	private OrderType orderType;
	private OrderType orderType1;
	private PaymentType paymentType;
	private PaymentType paymentType1;
	private String redirect;
	private String action;
	private int orderId;

	private int storeid;
	private String storeName = null;
	private Map<Integer, Boolean> select = new HashMap<Integer, Boolean>();
	List<OrderItem> itemList = new ArrayList<OrderItem>();
	private String dateTime;
	private OrderMaster fromOrder = null;
	private OrderMaster toOrder = null;
	private boolean showOrderReportPane = false;
	private boolean showMsgPaneNoOrdrs = false;
	private List<OrderMaster> orderListsByDate = new ArrayList<OrderMaster>();

	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private List<SelectItem> selectItems1 = new ArrayList<SelectItem>();
	private List<SelectItem> menuItemList = new ArrayList<SelectItem>();
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();
	private List<OrderMaster> adminorderLst = new ArrayList<OrderMaster>();
	private String onclick = "";
	private OrderMaster viewDetailsorder;
	private Sales sales = new Sales();
	private EntityManager em = null;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	private String paymentDone = "";
	private double tenderAmt = 0.0;
	private boolean showCart = false;
	double sumTotal = 0.0f;
	double totalPrice = 0.0f;
	double subValue = 0.0;
	HttpSession httpSession11 = null;

	public OrderController() {

		System.out.println("in OrderController ");
	}

	@PostConstruct
	public void postConstruct() {

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster selectedStore = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			storeid = selectedStore.getId();

			Map<String, String> params = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			String actionName = params.get("action_name");

			if (actionName != null && actionName != "") {
				HttpSession session = (HttpSession) context
						.getExternalContext().getSession(true);
				session.setAttribute("param_order", actionName);
			}

			context.getExternalContext().getSessionMap()
					.put("ckeckClick", "false");
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			System.out.println("redirect value:: " + redirect);

			HttpServletRequest req = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpSession session = req.getSession();
			String param = (String) session.getAttribute("param_order");
			if (param != null) {
				if (param.equalsIgnoreCase("ordercreation")) {
					getAllMenuItems();
					getOrderTypes();
					getPaymentTypes();
					getAllOrderTypes();
					getAllPyamentTypes();
				}
				if (param.equalsIgnoreCase("deliveredorders")) {
					getDeliveredOrders();
				}

				if (param.equalsIgnoreCase("orders")) {
					if (storeMaster != null) {
						storeid = storeMaster.getId();
						System.out.println("in getAllOrders: store id: "
								+ storeid);
						orderLists = orderService.getAllOrders(storeid);

					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("hello12345");
		}
		// return "disp_order_page";
	}

	public String dispOrder() {
		System.out.println("in dispOrder  :" + storeid);
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		redirect = "disp_order_page";

		return redirect;

	}

	/**************** Get all Operations of admin side order creation **********/

	public String orderCreation() {

		showCart = false;
		FacesContext context1 = FacesContext.getCurrentInstance();
		context1.getExternalContext().getSessionMap().put("showCart", showCart);
		redirect = "create_order_page";

		double count1 = 0.0;
		context1.getExternalContext().getSessionMap().put("sum", count1);

		/*
		 * System.out.println("order is:: " + order); int itemId =
		 * order.getOrderItem1().getItem().getId(); if (itemId > 0) {
		 * order.getOrderItem1().setItem(new MenuItem());
		 * order.getOrderItem1().setTotalPriceByItem(0.0); }
		 */
		actionBtn = "orderCreation";
		List<OrderMaster> sessAdminorderLst = new ArrayList<OrderMaster>();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);
		// set the new cart list
		session.setAttribute("orderList", sessAdminorderLst);
		return redirect;
	}

	public String createOrderAdmin() {
		System.out.println("enter new order for admin side 00000000");

		try {
			System.out.println("enter new order for admin side");
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			if (storeMaster != null) {
				order.setStoreId(storeMaster.getId());

				System.out.println("enter new order for admin side 111");
				// OrderMaster oldOrder = orderService.getOrderById(oldOrderId);
				// if (oldOrder == null) {
				// order.setId(-1);
				// orderId = ordersDAO.createOrderAdmin(order);//new
				// } else
				{

					order.setId(-1);
					orderId = ordersDAO.createOrderAdmin(order);// update
				}
			}
			System.out.println("New Admin Side Order No.: " + orderId);
			// context.getExternalContext().getSessionMap().remove("selectedstore");
			order.getOrderList().clear();
			order.setCustomerName(" ");
			order.setCustomerContact(" ");
			order.setDeliveryAddress(" ");

			HttpServletRequest req = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpSession session = req.getSession();
			session.setAttribute("param_order", "orders");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "/page/disp_order.xhtml?faces-redirect=true";
	}

	public String addToCart() {
		System.out.println("in addToCart::");
		double netPriceEachItem = 0.0;
		showCart = true;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);
		String chkClck = (String) context.getExternalContext().getSessionMap()
				.get("ckeckClick");
		System.out.println("chk click:: " + chkClck);
		try {
			String clickEvnt = getOnclick();
			System.out.println("click event:: " + clickEvnt);

			int itemId = order.getOrderItem1().getItem().getId();
			OrdersDAO ordersDAO = new OrdersDAOImpl();
			MenuItem item = ordersDAO.getItemNameById(itemId);
			OrderMaster newOrder = new OrderMaster();
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantityOfItem(order.getOrderItem1()
					.getQuantityOfItem());
			orderItem.setTotalPriceByItem((order.getOrderItem1()
					.getTotalPriceByItem()));
			String promotionFlag = item.getPromotionFlag();

			totalPrice = order.getOrderItem1().getTotalPriceByItem();

			// calculate taxes
			Double itemServiceTax = item.getServiceTax();
			Double itemDiscPer = new Double(item.getPromotionValue());
			Double itemDisc = (itemDiscPer * totalPrice) / 100;
			Double serviceTaxForThsItem = (itemServiceTax * (totalPrice - itemDisc)) / 100;

			Double itemVat = item.getVat();
			Double vatForThsItem = (itemVat * (totalPrice - itemDisc)) / 100;

			netPriceEachItem = totalPrice + serviceTaxForThsItem
					+ vatForThsItem;
			orderItem.setNetPrice(netPriceEachItem);

			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpSession httpSession = request.getSession(false);

			if (httpSession != null) {
				System.out
						.println("if------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				// FacesContext context2 = FacesContext.getCurrentInstance();
				HttpServletRequest request1 = (HttpServletRequest) context
						.getExternalContext().getRequest();
				HttpSession httpSession1 = request1.getSession(false);
				if (httpSession1.getAttribute("sum") == null) {
					context.getExternalContext().getSessionMap()
							.put("sum", totalPrice);
				} else {
					// doble count =httpSession1.getAttribute("sum");
					HttpServletRequest request11 = (HttpServletRequest) context
							.getExternalContext().getRequest();
					HttpSession httpSession11 = request11.getSession(false);
					double count = NumberUtils.toDouble(String
							.valueOf(httpSession11.getAttribute("sum")));
					sumTotal = totalPrice + count;
					context.getExternalContext().getSessionMap()
							.put("sum", sumTotal);

				}

			} else {
				System.out
						.println("else---------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				// httpSession.setAttribute("sum", totalPrice);
				HttpServletRequest request1 = (HttpServletRequest) context
						.getExternalContext().getRequest();
				HttpSession httpSession1 = request1.getSession(false);
				if (httpSession1.getAttribute("sum") == null) {
					context.getExternalContext().getSessionMap()
							.put("sum", sumTotal);
				} else {

					HttpServletRequest request11 = (HttpServletRequest) context
							.getExternalContext().getRequest();
					HttpSession httpSession11 = request11.getSession(false);
					double count = NumberUtils.toDouble(String
							.valueOf(httpSession11.getAttribute("sum")));

					sumTotal += count;
					context.getExternalContext().getSessionMap()
							.put("sum", sumTotal);

				}

			}

			System.out.println("sumTotal.........................." + sumTotal);
			System.out.println("promotion flag:: " + promotionFlag);
			if (promotionFlag.equalsIgnoreCase("n")) {

				item.setPromotionValue(0);
			}

			orderItem.setItem(item);
			// set order item in order object
			newOrder.setOrderItem1(orderItem);
			System.out.println("item name::  " + item.getName());
			// get the list in session
			adminorderLst = (List<OrderMaster>) session.getAttribute("orderList");

			adminorderLst.add(newOrder);
			session.setAttribute("orderList", adminorderLst);

			order.setOrderList(adminorderLst);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<OrderMaster> newLst = adminorderLst;
		// put the new list in the session
		context.getExternalContext().getSessionMap().put("newList", newLst);
		context.getExternalContext().getSessionMap().put("showCart", showCart);
		// context.getExternalContext().getSessionMap().put("sum", sumTotal);

		return null;
	}

	public String delFrmCart(String itemid) {
		double sumbtotal = 0.0;

		System.out.println("in delFrmCart::");
		System.out.println("ietm id::: " + itemid);
		int itemId = Integer.parseInt(itemid);

		FacesContext context1 = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context1
				.getExternalContext().getRequest();

		HttpSession httpSession11 = request.getSession(false);
		double count = NumberUtils.toDouble(String.valueOf(httpSession11
				.getAttribute("sum")));

		FacesContext context = FacesContext.getCurrentInstance();
		List<OrderMaster> orderLst = (List<OrderMaster>) context.getExternalContext().getSessionMap().get("newList");

		try {
			if (orderLst != null && orderLst.size() > 0) {
				Iterator<OrderMaster> iterator = orderLst.iterator();
				while (iterator.hasNext()) {
					OrderMaster orderMaster = (OrderMaster) iterator.next();
					OrderItem orderItem = orderMaster.getOrderItem1();
					sumTotal = orderItem.getTotalPriceByItem();
					sumbtotal = count - sumTotal;
					context1.getExternalContext().getSessionMap()
							.put("sum", sumbtotal);
					MenuItem item = orderItem.getItem();
					System.out.println("item id::: " + item.getId());
					if (itemId == item.getId()) {
						orderLst.remove(orderMaster);
						break;
					}

				}
				// assign the new list to admin order list
				adminorderLst = orderLst;
				if (orderLst.size() == 0) {
					showCart = false;
					context.getExternalContext().getSessionMap()
							.put("showCart", showCart);
				} else {
				}
				// put the new list in the session
				// new added line
				context.getExternalContext().getSessionMap()
						.put("newList", orderLst);
				// check Session for delete record
				// context.getExternalContext().getSessionMap().put("deleteSSion",
				// deleteSS);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void getAllOrderTypes() {

		try {
			System.out.println("Enter getAllOrderTypes");
			System.out.println("select items size:" + selectItems.size());

			orderTypeList = orderService.getAdminOrderType();

			Iterator<OrderType> iterator = orderTypeList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				OrderType orderType = (OrderType) iterator.next();

				si.setValue(orderType.getId());
				System.out.println("Order Type id" + orderType.getId());
				si.setLabel(orderType.getOrderTypeName());
				System.out.println("Order Type Name"
						+ orderType.getOrderTypeName());

				selectItems.add(si);

			}

			System.out.println("exit getAllOrderTypes");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("test100");
		}

	}

	public void getAllPyamentTypes() {

		try {
			System.out.println("Enter getAllPyamentTypes");
			System.out.println("select items size:" + selectItems.size());

			paymentTypeList = orderService.getAdminPaymentType();
			System.out.println("No. of Payment Types :"
					+ paymentTypeList.size());
			Iterator<PaymentType> itrType = paymentTypeList.iterator();
			while (itrType.hasNext()) {
				PaymentType paymntType = (PaymentType) itrType.next();

				SelectItem si = new SelectItem();

				si.setValue(paymntType.getId());
				System.out.println("paymntType id" + paymntType.getId());
				si.setLabel(paymntType.getPaymentTypeName());
				System.out.println("paymntType name"
						+ paymntType.getPaymentTypeName());

				selectItems1.add(si);

			}
			System.out.println("exit getAllPyamentTypes");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("test100");
		}

	}

	public void getAllMenuItems() {

		try {
			System.out.println("Enter getAllMenuItems");

			menuItems = menuService.getAllItems();

			System.out.println("number of items:" + menuItems.size());
			Iterator<MenuItem> iterator = menuItems.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				MenuItem menuItem = (MenuItem) iterator.next();

				si.setValue(menuItem.getId());
				System.out.println("item id" + menuItem.getId());
				si.setLabel(menuItem.getName());
				System.out.println("items" + menuItem.getName());

				menuItemList.add(si);

			}
			System.out.println("exit getAllMenuItems");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("In Exception of getAllMenuItems");
		}

	}

	public String dispOrderReport() {
		System.out.println("in dispOrderReport  :" + storeid);
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		return "disp_order_report";
	}

	public String dispSalesReport() {
		System.out.println("in dispSalesReport  :" + storeid);
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		return "disp_sales_report";
	}

	public String dispSalesReportDaily() {
		System.out.println("in dispSalesReportDaily  :" + storeid);
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		return "disp_daywise_sales_report";
	}

	public String dispOrderReportDaily() {

		System.out.println("in dispOrderReportDaily  :" + storeid);
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		return "disp_daywise_order_report";

	}

	public String dispItemWiseDailyOrderReport() {
		
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		return "disp_daywise_order_report";

	}

	/*
	 * public String dispItemReportDaily() {
	 * System.out.println("in dispItemReportDaily  :" + storeid); FacesContext
	 * context = FacesContext.getCurrentInstance();
	 * 
	 * Map<String, String> params = context.getExternalContext()
	 * .getRequestParameterMap(); if (params.get("storeId") != null) { storeid =
	 * Integer.parseInt(params.get("storeId")); }
	 * System.out.println("store id: " + storeid);
	 * 
	 * return "disp_daywise_order_report1"; }
	 */

	public String cancelOrderReportDaily() {
		System.out.println("in cancelOrderReportDaily  :" + storeid);
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		return "disp_sales_cancellation";
	}

	public String itemOrderReport() {
		System.out.println("in itemOrderReport  :" + storeid);
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.get("storeId") != null) {
			storeid = Integer.parseInt(params.get("storeId"));
		}
		System.out.println("store id: " + storeid);

		return "disp_sales_item";
	}

	public String getOrderReport() {

		try {
			System.out.println("enter getOrderReport::");

			System.out.println("from order date:: " + fromOrder.getOrderDate());
			System.out.println("to order date:: " + toOrder.getOrderDate());
			showOrderReportPane = true;

			orderListsByDate = orderService.getOrdersByDate(fromOrder, toOrder);

			if (orderListsByDate.size() == 0) {

				showOrderReportPane = false;
				showMsgPaneNoOrdrs = true;
			}

			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap()
					.put("orderListsByDate", orderListsByDate);

			System.out.println("exit getOrderReport");
		} catch (ServiceException e) {

			e.printStackTrace();
		}
		// daySpecial2=new DaySpecial();
		// return "/page/disp_special_page?redirect=true";

		return "";
	}

	public String getSalesReport() throws IOException {

		System.out.println("enter getSalesReport::");
		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext
					.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext
					.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}
			sales.setStoreId(storeid);
			sales.setStoreName(storeName);

			System.out.println("from order date:: " + sales.getSalesFrmDate());
			String frmdate = formatter.format(sales.getSalesFrmDate());
			System.out.println("formatted date " + frmdate);
			System.out.println("to order date:: " + sales.getSalesToDate());
			String todate = formatter.format(sales.getSalesToDate());
			System.out.println("to order date:: " + todate);

			Map<String, Object> parameters = new HashMap<>();

			if (sales.getSalesReportPeriodType().equalsIgnoreCase("daily")) {
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId",
						Integer.toString(sales.getStoreId()));
				fileName = "sales_report.pdf";
				jasperFile = "restaurant_sales.jrxml";
			}

			else if (sales.getSalesReportPeriodType()
					.equalsIgnoreCase("yearly")) {
				System.out.println("yearly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId",
						Integer.toString(sales.getStoreId()));
				fileName = "sales_report_yearly.pdf";
				jasperFile = "restaurant_sales_yearly.jrxml";
			}

			else if (sales.getSalesReportPeriodType().equalsIgnoreCase(
					"monthly")) {
				System.out.println("monthly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId",
						Integer.toString(sales.getStoreId()));
				fileName = "sales_report_monthly.pdf";
				jasperFile = "restaurant_sales_monthly.jrxml";
			}
			// open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters,
					connection, jasperFile);

			System.out.println("exit getSalesReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					if (em != null)
						em.close();
				}
			} catch (SQLException se) {
			}
		}

		return "";
	}

	public String getItemsReport() throws IOException {

		System.out.println("enter getSalesReport::");
		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext
					.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext
					.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}
			sales.setStoreId(storeid);
			sales.setStoreName(storeName);

			System.out.println("from order date:: " + sales.getSalesFrmDate());
			String frmdate = formatter.format(sales.getSalesFrmDate());
			System.out.println("formatted date " + frmdate);
			System.out.println("to order date:: " + sales.getSalesToDate());
			String todate = formatter.format(sales.getSalesToDate());
			System.out.println("to order date:: " + todate);

			Map<String, Object> parameters = new HashMap<>();

			if (sales.getSalesReportPeriodType().equalsIgnoreCase("daily")) {
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId",
						Integer.toString(sales.getStoreId()));
				fileName = "sales_item_report.pdf";
				jasperFile = "restaurant_item_daywise.jrxml";
			}

			else if (sales.getSalesReportPeriodType()
					.equalsIgnoreCase("yearly")) {
				System.out.println("yearly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId",
						Integer.toString(sales.getStoreId()));
				fileName = "sales_item_report_yearly.pdf";
				jasperFile = "restaurant_item_yearly.jrxml";
			}

			else if (sales.getSalesReportPeriodType().equalsIgnoreCase(
					"monthly")) {
				System.out.println("monthly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId",
						Integer.toString(sales.getStoreId()));
				fileName = "sales_item_report_monthly.pdf";
				jasperFile = "restaurant_item_monthly.jrxml";
			}
			// open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters,
					connection, jasperFile);

			System.out.println("exit getSalesReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					if (em != null)
						em.close();
				}
			} catch (SQLException se) {
			}
		}

		return "";
	}

	public String getSalesReportForCancel() throws IOException {

		System.out.println("enter getSalesReport::");
		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext
					.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext
					.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}
			sales.setStoreId(storeid);
			sales.setStoreName(storeName);

			System.out.println("from order date:: " + sales.getSalesFrmDate());
			String frmdate = formatter.format(sales.getSalesFrmDate());
			System.out.println("formatted date " + frmdate);
			System.out.println("to order date:: " + sales.getSalesToDate());
			String todate = formatter.format(sales.getSalesToDate());
			System.out.println("to order date:: " + todate);

			Map<String, Object> parameters = new HashMap<>();

			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", Integer.toString(sales.getStoreId()));
			fileName = "sales_report_cancellation.pdf";
			jasperFile = "restaurant_sales_cancellation.jrxml";

			// open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters,
					connection, jasperFile);

			System.out.println("exit getSalesReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					em.close();
				}
			} catch (SQLException se) {
			}
		}

		return "";
	}

	public String getTodaysSalesReport() throws IOException {

		System.out.println("enter getTodaysSalesReport::");

		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext
					.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext
					.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}

			String salesDate = formatter.format(sales.getSalesDate());
			System.out.println("sales date:: " + salesDate);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", salesDate);
			// parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", Integer.toString(storeid));
			fileName = "sales_report_daily.pdf";
			jasperFile = "restaurant_sales_daily.jrxml";

			// open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters,
					connection, jasperFile);

			System.out.println("exit getTodaysSalesReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					em.close();
				}
			} catch (SQLException se) {
			}
		}

		return "";
	}

	public String getTodaysOrderReport1() throws IOException {

		System.out.println("enter getTodaysOrderReport::");

		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
		String selectSQLForCustomerDisc = null;
		Statement st = null;
		ResultSet rs = null;
		String totalCustDiscount = null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext
					.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext
					.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}

			String salesDate = formatter.format(sales.getSalesDate());
			System.out.println("sales date:: " + salesDate);

			selectSQLForCustomerDisc = "SELECT sum(bill.customer_discount) as total_cust_disc FROM restaurant.bp_t_bill bill inner join fo_t_orders o on bill.order_id=o.id where o.store_id="
					+ storeid
					+ " and o.order_date='"
					+ salesDate
					+ "' group by o.order_date";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			while (rs.next()) {
				totalCustDiscount = rs.getString("total_cust_disc");

			}
			// System.out.println("totalCustDiscount:::"+totalCustDiscount+"count::"+count);

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", salesDate);
			parameters.put("W_CustDisc", totalCustDiscount);

			// parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", Integer.toString(storeid));
			fileName = "item_report_daily.pdf";
			jasperFile = "restaurant_item_daily.jrxml";

			// open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters,
					connection, jasperFile);

			System.out.println("exit getTodaysOrderReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					em.close();
				}
			} catch (SQLException se) {
			}
		}

		return "";

	}

	public String getTodaysOrderReport() throws IOException {
		// for item report method

		System.out.println("enter getTodaysOrderReport::");

		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
		String selectSQLForCustomerDisc = null;
		String selectSQLForCustomerCancl=null;
		String selectSQLForRoundoff=null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String totalCustDiscount = null;
		String totalCanclAmount = null;
		String totalRoundAmount=null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext
					.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext
					.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}

			String salesDate = formatter.format(sales.getSalesDate());
			System.out.println("sales date:: " + salesDate);

			selectSQLForCustomerDisc = "SELECT sum(bill.customer_discount) as total_cust_disc FROM restaurant.bp_t_bill bill inner join fo_t_orders o on bill.order_id=o.id where o.store_id="
					+ storeid
					+ " and o.order_date='"
					+ salesDate
					+ "' group by o.order_date";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			while (rs.next()) {
				totalCustDiscount = rs.getString("total_cust_disc");

			}
			
			if(totalCustDiscount==null){
				
				totalCustDiscount="0.00";
			}
			
			selectSQLForCustomerCancl="select sum(oi.net_price) as total from  restaurant.fo_t_orders_item oi inner join fo_t_orders o on oi.order_id=o.id where o.store_id="+storeid+" and o.order_date='"+salesDate+"' and  o.cancel='Y'";
			st = connection.createStatement();
			rs1 = st.executeQuery(selectSQLForCustomerCancl);
			System.out.println("selectSQLForCustomerCancl:::"+selectSQLForCustomerCancl);
			if (rs1.next()) {
				totalCanclAmount = rs1.getString("total");
			}
			
			st.close();
			
      if(totalCanclAmount==null) {
      	totalCanclAmount="0.00";
			}
            
      selectSQLForRoundoff = "SELECT sum(b.round_off_amt) as roundoff FROM restaurant.bp_t_bill b left join fo_t_orders o on o.id=b.order_id where o.order_date ='"+salesDate+"' and o.store_id="+storeid+"";
      st = connection.createStatement();
			rs2 = st.executeQuery(selectSQLForRoundoff);
			System.out.println("selectSQLForRoundoff:::"+selectSQLForRoundoff);
			if (rs2.next()) {
				totalRoundAmount = rs2.getString("roundoff");
			}
			st.close();
			
      if(totalRoundAmount==null){
        	totalRoundAmount="0.00";
			}
            
            
			// System.out.println("totalCustDiscount:::"+totalCustDiscount+"count::"+count);
            System.out.println("round off::  "+totalRoundAmount);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_Date", salesDate);
			parameters.put("W_CustDisc", totalCustDiscount);
			parameters.put("W_CancPrice", totalCanclAmount);
			parameters.put("W_roundoff", totalRoundAmount);
			// parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", Integer.toString(storeid));
			fileName = "item_report_daily.pdf";
			jasperFile = "restaurant_item_daily.jrxml";

			// open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters,
					connection, jasperFile);

			System.out.println("exit getTodaysOrderReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					em.close();
				}
			} catch (SQLException se) {
			}
		}

		return "";
	}
	
	//period wise item  report
	
	public String getItemwiseSalesReport() throws IOException {
		// for item report method

		System.out.println("enter getTodaysOrderReport::");

		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
		String selectSQLForCustomerDisc = null;
		String selectSQLForCustomerCancl=null;
		String selectSQLForRoundoff=null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String totalCustDiscount = null;
		String totalCanclAmount = null;
		String totalRoundAmount=null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext
					.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext
					.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}

			System.out.println("from order date:: " + sales.getSalesFrmDate());
			String frmdate = formatter.format(sales.getSalesFrmDate());
			System.out.println("formatted date " + frmdate);
			System.out.println("to order date:: " + sales.getSalesToDate());
			String todate = formatter.format(sales.getSalesToDate());
			System.out.println("to order date:: " + todate);
			
			selectSQLForCustomerDisc = "SELECT sum(bill.customer_discount) as total_cust_disc FROM restaurant.bp_t_bill bill inner join fo_t_orders o on bill.order_id=o.id where o.store_id="
					+ storeid
					+ " AND o.order_date BETWEEN'"
					+ frmdate +"'  AND '" + todate
					+ "' group by o.order_date";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			if (rs.next()) {
				totalCustDiscount = rs.getString("total_cust_disc");
			}
			st.close();
			
			if(totalCustDiscount==null) {
				totalCustDiscount="0.00";
			}
			
			selectSQLForCustomerCancl="select sum(oi.net_price) as total from  restaurant.fo_t_orders_item oi inner join fo_t_orders o on oi.order_id=o.id where o.store_id="+storeid+"AND o.order_date BETWEEN'"
					+ frmdate +"'  AND '" + todate
					+ "'+ and  o.cancel='Y'";
			st = connection.createStatement();
			rs1 = st.executeQuery(selectSQLForCustomerCancl);
			System.out.println("selectSQLForCustomerCancl:::"+selectSQLForCustomerCancl);
			while (rs1.next()) {
				totalCanclAmount = rs1.getString("total");
			}
			st.close();
			
      if(totalCanclAmount==null){
      	totalCanclAmount="0.00";
			}
            
      selectSQLForRoundoff="SELECT sum(b.round_off_amt) as roundoff FROM restaurant.bp_t_bill b left join fo_t_orders o on o.id=b.order_id where o.order_date BETWEEN'"
					+ frmdate +"'  AND '" + todate
					+ "' and o.store_id="+storeid+"";

      st = connection.createStatement();
			rs2 = st.executeQuery(selectSQLForRoundoff);
			System.out.println("selectSQLForRoundoff:::"+selectSQLForRoundoff);
			if (rs2.next()) {
				totalRoundAmount = rs2.getString("roundoff");
			}
			st.close();
			
      if(totalRoundAmount==null){
      	totalRoundAmount="0.00";
			}
            
			// System.out.println("totalCustDiscount:::"+totalCustDiscount+"count::"+count);
      System.out.println("round off::  "+totalRoundAmount);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_CustDisc", totalCustDiscount);
			parameters.put("W_CancPrice", totalCanclAmount);
			parameters.put("W_roundoff", totalRoundAmount);
			parameters.put("W_StoreId", Integer.toString(storeid));
			//TODO:Change file .
			fileName = "item_report_daily.pdf";
			jasperFile = "restaurant_item_daily.jrxml";

			// open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters, connection, jasperFile);

			System.out.println("exit getTodaysOrderReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			// finally block used to close resources
			try {
        if(rs != null) rs.close();
        if(rs1 != null) rs1.close();
        if(rs2 != null) rs2.close();
			  if(st != null) st.close();
				if (stmt != null) {
					stmt.close();
					connection.close();
					em.close();
				}
			} catch (SQLException se) {
			}
		}

		return "";
	}

	/**
	 * @param context
	 * @param request
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void generatePDF(FacesContext context, HttpServletRequest request,
			HttpServletResponse response, String fileName,
			Map<String, Object> parameters, Connection connection,
			String jasperFile) throws FileNotFoundException, IOException {
	  
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {

			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);

			JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + jasperFile);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
			JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);

			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		}

		catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following
		// exception in the logs:
		// java.lang.IllegalStateException: Cannot forward after response has
		// been committed.
		context.responseComplete();
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail
				// it. It may be useful to
				// know that this will generally only be thrown when the client
				// aborted the download.
				e.printStackTrace();
			}
		}
	}

	/*****************************
	 * Get update status of delivered orders******************************
	 * 
	 */

	public void getDeliveredOrders() {

		try {
			// System.out.println("In getDeliveredOrders");

			orderdeliveredList = orderService.getDeliveredOrders();
			// System.out.println("number of orders:" + orderLists.size());
			Iterator<OrderMaster> iterator = orderLists.iterator();
			while (iterator.hasNext()) {
//				OrderMaster orderMaster = (OrderMaster) 
				    iterator.next();
				/*
				 * System.out.println("Delivery Address:" +
				 * orderMaster.getDeliveryAddress());
				 * System.out.println("Delivery deliver time:" +
				 * orderMaster.getDeliveryTime());
				 * System.out.println("Delivery from time:" +
				 * orderMaster.getFromTime());
				 * System.out.println("Delivery To time:" +
				 * orderMaster.getToTime()); System.out.println("Delivery flag:"
				 * + orderMaster.getFlag());
				 */
			}
			// System.out.println("out getDeliveredOrders");

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("hello12345");
		}
	}

	public String dispDeliveredOrder() {
		System.out.println("In dispDeliveredOrder");
		return "disp_delivered_order";

	}

	public void getItemPriceById() {
		System.out.println("In getItemPriceById::  "
				+ order.getOrderItem1().getItem().getId());
		System.out.println("In getItemPriceById name::  "
				+ order.getOrderItem1().getItem().getName());

		try {
			int itemId = order.getOrderItem1().getItem().getId();
			OrdersDAO dao = new OrdersDAOImpl();
			MenuItem selectedItem = dao.getItemPriceById(itemId);

			String promotionFlag = selectedItem.getPromotionFlag();
			System.out.println("promotion flag:: " + promotionFlag);
			if (promotionFlag.equalsIgnoreCase("n")) {
				System.out.println("zscsdgd");
				selectedItem.setPromotionValue(0);
			}
			order.getOrderItem1().setItem(selectedItem);
			// set default quantity to 1
			order.getOrderItem1().setQuantityOfItem("1");
			// set initial total price of item
			order.getOrderItem1().setTotalPriceByItem(
					Double.valueOf((order.getOrderItem1().getQuantityOfItem()))
							* (order.getOrderItem1().getItem().getPrice()));
			System.out.println("price::  "
					+ order.getOrderItem1().getItem().getPrice());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return null;

	}

	public void getChangeAmt() {
		System.out.println("In getItemPriceById::  "
				+ order.getOrderItem1().getItem().getId());

		try {
			int itemId = order.getOrderItem1().getItem().getId();
			OrdersDAO dao = new OrdersDAOImpl();
			MenuItem selectedItem = dao.getItemPriceById(itemId);

			String promotionFlag = selectedItem.getPromotionFlag();
			System.out.println("promotion flag:: " + promotionFlag);
			if (promotionFlag.equalsIgnoreCase("n")) {
				System.out.println("zscsdgd");
				selectedItem.setPromotionValue(0);
			}
			order.getOrderItem1().setItem(selectedItem);
			// set default quantity to 1
			order.getOrderItem1().setQuantityOfItem("1");
			// set initial total price of item
			order.getOrderItem1().setTotalPriceByItem(
					Double.valueOf((order.getOrderItem1().getQuantityOfItem()))
							* (order.getOrderItem1().getItem().getPrice()));
			System.out.println("price::  "
					+ order.getOrderItem1().getItem().getPrice());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return null;

	}

	public void getPromotionVal() {
		System.out.println("In getPromotionVal::  "
				+ order.getOrderItem1().getItem().getPromotionValue());

		try {
			String promotionFlag = order.getOrderItem1().getItem()
					.getPromotionFlag();
			System.out.println("promotion flag:: " + promotionFlag);
			if (promotionFlag.equalsIgnoreCase("n")) {
				System.out.println("zscsdgd");
				order.getOrderItem1().getItem().setPromotionValue(0);
			}
			/*
			 * int itemId = order.getOrderItem1().getItem().getId(); OrdersDAO
			 * dao = new OrdersDAOImpl(); MenuItem selectedItem =
			 * dao.getItemPriceById(itemId); double price =
			 * selectedItem.getPrice();
			 * order.getOrderItem1().setItem(selectedItem);
			 * System.out.println("price::  " +
			 * order.getOrderItem1().getItem().getPrice());
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return null;

	}

	public void calculateItemPrice() {
		System.out.println("In calculatePrice::  "
				+ order.getOrderItem1().getQuantityOfItem());
		System.out.println("In calculatePrice::  "
				+ order.getOrderItem1().getItem().getPrice());

		try {
			if (order.getOrderItem1().getQuantityOfItem() != null
					&& order.getOrderItem1().getQuantityOfItem() != "") {
				int quantityOfItem = Integer.parseInt(order.getOrderItem1()
						.getQuantityOfItem());
				double rate = order.getOrderItem1().getItem().getPrice();
				double totalPrce = quantityOfItem * rate;
				System.out.println("total prce::  " + totalPrce);
				order.getOrderItem1().setTotalPriceByItem(totalPrce);

				System.out.println("price::  "
						+ order.getOrderItem1().getItem().getPrice());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String updateDeliveryStatus() {
		System.out.println("In updateDeliveryStatus");
		System.out.println("OrderID for Status:" + orderId);
		try {
			for (OrderMaster order : orderLists) {
				if (select.get(order.getId()).booleanValue()) {
					System.out
							.println("OrderController.updateDeliveryStatus1111");
					orderService.updateDeliveryStatus(order);
					System.out
							.println("OrderController.updateDeliveryStatus222222");
				}
			}
			System.out.println("OrderController.updateDeliveryStatus333333");
			select.clear();
		} catch (ServiceException e) {

			e.printStackTrace();
		}
		System.out.println("Out updateDeliveryStatus");

		return "/page/disp_order.xhtml?faces-redirect=true";

	}

	public String showItemList() {

		System.out.println("In showItemList");
		System.out.println("Order ID :" + orderId);
		System.out.println("action:" + action);
		System.out.println("going to");
		return "/page/item_view.xhtml?faces-redirect=true";
	}

	/*****************************
	 * Get info about all Order type operations******************************
	 * 
	 */
	public String dispOrderType() {
		System.out.println("in dispOrderType");

		return "/page/disp_order_type.xhtml?faces-redirect=true";

	}

	public void getOrderTypes() {
		try {
			// System.out.println("In OrderController.getOrderTypes");
			orderTypeList = orderService.getAdminOrderType();
			// System.out.println("No. of Order Types :" +
			// orderTypeList.size());
			Iterator<OrderType> itrType = orderTypeList.iterator();
			while (itrType.hasNext()) {
//				OrderType orderType = (OrderType) 
				    itrType.next();
				/*
				 * System.out.println("Order Type ID:" + orderType.getId());
				 * System.out.println("Order Type Name:" +
				 * orderType.getOrderTypeName());
				 * System.out.println("Order Type Flag:" +
				 * orderType.getStatusFlag());
				 */
			}
			// System.out.println("Out OrderController.getOrderTypes");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String addOrderType() {

		System.out.println("In addOrderType");
		orderType = new OrderType();
		return "/page/add_order_type.xhtml?faces-redirect=true";
	}

	public String createOrderType() {
		try {
			System.out.println("Enter orderController.createOrderType");
			orderService.createOrderType(orderType1);
			System.out.println("Exit orderController.createOrderType");
		} catch (Exception e) {

			e.printStackTrace();
		}
		orderType = new OrderType();
		return "/page/disp_order_type.xhtml?faces-redirect=true";

	}

	public String deleteOrderType() {
		try {
			System.out.println("Enter orderController.deleteOrderType");
			orderService.deleteOrderType(orderType);
			System.out.println("Exit orderController.deleteOrderType");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/page/disp_order_type.xhtml?faces-redirect=true";
	}

	public String updateOrderTypeFlagAsActive() {
		System.out.println("In updateDeliveryStatus");
		try {
			for (OrderType orderType1 : orderTypeList) {
				if (select.get(orderType1.getId()).booleanValue()) {
					System.out
							.println("OrderController.updateDeliveryStatus1111");
					orderService.updateOrderTypeFlagAsActive(orderType1);
					System.out
							.println("OrderController.updateDeliveryStatus222222");
				}
			}
			System.out.println("OrderController.updateDeliveryStatus333333");
			select.clear();
		} catch (ServiceException e) {

			e.printStackTrace();
		}
		System.out.println("Out updateDeliveryStatus");

		return "/page/disp_order_type.xhtml?faces-redirect=true";

	}

	public String updateOrderTypeFlagAsInActive() {
		System.out.println("In updateDeliveryStatus");
		try {
			for (OrderType orderType1 : orderTypeList) {
				if (select.get(orderType1.getId()).booleanValue()) {
					System.out
							.println("OrderController.updateDeliveryStatus1111");
					orderService.updateOrderTypeFlagAsInActive(orderType1);
					System.out
							.println("OrderController.updateDeliveryStatus222222");
				}
			}
			System.out.println("OrderController.updateDeliveryStatus333333");
			select.clear();
		} catch (ServiceException e) {

			e.printStackTrace();
		}
		System.out.println("Out updateDeliveryStatus");

		return "/page/disp_order_type.xhtml?faces-redirect=true";

	}

	/*****************************
	 * Get info about all payment type operations******************************
	 * 
	 */
	public String dispPaymentType() {
		System.out.println("in dispPaymentType");

		return "/page/disp_payment_type.xhtml?faces-redirect=true";

	}

	public void getPaymentTypes() {
		try {
			// System.out.println("In OrderController.getPaymentTypes");
			paymentTypeList = orderService.getAdminPaymentType();
			System.out.println("No. of Payment Types :"
					+ paymentTypeList.size());
			Iterator<PaymentType> itrType = paymentTypeList.iterator();
			while (itrType.hasNext()) {
//				PaymentType paymntType = (PaymentType) 
				    itrType.next();
				/*
				 * System.out.println("Payment Type ID :" + paymntType.getId());
				 * System.out.println("Payment Type Name :" +
				 * paymntType.getPaymentTypeName());
				 * System.out.println("Payment Type Staus Flag :" +
				 * paymntType.getStatusFlag());
				 */
			}
			System.out.println("Out OrderController.getPaymentTypes");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String addPaymentType() {

		System.out.println("In addPaymentType");
		return "/page/add_payment_type.xhtml?faces-redirect=true";
	}

	public String createPaymentType() {
		try {
			System.out.println("Enter orderController.createPaymentType");
			orderService.createPaymentType(paymentType1);
			System.out.println("Exit orderController.createPaymentType");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/page/disp_payment_type.xhtml?faces-redirect=true";

	}

	public String deletePaymentType() {
		try {
			System.out.println("Enter orderController.deletePaymentType");
			orderService.deletePaymentType(paymentType);
			System.out.println("Exit orderController.deletePaymentType");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/page/disp_payment_type.xhtml?faces-redirect=true";
	}

	public String updatePaymentTypeFlagAsActive() {
		System.out.println("In updateDeliveryStatus");
		try {
			for (PaymentType paymentType : paymentTypeList) {
				if (select.get(paymentType.getId()).booleanValue()) {
					System.out
							.println("OrderController.updateDeliveryStatus1111");
					orderService.updatePaymentTypeFlagAsActive(paymentType);
					System.out
							.println("OrderController.updateDeliveryStatus222222");
				}
			}
			System.out.println("OrderController.updateDeliveryStatus333333");
			select.clear();
		} catch (ServiceException e) {

			e.printStackTrace();
		}
		System.out.println("Out updateDeliveryStatus");

		return "/page/disp_payment_type.xhtml?faces-redirect=true";

	}

	public String updatePaymentTypeFlagAsInActive() {
		System.out.println("In updateDeliveryStatus");

		try {
			for (PaymentType paymentType : paymentTypeList) {
				if (select.get(paymentType.getId()).booleanValue()) {
					System.out
							.println("OrderController.updateDeliveryStatus1111");
					orderService.updatePaymentTypeFlagAsInActive(paymentType);
					System.out
							.println("OrderController.updateDeliveryStatus222222");
				}
			}
			System.out.println("OrderController.updateDeliveryStatus333333");
			select.clear();
		} catch (ServiceException e) {

			e.printStackTrace();
		}
		System.out.println("Out updateDeliveryStatus");

		return "/page/disp_payment_type.xhtml?faces-redirect=true";

	}

	public String pay() throws ServiceException {
		FacesContext context = FacesContext.getCurrentInstance();
		try {

			Map<String, String> params = context.getExternalContext()
					.getRequestParameterMap();
			String orderid = params.get("orderid");
			orderService.pay(orderid);

			// for message display success
			if (context.getExternalContext().getSessionMap().get("paymentDone") != null) {
				context.getExternalContext().getSessionMap()
						.remove("paymentDone");
			}
			context.getExternalContext().getSessionMap()
					.put("paymentDone", "true");
			System.out.println("exit OrderController.pay ");

		} catch (ServiceException e) {
			e.printStackTrace();
			// for message display failure
			if (context.getExternalContext().getSessionMap().get("paymentDone") != null) {
				context.getExternalContext().getSessionMap()
						.remove("paymentDone");
			}
			context.getExternalContext().getSessionMap()
					.put("paymentDone", "false");
			throw new ServiceException(
					"problem occurred while trying to create a new store", e);

		}

		return "/page/disp_order.xhtml?faces-redirect=true";

	}

	public String payCash() throws ServiceException {

		try {

			System.out.println("tender amty:: " + tenderAmt);

			Payment payment = new Payment();
			payment.setOrderPayment(order);
			payment.setAmount(order.getOrderBill().getGrossAmt());
			// calculate paid amount
			/*
			 * double tendrAmt = order.getOrderPayment().getTenderAmount();
			 * double amtToPay = order.getOrderPayment().getAmountToPay(); if
			 * (tendrAmt > amtToPay) { payment.setPaidAmount(amtToPay);
			 * payment.setAmountToPay(0.0);
			 * 
			 * } else if (tendrAmt < amtToPay) {
			 * payment.setPaidAmount(tendrAmt); payment.setAmountToPay(amtToPay
			 * - tendrAmt);
			 * 
			 * } payment.setTenderAmount(tendrAmt);
			 * payment.setPaymentMode("cash"); payment.setRemarks("done");
			 * payment.setStoreId(order.getStoreId());
			 * payment.setCreatedBy(order.getCustomers().getName());
			 * payment.setCreationDate(curDate);
			 */
			orderService.payCash(payment);

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to pay by cash", e);

		}

		return "/page/disp_order.xhtml?faces-redirect=true";

	}

	public String delete() {

		System.out.println("order id is...::" + order.getId());

		try {
			orderService.delete(order);

		} catch (ServiceException e) {

			e.printStackTrace();
		}

		return "/page/disp_order.xhtml?faces-redirect=true";

	}

	/*****************************
	 * getter and setter methods ******************************
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public OrdersDAO getOrdersDAO() {
		return ordersDAO;
	}

	public void setOrdersDAO(OrdersDAO ordersDAO) {
		this.ordersDAO = ordersDAO;
	}

	public boolean isShowUserPane() {
		return showUserPane;
	}

	public void setShowUserPane(boolean showUserPane) {
		this.showUserPane = showUserPane;
	}

	public List<OrderMaster> getOrderList() {
		return orderLists;
	}

	public void setOrderList(List<OrderMaster> orderList) {
		this.orderLists = orderList;
	}

	public List<OrderMaster> getOrderLst() {
		return orderLst;
	}

	public void setOrderLst(List<OrderMaster> orderLst) {
		this.orderLst = orderLst;
	}

	public OrderMaster getOrder() {
		/*
		 * if(order!=null) order=new OrderMaster();
		 * System.out.println("old order::: "+order);
		 */
	  
		// order.getOrderItem1().setItem(item);
		// if(orderStat == null)
		// orderStat = new OrderMaster();
		// else return orderStat;
		// return new OrderMaster();
		return order;
	}

	public void setOrder(OrderMaster order) {
		/*
		 * if (order.getId() != 0) { order = new OrderMaster(); MenuItem item =
		 * new MenuItem(); OrderItem orderItem = new OrderItem();
		 * orderItem.setItem(item); order.setOrderItem1(orderItem);
		 * order.setOrdertype(new OrderType()); order.setPaymentType(new
		 * PaymentType()); }
		 */

		this.order = order;
	}

	public OrderMaster getOrderMaster() {
		return orderMaster;
	}

	public void setOrderMaster(OrderMaster orderMaster) {
		this.orderMaster = orderMaster;
	}

	public OrderMaster getOrderAdmin() {
		return orderAdmin;
	}

	public void setOrderAdmin(OrderMaster orderAdmin) {
		this.orderAdmin = orderAdmin;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderMaster> getOrderLists() {
		return orderLists;
	}

	public void setOrderLists(List<OrderMaster> orderLists) {
		this.orderLists = orderLists;
	}

	public List<OrderMaster> getOrderdeliveredList() {
		return orderdeliveredList;
	}

	public void setOrderdeliveredList(List<OrderMaster> orderdeliveredList) {
		this.orderdeliveredList = orderdeliveredList;
	}

	public List<OrderType> getOrderTypeList() {
		return orderTypeList;
	}

	public void setOrderTypeList(List<OrderType> orderTypeList) {
		this.orderTypeList = orderTypeList;
	}

	public List<PaymentType> getPaymentTypeList() {
		return paymentTypeList;
	}

	public void setPaymentTypeList(List<PaymentType> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public OrderType getOrderType1() {
		return orderType1;
	}

	public void setOrderType1(OrderType orderType1) {
		this.orderType1 = orderType1;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentType getPaymentType1() {
		return paymentType1;
	}

	public void setPaymentType1(PaymentType paymentType1) {
		this.paymentType1 = paymentType1;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Map<Integer, Boolean> getSelect() {
		return select;
	}

	public void setSelect(Map<Integer, Boolean> select) {
		this.select = select;
	}

	public List<OrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItem> itemList) {
		this.itemList = itemList;
	}

	public int getStoreid() {
		return storeid;
	}

	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public List<SelectItem> getSelectItems1() {
		return selectItems1;
	}

	public void setSelectItems1(List<SelectItem> selectItems1) {
		this.selectItems1 = selectItems1;
	}

	public List<SelectItem> getMenuItemList() {
		return menuItemList;
	}

	public void setMenuItemList(List<SelectItem> menuItemList) {
		this.menuItemList = menuItemList;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public String getDateTime() {

		Date currentTime = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, MMM d, yyyy hh:mm:ss a");

		// System.out.println("current date:: "+currentTime);
		dateTime = sdf.format(currentTime);
		System.out.println("datetime:: " + dateTime);
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public OrderMaster getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(OrderMaster fromOrder) {
		this.fromOrder = fromOrder;
	}

	public OrderMaster getToOrder() {
		return toOrder;
	}

	public void setToOrder(OrderMaster toOrder) {
		this.toOrder = toOrder;
	}

	public boolean isShowOrderReportPane() {
		System.out.println("isShowOrderReportPane:: " + showOrderReportPane);
		return showOrderReportPane;
	}

	public void setShowOrderReportPane(boolean showOrderReportPane) {
		this.showOrderReportPane = showOrderReportPane;
	}

	public List<OrderMaster> getOrderListsByDate() {
		if (orderListsByDate != null) {
			System.out.println(" orderListsByDate size:: "
					+ orderListsByDate.size());
		}
		return orderListsByDate;
	}

	public void setOrderListsByDate(List<OrderMaster> orderListsByDate) {
		this.orderListsByDate = orderListsByDate;
	}

	public boolean isShowMsgPaneNoOrdrs() {
		return showMsgPaneNoOrdrs;
	}

	public void setShowMsgPaneNoOrdrs(boolean showMsgPaneNoOrdrs) {
		this.showMsgPaneNoOrdrs = showMsgPaneNoOrdrs;
	}

	public List<OrderMaster> getAdminorderLst() {
		return adminorderLst;
	}

	public void setAdminorderLst(List<OrderMaster> adminorderLst) {
		this.adminorderLst = adminorderLst;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public OrderMaster getViewDetailsorder() {
		return viewDetailsorder;
	}

	public void setViewDetailsorder(OrderMaster viewDetailsorder) {
		this.viewDetailsorder = viewDetailsorder;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	public String getPaymentDone() {
		return paymentDone;
	}

	public void setPaymentDone(String paymentDone) {
		this.paymentDone = paymentDone;
	}

	public double getTenderAmt() {
		return tenderAmt;
	}

	public void setTenderAmt(double tenderAmt) {
		this.tenderAmt = tenderAmt;
	}

	public String getActionBtn() {
		return actionBtn;
	}

	public void setActionBtn(String actionBtn) {
		this.actionBtn = actionBtn;
	}

	public boolean isShowCart() {
		return showCart;
	}

	public void setShowCart(boolean showCart) {
		this.showCart = showCart;
	}

}

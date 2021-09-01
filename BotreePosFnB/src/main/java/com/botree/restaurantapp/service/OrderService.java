package com.botree.restaurantapp.service;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.OrdersDAO;
import com.botree.restaurantapp.dao.OrdersDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.CommonBean;
import com.botree.restaurantapp.model.ListHolder;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.OrderDeliveryBoy;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.OrderType;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.model.PaymentType;
import com.botree.restaurantapp.service.exception.ServiceException;


@Service
public class OrderService {

  @Autowired
	private OrdersDAO ordersDAO;

	public OrderService() {

	}

	//previously this service return the created order id(int) but from 19.07.2018 it will return the created order obj
	public OrderMaster createOrder(OrderMaster order, HttpServletRequest request)
			throws ServiceException {

		OrderMaster createdOrder;
		try {

			System.out.println("Enter OrderService.createOrder ");
			// create a new order
			//orderId = ordersDAO.createOrder(order, request);
			createdOrder = ordersDAO.createOrder(order, request);
			System.out.println("exit OrderService.createOrder ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to save an order", e);

		}
		//return orderId;
		return createdOrder;

	}
	
	public int createAdvOrder(OrderMaster order, HttpServletRequest request)
			throws ServiceException {
		int orderId;
		try {

			
			// create a new order
			orderId = ordersDAO.createAdvOrder(order, request);
		

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to save an order", e);

		}
		return orderId;

	}

	public List<OrderMaster> getAllOrders(int storeId) throws ServiceException {

		List<OrderMaster> orderList = null;
		try {

			System.out.println("Enter OrderService.getAllOrders ");
			// get list of all unDelivered orders
			orderList = ordersDAO.getAllOrders(storeId);
			System.out.println("exit OrderService.getAllOrders ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all orders", e);

		}
		return orderList;
	}

	public List<OrderMaster> getOrdersByDate(OrderMaster fromOrder,
			OrderMaster toOrder) throws ServiceException {

		List<OrderMaster> orderBydateList = null;
		try {

			System.out.println("Enter OrderService.getOrdersByDate ");
			// get list of all orders by date
			orderBydateList = ordersDAO.getOrdersByDate(fromOrder, toOrder);
			System.out.println("exit OrderService.getOrdersByDate ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all orders", e);

		}
		return orderBydateList;
	}

	public List<OrderMaster> getDeliveredOrders() throws ServiceException {

		List<OrderMaster> orderList = null;
		try {

			System.out.println("Enter OrderService.getDeliveredOrders ");
			// get list of all delivered orders
			orderList = ordersDAO.getDeliveredOrders();
			System.out.println("exit OrderService.getDeliveredOrders ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all delivered orders",
					e);

		}
		return orderList;
	}

	public OrderMaster getOrderById(int orderId, String lang) throws ServiceException {
		OrderMaster order = null;
		try {
			
			order = ordersDAO.getOrderById(orderId,lang);
			if (order != null) {
				System.out.println("Exit OrderService.getAllOrders by orderId");
			}
		} catch (DAOException e) {
			System.out.println("in service Exception");
			throw new ServiceException(
					"problem occured while trying to get all orders by orderId",
					e);

		}
		return order;

	}
	
	public OrderMaster getUnpaidOrderById(Integer orderId, Integer storeId) throws ServiceException {
		OrderMaster order = null;
		try {
			order = ordersDAO.getUnpaidOrderById(orderId,storeId);
			
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get unpaid orderId by id",
					e);

		}
		return order;

	}
	
	public OrderMaster getUnpaidOrderByNo(String orderNo, Integer storeId) throws ServiceException {
		OrderMaster order = null;
		try {
			order = ordersDAO.getUnpaidOrderByNo(orderNo,storeId);
			
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get unpaid order by no",
					e);

		}
		return order;

	}
	
	public OrderMaster getOrderById(int orderId) throws ServiceException {
		OrderMaster order = null;
		try {
			//System.out.println("Enter OrderService.getAllOrders by orderId");
			order = ordersDAO.getOrderById(orderId);
			/*if (order != null) {
				List<OrderItem> itemList = order.getOrderitem();
				System.out.println("Itemlist size" + itemList.size());
				Iterator<OrderItem> iterator = itemList.iterator();
				while (iterator.hasNext()) {
					OrderItem orderItem = (OrderItem) iterator.next();
					MenuItem menuItem = orderItem.getItem();
					System.out.println("Item Name :" + menuItem.getName());
				}
				System.out.println();
				System.out.println("Exit OrderService.getAllOrders by orderId");
			}*/
		} catch (DAOException e) {
			System.out.println("in service Exception");
			throw new ServiceException(
					"problem occured while trying to get orders by orderId",
					e);

		}
		return order;

	}
	
	public OrderMaster getOrderWithPaymentInfo(int orderId,int storeId) throws ServiceException {
		OrderMaster order = null;
		try {
			
			order = ordersDAO.getOrderWithPaymentInfo(orderId,storeId);
			
		} catch (DAOException e) {
			throw new ServiceException(
					"Problem occured while trying to get all orders by orderId", e);
		}
		return order;

	}

	public OrderMaster orderByIdForBillSplit(int orderId)
			throws ServiceException {
		OrderMaster order = null;
		try {
			order = ordersDAO.orderByIdForBillSplit(orderId);

		} catch (DAOException e) {
			System.out.println("in service Exception");
			throw new ServiceException(
					"Problem occured while trying to get all orders by orderId", e);
		}
		
		return order;
	}

	public List<OrderMaster> getCreditOrderByCustomerId(Integer storeId,
			String custId) throws ServiceException {
		List<OrderMaster> orders = null;

		try {
			// get list of all unpaid orders by waiter id and date
			orders = ordersDAO.getCreditOrderByCustomerId(storeId, custId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occured while trying to get all orders by user id", e);
		}

		return orders;

	}

	public OrderMaster chkOrderExistsOnTable(OrderMaster order)
			throws ServiceException {
		OrderMaster existingOrderOnTable = null;
		try {
			System.out.println("Enter OrderService.chkOrderExistsOnTable ");
			existingOrderOnTable = ordersDAO.chkOrderExistsOnTable(order);

		} catch (DAOException e) {
			System.err.println("In service Exception: problem occured while trying to get an existing order order by table number");
			e.printStackTrace();
			//throw new ServiceException("problem occured while trying to get an existing order order by table number", e);
		}

    return existingOrderOnTable;
	}
	
	public OrderMaster chkAdvOrderExistsOnTable(OrderMaster order)
			throws ServiceException {
		OrderMaster existingOrderOnTable = null;
		
		try {
			existingOrderOnTable = ordersDAO.chkAdvOrderExistsOnTable(order);
		} catch (DAOException e) {
			System.out.println("In service Exception: problem occured while trying to get an existing order order by table number");
			e.printStackTrace();
			//throw new ServiceException("problem occured while trying to get an existing order order by table number",	e);
		}

    return existingOrderOnTable;
	}

	public void updateDeliveryStatus(OrderMaster order) throws ServiceException {
		try {

			System.out.println("Enter OrderService.updateDeliveryStatus ");
			// update status of an order
			ordersDAO.updateDeliveryStatus(order);
			System.out.println("exit OrderService.updateDeliveryStatus ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occured while trying to update status of an order", e);
		}

	}

	
	
	public List<OrderType> getOrderTypeByStore(Integer storeid) throws ServiceException {

		List<OrderType> orderTypLst = null;
		try {
			// get list of all order Types
			orderTypLst = ordersDAO.getOrderTypeByStore(storeid);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occured while trying to get all order Types", e);
		}
		return orderTypLst;
	}
	
	public List<OrderType> getOrderType() throws ServiceException {

		List<OrderType> orderTypLst = null;
		try {

			System.out.println("Enter OrderService.getOrderType ");
			// get list of all order Types
			orderTypLst = ordersDAO.getOrderType();
			System.out.println("exit OrderService.getOrderType ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occured while trying to get all order Types", e);
		}
		return orderTypLst;
	}

	public List<OrderType> getAdminOrderType() throws ServiceException {

		List<OrderType> orderTypLst = null;
		try {

			System.out.println("Enter OrderService.getAdminOrderType ");
			// get list of all Admin order Types
			orderTypLst = ordersDAO.getAdminOrderType();
			System.out.println("exit OrderService.getAdminOrderType ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all  Admin order Types",
					e);

		}
		return orderTypLst;
	}

	public void createOrderType(OrderType orderType) throws ServiceException {
		try {

			System.out.println("Enter OrderService.createOrderType ");
			// create an order Type
			ordersDAO.createOrderType(orderType);
			System.out.println("exit OrderService.createOrderType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to create an Order Type", e);

		}

	}

	public void deleteOrderType(OrderType orderType) throws ServiceException {
		try {

			System.out.println("Enter OrderService.deleteOrderType ");
			// create an order Type
			ordersDAO.deleteOrderType(orderType);
			System.out.println("exit OrderService.deleteOrderType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to delete an Order Type", e);

		}

	}

	public void delete(OrderMaster order) throws ServiceException {
		try {

			// delete an order
			ordersDAO.delete(order);

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to delete an Order Type", e);

		}

	}

	public ListHolder getPaymentTypeByStore(Integer id) throws ServiceException {

		ListHolder holder = null;
		try {
		
			// get list of all getPayment Types
			holder = ordersDAO.getPaymentTypeByStore(id);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all getPayment Types by store",
					e);

		}
		return holder;
	}
	
	public List<PaymentType> getPaymentType() throws ServiceException {

		List<PaymentType> PaymentTypeLst = null;
		try {

			System.out.println("Enter OrderService.getPaymentType ");
			// get list of all getPayment Types
			PaymentTypeLst = ordersDAO.getPaymentType();
			System.out.println("exit OrderService.getPaymentType ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all getPayment Types",
					e);

		}
		return PaymentTypeLst;
	}

	public List<PaymentType> getAdminPaymentType() throws ServiceException {

		List<PaymentType> PaymentTypeLst = null;
		try {

			System.out.println("Enter OrderService.getAdminPaymentType ");
			// get list of all Admin getPayment Types
			PaymentTypeLst = ordersDAO.getAdminPaymentType();
			System.out.println("exit OrderService.getAdminPaymentType ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all Admin Payment Types",
					e);

		}
		return PaymentTypeLst;
	}

	public void createPaymentType(PaymentType paymentType)
			throws ServiceException {
		try {

			System.out.println("Enter OrderService.createPaymentType ");
			// create an Payment Type
			ordersDAO.createPaymentType(paymentType);
			System.out.println("exit OrderService.createPaymentType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to create a Payment Type", e);

		}

	}

	public void deletePaymentType(PaymentType paymentType)
			throws ServiceException {
		try {

			System.out.println("Enter OrderService.deletePaymentType ");
			// create an order Type
			ordersDAO.deletePaymentType(paymentType);
			System.out.println("exit OrderService.deletePaymentType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to delete a Payment Type", e);

		}

	}

	public void pay(String orderid) throws ServiceException {
		try {

			System.out.println("Enter OrderService.pay ");
			// make payment
			ordersDAO.pay(orderid);
			System.out.println("exit OrderService.pay ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to make payment", e);

		}

	}

	public void payCash(Payment payment) throws ServiceException {
		try {

			System.out.println("Enter OrderService.payCash ");
			// make payment
			ordersDAO.payCash(payment);
			System.out.println("exit OrderService.payCash ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to make payment", e);

		}

	}

	public void updateOrderTypeFlagAsActive(OrderType orderType)
			throws ServiceException {
		try {

			System.out.println("Enter OrderService.deletePaymentType ");
			// create an order Type
			ordersDAO.updateOrderTypeFlagAsActive(orderType);
			System.out.println("exit OrderService.deletePaymentType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to delete a Payment Type", e);

		}

	}

	public void updateOrderTypeFlagAsInActive(OrderType orderType)
			throws ServiceException {
		try {

			System.out.println("Enter OrderService.deletePaymentType ");
			// create an order Type
			ordersDAO.updateOrderTypeFlagAsInActive(orderType);
			System.out.println("exit OrderService.deletePaymentType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to delete a Payment Type", e);

		}

	}

	public void updatePaymentTypeFlagAsActive(PaymentType paymentType)
			throws ServiceException {
		try {

			System.out.println("Enter OrderService.deletePaymentType ");
			// create an order Type
			ordersDAO.updatePaymentTypeFlagAsActive(paymentType);
			System.out.println("exit OrderService.deletePaymentType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to delete a Payment Type", e);

		}

	}

	public void updatePaymentTypeFlagAsInActive(PaymentType paymentType)
			throws ServiceException {
		try {

			System.out.println("Enter OrderService.deletePaymentType ");
			// create an order Type
			ordersDAO.updatePaymentTypeFlagAsInActive(paymentType);
			System.out.println("exit OrderService.deletePaymentType ");

		} catch (DAOException e) {

			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to delete a Payment Type", e);

		}

	}

	public List<OrderItem> getItemsByOrderId(String id, String language)
			throws ServiceException {

		List<OrderItem> orderItems = null;

		try {

			System.out.println("Enter OrderService.getItemsByOrderId ");
			// get list of all items by order id
			orderItems = ordersDAO.getItemsByOrderId(id, language);
			System.out.println("exit OrderService.getItemsByOrderId ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all items by order id",
					e);

		}

		return orderItems;
	}

	public List<OrderItem> getItemsByOrderIdInRest(String id, String language)
			throws ServiceException {

		List<OrderItem> orderItems = null;

		try {

			System.out.println("Enter OrderService.getItemsByOrderIdInRest ");
			// get list of all items by order id in Rest
			orderItems = ordersDAO.getItemsByOrderIdInRest(id, language);
			System.out.println("exit OrderService.getItemsByOrderIdInRest ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all items by order id in rest",
					e);

		}

		return orderItems;
	}

	public List<OrderItem> getAllKitchenInItems(Integer storeId, String date,String lang)
			throws ServiceException {

		List<OrderItem> orderItems = null;
		try {
			// get list of all items that are in the kitchen
			orderItems = ordersDAO.getAllKitchenInItems(storeId, date,lang);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all items in the kitchen",
					e);
		}

		return orderItems;
	}
	
	/*public List<OrderItem> getAllKitchenInItems(Integer storeId, String date)
			throws ServiceException {

		List<OrderItem> orderItems = null;
		try {
			// get list of all items that are in the kitchen
			orderItems = ordersDAO.getAllKitchenInItems(storeId, date);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all items in the kitchen",
					e);
		}

		return orderItems;
	}*/

	public List<OrderItem> getItemsByOrderIdParcel(String id, String language)
			throws ServiceException {

		List<OrderItem> orderItems = null;

		try {

			System.out.println("Enter OrderService.getItemsByOrderIdParcel ");
			// get list of all items by order id in Rest
			orderItems = ordersDAO.getItemsByOrderIdParcel(id, language);
			System.out.println("exit OrderService.getItemsByOrderIdParcel ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all items by order id in rest",
					e);

		}

		return orderItems;
	}

	public List<OrderMaster> getAllUnpaidOrdersByDate(Integer id, String date)
			throws ServiceException {

		List<OrderMaster> orders = null;

		try {

			// get list of all unpaid orders by waiter id and date
			orders = ordersDAO.getAllUnpaidOrdersByDate(id, date);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all orders by user id",
					e);

		}

		return orders;
	}
	
	public List<OrderMaster> getAllAdvanceOrders(Integer id)
			throws ServiceException {

		List<OrderMaster> orders = null;

		try {

			// get list of all unpaid orders by waiter id and date
			orders = ordersDAO.getAllAdvanceOrders(id);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all orders by user id",
					e);

		}

		return orders;
	}
	
	public int getNoOfAdvanceOrders(Integer id)
			throws ServiceException {
		int noofadvorders=0;
		try {
			// get list of all unpaid orders by waiter id and date
			noofadvorders = ordersDAO.getNoOfAdvanceOrders(id);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get no of adv orders",
					e);

		}

		return noofadvorders;
	}
	
	public List<OrderMaster> getAllUnpaidOrdersByDateRange(Integer id, String date,String date2)
			throws ServiceException {

		List<OrderMaster> orders = null;

		try {

			// get list of all unpaid orders by waiter id and date
			orders = ordersDAO.getAllUnpaidOrdersByDateRange(id, date,date2);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all orders by user id",
					e);

		}

		return orders;
	}
	
	public List<OrderMaster> getAllUnpaidOrdersByStoreId(Integer id, String date)
			throws ServiceException {

		List<OrderMaster> orders = null;

		try {
			// get list of all unpaid orders by waiter id and date
			orders = ordersDAO.getAllUnpaidOrdersByStoreId(id, date);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occured while trying to get all orders by user id", e);
		}

		return orders;
	}

	public List<OrderMaster> getAllPaidOrdersByStoreId(Integer id, String date)
			throws ServiceException {

		List<OrderMaster> orders = null;

		try {

			// get list of all paid orders by id and date
			orders = ordersDAO.getAllPaidOrdersByStoreId(id, date);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all orders by user id",
					e);

		}

		return orders;
	}
	
	public OrderMaster getPaidOrderById(String id, Integer storeId)
			throws ServiceException {

		OrderMaster order = null;

		try {
			order = ordersDAO.getPaidOrderById(id, storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get paid order by id",
					e);
		}
		return order;
	}
	
	public OrderMaster getPaidOrderByNo(String id, Integer storeId)
			throws ServiceException {

		OrderMaster order = null;

		try {
			order = ordersDAO.getPaidOrderByNo(id, storeId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get paid order by no",
					e);
		}
		return order;
	}

	public void reqBillByOrderId(String id, String billReqTime)
			throws ServiceException {

		try {
			System.out.println("Enter OrderService.reqBillByOrderId ");
			// request bill by order id
			ordersDAO.reqBillByOrderId(id, billReqTime);
			System.out.println("exit OrderService.reqBillByOrderId ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to request bill by order id",
					e);
		}
	}

	public String getLastOrder(Integer storeId) throws ServiceException {

		String orderDtls = null;

		try {

			System.out.println("Enter OrderService.getLastOrder ");

			orderDtls = ordersDAO.getLastOrder(storeId);
			System.out.println("exit OrderService.getLastOrder ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to request bill by order id",
					e);

		}
		return orderDtls;

	}

	@SuppressWarnings("finally")
	public String chkBillReq(String id) throws ServiceException {

		String status = null;

		try {

			System.out.println("Enter OrderService.chkBillReq ");
			// check if request is already processed
			status = ordersDAO.chkBillReq(id);
			System.out.println("exit OrderService.chkBillReq ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to request bill by order id",
					e);

		} finally {
			return status;
		}

	}

	public void updateOrderByItemId(String orderitemid, String quantity)
			throws ServiceException {

		try {

			System.out.println("Enter OrderService.updateOrderByItemId ");
			ordersDAO.updateOrderByItemId(orderitemid, quantity);
			System.out.println("exit OrderService.updateOrderByItemId ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update order by order id",
					e);

		}

	}

	public String updateCookingStatus(String orderid, String orderitemid,
			String time) throws ServiceException {

		String status = "";

		try {
			status = ordersDAO.updateCookingStatus(orderid, orderitemid, time);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update order by order id",
					e);

		}

		return status;

	}

	public String updateNoOfPersons(String orderid, String noOfPersons)
			throws ServiceException {

		String status = "";

		try {
			status = ordersDAO.updateNoOfPersons(orderid, noOfPersons);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update order by order id",
					e);

		}

		return status;

	}

	public String updateTableNo(OrderMaster orderMaster)
			throws ServiceException {

		String status = "";

		try {
			status = ordersDAO.updateTableNo(orderMaster);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update table no by order id",
					e);

		}

		return status;

	}

	public String updateCreditSaleStatus(String orderid, Integer storeId,
			String storeCustomerId) throws ServiceException {

		String status = "";

		try {
			status = ordersDAO.updateCreditSaleStatus(orderid, storeId,
					storeCustomerId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update order by order id",
					e);

		}

		return status;

	}

	public String updateKitchenOutStatus(String orderid, String orderitemid)
			throws ServiceException {

		String status = "";
		try {
			status = ordersDAO.updateKitchenOutStatus(orderid, orderitemid);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update kitchen out", e);

		}

		return status;

	}

	public String cookingEndStatus(String orderid, String orderitemid,
			String time) throws ServiceException {

		String status = "";

		try {
			status = ordersDAO.cookingEndStatus(orderid, orderitemid, time);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update kitchen out", e);

		}

		return status;

	}

	public String printBill(String orderid, Integer storeid)
			throws ServiceException {

		String status = "";
		// MobPrintBill mobPrintBill;

		try {
			status = ordersDAO.printBill(orderid, storeid);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to print bill", e);

		}

		return status;

	}

	public String printSplitBill(String orderid, Integer storeid)
			throws ServiceException {

		String status = "";
		// MobPrintBill mobPrintBill;

		try {
			status = ordersDAO.printSplitBill(orderid, storeid);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to print bill", e);

		}

		return status;

	}

	public String printKot(Integer orderid, Integer storeid)
			throws ServiceException {

		String status = "";

		try {
			status = ordersDAO.printKot(orderid, storeid);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to print bill", e);

		}

		return status;

	}

	public String printUpdateKot(Integer orderid, Integer storeid,
			int currentQuantity, int previousQuantity,String itemName) throws ServiceException {

		String status = "";
		OrdersDAO ordersDAO=new OrdersDAOImpl();

		try {
			status = ordersDAO.printUpdateKot(orderid, storeid,
					currentQuantity, previousQuantity,itemName);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to print bill", e);

		}

		return status;

	}
	
	public String updateBillPrintCount(CommonBean bean) throws ServiceException {

		String status = "";
		OrdersDAO ordersDAO=new OrdersDAOImpl();

		try {
			status = ordersDAO.updateBillPrintCount(bean);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to update print bill count", e);

		}

		return status;

	}
	
	public String getBillPrintCount(Integer orderid, Integer storeid) throws ServiceException {

		String status = "";
		OrdersDAO ordersDAO=new OrdersDAOImpl();

		try {
			status = ordersDAO.getBillPrintCount(orderid,storeid);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get print bill count", e);

		}

		return status;

	}

	public void cancelOrderById(String id, Integer storeid, String cancelRemrk)
			throws ServiceException {

		try {

			System.out.println("Enter OrderService.cancelOrderById ");
			ordersDAO.cancelOrderById(id, storeid, cancelRemrk);
			System.out.println("exit OrderService.cancelOrderById ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to cancel order by order id",
					e);

		}

	}

	public String assignDeliveryBoy(OrderDeliveryBoy orderDeliveryBoy) throws  ServiceException{
		String status="failure";
		try {
			status = ordersDAO.assignDeliveryBoy(orderDeliveryBoy);
			if(status=="success")
				System.out.println("Assigning delivery boy successfull");
			else 
				System.out.println("Assigning delivery boy failed");
		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to cancel order by order id",
					e);
		}
		return status;
	}
	
	public int syncOrder(OrderMaster order, HttpServletRequest request)
			throws ServiceException {
		int orderId;
		try {
			orderId = ordersDAO.syncOrder(order, request);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occured while trying to sync an order", e);
		}
		return orderId;

	}
	
	public String setOrderRemark(OrderMaster orderMaster) throws  ServiceException{
		String status="failure";
		try {
			status = ordersDAO.setOrderRemark(orderMaster);
		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to set reamrks to order",
					e);
		}
		return status;
	}
	
	public String setPackagingNote(OrderMaster orderMaster) throws  ServiceException{
		String status="failure";
		try {
			status = ordersDAO.setPackagingNote(orderMaster);
		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to set special note to order",
					e);
		}
		return status;
	}
	
	public OrdersDAO getOrdersDAO() {
		return ordersDAO;
	}

	public void setOrdersDAO(OrdersDAO ordersDAO) {
		this.ordersDAO = ordersDAO;
	}

}

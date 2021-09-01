package com.botree.restaurantapp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.botree.restaurantapp.print.PrintKotMaster;

public interface OrdersDAO {

	public int createAdvOrder(OrderMaster order, HttpServletRequest request)
			throws DAOException;
	
	//previously this service return the created order id(int) but from 19.07.2018 it will return the created order obj
	public OrderMaster createOrder(OrderMaster order, HttpServletRequest request)
			throws DAOException;

	public int createOrderAdmin(OrderMaster order) throws DAOException;

	public MenuItem getItemPriceById(int itemId) throws DAOException;

	public MenuItem getItemNameById(int itemId) throws DAOException;

	public List<OrderMaster> getAllOrders(int storeId) throws DAOException;

	public List<OrderMaster> getDeliveredOrders() throws DAOException;

	public OrderMaster getOrderById(int orderId) throws DAOException;
	public OrderMaster getUnpaidOrderById(int orderId, int storeId) throws DAOException;
	//added on 18.07.2018
	public OrderMaster getUnpaidOrderByNo(String orderNo, int storeId) throws DAOException;
	public OrderMaster getOrderWithPaymentInfo(int orderId,int storeId) throws DAOException;
	public OrderMaster getOrderById(int orderId, String lang) throws DAOException;

	public void updateDeliveryStatus(OrderMaster order) throws DAOException;

	
	public List<OrderType> getOrderTypeByStore(Integer storeid) throws DAOException;
	
	public List<OrderType> getOrderType() throws DAOException;

	public List<OrderType> getAdminOrderType() throws DAOException;

	public void createOrderType(OrderType orderType) throws DAOException;

	public void deleteOrderType(OrderType orderType) throws DAOException;

	public ListHolder getPaymentTypeByStore(Integer id) throws DAOException;
	
	public List<PaymentType> getPaymentType() throws DAOException;

	public List<PaymentType> getAdminPaymentType() throws DAOException;

	public void createPaymentType(PaymentType paymentType) throws DAOException;

	public void deletePaymentType(PaymentType paymentType) throws DAOException;

	public void updateOrderTypeFlagAsActive(OrderType orderType)
			throws DAOException;

	public void updateOrderTypeFlagAsInActive(OrderType orderType)
			throws DAOException;

	public void updatePaymentTypeFlagAsActive(PaymentType paymentType)
			throws DAOException;

	public void updatePaymentTypeFlagAsInActive(PaymentType paymentType)
			throws DAOException;

	public List<OrderItem> getItemsByOrderId(String id, String language)
			throws DAOException;

	public List<OrderMaster> getOrdersByDate(OrderMaster fromOrder,
			OrderMaster toOrder) throws DAOException;

	public void reqBillByOrderId(String id, String billReqTime)
			throws DAOException;

	public List<OrderMaster> getAllUnpaidOrdersByDate(Integer id, String date)
			throws DAOException;
	
	public List<OrderMaster> getAllUnpaidOrdersByStoreId(Integer id, String date)
			throws DAOException;

	public String chkBillReq(String id) throws DAOException;

	public OrderMaster chkAdvOrderExistsOnTable(OrderMaster order)
			throws DAOException;
	
	public OrderMaster chkOrderExistsOnTable(OrderMaster order)
			throws DAOException;

	public void pay(String orderId) throws DAOException;

	public List<OrderItem> getAllItemsByOrderNTable(String tableNo, int orderId)
			throws DAOException;

	public void updateOrderByItemId(String orderitemid, String quantity)
			throws DAOException;

	public String updateCookingStatus(String orderid, String orderitemid,
			String time) throws DAOException;

	public String cookingEndStatus(String orderid, String orderitemid,
			String time) throws DAOException;

	public String updateKitchenOutStatus(String orderid, String orderitemid)
			throws DAOException;

	public void payCash(Payment payment) throws DAOException;

	public List<OrderItem> getItemsByOrderIdInRest(String id, String language)
			throws DAOException;

	public List<OrderItem> getItemsByOrderIdParcel(String id, String language)
			throws DAOException;

	public void cancelOrderById(String id, Integer storeid, String cancelRemrk)
			throws DAOException;

	public String getLastOrder(Integer storeId) throws DAOException;

	public void delete(OrderMaster order) throws DAOException;

	public List<OrderItem> getAllKitchenInItems(Integer storeId, String date,String lang)
			throws DAOException;
	
	/*public List<OrderItem> getAllKitchenInItems(Integer storeId, String date)
			throws DAOException;*/

	public String printBill(String orderid, Integer storeid) throws DAOException;

	public void updateKotPrint(int orderId) throws DAOException;

	public void updateBillPrint(int orderId) throws DAOException;

	public String printKot(Integer orderid, Integer storeid) throws DAOException;

	public String printUpdateKot(Integer orderid, Integer storeid,
			int currentQuantity, int previousQuantity,String itemName) throws DAOException;
	
	public String updateBillPrintCount(CommonBean bean) throws DAOException;
	
	public String getBillPrintCount(Integer orderid, Integer storeid) throws DAOException;

	public List<PrintKotMaster> getAllKotPrinter(int storeId)
			throws DAOException;

	public String updateCreditSaleStatus(String orderid, Integer storeId,
			String storeCustomerId) throws DAOException;

	public List<OrderMaster> getCreditOrderByCustomerId(Integer storeId,
			String custId) throws DAOException;

	/*public int addDailyExpenditure(DailyExpenditure dailyExpenditure)
			throws DAOException;

	public List<DailyExpenditure> getDailyExpenditureByDate(String date,
			Integer storeId) throws DAOException;*/

	public String updateNoOfPersons(String orderid, String noOfPersons)
			throws DAOException;

	public List<OrderMaster> getAllPaidOrdersByStoreId(Integer id, String date)
			throws DAOException;
	
	public OrderMaster getPaidOrderById(String id, Integer storeId)
			throws DAOException;
	
	public OrderMaster getPaidOrderByNo(String id, Integer storeId)
			throws DAOException;

	public String updateTableNo(OrderMaster orderMaster)
			throws DAOException;

	public String printSplitBill(String orderid, Integer storeid)
			throws DAOException;

	public OrderMaster orderByIdForBillSplit(int orderId) throws DAOException;

	List<OrderMaster> getAllAdvanceOrders(Integer storeId) throws DAOException;
	
	public int getNoOfAdvanceOrders(Integer storeId) throws DAOException;
	
	List<OrderMaster> getAllUnpaidOrdersByDateRange(Integer storeId,
			String date, String date2) throws DAOException;

	double getOrderTypeByStoreIdAndOrdershortName(Integer storeid,
			String shortName) throws DAOException;

	/*List<DailyExpenditure> getDailyExpenditureByPeriod(String date,
			String toDate, Integer storeId) throws DAOException;*/
	
	/* To assign delivery boy to order id */
	public String assignDeliveryBoy(OrderDeliveryBoy o)throws DAOException;
	
	public int syncOrder(OrderMaster order, HttpServletRequest request)
			throws DAOException;
	public String setOrderRemark(OrderMaster orderMaster)throws DAOException;
	public String setPackagingNote(OrderMaster orderMaster)throws DAOException;
	
}

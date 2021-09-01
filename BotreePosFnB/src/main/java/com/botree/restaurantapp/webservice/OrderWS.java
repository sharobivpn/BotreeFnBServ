package com.botree.restaurantapp.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;

import com.botree.restaurantapp.model.CommonBean;
import com.botree.restaurantapp.model.OrderDeliveryBoy;
import com.botree.restaurantapp.model.OrderMaster;

public interface OrderWS {

	String createOrder(OrderMaster order, HttpServletRequest request);

	public String getOrderTypes();

	public String getPaymentTypes();

	public String getItemsByOrderId(String id, String language);

	public String getOrderById(String id);

	public String reqBillByOrderId(String id, String billReqTime);

	public String getAllUnpaidOrdersByStoreId(Integer id, String date);

	public String updateOrderByItemId(String itemid, String quantity);

	public String getItemsByOrderIdInRest(String id, String language);

	public String getItemsByOrderIdParcel(String id, String language);

	public void getTodaysSalesReport(String frmdate,String todate, String storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);

	public void getTodaysSalesReportWithTax(String date, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);

	public void getTodaysCategoryWiseSalesReport(String date, Integer storeId, String category, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public void getTodaysUserWiseSalesReport(String frmdate,String todate, Integer storeId, String user, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public void getTodaysOrderReport(String date, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);

	public String cancelOrderById(String id, Integer storeId, String cancelRemrk);

	public void getSalesReport(String type, String frmdate, String todate, Integer storeId, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public String getLastOrder(Integer storeId);

	public String getAllKitchenInItems(Integer storeId, String date,String lang);

	public String updateCookingStatus(String orderid, String orderitemid,
			String time);

	public String cookingEndStatus(String orderid, String orderitemid,
			String endtime);

	public String updateKitchenOutStatus(String orderid, String orderitemid);

	public String printBill(String orderid, Integer storeId);

	public String printKot(Integer orderid, Integer storeid);

	public void getTodaysItemReport(String date, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);

	public void getPeriodwiseItemReport(String frmdate, String todate, Integer storeId, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public void getMonthlyItemReport(String year, String month, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);

	public String updateCreditSaleStatus(String orderid, Integer storeId, String storeCustomerId);

	public String getCreditOrderByCustomerId(Integer storeId, String id);

	public void getCreditSaleReport(String frmdate, String todate, Integer storeId, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public void getCanceledOrderReport(String frmdate, String todate, Integer storeId, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public void getTimelySalesReport(String frmdate,String todate, Integer storeId, String workingHours, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public String updateNoOfPersons(String orderid, String noOfPersons);

	public String getAllPaidOrdersByStoreId(Integer id, String date);
	
	public String getPaidOrderById(String id, Integer storeId);
	
	public String getPaidOrderByNo(String id, Integer storeId);

	public String updateTableNo(OrderMaster orderMaster);

	public void getTodaysSalesWithPaymentReport(String date, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);

	public String printSplitBill(String orderid, Integer storeId);

	public void getNocKotReport(String frmdate, String todate, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	
	public void getPackagingDetailReport(String frmdate, String todate, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);

	public String orderByIdForBillSplit(String id);

	public String updateKot(Integer orderid, Integer storeid);

	public String printUpdateKot(Integer orderid, Integer storeid, int crntQnty,
			int prevQnty, String itemName);

	public void getOrderReportPeriodWise(Integer storeId, String frmdate, String todate, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	public void getSalesReportPaymentMode( String frmdate,String todate, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);

	public void getTodaysCategoryWiseSalesReport( String date, String date1, Integer storeId, String category, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	
	public String getOrderById(String id, String lang);
	
	public String getAllKitchenInItems(Integer storeId, String date);
	
	public String getOrderWithPaymentInfo(String orderId, Integer storeId, String lang);
	
	public String updateBillPrintCount(CommonBean bean);
	public String getBillPrintCount(Integer orderid, Integer storeId);
	public String getPaymentTypeByStore(Integer id);
	
	public String getUnpaidOrderById(Integer id, Integer storeId, String lang);
	
	//added on 18.07.2018
	public String getUnpaidOrderByNo(String orderno, Integer storeId, String lang);
	
	public String getAllUnpaidOrdersByDate(Integer id, String date) ;
	
	/* Assign delivery boy according to order 
	 * Added by chanchalN 
	 */
	public String assignDeliveryBoy(OrderDeliveryBoy orderDeliveryBoy); 
	/*
	 * Report Generation for DeliveryBoy as per StoreId
	 */
	public void reportOrderdeliveryBoy(Integer storeId, String orderFrom, String orderTo,Integer deliveryBoyId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	// public String insertMenu(MenuFile menuFile);
	
	public void getRefundSummaryReport(Integer storeId, String frmdate, String todate, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	
	public void getRefundDetailsReport(Integer storeId, String frmdate, String todate, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	
	public void getTodaysSalesReportCelavi(String date, Integer storeId, Integer reportType,
	      HttpServletRequest request, HttpServletResponse response);
	 
	public void getSalesReportUserWisePaymentMode(String startDate, String endDate, Integer storeId, Integer reportType,
	      HttpServletRequest request, HttpServletResponse response);
	
// new added
	public void getTaxSummaryReport(String frmdate,String todate, String storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	public String setOrderRemark(@RequestBody OrderMaster orderMaster);
	public String setPackagingNote(@RequestBody OrderMaster orderMaster);
}

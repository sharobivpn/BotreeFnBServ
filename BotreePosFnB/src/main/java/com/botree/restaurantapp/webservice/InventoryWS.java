package com.botree.restaurantapp.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.botree.restaurantapp.model.FgInvoicePayment;
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
import com.botree.restaurantapp.model.SalesReturn;
import com.botree.restaurantapp.model.account.AccountGroupDTO;
import com.botree.restaurantapp.model.inv.FgStockIn;
import com.botree.restaurantapp.model.inv.FgStockInItemsChild;

public interface InventoryWS {

	String getInventoryType(Integer storeId);

	String getInventoryItems(Integer storeId);

	String getInventoryItemsByName(Integer storeId, String name);

	String getVendors(Integer storeId);

	String getPurchaseOrdersByDate(Integer storeid, String date);

	String getPurchaseOrdersById(Integer storeid, Integer id);

	String createPO(InventoryPurchaseOrder purchaseOrder);

	String getInventoryStockIn(Integer storeid, String date);

	String getInventoryStockOut(Integer storeid, String date);

	public String approvePO(Integer id, String approvedBy, String updatedBy,
			String updatedDate);

	public String updatePO(Integer id, String poBy, String updatedBy,
			String updatedDate);

	String updatePOItem(InventoryPurchaseOrder purchaseOrder);

	String deletePOItem(Integer poId, Integer poItemId, Integer storeId);
	
	//added on 23.04.2018
	String deletePO(Integer poId, Integer storeId);

	String getInventoryStockInById(Integer storeid, Integer id);

	String createStockIn(InventoryStockIn inventoryStockIn);

	String getInventoryStockOutById(Integer storeid, Integer id);

	String createStockOut(InventoryStockOut inventoryStockOut);

	public String closeStockIn(InventoryStockIn inventoryStockIn);

	String updateStockInItem(InventoryStockIn inventoryStockIn);

	String deleteStockInItem(Integer stockInId, Integer stockInItemId,
			Integer storeId);
	//added on 30.04.2018
	String deleteStockIn(Integer stockInId,Integer storeId);

	String updateEachStockInItem(InventoryStockInItem inventoryStockInItem);

	String getCurrentStockByItem(Integer storeid, Integer itemId);

	String updateEachPOItem(
			InventoryPurchaseOrderItem inventoryPurchaseOrderItem);

	public String addVendor(InventoryVendor inventoryVendor);

	public String updateVendor(InventoryVendor inventoryVendor);

	public String deleteVendor(Integer id, Integer storeId);

	public String addNewInventoryItem(InventoryItems inventoryItems);

	public String updateInventoryItem(InventoryItems inventoryItems);

	public String addInvntoryType(InventoryType inventoryType);

	public String updateInvntryType(InventoryType inventoryType);

	public String deleteInvntryType(Integer id, Integer storeId);

	public String deleteInvntryTypeItem(Integer id, Integer storeId);

	// webservices for reports
	/*public void getInventoryStockInReport(String type, String frmdate, String todate, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);*/
	public void getRawStockinSummary(String frmdate, String todate, Integer storeId, Integer vendorId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);
	
	public void getRawStockoutSummary(String frmdate, String todate, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);
	public void getCurrentStock(Integer storeId, Integer reportType,String date,Integer itemId, 
	    HttpServletRequest request, HttpServletResponse response);

	public void getInventoryPOReport(String type, String frmdate, String todate, Integer storeId, Integer reportType, 
	    HttpServletRequest request, HttpServletResponse response);

	/*public void getTodaysAInvStockInReport(String date, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);*/
	
	public void getRawStockinRegister(String frmdate,String todate, Integer storeId,Integer vendorId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	public void getRawStockoutRegister(String frmdate,String todate, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);

	public void getTodaysPOReport(String date, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);

	public String getRecipeIngredients(Integer storeid);

	public String getEstimateTypes(Integer storeid);

	public String createFgStockIn(FgStockIn fgStockIn);

	public String updateFgStockInItem(FgStockInItemsChild fgStockInItemsChild);

	public String approveFgStockIn(Integer storeId, Integer id, String by);

	public String updateEachRawStockOutItem(
			InventoryStockOutItem inventoryStockOutItem);

	public String deleteStockOutItem(Integer stockOutId, Integer stockOutItemId,
			Integer storeId);
	
	//added on 16.01.2020
	String deleteStockOut(Integer stockOutId,Integer storeId);

	public String approveRawStockOut(Integer id, Integer storeId,
			String approvedBy, String updatedBy, String updatedDate);

	public String getInventoryItemsByCode(Integer storeid, String code);

	public void getGstInOutReportPeriodWise(Integer storeId, String frmdate,
			String todate, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);
	
	public void getPLstatementReport(Integer storeId, String frmdate, String todate, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);

	public void getRawStockStatusReportByEdp(String date, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);

	public void getEdpReportById(String edpId, Integer storeId, Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);
	
	public void getPoReportById(Integer poId, Integer storeId, Integer reportType,
			HttpServletRequest request, HttpServletResponse response);
	
	//added 02.05.2018
	public String getCreditInfoByVendor(Integer vendorId, Integer storeId);

	public String invoicePayment(InventoryInvoicePayment payment);
	
	//added on 06.06.2018
	public String getReturnCauses(Integer storeid);
	
	public String createSalesReturn(SalesReturn salesReturn);

	public String getSalesReturn(Integer id, Integer storeId);
	
	public String getSalesReturnByOrderId(Integer orderId, Integer storeId);

	public String adjustReturnSales(Integer id, Integer storeId);
	
	//added on 14.06.2018
	public String approveSalesReturn(SalesReturn salesReturn);
	
	public void getGRNReportById(String stkId, String storeId,
			HttpServletRequest request, HttpServletResponse response);
	
	public String deleteSalesReturn(Integer returnId, Integer storeId);
  
  public String printRefundBill(String orderid, Integer storeId);
  
	public String createAccountGroup(AccountGroupDTO accountGroupDTO);
	
	//added on 05.07.2018
	public void getVendorPaymentReport(Integer storeId, String frmdate, String todate,Integer vendorId ,Integer reportType, 
			HttpServletRequest request, HttpServletResponse response);

	//added on 09.07.2018
	public String createPurchaseReturn(InventoryReturn inventoryReturn);
	public String updatePurchaseReturn(InventoryReturn inventoryReturn);
	public String deletePurchaseReturn(Integer stockInRetId,Integer storeId);
	public String approvePurchaseReturn(InventoryReturn inventoryReturn);
	public String getPurchaseReturn(Integer storeid, String date);
	public String getPurchaseReturnById(Integer id, Integer storeId);
	//added on 04.03.2019
	public String getSimpleFgStockInByDate(Integer id, String date);
	//added on 05.03.2019
	String getFgCurrentStockByItem(Integer storeid, Integer itemId);
	String getFgStockInById(Integer storeid, Integer id);
	String updateFgStockIn(FgStockIn fgStockIn);
	String deleteFgStockIn(Integer fgstockInId,Integer storeId);
	
	//added on 09.07.2018
	public String createFgReturn(FgReturn fgReturn);
	public String updateFgReturn(FgReturn fgReturn);
	public String deleteFgReturn(Integer fgstockInRetId,Integer storeId);
	public String approveFgReturn(FgReturn fgReturn);
	public String getFgReturn(String storeid, String date);
	public String getFgReturnById(String id, String storeId);
	//added 18.06.2019
	public String getFgCreditInfoByVendor(Integer vendorId, Integer storeId);
	public String invoiceFgPayment(FgInvoicePayment payment);
	//added on 24.06.2019
	public void getFgVendorPaymentReport(Integer storeId, String frmdate, String todate,Integer vendorId ,Integer reportType, 
				HttpServletRequest request, HttpServletResponse response);
	//added on 30.07.2019
	public String getRawItemQtytobeSoldOut(Integer storeId, String date);
	//added on 15.11.2019
	String getFgStockInItemsByItemId(Integer storeid, Integer itemId);
}

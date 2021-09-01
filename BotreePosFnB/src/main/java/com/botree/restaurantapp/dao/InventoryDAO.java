package com.botree.restaurantapp.dao;

import java.sql.ResultSet;
import java.util.List;

import com.botree.restaurantapp.commonUtil.CommonResultSetMapper;
import com.botree.restaurantapp.commonUtil.ResponseObj;
import com.botree.restaurantapp.dao.exception.DAOException;
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
import com.botree.restaurantapp.print.PrintKotMaster;

public interface InventoryDAO {

	public List<InventoryType> getInventoryType(Integer storeId)
			throws DAOException;

	public List<InventoryItems> getInventoryItems(Integer storeId)
			throws DAOException;

	public List<InventoryItems> getInventoryItemsByName(Integer storeId,
			String name) throws DAOException;

	public List<InventoryVendor> getVendors(Integer storeId) throws DAOException;

	public List<InventoryPurchaseOrder> getPurchaseOrdersByDate(Integer storeId,
			String date) throws DAOException;

	public List<InventoryPurchaseOrder> getPurchaseOrdersById(Integer storeId,
			Integer id) throws DAOException;

	public void addInventoryType(InventoryType inventoryType)
			throws DAOException;

	public void addInventoryItem(InventoryItems inventoryItems)
			throws DAOException;

	public void addInventoryVendor(InventoryVendor inventoryVendor)
			throws DAOException;

	public int createPO(InventoryPurchaseOrder purchaseOrder)
			throws DAOException;

	public List<InventoryStockIn> getInventoryStockIn(Integer storeId,
			String date) throws DAOException;

	public List<InventoryStockOut> getInventoryStockOut(Integer storeId,
			String date) throws DAOException;

	public String approvePO(Integer id, String approvedBy, String updatedBy,
			String updatedDate) throws DAOException;

	public String updatePO(Integer id, String poBy, String updatedBy,
			String updatedDate) throws DAOException;

	public int updatePOItem(InventoryPurchaseOrder purchaseOrder)
			throws DAOException;

	public String deletePOItem(Integer poId, Integer poItemId, Integer storeId)
			throws DAOException;
	
	//added on 23.04.2018
	public String deletePO(Integer poId, Integer storeId)
			throws DAOException;

	public InventoryType getInventoryTypeById(Integer inventoryTypeId)
			throws DAOException;

	public void updateInventoryType(InventoryType inventoryType)
			throws DAOException;

	public List<InventoryStockIn> getInventoryStockInById(Integer storeId,
			Integer id) throws DAOException;

	public int createStockIn(InventoryStockIn inventoryStockIn)
			throws DAOException;

	public List<InventoryStockOut> getInventoryStockOutById(Integer storeId,
			Integer id) throws DAOException;

	public String createStockOut(InventoryStockOut inventoryStockOut)
			throws DAOException;

	public String closeStockIn(InventoryStockIn inventoryStockIn) throws DAOException;

	public int updateStockInItem(InventoryStockIn inventoryStockIn)
			throws DAOException;

	public String deleteStockInItem(Integer stockInId, Integer stockInItemId,
			Integer storeId) throws DAOException;
	
	//added on 30.04.2018
	public String deleteStockIn(Integer stockInId, Integer storeId) throws DAOException;

	public String updateEachStockInItem(
			InventoryStockInItem inventoryStockInItem) throws DAOException;

	public void updateInventoryTypeItem(InventoryItems inventoryTypeItem)
			throws DAOException;

	public List<ItemCurrentStock> getCurrentStockByItem(Integer storeId, Integer itemId)
			throws DAOException;

	public String updateEachPOItem(
			InventoryPurchaseOrderItem inventoryPurchaseOrderItem)
			throws DAOException;

	public int addVendor(InventoryVendor inventoryVendor) throws DAOException;

	public int assignPrinter(PrintKotMaster kotMaster) throws DAOException;

	public String updateVendor(InventoryVendor inventoryVendor)
			throws DAOException;

	public String updateInvntryType(InventoryType inventoryType)
			throws DAOException;

	public String deleteVendor(Integer id, Integer storeId) throws DAOException;

	public String deleteInvntryType(Integer id, Integer storeId)
			throws DAOException;

	public String deleteInvntryTypeItem(Integer id, Integer storeId)
			throws DAOException;

	public int addNewInventoryItem(InventoryItems inventoryItems)
			throws DAOException;

	public String updateInventoryItem(InventoryItems inventoryItems)
			throws DAOException;

	public List<InventoryVendor> getInventoryVendor(Integer storeId)
			throws DAOException;

	public void updateInventoryVendor(InventoryVendor inventoryVendor)
			throws DAOException;

	public void deleteInventoryItems(InventoryItems inventoryTypeItem)
			throws DAOException;

	public void deleteInventoryType(InventoryType inventoryType)
			throws DAOException;

	public void deleteInventoryVendor(InventoryVendor inventoryVendor)
			throws DAOException;

	public int addInvntoryType(InventoryType inventoryType) throws DAOException;

	public void poPrintInThreeInch(
			List<InventoryPurchaseOrder> inventoryPoOrders) throws DAOException;

	public List<RecipeIngredient> getRecipeIngredients(Integer storeId)
			throws DAOException;

	public List<EstimateType> getEstimateTypes(Integer storeId)
			throws DAOException;

	public List<PrintKotMaster> getAllServerPrinters(Integer storeId)
			throws DAOException;

	public int createFgStockIn(FgStockIn fgStockIn) throws DAOException;

	public String updateFgStockInItem(FgStockInItemsChild fgStockInItemsChild)
			throws DAOException;

	public String approveFgStockIn(Integer storeId, Integer id, String by)
			throws DAOException;

	public ResultSet getCurrentStockByStore(Integer storeId) throws DAOException;

	public String updateEachRawStockOutItem(
			InventoryStockOutItem inventoryStockOutItem) throws DAOException;

	public String deleteStockOutItem(Integer stockOutId, Integer stockOutItemId,
			Integer storeId) throws DAOException;
	
	//added on 16.01.2020
	public String deleteStockOut(Integer stockOutId, Integer storeId) throws DAOException;

	public String approveRawStockOut(Integer id, Integer storeId,
			String approvedBy, String updatedBy, String updatedDate)
			throws DAOException;
	
	public InventoryItems getInventoryItemsByCode(Integer storeId,
			String code) throws DAOException;
	
	//added on 02.05.2018
	public List<InventoryInvoicePayment> getCreditInfoByVendor(Integer id,
			Integer storeId) throws DAOException;
	
	public String invoicePayment(InventoryInvoicePayment payment)
			throws DAOException;
	
	//added on 06.06.2018
	public List<ReturnTypes> getReturnCauses(Integer storeId)
			throws DAOException;
	
	public int createSalesReturn(SalesReturn salesReturn) throws DAOException;
	
	public SalesReturn getSalesReturn(Integer id, Integer storeId)
			throws DAOException;
	
	public SalesReturn getSalesReturnByOrderId(Integer orderId, Integer storeId)
			throws DAOException;
	
	public String adjustReturnSales(Integer id, Integer storeId)
			throws DAOException;
	//added on 14.06.2018
	public String approveSalesReturn(SalesReturn salesReturn)
			throws DAOException;
	
	public String deleteSalesReturn(Integer returnId,  Integer storeId) throws DAOException;
	
  public String printRefundBill(String orderid, Integer storeid) throws DAOException;
  
  //added on 09.07.2018
	public int createPurchaseReturn(InventoryReturn inventoryReturn)
			throws DAOException;
	public int updatePurchaseReturn(InventoryReturn inventoryReturn)
			throws DAOException;
	public String deletePurchaseReturn(Integer returnId, Integer storeId) throws DAOException;
	
	public String approvePurchaseReturn(InventoryReturn inventoryReturn) throws DAOException;
	
	public List<InventoryReturn> getPurchaseReturn(Integer storeId,String date) throws DAOException;
	
	public InventoryReturn getPurchaseReturnById(Integer id, Integer storeId) throws DAOException;
	public List<AccountTypeDTO> getAllAccountType(CommonResultSetMapper mapper) throws DAOException;

	public List<AccountGroupDTO> getAllAccountGroup(CommonResultSetMapper mapper) throws DAOException;

	public ResponseObj updateAccountGroup(AccountGroupDTO accountGroupDTO)throws DAOException;

	public ResponseObj deleteAccountGroup(CommonResultSetMapper mapper)throws DAOException;

	public ResponseObj createAccountGroup(AccountGroupDTO accountGroupDTO)throws DAOException;

	public List<AccountDTO> getAllAccounts(CommonResultSetMapper mapper)throws DAOException;

	public ResponseObj createAccount(AccountMaster master)throws DAOException;

	public ResponseObj getDuplicateAccount(CommonResultSetMapper mapper) throws DAOException;

	public ResponseObj updateAccount(AccountMaster master)throws DAOException;

	public ResponseObj deleteAccount(CommonResultSetMapper mapper)throws DAOException;

	public List<ChartOfAccountDTO> getChartOfAccount(CommonResultSetMapper mapper) throws DAOException;

	public String addJournal(JournalListDTO journallistDTO) throws DAOException;
	public List<ParaJournalTypeMaster> getJournalTypeByQS(CommonResultSetMapper mapper)throws DAOException;

	public List<AccountDTO> getAccountsAutocomplete(CommonResultSetMapper mapper) throws DAOException;

	public ResponseObj deleteJournal(CommonResultSetMapper mapper) throws DAOException;

	public List<JournalDTO> getAllJournalByType(CommonResultSetMapper mapper) throws DAOException;

	public List<JournalListDTO> getJournalById(CommonResultSetMapper mapper) throws DAOException;

	public List<AccountDTO> getAccountsAutocompleteByGroup(CommonResultSetMapper mapper) throws DAOException;

	public List<AccountDTO> getLedgerDetailsByCode(CommonResultSetMapper mapper) throws DAOException;
	//added on 04.03.2019
	public List<FgStockIn> getSimpleFgStockInByDate(Integer id, String date) throws DAOException;
	//added on 05.03.2019
	public List<FgItemCurrentStock> getFgCurrentStockByItem(Integer storeId, Integer itemId)
			throws DAOException;
	public FgStockIn getFgStockInById(Integer storeId,
			Integer id) throws DAOException;
	//added on 15.11.2019
	public List<FgStockInItemsChild> getFgStockInItemsByItemId(Integer storeId,
			Integer itemId) throws DAOException;
	public int updateFgStockIn(FgStockIn fgStockIn)
			throws DAOException;
	public String deleteFgStockIn(Integer fgstockInId, Integer storeId) throws DAOException;

	//added on 17.06.2019
	public int createFgReturn(FgReturn fgReturn)
				throws DAOException;
	public int updateFgReturn(FgReturn fgReturn)
				throws DAOException;
	public String deleteFgReturn(Integer fgreturnId, Integer storeId) throws DAOException;
		
	public String approveFgReturn(FgReturn fgReturn) throws DAOException;
		
	public List<FgReturn> getFgReturn(Integer storeId,String date) throws DAOException;
		
	public FgReturn getFgReturnById(Integer id, Integer storeId) throws DAOException;
	
	public List<FgInvoicePayment> getFgCreditInfoByVendor(Integer id,
			Integer storeId) throws DAOException;
	public String invoiceFgPayment(FgInvoicePayment payment) throws DAOException;
	//added on 01.07.2019
	public double getFgPrevRetQtyByItem(Integer storeId, Integer itemId,Integer stockinId)
			throws DAOException;
	//added on 30.07.2019
	public List<InventoryItems> getRawItemQtytobeSoldOut(Integer storeId,
			String date) throws DAOException;
}

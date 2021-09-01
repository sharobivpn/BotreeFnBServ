package com.botree.restaurantapp.service;

import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

import javax.print.PrintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.commonUtil.CommonResultSetMapper;
import com.botree.restaurantapp.commonUtil.ResponseObj;
import com.botree.restaurantapp.dao.InventoryDAO;
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
import com.botree.restaurantapp.print.PrinterMaster;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class InventoryService {

  @Autowired
	private InventoryDAO inventoryDAO;

	public InventoryService() {

	}

	public List<InventoryType> getInventoryType(Integer storeId)
			throws ServiceException {
		List<InventoryType> inventoryTypes = null;
		try {

			System.out.println("Enter InventoryService.getInventoryType ");

			inventoryTypes = inventoryDAO.getInventoryType(storeId);
			System.out.println("exit InventoryService.getInventoryType ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryTypes;
	}

	public List<PrinterMaster> getAllInstalledPrinters()
			throws ServiceException {


		List<PrinterMaster> printerMasters = new ArrayList<PrinterMaster>();
		try {

			PrintService[] service = PrinterJob.lookupPrintServices();
			for (int i = 0; i < service.length; i++) {
				System.out.println("printers:" + service[i].getName());
				PrinterMaster master = new PrinterMaster();
				master.setName(service[i].getName());
				printerMasters.add(master);
				// printers = printers + service[i].getName() + "\n";

			}

		} catch (Exception e) {
			throw new ServiceException("printer get error", e);

		}
		return printerMasters;
	}

	public List<PrintKotMaster> getAllServerPrinters(Integer storeId)
			throws ServiceException {

		List<PrintKotMaster> printers = null;
		try {

			printers = inventoryDAO.getAllServerPrinters(storeId);

		} catch (DAOException e) {
			throw new ServiceException("get all printers server error", e);

		}
		return printers;
	}

	public List<InventoryItems> getInventoryItems(Integer storeId)
			throws ServiceException {
		List<InventoryItems> inventoryItems = null;
		try {
			inventoryItems = inventoryDAO.getInventoryItems(storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory getInventoryItems error", e);

		}
		return inventoryItems;
	}

	public List<InventoryItems> getInventoryItemsByName(Integer storeId,
			String name) throws ServiceException {
		List<InventoryItems> inventoryItems = null;
		try {

			System.out
					.println("Enter InventoryService.getInventoryItemsByName ");

			inventoryItems = inventoryDAO
					.getInventoryItemsByName(storeId, name);
			System.out
					.println("exit InventoryService.getInventoryItemsByName ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryItems;
	}

	public InventoryItems getInventoryItemsByCode(Integer storeId, String code)
			throws ServiceException {
		InventoryItems inventoryItems = null;
		try {

			inventoryItems = inventoryDAO
					.getInventoryItemsByCode(storeId, code);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryItems;
	}

	public List<InventoryVendor> getVendors(Integer storeId)
			throws ServiceException {
		List<InventoryVendor> inventoryVendors = null;
		try {

			//System.out.println("Enter InventoryService.getVendors ");

			inventoryVendors = inventoryDAO.getVendors(storeId);
			//System.out.println("exit InventoryService.getVendors ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryVendors;
	}

	public List<InventoryPurchaseOrder> getPurchaseOrdersByDate(Integer storeId,
			String date) throws ServiceException {
		List<InventoryPurchaseOrder> inventoryVendors = null;
		try {

			System.out
					.println("Enter InventoryService.getPurchaseOrdersByDate ");

			inventoryVendors = inventoryDAO.getPurchaseOrdersByDate(storeId,
					date);
			System.out
					.println("exit InventoryService.getPurchaseOrdersByDate ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryVendors;
	}

	public List<InventoryPurchaseOrder> getPurchaseOrdersById(Integer storeId,
			Integer id) throws ServiceException {
		List<InventoryPurchaseOrder> inventoryVendors = null;
		try {

			System.out.println("Enter InventoryService.getPurchaseOrdersById ");

			inventoryVendors = inventoryDAO.getPurchaseOrdersById(storeId, id);
			System.out.println("exit InventoryService.getPurchaseOrdersById ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryVendors;
	}

	public List<InventoryStockIn> getInventoryStockIn(Integer storeId,
			String date) throws ServiceException {
		List<InventoryStockIn> inventoryStockIn = null;
		try {

			//System.out.println("Enter InventoryService.getInventoryStockIn ");

			inventoryStockIn = inventoryDAO.getInventoryStockIn(storeId, date);
			//System.out.println("exit InventoryService.getInventoryStockIn ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryStockIn;
	}

	public List<InventoryStockIn> getInventoryStockInById(Integer storeId,
			Integer id) throws ServiceException {
		List<InventoryStockIn> inventoryStockIn = null;
		try {
			inventoryStockIn = inventoryDAO
					.getInventoryStockInById(storeId, id);
		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryStockIn;
	}

	public String approvePO(Integer id, String approvedBy, String updatedBy,
			String updatedDate) throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.approvePO(id, approvedBy, updatedBy,
					updatedDate);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return status;
	}

	public String approveRawStockOut(Integer id, Integer storeId,
			String approvedBy, String updatedBy, String updatedDate)
			throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.approveRawStockOut(id, storeId, approvedBy,
					updatedBy, updatedDate);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return status;
	}

	public String approveFgStockIn(Integer storeId, Integer id, String by)
			throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.approveFgStockIn(storeId, id, by);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return status;
	}

	public String updatePO(Integer id, String poBy, String updatedBy,
			String updatedDate) throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.updatePO(id, poBy, updatedBy, updatedDate);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return status;
	}

	public List<InventoryStockOut> getInventoryStockOut(Integer storeId,
			String date) throws ServiceException {
		List<InventoryStockOut> inventoryStockOut = null;
		try {
			inventoryStockOut = inventoryDAO
					.getInventoryStockOut(storeId, date);
		} catch (DAOException e) {
			throw new ServiceException("inventory getInventoryStockOut by date error", e);

		}
		return inventoryStockOut;
	}

	public void addInventoryType(InventoryType inventoryType)
			throws ServiceException {

		try {
			inventoryDAO.addInventoryType(inventoryType);
		} catch (DAOException e) {
			System.out.println("service:: addInventoryType: " + e.getMessage());
			e.printStackTrace();

			throw new ServiceException(e.getMessage(), e);
		}
	}

	public void addInventoryItem(InventoryItems inventoryItem)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.addInventoryItem ");
			inventoryDAO.addInventoryItem(inventoryItem);
			System.out.println("Enter InventoryService.addInventoryItem ");
		} catch (DAOException e) {
			System.out.println("service:: addInventoryItem: " + e.getMessage());
			e.printStackTrace();

			throw new ServiceException(e.getMessage(), e);
		}
	}

	public void addInventoryVendor(InventoryVendor inventoryVendor)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.addInventoryVendor ");
			inventoryDAO.addInventoryVendor(inventoryVendor);
			System.out.println("Enter InventoryService.addInventoryVendor ");
		} catch (DAOException e) {
			System.out.println("service:: addInventoryVendor: "
					+ e.getMessage());
			e.printStackTrace();

			throw new ServiceException(e.getMessage(), e);
		}
	}

	public int createPO(InventoryPurchaseOrder purchaseOrder)
			throws ServiceException {
		int purchaseOrderId = 0;
		try {
			System.out.println("Enter InventoryService.createPO ");
			purchaseOrderId = inventoryDAO.createPO(purchaseOrder);
			System.out.println("exit InventoryService.createPO ");

		} catch (DAOException e) {
			throw new ServiceException("error creating PO", e);

		}
		return purchaseOrderId;
	}

	public int updatePOItem(InventoryPurchaseOrder purchaseOrder)
			throws ServiceException {
		int purchaseOrderId = 0;
		try {
			System.out.println("Enter InventoryService.updatePOItem ");
			purchaseOrderId = inventoryDAO.updatePOItem(purchaseOrder);
			System.out.println("exit InventoryService.updatePOItem ");

		} catch (DAOException e) {
			throw new ServiceException("error creating updatePOItem", e);

		}
		return purchaseOrderId;
	}

	public String deletePOItem(Integer poId, Integer poItemId, Integer storeId)
			throws ServiceException {
		String message = "";
		try {

			System.out.println("Enter InventoryService.deletePOItem ");

			message = inventoryDAO.deletePOItem(poId, poItemId, storeId);
			System.out.println("exit InventoryService.deletePOItem ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return message;
	}
	
	//added on 23.04.2018
	public String deletePO(Integer poId, Integer storeId)
			throws ServiceException {
		String message = "";
		try {

			System.out.println("Enter InventoryService.deletePO ");

			message = inventoryDAO.deletePO(poId, storeId);
			System.out.println("exit InventoryService.deletePO ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return message;
	}
	
	public String deleteVendor(Integer id, Integer storeId)
			throws ServiceException {
		String message = "";
		try {

			message = inventoryDAO.deleteVendor(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("vendor delete error", e);

		}
		return message;
	}

	public String deleteInvntryType(Integer id, Integer storeId)
			throws ServiceException {
		String message = "";
		try {

			message = inventoryDAO.deleteInvntryType(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory type delete error", e);

		}
		return message;
	}

	public String deleteInvntryTypeItem(Integer id, Integer storeId)
			throws ServiceException {
		String message = "";
		try {

			message = inventoryDAO.deleteInvntryTypeItem(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory type item delete error", e);

		}
		return message;
	}

	public InventoryType getInventoryTypeById(Integer inventoryTypeId)
			throws ServiceException {

		InventoryType inventoryType = null;

		try {
			System.out.println("Enter InventoryService.getInventoryTypeById ");
			// MenuCategory menuCategory=menuDAO.getMenu();
			inventoryType = inventoryDAO.getInventoryTypeById(inventoryTypeId);

			System.out.println("Exit InventoryService.getInventoryTypeById ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to get inventory Type", e);
		}
		return inventoryType;

	}

	public void updateInventoryType(InventoryType inventoryType)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.update ");

			inventoryDAO.updateInventoryType(inventoryType);

			System.out.println("Exit InventoryService.update ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to update inventoryType", e);
		}

	}

	public void updateInventoryTypeItem(InventoryItems inventoryTypeItem)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.update ");

			inventoryDAO.updateInventoryTypeItem(inventoryTypeItem);

			System.out.println("Exit InventoryService.update ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to update inventoryType", e);
		}

	}

	public String closeStockIn(InventoryStockIn inventoryStockIn) throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.closeStockIn(inventoryStockIn);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return status;
	}

	public int updateStockInItem(InventoryStockIn inventoryStockIn)
			throws ServiceException {
		int invStckInId = 0;
		try {
			invStckInId = inventoryDAO.updateStockInItem(inventoryStockIn);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateStockInItem", e);

		}
		return invStckInId;
	}

	public String updateEachStockInItem(
			InventoryStockInItem inventoryStockInItem) throws ServiceException {

		String messaString = "";
		try {
			messaString = inventoryDAO
					.updateEachStockInItem(inventoryStockInItem);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateEachStockInItem",
					e);

		}
		return messaString;
	}

	public String updateEachRawStockOutItem(
			InventoryStockOutItem inventoryStockOutItem)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = inventoryDAO
					.updateEachRawStockOutItem(inventoryStockOutItem);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateEachStockInItem",
					e);

		}
		return messaString;
	}

	public String updateEachPOItem(
			InventoryPurchaseOrderItem inventoryPurchaseOrderItem)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = inventoryDAO
					.updateEachPOItem(inventoryPurchaseOrderItem);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateEachPOItem", e);

		}
		return messaString;
	}

	public String updateFgStockInItem(FgStockInItemsChild fgStockInItemsChild)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = inventoryDAO.updateFgStockInItem(fgStockInItemsChild);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateEachPOItem", e);

		}
		return messaString;
	}

	public String updateVendor(InventoryVendor inventoryVendor)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = inventoryDAO.updateVendor(inventoryVendor);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateVendor", e);

		}
		return messaString;
	}

	public String updateInvntryType(InventoryType inventoryType)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = inventoryDAO.updateInvntryType(inventoryType);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateInvntryType", e);

		}
		return messaString;
	}

	public String deleteStockInItem(Integer stockInId, Integer stockInItemId,
			Integer storeId) throws ServiceException {
		String message = "";
		try {
			message = inventoryDAO.deleteStockInItem(stockInId, stockInItemId,
					storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return message;
	}
	
	//added on 30.04.2018
	public String deleteStockIn(Integer stockInId,
			Integer storeId) throws ServiceException {
		String message = "";
		try {
			message = inventoryDAO.deleteStockIn(stockInId, storeId);
		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return message;
	}
	
	//added on 16.01.2020
		public String deleteStockOut(Integer stockOutId,
				Integer storeId) throws ServiceException {
			String message = "";
			try {
				message = inventoryDAO.deleteStockOut(stockOutId, storeId);
			} catch (DAOException e) {
				throw new ServiceException("inventory get error", e);

			}
			return message;
		}

	public String deleteStockOutItem(Integer stockOutId, Integer stockOutItemId,
			Integer storeId) throws ServiceException {
		String message = "";
		try {
			message = inventoryDAO.deleteStockOutItem(stockOutId,
					stockOutItemId, storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return message;
	}

	public int createStockIn(InventoryStockIn inventoryStockIn)
			throws ServiceException {
		int stockInId = 0;
		try {
			System.out.println("Enter InventoryService.createStockIn ");
			stockInId = inventoryDAO.createStockIn(inventoryStockIn);
			System.out.println("exit InventoryService.createStockIn ");

		} catch (DAOException e) {
			throw new ServiceException("error creating PO", e);

		}
		return stockInId;
	}

	public int createFgStockIn(FgStockIn fgStockIn) throws ServiceException {
		int stockInId = 0;
		try {

			stockInId = inventoryDAO.createFgStockIn(fgStockIn);

		} catch (DAOException e) {
			throw new ServiceException("error creating fgstockin", e);

		}
		return stockInId;
	}

	public int addVendor(InventoryVendor inventoryVendor)
			throws ServiceException {
		int vendorId = 0;
		try {
			vendorId = inventoryDAO.addVendor(inventoryVendor);

		} catch (DAOException e) {
			throw new ServiceException("error creating vendor", e);

		}
		return vendorId;
	}

	public int assignPrinter(PrintKotMaster kotMaster) throws ServiceException {
		int vendorId = 0;
		try {
			vendorId = inventoryDAO.assignPrinter(kotMaster);

		} catch (DAOException e) {
			throw new ServiceException("error creating vendor", e);

		}
		return vendorId;
	}

	public int addInvntoryType(InventoryType inventoryType)
			throws ServiceException {
		int inventryTypeId = 0;
		try {
			inventryTypeId = inventoryDAO.addInvntoryType(inventoryType);

		} catch (DAOException e) {
			throw new ServiceException("error creating addInvntoryType", e);

		}
		return inventryTypeId;
	}

	public int addNewInventoryItem(InventoryItems inventoryItems)
			throws ServiceException {
		int itemId = 0;
		try {
			itemId = inventoryDAO.addNewInventoryItem(inventoryItems);

		} catch (DAOException e) {
			throw new ServiceException("error creating inventory item", e);

		}
		return itemId;
	}

	public String updateInventoryItem(InventoryItems inventoryItems)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = inventoryDAO.updateInventoryItem(inventoryItems);

		} catch (DAOException e) {
			throw new ServiceException("error updating updateInventoryItem", e);

		}
		return messaString;
	}

	public String createStockOut(InventoryStockOut inventoryStockOut)
			throws ServiceException {
		String stockOutId = "";
		try {
			stockOutId = inventoryDAO.createStockOut(inventoryStockOut);

		} catch (DAOException e) {
			throw new ServiceException("error creating StockOut", e);

		}
		return stockOutId;
	}

	public List<InventoryStockOut> getInventoryStockOutById(Integer storeId,
			Integer id) throws ServiceException {
		List<InventoryStockOut> inventoryStockOut = null;
		try {
			inventoryStockOut = inventoryDAO.getInventoryStockOutById(storeId,
					id);
		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryStockOut;
	}

	public List<ItemCurrentStock> getCurrentStockByItem(Integer storeId, Integer itemId)
			throws ServiceException {
		List<ItemCurrentStock> itemCurrentStockList = null;
		try {
			itemCurrentStockList = inventoryDAO.getCurrentStockByItem(storeId, itemId);
		} catch (DAOException e) {
			throw new ServiceException("inventory get curr stock error", e);

		}
		return itemCurrentStockList;
	}

	public List<InventoryVendor> getInventoryVendor(Integer storeId)
			throws ServiceException {
		List<InventoryVendor> inventoryVendor = null;
		try {

			System.out.println("Enter InventoryService.getInventoryVendor ");

			inventoryVendor = inventoryDAO.getInventoryVendor(storeId);
			System.out.println("exit InventoryService.getInventoryVendor ");

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return inventoryVendor;
	}

	public void updateInventoryVendor(InventoryVendor inventoryVendor)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.updateInventoryVendor ");

			inventoryDAO.updateInventoryVendor(inventoryVendor);

			System.out.println("Exit InventoryService.updateInventoryVendor ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to update InventoryVendor", e);
		}

	}

	public void deleteInventoryItems(InventoryItems inventoryTypeItem)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.delete ");

			inventoryDAO.deleteInventoryItems(inventoryTypeItem);

			System.out.println("Exit InventoryService.delete ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to delete InventoryTypeItem",
					e);
		}

	}

	public void deleteInventoryType(InventoryType inventoryType)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.deleteInventoryType ");

			inventoryDAO.deleteInventoryType(inventoryType);

			System.out.println("Exit InventoryService.deleteInventoryType ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to delete deleteInventoryType",
					e);
		}

	}

	public void deleteInventoryVendor(InventoryVendor inventoryVendor)
			throws ServiceException {

		try {
			System.out.println("Enter InventoryService.deleteInventoryType ");

			inventoryDAO.deleteInventoryVendor(inventoryVendor);

			System.out.println("Exit InventoryService.deleteInventoryVendor ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to delete deleteInventoryVendor",
					e);
		}

	}

	public void poPrintInThreeInch(
			List<InventoryPurchaseOrder> inventoryPoOrders)
			throws ServiceException {

		try {

			inventoryDAO.poPrintInThreeInch(inventoryPoOrders);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occured while trying to print po server", e);
		}

	}

	public List<RecipeIngredient> getRecipeIngredients(Integer storeId)
			throws ServiceException {
		List<RecipeIngredient> recipeIngredients = null;
		try {
			recipeIngredients = inventoryDAO.getRecipeIngredients(storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return recipeIngredients;
	}

	public List<EstimateType> getEstimateTypes(Integer storeId)
			throws ServiceException {
		List<EstimateType> estimateTypes = null;
		try {
			estimateTypes = inventoryDAO.getEstimateTypes(storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return estimateTypes;
	}
	
	//added on 02.05.2018
	public List<InventoryInvoicePayment> getCreditInfoByVendor(Integer vendorId,
			Integer storeId) throws ServiceException {

		List<InventoryInvoicePayment> invoicePayments = null;
		try {
			invoicePayments = inventoryDAO.getCreditInfoByVendor(vendorId,
					storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return invoicePayments;
	}
	
	public String invoicePayment(InventoryInvoicePayment payment)
			throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.invoicePayment(payment);

		} catch (DAOException e) {
			throw new ServiceException("error creating addInvntoryType", e);

		}
		return status;
	}
	
	//added on 06.06.2018
	public List<ReturnTypes> getReturnCauses(Integer storeId)
			throws ServiceException {
		List<ReturnTypes> returnTypes = null;
		try {
			returnTypes = inventoryDAO.getReturnCauses(storeId);
		} catch (DAOException e) {
			throw new ServiceException("inventory get return causes error", e);
		}
		return returnTypes;
	}
	
	public int createSalesReturn(SalesReturn salesReturn)
			throws ServiceException {
		int salereturnId = 0;
		try {
			salereturnId = inventoryDAO.createSalesReturn(salesReturn);

		} catch (DAOException e) {
			throw new ServiceException("error creating sale return", e);

		}
		return salereturnId;
	}
	
	public SalesReturn getSalesReturn(Integer id, Integer storeId)
			throws ServiceException {
		SalesReturn salesReturn = null;
		try {

			salesReturn = inventoryDAO.getSalesReturn(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get sale return error", e);

		}
		return salesReturn;
	}
	
	public SalesReturn getSalesReturnByOrderId(Integer orderId, Integer storeId)
			throws ServiceException {
		SalesReturn salesReturn = null;
		try {

			salesReturn = inventoryDAO.getSalesReturnByOrderId(orderId, storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get sale return by order id error", e);

		}
		return salesReturn;
	}
	
	
	public String adjustReturnSales(Integer id, Integer storeId)
			throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.adjustReturnSales(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory get error", e);

		}
		return status;
	}
	
	public String approveSalesReturn(SalesReturn salesReturn)
			throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.approveSalesReturn(salesReturn);

		} catch (DAOException e) {
			throw new ServiceException("approve sale return error", e);

		}
		return status;
	}
	
	public String deleteSalesReturn(Integer returnId,
			Integer storeId) throws ServiceException {
		String message = "";
		try {
			message = inventoryDAO.deleteSalesReturn(returnId,
					storeId);

		} catch (DAOException e) {
			throw new ServiceException("delete sale return get error", e);

		}
		return message;
	}
	
  public String printRefundBill(String orderid, Integer storeid)
      throws ServiceException {

    String status = "";
    // MobPrintBill mobPrintBill;

    try {
      status = inventoryDAO.printRefundBill(orderid, storeid);

    } catch (DAOException e) {
      e.printStackTrace();
      throw new ServiceException(
          "problem occured while trying to print refund bill", e);

    }

    return status;

  }
  
	//added on 09.07.2018
	public int createPurchaseReturn(InventoryReturn inventoryReturn)
			throws ServiceException {
		int returnId = 0;
		try {
			returnId = inventoryDAO.createPurchaseReturn(inventoryReturn);

		} catch (DAOException e) {
			throw new ServiceException("error creating purchase return", e);

		}
		return returnId;
	}
	
	public int updatePurchaseReturn(InventoryReturn inventoryReturn)
			throws ServiceException {
		int returnId = 0;
		try {
			returnId = inventoryDAO.updatePurchaseReturn(inventoryReturn);

		} catch (DAOException e) {
			throw new ServiceException("error updating purchase return", e);

		}
		return returnId;
	}
	
	public String deletePurchaseReturn(Integer returnId,
			Integer storeId) throws ServiceException {
		String message = "";
		try {
			message = inventoryDAO.deletePurchaseReturn(returnId, storeId);
		} catch (DAOException e) {
			throw new ServiceException("deleteting purchase return get error", e);

		}
		return message;
	}
	
	public String approvePurchaseReturn(InventoryReturn inventoryReturn) throws ServiceException {
		String status = "";
		try {
			status = inventoryDAO.approvePurchaseReturn(inventoryReturn);

		} catch (DAOException e) {
			throw new ServiceException("approving purchase return get error", e);

		}
		return status;
	}
	
	public List<InventoryReturn> getPurchaseReturn(Integer storeId,
			String date) throws ServiceException {
		List<InventoryReturn> inventoryReturns = null;
		try {
			inventoryReturns = inventoryDAO.getPurchaseReturn(storeId, date);

		} catch (DAOException e) {
			throw new ServiceException("inventory get pur ret by date error", e);

		}
		return inventoryReturns;
	}
	
	public InventoryReturn getPurchaseReturnById(Integer id, Integer storeId)
			throws ServiceException {
		InventoryReturn inventoryReturn = null;
		try {

			inventoryReturn = inventoryDAO.getPurchaseReturnById(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("inventory pur return by id get error", e);

		}
		return inventoryReturn;
	}
	
	public List<FgStockIn> getSimpleFgStockInByDate(Integer storeId,
			String date) throws ServiceException {
		List<FgStockIn> simpleFgStockIns = null;
		try {

			simpleFgStockIns = inventoryDAO.getSimpleFgStockInByDate(
					storeId, date);

		} catch (DAOException e) {
			throw new ServiceException("getsimplegfstockinby date error+", e);

		}
		return simpleFgStockIns;
	}
	
	public List<FgItemCurrentStock> getFgCurrentStockByItem(Integer storeId, Integer itemId)
			throws ServiceException {
		List<FgItemCurrentStock> fgItemCurrentStockList = null;
		try {
			fgItemCurrentStockList = inventoryDAO.getFgCurrentStockByItem(storeId, itemId);
		} catch (DAOException e) {
			throw new ServiceException("inventory get fg curr stock error", e);

		}
		return fgItemCurrentStockList;
	}
	
	public FgStockIn getFgStockInById(Integer storeId,
			Integer id) throws ServiceException {
		FgStockIn fgStockIn = null;
		try {
			fgStockIn = inventoryDAO
					.getFgStockInById(storeId, id);
		} catch (DAOException e) {
			throw new ServiceException("inventory get fgstockin by id error", e);

		}
		return fgStockIn;
	}
	
	public List<FgStockInItemsChild> getFgStockInItemsByItemId(Integer storeId,
			Integer itemId) throws ServiceException {
		List<FgStockInItemsChild> fgStockInItems = null;
		try {
			fgStockInItems = inventoryDAO.getFgStockInItemsByItemId(storeId, itemId);
		} catch (DAOException e) {
			throw new ServiceException("inventory get fgstockinitems by itemid error", e);

		}
		return fgStockInItems;
	}
	
	public int updateFgStockIn(FgStockIn fgStockIn)
			throws ServiceException {
		int fgStckInId = 0;
		try {
			fgStckInId = inventoryDAO.updateFgStockIn(fgStockIn);

		} catch (DAOException e) {
			throw new ServiceException("error creating updatefgstockin", e);

		}
		return fgStckInId;
	}
	
	public String deleteFgStockIn(Integer fgstockInId,
			Integer storeId) throws ServiceException {
		String message = "";
		try {
			message = inventoryDAO.deleteFgStockIn(fgstockInId, storeId);
		} catch (DAOException e) {
			throw new ServiceException("inventory delete fgstockin error", e);

		}
		return message;
	}
	
	//added on 17.06.2019
		public int createFgReturn(FgReturn fgReturn)
				throws ServiceException {
			int returnId = 0;
			try {
				returnId = inventoryDAO.createFgReturn(fgReturn);

			} catch (DAOException e) {
				throw new ServiceException("error creating fg purchase return", e);

			}
			return returnId;
		}
		
		public int updateFgReturn(FgReturn fgReturn)
				throws ServiceException {
			int returnId = 0;
			try {
				returnId = inventoryDAO.updateFgReturn(fgReturn);

			} catch (DAOException e) {
				throw new ServiceException("error updating fg purchase return", e);

			}
			return returnId;
		}
		
		public String deleteFgReturn(Integer fgreturnId,
				Integer storeId) throws ServiceException {
			String message = "";
			try {
				message = inventoryDAO.deleteFgReturn(fgreturnId, storeId);
			} catch (DAOException e) {
				throw new ServiceException("deleteting fg purchase return get error", e);

			}
			return message;
		}
		
		public String approveFgReturn(FgReturn fgReturn) throws ServiceException {
			String status = "";
			try {
				status = inventoryDAO.approveFgReturn(fgReturn);

			} catch (DAOException e) {
				throw new ServiceException("approving fg purchase return get error", e);

			}
			return status;
		}
		
		public List<FgReturn> getFgReturn(Integer storeId,
				String date) throws ServiceException {
			List<FgReturn> fgReturns = null;
			try {
				fgReturns = inventoryDAO.getFgReturn(storeId, date);

			} catch (DAOException e) {
				throw new ServiceException("inventory get fg pur ret by date error", e);

			}
			return fgReturns;
		}
		
		public FgReturn getFgReturnById(Integer id, Integer storeId)
				throws ServiceException {
			FgReturn fgReturn = null;
			try {

				fgReturn = inventoryDAO.getFgReturnById(id, storeId);

			} catch (DAOException e) {
				throw new ServiceException("inventory fg pur return by id get error", e);

			}
			return fgReturn;
		}
		
		//added on 18.06.2019
		public List<FgInvoicePayment> getFgCreditInfoByVendor(Integer vendorId,
				Integer storeId) throws ServiceException {

			List<FgInvoicePayment> invoicePayments = null;
			try {
				invoicePayments = inventoryDAO.getFgCreditInfoByVendor(vendorId,
						storeId);

			} catch (DAOException e) {
				throw new ServiceException("inventory getFgCreditInfoByVendor get error", e);

			}
			return invoicePayments;
		}
		
		public String invoiceFgPayment(FgInvoicePayment payment)
				throws ServiceException {
			String status = "";
			try {
				status = inventoryDAO.invoiceFgPayment(payment);

			} catch (DAOException e) {
				throw new ServiceException("error creating invoiceFgPayment", e);

			}
			return status;
		}
		
		//added on 30.07.2019
		public List<InventoryItems> getRawItemQtytobeSoldOut(Integer storeId,
				String date) throws ServiceException {
			List<InventoryItems> inventoryItems = null;
			try {
				inventoryItems = inventoryDAO.getRawItemQtytobeSoldOut(storeId,
						date);

			} catch (DAOException e) {
				throw new ServiceException("inventory getRawItemQtytobeSoldOut get error", e);

			}
			return inventoryItems;
		}	

	public InventoryDAO getInventoryDAO() {
		return inventoryDAO;
	}

	public void setInventoryDAO(InventoryDAO inventoryDAO) {
		this.inventoryDAO = inventoryDAO;
	}

	public List<AccountTypeDTO> getAllAccountType(CommonResultSetMapper mapper) throws ServiceException {
		List<AccountTypeDTO> typeDTOs = null;
		try {

			typeDTOs = inventoryDAO.getAllAccountType(mapper);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get AccountTypeDTO", e);

		}
		return typeDTOs;
	}

	public List<AccountGroupDTO> getAllAccountGroup(CommonResultSetMapper mapper) throws ServiceException {
		List<AccountGroupDTO> typeDTOs = null;
		try {

			typeDTOs = inventoryDAO.getAllAccountGroup(mapper);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get account group", e);

		}
		return typeDTOs;
	}

	public ResponseObj updateAccountGroup(AccountGroupDTO accountGroupDTO) throws ServiceException {
		ResponseObj responseObj = null;
		try {
			responseObj = inventoryDAO.updateAccountGroup(accountGroupDTO);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error updating Group", e);

		}
		return responseObj;
	}

	public ResponseObj deleteAccountGroup(CommonResultSetMapper mapper) throws ServiceException {
		ResponseObj responseObj = null;
		try {
			responseObj = inventoryDAO.deleteAccountGroup(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error creating Group", e);

		}
		return responseObj;
	}

	public ResponseObj createAccountGroup(AccountGroupDTO accountGroupDTO) throws ServiceException {
		ResponseObj responseObj = null;
		try {
			responseObj = inventoryDAO.createAccountGroup(accountGroupDTO);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error creating Group", e);

		}
		return responseObj;
	}

	public List<ParaJournalTypeMaster> getJournalTypeByQS(CommonResultSetMapper mapper) throws ServiceException {
		List<ParaJournalTypeMaster> pjtms = null;
		try {
			pjtms = inventoryDAO.getJournalTypeByQS(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error getting ParaJournalTypeMaster", e);

		}
		return pjtms;
	}
	public List<AccountDTO> getAllAccounts(CommonResultSetMapper mapper) throws ServiceException{

		List<AccountDTO> accnts = null;
		try {

			accnts = inventoryDAO.getAllAccounts(mapper);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occured while trying to get all accounts", e);

		}
		return accnts;
	}

	public ResponseObj createAccount(AccountMaster master)
			throws ServiceException {
		ResponseObj status = null;
		try {
			
			status = inventoryDAO.createAccount(master);
			

		} catch (DAOException e) {
			throw new ServiceException("error creating account", e);

		}
		return status;
	}

	public ResponseObj getDuplicateAccounts(CommonResultSetMapper mapper) throws ServiceException {
		
		ResponseObj responseObj = null;
		try {
			responseObj = inventoryDAO.getDuplicateAccount(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error fetching duplicate accounts", e);

		}
		return responseObj;
	}

	public ResponseObj updateAccounts(AccountMaster accountMaster) throws ServiceException {

		ResponseObj responseObj = null;
		try {
			responseObj = inventoryDAO.updateAccount(accountMaster);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error updating accounts", e);

		}
		return responseObj;
	}

	public ResponseObj deleteAccounts(CommonResultSetMapper mapper) throws ServiceException {

		ResponseObj responseObj = null;
		try {
			responseObj = inventoryDAO.deleteAccount(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error deleting accounts", e);

		}
		return responseObj;
	}

	public List<ChartOfAccountDTO> getChartOfAccount(CommonResultSetMapper mapper) throws ServiceException {
		List<ChartOfAccountDTO> accnts = null;
		try {
			accnts = inventoryDAO.getChartOfAccount(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error getting chartofaccounts", e);

		}
		return accnts;
	}

	public String addJournal(JournalListDTO journallistDTO) throws ServiceException {
		String responseObj="";
		try {
			responseObj = inventoryDAO.addJournal(journallistDTO);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error updating city", e);

		}
		return responseObj;
	}

	public List<AccountDTO> getAccountsAutocomplete(CommonResultSetMapper mapper) throws ServiceException {
		List<AccountDTO> accnts = null;
		try {
			accnts = inventoryDAO.getAccountsAutocomplete(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error getting accounts", e);

		}
		return accnts;
	}

	public ResponseObj deleteJournal(CommonResultSetMapper mapper) throws ServiceException {
		ResponseObj responseObj = null;
		try {
			responseObj = inventoryDAO.deleteJournal(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error deleting accounts", e);

		}
		return responseObj;
	}

	public List<JournalDTO> getAllJournalByType(CommonResultSetMapper mapper) throws ServiceException {
		List<JournalDTO> journals = null;
		try {
			journals = inventoryDAO.getAllJournalByType(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error getting getAllJournalByType", e);

		}
		return journals;
	}

	public List<JournalListDTO> getJournalById(CommonResultSetMapper mapper) throws ServiceException {
		List<JournalListDTO> journals = null;
		try {
			journals = inventoryDAO.getJournalById(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error getting getJournalById", e);

		}
		return journals;
	}

	public List<AccountDTO> getAccountsAutocompleteByGroup(CommonResultSetMapper mapper) throws ServiceException {
		List<AccountDTO> accnts = null;
		try {
			accnts = inventoryDAO.getAccountsAutocompleteByGroup(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error getting accounts", e);

		}
		return accnts;
	}

	public List<AccountDTO> getLedgerDetailsByCode(CommonResultSetMapper mapper) throws ServiceException  {
		List<AccountDTO> ledgerdetails = null;
		try {
			ledgerdetails = inventoryDAO.getLedgerDetailsByCode(mapper);

		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error getting ledgerdetails", e);

		}
		return ledgerdetails;
	}

}

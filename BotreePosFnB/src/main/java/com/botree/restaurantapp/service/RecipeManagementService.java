package com.botree.restaurantapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.Restaurant.rahulbean.FgClose;
import com.botree.Restaurant.rahulbean.FgCloseChild;
import com.botree.Restaurant.rahulbean.FgSaleOut;
import com.botree.Restaurant.rahulbean.FgSaleOutItemsChild;
import com.botree.Restaurant.rahulbean.RawClose;
import com.botree.Restaurant.rahulbean.RawCloseChild;
import com.botree.restaurantapp.dao.RecipeManagementDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.InventoryPurchaseOrder;
import com.botree.restaurantapp.model.InventoryPurchaseOrderItem;
import com.botree.restaurantapp.model.InventoryStockOutItem;
import com.botree.restaurantapp.model.InventoryVendor;
import com.botree.restaurantapp.model.inv.FgStockInItemsChild;
import com.botree.restaurantapp.model.rm.CookingUnit;
import com.botree.restaurantapp.model.rm.EstimateDailyProd;
import com.botree.restaurantapp.model.rm.EstimateDailyProdItem;
import com.botree.restaurantapp.model.rm.FmcgUnit;
import com.botree.restaurantapp.model.rm.Ingredient;
import com.botree.restaurantapp.model.rm.IngredientList;
import com.botree.restaurantapp.model.rm.MetricUnit;
import com.botree.restaurantapp.model.rm.UnitConversion;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class RecipeManagementService {

  @Autowired
	private RecipeManagementDAO recipeManagementDAO;

	public List<CookingUnit> getAllCookingUnits(Integer storeId)
			throws ServiceException {
		List<CookingUnit> cookingUnits = null;
		try {

			cookingUnits = recipeManagementDAO.getAllCookingUnits(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return cookingUnits;
	}

	public double geMetrictUnitConVersionFactor(Integer storeId,
			Integer fromUnit, Integer toUnit) throws ServiceException {
		double factor = 0.0;
		try {

			factor = recipeManagementDAO.geMetrictUnitConVersionFactor(storeId,
					fromUnit, toUnit);

		} catch (DAOException e) {
			throw new ServiceException("unit conv get error", e);

		}
		return factor;
	}

	public List<FgSaleOutItemsChild> getFgSaleOutByDate(Integer storeId,
			String date) throws ServiceException {
		List<FgSaleOutItemsChild> fgSaleOutItemsChild = null;
		try {

			fgSaleOutItemsChild = recipeManagementDAO.getFgSaleOutByDate(
					storeId, date);

		} catch (DAOException e) {
			throw new ServiceException("", e);

		}
		return fgSaleOutItemsChild;
	}

	public List<FgCloseChild> getFgCloseItemsByDate(Integer storeId, String date)
			throws ServiceException {
		List<FgCloseChild> fgCloseChild = null;
		try {

			fgCloseChild = recipeManagementDAO.getFgCloseItemsByDate(storeId,
					date);

		} catch (DAOException e) {
			throw new ServiceException("", e);

		}
		return fgCloseChild;
	}

	public List<RawCloseChild> getRawCloseItemsByDate(Integer storeId,
			String date) throws ServiceException {
		List<RawCloseChild> rawCloseChild = null;
		try {

			rawCloseChild = recipeManagementDAO.getRawCloseItemsByDate(storeId,
					date);

		} catch (DAOException e) {
			throw new ServiceException("", e);

		}
		return rawCloseChild;
	}

	public List<FmcgUnit> getAllFmcgUnits(Integer storeId)
			throws ServiceException {
		List<FmcgUnit> fmcgUnits = null;
		try {

			fmcgUnits = recipeManagementDAO.getAllFmcgUnits(storeId);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return fmcgUnits;
	}

	public List<Ingredient> getAllIngredients(Integer storeId, Integer itemId)
			throws ServiceException {
		List<Ingredient> ingredients = null;
		try {

			ingredients = recipeManagementDAO
					.getAllIngredients(storeId, itemId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return ingredients;
	}

	public List<Ingredient> getIngredientsForEdp(Integer storeId, Integer edpId)
			throws ServiceException {
		List<Ingredient> ingredients = null;
		try {

			ingredients = recipeManagementDAO.getIngredientsForEdp(storeId,
					edpId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return ingredients;
	}

	public List<EstimateDailyProd> getEstimateDailyProdByDate(Integer storeId,
			String date) throws ServiceException {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {

			estimateDailyProds = recipeManagementDAO
					.getEstimateDailyProdByDate(storeId, date);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return estimateDailyProds;
	}

	public List<EstimateDailyProd> getRequisitionByDate(Integer storeId,
			String date) throws ServiceException {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {

			estimateDailyProds = recipeManagementDAO.getRequisitionByDate(
					storeId, date);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return estimateDailyProds;
	}

	public List<EstimateDailyProd> getFgStockInByDate(Integer storeId,
			String date) throws ServiceException {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {

			estimateDailyProds = recipeManagementDAO.getFgStockInByDate(
					storeId, date);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return estimateDailyProds;
	}

	public List<EstimateDailyProd> getRawStockOutByDate(Integer storeId,
			String date) throws ServiceException {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {

			estimateDailyProds = recipeManagementDAO.getRawStockOutByDate(
					storeId, date);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return estimateDailyProds;
	}

	public List<EstimateDailyProdItem> getEstimateDailyProdItemById(
			Integer storeId, Integer id) throws ServiceException {
		List<EstimateDailyProdItem> estimateDailyProdItems = null;
		try {

			estimateDailyProdItems = recipeManagementDAO
					.getEstimateDailyProdItemById(storeId, id);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return estimateDailyProdItems;
	}

	public List<FgStockInItemsChild> getFgStockInItemsById(Integer storeId,
			Integer fgStockInId) throws ServiceException {
		List<FgStockInItemsChild> fgStockInItemsChilds = null;
		try {

			fgStockInItemsChilds = recipeManagementDAO.getFgStockInItemsById(
					storeId, fgStockInId);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return fgStockInItemsChilds;
	}

	public List<InventoryPurchaseOrderItem> getRequisitionByIds(Integer storeId,
			Integer poId) throws ServiceException {
		List<InventoryPurchaseOrderItem> inventoryPoItems = null;
		try {

			inventoryPoItems = recipeManagementDAO.getRequisitionByIds(storeId,
					poId);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return inventoryPoItems;
	}
	
	//added on 07.05.2018
	public List<InventoryPurchaseOrder> getRequisitionByIdsNew(Integer storeId,
			Integer poId) throws ServiceException {
		List<InventoryPurchaseOrder> inventoryPo = null;
		try {

			inventoryPo = recipeManagementDAO.getRequisitionByIdsNew(storeId,
					poId);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return inventoryPo;
	}

	public List<InventoryStockOutItem> getRawStockOutById(Integer storeId,
			Integer rawStockOutId) throws ServiceException {
		List<InventoryStockOutItem> inventoryStckOutItems = null;
		try {

			inventoryStckOutItems = recipeManagementDAO.getRawStockOutById(
					storeId, rawStockOutId);

		} catch (DAOException e) {
			throw new ServiceException("recipe get error", e);

		}
		return inventoryStckOutItems;
	}

	public List<MetricUnit> getAllMetricUnits(String type)
			throws ServiceException {
		List<MetricUnit> metricUnits = null;
		try {

			metricUnits = recipeManagementDAO.getAllMetricUnits(type);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return metricUnits;
	}
	
	public List<MetricUnit> getAllMetricUnitsbyType(String type)
			throws ServiceException {
		List<MetricUnit> metricUnits = null;
		try {

			metricUnits = recipeManagementDAO.getAllMetricUnitsbyType(type);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return metricUnits;
	}
	
	public List<InventoryVendor> getVendorByEdp(String edpId,Integer storeId)
			throws ServiceException {
		List<InventoryVendor> idList = null;
		try {

			idList = recipeManagementDAO.getVendorByEdp(edpId,storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return idList;
	}

	public MetricUnit getMetricUnit(Integer id) throws ServiceException {
		MetricUnit metricUnit = null;
		try {

			metricUnit = recipeManagementDAO.getMetricUnit(id);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return metricUnit;
	}

	public List<UnitConversion> getAllUnitConversions(Integer storeId)
			throws ServiceException {
		List<UnitConversion> unitConversions = null;
		try {

			unitConversions = recipeManagementDAO
					.getAllUnitConversions(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return unitConversions;
	}

	public int addEstimateDailyProd(EstimateDailyProd estimateDailyProds)
			throws ServiceException {
		int edpId = 0;
		try {
			edpId = recipeManagementDAO
					.addEstimateDailyProd(estimateDailyProds);

		} catch (DAOException e) {
			String excpMsg = e.getMessage();
			if (excpMsg.equalsIgnoreCase("999999999")) {
				return 999999999;
			}
			else if (excpMsg.equalsIgnoreCase("999999998")) {
				return 999999998;
			}else {
				throw new ServiceException("recipee get error EDP", e);
			}

		}
		return edpId;
	}

	public String updateEstimatedDailyProdItem(
			EstimateDailyProdItem estimateDailyProdItem)
			throws ServiceException {
		String status = "";
		try {
			status = recipeManagementDAO
					.updateEstimatedDailyProdItem(estimateDailyProdItem);

		} catch (DAOException e) {
			throw new ServiceException("recipee get error EDP", e);

		}
		return status;
	}

	public String addEstimatedDailyProdItem(
			EstimateDailyProdItem estimateDailyProdItem)
			throws ServiceException {
		String status = "";
		try {
			status = recipeManagementDAO
					.addEstimatedDailyProdItem(estimateDailyProdItem);

		} catch (DAOException e) {
			throw new ServiceException("recipee get error EDP", e);

		}
		return status;
	}

	public String addRecipeIngredient(IngredientList ingredients)
			throws ServiceException {
		String status = "";
		try {
			status = recipeManagementDAO.addRecipeIngredient(ingredients);

		} catch (DAOException e) {
			throw new ServiceException("recipee get error addRecipeIngredient", e);

		}
		return status;
	}

	public int createFgSaleOut(FgSaleOut fgSaleOut) throws ServiceException {
		int recipeId = 0;
		
		try {
			recipeId = recipeManagementDAO.createFgSaleOut(fgSaleOut);

		} catch (DAOException e) {
			throw new ServiceException("recipee get error EDP", e);

		}
		return recipeId;
	}

	public int createFgClose(FgClose fgClose) throws ServiceException {
		int recipeId = 0;

		try {
			recipeId = recipeManagementDAO.createFgClose(fgClose);

		} catch (DAOException e) {
			throw new ServiceException("recipee get error EDP", e);

		}
		return recipeId;
	}

	public int createRawClose(RawClose rawClose) throws ServiceException {
		int rawClseId = 0;

		try {
			rawClseId = recipeManagementDAO.createRawClose(rawClose);

		} catch (DAOException e) {
			throw new ServiceException("recipee get error EDP", e);

		}
		return rawClseId;
	}

	public String updateRecipeIngredient(Ingredient ingredient)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = recipeManagementDAO
					.updateRecipeIngredient(ingredient);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateRecipeIngredient", e);

		}
		return messaString;
	}

	public String deleteRecipeIngredient(Integer storeid, Integer id)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = recipeManagementDAO.deleteRecipeIngredient(storeid,
					id);

		} catch (DAOException e) {
			throw new ServiceException("error creating deleteRecipeIngredient", e);

		}
		return messaString;
	}

	public String approveEstimateDailyProd(Integer storeid, Integer id, String by)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = recipeManagementDAO.approveEstimateDailyProd(storeid,
					id, by);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateInvntryType", e);

		}
		return messaString;
	}

	public String approveRawClose(Integer storeid, Integer id, String approvedBy,
			String updatedBy, String updatedDate) throws ServiceException {

		String messaString = "";
		try {
			messaString = recipeManagementDAO.approveRawClose(storeid, id,
					approvedBy, updatedBy, updatedDate);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateInvntryType", e);

		}
		return messaString;
	}

	public String approveFgClose(Integer storeid, Integer id, String approvedBy,
			String updatedBy, String updatedDate) throws ServiceException {

		String messaString = "";
		try {
			messaString = recipeManagementDAO.approveFgClose(storeid, id,
					approvedBy, updatedBy, updatedDate);

		} catch (DAOException e) {
			throw new ServiceException("error approving fg close", e);

		}
		return messaString;
	}

	public String deleteEstimateDailyProdItem(Integer storeid, Integer id,
			String edpId) throws ServiceException {

		String messaString = "";
		try {
			messaString = recipeManagementDAO.deleteEstimateDailyProdItem(
					storeid, id, edpId);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateInvntryType", e);

		}
		return messaString;
	}

	public String deleteEstimateDailyProd(String edpId, Integer storeid)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = recipeManagementDAO.deleteEstimateDailyProd(edpId,
					storeid);

		} catch (DAOException e) {
			throw new ServiceException("error creating updateInvntryType", e);

		}
		return messaString;
	}

	public RecipeManagementDAO getRecipeManagementDAO() {
		return recipeManagementDAO;
	}

	public void setRecipeManagementDAO(RecipeManagementDAO recipeManagementDAO) {
		this.recipeManagementDAO = recipeManagementDAO;
	}

}

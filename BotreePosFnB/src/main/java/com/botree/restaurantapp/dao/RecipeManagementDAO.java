package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.Restaurant.rahulbean.FgClose;
import com.botree.Restaurant.rahulbean.FgCloseChild;
import com.botree.Restaurant.rahulbean.FgSaleOut;
import com.botree.Restaurant.rahulbean.FgSaleOutItemsChild;
import com.botree.Restaurant.rahulbean.RawClose;
import com.botree.Restaurant.rahulbean.RawCloseChild;
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

public interface RecipeManagementDAO {

	public List<CookingUnit> getAllCookingUnits(Integer storeId)
			throws DAOException;

	public List<Ingredient> getAllIngredients(Integer storeId, Integer itemId)
			throws DAOException;

	public List<EstimateDailyProd> getEstimateDailyProdByDate(Integer storeId,
			String date) throws DAOException;

	public List<MetricUnit> getAllMetricUnits(String type) throws DAOException;
	
	public List<MetricUnit> getAllMetricUnitsbyType(String type) throws DAOException;
	
	public List<InventoryVendor> getVendorByEdp(String edpId,Integer storeId) throws DAOException;

	public List<UnitConversion> getAllUnitConversions(Integer storeId)
			throws DAOException;

	public List<EstimateDailyProdItem> getEstimateDailyProdItemById(
			Integer storeId, Integer id) throws DAOException;

	public int addEstimateDailyProd(EstimateDailyProd estimateDailyProds)
			throws DAOException;

	public String addRecipeIngredient(IngredientList ingredients)
			throws DAOException;

	public List<FmcgUnit> getAllFmcgUnits(Integer storeId) throws DAOException;

	public String updateRecipeIngredient(Ingredient ingredient)
			throws DAOException;

	public String deleteRecipeIngredient(Integer storeid, Integer id)
			throws DAOException;

	public String approveEstimateDailyProd(Integer storeid, Integer id, String by)
			throws DAOException;

	public String deleteEstimateDailyProdItem(Integer storeid, Integer id,
			String edpId) throws DAOException;

	public List<Ingredient> getIngredientsForEdp(Integer storeId, Integer edpId)
			throws DAOException;

	public List<EstimateDailyProd> getRequisitionByDate(Integer storeId,
			String date) throws DAOException;

	public List<EstimateDailyProd> getFgStockInByDate(Integer storeId,
			String date) throws DAOException;

	public List<FgStockInItemsChild> getFgStockInItemsById(Integer storeId,
			Integer fgStockInId) throws DAOException;

	public List<InventoryPurchaseOrderItem> getRequisitionByIds(Integer storeId,
			Integer poId) throws DAOException;
	
	//added on 07.05.2018
	public List<InventoryPurchaseOrder> getRequisitionByIdsNew(Integer storeId,
			Integer poId) throws DAOException;

	public List<FgSaleOutItemsChild> getFgSaleOutByDate(Integer storeId,
			String date) throws DAOException;

	public List<FgCloseChild> getFgCloseItemsByDate(Integer storeId, String date)
			throws DAOException;

	public List<RawCloseChild> getRawCloseItemsByDate(Integer storeId,
			String date) throws DAOException;

	public int createFgSaleOut(FgSaleOut fgSaleOut) throws DAOException;

	public int createFgClose(FgClose fgClose) throws DAOException;

	public int createRawClose(RawClose rawClose) throws DAOException;

	public List<EstimateDailyProd> getRawStockOutByDate(Integer storeId,
			String date) throws DAOException;

	public List<InventoryStockOutItem> getRawStockOutById(Integer storeId,
			Integer rawStockOutId) throws DAOException;

	public String approveRawClose(Integer storeid, Integer id, String approvedBy,
			String updatedBy, String updatedDate) throws DAOException;
	
	public String updateEstimatedDailyProdItem(EstimateDailyProdItem estimateDailyProdItem)
			throws DAOException;
	
	public String approveFgClose(Integer storeid, Integer id, String approvedBy,
			String updatedBy, String updatedDate) throws DAOException;
	
	public double geMetrictUnitConVersionFactor(Integer storeId,
			Integer fromUnit, Integer toUnit)
			throws DAOException;
	
	public String addEstimatedDailyProdItem(EstimateDailyProdItem estimateDailyProdItem)
			throws DAOException;
	
	public MetricUnit getMetricUnit(Integer id) throws DAOException;
	
	public String deleteEstimateDailyProd(String edpId,Integer storeid) throws DAOException;
}

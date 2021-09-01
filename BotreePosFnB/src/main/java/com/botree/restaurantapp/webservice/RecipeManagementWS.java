package com.botree.restaurantapp.webservice;

import com.botree.Restaurant.rahulbean.FgClose;
import com.botree.Restaurant.rahulbean.FgSaleOut;
import com.botree.Restaurant.rahulbean.RawClose;
import com.botree.restaurantapp.model.rm.EstimateDailyProd;
import com.botree.restaurantapp.model.rm.EstimateDailyProdItem;
import com.botree.restaurantapp.model.rm.Ingredient;
import com.botree.restaurantapp.model.rm.IngredientList;

public interface RecipeManagementWS {

	public String getAllCookingUnits(Integer storeid);

	public String getAllIngredients(Integer storeid, Integer itemid);

	public String getAllMetricUnits(String unitType);
	
	public String getAllMetricUnitsbyType(String unitType);

	public String getAllUnitConversions(Integer storeid);

	public String getEstimateDailyProdByDate(Integer storeid, String date);

	public String getEstimateDailyProdItemById(Integer storeId, Integer id);

	public String addEstimateDailyProd(EstimateDailyProd estimateDailyProds);

	public String addRecipeIngredient(IngredientList ingredients);

	public String getAllFmcgUnits(Integer storeid);

	public String updateRecipeIngredient(Ingredient ingredient);

	public String deleteRecipeIngredient(Integer storeid, Integer id);

	public String approveEstimateDailyProd(Integer storeid, Integer id, String by);

	public String deleteEstimateDailyProdItem(Integer storeid, Integer id,
			String edpId);

	public String getIngredientsForEdp(Integer storeid, Integer edpid);

	public String getRequisitionByDate(Integer storeid, String date);

	public String getFgStockInByDate(Integer storeid, String date);

	public String getFgStockInByIds(Integer storeId, Integer edpId,
			Integer fgStockInId);

	public String getRequisitionByIds(Integer storeId, Integer edpId, Integer poId);
	
	//added on 07.05.2018
	public String getRequisitionByIdsNew(Integer storeId, Integer edpId, Integer poId);

	public String getFgSaleOutByDate(Integer storeid, String date);

	public String getFgCloseItemsByDate(Integer storeid, String date);

	public String getRawCloseItemsByDate(Integer storeid, String date);

	public String createFgSaleOut(FgSaleOut fgSaleOut);

	public String createRawClose(RawClose rawClose);

	public String createFgClose(FgClose fgClose);

	public String getRawStockOutByDate(Integer storeid, String date);

	public String getRawStockOutById(Integer storeId, Integer edpId, Integer poId);

	public String approveRawClose(Integer storeId, Integer id, String approvedBy,
			String updatedBy, String updatedDate);

	public String updateEstimatedDailyProdItem(
			EstimateDailyProdItem estimateDailyProdItem);

	public String approveFgClose(Integer storeId, Integer id, String approvedBy,
			String updatedBy, String updatedDate);

	public String geMetrictUnitConVersionFactor(Integer storeid,
			Integer fromUnit, Integer toUnit);

	public String addEstimatedDailyProdItem(
			EstimateDailyProdItem updateEstimatedDailyProdItem);

	public String getMetricUnit(Integer id);

	public String deleteEstimateDailyProd(String edpId, Integer storeid);

	public String getVendorByEdp(String edpId, Integer storeId);
}

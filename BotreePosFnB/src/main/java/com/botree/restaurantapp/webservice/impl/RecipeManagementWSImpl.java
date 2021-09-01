package com.botree.restaurantapp.webservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.Restaurant.rahulbean.FgClose;
import com.botree.Restaurant.rahulbean.FgCloseChild;
import com.botree.Restaurant.rahulbean.FgSaleOut;
import com.botree.Restaurant.rahulbean.FgSaleOutItemsChild;
import com.botree.Restaurant.rahulbean.RawClose;
import com.botree.Restaurant.rahulbean.RawCloseChild;
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
import com.botree.restaurantapp.service.RecipeManagementService;
import com.botree.restaurantapp.service.exception.ServiceException;
import com.botree.restaurantapp.webservice.RecipeManagementWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/rm")
public class RecipeManagementWSImpl implements RecipeManagementWS {
	
	//private final static Logger logger = LogManager.getLogger(RecipeManagementWSImpl.class);

	@Autowired
	private RecipeManagementService recipeManagementService;

	@Override
	@RequestMapping(value = "/getAllCookingUnits", method = RequestMethod.GET, produces = "text/plain")
	public String getAllCookingUnits(
			@RequestParam(value = "storeid") Integer storeid) {
		List<CookingUnit> cookingUnits = null;
		try {
			cookingUnits = recipeManagementService.getAllCookingUnits(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<CookingUnit>>() {
		}.getType();
		String json = gson.toJson(cookingUnits, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllMetricUnits", method = RequestMethod.GET, produces = "text/plain")
	public String getAllMetricUnits(@RequestParam(value = "type") String unitType) {
		List<MetricUnit> metricUnits = null;
		try {
			metricUnits = recipeManagementService.getAllMetricUnits(unitType);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MetricUnit>>() {
		}.getType();
		String json = gson.toJson(metricUnits, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getAllMetricUnitsbyType", method = RequestMethod.GET, produces = "text/plain")
	public String getAllMetricUnitsbyType(@RequestParam(value = "type") String unitType) {
		List<MetricUnit> metricUnits = null;
		try {
			metricUnits = recipeManagementService.getAllMetricUnitsbyType(unitType);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<MetricUnit>>() {
		}.getType();
		String json = gson.toJson(metricUnits, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/geMetrictUnitConVersionFactor", method = RequestMethod.GET, produces = "text/plain")
	public String geMetrictUnitConVersionFactor(
			@RequestParam(value = "storeId") Integer storeid,
			@RequestParam(value = "fromUnitId") Integer fromUnit,
			@RequestParam(value = "toUnitId") Integer toUnit) {
		double factor = 0.0;
		String convfactor = "";
		try {
			factor = recipeManagementService.geMetrictUnitConVersionFactor(
					storeid, fromUnit, toUnit);
			convfactor = factor + "";
		} catch (Exception x) {
			convfactor="1.0";
			x.printStackTrace();
		}

		return convfactor;
	}

	@Override
	@RequestMapping(value = "/getAllFmcgUnits", method = RequestMethod.GET, produces = "text/plain")
	public String getAllFmcgUnits(@RequestParam(value = "storeid") Integer storeid) {
		List<FmcgUnit> fmcgUnits = null;
		try {
			fmcgUnits = recipeManagementService.getAllFmcgUnits(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<FmcgUnit>>() {
		}.getType();
		String json = gson.toJson(fmcgUnits, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllIngredients", method = RequestMethod.GET, produces = "text/plain")
	public String getAllIngredients(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "itemid") Integer itemid) {
		List<Ingredient> ingredients = null;
		try {
			ingredients = recipeManagementService.getAllIngredients(storeid,
					itemid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Ingredient>>() {
		}.getType();
		String json = gson.toJson(ingredients, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getIngredientsForEdp", method = RequestMethod.GET, produces = "text/plain")
	public String getIngredientsForEdp(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "edpid") Integer edpid) {
		List<Ingredient> ingredients = null;
		try {
			ingredients = recipeManagementService.getIngredientsForEdp(storeid,
					edpid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Ingredient>>() {
		}.getType();
		String json = gson.toJson(ingredients, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getEstimateDailyProdByDate", method = RequestMethod.GET, produces ="text/plain")
	public String getEstimateDailyProdByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {
			estimateDailyProds = recipeManagementService
					.getEstimateDailyProdByDate(storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EstimateDailyProd>>() {
		}.getType();
		String json = gson.toJson(estimateDailyProds, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getRequisitionByDate", method = RequestMethod.GET, produces = "text/plain")
	public String getRequisitionByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {
			estimateDailyProds = recipeManagementService.getRequisitionByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EstimateDailyProd>>() {
		}.getType();
		String json = gson.toJson(estimateDailyProds, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getFgStockInByDate", method = RequestMethod.GET, produces = "text/plain")
	public String getFgStockInByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {
			estimateDailyProds = recipeManagementService.getFgStockInByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EstimateDailyProd>>() {
		}.getType();
		String json = gson.toJson(estimateDailyProds, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getRawStockOutByDate", method = RequestMethod.GET, produces = "text/plain")
	public String getRawStockOutByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {
		List<EstimateDailyProd> estimateDailyProds = null;
		try {
			estimateDailyProds = recipeManagementService.getRawStockOutByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EstimateDailyProd>>() {
		}.getType();
		String json = gson.toJson(estimateDailyProds, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/addEstimatedDailyProd", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addEstimateDailyProd(@RequestBody EstimateDailyProd estimateDailyProds) {
		int edpId = 0;
		try {
			edpId = recipeManagementService
					.addEstimateDailyProd(estimateDailyProds);
		} catch (Exception x) {
			x.printStackTrace();
		}
		if (edpId == 999999999) {
			return "Daily Type For Today's Date Already Exist...";
		}
		else if (edpId == 999999998) {
			return "Left Over Food Type Estimate For Today's Date Already Exist...";
		}else {
			return edpId + "";
		}
	}

	@Override
	@RequestMapping(value = "/updateEstimatedDailyProdItem", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateEstimatedDailyProdItem(@RequestBody EstimateDailyProdItem updateEstimatedDailyProdItem) {
		String status = "";
		try {
			status = recipeManagementService
					.updateEstimatedDailyProdItem(updateEstimatedDailyProdItem);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/addEstimatedDailyProdItem", method = RequestMethod.POST, consumes = "application/json", produces ="text/plain")
	public String addEstimatedDailyProdItem(@RequestBody EstimateDailyProdItem updateEstimatedDailyProdItem) {
		String status = "";
		try {
			status = recipeManagementService
					.addEstimatedDailyProdItem(updateEstimatedDailyProdItem);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/getFgSaleOutByDate", method = RequestMethod.GET, produces ="text/plain")
	public String getFgSaleOutByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {

		List<FgSaleOutItemsChild> fgSaleOutItemsChild = null;
		try {
			fgSaleOutItemsChild = recipeManagementService.getFgSaleOutByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<FgSaleOutItemsChild>>() {
		}.getType();
		String json = gson.toJson(fgSaleOutItemsChild, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getFgCloseItemsByDate", method = RequestMethod.GET, produces = "text/plain")
	public String getFgCloseItemsByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {

		List<FgCloseChild> fgCloseChild = null;
		try {
			fgCloseChild = recipeManagementService.getFgCloseItemsByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<FgCloseChild>>() {
		}.getType();
		String json = gson.toJson(fgCloseChild, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getRawCloseItemsByDate", method = RequestMethod.GET, produces ="text/plain")
	public String getRawCloseItemsByDate(
			@RequestParam(value = "storeid") Integer storeid,
			@RequestParam(value = "date") String date) {

		List<RawCloseChild> rawCloseChild = null;
		try {
			rawCloseChild = recipeManagementService.getRawCloseItemsByDate(
					storeid, date);
		} catch (Exception x) {
			x.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RawCloseChild>>() {
		}.getType();
		String json = gson.toJson(rawCloseChild, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/addRecipeIngredient", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addRecipeIngredient(@RequestBody IngredientList ingredients) {
		
		String status = "";
		try {
			status = recipeManagementService.addRecipeIngredient(ingredients);
		} catch (Exception x) {
			status = "failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/createFgSaleOut", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String createFgSaleOut(@RequestBody FgSaleOut fgSaleOut) {
		int recipeId = 0;
		
		try {
			recipeId = recipeManagementService.createFgSaleOut(fgSaleOut);
		} catch (Exception x) {
			
			x.printStackTrace();
		}

		return recipeId + "";
	}

	@Override
	@RequestMapping(value = "/createFgClose", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String createFgClose(@RequestBody FgClose fgClose) {
		int fgCloseId = 0;
		
		try {
			fgCloseId = recipeManagementService.createFgClose(fgClose);
		} catch (Exception x) {
			
			x.printStackTrace();
		}

		return fgCloseId + "";
	}

	@Override
	@RequestMapping(value = "/createRawClose", method = RequestMethod.POST, consumes = "application/json", produces ="text/plain")
	public String createRawClose(@RequestBody RawClose rawClose) {
		int rawClseId = 0;
		
		try {
			rawClseId = recipeManagementService.createRawClose(rawClose);
		} catch (Exception x) {
			
			x.printStackTrace();
		}

		return rawClseId + "";
	}

	@Override
	@RequestMapping(value = "/getEstimateDailyProdItemById", method = RequestMethod.GET, produces = "text/plain")
	public String getEstimateDailyProdItemById(
			@RequestParam(value = "storeid") Integer storeId,
			@RequestParam(value = "id") Integer id) {
		List<EstimateDailyProdItem> estimateDailyProdItems = null;
		try {
			estimateDailyProdItems = recipeManagementService
					.getEstimateDailyProdItemById(storeId, id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EstimateDailyProdItem>>() {
		}.getType();
		String json = gson.toJson(estimateDailyProdItems, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getFgStockInByIds", method = RequestMethod.GET, produces = "text/plain")
	public String getFgStockInByIds(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "edpId") Integer edpId,
			@RequestParam(value = "fgStockInId") Integer fgStockInId) {
		List<EstimateDailyProdItem> estimateDailyProdItems = null;
		List<FgStockInItemsChild> fgStockInItemsChilds = null;
		String json = null;
		int fgStckInId = (fgStockInId);
		if (fgStckInId != 0) {
			try {
				fgStockInItemsChilds = recipeManagementService.getFgStockInItemsById(storeId, fgStockInId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<FgStockInItemsChild>>() {
			}.getType();
			json = gson.toJson(fgStockInItemsChilds, type);
			//System.out.println(json);
		} else { // from edp

			try {
				estimateDailyProdItems = recipeManagementService
						.getEstimateDailyProdItemById(storeId, edpId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<EstimateDailyProdItem>>() {
			}.getType();
			json = gson.toJson(estimateDailyProdItems, type);
			//System.out.println(json);
		}
		return json;
	}

	@Override
	@RequestMapping(value = "/getRequisitionByIds", method = RequestMethod.GET, produces = "text/plain")
	public String getRequisitionByIds(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "edpId") Integer edpId,
			@RequestParam(value = "poId") Integer poId) {

		List<InventoryPurchaseOrderItem> inventoryPoItems = null;
		List<Ingredient> ingList = null;
		String json = null;
		int poId1 = (poId);
		
		if (poId1 != 0) { // for po id
			try {
				inventoryPoItems = recipeManagementService.getRequisitionByIds(storeId, poId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<InventoryPurchaseOrderItem>>() {
			}.getType();
			json = gson.toJson(inventoryPoItems, type);
			//System.out.println(json);
		} else { // from edp

			try {
				ingList = recipeManagementService.getIngredientsForEdp(storeId, edpId);
				/*
				 * // current stock calculation if (ingList != null &&
				 * ingList.size() > 0) { InventoryDAO inventoryDAO = new
				 * InventoryDAOImpl(); try { rs =
				 * inventoryDAO.getCurrentStockByStore(storeId); while
				 * (rs.next()) { int i = rs.getInt("item_id"); double q =
				 * rs.getDouble("quantity"); for (Ingredient ing : ingList) {
				 * InventoryItems item = ing.getInventoryItem(); if
				 * (item.getId() == i) { ing.setCurrentQuantity(q); }
				 * 
				 * }
				 * 
				 * } } catch (Exception e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } finally { try { if (rs != null) {
				 * Statement st = rs.getStatement(); Connection con =
				 * st.getConnection(); con.close(); st.close(); rs.close();
				 * 
				 * 
				 * 
				 * }
				 * 
				 * } catch (SQLException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } } }
				 */
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<Ingredient>>() {
			}.getType();
			json = gson.toJson(ingList, type);
			//System.out.println(json);
		}
		return json;
	}
	
	//added on 07.05.2018
	@Override
	@RequestMapping(value = "/getRequisitionByIdsNew", method = RequestMethod.GET, produces = "text/plain")
	public String getRequisitionByIdsNew(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "edpId") Integer edpId,
			@RequestParam(value = "poId") Integer poId) {

		List<InventoryPurchaseOrder> inventoryPo = null;
		List<Ingredient> ingList = null;
		String json = null;
		
		int poId1 = (poId);
		if (poId1 != 0) { // for po id
			try {
				inventoryPo = recipeManagementService.getRequisitionByIdsNew(storeId, poId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<InventoryPurchaseOrderItem>>() {
			}.getType();
			json = gson.toJson(inventoryPo, type);
			//System.out.println(json);
		} else { // from edp

			try {
				ingList = recipeManagementService.getIngredientsForEdp(storeId, edpId);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<Ingredient>>() {
			}.getType();
			json = gson.toJson(ingList, type);
			//System.out.println(json);
		}
		return json;
	}

	@Override
	@RequestMapping(value = "/getRawStockOutById", method = RequestMethod.GET, produces = "text/plain")
	public String getRawStockOutById(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "edpId") Integer edpId,
			@RequestParam(value = "rawStockOutId") Integer rawStockOutId) {
		List<InventoryStockOutItem> inventoryStckOutItems = null;
		List<Ingredient> ingList = null;
		String json = null;

		int rawStockOutId1 = (rawStockOutId);
		if (rawStockOutId1 != 0) { // for stock out id
			try {
				inventoryStckOutItems = recipeManagementService.getRawStockOutById(storeId, rawStockOutId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<InventoryStockOutItem>>() {
			}.getType();
			json = gson.toJson(inventoryStckOutItems, type);
			//System.out.println(json);
		} else { // from edp

			try {
				ingList = recipeManagementService.getIngredientsForEdp(storeId, edpId);

			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			java.lang.reflect.Type type = new TypeToken<List<Ingredient>>() {
			}.getType();
			json = gson.toJson(ingList, type);
			//System.out.println(json);
		}
		return json;
	}

	

	@Override
	@RequestMapping(value = "/getVendorByEdp", method = RequestMethod.GET, produces = "text/plain")
	public String getVendorByEdp(@RequestParam(value = "edpId") String edpId,
			@RequestParam(value = "storeId") Integer storeId) {
		List<InventoryVendor> idList = null;
		try {
			idList = recipeManagementService.getVendorByEdp(edpId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<InventoryVendor>>() {
		}.getType();
		String json = gson.toJson(idList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getMetricUnit", method = RequestMethod.GET, produces = "text/plain")
	public String getMetricUnit(@RequestParam(value = "id") Integer id) {
		MetricUnit metricUnits = null;
		try {
			metricUnits = recipeManagementService.getMetricUnit(id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(metricUnits, MetricUnit.class);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllUnitConversions", method = RequestMethod.GET, produces = "text/plain")
	public String getAllUnitConversions(
			@RequestParam(value = "storeid") Integer storeid) {
		List<UnitConversion> unitConversions = null;
		try {
			unitConversions = recipeManagementService
					.getAllUnitConversions(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<UnitConversion>>() {
		}.getType();
		String json = gson.toJson(unitConversions, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/updateRecipeIngredient", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateRecipeIngredient(@RequestBody Ingredient ingredient) {
		String messaString = "";
		try {
			messaString = recipeManagementService
					.updateRecipeIngredient(ingredient);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/deleteRecipeIngredient", method = RequestMethod.GET, produces = "text/plain")
	public String deleteRecipeIngredient(
			@RequestParam(value = "storeId") Integer storeid,
			@RequestParam(value = "id") Integer id) {
		String messaString = "";
		try {
			messaString = recipeManagementService.deleteRecipeIngredient(
					storeid, id);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/approveEstimateDailyProd", method = RequestMethod.GET, produces = "text/plain")
	public String approveEstimateDailyProd(
			@RequestParam(value = "storeId") Integer storeid,
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "by") String by) {
		String messaString = "";
		try {
			messaString = recipeManagementService.approveEstimateDailyProd(
					storeid, id, by);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/approveRawClose", method = RequestMethod.GET, produces = "text/plain")
	public String approveRawClose(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "approvedBy") String approvedBy,
			@RequestParam(value = "updatedBy") String updatedBy,
			@RequestParam(value = "updatedDate") String updatedDate) {
		String messaString = "";
		try {
			messaString = recipeManagementService.approveRawClose(storeId, id,
					approvedBy, updatedBy, updatedDate);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "/approveFgClose", method = RequestMethod.GET, produces = "text/plain")
	public String approveFgClose(@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "approvedBy") String approvedBy,
			@RequestParam(value = "updatedBy") String updatedBy,
			@RequestParam(value = "updatedDate") String updatedDate) {
		String messaString = "";
		try {
			messaString = recipeManagementService.approveFgClose(storeId, id,
					approvedBy, updatedBy, updatedDate);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "deleteEstimateDailyProdItem", method = RequestMethod.GET, produces = "text/plain")
	public String deleteEstimateDailyProdItem(
			@RequestParam(value = "storeId") Integer storeid,
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "edpId") String edpId) {
		String messaString = "";
		try {
			messaString = recipeManagementService.deleteEstimateDailyProdItem(
					storeid, id, edpId);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	@Override
	@RequestMapping(value = "deleteEstimateDailyProd", method = RequestMethod.GET, produces = "text/plain")
	public String deleteEstimateDailyProd(
			@RequestParam(value = "edpId") String edpId,
			@RequestParam(value = "storeId") Integer storeid) {
		String messaString = "";
		try {
			messaString = recipeManagementService.deleteEstimateDailyProd(
					edpId, storeid);
		} catch (Exception x) {
			messaString = "failure";
			x.printStackTrace();
		}

		return messaString;
	}

	public RecipeManagementService getRecipeManagementService() {
		return recipeManagementService;
	}

	public void setRecipeManagementService(
			RecipeManagementService recipeManagementService) {
		this.recipeManagementService = recipeManagementService;
	}

}

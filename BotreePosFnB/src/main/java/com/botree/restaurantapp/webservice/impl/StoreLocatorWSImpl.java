package com.botree.restaurantapp.webservice.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.DashPaymentSummary;
import com.botree.restaurantapp.model.DashSalesSummary;
import com.botree.restaurantapp.model.DashSalesSummaryOrderType;
import com.botree.restaurantapp.model.DashSalesSummaryPaymentType;
import com.botree.restaurantapp.model.DashTopCustomer;
import com.botree.restaurantapp.model.DashTopItem;
import com.botree.restaurantapp.model.MobileFontSetting;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreDayBookRegisterBean;
import com.botree.restaurantapp.model.StoreFeaturesChild;
import com.botree.restaurantapp.model.StoreLocator;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.StoreSMSConfiguration;
import com.botree.restaurantapp.service.StoreAddressService;
import com.botree.restaurantapp.webservice.StoreLocatorWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/storeLocator")
public class StoreLocatorWSImpl implements StoreLocatorWS {

  @Autowired
	private StoreAddressService storeAddressService;

	@Override
	@RequestMapping(value = "/get",
	method = RequestMethod.GET,
	consumes = "application/json",
	produces = "application/json")
	public List<StoreMaster> getStores() {
		List<StoreMaster> stores = null;
		try {
			stores = storeAddressService.getAllStore();
		} catch (Exception x) {
			x.printStackTrace();
		}
		return stores;
	}

	@Override
	@RequestMapping(value = "/id", method = RequestMethod.GET, consumes = "application/json",	produces = "application/json")
	public List<StoreMaster> getByStoreId(@RequestParam(value = "id") Integer id) {

		List<StoreMaster> stores = null;
		try {
			System.out.println("enter getByStoreId webservice");
			stores = storeAddressService.getStoreById(id);
			System.out.println("exit getByStoreId webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		// return json;
		return stores;
	}

	@Override
	@RequestMapping(value = "/insertOpenTime",	method = RequestMethod.GET,	consumes = "application/json",	produces = "application/json")
	public StoreDayBookRegisterBean insertOpenTime(@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "userId") String userId,@RequestParam(value = "opentimeId") String opentimeId) {

		StoreDayBookRegisterBean storeDayBookRegisterBean=new StoreDayBookRegisterBean();
		try {
			
			storeDayBookRegisterBean = storeAddressService.insertOpenTime(storeId,userId,opentimeId);
			
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		return storeDayBookRegisterBean;
	}
	
	@Override
	@RequestMapping(value = "/checkRestaurantOpenStatus",	method = RequestMethod.GET,	consumes = "application/json", produces = "application/json")
	public StoreDayBookRegisterBean checkRestaurantOpenStatus(@RequestParam(value = "storeId") Integer storeId) {

		StoreDayBookRegisterBean storeDayBookRegisterBean=new StoreDayBookRegisterBean();
		try {
			
			storeDayBookRegisterBean = storeAddressService.checkRestaurantOpenStatus(storeId);
			
		} catch (Exception x) {
			x.printStackTrace();
		}

		return storeDayBookRegisterBean;
	}
	
	@Override
	@RequestMapping(value = "/insertCloseTime",	method = RequestMethod.GET,	consumes = "application/json",	produces = "application/json")
	public String insertCloseTime(@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "userId") String userId,@RequestParam(value = "opentimeId") String opentimeId) {

		String status="";
		try {
			
			status = storeAddressService.insertCloseTime(storeId,userId,opentimeId);
			
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		
		return status;
	}

	@Override
	@RequestMapping(value = "/storeid",	method = RequestMethod.GET,	 produces = "text/plain")  /*consumes = "application/json",*/
	public String getStoreByStoreId(@RequestParam(value = "id") String id) {

		StoreMaster store = null;
		try {
			int storeId = Integer.parseInt(id);
			System.out.println("enter getStoreByStoreId webservice");
			store = storeAddressService.getStoreByStoreId(storeId);
			System.out.println("exit getStoreByStoreId webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		/*
		 * Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
		 * .create(); java.lang.reflect.Type type = new
		 * TypeToken<List<StoreMaster>>() { }.getType(); String json =
		 * gson.toJson(store, type);
		 */
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(store, StoreMaster.class);
		System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getRestaurant",	method = RequestMethod.GET,	consumes = "application/json", produces = "application/json")
	public List<RestaurantMaster> getAllRestaurant() {

		List<RestaurantMaster> restaurantList = null;
		try {
			System.out.println("enter getAllRestaurant webservice");
			restaurantList = storeAddressService.getAllRestaurant();
			System.out.println("exit getAllRestaurant webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}

		return restaurantList;
	}

	@Override
	@RequestMapping(value = "/getRestaurantWithLatLong", method = RequestMethod.POST,	consumes = "application/json", produces = "application/json")
	public List<RestaurantMaster> getAllRestaurantWithLatLong(@RequestBody Customer customerParam) {

		List<RestaurantMaster> restaurantList = null;
		try {
			System.out.println("enter getAllRestaurant webservice");
			restaurantList = storeAddressService
					.getAllRestaurantWithLatLong(customerParam);
			System.out.println("exit getAllRestaurant webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		return restaurantList;
	}

	@Override
	@RequestMapping(value = "/getAllStoresWithLatLong",	method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<StoreMaster> getAllStoresWithLatLong(@RequestBody Customer customerParam) {

		List<StoreMaster> restaurantList = null;
		try {
			System.out.println("enter getAllStoresWithLatLong webservice");
			restaurantList = storeAddressService
					.getAllStoresWithLatLong(customerParam);
			System.out.println("exit getAllStoresWithLatLong webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		return restaurantList;
	}

	@Override
	@RequestMapping(value = "/getAllStoresByCity", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<StoreMaster> getAllStoresByCity(@RequestBody Customer customerParam) {

		List<StoreMaster> storeList = null;
		try {
			System.out.println("enter getAllStoresByCity webservice");
			storeList = storeAddressService.getAllStoresByCity(customerParam);
			System.out.println("exit getAllStoresByCity webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		return storeList;
	}

	@Override
	@RequestMapping(value = "/getAllCitiesForStores", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllCitiesForStores() {

		List<String> cityList = null;
		try {
			System.out.println("enter getAllCitiesForStores webservice");
			cityList = storeAddressService.getAllCitiesForStores();
			System.out.println("exit getAllCitiesForStores webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<String>>() {
		}.getType();
		String json = gson.toJson(cityList, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/storeFeatures", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<StoreFeaturesChild> getStoreMenuOptionsById(
			@RequestParam(value = "id") Integer storeId) {

		List<StoreFeaturesChild> storeFeaturesChilds = null;
		try {
			System.out.println("enter getStoreMenuOptionsById webservice");
			storeFeaturesChilds = storeAddressService
					.getStoreMenuOptionsById(storeId);
			System.out.println("exit getStoreMenuOptionsById webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}

		return storeFeaturesChilds;
	}

	@Override
	@RequestMapping(value = "/getMobileFontByStore",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getMobileFontByStore(@RequestParam(value = "id") Integer id) {
		MobileFontSetting fontSetting = null;
		try {
			System.out.println("enter getMobileFontByStore webservice");
			fontSetting = storeAddressService.getMobileFontByStore(id);
			System.out.println("exit getMobileFontByStore webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(fontSetting, MobileFontSetting.class);
		//System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getAllStores",
	method = RequestMethod.GET,
	produces = "application/json")
	public List<StoreMaster> getAllStores() {

		List<StoreMaster> storeLst = null;
		try {
			// int storeId = Integer.parseInt(id);
			System.out.println("enter getAllStores webservice");
			storeLst = storeAddressService.getAllStores();
			System.out.println("exit getAllStores webservice");
		} catch (Exception x) {
			x.printStackTrace();
		}

		/*
		 * Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
		 * .create(); java.lang.reflect.Type type = new
		 * TypeToken<List<StoreMaster>>() { }.getType(); String json =
		 * gson.toJson(storeLst, type); System.out.println(json);
		 */

		return storeLst;
	}

	@Override
	@RequestMapping(value = "/getAllStoresByRestaurantId",
	method = RequestMethod.GET,
	produces = "application/json")
	public List<StoreMaster> getAllStoresByRestaurantId(
			@RequestParam(value = "id") String id) {

		List<StoreMaster> storeLst = null;
		try {

			storeLst = storeAddressService.getAllStoresByRestaurantId(id);

		} catch (Exception x) {
			x.printStackTrace();
		}

		return storeLst;
	}
	
	@Override
	@RequestMapping(value = "/getStoreSMSConfiguration", method = RequestMethod.GET,	consumes = "application/json", produces = "text/plain")
	public String getStoreSMSConfiguration(@RequestParam(value = "storeId") Integer storeId) {

		StoreSMSConfiguration storeSMS = null;
		try {
			storeSMS = storeAddressService.getStoreSMSConfiguration(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(storeSMS, StoreSMSConfiguration.class);

		return json;
	}
	
	@RequestMapping(value = "/getDashSaleSummary", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getDashSaleSummary(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		DashSalesSummary salesSummary = null;

		try {
			salesSummary = storeAddressService.getDashSaleSummary(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<DashSalesSummary>() {
		}.getType();
		String json = gson.toJson(salesSummary, type);
		return json;
	}
	
	@RequestMapping(value = "/getDashPaymentSummary", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getDashPaymentSummary(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		DashPaymentSummary paymentSummary = null;

		try {
			paymentSummary = storeAddressService.getDashPaymentSummary(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<DashPaymentSummary>() {
		}.getType();
		String json = gson.toJson(paymentSummary, type);
		return json;
	}
	
	@RequestMapping(value = "/getDashSaleSummaryOrderType", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getDashSaleSummaryOrderType(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		List<DashSalesSummaryOrderType> salesSummaryOrderTypeList = null;

		try {
			salesSummaryOrderTypeList = storeAddressService.getDashSaleSummaryOrderType(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DashSalesSummaryOrderType>>() {
		}.getType();
		String json = gson.toJson(salesSummaryOrderTypeList, type);
		return json;
	}
	
	@RequestMapping(value = "/getDashSaleSummaryPaymentType", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getDashSaleSummaryPaymentType(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		List<DashSalesSummaryPaymentType> salesSummaryPaymentTypeList = null;

		try {
			salesSummaryPaymentTypeList = storeAddressService.getDashSaleSummaryPaymentType(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DashSalesSummaryPaymentType>>() {
		}.getType();
		String json = gson.toJson(salesSummaryPaymentTypeList, type);
		return json;
	}
	
	@RequestMapping(value = "/getDashTopCustomer", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getDashTopCustomer(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		List<DashTopCustomer> topCustomerList = null;

		try {
			topCustomerList = storeAddressService.getDashTopCustomer(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DashTopCustomer>>() {
		}.getType();
		String json = gson.toJson(topCustomerList, type);
		return json;
	}
	
	@RequestMapping(value = "/getDashTopItem", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getDashTopItem(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		List<DashTopItem> topItemList = null;

		try {
			topItemList = storeAddressService.getDashTopItem(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DashTopItem>>() {
		}.getType();
		String json = gson.toJson(topItemList, type);
		return json;
	}

	public StoreAddressService getStoreAddressService() {
		return storeAddressService;
	}

	public void setStoreAddressService(StoreAddressService storeAddressService) {
		this.storeAddressService = storeAddressService;
	}

}

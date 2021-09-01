package com.botree.restaurantapp.webservice;

import java.util.List;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreDayBookRegisterBean;
import com.botree.restaurantapp.model.StoreFeaturesChild;
import com.botree.restaurantapp.model.StoreLocator;
import com.botree.restaurantapp.model.StoreMaster;

public interface StoreLocatorWS {

	List<StoreMaster> getStores();

	List<StoreMaster> getByStoreId(Integer storeId);

	List<RestaurantMaster> getAllRestaurant();

	List<StoreFeaturesChild> getStoreMenuOptionsById(Integer storeId);

	List<RestaurantMaster> getAllRestaurantWithLatLong(Customer customerParam);

	List<StoreMaster> getAllStoresWithLatLong(Customer customerParam);

	String getStoreByStoreId(String id);

	String getAllCitiesForStores();

	List<StoreMaster> getAllStoresByCity(Customer customerParam);

	String getMobileFontByStore(Integer storeId);
	
	 List<StoreMaster> getAllStores();
	 
	 public List<StoreMaster> getAllStoresByRestaurantId(String id);
	 public StoreDayBookRegisterBean insertOpenTime(Integer storeId,
				String userId, String opentimeId);
	 
	String insertCloseTime(Integer storeId,
				String userId, String opentimeId);
	
	public StoreDayBookRegisterBean checkRestaurantOpenStatus(Integer storeId);
	
	String getStoreSMSConfiguration(Integer storeId);
}

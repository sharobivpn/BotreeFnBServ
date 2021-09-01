package com.botree.restaurantapp.dao;

import java.text.ParseException;
import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.DashBoardData;
import com.botree.restaurantapp.model.DashPaymentSummary;
import com.botree.restaurantapp.model.DashSalesSummary;
import com.botree.restaurantapp.model.DashSalesSummaryOrderType;
import com.botree.restaurantapp.model.DashSalesSummaryPaymentType;
import com.botree.restaurantapp.model.DashTopCustomer;
import com.botree.restaurantapp.model.DashTopItem;
import com.botree.restaurantapp.model.MobileFontSetting;
import com.botree.restaurantapp.model.PosModules;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreDayBookRegisterBean;
import com.botree.restaurantapp.model.StoreFeaturesChild;
import com.botree.restaurantapp.model.StoreLocator;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.StoreSMSConfiguration;
import com.botree.restaurantapp.model.dto.TbTableMaster;

public interface StoreAddressDAO {

	public void createStore(StoreLocator store) throws DAOException;

	public List<StoreMaster> getAllStore() throws DAOException;

	// public List<StoreLocator> getAllMainModuleByStore() throws DAOException;

	public void update(StoreLocator store) throws DAOException;

	public void updateModule(List<PosModules> modList) throws DAOException;

	public void delete(StoreLocator store) throws DAOException;

	public List<StoreMaster> getByStoreId(Integer storeId) throws DAOException;
	
	public StoreDayBookRegisterBean checkRestaurantOpenStatus(Integer storeId) throws DAOException, ParseException ;
	
	public StoreDayBookRegisterBean insertOpenTime(Integer storeId,String userId,String opentimeId) throws DAOException, ParseException ;
	
	public String insertCloseTime(Integer storeId,String userId,String opentimeId) throws DAOException, ParseException ;

	public StoreMaster getStoreByStoreId(int storeId) throws DAOException;

	public List<RestaurantMaster> getAllRestaurant() throws DAOException;

	public List<RestaurantMaster> getRestaurants() throws DAOException;

	public List<RestaurantMaster> getAllRestaurantWithLatLong(
			Customer customerParam) throws DAOException;

	public List<StoreMaster> getAllStoresWithLatLong(Customer customerParam)
			throws DAOException;

	public List<StoreFeaturesChild> getStoreMenuOptionsById(Integer storeId)
			throws DAOException;

	public List<StoreMaster> getAllStores() throws DAOException;
	
	public List<StoreMaster> getAllStoresByRestaurantId(String id) throws DAOException;

	public List<StoreMaster> getStoreByRestaurantId(RestaurantMaster restaurant)
			throws DAOException;

	public void addNewRestaurant(RestaurantMaster restaurant)
			throws DAOException;

	public void addNewStore(StoreMaster storeMaster) throws DAOException;

	public void updateRestaurant(RestaurantMaster restaurant)
			throws DAOException;

	public void deleteRestaurant(RestaurantMaster restaurant)
			throws DAOException;

	public void setRestaurantActive(RestaurantMaster restaurant)
			throws DAOException;

	public void setRestaurantInActive(RestaurantMaster restaurant)
			throws DAOException;

	public void updateStore(StoreMaster storeMaster) throws DAOException;

	public void deleteStore(StoreMaster storeMaster) throws DAOException;

	public void setStoreAsActive(StoreMaster storeMaster) throws DAOException;

	public void setStoreAsInActive(StoreMaster storeMaster) throws DAOException;

	public List<StoreMaster> getAllStoresByStoreId() throws DAOException;

	public List<String> getAllCitiesForStores() throws DAOException;

	public List<StoreMaster> getStoresByCity(String city, String cuisineType)
			throws DAOException;

	public List<StoreMaster> getAllStoresByCity(Customer customerParam)
			throws DAOException;

	public MobileFontSetting getMobileFontByStore(Integer storeId)
			throws DAOException;

	public List<TbTableMaster> getAllTablesByStoreId() throws DAOException;

	public void createTable(TbTableMaster table) throws DAOException;

	public List<PosModules> getAllModules() throws DAOException;

	// public List<StoreMaster> getAllStores() throws DAOException;
	
	public StoreSMSConfiguration getStoreSMSConfiguration(int storeId) throws DAOException;
	public DashSalesSummary getDashSaleSummary(Integer storeId,String fromDate,String toDate) throws DAOException;
	public DashPaymentSummary getDashPaymentSummary(Integer storeId,String fromDate,String toDate) throws DAOException;
	public List<DashSalesSummaryOrderType> getDashSaleSummaryOrderType(Integer storeId,String fromDate,String toDate) throws DAOException;
	public List<DashSalesSummaryPaymentType> getDashSaleSummaryPaymentType(Integer storeId,String fromDate,String toDate) throws DAOException;
	public List<DashTopCustomer> getDashTopCustomer(Integer storeId,String fromDate,String toDate) throws DAOException;
	public List<DashTopItem> getDashTopItem(Integer storeId,String fromDate,String toDate) throws DAOException;
	public DashBoardData getDashBoardData(Integer storeId,String fromDate,String toDate) throws DAOException;
}

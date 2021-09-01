package com.botree.restaurantapp.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.StoreAddressDAO;
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
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class StoreAddressService {

  @Autowired
	private StoreAddressDAO storeAddressDAO;

	public StoreAddressService() {

	}

	public void createStore(StoreLocator store) throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.createStore ");
			// create a new store
			storeAddressDAO.createStore(store);
			System.out.println("exit StoreAddressService.createStore ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to create a new store", e);

		}

	}

	public void createTable(TbTableMaster table) throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.createTable ");
			// create a new store
			storeAddressDAO.createTable(table);
			System.out.println("exit StoreAddressService.createTable ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to create a new store", e);

		}

	}

	public void update(StoreLocator store) throws ServiceException {
		try {

			System.out.println("Enter StoreAddressService.updateStore ");
			// update a store
			storeAddressDAO.update(store);
			System.out.println("exit StoreAddressService.updateStore ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to update store", e);

		}

	}
	
	public void updateModule(List<PosModules> modList) throws ServiceException {
		try {

			System.out.println("Enter StoreAddressService.updateModule ");
			// update modules
			storeAddressDAO.updateModule(modList);
			System.out.println("exit StoreAddressService.updateModule ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to update store", e);

		}

	}

	public List<StoreMaster> getAllStore() throws ServiceException {

		List<StoreMaster> storeList = null;
		try {

			System.out.println("Enter StoreAddressService.getAllStore ");
			// get all stores
			storeList = storeAddressDAO.getAllStore();
			System.out.println("exit StoreAddressService.getAllStore ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all stores", e);

		}

		return storeList;
	}

	public List<PosModules> getAllModules() throws ServiceException {

		List<PosModules> moduleList = null;
		try {

			// get all stores
			moduleList = storeAddressDAO.getAllModules();

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all modules", e);

		}

		return moduleList;
	}

	public List<StoreMaster> getAllMainModuleByStore() throws ServiceException {

		List<StoreMaster> storeList = null;
		try {

			System.out
					.println("Enter StoreAddressService.getAllMainModuleByStore ");
			// get all stores
			storeList = storeAddressDAO.getAllStore();
			System.out
					.println("exit StoreAddressService.getAllMainModuleByStore ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all stores", e);

		}

		return storeList;
	}

	public List<StoreMaster> getStoreById(Integer id) throws ServiceException {

		List<StoreMaster> storeList = null;
		try {

			System.out.println("Enter StoreAddressService.getStoreById ");
			// get store by id
			storeList = storeAddressDAO.getByStoreId(id);
			System.out.println("exit StoreAddressService.getStoreById ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get store by id", e);

		}

		return storeList;
	}
	
	public StoreDayBookRegisterBean insertOpenTime(Integer storeId,String userId,String opentimeId) throws ServiceException, ParseException {

		StoreDayBookRegisterBean status=new StoreDayBookRegisterBean();
		try {
			
			status = storeAddressDAO.insertOpenTime(storeId,userId,opentimeId);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to insert store open time", e);

		}

		return status;
	}
	
	public StoreDayBookRegisterBean checkRestaurantOpenStatus(Integer storeId) throws ServiceException, ParseException {

		StoreDayBookRegisterBean status=new StoreDayBookRegisterBean();
		try {
			
			status = storeAddressDAO.checkRestaurantOpenStatus(storeId);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while checkng rest open status", e);

		}

		return status;
	}
	
	public String insertCloseTime(Integer storeId,String userId,String opentimeId) throws ServiceException, ParseException {

		String status="";
		try {
			
			status = storeAddressDAO.insertCloseTime(storeId,userId,opentimeId);

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to insert store open time", e);

		}

		return status;
	}

	public List<StoreMaster> getAllStores() throws ServiceException {

		List<StoreMaster> storeList = null;
		try {

			System.out.println("Enter StoreAddressService.getAllStores ");
			// get All stores
			storeList = storeAddressDAO.getAllStores();
			System.out.println("exit StoreAddressService.getAllStores ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to get all stores", e);

		}

		return storeList;
	}
	
	public List<StoreMaster> getAllStoresByRestaurantId(String id) throws ServiceException {

		List<StoreMaster> storeList = null;
		try {

		
			// get All stores
			storeList = storeAddressDAO.getAllStoresByRestaurantId(id);
			

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to get all stores by restaurant", e);

		}

		return storeList;
	}

	public List<StoreMaster> getAllStoresByStoreId() throws ServiceException {

		List<StoreMaster> storeList = null;
		try {

			System.out
					.println("Enter StoreAddressService.getAllStoresByStoreId ");
			// get All stores
			storeList = storeAddressDAO.getAllStoresByStoreId();
			System.out
					.println("exit StoreAddressService.getAllStoresByStoreId ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to get all stores", e);

		}

		return storeList;
	}

	public List<TbTableMaster> getAllTablesByStoreId() throws ServiceException {

		List<TbTableMaster> tabList = null;
		try {

			System.out
					.println("Enter StoreAddressService.getAllTablesByStoreId ");
			// get All tables
			tabList = storeAddressDAO.getAllTablesByStoreId();
			System.out
					.println("exit StoreAddressService.getAllTablesByStoreId ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"problem occurred while trying to get all tables", e);

		}

		return tabList;
	}

	public List<RestaurantMaster> getAllRestaurant() throws ServiceException {

		List<RestaurantMaster> restaurantList = null;
		try {

			System.out.println("Enter StoreAddressService.getAllRestaurant ");
			// get restaurant by id
			restaurantList = storeAddressDAO.getAllRestaurant();
			System.out.println("exit StoreAddressService.getAllRestaurant ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to all get restaurant", e);

		}

		return restaurantList;
	}

	public List<RestaurantMaster> getAllRestaurantWithLatLong(
			Customer customerParam) throws ServiceException {

		List<RestaurantMaster> restaurantList = null;
		try {

			System.out.println("Enter StoreAddressService.getAllRestaurant ");
			// get restaurant by id
			restaurantList = storeAddressDAO
					.getAllRestaurantWithLatLong(customerParam);
			System.out.println("exit StoreAddressService.getAllRestaurant ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to all get restaurant", e);

		}

		return restaurantList;
	}

	public List<StoreMaster> getAllStoresWithLatLong(Customer customerParam)
			throws ServiceException {

		List<StoreMaster> restaurantList = null;
		try {

			System.out
					.println("Enter StoreAddressService.getAllStoresWithLatLong ");
			// get restaurant by id
			restaurantList = storeAddressDAO
					.getAllStoresWithLatLong(customerParam);
			System.out
					.println("exit StoreAddressService.getAllStoresWithLatLong ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to all get stores", e);

		}

		return restaurantList;
	}

	public List<StoreMaster> getAllStoresByCity(Customer customerParam)
			throws ServiceException {

		List<StoreMaster> storeList = null;
		try {

			System.out.println("Enter StoreAddressService.getAllStoresByCity ");
			// get restaurant by id
			storeList = storeAddressDAO.getAllStoresByCity(customerParam);
			System.out.println("exit StoreAddressService.getAllStoresByCity ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to all get stores", e);

		}

		return storeList;
	}

	public List<String> getAllCitiesForStores() throws ServiceException {

		List<String> cityList = null;
		try {

			System.out
					.println("Enter StoreAddressService.getAllCitiesForStores ");
			// get restaurant by id
			cityList = storeAddressDAO.getAllCitiesForStores();
			System.out
					.println("exit StoreAddressService.getAllCitiesForStores ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to all get cities", e);

		}

		return cityList;
	}

	public List<RestaurantMaster> getRestaurants() throws ServiceException {

		List<RestaurantMaster> restaurantList = null;
		try {

			System.out.println("Enter StoreAddressService.getRestaurants ");
			// get all restaurants
			restaurantList = storeAddressDAO.getRestaurants();
			System.out.println("exit StoreAddressService.getRestaurants ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get all restaurant", e);

		}

		return restaurantList;
	}

	public void delete(StoreLocator store) throws ServiceException {
		try {

			System.out.println("Enter StoreAddressService.deleteStore ");
			// delete a store
			storeAddressDAO.delete(store);
			System.out.println("exit StoreAddressService.deleteStore ");
		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to delete a store", e);
		}

	}

	public List<StoreFeaturesChild> getStoreMenuOptionsById(Integer storeId)
			throws ServiceException {

		List<StoreFeaturesChild> storeFeaturesChilds = null;
		try {

			System.out
					.println("Enter StoreAddressService.getStoreMenuOptionsById ");
			// get StoreMenuOptions by id
			storeFeaturesChilds = storeAddressDAO.getStoreMenuOptionsById(storeId);
			System.out
					.println("exit StoreAddressService.getStoreMenuOptionsById ");

		} catch (DAOException e) {

			throw new ServiceException(
					"problem occurred while trying to get StoreMenuOptions by id",
					e);

		}

		return storeFeaturesChilds;
	}

	public void addNewRestaurant(RestaurantMaster restaurant)
			throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.addNewRestaurant ");
			// add a new Restaurant
			storeAddressDAO.addNewRestaurant(restaurant);
			System.out.println("exit StoreAddressService.addNewRestaurant ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to create a new restaurant",
					e);

		}

	}

	public void addNewStore(StoreMaster storeMaster) throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.addNewStore ");
			// add a new Restaurant
			storeAddressDAO.addNewStore(storeMaster);
			System.out.println("exit StoreAddressService.addNewStore ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to create a new store", e);

		}

	}

	public void updateRestaurant(RestaurantMaster restaurant)
			throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.updateRestaurant ");
			// update a new Restaurant
			storeAddressDAO.updateRestaurant(restaurant);
			System.out.println("exit StoreAddressService.updateRestaurant ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to update a Restaurant", e);

		}

	}

	public void deleteRestaurant(RestaurantMaster restaurant)
			throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.deleteRestaurant ");
			// add a new Restaurant
			storeAddressDAO.deleteRestaurant(restaurant);
			System.out.println("exit StoreAddressService.deleteRestaurant ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to delete a Restaurant", e);

		}

	}

	public void setRestaurantActive(RestaurantMaster restaurant)
			throws ServiceException {
		try {
			System.out
					.println("Enter StoreAddressService.setRestaurantActive ");
			// add a new Restaurant
			storeAddressDAO.setRestaurantActive(restaurant);
			System.out.println("exit StoreAddressService.setRestaurantActive ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to activate a Restaurant", e);

		}

	}

	public void setRestaurantInActive(RestaurantMaster restaurant)
			throws ServiceException {
		try {
			System.out
					.println("Enter StoreAddressService.setRestaurantInActive ");
			// add a new Restaurant
			storeAddressDAO.setRestaurantInActive(restaurant);
			System.out
					.println("exit StoreAddressService.setRestaurantInActive ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to deactivate a Restaurant",
					e);

		}

	}

	public void updateStore(StoreMaster storeMaster) throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.updateStore ");
			// add a new Restaurant
			storeAddressDAO.updateStore(storeMaster);
			System.out.println("exit StoreAddressService.updateStore ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to update a  store", e);

		}

	}

	public void deleteStore(StoreMaster storeMaster) throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.deleteStore ");
			// delete a store
			storeAddressDAO.deleteStore(storeMaster);
			System.out.println("exit StoreAddressService.deleteStore ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to delete a store", e);

		}

	}

	public void setStoreAsActive(StoreMaster storeMaster)
			throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.setStoreAsActive ");
			// add a new Restaurant
			storeAddressDAO.setStoreAsActive(storeMaster);
			System.out.println("exit StoreAddressService.setStoreAsActive ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to activate a store", e);

		}

	}

	public void setStoreAsInActive(StoreMaster storeMaster)
			throws ServiceException {
		try {
			System.out.println("Enter StoreAddressService.setStoreAsInActive ");
			// add a new Restaurant
			storeAddressDAO.setStoreAsInActive(storeMaster);
			System.out.println("exit StoreAddressService.setStoreAsInActive ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to deactivate a store", e);

		}

	}

	public StoreMaster getStoreByStoreId(int id) throws ServiceException {
		StoreMaster storeMaster = null;
		try {
			System.out.println("Enter StoreAddressService.getStoreByStoreId ");
			// add a new Restaurant
			storeMaster = storeAddressDAO.getStoreByStoreId(id);
			System.out.println("exit StoreAddressService.getStoreByStoreId ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to get store by id", e);

		}
		return storeMaster;
	}

	public MobileFontSetting getMobileFontByStore(Integer storeId)
			throws ServiceException {
		MobileFontSetting fontSetting = null;
		try {
			System.out
					.println("Enter StoreAddressService.getMobileFontByStore ");
			fontSetting = storeAddressDAO.getMobileFontByStore(storeId);
			System.out
					.println("exit StoreAddressService.getMobileFontByStore ");

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to get fonts by storeId", e);

		}
		return fontSetting;
	}
	
	public StoreSMSConfiguration getStoreSMSConfiguration(int id) throws ServiceException {
		StoreSMSConfiguration storeSMS = null;
		try {
			storeSMS = storeAddressDAO.getStoreSMSConfiguration(id);

		} catch (DAOException e) {
			e.printStackTrace();

			throw new ServiceException(
					"problem occurred while trying to get store sms conf by id", e);

		}
		return storeSMS;
	}
	
	public DashSalesSummary getDashSaleSummary(Integer storeId,String fromDate,String toDate) throws ServiceException {
		DashSalesSummary salesSummary = null;
		try {
			salesSummary = storeAddressDAO.getDashSaleSummary(storeId,fromDate,toDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to get sales summary dashboard", e);
		}
		return salesSummary;
	}
	
	public DashPaymentSummary getDashPaymentSummary(Integer storeId,String fromDate,String toDate) throws ServiceException {
		DashPaymentSummary paymentSummary = null;
		try {
			paymentSummary = storeAddressDAO.getDashPaymentSummary(storeId,fromDate,toDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to get payment summary dashboard", e);
		}
		return paymentSummary;
	}
	
	public List<DashSalesSummaryOrderType> getDashSaleSummaryOrderType(Integer storeId,String fromDate,String toDate) throws ServiceException {
		List<DashSalesSummaryOrderType> salesSummaryOrderTypeList = null;
		try {
			salesSummaryOrderTypeList = storeAddressDAO.getDashSaleSummaryOrderType(storeId,fromDate,toDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to get sales summary order type dashboard", e);
		}
		return salesSummaryOrderTypeList;
	}
	
	public List<DashSalesSummaryPaymentType> getDashSaleSummaryPaymentType(Integer storeId,String fromDate,String toDate) throws ServiceException {
		List<DashSalesSummaryPaymentType> salesSummaryPaymentTypeList = null;
		try {
			salesSummaryPaymentTypeList = storeAddressDAO.getDashSaleSummaryPaymentType(storeId,fromDate,toDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to get sales summary payment type dashboard", e);
		}
		return salesSummaryPaymentTypeList;
	}
	
	public List<DashTopCustomer> getDashTopCustomer(Integer storeId,String fromDate,String toDate) throws ServiceException {
		List<DashTopCustomer> topCustomerList = null;
		try {
			topCustomerList = storeAddressDAO.getDashTopCustomer(storeId,fromDate,toDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to get top customer dashboard", e);
		}
		return topCustomerList;
	}
	
	public List<DashTopItem> getDashTopItem(Integer storeId,String fromDate,String toDate) throws ServiceException {
		List<DashTopItem> topItemList = null;
		try {
			topItemList = storeAddressDAO.getDashTopItem(storeId,fromDate,toDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to get top item dashboard", e);
		}
		return topItemList;
	}
	
	public DashBoardData getDashBoardData(Integer storeId,String fromDate,String toDate) throws ServiceException {
		DashBoardData dData = null;
		try {
			dData = storeAddressDAO.getDashBoardData(storeId,fromDate,toDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to get fnb dash board data", e);
		}
		return dData;
	}

	public StoreAddressDAO getStoreAddressDAO() {
		return storeAddressDAO;
	}

	public void setStoreAddressDAO(StoreAddressDAO storeAddrDAO) {
		this.storeAddressDAO = storeAddrDAO;
	}

}

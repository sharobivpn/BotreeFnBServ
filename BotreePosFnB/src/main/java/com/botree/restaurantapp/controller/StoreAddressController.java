package com.botree.restaurantapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreLocator;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.dto.TbTableMaster;
import com.botree.restaurantapp.service.StoreAddressService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class StoreAddressController {
	private final static Logger logger = LogManager.getLogger(StoreAddressController.class);

	private StoreLocator storeLocator;
	private StoreAddressService storeAddressService = null;
	private int id;
	private String address;
	private List<StoreMaster> storeList = new ArrayList<StoreMaster>();
	private StoreLocator newStoreLocator;
	private RestaurantMaster restaurantMaster;
	private RestaurantMaster restaurantMaster1;
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private List<RestaurantMaster> restaurantMasterList = new ArrayList<RestaurantMaster>();
	private List<RestaurantMaster> restaurantList = new ArrayList<RestaurantMaster>();
	private StoreMaster storeMaster;
	private StoreMaster storeMaster1;
	private List<StoreMaster> storeMasterLst = new ArrayList<StoreMaster>();
	private List<TbTableMaster> tableLst = new ArrayList<TbTableMaster>();
	private Part part;
	private TbTableMaster table;

	public StoreAddressController() {

	}

	FacesContext context = FacesContext.getCurrentInstance();
	FacesContext context1 = FacesContext.getCurrentInstance();

	@PostConstruct
	public void postConstruct() {

		try {

			System.out.println("Enter StoreAddressController.getAllStore ");

			// get all stores
			storeList = storeAddressService.getAllStore();
			System.out.println("number of stores:" + storeList.size());
			System.out.println("exit StoreAddressController.getAllStore ");

			System.out.println("enter StoreAddressController.getAllRestaurant in getAllStore");
			getAllRestaurant();
			System.out.println("exit StoreAddressController.getAllRestaurant in getAllStore");

			System.out.println("exit StoreAddressController.getStores in getAllStore");
			getStores();
			System.out.println("exit StoreAddressController.getStores in getAllStore");

			System.out.println("exit StoreAddressController.getRestaurants in getAllStore");
			getRestaurants();
			System.out.println("exit StoreAddressController.getRestaurants in getAllStore");
			getTables();

		} catch (Exception e) {

			System.out.println("Exception getting all stores");
			e.printStackTrace();

		}

	}

	public String createStore() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.createStore ");
			// create a new store
			System.out.println("store after creation is:" + newStoreLocator);
			storeAddressService.createStore(newStoreLocator);
			System.out.println("exit StoreAddressController.createStore ");

		} catch (ServiceException e) {

			throw new ServiceException("problem occurred while trying to create a new store", e);

		}

		return "/page/disp_store.xhtml?faces-redirect=true";

	}

	public String createTable() throws ServiceException {

		System.out.println("In create table ::::");
		System.out.println("table is ::: " + table);

		try {

			System.out.println("Enter StoreAddressController.createTable ");
			// create a new table

			storeAddressService.createTable(table);
			System.out.println("exit StoreAddressController.createTable ");

		} catch (ServiceException e) {

			throw new ServiceException("problem occurred while trying to create a new table", e);

		}

		return "/page/disp_tables.xhtml?faces-redirect=true";

	}

	public String update() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.updateStore ");
			// update a store
			storeAddressService.update(storeLocator);
			System.out.println("exit StoreAddressController.updateStore ");

		} catch (ServiceException e) {

			throw new ServiceException("problem occurred while trying to update store", e);

		}

		return "/page/disp_store.xhtml?faces-redirect=true";

	}

	public String delete() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.deleteStore ");
			// delete a store
			System.out.println("store lo:" + storeLocator);
			storeAddressService.delete(storeLocator);
			System.out.println("exit StoreAddressController.deleteStore ");
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to delete a store", e);
		}
		return "/page/disp_store.xhtml?faces-redirect=true";

	}

	public String editStore() {

		String redirect = "";
		System.out.println("In editStore");

		redirect = "edit_store_page";

		return redirect;
	}

	public String addStore() {

		return "/page/add_store.xhtml?faces-redirect=true";
	}

	public String addTable() {

		return "/page/add_table.xhtml?faces-redirect=true";
	}

	public String dispStores() {
		System.out.println(" in display stores");
		String redirect = "";

		redirect = "disp_store_page";
		System.out.println("going to...");
		// System.out.println("user list size:" + userList.size());
		return redirect;
		//return "/page/Demo.jsp";

	}

	/**************************** All Restaurant Info methods ********************************/

	public void getAllRestaurant() {

		try {
			System.out.println("Enter getAllRestaurant");
			System.out.println("select items size:" + selectItems.size());

			restaurantMasterList = storeAddressService.getRestaurants();

			System.out.println("number of Restaurants:" + restaurantMasterList.size());

			Iterator<RestaurantMaster> iterator = restaurantMasterList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				RestaurantMaster master = (RestaurantMaster) iterator.next();

				si.setValue(master.getId());
				System.out.println("Restaurant Id :" + master.getId());
				si.setLabel(master.getName());
				System.out.println("Restaurant Name :" + master.getName());

				selectItems.add(si);

			}

			System.out.println("exit getAllRestaurant");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("In getAllRestaurant Exception");
		}

	}

	public void getRestaurants() {

		try {
			System.out.println("Enter getRestaurants");
			System.out.println("select items size:" + selectItems.size());

			restaurantList = storeAddressService.getRestaurants();

			System.out.println("number of Restaurants:" + restaurantList.size());

			System.out.println("exit getRestaurants");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("In getRestaurants Exception");
		}

	}

	public String addNewRestaurant() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.addNewRestaurant ");
			// create a new restaurant
			storeAddressService.addNewRestaurant(restaurantMaster1);
			System.out.println("exit StoreAddressController.addNewRestaurant ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to create a new restaurant", e);

		}

		return "/page/st_disp_restaurant.xhtml?faces-redirect=true";

	}

	public String updateRestaurant() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.updateRestaurant ");
			System.out.println("Rest Id : " + restaurantMaster.getId());
			System.out.println("Rest Name :" + restaurantMaster.getName());

			// update a restaurant
			storeAddressService.updateRestaurant(restaurantMaster);
			System.out.println("exit StoreAddressController.updateRestaurant ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to update a restaurant", e);

		}
		context.getExternalContext().getSessionMap().remove("restaurant");
		return "/page/st_disp_restaurant.xhtml?faces-redirect=true";

	}

	public String deleteRestaurant() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.deleteRestaurant ");
			// delete a restaurant
			storeAddressService.deleteRestaurant(restaurantMaster);
			System.out.println("exit StoreAddressController.deleteRestaurant ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to delete a restaurant", e);

		}

		return "/page/st_disp_restaurant.xhtml?faces-redirect=true";

	}

	public String setRestaurantActive() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.setRestaurantActive ");
			// delete a restaurant
			storeAddressService.setRestaurantActive(restaurantMaster);
			System.out.println("exit StoreAddressController.setRestaurantActive ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to activate a restaurant", e);

		}

		return "/page/st_disp_restaurant.xhtml?faces-redirect=true";

	}

	public String setRestaurantInActive() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.setRestaurantInActive ");
			// delete a restaurant
			storeAddressService.setRestaurantInActive(restaurantMaster);
			System.out.println("exit StoreAddressController.setRestaurantInActive ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to de-activate a restaurant", e);

		}

		return "/page/st_disp_restaurant.xhtml?faces-redirect=true";

	}

	public String dispRestaurant() {
		System.out.println("in dispRestaurant");

		return "/page/st_add_restaurant.xhtml?faces-redirect=true";

	}

	public String showRestaurantForST() {
		System.out.println("in showRestaurantForST");

		return "st_disp_restaurant_page";

	}

	public String addRestaurant() {
		System.out.println("in addRestaurant");
		return "/page/st_add_restaurant.xhtml?faces-redirect=true";

	}

	public String editRestaurant() {
		try {

			System.out.println("in editRestaurant");
			//FacesContext context=FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("restaurant", restaurantMaster);
			System.out.println("Restaurant Id :" + restaurantMaster.getId());
			System.out.println("Restaurant Name :" + restaurantMaster.getName());
			System.out.println("Restaurant Address :" + restaurantMaster.getAddress());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("please clear the session.........");
			e.printStackTrace();
		}
		return "/page/st_edit_restaurant.xhtml?faces-redirect=true";

	}

	/**************************** All Store Info methods ********************************/

	public void getStores() {

		try {

			System.out.println("Enter StoreAddressController.getStores ");
			// get all stores
			storeMasterLst = storeAddressService.getAllStoresByStoreId();
			System.out.println("number of stores:" + storeMasterLst.size());
			System.out.println("exit StoreAddressController.getStores ");

		} catch (Exception e) {

			System.out.println("Exception getting all stores by store id");
			e.printStackTrace();

		}

	}

	public void getTables() {

		try {

			System.out.println("Enter StoreAddressController.getTables ");
			// get all tables
			tableLst = storeAddressService.getAllTablesByStoreId();
			System.out.println("number of stores:" + storeMasterLst.size());
			System.out.println("exit StoreAddressController.getTables ");

		} catch (Exception e) {

			System.out.println("Exception getting all tables by store id");
			e.printStackTrace();

		}

	}

	public String addNewStore() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.addNewStore ");
			System.out.println("Parent Restaurant Id :" + storeMaster1.getRestaurant().getId());
			// create a new store
			logger.debug("Adding this store: {}", storeMaster1);
			storeAddressService.addNewStore(storeMaster1);

			// upload image
			//uploadImage(maxCatId);
			System.out.println("exit StoreAddressController.addNewStore ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to create a new store", e);

		}

		//return "/page/disp_storemaster.xhtml?faces-redirect=true";
		return "/page/st_disp_storemaster.xhtml?faces-redirect=true";

	}

	public String uploadImage(int storeId) throws IOException {
		System.out.println("enter uploadImage client store edit::" + part);
		// Extract file name from content-disposition header of file part
		if (part != null) {
			String fileName = getFileName(part);
			System.out.println("***** fileName: " + fileName);
			System.out.println("***** store id: " + storeId);
			String[] parts = fileName.split("\\.");

			String beforeDot = parts[0];
			String afterDot = parts[1];

			String changedFileName = "" + storeId + "." + afterDot;
			System.out.println("changed file name:  " + changedFileName);

			String basePath = "/home/ubuntu/.resturant/logo";
			//String basePath = "C:/restaurantImages/logo";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				System.out.println("windows machine: upload logo");
				basePath = "C:/restaurantImages/logo";
			}

			System.out.println("basePath :" + basePath);
			File outputFilePath = new File(basePath + "/" + changedFileName);

			// Copy uploaded file to destination path
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = part.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);

				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				// statusMessage = "File upload successful !!";
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("In uploadFile exception");
				// statusMessage = "File upload failed !!";
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return null; // return to same page
	}

	private String getFileName(Part part) {
		try {

			final String partHeader = part.getHeader("content-disposition");
			System.out.println("***** partHeader: " + partHeader);
			for (String content : part.getHeader("content-disposition").split(";")) {
				if (content.trim().startsWith("filename")) {
					return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("In getFileName exception");

		}
		return null;
	}

	public String updateStore() throws ServiceException {
		String isSharobi = "";
		try {

			System.out.println("Enter StoreAddressController.updateStore ");
			// update a store
			storeAddressService.updateStore(storeMaster);

			//check if client is editing
			isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");
			System.out.println("isSharobi:   ----" + isSharobi);
			if (isSharobi != "y" || isSharobi == null || isSharobi == "") {
				System.out.println("sharobi store edit upload logo client");
				//FacesContext context = FacesContext.getCurrentInstance();
				StoreMaster storeMaster = (StoreMaster) context.getExternalContext().getSessionMap().get("selectedstore");

				int storeId = 0;
				if (storeMaster != null) {
					int restId = storeMaster.getRestaurantId();
					System.out.println("rest id:  " + restId);
					storeId = storeMaster.getId();
				}
				// upload image
				uploadImage(storeId);

			}

			System.out.println("exit StoreAddressController.updateStore ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to update a store", e);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In update category IOException");
			e.printStackTrace();
		}

		//check if sharobi tech is editing
		//String isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");
		if (isSharobi == "y") {
			System.out.println("sharobi store edit done");
			return "/page/st_disp_storemaster.xhtml?faces-redirect=true";

		} else {
			System.out.println("client store edit done");
			return "/page/disp_storemaster.xhtml?faces-redirect=true";
		}

	}

	public String updateStoreST() throws ServiceException {
		String isSharobi = "";
		try {

			System.out.println("Enter StoreAddressController.updateStoreST ");
			// update a store
			storeAddressService.updateStore(storeMaster);

			//check if client is editing
			isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");
			System.out.println("isSharobi:   ----" + isSharobi);
			if (isSharobi != "y" || isSharobi == null || isSharobi == "") {
				System.out.println("sharobi store edit upload logo client");
				//FacesContext context = FacesContext.getCurrentInstance();
				StoreMaster storeMaster = (StoreMaster) context.getExternalContext().getSessionMap().get("selectedstore");

				int storeId = 0;
				if (storeMaster != null) {
					int restId = storeMaster.getRestaurantId();
					System.out.println("rest id:  " + restId);
					storeId = storeMaster.getId();
				}
				// upload image
				uploadImage(storeId);

			}

			System.out.println("exit StoreAddressController.updateStoreST ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to update a store", e);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In update category IOException");
			e.printStackTrace();
		}

		//check if sharobi tech is editing
		//String isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");

		System.out.println("sharobi store edit done");
		return "/page/st_disp_storemaster.xhtml?faces-redirect=true";

	}
	
	

	public String setStoreAsActive() throws ServiceException {

		String isSharobi = "";
		try {

			System.out.println("Enter StoreAddressController.setStoreAsActive ");
			// delete a store
			storeAddressService.setStoreAsActive(storeMaster);
			System.out.println("exit StoreAddressController.setStoreAsActive ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to activate a store", e);

		}

		//check if sharobi tech is editing
		isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");
		if (isSharobi == "y") {
			System.out.println("sharobi store edit done");
			return "/page/st_disp_storemaster.xhtml?faces-redirect=true";

		} else {
			System.out.println("client store edit done");
			return "/page/disp_storemaster.xhtml?faces-redirect=true";
		}

	}

	public String deleteStore() throws ServiceException {
		try {

			System.out.println("Enter StoreAddressController.deleteStore ");
			// delete a store
			storeAddressService.deleteStore(storeMaster);
			System.out.println("exit StoreAddressController.deleteStore ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to delete a store", e);

		}

		//check if sharobi tech is deleting
		String isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");
		if (isSharobi == "y") {
			System.out.println("sharobi store edit");
			return "/page/st_disp_storemaster.xhtml?faces-redirect=true";

		} else {
			System.out.println("client store edit");
			return "/page/disp_storemaster.xhtml?faces-redirect=true";
		}

	}

	public String deleteTable() throws ServiceException {
		/*	try {

				System.out.println("Enter StoreAddressController.deleteTable ");
				// delete a store
				storeAddressService.deleteStore(storeMaster);
				System.out.println("exit StoreAddressController.deleteTable ");

			} catch (ServiceException e) {
				e.printStackTrace();
				throw new ServiceException("problem occurred while trying to delete a store", e);

			}*/

		return "";

	}

	public String setStoreAsInActive() throws ServiceException {

		String isSharobi = "";
		try {

			System.out.println("Enter StoreAddressController.setStoreAsInActive ");
			// delete a store
			storeAddressService.setStoreAsInActive(storeMaster);
			System.out.println("exit StoreAddressController.setStoreAsInActive ");

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to deactivate a store", e);

		}

		//check if sharobi tech is editing
		isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");
		if (isSharobi == "y") {
			System.out.println("sharobi store edit done");
			return "/page/st_disp_storemaster.xhtml?faces-redirect=true";

		} else {
			System.out.println("client store edit done");
			return "/page/disp_storemaster.xhtml?faces-redirect=true";
		}

	}

	public String dispStoreMaster() {
		System.out.println("in dispStoreMaster");

		return "/page/add_new_store.xhtml?faces-redirect=true";

	}

	public String showStoreMaster() {
		System.out.println("in showStoreMaster: ");

		return "disp_storemaster_page";

	}

	public String dispTables() {
		System.out.println("in dispTables: ");

		return "disp_table_page";

	}

	public String showStoreMasterForST() {
		System.out.println("in showStoreMasterForST: ");

		return "st_disp_storemaster_page";

	}

	public String addStoreMaster() {
		System.out.println("in addStoreMaster");

		return "/page/add_new_store.xhtml?faces-redirect=true";

	}

	public String editStoreMaster() {
		System.out.println("in editStoreMaster");
		context1.getExternalContext().getSessionMap().put("store", storeMaster);
		System.out.println("Store Id :" + storeMaster.getId());

		return "/page/edit_storemaster.xhtml?faces-redirect=true";

	}

	public String editTable() {
		/*System.out.println("in editTable");
		context1.getExternalContext().getSessionMap().put("store", storeMaster);
		System.out.println("Store Id :" + storeMaster.getId());

		return "/page/edit_storemaster.xhtml?faces-redirect=true";
		*/
		return "";

	}

	public String editStoreMasterForST() {
		System.out.println("in editStoreMasterForST");
		logger.debug("editStoreMasterForST: Edit store master: {}", storeMaster);
		context1.getExternalContext().getSessionMap().put("store", storeMaster);
		System.out.println("Store Id ST:" + storeMaster.getId());

		return "/page/st_edit_storemaster.xhtml?faces-redirect=true";

	}
	
	public String editMoudles() {
		//StoreAddressDAO addressDAO=new StoreAddressDAOImpl();
		System.out.println("in editMoudles");
		logger.debug("editMoudles: Edit store master: {}", storeMaster);
	
			//storeMaster=addressDAO.getStoreByStoreId(storeMaster.getId());
			context1.getExternalContext().getSessionMap().put("store", storeMaster);
			System.out.println("Store Id ST:" + storeMaster.getId());
	
		return "/page/st_edit_modules.xhtml?faces-redirect=true";

	}

	/**************************** All Store Info methods End ********************************/

	public StoreLocator getStoreLocator() {
		return storeLocator;
	}

	public void setStoreLocator(StoreLocator storeLocator) {
		this.storeLocator = storeLocator;
	}

	public StoreAddressService getStoreAddressService() {
		return storeAddressService;
	}

	public void setStoreAddressService(StoreAddressService storeAddressService) {
		this.storeAddressService = storeAddressService;
	}

	public List<StoreMaster> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<StoreMaster> storeList) {
		this.storeList = storeList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public StoreLocator getNewStoreLocator() {
		return newStoreLocator;
	}

	public void setNewStoreLocator(StoreLocator newStoreLocator) {
		this.newStoreLocator = newStoreLocator;
	}

	public RestaurantMaster getRestaurantMaster() {
		return restaurantMaster;
	}

	public void setRestaurantMaster(RestaurantMaster restaurantMaster) {
		this.restaurantMaster = restaurantMaster;
	}

	public RestaurantMaster getRestaurantMaster1() {
		return restaurantMaster1;
	}

	public void setRestaurantMaster1(RestaurantMaster restaurantMaster1) {
		this.restaurantMaster1 = restaurantMaster1;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public List<RestaurantMaster> getRestaurantMasterList() {
		return restaurantMasterList;
	}

	public void setRestaurantMasterList(List<RestaurantMaster> restaurantMasterList) {
		this.restaurantMasterList = restaurantMasterList;
	}

	public List<RestaurantMaster> getRestaurantList() {
		return restaurantList;
	}

	public void setRestaurantList(List<RestaurantMaster> restaurantList) {
		this.restaurantList = restaurantList;
	}

	public StoreMaster getStoreMaster() {
		return storeMaster;
	}

	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}

	public StoreMaster getStoreMaster1() {
		return storeMaster1;
	}

	public void setStoreMaster1(StoreMaster storeMaster1) {
		this.storeMaster1 = storeMaster1;
	}

	public List<StoreMaster> getStoreMasterLst() {
		return storeMasterLst;
	}

	public void setStoreMasterLst(List<StoreMaster> storeMasterLst) {
		this.storeMasterLst = storeMasterLst;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public List<TbTableMaster> getTableLst() {
		return tableLst;
	}

	public void setTableLst(List<TbTableMaster> tableLst) {
		this.tableLst = tableLst;
	}

	public TbTableMaster getTable() {
		return table;
	}

	public void setTable(TbTableMaster table) {
		this.table = table;
	}

}

package com.botree.restaurantapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.dao.StoreAddressDAO;
import com.botree.restaurantapp.dao.StoreAddressDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.PosModules;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreLocator;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.dto.TbTableMaster;
import com.botree.restaurantapp.service.StoreAddressService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class ModuleController {
	private final static Logger logger = LogManager
			.getLogger(ModuleController.class);

	private StoreLocator storeLocator;
	private StoreAddressService storeAddressService = null;
	private int id;
	private String address;
	private List<StoreLocator> storeList = new ArrayList<StoreLocator>();
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
	private List<PosModules> moduleList = new ArrayList<PosModules>();
	private Map<Integer, Boolean> select = new HashMap<Integer, Boolean>();

	public ModuleController() {

	}

	FacesContext context = FacesContext.getCurrentInstance();
	FacesContext context1 = FacesContext.getCurrentInstance();

	@PostConstruct
	public void postConstruct() {

		try {

			System.out.println("Enter ModuleController.getAllStore ");

			// get all stores
			// storeList = storeAddressService.getAllStore();
			// get list of main modules

			// get module list
			moduleList = storeAddressService.getAllModules();

			System.out.println("exit ModuleController.getAllStore ");

		} catch (Exception e) {

			System.out.println("Exception getting all stores");
			e.printStackTrace();

		}

	}
	
	public String updateModule() throws ServiceException {
		String isSharobi = "";
		try {

			System.out.println("Enter ModuleController.updateModule ");
			
			List<PosModules> modList=this.moduleList;
			 storeAddressService.updateModule(modList);
			
			// update a store
		/*	storeAddressService.updateStore(storeMaster);

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
				

			}*/

			System.out.println("exit ModuleController.updateModule ");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("problem occurred while trying to update a store", e);

		}

		//check if sharobi tech is editing
		//String isSharobi = (String) context.getExternalContext().getSessionMap().get("isSharobi");

		System.out.println("sharobi store edit done");
		return "/page/st_edit_modules.xhtml?faces-redirect=true";

	}

	public String editMoudles() {
		System.out.println("in editMoudles");
		StoreAddressDAO addressDAO=new StoreAddressDAOImpl();
		logger.debug("editMoudles: Edit store master: {}", storeMaster);
		try {
			storeMaster=addressDAO.getStoreByStoreId(storeMaster.getId());
			context1.getExternalContext().getSessionMap().put("store", storeMaster);
			System.out.println("Store Id ST:" + storeMaster.getId());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return "/page/st_edit_modules.xhtml?faces-redirect=true";

	}

	public List<PosModules> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<PosModules> moduleList) {
		this.moduleList = moduleList;
	}

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

	public List<StoreLocator> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<StoreLocator> storeList) {
		this.storeList = storeList;
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

	public void setRestaurantMasterList(
			List<RestaurantMaster> restaurantMasterList) {
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

	public List<TbTableMaster> getTableLst() {
		return tableLst;
	}

	public void setTableLst(List<TbTableMaster> tableLst) {
		this.tableLst = tableLst;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public TbTableMaster getTable() {
		return table;
	}

	public void setTable(TbTableMaster table) {
		this.table = table;
	}

	public Map<Integer, Boolean> getSelect() {
		return select;
	}

	public void setSelect(Map<Integer, Boolean> select) {
		this.select = select;
	}

}

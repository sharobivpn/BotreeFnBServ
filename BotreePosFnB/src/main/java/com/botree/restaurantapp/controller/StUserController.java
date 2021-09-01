package com.botree.restaurantapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.model.StUser;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.service.StUserService;
import com.botree.restaurantapp.service.StoreAddressService;
import com.botree.restaurantapp.service.exception.ServiceException;

//@SessionScoped

public class StUserController {
	private final static Logger logger = LogManager.getLogger(StUserController.class);

	private boolean showUserPane = false;
	private List<StUser> userList = new ArrayList<StUser>();
	private List<User> userLst;

	private Map sessMap = new HashMap();
	private StUser stUser;
	private User newUser;
	private String userId;
	private String action;
	private String redirect;
	private StUserService stUserService;
	private StoreAddressService storeAddressService;
	private List<StoreMaster> strList = new ArrayList<StoreMaster>();
	private List<SelectItem> selectCategories = new ArrayList<SelectItem>();
	private StoreMaster storeMaster;
	private SelectItem selectItem;
	private int i = 0;
	private StUser loggdUser;

	public StUserController() {
		// TODO Auto-generated constructor stub
		System.out.println("In UserController");

	}

	@PostConstruct
	public void postConstruct() {

		try {
			System.out.println("In get all st users");
			//System.out.println("current user:  " + stUser.getId());
			i++;
			System.out.println("i is  :" + i);
			FacesContext context = FacesContext.getCurrentInstance();
			//context.getExternalContext().getSessionMap().put("loggeduser", loggedUser);
			loggdUser = (StUser) context.getExternalContext().getSessionMap().get("loggeduser");

			userList = stUserService.getAllUsers();
			System.out.println("number of st users:" + userList.size());
			Iterator<StUser> iterator = userList.iterator();
			while (iterator.hasNext()) {
				StUser user = (StUser) iterator.next();
				System.out.println("st user name:" + user.getUserName());
			}
			System.out.println("editing st user..:" + stUser);
			System.out.println("test6");

		} catch (Exception e) {
			// throw new DAOException("Check data to be inserted", e);
			e.printStackTrace();
			System.out.println("test8");
		}

	}

	public String dispUser() {
		/*System.out.println(" in display user");

		// this.showUserPane = true;
		redirect = "disp_user_page";
		System.out.println("going to...");
		System.out.println("user list size:" + userList.size());*/
		//return redirect;
		return "";

	}

	public String editNDeleteUser() {

		String redirect = "";
		System.out.println("In editNDeleteUser");
		System.out.println("user id: " + userId);
		System.out.println("action:" + action);
		try {

			if (action.equalsIgnoreCase("edit")) {
				//stUser = userService.getUserById(userId);
				System.out.println("user:    " + stUser);
				redirect = "edit_user_page";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return redirect;
	}

	/*public String deleteUser() {
		System.out.println("In deleteUser");

		try {
			System.out.println("user to b deleted is:" + stUser);
			userService.delete(stUser);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserController.delete");

		return "/page/disp_user.xhtml?faces-redirect=true";

	}*/

	/*public String update() {

		try {

			userService.update(stUser);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserController.save");

		return "/page/disp_user.xhtml?faces-redirect=true";

	}*/

	/*public String createUser() {

		String redirect;
		try {
			System.out.println("user after creation is:" + newUser);
			System.out.println("rest id: " + newUser.getRestMaster().getId());
			stUserService.createUser(newUser);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserController.createUser");
		//redirect = "disp_user_page";
		//return "";
		return "/page/disp_user.xhtml?faces-redirect=true";
	}*/

	public String addUser() {

		return "/page/create_user.xhtml?redirect=true";

	}

	public String login() {
		i++;
		System.out.println("value of i " + i);
		StUser loggedUser = null;
		String redirect = "";
		logger.debug("log in st user: [{}]", stUser);
		try {

			loggedUser = stUserService.login(stUser);
			System.out.println("logged user: " + loggedUser.getId());
			FacesContext context = FacesContext.getCurrentInstance();
			//String isSharobi="y";
			//context.getExternalContext().getSessionMap().put("loggeduser", loggedUser);
			//System.out.println("setting sharobi");
			//context.getExternalContext().getSessionMap().put("isSharobi", isSharobi);

		} catch (ServiceException e) {
			logger.error("Exception: ", e);
		}
		if (loggedUser != null) {
			logger.debug("st user id: ", loggedUser.getId());
			/*// get stores for logged user
			strList = getStoresByUser(loggedUser.getId());
			System.out.println("number of stores :" + strList.size());
			populateDropDownList(strList);*/
			redirect = "/page/st_index.xhtml?faces-redirect=true";
		} else {
			logger.error("invalid login");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid credentials"));
		}
		return redirect;
	}

	/*public List<StoreMaster> getStoresByUser(int id) {

		try {
			logger.debug("Enter UserController:getStoresByUser", id);
			strList = stUserService.getStoresByUser(id);
		} catch (ServiceException e) {
			logger.error("ServiceException: ", e);
			// TODO Auto-generated catch block

		}
		logger.debug("exit UserController:getStoresByUser", id);
		return strList;
	}*/

	public String goToHome() {

		System.out.println("selected store id: " + storeMaster.getId());

		try {
			storeMaster = storeAddressService.getStoreByStoreId(storeMaster.getId());

			System.out.println("selected store name: " + storeMaster.getStoreName());
			System.out.println("selected rest name: " + storeMaster.getRestaurantId());
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("selectedstore", storeMaster);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/page/index.xhtml?faces-redirect=true";
	}

	public String logout() throws IOException {
		/*FacesContext facesContext = FacesContext.getCurrentInstance();
		  HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
		  httpSession.invalidate();
		  System.out.println("Hello ADMIN");*/

		/*ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		System.out.println("Hello ADMIN11111111");*/

		//ec.redirect(ec.getRequestContextPath() + "/page/admin_login.xhtml");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getSessionMap().remove("isSharobi");
		facesContext.getExternalContext().invalidateSession();
		System.out.println("Hello ADMIN2345");
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/page/st_login.xhtml?faces-redirect=true");
		//return "/page/admin_login.xhtml?faces-redirect=true";
		return "";
	}

	public boolean isShowUserPane() {
		System.out.println("pane...:" + showUserPane);
		return showUserPane;
	}

	public void setShowUserPane(boolean showUserPane) {
		this.showUserPane = showUserPane;
	}

	public List<StUser> getUserList() {
		//getUsers();
		return userList;
	}

	public void setUserList(List<StUser> userList) {
		this.userList = userList;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	

	public StUserService getStUserService() {
		return stUserService;
	}

	public void setStUserService(StUserService stUserService) {
		this.stUserService = stUserService;
	}

	public Map getSessMap() {
		return sessMap;
	}

	public void setSessMap(Map sessMap) {
		this.sessMap = sessMap;
	}

	public List<User> getUserLst() {
		return userLst;
	}

	public void setUserLst(List<User> userLst) {
		this.userLst = userLst;
	}

	public List<SelectItem> getSelectCategories() {
		return selectCategories;
	}

	public void setSelectCategories(List<SelectItem> selectCategories) {
		this.selectCategories = selectCategories;
	}

	public StoreMaster getStoreMaster() {
		return storeMaster;
	}

	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}

	public SelectItem getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(SelectItem selectItem) {
		this.selectItem = selectItem;
	}

	public StUser getLoggdUser() {
		return loggdUser;
	}

	public void setLoggdUser(StUser loggdUser) {
		this.loggdUser = loggdUser;
	}

	public StoreAddressService getStoreAddressService() {
		return storeAddressService;
	}

	public void setStoreAddressService(StoreAddressService storeAddressService) {
		this.storeAddressService = storeAddressService;
	}

	public StUser getStUser() {
		return stUser;
	}

	public void setStUser(StUser stUser) {
		this.stUser = stUser;
	}

	/*public String getAllUsers() {
		System.out.println("In getAllUsers");
		userLst = new ArrayList<User>();
		userLst = (ArrayList<User>) sessMap.get("userlist");
		System.out.println("userlist size is............" + userLst.size());
		return "";
	}*/
}

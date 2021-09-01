package com.botree.restaurantapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.model.UserTransaction;
import com.botree.restaurantapp.service.StoreAddressService;
import com.botree.restaurantapp.service.UserService;
import com.botree.restaurantapp.service.exception.ServiceException;

//@ManagedBean(name="user")
//@SessionScoped

public class UserController {
	private final static Logger logger = LogManager.getLogger(UserController.class);

	private boolean showUserPane = false;
	private boolean showErrMsg = false;
	private List<User> userList = new ArrayList<User>();
	private List<Customer> tabletUserList = new ArrayList<Customer>();
	private List<User> userLst;

	

	private Map sessMap = new HashMap();
	private User user;
	private Customer tabletUser;
	

	private User newUser;
	private Customer newtabletUser;
	private String userId;
	private String tabletUserId;
	private String action;
	private String redirect;
	private UserService userService;
	private StoreAddressService storeAddressService;
	private List<StoreMaster> strList = new ArrayList<StoreMaster>();
	private List<StoreMaster> storeList = new ArrayList<StoreMaster>();
	private List<SelectItem> selectCategories = new ArrayList<SelectItem>();
	private List<SelectItem> selectStores = new ArrayList<SelectItem>();
	private StoreMaster storeMaster;
	private SelectItem selectItem;
	private int i = 0;
	private User loggdUser;
	private UserTransaction userTrans = new UserTransaction();
	List<StoreMaster> storeByRestList = null;
	List<SelectItem> storeNameList = null;
	String storeName = null;
	private boolean successUsrCreated = false;
	private boolean failureUsrCreated = false;
	private String changeLang = "N";
	private boolean showSelectlanguage = false;

	public UserController() {
		// TODO Auto-generated constructor stub
		System.out.println("In UserController");

	}

	@PostConstruct
	public void postConstruct() {

		try {
			System.out.println("In get all users");
			System.out.println("current user:  " + user.getId());
			i++;
			System.out.println("i is  :" + i);
			FacesContext context = FacesContext.getCurrentInstance();
			//context.getExternalContext().getSessionMap().put("loggeduser", loggedUser);
			loggdUser = (User) context.getExternalContext().getSessionMap().get("loggeduser");

			userList = userService.getAllUsers();
			System.out.println("number of users:" + userList.size());
			Iterator<User> iterator = userList.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();
				//System.out.println("user name:" + user.getUserName());
			}
			System.out.println("editing user..:" + user);
			System.out.println("test6");
			
			//Tablet User:
			
			tabletUserList = userService.getAllTabletUsers();
			System.out.println("number of tabletUser:" + tabletUserList.size());
			Iterator<Customer> iteratorTabletUser = tabletUserList.iterator();
			while (iteratorTabletUser.hasNext()) {
				Customer tabletUser = (Customer) iteratorTabletUser.next();
				//System.out.println("user name:" + user.getUserName());
			}


			// get stores for logged user
			if (loggdUser != null) {
				strList = getStoresByUser(loggdUser.getId());
				System.out.println("number of stores :" + strList.size());
				populateDropDownList(strList);
			}
			if (context.getExternalContext().getSessionMap().get("showSelectlanguage") != null) {

				boolean showLang = Boolean.parseBoolean(String.valueOf(context.getExternalContext().getSessionMap().get("showSelectlanguage")));
				System.out.println("show lang zdfdfgdgh:: " + showLang);
				if (new Boolean(showLang) != null && showLang == true) {
					System.out.println("show lang :: " + showLang);
					showSelectlanguage = true;
				}
			}

			//getStoreByRestaurantId();

		} catch (Exception e) {
			// throw new DAOException("Check data to be inserted", e);
			e.printStackTrace();
			System.out.println("test8");
		}

		/*
		 * System.out.println("credentials: " + userModel); String goingto = "";
		 * UserModel loggedinUser = null; loggedinUser =
		 * loginService.validateLogin(userModel);
		 * System.out.println("logged in user is: " + loggedinUser); if
		 * (loggedinUser != null) { goingto = "home_page";
		 * System.out.println("valid credentials and logging in"); } else {
		 * goingto = "login_page"; System.out.println("invalid credentials");
		 * errorMessage="Invalid user name / password"; } return goingto;
		 */

	}

	public String dispUser() {
		System.out.println(" in display user");

		// this.showUserPane = true;
		redirect = "disp_user_page";
		System.out.println("going to...");
		System.out.println("user list size:" + userList.size());
		System.out.println("user list:" + userList);
		return redirect;

	}
	
	//Display Tablet User
	public String dispTabletUser() {
		System.out.println(" in display Tablet user");

		redirect = "disp_tablet_user_page";
		System.out.println("going to...");
		System.out.println("Tabletuser list size:" + tabletUserList.size());
		return redirect;

	}

	public void changeLanguage() {

		if (changeLang != null && changeLang.equalsIgnoreCase("N")) {
			showSelectlanguage = false;
		} else if (changeLang != null && changeLang.equalsIgnoreCase("Y")) {
			showSelectlanguage = true;

		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("showSelectlanguage", showSelectlanguage);

	}

	public String editNDeleteUser() {

		String redirect = "";
		System.out.println("In editNDeleteUser");
		System.out.println("user id: " + userId);
		System.out.println("action:" + action);
		try {

			if (action.equalsIgnoreCase("edit")) {
				user = userService.getUserById(userId);
				System.out.println("user:    " + user);
				redirect = "edit_user_page";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/page/edit_user.xhtml?faces-redirect=true";
	}
	
	public String editTabletUser() {

		String redirect = "";
		System.out.println("In editTabletUser");
		System.out.println("user id: " + tabletUserId);
		System.out.println("action:" + action);
		try {

			if (action.equalsIgnoreCase("edit")) {
				tabletUser = userService.getTabletUserById(tabletUserId);
				System.out.println("tabletUser:    " + tabletUser);
				redirect = "edit_user_page";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/page/edit_tabletUser.xhtml?faces-redirect=true";
	}

	public String deleteUser() {
		System.out.println("In deleteUser");

		try {
			System.out.println("user to b deleted is:" + user);
			userService.delete(user);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserController.delete");

		return "/page/disp_user.xhtml?faces-redirect=true";

	}

	public String update() {

		try {

			userService.update(user);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserController.save");

		return "/page/disp_user.xhtml?faces-redirect=true";

	}
	
	public String updateTabletUser() {

		try {

			userService.updateTabletUser(tabletUser);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserController.save");

		return "/page/disp_tablet_user.xhtml?faces-redirect=true";

	}

	public String createUser() {

		String redirect = "";
		try {
			System.out.println("user after creation is:" + newUser);
			System.out.println("rest id: " + newUser.getRestMaster().getId());
			userService.createUser(newUser);
			System.out.println("UserController.createUser");
			redirect = "/page/disp_user.xhtml?faces-redirect=true";
			return redirect;

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if (e.getMessage().equalsIgnoreCase("Integrity Constraint violation")) {
				System.out.println("controller:: createUser: ");
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setDetail("Phone Number already exists.");
				FacesContext.getCurrentInstance().addMessage("userFrm:contact", message);
			}
			return null;
		}

	}

	public String createUserST() {

		String redirect = "";
		try {
			System.out.println("user after creation is:" + newUser);
			System.out.println("store name is:::" + storeName);
			System.out.println("rest id: " + newUser.getRestMaster().getId());
			userService.createUserST(newUser, userTrans);
			System.out.println("UserController.createUser");
			successUsrCreated = true;
			//redirect = "/page/st_create_user.xhtml?faces-redirect=true";
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Created"));
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().remove("storeNameList");
			return redirect;

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failureUsrCreated = true;

			return null;
		}

	}
	
	public String createTabletUserST() {

		String redirect = "";
		try {
			System.out.println("tabletuser after creation is:" + newtabletUser);
			//System.out.println("store name is:::" + storeName);
			//System.out.println("rest id: " + newUser.getRestMaster().getId());
			userService.createTabletUserST(newtabletUser);
			//System.out.println("UserController.createUser");
			successUsrCreated = true;
			//redirect = "/page/st_create_user.xhtml?faces-redirect=true";
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Created"));
			
			//FacesContext context = FacesContext.getCurrentInstance();
			//context.getExternalContext().getSessionMap().remove("storeNameList");
			return redirect;

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failureUsrCreated = true;

			return null;
		}
		
	}

	public String addUser() {

		return "/page/create_user.xhtml?redirect=true";

	}

	public String login() {
		i++;
		System.out.println("value of i " + i);
		User loggedUser = null;
		String redirect = "";
		logger.debug("log in user: [{}]", user);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			loggedUser = userService.login(user);
			System.out.println("logged user: " + loggedUser.getId());

			ExternalContext externalContext = context.getExternalContext();

			/*//code to remove session
			HttpSession session = (HttpSession) externalContext.getSession(false);
			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

			// remove cookies
			Cookie[] cookies = ((HttpServletRequest) externalContext.getRequest()).getCookies();

			if (cookies != null)
				for (int i = 0; i < cookies.length; i++) {
					cookies[i].setValue("");
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			
			// check what we have in sessionMap
			System.out.println(externalContext.getSessionMap());
			
			// invalidate session
			if (session != null) {
				System.out.println("invalidating session");
				session.invalidate();
			}
			// see what is there in session map
			System.out.println(externalContext.getSessionMap());*/
			externalContext.getSessionMap().put("loggeduser", loggedUser);

		} catch (ServiceException e) {
			logger.error("Exception: ", e);
		}
		if (loggedUser != null) {
			logger.debug("user id: ", loggedUser.getId());
			//set the locale
			//FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.FRENCH);
			//get the curretn locale
			Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			System.out.println("locale::: " + locale.toString());

			//set css id as per restaurant id
			int restId = loggedUser.getRestMaster().getId();
			context.getExternalContext().getSessionMap().put("cssId", restId);

			/*// get stores for logged user
			strList = getStoresByUser(loggedUser.getId());
			System.out.println("number of stores :" + strList.size());
			populateDropDownList(strList);*/
			redirect = "/page/select_store.xhtml?faces-redirect=true";
		} else {
			logger.error("invalid login");
			showErrMsg = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(""));
		}
		return redirect;
	}

	public void populateDropDownList(List<StoreMaster> storeList) {
		try {

			System.out.println("Enter populateDropDownList");
			System.out.println("select stores size:" + storeList.size());
			//catList = menuService.getAllItemTypes();

			//System.out.println("number of categoreies:" + catList.size());
			Iterator<StoreMaster> iterator = storeList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				StoreMaster store = (StoreMaster) iterator.next();

				si.setValue(store.getId());
				System.out.println("store id" + store.getId());
				si.setLabel(store.getStoreName());
				System.out.println("store name: " + store.getStoreName());

				selectCategories.add(si);
				System.out.println("Exit populateDropDownList");

			}
		} catch (Exception e) {
			// throw new DAOException("Check data to be inserted", e);
			e.printStackTrace();
			//System.out.println("test8");
		}

	}

	public List<StoreMaster> getStoresByUser(int id) {

		try {
			logger.debug("Enter UserController:getStoresByUser", id);
			strList = userService.getStoresByUser(id);
		} catch (ServiceException e) {
			logger.error("ServiceException: ", e);
			// TODO Auto-generated catch block

		}
		logger.debug("exit UserController:getStoresByUser", id);
		return strList;
	}

	public void getStoreByRestaurantId() {
		FacesContext context = FacesContext.getCurrentInstance();
		//context.getExternalContext().getSessionMap().put("loggeduser", loggedUser);

		try {
			if (newUser != null) {
				int restId = newUser.getRestMaster().getId();
				logger.debug("Enter UserController:getStoreByRestaurantId");
				storeList = userService.getStoreByRestaurantId(restId);
				if (storeList != null) {
					storeNameList = new ArrayList<SelectItem>();
					Iterator<StoreMaster> iterator = storeList.iterator();
					while (iterator.hasNext()) {
						StoreMaster storeMaster = (StoreMaster) iterator.next();
						String name = storeMaster.getStoreName();
						storeNameList.add(new SelectItem(name, name));
					}
					context.getExternalContext().getSessionMap().put("storeNameList", storeList);
					logger.debug("exit UserController:getStoreByRestaurantId");
					Iterator<StoreMaster> iterator1 = storeList.iterator();
					while (iterator1.hasNext()) {
						SelectItem si = new SelectItem();

						StoreMaster store = (StoreMaster) iterator1.next();

						si.setValue(store.getId());
						System.out.println("store id" + store.getId());
						si.setLabel(store.getStoreName());
						System.out.println("store name: " + store.getStoreName());

						selectStores.add(si);

					}
				}
			}
		} catch (ServiceException e) {
			logger.error("ServiceException: ", e);
			// TODO Auto-generated catch block

		}

	}
	
	public void getStoreByRestaurantIdForTablet() {
		FacesContext context = FacesContext.getCurrentInstance();
		//context.getExternalContext().getSessionMap().put("loggeduser", loggedUser);

		try {
			if (newtabletUser != null) {
				int restId = newtabletUser.getRestaurant1().getId();
				logger.debug("Enter getStoreByRestaurantIdForTablet");
				storeList = userService.getStoreByRestaurantId(restId);
				if (storeList != null) {
					storeNameList = new ArrayList<SelectItem>();
					Iterator<StoreMaster> iterator = storeList.iterator();
					while (iterator.hasNext()) {
						StoreMaster storeMaster = (StoreMaster) iterator.next();
						String name = storeMaster.getStoreName();
						storeNameList.add(new SelectItem(name, name));
					}
					context.getExternalContext().getSessionMap().put("storeNameList", storeList);
					logger.debug("exit UserController:getStoreByRestaurantId");
					Iterator<StoreMaster> iterator1 = storeList.iterator();
					while (iterator1.hasNext()) {
						SelectItem si = new SelectItem();

						StoreMaster store = (StoreMaster) iterator1.next();

						si.setValue(store.getId());
						System.out.println("store id" + store.getId());
						si.setLabel(store.getStoreName());
						System.out.println("store name: " + store.getStoreName());

						selectStores.add(si);

					}
				}
			}
		} catch (ServiceException e) {
			logger.error("ServiceException: ", e);
			// TODO Auto-generated catch block

		}

	}

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
		FacesContext context = FacesContext.getCurrentInstance();
		Object cssId = context.getExternalContext().getSessionMap().get("cssId");
		if (cssId != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cssId");
		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		System.out.println("Hello ADMIN2345");
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/page/admin_login.xhtml?faces-redirect=true");
		//return "/page/admin_login.xhtml?faces-redirect=true";
		return "";
	}

	public String logoutST() throws IOException {
		/*FacesContext facesContext = FacesContext.getCurrentInstance();
		  HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
		  httpSession.invalidate();
		  System.out.println("Hello ADMIN");*/

		/*ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		System.out.println("Hello ADMIN11111111");*/

		//ec.redirect(ec.getRequestContextPath() + "/page/admin_login.xhtml");

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		System.out.println("Hello logoutST");
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/page/st_login.xhtml?faces-redirect=true");
		//return "/page/admin_login.xhtml?faces-redirect=true";
		return "";
	}

	public void checkMobile(FacesContext context,
							UIComponent component,
							Object value) {

		String contact = (String) value;
		System.out.println("contact:::  " + contact);
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		//message.setSummary("Duplicate Phone Number.");
		message.setDetail("Duplicate Phone Number");
		context.addMessage("userFrm:contact", message);

	}

	public String stCreateUser() {

		return "/page/st_create_user.xhtml?faces-redirect=true";

	}
	
	public String stCreateTabletUser() {

		return "/page/st_create_tabletuser.xhtml?faces-redirect=true";

	}

	public boolean isShowUserPane() {
		System.out.println("pane...:" + showUserPane);
		return showUserPane;
	}

	public void setShowUserPane(boolean showUserPane) {
		this.showUserPane = showUserPane;
	}

	public List<User> getUserList() {
		//getUsers();
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

	public User getLoggdUser() {
		return loggdUser;
	}

	public void setLoggdUser(User loggdUser) {
		this.loggdUser = loggdUser;
	}

	public StoreAddressService getStoreAddressService() {
		return storeAddressService;
	}

	public void setStoreAddressService(StoreAddressService storeAddressService) {
		this.storeAddressService = storeAddressService;
	}

	public String getAllUsers() {
		System.out.println("In getAllUsers");
		userLst = new ArrayList<User>();
		userLst = (ArrayList<User>) sessMap.get("userlist");
		System.out.println("userlist size is............" + userLst.size());
		return "";
	}

	public List<SelectItem> getSelectStores() {
		return selectStores;
	}

	public void setSelectStores(List<SelectItem> selectStores) {
		this.selectStores = selectStores;
	}

	public UserTransaction getUserTrans() {
		return userTrans;
	}

	public void setUserTrans(UserTransaction userTrans) {
		this.userTrans = userTrans;
	}

	public boolean isShowErrMsg() {
		return showErrMsg;
	}

	public void setShowErrMsg(boolean showErrMsg) {
		this.showErrMsg = showErrMsg;
	}

	public List<StoreMaster> getStoreList() {

		FacesContext context = FacesContext.getCurrentInstance();
		storeList = (List<StoreMaster>) context.getExternalContext().getSessionMap().get("storeNameList");
		System.out.println("store name list is null or size 0.." + storeList);

		/*if (storeList != null)
			System.out.println("store name list size:: " + storeList.size());
		else if (storeList == null || storeList.size() == 0) {
			System.out.println("store name list is null..");
			FacesContext context = FacesContext.getCurrentInstance();
			storeList = (List<StoreMaster>) context.getExternalContext().getSessionMap().get("storeNameList");
			System.out.println("store name list is null or size 0.." + storeList);
		}*/
		return storeList;
	}

	public void setStoreList(List<StoreMaster> storeList) {
		this.storeList = storeList;
	}

	public List<StoreMaster> getStoreByRestList() {
		return storeByRestList;
	}

	public void setStoreByRestList(List<StoreMaster> storeByRestList) {
		this.storeByRestList = storeByRestList;
	}

	public List<SelectItem> getStoreNameList() {
		//getStoreByRestaurantId();
		/*if (storeNameList != null)
			System.out.println("store name list size:: " + storeNameList.size());
		else if (storeNameList == null || storeNameList.size() == 0) {
			System.out.println("store name list is null..");
			FacesContext context = FacesContext.getCurrentInstance();
			storeNameList = (List<SelectItem>) context.getExternalContext().getSessionMap().get("storeNameList");
			System.out.println("store name list is null or size 0.." + storeNameList);
		}*/
		return storeNameList;
	}

	public void setStoreNameList(List<SelectItem> storeNameList) {
		this.storeNameList = storeNameList;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public boolean isSuccessUsrCreated() {
		return successUsrCreated;
	}

	public void setSuccessUsrCreated(boolean successUsrCreated) {
		this.successUsrCreated = successUsrCreated;
	}

	public boolean isFailureUsrCreated() {
		return failureUsrCreated;
	}

	public void setFailureUsrCreated(boolean failureUsrCreated) {
		this.failureUsrCreated = failureUsrCreated;
	}

	public String getChangeLang() {
		return changeLang;
	}

	public void setChangeLang(String changeLang) {
		this.changeLang = changeLang;
	}

	public boolean isShowSelectlanguage() {
		return showSelectlanguage;
	}

	public void setShowSelectlanguage(boolean showSelectlanguage) {
		this.showSelectlanguage = showSelectlanguage;
	}

	public List<StoreMaster> getStores(RestaurantMaster rest) {

		try {
			int restId = rest.getId();
			logger.debug("Enter UserController:getStores");
			storeByRestList = userService.getStoreByRestaurantId(restId);
		} catch (ServiceException e) {
			logger.error("ServiceException: ", e);
			// TODO Auto-generated catch block

		}

		return storeByRestList;
	}

	public void handleRestSelect(ValueChangeEvent event) {
		RestaurantMaster rest = (RestaurantMaster) event.getNewValue();
		getStores(rest);
	}
	
	public List<Customer> getTabletUserList() {
		return tabletUserList;
	}

	public void setTabletUserList(List<Customer> tabletUserList) {
		this.tabletUserList = tabletUserList;
	}
	
	public Customer getTabletUser() {
		return tabletUser;
	}

	public void setTabletUser(Customer tabletUser) {
		this.tabletUser = tabletUser;
	}

	public Customer getNewtabletUser() {
		return newtabletUser;
	}

	public void setNewtabletUser(Customer newtabletUser) {
		this.newtabletUser = newtabletUser;
	}

	public String getTabletUserId() {
		return tabletUserId;
	}

	public void setTabletUserId(String tabletUserId) {
		this.tabletUserId = tabletUserId;
	}
	
	

}

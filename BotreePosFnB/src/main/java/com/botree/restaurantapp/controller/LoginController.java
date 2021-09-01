package com.botree.restaurantapp.controller;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.dao.CustomerDAO;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.service.CustomerService;
import com.botree.restaurantapp.service.LoginService;
import com.botree.restaurantapp.service.UserService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class LoginController {
	private final static Logger logger = LogManager.getLogger(LoginController.class);

	private String actionBtn;
	private LoginService loginService;
	private String errorMessage;
	private Customer customer;
	private CustomerDAO custDAO;
	private CustomerService customerService;
	private String changeLang = "N";
	private boolean showSelectlanguage = false;
	private User user = new User();
	private UserService userService;
	private boolean showErrMsg = false;
	@ManagedProperty(value = "#{param.pageId}")
	private String pageId;

	@PostConstruct
	public void postConstruct() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getExternalContext().getSessionMap().get("showSelectlanguage") != null) {

			boolean showLang = Boolean.parseBoolean(String.valueOf(context.getExternalContext().getSessionMap().get("showSelectlanguage")));
			System.out.println("show lang zdfdfgdgh:: " + showLang);
			if (new Boolean(showLang) != null && showLang == true) {
				System.out.println("show lang :: " + showLang);
				showSelectlanguage = true;
			}
		}

	}

	public String login() {
		System.out.println("action name:: " + actionBtn);
		System.out.println("action name:: " + pageId);
		User loggedUser = null;
		String redirect = "";
		logger.debug("log in user: [{}]", user);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			loggedUser = userService.login(user);
			System.out.println("logged user: " + loggedUser.getId());

			ExternalContext externalContext = context.getExternalContext();

			/*
			 * //code to remove session HttpSession session = (HttpSession)
			 * externalContext.getSession(false); HttpServletResponse response =
			 * (HttpServletResponse) externalContext.getResponse();
			 * 
			 * // remove cookies Cookie[] cookies = ((HttpServletRequest)
			 * externalContext.getRequest()).getCookies();
			 * 
			 * if (cookies != null) for (int i = 0; i < cookies.length; i++) {
			 * cookies[i].setValue(""); cookies[i].setPath("/");
			 * cookies[i].setMaxAge(0); response.addCookie(cookies[i]); }
			 * 
			 * // check what we have in sessionMap
			 * System.out.println(externalContext.getSessionMap());
			 * 
			 * // invalidate session if (session != null) {
			 * System.out.println("invalidating session"); session.invalidate();
			 * } // see what is there in session map
			 * System.out.println(externalContext.getSessionMap());
			 */
			externalContext.getSessionMap().put("loggeduser", loggedUser);

		} catch (ServiceException e) {
			logger.error("Exception: ", e);
		}
		if (loggedUser != null) {
			logger.debug("user id: ", loggedUser.getId());
			// set the locale
			// FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.FRENCH);
			// get the curretn locale
			Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			System.out.println("locale::: " + locale.toString());

			// set css id as per restaurant id
			int restId = loggedUser.getRestMaster().getId();
			context.getExternalContext().getSessionMap().put("cssId", restId);

			/*
			 * // get stores for logged user strList =
			 * getStoresByUser(loggedUser.getId());
			 * System.out.println("number of stores :" + strList.size());
			 * populateDropDownList(strList);
			 */
			redirect = "/page/select_store.xhtml?faces-redirect=true";
		} else {
			logger.error("invalid login");
			showErrMsg = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(""));
		}
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

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerDAO getCustDAO() {
		return custDAO;
	}

	public void setCustDAO(CustomerDAO custDAO) {
		this.custDAO = custDAO;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public boolean isShowErrMsg() {
		return showErrMsg;
	}

	public void setShowErrMsg(boolean showErrMsg) {
		this.showErrMsg = showErrMsg;
	}

	public String getActionBtn() {
		return actionBtn;
	}

	public void setActionBtn(String actionBtn) {
		this.actionBtn = actionBtn;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	
}

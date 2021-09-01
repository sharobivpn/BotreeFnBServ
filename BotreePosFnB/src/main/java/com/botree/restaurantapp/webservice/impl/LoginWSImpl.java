package com.botree.restaurantapp.webservice.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.PosModules;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.service.LoginService;
import com.botree.restaurantapp.service.UserService;
import com.botree.restaurantapp.service.exception.ServiceException;
import com.botree.restaurantapp.webservice.LoginWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/login")
public class LoginWSImpl implements LoginWS {
	private final static Logger logger = LogManager.getLogger(LoginWSImpl.class);

  @Autowired
	private LoginService loginService;

	@Override
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String authenticateUser(@RequestBody Customer customerParam) {
		Customer customerResult = null;
		try {
			//System.out.println("Enter LoginWSImpl: authenticateUser Contact No. :"+ customerParam.getContactNo() + "  " + new Date());
			logger.debug("Enter LoginWSImpl: authenticateUser: {} ",
					customerParam.getContactNo());
			customerResult = loginService.getUserByCredential(customerParam);
		} catch (ServiceException e) {
			e.printStackTrace();
			//logger.error("error in authenticateUser:"+e);
		}
		/*
		 * if (customerResult == null) { customerResult = new Customer();
		 * customerResult.setId(0); }
		 */
		//System.out.println("Exit LoginWSImpl: authenticateUser Contact No. :"+ customerParam.getContactNo() + new Date());
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create().toJson(customerResult);
	}

	@Override
	@RequestMapping(value = "/getAllWaiters",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllWaiters(@RequestParam(value = "id") String id) {
		List<Customer> waiters = null;
		try {
			waiters = loginService.getAllWaiters(id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {
		}.getType();
		String json = gson.toJson(waiters, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getPosModulesByUserId",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getPosModulesByUserId(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "storeId") String storeId) {
		List<PosModules> posModules = null;
		try {
			posModules = loginService.getPosModulesByUserId(userId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<PosModules>>() {
		}.getType();
		String json = gson.toJson(posModules, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getReportByStore",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getReportByStore(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "storeId") String storeId) {
		List<PosModules> posModules = null;
		try {
			posModules = loginService.getReportByStore(userId, storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<PosModules>>() {
		}.getType();
		String json = gson.toJson(posModules, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/loginAdminPOS", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String loginAdminPOS(@RequestBody User user) { //
		//System.out.println("logged user: " + user.getUserName() + "password: "+user.getPassword());
		User loggedUser = null;
		try {
			UserService userService = new UserService();
			loggedUser = userService.loginToCheckAdmin(user);

			if (loggedUser != null
					&& loggedUser.getStoreId() == user.getStoreId()) {
				
				return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
						.create().toJson(loggedUser);

			}

		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return "failure";
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}

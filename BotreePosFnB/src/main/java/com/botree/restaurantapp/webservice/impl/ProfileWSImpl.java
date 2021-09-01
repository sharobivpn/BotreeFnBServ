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
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.StatusMessage;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.service.CustomerService;
import com.botree.restaurantapp.service.exception.ServiceException;
import com.botree.restaurantapp.webservice.ProfileWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Controller
@ResponseBody
@RequestMapping(value = "/profile")
public class ProfileWSImpl implements ProfileWS {

  @Autowired
	private CustomerService customerService;

	@Override
	@RequestMapping(value = "/get",
	method = RequestMethod.POST,
	consumes = "application/json",
	produces = "application/json")
	public Customer getUserProfile(@RequestBody Customer user) {
		Customer customer = null;
		try {
			customer = customerService.getCustomerByPhNmbr(user.getContactNo());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public StatusMessage updateUserProfile(@RequestBody Customer user) {
		StatusMessage statusMessage = null;
		statusMessage = new StatusMessage();
		try {
			customerService.updateCustomer(user);
		} catch (ServiceException e) {
			e.printStackTrace();
			statusMessage.setMessage(StatusMessage.FAILURE);
		}
		statusMessage.setMessage(StatusMessage.SUCCESS);
		return statusMessage;
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public StatusMessage createUserProfile(@RequestBody Customer customer) {
		StatusMessage statusMessage = null;
		statusMessage = new StatusMessage();
		try {
			customerService.createCustomer(customer);
			statusMessage.setMessage(StatusMessage.SUCCESS);
		} catch (ServiceException e) {
			e.printStackTrace();
			statusMessage.setMessage(StatusMessage.FAILURE);
		}

		return statusMessage;
	}

	@Override
	@RequestMapping(value = "/getCustomerByStore", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerByStore(@RequestParam(value = "storeId") String id) {
		List<StoreCustomer> customerList = null;
		try {
			customerList = customerService.getCustomerByStore(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<StoreCustomer>>() {
		}.getType();
		String json = gson.toJson(customerList, type);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getCreditCustomerByStore", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCreditCustomerByStore(
			@RequestParam(value = "storeId") String id) {
		List<StoreCustomer> customerList = null;
		try {
			customerList = customerService.getCreditCustomerByStore(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<StoreCustomer>>() {
		}.getType();
		String json = gson.toJson(customerList, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/getCustomerById", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerById(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		StoreCustomer customer = null;
		try {
			customer = customerService.getCustomerById(id, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(customer, StoreCustomer.class);
		// System.out.println(json);

		return json;
	}
	
	
	
	@Override
	@RequestMapping(value = "/createDataDump", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getDataDump(@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "user") String user,
			@RequestParam(value = "pwd") String pwd) {
		String status = null;
		try {
			status = customerService.getDataDump(storeId, user, pwd);

		} catch (ServiceException e) {
			status = "failure";
			e.printStackTrace();
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addCustomer(@RequestBody StoreCustomer customer) {
		String status = "";
		try {
			status = customerService.addCustomer(customer);

		} catch (Exception x) {
			status = "failure";
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateCustomer(@RequestBody StoreCustomer customer) {
		String status = "";
		try {
			status = customerService.updateCustomer(customer);

		} catch (Exception x) {
			status = "failure";
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String deleteCustomer(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		String status = "";
		try {
			status = customerService.deleteCustomer(id, storeId);

		} catch (Exception x) {
			status = "failure";
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String changePassword(@RequestBody Customer customer) {
		String status = "";
		try {
			int id = customer.getId();
			int storeId = customer.getStoreId();
			String oldPassword = customer.getPassword();
			String newPassword = customer.getNewPasword();
			status = customerService.changePassword("" + id, "" + storeId,
					oldPassword, newPassword);

		} catch (Exception x) {
			status = "failure";
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/changeAdminPassword", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String changeAdminPassword(@RequestBody User user) {
		String status = "";
		try {
			String userId = user.getUserId();
			int storeId = user.getStoreId();
			String oldPassword = user.getPassword();
			String newPassword = user.getNewPassword();
			status = customerService.changeAdminPassword(userId, "" + storeId,
					oldPassword, newPassword);

		} catch (Exception x) {
			status = "failure";
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/getCustomerByContact", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerByContact(
			@RequestParam(value = "contact") String contact,
			@RequestParam(value = "storeId") String storeId) {
		CustomerInfo customer = null;
		try {
			customer = customerService.getCustomerByContact(contact, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(customer, CustomerInfo.class);
		// System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getAllCustomerPhoneNo", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllCustomerPhoneNo(
			@RequestParam(value = "storeId") String storeId) {
		List<String> contactLst = null;
		try {
			contactLst = customerService.getAllCustomerPhoneNo(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<String>>() {
		}.getType();
		String json = gson.toJson(contactLst, type);
		// System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getAllCustomerLocation", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllCustomerLocation(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "location") String location) {
		List<Customer> contactLst = null;
		try {
			contactLst = customerService.getAllCustomerLocation(storeId,location);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {
		}.getType();
		String json = gson.toJson(contactLst, type);
		// System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getCustomerDetails", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerDetails(
			@RequestParam(value = "storeId") String storeId) {
		List<Customer> custList = null;
		try {
			custList = customerService.getCustomerDetails(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {
		}.getType();
		String json = gson.toJson(custList, type);
		// System.out.println(json);

		return json;
	}

	@Override
	@RequestMapping(value = "/getAllCustomerByName", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllCustomerByName(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "name") String name) {
		List<Customer> custList = null;
		try {
			custList = customerService.getAllCustomerByName(storeId, name);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {
		}.getType();
		String json = gson.toJson(custList, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/getAllCustomerByPhone", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getAllCustomerByPhone(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "phone") String phone) {
		List<Customer> custList = null;
		try {
			custList = customerService.getAllCustomerByPhone(storeId, phone);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {
		}.getType();
		String json = gson.toJson(custList, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/authenticateOnlineCustomer", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String authenticateOnlineCustomer(@RequestBody StoreCustomer customerParam) {
		StoreCustomer customerResult = null;
		try {

			customerResult = customerService
					.authenticateOnlineCustomer(customerParam);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create().toJson(customerResult);
	}

	@Override
	@RequestMapping(value = "/registerOnlineCustomer", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String registerOnlineCustomer(@RequestBody StoreCustomer customerParam) {
		String status = "";
		try {

			status = customerService.registerOnlineCustomer(customerParam);
		} catch (ServiceException e) {
			status = "failure";
			e.printStackTrace();
		}

		return status;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

}

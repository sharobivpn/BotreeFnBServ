package com.botree.restaurantapp.webservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.BillSplitER;
import com.botree.restaurantapp.model.BillSplitER_duplicate;
import com.botree.restaurantapp.model.BillSplitManual;
import com.botree.restaurantapp.model.BillSplitManual_duplicate;
import com.botree.restaurantapp.model.CardPayment;
import com.botree.restaurantapp.model.Discount;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.service.BillingService;
import com.botree.restaurantapp.service.exception.ServiceException;
import com.botree.restaurantapp.webservice.BillingWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/bill")
public class BillingWSImpl implements BillingWS {
	
	//private final static Logger logger = LogManager.getLogger(BillingWSImpl.class);

	@Autowired
	private BillingService billingService;

	public BillingService getBillingService() {
		return billingService;
	}

	public void setBillingService(BillingService billingService) {
		this.billingService = billingService;
	}

	@Override
	@RequestMapping(value = "/billSplitER", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String billSplitER(@RequestBody BillSplitER_duplicate billList) {

		String status = "";
		try {
			status = billingService.billSplitER(billList);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/billSplitManual", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String billSplitManual(@RequestBody BillSplitManual_duplicate billList) {

		String status = "";
		try {
			status = billingService.billSplitManual(billList);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/payment", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String payment(@RequestBody Payment payment) {

 		String status = "";
		try {
			status = billingService.payment(payment);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}
	
	@Override
	@RequestMapping(value = "/paymentAdvOrder", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String paymentAdvOrder(@RequestBody Payment payment) {

 		String status = "";
		try {
			status = billingService.paymentAdvOrder(payment);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}

	@Override
	@RequestMapping(value = "/getPaymentInfoByOrderId", method = RequestMethod.GET, produces = "text/plain")
	public String getPaymentInfoByOrderId(@RequestParam(value = "orderId") String orderId) {
		List<Payment> payList = null;
		try {
			payList = billingService.getPaymentInfoByOrderId(orderId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<Payment>>() {}.getType();
		String json = gson.toJson(payList, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getBillByOrderId", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "text/plain")
	public String getBillByOrderId(@RequestParam(value = "orderId") String orderId) {
		Bill bill = null;
		try {
			bill = billingService.getBillByOrderId(orderId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = gson.toJson(bill, Bill.class);
		//System.out.println(json);

		return json;
	}
	
	@Override
	@RequestMapping(value = "/getBillSplitERByOrderId",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getBillSplitERByOrderId(@RequestParam(value = "orderId") String orderId) {
		List<BillSplitER> billSplitERList = null;
		try {
			billSplitERList = billingService.getBillSplitERByOrderId(orderId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<BillSplitER>>() {}.getType();
		String json = gson.toJson(billSplitERList, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getBillSplitManualByOrderId",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getBillSplitManualByOrderId(@RequestParam(value = "orderId") String orderId) {
		List<BillSplitManual> billSplitManualList = null;
		try {
			billSplitManualList = billingService.getBillSplitManualByOrderId(orderId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<BillSplitManual>>() {}.getType();
		String json = gson.toJson(billSplitManualList, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getBillSplitManualOrderId",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getBillSplitManualOrderId(@RequestParam(value = "orderId") String orderId) {
		List<BillSplitManual> billSplitManualList = null;
		try {
			billSplitManualList = billingService.getBillSplitManualOrderId(orderId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<BillSplitManual>>() {}.getType();
		String json = gson.toJson(billSplitManualList, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/addDiscount", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addDiscount(@RequestBody Discount discount) {

		String status = "";
		try {
			status = billingService.addDiscount(discount);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}
	
	@Override
	@RequestMapping(value = "/addCardPayment", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String addCardPayment(@RequestBody CardPayment cardPayment) {

		String status = "";
		try {
			status = billingService.addCardPayment(cardPayment);

		} catch (Exception x) {
			status="failure";
			x.printStackTrace();

		}
		return status;

	}
	
	@Override
	@RequestMapping(value = "/updateCardPayment", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateCardPayment(@RequestBody CardPayment cardPayment) {

		String status = "";
		try {
			status = billingService.updateCardPayment(cardPayment);

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}
	

	@Override
	@RequestMapping(value = "/updateBillServiceCharge", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String updateBillServiceCharge(
			@RequestParam(value = "orderId") String orderid,
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "sChargeRate") String sChargeRate) {

		String status = "";
		try {
			status = billingService.updateBillServiceCharge(orderid, storeId,
					sChargeRate);

		} catch (Exception x) {
			x.printStackTrace();
			status = "Failure";
		}
		return status;
	}


}

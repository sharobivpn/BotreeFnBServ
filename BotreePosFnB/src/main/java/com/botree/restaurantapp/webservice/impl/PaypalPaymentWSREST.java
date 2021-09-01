package com.botree.restaurantapp.webservice.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.model.dto.PaypalPaymentWSRESTdoECReqDto;
import com.botree.restaurantapp.model.dto.PaypalPaymentWSRESTdoECResDto;
import com.botree.restaurantapp.model.dto.PaypalPaymentWSRESTsetECReqDto;
import com.botree.restaurantapp.model.dto.PaypalPaymentWSRESTsetECResDto;
import com.botree.restaurantapp.model.dto.SetECFormBean;
import com.botree.restaurantapp.service.PaymentGatewayService;
import com.google.gson.Gson;

@Controller
@ResponseBody
@RequestMapping(value = "/paypalPaymentWSREST")
public class PaypalPaymentWSREST {
	private final static Logger logger = LogManager.getLogger(PaypalPaymentWSREST.class);
	
  @Autowired
	private PaymentGatewayService paymentGatewayService;
  
	private static Gson gson = new Gson();

	@RequestMapping(value = "/doSetExpressCheckoutWS",
	method = RequestMethod.POST,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public String doSetExpressCheckoutWS(@RequestBody String param) {
		logger.info("Req param: " + param);
		PaypalPaymentWSRESTsetECReqDto paramJson = gson.fromJson(param, PaypalPaymentWSRESTsetECReqDto.class);

		logger.debug("Calling 'doSetExpressCheckoutWS(String itemDescription, String currency, double totalAmount, String redirectUrl)' webservice method.");
		SetECFormBean setECFormBean = new SetECFormBean();
		setECFormBean.setItemDescription(paramJson.getItemDescription());
		setECFormBean.setCurrency(paramJson.getCurrency());
		setECFormBean.setTotalAmount(paramJson.getTotalAmount());

		String response = paymentGatewayService.doSetExpressCheckout(setECFormBean, paramJson.getRedirectUrl());
		String responseJson = gson.toJson(new PaypalPaymentWSRESTsetECResDto(response));

		logger.debug("Response JSON: {}", responseJson);
		return responseJson;
	}

	@RequestMapping(value = "/doDoExpressCheckoutWS",
	method = RequestMethod.GET,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public String doDoExpressCheckoutWS(@RequestParam("param") String param) {
		PaypalPaymentWSRESTdoECReqDto request = gson.fromJson(param, PaypalPaymentWSRESTdoECReqDto.class);
		logger.debug("Calling 'doSetExpressCheckoutWS(String token, String prayerId, String totalAmount)' webservice method.");
		return gson.toJson(new PaypalPaymentWSRESTdoECResDto(paymentGatewayService.doDoExpressCheckout(request.getToken(), request.getPrayerId(), request.getTotalAmount())));
	}
}

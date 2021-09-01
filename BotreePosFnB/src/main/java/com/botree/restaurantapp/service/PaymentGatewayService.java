package com.botree.restaurantapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.commonUtil.NumberFormatter;
import com.botree.restaurantapp.model.dto.SetECFormBean;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;

@Service
public class PaymentGatewayService {
	private final static Logger logger = LogManager.getLogger(PaymentGatewayService.class);

	// Creates a configuration map containing credentials and other required
	// configuration parameters.
	public static final Map<String, String> getAcctAndConfig() {

		Map<String, String> configMap = new HashMap<String, String>();
		configMap.putAll(getConfig());

		configMap.put("acct1.UserName", "sourav.biswas-facilitator_api1.sharobitech.com");
		configMap.put("acct1.Password", "1395319654");
		configMap.put("acct1.Signature", "AFcWxV21C7fd0v3bYYYRCpSSRl31A53Vm-ftBr5H-Skz8NdmSPBcX4aM");

		return configMap;
	}

	public String doSetExpressCheckout(	SetECFormBean bean,
										String redirectUrl) {

		APIContext apiContext = null;

		try {
			apiContext = new APIContext(getAccessToken());

			Details details = new Details();
			details.setShipping("0");
			details.setSubtotal(NumberFormatter.getFormattedDecimal(bean.getTotalAmount()));
			details.setTax("0");

			/* Adding total amount */
			Amount amountPP = new Amount();
			amountPP.setCurrency(bean.getCurrency());
			amountPP.setTotal(NumberFormatter.getFormattedDecimal(bean.getTotalAmount()));
			amountPP.setDetails(details);

			/* Adding item description */
			Transaction transaction = new Transaction();
			transaction.setAmount(amountPP);
			transaction.setDescription(bean.getItemDescription() + ": " + bean.getTotalAmount());

			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);

			// ###Redirect URLs
			RedirectUrls redirectUrls = new RedirectUrls();
			String guid = UUID.randomUUID().toString().replaceAll("-", "");
			/* Adding redirect urls. */
			StringBuilder url = new StringBuilder(redirectUrl);
			url.append("?guid=" + guid);

			redirectUrls.setCancelUrl(url.toString().concat("&resp=can"));
			redirectUrls.setReturnUrl(url.toString().concat("&resp=succ"));
			payment.setRedirectUrls(redirectUrls);

			Payment createdPayment = payment.create(apiContext);
			String response = Payment.getLastResponse();
			logger.debug(response);

			List<Links> links = createdPayment.getLinks();
			Iterator<Links> linksItr = links.iterator();
			Links tmpLinks = null;
			String redirectLink = null;
			while (linksItr.hasNext()) {
				tmpLinks = linksItr.next();
				if (tmpLinks.getRel().equalsIgnoreCase("approval_url")) {
					redirectLink = tmpLinks.getHref();
				}
			}

			return redirectLink;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Confirm/Finalize   
	 * */
	public boolean doDoExpressCheckout(	String token,
										String prayerId,
										String totalAmount) {

		Map<String, String> configurationMap = getAcctAndConfig();
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);

		DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
		DoExpressCheckoutPaymentRequestDetailsType details = new DoExpressCheckoutPaymentRequestDetailsType();

		details.setToken(token);
		details.setPayerID(prayerId);

		details.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));

		PaymentDetailsType paymentDetails = new PaymentDetailsType();

		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setValue(totalAmount);
		orderTotal.setCurrencyID(CurrencyCodeType.fromValue("USD"));
		paymentDetails.setOrderTotal(orderTotal);

		List<PaymentDetailsType> payDetailType = new ArrayList<PaymentDetailsType>();
		payDetailType.add(paymentDetails);

		details.setPaymentDetails(payDetailType);

		doCheckoutPaymentRequestType.setDoExpressCheckoutPaymentRequestDetails(details);
		DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
		doExpressCheckoutPaymentReq.setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);

		DoExpressCheckoutPaymentResponseType doCheckoutPaymentResponseType;
		String acknowledgement = null;
		try {
			doCheckoutPaymentResponseType = service.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
			acknowledgement = doCheckoutPaymentResponseType.getAck().getValue();
			List<ErrorType> errors = doCheckoutPaymentResponseType.getErrors();
			Iterator<ErrorType> errorIterator = errors.iterator();

			while (errorIterator.hasNext()) {
				logger.error(errorIterator.next().getLongMessage());
			}

			logger.info("Transaction Status - ACK: {} , Crrelation Id: {}", doCheckoutPaymentResponseType.getAck(), doCheckoutPaymentResponseType.getCorrelationID());

			return acknowledgement.equalsIgnoreCase("Success") ? true : false;
		} catch (Exception e) {
			logger.error("Failed: ", e);
		}
		return false;
	}

	private static final Map<String, String> getConfig() {

		Map<String, String> configMap = new HashMap<String, String>();

		configMap.put("mode", "sandbox");
		return configMap;
	}

	private static String getAccessToken() throws PayPalRESTException {

		String clientID = "Abt0QBAwoDQ8d572VUy5gqDO2BLrZYYUV9PnJ3-c20jI2WYPz5zrafRDCXC1";
		String clientSecret = "ENBk8xDTo4eV2Ce87ncbhvqBTuvu0rMJ5Mf8DxJYoUMYx84G54fccsXCspjg";

		return new OAuthTokenCredential(clientID, clientSecret).getAccessToken();
	}

}

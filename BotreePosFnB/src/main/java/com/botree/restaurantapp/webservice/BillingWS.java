package com.botree.restaurantapp.webservice;

import com.botree.restaurantapp.model.BillSplitER_duplicate;
import com.botree.restaurantapp.model.BillSplitManual_duplicate;
import com.botree.restaurantapp.model.CardPayment;
import com.botree.restaurantapp.model.Discount;
import com.botree.restaurantapp.model.Payment;

public interface BillingWS {

	public String billSplitER(BillSplitER_duplicate billList);

	public String billSplitManual(BillSplitManual_duplicate billList);

	public String payment(Payment payment);
	
	public String paymentAdvOrder(Payment payment);

	public String getPaymentInfoByOrderId(String orderId);

	public String getBillByOrderId(String orderId);

	public String getBillSplitERByOrderId(String orderId);

	public String getBillSplitManualByOrderId(String orderId);

	public String addDiscount(Discount discount);

	public String getBillSplitManualOrderId(String orderId);
	
	public String addCardPayment(CardPayment cardPayment);
	
	public String updateCardPayment(CardPayment cardPayment);
	
	public String updateBillServiceCharge(String orderid, String storeId,String sChargeRate);
}

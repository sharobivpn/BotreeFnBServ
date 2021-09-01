package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.BillSplitER;
import com.botree.restaurantapp.model.BillSplitER_duplicate;
import com.botree.restaurantapp.model.BillSplitManual;
import com.botree.restaurantapp.model.BillSplitManual_duplicate;
import com.botree.restaurantapp.model.CardPayment;
import com.botree.restaurantapp.model.Discount;
import com.botree.restaurantapp.model.Payment;

public interface BillingDAO {

	public void sendBill(Bill bill) throws DAOException;

	public String billSplitER(BillSplitER_duplicate billLst)
			throws DAOException;

	public String billSplitManual(BillSplitManual_duplicate billList)
			throws DAOException;

	public String payment(Payment payment) throws DAOException;
	
	public String paymentAdvOrder(Payment payment) throws DAOException;

	public List<Payment> getPaymentInfoByOrderId(String orderId)
			throws DAOException;
	public List<Payment> getPaymentInfoByPaymentMode(String orderId)
			throws DAOException;

	public Bill getBillByOrderId(String orderId) throws DAOException;

	public List<BillSplitER> getBillSplitERByOrderId(String orderId)
			throws DAOException;

	public List<BillSplitManual> getBillSplitManualByOrderId(String orderId)
			throws DAOException;

	public String addDiscount(Discount discount) throws DAOException;
	
	public String addCardPayment(CardPayment cardPayment) throws DAOException;
	
	public String updateCardPayment(CardPayment cardPayment) throws DAOException;

	public List<Payment> getPaymentInfoByOrderList(List<Integer> orderList)
			throws DAOException;

	public List<Bill> getBillListByOrderIdList(List<Integer> orderIdList)
			throws DAOException;
	public List<BillSplitManual> getBillSplitManualOrderId(String orderId)
			throws DAOException;
	
	public String updateBillServiceCharge(String orderid, String storeId,
			String sChargeRate) throws DAOException;
	
	public String paymentforSync(Payment payment,int orderId) throws DAOException;

}

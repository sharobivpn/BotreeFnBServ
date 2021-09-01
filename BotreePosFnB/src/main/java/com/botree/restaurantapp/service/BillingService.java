package com.botree.restaurantapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.BillingDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.BillSplitER;
import com.botree.restaurantapp.model.BillSplitER_duplicate;
import com.botree.restaurantapp.model.BillSplitManual;
import com.botree.restaurantapp.model.BillSplitManual_duplicate;
import com.botree.restaurantapp.model.CardPayment;
import com.botree.restaurantapp.model.Discount;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class BillingService {

  @Autowired
	private BillingDAO billingDAO;
	
	private Bill bill = null;

	public BillingService() {

	}

	public String sendBill(Integer ph, String bill1) throws ServiceException {

		try {
			// bill.setBillText(bill1);
			// bill.setBillPicture(null);
			// bill.setOrder(null);
			System.out.println("Enter BillingService.sendBill ");
			// create a new bill
			billingDAO.sendBill(bill);
			System.out.println("exit BillingService.sendBill ");

			// store the bill in DB
		} catch (DAOException e) {
			throw new ServiceException("Send bill error", e);

		}
		return bill1;

	}

	public byte[] sendBill(Integer ph, byte[] bill1) throws ServiceException {

		try {
			// bill.setBillText(null);
			// bill.setBillPicture(bill1);
			// bill.setOrder(null);
			System.out.println("Enter BillingService.sendBill ");
			// create a new bill
			billingDAO.sendBill(bill);
			System.out.println("exit BillingService.sendBill ");

			// store the bill in DB
		} catch (DAOException e) {
			throw new ServiceException("Send bill error", e);

		}

		return bill1;
	}

	public String sendBill(String email, String bill1) throws ServiceException {

		try {
			// bill.setBillText(bill1);
			// bill.setBillPicture(null);
			// bill.setOrder(null);
			System.out.println("Enter BillingService.sendBill ");
			// create a new bill
			billingDAO.sendBill(bill);
			System.out.println("exit BillingService.sendBill ");

			// store the bill in DB
		} catch (DAOException e) {
			throw new ServiceException("Send bill error", e);

		}
		return bill1;
	}

	public byte[] sendBill(String email, byte[] bill1) throws ServiceException {
		try {
			// bill.setBillText(null);
			// bill.setBillPicture(bill1);
			// bill.setOrder(null);
			System.out.println("Enter BillingService.sendBill ");
			// create a new bill
			billingDAO.sendBill(bill);
			System.out.println("exit BillingService.sendBill ");

			// store the bill in DB
		} catch (DAOException e) {
			throw new ServiceException("Send bill error", e);

		}
		return bill1;
	}

	public String sendBill(String email, Integer ph, String bill1)
			throws ServiceException {
		try {
			// bill.setBillText(bill1);
			// bill.setBillPicture(null);
			// bill.setOrder(null);
			System.out.println("Enter BillingService.sendBill ");
			// create a new bill
			billingDAO.sendBill(bill);
			System.out.println("Exit BillingService.sendBill ");

			// store the bill in DB
		} catch (DAOException e) {
			throw new ServiceException("Send bill error", e);

		}
		return bill1;
	}

	public byte[] sendBill(String email, Integer ph, byte[] bill1)
			throws ServiceException {
		try {
			// bill.setBillText(null);
			// bill.setBillPicture(bill1);
			// bill.setOrder(null);
			System.out.println("Enter BillingService.sendBill ");
			// create a new bill
			billingDAO.sendBill(bill);
			System.out.println("Exit BillingService.sendBill ");

			// store the bill in DB
		} catch (DAOException e) {
			throw new ServiceException("Send bill error", e);

		}
		return bill1;
	}

	public String billSplitER(BillSplitER_duplicate billLst)
			throws ServiceException {
		String status = "";
		try {
			status = billingDAO.billSplitER(billLst);

		} catch (DAOException e) {
			throw new ServiceException(
					"Problem occurred while trying to split bill", e);

		}
		return status;

	}

	public String billSplitManual(BillSplitManual_duplicate billList)
			throws ServiceException {
		String status = "";
		try {
			status = billingDAO.billSplitManual(billList);

		} catch (DAOException e) {
			throw new ServiceException(
					"Problem occurred while trying to split bill", e);

		}
		return status;

	}

	public String payment(Payment payment) throws ServiceException {
		String status = "";
		try {
			status = billingDAO.payment(payment);

		} catch (DAOException e) {
			throw new ServiceException(
					"Problem occurred while trying to split bill", e);

		}
		return status;

	}
	
	public String paymentAdvOrder(Payment payment) throws ServiceException {
		String status = "";
		try {
			status = billingDAO.paymentAdvOrder(payment);

		} catch (DAOException e) {
			throw new ServiceException(
					"Problem occurred while trying to payementadvorder", e);

		}
		return status;

	}

	public String addDiscount(Discount discount) throws ServiceException {
		String status = "";
		try {
			status = billingDAO.addDiscount(discount);

		} catch (DAOException e) {
			throw new ServiceException("Problem occurred while add discount", e);

		}
		return status;

	}
	
	public String addCardPayment(CardPayment cardPayment) throws ServiceException {
		String status = "";
		try {
			status = billingDAO.addCardPayment(cardPayment);

		} catch (DAOException e) {
			throw new ServiceException("Problem occurred while add card payment", e);

		}
		return status;

	}
	
	public String updateCardPayment(CardPayment cardPayment) throws ServiceException {
		String status = "";
		try {
			status = billingDAO.updateCardPayment(cardPayment);

		} catch (DAOException e) {
			throw new ServiceException("Problem occurred while add discount", e);

		}
		return status;

	}

	public List<Payment> getPaymentInfoByOrderId(String orderId)
			throws ServiceException {

		List<Payment> payList = null;
		try {

			payList = billingDAO.getPaymentInfoByOrderId(orderId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occurred while trying to getPaymentInfoByOrderId ", e);
		}
		return payList;
	}

	public List<BillSplitER> getBillSplitERByOrderId(String orderId)
			throws ServiceException {

		List<BillSplitER> billSplitList = null;
		try {

			billSplitList = billingDAO.getBillSplitERByOrderId(orderId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occurred while trying to getPaymentInfoByOrderId ", e);
		}
		return billSplitList;
	}

	public List<BillSplitManual> getBillSplitManualByOrderId(String orderId)
			throws ServiceException {

		List<BillSplitManual> billSplitList = null;
		try {

			billSplitList = billingDAO.getBillSplitManualByOrderId(orderId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occurred while trying to getBillSplitManualByOrderId ", e);
		}
		return billSplitList;
	}
	
	public List<BillSplitManual> getBillSplitManualOrderId(String orderId)
			throws ServiceException {

		List<BillSplitManual> billSplitList = null;
		try {

			billSplitList = billingDAO.getBillSplitManualOrderId(orderId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occurred while trying to getBillSplitManualByOrderId ", e);
		}
		return billSplitList;
	}

	public Bill getBillByOrderId(String orderId) throws ServiceException {

		Bill bill = null;
		try {

			bill = billingDAO.getBillByOrderId(orderId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occurred while trying to getBillByOrderId ", e);
		}
		return bill;
	}
	
	public String updateBillServiceCharge(String orderid, String storeId,
			String sChargeRate) throws ServiceException {

		String status = "";

		try {
			status = billingDAO.updateBillServiceCharge(orderid, storeId,
					sChargeRate);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occured while trying to update service charge in bill by order id", e);

		}

		return status;

	}

	public BillingDAO getBillingDAO() {
		return billingDAO;
	}

	public void setBillingDAO(BillingDAO billingDAO) {
		this.billingDAO = billingDAO;
	}

}

package com.botree.restaurantapp.dao;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.commonUtil.CashDrawer;
import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.commonUtil.StoredProcedures;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.BillSplitER;
import com.botree.restaurantapp.model.BillSplitER_duplicate;
import com.botree.restaurantapp.model.BillSplitManual;
import com.botree.restaurantapp.model.BillSplitManual_duplicate;
import com.botree.restaurantapp.model.CardPayment;
import com.botree.restaurantapp.model.Discount;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.account.JournalEntry;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("billingDAO")
public class BillingDAOImpl implements BillingDAO {

	private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
	
	@Override
	public void sendBill(Bill bill) throws DAOException {

		Bill bills = new Bill();
		EntityManager em = null;
		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			// bills.setBillPicture(bill.getBillPicture());
			// bills.setBillText(bill.getBillText());
			em.persist(bills);

			em.getTransaction().commit();
			System.out.print("send bill successfully...");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
	}

	@Override
	public String billSplitER(BillSplitER_duplicate billLst)
			throws DAOException {

		EntityManager em = null;
		String status = "failure";
		List<BillSplitER> billSplitERs = null;
		int billId = 0;
		int orderId = 0;
		String billId1 = "";
		String prntBillNo = "";
		String billIdLstTwoDigits = "";
		String orderId1 = "";
		String splitMode = "";

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			billSplitERs = billLst.getBillSplitERs();
			if (billSplitERs != null && billSplitERs.size() > 0) {
				Iterator<BillSplitER> iterator = billSplitERs.iterator();
				while (iterator.hasNext()) {
					BillSplitER billSplitER2 = (BillSplitER) iterator.next();
					// billId = billSplitER2.getBillId();
					orderId = billSplitER2.getOrderId();
					splitMode = billSplitER2.getMode();
					break;

				}
				
				TypedQuery<Bill> qry1 = em.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id=:orderId", Bill.class);
				qry1.setParameter("orderId", orderId);
				Bill bill = qry1.getSingleResult();
				billId = bill.getId();
				// start generate print bill number
				billId1 = Integer.toString(billId);
				orderId1 = Integer.toString(orderId);
				if (billId1 != null && billId1 != "" && billId1.length() > 0)
					billIdLstTwoDigits = billId1.length() > 2 ? billId1
							.substring(billId1.length() - 2) : billId1;
				prntBillNo = orderId1 + billIdLstTwoDigits;
				// end generate print bill number

				// check if there exists bill split ER object with the bill id
				// and order id
				TypedQuery<BillSplitER> qry = em
						.createQuery("SELECT b FROM BillSplitER b WHERE b.billId=:BillId and b.orderId=:orderId", BillSplitER.class);
				qry.setParameter("BillId", billId);
				qry.setParameter("orderId", orderId);
				List<BillSplitER> billSplitERLst = qry.getResultList();
				if (billSplitERLst != null && billSplitERLst.size() > 0) {

					Iterator<BillSplitER> iterator1 = billSplitERLst.iterator();
					while (iterator1.hasNext()) {
						BillSplitER billSplitER2 = (BillSplitER) iterator1
								.next();
						em.remove(billSplitER2);
					}
				}
				if (splitMode.equalsIgnoreCase(CommonProerties.billSplitEqual)) {
					// get the number of bills
					int noOfBills = billSplitERs.size();
					// get the bill amount without taxes
					Bill bill2 = getBillByOrderId(orderId + "");
					double billAmt = bill2.getBillAmount();
					// bill amount for each customer if equal
					double reslt = (double) billAmt / noOfBills;
					double billEach = Math.floor(reslt * 100) / 100d;
					Iterator<BillSplitER> iterator2 = billSplitERs.iterator();
					while (iterator2.hasNext()) {
						BillSplitER billSplitER2 = (BillSplitER) iterator2
								.next();
						billSplitER2.setBillId(billId);
						billSplitER2.setPrintBillNumber(prntBillNo);
						billSplitER2.setBillAmount(billEach);
						em.persist(billSplitER2);
					}
				} else if (splitMode
						.equalsIgnoreCase(CommonProerties.billSplitRatio)) {

					Iterator<BillSplitER> iterator2 = billSplitERs.iterator();
					while (iterator2.hasNext()) {
						BillSplitER billSplitER2 = (BillSplitER) iterator2
								.next();
						billSplitER2.setBillId(billId);
						billSplitER2.setPrintBillNumber(prntBillNo);
						em.persist(billSplitER2);
					}
				}
				em.getTransaction().commit();

				// start update order set bill split to Y
				em.getTransaction().begin();

				OrderMaster order = em.find(OrderMaster.class, orderId);
				if (order != null) {
					order.setSplitBill("Y");
				}
				em.getTransaction().commit();
				// end update order set bill split to Y
				status = "bill split successfully done";
			}

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if (em != null) em.close();
		}

		return status;

	}

	@Override
	public String billSplitManual(BillSplitManual_duplicate billList)
			throws DAOException {

		EntityManager em = null;
		String status = "failure";
		List<BillSplitManual> billSplitManuals = null;
		int billId = 0;
		int orderId = 0;
		String billId1 = "";
		String prntBillNo = "";
		String billIdLstTwoDigits = "";
		String orderId1 = "";

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			billSplitManuals = billList.getBillSplitManuals();
			if (billSplitManuals != null && billSplitManuals.size() > 0) {
				Iterator<BillSplitManual> iterator = billSplitManuals
						.iterator();
				while (iterator.hasNext()) {
					BillSplitManual billSplitManual2 = (BillSplitManual) iterator
							.next();
					orderId = billSplitManual2.getOrderId();
					break;

				}
				TypedQuery<Bill> qry1 = em
						.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id=:orderId", Bill.class);
				qry1.setParameter("orderId", orderId);
				Bill bill = qry1.getSingleResult();
				billId = bill.getId();
				// start generate print bill number
				billId1 = Integer.toString(billId);
				orderId1 = Integer.toString(orderId);
				if (billId1 != null && billId1 != "" && billId1.length() > 0)
					billIdLstTwoDigits = billId1.length() > 2 ? billId1
							.substring(billId1.length() - 2) : billId1;
				prntBillNo = orderId1 + billIdLstTwoDigits;
				// end generate print bill number

				// check if there exists bill split manual object with the bill
				// id and order id
				TypedQuery<BillSplitManual> qry = em
						.createQuery("SELECT b FROM BillSplitManual b WHERE b.billId=:BillId and b.orderId=:orderId", BillSplitManual.class);
				qry.setParameter("BillId", billId);
				qry.setParameter("orderId", orderId);
				List<BillSplitManual> billSplitManualLst = (List<BillSplitManual>) qry
						.getResultList();
				if (billSplitManualLst != null && billSplitManualLst.size() > 0) {

					Iterator<BillSplitManual> iteratorDelete = billSplitManualLst
							.iterator();
					while (iteratorDelete.hasNext()) {
						BillSplitManual billSplitManual2 = (BillSplitManual) iteratorDelete
								.next();
						em.remove(billSplitManual2);

					}

				}
				Iterator<BillSplitManual> iteratorAdd = billSplitManuals
						.iterator();
				while (iteratorAdd.hasNext()) {
					BillSplitManual billSplitManual2 = (BillSplitManual) iteratorAdd
							.next();
					billSplitManual2.setBillId(billId);
					billSplitManual2.setPrintBillNumber(prntBillNo);
					em.persist(billSplitManual2);

				}

				em.getTransaction().commit();

				// start update order set bill split to Y
				em.getTransaction().begin();

				OrderMaster order = em.find(OrderMaster.class, orderId);
				if (order != null) {
					order.setSplitBill("Y");
				}
				em.getTransaction().commit();
				// end update order set bill split to Y

				status = "success";
			}

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if (em != null) em.close();
		}

		return status;

	}

	@Override
	public String payment(Payment payment) throws DAOException {

		EntityManager em = null;
		String status = "";
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		StoreMaster store = null;

		try {
			addressDAO = new StoreAddressDAOImpl();
			int storeId = payment.getStoreId();
			store = addressDAO.getStoreByStoreId(storeId);
			em = PersistenceListener.getEntityManager().createEntityManager();
			
			//checking for already paid or not
			OrderMaster order = payment.getOrderPayment();
			int orderId = order.getId();
			
			TypedQuery<OrderMaster> oldOrdre = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);
			oldOrdre.setParameter("orderid", orderId);
			OrderMaster oldOrder = (OrderMaster) oldOrdre.getSingleResult();
			if("N".equalsIgnoreCase(oldOrder.getFlag()))
			{
			em.getTransaction().begin();
			em.persist(payment);

			em.getTransaction().commit();
			em.getTransaction().begin();

			// check if payment is complete
			/*OrderMaster order = payment.getOrderPayment();
			int orderId = order.getId();*/
			TypedQuery<Payment> qry = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderId and p.amountToPay <=0", Payment.class);
			qry.setParameter("orderId", orderId);
			List<Payment> paymentLst = (List<Payment>) qry.getResultList();

			// update bill request status if payment complete
			if (paymentLst.size() > 0) {
				String billReqTime = payment.getCreationDate();
				OrderMaster orderMaster = em.find(OrderMaster.class, orderId);
				orderMaster.setBillReqStatus("Yes");
				orderMaster.setFlag("Y");
				orderMaster.setBillReqTime(billReqTime);
				em.getTransaction().commit();

				try {

					if (store.getPrintPaidBill().equalsIgnoreCase("Y")) {
						OrdersDAO dao = new OrdersDAOImpl();
						dao.printBill(payment.getOrderPayment().getId() + "", storeId);

					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			status = "success";
		}else{
			status = "alreadypaid";
		}

			

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		try {
			String cdCode = store.getCashDrawerCode();
			if (cdCode != null) {
				CashDrawer p = new CashDrawer();
				p.feedPrinter(cdCode);
			}
		} catch (Exception ex) {
		}
		
		if(("y").equalsIgnoreCase(payment.getIs_account())) {
			CustomerPaymentjournalEntry(payment);
		}
		
		return status;

	}
	
	@Override
	public String paymentAdvOrder(Payment payment) throws DAOException {

		EntityManager em = null;
		String status = "";
		
		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			
			// check if payment is complete
			OrderMaster order = payment.getOrderPayment();
			int orderId = order.getId();
			TypedQuery<Payment> qry = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderId", Payment.class);
			qry.setParameter("orderId", orderId);
			
			Payment oldpayment = qry.getSingleResult();
			oldpayment.setPaidAmount(oldpayment.getPaidAmount()+payment.getPaidAmount());
			em.merge(oldpayment);
			em.getTransaction().commit();
			// update bill request status if payment complete
			/*if (paymentLst.size() > 0) {
				
			}*/

			status = "success";

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		return status;

	}
	
	@Override
	public String paymentforSync(Payment payment,int orderId) throws DAOException {

		EntityManager em = null;
		String status = "";

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			
			//checking for already paid or not
			//OrderMaster order = payment.getOrderPayment();
			//int orderId = order.getId();
			
			TypedQuery<OrderMaster> oldOrdre = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);
			oldOrdre.setParameter("orderid", orderId);
			OrderMaster oldOrder = (OrderMaster) oldOrdre.getSingleResult();
			payment.setOrderPayment(oldOrder);
			if("N".equalsIgnoreCase(oldOrder.getFlag()))
			{
			em.getTransaction().begin();
			em.persist(payment);

			em.getTransaction().commit();
			em.getTransaction().begin();

			// check if payment is complete
			/*OrderMaster order = payment.getOrderPayment();
			int orderId = order.getId();*/
			TypedQuery<Payment> qry = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderId and p.amountToPay <=0", Payment.class);
			qry.setParameter("orderId", orderId);
			List<Payment> paymentLst = (List<Payment>) qry.getResultList();

			// update bill request status if payment complete
			if (paymentLst.size() > 0) {
				String billReqTime = payment.getCreationDate();
				OrderMaster orderMaster = em.find(OrderMaster.class, orderId);
				orderMaster.setBillReqStatus("Yes");
				orderMaster.setFlag("Y");
				orderMaster.setBillReqTime(billReqTime);
				em.getTransaction().commit();
			}
			status = "success";
		}else{
			status = "alreadypaid";
		}

			

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}
		
		if(("y").equalsIgnoreCase(payment.getIs_account())) {
			CustomerPaymentjournalEntry(payment);
		}
		
		return status;

	}

	@Override
	public String addDiscount(Discount discount) throws DAOException {

		EntityManager em = null;
		String status = "";
		Bill bill;
		List<Payment> payList = new ArrayList<Payment>();
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		StoreMaster storeMaster = new StoreMaster();
		DecimalFormat df = new DecimalFormat("00.00");
		double totalVatAmt=0.0;
		double totalServceTaxAmt=0.0;
		double totalServceChrgAmt=0.0;
		double totalitemPrice=0.0;
		double serviceChrgRate=0.0;
		double totalcustDiscAmt=0.0;

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			int orderId = discount.getOrderId();
			int storeid = discount.getStoreId();
			storeMaster = addressDAO.getStoreByStoreId(storeid);
			double discPercentage = discount.getDiscountPercentage();
			String discReason = discount.getDiscountReason();
			
			try {
			  TypedQuery<Payment> qry = em
						.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid", Payment.class);
				qry.setParameter("orderid", orderId);
				payList = qry.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (payList.size() == 1) {
				bill = getBillByOrderId(orderId + "");
				
				try {
					TypedQuery<OrderMaster> oldOrdre = em
							.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);
					oldOrdre.setParameter("orderid", orderId);
					OrderMaster oldOrder =oldOrdre.getSingleResult();
					if(oldOrder!=null)
					{
						//serviceChrgRate=oldOrder.getOrdertype().getServiceChargeValue();
						serviceChrgRate=bill.getServiceChargeRate();
						List<OrderItem> orderItemList = oldOrder.getOrderitem();
						Iterator<OrderItem> iterator = orderItemList.iterator();
						while (iterator.hasNext()) {
							OrderItem orderItem = (OrderItem) iterator.next();
							MenuItem item = orderItem.getItem();
					
		
							Double itemcustDisc=0.0;
			
							Double itemPrice=orderItem.getTotalPriceByItem();
							
							Double itemDiscPer = new Double(orderItem.getPromotionValue());
							Double itemDisc = (itemDiscPer * itemPrice) / 100;
							if("Y".equalsIgnoreCase(item.getSpotDiscount()))
							{
								itemcustDisc = (discPercentage * itemPrice) / 100;
							}
							Double itemPriceafterdis=itemPrice-(itemDisc+itemcustDisc);
							Double itemserviceChrg = (serviceChrgRate * itemPriceafterdis) / 100;
							Double itemVat = orderItem.getVat();
							double vatForThsItem = (itemVat * (itemPriceafterdis+itemserviceChrg)) / 100;
							Double itemServiceTax = orderItem.getServiceTax();
							Double serviceTaxForThsItem = (itemServiceTax * (itemPriceafterdis+itemserviceChrg)) / 100;
						
							totalVatAmt = totalVatAmt + vatForThsItem;
							totalServceTaxAmt = totalServceTaxAmt+ serviceTaxForThsItem;
							totalServceChrgAmt=totalServceChrgAmt+itemserviceChrg;
							totalcustDiscAmt=totalcustDiscAmt+itemcustDisc;
							totalitemPrice=totalitemPrice+itemPrice;
							orderItem.setDiscountPercentage(discPercentage);
							orderItem.setCustomerDiscount(itemcustDisc);
							orderItem.setNetPrice((itemPrice+itemserviceChrg+vatForThsItem+serviceTaxForThsItem)-(itemcustDisc));
							em.merge(orderItem);
						}
						
						//gross = (totalitemPrice + totalVatAmt + totalServceTaxAmt + totalServceChrgAmt);
						double grossAmt = (totalitemPrice+totalServceChrgAmt + totalVatAmt + totalServceTaxAmt)- totalcustDiscAmt;
						String grossUptoTwoDecimal = df.format(grossAmt);
						grossAmt = Double.parseDouble(grossUptoTwoDecimal);
						String redSTaxUptoTwoDecimal = df.format(totalServceTaxAmt);
						String redVatUptoTwoDecimal = df.format(totalVatAmt);
						String schargeUptoTwoDecimal = df.format(totalServceChrgAmt);
						
						bill.setSubTotalAmt(totalitemPrice);
						bill.setBillAmount(grossAmt);
						bill.setServiceTaxAmt(Double.parseDouble(redSTaxUptoTwoDecimal));
						bill.setVatAmt(Double.parseDouble(redVatUptoTwoDecimal));
						bill.setServiceChargeAmt(Double.parseDouble(schargeUptoTwoDecimal));
						
						if (storeMaster.getRoundOffTotalAmtStatus() != null && storeMaster.getRoundOffTotalAmtStatus().equalsIgnoreCase("Y")) {
								grossAmt = new Double(Math.round(grossAmt)); // round
						}
            else if (storeMaster.getDoubleRoundOff() != null
                && !storeMaster.getDoubleRoundOff().equalsIgnoreCase("")
                && storeMaster.getDoubleRoundOff().equalsIgnoreCase("Y")) {
              //String formvl = df.format(grossBillAmt);//added on 29.05.2018
              grossAmt = new Double(CommonProerties.roundOffUptoTwoPlacesDouble(grossAmt, 1)); // round off double
            }
						bill.setGrossAmt(grossAmt);
						// update other values
						bill.setCustomerDiscount(totalcustDiscAmt);
						bill.setDiscountPercentage(discPercentage);
						bill.setDiscountReason(discReason);
						bill.setIsNonchargeable(discount.getIsNonchargeable());
						// update payment
						Payment payment = payList.get(0);
						payment.setAmount(grossAmt);
						payment.setAmountToPay(grossAmt);
						em.merge(payment);
						em.merge(bill);
					}
					em.getTransaction().commit();
					status = "success";
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (payList.size() > 1) {
				status = "Payment Already Started";
			}

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		return status;

	}

	@Override
	public String addCardPayment(CardPayment cardPayment) throws DAOException {

		EntityManager em = null;
		String status = "success";

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			em.persist(cardPayment);
			em.getTransaction().commit();

			status = "" + cardPayment.getId();

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		return status;

	}

	@Override
	public String updateCardPayment(CardPayment cardPayment)
			throws DAOException {

		EntityManager em = null;
		String status = "";

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			em.merge(cardPayment);
			em.getTransaction().commit();

			status = "success";

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			if(em != null) em.close();
		}

		return status;
	}

	@Override
	public List<Payment> getPaymentInfoByOrderId(String orderId)
			throws DAOException {

		int intOrderId = Integer.parseInt(orderId);
		EntityManager em = null;
		List<Payment> payList = new ArrayList<Payment>();

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();

			TypedQuery<Payment> qry = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid", Payment.class);
			qry.setParameter("orderid", intOrderId);
			payList = qry.getResultList();

			// get the bill
			Bill bill = getBillByOrderId(orderId);
			Double custDiscount = bill.getCustomerDiscount();

			// get the order id
			Iterator<Payment> iterator = payList.iterator();
			while (iterator.hasNext()) {
				Payment payment = (Payment) iterator.next();
				OrderMaster order = payment.getOrderPayment();
				payment.setOrderId(order.getId());
				payment.setCustomerDiscount(custDiscount);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Problem occured in getpaymentinfo by orderid", e);
		}
		finally {
			if(em != null) em.close();
		}
		
		return payList;
	}

	@Override
	public List<Payment> getPaymentInfoByPaymentMode(String orderId)
			throws DAOException {

		int orderId1 = Integer.parseInt(orderId);
		EntityManager em = null;
		List<Payment> payList1 = new ArrayList<Payment>();
		List<Payment> payList = new ArrayList<Payment>();
		double paidAmtCash = 0.0;
		double amountCash = 0.0;
		double tenderAmtCash = 0.0;
		double paidAmtPaytm = 0.0;
		double amountPaytm = 0.0;
		double tenderAmtPaytm = 0.0;
		double paidAmtBhim = 0.0;
		double amountBhim = 0.0;
		double tenderAmtBhim = 0.0;
		double paidAmtCard = 0.0;
		double amountCard = 0.0;
		double tenderAmtCard = 0.0;
		double paidAmtCheque = 0.0;
		double amountCheque = 0.0;
		double tenderAmtCheque = 0.0;
		double paidAmtOnln = 0.0;
		double amountOnln = 0.0;
		double tenderAmtOnln = 0.0;
		double paidAmtGpay = 0.0;
		double amountGpay = 0.0;
		double tenderAmtGpay = 0.0;
		double paidAmtNeft = 0.0;
		double amountNeft = 0.0;
		double tenderAmtNeft = 0.0;
		double paidAmtRtgs = 0.0;
		double amountRtgs = 0.0;
		double tenderAmtRtgs = 0.0;
		double paidAmtImps = 0.0;
		double amountImps = 0.0;
		double tenderAmtImps = 0.0;
		double paidAmtPhonepay = 0.0;
		double amountPhonepay = 0.0;
		double tenderAmtPhonepay = 0.0;
		String paymentMode = "";

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Payment> qry = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid", Payment.class);
			qry.setParameter("orderid", orderId1);
			payList1 = qry.getResultList();

			// get the bill
			Bill bill = getBillByOrderId(orderId);
			Double custDiscount = bill.getCustomerDiscount();
			double discPer = bill.getDiscountPercentage();

			Payment paymentCash = new Payment();
			Payment paymentCard = new Payment();
			Payment paymentPaytm = new Payment();
			Payment paymentBhim = new Payment();
			Payment paymentDiscount = new Payment();
			Payment paymentCheque = new Payment();
			Payment paymentOnln = new Payment();
			Payment paymentGPay = new Payment();
			Payment paymentNeft = new Payment();
			Payment paymentRtgs = new Payment();
			Payment paymentImps = new Payment();
			Payment paymentPhonePay = new Payment();

			// if not full discount
			if (discPer < 100) {
				// get the order id
				Iterator<Payment> iterator = payList1.iterator();
				while (iterator.hasNext()) {
					Payment payment = (Payment) iterator.next();
					if (payment.getPaymentMode() != null) {
						if (payment.getPaymentMode().equalsIgnoreCase("cash")) {

							paymentMode = "cash";
							amountCash = payment.getAmount();
							paidAmtCash = paidAmtCash + payment.getPaidAmount();
							tenderAmtCash = tenderAmtCash
									+ payment.getTenderAmount();
							// set payment cash values
							paymentCash.setPaymentMode(paymentMode);
							paymentCash.setAmount(amountCash);
							paymentCash.setPaidAmount(paidAmtCash);
							paymentCash.setTenderAmount(tenderAmtCash);
							paymentCash.setCustomerDiscount(custDiscount);
						}

						else if (payment.getPaymentMode().equalsIgnoreCase(
								"Paytm")) {

							paymentMode = "paytm";
							amountPaytm = payment.getAmount();
							paidAmtPaytm = paidAmtPaytm
									+ payment.getPaidAmount();
							tenderAmtPaytm = tenderAmtPaytm
									+ payment.getTenderAmount();
							// set payment paytm values
							paymentPaytm.setPaymentMode(paymentMode);
							paymentPaytm.setAmount(amountPaytm);
							paymentPaytm.setPaidAmount(paidAmtPaytm);
							paymentPaytm.setTenderAmount(tenderAmtPaytm);
							paymentPaytm.setCustomerDiscount(custDiscount);

						}

						else if (payment.getPaymentMode().equalsIgnoreCase(
								"Bhim")) {

							paymentMode = "bhim";
							amountBhim = payment.getAmount();
							paidAmtBhim = paidAmtBhim + payment.getPaidAmount();
							tenderAmtBhim = tenderAmtBhim
									+ payment.getTenderAmount();
							// set payment bhim values
							paymentBhim.setPaymentMode(paymentMode);
							paymentBhim.setAmount(amountBhim);
							paymentBhim.setPaidAmount(paidAmtBhim);
							paymentBhim.setTenderAmount(tenderAmtBhim);
							paymentBhim.setCustomerDiscount(custDiscount);

						}

						else if (payment.getPaymentMode().equalsIgnoreCase(
								"card")) {

							paymentMode = "card";
							amountCard = payment.getAmount();
							paidAmtCard = paidAmtCard + payment.getPaidAmount();
							tenderAmtCard = tenderAmtCard
									+ payment.getTenderAmount();
							// set payment card values
							paymentCard.setPaymentMode(paymentMode);
							paymentCard.setAmount(amountCard);
							paymentCard.setPaidAmount(paidAmtCard);
							paymentCard.setTenderAmount(tenderAmtCard);
							paymentCard.setCustomerDiscount(custDiscount);
							paymentCard.setCardLastFourDigits(payment
									.getCardLastFourDigits());
						}

						else if (payment.getPaymentMode().equalsIgnoreCase(
								"cheque")) {

							paymentMode = "cheque";
							amountCheque = payment.getAmount();
							paidAmtCheque = paidAmtCheque
									+ payment.getPaidAmount();
							tenderAmtCheque = tenderAmtCheque
									+ payment.getTenderAmount();
							// set payment card values
							paymentCheque.setPaymentMode(paymentMode);
							paymentCheque.setAmount(amountCheque);
							paymentCheque.setPaidAmount(paidAmtCheque);
							paymentCheque.setTenderAmount(tenderAmtCheque);
							paymentCheque.setCustomerDiscount(custDiscount);

						}

						else if (payment.getPaymentMode().equalsIgnoreCase(
								"online")) {

							paymentMode = "online";
							amountOnln = payment.getAmount();
							paidAmtOnln = paidAmtOnln + payment.getPaidAmount();
							tenderAmtOnln = tenderAmtOnln
									+ payment.getTenderAmount();
							// set payment card values
							paymentOnln.setPaymentMode(paymentMode);
							paymentOnln.setAmount(amountOnln);
							paymentOnln.setPaidAmount(paidAmtOnln);
							paymentOnln.setTenderAmount(tenderAmtOnln);
							paymentOnln.setCustomerDiscount(custDiscount);

						}
						
						else if (payment.getPaymentMode().equalsIgnoreCase(
								"Gpay")) {

							paymentMode = "gpay";
							amountGpay = payment.getAmount();
							paidAmtGpay = paidAmtGpay + payment.getPaidAmount();
							tenderAmtGpay = tenderAmtGpay
									+ payment.getTenderAmount();
							// set payment card values
							paymentGPay.setPaymentMode(paymentMode);
							paymentGPay.setAmount(amountGpay);
							paymentGPay.setPaidAmount(paidAmtGpay);
							paymentGPay.setTenderAmount(tenderAmtGpay);
							paymentGPay.setCustomerDiscount(custDiscount);

						}
						
						else if (payment.getPaymentMode().equalsIgnoreCase(
								"Neft")) {

							paymentMode = "neft";
							amountNeft = payment.getAmount();
							paidAmtNeft = paidAmtNeft + payment.getPaidAmount();
							tenderAmtNeft = tenderAmtNeft
									+ payment.getTenderAmount();
							// set payment card values
							paymentNeft.setPaymentMode(paymentMode);
							paymentNeft.setAmount(amountNeft);
							paymentNeft.setPaidAmount(paidAmtNeft);
							paymentNeft.setTenderAmount(tenderAmtNeft);
							paymentNeft.setCustomerDiscount(custDiscount);

						}
						
						else if (payment.getPaymentMode().equalsIgnoreCase(
								"Rtgs")) {

							paymentMode = "rtgs";
							amountRtgs = payment.getAmount();
							paidAmtRtgs = paidAmtRtgs + payment.getPaidAmount();
							tenderAmtRtgs = tenderAmtRtgs
									+ payment.getTenderAmount();
							// set payment card values
							paymentRtgs.setPaymentMode(paymentMode);
							paymentRtgs.setAmount(amountRtgs);
							paymentRtgs.setPaidAmount(paidAmtRtgs);
							paymentRtgs.setTenderAmount(tenderAmtRtgs);
							paymentRtgs.setCustomerDiscount(custDiscount);

						}
						
						else if (payment.getPaymentMode().equalsIgnoreCase(
								"Imps")) {

							paymentMode = "imps";
							amountImps = payment.getAmount();
							paidAmtImps = paidAmtImps + payment.getPaidAmount();
							tenderAmtImps = tenderAmtImps
									+ payment.getTenderAmount();
							// set payment card values
							paymentImps.setPaymentMode(paymentMode);
							paymentImps.setAmount(amountImps);
							paymentImps.setPaidAmount(paidAmtImps);
							paymentImps.setTenderAmount(tenderAmtImps);
							paymentImps.setCustomerDiscount(custDiscount);

						}
						
						else if (payment.getPaymentMode().equalsIgnoreCase(
								"PhonePay")) {

							paymentMode = "phonepay";
							amountPhonepay = payment.getAmount();
							paidAmtPhonepay = paidAmtPhonepay + payment.getPaidAmount();
							tenderAmtPhonepay = tenderAmtPhonepay
									+ payment.getTenderAmount();
							// set payment card values
							paymentPhonePay.setPaymentMode(paymentMode);
							paymentPhonePay.setAmount(amountPhonepay);
							paymentPhonePay.setPaidAmount(paidAmtPhonepay);
							paymentPhonePay.setTenderAmount(tenderAmtPhonepay);
							paymentPhonePay.setCustomerDiscount(custDiscount);

						}
						
						
					}

				}
				if (paymentCash.getPaidAmount() > 0.0) {
					payList.add(paymentCash);
				}
				if (paymentCard.getAmount() > 0.0) {
					payList.add(paymentCard);
				}
				if (paymentPaytm.getAmount() > 0.0) {
					payList.add(paymentPaytm);
				}
				if (paymentBhim.getAmount() > 0.0) {
					payList.add(paymentBhim);
				}
				if (paymentCheque.getAmount() > 0.0) {
					payList.add(paymentCheque);
				}
				if (paymentOnln.getAmount() > 0.0) {
					payList.add(paymentOnln);
				}
				if (paymentGPay.getAmount() > 0.0) {
					payList.add(paymentGPay);
				}
				if (paymentNeft.getAmount() > 0.0) {
					payList.add(paymentNeft);
				}
				if (paymentRtgs.getAmount() > 0.0) {
					payList.add(paymentRtgs);
				}
				if (paymentImps.getAmount() > 0.0) {
					payList.add(paymentImps);
				}
				if (paymentPhonePay.getAmount() > 0.0) {
					payList.add(paymentPhonePay);
				}
				
			}

			// full discount
			else if (discPer == 100) {
				paymentDiscount.setPaymentMode("");
				payList.add(paymentDiscount);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"problem occured in MenuDAOImpl.getCategoryByStore", e);

		} finally {
			if(em != null) em.close();
		}
		return payList;
	}

	@Override
	public List<Payment> getPaymentInfoByOrderList(List<Integer> orderIdList)
			throws DAOException {
		EntityManager em = null;
		List<Payment> payList = new ArrayList<Payment>();

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Payment> qry = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id IN (:orderIdList)", Payment.class);
			qry.setParameter("orderIdList", orderIdList);
			payList = qry.getResultList();

			// get the bill
			List<Bill> billList = getBillListByOrderIdList(orderIdList);
			Iterator<Bill> iteratorBill = billList.iterator();
			while (iteratorBill.hasNext()) {
				Bill bill2 = (Bill) iteratorBill.next();
				int orderIdBill = bill2.getOrderbill().getId();
				Double custDiscount = bill2.getCustomerDiscount();
				Iterator<Payment> iteratorPayment = payList.iterator();
				while (iteratorPayment.hasNext()) {
					Payment payment = (Payment) iteratorPayment.next();
					OrderMaster order = payment.getOrderPayment();
					int orderIdPayment = order.getId();
					// check if order ids are equal
					if (orderIdBill == orderIdPayment) {
						payment.setOrderId(order.getId());
						payment.setCustomerDiscount(custDiscount);

					}

				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"Problem occured in MenuDAOImpl.getCategoryByStore", e);
		} finally {
			if(em != null) em.close();
		}
		return payList;
	}

	@Override
	public List<BillSplitER> getBillSplitERByOrderId(String orderId)
			throws DAOException {

		int orderId1 = Integer.parseInt(orderId);
		EntityManager em = null;
		List<BillSplitER> billSplitERList = new ArrayList<BillSplitER>();

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<BillSplitER> qry = em
					.createQuery("SELECT b FROM BillSplitER b WHERE b.orderId=:orderid", BillSplitER.class);
			qry.setParameter("orderid", orderId1);
			billSplitERList = qry.getResultList();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"Problem occured in MenuDAOImpl.getCategoryByStore", e);
		} finally {
			if(em != null) em.close();
		}
		return billSplitERList;
	}

	@Override
	public List<BillSplitManual> getBillSplitManualByOrderId(String orderId)
			throws DAOException {

		int orderId1 = Integer.parseInt(orderId);
		EntityManager em = null;
		List<Object> billSplitManualList = new ArrayList<Object>();
		List<BillSplitManual> billSplitManualLst = new ArrayList<BillSplitManual>();
		List<BillSplitManual> billSplitManualLst1 = new ArrayList<BillSplitManual>();
		List<BillSplitManual_duplicate> billSplitManuaDuplicatelLst = null;

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			Query qry = em.createQuery("SELECT b, sum(b.billAmount) FROM BillSplitManual b WHERE b.orderId=:orderid group by b.billNo");
			qry.setParameter("orderid", orderId1);
			billSplitManualList = (List<Object>)qry.getResultList();
			Iterator<Object> iterator = billSplitManualList.iterator();
			while (iterator.hasNext()) {
				billSplitManuaDuplicatelLst = new ArrayList<BillSplitManual_duplicate>();
				Object[] billSplitManual = (Object[]) iterator.next();
				BillSplitManual manual = (BillSplitManual) billSplitManual[0];
				Double total = (Double) billSplitManual[1];
				manual.setTotal(total);
				// get the bill number
				int billNum = manual.getBillNo();
				TypedQuery<BillSplitManual> qry1 = em
						.createQuery("SELECT b FROM BillSplitManual b WHERE b.orderId=:orderid and b.billNo=:billno", BillSplitManual.class);
				qry1.setParameter("orderid", orderId1);
				qry1.setParameter("billno", billNum);
				billSplitManualLst1 = qry1.getResultList();
				Iterator<BillSplitManual> iterator1 = billSplitManualLst1
						.iterator();
				while (iterator1.hasNext()) {
					BillSplitManual billSplitManual2 = (BillSplitManual) iterator1
							.next();
					// create BillSplitManual_duplicate object out of a
					// BillSplitManual
					BillSplitManual_duplicate duplicate = new BillSplitManual_duplicate();
					duplicate.setItemName(billSplitManual2.getItemName());
					duplicate.setItemQuantity(billSplitManual2
							.getItemQuantity());
					duplicate.setItemRate(billSplitManual2.getItemRate());
					duplicate.setItemId(billSplitManual2.getItemId());
					duplicate.setBillAmount(billSplitManual2.getBillAmount());
					billSplitManuaDuplicatelLst.add(duplicate);

				}
				manual.setBillSplitManualList(billSplitManuaDuplicatelLst);
				billSplitManualLst.add(manual);

			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"Problem occured in getBillSplitManualByOrderId", e);
		} finally {
			if(em != null) em.close();
		}
		// return (List<BillSplitManual>)billSplitManualList;
		return billSplitManualLst;
	}

	@Override
	public List<BillSplitManual> getBillSplitManualOrderId(String orderId)
			throws DAOException {

		int orderId1 = Integer.parseInt(orderId);
		EntityManager em = null;

		List<BillSplitManual> billSplitManualLst1 = new ArrayList<BillSplitManual>();

		try {
//			entityManagerFactory = PersistenceListener.getEntityManager();
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<BillSplitManual> qry1 = em
					.createQuery("SELECT b FROM BillSplitManual b WHERE b.orderId=:orderid", BillSplitManual.class);
			qry1.setParameter("orderid", orderId1);

			billSplitManualLst1 = qry1.getResultList();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"Problem occured in getBillSplitManualByOrderId", e);
		} finally {
			if(em != null) em.close();
		}

		return billSplitManualLst1;
	}

	@Override
	public Bill getBillByOrderId(String orderId) throws DAOException {

		int orderId1 = Integer.parseInt(orderId);
		EntityManager em = null;
		Bill bill = null;

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Bill> qry = em
					.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id=:orderid", Bill.class);
			qry.setParameter("orderid", orderId1);
			bill = qry.getSingleResult();

		}

		catch (Exception e) {
			e.printStackTrace();
			bill = new Bill();
			throw new DAOException("problem occured in getBillByOrderId", e);
		} finally {
			if(em != null) em.close();
		}
		return bill;
	}

	@Override
	public List<Bill> getBillListByOrderIdList(List<Integer> orderIdList)
			throws DAOException {
		EntityManager em = null;
		List<Bill> billList = null;

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Bill> qry = em
					.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id IN (:orderIdList)", Bill.class);
			qry.setParameter("orderIdList", orderIdList);
			billList = qry.getResultList();

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("problem occured in getBillByOrderId", e);
		} finally {
			if(em != null) em.close();
		}
		return billList;
	}
	
	@Override
	public String updateBillServiceCharge(String orderid, String storeId,
			String sChargeRate) throws DAOException {

		EntityManager em = null;
		String status = "";
		Bill bill;
		List<Payment> payList = new ArrayList<Payment>();
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		StoreMaster storeMaster = new StoreMaster();
		DecimalFormat df = new DecimalFormat("00.00");
		double totalVatAmt=0.0;
		double totalServceTaxAmt=0.0;
		double totalServceChrgAmt=0.0;
		double totalitemPrice=0.0;
		double serviceChrgRate=Double.parseDouble(sChargeRate);
		//double totalcustDiscAmt=0.0;

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			int orderId = Integer.parseInt(orderid);
			int storeid = Integer.parseInt(storeId);
			storeMaster = addressDAO.getStoreByStoreId(storeid);
			//double discPercentage = discount.getDiscountPercentage();
			//String discReason = discount.getDiscountReason();
			
			try {
			  TypedQuery<Payment> qry = em
						.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid", Payment.class);
				qry.setParameter("orderid", orderId);
				payList = qry.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (payList.size() == 1) {
				bill = getBillByOrderId(orderId + "");
				
				try {
					TypedQuery<OrderMaster> oldOrdre = em
							.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);
					oldOrdre.setParameter("orderid", orderId);
					OrderMaster oldOrder =oldOrdre.getSingleResult();
					if(oldOrder!=null)
					{
						//serviceChrgRate=oldOrder.getOrdertype().getServiceChargeValue();
						List<OrderItem> orderItemList = oldOrder.getOrderitem();
						Iterator<OrderItem> iterator = orderItemList.iterator();
						while (iterator.hasNext()) {
							OrderItem orderItem = (OrderItem) iterator.next();
							//MenuItem item = orderItem.getItem();
					
		
							//Double itemcustDisc=0.0;
			
							Double itemPrice=orderItem.getTotalPriceByItem();
							
							Double itemDiscPer = new Double(orderItem.getPromotionValue());
							Double itemDisc = (itemDiscPer * itemPrice) / 100;
							/*if(item.getSpotDiscount().equalsIgnoreCase("Y"))
							{
								itemcustDisc = (discPercentage * itemPrice) / 100;
							}*/
							Double itemPriceafterdis=itemPrice-itemDisc;
							Double itemserviceChrg = (serviceChrgRate * itemPriceafterdis) / 100;
							Double itemVat = orderItem.getVat();
							double vatForThsItem = (itemVat * (itemPriceafterdis+itemserviceChrg)) / 100;
							Double itemServiceTax = orderItem.getServiceTax();
							Double serviceTaxForThsItem = (itemServiceTax * (itemPriceafterdis+itemserviceChrg)) / 100;
						
							totalVatAmt = totalVatAmt + vatForThsItem;
							totalServceTaxAmt = totalServceTaxAmt+ serviceTaxForThsItem;
							totalServceChrgAmt=totalServceChrgAmt+itemserviceChrg;
							//totalcustDiscAmt=totalcustDiscAmt+itemcustDisc;
							totalitemPrice=totalitemPrice+itemPrice;
							
						}
						
						//gross = (totalitemPrice + totalVatAmt + totalServceTaxAmt + totalServceChrgAmt);
						double grossAmt = (totalitemPrice+totalServceChrgAmt + totalVatAmt + totalServceTaxAmt);
						String grossUptoTwoDecimal = df.format(grossAmt);
						grossAmt = Double.parseDouble(grossUptoTwoDecimal);
						String redSTaxUptoTwoDecimal = df.format(totalServceTaxAmt);
						String redVatUptoTwoDecimal = df.format(totalVatAmt);
						String schargeUptoTwoDecimal = df.format(totalServceChrgAmt);
						
						bill.setSubTotalAmt(totalitemPrice);
						bill.setBillAmount(grossAmt);
						bill.setServiceTaxAmt(Double.parseDouble(redSTaxUptoTwoDecimal));
						bill.setVatAmt(Double.parseDouble(redVatUptoTwoDecimal));
						bill.setServiceChargeRate(serviceChrgRate);
						bill.setServiceChargeAmt(Double.parseDouble(schargeUptoTwoDecimal));
						
						if (storeMaster.getRoundOffTotalAmtStatus() != null && storeMaster.getRoundOffTotalAmtStatus().equalsIgnoreCase("Y")) {
								grossAmt = new Double(Math.round(grossAmt)); // round
							}
						bill.setGrossAmt(grossAmt);
						// update other value
						// update payment
						Payment payment = payList.get(0);
						payment.setAmount(grossAmt);
						payment.setAmountToPay(grossAmt);
						em.merge(payment);
						em.merge(bill);
					}
					em.getTransaction().commit();
					status = "success";
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (payList.size() > 1) {
				status = "Payment Already Started";
			}

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		return status;
	}

	public Double getTotalPaid(int orderId , String paymode)
	{
		EntityManager em = null;
		BigDecimal paidAmt = null;
		try {
			
			em = PersistenceListener.getEntityManager().createEntityManager();
			//em.getTransaction().begin();
//			Query q = em.createQuery("select sum(p.paidAmount) from Payment p where p.orderPayment.id=:orderId and p.paymentMode=:paymentMode group by p.paymentMode");
			//select sum(paid_amount) as tot_amt from restaurant.bp_t_fo_orders_payment where order_id=13767 and payment_mode="cash" group by payment_mode;
			Query query = em.createNativeQuery("select sum(p.paid_amount) from bp_t_fo_orders_payment p where p.order_id=:orderId and p.payment_mode=:paymentMode group by p.payment_mode");
			System.out.println("com.sharobi.restaurantapp.dao.BillingDAOImpl.getTotalPaid(int, String):: Query = " + query);
			/*paidAmt =  (BigDecimal)q.setParameter("orderId", payment.getOrderPayment().getId())
					  .setParameter("paymentMode", payment.getPaymentMode())
					  .getSingleResult();*/
			paidAmt =  (BigDecimal)query.setParameter("orderId", orderId)
					  .setParameter("paymentMode", paymode).getSingleResult();
			
			System.out.println("Total Pay = " + paidAmt);
			//em.getTransaction().commit();
		} catch (Exception e) {
			//em.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			if(em!=null)
				em.close();
		}
		if(paidAmt==null)
			return 0.0;
		else 
			return paidAmt.doubleValue();
	}
	
	public int CustomerPaymentjournalEntry(Payment payment)
			throws DAOException {
		System.out.println("CustomerPaymentjournalEntry InventoryInvoicePayment payment = "+payment);
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		int nocusacc = 0;
		int nosaleacc = 0;
		
		try {
			
			Date createddate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse((payment.getCreationDate()));
			Payment stockxml = new Payment();
			
			//Customer Payment Journal Entry
			if(payment.getIs_account().equalsIgnoreCase("y") && payment.getCr_legder_id()!=0 && payment.getDr_legder_id()!=0)
			{
			
				List<JournalEntry> jes = new ArrayList<JournalEntry>();
				
				if(payment.getCr_legder_id()!=0 && payment.getCr_amount()>0)
				{
					jes.add(new JournalEntry("cr", payment.getCr_legder_id(), payment.getCr_amount()));
				}
				if(payment.getDr_legder_id()!=0 && payment.getDr_amount()>0)
				{
					jes.add(new JournalEntry("dr", payment.getDr_legder_id(), payment.getDr_amount()));
				}
				stockxml.setJes(jes);
				System.out.println("cust pay : jes = "+jes);
				
				nocusacc = 0;
			}
			else {
				nocusacc = 1;
			}
			
			
			//For Sale Journal entry
			if(payment.getIs_account().equalsIgnoreCase("y") && payment.getSale_ledger_id()!=0 && payment.getDuties_ledger_id()!=0)
			{
				double roundoff = 0;
				double cashAmt = getTotalPaid(payment.getOrderPayment().getId(),"cash");
				double cardAmt = getTotalPaid(payment.getOrderPayment().getId(),"card");
				cardAmt += getTotalPaid(payment.getOrderPayment().getId(),"Paytm");
				cardAmt += getTotalPaid(payment.getOrderPayment().getId(),"Online");
				
				List<JournalEntry> jes = new ArrayList<>();
				if(payment.getSale_ledger_id()!=0 && payment.getGrossAmt()!=0)
				{
					jes.add(new JournalEntry("cr", payment.getSale_ledger_id(), payment.getGrossAmt()));
					roundoff += payment.getGrossAmt();
				}
				if(payment.getDuties_ledger_id()!=0)
				{
					jes.add(new JournalEntry("cr", payment.getDuties_ledger_id(), payment.getTaxVatAmt()));
					roundoff += payment.getTaxVatAmt();
				}
				if(payment.getService_charge_ledger_id()!=0 && payment.getServiceChargeAmt()>0)
				{
					jes.add(new JournalEntry("cr", payment.getService_charge_ledger_id(), payment.getServiceChargeAmt()));
					roundoff += payment.getServiceChargeAmt();
				}
				
				
				
				if(payment.getDebitor_ledger_id()!=0)
				{
					if(payment.getPaymentMode().equalsIgnoreCase("nopay") && payment.getAmountToPay()>0)
					{
						
						jes.add(new JournalEntry("dr", payment.getDebitor_ledger_id(), payment.getAmountToPay()));
						roundoff -= payment.getAmountToPay();
					}
					else
					{
						//double amt = payment.getNetTotal()-payment.getPaidAmount()-cashAmt-cardAmt;
						double amt = payment.getAmountToPay();
						jes.add(new JournalEntry("dr", payment.getDebitor_ledger_id(), amt ));
						roundoff -= amt;
					}
				}
				/*else if(payment.getPaymentMode().equalsIgnoreCase("cash"))
				{
					if(payment.getDebitor_cash_ledger_id()!=0 && payment.getPaidAmount()>0)
					{
						//double amt = getTotalPaid(payment.getOrderPayment().getId(),payment.getPaymentMode());
						jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), cashAmt));
						roundoff -= amt;
					}
				}
				else if(payment.getPaymentMode().equalsIgnoreCase("card"))
				{
					if(payment.getCard_ledger_id()!=0 && payment.getPaidAmount()>0)
					{
						//double amt = getTotalPaid(payment.getOrderPayment().getId(),payment.getPaymentMode());
						//jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), payment.getPaidAmount()));
						jes.add(new JournalEntry("dr", payment.getCard_ledger_id(), cardAmt));
						roundoff -= amt;
					}
				}*/
				
				
				if(payment.getDebitor_cash_ledger_id()!=0 && cashAmt>0)
				{
					jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), cashAmt));
					roundoff -= cashAmt;
				}
				if(payment.getCard_ledger_id()!=0 && cardAmt>0)
				{
					//jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), payment.getPaidAmount()));
					jes.add(new JournalEntry("dr", payment.getCard_ledger_id(), cardAmt));
					roundoff -= cardAmt;
				}
				
				
				if(payment.getDiscount_ledger_id()!=0 && payment.getDiscAmt()>0)
				{
					jes.add(new JournalEntry("dr", payment.getDiscount_ledger_id(), payment.getDiscAmt()));
					roundoff -= payment.getDiscAmt();
				}
				if(payment.getRound_ledger_id()!=0 && Math.abs(roundoff)<1) //as during part payment roundoff will differ
				{
					String type="";
					if(roundoff<0)
						type="cr";
					else
						type="dr";
					
					jes.add(new JournalEntry(type, payment.getRound_ledger_id(), Math.abs(roundoff)));
				}
				
				stockxml.setJes(jes);
				
				nosaleacc = 0;
			}
			else
			{
				nosaleacc = 1;
			}

			
			if(nocusacc==1 && nosaleacc==1)
			{
				return 0;
			}
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			
			try {
				StringWriter sw = new StringWriter();
				//File file = new File("D:\\2017-10-04\\file_purchase_invoice_creation.xml");
				JAXBContext jaxbContext = JAXBContext
						.newInstance(Payment.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				//jaxbMarshaller.marshal(InventoryInvoicePayment, file);
				// jaxbMarshaller.marshal(InventoryInvoicePayment, System.out);
				jaxbMarshaller.marshal(stockxml, sw);
				String result = sw.toString();
				System.out.println("Customer Payment xml:: "+result);
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_INSERT_XMLJOURNAL_TRANSACTION);
				callableStatement.setString(1, result);
				callableStatement.setString(2, "jes");
				callableStatement.setString(3, payment.getQs());
				callableStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(createddate));
				//callableStatement.setInt(5, payment.getId());//(5, payment.getOrderPayment().getId());
				callableStatement.setInt(5, payment.getOrderPayment().getId());//(5, payment.getOrderPayment().getId());
				callableStatement.setString(6, "");
				callableStatement.setInt(7, 0);
				callableStatement.setInt(8, payment.getStoreId());
				callableStatement.setInt(9, 0);
				callableStatement.setString(10, "");
				callableStatement.setString(11, payment.getCreatedBy());
				
				System.out.println("callable "+callableStatement.toString());
				callableStatement.execute();
				//invNo = callableStatement.getString(4);
				status = 1;
				/*if (status == 0) {
					result1 = "" + status;
				} else if (status == 1) {
					result1 = invNo;
				}*/
				// result=result.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
				// System.out.println("formatted output::: "+result);

			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(em!=null)
				em.close();
		}
		return status;

	}

}

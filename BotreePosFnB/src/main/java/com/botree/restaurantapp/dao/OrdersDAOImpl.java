package com.botree.restaurantapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.license.util.Constants;
import com.botree.restaurantapp.commonUtil.BillPosPrinterMain;
import com.botree.restaurantapp.commonUtil.BillSplitPosPrinterMain;
import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.commonUtil.KotPrint;
import com.botree.restaurantapp.commonUtil.KotPrintItemWise;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.BillSplitManual;
import com.botree.restaurantapp.model.BillSplitManual_duplicate;
import com.botree.restaurantapp.model.CommonBean;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.Discount;
import com.botree.restaurantapp.model.ListHolder;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.MenuItemLangMap;
import com.botree.restaurantapp.model.OrderDeliveryBoy;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.OrderType;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.model.PaymentType;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.print.PrintKotItemMaster;
import com.botree.restaurantapp.print.PrintKotMaster;


@Component("ordersDAO")
public class OrdersDAOImpl implements OrdersDAO {

  private final static Logger LOGGER = LogManager.getLogger(OrdersDAOImpl.class);
  
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

  private UserDAO userDAO;

  // MenuDAO
  private MenuDAO menuDAO = new MenuDAOImpl();

//previously this service return the created order id(int) but from 19.07.2018 it will return the created order obj
	@Override
	public OrderMaster createOrder(OrderMaster order, HttpServletRequest request)
			throws DAOException {

		List<OrderItem> oldorderItemList=null;
		
		int orderId = 0;
		OrderMaster orderCreated=null;
		Date currDate = new Date();
		Double totalBillAmt = 0.0;
		// Double netBillAmt = 0.0;
		Double totalDiscount = 0.0;
		double newTotalBillAmt = 0.0;
		double newTotalDscnt = 0.0;
		double netPriceEachItem = 0.0;
		boolean isExistingOrder = false;
		double serviceCharge = 0.0;
		double serviceChargeRate = 0.0;
		int storeCustomerId = 0;
		double itemVat=0.0;
		double itemServiceTax=0.0;
		
		boolean isNewOrder = (order.getId() <= 0) ? true : false;
		
		String grossBillAmtUptoTwoDecimal=null;
		// int billId = 0;
		EntityManager em = null;
		// String orderBillId = "";
		Double totalserviceTax = 0.0;
		Double totalVat = 0.0;
		Double totalItemPriceWithoutTax = 0.0;
		StoreMaster storeMaster = new StoreMaster();

		StoreAddressDAO dao = new StoreAddressDAOImpl();
		StoreCustomerDaoImpl storeCustomerDaoImpl = new StoreCustomerDaoImpl();
		
    int storeId = order.getStoreId();
    int orderTypeid = order.getOrdertype().getId();

		try {
	    OrderType orderType = storeCustomerDaoImpl.getOrderType(orderTypeid, storeId);
			// get current date and time, changed for celavi
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String strDate = sdf.format(cal.getTime());
			// System.out.println("Current date in String Format: " + strDate);
			// set date and time
			order.setOrderTime(strDate);
			String orderTime = order.getOrderTime();
			storeMaster = dao.getStoreByStoreId(order.getStoreId());
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// System.out.println("Helloo11111");

			if (order.getId() == -1) {// new
				order.setOrderDate(currDate);
				order.setFlag("N");
				order.setBillReqStatus("No");
				order.setSplitBill("N");
				order.setKotPrintStatus("N");
				order.setKotPrintCount(0);
				order.setBillPrintcount(0);
				order.setCreditFlag("N");
				
				// start calculate time of order
				String orderDtNTime = order.getOrderTime();
				if (orderDtNTime != null && orderDtNTime.length() > 0) {
					String[] tempURL1 = orderDtNTime.split(" ");
					String time = tempURL1[1];
					String[] timefull = time.split(":");
					String hr = timefull[0];
					String mins = timefull[1];
					String hrMins = hr + ":" + mins;

					order.setTime(hrMins);
				}
				// end calculate time of order
				order.setId(0);

				try {
					if (order.getCustomerContact().equals("")
							&& order.getCustomerName().equals("")) {
						storeCustomerId = 0;
					} /*else if (order.getCustomerContact().equals("")) {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					}*//*
					 * else if(order.getCustomerName().equals("")){
					 * storeCustomerId = updateOrderByStoreCustomerId(order); }
					 */else if (order.getStoreCustomerId() > 0) {
						// storeCustomerId = order.getStoreCustomerId();
						storeCustomerId = editOrderByStoreCustomerId(order);
					} else {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					}
					// storeCustomerId=155;
					LOGGER.info("Successful");
				} catch (Exception e) {
					e.printStackTrace();
					storeCustomerId = order.getStoreCustomerId();
				}
				order.setStoreCustomerId(storeCustomerId);
				em.persist(order);// order id
			}
			else { //if (order.getId() != -1) {// old order special item update

				System.out.println("special note:  " + order.getSpecialNote()
						+ "Store Customer ID:  " + order.getStoreCustomerId());
				OrderMaster oldOrder = null;
				
				try {
					if (order.getCustomerContact().equals("")
							&& order.getCustomerName().equals("")) {
						storeCustomerId = 0;
					} else if (order.getStoreCustomerId() == 0
							&& !(order.getCustomerName().equals(""))) {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					} else if (order.getStoreCustomerId() == 0
							&& !(order.getCustomerContact().equals(""))) {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					} else {
						storeCustomerId = order.getStoreCustomerId();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					int orderid = order.getId();
					Query qryOldOrder = em
							.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid");
					qryOldOrder.setParameter("orderid", orderid);
					oldOrder = (OrderMaster) qryOldOrder.getSingleResult();
					oldorderItemList = new ArrayList<OrderItem>();
					oldorderItemList = oldOrder.getOrderitem();
					System.out.println("old order item list size while getting::"+oldorderItemList.size());
					oldOrder.setCustomerName(order.getCustomerName());
					oldOrder.setCustomerContact(order.getCustomerContact());
					oldOrder.setDeliveryAddress(order.getDeliveryAddress());
					oldOrder.setDeliveryPersonName(order
							.getDeliveryPersonName());
					oldOrder.setNoOfPersons(order.getNoOfPersons());
					oldOrder.setCustVatRegNo(order.getCustVatRegNo());
					oldOrder.setState(order.getState());
					oldOrder.setLocation(order.getLocation());
					oldOrder.setHouseNo(order.getHouseNo());
					oldOrder.setStoreCustomerId(storeCustomerId);
					oldOrder.setStreet(order.getStreet());
					order.setOrderNo(oldOrder.getOrderNo());
					oldOrder.setRemarks(order.getRemarks());
					try {
						oldOrder.setAnniversary(order.getAnniversary());
					} catch (Exception e) {
						oldOrder.setAnniversary(null);
					}
					try {
						oldOrder.setDob(order.getDob());
					} catch (Exception e) {
						oldOrder.setDob(null);
					}
					try {
						if (order.getSpecialNote().trim().length() > 0) {
							oldOrder.setSpecialNote(order.getSpecialNote());
						}
					} catch (Exception e1) {
						oldOrder.setSpecialNote("");
					}
					/*
					 * Update Customer While Edit
					 */
					// editOrderByStoreCustomerId(order);
					int storeCusId = order.getStoreCustomerId();
					String homeDeliveryCustName = "";
					String homeDeliveryCustAddr = "";
					String homeDeliveryCustPh = "";
					// String homeDeliveryPersonName = "";
					String homeDeliveryLocation = "";
					String homeDeliveryStreet = "";
					String homeDeliveryHouseNo = "";
					String custVatRegNo = "";
					String homeDeliveryState = "";
					String waiterName = "";
					/** New Fields */
					String carNo = "";
					Date anniversary_date = new Date();
					Date dob = new Date();
					TypedQuery<StoreCustomer> qrySC = em
							.createQuery("SELECT s FROM StoreCustomer s WHERE  s.id=:storeCusId", StoreCustomer.class);
					qrySC.setParameter("storeCusId", storeCusId);
					StoreCustomer store = (StoreCustomer) qrySC
							.getSingleResult();

					// if (order.getTable_no().equalsIgnoreCase("0")&&
					// store.getParcelAddress().equalsIgnoreCase("Y")) {
					homeDeliveryCustName = order.getCustomerName();
					homeDeliveryCustAddr = order.getDeliveryAddress();
					homeDeliveryCustPh = order.getCustomerContact();
					// homeDeliveryPersonName = order.getDeliveryPersonName();
					homeDeliveryLocation = order.getLocation();
					homeDeliveryStreet = order.getStreet();
					homeDeliveryHouseNo = order.getHouseNo();
					homeDeliveryState = order.getState();
					custVatRegNo = order.getCustVatRegNo();
					carNo = order.getCar_no();
					try {
						anniversary_date = order.getAnniversary();
					} catch (NullPointerException e) {
						anniversary_date = null;
					}
					try {
						dob = order.getDob();
					} catch (NullPointerException e) {
						dob = null;
					}
					waiterName = order.getWaiterName();
					// }

					/** Store data in StoreCustomer */
					// store.setId(storeCusId);
					store.setName(homeDeliveryCustName.toString().trim());
					store.setAddress(homeDeliveryCustAddr.toString());
					store.setContactNo(homeDeliveryCustPh);
					store.setLocation(homeDeliveryLocation.toString());
					store.setStreet(homeDeliveryStreet);
					store.setHouse_no(homeDeliveryHouseNo);
					store.setCust_vat_reg_no(custVatRegNo);
					store.setAnniversary(anniversary_date);
					store.setCar_no(carNo);
					store.setDob(dob);
					store.setStoreId(order.getStoreId());
					store.setCreditCustomer("N");
					store.setDeleteFlag("N");
					store.setState(order.getState());
					store.setState(homeDeliveryState);
					store.setWaiterName(waiterName);
					em.merge(store);
					// em.persist(storeCustomer);
					/*
					 * End Update
					 */
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					em.merge(oldOrder);
				}
			}

      order.setOrdertype(orderType);

      List<OrderItem> orderItemList = order.getOrderitem();
      
			List<Integer> menuItemIDs = new ArrayList<>();
			for(OrderItem orderItem : orderItemList) {
			  menuItemIDs.add(orderItem.getItem().getId());
			}
			
			List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
			
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
			
			Iterator<OrderItem> iterator = orderItemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();

				OrderMaster master = new OrderMaster();
				master.setId(order.getId());
				orderItem.setOrders(master);
				orderItem.setOrderTime(orderTime);
				// orderItem.setSpecialNote(order.getSpecialNote());
				orderItem.setOrdertype(order.getOrdertype().getId());
				orderItem.setKitchenOut("N");

				// calculate total price for each item
				MenuItem menuItem = menuItemMap.get(orderItem.getItem().getId());
				Double itemRate=menuItem.getPrice();
				if(orderItem.getRate()>0)
				{
					itemRate=orderItem.getRate();
				}
				
				Double itemPrice = Double.parseDouble((orderItem.getQuantityOfItem())) * itemRate;

				// service tax calculation for each item
				itemServiceTax = menuItem.getServiceTax();
				Double itemDisc = 0.0;
				Double serviceTaxForThsItem = 0.0;
				Double itemDiscPer = 0.0;

				if (menuItem.getPromotionFlag().equalsIgnoreCase("Y")) {
					itemDiscPer = new Double(menuItem.getPromotionValue());

				}
				itemDisc = (itemDiscPer * itemPrice) / 100;
				serviceTaxForThsItem = (itemServiceTax * (itemPrice - itemDisc)) / 100;
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {

					if (storeMaster.getParcelServiceTax().equalsIgnoreCase("N")) {

						serviceTaxForThsItem = 0.0;

					}
				}

				totalserviceTax = totalserviceTax + serviceTaxForThsItem;

				// vat calculation for each item
				itemVat = menuItem.getVat();
				Double vatForThsItem = (itemVat * (itemPrice - itemDisc)) / 100;
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {

					if (storeMaster.getParcelVat().equalsIgnoreCase("N")) {

						vatForThsItem = 0.0;

					}
				}

				totalVat = totalVat + vatForThsItem;

				// calculate total item price without tax
				totalItemPriceWithoutTax = totalItemPriceWithoutTax + itemPrice;

				// set item price
				//orderItem.setRate(menuItem.getPrice());
				orderItem.setRate(itemRate);
				// set item service tax and vat for parcel (for marufaz)
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {

					if (storeMaster.getParcelServiceTax().equalsIgnoreCase("N"))

						orderItem.setServiceTax(0.0);

					else if (storeMaster.getParcelServiceTax()
							.equalsIgnoreCase("Y")) {

						orderItem.setServiceTax(menuItem.getServiceTax());

					}

					if (storeMaster.getParcelVat().equalsIgnoreCase("N"))

						orderItem.setVat(0.0);

					else if (storeMaster.getParcelVat().equalsIgnoreCase("Y")) {

						orderItem.setVat(menuItem.getVat());

					}
				}

				else {
					orderItem.setServiceTax(menuItem.getServiceTax());
					orderItem.setVat(menuItem.getVat());
				}

				orderItem.setTotalPriceByItem(itemPrice);

				double discount = 0.0;
				// check for any promotion
				if (menuItem.getPromotionFlag().equalsIgnoreCase("y")) {

					orderItem.setPromotionValue(menuItem.getPromotionValue());
					discount = ((itemPrice) * (menuItem.getPromotionValue())) / 100;
					System.out.println("discount:: " + discount);
					orderItem.setPromotionDiscountAmt(discount);
					totalDiscount = totalDiscount + discount;
				}
				System.out.println("total discount:: " + totalDiscount);

				//
				netPriceEachItem = itemPrice + serviceTaxForThsItem
						+ vatForThsItem - discount;
				orderItem.setNetPrice(netPriceEachItem);
				orderItem.setStoreId(order.getStoreId());
				//

				em.persist(orderItem);

			}

			// total item price to 2 decimal places
			DecimalFormat df = new DecimalFormat("00.00");
			String totalItemPriceWithoutTaxUptoTwoDecimal = df.format(totalItemPriceWithoutTax);
			// calculate total vat upto 2 decimal places
			String totalVatUptoTwoDecimal = df.format(totalVat);
			// calculate total service tax upto 2 decimal places
			String totalServiceTaxUptoTwoDecimal = df.format(totalserviceTax);
			totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal)
					+ Double.parseDouble(totalVatUptoTwoDecimal)
					+ Double.parseDouble(totalServiceTaxUptoTwoDecimal);

			String totalServiceTaxUptoTwoDecimalWithServceChrg = null;
			String totalVatUptoTwoDecimalWithServceChrg = null;
			
			/**
			 * Start :New Logic for Service Charge
			 */
			totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
			totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
			
			/**
			 * End: New Logic for Service Charge
			 */
			// check if there an old bill
			int orderid = order.getId();
			Bill bill = new Bill();
			Bill oldBill = null;
			Payment oldPay = null;
			try {
				TypedQuery<Bill> qryBill = em
						.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id=:orderid", Bill.class);
				qryBill.setParameter("orderid", orderid);
				oldBill = (Bill) qryBill.getSingleResult();
				System.out.println("old bill :" + oldBill);
				serviceChargeRate=oldBill.getServiceChargeRate();
				
				//new calculation for service charge for updating service charge
				if ("Y".equalsIgnoreCase(storeMaster.getServiceChargeFlag())) {
					serviceCharge = (totalItemPriceWithoutTax * serviceChargeRate) / 100;
					if (order.getTable_no().trim().equalsIgnoreCase("0")) {
						if (order.getOrdertype().getId() == 2) {
							serviceChargeRate = 0.0;
							serviceCharge = 0.0;
						}
					}
					totalserviceTax = totalserviceTax + ((totalserviceTax * serviceChargeRate) / 100);
					totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
					totalVat = totalVat + ((totalVat * serviceChargeRate) / 100);
					totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
					totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal) + serviceCharge
							+ Double.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
							+ Double.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg);
				}
				
				double oldtotalVatAmt=0.0;
				double oldtotalServceTaxAmt=0.0;
				double oldtotalServceChrgAmt=0.0;
				double oldtotalitemPrice=0.0;
				Iterator<OrderItem> oldIterator = oldorderItemList.iterator();
				System.out.println("old order item list size while iterating::"+oldorderItemList.size());
				while (oldIterator.hasNext()) {
					OrderItem oldorderItem = (OrderItem) oldIterator.next();
			
					Double itemPrice=oldorderItem.getTotalPriceByItem();
					
					Double itemDiscPer = new Double(oldorderItem.getPromotionValue());
					Double itemDisc = (itemDiscPer * itemPrice) / 100;
					
					Double itemPriceafterdis=itemPrice-itemDisc;
					Double itemserviceChrg = (serviceChargeRate * itemPriceafterdis) / 100;
					
					double vatForThsItem = (oldorderItem.getVat() * (itemPriceafterdis+itemserviceChrg)) / 100;
					
					Double serviceTaxForThsItem = (oldorderItem.getServiceTax() * (itemPriceafterdis+itemserviceChrg)) / 100;
				
					oldtotalVatAmt = oldtotalVatAmt + vatForThsItem;
					oldtotalServceTaxAmt = oldtotalServceTaxAmt+ serviceTaxForThsItem;
					oldtotalServceChrgAmt=oldtotalServceChrgAmt+itemserviceChrg;
					
					oldtotalitemPrice=oldtotalitemPrice+itemPrice;
					
				}
				double oldbillAmt = (oldtotalitemPrice+oldtotalServceChrgAmt + oldtotalVatAmt + oldtotalServceTaxAmt);
				newTotalBillAmt = oldbillAmt + totalBillAmt;
				newTotalDscnt = oldBill.getTotalDiscount() + totalDiscount;
				newTotalBillAmt = newTotalBillAmt - totalDiscount;
				// ===================================================
				/*14/11/2017*/
			
				double grossBillAmt=0.0;
				
					totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
					//totalVatUptoTwoDecimalWithServceChrg = df.format(newVat); //added on 29.05.2018
					totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
					//grossBillAmt = newSubTotal + newVat + newServiceTax;
					//grossBillAmt = newSubTotal + newVat + newServiceTax;
					//grossBillAmtUptoTwoDecimal = df.format(grossBillAmt);//added on 28.05.2018
					grossBillAmtUptoTwoDecimal = df.format(newTotalBillAmt);
					// ====================================================

					// Double grossBillAmt = newTotalBillAmt;

					System.out.println("total gross amt:  " + grossBillAmt);

					// update old bill
					oldBill.setSubTotalAmt(totalItemPriceWithoutTax
							+ oldBill.getSubTotalAmt());
					// oldBill.setBillAmount(newTotalBillAmt);
					oldBill.setBillAmount(Double.parseDouble(grossBillAmtUptoTwoDecimal));
					// }
					if (storeMaster.getServiceChargeText() != null && storeMaster.getServiceChargeText().length() > 0) {
						//if (storeMaster.getServiceChargeText().length() > 0) {
								oldBill.setServiceTaxAmt(Double.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg)
										+ oldtotalServceTaxAmt);
								oldBill.setVatAmt(Double.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
										+ oldtotalVatAmt);
								oldBill.setServiceChargeRate(serviceChargeRate);
								oldBill.setServiceChargeAmt(serviceCharge+oldtotalServceChrgAmt);
						//}
					} else {
						oldBill.setVatAmt(Double
								.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
								+ oldtotalVatAmt);
						oldBill.setServiceTaxAmt(Double
								.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg)
								+ oldtotalServceTaxAmt);
						oldBill.setServiceChargeRate(0.0);
						oldBill.setServiceChargeAmt(0.0);
					}

					oldBill.setTotalDiscount(newTotalDscnt);

				// update old payment object
				TypedQuery<Payment> qryPay = em
						.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid", Payment.class);
				qryPay.setParameter("orderid", orderid);
				oldPay = (Payment) qryPay.getSingleResult();
				String roundOffStatus = storeMaster.getRoundOffTotalAmtStatus();
				String roundOffDouble = storeMaster.getDoubleRoundOff();
				if (roundOffStatus != null
						&& !roundOffStatus.equalsIgnoreCase("")
						&& roundOffStatus.equalsIgnoreCase("Y")) {
					//grossBillAmt = new Double(Math.round(grossBillAmt)); // round //added on 29.05.2018
					grossBillAmt = new Double(Math.round(newTotalBillAmt));
				} else if (roundOffDouble != null
						&& !roundOffDouble.equalsIgnoreCase("")
						&& roundOffDouble.equalsIgnoreCase("Y")) {
					//String formvl = df.format(grossBillAmt);//added on 29.05.2018
					String formvl = df.format(newTotalBillAmt);
					grossBillAmt = Double.parseDouble(formvl);
					grossBillAmt = new Double(
							CommonProerties.roundOffUptoTwoPlacesDouble(
									grossBillAmt, 1)); // round off double
				}
				oldBill.setGrossAmt(grossBillAmt);
				oldPay.setAmount(grossBillAmt);

				oldPay.setAmountToPay(grossBillAmt);

				// reset customer discount to 0.0
				oldBill.setCustomerDiscount(0.0);
				//added for discount mode M
				oldBill.setDiscountPercentage(0.00);
				oldBill.setDiscountReason("");
				em.merge(oldBill);
				em.getTransaction().commit();
				isExistingOrder = true;

			} catch (Exception e) { // new bill
				if ("Y".equalsIgnoreCase(storeMaster.getServiceChargeFlag())) {
					serviceChargeRate = orderType.getServiceChargeValue();
					serviceCharge = (totalItemPriceWithoutTax * serviceChargeRate) / 100;
					if (order.getTable_no().trim().equalsIgnoreCase("0")) {

						if (order.getOrdertype().getId() == 2) {
							serviceChargeRate = 0.0;
							serviceCharge = 0.0;
						}
					}
					totalserviceTax = totalserviceTax + ((totalserviceTax * serviceChargeRate) / 100);
					totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
					totalVat = totalVat + ((totalVat * serviceChargeRate) / 100);
					totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
					totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal) + serviceCharge
							+ Double.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
							+ Double.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg);
				}
				
				totalBillAmt = totalBillAmt - totalDiscount;

				Double grossBillAmt = totalBillAmt;
				System.out.println("total gross amt:  " + grossBillAmt);

				bill.setOrderbill(order);
				bill.setSubTotalAmt(totalItemPriceWithoutTax);
				bill.setBillAmount(totalBillAmt);
				if (storeMaster.getServiceChargeText() != null && storeMaster.getServiceChargeText().length() > 0) {
					//if (storeMaster.getServiceChargeText().length() > 0) {
						//if (storeMaster.getServiceChargeRate() > 0) {  ///modified on 29.05.2018
						if(orderType.getServiceChargeValue()>0){
							bill.setServiceTaxAmt(Double
									.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
							bill.setVatAmt(Double
									.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
							bill.setServiceChargeRate(serviceChargeRate);
							bill.setServiceChargeAmt(serviceCharge);
						} else {
							try {
								bill.setServiceTaxAmt(Double
										.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
							} catch (Exception e2) {
								bill.setServiceTaxAmt(0.0);
							}

							try {
								bill.setVatAmt(Double
										.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
							} catch (Exception e2) {
								bill.setVatAmt(0.0);
							}
							bill.setServiceChargeRate(0.0);
							bill.setServiceChargeAmt(0.0); // no service charge

						}
					//}
				} else {
					
					bill.setServiceTaxAmt(Double
							.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
					bill.setVatAmt(Double
							.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
					bill.setServiceChargeRate(0.0);
					bill.setServiceChargeAmt(0.0); // no service charge

				}
				bill.setTotalDiscount(totalDiscount);
				bill.setDiscountPercentage(0.00);
				bill.setDiscountReason("");
				// bill.setGrossAmt(grossBillAmt);

				// create new payment
				Payment payment = new Payment();
				payment.setOrderPayment(order);
				String roundOffStatus = storeMaster.getRoundOffTotalAmtStatus();
				String roundOffDouble = storeMaster.getDoubleRoundOff();
				if (roundOffStatus != null
						&& !roundOffStatus.equalsIgnoreCase("")
						&& roundOffStatus.equalsIgnoreCase("Y")) {
					grossBillAmt = new Double(Math.round(grossBillAmt)); // round
				}

				else if (roundOffDouble != null
						&& !roundOffDouble.equalsIgnoreCase("")
						&& roundOffDouble.equalsIgnoreCase("Y")) {
					String formvl = df.format(grossBillAmt);
					grossBillAmt = Double.parseDouble(formvl);
					grossBillAmt = new Double(
							CommonProerties.roundOffUptoTwoPlacesDouble(
									grossBillAmt, 1)); // round off double
				}

				bill.setGrossAmt(grossBillAmt);
				bill.setCustomerDiscount(0.0);
				bill.setDiscountPercentage(0.0);
				double roundOffAmt = grossBillAmt - totalBillAmt;
				bill.setRoundOffAmt(roundOffAmt);
				payment.setAmount(grossBillAmt);
				payment.setAmountToPay(grossBillAmt);

				// payment.setPaymentMode("cash");
				// payment.setRemarks("done");
				payment.setStoreId(order.getStoreId());
				// payment.setCreatedBy(order.getCustomers().getName());
				// payment.setCreationDate(curDate);
				em.persist(bill);
				em.persist(payment);
				em.getTransaction().commit();
			}

			//System.out.print("Order data saved successfully...");

			// em.close();
			orderId = order.getId();
			TypedQuery<OrderMaster> oldOrder = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);
			oldOrder.setParameter("orderid", orderId);
			orderCreated = (OrderMaster) oldOrder.getSingleResult();
			em.refresh(orderCreated);
			System.out.println("created order id:: " + orderCreated.getId()+" and order no::"+orderCreated.getOrderNo());
			// check if kitchen print is true for the store
			TypedQuery<StoreMaster> qrySM = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid", StoreMaster.class);
			qrySM.setParameter("storeid", storeId);
			StoreMaster store = (StoreMaster) qrySM.getSingleResult();
			String kitchenPrntFlag = store.getKitchenPrint();
			String kotPrintType = store.getKotPrintType();

			if ("Y".equalsIgnoreCase(kitchenPrntFlag)) {
				if ("item".equalsIgnoreCase(kotPrintType)) {
					new KotPrintItemWise().printKot(isNewOrder, order);
				} 
				else if ("category".equalsIgnoreCase(kotPrintType)) {

					if (store.getId() == 37 || store.getId() == 38) {
						new KotPrint().printKotMulti(isNewOrder, order);
					} else {
						new KotPrint().printKot(isNewOrder, order);
					}
				} 
				else {

					if (store.getId() == 37 || store.getId() == 38) {
						new KotPrint()
								.printKotMultiSubCategoryMergeWithKitchen(isNewOrder, order);
					} else {
						if (store.getId() == 59 || store.getId() == 63) {
							long diffMinutes = 0;
							if (isExistingOrder) {
								SimpleDateFormat format = new SimpleDateFormat(
										"dd/MM/yyyy HH:mm:ss");
								String firstOrderTime = orderCreated.getOrderTime();
								Date formattedDate = format
										.parse(firstOrderTime);
								long currentTime = System.currentTimeMillis();
								long firstOrderTimeMilis = formattedDate
										.getTime();
								long diff = currentTime - firstOrderTimeMilis;
								diffMinutes = diff / (60 * 1000) % 60;

								System.out.print(diffMinutes + " minutes, ");
							}
							if (diffMinutes > 45)
								CommonProerties.longWaitingOrder = true;
						}
						new KotPrint().printKot(isNewOrder, order);
					}
				}
			}

			/*Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			String json = gson.toJson(order, OrderMaster.class);
			LOGGER.info("Order Obj: {}", json);*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		}
		finally {
			if(em != null) em.close();
		}

		//return orderId;
		return orderCreated;
	}
	
	@Override
	public int createAdvOrder(OrderMaster order, HttpServletRequest request)
			throws DAOException {

		List<OrderItem> oldorderItemList=null;
		
		int orderId = 0;
		OrderMaster orderCreated=null;
		Date currDate = new Date();
		Double totalBillAmt = 0.0;
		// Double netBillAmt = 0.0;
		Double totalDiscount = 0.0;
		double newTotalBillAmt = 0.0;
		double newTotalDscnt = 0.0;
		double netPriceEachItem = 0.0;
		boolean isExistingOrder = false;
		double serviceCharge = 0.0;
		double serviceChargeRate = 0.0;
		int storeCustomerId = 0;
		double itemVat=0.0;
		double itemServiceTax=0.0;
		
		boolean isNewOrder = (order.getId() <= 0) ? true : false;
		
		String grossBillAmtUptoTwoDecimal=null;
		// int billId = 0;
		EntityManager em = null;
		// String orderBillId = "";
		Double totalserviceTax = 0.0;
		Double totalVat = 0.0;
		Double totalItemPriceWithoutTax = 0.0;
		StoreMaster storeMaster = new StoreMaster();

		StoreAddressDAO dao = new StoreAddressDAOImpl();
		StoreCustomerDaoImpl storeCustomerDaoImpl = new StoreCustomerDaoImpl();
		
		int storeId = order.getStoreId();
		int orderTypeid = order.getOrdertype().getId();

		try {
	    OrderType orderType = storeCustomerDaoImpl.getOrderType(orderTypeid, storeId);
			// get current date and time, changed for celavi
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String strDate = sdf.format(cal.getTime());
			// System.out.println("Current date in String Format: " + strDate);
			// set date and time
			order.setOrderTime(strDate);
			String orderTime = order.getOrderTime();
			storeMaster = dao.getStoreByStoreId(order.getStoreId());
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// System.out.println("Helloo11111");

			if (order.getId() == -1) {// new
				//order.setOrderDate(currDate);
				order.setFlag("N");
				order.setBillReqStatus("No");
				order.setSplitBill("N");
				order.setKotPrintStatus("N");
				order.setKotPrintCount(0);
				order.setBillPrintcount(0);
				order.setCreditFlag("N");
				
				// start calculate time of order
				/*String orderDtNTime = order.getOrderTime();
				if (orderDtNTime != null && orderDtNTime.length() > 0) {
					String[] tempURL1 = orderDtNTime.split(" ");
					String time = tempURL1[1];
					String[] timefull = time.split(":");
					String hr = timefull[0];
					String mins = timefull[1];
					String hrMins = hr + ":" + mins;

					order.setTime(hrMins);
				}*/
				// end calculate time of order
				order.setId(0);

				try {
					if (order.getCustomerContact().equals("")
							&& order.getCustomerName().equals("")) {
						storeCustomerId = 0;
					} /*else if (order.getCustomerContact().equals("")) {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					}*//*
					 * else if(order.getCustomerName().equals("")){
					 * storeCustomerId = updateOrderByStoreCustomerId(order); }
					 */else if (order.getStoreCustomerId() > 0) {
						// storeCustomerId = order.getStoreCustomerId();
						storeCustomerId = editOrderByStoreCustomerId(order);
					} else {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					}
					// storeCustomerId=155;
					LOGGER.info("Successful");
				} catch (Exception e) {
					e.printStackTrace();
					storeCustomerId = order.getStoreCustomerId();
				}
				order.setStoreCustomerId(storeCustomerId);
				em.persist(order);// order id
			}
			else { //if (order.getId() != -1) {// old order special item update

				System.out.println("special note:  " + order.getSpecialNote()
						+ "Store Customer ID:  " + order.getStoreCustomerId());
				OrderMaster oldOrder = null;
				
				try {
					if (order.getCustomerContact().equals("")
							&& order.getCustomerName().equals("")) {
						storeCustomerId = 0;
					} else if (order.getStoreCustomerId() == 0
							&& !(order.getCustomerName().equals(""))) {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					} else if (order.getStoreCustomerId() == 0
							&& !(order.getCustomerContact().equals(""))) {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					} else {
						storeCustomerId = order.getStoreCustomerId();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					int orderid = order.getId();
					Query qryOldOrder = em
							.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid");
					qryOldOrder.setParameter("orderid", orderid);
					oldOrder = (OrderMaster) qryOldOrder.getSingleResult();
					oldorderItemList = new ArrayList<OrderItem>();
					oldorderItemList = oldOrder.getOrderitem();
					System.out.println("old order item list size while getting::"+oldorderItemList.size());
					oldOrder.setCustomerName(order.getCustomerName());
					oldOrder.setCustomerContact(order.getCustomerContact());
					oldOrder.setDeliveryAddress(order.getDeliveryAddress());
					oldOrder.setDeliveryPersonName(order
							.getDeliveryPersonName());
					oldOrder.setNoOfPersons(order.getNoOfPersons());
					oldOrder.setCustVatRegNo(order.getCustVatRegNo());
					oldOrder.setState(order.getState());
					oldOrder.setLocation(order.getLocation());
					oldOrder.setHouseNo(order.getHouseNo());
					oldOrder.setStoreCustomerId(storeCustomerId);
					oldOrder.setStreet(order.getStreet());
					order.setOrderNo(oldOrder.getOrderNo());
					oldOrder.setOrderDate(order.getOrderDate());
					oldOrder.setTime(order.getTime());
					oldOrder.setTable_no(order.getTable_no());
					try {
						oldOrder.setAnniversary(order.getAnniversary());
					} catch (Exception e) {
						oldOrder.setAnniversary(null);
					}
					try {
						oldOrder.setDob(order.getDob());
					} catch (Exception e) {
						oldOrder.setDob(null);
					}
					try {
						if (order.getSpecialNote().trim().length() > 0) {
							oldOrder.setSpecialNote(order.getSpecialNote());
						}
					} catch (Exception e1) {
						oldOrder.setSpecialNote("");
					}
					/*
					 * Update Customer While Edit
					 */
					// editOrderByStoreCustomerId(order);
					int storeCusId = order.getStoreCustomerId();
					String homeDeliveryCustName = "";
					String homeDeliveryCustAddr = "";
					String homeDeliveryCustPh = "";
					// String homeDeliveryPersonName = "";
					String homeDeliveryLocation = "";
					String homeDeliveryStreet = "";
					String homeDeliveryHouseNo = "";
					String custVatRegNo = "";
					String homeDeliveryState = "";
					String waiterName = "";
					/** New Fields */
					String carNo = "";
					Date anniversary_date = new Date();
					Date dob = new Date();
					TypedQuery<StoreCustomer> qrySC = em
							.createQuery("SELECT s FROM StoreCustomer s WHERE  s.id=:storeCusId", StoreCustomer.class);
					qrySC.setParameter("storeCusId", storeCusId);
					StoreCustomer store = (StoreCustomer) qrySC
							.getSingleResult();

					// if (order.getTable_no().equalsIgnoreCase("0")&&
					// store.getParcelAddress().equalsIgnoreCase("Y")) {
					homeDeliveryCustName = order.getCustomerName();
					homeDeliveryCustAddr = order.getDeliveryAddress();
					homeDeliveryCustPh = order.getCustomerContact();
					// homeDeliveryPersonName = order.getDeliveryPersonName();
					homeDeliveryLocation = order.getLocation();
					homeDeliveryStreet = order.getStreet();
					homeDeliveryHouseNo = order.getHouseNo();
					homeDeliveryState = order.getState();
					custVatRegNo = order.getCustVatRegNo();
					carNo = order.getCar_no();
					try {
						anniversary_date = order.getAnniversary();
					} catch (NullPointerException e) {
						anniversary_date = null;
					}
					try {
						dob = order.getDob();
					} catch (NullPointerException e) {
						dob = null;
					}
					waiterName = order.getWaiterName();
					// }

					/** Store data in StoreCustomer */
					// store.setId(storeCusId);
					store.setName(homeDeliveryCustName.toString().trim());
					store.setAddress(homeDeliveryCustAddr.toString());
					store.setContactNo(homeDeliveryCustPh);
					store.setLocation(homeDeliveryLocation.toString());
					store.setStreet(homeDeliveryStreet);
					store.setHouse_no(homeDeliveryHouseNo);
					store.setCust_vat_reg_no(custVatRegNo);
					store.setAnniversary(anniversary_date);
					store.setCar_no(carNo);
					store.setDob(dob);
					store.setStoreId(order.getStoreId());
					store.setCreditCustomer("N");
					store.setDeleteFlag("N");
					store.setState(order.getState());
					store.setState(homeDeliveryState);
					store.setWaiterName(waiterName);
					em.merge(store);
					// em.persist(storeCustomer);
					/*
					 * End Update
					 */
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					em.merge(oldOrder);
				}
			}

      order.setOrdertype(orderType);

      List<OrderItem> orderItemList = order.getOrderitem();
          if(orderItemList.size()>0)
          {
			List<Integer> menuItemIDs = new ArrayList<>();
			for(OrderItem orderItem : orderItemList) {
			  menuItemIDs.add(orderItem.getItem().getId());
			}
			
			List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
			
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
			
			Iterator<OrderItem> iterator = orderItemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();

				OrderMaster master = new OrderMaster();
				master.setId(order.getId());
				orderItem.setOrders(master);
				orderItem.setOrderTime(orderTime);
				// orderItem.setSpecialNote(order.getSpecialNote());
				orderItem.setOrdertype(order.getOrdertype().getId());
				orderItem.setKitchenOut("N");

				// calculate total price for each item
				MenuItem menuItem = menuItemMap.get(orderItem.getItem().getId());
				
				Double itemRate=menuItem.getPrice();
				if(orderItem.getRate()>0)
				{
					itemRate=orderItem.getRate();
				}
				
				Double itemPrice = Double.parseDouble((orderItem.getQuantityOfItem())) * itemRate;

				// service tax calculation for each item
				itemServiceTax = menuItem.getServiceTax();
				Double itemDisc = 0.0;
				Double serviceTaxForThsItem = 0.0;
				Double itemDiscPer = 0.0;

				if (menuItem.getPromotionFlag().equalsIgnoreCase("Y")) {
					itemDiscPer = new Double(menuItem.getPromotionValue());

				}
				itemDisc = (itemDiscPer * itemPrice) / 100;
				serviceTaxForThsItem = (itemServiceTax * (itemPrice - itemDisc)) / 100;
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {

					if (storeMaster.getParcelServiceTax().equalsIgnoreCase("N")) {

						serviceTaxForThsItem = 0.0;

					}
				}

				totalserviceTax = totalserviceTax + serviceTaxForThsItem;

				// vat calculation for each item
				itemVat = menuItem.getVat();
				Double vatForThsItem = (itemVat * (itemPrice - itemDisc)) / 100;
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {

					if (storeMaster.getParcelVat().equalsIgnoreCase("N")) {

						vatForThsItem = 0.0;

					}
				}

				totalVat = totalVat + vatForThsItem;


				// calculate total item price without tax
				totalItemPriceWithoutTax = totalItemPriceWithoutTax + itemPrice;

				// set item price
				//orderItem.setRate(menuItem.getPrice());
				orderItem.setRate(itemRate);
				// set item service tax and vat for parcel (for marufaz)
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {

					if (storeMaster.getParcelServiceTax().equalsIgnoreCase("N"))

						orderItem.setServiceTax(0.0);

					else if (storeMaster.getParcelServiceTax()
							.equalsIgnoreCase("Y")) {

						orderItem.setServiceTax(menuItem.getServiceTax());

					}

					if (storeMaster.getParcelVat().equalsIgnoreCase("N"))

						orderItem.setVat(0.0);

					else if (storeMaster.getParcelVat().equalsIgnoreCase("Y")) {

						orderItem.setVat(menuItem.getVat());

					}
				}

				else {
					orderItem.setServiceTax(menuItem.getServiceTax());
					orderItem.setVat(menuItem.getVat());
				}

				orderItem.setTotalPriceByItem(itemPrice);

				double discount = 0.0;
				// check for any promotion
				if (menuItem.getPromotionFlag().equalsIgnoreCase("y")) {

					orderItem.setPromotionValue(menuItem.getPromotionValue());
					discount = ((itemPrice) * (menuItem.getPromotionValue())) / 100;
					System.out.println("discount:: " + discount);
					orderItem.setPromotionDiscountAmt(discount);
					totalDiscount = totalDiscount + discount;
				}
				System.out.println("total discount:: " + totalDiscount);

				//
				netPriceEachItem = itemPrice + serviceTaxForThsItem
						+ vatForThsItem - discount;
				orderItem.setNetPrice(netPriceEachItem);
				orderItem.setStoreId(order.getStoreId());
				//

				em.persist(orderItem);

			}
          }
			// total item price to 2 decimal places
			DecimalFormat df = new DecimalFormat("00.00");
			String totalItemPriceWithoutTaxUptoTwoDecimal = df.format(totalItemPriceWithoutTax);
			// calculate total vat upto 2 decimal places
			String totalVatUptoTwoDecimal = df.format(totalVat);
			// calculate total service tax upto 2 decimal places
			String totalServiceTaxUptoTwoDecimal = df.format(totalserviceTax);
			totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal)
					+ Double.parseDouble(totalVatUptoTwoDecimal)
					+ Double.parseDouble(totalServiceTaxUptoTwoDecimal);

			String totalServiceTaxUptoTwoDecimalWithServceChrg = null;
			String totalVatUptoTwoDecimalWithServceChrg = null;
			
			/**
			 * Start :New Logic for Service Charge
			 */
			totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
			totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
			
			/**
			 * End: New Logic for Service Charge
			 */
			// check if there an old bill
			int orderid = order.getId();
			Bill bill = new Bill();
			Bill oldBill = null;
			Payment oldPay = null;
			try {
				TypedQuery<Bill> qryBill = em
						.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id=:orderid", Bill.class);
				qryBill.setParameter("orderid", orderid);
				oldBill = (Bill) qryBill.getSingleResult();
				System.out.println("old bill :" + oldBill);
				serviceChargeRate=oldBill.getServiceChargeRate();
				
				//new calculation for service charge for updating service charge
				if ("Y".equalsIgnoreCase(storeMaster.getServiceChargeFlag())) {
					serviceCharge = (totalItemPriceWithoutTax * serviceChargeRate) / 100;
					if (order.getTable_no().trim().equalsIgnoreCase("0")) {
						if (order.getOrdertype().getId() == 2) {
							serviceChargeRate = 0.0;
							serviceCharge = 0.0;
						}
					}
					totalserviceTax = totalserviceTax + ((totalserviceTax * serviceChargeRate) / 100);
					totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
					totalVat = totalVat + ((totalVat * serviceChargeRate) / 100);
					totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
					totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal) + serviceCharge
							+ Double.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
							+ Double.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg);
				}
				
				double oldtotalVatAmt=0.0;
				double oldtotalServceTaxAmt=0.0;
				double oldtotalServceChrgAmt=0.0;
				double oldtotalitemPrice=0.0;
				Iterator<OrderItem> oldIterator = oldorderItemList.iterator();
				System.out.println("old order item list size while iterating::"+oldorderItemList.size());
				while (oldIterator.hasNext()) {
					OrderItem oldorderItem = (OrderItem) oldIterator.next();
			
					Double itemPrice=oldorderItem.getTotalPriceByItem();
					
					Double itemDiscPer = new Double(oldorderItem.getPromotionValue());
					Double itemDisc = (itemDiscPer * itemPrice) / 100;
					
					Double itemPriceafterdis=itemPrice-itemDisc;
					Double itemserviceChrg = (serviceChargeRate * itemPriceafterdis) / 100;
					
					double vatForThsItem = (oldorderItem.getVat() * (itemPriceafterdis+itemserviceChrg)) / 100;
					
					Double serviceTaxForThsItem = (oldorderItem.getServiceTax() * (itemPriceafterdis+itemserviceChrg)) / 100;
				
					oldtotalVatAmt = oldtotalVatAmt + vatForThsItem;
					oldtotalServceTaxAmt = oldtotalServceTaxAmt+ serviceTaxForThsItem;
					oldtotalServceChrgAmt=oldtotalServceChrgAmt+itemserviceChrg;
					
					oldtotalitemPrice=oldtotalitemPrice+itemPrice;
					
				}
				double oldbillAmt = (oldtotalitemPrice+oldtotalServceChrgAmt + oldtotalVatAmt + oldtotalServceTaxAmt);
				newTotalBillAmt = oldbillAmt + totalBillAmt;
				newTotalDscnt = oldBill.getTotalDiscount() + totalDiscount;
				newTotalBillAmt = newTotalBillAmt - totalDiscount;
				// ===================================================
				/*14/11/2017*/
			
				double grossBillAmt=0.0;
				
					totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
					//totalVatUptoTwoDecimalWithServceChrg = df.format(newVat); //added on 29.05.2018
					totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
					//grossBillAmt = newSubTotal + newVat + newServiceTax;
					//grossBillAmt = newSubTotal + newVat + newServiceTax;
					//grossBillAmtUptoTwoDecimal = df.format(grossBillAmt);//added on 28.05.2018
					grossBillAmtUptoTwoDecimal = df.format(newTotalBillAmt);
					// ====================================================

					// Double grossBillAmt = newTotalBillAmt;

					System.out.println("total gross amt:  " + grossBillAmt);

					// update old bill
					oldBill.setSubTotalAmt(totalItemPriceWithoutTax
							+ oldBill.getSubTotalAmt());
					// oldBill.setBillAmount(newTotalBillAmt);
					oldBill.setBillAmount(Double.parseDouble(grossBillAmtUptoTwoDecimal));
					// }
					if (storeMaster.getServiceChargeText() != null && storeMaster.getServiceChargeText().length() > 0) {
						//if (storeMaster.getServiceChargeText().length() > 0) {
								oldBill.setServiceTaxAmt(Double.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg)
										+ oldtotalServceTaxAmt);
								oldBill.setVatAmt(Double.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
										+ oldtotalVatAmt);
								oldBill.setServiceChargeRate(serviceChargeRate);
								oldBill.setServiceChargeAmt(serviceCharge+oldtotalServceChrgAmt);
						//}
					} else {
						oldBill.setVatAmt(Double
								.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
								+ oldtotalVatAmt);
						oldBill.setServiceTaxAmt(Double
								.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg)
								+ oldtotalServceTaxAmt);
						oldBill.setServiceChargeRate(0.0);
						oldBill.setServiceChargeAmt(0.0);
					}

					oldBill.setTotalDiscount(newTotalDscnt);

				// update old payment object
				TypedQuery<Payment> qryPay = em
						.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid", Payment.class);
				qryPay.setParameter("orderid", orderid);
				oldPay = (Payment) qryPay.getSingleResult();
				String roundOffStatus = storeMaster.getRoundOffTotalAmtStatus();
				String roundOffDouble = storeMaster.getDoubleRoundOff();
				if (roundOffStatus != null
						&& !roundOffStatus.equalsIgnoreCase("")
						&& roundOffStatus.equalsIgnoreCase("Y")) {
					//grossBillAmt = new Double(Math.round(grossBillAmt)); // round //added on 29.05.2018
					grossBillAmt = new Double(Math.round(newTotalBillAmt));
				} else if (roundOffDouble != null
						&& !roundOffDouble.equalsIgnoreCase("")
						&& roundOffDouble.equalsIgnoreCase("Y")) {
					//String formvl = df.format(grossBillAmt);//added on 29.05.2018
					String formvl = df.format(newTotalBillAmt);
					grossBillAmt = Double.parseDouble(formvl);
					grossBillAmt = new Double(
							CommonProerties.roundOffUptoTwoPlacesDouble(
									grossBillAmt, 1)); // round off double
				}
				oldBill.setGrossAmt(grossBillAmt);
				oldPay.setAmount(grossBillAmt);

				oldPay.setAmountToPay(grossBillAmt);

				// reset customer discount to 0.0
				oldBill.setCustomerDiscount(0.0);
				//added for discount mode M
				oldBill.setDiscountPercentage(0.00);
				oldBill.setDiscountReason("");
				em.merge(oldBill);
				em.getTransaction().commit();
				isExistingOrder = true;

			} catch (Exception e) { // new bill
				if ("Y".equalsIgnoreCase(storeMaster.getServiceChargeFlag())) {
					serviceChargeRate = orderType.getServiceChargeValue();
					serviceCharge = (totalItemPriceWithoutTax * serviceChargeRate) / 100;
					if (order.getTable_no().trim().equalsIgnoreCase("0")) {

						if (order.getOrdertype().getId() == 2) {
							serviceChargeRate = 0.0;
							serviceCharge = 0.0;
						}
					}
					totalserviceTax = totalserviceTax + ((totalserviceTax * serviceChargeRate) / 100);
					totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
					totalVat = totalVat + ((totalVat * serviceChargeRate) / 100);
					totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
					totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal) + serviceCharge
							+ Double.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
							+ Double.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg);
				}
				
				totalBillAmt = totalBillAmt - totalDiscount;

				Double grossBillAmt = totalBillAmt;
				System.out.println("total gross amt:  " + grossBillAmt);

				bill.setOrderbill(order);
				bill.setSubTotalAmt(totalItemPriceWithoutTax);
				bill.setBillAmount(totalBillAmt);
				if (storeMaster.getServiceChargeText() != null && storeMaster.getServiceChargeText().length() > 0) {
					//if (storeMaster.getServiceChargeText().length() > 0) {
						//if (storeMaster.getServiceChargeRate() > 0) {  ///modified on 29.05.2018
						if(orderType.getServiceChargeValue()>0){
							bill.setServiceTaxAmt(Double
									.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
							bill.setVatAmt(Double
									.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
							bill.setServiceChargeRate(serviceChargeRate);
							bill.setServiceChargeAmt(serviceCharge);
						} else {
							try {
								bill.setServiceTaxAmt(Double
										.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
							} catch (Exception e2) {
								bill.setServiceTaxAmt(0.0);
							}

							try {
								bill.setVatAmt(Double
										.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
							} catch (Exception e2) {
								bill.setVatAmt(0.0);
							}
							bill.setServiceChargeRate(0.0);
							bill.setServiceChargeAmt(0.0); // no service charge

						}
					//}
				} else {
					
					bill.setServiceTaxAmt(Double
							.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
					bill.setVatAmt(Double
							.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
					bill.setServiceChargeRate(0.0);
					bill.setServiceChargeAmt(0.0); // no service charge

				}
				bill.setTotalDiscount(totalDiscount);
				bill.setDiscountPercentage(0.00);
				bill.setDiscountReason("");
				// bill.setGrossAmt(grossBillAmt);

				// create new payment
				Payment payment = new Payment();
				payment.setOrderPayment(order);
				String roundOffStatus = storeMaster.getRoundOffTotalAmtStatus();
				String roundOffDouble = storeMaster.getDoubleRoundOff();
				if (roundOffStatus != null
						&& !roundOffStatus.equalsIgnoreCase("")
						&& roundOffStatus.equalsIgnoreCase("Y")) {
					grossBillAmt = new Double(Math.round(grossBillAmt)); // round
				}

				else if (roundOffDouble != null
						&& !roundOffDouble.equalsIgnoreCase("")
						&& roundOffDouble.equalsIgnoreCase("Y")) {
					String formvl = df.format(grossBillAmt);
					grossBillAmt = Double.parseDouble(formvl);
					grossBillAmt = new Double(
							CommonProerties.roundOffUptoTwoPlacesDouble(
									grossBillAmt, 1)); // round off double
				}

				bill.setGrossAmt(grossBillAmt);
				bill.setCustomerDiscount(0.0);
				bill.setDiscountPercentage(0.0);
				double roundOffAmt = grossBillAmt - totalBillAmt;
				bill.setRoundOffAmt(roundOffAmt);
				payment.setAmount(grossBillAmt);
				payment.setAmountToPay(grossBillAmt);

				// payment.setPaymentMode("cash");
				// payment.setRemarks("done");
				payment.setStoreId(order.getStoreId());
				// payment.setCreatedBy(order.getCustomers().getName());
				// payment.setCreationDate(curDate);
				em.persist(bill);
				em.persist(payment);
				em.getTransaction().commit();
			}

			//System.out.print("Order data saved successfully...");

			// em.close();
			orderId = order.getId();
			/*TypedQuery<OrderMaster> oldOrder = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);
			oldOrder.setParameter("orderid", orderId);
			orderCreated = (OrderMaster) oldOrder.getSingleResult();
			em.refresh(orderCreated);
			System.out.println("created order id:: " + orderCreated.getId()+" and order no::"+orderCreated.getOrderNo());*/
			System.out.println("created adv order id:: " + orderId);

			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		}
		finally {
			if(em != null) em.close();
		}

		return orderId;
		//return orderCreated;
	}

	@Override
	public int createOrderAdmin(OrderMaster order) throws DAOException {
		// OrderMaster orders=new OrderMaster();
		int orderId = 0;
		Date currDate = new Date();
		DateFormat formatter;
		Double totalBillAmt = 0.0;
		Double totalDiscount = 0.0;
		EntityManager em = null;
		User loggdUser = null;
		String orderTime = "";
		Double netBillAmt = 0.0;
		Double totalserviceTax = 0.0;
		Double totalVat = 0.0;
		Double totalItemPriceWithoutTax = 0.0;
		double newTotalBillAmt = 0.0;
		double newTotalDscnt = 0.0;
		double netPriceEachItem = 0.0;
		

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//System.out.println("Helloo11111");

			if (order.getId() == -1) {// new
				order.setOrderDate(currDate);
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				orderTime = formatter.format(currDate);
				order.setOrderTime(orderTime);
				order.setBillReqStatus("No");
				order.setFlag("N");
				order.setSplitBill("N");
				// start calculate time of order
				String orderDtNTime = order.getOrderTime();
				if (orderDtNTime != null && orderDtNTime.length() > 0) {
					String[] tempURL1 = orderDtNTime.split(" ");
					String time = tempURL1[1];
					String[] timefull = time.split(":");
					String hr = timefull[0];
					String mins = timefull[1];
					String hrMins = hr + ":" + mins;

					order.setTime(hrMins);
				}
				// end calculate time of order
				order.setId(0);
				//System.out.println("Helloo333333");
				loggdUser = (User) context.getExternalContext().getSessionMap()
						.get("loggeduser");
				try {
					if (loggdUser != null) {
						String conctNo = loggdUser.getContact();
						Customer customer = userDAO.getCustomerByContactNo(conctNo);
						order.setCustomers(customer);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				em.persist(order);// order id
			}
			List<OrderItem> orderItemList = new ArrayList<OrderItem>();
			List<OrderMaster> ordList = order.getOrderList();
			Iterator<OrderMaster> ordIterator = ordList.iterator();
			while (ordIterator.hasNext()) {
				OrderMaster orderMaster = (OrderMaster) ordIterator.next();
				orderItemList.add(orderMaster.getOrderItem1());

			}

			Iterator<OrderItem> iterator = orderItemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();
				OrderMaster master = new OrderMaster();
				master.setId(order.getId());
				orderItem.setOrders(master);
				orderItem.setOrdertype(order.getOrdertype().getId());
				orderItem.setOrderTime(orderTime);
				orderItem.setKitchenOut("N");

				// calculate total price for each item
				MenuItem item = orderItem.getItem();
				Double itemPrice = Integer.parseInt((orderItem
						.getQuantityOfItem())) * (item.getPrice());

				// service tax calculation for each item
				Double itemServiceTax = item.getServiceTax();
				Double itemDiscPer = new Double(item.getPromotionValue());
				Double itemDisc = (itemDiscPer * itemPrice) / 100;
				Double serviceTaxForThsItem = (itemServiceTax * (itemPrice - itemDisc)) / 100;
				totalserviceTax = totalserviceTax + serviceTaxForThsItem;

				// vat calculation for each item
				Double itemVat = item.getVat();
				Double vatForThsItem = (itemVat * (itemPrice - itemDisc)) / 100;
				totalVat = totalVat + vatForThsItem;

				// calculate item price with taxes
				Double itemPriceWithTax = itemPrice + vatForThsItem
						+ serviceTaxForThsItem;

				DecimalFormat df = new DecimalFormat("00.00");

				String itemPrceWithTax = df.format(itemPriceWithTax);

				itemPriceWithTax = Double.parseDouble(itemPrceWithTax);

				System.out.println("Item price with TAx::::  "
						+ itemPrceWithTax);

				// calculate total item price without tax
				totalItemPriceWithoutTax = totalItemPriceWithoutTax + itemPrice;

				/*
				 * // add each item price with taxes to total bill totalBillAmt
				 * = totalBillAmt + itemPriceWithTax;
				 * System.out.println("item price:  " +
				 * itemPrice.doubleValue());
				 */

				orderItem.setTotalPriceByItem(itemPrice);

				// check for any promotion
				if (item.getPromotionFlag().equalsIgnoreCase("y")) {

					orderItem.setPromotionValue(item.getPromotionValue());
					double discount = ((itemPrice) * (item.getPromotionValue())) / 100;
					System.out.println("discount:: " + discount);
					orderItem.setPromotionDiscountAmt(discount);
					totalDiscount = totalDiscount + discount;
				}
				System.out.println("total discount:: " + totalDiscount);

				//
				netPriceEachItem = itemPrice + serviceTaxForThsItem
						+ vatForThsItem;
				orderItem.setNetPrice(netPriceEachItem);
				//

				em.persist(orderItem);

			}

			// total item price to 2 decimal places
			DecimalFormat df = new DecimalFormat("00.00");
			String totalItemPriceWithoutTaxUptoTwoDecimal = df
					.format(totalItemPriceWithoutTax);
			// calculate total vat upto 2 decimal places
			String totalVatUptoTwoDecimal = df.format(totalVat);
			// calculate total service tax upto 2 decimal places
			String totalServiceTaxUptoTwoDecimal = df.format(totalserviceTax);

			totalBillAmt = Double
					.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal)
					+ Double.parseDouble(totalVatUptoTwoDecimal)
					+ Double.parseDouble(totalServiceTaxUptoTwoDecimal);

			// netBillAmt = totalBillAmt - totalDiscount;

			/*
			 * Double grossBillAmt = netBillAmt;
			 * System.out.println("total gross amt:  " + grossBillAmt);
			 */

			// check if there an old bill
			int orderid = order.getId();
			Bill bill = new Bill();
			Bill oldBill = null;
			Payment oldPay = null;
			try {
				Query qryBill = em
						.createQuery("SELECT b FROM Bill b WHERE b.orderbill.id=:orderid");
				qryBill.setParameter("orderid", orderid);
				oldBill = (Bill) qryBill.getSingleResult();
				//System.out.println("old bill :" + oldBill);
				newTotalBillAmt = oldBill.getBillAmount() + totalBillAmt;
				newTotalDscnt = oldBill.getTotalDiscount() + totalDiscount;
				netBillAmt = newTotalBillAmt - newTotalDscnt;

				Double grossBillAmt = netBillAmt;

				/*
				 * double olbBillamt = oldBill.getBillAmount(); double oldGross
				 * = oldBill.getGrossAmt();
				 */

				// update old bill
				oldBill.setBillAmount(newTotalBillAmt);
				oldBill.setVatAmt(Double.parseDouble(totalVatUptoTwoDecimal)
						+ oldBill.getVatAmt());
				oldBill.setServiceTaxAmt(Double
						.parseDouble(totalServiceTaxUptoTwoDecimal)
						+ oldBill.getServiceTaxAmt());
				oldBill.setTotalDiscount(newTotalDscnt);
				oldBill.setGrossAmt(grossBillAmt);

				// update old payment object
				Query qryPay = em
						.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderid");
				qryPay.setParameter("orderid", orderid);
				oldPay = (Payment) qryPay.getSingleResult();
				oldPay.setAmount(grossBillAmt);
				oldPay.setAmountToPay(grossBillAmt);

				em.merge(oldBill);
				em.getTransaction().commit();

			} catch (Exception e) {
				// new bill
				netBillAmt = totalBillAmt - totalDiscount;
				Double grossBillAmt = netBillAmt;

				bill.setOrderbill(order);
				bill.setBillAmount(totalBillAmt);
				bill.setServiceTaxAmt(Double
						.parseDouble(totalServiceTaxUptoTwoDecimal));
				bill.setVatAmt(Double.parseDouble(totalVatUptoTwoDecimal));
				bill.setTotalDiscount(totalDiscount);
				bill.setGrossAmt(grossBillAmt);

				// create new payment
				Payment payment = new Payment();
				payment.setOrderPayment(order);
				payment.setAmount(grossBillAmt);
				payment.setAmountToPay(grossBillAmt);
				// payment.setPaymentMode("cash");
				// payment.setRemarks("done");
				payment.setStoreId(order.getStoreId());

				em.persist(bill);
				em.persist(payment);
				em.getTransaction().commit();
			}

			System.out.print("Order data saved successfully...");

			// em.close();
			orderId = order.getId();

			/****************
			 * start kitchen print************************
			 * 
			 */
			// if kitchen admin print is true
			/*
			DEAD CODE - Kalyan
			try {
				if (false) {
					int storeId = order.getStoreId();
					String s = "\\u0020"; // for space
					char c = (char) Integer.parseInt(s.substring(2), 16);
					// get store name
					Query qry1 = em
							.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid");
					qry1.setParameter("storeid", storeId);
					StoreMaster store = (StoreMaster) qry1.getSingleResult();
					String strName = store.getStoreName();
					String kitchenPrnt = "";
					StringBuffer kitchenHdrStrng = new StringBuffer();
					StringBuffer kitchenItemStrng = new StringBuffer();
					StringBuffer kitchenEndStrng = new StringBuffer();

					// String finalItemName = "";
					String upTo3Characters = strName.substring(0,
							Math.min(strName.length(), 3));

					kitchenHdrStrng.append(strName);
					kitchenHdrStrng.append("\n");
					// get order time
					String ordeTime = order.getOrderTime();

					String[] tempURL = ordeTime.split("/");
					String day = tempURL[0];
					String mnth = tempURL[1];
					String dayMnth = day + "/" + mnth;
					String[] tempURL1 = ordeTime.split(" ");
					String time = tempURL1[1];
					System.out.println("dayMnth.." + dayMnth);
					System.out.println("time.." + time);
					String[] timefull = time.split(":");
					String hr = timefull[0];
					String mins = timefull[1];
					String hrMins = hr + ":" + mins;
					System.out.println("hr mins:::  " + hrMins);
					String dtTime = dayMnth + " " + hrMins;
					System.out.println("dttime:: " + dtTime);

					kitchenHdrStrng.append(dtTime);
					kitchenHdrStrng.append(" ");
					// get order taken by person info
					Customer customer = order.getCustomers();
					int custId = customer.getId();
					kitchenHdrStrng.append("(");
					if (custId > 0) {
						kitchenHdrStrng.append("" + custId);
					}
					kitchenHdrStrng.append(")");
					kitchenHdrStrng.append("\n");
					kitchenHdrStrng.append("Order No:");
					kitchenHdrStrng.append("" + orderId);
					kitchenHdrStrng.append(",");
					kitchenHdrStrng.append("T-");
					// get table no.
					String tblNo = order.getTable_no();
					if (tblNo != null && tblNo.length() > 0) {
						kitchenHdrStrng.append(tblNo);
					} else {
						kitchenHdrStrng.append("");
					}
					kitchenHdrStrng.append("\n\n");
					// get items from order
					List<OrderMaster> ordrLst = order.getOrderList();
					Iterator<OrderMaster> ordrItr = ordrLst.iterator();
					while (ordrItr.hasNext()) {
						OrderMaster orderMaster = (OrderMaster) ordrItr.next();
						OrderItem item = orderMaster.getOrderItem1();
						MenuItem menuItem = item.getItem();
						String itemName = menuItem.getName();

						int itemLength = itemName.length();

						if (itemLength > 15)
							itemName = itemName.substring(0,
									Math.min(itemName.length(), 15));

						itemLength = itemName.length();
						StringBuffer itemNameStrng = new StringBuffer();
						if (itemLength < 16) {
							itemNameStrng.append(itemName);
							int remLength = 15 - itemLength;
							for (int i = 1; i <= remLength; i++) {
								itemNameStrng.append(c);
								itemNameStrng.append(c);

							}

							itemName = itemNameStrng.toString();
						}

						String quantity = item.getQuantityOfItem();
						itemName = String
								.format("%-35s:%s", itemName, quantity);
						kitchenItemStrng.append(itemName);

						kitchenItemStrng.append("\n");
					}
					kitchenEndStrng.append("................................");
					// String s = StringEscapeUtils.unescapeJava("\\u2701\\n");

					int orientation = 1;
					// String orderId="1752";
					// String data =
					// "PIZA HUT:2015-01-15::16.18\nOrder No:1752,T-14\n\nChowin: 3\nChicken Tikka: 2\n--------------------";
					kitchenPrnt = kitchenHdrStrng.toString()
							+ kitchenItemStrng.toString()
							+ kitchenEndStrng.toString();
					System.out.println(kitchenPrnt);
					new SilentPrintBill().printByRequest(orientation, orderId
							+ "", kitchenPrnt, request);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			*/

			/****************
			 * end kitchen print************************
			 * 
			 */
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		return orderId;
	}

	@Override
	public MenuItem getItemPriceById(int itemId) throws DAOException {

		MenuItem menuItem = new MenuItem();
		EntityManager em = null;
		try {
			System.out.println("in OrdersDAOImpl getItemPriceById....");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT m FROM MenuItem m WHERE m.id=:param");
			qry.setParameter("param", itemId);
			menuItem = (MenuItem) qry.getSingleResult();
			System.out.println("order 555");
			em.getTransaction().commit();
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getItemPriceById", e);

		} finally {
			if(em != null) em.close();
		}
		return menuItem;
	}

	@Override
	public MenuItem getItemNameById(int itemId) throws DAOException {

		MenuItem menuItem = getItemPriceById(itemId);
		return menuItem;
	}

	@Override
	public List<OrderMaster> getAllOrders(int storeId) throws DAOException {

		List<OrderMaster> orderMasterList = null;
		Date currDate = new Date();
		EntityManager em = null;
		try {
			System.out.println("in OrdersDAOImpl:getAllOrders: " + storeId);

			System.out.println("curr date:" + currDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// DateFormat dateFormat = new
			// SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
			String curDate = dateFormat.format(currDate);
			System.out.println("date is:" + curDate);
			Date date = dateFormat.parse(curDate);
			System.out.println("today date:" + date);

			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.storeId=:storeid and c.flag= 'N' and c.orderDate=:currDate and c.cancel='N'", OrderMaster.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("currDate", date);
			orderMasterList = qry.getResultList();

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getAllOrders", e);

		} finally {
			if(em != null) em.close();
		}

		return orderMasterList;
	}

	@Override
	public List<OrderMaster> getOrdersByDate(OrderMaster fromOrder,
			OrderMaster toOrder) throws DAOException {

		List<OrderMaster> orderMasterList = null;
		Date currDate = new Date();
		EntityManager em = null;
		System.out.println("from order date:: " + fromOrder.getOrderDate());
		System.out.println("to order date:: " + toOrder.getOrderDate());

		try {

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}
			System.out.println("in OrdersDAOImpl:getOrdersByDate: " + storeId);

			System.out.println("curr date:" + currDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(currDate);
			System.out.println("date is:" + curDate);
			Date date = dateFormat.parse(curDate);
			System.out.println("today date:" + date);
			Date fromDate = fromOrder.getOrderDate();
			Date toDate = toOrder.getOrderDate();

			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.storeId=:storeid and c.orderDate>=:fromDate and c.orderDate<=:toDate", OrderMaster.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			orderMasterList = qry.getResultList();

			em.getTransaction().commit();

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getAllOrders", e);

		} finally {
			if(em != null) em.close();
		}

		return orderMasterList;
	}

	@Override
	public List<OrderMaster> getDeliveredOrders() throws DAOException {

		List<OrderMaster> orderMasterList = null;
		EntityManager em = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			int storeId = 0;
			if (storeMaster != null) {
				int restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}
			System.out.println("in getDeliveredOrders....");
			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.flag= 'Y' and c.storeId=:storeid", OrderMaster.class);
			qry.setParameter("storeid", storeId);

			orderMasterList = qry.getResultList();

			em.getTransaction().commit();

			System.out.println("out getDeliveredOrders....");
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getDeliveredOrders", e);

		} finally {
			if(em != null) em.close();
		}

		return orderMasterList;
	}

	@Override
	public OrderMaster getOrderById(int orderId) throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;
		try {
			System.out.println("in OrdersDAOImpl....");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid", OrderMaster.class);
			qry.setParameter("orderid", orderId);
			order = qry.getSingleResult();
			em.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("no order created");
			e.printStackTrace();
			return null;
		} finally {
			if(em != null) em.close();
		}
		return order;
	}

	@Override
	public OrderMaster getUnpaidOrderById(int orderId, int storeId)
			throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				TypedQuery<OrderMaster> qry = em
						.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeId and c.flag='N' and c.cancel='N'", OrderMaster.class);
				qry.setParameter("orderid", orderId);
				qry.setParameter("storeId", storeId);
				order = (OrderMaster) qry.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if(em != null) em.close();
		}
		
		if (order == null) {
			order = new OrderMaster();
			order.setId(0);
		}

		return order;
	}
	
	//added on 18.07.2018
	@Override
	public OrderMaster getUnpaidOrderByNo(String orderNo, int storeId)
			throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				TypedQuery<OrderMaster> qry = em
						.createQuery("SELECT c FROM OrderMaster c WHERE c.orderNo=:orderno and c.storeId=:storeId and c.flag='N' and c.cancel='N'", OrderMaster.class);
				qry.setParameter("orderno", orderNo);
				qry.setParameter("storeId", storeId);
				order = (OrderMaster) qry.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if(em != null) em.close();
		}
		
		if (order == null) {
			order = new OrderMaster();
			order.setId(0);
		}

		return order;
	}

	@Override
	public OrderMaster getOrderWithPaymentInfo(int orderId, int storeId)
			throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;
		BillingDAO billingDAO = new BillingDAOImpl();
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeId", OrderMaster.class);
			qry.setParameter("orderid", orderId);
			qry.setParameter("storeId", storeId);
			order = qry.getSingleResult();
			
			if (order != null) {
				List<Payment> payList = billingDAO
						.getPaymentInfoByPaymentMode("" + orderId);
				order.setPayments(payList);
			}
			
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(em != null) em.close();
		}
		return order;
	}

	@Override
	public OrderMaster getOrderById(int orderId, String lang)
			throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid", OrderMaster.class);
			qry.setParameter("orderid", orderId);
			order = qry.getSingleResult();
			// check for translation
			if (order != null) {
			  Map<Integer, MenuItem> menuItemMap = new HashMap<>();
			  List<Integer> itemIdList = new ArrayList<>();
			  
				List<OrderItem> itemList = order.getOrderitem();
				
				Iterator<OrderItem> iterator = itemList.iterator();
				while (iterator.hasNext()) {
					MenuItem menuItem = iterator.next().getItem();
					menuItemMap.put(menuItem.getId(), menuItem);
					itemIdList.add(menuItem.getId());
				}

          //OrderItem orderItem = iterator.next();
          //MenuItem menuItem = iterator.next().getItem();
          //int itemId = menuItem.getId();
					if (!Constants.DEFAULT_LANG.equalsIgnoreCase(lang)) {
						try {
							// check if item translated
							TypedQuery<MenuItemLangMap> qry2 = em
//                .createQuery("SELECT l FROM MenuItemLangMap l WHERE l.menuItem.id=:itemId and l.storeId=:storeId and l.language=:language");
                  .createQuery("SELECT l FROM MenuItemLangMap l WHERE l.menuItem.id IN (:itemIdList) AND l.storeId=:storeId and l.language=:language", MenuItemLangMap.class);
              qry2.setParameter("itemIdList", itemIdList);
//							qry2.setParameter("itemId", itemId);
							qry2.setParameter("storeId", order.getStoreId());
							qry2.setParameter("language", lang);
//							MenuItemLangMap itemLng = (MenuItemLangMap) qry2.getSingleResult();
//							menuItem.setName(itemLng.getName());
//							menuItem.setDescription(itemLng.getDescription());
							
							List<MenuItemLangMap> itemLngList = qry2.getResultList();
							for(MenuItemLangMap itemLng : itemLngList) {
							  MenuItem menuItem = menuItemMap.get(itemLng.getMenuItem().getId());
	              menuItem.setName(itemLng.getName());
	              menuItem.setDescription(itemLng.getDescription());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
		}
		catch (Exception e) {
			System.out.println("no order created");
			e.printStackTrace();
			// em.close();
			return null;
		} finally {
			if(em != null) em.close();
		}
		return order;
	}

	@Override
	public OrderMaster orderByIdForBillSplit(int orderId) throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;
		try {

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid", OrderMaster.class);
			qry.setParameter("orderid", orderId);
			order = qry.getSingleResult();

			List<OrderItem> orderItemList = order.getOrderitem();
			Iterator<OrderItem> iterator = orderItemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();
				MenuItem item = orderItem.getItem();
				MenuCategory subCat = item.getMenucategory();
				MenuCategory cat = subCat.getMenutype();
				int subCatId = subCat.getId();
				String subCatName = subCat.getMenuCategoryName();
				int catId = cat.getId();
				String catName = cat.getMenuCategoryName();
				item.setSubCategoryId(subCatId);
				item.setSubCategoryName(subCatName);
				item.setCategoryId(catId);
				item.setCategoryName(catName);
			}
			
			em.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("no order created");
			e.printStackTrace();
			return null;
		} finally {
			if(em != null) em.close();
		}
		return order;
	}

	@Override
	public List<OrderMaster> getAllUnpaidOrdersByStoreId(Integer storeId,
			String date) throws DAOException {

		List<OrderMaster> orderList = null;
		//List<Integer> orderIdListTemp = new ArrayList<Integer>();
		//List<Payment> paymentList = null;
		EntityManager em = null;
		//BillingDAO billingDAO = new BillingDAOImpl();

		int storeid = (storeId);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get all unpaid orders for this customer
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currDate = dateFormat.parse(date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			if(storeId==128)
			{
				cal.add(Calendar.DATE, -10);
			}
			else {
			cal.add(Calendar.DATE, -1);
			}
			Date previousDate = cal.getTime();

			List<Date> dateList = new ArrayList<Date>();

			dateList.add(currDate);
			dateList.add(previousDate);

			//TypedQuery<OrderMaster> qry = em.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no!=NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate IN (:dateList)", OrderMaster.class);
			TypedQuery<OrderMaster> qry = em.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no!=NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate between :fromDate and :toDate", OrderMaster.class);
			qry.setParameter("storeId", storeid);
			//qry.setParameter("dateList", dateList);
			qry.setParameter("fromDate", previousDate);
			qry.setParameter("toDate", currDate);

			orderList = qry.getResultList();

			//this logic is commented because of performance,need to be proper tested..28.06.2018
			
			/*if (orderList != null && orderList.size() > 0) {
				List<Integer> orderIdList = new ArrayList<Integer>();
				Iterator<OrderMaster> iterator = orderList.iterator();
				while (iterator.hasNext()) {
					OrderMaster orderMaster = iterator.next();
					orderIdList.add(orderMaster.getId());
				}
				
				// new method
				paymentList = billingDAO.getPaymentInfoByOrderList(orderIdList);
				Iterator<Integer> iterator2 = orderIdList.iterator();
				while (iterator2.hasNext()) {
					List<Payment> paymentListTemp = new ArrayList<Payment>();
					Integer orderId = (Integer) iterator2.next();
					Iterator<Payment> paymentItr = paymentList.iterator();
					while (paymentItr.hasNext()) {
						Payment payment = (Payment) paymentItr.next();
						int orderIdPayment = payment.getOrderPayment().getId();
						if (orderId == orderIdPayment) {
							paymentListTemp.add(payment);
						}

					}

					if (paymentListTemp.size() > 1) {
						Iterator<Payment> paymentIterator = paymentListTemp
								.iterator();
						while (paymentIterator.hasNext()) {
							Payment payment = (Payment) paymentIterator.next();
							double amtToPay = payment.getAmountToPay();
							if (amtToPay == 0.00) {

								orderIdListTemp.add(orderId);
							}

						}

					}

				}
				
				// Modify order list, by removing orders for which payment has already started
				for (int i = 0; i < orderIdListTemp.size(); i++) {
					int id = orderIdListTemp.get(i);

					for (int j = 0; j < orderList.size(); j++) {
						OrderMaster orderMaster = orderList.get(j);
						int orderid = orderMaster.getId();

						if (id == orderid) {
							orderList.remove(j);
						}
					}
				}
			}*/

			
			em.getTransaction().commit();
			// System.out.println("getAllUnpaidOrdersByUserId 666");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return orderList;
	}

	@Override
	public List<OrderMaster> getAllUnpaidOrdersByDate(Integer storeId,
			String date) throws DAOException {

		List<OrderMaster> orderList = null;
		List<Integer> orderIdListTemp = new ArrayList<Integer>();
		List<Payment> paymentList = null;

		EntityManager em = null;
		BillingDAO billingDAO = new BillingDAOImpl();

		int storeid = (storeId);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get all unpaid orders for this customer
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currDate = dateFormat.parse(date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, -1);
			Date previousDate = cal.getTime();

			List<Date> dateList = new ArrayList<Date>();

			dateList.add(currDate);
			dateList.add(previousDate);

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no IS NOT NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate=:currDate)", OrderMaster.class);
			qry.setParameter("storeId", storeid);
			qry.setParameter("currDate", currDate);

			orderList = qry.getResultList();

			if (orderList != null && orderList.size() > 0) {
				List<Integer> orderIdList = new ArrayList<Integer>();
				Iterator<OrderMaster> iterator = orderList.iterator();
				while (iterator.hasNext()) {
					OrderMaster orderMaster = (OrderMaster) iterator.next();
					orderIdList.add(orderMaster.getId());
				}
				
				// New method
				paymentList = billingDAO.getPaymentInfoByOrderList(orderIdList);
				Iterator<Integer> iterator2 = orderIdList.iterator();
				while (iterator2.hasNext()) {
					List<Payment> paymentListTemp = new ArrayList<Payment>();
					Integer orderId = (Integer) iterator2.next();
					Iterator<Payment> paymentItr = paymentList.iterator();
					while (paymentItr.hasNext()) {
						Payment payment = (Payment) paymentItr.next();
						int orderIdPayment = payment.getOrderPayment().getId();
						if (orderId == orderIdPayment) {
							paymentListTemp.add(payment);
						}
					}

					if (paymentListTemp.size() > 1) {
						Iterator<Payment> paymentIterator = paymentListTemp
								.iterator();
						while (paymentIterator.hasNext()) {
							Payment payment = (Payment) paymentIterator.next();
							double amtToPay = payment.getAmountToPay();
							if (amtToPay == 0.00) {
								orderIdListTemp.add(orderId);
							}
						}
					}
				}
				
				// Modify order list, by removing orders for which payment has already started
				for (int i = 0; i < orderIdListTemp.size(); i++) {
					int id = orderIdListTemp.get(i);

					for (int j = 0; j < orderList.size(); j++) {
						OrderMaster orderMaster = orderList.get(j);
						int orderid = orderMaster.getId();

						if (id == orderid) {
							orderList.remove(j);
						}
					}
				}
			}


			em.getTransaction().commit();
			// System.out.println("getAllUnpaidOrdersByUserId 666");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		return orderList;
	}

	@Override
	public List<OrderMaster> getAllPaidOrdersByStoreId(Integer storeId,
			String date) throws DAOException {

		List<OrderMaster> orderList = null;
		EntityManager em = null;

		int storeid = (storeId);

		try {
			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			// get all paid orders for this store
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date orderDate = dateFormat.parse(date);

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='Y' and o.table_no!=NULL and o.cancel='N' and o.orderDate=:orderDate order by o.id DESC", 
					    OrderMaster.class);
			qry.setParameter("storeId", storeid);
			qry.setParameter("orderDate", orderDate);

			orderList = qry.getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}
		
		return orderList;
	}
	
	@Override
	public OrderMaster getPaidOrderById(String id,
			Integer storeId) throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;

		int storeid = (storeId);
		int orderId = Integer.parseInt(id);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='Y' and o.table_no!=NULL and o.cancel='N' and o.id=:orderId order by o.id desc");
			qry.setParameter("storeId", storeid);
			qry.setParameter("orderId", orderId);

			order = (OrderMaster) qry.getSingleResult();

			em.getTransaction().commit();

		} catch (Exception e) {
			order=new OrderMaster();
			//e.printStackTrace();
			throw new DAOException("error occurred in get paid order by id::", e);
		} finally {
			if(em != null) em.close();
		}
		return order;
	}
	
	@Override
	public OrderMaster getPaidOrderByNo(String id,
			Integer storeId) throws DAOException {

		OrderMaster order = null;
		EntityManager em = null;

		//int storeid = (storeId);
		//int orderId = Integer.parseInt(id);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='Y' and o.table_no!=NULL and o.cancel='N' and o.orderNo=:orderNo order by o.id desc");
			qry.setParameter("storeId", storeId);
			qry.setParameter("orderNo", id);

			order = (OrderMaster) qry.getSingleResult();

			em.getTransaction().commit();

		} catch (Exception e) {
			order=new OrderMaster();
			//e.printStackTrace();
			throw new DAOException("error occurred in get paid order by no::", e);
		} finally {
			if(em != null) em.close();
		}
		return order;
	}

	@Override
	public void updateDeliveryStatus(OrderMaster order) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			OrderMaster orderMaster = em.find(OrderMaster.class, order.getId());
			
			orderMaster.setFlag("Y");
			//System.out.println("update order 33333333");
			em.getTransaction().commit();
			System.out.print("Order delivery status updated successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Order data to be updated", e);
		} finally {
			if(em != null) em.close();
		}

	}

	@Override
	public List<OrderType> getOrderType() throws DAOException {

		List<OrderType> orderTypeLst = new ArrayList<OrderType>();
		EntityManager em = null;
		try {
			System.out.println("in OrdersDAOImpl getOrderType....");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderType> qry = em
					.createQuery("SELECT o FROM OrderType o WHERE o.statusFlag='Y'", OrderType.class);

			orderTypeLst = qry.getResultList();
			//System.out.println("order 555");

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getOrderType", e);

		} finally {
			if(em != null) em.close();
		}
		
		return orderTypeLst;
	}

	@Override
	public List<OrderType> getOrderTypeByStore(Integer storeid)
			throws DAOException {

		List<OrderType> orderTypeLst = new ArrayList<OrderType>();
		EntityManager em = null;
		try {
			int strId = (storeid);
			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<OrderType> qry = em
					.createQuery("SELECT o FROM OrderType o WHERE o.storeId=:storeId and o.statusFlag='Y'", OrderType.class);

			qry.setParameter("storeId", strId);
			orderTypeLst = qry.getResultList();

			em.getTransaction().commit();
		}

		catch (Exception e) {

			e.printStackTrace();
			throw new DAOException("In DAOException of getOrderType", e);

		} finally {
			if(em != null) em.close();
		}
		return orderTypeLst;
	}
	
	
	/**
	 * @Auther Anup
	 * @return Service Charge Value
	 */
	@Override
	public double getOrderTypeByStoreIdAndOrdershortName(Integer storeid,
			String shortName) throws DAOException {

		double serviceChargeVal = 0.00f;
		// OrderType orderType=new OrderType();
		EntityManager em = null;
		try {
			int strId = (storeid);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderType> qry = em
					.createQuery("SELECT o FROM OrderType o WHERE o.storeId=:storeId and o.statusFlag='Y' and o.ordertypeShortName=:shortName", OrderType.class);
			qry.setParameter("storeId", strId);
			qry.setParameter("shortName", shortName);
			OrderType orderType = qry.getSingleResult();
			serviceChargeVal = orderType.getServiceChargeValue();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getOrderType", e);

		} finally {
			if(em != null) em.close();
		}
		return serviceChargeVal;
	}

	/*@Override
	public List<DailyExpenditure> getDailyExpenditureByDate(String date,
			Integer storeId) throws DAOException {

		List<DailyExpenditure> dailyExpenditureLst = new ArrayList<DailyExpenditure>();
		EntityManager em = null;
		SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

		Date voucherDate = null;

		try {
			// String voucherDateStrng = FORMAT.format(date);
			voucherDate = FORMAT.parse(date);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			int storeid = (storeId);

			Query qry = em
					.createQuery("SELECT d FROM DailyExpenditure d WHERE d.voucherDate=:voucherDate and d.storeId=:storeId and d.deleteFlag='N'");
			qry.setParameter("voucherDate", voucherDate);
			qry.setParameter("storeId", storeid);
			dailyExpenditureLst = (List<DailyExpenditure>) qry.getResultList();

			
			 * Gson gson = new GsonBuilder()
			 * .excludeFieldsWithoutExposeAnnotation().create();
			 * java.lang.reflect.Type type = new TypeToken<List<OrderType>>() {
			 * }.getType(); String json = gson.toJson(dailyExpenditureLst,
			 * type); System.out.println("====>" + json);
			 

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			em.close();
		}
		return dailyExpenditureLst;
	}*/

	/**
	 * @author Anup Mallick
	 * @Date 23/08/2017
	 */
	/*@Override
	public List<DailyExpenditure> getDailyExpenditureByPeriod(String date,
			String toDate, Integer storeId) throws DAOException {

		List<DailyExpenditure> dailyExpenditureLst = new ArrayList<DailyExpenditure>();
		EntityManager em = null;
		SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

		Date voucherDate = null;
		Date voucherDate2 = null;

		try {
			voucherDate = FORMAT.parse(date);
			voucherDate2 = FORMAT.parse(toDate);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			int storeid = (storeId);
			Query qry = em
					.createQuery("SELECT d FROM DailyExpenditure d WHERE d.voucherDate >=:voucherDate and d.voucherDate <=:voucherDate2 and d.storeId=:storeId and d.deleteFlag='N'");
			qry.setParameter("voucherDate", voucherDate);
			qry.setParameter("voucherDate2", voucherDate2);
			qry.setParameter("storeId", storeid);
			dailyExpenditureLst = (List<DailyExpenditure>) qry.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);
		} finally {
			em.close();
		}
		return dailyExpenditureLst;
	}*/

	@Override
	public List<OrderType> getAdminOrderType() throws DAOException {

		List<OrderType> orderTypeLst = new ArrayList<OrderType>();
		EntityManager em = null;
		try {
			System.out.println("in OrdersDAOImpl getAdminOrderType....");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<OrderType> qry = em
					.createQuery("SELECT o FROM OrderType o where o.statusFlag='Y'", OrderType.class);

			orderTypeLst = qry.getResultList();

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getAdminOrderType", e);
		}
		finally {
			if(em != null) em.close();
		}
		return orderTypeLst;
	}

	@Override
	public void createOrderType(OrderType orderType) throws DAOException {

		OrderType odrType = new OrderType();
		EntityManager em = null;
		try {
			
			//System.out.println("orderType111");
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			odrType.setOrderTypeName(orderType.getOrderTypeName());
			odrType.setStatusFlag("Y");
			em.persist(odrType);
			//System.out.println("orderType222");
			System.out.print("Order Type saved successfully...");
			em.getTransaction().commit();
			//System.out.println("orderType333");
			// em.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In createOrderType DAOException", e);
		} finally {
			if(em != null) em.close();
		}
	}

	@Override
	public void deleteOrderType(OrderType orderType) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			OrderType OdrTyp = em.find(OrderType.class, orderType.getId());
			em.remove(OdrTyp);
			em.getTransaction().commit();
			System.out.print("OrderType deleted successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"In ordersDAOImpl deleteOrderType DAOException ", e);
		} finally {
			if (em != null) em.close();
		}
	}

	@Override
	public void delete(OrderMaster order) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			OrderMaster ord = em.find(OrderMaster.class, order.getId());
			ord.setCancel("Y");
			em.getTransaction().commit();
			System.out.print("Order deleted successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In ordersDAOImpl Order DAOException ", e);
		} finally {
			if (em != null) em.close();
		}
	}

	@Override
	public List<PaymentType> getPaymentType() throws DAOException {

		List<PaymentType> paymentTypeLst = new ArrayList<PaymentType>();
		EntityManager em = null;
		try {
			System.out.println("in OrdersDAOImpl getOrderType....");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<PaymentType> qry = em
					.createQuery("SELECT p FROM PaymentType p WHERE p.statusFlag='Y'", PaymentType.class);
			paymentTypeLst = qry.getResultList();

			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getPaymentType", e);

		} finally {
			if(em != null) em.close();
		}
		return paymentTypeLst;
	}

	@Override
	public ListHolder getPaymentTypeByStore(Integer id) throws DAOException {

		List<PaymentType> paymentTypeLst = new ArrayList<PaymentType>();
		ListHolder holder = new ListHolder();
		EntityManager em = null;
		try {
			int storeid = (id);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<PaymentType> qry = em
					.createQuery("SELECT p FROM PaymentType p WHERE p.storeId=:storeId AND LOWER(p.paymentTypeName) != 'card' AND LOWER(p.paymentTypeName) != 'cash' AND p.statusFlag='Y'", 
					    PaymentType.class);
			qry.setParameter("storeId", storeid);

			paymentTypeLst = qry.getResultList();

			/*
			if(paymentTypeLst !=null && paymentTypeLst.size()>0)
			{
				Iterator<PaymentType> paymentIterator=paymentTypeLst.iterator();
				while(paymentIterator.hasNext())
				{
					PaymentType paymentType = paymentIterator.next();
					if(!paymentType.getPaymentTypeName().equalsIgnoreCase("Card") && !paymentType.getPaymentTypeName().equalsIgnoreCase("Cash"))
					{
						paymentTypeLstnew.add(paymentType);
					}
				}
			}
			*/

			// paymentTypeLst.remove(1);

//			int size = paymentTypeLstnew.size();
//			holder.setPaymentTypes(paymentTypeLstnew);
//			holder.setSize(size);
      holder.setPaymentTypes(paymentTypeLst);
      holder.setSize(paymentTypeLst.size());
			
			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getPaymentType", e);
		} finally {
			if(em != null) em.close();
		}
		return holder;
	}

	@Override
	public List<PaymentType> getAdminPaymentType() throws DAOException {

		List<PaymentType> paymentTypeLst = new ArrayList<PaymentType>();
		EntityManager em = null;
		try {
			System.out.println("in OrdersDAOImpl getAdminPaymentType....");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<PaymentType> qry = em
					.createQuery("SELECT p FROM PaymentType p where p.statusFlag='Y'", PaymentType.class);
			paymentTypeLst = qry.getResultList();

			em.getTransaction().commit();
		}

		catch (Exception e) {
			//System.out.println("PaymentType 888");
			e.printStackTrace();
			throw new DAOException("In DAOException of getAdminPaymentType", e);

		} finally {
			if(em != null) em.close();
		}
		
		return paymentTypeLst;
	}

	@Override
	public void createPaymentType(PaymentType paymentType) throws DAOException {

		PaymentType payment = new PaymentType();
		EntityManager em = null;
		try {
			
			//System.out.println("PaymentType111");
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			payment.setPaymentTypeName(paymentType.getPaymentTypeName());
			payment.setStatusFlag("Y");
			em.persist(payment);
			//System.out.println("PaymentType222");
			System.out.print("PaymentType saved successfully...");
			em.getTransaction().commit();
			//System.out.println("PaymentType333");
			// em.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In createPaymentType DAOException", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public void deletePaymentType(PaymentType paymentType) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			PaymentType payment = em.find(PaymentType.class,
					paymentType.getId());
			em.remove(payment);
			em.getTransaction().commit();
			System.out.print("PaymentType deleted successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"In ordersDAOImpl deletePaymentType DAOException ", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public void updateOrderTypeFlagAsActive(OrderType orderType)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			OrderType orderTyp = em.find(OrderType.class, orderType.getId());
			
			orderTyp.setStatusFlag("Y");
			//System.out.println("update order 33333333");
			em.getTransaction().commit();
			System.out.print("Order delivery status updated successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Order data to be updated", e);
		} finally {
      if(em != null) em.close();
		}

	}

	@Override
	public void updateOrderTypeFlagAsInActive(OrderType orderType)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			OrderType orderTyp = em.find(OrderType.class, orderType.getId());
			
			orderTyp.setStatusFlag("N");
			//System.out.println("update order 33333333");
			em.getTransaction().commit();
			System.out.print("Order delivery status updated successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Order data to be updated", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public void updatePaymentTypeFlagAsActive(PaymentType paymentType)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			PaymentType paymentTyp = em.find(PaymentType.class,
					paymentType.getId());
			
			paymentTyp.setStatusFlag("Y");
			//System.out.println("update order 33333333");
			em.getTransaction().commit();
			System.out.print("Order delivery status updated successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Order data to be updated", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public void updatePaymentTypeFlagAsInActive(PaymentType paymentType)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			PaymentType paymentTyp = em.find(PaymentType.class,
					paymentType.getId());
			
			paymentTyp.setStatusFlag("N");
			//System.out.println("update order 33333333");
			em.getTransaction().commit();
			System.out.print("Order delivery status updated successfully....");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Order data to be updated", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public List<OrderItem> getItemsByOrderId(String orderId, String language)
			throws DAOException {

		List<OrderItem> itemList = null;
		EntityManager em = null;
		List<MenuItemLangMap> menuItemLangList = null;
		System.out.println("order id is: " + orderId);
		OrderMaster order = new OrderMaster();
		int orderid = Integer.parseInt(orderId);
		order.setId(orderid);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.orders=:order and i.quantityOfItem > 0", OrderItem.class);
			qry.setParameter("order", order);
			
			itemList = qry.getResultList();
			System.out.println("Number of items : " + itemList.size());

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
      if(em != null) em.close();
		}

		// Translation
		try {
      int storeid = (itemList.size() > 0) ? itemList.get(0).getItem().getStoreId() : 0;
      /*
			int storeid = 0;
			Iterator<OrderItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();
				MenuItem item = orderItem.getItem();
				storeid = item.getStoreId();
				break;
			}
			*/

			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			/*
			 * //query to get all categories and sub-categories Query
			 * qryCatTypelang = em.createQuery(
			 * "SELECT m FROM MenuCategoryLangMap m WHERE m.storeId=:storeid and m.language=:language"
			 * ); qryCatTypelang.setParameter("storeid", storeid);
			 * qryCatTypelang.setParameter("language", language); menuLanglist =
			 * (List<MenuCategoryLangMap>) qryCatTypelang.getResultList();
			 */

			// query to get all items
			TypedQuery<MenuItemLangMap> qryItemlang = em
					.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.storeId=:storeid and i.language=:language", MenuItemLangMap.class);
			qryItemlang.setParameter("storeid", storeid);
			qryItemlang.setParameter("language", language);
			menuItemLangList = qryItemlang.getResultList();

			Map<Integer, MenuItem> menuItemMap = new HashMap<>();
			Iterator<OrderItem> orderItemIterator = itemList.iterator();
			while (orderItemIterator.hasNext()) {
				OrderItem orderItem = orderItemIterator.next();
				menuItemMap.put(orderItem.getItem().getId(), orderItem.getItem());
			}
			
      if (menuItemLangList != null && menuItemLangList.size() > 0) {
        Iterator<MenuItemLangMap> iterItem = menuItemLangList.iterator();
        while (iterItem.hasNext()) {
          MenuItemLangMap menuItemLangMap = iterItem.next();
          MenuItem itemLang = menuItemLangMap.getMenuItem();
          int itemLangId = itemLang.getId();
          MenuItem item = menuItemMap.get(itemLangId);
          
          if (item != null) {
            String itemlangName = menuItemLangMap.getName();
            String itemlanDesc = menuItemLangMap.getDescription();
            
            if (itemlangName != null && itemlangName.length() > 0) {
              item.setName(itemlangName);
            }
            if (itemlanDesc != null && itemlanDesc.length() > 0) {
              item.setDescription(itemlanDesc);
            }
          }
        }
      }
      
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
      if(em != null) em.close();
		}

		return itemList;
	}

	@Override
	public List<OrderItem> getItemsByOrderIdInRest(String orderId,
			String language) throws DAOException {

		List<OrderItem> itemList = null;
		EntityManager em = null;
		List<MenuItemLangMap> menuItemLangList = null;
		System.out.println("order id is: " + orderId);
		OrderMaster order = new OrderMaster();
		int orderid = Integer.parseInt(orderId);
		order.setId(orderid);
		int ordertype = 3;

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.orders=:order and i.ordertype=:ordertype and i.quantityOfItem > 0", 
					    OrderItem.class);
			qry.setParameter("order", order);
			qry.setParameter("ordertype", ordertype);
			
			itemList = qry.getResultList();
			System.out.println("Number of items : " + itemList.size());
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
      if(em != null) em.close();
		}

		// Translation
		try {
			int storeid = 0;
			Iterator<OrderItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();
				MenuItem item = orderItem.getItem();
				storeid = item.getStoreId();
				break;

			}

			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			/*
			 * //query to get all categories and sub-categories Query
			 * qryCatTypelang = em.createQuery(
			 * "SELECT m FROM MenuCategoryLangMap m WHERE m.storeId=:storeid and m.language=:language"
			 * ); qryCatTypelang.setParameter("storeid", storeid);
			 * qryCatTypelang.setParameter("language", language); menuLanglist =
			 * (List<MenuCategoryLangMap>) qryCatTypelang.getResultList();
			 */

			// Query to get all items
			TypedQuery<MenuItemLangMap> qryItemlang = em
					.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.storeId=:storeid and i.language=:language",
					    MenuItemLangMap.class);
			qryItemlang.setParameter("storeid", storeid);
			qryItemlang.setParameter("language", language);
			menuItemLangList = qryItemlang.getResultList();

			Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      Iterator<OrderItem> orderItemIterator = itemList.iterator();
      while (orderItemIterator.hasNext()) {
        OrderItem orderItem = orderItemIterator.next();
        menuItemMap.put(orderItem.getItem().getId(), orderItem.getItem());
      }

      if (menuItemLangList != null && menuItemLangList.size() > 0) {
        Iterator<MenuItemLangMap> iterItem = menuItemLangList.iterator();
        while (iterItem.hasNext()) {
          MenuItemLangMap menuItemLangMap = iterItem.next();
          MenuItem itemLang = menuItemLangMap.getMenuItem();
          int itemLangId = itemLang.getId();
          MenuItem item = menuItemMap.get(itemLangId);
          
          if (item != null) {
            String itemlangName = menuItemLangMap.getName();
            String itemlanDesc = menuItemLangMap.getDescription();
            
            if (itemlangName != null && itemlangName.length() > 0) {
              item.setName(itemlangName);
            }
            if (itemlanDesc != null && itemlanDesc.length() > 0) {
              item.setDescription(itemlanDesc);
            }
          }
        }
      }
      
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
      if(em != null) em.close();
		}

		return itemList;
	}

	@Override
	public List<OrderItem> getItemsByOrderIdParcel(String orderId,
			String language) throws DAOException {

		List<OrderItem> itemList = null;
		EntityManager em = null;
		List<MenuItemLangMap> menuItemLangList = null;
		System.out.println("order id is: " + orderId);
		OrderMaster order = new OrderMaster();
		int orderid = Integer.parseInt(orderId);
		order.setId(orderid);
		int ordertype = 2;

		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("getItemsByOrderId 111");
			em.getTransaction().begin();
			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.orders=:order and i.ordertype=:ordertype and i.quantityOfItem > 0",
					    OrderItem.class);
			//System.out.println("getItemsByOrderId 222");
			qry.setParameter("order", order);
			qry.setParameter("ordertype", ordertype);
			
			itemList = qry.getResultList();
			//System.out.println("getItemsByOrderId 444");
			System.out.println("Number of items : " + itemList.size());

			em.getTransaction().commit();
			
		} catch (Exception e) {
			//System.out.println("getItemsByOrderId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if(em != null) em.close();
		}

		// Translation
		try {
			int storeid = 0;
			Iterator<OrderItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();
				MenuItem item = orderItem.getItem();
				storeid = item.getStoreId();
				break;

			}

			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			/*
			 * //query to get all categories and sub-categories Query
			 * qryCatTypelang = em.createQuery(
			 * "SELECT m FROM MenuCategoryLangMap m WHERE m.storeId=:storeid and m.language=:language"
			 * ); qryCatTypelang.setParameter("storeid", storeid);
			 * qryCatTypelang.setParameter("language", language); menuLanglist =
			 * (List<MenuCategoryLangMap>) qryCatTypelang.getResultList();
			 */

			// query to get all items
			TypedQuery<MenuItemLangMap> qryItemlang = em
					.createQuery("SELECT i FROM MenuItemLangMap i WHERE i.storeId=:storeid and i.language=:language",
					    MenuItemLangMap.class);
			qryItemlang.setParameter("storeid", storeid);
			qryItemlang.setParameter("language", language);
			menuItemLangList = qryItemlang.getResultList();

      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      Iterator<OrderItem> orderItemIterator = itemList.iterator();
      while (orderItemIterator.hasNext()) {
        OrderItem orderItem = orderItemIterator.next();
        menuItemMap.put(orderItem.getItem().getId(), orderItem.getItem());
      }

      if (menuItemLangList != null && menuItemLangList.size() > 0) {
        Iterator<MenuItemLangMap> iterItem = menuItemLangList.iterator();
        while (iterItem.hasNext()) {
          MenuItemLangMap menuItemLangMap = iterItem.next();
          MenuItem itemLang = menuItemLangMap.getMenuItem();
          int itemLangId = itemLang.getId();
          MenuItem item = menuItemMap.get(itemLangId);
          
          if (item != null) {
            String itemlangName = menuItemLangMap.getName();
            String itemlanDesc = menuItemLangMap.getDescription();
            
            if (itemlangName != null && itemlangName.length() > 0) {
              item.setName(itemlangName);
            }
            if (itemlanDesc != null && itemlanDesc.length() > 0) {
              item.setDescription(itemlanDesc);
            }
          }
        }
      }
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
      if(em != null) em.close();
		}

		return itemList;
	}

	@Override
	public void reqBillByOrderId(String id, String billReqTime)
			throws DAOException {

		EntityManager em = null;
		System.out.println("order id is: " + id);
		int orderId = Integer.parseInt(id);
		try {
			
			em = entityManagerFactory.createEntityManager();
			//System.out.println("reqBillByOrderId 111");
			em.getTransaction().begin();
			OrderMaster orderMaster = em.find(OrderMaster.class, orderId);
			orderMaster.setBillReqStatus("Yes");
			orderMaster.setFlag("Y");
			orderMaster.setBillReqTime(billReqTime);
			em.getTransaction().commit();
			//System.out.println("reqBillByOrderId..");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Error occurred trying to request bill", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public String chkBillReq(String id) throws DAOException {

		String status = null;
		EntityManager em = null;
		System.out.println("order id is: " + id);
		int orderId = Integer.parseInt(id);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			OrderMaster orderMaster = em.find(OrderMaster.class, orderId);
			if (orderMaster != null) {
				status = orderMaster.getBillReqStatus();
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Error occurred trying to request bill", e);
		} finally {
      if(em != null) em.close();
		}
		
    return status;
	}

	@Override
	public List<OrderMaster> getCreditOrderByCustomerId(Integer storeId,
			String custId) throws DAOException {

		List<OrderMaster> orderList = null;
		List<Integer> orderIdListTemp = new ArrayList<Integer>();
		List<Payment> paymentList = null;
		EntityManager em = null;
		BillingDAO billingDAO = new BillingDAOImpl();

		int custId1 = Integer.parseInt(custId);
		int storeId1 = (storeId);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId and o.storeCustomerId=:storeCustomerId and o.flag='N' and o.cancel='N' and o.creditFlag='Y'",
					    OrderMaster.class);
			qry.setParameter("storeId", storeId1);
			qry.setParameter("storeCustomerId", custId1);

			orderList = qry.getResultList();

			if (orderList != null && orderList.size() > 0) {
				List<Integer> orderIdList = new ArrayList<Integer>();
				Iterator<OrderMaster> iterator = orderList.iterator();
				while (iterator.hasNext()) {
					OrderMaster orderMaster = (OrderMaster) iterator.next();
					orderIdList.add(orderMaster.getId());
				}
				
				// New method
				paymentList = billingDAO.getPaymentInfoByOrderList(orderIdList);
				Iterator<Integer> iterator2 = orderIdList.iterator();
				while (iterator2.hasNext()) {
					List<Payment> paymentListTemp = new ArrayList<Payment>();
					Integer orderId = (Integer) iterator2.next();
					Iterator<Payment> paymentItr = paymentList.iterator();
					while (paymentItr.hasNext()) {
						Payment payment = (Payment) paymentItr.next();
						int orderIdPayment = payment.getOrderPayment().getId();
						if (orderId == orderIdPayment) {
							paymentListTemp.add(payment);
							
              double amtToPay = payment.getAmountToPay();
              if (amtToPay == 0.00) {
                orderIdListTemp.add(orderId);
              }
						}
					}
				}
				// Modify order list, by removing orders for which payment has already started
				//for (int i = 0; i < orderIdListTemp.size(); i++) {
					//int id = orderIdListTemp.get(i);

					for (int j = 0; j < orderList.size(); j++) {
						OrderMaster orderMaster = orderList.get(j);
						int orderid = orderMaster.getId();

//            if (id == orderid) {
						if (orderIdListTemp.contains(orderid)) {
							orderList.remove(j);
						}
					}
				//}
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
      if(em != null) em.close();
		}
		return orderList;
	}

	@Override
	public OrderMaster chkOrderExistsOnTable(OrderMaster order)
			throws DAOException {

		OrderMaster orderMaster = null;
		Date currDate = new Date();
		EntityManager em = null;
		
		try {

			int storeId = order.getStoreId();
			/*
			 * if (storeMaster != null) { int restId =
			 * storeMaster.getRestaurantId(); System.out.println("rest id:  " +
			 * restId); storeId = storeMaster.getId(); }
			 */
			System.out.println("in OrdersDAOImpl:chkOrderExistsOnTable: "
					+ storeId);

			System.out.println("curr date:" + currDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// DateFormat dateFormat = new
			// SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
			String curDate = dateFormat.format(currDate);
			System.out.println("date is:" + curDate);
			Date date = dateFormat.parse(curDate);
			System.out.println("today date:" + date);
			String table_no = order.getTable_no();

			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.storeId=:storeid and c.flag= 'N' and c.orderDate=:currDate and c.table_no=:table_no and c.creditFlag!='Y' and c.cancel='N'",
					    OrderMaster.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("currDate", date);
			qry.setParameter("table_no", table_no);
			orderMaster = qry.getSingleResult();

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"In DAOException of OrdersDAOImpl:chkOrderExistsOnTable", e);

		} finally {
      if(em != null) em.close();
		}
		
    return orderMaster;
	}
	
	@Override
	public OrderMaster chkAdvOrderExistsOnTable(OrderMaster order)
			throws DAOException {

		OrderMaster orderMaster = null;
		Date date = order.getOrderDate();
		EntityManager em = null;
		
		try {

			int storeId = order.getStoreId();
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String advDate = dateFormat.format(date);
			Date date1 = dateFormat.parse(advDate);
			System.out.println("date:" + date1);
			String table_no = order.getTable_no();

			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.storeId=:storeid and c.flag= 'N' and c.orderDate=:currDate and c.table_no=:table_no and c.creditFlag!='Y' and c.cancel='N'",
					    OrderMaster.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("currDate", date1);
			qry.setParameter("table_no", table_no);
			orderMaster = (OrderMaster) qry.getSingleResult();

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"In DAOException of OrdersDAOImpl:chkOrderExistsOnTable", e);

		} finally {
      if(em != null) em.close();
		}

    return orderMaster;
	}

	@Override
	public void pay(String orderId) throws DAOException {

		EntityManager em = null;
		OrderMaster orderMaster = null;
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			int ordrId = Integer.parseInt(orderId);

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Date currDate = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String curDate = dateFormat.format(currDate);

			orderMaster = em.find(OrderMaster.class, ordrId);
			
			orderMaster.setFlag("Y");
			orderMaster.setBillReqStatus("Yes");
			orderMaster.setBillReqTime(curDate);

			// create payment
			Payment payment = new Payment();
			payment.setOrderPayment(orderMaster);
			payment.setAmount(orderMaster.getOrderBill().getGrossAmt());
			payment.setPaymentMode("cash");
			payment.setRemarks("done");
			payment.setStoreId(orderMaster.getStoreId());
			payment.setCreatedBy(orderMaster.getCustomers().getName());
			payment.setCreationDate(curDate);

			em.persist(payment);

			em.getTransaction().commit();
			if (context.getExternalContext().getSessionMap().get("orderId") != null) {
				context.getExternalContext().getSessionMap().remove("orderId");
			}
			context.getExternalContext().getSessionMap()
					.put("orderId", orderId);
		}

		catch (Exception e) {
			e.printStackTrace();
			if (context.getExternalContext().getSessionMap().get("orderId") != null) {
				context.getExternalContext().getSessionMap().remove("orderId");
			}
			context.getExternalContext().getSessionMap()
					.put("orderId", orderId);
			throw new DAOException("In DAOException of pay()", e);

		} finally {
      if(em != null) em.close();
		}

	}

	@Override
	public void payCash(Payment payment) throws DAOException {

		EntityManager em = null;

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(payment);

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of payCash()", e);

		} finally {
			if(em != null) em.close();
		}
	}

	@Override
	public List<OrderItem> getAllItemsByOrderNTable(String tableNo, int orderId)
			throws DAOException {

		List<OrderItem> itemList = null;
		EntityManager em = null;
		OrderMaster order = new OrderMaster();
		order.setId(orderId);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.orders=:order and i.tableNo=:tableNo",
					    OrderItem.class);
			qry.setParameter("order", order);
			qry.setParameter("tableNo", tableNo);
			itemList = qry.getResultList();

			em.getTransaction().commit();
			//System.out.println("getItemsByOrderId 666");
			// em.close();
		} catch (Exception e) {
			//System.out.println("getItemsByOrderId 777");
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
      if(em != null) em.close();
		}
		return itemList;
	}

	@Override
	public void updateOrderByItemId(String orderitemid, String quantity)
			throws DAOException {

		EntityManager em = null;
		int orderitemId = Integer.parseInt(orderitemid);
		int qnty = Integer.parseInt(quantity);
		double netPriceEachItem = 0.0;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			OrderItem orderItem = em.find(OrderItem.class, orderitemId);
			// get the item
			MenuItem item = orderItem.getItem();
			double rate = item.getPrice();
			// set total price
			double totalPrice = rate * qnty;
			orderItem.setPreviousQuantity(orderItem.getQuantityOfItem());
			orderItem.setQuantityOfItem(quantity);
			orderItem.setTotalPriceByItem(totalPrice);
			// calculate taxes start
			Double itemServiceTax = item.getServiceTax();
			Double itemDiscPer = new Double(item.getPromotionValue());
			Double itemDisc = (itemDiscPer * totalPrice) / 100;
			Double serviceTaxForThsItem = (itemServiceTax * (totalPrice - itemDisc)) / 100;

			Double itemVat = item.getVat();
			Double vatForThsItem = (itemVat * (totalPrice - itemDisc)) / 100;

			netPriceEachItem = totalPrice + serviceTaxForThsItem
					+ vatForThsItem;
			orderItem.setNetPrice(netPriceEachItem);
			// calculate taxes end

			em.merge(orderItem);
			em.getTransaction().commit();

			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Error occurred", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public String updateCookingStatus(String orderid, String orderitemid,
			String time) throws DAOException {

		EntityManager em = null;
		int orderId = Integer.parseInt(orderid);
		int orderitemId = Integer.parseInt(orderitemid);
		String status = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.id=:id and i.orders.id=:orderid",
					    OrderItem.class);

			qry.setParameter("id", orderitemId);
			qry.setParameter("orderid", orderId);

			OrderItem item = qry.getSingleResult();
			item.setCookingStatus("Y");
			item.setCookingStartTime(time);
			em.getTransaction().commit();
			status = "Cooking started";

		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if(em != null) em.close();
		}

		return status;
	}

	@Override
	public String updateNoOfPersons(String orderid, String noOfPersons)
			throws DAOException {

		EntityManager em = null;
		int orderId = Integer.parseInt(orderid);
		int noOfPersons1 = Integer.parseInt(noOfPersons);

		String status = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);

			qry.setParameter("orderid", orderId);

			OrderMaster order = qry.getSingleResult();
			order.setNoOfPersons(noOfPersons1);

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if(em != null) em.close();
		}

		return status;
	}

	@Override
	public String updateTableNo(OrderMaster orderMaster)
			throws DAOException {

		EntityManager em = null;
		int orderId = orderMaster.getId();
		String tableNo=orderMaster.getTable_no();
		String status = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid", OrderMaster.class);

			qry.setParameter("orderid", orderId);

			OrderMaster order = qry.getSingleResult();
			order.setTable_no(tableNo);

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if(em != null) em.close();
		}

		return status;
	}

	@Override
	public String updateCreditSaleStatus(String orderid, Integer storeId,
			String storeCustId) throws DAOException {

		EntityManager em = null;
		int orderId = Integer.parseInt(orderid);
		int storeId1 = (storeId);
		int storeCustId1 = Integer.parseInt(storeCustId);
		String status = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:id and o.storeId=:storeId and o.cancel='N'",
					    OrderMaster.class);

			qry.setParameter("id", orderId);
			qry.setParameter("storeId", storeId1);

			OrderMaster order = (OrderMaster) qry.getSingleResult();
			order.setCreditFlag("Y");
			order.setStoreCustomerId(storeCustId1);

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if(em != null) em.close();
		}

		return status;
	}

	@Override
	public String updateKitchenOutStatus(String orderid, String orderitemid)
			throws DAOException {

		EntityManager em = null;
		int orderId = Integer.parseInt(orderid);
		int orderitemId = Integer.parseInt(orderitemid);
		String status = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.id=:id and i.orders.id=:orderid",
					    OrderItem.class);

			qry.setParameter("id", orderitemId);
			qry.setParameter("orderid", orderId);

			OrderItem item = qry.getSingleResult();
			item.setKitchenOut("Y");
			em.getTransaction().commit();
			status = "Item deleted";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if(em != null) em.close();
		}

		return status;
	}

	@Override
	public String cookingEndStatus(String orderid, String orderitemid,
			String time) throws DAOException {

		EntityManager em = null;
		int orderId = Integer.parseInt(orderid);
		int orderitemId = Integer.parseInt(orderitemid);
		String status = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.id=:id and i.orders.id=:orderid",
					    OrderItem.class);

			qry.setParameter("id", orderitemId);
			qry.setParameter("orderid", orderId);

			OrderItem item = (OrderItem) qry.getSingleResult();
			item.setCookingEndTime(time);
			em.getTransaction().commit();
			status = "Cooking done";

		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if(em != null) em.close();
		}

		return status;
	}

	@Override
	public void cancelOrderById(String id, Integer storeid, String cancelRemrk)
			throws DAOException {

		EntityManager em = null;
		int orderId = Integer.parseInt(id);
		int storeId = (storeid);
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeid",
					    OrderMaster.class);
			qry.setParameter("orderid", orderId);
			qry.setParameter("storeid", storeId);
			OrderMaster order = (OrderMaster) qry.getSingleResult();
			order.setCancel("Y");
			order.setCancelRemark(cancelRemrk);

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Error occurred trying to cancel order", e);
		} finally {
      if(em != null) em.close();
		}
	}

	@Override
	public String getLastOrder(Integer storeId) throws DAOException {

		int storeid1 = (storeId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String orderDetails = "";
		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
      Session session = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session
          .getSessionFactory();
      con = sessionFactory.getConnectionProvider().getConnection();
			//con = session.connection();
			String cancel = "N";
			int maxOrderId = 0;

			String orderTime = "";
			String tableNo = "";

			String GET_LAST_ORDER = "select max(id) from fo_t_orders where store_id = ? and cancel=?";

			ps = con.prepareStatement(GET_LAST_ORDER);
			ps.setInt(1, storeid1);
			ps.setString(2, cancel);

			rs = ps.executeQuery();

			while (rs.next()) {
				maxOrderId = rs.getInt(1);
			}

			// get last order
			OrderMaster lastOrder = getOrderById(maxOrderId);
			if (lastOrder != null) {
				tableNo = lastOrder.getTable_no();
				if (tableNo == null || tableNo.length() == 0) {
					tableNo = "";
				}
				orderTime = lastOrder.getTime();
				if (orderTime == null || orderTime.length() == 0) {
					orderTime = "";
				}
				if (!tableNo.equalsIgnoreCase("0")) {
					orderDetails = maxOrderId + "," + orderTime + "," + "T-"
							+ tableNo;
				} else if (tableNo.equalsIgnoreCase("0")) {
					orderDetails = maxOrderId + "," + orderTime + "," + "P";
				} else if (tableNo.equalsIgnoreCase("")) {
					orderDetails = maxOrderId + "," + orderTime;
				}
			}

			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if(em != null) em.close();

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Problem occured in ordersdaoimpl.getLastOrder", e);
		}
		
		return orderDetails;

	}

	@Override
	public List<OrderItem> getAllKitchenInItems(Integer storeId, String date,
			String lang) throws DAOException {

		List<OrderItem> itemList = null;
		EntityManager em = null;
		MenuDAO menuDAO = new MenuDAOImpl();
		String kitchenOut = "N";
		String cancel = "N";
		String billReqStatus = "No";
		String paidFlag = "N";
		int storeid = (storeId);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currDate = dateFormat.parse(date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			if(storeId==128)
			{
				cal.add(Calendar.DATE, -10);
			}
			else {
			cal.add(Calendar.DATE, -1);
			}
			Date previousDate = cal.getTime();

			List<Date> dateList = new ArrayList<Date>();

			dateList.add(currDate);
			dateList.add(previousDate);

			TypedQuery<OrderItem> qry = em
					.createQuery("SELECT i FROM OrderItem i WHERE i.kitchenOut=:kitchenOut and i.ordertype is not null and i.orders.cancel=:cancel and i.orders.flag=:flag and i.orders.billReqStatus=:billReqStatus and i.orders.storeId=:storeId and i.orders.orderDate IN (:dateList) order by i.orders.id",
					    OrderItem.class);

			qry.setParameter("kitchenOut", kitchenOut);
			qry.setParameter("cancel", cancel);
			qry.setParameter("flag", paidFlag);
			qry.setParameter("billReqStatus", billReqStatus);
			qry.setParameter("storeId", storeid);
			qry.setParameter("dateList", dateList);
			
			itemList = qry.getResultList();
			
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      List<Integer> menuItemIdList = new ArrayList<>();
      
      Map<Integer, OrderItem> orderItemMap = new HashMap<>();
      List<Integer> orderItemIdList = new ArrayList<>();
      
      Iterator<OrderItem> orderItemIterator = itemList.iterator();
      while (orderItemIterator.hasNext()) {
        OrderItem orderItem = orderItemIterator.next();
        
        orderItemIdList.add(orderItem.getId());
        orderItemMap.put(orderItem.getId(), orderItem);
        
        menuItemIdList.add(orderItem.getItem().getId());
        menuItemMap.put(orderItem.getItem().getId(), orderItem.getItem());
      }
      

//			Iterator<OrderItem> iterator1 = itemList.iterator();
//			while (iterator1.hasNext()) {
//
//				OrderItem orderItem = (OrderItem) iterator1.next();
//				MenuItem item1 = orderItem.getItem();
//				int itemId = item1.getId();

				// for translation
				if (!lang.equalsIgnoreCase("") && lang.length() > 0 && !lang.equalsIgnoreCase(Constants.DEFAULT_LANG)) {
					try {
						// check if item translated
						TypedQuery<MenuItemLangMap> qry2 = em
	              .createQuery("SELECT l FROM MenuItemLangMap l WHERE l.menuItem.id IN (:itemIdList) AND l.storeId=:storeId and l.language=:language", MenuItemLangMap.class);
//								.createQuery("SELECT l FROM MenuItemLangMap l WHERE l.menuItem.id=:itemId and l.storeId=:storeId and l.language=:language");
	          qry2.setParameter("itemIdList", menuItemIdList);
//          qry2.setParameter("itemId", itemId);
						qry2.setParameter("storeId", storeid);
						qry2.setParameter("language", lang);
//						MenuItemLangMap itemLng = (MenuItemLangMap) qry2.getSingleResult();
//						item1.setName(itemLng.getName());
//						item1.setDescription(itemLng.getDescription());
						
	          List<MenuItemLangMap> itemLngList = qry2.getResultList();
	          for(MenuItemLangMap itemLng : itemLngList) {
	            MenuItem menuItem = menuItemMap.get(itemLng.getMenuItem().getId());
	            menuItem.setName(itemLng.getName());
	            menuItem.setDescription(itemLng.getDescription());
	          }
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				/*
        Iterator<OrderItem> iterator = itemList.iterator();
        while (iterator.hasNext()) {
          OrderItem orderItem = iterator.next();
          int orderItemId = orderItem.getId();
          TypedQuery<OrderMaster> query = em
//            .createQuery("SELECT o FROM OrderMaster o, OrderItem i WHERE i.id IN (:orderItemIdList) and o.id=i.orders", OrderMaster.class);
              .createQuery("SELECT o FROM OrderMaster o, OrderItem i WHERE i.id=:id and o.id=i.orders", OrderMaster.class);
//        query.setParameter("orderItemIdList", orderItemIdList);
          query.setParameter("id", orderItemId);
          OrderMaster order1 = (OrderMaster) query.getSingleResult();
          orderItem.setOrdrId(order1.getId());
          
          MenuItem item = menuDAO.getItemDetailsById("" + orderItem.getItem().getId(), "" + storeid);
          orderItem.setKitchenName(item.getMenucategory().getMenutype().getMenuCategoryName());
        }
        */
				
				// Avoiding repeated calls for getItemDetailsById below.
				List<MenuItem> menuItemList = menuDAO.getAllItemDetails(storeid);
				for(MenuItem menuItem : menuItemList) {  menuItemMap.put(menuItem.getId(), menuItem); }
	        
        for(Integer orderItemId : orderItemIdList) {
        	OrderItem orderItem = orderItemMap.get(orderItemId);
	        orderItem.setOrdrId(orderItem.getOrders().getId());
	        orderItem.setOrderNo(orderItem.getOrders().getOrderNo());
	        MenuItem item = menuItemMap.get(orderItem.getItem().getId()); //menuDAO.getItemDetailsById((orderItem.getItem().getId()), (storeid));
	        orderItem.setKitchenName(item.getMenucategory().getMenutype().getMenuCategoryName());
        }
//			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
			if (em != null) em.close();
		}

		return itemList;
	}

	@Override
	public String printBill(String orderid, Integer storeid) throws DAOException {

		EntityManager em = null;
		int orderId = Integer.parseInt(orderid);
		int storeId = (storeid);
		String status = "";
		OrderMaster order = null;
		double totalNonVatItemOrderAmt = 0.0;
		double totalVatItemOrderAmt = 0.0;
		double totalVatAmt = 0.0;
		double totalServceTaxAmt = 0.0;

		String createdBy = null;

		BillingDAO billDao = new BillingDAOImpl();

		try {
			System.out.println("Print startted in DAO...");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeId",
					    OrderMaster.class);
			qry.setParameter("orderid", orderId);
			qry.setParameter("storeId", storeId);
			order = (OrderMaster) qry.getSingleResult();

			int orderId1 = order.getId();
			int storeId1 = order.getStoreId();

			TypedQuery<Payment> qry2 = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderId1 and p.paidAmount!=0.0 and p.storeId=:storeId1",
					    Payment.class);
			qry2.setParameter("orderId1", orderId1);
			qry2.setParameter("storeId1", storeId1);
			List<Payment> paymentList = qry2.getResultList();
			
			createdBy = paymentList.size() > 0 ? paymentList.get(0).getCreatedBy() : createdBy;
			/*
			Iterator<Payment> iteratorPayment = paymentList.iterator();
			while (iteratorPayment.hasNext()) {
				Payment payment = (Payment) iteratorPayment.next();
				createdBy = payment.getCreatedBy();
				break;
			}
			*/

			// check if bill print is true for the store
			// int storeId = order.getStoreId();
			TypedQuery<StoreMaster> qrySM = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid",
					    StoreMaster.class);
			qrySM.setParameter("storeid", storeId);
			StoreMaster store = (StoreMaster) qrySM.getSingleResult();
			String billPrintServerFlag = store.getBillPrint();
			String roundOffStatus = store.getRoundOffTotalAmtStatus();
			String roundOffDouble = store.getDoubleRoundOff();

			// 14.5% items

			// String billPrintMobFlag = store.getMobBillPrint();

			/****************
			 * start bill print***********************
			 * 
			 */

			// if kitchen admin print is true

			if ("Y".equalsIgnoreCase(billPrintServerFlag)) {
				// int storeId = order.getStoreId();
//				String s = "\\u0020"; // for space
				// store name
				String strName = store.getStoreName();
				// store address
				String address = store.getAddress();
				StringBuffer emailId = new StringBuffer();
				if (store.getEmailId() != null
						&& !store.getEmailId().trim().equalsIgnoreCase("")) {
					emailId.append("Email:");
					emailId.append(store.getEmailId());
				}
				StringBuffer phoneNumbr = new StringBuffer();
				phoneNumbr.append("Ph:");
				// get phone number
				String phone = store.getPhoneNo();
				phoneNumbr.append(phone);
				StringBuffer vatReg = new StringBuffer();
				StringBuffer gstReg = new StringBuffer();
				StringBuffer serviceTax = new StringBuffer();
				String paxString="";
				if (store.getGstRegNo() != null) { // check GST / VAT, STAX
					if (store.getGstRegNo().trim().length() > 0) {
						gstReg.append(store.getGstText() + ": ");
						gstReg.append(store.getGstRegNo());
					}
				} else {
					if (store.getVatTaxText() == null
							|| "".equalsIgnoreCase(store.getVatTaxText().trim())) {
						vatReg.append("Vat" + "  Reg. No: ");
					} else {
						vatReg.append(store.getVatTaxText() + "  Reg. No: ");
					}
					// get vat reg no.
					String vatRegNo = store.getVatRegNo();
					vatReg.append(vatRegNo);

					if (storeId == 120)
						serviceTax.append("S.Tax No: ");
					else {
						serviceTax.append(store.getServiceTaxText()
								+ " Tax No: ");
					}
					// get service tax no.
					String serviceTaxNo = store.getServiceTaxNo();
					serviceTax.append(serviceTaxNo);
				}
				StringBuffer invoice = new StringBuffer();
				if (storeId == 120)
					invoice.append("Invoice No:  ");
				else {
					invoice.append("Order No:  ");
				}
				//invoice.append(orderId + "");
				invoice.append(order.getOrderNo() + "");//after incorporating order_no
				if ("Y".equalsIgnoreCase(store.getIsPax())) {
					//invoice.append("     " + "Pax(s): "+order.getNoOfPersons());
					paxString="     Pax(s): "+order.getNoOfPersons();
					//int noOfPersons = order.getNoOfPersons();
					//invoice.append(noOfPersons + "");
				}

				// 14.5 and 20 % tax calculate for elan
				StringBuffer dateTable = new StringBuffer();
				dateTable.append("Date: ");

				// formatted
				String orderDate = order.getOrderTime();

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				Date date = sdf.parse(orderDate);
				sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
				orderDate = sdf.format(date);

				dateTable.append(orderDate);
				// add cashier
				Customer loggedCustomer1 = order.getCustomers();
				StringBuffer cashierString = new StringBuffer();
				cashierString.append("Cashier: ");
				cashierString.append(loggedCustomer1.getContactNo());
				/*
				 * if (createdBy != null) { if (createdBy.length() > 0)
				 * cashierString.append(createdBy);
				 * 
				 * }
				 */

				// Add server name
				StringBuffer serverString = new StringBuffer();
				serverString.append("Server: ");
				serverString.append(loggedCustomer1.getContactNo());
				/*
				 * if (createdBy != null) { if (createdBy.length() > 0)
				 * serverString.append(createdBy);
				 * 
				 * }
				 */

				StringBuffer tableNoStrng = new StringBuffer();
				String tableNo = order.getTable_no();
				
				
				if (tableNo != null && tableNo.length() > 0 && !tableNo.equalsIgnoreCase("0")) {
					tableNoStrng.append("Table No. ");
					tableNoStrng.append(tableNo);
					invoice.append(paxString);
				} else {
					tableNoStrng.append(order.getOrdertype().getOrderTypeName());
				}

//				String kitchenPrnt = "";
				String kot = "         KOT";
				StringBuffer kitchenHdrStrng = new StringBuffer();
				StringBuffer kitchenItemStrng = new StringBuffer();
				StringBuffer kitchenEndStrng = new StringBuffer();
				// StringBuffer itemNameStrng = new StringBuffer();
//				String finalItemName = "";
//				String upTo3Characters = strName.substring(0, Math.min(strName.length(), 3));
				kitchenHdrStrng.append(kot);
				kitchenHdrStrng.append("\n");
				kitchenHdrStrng.append(strName); // 1st string
				kitchenHdrStrng.append("\n");
				kitchenHdrStrng.append("\n");
				// get order time
				String ordeTime = order.getOrderTime();

				String[] tempURL = ordeTime.split("/");
				String day = tempURL[0];
				String mnth = tempURL[1];
				String dayMnth = day + "/" + mnth;
				String[] tempURL1 = ordeTime.split(" ");
				String time = tempURL1[1];
				System.out.println("dayMnth.." + dayMnth);
				System.out.println("time.." + time);
				String[] timefull = time.split(":");
				String hr = timefull[0];
				String mins = timefull[1];
				String hrMins = hr + ":" + mins;
				System.out.println("hr mins:::  " + hrMins);
				String dtTime = dayMnth + " " + hrMins;
				System.out.println("dttime:: " + dtTime);
				StringBuffer secndString = new StringBuffer();
				secndString.append(dtTime); // 2nd string start
				secndString.append(" ");
				// get order taken by person info
				Customer customer = order.getCustomers();
				int custId = customer.getId();
				secndString.append("(");
				if (custId > 0) {
					secndString.append("" + custId);
				}
				secndString.append(")"); // 2nd string end
				kitchenHdrStrng.append("\n");
				StringBuffer thrdString = new StringBuffer();
				thrdString.append("Order No:"); // 3rd string start
				//thrdString.append(orderId);
				thrdString.append(order.getOrderNo());//after incorporating order_no
				thrdString.append(",");
				thrdString.append("Table-");
				// get table no.
				String tblNo = order.getTable_no();
				if (tblNo != null && tblNo.length() > 0) {
					thrdString.append(tblNo);
				} else {
					thrdString.append("");
				}
				// 3rd string end
				// 4th string start
//				String noOfPersons = "No. of persons:" + order.getNoOfPersons() + "";
				// 4th string end

				kitchenHdrStrng.append("\n\n");
				// declare a map to hold item name, item quantity
				// get items from order
				Map<Integer, List<String>> mapToHoldItems = new LinkedHashMap<Integer, List<String>>();
				List<OrderItem> orderItemLst = order.getOrderitem();
				Iterator<OrderItem> ordrItr = orderItemLst.iterator();
				while (ordrItr.hasNext()) {
					OrderItem orderItem = (OrderItem) ordrItr.next();
					// get the special note
					String specialNote = orderItem.getSpecialNote();
					MenuItem menuItem = orderItem.getItem();
					String itemName = menuItem.getName();
					int itemId = menuItem.getId();
					StringBuffer nameNSpecl = new StringBuffer();
					nameNSpecl.append(itemName);
					nameNSpecl.append("\n" + "##");
					nameNSpecl.append(specialNote);
//					String nameWithSpclNote = nameNSpecl.toString();
					// String nameWithSpclNote=itemName+"##"+specialNote;
//					int itemLength = itemName.length();
//					int maxLength = 28;

					/*
					 * if (itemLength > maxLength) itemName =
					 * itemName.substring(0, Math.min(itemName.length(),
					 * maxLength));
					 * 
					 * itemLength = itemName.length(); StringBuffer
					 * itemNameStrng = new StringBuffer(); if (itemLength <
					 * maxLength + 1) { itemNameStrng.append(itemName); int
					 * remLength = maxLength - itemLength; for (int i = 1; i <=
					 * remLength; i++) { itemNameStrng.append(c);
					 * itemNameStrng.append(c);
					 * 
					 * }
					 * 
					 * itemName = itemNameStrng.toString(); }
					 */
					String quantity = orderItem.getQuantityOfItem();
					double totalPriceByItem = orderItem.getTotalPriceByItem();

					DecimalFormat decim = new DecimalFormat("00.00");
					String totalPriceItem = decim.format(totalPriceByItem);
					double rate = orderItem.getRate();
					String itemRate = decim.format(rate);
					String hsnCode=orderItem.getItem().getCode();
					double vatForItem = menuItem.getVat();
					Double itemServiceTax = menuItem.getServiceTax();
					List<String> itemLst = new ArrayList<String>();
					if(store.getId() == 164) {
						itemName=itemName+" ("+hsnCode+")";
						rate=rate+(rate*vatForItem/100+rate*itemServiceTax/100);
						totalPriceByItem=totalPriceByItem+(totalPriceByItem*vatForItem/100+totalPriceByItem*itemServiceTax/100);
						itemRate = decim.format(rate);
						totalPriceItem = decim.format(totalPriceByItem);
					}
					itemLst.add(0, itemName);
					itemLst.add(1, quantity);
					itemLst.add(2, totalPriceItem);
					// add rate
					itemLst.add(3, itemRate);
					// add hsn
					itemLst.add(4,hsnCode);
					List<String> itemLstOld = mapToHoldItems.get(itemId);

					if (itemLstOld != null && itemLstOld.size() > 0) {
						itemLst.set(1, new Integer(itemLstOld.get(1))
								+ new Integer(itemLst.get(1)) + "");

						double total = new Double(itemLstOld.get(2))
								+ new Double(itemLst.get(2));
						DecimalFormat dec = new DecimalFormat("00.00");
						String totalPrice = dec.format(total);
						itemLst.set(2, totalPrice);

					}

					mapToHoldItems.put(itemId, itemLst);

					// calculate non Vat item order amount
					//totalPriceByItem = orderItem.getTotalPriceByItem();//reinitialise becuase of balaram
					// service tax calculation for each item
					Double itemDiscPer = new Double(
							menuItem.getPromotionValue());
					Double itemDisc = (itemDiscPer * totalPriceByItem) / 100;
					Double serviceTaxForThsItem = (itemServiceTax * (totalPriceByItem - itemDisc)) / 100;
					if (order.getTable_no().trim().equalsIgnoreCase("0")) {
						if (store.getParcelServiceTax().equalsIgnoreCase("N")) {
							serviceTaxForThsItem = 0.0;
						}
					}
					
					totalServceTaxAmt = totalServceTaxAmt + serviceTaxForThsItem;

					if (vatForItem <= 0.0) {
						totalNonVatItemOrderAmt = totalNonVatItemOrderAmt	+ totalPriceByItem;
					}
					else if (vatForItem > 0.0) {
						totalVatItemOrderAmt = totalVatItemOrderAmt	+ totalPriceByItem;
						double vatForThsItem = (vatForItem * (totalPriceByItem - itemDisc)) / 100;
						
						if (order.getTable_no().trim().equalsIgnoreCase("0")) {
							if (store.getParcelVat().equalsIgnoreCase("N")) {
								vatForThsItem = 0.0;
							}
						}
						totalVatAmt = totalVatAmt + vatForThsItem;
					}

					// difference vat calculation for elan siliguri

					itemName = String.format("%-35s:%s", itemName, quantity);
					kitchenItemStrng.append(itemName);
					kitchenItemStrng.append("\n");
				}

				// create a list to hold the non vat item order amount
				DecimalFormat decim = new DecimalFormat("00.00");
				String totalNonVatItemOrdrAmt = decim
						.format(totalNonVatItemOrderAmt);
				List<String> nonVatItemOrdrAmt = new ArrayList<String>();

				if (store.getVatTaxText().trim().length() == 0) {
					nonVatItemOrdrAmt.add(0, "Item Order Amt");
				} else {
					if("CGST".equalsIgnoreCase(store.getVatTaxText()))
					{
						nonVatItemOrdrAmt.add(0, "Non " + "GST" +" Amt");
					}
					else
					{
						nonVatItemOrdrAmt.add(0, "Non " + store.getVatTaxText() +" Amt");
					}
					// nonVatItemOrdrAmt.add(0, "Non " + store.getVatTaxText() +
					// " Item Order Amt");
					//nonVatItemOrdrAmt.add(0, "Item Order Amt");
				}
				nonVatItemOrdrAmt.add(1, totalNonVatItemOrdrAmt);

				// create a list to hold the vat item order amount
				List<String> vatItemOrdrAmt = new ArrayList<String>();
				DecimalFormat decim1 = new DecimalFormat("00.00");
				String totalVatItemOrdrAmt = decim1
						.format(totalVatItemOrderAmt);

				// vatItemOrdrAmt.add(0, store.getVatTaxText() +
				// " Item Order Amt");
				vatItemOrdrAmt.add(0, "Item Order Amt");
				vatItemOrdrAmt.add(1, totalVatItemOrdrAmt);

				// create a list to hold the total 14.5% vat amount

				// create a list to hold the total 14.5% vat amount

				// create a list to hold the total order amount
				double totalOrderAmt = totalNonVatItemOrderAmt
						+ totalVatItemOrderAmt;
				List<String> totalAmt = new ArrayList<String>();
				DecimalFormat decim2 = new DecimalFormat("00.00");
				String totalOrderAmtStr = decim2.format(totalOrderAmt);
				if (store.getId() == 37 || store.getId() == 38) {
					totalAmt.add(0, "Sub Total Amt");
				} else {
					totalAmt.add(0, "Total Order Amt");
				}
				totalAmt.add(1, totalOrderAmtStr);

				// create a list to hold the vat
				List<String> vatAmtLst = new ArrayList<String>();

				if (store.getVatAmt() == 0.0) {

					vatAmtLst.add(0, store.getVatTaxText());
				} else if (store.getVatAmt() > 0.0) {
					if(store.getId() == 164) {
					vatAmtLst.add(0,store.getVatTaxText());
					}else {
					vatAmtLst.add(0,store.getVatTaxText() + " @" + store.getVatAmt()+ "%");	
					}
				}
				// double roundOffVat = Math.round(totalVatAmt * 100.0) / 100.0;
				DecimalFormat decim3 = new DecimalFormat("00.00");
				// newly added
				totalVatAmt = order.getOrderBill().getVatAmt();
				String roundOffVatStr = decim3.format(totalVatAmt);
				vatAmtLst.add(1, roundOffVatStr);

				// create a list to hold the round off adjustment
				/*
				 * List<String> roundOffAdjLst = new ArrayList<String>();
				 * roundOffAdjLst.add(0,"Rounding Adj."); // double roundOffVat
				 * = Math.round(totalVatAmt * 100.0) / 100.0; double
				 * roundingAdj=order.getOrderBill().getRoundOffAmt();
				 * DecimalFormat decim8 = new DecimalFormat("00.00"); String
				 * roundingAdjStr = decim8.format(roundingAdj);
				 * roundOffAdjLst.add(1, roundingAdjStr);
				 */

				// create a list to hold the service tax
//				double servcTax = 0.0;
				List<String> servcTaxLst = new ArrayList<String>();
				// double roundOffStax = Math.round(totalServceTaxAmt * 100.0) /
				// 100.0;
				DecimalFormat decim4 = new DecimalFormat("00.00");
				// newly added
				totalServceTaxAmt = order.getOrderBill().getServiceTaxAmt();
				String roundOffStaxStr = decim4.format(totalServceTaxAmt);
				if (store.getServiceTaxAmt() == 0.0) {
					servcTaxLst.add(0, store.getServiceTaxText());
				} else if (store.getServiceTaxAmt() > 0.0) {
					if(store.getId() == 164) {
						servcTaxLst.add(0,store.getServiceTaxText());
					}else {
						servcTaxLst.add(0,store.getServiceTaxText() + " @"+ store.getServiceTaxAmt() + "%");
					}
					

				}
				servcTaxLst.add(1, roundOffStaxStr);

				// create a list to hold the service charge
				double servcCharge = order.getOrderBill().getServiceChargeAmt();
				double servcChargeRate = order.getOrderBill().getServiceChargeRate();
				List<String> servcChrgLst = new ArrayList<String>();
				decim4 = new DecimalFormat("00.00");

				String roundOffServcChargeStr = decim4.format(servcCharge);
				if (store.getServiceChargeRate() == 0.0) {
					servcChrgLst.add(0, store.getServiceChargeText());
				} else if (store.getServiceChargeRate() > 0.0) {
					if(store.getId() == 133) {
						servcChrgLst.add(0, store.getServiceChargeText());
					}else {
					servcChrgLst.add(0, store.getServiceChargeText() + " @"
							+ servcChargeRate + "%");
					}
				}
				servcChrgLst.add(1, roundOffServcChargeStr);

				// create a list to hold the discount
				Double totalDiscnt = 0.0;
				Bill bill = billDao.getBillByOrderId(orderid);
				Double custDiscount = bill.getCustomerDiscount();
				Double itemsDiscount = bill.getTotalDiscount();
				/*
				 * if (discount != null) totalDiscnt = discount;
				 * 
				 * if (itemsDiscount != null) totalDiscnt = totalDiscnt +
				 * itemsDiscount;
				 */

				List<String> itemDiscntLst = new ArrayList<String>();
				String itemDscntStr = decim4
						.format(itemsDiscount.doubleValue());
				itemDiscntLst.add(0, "-Items Disc.");
				itemDiscntLst.add(1, itemDscntStr);

				List<String> custDiscntLst = new ArrayList<String>();
				String custDscntStr = decim4.format(custDiscount.doubleValue());
				double custDiscPercntage = order.getOrderBill()
						.getDiscountPercentage();
				custDiscntLst.add(
						0,
						"-Customer Disc." + "("
								+ Double.toString(custDiscPercntage) + "%)");
				custDiscntLst.add(1, custDscntStr);

				// create a list to hold the gross bill
				List<String> grossLst = new ArrayList<String>();
				double gross = 0.0;
				double dscnt = 0.0;
				// calculate gross
				// if (discount != null & itemDiscount != null)
				{
					dscnt = totalDiscnt;

				}

				if (store.getId() == 35
						&& order.getTable_no().trim().equalsIgnoreCase("0")) { // for
																				// marufaz
					totalServceTaxAmt = 0.0;
				}

				System.out.println("totalOrderAmt   " + totalOrderAmt
						+ " totalVatAmt " + totalVatAmt + " totalServceTaxAmt "
						+ totalServceTaxAmt + " dscnt " + dscnt);

				String formattedTotalVatStr = decim3.format(totalVatAmt);
				totalVatAmt = Double.parseDouble(formattedTotalVatStr);
				// service charge added to gross
				if(store.getId() == 164) {
					gross = (totalOrderAmt + servcCharge)
							- dscnt;
				}
				else
				{
					gross = (totalOrderAmt + totalVatAmt + totalServceTaxAmt + servcCharge)
							- dscnt;
				}
				

				if (roundOffStatus != null
						&& !roundOffStatus.equalsIgnoreCase("")
						&& roundOffStatus.equalsIgnoreCase("Y")) {
					gross = new Double(Math.round(gross)); // round
				} else if (roundOffDouble != null
						&& !roundOffDouble.equalsIgnoreCase("")
						&& roundOffDouble.equalsIgnoreCase("Y")) {
					String formvl = decim4.format(gross);
					gross = Double.parseDouble(formvl);
					gross = new Double(
							CommonProerties.roundOffUptoTwoPlacesDouble(gross,
									1)); // round
					// off
					// double
				}

				String roundOffGrossStr = decim4.format(gross);
				/*
				 * if (roundOffStatus != null &&
				 * !roundOffStatus.equalsIgnoreCase("") &&
				 * roundOffStatus.equalsIgnoreCase("Y")) { grossLst.add(0,
				 * "TOTAL(Round off)"); } else { grossLst.add(0, "TOTAL"); }
				 */
				grossLst.add(0, "TOTAL");
				grossLst.add(1, roundOffGrossStr);

				// create a list to hold the paid amount
				List<String> paidAmtLst = new ArrayList<String>();

				double amount = 0.0;
				double paidAmount = 0.0;
				double amtToPay = 0.0;
				double tenderAmt = 0.0;
				double refundAmt = 0.0;
				String refundAmtUptoTwoDecimal = "";

				Set<String> pTypes = new TreeSet<String>();
				String paymentType = "";

				try {
					List<Payment> paymentLst = billDao
							.getPaymentInfoByOrderId(orderid);
					Iterator<Payment> iterator = paymentLst.iterator();
					while (iterator.hasNext()) {
						Payment payment = (Payment) iterator.next();
						amount = payment.getAmount();
						paidAmount = paidAmount + payment.getPaidAmount();
						tenderAmt = tenderAmt + payment.getTenderAmount();
						if (payment.getPaymentMode() != null)
							pTypes.add(payment.getPaymentMode());

						if (payment.getPaymentMode() != null
								&& payment.getPaymentMode().equalsIgnoreCase(
										"cash")) {
							refundAmt = tenderAmt - paidAmount;
							if (refundAmt < 0) {
								refundAmt = 0.00;
								DecimalFormat df = new DecimalFormat("00.00");
								refundAmtUptoTwoDecimal = df.format(refundAmt);
							} else if (refundAmt > 0) {

								DecimalFormat df = new DecimalFormat("00.00");
								refundAmtUptoTwoDecimal = df.format(refundAmt);

							}

						}
					}

					if (pTypes.size() == 1) {
						paymentType = pTypes.toArray()[0].toString();
					}
					if (pTypes.size() == 2) {
						paymentType = pTypes.toArray()[0].toString() + " and "
								+ pTypes.toArray()[1].toString();
					}

					amtToPay = amount - paidAmount;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String paidAmountToTwoDecimalPlaces = decim4.format(paidAmount);
				paidAmtLst.add(0, "Paid Amount");
				paidAmtLst.add(1, paidAmountToTwoDecimalPlaces);

				// create a list to hold the amount to pay
				List<String> amtToPayLst = new ArrayList<String>();

				String amtToPayToTwoDecimalPlaces = decim4.format(amtToPay);
				amtToPayLst.add(0, "Amount to Pay");
				amtToPayLst.add(1, amtToPayToTwoDecimalPlaces);

				// create a list to hold the tender amount
				List<String> tenderAmtLst = new ArrayList<String>();

				String tenderAmtToTwoDecimalPlaces = decim4.format(tenderAmt);
				tenderAmtLst.add(0, "Tender Amount");
				tenderAmtLst.add(1, tenderAmtToTwoDecimalPlaces);

				// create a list to hold payment types
				List<String> paymntTypeLst = new ArrayList<String>();
				paymntTypeLst.add(0, "Payment Type");
				paymntTypeLst.add(1, paymentType);

				// create a list to hold refund amount
				List<String> refundAmtLst = new ArrayList<String>();
				refundAmtLst.add(0, "Refund Amount");
				refundAmtLst.add(1, refundAmtUptoTwoDecimal);

				kitchenEndStrng.append("..........................");

				// String s = "\\u0020"; //for space
				// char c = (char) Integer.parseInt(s.substring(2), 16);
//				int orientation = 1;
				// String data =
				// "PIZA HUT:2015-01-15::16.18\nOrder No:1752,T-14\n\nChowin: 3\nChicken Tikka: 2\n--------------------";
//				kitchenPrnt = kitchenHdrStrng.toString()
//						+ kitchenItemStrng.toString()
//						+ kitchenEndStrng.toString();

				Customer loggedCustomer = order.getCustomers();
				String userId = loggedCustomer.getName();
				if (createdBy != null) {
					if (createdBy.trim().length() > 0)
						userId = createdBy;
				} else {
					createdBy = userId;
				}
				// for home delivery
				String homeDeliveryCustName = "";
				String homeDeliveryCustAddr = "";
				String homeDeliveryCustPh = "";
				String homeDeliveryPersonName = "";
				String homeDeliveryLocation = "";
				String homeDeliveryStreet = "";
				String homeDeliveryHouseNo = "";
				String custVatRegNo = "";
				/** New Fields */
//				String carNo = "";
//				Date anniversary_date = new Date();
//				Date dob = new Date();

				if (order.getTable_no().equalsIgnoreCase("0")
						&& store.getParcelAddress().equalsIgnoreCase("Y")) {
					homeDeliveryCustName = order.getCustomerName();
					homeDeliveryCustAddr = order.getDeliveryAddress();
					homeDeliveryCustPh = order.getCustomerContact();
					homeDeliveryPersonName = order.getDeliveryPersonName();
					homeDeliveryLocation = order.getLocation();
					homeDeliveryStreet = order.getStreet();
					homeDeliveryHouseNo = order.getHouseNo();
					custVatRegNo = order.getCustVatRegNo();
				}
				
				// print waiter name
				String waiterName=null;
				waiterName=order.getWaiterName();
				/*
				 * new SilentPrintBill().printByRequest(orientation, orderId +
				 * "", kitchenPrnt, request);
				 */
				Object[] arryToHoldElems = new Object[50];
				// arryToHoldElems[0] = kot;
				arryToHoldElems[0] = strName;
				arryToHoldElems[1] = address;
				arryToHoldElems[2] = emailId.toString();
				arryToHoldElems[3] = phoneNumbr.toString();
				arryToHoldElems[4] = vatReg.toString();
				arryToHoldElems[5] = serviceTax.toString();
				arryToHoldElems[6] = invoice.toString();
				arryToHoldElems[7] = dateTable.toString();
				arryToHoldElems[8] = mapToHoldItems;
				arryToHoldElems[9] = nonVatItemOrdrAmt;
				arryToHoldElems[10] = vatItemOrdrAmt;
				arryToHoldElems[11] = totalAmt;
				arryToHoldElems[12] = vatAmtLst;
				arryToHoldElems[13] = servcTaxLst;
				arryToHoldElems[14] = itemDiscntLst;
				arryToHoldElems[15] = grossLst;
				arryToHoldElems[16] = tableNoStrng.toString();
				arryToHoldElems[17] = paidAmtLst;
				arryToHoldElems[18] = amtToPayLst;
				arryToHoldElems[19] = custDiscntLst;
				arryToHoldElems[20] = userId;
				arryToHoldElems[21] = tenderAmtLst;
				arryToHoldElems[22] = paymntTypeLst;
				arryToHoldElems[23] = refundAmtLst;
				arryToHoldElems[24] = homeDeliveryCustName;
				arryToHoldElems[25] = homeDeliveryCustAddr;
				arryToHoldElems[26] = homeDeliveryCustPh;
				arryToHoldElems[27] = homeDeliveryPersonName;
				arryToHoldElems[28] = tblNo;
				arryToHoldElems[29] = store.getBillFooterText();
				arryToHoldElems[30] = store.getThanksLine1();
				arryToHoldElems[31] = store.getThanksLine2();
				arryToHoldElems[32] = order.getOrderBill().getRoundOffAmt();
				arryToHoldElems[33] = cashierString.toString();
				arryToHoldElems[34] = serverString.toString();
				arryToHoldElems[35] = servcChrgLst;
				arryToHoldElems[36] = custVatRegNo;
				arryToHoldElems[37] = homeDeliveryLocation;
				arryToHoldElems[38] = homeDeliveryStreet;
				arryToHoldElems[39] = homeDeliveryHouseNo;
				arryToHoldElems[40] = gstReg.toString();
				arryToHoldElems[41] = storeId;
				arryToHoldElems[42] = waiterName;

				try { // to print
						// if (billPrintServerFlag.equalsIgnoreCase("Y")) {

					new BillPosPrinterMain().a(arryToHoldElems, storeId);
					updateBillPrint(orderId);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				System.out.println("Print end in DAO...");
			}
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if (em != null) em.close();
		}

		return status;
	}

	public String printSplitBill(String orderid, Integer storeid)
			throws DAOException {

		EntityManager em = null;
		// BillingDAO billingDAO = new BillingDAOImpl();
		int orderId = Integer.parseInt(orderid);
		int storeId = (storeid);
		String status = "";
		OrderMaster order = null;
		double totalNonVatItemOrderAmt = 0.0;
		double totalVatItemOrderAmt = 0.0;

		String createdBy = null;

		BillingDAO billDao = new BillingDAOImpl();

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeId",
					    OrderMaster.class);
			qry.setParameter("orderid", orderId);
			qry.setParameter("storeId", storeId);
			order = qry.getSingleResult();

			int orderId1 = order.getId();
			int storeId1 = order.getStoreId();

			TypedQuery<Payment> qry2 = em
					.createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderId1 and p.paidAmount!=0.0 and p.storeId=:storeId1",
					    Payment.class);
			qry2.setParameter("orderId1", orderId1);
			qry2.setParameter("storeId1", storeId1);
			List<Payment> paymentList = qry2.getResultList();
			Iterator<Payment> iteratorPayment = paymentList.iterator();
			while (iteratorPayment.hasNext()) {
				Payment payment = (Payment) iteratorPayment.next();
				createdBy = payment.getCreatedBy();
				break;
			}

			// check if bill print is true for the store
			// int storeId = order.getStoreId();
			TypedQuery<StoreMaster> qry1 = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid",
					    StoreMaster.class);
			qry1.setParameter("storeid", storeId);
			StoreMaster store = (StoreMaster) qry1.getSingleResult();
			String billPrintServerFlag = store.getBillPrint();
			String roundOffStatus = store.getRoundOffTotalAmtStatus();
			String roundOffDouble = store.getDoubleRoundOff();
			// String billPrintMobFlag = store.getMobBillPrint();

			/****************
			 * start bill print************************
			 * 
			 */

			// if kitchen admin print is true

			if (billPrintServerFlag.equalsIgnoreCase("Y")) {
				// int storeId = order.getStoreId();
//				String s = "\\u0020"; // for space
//				char c = (char) Integer.parseInt(s.substring(2), 16);

				// store name
				String strName = store.getStoreName();
				// store address
				String address = store.getAddress();
				StringBuffer emailId = new StringBuffer();
				if (store.getEmailId() != null
						&& !store.getEmailId().trim().equalsIgnoreCase("")) {
					emailId.append("Email:");
					emailId.append(store.getEmailId());
				}
				StringBuffer phoneNumbr = new StringBuffer();
				phoneNumbr.append("Ph:");
				// get phone number
				String phone = store.getPhoneNo();
				phoneNumbr.append(phone);
				
				StringBuffer vatReg = new StringBuffer();
				StringBuffer gstReg = new StringBuffer();
				StringBuffer serviceTax = new StringBuffer();
				if (store.getGstRegNo() != null) { // check GST / VAT, STAX
					if (store.getGstRegNo().trim().length() > 0) {
						gstReg.append(store.getGstText() + ": ");
						gstReg.append(store.getGstRegNo());
					}
				} else {
					if (store.getVatTaxText().equalsIgnoreCase("")
							|| store.getVatTaxText() == null
							|| store.getVatTaxText().trim().length() == 0) {
						vatReg.append("Vat" + "  Reg. No: ");
					} else {
						vatReg.append(store.getVatTaxText() + "  Reg. No: ");
					}
					// get vat reg no.
					String vatRegNo = store.getVatRegNo();
					vatReg.append(vatRegNo);

					if (storeId == 120)
						serviceTax.append("S.Tax No: ");
					else {
						serviceTax.append(store.getServiceTaxText()
								+ " Tax No: ");
					}
					// get service tax no.
					String serviceTaxNo = store.getServiceTaxNo();
					serviceTax.append(serviceTaxNo);
				}

				StringBuffer dateTable = new StringBuffer();
				dateTable.append("Date: ");

				String orderDate = order.getOrderTime();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss");
				Date date = sdf.parse(orderDate);
				sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
				orderDate = sdf.format(date);
				dateTable.append(orderDate);

				// add cashier
				Customer loggedCustomer1 = order.getCustomers();
				StringBuffer cashierString = new StringBuffer();
				cashierString.append("Cashier: ");
				cashierString.append(loggedCustomer1.getContactNo());
				/*
				 * if (createdBy != null) { if (createdBy.length() > 0)
				 * cashierString.append(createdBy);
				 * 
				 * }
				 */

				// add server name
				StringBuffer serverString = new StringBuffer();
				serverString.append("Server: ");
				serverString.append(loggedCustomer1.getContactNo());
				/*
				 * if (createdBy != null) { if (createdBy.length() > 0)
				 * serverString.append(createdBy);
				 * 
				 * }
				 */

				StringBuffer tableNoStrng = new StringBuffer();
				String tableNo = order.getTable_no();
				
				
				if (tableNo != null && tableNo.length() > 0 && !tableNo.equalsIgnoreCase("0")) {
					tableNoStrng.append("Table No. ");
					tableNoStrng.append(tableNo);
				} else {
					tableNoStrng.append(order.getOrdertype().getOrderTypeName());
				}

//				String kitchenPrnt = "";
				String kot = "         KOT";
				StringBuffer kitchenHdrStrng = new StringBuffer();
				StringBuffer kitchenItemStrng = new StringBuffer();
				StringBuffer kitchenEndStrng = new StringBuffer();
				// StringBuffer itemNameStrng = new StringBuffer();
//				String finalItemName = "";
//				String upTo3Characters = strName.substring(0,	Math.min(strName.length(), 3));
				kitchenHdrStrng.append(kot);
				kitchenHdrStrng.append("\n");
				kitchenHdrStrng.append(strName); // 1st string
				kitchenHdrStrng.append("\n");
				// get order time
				String ordeTime = order.getOrderTime();

				String[] tempURL = ordeTime.split("/");
				String day = tempURL[0];
				String mnth = tempURL[1];
				String dayMnth = day + "/" + mnth;
				String[] tempURL1 = ordeTime.split(" ");
				String time = tempURL1[1];
				System.out.println("dayMnth.." + dayMnth);
				System.out.println("time.." + time);
				String[] timefull = time.split(":");
				String hr = timefull[0];
				String mins = timefull[1];
				String hrMins = hr + ":" + mins;
				System.out.println("hr mins:::  " + hrMins);
				String dtTime = dayMnth + " " + hrMins;
				System.out.println("dttime:: " + dtTime);
				StringBuffer secndString = new StringBuffer();
				secndString.append(dtTime); // 2nd string start
				secndString.append(" ");
				// get order taken by person info
				Customer customer = order.getCustomers();
				int custId = customer.getId();
				secndString.append("(");
				if (custId > 0) {
					secndString.append("" + custId);
				}
				secndString.append(")"); // 2nd string end
				kitchenHdrStrng.append("\n");
				StringBuffer thrdString = new StringBuffer();
				thrdString.append("Order No:"); // 3rd string start
				//thrdString.append(orderId);
				thrdString.append(order.getOrderNo()); //after incorporating order_no
				thrdString.append(",");
				thrdString.append("Table-");
				// get table no.
				String tblNo = order.getTable_no();
				if (tblNo != null && tblNo.length() > 0) {
					thrdString.append(tblNo);
				} else {
					thrdString.append("");
				}
				// 3rd string end
				// 4th string start
//				String noOfPersons = "No. of persons:" + order.getNoOfPersons();
				// 4th string end

				kitchenHdrStrng.append("\n\n");

				// create a list to hold the non vat item order amount
				DecimalFormat decim = new DecimalFormat("00.00");
				String totalNonVatItemOrdrAmt = decim
						.format(totalNonVatItemOrderAmt);
				List<String> nonVatItemOrdrAmt = new ArrayList<String>();

				if (store.getVatTaxText().trim().length() == 0) {
					nonVatItemOrdrAmt.add(0, "Item Order Amt");
				} else {
					nonVatItemOrdrAmt.add(0, "Non " + store.getVatTaxText()
							+ " Item Order Amt");
				}
				nonVatItemOrdrAmt.add(1, totalNonVatItemOrdrAmt);

				// create a list to hold the vat item order amount
				List<String> vatItemOrdrAmt = new ArrayList<String>();
				DecimalFormat decim1 = new DecimalFormat("00.00");
				String totalVatItemOrdrAmt = decim1
						.format(totalVatItemOrderAmt);

				vatItemOrdrAmt
						.add(0, store.getVatTaxText() + " Item Order Amt");
				vatItemOrdrAmt.add(1, totalVatItemOrdrAmt);

				// create a list to hold the total order amount
//				double totalOrderAmt = totalNonVatItemOrderAmt + totalVatItemOrderAmt;

				Object[] arryToHoldElems = new Object[40];

				arryToHoldElems[0] = strName;
				arryToHoldElems[1] = address;
				arryToHoldElems[2] = emailId.toString();
				arryToHoldElems[3] = phoneNumbr.toString();
				arryToHoldElems[4] = vatReg.toString();
				arryToHoldElems[5] = serviceTax.toString();
				arryToHoldElems[7] = dateTable.toString();
				arryToHoldElems[9] = nonVatItemOrdrAmt;
				arryToHoldElems[10] = vatItemOrdrAmt;
				arryToHoldElems[28] = tblNo;
				arryToHoldElems[29] = store.getBillFooterText();
				arryToHoldElems[30] = store.getThanksLine1();
				arryToHoldElems[31] = store.getThanksLine2();
				arryToHoldElems[32] = order.getOrderBill().getRoundOffAmt();
				arryToHoldElems[33] = cashierString.toString();
				arryToHoldElems[34] = serverString.toString();
				arryToHoldElems[36] = gstReg.toString();

				// declare a map to hold item name, item quantity
				// get items from order
				MenuDAO menuDAO = new MenuDAOImpl();

				// start code to get list of bill split manual
				List<BillSplitManual> billSplitmanuals = billDao
						.getBillSplitManualByOrderId(orderId + "");
				order.setBillSplitManualList(billSplitmanuals);
//				int noOfBills = billSplitmanuals.size();
				int billNo = 0;
				MenuCategory category = null;
				int catId = 0;
				double subTotalAmt = 0.0;
				for (int i = 0; i < billSplitmanuals.size(); i++) {
					double totalVatAmt = 0.0;
					double totalServceTaxAmt = 0.0;
					double totalServceChrgAmt = 0.0;
					double totalcustDiscAmt = 0.0;
					double custDiscPercntage = order.getOrderBill().getDiscountPercentage();
					double serviceChrgRate=order.getOrderBill().getServiceChargeRate();
					Map<Integer, List<String>> mapToHoldItems = new LinkedHashMap<Integer, List<String>>();

					// List<OrderItem> orderItemLst = order.getOrderitem();
					BillSplitManual billSplitmanual = billSplitmanuals.get(i);
					subTotalAmt = billSplitmanual.getTotal();
					billNo = billSplitmanual.getBillNo();
					catId = billSplitmanual.getCategoryId();
					List<BillSplitManual_duplicate> billSplitmanualDuplicateList = billSplitmanual
							.getBillSplitManualList();
					Iterator<BillSplitManual_duplicate> iterator = billSplitmanualDuplicateList
							.iterator();
					while (iterator.hasNext()) {
						BillSplitManual_duplicate billSpltManualDuplicate = (BillSplitManual_duplicate) iterator
								.next();
						int itemId = billSpltManualDuplicate.getItemId();
						// int catId = billSpltManualDuplicate.getCategoryId();
						// category = menuDAO.getCategoryById(catId);
						MenuItem menuItem = menuDAO.getItemDetailsById(itemId, storeId);
						String itemName = menuItem.getName();
						// int itemId = menuItem.getId();

						String quantity = ""
								+ billSpltManualDuplicate.getItemQuantity();
						double totalPriceByItem = billSpltManualDuplicate
								.getBillAmount();

						decim = new DecimalFormat("00.00");
						String totalPriceItem = decim.format(totalPriceByItem);
						double rate = billSpltManualDuplicate.getItemRate();
						String itemRate = decim.format(rate);

						List<String> itemLst = new ArrayList<String>();
						itemLst.add(0, itemName);
						itemLst.add(1, quantity);
						itemLst.add(2, totalPriceItem);
						// add rate
						itemLst.add(3, itemRate);

						List<String> itemLstOld = mapToHoldItems.get(itemId);

						if (itemLstOld != null && itemLstOld.size() > 0) {
							itemLst.set(1, new Integer(itemLstOld.get(1))
									+ new Integer(itemLst.get(1)) + "");

							double total = new Double(itemLstOld.get(2))
									+ new Double(itemLst.get(2));
							DecimalFormat dec = new DecimalFormat("00.00");
							String totalPrice = dec.format(total);
							itemLst.set(2, totalPrice);

						}

						mapToHoldItems.put(itemId, itemLst);

						// calculate non Vat item order amount
						double vatForItem = menuItem.getVat();

						// service tax calculation for each item
						Double itemDiscPer = new Double(menuItem.getPromotionValue());
						Double itemDisc = (itemDiscPer * totalPriceByItem) / 100;
						Double itemcustDisc = (custDiscPercntage * totalPriceByItem) / 100;
						Double itemserviceChrg = (serviceChrgRate * (totalPriceByItem-(itemDisc+itemcustDisc))) / 100;
						Double itemServiceTax = menuItem.getServiceTax();
						Double serviceTaxForThsItem = (itemServiceTax * (totalPriceByItem-(itemDisc+itemcustDisc)+itemserviceChrg)) / 100;
						if (order.getTable_no().trim().equalsIgnoreCase("0")) {
							if (store.getParcelServiceTax().equalsIgnoreCase("N")) {
								serviceTaxForThsItem = 0.0;
							}
						}
						
						totalServceTaxAmt = totalServceTaxAmt	+ serviceTaxForThsItem;

						if (vatForItem <= 0.0) {
							totalNonVatItemOrderAmt = totalNonVatItemOrderAmt + totalPriceByItem;
						} else if (vatForItem > 0.0) {
							totalVatItemOrderAmt = totalVatItemOrderAmt + totalPriceByItem;

							double vatForThsItem = (vatForItem * (totalPriceByItem-(itemDisc+itemcustDisc)+itemserviceChrg)) / 100;
							
							if (order.getTable_no().trim().equalsIgnoreCase("0")) {
								if (store.getParcelVat().equalsIgnoreCase("N")) {
									vatForThsItem = 0.0;
								}
							}
							
							totalVatAmt = totalVatAmt + vatForThsItem;
						}
						
						totalServceChrgAmt=totalServceChrgAmt+itemserviceChrg;
						totalcustDiscAmt=totalcustDiscAmt+itemcustDisc;

						itemName = String.format("%-35s:%s", itemName, quantity);
						kitchenItemStrng.append(itemName);
						kitchenItemStrng.append("\n");
					}
					// create a list to hold the vat
					List<String> vatAmtLst = new ArrayList<String>();
					if (storeId == 53) {
						category = menuDAO.getCategoryById(catId);
						if (category.getMenuCategoryName().trim().equalsIgnoreCase("beverage")) {
							vatAmtLst.add(0, "Sales Tax" + " @" + "20.1" + "%");
						}
						else {
							if (store.getVatAmt() == 0.0) {
								vatAmtLst.add(0, store.getVatTaxText());
							} else if (store.getVatAmt() > 0.0) {
								vatAmtLst.add(0, store.getVatTaxText() + " @" + store.getVatAmt() + "%");
							}
						}
					} else {
						if (store.getVatAmt() == 0.0) {
							vatAmtLst.add(0, store.getVatTaxText());
						} else if (store.getVatAmt() > 0.0) {
							vatAmtLst.add(0, store.getVatTaxText() + " @"
									+ store.getVatAmt() + "%");
						}
					}
					// double roundOffVat = Math.round(totalVatAmt * 100.0) /
					// 100.0;
					DecimalFormat decim3 = new DecimalFormat("00.00");
					String roundOffVatStr = decim3.format(totalVatAmt);
					vatAmtLst.add(1, roundOffVatStr);

					
					// create a list to hold the service tax
//					double servcTax = 0.0;
					List<String> servcTaxLst = new ArrayList<String>();
					// double roundOffStax = Math.round(totalServceTaxAmt *
					// 100.0) /
					// 100.0;
					DecimalFormat decim4 = new DecimalFormat("00.00");

					String roundOffStaxStr = decim4.format(totalServceTaxAmt);
					if (storeId == 53) {
						if (category.getMenuCategoryName().trim()
								.equalsIgnoreCase("beverage")) {
							servcTaxLst.add(0, store.getServiceTaxText() + " @"
									+ store.getServiceTaxAmt() + "%");
						} else {
							if (store.getServiceTaxAmt() == 0.0) {
								servcTaxLst.add(0, store.getServiceTaxText());
							} else if (store.getServiceTaxAmt() > 0.0) {
								servcTaxLst
										.add(0,
												store.getServiceTaxText()
														+ " @"
														+ store.getServiceTaxAmt()
														+ "%");
							}
						}
					} else {
						if (store.getServiceTaxAmt() == 0.0) {
							servcTaxLst.add(0, store.getServiceTaxText());
						} else if (store.getServiceTaxAmt() > 0.0) {

							servcTaxLst.add(0, store.getServiceTaxText() + " @"
									+ store.getServiceTaxAmt() + "%");

						}
					}
					servcTaxLst.add(1, roundOffStaxStr);
					
					// create a list to hold the service charge
					//double servcCharge = order.getOrderBill().getServiceChargeAmt();
					List<String> servcChrgLst = new ArrayList<String>();
					decim4 = new DecimalFormat("00.00");

					String roundOffServcChargeStr = decim4.format(totalServceChrgAmt);
					if (store.getServiceChargeRate() == 0.0) {
						servcChrgLst.add(0, store.getServiceChargeText());
					} else if (store.getServiceChargeRate() > 0.0) {

						servcChrgLst.add(0, store.getServiceChargeText() + " @"
								+ serviceChrgRate + "%");

					}
					servcChrgLst.add(1, roundOffServcChargeStr);

					// create a list to hold the discount
//					Double totalDiscnt = 0.0;
					Bill bill = billDao.getBillByOrderId(orderid);
//					Double custDiscount = bill.getCustomerDiscount();
					
					Double itemsDiscount = bill.getTotalDiscount();
					

					List<String> itemDiscntLst = new ArrayList<String>();
					String itemDscntStr = decim4.format(itemsDiscount
							.doubleValue());
					itemDiscntLst.add(0, "-Items Disc.");
					itemDiscntLst.add(1, itemDscntStr);

					List<String> custDiscntLst = new ArrayList<String>();
					String custDscntStr = decim4.format(totalcustDiscAmt);
					
					custDiscntLst
							.add(0,
									"-Customer Disc."
											+ "("
											+ Double.toString(custDiscPercntage)
											+ "%)");
					custDiscntLst.add(1, custDscntStr);

					// create a list to hold the paid amount
					List<String> paidAmtLst = new ArrayList<String>();

//					double amount = 0.0;
					double paidAmount = 0.0;
//					double amtToPay = 0.0;
					double tenderAmt = 0.0;
					double refundAmt = 0.0;
					String refundAmtUptoTwoDecimal = "";

					Set<String> pTypes = new TreeSet<String>();
					String paymentType = "";

					try {
						List<Payment> paymentLst = billDao
								.getPaymentInfoByOrderId(orderid);
						Iterator<Payment> iterator1 = paymentLst.iterator();
						while (iterator1.hasNext()) {
							Payment payment = (Payment) iterator1.next();
//							amount = payment.getAmount();
							paidAmount = paidAmount + payment.getPaidAmount();
							tenderAmt = tenderAmt + payment.getTenderAmount();
							if (payment.getPaymentMode() != null)
								pTypes.add(payment.getPaymentMode());

							if (payment.getPaymentMode() != null
									&& payment.getPaymentMode()
											.equalsIgnoreCase("cash")) {
								refundAmt = tenderAmt - paidAmount;
								if (refundAmt < 0) {
									refundAmt = 0.00;
									DecimalFormat df = new DecimalFormat(
											"00.00");
									refundAmtUptoTwoDecimal = df
											.format(refundAmt);
								} else if (refundAmt > 0) {

									DecimalFormat df = new DecimalFormat(
											"00.00");
									refundAmtUptoTwoDecimal = df
											.format(refundAmt);

								}

							}
						}

						if (pTypes.size() == 1) {
							paymentType = pTypes.toArray()[0].toString();
						}
						if (pTypes.size() == 2) {
							paymentType = pTypes.toArray()[0].toString()
									+ " and " + pTypes.toArray()[1].toString();
						}

//						amtToPay = amount - paidAmount;
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					String paidAmountToTwoDecimalPlaces = decim4
							.format(paidAmount);
					paidAmtLst.add(0, "Paid Amount");
					paidAmtLst.add(1, paidAmountToTwoDecimalPlaces);

					// create a list to hold the tender amount
					List<String> tenderAmtLst = new ArrayList<String>();

					String tenderAmtToTwoDecimalPlaces = decim4.format(tenderAmt);
					tenderAmtLst.add(0, "Tender Amount");
					tenderAmtLst.add(1, tenderAmtToTwoDecimalPlaces);

					// create a list to hold payment types
					List<String> paymntTypeLst = new ArrayList<String>();
					paymntTypeLst.add(0, "Payment Type");
					paymntTypeLst.add(1, paymentType);

					// create a list to hold refund amount
					List<String> refundAmtLst = new ArrayList<String>();
					refundAmtLst.add(0, "Refund Amount");
					refundAmtLst.add(1, refundAmtUptoTwoDecimal);

					kitchenEndStrng.append("..........................");
//					kitchenPrnt = kitchenHdrStrng.toString()
//							+ kitchenItemStrng.toString()
//							+ kitchenEndStrng.toString();

					Customer loggedCustomer = order.getCustomers();
					String userId = loggedCustomer.getName();
					if (createdBy != null) {
						if (createdBy.trim().length() > 0)
							userId = createdBy;
					} else {
						createdBy = userId;
					}
					// for home delivery
					String homeDeliveryCustName = "";
					String homeDeliveryCustAddr = "";
					String homeDeliveryCustPh = "";
					String homeDeliveryPersonName = "";
					if (order.getTable_no().equalsIgnoreCase("0")
							&& store.getParcelAddress().equalsIgnoreCase("Y")) {
						homeDeliveryCustName = order.getCustomerName();
						homeDeliveryCustAddr = order.getDeliveryAddress();
						homeDeliveryCustPh = order.getCustomerContact();
						homeDeliveryPersonName = order.getDeliveryPersonName();
					}

					/************************
					 * 
					 */

					StringBuffer invoice = new StringBuffer();
					invoice.append("Order No:  ");
					invoice.append(orderId + "");
					invoice.append("#" + billNo);
					if (storeId == 37 || storeId == 38) {
						invoice.append("     " + "Pax(s):  ");
						int noOfPerson = order.getNoOfPersons();
						invoice.append(noOfPerson + "");
					}
					List<String> totalAmt = new ArrayList<String>();
					DecimalFormat decim2 = new DecimalFormat("00.00");
					String subTotalAmtStr = decim2.format(subTotalAmt);
					if (store.getId() == 37 || store.getId() == 38) {
						totalAmt.add(0, "Sub Total Amt");
					} else {
						totalAmt.add(0, "Total Order Amt");
					}
					totalAmt.add(1, subTotalAmtStr);

					// create a list to hold the gross bill
					List<String> grossLst = new ArrayList<String>();
					double gross = 0.0;
					// service charge added to gross
					gross = (subTotalAmt + totalVatAmt + totalServceTaxAmt + totalServceChrgAmt);
					double amttopay=0.0;
					amttopay=gross-totalcustDiscAmt;
					if (roundOffStatus != null
							&& !roundOffStatus.equalsIgnoreCase("")
							&& roundOffStatus.equalsIgnoreCase("Y")) {
						gross = new Double(Math.round(gross)); // round
						amttopay=new Double(Math.round(amttopay));
					} else if (roundOffDouble != null
							&& !roundOffDouble.equalsIgnoreCase("")
							&& roundOffDouble.equalsIgnoreCase("Y")) {
						String formvl = decim4.format(gross);
						String formv2 = decim4.format(amttopay);
						gross = Double.parseDouble(formvl);
						amttopay= Double.parseDouble(formv2);
						gross = new Double(CommonProerties.roundOffUptoTwoPlacesDouble(gross,1)); // round off double
						amttopay = new Double(CommonProerties.roundOffUptoTwoPlacesDouble(amttopay,1)); // round off double
					}

					String roundOffGrossStr = decim4.format(gross);
					grossLst.add(0, "TOTAL");
					grossLst.add(1, roundOffGrossStr);

					// create a list to hold the amount to pay
					List<String> amtToPayLst = new ArrayList<String>();
					
					//String amtToPayToTwoDecimalPlaces = decim4.format(gross);
					String amtToPayToTwoDecimalPlaces = decim4.format(amttopay);
					amtToPayLst.add(0, "Amount to Pay");
					amtToPayLst.add(1, amtToPayToTwoDecimalPlaces);

					arryToHoldElems[13] = servcTaxLst;
					arryToHoldElems[14] = itemDiscntLst;
					arryToHoldElems[15] = grossLst;
					arryToHoldElems[16] = tableNoStrng.toString();
					arryToHoldElems[17] = paidAmtLst;
					arryToHoldElems[18] = amtToPayLst;
					arryToHoldElems[19] = custDiscntLst;
					arryToHoldElems[20] = userId;
					arryToHoldElems[21] = tenderAmtLst;
					arryToHoldElems[22] = paymntTypeLst;
					arryToHoldElems[23] = refundAmtLst;
					arryToHoldElems[24] = homeDeliveryCustName;
					arryToHoldElems[25] = homeDeliveryCustAddr;
					arryToHoldElems[26] = homeDeliveryCustPh;
					arryToHoldElems[27] = homeDeliveryPersonName;
					arryToHoldElems[6] = invoice.toString();
					arryToHoldElems[8] = mapToHoldItems;
					arryToHoldElems[11] = totalAmt;
					arryToHoldElems[12] = vatAmtLst;
					arryToHoldElems[35] = servcChrgLst;

					try { // to print
							// if (billPrintServerFlag.equalsIgnoreCase("Y")) {

						new BillSplitPosPrinterMain().a(arryToHoldElems,
								storeId);
						updateBillPrint(orderId);

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

			}
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if (em != null) em.close();
		}

		return status;
	}

	@Override
	public void updateBillPrint(int orderId) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> oldOrder = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid and o.cancel='N'", OrderMaster.class);
			oldOrder.setParameter("orderid", orderId);
			OrderMaster existingOrder = oldOrder.getSingleResult();
			int billPrintCount = existingOrder.getBillPrintcount();
			existingOrder.setBillPrintcount(++billPrintCount);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Order data to be updated", e);
		} finally {
      if (em != null) em.close();
		}

	}

	@Override
	public void updateKotPrint(int orderId) throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> oldOrder = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid and o.cancel='N'", OrderMaster.class);
			oldOrder.setParameter("orderid", orderId);
			OrderMaster existingOrder = (OrderMaster) oldOrder
					.getSingleResult();
			int kotPrintCount = existingOrder.getKotPrintCount();
			existingOrder.setKotPrintStatus("Y");
			existingOrder.setKotPrintCount(++kotPrintCount);
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Order data to be updated", e);
		} finally {
      if (em != null) em.close();
		}

	}

	@Override
	public String printKot(Integer orderId, Integer storeId) throws DAOException {

		EntityManager em = null;
		String status = "";
		OrderMaster order = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeId", OrderMaster.class);
			qry.setParameter("orderid", orderId);
			qry.setParameter("storeId", storeId);
			order = qry.getSingleResult();

			status = new KotPrint().printKot((orderId <= 0 ? true : false), order);

		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if (em != null) em.close();
		}

		return status;
	}

	@Override
	public String printUpdateKot(Integer orderId, Integer storeId,
			int currentQuantity, int previousQuantity, String itemName)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		OrderMaster order = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeId", OrderMaster.class);
			qry.setParameter("orderid", orderId);
			qry.setParameter("storeId", storeId);
			order = (OrderMaster) qry.getSingleResult();

			status = new KotPrint().printUpdateKot((orderId <= 0 ? true : false), order, currentQuantity, previousQuantity, itemName);

		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if (em != null) em.close();
		}

		return status;
	}

	@Override
	public String updateBillPrintCount(CommonBean bean) throws DAOException {

		EntityManager em = null;
		int orderId = bean.getOrderId();
		int storeId = bean.getStoreId();
		String status = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> oldOrder = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid and o.storeId=:storeId and o.cancel='N'", OrderMaster.class);
			oldOrder.setParameter("orderid", orderId);
			oldOrder.setParameter("storeId", storeId);
			OrderMaster existingOrder = oldOrder.getSingleResult();
			int billPrintCount = existingOrder.getBillPrintcount();
			existingOrder.setBillPrintcount(++billPrintCount);
			existingOrder.setBillPrintReason(bean.getBillPrintReason());
			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Error occurred", e);
		} finally {
      if (em != null) em.close();
		}

		return status;
	}

	@Override
	public String getBillPrintCount(Integer orderId, Integer storeId)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		int billPrintCount = 0;

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> oldOrder = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid and o.storeId=:storeId and o.cancel='N'", OrderMaster.class);
			oldOrder.setParameter("orderid", orderId);
			oldOrder.setParameter("storeId", storeId);
			OrderMaster existingOrder = oldOrder.getSingleResult();
			billPrintCount = existingOrder.getBillPrintcount();
			status = "" + billPrintCount;

		} catch (Exception e) {
			e.printStackTrace();
			status = "" + 0;
			throw new DAOException("Error occurred", e);
		} finally {
      if (em != null) em.close();
		}

		return status;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public List<PrintKotMaster> getAllKotPrinter(int storeId)
			throws DAOException { // Rahul

		// List<PrintKotMaster> printkotMasterList = null;
		List<PrintKotMaster> printkotMasterList = new ArrayList<PrintKotMaster>();

		// List<PrintKotMaster> printkotMaster = new
		// ArrayList<PrintKotMaster>();
		int cuisineTypeId = 0;
		EntityManager em = null;
		try {
			System.out.println("in getAllKotPrinter: " + storeId);

			System.out.println("cuisineTypeId:" + cuisineTypeId);

			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<PrintKotMaster> qry = em
					.createQuery("SELECT k FROM PrintKotMaster k WHERE k.storeId=:storeid and k.deleteFlag='N'", PrintKotMaster.class);
			qry.setParameter("storeid", storeId);
			// qry.setParameter("cuisineTypeId", cuisineTypeId);
			printkotMasterList = qry.getResultList();

			em.getTransaction().commit();
			// em.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getAllKotPrinter", e);
		} finally {
      if (em != null) em.close();
		}

		return printkotMasterList;
	}

	public List<PrintKotItemMaster> getAllKotItemPrinter(int storeId)
			throws DAOException {

		List<PrintKotItemMaster> printkotMasterList = new ArrayList<PrintKotItemMaster>();

		// List<PrintKotMaster> printkotMaster = new
		// ArrayList<PrintKotMaster>();
		EntityManager em = null;
		try {

			
			em = entityManagerFactory.createEntityManager();

			em.getTransaction().begin();

			TypedQuery<PrintKotItemMaster> qry = em
					.createQuery("SELECT k FROM PrintKotItemMaster k WHERE k.storeId=:storeid and k.deleteFlag='N'", PrintKotItemMaster.class);
			qry.setParameter("storeid", storeId);
			// qry.setParameter("cuisineTypeId", cuisineTypeId);
			printkotMasterList = qry.getResultList();
			
			em.getTransaction().commit();

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException of getAllKotItemPrinter", e);
		} finally {
      if (em != null) em.close();
		}

		return printkotMasterList;
	}

	/*@Override
	public int addDailyExpenditure(DailyExpenditure dailyExpenditure)
			throws DAOException {
		EntityManager em = null;
		int dailyExpId;
		try {

			dailyExpId = 0;
			// Date currDate = new Date();
			// dailyExpenditure.setVoucherDate(currDate);

			// SimpleDateFormat

			// persist the daily expenditure
			dailyExpenditure.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(dailyExpenditure);

			em.getTransaction().commit();
			dailyExpId = dailyExpenditure.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return dailyExpId;
	}*/

	/**
	 * @author Anup Mallick
	 * @param order
	 * @return storeCustomerId
	 * @throws DAOException
	 */
	public int updateOrderByStoreCustomerId(OrderMaster order)
			throws DAOException {

		StoreCustomer storeCustomer = new StoreCustomer();
		EntityManager em = null;
		int storeCustomerId;
		String homeDeliveryCustName = "";
		String homeDeliveryCustAddr = "";
		String homeDeliveryCustPh = "";
		String homeDeliveryLocation = "";
		String homeDeliveryStreet = "";
		String homeDeliveryHouseNo = "";
		String custVatRegNo = "";
		String homeDeliveryState = "";
		String waiterName = "";
		/** New Fields */
		String carNo = "";
		Date anniversary_date = new Date();
		Date dob = new Date();
		try {
//			int orderId = Integer.parseInt(order.getOrderId());
			int storeId = order.getStoreId();
			System.out.println("Print startted in DAO...");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			// check if bill print is true for the store
			// int storeId = order.getStoreId();
			TypedQuery<StoreMaster> qry1 = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid", StoreMaster.class);
			qry1.setParameter("storeid", storeId);
			StoreMaster store = qry1.getSingleResult();

			/*if (order.getTable_no().equalsIgnoreCase("0")
					&& store.getParcelAddress().equalsIgnoreCase("Y")) {*///for all type order cust save
			if (store.getParcelAddress().equalsIgnoreCase("Y")) {
				homeDeliveryCustName = order.getCustomerName();
				homeDeliveryCustAddr = order.getDeliveryAddress();
				homeDeliveryCustPh = order.getCustomerContact();
//				homeDeliveryPersonName = order.getDeliveryPersonName();
				homeDeliveryLocation = order.getLocation();
				homeDeliveryStreet = order.getStreet();
				homeDeliveryHouseNo = order.getHouseNo();
				homeDeliveryState = order.getState();
				custVatRegNo = order.getCustVatRegNo();
				carNo = order.getCar_no();
				anniversary_date = order.getAnniversary();
				dob = order.getDob();
				waiterName = order.getWaiterName();
			}

			/** Store data in StoreCustomer */
			storeCustomer.setName(homeDeliveryCustName.toString().trim());
			storeCustomer.setAddress(homeDeliveryCustAddr);
			storeCustomer.setContactNo(homeDeliveryCustPh);
			storeCustomer.setLocation(homeDeliveryLocation);
			storeCustomer.setStreet(homeDeliveryStreet);
			storeCustomer.setHouse_no(homeDeliveryHouseNo);
			storeCustomer.setCust_vat_reg_no(custVatRegNo);
			storeCustomer.setAnniversary(anniversary_date);
			storeCustomer.setCar_no(carNo);
			storeCustomer.setDob(dob);
			storeCustomer.setStoreId(storeId);
			storeCustomer.setCreditCustomer("N");
			storeCustomer.setDeleteFlag("N");
			storeCustomer.setState(order.getState());
			storeCustomer.setState(homeDeliveryState);
			storeCustomer.setWaiterName(waiterName);
			em.persist(storeCustomer);
			em.getTransaction().commit();
			storeCustomerId = storeCustomer.getId();
			LOGGER.info("Customer Id stored successfully : " + storeCustomerId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check storeCustomerId data to be inserted",
					e);
		} finally {
      if (em != null) em.close();
		}
		return storeCustomerId;
	}

	/**
	 * @Desc editOrderByStoreCustomerId
	 * @param order
	 * @return int StoreCustomerId
	 * @throws DAOException
	 */
	public int editOrderByStoreCustomerId(OrderMaster order)
			throws DAOException {

		EntityManager em = null;
		EntityManager em1 = null;
		int storeCustomerId;
		String homeDeliveryCustName = "";
		String homeDeliveryCustAddr = "";
		String homeDeliveryCustPh = "";
		String homeDeliveryLocation = "";
		String homeDeliveryStreet = "";
		String homeDeliveryHouseNo = "";
		String custVatRegNo = "";
		String homeDeliveryState = "";
		String waiterName = "";
		/** New Fields */
		String carNo = "";
		Date anniversary_date = new Date();
		Date dob = new Date();
		try {
			int storeCusId = 0;
			if("Y".equals(order.getIsSync()))
					{
				storeCusId=order.getStorecustmerServerId();
					}
			else
			{
				storeCusId=order.getStoreCustomerId();
			}
			
//			int orderId = Integer.parseInt(order.getOrderId());
			int storeId = order.getStoreId();
			System.out.println("Print startted in DAO...");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em1 = entityManagerFactory.createEntityManager();
			em1.getTransaction().begin();

			TypedQuery<StoreMaster> qry = em1
					.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid", StoreMaster.class);
			qry.setParameter("storeid", storeId);
			StoreMaster store1 = qry.getSingleResult();

			TypedQuery<StoreCustomer> qry1 = em
					.createQuery("SELECT s FROM StoreCustomer s WHERE  s.id=:storeCusId", StoreCustomer.class);
			qry1.setParameter("storeCusId", storeCusId);
			StoreCustomer store = (StoreCustomer) qry1.getSingleResult();

			/*if (order.getTable_no().equalsIgnoreCase("0")
					&& store1.getParcelAddress().equalsIgnoreCase("Y")) {*/// for all type order cust save
			if (store1.getParcelAddress().equalsIgnoreCase("Y")) {
				homeDeliveryCustName = order.getCustomerName();
				homeDeliveryCustAddr = order.getDeliveryAddress();
				homeDeliveryCustPh = order.getCustomerContact();
//				homeDeliveryPersonName = order.getDeliveryPersonName();
				homeDeliveryLocation = order.getLocation();
				homeDeliveryStreet = order.getStreet();
				homeDeliveryHouseNo = order.getHouseNo();
				homeDeliveryState = order.getState();
				custVatRegNo = order.getCustVatRegNo();
				carNo = order.getCar_no();
				try {
					anniversary_date = order.getAnniversary();
				} catch (NullPointerException e) {
					anniversary_date = null;
				}
				try {
					dob = order.getDob();
				} catch (NullPointerException e) {
					dob = null;
				}
				waiterName = order.getWaiterName();
			}

			/** Store data in StoreCustomer */
			store.setName(homeDeliveryCustName.toString().trim());
			store.setAddress(homeDeliveryCustAddr.toString());
			store.setContactNo(homeDeliveryCustPh);
			store.setLocation(homeDeliveryLocation.toString());
			store.setStreet(homeDeliveryStreet);
			store.setHouse_no(homeDeliveryHouseNo);
			store.setCust_vat_reg_no(custVatRegNo);
			store.setAnniversary(anniversary_date);
			store.setCar_no(carNo);
			store.setDob(dob);
			store.setStoreId(storeId);
			store.setCreditCustomer("N");
			store.setDeleteFlag("N");
			store.setState(order.getState());
			store.setState(homeDeliveryState);
			store.setWaiterName(waiterName);
			em.merge(store);
			em.getTransaction().commit();
			em1.getTransaction().commit();
			storeCustomerId = store.getId();
			LOGGER.info("Customer Id stored successfully : " + storeCustomerId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check storeCustomerId data to be inserted", e);
		} finally {
      if (em != null) em.close();
		}
		return storeCustomerId;
	}

	/**
	 * @Auther Anup
	 * @param storeId
	 * @param date
	 * @param date2
	 * @return
	 * @throws DAOException
	 */
	@Override
	public List<OrderMaster> getAllUnpaidOrdersByDateRange(Integer storeId,
			String date, String date2) throws DAOException {

		List<OrderMaster> orderList = null;
		List<Integer> orderIdListTemp = new ArrayList<Integer>();
		List<Payment> paymentList = null;
		
		EntityManager em = null;
		BillingDAO billingDAO = new BillingDAOImpl();

		int storeid = (storeId);

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get all unpaid orders for this customer
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currDate = dateFormat.parse(date);
			Date toDate = dateFormat.parse(date2);
			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, -1);
			Date previousDate = cal.getTime();

			List<Date> dateList = new ArrayList<Date>();

			dateList.add(currDate);
			dateList.add(previousDate);

			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no!=NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate >=:currDate and o.orderDate <=:toDate)",
					    OrderMaster.class);
			qry.setParameter("storeId", storeid);
			qry.setParameter("currDate", currDate);
			qry.setParameter("toDate", toDate);

			/*
			 * String queryStr=
			 * "SELECT * FROM fo_t_orders o WHERE o.store_id=?  and o.Flag='N' and o.table_no IS NOT NULL and o.cancel='N' and o.credit_flag='N' and o.order_date >=? and o.order_date <=?"
			 * ; Query qry = em.createNativeQuery(queryStr); qry.setParameter(1,
			 * storeId); qry.setParameter(2, date); qry.setParameter(3, date2);
			 */
			orderList = (List<OrderMaster>) qry.getResultList();

			if (orderList != null && orderList.size() > 0) {
				List<Integer> orderIdList = new ArrayList<Integer>();
				Iterator<OrderMaster> iterator = orderList.iterator();
				while (iterator.hasNext()) {
					OrderMaster orderMaster = (OrderMaster) iterator.next();
					orderIdList.add(orderMaster.getId());

				}
				// new method
				paymentList = billingDAO.getPaymentInfoByOrderList(orderIdList);
				Iterator<Integer> iterator2 = orderIdList.iterator();
				while (iterator2.hasNext()) {
					List<Payment> paymentListTemp = new ArrayList<Payment>();
					Integer orderId = (Integer) iterator2.next();
					Iterator<Payment> paymentItr = paymentList.iterator();
					while (paymentItr.hasNext()) {
						Payment payment = (Payment) paymentItr.next();
						int orderIdPayment = payment.getOrderPayment().getId();
						if (orderId == orderIdPayment) {
							paymentListTemp.add(payment);
						}

					}

					if (paymentListTemp.size() > 1) {
						Iterator<Payment> paymentIterator = paymentListTemp
								.iterator();
						while (paymentIterator.hasNext()) {
							Payment payment = (Payment) paymentIterator.next();
							double amtToPay = payment.getAmountToPay();
							if (amtToPay == 0.00) {
								orderIdListTemp.add(orderId);
							}
						}
					}
				}
				// Modify order list, by removing orders for which payment has already started
				for (int i = 0; i < orderIdListTemp.size(); i++) {
					int id = orderIdListTemp.get(i);

					for (int j = 0; j < orderList.size(); j++) {
						OrderMaster orderMaster = orderList.get(j);
						int orderid = orderMaster.getId();

						if (id == orderid) {
							orderList.remove(j);
						}
					}
				}
			}

			em.getTransaction().commit();
			// System.out.println("getAllUnpaidOrdersByUserId 666");
			// em.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
      if (em != null) em.close();
		}
		
		return orderList;
	}
	
	@Override
	public List<OrderMaster> getAllAdvanceOrders(Integer storeId) throws DAOException {

		List<OrderMaster> orderList = null;
		// List<Payment> paymentListTemp = new ArrayList<Payment>();
		EntityManager em = null;

		int storeid = (storeId);
		BillingDAO billingDAO = new BillingDAOImpl();
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get all unpaid orders for this customer
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String formatted = dateFormat.format(new Date());
			Date currDate = dateFormat.parse(formatted);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, +30);
			Date toDate = cal.getTime();

			//Query qry = em.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no!=NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate >=:currDate)");
			/*TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no!=NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate between :currDate and :toDate and o.orderBill.billAmount=0 and o.orderBill.isNonchargeable is null",
					    OrderMaster.class);*/
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no!=NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate >:currDate",
					    OrderMaster.class);
			qry.setParameter("storeId", storeid);
			qry.setParameter("currDate", currDate);
			//qry.setParameter("toDate", toDate);
			

			/*
			 * String queryStr=
			 * "SELECT * FROM fo_t_orders o WHERE o.store_id=?  and o.Flag='N' and o.table_no IS NOT NULL and o.cancel='N' and o.credit_flag='N' and o.order_date >=? and o.order_date <=?"
			 * ; Query qry = em.createNativeQuery(queryStr); qry.setParameter(1,
			 * storeId); qry.setParameter(2, date); qry.setParameter(3, date2);
			 */
			orderList = qry.getResultList();
			
			if (orderList != null && orderList.size() > 0) {
				Iterator<OrderMaster> iterator = orderList.iterator();
				while (iterator.hasNext()) {
					OrderMaster orderMaster = (OrderMaster) iterator.next();
					List<Payment> payList = billingDAO.getPaymentInfoByOrderId("" + orderMaster.getId());
					orderMaster.setPayments(payList);
				}
				}

			em.getTransaction().commit();
		System.out.println("list:"+orderList);
		System.out.println("list11");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
      if (em != null) em.close();
		}
		return orderList;
	}
	
	@Override
	public int getNoOfAdvanceOrders(Integer storeId) throws DAOException {

		List<OrderMaster> orderList = null;
		EntityManager em = null;
		int storeid = (storeId);
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// get all unpaid orders for this customer
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String formatted = dateFormat.format(new Date());
			Date currDate = dateFormat.parse(formatted);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, +30);
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.storeId=:storeId  and o.flag='N' and o.table_no!=NULL and o.cancel='N' and o.creditFlag='N' and o.orderDate >:currDate",
					    OrderMaster.class);
			qry.setParameter("storeId", storeid);
			qry.setParameter("currDate", currDate);
			
			orderList = qry.getResultList();
			
			em.getTransaction().commit();
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		} finally {
      if (em != null) em.close();
		}
		return orderList.size();
	}

	@Override
	public String assignDeliveryBoy(OrderDeliveryBoy o) throws DAOException {
		String status="failure";
		EntityManager em = null;
		OrderMaster om=null;
		try {
			int orderId=o.getOrderId();
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:id", OrderMaster.class);
			qry.setParameter("id", orderId);
			om = qry.getSingleResult();
			om.setDeliveryPersonName(o.getDeliveryPersonName());
			em.persist(om);
			em.getTransaction().commit();
			status="success";
			System.out.println("assign delv boy Succesfull");
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be shown", e);
		}
		finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public int syncOrder(OrderMaster order, HttpServletRequest request)
			throws DAOException {

		int orderId = 0;
		Double totalBillAmt = 0.0;
		// Double netBillAmt = 0.0;
		Double totalDiscount = 0.0;
		double netPriceEachItem = 0.0;
		double serviceCharge = 0.0;
		double serviceChargeRate = 0.0;
		int storeCustomerId = 0;
		double itemVat=0.0;
		double itemServiceTax=0.0;
		EntityManager em = null;
		Double totalserviceTax = 0.0;
		Double totalVat = 0.0;
		Double totalItemPriceWithoutTax = 0.0;
		StoreMaster storeMaster = new StoreMaster();
		order.setIsSync("Y");
		StoreAddressDAO dao = new StoreAddressDAOImpl();
		StoreCustomerDaoImpl storeCustomerDaoImpl = new StoreCustomerDaoImpl();
		BillingDAO billDao = new BillingDAOImpl();
		
		int storeId = order.getStoreId();
		int orderTypeid = order.getOrdertype().getId();

		try {
	    OrderType orderType = storeCustomerDaoImpl.getOrderType(orderTypeid, storeId);
			// get current date and time, changed for celavi
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String strDate = sdf.format(cal.getTime());
			// System.out.println("Current date in String Format: " + strDate);
			// set date and time
			order.setOrderTime(strDate);
			String orderTime = order.getOrderTime();
			storeMaster = dao.getStoreByStoreId(order.getStoreId());
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			if (order.getId() == -1) {// new
				order.setFlag("N");
				order.setBillReqStatus("No");
				order.setSplitBill("N");
				order.setKotPrintStatus("N");
				order.setKotPrintCount(0);
				order.setBillPrintcount(0);
				order.setCreditFlag("N");
				order.setId(0);
				try {
					if (order.getCustomerContact().equals("")
							&& order.getCustomerName().equals("")) {
						storeCustomerId = 0;
					}else if (order.getStorecustmerServerId() > 0) {
						// storeCustomerId = order.getStoreCustomerId();
						storeCustomerId = editOrderByStoreCustomerId(order);
					} else {
						storeCustomerId = updateOrderByStoreCustomerId(order);
					}
					LOGGER.info("Successful");
				} catch (Exception e) {
					e.printStackTrace();
					storeCustomerId = order.getStorecustmerServerId();
				}
				order.setStoreCustomerId(storeCustomerId);
				em.persist(order);// order id
			}

      order.setOrdertype(orderType);

      List<OrderItem> orderItemList = order.getOrderitem();
          if(orderItemList.size()>0)
          {
			List<Integer> menuItemIDs = new ArrayList<>();
			for(OrderItem orderItem : orderItemList) {
			  menuItemIDs.add(orderItem.getItem().getId());
			}
			
			List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
			
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
			
			Iterator<OrderItem> iterator = orderItemList.iterator();
			while (iterator.hasNext()) {
				OrderItem orderItem = (OrderItem) iterator.next();

				OrderMaster master = new OrderMaster();
				master.setId(order.getId());
				orderItem.setOrders(master);
				orderItem.setOrderTime(orderTime);
				// orderItem.setSpecialNote(order.getSpecialNote());
				orderItem.setOrdertype(order.getOrdertype().getId());
				orderItem.setKitchenOut("N");

				// calculate total price for each item
				MenuItem menuItem = menuItemMap.get(orderItem.getItem().getId());

				Double itemPrice = Integer.parseInt((orderItem.getQuantityOfItem())) * (menuItem.getPrice());

				// service tax calculation for each item
				itemServiceTax = menuItem.getServiceTax();
				Double itemDisc = 0.0;
				Double serviceTaxForThsItem = 0.0;
				Double itemDiscPer = 0.0;

				if (menuItem.getPromotionFlag().equalsIgnoreCase("Y")) {
					itemDiscPer = new Double(menuItem.getPromotionValue());

				}
				itemDisc = (itemDiscPer * itemPrice) / 100;
				serviceTaxForThsItem = (itemServiceTax * (itemPrice - itemDisc)) / 100;
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {
					if (storeMaster.getParcelServiceTax().equalsIgnoreCase("N")) {
						serviceTaxForThsItem = 0.0;
					}
				}
				totalserviceTax = totalserviceTax + serviceTaxForThsItem;
				// vat calculation for each item
				itemVat = menuItem.getVat();
				Double vatForThsItem = (itemVat * (itemPrice - itemDisc)) / 100;
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {
					if (storeMaster.getParcelVat().equalsIgnoreCase("N")) {
						vatForThsItem = 0.0;
					}
				}
				totalVat = totalVat + vatForThsItem;
				// calculate total item price without tax
				totalItemPriceWithoutTax = totalItemPriceWithoutTax + itemPrice;
				// set item price
				orderItem.setRate(menuItem.getPrice());
				// set item service tax and vat for parcel (for marufaz)
				if (order.getTable_no().trim().equalsIgnoreCase("0")) {
					if (storeMaster.getParcelServiceTax().equalsIgnoreCase("N"))
						orderItem.setServiceTax(0.0);
					else if (storeMaster.getParcelServiceTax()
							.equalsIgnoreCase("Y")) {
						orderItem.setServiceTax(menuItem.getServiceTax());
					}
					if (storeMaster.getParcelVat().equalsIgnoreCase("N"))
						orderItem.setVat(0.0);
					else if (storeMaster.getParcelVat().equalsIgnoreCase("Y")) {
						orderItem.setVat(menuItem.getVat());
					}
				}
				else {
					orderItem.setServiceTax(menuItem.getServiceTax());
					orderItem.setVat(menuItem.getVat());
				}
				orderItem.setTotalPriceByItem(itemPrice);
				double discount = 0.0;
				// check for any promotion
				if (menuItem.getPromotionFlag().equalsIgnoreCase("y")) {

					orderItem.setPromotionValue(menuItem.getPromotionValue());
					discount = ((itemPrice) * (menuItem.getPromotionValue())) / 100;
					//System.out.println("discount:: " + discount);
					orderItem.setPromotionDiscountAmt(discount);
					totalDiscount = totalDiscount + discount;
				}
				//System.out.println("total discount:: " + totalDiscount);

				//
				netPriceEachItem = itemPrice + serviceTaxForThsItem
						+ vatForThsItem - discount;
				orderItem.setNetPrice(netPriceEachItem);
				orderItem.setStoreId(order.getStoreId());
				//

				em.persist(orderItem);

			}
          }
			// total item price to 2 decimal places
			DecimalFormat df = new DecimalFormat("00.00");
			String totalItemPriceWithoutTaxUptoTwoDecimal = df.format(totalItemPriceWithoutTax);
			// calculate total vat upto 2 decimal places
			String totalVatUptoTwoDecimal = df.format(totalVat);
			// calculate total service tax upto 2 decimal places
			String totalServiceTaxUptoTwoDecimal = df.format(totalserviceTax);
			totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal)
					+ Double.parseDouble(totalVatUptoTwoDecimal)
					+ Double.parseDouble(totalServiceTaxUptoTwoDecimal);

			String totalServiceTaxUptoTwoDecimalWithServceChrg = null;
			String totalVatUptoTwoDecimalWithServceChrg = null;
			
			/**
			 * Start :New Logic for Service Charge
			 */
			totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
			totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
			
			/**
			 * End: New Logic for Service Charge
			 */
		
			Bill bill = new Bill();
			// new bill
				if ("Y".equalsIgnoreCase(storeMaster.getServiceChargeFlag())) {
					serviceChargeRate = orderType.getServiceChargeValue();
					serviceCharge = (totalItemPriceWithoutTax * serviceChargeRate) / 100;
					if (order.getTable_no().trim().equalsIgnoreCase("0")) {

						if (order.getOrdertype().getId() == 2) {
							serviceChargeRate = 0.0;
							serviceCharge = 0.0;
						}
					}
					totalserviceTax = totalserviceTax + ((totalserviceTax * serviceChargeRate) / 100);
					totalServiceTaxUptoTwoDecimalWithServceChrg = df.format(totalserviceTax);
					totalVat = totalVat + ((totalVat * serviceChargeRate) / 100);
					totalVatUptoTwoDecimalWithServceChrg = df.format(totalVat);
					totalBillAmt = Double.parseDouble(totalItemPriceWithoutTaxUptoTwoDecimal) + serviceCharge
							+ Double.parseDouble(totalVatUptoTwoDecimalWithServceChrg)
							+ Double.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg);
				}
				
				totalBillAmt = totalBillAmt - totalDiscount;

				Double grossBillAmt = totalBillAmt;
				System.out.println("total gross amt:  " + grossBillAmt);

				bill.setOrderbill(order);
				bill.setSubTotalAmt(totalItemPriceWithoutTax);
				bill.setBillAmount(totalBillAmt);
				if (storeMaster.getServiceChargeText() != null && storeMaster.getServiceChargeText().length() > 0) {
					//if (storeMaster.getServiceChargeText().length() > 0) {
						//if (storeMaster.getServiceChargeRate() > 0) {  ///modified on 29.05.2018
						if(orderType.getServiceChargeValue()>0){
							bill.setServiceTaxAmt(Double
									.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
							bill.setVatAmt(Double
									.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
							bill.setServiceChargeRate(serviceChargeRate);
							bill.setServiceChargeAmt(serviceCharge);
						} else {
							try {
								bill.setServiceTaxAmt(Double
										.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
							} catch (Exception e2) {
								bill.setServiceTaxAmt(0.0);
							}

							try {
								bill.setVatAmt(Double
										.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
							} catch (Exception e2) {
								bill.setVatAmt(0.0);
							}
							bill.setServiceChargeRate(0.0);
							bill.setServiceChargeAmt(0.0); // no service charge

						}
					//}
				} else {
					
					bill.setServiceTaxAmt(Double
							.parseDouble(totalServiceTaxUptoTwoDecimalWithServceChrg));
					bill.setVatAmt(Double
							.parseDouble(totalVatUptoTwoDecimalWithServceChrg));
					bill.setServiceChargeRate(0.0);
					bill.setServiceChargeAmt(0.0); // no service charge

				}
				bill.setTotalDiscount(totalDiscount);
				bill.setDiscountPercentage(0.00);
				bill.setDiscountReason("");
				// bill.setGrossAmt(grossBillAmt);

				// create new payment
				Payment payment = new Payment();
				payment.setOrderPayment(order);
				String roundOffStatus = storeMaster.getRoundOffTotalAmtStatus();
				String roundOffDouble = storeMaster.getDoubleRoundOff();
				if (roundOffStatus != null
						&& !roundOffStatus.equalsIgnoreCase("")
						&& roundOffStatus.equalsIgnoreCase("Y")) {
					grossBillAmt = new Double(Math.round(grossBillAmt)); // round
				}

				else if (roundOffDouble != null
						&& !roundOffDouble.equalsIgnoreCase("")
						&& roundOffDouble.equalsIgnoreCase("Y")) {
					String formvl = df.format(grossBillAmt);
					grossBillAmt = Double.parseDouble(formvl);
					grossBillAmt = new Double(
							CommonProerties.roundOffUptoTwoPlacesDouble(
									grossBillAmt, 1)); // round off double
				}

				bill.setGrossAmt(grossBillAmt);
				bill.setCustomerDiscount(0.0);
				bill.setDiscountPercentage(0.0);
				double roundOffAmt = grossBillAmt - totalBillAmt;
				bill.setRoundOffAmt(roundOffAmt);
				payment.setAmount(grossBillAmt);
				payment.setAmountToPay(grossBillAmt);

				payment.setStoreId(order.getStoreId());
				em.persist(bill);
				em.persist(payment);
				em.getTransaction().commit();
				orderId = order.getId();
				
				if(order.getOrderBill().getDiscountPercentage()>0)
				{
					Discount discount=new Discount();
					discount.setOrderId(orderId);
					discount.setDiscountPercentage(order.getOrderBill().getDiscountPercentage());
					discount.setDiscountReason(order.getOrderBill().getDiscountReason());
					discount.setIsNonchargeable(order.getOrderBill().getIsNonchargeable());
					discount.setStoreId(order.getStoreId());
					billDao.addDiscount(discount);
				}
				if(order.getPayments().size()>1)
				{
					List<Payment> paylist=order.getPayments();
					for(int i=1;i<paylist.size();i++)
					{
						billDao.paymentforSync(paylist.get(i),orderId);
					}
				}
				
				System.out.println("created sync order id:: " + orderId);
		
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		}
		finally {
			if(em != null) em.close();
		}
		return orderId;
	}
	
	@Override
	public String setOrderRemark(OrderMaster orderMaster)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeid",
					    OrderMaster.class);
			qry.setParameter("orderid", orderMaster.getId());
			qry.setParameter("storeid", orderMaster.getStoreId());
			OrderMaster order = (OrderMaster) qry.getSingleResult();
			order.setRemarks(orderMaster.getRemarks());

			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Error occurred trying to set remarks to order", e);
		} finally {
      if(em != null) em.close();
		}
		
		return status;
	}
	
	@Override
	public String setPackagingNote(OrderMaster orderMaster)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<OrderMaster> qry = em
					.createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeid",
					    OrderMaster.class);
			qry.setParameter("orderid", orderMaster.getId());
			qry.setParameter("storeid", orderMaster.getStoreId());
			OrderMaster order = (OrderMaster) qry.getSingleResult();
			order.setSpecialNote(orderMaster.getSpecialNote());

			em.getTransaction().commit();
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Error occurred trying to set special note to order", e);
		} finally {
      if(em != null) em.close();
		}
		
		return status;
	}

}

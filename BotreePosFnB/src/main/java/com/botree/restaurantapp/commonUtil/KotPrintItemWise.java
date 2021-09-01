package com.botree.restaurantapp.commonUtil;

import static com.botree.restaurantapp.commonUtil.CommonProerties.SARAVANA1;
import static com.botree.restaurantapp.commonUtil.CommonProerties.SARAVANA2;
import static com.botree.restaurantapp.commonUtil.CommonProerties.YUMMY;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.botree.restaurantapp.dao.CustomerDAO;
import com.botree.restaurantapp.dao.CustomerDAOImpl;
import com.botree.restaurantapp.dao.MenuDAO;
import com.botree.restaurantapp.dao.MenuDAOImpl;
import com.botree.restaurantapp.dao.OrdersDAOImpl;
import com.botree.restaurantapp.dao.StoreAddressDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.print.PrintKotItemMaster;

public class KotPrintItemWise {

	public String printKot(boolean isNewOrder, OrderMaster order) throws DAOException {
		String status = "";
		int storeId = order.getStoreId();
		EntityManager em = null;
		try {
			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
			MenuDAO menuDAO = new MenuDAOImpl();
			List<PrintKotItemMaster> printers = null;

			try {
				printers = ordersDAOImpl.getAllKotItemPrinter(storeId);
			} catch (DAOException e) {
				e.printStackTrace();
			}

			List<OrderItem> orderItemLst = order.getOrderitem();
			
      List<Integer> menuItemIDs = new ArrayList<>();
      for(OrderItem orderItem : orderItemLst) {
        menuItemIDs.add(orderItem.getItem().getId());
      }
      
      List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
      
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
			
			if (printers.size() > 0) {
				for (int p = 0; p < printers.size(); p++) { // printer in db
					PrintKotItemMaster printer = printers.get(p);
					String printerIp = printer.getPrinterIp();
					// int printerId=printer.getId();
					System.out.println("printer name:: " + printerIp);
					List<OrderItem> orderItemLstCuisine = new ArrayList<OrderItem>();
					String printerName = "";
					System.out.println("Cuisine: " + printer.getCuisineTypeId());
					//order=ordersDAOImpl.getOrderById(order.getId());
					//orderItemLst = order.getOrderitem();
					boolean itemFound = false;
					for (int i = 0; i < orderItemLst.size(); i++) { // item
						OrderItem orderItem = orderItemLst.get(i);
						int itemId = orderItem.getItem().getId();
						
						MenuItem item = menuItemMap.get(itemId); //menuDao.getItemDetailsById(itemId, storeId);
            
            if (item.getIsKotPrint().equalsIgnoreCase("Y")) {
            	String printerList = item.getPrinterList();
            	if (printerList!=null){
            		if (printerList.length()>0 || !printerList.isEmpty()) {
            			String[] printerLst = printerList.split("~");
            			for (int j = 0; j < printerLst.length; j++) {
            				String printrId = printerLst[j];
            				int printerId = Integer.parseInt(printrId);
            				if (printerId > 0) {
            					if (printerId == printer.getId()) {
            						printerName = printer.getName();
            						itemFound = true;
            						orderItemLstCuisine.add(orderItem);
            					//OrderItem removedItem=orderItemLst.remove(i);
            					//removedItemLst.add(removedItem);// printed
            					//i--;
                			}
                		}
                	}
                }
              }
            }

					}

					if (itemFound == false)
						printerName = "";

					if (orderItemLstCuisine.size() > 0) {// priner wise
						print(isNewOrder, order, orderItemLstCuisine, storeId, printerName,
								printerIp);
					}

				}
			}

			/*if (orderItemLst.size() > 0) { // default printer
				print(order, orderItemLst, storeId, "", "");
			}*/

			status = "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(em!=null)em.close();
		}
		return status;

	}

	public String print(boolean isNewOrder, OrderMaster order, List<OrderItem> orderItemLst,
			int storeId, String cuisine, String printerName) { // Rajarshi
		String status = "";

		EntityManager em = null;
		

		StoreMaster store = new StoreMaster();

		StoreAddressDAOImpl impl = new StoreAddressDAOImpl();
		try {
			store = impl.getStoreByStoreId(storeId);
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		StringBuffer kitchenHdrStrng = new StringBuffer();
		StringBuffer kitchenItemStrng = new StringBuffer();
		StringBuffer kitchenEndStrng = new StringBuffer();

		String strName = store.getStoreName();
		String kot = " - KOT -  ";
		
		if(storeId == SARAVANA1 || storeId == SARAVANA2){
			kot = "KOT-" + printerName.split(" ")[0];
		}
		String cuisineName = "(" + cuisine + ")";
		
		kitchenHdrStrng = new StringBuffer();
		kitchenItemStrng = new StringBuffer();
		kitchenEndStrng = new StringBuffer();
		kitchenHdrStrng.append(kot);
		kitchenHdrStrng.append("\n");
		kitchenHdrStrng.append(strName); // 1st string
		kitchenHdrStrng.append("\n");
		// get order time
		String ordeTime = order.getOrderTime();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm:ss");
			Date date = sdf.parse(ordeTime);
			sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
			ordeTime = sdf.format(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*String[] tempURL = ordeTime.split("/");
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
		System.out.println("dttime:: " + dtTime);*/
		StringBuffer secndString = new StringBuffer();
		secndString.append(ordeTime); // 2nd string start
		secndString.append(" ");
		// get order taken by person info

		/*try {
			Customer customer = order.getCustomers();
			int custId = customer.getId();
			secndString.append("(");
			if (custId > 0) {
				secndString.append("" + custId);
			}
		} catch (Exception ex) {

		}*/
		try {
			CustomerDAO customerDAO = new CustomerDAOImpl();
			Customer customer = customerDAO.getCustomerById(order
					.getCustomers().getId());
			// Customer customer = order.getCustomers();

			secndString.append("(");
			String userId = "";
			userId = customer.getContactNo();

			if (userId != null && userId.length() > 0) {
				secndString.append(userId);
			}
		} catch (Exception ex) {

		}
		secndString.append(")"); // 2nd string end
		kitchenHdrStrng.append("\n");
		String paxString="";
		String waiterString="";
		StringBuffer thrdString = new StringBuffer();
		thrdString.append("Order No:"); // 3rd string start
		//thrdString.append(order.getId());
		thrdString.append(order.getOrderNo());//after incorporating order_no
	
		//order type
		StringBuffer orderType = new StringBuffer(); // order type string
	
		//for pax
		if ("Y".equalsIgnoreCase(store.getIsPax())) {
			paxString="     Pax(s): " + order.getNoOfPersons();	
			}
		//for waiter
		if ("Y".equalsIgnoreCase(store.getWaiterNameFlag())) {
			if(order.getWaiterName() != null && order.getWaiterName().length() > 0 && !"0".equalsIgnoreCase(order.getWaiterName()))
				{
					waiterString="     Waiter: " + order.getWaiterName();	
				}	
			}
		// get table no.
		String tblNo = order.getTable_no();
		
		if (tblNo != null && tblNo.length() > 0 && !"0".equalsIgnoreCase(tblNo)) {
			orderType.append("Table-");
			orderType.append(tblNo);
			orderType.append(waiterString);
			thrdString.append(paxString);
		} else {
			orderType.append(order.getOrdertype().getOrderTypeName());
		       }
    if(!isNewOrder && storeId == YUMMY)  orderType.append("   [RUNNING]");
		
		// 3rd string end
		// 4th string start
		String noOfPersons = "No. of persons:" + order.getNoOfPersons();
		// 4th string end

		kitchenHdrStrng.append("\n\n");
		// declare a map to hold item name, item quantity
		// get items from order

		Map<String, String> mapToHoldItems = new LinkedHashMap<String, String>();
		mapToHoldItems = new LinkedHashMap<String, String>();
		int spnoteCount=0;
		for (int i = 0; i < orderItemLst.size(); i++) { // item
			OrderItem orderItem = orderItemLst.get(i);
			// get the special note
			String specialNote = orderItem.getSpecialNote();
			MenuItem menuItem = orderItem.getItem();
			String itemName = menuItem.getName();
			StringBuffer nameNSpecl = new StringBuffer();
			nameNSpecl.append(itemName);
			nameNSpecl.append("\n" + "##");
			nameNSpecl.append(specialNote);
			
			String quantity = orderItem.getQuantityOfItem();
			mapToHoldItems.put(itemName, quantity);
			if (specialNote != null) {
				if (specialNote.length() > 0
						&& !specialNote.equalsIgnoreCase("")) {
					spnoteCount++;
					mapToHoldItems.put("  "+spnoteCount+"##" + specialNote, "");

				}
			}
			itemName = String.format("%-35s:%s", itemName, quantity);
			kitchenItemStrng.append(itemName);
			kitchenItemStrng.append("\n");
		}
		kitchenEndStrng.append("..........................");

		Object[] arryToHoldElems = new Object[10];
		arryToHoldElems[0] = kot;
		arryToHoldElems[1] = strName;
		arryToHoldElems[2] = secndString;
		arryToHoldElems[3] = thrdString;
		arryToHoldElems[4] = noOfPersons;
		arryToHoldElems[5] = mapToHoldItems;
		arryToHoldElems[6] = orderType;
		arryToHoldElems[7] = cuisineName;
		try {

			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			/*
			int storeid = order.getStoreId();
			// int ordId = order.getId();
			Query oldOrdre = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid and o.cancel='N'");
			oldOrdre.setParameter("orderid", order.getId());
			OrderMaster existingOrder = (OrderMaster) oldOrdre
					.getSingleResult();
			int kotPrintCount = existingOrder.getKotPrintCount();
			*/

			new PosPrinterMain().a(arryToHoldElems, store, printerName);

			// code to set the kot print status and count
			OrdersDAOImpl orderDao = new OrdersDAOImpl();
			orderDao.updateKotPrint(order.getId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (em != null)
				em.close();
		}

		return status;
	}

}

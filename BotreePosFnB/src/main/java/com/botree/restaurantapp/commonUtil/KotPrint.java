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
import java.util.Set;
import java.util.TreeSet;

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
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.print.PrintKotMaster;

public class KotPrint {
	
  private MenuDAO menuDAO = new MenuDAOImpl();

	public String printKotMultiSubCategoryMergeWithKitchen(boolean isNewOrder, OrderMaster order) { // FOR SARVANA wrt kitchen

		String status = "";
		int storeId = order.getStoreId();
		EntityManager em = null;

		try {
			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

//			StoreMaster store = new StoreMaster();
			OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
			List<PrintKotMaster> printers = null;

			try {
				printers = ordersDAOImpl.getAllKotPrinter(storeId);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			
			Set<String> printersSet = new TreeSet<String>();
			
			for (int p = 0; p < printers.size(); p++){
				printersSet.add(printers.get(p).getPrinterIp());
			}
				
			List<OrderItem> orderItemList = order.getOrderitem();
			List<OrderItem> orderItemListCuisine = null;
			
      List<Integer> menuItemIDs = new ArrayList<>();
      for(OrderItem orderItem : orderItemList) {
        menuItemIDs.add(orderItem.getItem().getId());
      }
      
      List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
      
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
      
			if (printers.size() > 0) {
				
			  for(String ps : printersSet){
				System.out.println("printer set name:: " + ps);
				orderItemListCuisine = new ArrayList<OrderItem>();
				
				for (int p = 0; p < printers.size(); p++) { // printer in db
					PrintKotMaster printer = printers.get(p);
					String printerName = printer.getPrinterIp();
					System.out.println("printer name:: " + printerName);
					
					System.out.println("Cuisine: " + printer.getCuisineTypeId());

					for (int i = 0; i < orderItemList.size(); i++) { // item
						OrderItem orderItem = orderItemList.get(i);
						int itemId = orderItem.getItem().getId();
						int catId = 0;
						MenuItem item = menuItemMap.get(itemId); //menuDao.getItemDetailsById(itemId, storeId);
            MenuCategory cat = item.getMenucategory(); //here cat is sub cat
            /*if (storeId == 39) {
            	if (itemname.trim().equalsIgnoreCase(
            			"Water Small")
            			|| itemname.trim().equalsIgnoreCase(
            					"Water Large")) {// No KOT
            		orderItemLst.remove(i);
            		break;
            	}
            }*/
            
            if(item.getIsKotPrint().equalsIgnoreCase("N")){
            	orderItemList.remove(i);
            	break;
            }
            //MenuCategory cat = subCat.getMenutype();
            catId = cat.getId();
            try {
            	int directCat = order.getDirectCat();
            	if (directCat > 0) {

            		if (cat.getPrintStatus().equalsIgnoreCase(
            				"N")) {// No KOT
            			orderItemList.remove(i);
            			break;
            		}
            	}
            } catch (Exception e) {

            }

            boolean kitchen = false;
            for (int p1 = 0; p1 < printers.size(); p1++){
            	printersSet.add(printers.get(p1).getPrinterIp());
            	if(ps.equals(printers.get(p1).getPrinterIp()) && catId == printers.get(p1).getCuisineTypeId()){
            		kitchen = true;
            		break;
            	}
            }
            
            
            if (kitchen) {
            	orderItemListCuisine.add(orderItem);

            	// orderItemLst.remove(i); // printed
            	// i--;
            }

					}//item

				}//printers all
				
				if (orderItemListCuisine.size() > 0) {// kitchen wise
					System.out.println("kitchen wise sravanaa: " + ps);
					print(isNewOrder, order, orderItemListCuisine, storeId, "", 	ps);
				}
			  }
			}//printers set

			else {
				if (orderItemList.size() > 0) { // default printer
					print(isNewOrder, order, orderItemList, storeId, "", "");
				}
			}
			status = "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				em.close();
		}

		return status;

	}

	
	public String printKotMultiSubCategory(boolean isNewOrder, OrderMaster order) { // FOR SARVANA

		String status = "";
		int storeId = order.getStoreId();
		EntityManager em = null;

		try {
			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
			List<PrintKotMaster> printers = null;

			try {
				printers = ordersDAOImpl.getAllKotPrinter(storeId);
			} catch (DAOException e) {
				e.printStackTrace();
			}

			List<OrderItem> orderItemList = order.getOrderitem();
      List<Integer> menuItemIDs = new ArrayList<>();
      
      for(OrderItem orderItem : orderItemList) {
        menuItemIDs.add(orderItem.getItem().getId());
      }
      
      List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
      
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
			
			if (printers.size() > 0)
				for (int p = 0; p < printers.size(); p++) { // printer in db
					PrintKotMaster printer = printers.get(p);
					String printerName = printer.getPrinterIp();
					System.out.println("printer name:: " + printerName);
					List<OrderItem> orderItemListCuisine = new ArrayList<OrderItem>();
					String catName = "";
					System.out
							.println("Cuisine: " + printer.getCuisineTypeId());
					boolean catFound = false;
					for (int i = 0; i < orderItemList.size(); i++) { // item
						OrderItem orderItem = orderItemList.get(i);
						int itemId = orderItem.getItem().getId();
						int catId = 0;
						MenuItem item = menuItemMap.get(itemId); //menuDao.getItemDetailsById(itemId, storeId);
            MenuCategory cat = item.getMenucategory(); //here cat is sub cat
            /*if (storeId == 39) {
            	if (itemname.trim().equalsIgnoreCase(
            			"Water Small")
            			|| itemname.trim().equalsIgnoreCase(
            					"Water Large")) {// No KOT
            		orderItemLst.remove(i);
            		break;
            	}
            }*/
            
            if(item.getIsKotPrint().equalsIgnoreCase("N")){
            	orderItemList.remove(i);
            	break;
            }
            //MenuCategory cat = subCat.getMenutype();
            catId = cat.getId();
            try {
            	int directCat = order.getDirectCat();
            	if (directCat > 0) {

            		if (cat.getPrintStatus().equalsIgnoreCase(
            				"N")) {// No KOT
            			orderItemList.remove(i);
            			break;
            		}
            	}
            } catch (Exception e) {

            }

            if (catId == printer.getCuisineTypeId()) {
            	catName = cat.getMenuCategoryName();
            	catFound = true;
            	orderItemListCuisine.add(orderItem);

            	// orderItemLst.remove(i); // printed
            	// i--;
            }
					}

					if (catFound == false)
						catName = "";

					if (orderItemListCuisine.size() > 0) {// cuisine wise
						print(isNewOrder, order, orderItemListCuisine, storeId, catName,
								printerName);
					}

				}

			if (orderItemList.size() > 0) { // default printer
				// print(order, orderItemLst, storeId, "", "");
			}

			status = "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				em.close();
		}

		return status;

	}



	public String printKotMulti(boolean isNewOrder, OrderMaster order) { // FOR SARVANA

		String status = "";
		int storeId = order.getStoreId();
		EntityManager em = null;

		try {
			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
			List<PrintKotMaster> printers = null;

			try {
				printers = ordersDAOImpl.getAllKotPrinter(storeId);
			} catch (DAOException e) {
				e.printStackTrace();
			}

			List<OrderItem> orderItemList = order.getOrderitem();
			
      List<Integer> menuItemIDs = new ArrayList<>();
      for(OrderItem orderItem : orderItemList) {
        menuItemIDs.add(orderItem.getItem().getId());
      }
      
      List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
      
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
			
			if (printers.size() > 0)
				for (int p = 0; p < printers.size(); p++) { // printer in db
					PrintKotMaster printer = printers.get(p);
					String printerName = printer.getPrinterIp();
					System.out.println("printer name:: " + printerName);
					List<OrderItem> orderItemListCuisine = new ArrayList<OrderItem>();
					String catName = "";
					System.out
							.println("Cuisine: " + printer.getCuisineTypeId());
					boolean catFound = false;
					for (int i = 0; i < orderItemList.size(); i++) { // item
						OrderItem orderItem = orderItemList.get(i);
						int itemId = orderItem.getItem().getId();
						int catId = 0;
						MenuItem item = menuItemMap.get(itemId); //menuDao.getItemDetailsById(itemId, storeId);
            MenuCategory subCat = item.getMenucategory();
            /*if (storeId == 39) {
            	if (itemname.trim().equalsIgnoreCase(
            			"Water Small")
            			|| itemname.trim().equalsIgnoreCase(
            					"Water Large")) {// No KOT
            		orderItemLst.remove(i);
            		break;
            	}
            }*/
            if(item.getIsKotPrint().equalsIgnoreCase("N")){
            	orderItemList.remove(i);
            	break;
            }
            MenuCategory cat = subCat.getMenutype();
            catId = cat.getId();
            try {
            	int directCat = order.getDirectCat();
            	if (directCat > 0) {

            		if (cat.getPrintStatus().equalsIgnoreCase(
            				"N")) {// No KOT
            			orderItemList.remove(i);
            			break;
            		}
            	}
            } catch (Exception e) {

            }

            if (catId == printer.getCuisineTypeId()) {
            	catName = cat.getMenuCategoryName();
            	catFound = true;
            	orderItemListCuisine.add(orderItem);

            	// orderItemLst.remove(i); // printed
            	// i--;
            }

					}

					if (catFound == false)
						catName = "";

					if (orderItemListCuisine.size() > 0) {// cuisine wise
						print(isNewOrder, order, orderItemListCuisine, storeId, catName,
								printerName);
					}

				}

			if (orderItemList.size() > 0) { // default printer
				// print(order, orderItemLst, storeId, "", "");
			}

			status = "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				em.close();
		}

		return status;

	}

	public String printKot(boolean isNewOrder, OrderMaster order) {

		String status = "";
		int storeId = order.getStoreId();
		EntityManager em = null;

		try {
			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
			List<PrintKotMaster> printers = null;

			try {
				printers = ordersDAOImpl.getAllKotPrinter(storeId);
			} catch (DAOException e) {
				e.printStackTrace();
			}

			List<OrderItem> orderItemList = order.getOrderitem();
      List<Integer> menuItemIDs = new ArrayList<>();
      for(OrderItem orderItem : orderItemList) {
        menuItemIDs.add(orderItem.getItem().getId());
      }
      
      List<MenuItem> menuItems = menuDAO.getMenuItems(order.getStoreId(), menuItemIDs);
      
      Map<Integer, MenuItem> menuItemMap = new HashMap<>();
      for(MenuItem menuItem : menuItems) { menuItemMap.put(menuItem.getId(), menuItem); }
			
			if (printers.size() > 0)
				for (int p = 0; p < printers.size(); p++) { // printer in db
					PrintKotMaster printer = printers.get(p);
					String printerName = printer.getPrinterIp();
					System.out.println("printer name:: " + printerName);
					List<OrderItem> orderItemListCuisine = new ArrayList<OrderItem>();
					String catName = "";
					System.out
							.println("Cuisine: " + printer.getCuisineTypeId());
					boolean catFound = false;
					for (int i = 0; i < orderItemList.size(); i++) { // item
						OrderItem orderItem = orderItemList.get(i);
						int itemId = orderItem.getItem().getId();
						int catId = 0;
						MenuItem item = menuItemMap.get(itemId); //menuDao.getItemDetailsById(itemId, storeId);
            MenuCategory subCat = item.getMenucategory();
            
            
            MenuCategory cat = subCat.getMenutype();
            catId = cat.getId();
            try{
            	int directCat=order.getDirectCat();
            	if(directCat>0){
            		
            		if(cat.getPrintStatus().equalsIgnoreCase("N")){//No KOT
            			orderItemList.remove(i);
            			i--;
            			//break;
            		}
            	}
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
            try{
            itemId = orderItemList.get(i).getItem().getId();// //refresh
            item =  menuItemMap.get(itemId);  //menuDAO.getItemDetailsById(itemId, storeId);
            /*
            String itemname=item.getName();
            if (storeId == 39) {
            	if (itemname.trim().equalsIgnoreCase("Soft Drinks") ||itemname.trim().equalsIgnoreCase("Water Small") || itemname.trim().equalsIgnoreCase("Water Large")) {//No KOT
            		orderItemLst.remove(i);
            		i--;
            		//break;
            	}
            }*/
            if(item.getIsKotPrint().equalsIgnoreCase("N")){
            	orderItemList.remove(i);
            	i--;
            }
            }catch(Exception ex){
            	
            	
            }
            if (printers.size()==1) {
            	
            		//catName = cat.getMenuCategoryName();
            		//catFound = true;
            		orderItemListCuisine.add(orderItem);

            		orderItemList.remove(i); // printed
            		i--;
            	
            }
            else {
            	if (catId == printer.getCuisineTypeId()) {
            		catName = cat.getMenuCategoryName();
            		catFound = true;
            		orderItemListCuisine.add(orderItem);

            		orderItemList.remove(i); // printed
            		i--;
            	}
            }

					}

					if (catFound == false)
						catName = "";

					if (orderItemListCuisine.size() > 0) {// cuisine wise
						print(isNewOrder, order, orderItemListCuisine, storeId, catName,
								printerName);
					}

				}

			if (orderItemList.size() > 0) { // default printer
				print(isNewOrder, order, orderItemList, storeId, "", "");
			}

			status = "Success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				em.close();
		}

		return status;

	}
	
	public String printUpdateKot(boolean isNewOrder, OrderMaster order,int currentQuantity, int previousQuantity,String itemName) {

		String status = "";
		int storeId = order.getStoreId();
		EntityManager em = null;

		try {
			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			//printUpdateKot(isNewOrder, order, storeId, "", "",currentQuantity,previousQuantity,itemName);

			//StoreMaster store = new StoreMaster();
			OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
			//MenuDAO menuDao = new MenuDAOImpl();
			List<PrintKotMaster> printers = null;

			try {
				printers = ordersDAOImpl.getAllKotPrinter(storeId);
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
		      String printerName=null;
		      String catName = "";
			if (printers.size() > 0){
				for (int p = 0; p < printers.size(); p++) { // printer in db
					PrintKotMaster printer = printers.get(p);
					printerName = printer.getPrinterIp();
					//System.out.println("printer name:: " + printerName);
					//List<OrderItem> orderItemLstCuisine = new ArrayList<OrderItem>();
					
					System.out
							.println("Cuisine: " + printer.getCuisineTypeId());
					//boolean catFound = false;
					for (int i = 0; i < orderItemLst.size(); i++) { // item
						OrderItem orderItem = orderItemLst.get(i);
						int itemId = orderItem.getItem().getId();
						int catId = 0;
						//try {
							//MenuItem item = menuDao.getItemDetailsById(itemId+ "", storeId + "");
						   MenuItem item = menuItemMap.get(itemId);
						   if(item.getName().equalsIgnoreCase(itemName))
						   {
							   MenuCategory subCat = item.getMenucategory();
							   MenuCategory cat = subCat.getMenutype();
							   catId = cat.getId();
							   if (catId == printer.getCuisineTypeId()) {
									catName = cat.getMenuCategoryName();
									printerName = printer.getPrinterIp();
									/*printUpdateKot(isNewOrder,order, storeId, catName,
											printerName,currentQuantity,previousQuantity,itemName);
									catFound=true;
									status = "Success";
									return status;*/
							   }
							   else
							   {
								  /* printUpdateKot(isNewOrder,order, storeId, "", "",currentQuantity,previousQuantity,itemName);
								   status = "Success";
								   return status;*/
							   }
							   break;
						   }
						   
							/*MenuCategory subCat = item.getMenucategory();
							MenuCategory cat = subCat.getMenutype();
							catId = cat.getId();
							try{
								int directCat=order.getDirectCat();
								if(directCat>0){
									
									if(cat.getPrintStatus().equalsIgnoreCase("N")){//No KOT
										orderItemLst.remove(i);
										i--;
										//break;
									}
								}
							}
							catch(Exception e){
								e.printStackTrace();
							}
							
							try{
							itemId =orderItemLst.get(i).getItem().getId();// //refresh
							//item = menuDao.getItemDetailsById(itemId+ "", storeId + "");
							item =  menuItemMap.get(itemId);
							String itemname=item.getName();
							if (storeId == 39) {
								if (itemname.trim().equalsIgnoreCase("Soft Drinks") ||itemname.trim().equalsIgnoreCase("Water Small") || itemname.trim().equalsIgnoreCase("Water Large")) {//No KOT
									orderItemLst.remove(i);
									i--;
									//break;
								}
							}
							if(item.getIsKotPrint().equalsIgnoreCase("N")){
								orderItemLst.remove(i);
				            	i--;
				            }
							}catch(Exception ex){
								
								
							}
							if (printers.size()==1) {
								
									//catName = cat.getMenuCategoryName();
									//catFound = true;
									orderItemLstCuisine.add(orderItem);

									orderItemLst.remove(i); // printed
									i--;
								
							}
							else {
								if (catId == printer.getCuisineTypeId()) {
									catName = cat.getMenuCategoryName();
									catFound = true;
									orderItemLstCuisine.add(orderItem);

									orderItemLst.remove(i); // printed
									i--;
								}
							}*/
						/*} catch (DAOException e) {
							e.printStackTrace();
						}*/

					}

					/*if (catFound == false)
						catName = "";

					if (orderItemLstCuisine.size() > 0) {// cuisine wise
						printUpdateKot(isNewOrder,order, orderItemLstCuisine, storeId, catName,
								printerName,currentQuantity,previousQuantity);
					}*/
					
				}
				if(printerName!=null){
					printUpdateKot(isNewOrder,order, storeId, catName,
							printerName,currentQuantity,previousQuantity,itemName);
				}
				else{
					printUpdateKot(isNewOrder,order, storeId, "", "",currentQuantity,previousQuantity,itemName);
				}
				}else
				{
					printUpdateKot(isNewOrder,order, storeId, "", "",currentQuantity,previousQuantity,itemName);
				}

			/*if (orderItemLst.size() > 0) { // default printer
				printUpdateKot(isNewOrder,order, storeId, "", "",currentQuantity,previousQuantity,itemName);
			}*/

			status = "Success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				em.close();
		}

		return status;

	}
	
	

	public String print(boolean isNewOrder, OrderMaster order, List<OrderItem> orderItemList,
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

		String kot = null;
		
		if(CommonProerties.longWaitingOrder==true){
			kot = " ** - KOT -  ";
		}
		else {
			kot = " - KOT -  ";
		}
		CommonProerties.longWaitingOrder=false;
		
		if(storeId == SARAVANA1 || storeId == SARAVANA2){
			kot = "KOT-" + printerName.split(" ")[0];
		}
		
    String cuisineName = "(" + cuisine + ")";
    //if(!isNewOrder && storeId == YUMMY)  cuisineName += "   [RUNNING]";
    
		kitchenHdrStrng = new StringBuffer();
		kitchenItemStrng = new StringBuffer();
		kitchenEndStrng = new StringBuffer();
		kitchenHdrStrng.append(kot);
		kitchenHdrStrng.append("\n");
		kitchenHdrStrng.append(strName); // 1st string
		kitchenHdrStrng.append("\n");
		// get order time
		//String ordeTime = order.getOrderTime();
		
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
		

		
		StringBuffer secndString = new StringBuffer();
		secndString.append(ordeTime); // 2nd string start
		secndString.append(" ");
		// get order taken by person info

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
		StringBuffer orderType = new StringBuffer(); // order type string
		// thrdString.append(",");
        //for pax
		if ("Y".equalsIgnoreCase(store.getIsPax())) {
			paxString="     Pax(s): " + order.getNoOfPersons();	
		}
		//for waiter
		if ("Y".equalsIgnoreCase(store.getWaiterNameFlag())) {
			if( order.getWaiterName() != null && order.getWaiterName().length() > 0 && !"0".equalsIgnoreCase(order.getWaiterName()))
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
		
		// get remarks.
	    String remarks = order.getRemarks();
		
		//add for showing running status of an order
		if(!isNewOrder && storeId == YUMMY)  orderType.append("   [RUNNING]");
		// 3rd string end
		// 4th string start
		 String noOfPersons = "Pax(s): " + order.getNoOfPersons() + "";
		//String noOfPersons = "No. of persons:";
		// 4th string end

		kitchenHdrStrng.append("\n\n");
		// declare a map to hold item name, item quantity
		// get items from order

		Map<String, String> mapToHoldItems = new LinkedHashMap<String, String>();
		Map<String, String> mapToHoldItemsOld = new LinkedHashMap<String, String>();
		mapToHoldItems = new LinkedHashMap<String, String>();
		int spnoteCount=0;
		for (int i = 0; i < orderItemList.size(); i++) { // item
			OrderItem orderItem = orderItemList.get(i);
			// get the special note
			String specialNote = orderItem.getSpecialNote();
			MenuItem menuItem = orderItem.getItem();
			String itemName = menuItem.getName();
			StringBuffer nameNSpecl = new StringBuffer();
			nameNSpecl.append(itemName);
			nameNSpecl.append("\n" + "##");
			nameNSpecl.append(specialNote);
			String quantity = orderItem.getQuantityOfItem();
			
			if (mapToHoldItemsOld.containsKey(itemName)){
			
				String currentQuantity=mapToHoldItemsOld.get(itemName);
				//int newQuantity=Integer.parseInt(currentQuantity)+Integer.parseInt(quantity);
				//String newQnty=Integer.toString(newQuantity);
				double newQuantity=Double.parseDouble(currentQuantity)+Double.parseDouble(quantity);
				String newQnty=Double.toString(newQuantity);
				mapToHoldItems.put(itemName, newQnty);
				quantity=newQnty;
			}
			else {
				mapToHoldItems.put(itemName, quantity);
			}

			mapToHoldItemsOld.put(itemName, quantity);
			
			
			if (specialNote != null) {
				if (specialNote.length() > 0 && !specialNote.equalsIgnoreCase("")) {
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
		arryToHoldElems[8] = remarks;
		try {

			EntityManagerFactory entityManagerFactory;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

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
	
	public String printUpdateKot(boolean isNewOrder, OrderMaster order,	int storeId, String cuisine, String printerName,int currentQuantity, int previousQuantity,String itemName) {
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
		StringBuffer kitchenEndStrng = new StringBuffer();

		String strName = store.getStoreName();
		String kot = " - CHANGE KOT -  ";
		
		if(storeId == SARAVANA1 || storeId == SARAVANA2){
			kot = "CHANGE KOT-" + printerName.split(" ")[0];
		}
		
		String cuisineName = "(" + cuisine + ")";
		
		kitchenHdrStrng.append(kot);
		kitchenHdrStrng.append("\n");
		kitchenHdrStrng.append(strName); // 1st string
		kitchenHdrStrng.append("\n");
		// get order time
		//String ordeTime = order.getOrderTime();
		
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
		

		StringBuffer secndString = new StringBuffer();
		secndString.append(ordeTime); // 2nd string start
		secndString.append(" ");
		// get order taken by person info

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
		StringBuffer orderType = new StringBuffer(); // order type string
		// thrdString.append(",");
		 //for pax
		if ("Y".equalsIgnoreCase(store.getIsPax())) {
			paxString="     Pax(s): " + order.getNoOfPersons();	
		}
		//for waiter
		if ("Y".equalsIgnoreCase(store.getWaiterNameFlag())) {
			if( order.getWaiterName() != null && order.getWaiterName().length() > 0 && !"0".equalsIgnoreCase(order.getWaiterName()))
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
		// String noOfPersons = "No. of persons:" + order.getNoOfPersons() + "";
		String noOfPersons = "No. of persons:";
		// 4th string end

		kitchenHdrStrng.append("\n\n");
		// declare a map to hold item name, item quantity
		// get items from order

		/*for (int i = 0; i < orderItemLst.size(); i++) { // item
			OrderItem orderItem = orderItemLst.get(i);
			// get the special note
			String specialNote = orderItem.getSpecialNote();
			MenuItem menuItem = orderItem.getItem();
			String itemName = menuItem.getName();
			StringBuffer nameNSpecl = new StringBuffer();
			nameNSpecl.append(itemName);
			nameNSpecl.append("\n" + "##");
			nameNSpecl.append(specialNote);
			String nameWithSpclNote = nameNSpecl.toString();
			// String nameWithSpclNote=itemName+"##"+specialNote;
			int itemLength = itemName.length();
			int maxLength = 28;
			String quantity = orderItem.getQuantityOfItem();
			
			if (mapToHoldItemsOld.containsKey(itemName)){
			
				String currentQuantity1=mapToHoldItemsOld.get(itemName);
				int newQuantity=Integer.parseInt(currentQuantity1)+Integer.parseInt(quantity);
				String newQnty=Integer.toString(newQuantity);
				mapToHoldItems.put(itemName, newQnty);
				quantity=newQnty;
			}
			else {
				mapToHoldItems.put(itemName, quantity);
			}

			mapToHoldItemsOld.put(itemName, quantity);
			
			
			if (specialNote != null) {
				if (specialNote.length() > 0
						&& !specialNote.equalsIgnoreCase("")) {
					mapToHoldItems.put("  ##" + specialNote, "");

				}
			}
			itemName = String.format("%-35s:%s", itemName, quantity);
			kitchenItemStrng.append(itemName);
			kitchenItemStrng.append("\n");
		}*/
		kitchenEndStrng.append("..........................");

		Object[] arryToHoldElems = new Object[10];
		arryToHoldElems[0] = kot;
		arryToHoldElems[1] = strName;
		arryToHoldElems[2] = secndString;
		arryToHoldElems[3] = thrdString;
		arryToHoldElems[4] = noOfPersons;
		arryToHoldElems[5] = itemName;
		arryToHoldElems[6] = orderType;
		arryToHoldElems[7] = cuisineName;
		arryToHoldElems[8] = ""+previousQuantity;
		arryToHoldElems[9] = ""+currentQuantity;
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
		
			new PosPrinterMainUpdateKot().a(arryToHoldElems, store, printerName);

			// code to set the kot print status and count
			//OrdersDAOImpl orderDao = new OrdersDAOImpl();
			//orderDao.updateKotPrint(order.getId());
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

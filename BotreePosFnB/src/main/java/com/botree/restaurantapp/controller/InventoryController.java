package com.botree.restaurantapp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;

import com.botree.restaurantapp.model.InventoryItems;
import com.botree.restaurantapp.model.InventoryType;
import com.botree.restaurantapp.model.InventoryVendor;
import com.botree.restaurantapp.model.Sales;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.InventoryService;
import com.botree.restaurantapp.service.exception.ServiceException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class InventoryController {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
  
	private InventoryService inventoryService;
	private InventoryType inventoryType=null;
	private InventoryType newInventoryType;
	private InventoryItems inventoryItems;
	private InventoryItems newInventoryItems;
	private InventoryVendor inventoryVendor;
	private InventoryVendor newInventoryVendor;
	// private Feedback feedback;
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private List<SelectItem> selectVendors = new ArrayList<SelectItem>();
	private List<SelectItem> selectTypeItems = new ArrayList<SelectItem>();
	private List<InventoryType> invTypeList = new ArrayList<InventoryType>();
	private List<InventoryItems> invTypeItemList = new ArrayList<InventoryItems>();
	private List<InventoryVendor> vendorList = new ArrayList<InventoryVendor>();
	private String redirect;
	private String action;
	private String id;
	private int storeid;
	private String storeName = null;
	private Sales sales = new Sales();
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private boolean showOrderReportPane = false;
	

	public InventoryController() {

		System.out.println("in InventoryController ");
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("Inside get all inventories");
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

			getInventoryTypes(storeId);
			getInventoryTypeItems(storeId);
			getInventoryVendor(storeId);
		} catch (Exception e) {
			e.printStackTrace();
		
		}

	}

	private void getInventoryTypes(Integer storeId) {
		try {
			System.out.println("select items size:" + selectItems.size());

			invTypeList = inventoryService.getInventoryType(storeId);

			// System.out.println("number of items:" + itemList.size());
			Iterator<InventoryType> iterator = invTypeList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				InventoryType invType = (InventoryType) iterator.next();

				si.setValue(invType.getId());
				System.out.println("inv type id" + invType.getId());
				si.setLabel(invType.getName());
				System.out.println("inv type name" + invType.getName());

				selectItems.add(si);
			}
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("test100");
		}
	}
	
	
	private void getInventoryVendor(int storeId) {
		try {
			System.out.println("select items size:" + selectVendors.size());

			vendorList = inventoryService.getInventoryVendor(storeId);

			// System.out.println("number of items:" + itemList.size());
			Iterator<InventoryVendor> iterator = vendorList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				InventoryVendor invVendor = (InventoryVendor) iterator.next();

				si.setValue(invVendor.getId());
				System.out.println("inv type id" + invVendor.getId());
				si.setLabel(invVendor.getName());
				System.out.println("inv type name" + invVendor.getName());

				selectVendors.add(si);
			}
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("test100");
		}
	}
	
	private void getInventoryTypeItems(int storeId) {
		try {
			System.out.println("select items size:" + selectTypeItems.size());

			invTypeItemList = inventoryService.getInventoryItems(storeId);

			// System.out.println("number of items:" + itemList.size());
			Iterator<InventoryItems> iterator = invTypeItemList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				InventoryItems invTypeItem = (InventoryItems) iterator.next();

				si.setValue(invTypeItem.getId());
				System.out.println("inv type item id" + invTypeItem.getId());
				si.setLabel(invTypeItem.getName());
				System.out.println("inv type item name" + invTypeItem.getName());

				selectTypeItems.add(si);
			}
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("test100");
		}
	}

	public String goToAddInventoryTypePage() {
		System.out.println("inside goToAddInventoryTypePage");
		return "/page/add_inventory_type.xhtml?redirect=true";

	}
	
	public String goToDispInventoryTypePage() {
		System.out.println("inside goToDispInventoryTypePage");
		return "/page/disp_inv_type.xhtml?redirect=true";

	}
	
	public String goToDispInventoryVendorPage() {
		System.out.println("inside goToDispInventoryTypePage");
		return "/page/disp_inv_vendor.xhtml?redirect=true";

	}
	
	public String goToDispInventoryTypeItemPage() {
		System.out.println("inside goToDispInventoryTypeItemPage");
		return "/page/disp_inv_type_item.xhtml?redirect=true";

	}

	public String addInventoryType() {

		try {
			inventoryService.addInventoryType(newInventoryType);
			System.out.println("InventoryController.addInventoryType");
			// redirect = "/page/disp_user.xhtml?faces-redirect=true";

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return "/page/disp_inv_type.xhtml?faces-redirect=true";

	}

	public String goToAddInventoryTypeItemPage() {
		System.out.println("inside goToAddInventoryTypeItemPage");
		return "/page/add_inventory_item.xhtml?redirect=true";

	}

	public String addInventoryItem() {

		try {
			inventoryService.addInventoryItem(newInventoryItems);
			System.out.println("InventoryController.newInventoryItems");
			/*redirect = "/page/disp_inv_type_item.xhtml?faces-redirect=true";*/

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return "/page/disp_inv_type_item.xhtml?faces-redirect=true";

	}
	public String goToAddInventoryVendorPage() {
		String redirect = "";
		System.out.println("inside goToAddInventoryVendorPage");
		redirect= "/page/add_vendor.xhtml?redirect=true";
		return redirect;

	}
	
	public String addInventoryVendor() {

		String redirect = "";
		try {
			inventoryService.addInventoryVendor(newInventoryVendor);
			System.out.println("InventoryController.newInventoryVendor");
			redirect = "/page/disp_inv_vendor.xhtml?faces-redirect=true";
			return redirect;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		FacesContext context = FacesContext.getCurrentInstance();
		String viewId = context.getViewRoot().getViewId();
		javax.faces.application.ViewHandler handler = context.getApplication().getViewHandler();
		UIViewRoot root = handler.createView(context, viewId);
		root.setViewId(viewId);
		context.setViewRoot(root);
		
		return null;

	}
	
	public String editInventoryType() {

		System.out.println("In editInventoryType");
		System.out.println("inventory id: " + inventoryType.getId());

		return "/page/edit_inventoryType.xhtml?faces-redirect=true";
	}
	
	public String editInventoryTypeItem() {

		System.out.println("In editInventoryType");
		System.out.println("inventory id: " + inventoryItems.getId());

		return "/page/edit_inventoryTypeItem.xhtml?faces-redirect=true";
	}
	
	public String editInventoryVendor() {

		String redirect = "";
		System.out.println("In editInventoryVendor");
		System.out.println("inventory id: " + inventoryVendor.getId());
		redirect="/page/edit_inv_vendor.xhtml?faces-redirect=true";
		FacesContext context = FacesContext.getCurrentInstance();
		String viewId = context.getViewRoot().getViewId();
		javax.faces.application.ViewHandler handler = context.getApplication().getViewHandler();
		UIViewRoot root = handler.createView(context, viewId);
		root.setViewId(viewId);
		context.setViewRoot(root);
		return redirect;
	}
	
	public String updateInventoryType() {

		try {
			System.out.println("inventory id: " + inventoryType);
			
			inventoryService.updateInventoryType(inventoryType);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("InventoryController.save");

		return "/page/disp_inv_type.xhtml?faces-redirect=true";

	}
	
	public String updateInventoryVendor() {
		
		String redirect = "";

		try {
			System.out.println("inventory id: " + inventoryVendor.getId());
			
			inventoryService.updateInventoryVendor(inventoryVendor);						
			redirect= "/page/disp_inv_vendor.xhtml?faces-redirect=true";
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("InventoryController.save");
		
		FacesContext context = FacesContext.getCurrentInstance();
		String viewId = context.getViewRoot().getViewId();
		javax.faces.application.ViewHandler handler = context.getApplication().getViewHandler();
		UIViewRoot root = handler.createView(context, viewId);
		root.setViewId(viewId);
		context.setViewRoot(root);
		return redirect;

	}
	
	public String updateInventoryTypeItem() {

		try {
			System.out.println("inventory Item id: " + inventoryItems);
			
			inventoryService.updateInventoryTypeItem(inventoryItems);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("InventoryController.save");

		return "/page/disp_inv_type_item.xhtml?faces-redirect=true";

	}
	
	public String deleteInventoryTypeItem() {
		System.out.println("In deleteInventoryTypeItem");

		try {
			System.out.println("InventoryTypeItem to b deleted is:" + inventoryItems);
			inventoryService.deleteInventoryItems(inventoryItems);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("InventoryController.deleteInventoryTypeItem");

		return "/page/disp_inv_type_item.xhtml?faces-redirect=true";

	}
	
	
	public String deleteInventoryType() {
		System.out.println("In deleteInventoryType");

		try {
			System.out.println("InventoryType to b deleted is:" + inventoryType.getId());
			inventoryService.deleteInventoryType(inventoryType);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("InventoryController.deleteInventoryType");

		return "/page/disp_inv_type.xhtml?faces-redirect=true";

	}
	
	public String deleteInventoryVendor() {
		System.out.println("In deleteInventoryVendor");

		try {
			System.out.println("InventoryVendor to b deleted is:" + inventoryVendor.getId());
			inventoryService.deleteInventoryVendor(inventoryVendor);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("InventoryController.deleteInventoryType");

		return "/page/disp_inv_vendor.xhtml?faces-redirect=true";

	}
	
	//inventory Report
	public String getInventoryReport(){


		System.out.println("enter getInventoryReport::");
		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
	  EntityManager em = null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			//get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context.getExternalContext().getSessionMap().get("selectedstore");
			//FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}
			sales.setStoreId(storeid);
			sales.setStoreName(storeName);

			System.out.println("from order date:: " + sales.getSalesFrmDate());
			String frmdate = formatter.format(sales.getSalesFrmDate());
			System.out.println("formatted date " + frmdate);
			System.out.println("to order date:: " + sales.getSalesToDate());
			String todate = formatter.format(sales.getSalesToDate());
			System.out.println("to order date:: " + todate);

			Map<String, Object> parameters = new HashMap<>();

			if (sales.getSalesReportPeriodType().equalsIgnoreCase("dailyInv")) {
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", Integer.toString(sales.getStoreId()));
				fileName = "sales_report_daily.pdf";
				jasperFile = "restaurant_inventory_daily.jrxml";
			}

			else if (sales.getSalesReportPeriodType().equalsIgnoreCase("monthlyInv")) {
				System.out.println("yearly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", Integer.toString(sales.getStoreId()));
				fileName = "Inventory_report_monthly.pdf";
				jasperFile = "restaurant_inventory_monthly.jrxml";
			}

			else if (sales.getSalesReportPeriodType().equalsIgnoreCase("weeklyInv")) {
				System.out.println("monthly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", Integer.toString(sales.getStoreId()));
				fileName = "Inventory_report_weekly.pdf";
				jasperFile = "restaurant_inventory_weekly.jrxml";
			}
			//open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters, connection, jasperFile);

			System.out.println("exit getSalesReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			//finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					if (em != null)
					em.close();
				}
			} catch (SQLException se) {}
		}

		return "";
	
		
	}
	
	public String getInventoryReportStockout(){

		System.out.println("enter getInventoryReport::");
		Statement stmt = null;
		Connection connection = null;
		String fileName = null;
		String jasperFile = null;
	  EntityManager em = null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();

			//get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context.getExternalContext().getSessionMap().get("selectedstore");
			//FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) (externalContext.getRequest());
			HttpServletResponse response = (HttpServletResponse) (externalContext.getResponse());
			if (storeMaster != null) {
				storeid = storeMaster.getId();
				storeName = storeMaster.getStoreName();
			}
			sales.setStoreId(storeid);
			sales.setStoreName(storeName);

			System.out.println("from order date:: " + sales.getSalesFrmDate());
			String frmdate = formatter.format(sales.getSalesFrmDate());
			System.out.println("formatted date " + frmdate);
			System.out.println("to order date:: " + sales.getSalesToDate());
			String todate = formatter.format(sales.getSalesToDate());
			System.out.println("to order date:: " + todate);

			Map<String, Object> parameters = new HashMap<>();

			if (sales.getSalesReportPeriodType().equalsIgnoreCase("daily")) {
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", Integer.toString(sales.getStoreId()));
				fileName = "sales_report__stockout_daily.pdf";
				jasperFile = "restaurant_inventory_out_daily.jrxml";
			}

			else if (sales.getSalesReportPeriodType().equalsIgnoreCase("monthly")) {
				System.out.println("yearly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", Integer.toString(sales.getStoreId()));
				fileName = "Inventory_report_stockout_monthly.pdf";
				jasperFile = "restaurant_inventory_out_monthly.jrxml";
			}

			else if (sales.getSalesReportPeriodType().equalsIgnoreCase("weekly")) {
				System.out.println("monthly data:::");
				parameters.put("W_StartDate", frmdate);
				parameters.put("W_EndDate", todate);
				parameters.put("W_StoreId", Integer.toString(sales.getStoreId()));
				fileName = "Inventory_report_stockout_weekly.pdf";
				jasperFile = "restaurant_inventory_out_weekly.jrxml";
			}
			//open report in new tab in pdf format
			generatePDF(context, request, response, fileName, parameters, connection, jasperFile);

			System.out.println("exit getSalesReport");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			//finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
					connection.close();
					if (em != null)
					em.close();
				}
			} catch (SQLException se) {}
		}

		return "";
	
		
	
		
	}
	
	public void generatePDF(FacesContext context,
							HttpServletRequest request,
							HttpServletResponse response,
							String fileName,
							Map<String, Object> parameters,
							Connection connection,
							String jasperFile) throws FileNotFoundException, IOException {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {

			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);

			JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/jasper") + "/" + jasperFile);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
			JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);

			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		}

		catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following exception in the logs:
		// java.lang.IllegalStateException: Cannot forward after response has been committed.
		context.responseComplete();
	}
	
	
	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail it. It may be useful to 
				// know that this will generally only be thrown when the client aborted the download.
				e.printStackTrace();
			}
		}
	}

	//StockIn Inventory Daily Report for stockin
		public String dispInventoryReport() {
			System.out.println("in dispInventoryReport stockin  :" + storeid);
			FacesContext context = FacesContext.getCurrentInstance();

			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			if (params.get("storeId") != null) {
				storeid = Integer.parseInt(params.get("storeId"));
			}
			System.out.println("store id: " + storeid);

			//return "disp_sales_report";
			return "disp_inventory_report";
			
		}
		
		//StockIn Inventory Daily Report for Stockout
				public String dispInventoryReportForStockout() {
					System.out.println("in dispInventoryReport stockout :" + storeid);
					FacesContext context = FacesContext.getCurrentInstance();

					Map<String, String> params = context.getExternalContext().getRequestParameterMap();
					if (params.get("storeId") != null) {
						storeid = Integer.parseInt(params.get("storeId"));
					}
					System.out.println("store id: " + storeid);
					
					return "disp_inventory_report_stockout";
					
				}

	
	/*
	 * public String delete() { System.out.println("In deleteFeedback");
	 * 
	 * try { feedbackService.delete(feedback);
	 * 
	 * } catch (ServiceException e) {
	 * 
	 * e.printStackTrace(); } System.out.println("FeedbackController.delete");
	 * 
	 * return "/page/disp_feedback.xhtml?faces-redirect=true";
	 * 
	 * }
	 */

	/*
	 * public List<Feedback> getFeedbackList() { return feedbackList; }
	 * 
	 * public void setFeedbackList(List<Feedback> feedbackList) {
	 * this.feedbackList = feedbackList; }
	 */

	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public InventoryType getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}

	public InventoryItems getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(InventoryItems inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public List<InventoryType> getInvTypeList() {
		return invTypeList;
	}

	public List<InventoryItems> getInvTypeItemList() {
		return invTypeItemList;
	}

	public void setInvTypeItemList(List<InventoryItems> invTypeItemList) {
		this.invTypeItemList = invTypeItemList;
	}

	public List<InventoryVendor> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<InventoryVendor> vendorList) {
		this.vendorList = vendorList;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInvTypeList(List<InventoryType> invTypeList) {
		this.invTypeList = invTypeList;
	}
	
	public InventoryVendor getInventoryVendor() {
		return inventoryVendor;
	}

	public void setInventoryVendor(InventoryVendor inventoryVendor) {
		this.inventoryVendor = inventoryVendor;
	}
	

	public InventoryVendor getNewInventoryVendor() {
		return newInventoryVendor;
	}

	public void setNewInventoryVendor(InventoryVendor newInventoryVendor) {
		this.newInventoryVendor = newInventoryVendor;
	}

	public InventoryType getNewInventoryType() {
		return newInventoryType;
	}

	public void setNewInventoryType(InventoryType newInventoryType) {
		this.newInventoryType = newInventoryType;
	}

	public InventoryItems getNewInventoryItems() {
		return newInventoryItems;
	}

	public void setNewInventoryItems(InventoryItems newInventoryItems) {
		this.newInventoryItems = newInventoryItems;
	}

	public List<SelectItem> getSelectVendors() {
		return selectVendors;
	}

	public void setSelectVendors(List<SelectItem> selectVendors) {
		this.selectVendors = selectVendors;
	}

	public List<SelectItem> getSelectTypeItems() {
		return selectTypeItems;
	}

	public void setSelectTypeItems(List<SelectItem> selectTypeItems) {
		this.selectTypeItems = selectTypeItems;
	}

	public boolean isShowOrderReportPane() {
		return showOrderReportPane;
	}

	public void setShowOrderReportPane(boolean showOrderReportPane) {
		this.showOrderReportPane = showOrderReportPane;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}
	

	
}

package com.botree.restaurantapp.webservice.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.CustomerInfo;
import com.botree.restaurantapp.model.ServiceChargesFortAllItems;
import com.botree.restaurantapp.model.StoreCustomer;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.StoreCustomerService;
import com.botree.restaurantapp.service.exception.ServiceException;
import com.botree.restaurantapp.webservice.StoreCustomerWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Controller
@ResponseBody
@RequestMapping(value = "/storeCustomer")
public class StoreCustomerWSImpl implements StoreCustomerWS {

  @Autowired
	private StoreCustomerService storeCustomerService;
  
	private static Gson gson = new Gson();
  private final Gson GSON_WO_EXPOSED = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  
	private final static Logger logger = LogManager.getLogger(StoreCustomerWSImpl.class);
	

	@Override
	@RequestMapping(value = "/getStoreCustomerByPhNmbr", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getStoreCustomerByPhNmbr(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "phone") String phone) {
		CustomerInfo custList = null;
		storeCustomerService=new StoreCustomerService();
		try {
			custList = storeCustomerService
					.getStoreCustomerByPhNmbr(phone, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(custList, CustomerInfo.class);
	}
	
	
	@RequestMapping(value = "/getCreditCustomerByPhNmbr", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCreditCustomerByPhNmbr(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "phone") String phone) {
		StoreCustomer creditCust = null;
		storeCustomerService=new StoreCustomerService();
		try {
			creditCust = storeCustomerService.getCreditCustomerByPhNmbr(phone, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(creditCust, StoreCustomer.class);
	}

	@RequestMapping(value = "/getStoreCustomerByCustId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getStoreCustomerByCustId(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "custId") int custId) {
		CustomerInfo custList = null;
		storeCustomerService=new StoreCustomerService();
		try {
			custList = storeCustomerService
					.getStoreCustomerByCustId(custId, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(custList, CustomerInfo.class);
	}
	
	@Override
	@RequestMapping(value = "/getAllStoreCustomerByPhNmbr", method = RequestMethod.GET, produces = "text/plain")
	public String getAllStoreCustomerByPhNmbr(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "phone") String phone) {
		List<StoreCustomer> custList = null;
		storeCustomerService=new StoreCustomerService();
		try {
			custList = storeCustomerService
					.getAllStoreCustomerByPhNmbr(phone, storeId);
			System.out.println(custList.toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		java.lang.reflect.Type type = new TypeToken<List<StoreCustomer>>() {}.getType();
		return GSON_WO_EXPOSED.toJson(custList, type);
	}
	
	@Override
	@RequestMapping(value = "/getAllStoreCustomerByName", method = RequestMethod.GET, produces = "text/plain")
	public String getAllStoreCustomerByName(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "name") String name) {
		List<StoreCustomer> custList = null;
		storeCustomerService=new StoreCustomerService();
		try {
			custList = storeCustomerService.getAllStoreCustomerByName(storeId,
					name);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {}.getType();
		return GSON_WO_EXPOSED.toJson(custList, type);
	}
	
	//added on 04.04.2019
	@Override
	@RequestMapping(value = "/getAllStoreCustomerByStoreId", method = RequestMethod.GET, produces = "text/plain")
	public String getAllStoreCustomerByStoreId(
			@RequestParam(value = "storeId") String storeId) {
		List<StoreCustomer> custList = null;
		//storeCustomerService=new StoreCustomerService();
		try {
			custList = storeCustomerService.getAllStoreCustomerByStoreId(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {}.getType();
		return GSON_WO_EXPOSED.toJson(custList, type);
	}
	
	//added on 31.10.2019
	@Override
	@RequestMapping(value = "/getAllRBStoreCustomerByPhNmbr", method = RequestMethod.GET, produces = "text/plain")
	public String getAllRBStoreCustomerByPhNmbr(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "phone") String phone) {
		List<StoreCustomer> custList = null;
		//storeCustomerService=new StoreCustomerService();
		try {
			custList = storeCustomerService
					.getAllRBStoreCustomerByPhNmbr(phone, storeId);
			System.out.println(custList.toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		java.lang.reflect.Type type = new TypeToken<List<StoreCustomer>>() {}.getType();
		return GSON_WO_EXPOSED.toJson(custList, type);
	}
	
	@Override
	@RequestMapping(value = "/getAllRBStoreCustomerByName", method = RequestMethod.GET, produces = "text/plain")
	public String getAllRBStoreCustomerByName(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "name") String name) {
		List<StoreCustomer> custList = null;
		//storeCustomerService=new StoreCustomerService();
		try {
			custList = storeCustomerService.getAllRBStoreCustomerByName(storeId,
					name);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		java.lang.reflect.Type type = new TypeToken<List<Customer>>() {}.getType();
		return GSON_WO_EXPOSED.toJson(custList, type);
	}
	
	@Override
	@RequestMapping(value = "/convertToCreditCustomer", method = RequestMethod.GET, produces = "text/plain")
	public String convertToCreditCustomer(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "custId") String custId) {
		String status = "failure";
		try {
			status = storeCustomerService
					.convertToCreditCustomer(custId, storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return status;
	}

	@Override
	@RequestMapping(value = "/getTotaltransactionPerCustomer", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getTotaltransactionPerCustomer(
			@RequestParam(value = "storeCustomerId") int storeCustomerId,
			@RequestParam(value = "storeId") String storeId) {
		double sumBillDetails = 0;
		storeCustomerService = new StoreCustomerService();
		try {
			sumBillDetails = storeCustomerService.getTotaltransactionPerCustomer(storeCustomerId,storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return gson.toJson(String.valueOf(sumBillDetails));
	}
	
	@Override
	@RequestMapping(value = "/getCustomerMostPurchaseItem", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerMostPurchaseItem(
			@RequestParam(value = "storeCustomerId") int storeCustomerId,
			@RequestParam(value = "storeId") String storeId) {
		//List<String> customerLastVisitDateListObj= new ArrayList<String>();
		List<ArrayList<String>> customerMostPurchaseItem=new ArrayList<ArrayList<String>>();
		storeCustomerService = new StoreCustomerService();
		try {
			customerMostPurchaseItem = storeCustomerService.getCustomerMostPurchaseItem(storeCustomerId,storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(customerMostPurchaseItem);
	}
	
	@Override
	@RequestMapping(value = "/getCustomerLastVisitDate", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerLastVisitDate(
			@RequestParam(value = "storeCustomerId") int storeCustomerId,
			@RequestParam(value = "storeId") String storeId) {
		List<String> customerLastVisitDateListObj= new ArrayList<String>();
		storeCustomerService = new StoreCustomerService();
		try {
			customerLastVisitDateListObj = storeCustomerService.getCustomerLastVisitDate(storeCustomerId,storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(customerLastVisitDateListObj);
	}
	
	@Override
	@RequestMapping(value = "/getCustomerTotalSpendAmount", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerTotalSpendAmount(
			@RequestParam(value = "storeCustomerId") int storeCustomerId,
			@RequestParam(value = "storeId") String storeId) {
		List<String> customerLastVisitDateListObj= new ArrayList<String>();
		storeCustomerService = new StoreCustomerService();
		try {
			customerLastVisitDateListObj = storeCustomerService.getCustomerTotalSpendAmount(storeCustomerId,storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(customerLastVisitDateListObj);
	}
	
	@Override
	@RequestMapping(value = "/getTopCustomer", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getTopCustomer(
			@RequestParam(value = "storeId") String storeId) {
		//List<String> topCustomerList= new ArrayList<String>();
		List<ArrayList<CustomerInfo>> topCustomerList=new ArrayList<ArrayList<CustomerInfo>>();
		storeCustomerService = new StoreCustomerService();
		try {
			topCustomerList = storeCustomerService.getTopCustomer(storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(topCustomerList);
	}
	
	@Override
	@RequestMapping(value = "/getCustomerTransactionSummery", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerTransactionSummery(
			@RequestParam(value = "storeCustomerId") int storeCustomerId,
			@RequestParam(value = "storeId") String storeId) {
		//List<String> customerTransactionSummery= new ArrayList<String>();
		List<ArrayList<String>> customerTransactionSummery=new ArrayList<ArrayList<String>>();;
		//List<String> transactionSummeryList= new ArrayList<String>();
		storeCustomerService = new StoreCustomerService();
		try {
			customerTransactionSummery = storeCustomerService.getCustomerTransactionSummery(storeCustomerId,storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(customerTransactionSummery);
	}
	
	@Override
	@RequestMapping(value = "/getCustomerPaymentSummery", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getCustomerPaymentSummery(
			@RequestParam(value = "storeCustomerId") int storeCustomerId,
			@RequestParam(value = "storeId") String storeId) {
		//List<String> customerPaymentSummery= new ArrayList<String>();
		List<ArrayList<String>> customerPaymentSummery=new ArrayList<ArrayList<String>>();
		storeCustomerService = new StoreCustomerService();
		try {
			customerPaymentSummery = storeCustomerService.getCustomerPaymentSummery(storeCustomerId,storeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return GSON_WO_EXPOSED.toJson(customerPaymentSummery);
	}
	
	@Override
	@RequestMapping(value = "/reportOutstandingCustomerDetails", method = RequestMethod.GET)
	public void reportCustomerDetails(
			@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();

			// TODO:Change Files
			baseFileName = "Outstanding Customer details";
			jasperFile = "Outstanding Customer details.jrxml";
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("W_StoreID", storeId);
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@RequestMapping(value = "/reportTopFiveCustomerDetails", method = RequestMethod.GET)
	public void reportTop5CustomerDetails(
			@RequestParam(value = "storeId") Integer storeId,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();

			// TODO:Change Files
			baseFileName = "Top_5_Customer_Details";
			jasperFile = "Top_5_Customer_Details.jrxml";
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("W_StoreID",storeId);
			
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	@RequestMapping(value = "/reportOldVsNewCustomerDetails", method = RequestMethod.GET)
	public void reportOldVsNewCustomerDetails(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "orderFrom") String orderFrom,
			@RequestParam(value = "orderTo") String orderTo,
      @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();

			// TODO:Change Files
			baseFileName = "Old_Vs_New_CustomerDetails";
			jasperFile = "Old_Vs_New_CustomerDetails.jrxml";
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("W_StoreID", storeId);
			parameters.put("W_OrderFrom", orderFrom);
			parameters.put("W_OrderTo", orderTo);

      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      try {
        if(is != null) { is.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	}
	
	/**
	 * @Desc Order Item Details Report
	 * 
	 */
	
	@RequestMapping(value = "/reportOrderItemDetails", method = RequestMethod.GET)
	public void reportOrderItemDetails(
			@RequestParam(value = "storeId") Integer storeId,
			@RequestParam(value = "orderFrom") String orderFrom,
			@RequestParam(value = "orderTo") String orderTo,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();

			// TODO:Change Files
			baseFileName = "OrderItemDetails.pdf";
			jasperFile = "OrderItemDetails.jrxml";
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("W_StoreId", storeId);
			parameters.put("W_OrderFrom", orderFrom);
			parameters.put("W_OrderTo", orderTo);

      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*@RequestMapping(value = "/storeServiceCharges",
	method = RequestMethod.GET,
	consumes = "application/json",
	produces = "text/plain")
	public String storeServiceCharges(
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "orderTypeShortName") String orderTypeShortName,
			@RequestParam(value = "scVal") String scVal) {
		String status=null;
		storeCustomerService = new StoreCustomerService();
		try {
			storeCustomerService.storeServiceCharges(storeId,orderTypeShortName,scVal);
			status="success";
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return status;
	}*/
	
	
	@RequestMapping(value = "/storeServiceCharges", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String storeServiceCharges(@RequestBody String serviceChargesFortAllItems) {
		String status=null;
		storeCustomerService = new StoreCustomerService();
		logger.info("Req param: " + serviceChargesFortAllItems);
		try {
			ServiceChargesFortAllItems serviceChargesFortAllItemsJson[] = gson.fromJson(serviceChargesFortAllItems,
							ServiceChargesFortAllItems[].class);
			for (ServiceChargesFortAllItems serviceChargesFortAllItemsObj : serviceChargesFortAllItemsJson) {
				int storeId = serviceChargesFortAllItemsObj.getStoreId();
				String shortName = serviceChargesFortAllItemsObj.getOrderTypeShortName();
				float scValue = serviceChargesFortAllItemsObj.getScValue();

				storeCustomerService.storeServiceCharges(storeId, shortName, Float.toString(scValue));
			}
			status="SUCCESS";
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//logger.info("serviceChargesFortAllItems Json : " + serviceChargesFortAllItemsJson);
		/*try {
			storeCustomerService.storeAllServiceCharges(serviceChargesFortAllItems);
			status="success";
		} catch (ServiceException e) {
			e.printStackTrace();
		}*/
		return status;
	}
	
	// method to generate pdf
		public void generatePDF(FacesContext context, HttpServletRequest request,
				HttpServletResponse response, String fileName,
				Map<String, Object> parameters, Connection connection,
				String jasperFile) throws FileNotFoundException, IOException {
		  
			try {

        JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + jasperFile);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
        JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
			}
			catch (Exception e) {
				e.printStackTrace();
			} finally {

			}

			// Inform JSF that it doesn't need to handle response.
			// This is very important, otherwise you will get the following
			// exception in the logs:
			// java.lang.IllegalStateException: Cannot forward after response has
			// been committed.
			// context.responseComplete();
		}

    // Method to generate Excel report
    public void generateXLSX(FacesContext context, HttpServletRequest request,
        HttpServletResponse response, String fileName,
        Map<String, Object> parameters, Connection connection,
        String jasperFile) throws FileNotFoundException, IOException {

      
      try {
        JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + jasperFile);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
        
        File xlsxFile = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        
        xlsxExporter.setExporterInput(new SimpleExporterInput(print));
        xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxFile));
        
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        xlsxExporter.setConfiguration(configuration);

//        xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.JASPER_PRINT, print);
//        xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.OUTPUT_FILE, xlsxFile);
//        xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        
        xlsxExporter.exportReport(); //File is generated Correctly
      }
      catch (JRException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } finally {

      }
    }
  
		
	public StoreCustomerService getStoreCustomerService() {
		return storeCustomerService;
	}

	public void setStoreCustomerService(
			StoreCustomerService storeCustomerService) {
		this.storeCustomerService = storeCustomerService;
	}
}

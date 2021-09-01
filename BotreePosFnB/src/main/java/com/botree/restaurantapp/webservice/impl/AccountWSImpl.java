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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.commonUtil.DateUtil;
import com.botree.restaurantapp.model.DailyExpenditure;
import com.botree.restaurantapp.model.DailyExpenditureType;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.AccountService;
import com.botree.restaurantapp.webservice.AccountWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Controller
@ResponseBody
@RequestMapping(value = "/account")
public class AccountWSImpl implements AccountWS {

  @Autowired
	private AccountService accountService;

	@Override
	@RequestMapping(value = "/addDailyExpenditure", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addDailyExpenditure(@RequestBody DailyExpenditure dailyExpenditure) {
		int dailyExpId = 0;
		try {
			dailyExpId = accountService.addDailyExpenditure(dailyExpenditure);
			
		} catch (Exception x) {
			x.printStackTrace();
		}

		return "" + dailyExpId;
	}

	@Override
	@RequestMapping(value = "/getDailyExpenditureByDate", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getDailyExpenditureByDate(
			@RequestParam(value = "date") String date,
			@RequestParam(value = "storeId") String storeId) {
		List<DailyExpenditure> dailyExpenditureList = null;
		try {

			dailyExpenditureList = accountService.getDailyExpenditureByDate(
					date, storeId);

		} catch (Exception x) {
			//System.out.println("in webservice.......");
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DailyExpenditure>>() {
		}.getType();
		String json = gson.toJson(dailyExpenditureList, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/getDailyExpenditureByPeriod", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getDailyExpenditureByPeriod(
			@RequestParam(value = "date") String date,
			@RequestParam(value = "todate") String toDate,
			@RequestParam(value = "storeId") String storeId) {
		List<DailyExpenditure> dailyExpenditureList = null;
		try {

			dailyExpenditureList = accountService.getDailyExpenditureByPeriod(
					date,toDate, storeId);

		} catch (Exception x) {
			//System.out.println("in webservice.......");
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DailyExpenditure>>() {
		}.getType();
		String json = gson.toJson(dailyExpenditureList, type);

		return json;
	}
	
	@Override
	@RequestMapping(value = "/deleteDailyExpenditure", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String deleteDailyExpenditure(@RequestBody DailyExpenditure dailyExpenditure) {
		String status = "";
		try {
			status = accountService.deleteDailyExpenditure(dailyExpenditure);

		} catch (Exception x) {
			x.printStackTrace();
		}
		return status;

	}
	
	@Override
	@RequestMapping(value = "/updateDailyExpenditure", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public String updateDailyExpenditure(@RequestBody DailyExpenditure dailyExpenditure) {
		String status = "";
		try {
			status = accountService.updateDailyExpenditure(dailyExpenditure);

		} catch (Exception x) {
			x.printStackTrace();
		}
		return status;

	}
	
	//added on 15.05.2018
	@Override
	@RequestMapping(value = "/getExpenditureTypes", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getExpenditureTypes() {
		List<DailyExpenditureType> dailyExpenditureTypeList = null;
		try {

			dailyExpenditureTypeList = accountService.getExpenditureTypes();

		} catch (Exception x) {
			//System.out.println("in webservice.......");
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DailyExpenditureType>>() {
		}.getType();
		String json = gson.toJson(dailyExpenditureTypeList, type);

		return json;
	}
	
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	
	
	/*Account Reports START*/
	
	@RequestMapping(value = "/report/dailyCollectionReport", method = RequestMethod.GET)
	public void getdailyCollectionReport(@RequestParam(value = "cmpnyId") int cmpnyId,
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "finYrId") int finYrId,
			
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "report_format",required=false,defaultValue = "1") int report_format, //1:PDF / 2:XLS
			
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("dailyCollectionReport Parameters are : cmpnyId = "+cmpnyId +", storeId = "+storeId+", finYrId = "+finYrId+", startDate = "+startDate+", endDate = "+endDate+", report_format = "+report_format);
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			String DATE_FORMAT_NEEDED = "MM/dd/yyyy";
			String DATE_FORMAT_RECEIVED = "yyyy-MM-dd";
			String convertdDateStrt=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,startDate);
			String convertdDateEnd=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,endDate);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			//String asOnDatestr=mapper.getAsOnDate();
			java.sql.Date startDt= DateUtil.convertStringDateToSqlDate(convertdDateStrt, DATE_FORMAT_NEEDED);
			java.sql.Date endDt= DateUtil.convertStringDateToSqlDate(convertdDateEnd, DATE_FORMAT_NEEDED);
			
			
			Map<String, Object> parameters = new HashMap<String,Object>();

			parameters.put("W_companyID", cmpnyId);
			parameters.put("W_storeID", storeId);
			parameters.put("W_finyrID", finYrId);
			parameters.put("W_startDate", startDt);
			parameters.put("W_endDate", endDt);
			
			
			if(report_format==1) //pdf
			{
				fileName = "DailyCollection.pdf";
				jasperFile = "DailyCollection.jrxml";
			}
			else if(report_format==2) //xls
			{
				fileName = "DailyCollection.xls";
				jasperFile = "DailyCollectionXls.jrxml";
			}
			

			if(report_format==1)
			{
				// call method to generate pdf
				generatePDF(request, response, fileName, parameters,connection, jasperFile);
			}
			else if(report_format==2) {
				// call method to generate xls
				generateXLS(request, response, fileName, parameters,connection, jasperFile);
			}
			
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
			InputStream is = new FileInputStream(file);
			
			if(report_format==1) //PDF
			{
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
			}
			else if(report_format==2) { //XLSX
				response.reset();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\""); //for excel file download
			}
			
			
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/report/dailyPaymentReport", method = RequestMethod.GET)
	public void getdailyPaymentReport (
			@RequestParam(value = "cmpnyId") int cmpnyId,
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "finYrId") int finYrId,
			
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "report_format",required=false,defaultValue = "1") int report_format, //1:PDF / 2:XLS
			
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("dailyPaymentReport Parameters are : cmpnyId = "+cmpnyId +", storeId = "+storeId+", finYrId = "+finYrId+", startDate = "+startDate+", endDate = "+endDate+", report_format = "+report_format);
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			String DATE_FORMAT_NEEDED = "MM/dd/yyyy";
			String DATE_FORMAT_RECEIVED = "yyyy-MM-dd";
			String convertdDateStrt=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,startDate);
			String convertdDateEnd=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,endDate);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			//String asOnDatestr=mapper.getAsOnDate();
			java.sql.Date startDt= DateUtil.convertStringDateToSqlDate(convertdDateStrt, DATE_FORMAT_NEEDED);
			java.sql.Date endDt= DateUtil.convertStringDateToSqlDate(convertdDateEnd, DATE_FORMAT_NEEDED);
			
			
			Map<String, Object> parameters = new HashMap<String,Object>();

			parameters.put("W_companyID", cmpnyId);
			parameters.put("W_storeID", storeId);
			parameters.put("W_finyrID", finYrId);
			parameters.put("W_startDate", startDt);
			parameters.put("W_endDate", endDt);
			
			
			if(report_format==1) //pdf
			{
				fileName = "DailyPayment.pdf";
				jasperFile = "DailyPayment.jrxml";
			}
			else if(report_format==2) //xls
			{
				fileName = "DailyPayment.xls";
				jasperFile = "DailyPaymentXls.jrxml";
			}
			

			if(report_format==1)
			{
				// call method to generate pdf
				generatePDF(request, response, fileName, parameters,connection, jasperFile);
			}
			else if(report_format==2) {
				// call method to generate xls
				generateXLS(request, response, fileName, parameters,connection, jasperFile);
			}
			
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
			InputStream is = new FileInputStream(file);
			
			if(report_format==1) //PDF
			{
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
			}
			else if(report_format==2) { //XLSX
				response.reset();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\""); //for excel file download
			}
			
			
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/report/balanceSheetReport", method = RequestMethod.GET)
	public void getbalanceSheetReport (
			@RequestParam(value = "cmpnyId") int cmpnyId,
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "finYrId") int finYrId,
			
			@RequestParam(value = "asOnDate") String asOnDate,
			@RequestParam(value = "report_format",required=false,defaultValue = "1") int report_format, //1:PDF / 2:XLS
			
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("balanceSheet Parameters are : cmpnyId = "+cmpnyId +", storeId = "+storeId+", finYrId = "+finYrId+", asOnDate = "+asOnDate+", report_format = "+report_format);
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			String DATE_FORMAT_NEEDED = "MM/dd/yyyy";
			String DATE_FORMAT_RECEIVED = "yyyy-MM-dd";
			String convertdAsOnDate=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,asOnDate);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			//String asOnDatestr=mapper.getAsOnDate();
			java.sql.Date asOnDt= DateUtil.convertStringDateToSqlDate(convertdAsOnDate, DATE_FORMAT_NEEDED);
			
			
			Map<String, Object> parameters = new HashMap<String,Object>();

			parameters.put("W_companyID", cmpnyId);
			parameters.put("W_storeID", storeId);
			parameters.put("W_finyrID", finYrId);
			
			parameters.put("W_asOnDate", asOnDt);
			
			parameters.put("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/");
			
			//parameters.put("SUBREPORT_DIR",generateSubReport(request, response, "BalanceSheetAssets.jasper", parameters,connection, "BalanceSheetAssets.jrxml"));
			//parameters.put("SUBREPORT_DIR",generateSubReport(request, response, "BalanceSheetLiabilities.jasper", parameters,connection, "BalanceSheetLiabilities.jrxml"));
			
			generateSubReport(request, response, "BalanceSheetAssets.jasper", parameters,connection, "BalanceSheetAssets.jrxml");
			generateSubReport(request, response, "BalanceSheetLiabilities.jasper", parameters,connection, "BalanceSheetLiabilities.jrxml");
			
			if(report_format==1) //pdf
			{
				fileName = "BalanceSheet.pdf";
				jasperFile = "BalanceSheet.jrxml";
			}
			else if(report_format==2) //xls
			{
				fileName = "BalanceSheet.xls";
				jasperFile = "BalanceSheetXls.jrxml";
			}
			

			if(report_format==1)
			{
				// call method to generate pdf
				generatePDF(request, response, fileName, parameters,connection, jasperFile);
			}
			else if(report_format==2) {
				// call method to generate xls
				generateXLS(request, response, fileName, parameters,connection, jasperFile);
			}
			
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
			InputStream is = new FileInputStream(file);
			
			if(report_format==1) //PDF
			{
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
			}
			else if(report_format==2) { //XLSX
				response.reset();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\""); //for excel file download
			}
			
			
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/report/accountBalanceReport", method = RequestMethod.GET)
	public void getAccountBalanceReport (
			@RequestParam(value = "cmpnyId") int cmpnyId,
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "finYrId") int finYrId,
			
			@RequestParam(value = "accountgroup_id") int groupId,
			@RequestParam(value = "account_id") int ledgerId,
			
			@RequestParam(value = "asOnDate") String asOnDate,
			@RequestParam(value = "report_format",required=false,defaultValue = "1") int report_format, //1:PDF / 2:XLS
			
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("accountBalance Parameters are : cmpnyId = "+cmpnyId +", storeId = "+storeId+", finYrId = "+finYrId+", groupId = "+groupId+", ledgerId = "+ledgerId+", asOnDate = "+asOnDate+", report_format = "+report_format);
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			String DATE_FORMAT_NEEDED = "MM/dd/yyyy";
			String DATE_FORMAT_RECEIVED = "yyyy-MM-dd";
			String convertdAsOnDate=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,asOnDate);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			//String asOnDatestr=mapper.getAsOnDate();
			java.sql.Date asOnDt= DateUtil.convertStringDateToSqlDate(convertdAsOnDate, DATE_FORMAT_NEEDED);
			
			
			Map<String, Object> parameters = new HashMap<String,Object>();

			parameters.put("W_companyID", cmpnyId);
			parameters.put("W_storeID", storeId);
			parameters.put("W_finyrID", finYrId);
			
			parameters.put("W_groupID", groupId);
			parameters.put("W_ledgerID", ledgerId);
			
			parameters.put("W_asOnDate", asOnDt);
			
			if(report_format==1) //pdf
			{
				fileName = "AccountBalance.pdf";
				jasperFile = "AccountBalance.jrxml";
			}
			else if(report_format==2) //xlsx
			{
				fileName = "AccountBalance.xlsx";
				jasperFile = "AccountBalanceXls.jrxml";
			}
			

			if(report_format==1)
			{
				// call method to generate pdf
				generatePDF(request, response, fileName, parameters,connection, jasperFile);
			}
			else if(report_format==2) {
				// call method to generate xls
				generateXLSX(request, response, fileName, parameters,connection, jasperFile);
			}
			
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
			InputStream is = new FileInputStream(file);
			
			if(report_format==1) //PDF
			{
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
			}
			else if(report_format==2) { //XLSX
				response.reset();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\""); //for excel file download
			}
			
			
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/report/profitNLossReport", method = RequestMethod.GET)
	public void getProfitLossReport (
			@RequestParam(value = "cmpnyId") int cmpnyId,
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "finYrId") int finYrId,

			@RequestParam(value = "asOnDate") String asOnDate,
			@RequestParam(value = "report_format",required=false,defaultValue = "1") int report_format, //1:PDF / 2:XLS
			
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("profitNLossReport Parameters are : cmpnyId = "+cmpnyId +", storeId = "+storeId+", finYrId = "+finYrId+", asOnDate = "+asOnDate+", report_format = "+report_format);
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			String DATE_FORMAT_NEEDED = "MM/dd/yyyy";
			String DATE_FORMAT_RECEIVED = "yyyy-MM-dd";
			String convertdAsOnDate=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,asOnDate);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			//String asOnDatestr=mapper.getAsOnDate();
			java.sql.Date asOnDt= DateUtil.convertStringDateToSqlDate(convertdAsOnDate, DATE_FORMAT_NEEDED);
			
			
			Map<String, Object> parameters = new HashMap<String,Object>();

			parameters.put("W_companyID", cmpnyId);
			parameters.put("W_storeID", storeId);
			parameters.put("W_finyrID", finYrId);
			
			parameters.put("W_asOnDate", asOnDt);
			
			if(report_format==1) //pdf
			{
				fileName = "ProfitNLoss.pdf";
				jasperFile = "ProfitNLoss.jrxml";
			}
			else if(report_format==2) //xls
			{
				fileName = "ProfitNLoss.xls";
				jasperFile = "ProfitNLossXls.jrxml";
			}
			

			if(report_format==1)
			{
				// call method to generate pdf
				generatePDF(request, response, fileName, parameters,connection, jasperFile);
			}
			else if(report_format==2) {
				// call method to generate xls
				generateXLS(request, response, fileName, parameters,connection, jasperFile);
			}
			
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
			InputStream is = new FileInputStream(file);
			
			if(report_format==1) //PDF
			{
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
			}
			else if(report_format==2) { //XLSX
				response.reset();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\""); //for excel file download
			}
			
			
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/report/accounts", method = RequestMethod.GET)
	public void getAccountsReport(@RequestParam(value = "cmpnyId") int cmpnyId,
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "finYrId") int finYrId,
			
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			
			@RequestParam(value = "accountgroup_id") int accountgroup_id,
			@RequestParam(value = "account_id") int account_id,
			//@RequestParam(value = "report_type") int report_type,
			
			@RequestParam(value = "report_format",required=false,defaultValue = "1") int report_format,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("accounts ledger Parameters are : cmpnyId = "+cmpnyId +", storeId = "+storeId+", finYrId = "+finYrId+", startDate = "+startDate+", endDate = "+endDate+", accountgroup_id = "+accountgroup_id+", account_id = "+account_id+", report_format = "+report_format);
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			String DATE_FORMAT_NEEDED = "MM/dd/yyyy";
			String DATE_FORMAT_RECEIVED = "yyyy-MM-dd";
			String convertdDateStrt=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,startDate);
			String convertdDateEnd=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,endDate);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			//String asOnDatestr=mapper.getAsOnDate();
			java.sql.Date strtDt= DateUtil.convertStringDateToSqlDate(convertdDateStrt, DATE_FORMAT_NEEDED);
			java.sql.Date endDt= DateUtil.convertStringDateToSqlDate(convertdDateEnd, DATE_FORMAT_NEEDED);
			
			
			Map<String, Object> parameters = new HashMap<String,Object>();

			parameters.put("W_companyID", cmpnyId);
			parameters.put("W_storeID", storeId);
			parameters.put("W_finyrID", finYrId);
			parameters.put("W_startDate", strtDt);
			parameters.put("W_endDate", endDt);
			
			parameters.put("W_groupID", accountgroup_id);
			parameters.put("W_ledgerID", account_id);
			
			
			//fileName = "accounts.pdf";
			
				if(report_format==1)
				{
					fileName = "LedgerBalance.pdf";
					jasperFile = "LedgerBalance.jrxml";
				}
				else if(report_format==2)
				{
					fileName = "LedgerBalance.xls";
					jasperFile = "LedgerBalanceXls.jrxml";
				}

			if(report_format==1)
			{
				// call method to generate pdf
				generatePDF(request, response, fileName, parameters,connection, jasperFile);
			}
			else if(report_format==2) {
				// call method to generate xls
				generateXLS(request, response, fileName, parameters,connection, jasperFile);
			}
			
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
			InputStream is = new FileInputStream(file);
			
			if(report_format==1) //PDF
			{
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
			}
			else if(report_format==2) { //XLSX
				response.reset();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\""); //for excel file download
			}
			
			
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/report/trialBalance",
	method = RequestMethod.GET)
	public void getTrialBalanceReport(@RequestParam(value = "cmpnyId") int cmpnyId,
			@RequestParam(value = "storeId") int storeId,
			@RequestParam(value = "finYrId") int finYrId,
			
			@RequestParam(value = "asOnDate") String asOnDate,
			
			//@RequestParam(value = "report_type") int report_type,
			
			@RequestParam(value = "trial_type") int trial_type, //1 : GroupTrial , 2 : LedgerTrial
			
			@RequestParam(value = "report_format",required=false,defaultValue = "1") int report_format, //1:PDF / 2:XLS
			
			@RequestParam(value = "isExcludingZero") int isExcludingZero,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("trialBalance Parameters are : cmpnyId = "+cmpnyId +", storeId = "+storeId+", finYrId = "+finYrId+", asOnDate = "+asOnDate+", report_format = "+report_format+", trial_type = "+trial_type+", isExcludingZero = "+isExcludingZero);
		String fileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		try {
			String DATE_FORMAT_NEEDED = "MM/dd/yyyy";
			String DATE_FORMAT_RECEIVED = "yyyy-MM-dd";
			String convertdAsOnDate=DateUtil.convertReceivedDateToNeededDateStr(DATE_FORMAT_RECEIVED,DATE_FORMAT_NEEDED,asOnDate);
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();
			//String asOnDatestr=mapper.getAsOnDate();
			java.sql.Date asOnDt= DateUtil.convertStringDateToSqlDate(convertdAsOnDate, DATE_FORMAT_NEEDED);
			
			
			Map<String, Object> parameters = new HashMap<String,Object>();

			parameters.put("W_companyID", cmpnyId);
			parameters.put("W_storeID", storeId);
			parameters.put("W_finyrID", finYrId);
			parameters.put("W_asOnDate", asOnDt);
			parameters.put("W_IsExcludingZero", isExcludingZero);
			
			//fileName = "accounts.pdf";
			if(trial_type == 1) //groupwise
			{
				if(report_format==1)
				{
					fileName = "GroupTrialBalance.pdf";
					jasperFile = "GroupTrialBalance.jrxml";
				}
				else if(report_format==2)
				{
					fileName = "GroupTrialBalance.xls";
					jasperFile = "GroupTrialBalanceXls.jrxml";
				}
			}
			if(trial_type == 2) //ledgerwise
			{
				if(report_format==1)
				{
					fileName = "LedgerTrialBalance.pdf";
					jasperFile = "LedgerTrialBalance.jrxml";
				}
				else if(report_format==2)
				{
					fileName = "LedgerTrialBalance.xls";
					jasperFile = "LedgerTrialBalanceXls.jrxml";
				}
			}

			if(report_format==1)
			{
				// call method to generate pdf
				generatePDF(request, response, fileName, parameters,connection, jasperFile);
			}
			else if(report_format==2) {
				// call method to generate xls
				generateXLS(request, response, fileName, parameters,connection, jasperFile);
			}
			
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
			InputStream is = new FileInputStream(file);
			
			if(report_format==1) //PDF
			{
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
			}
			else if(report_format==2) { //XLSX
				response.reset();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\""); //for excel file download
			}
			
			
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*ACCOUNTS REPORTS END*/
	
	// method to generate sub report and return
	public void generateSubReport(HttpServletRequest request,
			HttpServletResponse response, String fileName,
			Map<String, Object> parameters, Connection connection,
			String jasperFile) throws FileNotFoundException, IOException {
		
		try {
			System.out.println("fileName = "+fileName);
			System.out.println("jasperFile = "+jasperFile);
			System.out.println("jasperfile path = " + request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + jasperFile);
			
			//Create .jasper file as per filename
			JasperCompileManager.compileReportToFile(request.getSession().getServletContext().getRealPath("/jaspe/accounts/") + jasperFile, request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);

		}
		catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
					
		
		// method to generate pdf
		public void generatePDF(HttpServletRequest request,
				HttpServletResponse response, String fileName,
				Map<String, Object> parameters, Connection connection,
				String jasperFile) throws FileNotFoundException, IOException {
			
			try {
				System.out.println("fileName = "+fileName);
				System.out.println("jasperFile = "+jasperFile);
				System.out.println("jasperfile path = " + request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + jasperFile);
				
				
				JasperReport report;
				report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + jasperFile);

				JasperPrint print = JasperFillManager.fillReport(report,
						parameters, connection);
				JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);

			}

			catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
		}
			
		
		
		// Method to generate Excel report
		  public void generateXLSX(HttpServletRequest request,
		      HttpServletResponse response, String fileName,
		      Map<String, Object> parameters, Connection connection,
		      String jasperFile) throws FileNotFoundException, IOException {

		    
		    try {
		      JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + jasperFile);
		      JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
		      
		      File xlsxFile = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + fileName);
		      JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		      
		      xlsxExporter.setExporterInput(new SimpleExporterInput(print));
		      xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxFile));
		      
		      SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
		      configuration.setDetectCellType(true);
		      configuration.setCollapseRowSpan(false);
		      xlsxExporter.setConfiguration(configuration);
		      
//		      xlsxExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
//		      xlsxExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE, xlsxFile);
//		      xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		      
		      xlsxExporter.exportReport(); //File is generated Correctly
		    }
		    catch (JRException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    } finally {

		    }
		  }
		  
		  
		// method to generate xls
		public void generateXLS(HttpServletRequest request,
				HttpServletResponse response, String fileName,
				Map<String, Object> parameters, Connection connection,
				String jasperFile) throws FileNotFoundException, IOException {
			

			try {
				String xlsPath=request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" +fileName;

				JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/accounts/" + jasperFile);
				JasperPrint print = JasperFillManager.fillReport(report,parameters, connection);
				JRXlsExporter exporterXls = new JRXlsExporter();
				
//      exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, print);
//      exporterXls.setParameter(JRXlsExporterParameter.OFFSET_X,0);
//      exporterXls.setParameter(JRXlsExporterParameter.OFFSET_Y,0);
//      exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, xlsPath);
      
      exporterXls.setExporterInput(new SimpleExporterInput(print));
      exporterXls.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsPath));
             
      SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
      configuration.setOffsetX(0);
      configuration.setOffsetY(0);
//      configuration.setIgnoreCellBorder(false);
//      configuration.setDetectCellType(true);
//      configuration.setWhitePageBackground(false);
//      configuration.setRemoveEmptySpaceBetweenRows(true);
//      configuration.setRemoveEmptySpaceBetweenColumns(true);
//      configuration.setCollapseRowSpan(true);
      exporterXls.setConfiguration(configuration);
				
				exporterXls.exportReport();
			}

			catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}
		
}

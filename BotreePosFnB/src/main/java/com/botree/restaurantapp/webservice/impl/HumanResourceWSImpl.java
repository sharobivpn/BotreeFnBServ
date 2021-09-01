package com.botree.restaurantapp.webservice.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.botree.restaurantapp.model.hr.Department;
import com.botree.restaurantapp.model.hr.Designation;
import com.botree.restaurantapp.model.hr.DutyShift;
import com.botree.restaurantapp.model.hr.EmpAttendance;
import com.botree.restaurantapp.model.hr.EmpLeaveCal;
import com.botree.restaurantapp.model.hr.EmpShiftSchedule;
import com.botree.restaurantapp.model.hr.EmpShiftScheduleList;
import com.botree.restaurantapp.model.hr.Employee;
import com.botree.restaurantapp.model.hr.EmployeeHoliday;
import com.botree.restaurantapp.model.hr.EmployeeLeave;
import com.botree.restaurantapp.model.hr.EmployeeOffDay;
import com.botree.restaurantapp.model.hr.EmployeeType;
import com.botree.restaurantapp.model.hr.StoreHoliday;
import com.botree.restaurantapp.service.HumanResourceService;
import com.botree.restaurantapp.webservice.HumanResourceWS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/hr")
public class HumanResourceWSImpl implements HumanResourceWS {
  
	//private final static Logger logger = LogManager.getLogger(HumanResourceWSImpl.class);

	@Autowired
	private HumanResourceService humanResourceService;

	@Override
	@RequestMapping(value = "/getAllDepartments",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllDepartments(
			@RequestParam(value = "storeId") String storeId) {
		List<Department> departmnts = null;
		try {
			departmnts = humanResourceService.getAllDepartments(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Department>>() {
		}.getType();
		String json = gson.toJson(departmnts, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllDesignations",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllDesignations(
			@RequestParam(value = "storeId") String storeId) {
		List<Designation> designations = null;
		try {
			designations = humanResourceService.getAllDesignations(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Designation>>() {
		}.getType();
		String json = gson.toJson(designations, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllDutyShifts",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllDutyShifts(@RequestParam(value = "storeId") String storeId) {
		List<DutyShift> dutyShifts = null;
		try {
			dutyShifts = humanResourceService.getAllDutyShifts(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<DutyShift>>() {
		}.getType();
		String json = gson.toJson(dutyShifts, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getDutyShiftsById",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getDutyShiftsById(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "id") String id) {
		DutyShift dutyShift = null;
		try {
			dutyShift = humanResourceService.getDutyShiftsById(storeId,id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<DutyShift>() {
		}.getType();
		String json = gson.toJson(dutyShift, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getAllEmployees",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllEmployees(@RequestParam(value = "storeId") String storeId) {
		List<Employee> employees = null;
		try {
			employees = humanResourceService.getAllEmployees(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Employee>>() {
		}.getType();
		String json = gson.toJson(employees, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getEmployeeById",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmployeeById(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "id") String id) {
		Employee employee = null;
		try {
			employee = humanResourceService.getEmployeeById(storeId,id);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<Employee>() {
		}.getType();
		String json = gson.toJson(employee, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getEmployeeHolidays",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllEmployeeHolidays(
			@RequestParam(value = "storeid") String storeid) {
		List<EmployeeHoliday> holidays = null;
		try {
			holidays = humanResourceService.getAllEmployeeHolidays(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmployeeHoliday>>() {
		}.getType();
		String json = gson.toJson(holidays, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getEmployeeLeaves",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmployeeLeaves(
			@RequestParam(value = "storeid") String storeid) {
		List<EmployeeLeave> leaves = null;
		try {
			leaves = humanResourceService.getEmployeeLeaves(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmployeeLeave>>() {
		}.getType();
		String json = gson.toJson(leaves, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getEmployeeOffDay",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmployeeOffDay(
			@RequestParam(value = "storeid") String storeid) {
		List<EmployeeOffDay> offday = null;
		try {
			offday = humanResourceService.getEmployeeOffDay(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmployeeOffDay>>() {
		}.getType();
		String json = gson.toJson(offday, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getEmployeeTypes",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmployeeTypes(@RequestParam(value = "storeId") String storeId) {
		List<EmployeeType> types = null;
		try {
			types = humanResourceService.getEmployeeTypes(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmployeeType>>() {
		}.getType();
		String json = gson.toJson(types, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getEmployeeTypesById",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmployeeTypesById(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "id") String id) {
		EmployeeType emptype = null;
		try {
			int empId = Integer.parseInt(id);
			emptype = humanResourceService.getEmployeeTypesById(storeId,empId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<EmployeeType>() {
		}.getType();
		String json = gson.toJson(emptype, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/getStoreHolidays",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getStoreHolidays(@RequestParam(value = "storeid") String storeid) {
		List<StoreHoliday> holidays = null;
		try {
			holidays = humanResourceService.getStoreHolidays(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<StoreHoliday>>() {
		}.getType();
		String json = gson.toJson(holidays, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addDepartment(@RequestBody Department department) {
		String status = "";
		try {
			status = humanResourceService.addDepartment(department);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updateDepartment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateDepartment(@RequestBody Department department) {
		String status = "";
		try {
			status = humanResourceService.updateDepartment(department);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/deleteDepartment", method = RequestMethod.GET, produces = "text/plain")
	public String deleteDepartment(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = humanResourceService.deleteDepartment(id, storeId);
		} catch (Exception x) {
			message = "Failure";
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/addDesignation", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addDesignation(@RequestBody Designation designation) {
		String status = "";
		try {
			status = humanResourceService.addDesignation(designation);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updateDesignation", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateDesignation(@RequestBody Designation designation) {
		String status = "";
		try {
			status = humanResourceService.updateDesignation(designation);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/deleteDesignation", method = RequestMethod.GET, produces = "text/plain")
	public String deleteDesignation(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = humanResourceService.deleteDesignation(id, storeId);
		} catch (Exception x) {
			message = "Failure";
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/addDutyShift", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addDutyShift(@RequestBody DutyShift dutyShift) {
		String status = "";
		try {
			status = humanResourceService.addDutyShift(dutyShift);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updateDutyShift", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateDutyShift(@RequestBody DutyShift dutyShift) {
		String status = "";
		try {
			status = humanResourceService.updateDutyShift(dutyShift);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/deleteDutyShift", method = RequestMethod.GET, produces = "text/plain")
	public String deleteDutyShift(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = humanResourceService.deleteDutyShift(id, storeId);
		} catch (Exception x) {
			message = "Failure";
			x.printStackTrace();
		}

		return message;
	}

	@Override
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addEmployee(@RequestBody Employee employee) {
		String status = "";
		int empid=0;
		try {
			empid = humanResourceService.addEmployee(employee);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status+empid;
	}

	@Override
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateEmployee(@RequestBody Employee employee) {
		String status = "";
		int empid=0;
		try {
			empid = humanResourceService.updateEmployee(employee);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return status+empid;
	}

	@Override
	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET, produces = "text/plain")
	public String deleteEmployee(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = humanResourceService.deleteEmployee(id, storeId);
		} catch (Exception x) {
			message = "Failure";
			x.printStackTrace();
		}

		return message;
	}
	
	
	@RequestMapping(value = "/uploadEmpImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String uploadEmpImage(
	    @RequestPart("file") MultipartFile  file,
	    @RequestPart("emp") String emp) {
		ObjectMapper objectMapper = new ObjectMapper();
		String status = "";
		try {
			JsonNode mf = objectMapper.readTree(emp);
			String empId=mf.get("id").toString();
			String fileName=mf.get("fileName").toString();
			status = humanResourceService.uploadEmpImage(empId, fileName, file.getInputStream());

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}
	
	@RequestMapping(value = "/uploadEmpDocImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String uploadEmpDocImage(
			@RequestPart("file") MultipartFile  file,
		    @RequestPart("emp") String emp) {
		ObjectMapper objectMapper = new ObjectMapper();
		String status = "";
		try {
			JsonNode mf = objectMapper.readTree(emp);
			String empId=mf.get("id").toString();
			String docName=mf.get("docName").textValue();
			String fileName=mf.get("fileName").toString();
			status = humanResourceService.uploadEmpDocImage(empId,docName ,fileName, file.getInputStream());

		} catch (Exception x) {
			x.printStackTrace();

		}
		return status;

	}
	
	@RequestMapping(value = "/getEmpImage", method = RequestMethod.GET, produces = "text/plain")
	public void getEmpImage(@RequestParam(value = "id") String id,HttpServletRequest request,
			HttpServletResponse response) {
		InputStream is=null;
		try {
			String path = "/home/ubuntu/.resturant/hr";
			String ops = System.getProperty("os.name").toLowerCase();
			if (ops.startsWith("windows")) {
				path = "C:/restaurantImages/hr";
			}
			File file = new File(path + "/"+"emp_" + id + ".png");
			/*InputStream is = new FileInputStream(file);
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
			is.close();*/
			response.flushBuffer();
		    is = new FileInputStream(file);
		    IOUtils.copy(is, response.getOutputStream());
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("file not found exception! emp_"+id+".png");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("error occurred in get emp image by id");
		}
		finally {
		      try {
		        if(is != null) { is.close(); }
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
	}
	
	@RequestMapping(value = "/getEmpDocImage", method = RequestMethod.GET, produces = "text/plain")
	public void getEmpDocImage(@RequestParam(value = "id") String id,HttpServletRequest request,
			HttpServletResponse response) {
		InputStream is=null;
		try {
			String path = "/home/ubuntu/.resturant/hr";
			String ops = System.getProperty("os.name").toLowerCase();
			if (ops.startsWith("windows")) {
				path = "C:/restaurantImages/hr";
			}
			File file = new File(path + "/"+"empdoc_" + id + ".png");
			/*InputStream is = new FileInputStream(file);
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
			is.close();*/
			response.flushBuffer();
		    is = new FileInputStream(file);
		    IOUtils.copy(is, response.getOutputStream());
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("file not found exception! empdoc_"+id+".png");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("error occurred in get emp doc image by id");
		}
		finally {
		      try {
		        if(is != null) { is.close(); }
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
	}

	@Override
	@RequestMapping(value = "/addEmployeeType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addEmployeeType(@RequestBody EmployeeType employeeType) {
		String status = "";
		try {
			status = humanResourceService.addEmployeeType(employeeType);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updateEmployeeType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateEmployeeType(@RequestBody EmployeeType employeeType) {
		String status = "";
		try {
			status = humanResourceService.updateEmployeeType(employeeType);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/deleteEmployeeType",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String deleteEmployeeType(@RequestParam(value = "id") String id,
			@RequestParam(value = "storeId") String storeId) {
		String message = "";
		try {
			message = humanResourceService.deleteEmployeeType(id, storeId);
		} catch (Exception x) {
			message = "Failure";
			x.printStackTrace();
		}

		return message;
	}
	
	@Override
	@RequestMapping(value = "/getAllEmpShiftSchedule",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllEmpShiftSchedule(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		List<EmpShiftSchedule> shiftSchedules = null;
		try {
			shiftSchedules = humanResourceService.getAllEmpShiftSchedule(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmpShiftSchedule>>() {
		}.getType();
		String json = gson.toJson(shiftSchedules, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getEmpShiftScheduleByEmpIdandDate",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmpShiftScheduleByEmpIdandDate(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "empId") String empId,
			@RequestParam(value = "date") String date) {
		List<EmpShiftSchedule> shiftSchedules = null;
		try {
			shiftSchedules = humanResourceService.getEmpShiftScheduleByEmpIdandDate(storeId,empId,date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmpShiftSchedule>>() {
		}.getType();
		String json = gson.toJson(shiftSchedules, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/addEmpShiftSchedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addEmpShiftSchedule(@RequestBody EmpShiftScheduleList empShiftScheduleList) {
		String status = "";
		try {
			status = humanResourceService.addEmpShiftSchedule(empShiftScheduleList);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}
	
	@Override
	@RequestMapping(value = "/updateEmpShiftSchedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateEmpShiftSchedule(@RequestBody EmpShiftScheduleList empShiftScheduleList) {
		String status = "";
		try {
			status = humanResourceService.updateEmpShiftSchedule(empShiftScheduleList);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}
	
	@Override
	@RequestMapping(value = "/cancelEmpShiftSchedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String cancelEmpShiftSchedule(@RequestBody EmpShiftScheduleList empShiftScheduleList) {
		String status = "";
		try {
			status = humanResourceService.cancelEmpShiftSchedule(empShiftScheduleList);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}
	
	@Override
	@RequestMapping(value = "/getAllEmpAttendance",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllEmpAttendance(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		List<EmpAttendance> attns = null;
		try {
			attns = humanResourceService.getAllEmpAttendance(storeId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmpAttendance>>() {
		}.getType();
		String json = gson.toJson(attns, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getEmpAttendanceByEmpIdandDate",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmpAttendanceByEmpIdandDate(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "empId") String empId,
			@RequestParam(value = "date") String date) {
		EmpAttendance attn = null;
		try {
			attn = humanResourceService.getEmpAttendanceByEmpIdandDate(storeId,empId,date);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<EmpAttendance>() {
		}.getType();
		String json = gson.toJson(attn, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/addEmpAttendance", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addEmpAttendance(@RequestBody EmpAttendance empAttn) {
		String status = "";
		try {
			status = humanResourceService.addEmpAttendance(empAttn);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}
	
	@Override
	@RequestMapping(value = "/getEmpCalculationByYear",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getEmpCalculationByYear(@RequestParam(value = "storeId") String storeId,@RequestParam(value = "empId") String empId,
			@RequestParam(value = "fromDate") String fromDate,@RequestParam(value = "toDate") String toDate) {
		List<EmpLeaveCal> leaves = null;
		try {
			leaves = humanResourceService.getEmpCalculationByYear(storeId,empId,fromDate,toDate);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<EmpLeaveCal>>() {
		}.getType();
		String json = gson.toJson(leaves, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getAllChefs",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getAllChefs(@RequestParam(value = "storeid") String storeid) {
		List<Employee> employees = null;
		try {
			employees = humanResourceService.getAllChefs(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Employee>>() {
		}.getType();
		String json = gson.toJson(employees, type);
		//System.out.println(json);
		return json;
	}
	
	public HumanResourceService getHumanResourceService() {
		return humanResourceService;
	}

	public void setHumanResourceService(
			HumanResourceService humanResourceService) {
		this.humanResourceService = humanResourceService;
	}

}

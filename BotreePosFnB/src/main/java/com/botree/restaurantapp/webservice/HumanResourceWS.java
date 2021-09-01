package com.botree.restaurantapp.webservice;

import java.io.InputStream;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.botree.restaurantapp.model.hr.Department;
import com.botree.restaurantapp.model.hr.Designation;
import com.botree.restaurantapp.model.hr.DutyShift;
import com.botree.restaurantapp.model.hr.EmpAttendance;
import com.botree.restaurantapp.model.hr.EmpShiftScheduleList;
import com.botree.restaurantapp.model.hr.Employee;
import com.botree.restaurantapp.model.hr.EmployeeType;

public interface HumanResourceWS {

	public String getAllDepartments(String storeid);

	public String getAllDesignations(String storeid);

	public String getAllDutyShifts(String storeid);
	
	public String getDutyShiftsById(String storeid,String id);

	public String getAllEmployees(String storeid);
	
	public String getEmployeeById(String storeid,String id);

	public String getAllEmployeeHolidays(String storeid);

	public String getEmployeeLeaves(String storeid);

	public String getEmployeeOffDay(String storeid);

	public String getEmployeeTypes(String storeid);
	
	public String getEmployeeTypesById(String storeid,String id);

	public String getStoreHolidays(String storeid);

	public String addDepartment(Department department);

	public String updateDepartment(Department department);

	public String deleteDepartment(String id, String storeId);

	public String addDesignation(Designation designation);

	public String updateDesignation(Designation designation);

	public String deleteDesignation(String id, String storeId);

	public String addDutyShift(DutyShift dutyShift);

	public String updateDutyShift(DutyShift dutyShift);

	public String deleteDutyShift(String id, String storeId);

	public String addEmployee(Employee employee);

	public String updateEmployee(Employee employee);

	public String deleteEmployee(String id, String storeId);
	
	//public String uploadEmpImage(MultipartFile  file,String emp);
	
	//public String uploadEmpDocImage(String empId,String docName, String fileName,InputStream inputStream);

	public String addEmployeeType(EmployeeType employeeType);

	public String updateEmployeeType(EmployeeType employeeType);
	
	public String deleteEmployeeType(String id, String storeId);
	
	public String getAllEmpShiftSchedule(String storeId, String fromDate, String toDate);
	
	public String getEmpShiftScheduleByEmpIdandDate(String storeId, String empId, String date);
	
	public String addEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList);
	
	public String updateEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList);
	
	public String cancelEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList);
	
	public String getAllEmpAttendance(String storeId, String fromDate, String toDate);
	
	public String getEmpAttendanceByEmpIdandDate(String storeId, String empId, String date);
	
	public String addEmpAttendance(EmpAttendance empAttn);
	
	public String getEmpCalculationByYear(String storeId,String empId, String fromDate, String toDate);
	
	public String getAllChefs(String storeid);
}

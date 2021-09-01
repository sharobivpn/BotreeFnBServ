package com.botree.restaurantapp.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.HumanResourceDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
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
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class HumanResourceService {

  @Autowired
	private HumanResourceDAO humanResourceDAO;

	public List<Department> getAllDepartments(String storeId)
			throws ServiceException {
		List<Department> departmnts = null;
		try {

			departmnts = humanResourceDAO.getAllDepartments(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return departmnts;
	}

	public List<Designation> getAllDesignations(String storeId)
			throws ServiceException {
		List<Designation> designations = null;
		try {

			designations = humanResourceDAO.getAllDesignations(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return designations;
	}

	public List<DutyShift> getAllDutyShifts(String storeId)
			throws ServiceException {
		List<DutyShift> shifts = null;
		try {

			shifts = humanResourceDAO.getAllDutyShifts(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return shifts;
	}
	
	public DutyShift getDutyShiftsById(String storeId,String id)
			throws ServiceException {
		DutyShift shift = null;
		try {

			shift = humanResourceDAO.getDutyShiftsById(storeId,id);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return shift;
	}

	public List<Employee> getAllEmployees(String storeId)
			throws ServiceException {
		List<Employee> emps = null;
		try {

			emps = humanResourceDAO.getAllEmployees(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return emps;
	}
	
	public Employee getEmployeeById(String storeId,String id)
			throws ServiceException {
		Employee emp = null;
		try {

			emp = humanResourceDAO.getEmployeeById(storeId,id);

		} catch (DAOException e) {
			throw new ServiceException("hr getEmployeeById error", e);

		}
		return emp;
	}

	public List<Employee> getAllChefs(String storeId) throws ServiceException {
		List<Employee> emps = null;
		try {

			emps = humanResourceDAO.getAllChefs(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr getAllChefs error", e);

		}
		return emps;
	}

	public List<EmployeeHoliday> getAllEmployeeHolidays(String storeId)
			throws ServiceException {
		List<EmployeeHoliday> holidays = null;
		try {

			holidays = humanResourceDAO.getAllEmployeeHolidays(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return holidays;
	}

	public List<StoreHoliday> getStoreHolidays(String storeId)
			throws ServiceException {
		List<StoreHoliday> holidays = null;
		try {

			holidays = humanResourceDAO.getStoreHolidays(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return holidays;
	}

	public List<EmployeeLeave> getEmployeeLeaves(String storeId)
			throws ServiceException {
		List<EmployeeLeave> leaves = null;
		try {

			leaves = humanResourceDAO.getEmployeeLeaves(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return leaves;
	}

	public List<EmployeeOffDay> getEmployeeOffDay(String storeId)
			throws ServiceException {
		List<EmployeeOffDay> offday = null;
		try {
			offday = humanResourceDAO.getEmployeeOffDay(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return offday;
	}

	public List<EmployeeType> getEmployeeTypes(String storeId)
			throws ServiceException {
		List<EmployeeType> types = null;
		try {
			types = humanResourceDAO.getEmployeeTypes(storeId);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return types;
	}
	
	public EmployeeType getEmployeeTypesById(String storeId, int id)
			throws ServiceException {
		EmployeeType type = null;
		try {
			type = humanResourceDAO.getEmployeeTypesById(storeId,id);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return type;
	}

	public String addDepartment(Department department) throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.addDepartment(department);

		} catch (DAOException e) {
			throw new ServiceException("error creating department", e);

		}
		return status;
	}

	public String addDesignation(Designation designation)
			throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.addDesignation(designation);

		} catch (DAOException e) {
			throw new ServiceException("error creating department", e);

		}
		return status;
	}

	public String addDutyShift(DutyShift dutyShift) throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.addDutyShift(dutyShift);

		} catch (DAOException e) {
			throw new ServiceException("error creating duty shift", e);

		}
		return status;
	}

	public int addEmployee(Employee employee) throws ServiceException {
		int empId=0;
		try {
			empId = humanResourceDAO.addEmployee(employee);

		} catch (DAOException e) {
			throw new ServiceException("error creating addEmployee", e);

		}
		return empId;
	}

	public String addEmployeeType(EmployeeType employeeType)
			throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.addEmployeeType(employeeType);

		} catch (DAOException e) {
			throw new ServiceException("error creating employee type", e);

		}
		return status;
	}

	public String updateDepartment(Department department)
			throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.updateDepartment(department);

		} catch (DAOException e) {
			throw new ServiceException("error creating department", e);

		}
		return status;
	}

	public int updateEmployee(Employee employee) throws ServiceException {
		int empid=0;
		try {
			empid = humanResourceDAO.updateEmployee(employee);

		} catch (DAOException e) {
			throw new ServiceException("error updating employee", e);

		}
		return empid;
	}

	public String updateEmployeeType(EmployeeType employeeType)
			throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.updateEmployeeType(employeeType);

		} catch (DAOException e) {
			throw new ServiceException("error updating employee type", e);

		}
		return status;
	}

	public String updateDesignation(Designation designation)
			throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.updateDesignation(designation);

		} catch (DAOException e) {
			throw new ServiceException("error creating designation", e);

		}
		return status;
	}

	public String updateDutyShift(DutyShift dutyShift) throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.updateDutyShift(dutyShift);

		} catch (DAOException e) {
			throw new ServiceException("error creating duty shift", e);

		}
		return status;
	}

	public String deleteDepartment(String id, String storeId)
			throws ServiceException {
		String message = "";
		try {

			message = humanResourceDAO.deleteDepartment(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("department delete error", e);

		}
		return message;
	}

	public String deleteDesignation(String id, String storeId)
			throws ServiceException {
		String message = "";
		try {

			message = humanResourceDAO.deleteDesignation(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("department delete error", e);

		}
		return message;
	}

	public String deleteDutyShift(String id, String storeId)
			throws ServiceException {
		String message = "";
		try {

			message = humanResourceDAO.deleteDutyShift(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("duty shift delete error", e);

		}
		return message;
	}

	public String deleteEmployee(String id, String storeId)
			throws ServiceException {
		String message = "";
		try {

			message = humanResourceDAO.deleteEmployee(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("employee delete error", e);

		}
		return message;
	}
	
	public String uploadEmpImage(String empId, String fileName,InputStream inputStream) throws Exception {
		String status = "";
		try {

			status = humanResourceDAO.uploadEmpImage(empId, fileName, inputStream);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to addMenuItem", e);

		}
		return status;

	}
	
	public String uploadEmpDocImage(String empId,String docName, String fileName,InputStream inputStream) throws Exception {
		String status = "";
		try {

			status = humanResourceDAO.uploadEmpDocImage(empId,docName, fileName, inputStream);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to addMenuItem", e);

		}
		return status;

	}

	public String deleteEmployeeType(String id, String storeId)
			throws ServiceException {
		String message = "";
		try {

			message = humanResourceDAO.deleteEmployeeType(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("employee type delete error", e);

		}
		return message;
	}
	
	public List<EmpShiftSchedule> getAllEmpShiftSchedule(String storeId,String fromDate,String toDate)
			throws ServiceException {
		List<EmpShiftSchedule> shiftschedules = null;
		try {

			shiftschedules = humanResourceDAO.getAllEmpShiftSchedule(storeId,fromDate,toDate);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return shiftschedules;
	}
	
	public List<EmpShiftSchedule> getEmpShiftScheduleByEmpIdandDate(String storeId,String empId,String date)
			throws ServiceException {
		List<EmpShiftSchedule> shiftschedules = null;
		try {

			shiftschedules = humanResourceDAO.getEmpShiftScheduleByEmpIdandDate(storeId,empId,date);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return shiftschedules;
	}
	
	public String addEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList) throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.addEmpShiftSchedule(empShiftScheduleList);

		} catch (DAOException e) {
			throw new ServiceException("error creating shift schedule", e);

		}
		return status;
	}
	public String updateEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList) throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.updateEmpShiftSchedule(empShiftScheduleList);

		} catch (DAOException e) {
			throw new ServiceException("error updating shift schedule", e);

		}
		return status;
	}
	
	public String cancelEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList) throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.cancelEmpShiftSchedule(empShiftScheduleList);

		} catch (DAOException e) {
			throw new ServiceException("error cancelling shift schedule", e);

		}
		return status;
	}
	
	public List<EmpAttendance> getAllEmpAttendance(String storeId,String fromDate,String toDate)
			throws ServiceException {
		List<EmpAttendance> attns = null;
		try {

			attns = humanResourceDAO.getAllEmpAttendance(storeId,fromDate,toDate);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return attns;
	}
	
	public EmpAttendance getEmpAttendanceByEmpIdandDate(String storeId,String empId,String date)
			throws ServiceException {
		EmpAttendance attn = null;
		try {

			attn = humanResourceDAO.getEmpAttendanceByEmpIdandDate(storeId,empId,date);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return attn;
	}
	
	public String addEmpAttendance(EmpAttendance empAttn) throws ServiceException {
		String status = "";
		try {
			status = humanResourceDAO.addEmpAttendance(empAttn);

		} catch (DAOException e) {
			throw new ServiceException("error creating attendance", e);

		}
		return status;
	}
	
	public List<EmpLeaveCal> getEmpCalculationByYear(String storeId,String empId,String fromDate,String toDate)
			throws ServiceException {
		List<EmpLeaveCal> leaves = null;
		try {

			leaves = humanResourceDAO.getEmpCalculationByYear(storeId,empId,fromDate,toDate);

		} catch (DAOException e) {
			throw new ServiceException("hr get error", e);

		}
		return leaves;
	}

	public HumanResourceDAO getHumanResourceDAO() {
		return humanResourceDAO;
	}

	public void setHumanResourceDAO(HumanResourceDAO humanResourceDAO) {
		this.humanResourceDAO = humanResourceDAO;
	}

}

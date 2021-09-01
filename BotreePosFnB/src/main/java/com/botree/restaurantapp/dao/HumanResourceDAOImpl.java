package com.botree.restaurantapp.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.commonUtil.DateUtil;
import com.botree.restaurantapp.commonUtil.StoredProcedures;
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
import com.botree.restaurantapp.model.util.PersistenceListener;

import net.sf.resultsetmapper.ReflectionResultSetMapper;

@Component("humanResourceDAO")
public class HumanResourceDAOImpl implements HumanResourceDAO {
  
	//private final static Logger LOGGER = LogManager.getLogger(HumanResourceDAOImpl.class);

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public List<Department> getAllDepartments(String storeId)
			throws DAOException {

		List<Department> departmnts = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<Department> qry = em
					.createQuery("SELECT d FROM Department d WHERE d.storeId=:storeid and d.deleteFlag='N'",
					    Department.class);
			qry.setParameter("storeid", storeid);
			departmnts = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return departmnts;
	}

	@Override
	public List<Designation> getAllDesignations(String storeId)
			throws DAOException {

		List<Designation> desegnatons = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<Designation> qry = em
					.createQuery("SELECT d FROM Designation d WHERE d.storeId=:storeid and d.deleteFlag='N'",
					    Designation.class);
			qry.setParameter("storeid", storeid);
			desegnatons = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return desegnatons;
	}

	@Override
	public List<DutyShift> getAllDutyShifts(String storeId) throws DAOException {

		List<DutyShift> shifts = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<DutyShift> qry = em
					.createQuery("SELECT s FROM DutyShift s WHERE s.storeId=:storeid and s.deleteFlag='N'",
					    DutyShift.class);
			qry.setParameter("storeid", storeid);
			shifts = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return shifts;
	}
	
	@Override
	public DutyShift getDutyShiftsById(String storeId,String id) throws DAOException {

		DutyShift shifts = null;
		int storeid = Integer.parseInt(storeId);
		int shiftid = Integer.parseInt(id);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<DutyShift> qry = em
					.createQuery("SELECT s FROM DutyShift s WHERE s.storeId=:storeid and s.id=:id and s.deleteFlag='N'",
					    DutyShift.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", shiftid);
			shifts = qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return shifts;
	}

	@Override
	public List<Employee> getAllEmployees(String storeId) throws DAOException {

		List<Employee> emps = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			em = entityManagerFactory.createEntityManager();
			TypedQuery<Employee> qry = em
					.createQuery("SELECT e FROM Employee e WHERE e.storeId=:storeid and e.deleteFlag='N'",
					    Employee.class);
			qry.setParameter("storeid", storeid);
			emps = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return emps;
	}
	
	@Override
	public Employee getEmployeeById(String storeId,String id) throws DAOException {

		Employee emp = null;
		int storeid = Integer.parseInt(storeId);
		int empid = Integer.parseInt(id);
		EntityManager em = null;
		try {
			em = entityManagerFactory.createEntityManager();
			TypedQuery<Employee> qry = em
					.createQuery("SELECT e FROM Employee e WHERE e.storeId=:storeid and e.id=:id and e.deleteFlag='N'",
					    Employee.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", empid);
			emp = qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return emp;
	}
	
	@Override
	public List<Employee> getAllChefs(String storeId) throws DAOException {

		List<Employee> emps = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		int empType=3;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<Employee> qry = em
					.createQuery("SELECT e FROM Employee e WHERE e.storeId=:storeid and e.deleteFlag='N' and e.empTypeId=:empTypeId",
					    Employee.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("empTypeId", empType);
			emps = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return emps;
	}

	@Override
	public List<EmployeeHoliday> getAllEmployeeHolidays(String storeId)
			throws DAOException {

		List<EmployeeHoliday> holidays = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EmployeeHoliday> qry = em
					.createQuery("SELECT e FROM EmployeeHoliday e WHERE e.storeId=:storeid", 
					    EmployeeHoliday.class);
			qry.setParameter("storeid", storeid);
			holidays = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return holidays;
	}

	@Override
	public List<StoreHoliday> getStoreHolidays(String storeId)
			throws DAOException {

		List<StoreHoliday> holidays = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<StoreHoliday> qry = em
					.createQuery("SELECT e FROM StoreHoliday e WHERE e.storeId=:storeid", 
					    StoreHoliday.class);
			qry.setParameter("storeid", storeid);
			holidays = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return holidays;
	}

	@Override
	public List<EmployeeLeave> getEmployeeLeaves(String storeId)
			throws DAOException {

		List<EmployeeLeave> leaves = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EmployeeLeave> qry = em
					.createQuery("SELECT e FROM EmployeeLeave e WHERE e.storeId=:storeid and e.deleteFlag='N'",
					    EmployeeLeave.class);
			qry.setParameter("storeid", storeid);
			leaves = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return leaves;
	}

	@Override
	public List<EmployeeOffDay> getEmployeeOffDay(String storeId)
			throws DAOException {

		List<EmployeeOffDay> offDay = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EmployeeOffDay> qry = em
					.createQuery("SELECT e FROM EmployeeOffDay e WHERE e.storeId=:storeid",
					    EmployeeOffDay.class);
			qry.setParameter("storeid", storeid);
			offDay = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return offDay;
	}

	@Override
	public List<EmployeeType> getEmployeeTypes(String storeId)
			throws DAOException {

		List<EmployeeType> types = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EmployeeType> qry = em
					.createQuery("SELECT e FROM EmployeeType e WHERE e.storeId=:storeid and e.deleteFlag='N'",
					    EmployeeType.class);
			qry.setParameter("storeid", storeid);
			types = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return types;
	}

	@Override
	public EmployeeType getEmployeeTypesById(String storeId, int id)
			throws DAOException {

		EmployeeType types = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EmployeeType> qry = em
					.createQuery("SELECT e FROM EmployeeType e WHERE e.storeId=:storeid and e.id=:id and e.deleteFlag='N'",
					    EmployeeType.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", id);
			types = qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return types;
	}

	@Override
	public String addDepartment(Department department) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			// persist the department
			department.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(department);

			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String updateDepartment(Department department) throws DAOException {
		EntityManager em = null;
		Department dept=null;
		String messaString = "";
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			dept=em.find(Department.class, department.getId());
			dept.setName(department.getName());
			dept.setUpdatedBy(department.getUpdatedBy());
			dept.setUpdatedDate(department.getUpdatedDate());
			// update department
			em.merge(dept);
			em.getTransaction().commit();

			messaString = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String updateDesignation(Designation designation)
			throws DAOException {
		EntityManager em = null;
		Designation desig=null;
		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			desig=em.find(Designation.class, designation.getId());
			desig.setName(designation.getName());
			desig.setUpdatedBy(designation.getUpdatedBy());
			desig.setUpdatedDate(designation.getUpdatedDate());
			// update designation
			em.merge(desig);
			em.getTransaction().commit();

			messaString = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String updateDutyShift(DutyShift dutyShift) throws DAOException {
		EntityManager em = null;
		DutyShift ds=null;
		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			ds=em.find(DutyShift.class, dutyShift.getId());
			ds.setFromTime(dutyShift.getFromTime());
			ds.setToTime(dutyShift.getToTime());
			ds.setShiftingNo(dutyShift.getShiftingNo());
			ds.setShiftName(dutyShift.getShiftName());
			ds.setUpdatedBy(dutyShift.getUpdatedBy());
			ds.setUpdatedDate(dutyShift.getUpdatedDate());
			// update duty shift
			em.merge(ds);
			em.getTransaction().commit();

			messaString = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public int updateEmployee(Employee employee) throws DAOException {
		EntityManager em = null;
		Employee emp=null;
		int empId=0;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			emp=em.find(Employee.class, employee.getId());
			employee.setDeleteFlag("N");
			employee.setCreatedBy(emp.getCreatedBy());
			employee.setCreatedDate(emp.getCreatedDate());
			// update employee
			em.merge(employee);
			em.getTransaction().commit();
			empId=employee.getId();
			//messaString = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			//messaString = "Failure";
			empId=0;
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return empId;
	}

	@Override
	public String updateEmployeeType(EmployeeType employeeType)
			throws DAOException {
		EntityManager em = null;
		EmployeeType emptype=null;
		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			emptype=em.find(EmployeeType.class, employeeType.getId());
			emptype.setType(employeeType.getType());
			emptype.setCasualLeave(employeeType.getCasualLeave());
			emptype.setSickLeave(employeeType.getSickLeave());
			emptype.setMiscLeave(employeeType.getMiscLeave());
			emptype.setUpdatedBy(employeeType.getUpdatedBy());
			emptype.setUpdatedDate(employeeType.getUpdatedDate());
			// update employee type
			em.merge(emptype);
			em.getTransaction().commit();

			messaString = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String deleteDepartment(String id, String storeId)
			throws DAOException {

		int departmntId = Integer.parseInt(id);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		Department department = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Department> qry = em
					.createQuery("SELECT d FROM Department d WHERE d.storeId=:storeid and d.id=:id",
					    Department.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", departmntId);

			department = qry.getSingleResult();
			department.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
		return message;

	}

	@Override
	public String deleteDesignation(String id, String storeId)
			throws DAOException {

		int designationId = Integer.parseInt(id);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		Designation designation = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Designation> qry = em
					.createQuery("SELECT d FROM Designation d WHERE d.storeId=:storeid and d.id=:id",
					    Designation.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", designationId);

			designation = qry.getSingleResult();
			designation.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
		return message;

	}

	@Override
	public String deleteDutyShift(String id, String storeId)
			throws DAOException {

		int shiftId = Integer.parseInt(id);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		DutyShift dutyShift = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<DutyShift> qry = em
					.createQuery("SELECT d FROM DutyShift d WHERE d.storeId=:storeid and d.id=:id",
					    DutyShift.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", shiftId);

			dutyShift = qry.getSingleResult();
			dutyShift.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
		return message;

	}

	@Override
	public String deleteEmployee(String id, String storeId) throws DAOException {

		int empId = Integer.parseInt(id);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		Employee employee = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Employee> qry = em
					.createQuery("SELECT d FROM Employee d WHERE d.storeId=:storeid and d.id=:id",
					    Employee.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", empId);

			employee = qry.getSingleResult();
			employee.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "Failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
		return message;

	}
	
	@Override
	public String uploadEmpImage(String empId, String fileName,
			InputStream inputStream) throws IOException {
		System.out.println("enter uploadEmpImage");
		String status = "";
		// Extract file name from content-disposition header of file part
		if (inputStream != null) {
			// String fileName = getFileName(part);
			System.out.println("***** fileName: " + fileName);

			// String extension=afterDot;
			int empid = Integer.parseInt(empId);
			String changedFileName = "emp_" + empid + "." + "png";
			String basePath = "/home/ubuntu/.resturant/hr";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				basePath = "C:/restaurantImages/hr";
			}
			try {
				createFolderIfNotExists(basePath);
			}catch (SecurityException se) {
				System.out.println("cant create folder"+basePath+" : " + se);
			}
			System.out.println("basePath :" + basePath);
			String fullPath=basePath + "/" + changedFileName;
			File outputFilePath = new File(fullPath);
			OutputStream outputStream = null;
			try {
				// inputStream = part.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);

				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				outputStream.flush();
				status = "Success";
			} catch (IOException e) {
				e.printStackTrace();
				status = "Failure";
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return status; // return to same page
	}
	
	@Override
	public String uploadEmpDocImage(String empId,String docName, String fileName,
			InputStream inputStream) throws IOException {
		System.out.println("enter uploadEmpDocImage");
		String status = "";
		EntityManager em = null;
		Employee employee = null;
		// Extract file name from content-disposition header of file part
		if (inputStream != null) {
			// String fileName = getFileName(part);
			System.out.println("***** fileName: " + fileName);

			// String extension=afterDot;
			int empid = Integer.parseInt(empId);
			String changedFileName = "empdoc_" + empid + "." + "png";
			String basePath = "/home/ubuntu/.resturant/hr";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				basePath = "C:/restaurantImages/hr";
			}
			try {
				createFolderIfNotExists(basePath);
			}catch (SecurityException se) {
				System.out.println("cant create folder"+basePath+" : " + se);
			}
			System.out.println("basePath :" + basePath);
			String fullPath=basePath + "/" + changedFileName;
			File outputFilePath = new File(fullPath);
			OutputStream outputStream = null;
			try {
				// inputStream = part.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);

				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				outputStream.flush();
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				employee=em.find(Employee.class, Integer.parseInt(empId));
				employee.setIdProofName(docName);
				em.getTransaction().commit();
				status = "Success";
				
			} catch (IOException e) {
				e.printStackTrace();
				status = "Failure";
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return status; // return to same page
	}
	
	 private void createFolderIfNotExists(String dirName)
	            throws SecurityException {
	        File theDir = new File(dirName);
	        if (!theDir.exists()) {
	            theDir.mkdir();
	        }
	}

	@Override
	public String deleteEmployeeType(String id, String storeId)
			throws DAOException {

		int empTypeId = Integer.parseInt(id);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		EmployeeType employeeType = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<EmployeeType> qry = em
					.createQuery("SELECT d FROM EmployeeType d WHERE d.storeId=:storeid and d.id=:id",
					    EmployeeType.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", empTypeId);

			employeeType = qry.getSingleResult();
			employeeType.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
		return message;

	}

	@Override
	public String addDesignation(Designation designation) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			// persist the department
			designation.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(designation);

			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String addDutyShift(DutyShift dutyShift) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			// persist the duty shift
			dutyShift.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(dutyShift);

			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public int addEmployee(Employee employee) throws DAOException {
		EntityManager em = null;
		int empId=0;
		try {
			// persist the employee
			employee.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(employee);

			em.getTransaction().commit();
			//status = "Success";
			empId=employee.getId();
		} catch (Exception e) {
			e.printStackTrace();
			//status = "Failure";
			empId=0;
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return empId;
	}

	@Override
	public String addEmployeeType(EmployeeType employeeType)
			throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			// persist the employee type
			employeeType.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(employeeType);

			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public List<EmpShiftSchedule> getAllEmpShiftSchedule(String storeId,String fromDate,String toDate) throws DAOException {

		List<EmpShiftSchedule> shiftSchedules = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			Date fromdate=DateUtil.StringDateTojavaDate(fromDate, "yyyy-MM-dd");
			Date todate=DateUtil.StringDateTojavaDate(toDate, "yyyy-MM-dd");
			em = entityManagerFactory.createEntityManager();
			TypedQuery<EmpShiftSchedule> qry = em
					.createQuery("SELECT ess FROM EmpShiftSchedule ess WHERE ess.storeId=:storeid and ess.fromDate between :fromDate and :toDate and ess.isCanceled=0 order by ess.fromDate",
							EmpShiftSchedule.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("fromDate", fromdate);
			qry.setParameter("toDate", todate);
			shiftSchedules = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return shiftSchedules;
	}
	
	@Override
	public List<EmpShiftSchedule> getEmpShiftScheduleByEmpIdandDate(String storeId,String empId,String date) throws DAOException {

		List<EmpShiftSchedule> shiftSchedules = null;
		int storeid = Integer.parseInt(storeId);
		int empid = Integer.parseInt(empId);
		EntityManager em = null;
		try {
			Date dt=DateUtil.StringDateTojavaDate(date, "yyyy-MM-dd");
			em = entityManagerFactory.createEntityManager();
			TypedQuery<EmpShiftSchedule> qry = em
					.createQuery("SELECT ess FROM EmpShiftSchedule ess WHERE ess.storeId=:storeid and ess.employee.id=:empid and ess.fromDate=:date and ess.isCanceled=0",
							EmpShiftSchedule.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("empid", empid);
			qry.setParameter("date", dt);
			shiftSchedules = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return shiftSchedules;
	}
	
	@Override
	public String addEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			List<EmpShiftSchedule> shiftscheduleList=empShiftScheduleList.getEmpshiftscheduleList();
			Iterator<EmpShiftSchedule> iterator=shiftscheduleList.iterator();
			while(iterator.hasNext())
			{
				EmpShiftSchedule shiftSchedule=(EmpShiftSchedule)iterator.next();
				try {
				TypedQuery<EmpShiftSchedule> qry = em
						.createQuery("SELECT ess FROM EmpShiftSchedule ess WHERE ess.storeId=:storeid and ess.employee.id=:empid and ess.fromDate=:date and ess.isCanceled=0",
								EmpShiftSchedule.class);
				qry.setParameter("storeid", shiftSchedule.getStoreId());
				qry.setParameter("empid", shiftSchedule.getEmployee().getId());
				qry.setParameter("date", shiftSchedule.getFromDate());
				qry.getSingleResult();
				}catch(Exception ex)
				{
					em.persist(shiftSchedule);
				}
				
				
			}
			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public String cancelEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			List<EmpShiftSchedule> shiftscheduleList=empShiftScheduleList.getEmpshiftscheduleList();
			Iterator<EmpShiftSchedule> iterator=shiftscheduleList.iterator();
			while(iterator.hasNext())
			{
				EmpShiftSchedule shiftSchedule=(EmpShiftSchedule)iterator.next();
				EmpShiftSchedule ss=em.find(EmpShiftSchedule.class, shiftSchedule.getId());
				ss.setIsCanceled(1);
				em.merge(ss);
			}
			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public String updateEmpShiftSchedule(EmpShiftScheduleList empShiftScheduleList) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			List<EmpShiftSchedule> shiftscheduleList=empShiftScheduleList.getEmpshiftscheduleList();
			Iterator<EmpShiftSchedule> iterator=shiftscheduleList.iterator();
			while(iterator.hasNext())
			{
				EmpShiftSchedule shiftSchedule=(EmpShiftSchedule)iterator.next();
				em.merge(shiftSchedule);
			}
			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public List<EmpAttendance> getAllEmpAttendance(String storeId,String fromDate,String toDate) throws DAOException {

		List<EmpAttendance> attns = null;
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		try {
			Date fromdate=DateUtil.StringDateTojavaDate(fromDate, "yyyy-MM-dd");
			Date todate=DateUtil.StringDateTojavaDate(toDate, "yyyy-MM-dd");
			em = entityManagerFactory.createEntityManager();
			TypedQuery<EmpAttendance> qry = em
					.createQuery("SELECT attn FROM EmpAttendance attn WHERE attn.storeId=:storeid and attn.dateIn between :fromDate and :toDate order by attn.dateIn",
							EmpAttendance.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("fromDate", fromdate);
			qry.setParameter("toDate", todate);
			attns = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return attns;
	}
	
	@Override
	public EmpAttendance getEmpAttendanceByEmpIdandDate(String storeId,String empId,String date) throws DAOException {

		EmpAttendance attn = null;
		int storeid = Integer.parseInt(storeId);
		int empid = Integer.parseInt(empId);
		EntityManager em = null;
		try {
			Date dt=DateUtil.StringDateTojavaDate(date, "yyyy-MM-dd");
			em = entityManagerFactory.createEntityManager();
			TypedQuery<EmpAttendance> qry = em
					.createQuery("SELECT attn FROM EmpAttendance attn WHERE attn.storeId=:storeid and attn.employee.id=:empid and attn.dateIn=:date",
							EmpAttendance.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("empid", empid);
			qry.setParameter("date", dt);
			attn = qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return attn;
	}
	
	@Override
	public String addEmpAttendance(EmpAttendance empAttn) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(empAttn);
			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public List<EmpLeaveCal> getEmpCalculationByYear(String storeId,String empId,String fromDate,String toDate) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<EmpLeaveCal> leaves=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_EMP_LEAVE_CAL_MONTHLY);
			callableStatement.setInt(1, Integer.parseInt(storeId));
			callableStatement.setInt(2, Integer.parseInt(empId));
			callableStatement.setString(3, null);
			callableStatement.setString(4, null);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			/*ReflectionResultSetMapper<EmpLeaveCal> resultSetMapper1 = new ReflectionResultSetMapper<EmpLeaveCal>(
					EmpLeaveCal.class);*/

			while (rs.next()) {
				EmpLeaveCal leave=new EmpLeaveCal();
				leave.setEmpId(rs.getInt(1));
				leave.setEmpName(rs.getString(2));
				leave.setJanLeave(rs.getDouble(3));
				leave.setFebLeave(rs.getDouble(4));
				leave.setMarLeave(rs.getDouble(5));
				leave.setAprLeave(rs.getDouble(6));
				leave.setMayLeave(rs.getDouble(7));
				leave.setJunLeave(rs.getDouble(8));
				leave.setJulLeave(rs.getDouble(9));
				leave.setAugLeave(rs.getDouble(10));
				leave.setSepLeave(rs.getDouble(11));
				leave.setOctLeave(rs.getDouble(12));
				leave.setNovLeave(rs.getDouble(13));
				leave.setDecLeave(rs.getDouble(14));
				leave.setTotalLeave(rs.getDouble(15));
				leave.setAllotedLeave(rs.getDouble(16));
				leave.setLeaveBalance(rs.getDouble(17));
				//leave = resultSetMapper1.mapRow(rs);
				leaves.add(leave);

			}

			//logger.info("accounts fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		//System.out.println("auto accounts = "+accnts);
		return leaves;
	}

}

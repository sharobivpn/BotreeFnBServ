/**
 * 
 */
package com.botree.restaurantapp.model.hr;

import java.io.Serializable;
import java.util.List;

/**
 * @author Habib
 *
 */
public class EmpShiftScheduleList implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<EmpShiftSchedule> empshiftscheduleList;

	public List<EmpShiftSchedule> getEmpshiftscheduleList() {
		return empshiftscheduleList;
	}

	public void setEmpshiftscheduleList(List<EmpShiftSchedule> empshiftscheduleList) {
		this.empshiftscheduleList = empshiftscheduleList;
	}
	
	
	

}

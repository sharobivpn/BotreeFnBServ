package com.botree.restaurantapp.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dateformatter {
	Date convertedCurrentDate = null;
	String dateformat = null;
	Date date1 = null;

	public String dateFormat(String date) {

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = new Date();
		try {
			date1 = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formated = format1.format(date1);
		return formated;

	}

	public String  changeFormat(String date,String startTime,int workingHours){
		
		//String replaced;
		//String startTime="6:00:00";
		String finalStartDateTime;
		String finalEndDateTime;
		String nextdate;
		//String EndDateTime;
		
		//String formatDate=dateFormat(date);
		finalStartDateTime = date +" "+ startTime;
		System.out.println("Start Time: "+ finalStartDateTime);
		
		 
		 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar today = Calendar.getInstance();
		 Date date1 = new Date();
		 try {
			date1=format1.parse(finalStartDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 today.setTime(date1);
		 today.add(Calendar.HOUR_OF_DAY,workingHours);
		 nextdate = format1.format(today.getTime());
	     finalEndDateTime=nextdate;
		 System.out.println("Start Time: "+ finalStartDateTime);
		 
		 return finalEndDateTime;
	}
	
public String  getStartDateTime(String date,String startTime){
		
		//String replaced;
		String finalStartDateTime;
		//SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		
		//String formatDate=dateFormat(date);
		finalStartDateTime = date +" "+ startTime;
		System.out.println("Start Time: "+ finalStartDateTime);
		
		return finalStartDateTime;
	}
}

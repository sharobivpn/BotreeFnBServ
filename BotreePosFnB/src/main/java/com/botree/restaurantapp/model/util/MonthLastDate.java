package com.botree.restaurantapp.model.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MonthLastDate {
	
	private static MonthLastDate instance=null;
	private MonthLastDate(){
		
	}
	
	  public static MonthLastDate getInstance(){
		    if(instance == null){
		        synchronized (MonthLastDate.class) {
		            if(instance == null){
		                instance = new MonthLastDate();
		            }
		        }
		    }
		    return instance;
		}
	
	public  String getDate(int month, int year) {
	    Calendar calendar = Calendar.getInstance();
	    // passing month-1 because 0-->jan, 1-->feb... 
	    calendar.set(year, month -1 ,1);
	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    Date date = calendar.getTime();
	    DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	    return DATE_FORMAT.format(date);
	}
		
	
	public String getmonthName(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}

}

package com.botree.restaurantapp.commonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private final static String DATE_FORMAT_SHORT = "dd-MM-yyyy";
	private final static String DATE_FORMAT_FULL = "dd-MM-yyyy hh:mm:ss";

	private final static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);
	private final static SimpleDateFormat sdfFull = new SimpleDateFormat(DATE_FORMAT_FULL);

	public static Date getDate(String date) throws ParseException {
		return sdf.parse(date);
	}

	public static Date getDateFull(String date) throws ParseException {
		return sdfFull.parse(date);
	}
	
	public static String getStringDate(Date date){
		return sdf.format(date);
	}
	
	public static String getStringDateFull(Date date){
		return sdfFull.format(date);
	}
}

/**
 * 
 */
package com.botree.restaurantapp.commonUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author habib
 * 
 */
public class DateUtil {
	private final static String DATE_FORMAT_SHORT = "yyyy-MM-dd";
	private final static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);

	public static String getCurrentDateString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	};

	public static Date getCurrentDate() {
		return (new Date());
	};

	public static int getDateDiffDays(Date dateto, Date datefrom) {
		return (int) ((dateto.getTime() - datefrom.getTime()) / (24 * 60 * 60 * 1000));
	}

	public static Long getDateDiffSeconds(Date dateto, Date datefrom) {
		return ((dateto.getTime() - datefrom.getTime()) / (1000));
	}

	public static Long getNowInMilliSec() {
		return (new Date()).getTime();
	}

	public static boolean validateDate(String dateStr, boolean allowPast,
			String formatStr) {
		if (formatStr == null) {
			formatStr = "yyyy-MM-dd";
		}

		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Date testDate = null;
		try {
			testDate = df.parse(dateStr);
		} catch (Exception e) {
			// invalid date format
			return false;
		}
		if (!allowPast) {
			// initialise the calendar to midnight to prevent
			// the current day from being rejected
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			if (cal.getTime().after(testDate))
				return false;
		}
		// now test for legal values of parameters
		if (!df.format(testDate).equals(dateStr)) {
			return false;
		}
		return true;
	}

	public static boolean validateDate(String dateStr, String formatStr) {
		return validateDate(dateStr, true, formatStr);
	}

	public static String convertDateString(String dateStr, String fromFormat,
			String toFormat) {
		try {
			SimpleDateFormat fdf = new SimpleDateFormat(fromFormat);
			SimpleDateFormat tdf = new SimpleDateFormat(toFormat);
			Date testDate = null;
			testDate = fdf.parse(dateStr);
			String outDateString = tdf.format(testDate);

			return outDateString;

		} catch (Exception e) {
			return dateStr;

		}

	}

	public static Date StringDateTojavaDate(String dateStr, String format)
			throws Exception {

		if (dateStr == null) {
			throw new IllegalArgumentException("date string cant be null");
		}
		SimpleDateFormat sdFrom = new SimpleDateFormat(format);
		Date fromatDate = sdFrom.parse(dateStr.trim());
		sdFrom.applyPattern(format);
		// Timestamp st = new Timestamp(fromatDate.getTime());

		return fromatDate;

	}

	public static int getDateDiffDays(String fromDate, String toDate,
			String dateFormat) {

		SimpleDateFormat sdFrom = new SimpleDateFormat(dateFormat);

		Date frd = null;
		try {
			frd = sdFrom.parse(fromDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdFrom.applyPattern(dateFormat);

		Date toDa = null;
		try {
			toDa = sdFrom.parse(toDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdFrom.applyPattern(toDate);

		return getDateDiffDays(toDa, frd);
	}

	public static Date convertStringDate(String date, String format) {

		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date convertDate = sdf.parse(date);
			sdf.applyPattern(format);
			return convertDate;
		} catch (ParseException ex) {
			ex.printStackTrace();

		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static java.sql.Date convertStringDateToSqlDate(String date,
			String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.sql.Date sqlDate = null;
		try {
			sqlDate = new java.sql.Date(sdf.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sqlDate;
	}

	public static Date convertCurrentDateToFormattedDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		Date formattedDate=null;
		try {
			formattedDate = dateFormat.parse(dateFormat.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	public static String convertReceivedDateToNeededDateStr(String dateFormatReceived, String dateFormatNeeded, String date) {
		String convertdDateStr=null;
		try {
			SimpleDateFormat dateFormatReceivedFrmt = new SimpleDateFormat(dateFormatReceived);
			
			SimpleDateFormat dateFormatNeededFrmt = new SimpleDateFormat(dateFormatNeeded);
			Date date1=dateFormatReceivedFrmt.parse(date);
			convertdDateStr=dateFormatNeededFrmt.format(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertdDateStr;
	}
	
	public static String getStringDate(Date date){
		return sdf.format(date);
	}
	
	public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}
	
	public static java.util.Date getNextDate(int noOfDays) {
		
		Date m = new Date();
	    Calendar cal = Calendar.getInstance();  
	    cal.setTime(m);  
	    cal.add(Calendar.DATE, noOfDays); 
	    m = cal.getTime(); 
	    return m;
	}
	
	
	

	public static void main(String args[]) {
		try {

			System.out.println(convertStringDateToSqlDate("2016-11-10",
					"yyyy-MM-dd"));
			System.out.println("util to sql::"+convertJavaDateToSqlDate(new Date()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

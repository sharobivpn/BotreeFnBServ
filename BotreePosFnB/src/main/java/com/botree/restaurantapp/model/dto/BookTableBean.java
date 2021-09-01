package com.botree.restaurantapp.model.dto;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.commonUtil.DateUtils;

public class BookTableBean {
	private final static Logger logger = LogManager.getLogger(BookTableBean.class);
	// value represents minutes.
	private final static int DEFAULT_BOOKING_DURATION = 60;

	private Date bookingDateObjFrom;
	private Date bookingDateObjTo;

	private BookTableFormBean btFormBean = null;

	public BookTableBean(BookTableFormBean btFormBean) throws ParseException {
		this.btFormBean = btFormBean;
		this.prepareBeanForService(btFormBean.getBookingDate(), btFormBean.getHour(), btFormBean.getMinute(), btFormBean.getPeriod());
	}

	private void prepareBeanForService(	String bookingDate,
										int hour,
										int minute,
										int period) throws ParseException {
		Calendar cal = Calendar.getInstance();

		cal.setTime(DateUtils.getDate(bookingDate));
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.AM_PM, period);

		bookingDateObjFrom = cal.getTime();
		logger.debug("From time: {}", bookingDateObjFrom.toString());

		cal.add(Calendar.MINUTE, DEFAULT_BOOKING_DURATION);
		bookingDateObjTo = cal.getTime();
		logger.debug("To time: {}", bookingDateObjTo.toString());
	}

	public int getSeat() {
		return btFormBean.getSeats();
	}

	public Date getBookingDateObjFrom() {
		return bookingDateObjFrom;
	}

	public Date getBookingDateObjTo() {
		return bookingDateObjTo;
	}
}

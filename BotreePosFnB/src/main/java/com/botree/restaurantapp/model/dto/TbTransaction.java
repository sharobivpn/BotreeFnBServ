package com.botree.restaurantapp.model.dto;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_transaction")
public class TbTransaction {

	@Id
	@Column(name = "id_pk")
	int id;

	@Column(name = "user_id")
	int userId;

	@Column(name = "table_id")
	int tableId;

	@Column(name = "booking_date")
	Date bookingDate;

	@Column(name = "booking_time_from")
	Time bookingTimeFrom;

	@Column(name = "booking_time_to")
	Time bookingTimeTo;

	@Column(name = "transaction_timestamp")
	Timestamp transactionTimestamp;

	@Column(name = "is_available")
	boolean isAvailable;

	@Column(name = "is_locked")
	boolean isLocked;

	@Column(name = "is_confirmed")
	boolean isConfirmed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Time getBookingTimeFrom() {
		return bookingTimeFrom;
	}

	public void setBookingTimeFrom(Time bookingTimeFrom) {
		this.bookingTimeFrom = bookingTimeFrom;
	}

	public Time getBookingTimeTo() {
		return bookingTimeTo;
	}

	public void setBookingTimeTo(Time bookingTimeTo) {
		this.bookingTimeTo = bookingTimeTo;
	}

	public Timestamp getTransactionTimestamp() {
		return transactionTimestamp;
	}

	public void setTransactionTimestamp(Timestamp transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
}

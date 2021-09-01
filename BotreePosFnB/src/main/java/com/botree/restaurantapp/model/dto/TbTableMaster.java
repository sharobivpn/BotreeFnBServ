package com.botree.restaurantapp.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_table_master")
public class TbTableMaster {
	@Id
	@Column(name = "id_pk")
	int id;

	@Column(name = "table_no")
	String tableNo;

	@Column(name = "table_description")
	String tableDescription;

	@Column(name = "seating_capacity")
	int seatingCapacity;

	@Column(name = "available_for_online_booking")
	boolean availableForBooking;

	@Column(name = "store_id")
	int storeId;

	@Column(name = "style_id")
	String styleId = "1";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public String getTableDescription() {
		return tableDescription;
	}

	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public boolean isAvailableForBooking() {
		return availableForBooking;
	}

	public void setAvailableForBooking(boolean availableForBooking) {
		this.availableForBooking = availableForBooking;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	@Override
	public String toString() {
		return this.getId() + " # " + this.getTableNo() + " # " + this.getTableDescription() + " # " + this.getSeatingCapacity() + " # " + this.getStyleId();
	}
}

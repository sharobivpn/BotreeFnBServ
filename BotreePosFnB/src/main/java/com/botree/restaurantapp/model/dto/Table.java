package com.botree.restaurantapp.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class Table {
	@Expose private int tableId;
	@Expose private String tableNo;
	@Expose private String tableDescription;
	@Expose private int seatingCapacity;
	@Expose private String status;
	@Expose private short availableForOnlineBbooking;
	@Expose private String deleteFlag;
	@Expose private String styleId;
	@Expose private String multiOrder;
	@Expose private int xPos;
	@Expose private int yPos;
	@Expose private int section;
	@Expose private int storeId;

	public Table(int tableId, String tableNo, String tableDescription,
			int seatingCapacity) {
		this.tableId = tableId;
		this.tableNo = tableNo;
		this.tableDescription = tableDescription;
		this.seatingCapacity = seatingCapacity;
	}

	public Table(int tableId, String tableNo, String tableDescription,
			int seatingCapacity, String status,
			short availableForOnlineBbooking, String deleteFlag,
			String styleId, String multiOrder, int xPos, int yPos, int section,
			int storeId) {
		this.tableId = tableId;
		this.tableNo = tableNo;
		this.tableDescription = tableDescription;
		this.seatingCapacity = seatingCapacity;
		this.status = status;
		this.availableForOnlineBbooking = availableForOnlineBbooking;
		this.deleteFlag = deleteFlag;
		this.styleId = styleId;
		this.multiOrder = multiOrder;
		this.xPos = xPos;
		this.yPos = yPos;
		this.section = section;
		this.storeId = storeId;

	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public short getAvailableForOnlineBbooking() {
		return availableForOnlineBbooking;
	}

	public void setAvailableForOnlineBbooking(short availableForOnlineBbooking) {
		this.availableForOnlineBbooking = availableForOnlineBbooking;
	}

	public String getMultiOrder() {
		return multiOrder;
	}

	public void setMultiOrder(String multiOrder) {
		this.multiOrder = multiOrder;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

}

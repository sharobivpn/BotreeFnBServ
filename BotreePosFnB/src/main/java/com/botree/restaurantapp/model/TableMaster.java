package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: tb_table_master
 * 
 */
@XmlRootElement
@Entity
@Table(name = "tb_table_master")
public class TableMaster implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pk")
	private int id;

	@Expose
	@Column(name = "table_no")
	private String tableNo;

	@Expose
	@Column(name = "table_description")
	private String tableDescription;

	@Expose
	@Column(name = "seating_capacity")
	private int seatingCapacity;

	@Expose
	@Column(name = "available_for_online_booking")
	private Short availableForOnlineBbooking;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "style_id")
	private String styleId;

	@Expose
	@Column(name = "status")
	private String status;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "multi_order")
	private String multiOrder;

	@Expose
	@Column(name = "x_pos")
	private int xPos;

	@Expose
	@Column(name = "y_pos")
	private int yPos;

	@Expose
	@Column(name = "section")
	private int section;

	@Expose
	@Transient
	private List<TableMaster> tableList;

	private static final long serialVersionUID = 1L;

	public List<TableMaster> getTableList() {
		return tableList;
	}

	public void setTableList(List<TableMaster> tableList) {
		this.tableList = tableList;
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

	public Short getAvailableForOnlineBbooking() {
		return availableForOnlineBbooking;
	}

	public void setAvailableForOnlineBbooking(Short availableForOnlineBbooking) {
		this.availableForOnlineBbooking = availableForOnlineBbooking;
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

	public String getMultiOrder() {
		return multiOrder;
	}

	public void setMultiOrder(String multiOrder) {
		this.multiOrder = multiOrder;
	}

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "TableMaster [id=" + id + ", tableNo=" + tableNo + ", tableDescription=" + tableDescription
        + ", seatingCapacity=" + seatingCapacity + ", availableForOnlineBbooking=" + availableForOnlineBbooking
        + ", storeId=" + storeId + ", styleId=" + styleId + ", status=" + status + ", deleteFlag=" + deleteFlag
        + ", multiOrder=" + multiOrder + ", xPos=" + xPos + ", yPos=" + yPos + ", section=" + section + ", tableList="
        + tableList + "]";
  }

	
}
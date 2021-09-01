package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: fo_t_orders
 * 
 */
@XmlRootElement
@Entity
@Table(name = "fo_t_orders")
@ManagedBean(name = "order")
public class OrderMaster implements Serializable {
	
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Expose
	@Column(name = "customer_name")
	private String customerName;

	@Expose
	@Column(name = "customer_contact")
	private String customerContact;
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_time")
	private Date fromTime;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_time")
	private Date toTime;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "delivery_time")
	private Date deliveryTime;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date")
	private Date orderDate;

	@Expose
	@Column(name = "delivery_address")
	private String deliveryAddress;

	@Expose
	@ManyToOne
	@JoinColumn(name = "payment_type_Id")
	private PaymentType paymentType;

	@Expose
	@ManyToOne
	@JoinColumn(name = "Order_type_Id")
	private OrderType ordertype;

	@Expose
	@ManyToOne
	@JoinColumn(name = "Person_Id")
	private Customer customers;

	@ManyToOne
	@JoinColumn(name = "coupon_Id")
	private Coupon coupon;

	@ManyToOne
	@JoinColumn(name = "reward_point_Id")
	private RewardPoint reward;

	@ManyToOne
	@JoinColumn(name = "offer_Id")
	private Offer offer;

	@Expose
	@Column(name = "Flag")
	private String flag;

	/*@OneToMany(mappedBy = "orders")
	private List<Feedback> feedback;*/

	@Expose
	@OneToMany(mappedBy = "orders", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Where(clause = "quantityof_item>0")
	private List<OrderItem> orderitem;

	@Expose
	@OneToOne(mappedBy = "orderbill")
	private Bill orderBill;

	/*
	 * @Expose
	 * 
	 * @OneToOne(mappedBy = "orderPayment") private Payment orderPayment;
	 */
	@Expose
	@Column(name = "order_no")
	private String orderNo;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "table_no")
	private String table_no;

	@Expose
	@Column(name = "seat_no")
	private String seatNo;

	@Expose
	@Column(name = "distance")
	private Double distance;

	@Transient
	private OrderItem orderItem;

	@Transient
	private OrderItem orderItem1;

	@Transient
	private OrderItem orderItem2;

	@Transient
	private OrderItem orderItem3;

	@Transient
	private OrderItem orderItem4;

	@Transient
	private OrderItem orderItem5;

	@Transient
	private List<OrderMaster> orderList;

	@Expose
	@Column(name = "special_note")
	private String specialNote;

	@Expose
	@Column(name = "order_time")
	private String orderTime;

	@Expose
	@Column(name = "bill_req_status")
	private String billReqStatus;

	@Expose
	@Column(name = "bill_req_time")
	private String billReqTime;

	@Expose
	@Column(name = "change_note")
	private String changeNote = "-";

	@Expose
	@Column(name = "cancel")
	private String cancel = "N";

	@Expose
	@Column(name = "child_tables")
	private String childTables = "";

	@Expose
	@Column(name = "source")
	private String source;

	@Transient
	private List<ItemsTableWise> itemsTableWises;

	@Expose
	@Column(name = "no_of_persons")
	private int noOfPersons = 1;

	@Expose
	@Column(name = "split_bill")
	private String splitBill;

	@Transient
	private List<BillSplitER> billSplitERList;

	@Expose
	@Column(name = "time")
	private String time;

	@Transient
	private List<BillSplitManual> billSplitManualList;

	@Transient
	private int billSplitERListSize;

	@Transient
	private int billSplitManualListSize;

	@Expose
	@Column(name = "cancel_remark")
	private String cancelRemark;

	@Expose
	@Column(name = "kot_print_status")
	private String kotPrintStatus;

	@Expose
	@Column(name = "bill_print_count")
	private int billPrintcount;

	@Expose
	@Column(name = "kot_print_count")
	private int kotPrintCount;

	@Expose
	@Column(name = "printer_id")
	private int printerId;

	@Expose
	@Column(name = "delivery_person_name")
	private String deliveryPersonName;

	@Expose
	@Column(name = "store_customer_id")
	private int storeCustomerId;

	@Expose
	@Column(name = "credit_flag")
	private String creditFlag;

	@Expose
	@Column(name = "direct_cat")
	private int directCat;
	
	@Expose
	@Transient
	private List<Payment> payments;
	
	@Expose
	@Column(name = "bill_print_reason")
	private String billPrintReason;
	
	@Expose
	@Column(name = "cust_vat_reg_no")
	private String custVatRegNo;
	
	
	@Expose
	@Column(name = "location")
	private String location;
	
	@Expose
	@Column(name = "house_no")
	private String houseNo;
	
	@Expose
	@Column(name = "street")
	private String street;
	
	 @Expose
	 @Column(name = "car_no")
	 private String car_no;

	 @Expose
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "anniversary_date")
	 private Date anniversary;

	 @Expose
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "dob ")
	 private Date dob;

	 @Expose
	 @Column(name = "state")
	 private String state;
	 
	 @Expose
	 @Column(name = "waiterName")
	 private String waiterName;
	 
	 //added on 13.06.2018
	 @Expose
	 @Column(name = "refund_status")
	 private String refundStatus= "N";
	 
	 //added on 03.04.2019
	 @Expose
	 @Column(name = "remarks")
	 private String remarks;
	 
	//added on 05.04.2019
	@Transient
	private int storecustmerServerId;
	@Transient
	private String isSync="N";
	 
	private static final long serialVersionUID = 1L;
	
	
	
	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCustVatRegNo() {
		return custVatRegNo;
	}

	public void setCustVatRegNo(String custVatRegNo) {
		this.custVatRegNo = custVatRegNo;
	}

	public String getBillPrintReason() {
		return billPrintReason;
	}

	public void setBillPrintReason(String billPrintReason) {
		this.billPrintReason = billPrintReason;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public int getDirectCat() {
		return directCat;
	}

	public void setDirectCat(int directCat) {
		this.directCat = directCat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public OrderType getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(OrderType ordertype) {
		this.ordertype = ordertype;
	}

	public Customer getCustomers() {
		return customers;
	}

	public void setCustomers(Customer customers) {
		this.customers = customers;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public RewardPoint getReward() {
		return reward;
	}

	public void setReward(RewardPoint reward) {
		this.reward = reward;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/*public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}*/

	public List<OrderItem> getOrderitem() {
		return orderitem;
	}

	public void setOrderitem(List<OrderItem> orderitem) {
		this.orderitem = orderitem;
	}

	public Bill getOrderBill() {
		return orderBill;
	}

	public void setOrderBill(Bill orderBill) {
		this.orderBill = orderBill;
	}

	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getTable_no() {
		return table_no;
	}

	public void setTable_no(String table_no) {
		this.table_no = table_no;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public OrderItem getOrderItem1() {
		return orderItem1;
	}

	public void setOrderItem1(OrderItem orderItem1) {
		this.orderItem1 = orderItem1;
	}

	public OrderItem getOrderItem2() {
		return orderItem2;
	}

	public void setOrderItem2(OrderItem orderItem2) {
		this.orderItem2 = orderItem2;
	}

	public OrderItem getOrderItem3() {
		return orderItem3;
	}

	public void setOrderItem3(OrderItem orderItem3) {
		this.orderItem3 = orderItem3;
	}

	public OrderItem getOrderItem4() {
		return orderItem4;
	}

	public void setOrderItem4(OrderItem orderItem4) {
		this.orderItem4 = orderItem4;
	}

	public OrderItem getOrderItem5() {
		return orderItem5;
	}

	public void setOrderItem5(OrderItem orderItem5) {
		this.orderItem5 = orderItem5;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public List<OrderMaster> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderMaster> orderList) {
		this.orderList = orderList;
	}

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getBillReqStatus() {
		return billReqStatus;
	}

	public void setBillReqStatus(String billReqStatus) {
		this.billReqStatus = billReqStatus;
	}

	public String getBillReqTime() {
		return billReqTime;
	}

	public void setBillReqTime(String billReqTime) {
		this.billReqTime = billReqTime;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getChildTables() {
		return childTables;
	}

	public void setChildTables(String childTables) {
		this.childTables = childTables;
	}

	public List<ItemsTableWise> getItemsTableWises() {
		return itemsTableWises;
	}

	public void setItemsTableWises(List<ItemsTableWise> itemsTableWises) {
		this.itemsTableWises = itemsTableWises;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getNoOfPersons() {
		return noOfPersons;
	}

	public void setNoOfPersons(int noOfPersons) {
		this.noOfPersons = noOfPersons;
	}

	public String getSplitBill() {
		return splitBill;
	}

	public void setSplitBill(String splitBill) {
		this.splitBill = splitBill;
	}

	public List<BillSplitER> getBillSplitERList() {
		return billSplitERList;
	}

	public void setBillSplitERList(List<BillSplitER> billSplitERList) {
		this.billSplitERList = billSplitERList;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<BillSplitManual> getBillSplitManualList() {
		return billSplitManualList;
	}

	public void setBillSplitManualList(List<BillSplitManual> billSplitManualList) {
		this.billSplitManualList = billSplitManualList;
	}

	public int getBillSplitERListSize() {

		return billSplitERList.size();
	}

	public void setBillSplitERListSize(int billSplitERListSize) {
		this.billSplitERListSize = billSplitERListSize;
	}

	public int getBillSplitManualListSize() {
		return billSplitManualList.size();
	}

	public void setBillSplitManualListSize(int billSplitManualListSize) {
		this.billSplitManualListSize = billSplitManualListSize;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getKotPrintStatus() {
		return kotPrintStatus;
	}

	public void setKotPrintStatus(String kotPrintStatus) {
		this.kotPrintStatus = kotPrintStatus;
	}

	public int getBillPrintcount() {
		return billPrintcount;
	}

	public void setBillPrintcount(int billPrintcount) {
		this.billPrintcount = billPrintcount;
	}

	public int getKotPrintCount() {
		return kotPrintCount;
	}

	public void setKotPrintCount(int kotPrintCount) {
		this.kotPrintCount = kotPrintCount;
	}

	public int getPrinterId() {
		return printerId;
	}

	public void setPrinterId(int printerId) {
		this.printerId = printerId;
	}

	public String getDeliveryPersonName() {
		return deliveryPersonName;
	}

	public void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
	}

	public int getStoreCustomerId() {
		return storeCustomerId;
	}

	public void setStoreCustomerId(int storeCustomerId) {
		this.storeCustomerId = storeCustomerId;
	}

	public String getCreditFlag() {
		return creditFlag;
	}

	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public Date getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(Date anniversary) {
		this.anniversary = anniversary;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getWaiterName() {
		return waiterName;
	}

	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public int getStorecustmerServerId() {
		return storecustmerServerId;
	}

	public void setStorecustmerServerId(int storecustmerServerId) {
		this.storecustmerServerId = storecustmerServerId;
	}

	
	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	@Override
	public String toString() {
		return "OrderMaster [id=" + id + ", customerName=" + customerName + ", customerContact=" + customerContact
				+ ", fromTime=" + fromTime + ", toTime=" + toTime + ", deliveryTime=" + deliveryTime + ", orderDate="
				+ orderDate + ", deliveryAddress=" + deliveryAddress + ", paymentType=" + paymentType + ", ordertype="
				+ ordertype + ", customers=" + customers + ", coupon=" + coupon + ", reward=" + reward + ", offer="
				+ offer + ", flag=" + flag + ", orderitem=" + orderitem + ", orderBill=" + orderBill + ", orderNo="
				+ orderNo + ", storeId=" + storeId + ", table_no=" + table_no + ", seatNo=" + seatNo + ", distance="
				+ distance + ", orderItem=" + orderItem + ", orderItem1=" + orderItem1 + ", orderItem2=" + orderItem2
				+ ", orderItem3=" + orderItem3 + ", orderItem4=" + orderItem4 + ", orderItem5=" + orderItem5
				+ ", orderList=" + orderList + ", specialNote=" + specialNote + ", orderTime=" + orderTime
				+ ", billReqStatus=" + billReqStatus + ", billReqTime=" + billReqTime + ", changeNote=" + changeNote
				+ ", cancel=" + cancel + ", childTables=" + childTables + ", source=" + source + ", itemsTableWises="
				+ itemsTableWises + ", noOfPersons=" + noOfPersons + ", splitBill=" + splitBill + ", billSplitERList="
				+ billSplitERList + ", time=" + time + ", billSplitManualList=" + billSplitManualList
				+ ", billSplitERListSize=" + billSplitERListSize + ", billSplitManualListSize="
				+ billSplitManualListSize + ", cancelRemark=" + cancelRemark + ", kotPrintStatus=" + kotPrintStatus
				+ ", billPrintcount=" + billPrintcount + ", kotPrintCount=" + kotPrintCount + ", printerId=" + printerId
				+ ", deliveryPersonName=" + deliveryPersonName + ", storeCustomerId=" + storeCustomerId
				+ ", creditFlag=" + creditFlag + ", directCat=" + directCat + ", payments=" + payments
				+ ", billPrintReason=" + billPrintReason + ", custVatRegNo=" + custVatRegNo + ", location=" + location
				+ ", houseNo=" + houseNo + ", street=" + street + ", car_no=" + car_no + ", anniversary=" + anniversary
				+ ", dob=" + dob + ", state=" + state + ", waiterName=" + waiterName + ", refundStatus=" + refundStatus
				+ ", remarks=" + remarks + ", storecustmerServerId=" + storecustmerServerId + ", isSync=" + isSync
				+ "]";
	}

	
	
	
	
}
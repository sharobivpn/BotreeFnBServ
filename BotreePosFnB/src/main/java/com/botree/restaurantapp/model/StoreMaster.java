package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: sl_m_restaurant_store_c
 * 
 */

@XmlRootElement
@Entity
@ManagedBean(name = "storeMaster1")
@Table(name = "sl_m_restaurant_store_c")
// @SessionScoped
public class StoreMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/*
	 * @Column(name = "store_id") private String storeId;
	 */

	@Expose
	@Column(name = "ip")
	private String ip;

	@Expose
	@Column(name = "latitude")
	private Double latitude;

	@Expose
	@Column(name = "longitude")
	private Double longitude;

	@Expose
	@Column(name = "store_name")
	private String storeName;

	@Expose
	@Column(name = "chain_name")
	private String chainName;

	@Expose
	@Column(name = "name_search")
	private String nameSearch;

	@Expose
	@Column(name = "details_search")
	private String detailsSearch;

	@Expose
	@Column(name = "address")
	private String address;

	@Expose
	@Column(name = "phone_no")
	private String phoneNo;

	@Expose
	@Column(name = "mobile_no")
	private String mobileNo;

	@Expose
	@Column(name = "email_id")
	private String emailId;

	@Expose
	@Column(name = "status")
	private String status;

	@Expose
	@Column(name = "city")
	private String city;

	@Expose
	@Column(name = "state")
	private String state;

	@Expose
	@Column(name = "country")
	private String country;

	@Expose
	@Column(name = "category")
	private String category;

	@Expose
	@Column(name = "url")
	private String url;

	@Expose
	@Column(name = "machine_os")
	private String operatingSystem;

	@Expose
	@Column(name = "machine_ram")
	private String ram;

	@Expose
	@Column(name = "currency")
	private String currency;

	@Expose
	@Column(name = "active_flag")
	private String activeFlag;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private RestaurantMaster restaurant;

	@Expose
	@Column(name = "restId")
	private int restaurantId;

	@Expose
	@Column(name = "quality")
	private Double quality;

	@Expose
	@Column(name = "open_time")
	private String openTime;

	@Expose
	@Column(name = "close_time")
	private String closeTime;

	@Expose
	@Column(name = "delivery_time")
	private int deliveryTime;

	@Expose
	@Column(name = "min_order_amount")
	private Double minOrderAmt;

	@Expose
	@Column(name = "free_delivery")
	private String freeDelivery;

	@Expose
	@Column(name = "license")
	private String license;

	@Expose
	@Column(name = "promotion_desc")
	private String promotionDesc;

	@Expose
	@Column(name = "promotion_value")
	private double promotionValue;

	@Expose
	@Column(name = "promotion_flag")
	private String promotionFlag;

	@Expose
	@Column(name = "open_time_weekend")
	private String openTimeWeekend;

	@Expose
	@Column(name = "working_hours")
	private int workingHours;

	@Expose
	@Column(name = "close_time_weekend")
	private String closeTimeWeekend;

	@Expose
	@Column(name = "open_time_holiday")
	private String openTimeHoliday;

	@Expose
	@Column(name = "close_time_holiday")
	private String closeTimeHoliday;

	@Expose
	@Column(name = "table_flag")
	private String tableFlag;

	@Expose
	@Column(name = "parcel_flag")
	private String parcelFlag;

	@Expose
	@Column(name = "home_delivery_flag")
	private String homeDeliveryFlag;

	@Expose
	@Column(name = "data_source")
	private String dataSource;

	@Expose
	@Column(name = "mpayment")
	private String mpayment;

	@Expose
	@Column(name = "kitchen_print")
	private String kitchenPrint = "N";

	@Expose
	@Column(name = "kitchen_print_preview")
	private String kitchenPrintPreview;

	@Expose
	@OneToMany(mappedBy = "storeMaster")
	private List<Taxes> taxes;

	@Expose
	@Column(name = "cash_payment")
	private String cashPayment;

	@Expose
	@Column(name = "credit_payment")
	private String creditPayment;

	@Expose
	@Column(name = "stock_status")
	private String stockStatus;

	@Expose
	@Column(name = "stock_period")
	private int stockPeriod;

	@Expose
	@Column(name = "from_des")
	private String fromDes;

	@Expose
	@Column(name = "to_des")
	private String toDes;

	@Expose
	@Column(name = "kot_res_title_font")
	private int kotResTitleFont;

	@Expose
	@Column(name = "kot_text_font")
	private int kotTextFont;

	@Expose
	@Column(name = "kot_date_time_font")
	private int kotDateTimeFont;

	@Expose
	@Column(name = "kot_table_font")
	private int kotTableFont;

	@Expose
	@Column(name = "kot_item_title_font")
	private int kotItemTitleFont;

	@Expose
	@Column(name = "kot_item_font")
	private int kotItemFont;

	@Expose
	@Column(name = "kot_no_of_person_font")
	private int kotNoOfPersonFont;

	@Expose
	@Column(name = "pos_manual_print")
	private String posmanualPrint;

	@Expose
	@Column(name = "bill_print")
	private String billPrint;

	@Expose
	@Column(name = "vat_reg_no")
	private String vatRegNo;

	@Expose
	@Column(name = "service_tax_no")
	private String serviceTaxNo;

	@Expose
	@Column(name = "po_print_server")
	private String poPrintServer;

	@Expose
	@Column(name = "parcel_service_tax")
	private String parcelServiceTax;

	@Expose
	@Column(name = "parcel_vat")
	private String parcelVat;

	@Expose
	@Column(name = "round_off_total_amt_status")
	private String roundOffTotalAmtStatus;

	@Expose
	@Column(name = "kitchen_print_bt")
	private String kitchenPrintBt;

	@Expose
	@Column(name = "bill_print_bt")
	private String billPrintBt;

	@Expose
	@Column(name = "kot_count")
	private int kotCount;

	@Expose
	@Column(name = "discount_percentage")
	private String discountPercentage;

	@Expose
	@Column(name = "vat_tax_text")
	private String vatTaxText;

	@Expose
	@Column(name = "service_tax_text")
	private String serviceTaxText;

	@Expose
	@Column(name = "vat_amt")
	private double vatAmt;

	@Expose
	@Column(name = "service_tax_amt")
	private double serviceTaxAmt;

	@Expose
	@Column(name = "kot_print_type")
	private String kotPrintType;

	@Expose
	@Column(name = "bill_text_font")
	private int billTextFont;

	@Expose
	@Column(name = "bill_header_font")
	private int billHeaderFont;

	@Expose
	@Column(name = "print_paid_bill")
	private String printPaidBill;

	@Expose
	@Column(name = "parcel_address")
	private String parcelAddress;

	@Expose
	@Column(name = "multi_order_table")
	private String multiOrderTable;

	@Expose
	@Column(name = "credit_sale")
	private String creditSale;

	@Expose
	@Column(name = "customer_display")
	private String customerDisplay;

	@Expose
	@Column(name = "parcel_text")
	private String parcelText;

	@Expose
	@Column(name = "kot_default_printer")
	private String kotDefaultPrinter;

	@Expose
	@Column(name = "direct_category")
	private String directCategory;

	@Expose
	@Column(name = "cooking_unit")
	private String cookingUnit;

	@Expose
	@Column(name = "smart_im")
	private String smartIm;

	@Expose
	@Column(name = "bill_footer_text")
	private String billFooterText;

	@Expose
	@Column(name = "thanks_line1")
	private String thanksLine1;

	@Expose
	@Column(name = "thanks_line2")
	private String thanksLine2;

	@Expose
	@Column(name = "double_round_off")
	private String doubleRoundOff;

	@Expose
	@Column(name = "invoice_text")
	private String invoiceText;

	@Expose
	@Column(name = "rate_in_bill")
	private String rateInBill;

	@Expose
	@Column(name = "bill_font_type")
	private String billFontType;

	@Expose
	@Column(name = "kot_padding")
	private int kotPadding;

	@Expose
	@Column(name = "cash_drawer_code")
	private String cashDrawerCode;

	@Expose
	@Column(name = "table_screen")
	private String tableScreen;

	@Expose
	@Column(name = "soft_key")
	private String softKey;

	@Expose
	@Column(name = "menu_collapsable")
	private String menuCollapsable;

	@Expose
	@Column(name = "bill_split")
	private String billSplit;

	@Expose
	@Column(name = "is_pax")
	private String isPax;

	@Expose
	@Column(name = "item_with_image")
	private String itemWithImage;

	@Expose
	@Column(name = "special_note_type")
	private String specialNoteType;//not added to page:srabasti

	@Expose
	@Column(name = "table_layout")
	private String tableLayout;

	@Expose
	@Column(name = "validate_raw_stock_out")
	private String validateRawStockOut;

	@Expose
	@Column(name = "validate_fg_sale_out")
	private String validateFgSaleOut;

	@Expose
	@Column(name = "logo_x_coordinate")
	private int logoXCoordinate;

	@Expose
	@Column(name = "db_path")
	private String dbPath;

	@Expose
	@Column(name = "mob_bill_print")
	private String mobBillPrint;

	@Expose
	@Column(name = "print_bill_paper_size")
	private String printBillPaperSize;

	@Expose
	@Column(name = "card_payment_kit")
	private String cardPaymentKit;

	@Expose
	@Column(name = "service_charge_text")
	private String serviceChargeText;

	@Expose
	@Column(name = "service_charge_rate")
	private Double serviceChargeRate;

	@Expose
	@Column(name = "parcel_service_charge")
	private String parcelServiceCharge;

	@Expose
	@Column(name = "display_current_stock_menu")
	private String displayCurrentStockMenu;

	@Expose
	@Column(name = "is_barcode")
	private String isBarcode;

	@Expose
	@Column(name = "enterprise_tbl_layout")
	private String enterpriseTblLayout;
	
	@Expose
	@Column(name = "bluetooth_printer")
	private String bluetoothPrinter;
	
	@Expose
	@Column(name = "day_book_reg")
	private String dayBookReg;
	
	@Expose
	@Column(name = "gst_text")
	private String gstText;
	
	@Expose
	@Column(name = "gst_reg_no")
	private String gstRegNo;
	
	@Expose
	@Column(name = "sc_flag")
	private String serviceChargeFlag;
	
	@Expose
	@Column(name = "waiter_flag")
	private String waiterNameFlag;
	
	@Expose
	@Column(name = "delivery_boy_flag")
	private String deliveryBoyFlag;
	
	@Expose
	@Column(name = "country_id")
	private int countryId;
	
	@Expose
	@Column(name = "is_arabic_bill")
	private String isArabicBill;
	
	@Expose
	@Column(name = "is_provide_reason")
	private String isProvideReason;
	
	@Expose
	@Column(name = "sms_integration")
	private String smsIntegration;
	
	//added on 21.05.2018
	@Expose
	@Column(name = "bill_count")
	private int billCount;
	
	//added on 28.05.2018
	@Expose
	@Column(name = "simple_im")
	private String simpleIm;
	
	@Expose
	@Column(name = "room_booking")
	private String roomBooking;
	
	@Expose
	@Column(name = "ord_succ")
	private String ordSucc;
	
	//added on 13.06.2018
	@Expose
	@Column(name = "is_refund")
	private String isRefund;
	
	//added on 10.07.2018
	@Expose
	@Column(name = "is_account")
	private String is_account;
	
	//added on 23.01.2019
	@Expose
	@Column(name = "adv_order")
	private String advOrder;
	
	//added on 07.03.2019
	@Expose
	@Column(name = "negative_stock_billing")
	private String negativeStockBilling;
	
	//added on 10.10.2019
	@Expose
	@Column(name = "is_hr")
	private String isHr;
    
	
	public String getNegativeStockBilling() {
		return negativeStockBilling;
	}

	public void setNegativeStockBilling(String negativeStockBilling) {
		this.negativeStockBilling = negativeStockBilling;
	}

	public String getAdvOrder() {
		return advOrder;
	}

	public void setAdvOrder(String advOrder) {
		this.advOrder = advOrder;
	}

	public String getIs_account() {
		return is_account;
	}

	public void setIs_account(String is_account) {
		this.is_account = is_account;
	}

	public String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}

	public String getSimpleIm() {
		return simpleIm;
	}

	public void setSimpleIm(String simpleIm) {
		this.simpleIm = simpleIm;
	}

	public String getRoomBooking() {
		return roomBooking;
	}

	public void setRoomBooking(String roomBooking) {
		this.roomBooking = roomBooking;
	}

	public String getOrdSucc() {
		return ordSucc;
	}

	public void setOrdSucc(String ordSucc) {
		this.ordSucc = ordSucc;
	}

	public int getBillCount() {
		return billCount;
	}

	public void setBillCount(int billCount) {
		this.billCount = billCount;
	}

	public String getSmsIntegration() {
		return smsIntegration;
	}

	public void setSmsIntegration(String smsIntegration) {
		this.smsIntegration = smsIntegration;
	}

	public String getIsProvideReason() {
		return isProvideReason;
	}

	public void setIsProvideReason(String isProvideReason) {
		this.isProvideReason = isProvideReason;
	}

	public String getIsArabicBill() {
		return isArabicBill;
	}

	public void setIsArabicBill(String isArabicBill) {
		this.isArabicBill = isArabicBill;
	}
	
	public String getGstText() {
		return gstText;
	}

	public void setGstText(String gstText) {
		this.gstText = gstText;
	}

	public String getGstRegNo() {
		return gstRegNo;
	}

	public void setGstRegNo(String gstRegNo) {
		this.gstRegNo = gstRegNo;
	}

	public String getDayBookReg() {
		return dayBookReg;
	}

	public void setDayBookReg(String dayBookReg) {
		this.dayBookReg = dayBookReg;
	}

	public String getBluetoothPrinter() {
		return bluetoothPrinter;
	}

	public void setBluetoothPrinter(String bluetoothPrinter) {
		this.bluetoothPrinter = bluetoothPrinter;
	}

	public String getEnterpriseTblLayout() {
		return enterpriseTblLayout;
	}

	public void setEnterpriseTblLayout(String enterpriseTblLayout) {
		this.enterpriseTblLayout = enterpriseTblLayout;
	}

	public String getIsBarcode() {
		return isBarcode;
	}

	public void setIsBarcode(String isBarcode) {
		this.isBarcode = isBarcode;
	}

	public String getDisplayCurrentStockMenu() {
		return displayCurrentStockMenu;
	}

	public void setDisplayCurrentStockMenu(String displayCurrentStockMenu) {
		this.displayCurrentStockMenu = displayCurrentStockMenu;
	}

	public String getParcelServiceCharge() {
		return parcelServiceCharge;
	}

	public void setParcelServiceCharge(String parcelServiceCharge) {
		this.parcelServiceCharge = parcelServiceCharge;
	}

	public String getServiceChargeText() {
		return serviceChargeText;
	}

	public void setServiceChargeText(String serviceChargeText) {
		this.serviceChargeText = serviceChargeText;
	}

	public Double getServiceChargeRate() {
		return serviceChargeRate;
	}

	public void setServiceChargeRate(Double serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}

	public String getSoftKey() {
		return softKey;
	}

	public String getCardPaymentKit() {
		return cardPaymentKit;
	}

	public void setCardPaymentKit(String cardPaymentKit) {
		this.cardPaymentKit = cardPaymentKit;
	}

	public String getPrintBillPaperSize() {
		return printBillPaperSize;
	}

	public void setPrintBillPaperSize(String printBillPaperSize) {
		this.printBillPaperSize = printBillPaperSize;
	}

	public String getMobBillPrint() {
		return mobBillPrint;
	}

	public void setMobBillPrint(String mobBillPrint) {
		this.mobBillPrint = mobBillPrint;
	}

	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}

	public int getLogoXCoordinate() {
		return logoXCoordinate;
	}

	public void setLogoXCoordinate(int logoXCoordinate) {
		this.logoXCoordinate = logoXCoordinate;
	}

	public String getValidateRawStockOut() {
		return validateRawStockOut;
	}

	public void setValidateRawStockOut(String validateRawStockOut) {
		this.validateRawStockOut = validateRawStockOut;
	}

	public String getValidateFgSaleOut() {
		return validateFgSaleOut;
	}

	public void setValidateFgSaleOut(String validateFgSaleOut) {
		this.validateFgSaleOut = validateFgSaleOut;
	}

	public String getSpecialNoteType() {
		return specialNoteType;
	}

	public void setSpecialNoteType(String specialNoteType) {
		this.specialNoteType = specialNoteType;
	}

	public String getTableLayout() {
		return tableLayout;
	}

	public void setTableLayout(String tableLayout) {
		this.tableLayout = tableLayout;
	}

	public String getItemWithImage() {
		return itemWithImage;
	}

	public void setItemWithImage(String itemWithImage) {
		this.itemWithImage = itemWithImage;
	}

	public void setSoftKey(String softKey) {
		this.softKey = softKey;
	}

	public String getMenuCollapsable() {
		return menuCollapsable;
	}

	public void setMenuCollapsable(String menuCollapsable) {
		this.menuCollapsable = menuCollapsable;
	}

	public String getBillSplit() {
		return billSplit;
	}

	public void setBillSplit(String billSplit) {
		this.billSplit = billSplit;
	}

	public String getIsPax() {
		return isPax;
	}

	public void setIsPax(String isPax) {
		this.isPax = isPax;
	}

	public String getTableScreen() {
		return tableScreen;
	}

	public void setTableScreen(String tableScreen) {
		this.tableScreen = tableScreen;
	}

	public int getWorkingHours() {
		return workingHours;
	}

	public int getKotPadding() {
		return kotPadding;
	}

	public void setKotPadding(int kotPadding) {
		this.kotPadding = kotPadding;
	}

	public String getInvoiceText() {
		return invoiceText;
	}

	public void setInvoiceText(String invoiceText) {
		this.invoiceText = invoiceText;
	}

	public void setWorkingHours(int workingHours) {
		this.workingHours = workingHours;
	}

	public String getCustomerDisplay() {
		return customerDisplay;
	}

	public String getCookingUnit() {
		return cookingUnit;
	}

	public void setCookingUnit(String cookingUnit) {
		this.cookingUnit = cookingUnit;
	}

	public String getDirectCategory() {
		return directCategory;
	}

	public void setDirectCategory(String directCategory) {
		this.directCategory = directCategory;
	}

	public String getKotDefaultPrinter() {
		return kotDefaultPrinter;
	}

	public void setKotDefaultPrinter(String kotDefaultPrinter) {
		this.kotDefaultPrinter = kotDefaultPrinter;
	}

	public String getParcelText() {
		return parcelText;
	}

	public void setParcelText(String parcelText) {
		this.parcelText = parcelText;
	}

	public void setCustomerDisplay(String customerDisplay) {
		this.customerDisplay = customerDisplay;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public String getCreditSale() {
		return creditSale;
	}

	public void setCreditSale(String creditSale) {
		this.creditSale = creditSale;
	}

	public double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public String getBillPrintBt() {
		return billPrintBt;
	}

	public void setBillPrintBt(String billPrintBt) {
		this.billPrintBt = billPrintBt;
	}

	public int getKotCount() {
		return kotCount;
	}

	public void setKotCount(int kotCount) {
		this.kotCount = kotCount;
	}

	public String getRoundOffTotalAmtStatus() {
		return roundOffTotalAmtStatus;
	}

	public void setRoundOffTotalAmtStatus(String roundOffTotalAmtStatus) {
		this.roundOffTotalAmtStatus = roundOffTotalAmtStatus;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * public String getStoreId() { return storeId; }
	 * 
	 * public void setStoreId(String storeId) { this.storeId = storeId; }
	 */

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	public String getNameSearch() {
		return nameSearch;
	}

	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	public String getDetailsSearch() {
		return detailsSearch;
	}

	public void setDetailsSearch(String detailsSearch) {
		this.detailsSearch = detailsSearch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public RestaurantMaster getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantMaster restaurant) {
		this.restaurant = restaurant;
	}

	public Double getQuality() {
		return quality;
	}

	public void setQuality(Double quality) {
		this.quality = quality;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Double getMinOrderAmt() {
		return minOrderAmt;
	}

	public void setMinOrderAmt(Double minOrderAmt) {
		this.minOrderAmt = minOrderAmt;
	}

	public String getFreeDelivery() {
		return freeDelivery;
	}

	public void setFreeDelivery(String freeDelivery) {
		this.freeDelivery = freeDelivery;
	}

	public String getLicense() {
		return license;
	}

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public double getPromotionValue() {
		return promotionValue;
	}

	public void setPromotionValue(double promotionValue) {
		this.promotionValue = promotionValue;
	}

	public String getPromotionFlag() {
		return promotionFlag;
	}

	public void setPromotionFlag(String promotionFlag) {
		this.promotionFlag = promotionFlag;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOpenTimeWeekend() {
		return openTimeWeekend;
	}

	public void setOpenTimeWeekend(String openTimeWeekend) {
		this.openTimeWeekend = openTimeWeekend;
	}

	public String getCloseTimeWeekend() {
		return closeTimeWeekend;
	}

	public void setCloseTimeWeekend(String closeTimeWeekend) {
		this.closeTimeWeekend = closeTimeWeekend;
	}

	public String getOpenTimeHoliday() {
		return openTimeHoliday;
	}

	public void setOpenTimeHoliday(String openTimeHoliday) {
		this.openTimeHoliday = openTimeHoliday;
	}

	public String getCloseTimeHoliday() {
		return closeTimeHoliday;
	}

	public void setCloseTimeHoliday(String closeTimeHoliday) {
		this.closeTimeHoliday = closeTimeHoliday;
	}

	public String getTableFlag() {
		return tableFlag;
	}

	public void setTableFlag(String tableFlag) {
		this.tableFlag = tableFlag;
	}

	public String getParcelFlag() {
		return parcelFlag;
	}

	public void setParcelFlag(String parcelFlag) {
		this.parcelFlag = parcelFlag;
	}

	public String getHomeDeliveryFlag() {
		return homeDeliveryFlag;
	}

	public void setHomeDeliveryFlag(String homeDeliveryFlag) {
		this.homeDeliveryFlag = homeDeliveryFlag;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getMpayment() {
		return mpayment;
	}

	public void setMpayment(String mpayment) {
		this.mpayment = mpayment;
	}

	public String getKitchenPrint() {
		return kitchenPrint;
	}

	public void setKitchenPrint(String kitchenPrint) {
		this.kitchenPrint = kitchenPrint;
	}

	public String getKitchenPrintPreview() {
		return kitchenPrintPreview;
	}

	public void setKitchenPrintPreview(String kitchenPrintPreview) {
		this.kitchenPrintPreview = kitchenPrintPreview;
	}

	public List<Taxes> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Taxes> taxes) {
		this.taxes = taxes;
	}

	public String getCashPayment() {
		return cashPayment;
	}

	public void setCashPayment(String cashPayment) {
		this.cashPayment = cashPayment;
	}

	public String getCreditPayment() {
		return creditPayment;
	}

	public void setCreditPayment(String creditPayment) {
		this.creditPayment = creditPayment;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public int getStockPeriod() {
		return stockPeriod;
	}

	public void setStockPeriod(int stockPeriod) {
		this.stockPeriod = stockPeriod;
	}

	public String getFromDes() {
		return fromDes;
	}

	public void setFromDes(String fromDes) {
		this.fromDes = fromDes;
	}

	public String getToDes() {
		return toDes;
	}

	public void setToDes(String toDes) {
		this.toDes = toDes;
	}

	public int getKotResTitleFont() {
		return kotResTitleFont;
	}

	public void setKotResTitleFont(int kotResTitleFont) {
		this.kotResTitleFont = kotResTitleFont;
	}

	public int getKotTextFont() {
		return kotTextFont;
	}

	public void setKotTextFont(int kotTextFont) {
		this.kotTextFont = kotTextFont;
	}

	public int getKotDateTimeFont() {
		return kotDateTimeFont;
	}

	public void setKotDateTimeFont(int kotDateTimeFont) {
		this.kotDateTimeFont = kotDateTimeFont;
	}

	public int getKotTableFont() {
		return kotTableFont;
	}

	public void setKotTableFont(int kotTableFont) {
		this.kotTableFont = kotTableFont;
	}

	public int getKotItemTitleFont() {
		return kotItemTitleFont;
	}

	public void setKotItemTitleFont(int kotItemTitleFont) {
		this.kotItemTitleFont = kotItemTitleFont;
	}

	public int getKotItemFont() {
		return kotItemFont;
	}

	public void setKotItemFont(int kotItemFont) {
		this.kotItemFont = kotItemFont;
	}

	public int getKotNoOfPersonFont() {
		return kotNoOfPersonFont;
	}

	public void setKotNoOfPersonFont(int kotNoOfPersonFont) {
		this.kotNoOfPersonFont = kotNoOfPersonFont;
	}

	public String getPosmanualPrint() {
		return posmanualPrint;
	}

	public void setPosmanualPrint(String posmanualPrint) {
		this.posmanualPrint = posmanualPrint;
	}

	public String getBillPrint() {
		return billPrint;
	}

	public void setBillPrint(String billPrint) {
		this.billPrint = billPrint;
	}

	public String getVatRegNo() {
		return vatRegNo;
	}

	public void setVatRegNo(String vatRegNo) {
		this.vatRegNo = vatRegNo;
	}

	public String getServiceTaxNo() {
		return serviceTaxNo;
	}

	public void setServiceTaxNo(String serviceTaxNo) {
		this.serviceTaxNo = serviceTaxNo;
	}

	/*
	 * public String getMobBillPrint() { return mobBillPrint; }
	 * 
	 * public void setMobBillPrint(String mobBillPrint) { this.mobBillPrint =
	 * mobBillPrint; }
	 */

	public String getPoPrintServer() {
		return poPrintServer;
	}

	public void setPoPrintServer(String poPrintServer) {
		this.poPrintServer = poPrintServer;
	}

	public String getParcelServiceTax() {
		return parcelServiceTax;
	}

	public void setParcelServiceTax(String parcelServiceTax) {
		this.parcelServiceTax = parcelServiceTax;
	}

	public String getParcelVat() {
		return parcelVat;
	}

	public void setParcelVat(String parcelVat) {
		this.parcelVat = parcelVat;
	}

	public String getKitchenPrintBt() {
		return kitchenPrintBt;
	}

	public void setKitchenPrintBt(String kitchenPrintBt) {
		this.kitchenPrintBt = kitchenPrintBt;
	}

	public String getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getVatTaxText() {
		return vatTaxText;
	}

	public void setVatTaxText(String vatTaxText) {
		this.vatTaxText = vatTaxText;
	}

	public String getServiceTaxText() {
		return serviceTaxText;
	}

	public void setServiceTaxText(String serviceTaxText) {
		this.serviceTaxText = serviceTaxText;
	}

	public String getKotPrintType() {
		return kotPrintType;
	}

	public void setKotPrintType(String kotPrintType) {
		this.kotPrintType = kotPrintType;
	}

	public int getBillTextFont() {
		return billTextFont;
	}

	public void setBillTextFont(int billTextFont) {
		this.billTextFont = billTextFont;
	}

	public int getBillHeaderFont() {
		return billHeaderFont;
	}

	public void setBillHeaderFont(int billHeaderFont) {
		this.billHeaderFont = billHeaderFont;
	}

	public String getPrintPaidBill() {
		return printPaidBill;
	}

	public void setPrintPaidBill(String printPaidBill) {
		this.printPaidBill = printPaidBill;
	}

	public String getParcelAddress() {
		return parcelAddress;
	}

	public void setParcelAddress(String parcelAddress) {
		this.parcelAddress = parcelAddress;
	}

	public String getMultiOrderTable() {
		return multiOrderTable;
	}

	public void setMultiOrderTable(String multiOrderTable) {
		this.multiOrderTable = multiOrderTable;
	}

	public String getSmartIm() {
		return smartIm;
	}

	public void setSmartIm(String smartIm) {
		this.smartIm = smartIm;
	}

	public String getBillFooterText() {
		return billFooterText;
	}

	public void setBillFooterText(String billFooterText) {
		this.billFooterText = billFooterText;
	}

	public String getThanksLine1() {
		return thanksLine1;
	}

	public void setThanksLine1(String thanksLine1) {
		this.thanksLine1 = thanksLine1;
	}

	public String getThanksLine2() {
		return thanksLine2;
	}

	public void setThanksLine2(String thanksLine2) {
		this.thanksLine2 = thanksLine2;
	}

	public String getDoubleRoundOff() {
		return doubleRoundOff;
	}

	public void setDoubleRoundOff(String doubleRoundOff) {
		this.doubleRoundOff = doubleRoundOff;
	}

	public String getRateInBill() {
		return rateInBill;
	}

	public void setRateInBill(String rateInBill) {
		this.rateInBill = rateInBill;
	}

	public String getBillFontType() {
		return billFontType;
	}

	public void setBillFontType(String billFontType) {
		this.billFontType = billFontType;
	}

	public String getCashDrawerCode() {
		return cashDrawerCode;
	}

	public void setCashDrawerCode(String cashDrawerCode) {
		this.cashDrawerCode = cashDrawerCode;
	}

	
	public String getServiceChargeFlag() {
		return serviceChargeFlag;
	}

	public void setServiceChargeFlag(String serviceChargeFlag) {
		this.serviceChargeFlag = serviceChargeFlag;
	}

	
	public String getWaiterNameFlag() {
		return waiterNameFlag;
	}

	public void setWaiterNameFlag(String waiterNameFlag) {
		this.waiterNameFlag = waiterNameFlag;
	}

	public String getDeliveryBoyFlag() {
		return deliveryBoyFlag;
	}

	public void setDeliveryBoyFlag(String deliveryBoyFlag) {
		this.deliveryBoyFlag = deliveryBoyFlag;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getIsHr() {
		return isHr;
	}

	public void setIsHr(String isHr) {
		this.isHr = isHr;
	}

	@Override
	public String toString() {
		return "StoreMaster [id=" + id + ", ip=" + ip + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", storeName=" + storeName + ", chainName=" + chainName + ", nameSearch=" + nameSearch
				+ ", detailsSearch=" + detailsSearch + ", address=" + address + ", phoneNo=" + phoneNo + ", mobileNo="
				+ mobileNo + ", emailId=" + emailId + ", status=" + status + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", category=" + category + ", url=" + url + ", operatingSystem="
				+ operatingSystem + ", ram=" + ram + ", currency=" + currency + ", activeFlag=" + activeFlag
				+ ", restaurant=" + restaurant + ", restaurantId=" + restaurantId + ", quality=" + quality
				+ ", openTime=" + openTime + ", closeTime=" + closeTime + ", deliveryTime=" + deliveryTime
				+ ", minOrderAmt=" + minOrderAmt + ", freeDelivery=" + freeDelivery + ", license=" + license
				+ ", promotionDesc=" + promotionDesc + ", promotionValue=" + promotionValue + ", promotionFlag="
				+ promotionFlag + ", openTimeWeekend=" + openTimeWeekend + ", workingHours=" + workingHours
				+ ", closeTimeWeekend=" + closeTimeWeekend + ", openTimeHoliday=" + openTimeHoliday
				+ ", closeTimeHoliday=" + closeTimeHoliday + ", tableFlag=" + tableFlag + ", parcelFlag=" + parcelFlag
				+ ", homeDeliveryFlag=" + homeDeliveryFlag + ", dataSource=" + dataSource + ", mpayment=" + mpayment
				+ ", kitchenPrint=" + kitchenPrint + ", kitchenPrintPreview=" + kitchenPrintPreview + ", taxes=" + taxes
				+ ", cashPayment=" + cashPayment + ", creditPayment=" + creditPayment + ", stockStatus=" + stockStatus
				+ ", stockPeriod=" + stockPeriod + ", fromDes=" + fromDes + ", toDes=" + toDes + ", kotResTitleFont="
				+ kotResTitleFont + ", kotTextFont=" + kotTextFont + ", kotDateTimeFont=" + kotDateTimeFont
				+ ", kotTableFont=" + kotTableFont + ", kotItemTitleFont=" + kotItemTitleFont + ", kotItemFont="
				+ kotItemFont + ", kotNoOfPersonFont=" + kotNoOfPersonFont + ", posmanualPrint=" + posmanualPrint
				+ ", billPrint=" + billPrint + ", vatRegNo=" + vatRegNo + ", serviceTaxNo=" + serviceTaxNo
				+ ", poPrintServer=" + poPrintServer + ", parcelServiceTax=" + parcelServiceTax + ", parcelVat="
				+ parcelVat + ", roundOffTotalAmtStatus=" + roundOffTotalAmtStatus + ", kitchenPrintBt="
				+ kitchenPrintBt + ", billPrintBt=" + billPrintBt + ", kotCount=" + kotCount + ", discountPercentage="
				+ discountPercentage + ", vatTaxText=" + vatTaxText + ", serviceTaxText=" + serviceTaxText + ", vatAmt="
				+ vatAmt + ", serviceTaxAmt=" + serviceTaxAmt + ", kotPrintType=" + kotPrintType + ", billTextFont="
				+ billTextFont + ", billHeaderFont=" + billHeaderFont + ", printPaidBill=" + printPaidBill
				+ ", parcelAddress=" + parcelAddress + ", multiOrderTable=" + multiOrderTable + ", creditSale="
				+ creditSale + ", customerDisplay=" + customerDisplay + ", parcelText=" + parcelText
				+ ", kotDefaultPrinter=" + kotDefaultPrinter + ", directCategory=" + directCategory + ", cookingUnit="
				+ cookingUnit + ", smartIm=" + smartIm + ", billFooterText=" + billFooterText + ", thanksLine1="
				+ thanksLine1 + ", thanksLine2=" + thanksLine2 + ", doubleRoundOff=" + doubleRoundOff + ", invoiceText="
				+ invoiceText + ", rateInBill=" + rateInBill + ", billFontType=" + billFontType + ", kotPadding="
				+ kotPadding + ", cashDrawerCode=" + cashDrawerCode + ", tableScreen=" + tableScreen + ", softKey="
				+ softKey + ", menuCollapsable=" + menuCollapsable + ", billSplit=" + billSplit + ", isPax=" + isPax
				+ ", itemWithImage=" + itemWithImage + ", specialNoteType=" + specialNoteType + ", tableLayout="
				+ tableLayout + ", validateRawStockOut=" + validateRawStockOut + ", validateFgSaleOut="
				+ validateFgSaleOut + ", logoXCoordinate=" + logoXCoordinate + ", dbPath=" + dbPath + ", mobBillPrint="
				+ mobBillPrint + ", printBillPaperSize=" + printBillPaperSize + ", cardPaymentKit=" + cardPaymentKit
				+ ", serviceChargeText=" + serviceChargeText + ", serviceChargeRate=" + serviceChargeRate
				+ ", parcelServiceCharge=" + parcelServiceCharge + ", displayCurrentStockMenu="
				+ displayCurrentStockMenu + ", isBarcode=" + isBarcode + ", enterpriseTblLayout=" + enterpriseTblLayout
				+ ", bluetoothPrinter=" + bluetoothPrinter + ", dayBookReg=" + dayBookReg + ", gstText=" + gstText
				+ ", gstRegNo=" + gstRegNo + ", serviceChargeFlag=" + serviceChargeFlag + ", waiterNameFlag="
				+ waiterNameFlag + ", deliveryBoyFlag=" + deliveryBoyFlag + ", countryId=" + countryId
				+ ", isArabicBill=" + isArabicBill + ", isProvideReason=" + isProvideReason + ", smsIntegration="
				+ smsIntegration + ", billCount=" + billCount + ", simpleIm=" + simpleIm + ", roomBooking="
				+ roomBooking + ", ordSucc=" + ordSucc + ", isRefund=" + isRefund + ", is_account=" + is_account
				+ ", advOrder=" + advOrder + ", negativeStockBilling=" + negativeStockBilling + ", isHr=" + isHr + "]";
	}

	
	

	

}
package com.botree.restaurantapp.commonUtil;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

@XmlRootElement
public class CommonResultSetMapper implements Serializable {

	@Expose
	private int id;

	@Expose
	private int companyId;

	@Expose
	private int storeId;

	@Expose
	private String name;

	@Expose
	private String lang;

	@Expose
	@MapToData(columnAliases={"item_id"})
	private int itemId;

	@Expose
	@MapToData(columnAliases={"item_name"})
	private String itemName;

	@Expose
	private String brandName;

	@Expose
	private String contentName;

	@Expose
	private String manufacturerName;

	@Expose
	private String packUnitName;

	@Expose
	private String looseUnitName;

	@Expose
	private String rackName;

	@Expose
	private int conv;

	@Expose
	private String groupName;

	@Expose
	private String schdName;

	@Expose
	private double price;

	@Expose
	private String queryCondn;

	@Expose
	private int purInvId;

	@Expose
	private int finYrId;

	@Expose
	private String startDate;

	@Expose
	private String endDate;

	@Expose
	private String invoiceNo;

	@Expose
	private String purOrderNo;

	@Expose
	private String distributorName;

	@Expose
	private int distributorId;

	@Expose
	private String upToDate;

	@Expose
	private int noOfExpiryMonth;

	@Expose
	private int saleId;

	@Expose
	private int custId;

	@Expose
	private String custName;

	@Expose
	private String custPh;

	@Expose
	private int status;

	@Expose
	private String doctorName;

	@Expose
	private String doctorPh;

	@Expose
	private String asOnDate;

	@Expose
	private int deletedBy;

	@Expose
	private int createdBy;

	@Expose
	private int manuId;

	@Expose
	private int contentId;

	@Expose
	private String batchNo;

	@Expose
	private String expiryDateFormat;

	@Expose
	private double mrp;
	
	@Expose
	private int saleReturnId;
	
	@Expose
	private int noOfMonthBefore;
	
	@Expose
	private String manufacturerCode;
	
	@Expose
	private int purchaseReturnId;
	
	@Expose
	private int paymentId;
	
	@Expose
	private String paymentDate;
	
	@Expose
	private int expiryId;
	
	@Expose
	private String taxName;
	
	@Expose
	private int taxId;
	
	@Expose
	private int isGroup;
	
	@Expose
	private double taxPer;
	
	@Expose
	private int noOfRows;
	
	@Expose
	private String frmDate;
	
	@Expose
	private String toDate;
	
	@Expose
	private String sku;
	
	@Expose
	private String hsnCode;
	
	@Expose
	private String retType;
	
	@Expose
	private int purchaseOrderId;
	
	@Expose
	private String invDate;
	
	@Expose
	private String poGenType;
	
	@Expose
	private String noteLineOne;
	
	@Expose
	private String noteLineTwo;
	
	@Expose
	private int isRePrint;
	
	@Expose
	private int printCount;
	
	@Expose
	private String remarks;
	
	@Expose
	private String barCode;
	
	@Expose
	private int lastSaleDays;
	
	@Expose
	private int comingPurchaseDays;
	
	@Expose
	private int noOfDays;
	
	@Expose
	private String marketerCode;
	
	@Expose
	private String accntGrpName;
	
	@Expose
	private String marketerName;
	
	@Expose
	private double purchaseRate;
	
	@Expose
	private double saleRate;
	
	@Expose
	private Date launchDate;
	
	@Expose
	private Date discontinueDate;
	
	@Expose
	private int countryId;
	
	@Expose
	private int stateId;
	
	@Expose
	private int cityId;
	
	@Expose
	private int zoneId;
	
	@Expose
	private String districtName;
	
	@Expose
	private int variantTypeId;
	
	@Expose
	private String variantTypeName;
	
	@Expose
	private int menuID;
	
	@Expose
	private int retailTypeID;
	
	@Expose
	private double listedMrp;
	
	@Expose
	private int catId;
	
	@Expose
	private String billNo;
	
	@Expose
	private String expDate;
	
	@Expose
	private int saleDetailsId;
	
	@Expose
	private String qs;
	
	@Expose
	private String mulSeriesPrefix;
	
	//27.03.2018
	@Expose
	private String groupCode;
	@Expose
	private int accountID;
	@Expose
	private int referenceID;
	
	@Expose
	private String qryCondition;
	
	@Expose
	private String purchaseIds;
	
	@Expose
	@MapToData(columnAliases={"item_code"})
	private String itemCode;
	
	private static final long serialVersionUID = 1L;
	
	
	
	
	public int getSaleDetailsId() {
		return saleDetailsId;
	}

	public void setSaleDetailsId(int saleDetailsId) {
		this.saleDetailsId = saleDetailsId;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public double getListedMrp() {
		return listedMrp;
	}

	public void setListedMrp(double listedMrp) {
		this.listedMrp = listedMrp;
	}

	public int getVariantTypeId() {
		return variantTypeId;
	}

	public void setVariantTypeId(int variantTypeId) {
		this.variantTypeId = variantTypeId;
	}

	public String getVariantTypeName() {
		return variantTypeName;
	}

	public void setVariantTypeName(String variantTypeName) {
		this.variantTypeName = variantTypeName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getAccntGrpName() {
		return accntGrpName;
	}

	public void setAccntGrpName(String accntGrpName) {
		this.accntGrpName = accntGrpName;
	}

	public String getMarketerCode() {
		return marketerCode;
	}

	public void setMarketerCode(String marketerCode) {
		this.marketerCode = marketerCode;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int getLastSaleDays() {
		return lastSaleDays;
	}

	public void setLastSaleDays(int lastSaleDays) {
		this.lastSaleDays = lastSaleDays;
	}

	public int getComingPurchaseDays() {
		return comingPurchaseDays;
	}

	public void setComingPurchaseDays(int comingPurchaseDays) {
		this.comingPurchaseDays = comingPurchaseDays;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getIsRePrint() {
		return isRePrint;
	}

	public void setIsRePrint(int isRePrint) {
		this.isRePrint = isRePrint;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public String getNoteLineOne() {
		return noteLineOne;
	}

	public void setNoteLineOne(String noteLineOne) {
		this.noteLineOne = noteLineOne;
	}

	public String getNoteLineTwo() {
		return noteLineTwo;
	}

	public void setNoteLineTwo(String noteLineTwo) {
		this.noteLineTwo = noteLineTwo;
	}

	public String getPoGenType() {
		return poGenType;
	}

	public void setPoGenType(String poGenType) {
		this.poGenType = poGenType;
	}

	public String getInvDate() {
		return invDate;
	}

	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}

	public int getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(int purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getRetType() {
		return retType;
	}

	public void setRetType(String retType) {
		this.retType = retType;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getFrmDate() {
		return frmDate;
	}

	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getNoOfRows() {
		return noOfRows;
	}

	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}

	public double getTaxPer() {
		return taxPer;
	}

	public void setTaxPer(double taxPer) {
		this.taxPer = taxPer;
	}

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public int getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public int getExpiryId() {
		return expiryId;
	}

	public void setExpiryId(int expiryId) {
		this.expiryId = expiryId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getPurchaseReturnId() {
		return purchaseReturnId;
	}

	public void setPurchaseReturnId(int purchaseReturnId) {
		this.purchaseReturnId = purchaseReturnId;
	}

	public String getManufacturerCode() {
		return manufacturerCode;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public int getNoOfMonthBefore() {
		return noOfMonthBefore;
	}

	public void setNoOfMonthBefore(int noOfMonthBefore) {
		this.noOfMonthBefore = noOfMonthBefore;
	}

	public int getSaleReturnId() {
		return saleReturnId;
	}

	public void setSaleReturnId(int saleReturnId) {
		this.saleReturnId = saleReturnId;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public String getExpiryDateFormat() {
		return expiryDateFormat;
	}

	public void setExpiryDateFormat(String expiryDateFormat) {
		this.expiryDateFormat = expiryDateFormat;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getManuId() {
		return manuId;
	}

	public void setManuId(int manuId) {
		this.manuId = manuId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(int deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getAsOnDate() {
		return asOnDate;
	}

	public void setAsOnDate(String asOnDate) {
		this.asOnDate = asOnDate;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorPh() {
		return doctorPh;
	}

	public void setDoctorPh(String doctorPh) {
		this.doctorPh = doctorPh;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCustPh() {
		return custPh;
	}

	public void setCustPh(String custPh) {
		this.custPh = custPh;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public String getUpToDate() {
		return upToDate;
	}

	public void setUpToDate(String upToDate) {
		this.upToDate = upToDate;
	}

	public int getNoOfExpiryMonth() {
		return noOfExpiryMonth;
	}

	public void setNoOfExpiryMonth(int noOfExpiryMonth) {
		this.noOfExpiryMonth = noOfExpiryMonth;
	}

	public int getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(int distributorId) {
		this.distributorId = distributorId;
	}

	public int getFinYrId() {
		return finYrId;
	}

	public void setFinYrId(int finYrId) {
		this.finYrId = finYrId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getPurOrderNo() {
		return purOrderNo;
	}

	public void setPurOrderNo(String purOrderNo) {
		this.purOrderNo = purOrderNo;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public int getPurInvId() {
		return purInvId;
	}

	public void setPurInvId(int purInvId) {
		this.purInvId = purInvId;
	}

	public String getQueryCondn() {
		return queryCondn;
	}

	public void setQueryCondn(String queryCondn) {
		this.queryCondn = queryCondn;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getPackUnitName() {
		return packUnitName;
	}

	public void setPackUnitName(String packUnitName) {
		this.packUnitName = packUnitName;
	}

	public String getLooseUnitName() {
		return looseUnitName;
	}

	public void setLooseUnitName(String looseUnitName) {
		this.looseUnitName = looseUnitName;
	}

	public String getRackName() {
		return rackName;
	}

	public void setRackName(String rackName) {
		this.rackName = rackName;
	}

	public int getConv() {
		return conv;
	}

	public void setConv(int conv) {
		this.conv = conv;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSchdName() {
		return schdName;
	}

	public void setSchdName(String schdName) {
		this.schdName = schdName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getMarketerName() {
		return marketerName;
	}

	public void setMarketerName(String marketerName) {
		this.marketerName = marketerName;
	}

	public double getPurchaseRate() {
		return purchaseRate;
	}

	public void setPurchaseRate(double purchaseRate) {
		this.purchaseRate = purchaseRate;
	}

	public double getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(double saleRate) {
		this.saleRate = saleRate;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public Date getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(Date discontinueDate) {
		this.discontinueDate = discontinueDate;
	}

	/**
	 * @return the menuID
	 */
	public int getMenuID() {
		return menuID;
	}

	/**
	 * @param menuID the menuID to set
	 */
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}

	/**
	 * @return the retailTypeID
	 */
	public int getRetailTypeID() {
		return retailTypeID;
	}

	/**
	 * @param retailTypeID the retailTypeID to set
	 */
	public void setRetailTypeID(int retailTypeID) {
		this.retailTypeID = retailTypeID;
	}

	/**
	 * @return the qs
	 */
	public String getQs() {
		return qs;
	}

	/**
	 * @param qs the qs to set
	 */
	public void setQs(String qs) {
		this.qs = qs;
	}

	/**
	 * @return the mulSeriesPrefix
	 */
	public String getMulSeriesPrefix() {
		return mulSeriesPrefix;
	}

	/**
	 * @param mulSeriesPrefix the mulSeriesPrefix to set
	 */
	public void setMulSeriesPrefix(String mulSeriesPrefix) {
		this.mulSeriesPrefix = mulSeriesPrefix;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public String getQryCondition() {
		return qryCondition;
	}

	public void setQryCondition(String qryCondition) {
		this.qryCondition = qryCondition;
	}

	public String getPurchaseIds() {
		return purchaseIds;
	}

	public void setPurchaseIds(String purchaseIds) {
		this.purchaseIds = purchaseIds;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public String toString() {
		return "CommonResultSetMapper [id=" + id + ", companyId=" + companyId + ", storeId=" + storeId + ", name="
				+ name + ", lang=" + lang + ", itemId=" + itemId + ", itemName=" + itemName + ", brandName=" + brandName
				+ ", contentName=" + contentName + ", manufacturerName=" + manufacturerName + ", packUnitName="
				+ packUnitName + ", looseUnitName=" + looseUnitName + ", rackName=" + rackName + ", conv=" + conv
				+ ", groupName=" + groupName + ", schdName=" + schdName + ", price=" + price + ", queryCondn="
				+ queryCondn + ", purInvId=" + purInvId + ", finYrId=" + finYrId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", invoiceNo=" + invoiceNo + ", purOrderNo=" + purOrderNo
				+ ", distributorName=" + distributorName + ", distributorId=" + distributorId + ", upToDate=" + upToDate
				+ ", noOfExpiryMonth=" + noOfExpiryMonth + ", saleId=" + saleId + ", custId=" + custId + ", custName="
				+ custName + ", custPh=" + custPh + ", status=" + status + ", doctorName=" + doctorName + ", doctorPh="
				+ doctorPh + ", asOnDate=" + asOnDate + ", deletedBy=" + deletedBy + ", createdBy=" + createdBy
				+ ", manuId=" + manuId + ", contentId=" + contentId + ", batchNo=" + batchNo + ", expiryDateFormat="
				+ expiryDateFormat + ", mrp=" + mrp + ", saleReturnId=" + saleReturnId + ", noOfMonthBefore="
				+ noOfMonthBefore + ", manufacturerCode=" + manufacturerCode + ", purchaseReturnId=" + purchaseReturnId
				+ ", paymentId=" + paymentId + ", paymentDate=" + paymentDate + ", expiryId=" + expiryId + ", taxName="
				+ taxName + ", taxId=" + taxId + ", isGroup=" + isGroup + ", taxPer=" + taxPer + ", noOfRows="
				+ noOfRows + ", frmDate=" + frmDate + ", toDate=" + toDate + ", sku=" + sku + ", hsnCode=" + hsnCode
				+ ", retType=" + retType + ", purchaseOrderId=" + purchaseOrderId + ", invDate=" + invDate
				+ ", poGenType=" + poGenType + ", noteLineOne=" + noteLineOne + ", noteLineTwo=" + noteLineTwo
				+ ", isRePrint=" + isRePrint + ", printCount=" + printCount + ", remarks=" + remarks + ", barCode="
				+ barCode + ", lastSaleDays=" + lastSaleDays + ", comingPurchaseDays=" + comingPurchaseDays
				+ ", noOfDays=" + noOfDays + ", marketerCode=" + marketerCode + ", accntGrpName=" + accntGrpName
				+ ", marketerName=" + marketerName + ", purchaseRate=" + purchaseRate + ", saleRate=" + saleRate
				+ ", launchDate=" + launchDate + ", discontinueDate=" + discontinueDate + ", countryId=" + countryId
				+ ", stateId=" + stateId + ", cityId=" + cityId + ", zoneId=" + zoneId + ", districtName="
				+ districtName + ", variantTypeId=" + variantTypeId + ", variantTypeName=" + variantTypeName
				+ ", menuID=" + menuID + ", retailTypeID=" + retailTypeID + ", listedMrp=" + listedMrp + ", catId="
				+ catId + ", billNo=" + billNo + ", expDate=" + expDate + ", saleDetailsId=" + saleDetailsId + ", qs="
				+ qs + ", mulSeriesPrefix=" + mulSeriesPrefix + ", groupCode=" + groupCode + ", accountID=" + accountID
				+ ", referenceID=" + referenceID + ", qryCondition=" + qryCondition + ", purchaseIds=" + purchaseIds
				+ ", itemCode=" + itemCode + "]";
	}


}

/**
 * rajarshi
 */

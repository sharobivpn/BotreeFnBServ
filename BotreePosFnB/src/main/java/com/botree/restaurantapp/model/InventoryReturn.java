package com.botree.restaurantapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.botree.restaurantapp.model.account.JournalEntry;
import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

/**
 * Entity implementation class for Entity: im_t_raw_return
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_raw_purchase_return")
public class InventoryReturn implements Serializable {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@Expose
	@Column(name = "user_id")
	private String userId;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Column(name = "vendor_id")
	private int vendorId;
	
	@Expose
	@Column(name = "return_type_id")
	private int returnTypeId;

	@Expose
	@Column(name = "edp_id")
	private int edpId;

	@Expose
	@Column(name = "bill_no")
	private String billNo;

	@Expose
	@Column(name = "bill_date")
	private String billDate;
	
	@Expose
	@Column(name = "item_total")
	private Double itemTotal;
	
	@Expose
	@Column(name = "dis_per")
	private Double disPer;
	
	@Expose
	@Column(name = "dis_amt")
	private Double disAmt;
	
	@Expose
	@Column(name = "tax_amt")
	private Double taxAmt;
	
	@Expose
	@Column(name = "round_off_amt")
	private Double roundOffAmt;
	
	@Expose
	@Column(name = "net_amt")
	private Double netAmt;
	
	@Expose
	@Column(name = "misc_charge")
	private Double miscCharge;

	@Expose
	@Column(name = "approved")
	private String approved;

	@Expose
	@Transient
	private String vendorName;

	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

	@Expose
	@Column(name = "created_by")
	private String createdBy;

	@Expose
	@Column(name = "created_date")
	private String createdDate;

	@Expose
	@Column(name = "updated_by")
	private String updatedBy;

	@Expose
	@Column(name = "updated_date")
	private String updatedDate;

	@Expose
	@OneToMany(mappedBy = "inventoryReturn", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<InventoryReturnItem> inventoryReturnItems;
	
	@Expose
	@Transient
	private double totGrossAmt;
	
	//added for accounting 17.07.2018
			@Expose
			@Transient
			private int duties_ledger_id;
			@Expose
			@Transient
			private int round_ledger_id;
			@Expose
			@Transient
			private int purchase_ledger_id;
			@Expose
			@Transient
			private int discount_ledger_id;
			@Expose
			@Transient
			private int credior_ledger_id;
			@Expose
			@Transient
			private int missllenous_ledger_id;
			@Expose
			@MapToData(columnAliases="qs")
			@Transient
			private String qs;
			@Expose
			@Transient
			private String is_account;
			@Expose
			@Transient
			List<JournalEntry> jes;

	private static final long serialVersionUID = 1L;

	
	public double getTotGrossAmt() {
		return totGrossAmt;
	}

	public void setTotGrossAmt(double totGrossAmt) {
		this.totGrossAmt = totGrossAmt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<InventoryReturnItem> getInventoryReturnItems() {
		return inventoryReturnItems;
	}

	public void setInventoryReturnItems(
			List<InventoryReturnItem> inventoryReturnItems) {
		this.inventoryReturnItems = inventoryReturnItems;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
	

	public int getReturnTypeId() {
		return returnTypeId;
	}

	public void setReturnTypeId(int returnTypeId) {
		this.returnTypeId = returnTypeId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillDate() {
		return billDate;
	}
	
	

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public int getEdpId() {
		return edpId;
	}

	public void setEdpId(int edpId) {
		this.edpId = edpId;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public Double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public Double getDisPer() {
		return disPer;
	}

	public void setDisPer(Double disPer) {
		this.disPer = disPer;
	}

	public Double getDisAmt() {
		return disAmt;
	}

	public void setDisAmt(Double disAmt) {
		this.disAmt = disAmt;
	}

	public Double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Double getRoundOffAmt() {
		return roundOffAmt;
	}

	public void setRoundOffAmt(Double roundOffAmt) {
		this.roundOffAmt = roundOffAmt;
	}

	public Double getNetAmt() {
		return netAmt;
	}

	public void setNetAmt(Double netAmt) {
		this.netAmt = netAmt;
	}

	public Double getMiscCharge() {
		return miscCharge;
	}

	public void setMiscCharge(Double miscCharge) {
		this.miscCharge = miscCharge;
	}

	public int getDuties_ledger_id() {
		return duties_ledger_id;
	}

	public void setDuties_ledger_id(int duties_ledger_id) {
		this.duties_ledger_id = duties_ledger_id;
	}

	public int getRound_ledger_id() {
		return round_ledger_id;
	}

	public void setRound_ledger_id(int round_ledger_id) {
		this.round_ledger_id = round_ledger_id;
	}

	public int getPurchase_ledger_id() {
		return purchase_ledger_id;
	}

	public void setPurchase_ledger_id(int purchase_ledger_id) {
		this.purchase_ledger_id = purchase_ledger_id;
	}

	public int getDiscount_ledger_id() {
		return discount_ledger_id;
	}

	public void setDiscount_ledger_id(int discount_ledger_id) {
		this.discount_ledger_id = discount_ledger_id;
	}

	public int getCredior_ledger_id() {
		return credior_ledger_id;
	}

	public void setCredior_ledger_id(int credior_ledger_id) {
		this.credior_ledger_id = credior_ledger_id;
	}

	public int getMissllenous_ledger_id() {
		return missllenous_ledger_id;
	}

	public void setMissllenous_ledger_id(int missllenous_ledger_id) {
		this.missllenous_ledger_id = missllenous_ledger_id;
	}

	public String getQs() {
		return qs;
	}

	public void setQs(String qs) {
		this.qs = qs;
	}

	public String getIs_account() {
		return is_account;
	}

	public void setIs_account(String is_account) {
		this.is_account = is_account;
	}

	public List<JournalEntry> getJes() {
		return jes;
	}

	public void setJes(List<JournalEntry> jes) {
		this.jes = jes;
	}

	@Override
	public String toString() {
		return "InventoryReturn [id=" + id + ", date=" + date + ", userId=" + userId + ", storeId=" + storeId
				+ ", vendorId=" + vendorId + ", returnTypeId=" + returnTypeId + ", edpId=" + edpId + ", billNo="
				+ billNo + ", billDate=" + billDate + ", itemTotal=" + itemTotal + ", disPer=" + disPer + ", disAmt="
				+ disAmt + ", taxAmt=" + taxAmt + ", roundOffAmt=" + roundOffAmt + ", netAmt=" + netAmt
				+ ", miscCharge=" + miscCharge + ", approved=" + approved + ", vendorName=" + vendorName
				+ ", deleteFlag=" + deleteFlag + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", inventoryReturnItems="
				+ inventoryReturnItems + ", totGrossAmt=" + totGrossAmt + ", duties_ledger_id=" + duties_ledger_id
				+ ", round_ledger_id=" + round_ledger_id + ", purchase_ledger_id=" + purchase_ledger_id
				+ ", discount_ledger_id=" + discount_ledger_id + ", credior_ledger_id=" + credior_ledger_id
				+ ", missllenous_ledger_id=" + missllenous_ledger_id + ", qs=" + qs + ", is_account=" + is_account
				+ ", jes=" + jes + "]";
	}

}
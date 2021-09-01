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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.botree.restaurantapp.model.account.JournalEntry;
import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

/**
 * Entity implementation class for Entity: im_t_raw_stock_in
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_raw_stock_in")
public class InventoryStockIn implements Serializable {
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
	@Column(name = "edp_id")
	private int edpId;

	@Expose
	@Column(name = "closed")
	private String closed;

	@Expose
	@Column(name = "bill_no")
	private String billNo;

	@Expose
	@Column(name = "total_quantity")
	private Double totalQuantity;

	@Expose
	@Column(name = "total_price")
	private double totalPrice;

	@Expose
	@Column(name = "misc_charge")
	private double miscCharge;

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
	@OneToMany(mappedBy = "inventoryStockIn", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<InventoryStockInItem> inventoryStockInItems;

	@Expose
	@Column(name = "bill_date")
	private String billDate;
	
	
	//newly added for accounting 26.04.2018
		@Expose
		@Column(name = "po_id")
		private int poId;

		@Expose
		@Column(name = "item_total")
		private double itemTotal;
		
		@Expose
		@Column(name = "disc_per")
		private double discPer;
		
		@Expose
		@Column(name = "disc_amt")
		private double discAmt;
		
		@Expose
		@Column(name = "tax_amt")
		private double taxAmt;
		
		@Expose
		@Column(name = "round_off_amt")
		private Double roundOffAmt;

		
	//added for accounting 10.07.2018
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

		@Expose
		@Transient
		private double totGrossAmt;


	private static final long serialVersionUID = 1L;

	
	public double getTotGrossAmt() {
		return totGrossAmt;
	}

	@XmlElement
	public void setTotGrossAmt(double totGrossAmt) {
		this.totGrossAmt = totGrossAmt;
	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	@XmlElement
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	@XmlElement
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	@XmlElement
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	@XmlElement
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	@XmlElement
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	@XmlElement
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getDate() {
		return date;
	}

	@XmlElement
	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	@XmlElement
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<InventoryStockInItem> getInventoryStockInItems() {
		return inventoryStockInItems;
	}

	public void setInventoryStockInItems(
			List<InventoryStockInItem> inventoryStockInItems) {
		this.inventoryStockInItems = inventoryStockInItems;
	}

	public int getVendorId() {
		return vendorId;
	}

	@XmlElement
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	@XmlElement
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getClosed() {
		return closed;
	}

	@XmlElement
	public void setClosed(String closed) {
		this.closed = closed;
	}

	public String getBillNo() {
		return billNo;
	}

	@XmlElement
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	@XmlElement
	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	@XmlElement
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getMiscCharge() {
		return miscCharge;
	}

	@XmlElement
	public void setMiscCharge(double miscCharge) {
		this.miscCharge = miscCharge;
	}

	public String getBillDate() {
		return billDate;
	}

	@XmlElement
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public int getEdpId() {
		return edpId;
	}

	@XmlElement
	public void setEdpId(int edpId) {
		this.edpId = edpId;
	}

	public int getPoId() {
		return poId;
	}

	@XmlElement
	public void setPoId(int poId) {
		this.poId = poId;
	}

	public double getItemTotal() {
		return itemTotal;
	}

	@XmlElement
	public void setItemTotal(double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public double getDiscPer() {
		return discPer;
	}

	@XmlElement
	public void setDiscPer(double discPer) {
		this.discPer = discPer;
	}

	public double getDiscAmt() {
		return discAmt;
	}

	@XmlElement
	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	@XmlElement
	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Double getRoundOffAmt() {
		return roundOffAmt;
	}

	@XmlElement
	public void setRoundOffAmt(Double roundOffAmt) {
		this.roundOffAmt = roundOffAmt;
	}

	public int getDuties_ledger_id() {
		return duties_ledger_id;
	}

	@XmlElement
	public void setDuties_ledger_id(int duties_ledger_id) {
		this.duties_ledger_id = duties_ledger_id;
	}

	public int getRound_ledger_id() {
		return round_ledger_id;
	}

	@XmlElement
	public void setRound_ledger_id(int round_ledger_id) {
		this.round_ledger_id = round_ledger_id;
	}

	public int getPurchase_ledger_id() {
		return purchase_ledger_id;
	}

	@XmlElement
	public void setPurchase_ledger_id(int purchase_ledger_id) {
		this.purchase_ledger_id = purchase_ledger_id;
	}

	public int getDiscount_ledger_id() {
		return discount_ledger_id;
	}

	@XmlElement
	public void setDiscount_ledger_id(int discount_ledger_id) {
		this.discount_ledger_id = discount_ledger_id;
	}

	public int getCredior_ledger_id() {
		return credior_ledger_id;
	}

	@XmlElement
	public void setCredior_ledger_id(int credior_ledger_id) {
		this.credior_ledger_id = credior_ledger_id;
	}

	public int getMissllenous_ledger_id() {
		return missllenous_ledger_id;
	}

	@XmlElement
	public void setMissllenous_ledger_id(int missllenous_ledger_id) {
		this.missllenous_ledger_id = missllenous_ledger_id;
	}

	public String getQs() {
		return qs;
	}

	@XmlElement
	public void setQs(String qs) {
		this.qs = qs;
	}

	public String getIs_account() {
		return is_account;
	}

	@XmlElement
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
		return "InventoryStockIn [id=" + id + ", date=" + date + ", userId=" + userId + ", storeId=" + storeId
				+ ", vendorId=" + vendorId + ", edpId=" + edpId + ", closed=" + closed + ", billNo=" + billNo
				+ ", totalQuantity=" + totalQuantity + ", totalPrice=" + totalPrice + ", miscCharge=" + miscCharge
				+ ", vendorName=" + vendorName + ", deleteFlag=" + deleteFlag + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", inventoryStockInItems=" + inventoryStockInItems + ", billDate=" + billDate + ", poId=" + poId
				+ ", itemTotal=" + itemTotal + ", discPer=" + discPer + ", discAmt=" + discAmt + ", taxAmt=" + taxAmt
				+ ", roundOffAmt=" + roundOffAmt + ", duties_ledger_id=" + duties_ledger_id + ", round_ledger_id="
				+ round_ledger_id + ", purchase_ledger_id=" + purchase_ledger_id + ", discount_ledger_id="
				+ discount_ledger_id + ", credior_ledger_id=" + credior_ledger_id + ", missllenous_ledger_id="
				+ missllenous_ledger_id + ", qs=" + qs + ", is_account=" + is_account + ", jes=" + jes
				+ ", totGrossAmt=" + totGrossAmt + "]";
	}

}
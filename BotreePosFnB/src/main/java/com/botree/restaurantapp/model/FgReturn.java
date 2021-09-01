/**
 * 
 */
package com.botree.restaurantapp.model;

/**
 * @author Habib
 *
 */
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

import com.google.gson.annotations.Expose;


/**
 * Entity implementation class for Entity: im_t_fg_return
 * 
 */
@XmlRootElement
@Entity
@Table(name = "im_t_fg_purchase_return")
public class FgReturn implements Serializable {
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
	private Double itemTotal=0.0;
	
	@Expose
	@Column(name = "dis_per")
	private Double disPer=0.0;
	
	@Expose
	@Column(name = "dis_amt")
	private Double disAmt=0.0;
	
	@Expose
	@Column(name = "vat_amt")
	private Double vatAmt=0.0;
	
	@Expose
	@Column(name = "service_tax_amt")
	private Double serviceTaxAmt=0.0;
	
	@Expose
	@Column(name = "round_off_amt")
	private Double roundOffAmt=0.0;
	
	@Expose
	@Column(name = "total_price")
	private Double totalPrice=0.0;

	@Expose
	@Column(name = "approved")
	private String approved="N";

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
	@OneToMany(mappedBy = "fgReturn", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<FgReturnItem> fgReturnItems;

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
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

	public int getEdpId() {
		return edpId;
	}

	public void setEdpId(int edpId) {
		this.edpId = edpId;
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

	public Double getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(Double vatAmt) {
		this.vatAmt = vatAmt;
	}

	public Double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(Double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public Double getRoundOffAmt() {
		return roundOffAmt;
	}

	public void setRoundOffAmt(Double roundOffAmt) {
		this.roundOffAmt = roundOffAmt;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public List<FgReturnItem> getFgReturnItems() {
		return fgReturnItems;
	}

	public void setFgReturnItems(List<FgReturnItem> fgReturnItems) {
		this.fgReturnItems = fgReturnItems;
	}

	@Override
	public String toString() {
		return "FgReturn [id=" + id + ", date=" + date + ", storeId=" + storeId + ", vendorId=" + vendorId
				+ ", returnTypeId=" + returnTypeId + ", edpId=" + edpId + ", billNo=" + billNo + ", billDate="
				+ billDate + ", itemTotal=" + itemTotal + ", disPer=" + disPer + ", disAmt=" + disAmt + ", vatAmt="
				+ vatAmt + ", serviceTaxAmt=" + serviceTaxAmt + ", roundOffAmt=" + roundOffAmt + ", totalPrice="
				+ totalPrice + ", approved=" + approved + ", vendorName=" + vendorName + ", deleteFlag=" + deleteFlag
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", fgReturnItems=" + fgReturnItems + "]";
	}

	
}

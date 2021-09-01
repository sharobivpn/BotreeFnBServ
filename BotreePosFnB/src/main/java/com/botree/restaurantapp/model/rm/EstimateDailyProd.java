package com.botree.restaurantapp.model.rm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "im_t_estimate_daily_prod_item")
public class EstimateDailyProd implements Serializable {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@ManyToOne
	@JoinColumn(name = "estimate_type_id")
	private EstimateType estimateType;

	@Expose
	@Column(name = "store_id")
	private int storeId;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@Expose
	@Column(name = "total")
	private double total;

	@Expose
	@Column(name = "approved")
	private String approved;

	@Expose
	@Column(name = "approved_by")
	private String approvedBy;

	@Expose
	@Column(name = "requisition_po_status")
	private String requisitionPoStatus;

	@Expose
	@Column(name = "requisition_po_status_by")
	private String requisitionPoStatusBy;

	@Expose
	@Column(name = "fg_stock_in_status")
	private String fgStockInStatus;

	@Expose
	@Column(name = "fg_stock_in_status_by")
	private String fgStockInStatusBy;

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

	// @Expose
	// @OneToMany(mappedBy = "inventoryPurchaseOrder")
	// @Where(clause = "delete_flag='N'")
	//@Expose
	
	@OneToMany(mappedBy = "estimateDailyProdId", fetch = FetchType.EAGER)
	@Where(clause = "delete_flag='N'")
	private List<EstimateDailyProdItem> estimateDailyProdItems;

	@Expose
	@Transient
	private String dateText;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "requisition_po_date")
	private Date requisitionPoDate;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fg_stock_in_date")
	private Date fgStockInDate;
	
	/*@Expose
	@Transient
	private int poId;*/
	//added on 10.05.2018
	@Expose
	@Column(name = "po_id")
	private int poId;
	
	@Expose
	@Transient
	private int fgStockInId;
	
	@Expose
	@Transient
	private int rawStockOutId;
	
	@Expose
	@Transient
	private int estimatedQuantity;
	
	@Expose
	@Transient
	private double totalEstimatedQty;
	
	@Expose
	@Transient
	private double totalStockInQty;
	
	@Expose
	@Column(name = "raw_stock_out_status")
	private String rawStockOutStatus;

	@Expose
	@Column(name = "raw_stock_out_status_by")
	private String rawStockOutStatusBy;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "raw_stock_out_date")
	private Date rawStockOutDate;

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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getRequisitionPoStatus() {
		return requisitionPoStatus;
	}

	public void setRequisitionPoStatus(String requisitionPoStatus) {
		this.requisitionPoStatus = requisitionPoStatus;
	}

	public String getRequisitionPoStatusBy() {
		return requisitionPoStatusBy;
	}

	public void setRequisitionPoStatusBy(String requisitionPoStatusBy) {
		this.requisitionPoStatusBy = requisitionPoStatusBy;
	}

	public String getFgStockInStatus() {
		return fgStockInStatus;
	}

	public void setFgStockInStatus(String fgStockInStatus) {
		this.fgStockInStatus = fgStockInStatus;
	}

	public String getFgStockInStatusBy() {
		return fgStockInStatusBy;
	}

	public void setFgStockInStatusBy(String fgStockInStatusBy) {
		this.fgStockInStatusBy = fgStockInStatusBy;
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

	public EstimateType getEstimateType() {
		return estimateType;
	}

	public void setEstimateType(EstimateType estimateType) {
		this.estimateType = estimateType;
	}

	public List<EstimateDailyProdItem> getEstimateDailyProdItems() {
		return estimateDailyProdItems;
	}

	public void setEstimateDailyProdItems(
			List<EstimateDailyProdItem> estimateDailyProdItems) {
		this.estimateDailyProdItems = estimateDailyProdItems;
	}

	public String getDateText() {
		return dateText;
	}

	public void setDateText(String dateText) {
		this.dateText = dateText;
	}

	public Date getRequisitionPoDate() {
		return requisitionPoDate;
	}

	public void setRequisitionPoDate(Date requisitionPoDate) {
		this.requisitionPoDate = requisitionPoDate;
	}

	public Date getFgStockInDate() {
		return fgStockInDate;
	}

	public void setFgStockInDate(Date fgStockInDate) {
		this.fgStockInDate = fgStockInDate;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public int getFgStockInId() {
		return fgStockInId;
	}

	public void setFgStockInId(int fgStockInId) {
		this.fgStockInId = fgStockInId;
	}

	public int getEstimatedQuantity() {
		return estimatedQuantity;
	}

	public void setEstimatedQuantity(int estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}

	public double getTotalEstimatedQty() {
		return totalEstimatedQty;
	}

	public void setTotalEstimatedQty(double totalEstimatedQty) {
		this.totalEstimatedQty = totalEstimatedQty;
	}

	public double getTotalStockInQty() {
		return totalStockInQty;
	}

	public void setTotalStockInQty(double totalStockInQty) {
		this.totalStockInQty = totalStockInQty;
	}

	public String getRawStockOutStatus() {
		return rawStockOutStatus;
	}

	public void setRawStockOutStatus(String rawStockOutStatus) {
		this.rawStockOutStatus = rawStockOutStatus;
	}

	public String getRawStockOutStatusBy() {
		return rawStockOutStatusBy;
	}

	public void setRawStockOutStatusBy(String rawStockOutStatusBy) {
		this.rawStockOutStatusBy = rawStockOutStatusBy;
	}

	public Date getRawStockOutDate() {
		return rawStockOutDate;
	}

	public void setRawStockOutDate(Date rawStockOutDate) {
		this.rawStockOutDate = rawStockOutDate;
	}

	public int getRawStockOutId() {
		return rawStockOutId;
	}

	public void setRawStockOutId(int rawStockOutId) {
		this.rawStockOutId = rawStockOutId;
	}

	@Override
	public String toString() {
		return "EstimateDailyProd [id=" + id + ", estimateType=" + estimateType + ", storeId=" + storeId + ", date="
				+ date + ", total=" + total + ", approved=" + approved + ", approvedBy=" + approvedBy
				+ ", requisitionPoStatus=" + requisitionPoStatus + ", requisitionPoStatusBy=" + requisitionPoStatusBy
				+ ", fgStockInStatus=" + fgStockInStatus + ", fgStockInStatusBy=" + fgStockInStatusBy + ", deleteFlag="
				+ deleteFlag + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", estimateDailyProdItems=" + estimateDailyProdItems + ", dateText="
				+ dateText + ", requisitionPoDate=" + requisitionPoDate + ", fgStockInDate=" + fgStockInDate + ", poId="
				+ poId + ", fgStockInId=" + fgStockInId + ", rawStockOutId=" + rawStockOutId + ", estimatedQuantity="
				+ estimatedQuantity + ", totalEstimatedQty=" + totalEstimatedQty + ", totalStockInQty="
				+ totalStockInQty + ", rawStockOutStatus=" + rawStockOutStatus + ", rawStockOutStatusBy="
				+ rawStockOutStatusBy + ", rawStockOutDate=" + rawStockOutDate + "]";
	}
	

}

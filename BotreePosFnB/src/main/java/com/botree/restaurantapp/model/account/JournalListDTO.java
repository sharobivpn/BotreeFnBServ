package com.botree.restaurantapp.model.account;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

@XmlRootElement
public class JournalListDTO {
	@Expose
	private String entrydate;

	@Expose
	@MapToData(columnAliases = { "narration"})
	private String naration;
	@Expose
	private List<JournalDTO> journallist;

	@Expose
	private double drtotal;
	@Expose
	private double crtotal;
	@Expose
	private int companyId;

	@Expose
	private int storeId;

	@Expose
	private int createdBy;
	@Expose
	private String lang;
	@Expose
	private int finyrId;
	@Expose
	private String qs;
	@Expose
	@MapToData(columnAliases = { "journal_type_id"})
	private int journal_type_id;
	
	@Expose
	private String finyrCode;
	
	
	//NEW
	@Expose
	@MapToData(columnAliases = { "voucher_no"})
	private String voucher_no;
	
	@Expose
	@MapToData(columnAliases = { "description"})
	private String description;
	
	@Expose
	@MapToData(columnAliases = { "id"})
	private int id;
	@Expose
	@MapToData(columnAliases = { "inv_no"})
	private String inv_no;
	@Expose
	@MapToData(columnAliases = { "inv_date"})
	private String inv_date;
	
	//new
	@Expose
	@MapToData(columnAliases = { "journal_type"})
	private String entry_type;
	
	public JournalListDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getEntrydate() {
		return entrydate;
	}

	@XmlElement
	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public String getNaration() {
		return naration;
	}

	@XmlElement
	public void setNaration(String naration) {
		this.naration = naration;
	}

	public List<JournalDTO> getJournallist() {
		return journallist;
	}

	public void setJournallist(List<JournalDTO> journallist) {
		this.journallist = journallist;
	}

	public double getDrtotal() {
		return drtotal;
	}

	@XmlElement
	public void setDrtotal(double drtotal) {
		this.drtotal = drtotal;
	}

	public double getCrtotal() {
		return crtotal;
	}

	@XmlElement
	public void setCrtotal(double crtotal) {
		this.crtotal = crtotal;
	}

	public int getCompanyId() {
		return companyId;
	}

	@XmlElement
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getStoreId() {
		return storeId;
	}

	@XmlElement
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	@XmlElement
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getLang() {
		return lang;
	}

	@XmlElement
	public void setLang(String lang) {
		this.lang = lang;
	}

	public int getFinyrId() {
		return finyrId;
	}

	@XmlElement
	public void setFinyrId(int finyrId) {
		this.finyrId = finyrId;
	}

	public String getQs() {
		return qs;
	}

	@XmlElement
	public void setQs(String qs) {
		this.qs = qs;
	}

	public int getJournal_type_id() {
		return journal_type_id;
	}

	@XmlElement
	public void setJournal_type_id(int journal_type_id) {
		this.journal_type_id = journal_type_id;
	}

	public String getFinyrCode() {
		return finyrCode;
	}

	@XmlElement
	public void setFinyrCode(String finyrCode) {
		this.finyrCode = finyrCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInv_no() {
		return inv_no;
	}

	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}

	public String getInv_date() {
		return inv_date;
	}

	public void setInv_date(String inv_date) {
		this.inv_date = inv_date;
	}

	public String getVoucher_no() {
		return voucher_no;
	}

	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}

	public String getEntry_type() {
		return entry_type;
	}

	public void setEntry_type(String entry_type) {
		this.entry_type = entry_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "JournalListDTO [entrydate=" + entrydate + ", naration=" + naration + ", journallist=" + journallist
				+ ", drtotal=" + drtotal + ", crtotal=" + crtotal + ", companyId=" + companyId + ", storeId=" + storeId
				+ ", createdBy=" + createdBy + ", lang=" + lang + ", finyrId=" + finyrId + ", qs=" + qs
				+ ", journal_type_id=" + journal_type_id + ", finyrCode=" + finyrCode + ", voucher_no=" + voucher_no
				+ ", description=" + description + ", id=" + id + ", inv_no=" + inv_no + ", inv_date=" + inv_date
				+ ", entry_type=" + entry_type + "]";
	}


	public JournalListDTO(String entrydate, String naration, List<JournalDTO> journallist, double drtotal,
			double crtotal, int companyId, int storeId, int createdBy, String lang, int finyrId, String qs,
			int journal_type_id, String finyrCode, String voucher_no, String description, int id, String inv_no,
			String inv_date, String entry_type) {
		super();
		this.entrydate = entrydate;
		this.naration = naration;
		this.journallist = journallist;
		this.drtotal = drtotal;
		this.crtotal = crtotal;
		this.companyId = companyId;
		this.storeId = storeId;
		this.createdBy = createdBy;
		this.lang = lang;
		this.finyrId = finyrId;
		this.qs = qs;
		this.journal_type_id = journal_type_id;
		this.finyrCode = finyrCode;
		this.voucher_no = voucher_no;
		this.description = description;
		this.id = id;
		this.inv_no = inv_no;
		this.inv_date = inv_date;
		this.entry_type = entry_type;
	}

}

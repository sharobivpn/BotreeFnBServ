package com.botree.restaurantapp.model.account;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

@XmlRootElement
public class JournalDTO {
	@Expose
	@MapToData(columnAliases = { "account_id"})
	private int account_id;
	@Expose
	@MapToData(columnAliases = { "dr_amount"})
	private double dr_amount;
	@Expose
	@MapToData(columnAliases = { "cr_amount"})
	private double cr_amount;
	@Expose
	private double voucher_no;
	
	@Expose
	@MapToData(columnAliases = { "id"})
	private int id;
	@Expose
	@MapToData(columnAliases = { "inv_no"})
	private String inv_no;
	@Expose
	@MapToData(columnAliases = { "inv_date"})
	private String inv_date;
	@Expose
	@MapToData(columnAliases = { "narration"})
	private String narration;
	@Expose
	@MapToData(columnAliases = { "account_name"})
	private String account_name;
	@Expose
	@MapToData(columnAliases = { "account_group_id"})
	private int account_group_id;
	@Expose
	@MapToData(columnAliases = { "description"})
	private String description;
	
	
	public JournalDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getAccount_id() {
		return account_id;
	}

	@XmlElement
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public double getDr_amount() {
		return dr_amount;
	}

	@XmlElement
	public void setDr_amount(double dr_amount) {
		this.dr_amount = dr_amount;
	}

	public double getCr_amount() {
		return cr_amount;
	}

	@XmlElement
	public void setCr_amount(double cr_amount) {
		this.cr_amount = cr_amount;
	}

	public double getVoucher_no() {
		return voucher_no;
	}

	@XmlElement
	public void setVoucher_no(double voucher_no) {
		this.voucher_no = voucher_no;
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

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public int getAccount_group_id() {
		return account_group_id;
	}

	public void setAccount_group_id(int account_group_id) {
		this.account_group_id = account_group_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "JournalDTO [account_id=" + account_id + ", dr_amount=" + dr_amount + ", cr_amount=" + cr_amount
				+ ", voucher_no=" + voucher_no + ", id=" + id + ", inv_no=" + inv_no + ", inv_date=" + inv_date
				+ ", narration=" + narration + ", account_name=" + account_name + ", account_group_id="
				+ account_group_id + ", description=" + description + "]";
	}

	public JournalDTO(int account_id, double dr_amount, double cr_amount, double voucher_no, int id, String inv_no,
			String inv_date, String narration, String account_name, int account_group_id, String description) {
		super();
		this.account_id = account_id;
		this.dr_amount = dr_amount;
		this.cr_amount = cr_amount;
		this.voucher_no = voucher_no;
		this.id = id;
		this.inv_no = inv_no;
		this.inv_date = inv_date;
		this.narration = narration;
		this.account_name = account_name;
		this.account_group_id = account_group_id;
		this.description = description;
	}

}

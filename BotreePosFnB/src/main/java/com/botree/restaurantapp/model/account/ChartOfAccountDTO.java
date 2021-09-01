package com.botree.restaurantapp.model.account;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

@XmlRootElement
public class ChartOfAccountDTO {
	 @Expose
	 @MapToData(columnAliases = { "type_name" })
	 private String type_name;
	 @Expose
	 @MapToData(columnAliases = { "head_id" })
	 private int head_id;
	 @Expose
	 @MapToData(columnAliases = { "group_name" })
	 private String group_name;
	 @Expose
	 @MapToData(columnAliases = { "group_id" })
	 private int group_id;
	 @Expose
	 @MapToData(columnAliases = { "account_name" })
	 private String account_name;
	 @Expose
	 @MapToData(columnAliases = { "account_id" })
	 private int account_id;
	 @Expose
	 @MapToData(columnAliases = { "acc_opening_amt" })
	 private Double acc_opening_amt;
	 
	 @Expose
	 @MapToData(columnAliases = { "balance_type" })
	 private String balance_type;
	 
	 @Expose
	 @MapToData(columnAliases = { "current_balance" })
	 private Double current_balance;
	 
	 public ChartOfAccountDTO() { }
	 
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public int getHead_id() {
		return head_id;
	}
	public void setHead_id(int head_id) {
		this.head_id = head_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public Double getAcc_opening_amt() {
		return acc_opening_amt;
	}

	public void setAcc_opening_amt(Double acc_opening_amt) {
		this.acc_opening_amt = acc_opening_amt;
	}

	public String getBalance_type() {
		return balance_type;
	}

	public void setBalance_type(String balance_type) {
		this.balance_type = balance_type;
	}

	public Double getCurrent_balance() {
		return current_balance;
	}

	public void setCurrent_balance(Double current_balance) {
		this.current_balance = current_balance;
	}

	@Override
	public String toString() {
		return "ChartOfAccountDTO [type_name=" + type_name + ", head_id=" + head_id + ", group_name=" + group_name
				+ ", group_id=" + group_id + ", account_name=" + account_name + ", account_id=" + account_id
				+ ", acc_opening_amt=" + acc_opening_amt + ", balance_type=" + balance_type + ", current_balance="
				+ current_balance + "]";
	}

	public ChartOfAccountDTO(String type_name, int head_id, String group_name, int group_id, String account_name,
			int account_id, Double acc_opening_amt, String balance_type, Double current_balance) {
		super();
		this.type_name = type_name;
		this.head_id = head_id;
		this.group_name = group_name;
		this.group_id = group_id;
		this.account_name = account_name;
		this.account_id = account_id;
		this.acc_opening_amt = acc_opening_amt;
		this.balance_type = balance_type;
		this.current_balance = current_balance;
	}

}

package com.botree.restaurantapp.model.account;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

@Entity
//@Table(name="para_journal_type")
@XmlRootElement
public class ParaJournalTypeMaster {
	
	@Expose
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Expose
	@MapToData(columnAliases = { "journal_type"})
	private String journal_type;
	
	@Expose
	@MapToData(columnAliases = { "description"})
	private String description;
	
	@Expose
	@MapToData(columnAliases = { "journal_prefix"})
	private String journal_prefix;
	
	@Expose
	private int company_id;
	
	@Expose
	private int store_id;
	
	@Expose
	@Transient
	@MapToData(columnAliases = { "journal_type_id"})
	private int journal_type_id;
	
	public ParaJournalTypeMaster() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJournal_type() {
		return journal_type;
	}

	public void setJournal_type(String journal_type) {
		this.journal_type = journal_type;
	}

	public String getJournal_prefix() {
		return journal_prefix;
	}

	public void setJournal_prefix(String journal_prefix) {
		this.journal_prefix = journal_prefix;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getJournal_type_id() {
		return journal_type_id;
	}

	public void setJournal_type_id(int journal_type_id) {
		this.journal_type_id = journal_type_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ParaJournalTypeMaster [id=" + id + ", journal_type=" + journal_type + ", description=" + description
				+ ", journal_prefix=" + journal_prefix + ", company_id=" + company_id + ", store_id=" + store_id
				+ ", journal_type_id=" + journal_type_id + "]";
	}

	public ParaJournalTypeMaster(int id, String journal_type, String description, String journal_prefix, int company_id,
			int store_id, int journal_type_id) {
		super();
		this.id = id;
		this.journal_type = journal_type;
		this.description = description;
		this.journal_prefix = journal_prefix;
		this.company_id = company_id;
		this.store_id = store_id;
		this.journal_type_id = journal_type_id;
	}

}

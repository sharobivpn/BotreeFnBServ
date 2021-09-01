/**
 * 
 */
package com.botree.restaurantapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author admin
 *
 */

@XmlRootElement
@Entity
@Table(name = "st_m_sms_configuration")
public class StoreSMSConfiguration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private int storeId;
	
	@Expose
	@Column(name = "merchant_id")
	private String merchantId;
	
	@Expose
	@Column(name = "email")
	private String email;
	
	@Expose
	@Column(name = "type")
	private String type;
	
	@Expose
	@Column(name = "sub_id")
	private String subId;
	
	@Expose
	@Column(name = "request_url")
	private String requestUrl;
	
	@Expose
	@Column(name = "request_method")
	private String requestMethod;
	
	@Expose
	@Column(name = "content_type")
	private String contentType;
	
	@Expose
	@Column(name = "merchant_id_header")
	private String merchantIdHeader;
	
	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;

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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getMerchantIdHeader() {
		return merchantIdHeader;
	}

	public void setMerchantIdHeader(String merchantIdHeader) {
		this.merchantIdHeader = merchantIdHeader;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	

}

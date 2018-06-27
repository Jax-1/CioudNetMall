package com.mall.util;

/**
 * 短信
 * @author Jang
 *
 */
public class SmsEntity {
	private String accessKeyId;
	private String accessKeySecret;
	private String signname;
	private String templatecode;
	private String phone;
	private String code;
	
	public SmsEntity() {
		super();
	}
	
	public SmsEntity(String accessKeyId, String accessKeySecret, String signname, String templatecode, String phone,
			String code) {
		super();
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.signname = signname;
		this.templatecode = templatecode;
		this.phone = phone;
		this.code = code;
	}
	
	public String getSignname() {
		return signname;
	}

	public void setSignname(String signname) {
		this.signname = signname;
	}

	public String getTemplatecode() {
		return templatecode;
	}

	public void setTemplatecode(String templatecode) {
		this.templatecode = templatecode;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}

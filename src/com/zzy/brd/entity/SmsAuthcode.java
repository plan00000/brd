/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 短信校验码
 * @author zzy 2014年12月23日
 */
@Entity
@Table(name = "sms_authcode")
@DynamicUpdate(value = true)
public class SmsAuthcode extends IdEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3359251184299628871L;
	private String teleno;
	private String authcode;
	private Date sendDate;
	private short source;
	/**
	 * @return the source
	 */
	public short getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(short source) {
		this.source = source;
	}
	/**
	 * @return the teleno
	 */
	public String getTeleno() {
		return teleno;
	}
	/**
	 * @param teleno the teleno to set
	 */
	public void setTeleno(String teleno) {
		this.teleno = teleno;
	}
	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}
	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	/**
	 * @return the authcode
	 */
	public String getAuthcode() {
		return authcode;
	}
	/**
	 * @param authcode the authcode to set
	 */
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
}

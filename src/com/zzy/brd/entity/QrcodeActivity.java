package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

@Entity
@Table(name = "qrcode_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class QrcodeActivity extends IdEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -698357274236665320L;
	/** 名称*/
	private String name;
	/** 二维码*/
	private String qrcode;
	/** 添加时间*/
	private Date createTime;
	/** 扫描次数*/
	private int scanNum;
	/** 关注量*/
	private int concernNum;
	/** 留存量*/
	private int stayNum;
	/** 关注率*/
	private int concernRate;
	/** 留存率*/
	private int stayRate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getScanNum() {
		return scanNum;
	}
	public void setScanNum(int scanNum) {
		this.scanNum = scanNum;
	}
	public int getConcernNum() {
		return concernNum;
	}
	public void setConcernNum(int concernNum) {
		this.concernNum = concernNum;
	}
	public int getStayNum() {
		return stayNum;
	}
	public void setStayNum(int stayNum) {
		this.stayNum = stayNum;
	}
	public int getConcernRate() {
		return concernRate;
	}
	public void setConcernRate(int concernRate) {
		this.concernRate = concernRate;
	}
	public int getStayRate() {
		return stayRate;
	}
	public void setStayRate(int stayRate) {
		this.stayRate = stayRate;
	}
	
}

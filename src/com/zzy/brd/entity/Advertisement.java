package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-上午9:39:41
 **/
@Entity
@Table(name = "advertisement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Advertisement extends IdEntity {

	public enum AdSourceType {
		PC, /**PC官网的广告*/
		WECHAT, /**微信站广告*/
		;	
	}
	
	public enum AdPositionType {
		BANNER, /**轮播图*/
		FINANCMANAGER, /**融资经理图*/
		MIDDLE, /**PC站中间大图，滚播图下方*/
		SCROLLBLOW, /**滚动消息下方**/
		LEFTBOTTOM, /**左下*/
		RIGHTBOTTOM, /**右下**/
		;
		public String getDes() {
			if(this == BANNER)
				return "滚播图";
			if(this == FINANCMANAGER )
				return "融资经理图";
			if(this == MIDDLE)
				return "滚播图下方";
			if(this == LEFTBOTTOM)
				return "左下";
			if(this==RIGHTBOTTOM){
				return "右下";
			}
			if(this==SCROLLBLOW){
				return "滚动消息下方";
			}
			return "";	
		}		
	}
	
	public enum IsOutUrl {
		YES,
		NO
		;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5142978736092797146L;
	
	private AdSourceType source;

	private AdPositionType position;
	
	private String picurl;
	
	private IsOutUrl isouturl;
	
	private String address;	
	
	private Date createTime;
	
	public AdSourceType getSource() {
		return source;
	}

	public void setSource(AdSourceType source) {
		this.source = source;
	}

	public AdPositionType getPosition() {
		return position;
	}

	public void setPosition(AdPositionType position) {
		this.position = position;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	public IsOutUrl getIsouturl() {
		return isouturl;
	}

	public void setIsouturl(IsOutUrl isouturl) {
		this.isouturl = isouturl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

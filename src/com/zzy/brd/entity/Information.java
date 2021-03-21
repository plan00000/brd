package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-上午11:40:36
 **/
@Entity
@Table(name = "information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Information extends IdEntity {
	
	public enum InformationType {
		ACTIVITY, /**活动咨询*/
		HELP, /**帮助中心*/
		APPRENTIC, /**收徒指南*/
		ABOUT, /**关于我们*/
		AGREEMENT,/**服务协议*/
		PC_ACTIVITY, /**PC活动资讯*/
		;
		public String getDes(){
			if(this == HELP ){
				return "帮助中心";
			}
			if(this==ACTIVITY){
				return "活动资讯";
			}
			if(this==APPRENTIC){
				return "收徒指南";
			}
			if(this==AGREEMENT){
				return "服务协议";
			}
			if(this==PC_ACTIVITY){
				return "精彩资讯";
			}
			return "";
		}
	}
	
	public enum Status {
		NO,
		YES,
		DEL,
		;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347006010249502025L;
	
	@Column(name="type")
	private InformationType type;
	
	/**标题*/
	private String title;
	
	/**内容*/
	private String content;
	
	/**是否启用*/
	private Status status;
	
	/**排序号*/
	private int sortid;
	
	private Date addDate ;
	
	private Date modifyDate;
	
	private String summary;
	
	public InformationType getType() {
		return type;
	}


	public void setType(InformationType type) {
		this.type = type;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public int getSortid() {
		return sortid;
	}


	public void setSortid(int sortid) {
		this.sortid = sortid;
	}


	public Date getAddDate() {
		return addDate;
	}


	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}


	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}

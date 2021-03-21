package com.zzy.brd.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.interfaces.IUser;
import com.zzy.brd.entity.interfaces.IdEntity;

/***
 * 用户表
 * 
 * @author lzh
 *
 */
@Entity
@Table(name = "weixin_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class WeixinPost extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344363857919063737L;

	/** 状态 */
	public enum State {
		 ON ,OFF;
		public String getStr() {
			if (this == State.ON) {
				return "开";
			}
			if (this == State.OFF) {
				return "关";
			}
			return "";
		}
	}
	/** 用户类型 */
	public enum NoticeType {
		/** 贷款提交后的通知 */
		LOADSUBMIT,
		/** 贷款审核成功通知*/
		AUDITSUCCESS,
		/** 贷款审核失败通知*/
		AUDITFAILURE,
		/** 放款成功通知*/
		LOADSUCCESS,
		/** 佣金生成通知*/
		BROKERAGECREATE,
		/** 佣金放款通知*/
		BROKERAGELOAD,
		/***提现到款通知*/
		WITHDRAWARRIVAL;
		public String getStr() {
			if (this == LOADSUBMIT) {
				return "贷款提交后的通知";
			}
			if (this == AUDITSUCCESS) {
				return "贷款审核成功通知";
			}
			if (this == AUDITFAILURE) {
				return "贷款审核失败通知";
			}
			if (this == LOADSUCCESS) {
				return "放款成功通知";
			}
			if (this == BROKERAGECREATE) {
				return "佣金生成通知";
			}
			if (this == BROKERAGELOAD) {
				return "佣金放款通知";
			}
			if (this == WITHDRAWARRIVAL) {
				return "提现到款通知";
			}
			return "";
		}
	}
	/** 模板id */
	private String templateId ;

	/** 通知首段 */
	private String first ;

	/** 备注 */
	private String remark ;

	/** 状态 */
	private State state;
	
	/** 类型*/
	private NoticeType noticeType;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public NoticeType getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}
	
	
}

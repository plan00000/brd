package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 微信用户信息表
 * @author lzh 2016-9-25
 *
 */
@Entity
@Table(name = "weixin_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class WeixinUser extends IdEntity{
	
	public enum SubscribeType {
		NO,
		YES,
		;		
	}
	
	public enum SexType {
		UNKONWN,
		MALE,
		FEMALE,
		;
		public String getDes(){
			if(this==UNKONWN)
				return "未知";
			if(this==MALE)
				return "男";
			if(this==FEMALE)
				return "女";
			return "";
		}
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8057567895213711918L;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(targetEntity=User.class,mappedBy="weixinUser")
	private User user;
	
	
	private SubscribeType subscribe;
	
	private String openid;
	
	private String nickname;
	
	private SexType sex;
	
	private String city;
	
	private String country;
	
	private String province;
	
	private String language;
	
	private String headimgurl;
	
	private Date subscribeTime;
	
	private String unionid;
	
	private String remark;
	
	private int groupid;

	private String sceneids;

	public String getSceneids() {
		return sceneids;
	}

	public void setSceneids(String sceneids) {
		this.sceneids = sceneids;
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public SubscribeType getSubscribe() {
		return subscribe;
	}


	public void setSubscribe(SubscribeType subscribe) {
		this.subscribe = subscribe;
	}


	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public SexType getSex() {
		return sex;
	}


	public void setSex(SexType sex) {
		this.sex = sex;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getHeadimgurl() {
		return headimgurl;
	}


	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}


	public Date getSubscribeTime() {
		return subscribeTime;
	}


	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}


	public String getUnionid() {
		return unionid;
	}


	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public int getGroupid() {
		return groupid;
	}


	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	
}

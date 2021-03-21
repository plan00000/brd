package com.zzy.brd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-上午9:34:15
 **/
@Entity
@Table(name = "sys_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class SysInfo extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -160039755461433292L;
	
	/**icp **/
	private String icpNumber;//ICP 证书号
	
	private String qq;//客服QQ号码
	
	private String qrCodeUrl;//二维码图片存放路径
	private String apprenticeQrCodeUrl;//收徒二维码图片存放路径
	
	private String hotline;//服务热线
	
	private String cooperatePhone;//合作渠道
	
	private String shareNotify;//分享词语（微信站收徒）提示语
	
	private String scrollBall;//滚动文字设置
	
	private String notifyPhone;//订单短信通知电话
	
	private String appid;//微信公众号id
	
	private String appsecret;//微信公众号秘钥
	
	/***SEO 优化*/
	@Column(name="seo_title")
	private String seoTitle;
	
	@Column(name="seo_keyword")
	private String seoKeyword;
	
	@Column(name="seo_describe")
	private String seoDescribe;
	
	@Column(name="website_total_pv")
	private int websiteTotalPv;
	
	
	public String getIcpNumber() {
		return icpNumber;
	}

	public void setIcpNumber(String icpNumber) {
		this.icpNumber = icpNumber;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	public String getCooperatePhone() {
		return cooperatePhone;
	}

	public void setCooperatePhone(String cooperatePhone) {
		this.cooperatePhone = cooperatePhone;
	}

	public String getShareNotify() {
		return shareNotify;
	}

	public void setShareNotify(String shareNotify) {
		this.shareNotify = shareNotify;
	}

	public String getScrollBall() {
		return scrollBall;
	}

	public void setScrollBall(String scrollBall) {
		this.scrollBall = scrollBall;
	}

	public String getNotifyPhone() {
		return notifyPhone;
	}

	public void setNotifyPhone(String notifyPhone) {
		this.notifyPhone = notifyPhone;
	}

	public String getApprenticeQrCodeUrl() {
		return apprenticeQrCodeUrl;
	}

	public void setApprenticeQrCodeUrl(String apprenticeQrCodeUrl) {
		this.apprenticeQrCodeUrl = apprenticeQrCodeUrl;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeyword() {
		return seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public String getSeoDescribe() {
		return seoDescribe;
	}

	public void setSeoDescribe(String seoDescribe) {
		this.seoDescribe = seoDescribe;
	}

	public int getWebsiteTotalPv() {
		return websiteTotalPv;
	}

	public void setWebsiteTotalPv(int websiteTotalPv) {
		this.websiteTotalPv = websiteTotalPv;
	}
	
}

package com.zzy.brd.dto.rep.admin.user;

import org.springframework.web.util.HtmlUtils;

import com.zzy.brd.entity.User;
import com.zzy.brd.util.tld.PriceUtils;

/**
 * @author:xpk
 *    2016年10月31日-下午3:18:28
 **/
public class RepUserApprenticesDTO {
	//id
	private long id;
	//姓名
	private String realname="";	
	//手机号码 
	private String phone="";
	//身份证
	private String idcard="";
	//推荐码
	private String recommend="";
	//称谓
	private String named="";
	//徒弟数量
	private int sonSum;
	//徒孙数量
	private int grandSonsSum;
	//徒孙孙数量
	private int ggrandSonsSum;
	//我的订单总数
	private int orderSum;
	//我的订单总额
	private String orderMoney;
	//徒弟订单数
	private int sonOrderSum;;
	//徒弟订单总额
	private String sonOrderMoney;
	//徒孙订单总数
	private int grandSonOrderSum;
	//徒孙孙订单总额
	private int ggrandSonOrderSum;
	//徒孙订单总额
	private String grandSonOrderMoney;
	//徒孙孙订单总额
	private String ggrandSonOrderMoney;
	
	
	public RepUserApprenticesDTO(int i ,User user ){
		this.id = user.getId();
		if(i==0){
			this.named="";
		}else if(i==1){
			this.named="徒弟";
		}else if (i==2){
			this.named="徒孙";
		} else{
			this.named="徒孙孙";
		}
		this.realname = HtmlUtils.htmlEscape(user.getRealname()).toString();
		this.phone = user.getMobileno().toString();
		}
	
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getNamed() {
		return named;
	}
	public void setNamed(String named) {
		this.named = named;
	}
	public int getSonSum() {
		return sonSum;
	}
	public void setSonSum(int sonSum) {
		this.sonSum = sonSum;
	}
	public int getGrandSonsSum() {
		return grandSonsSum;
	}
	public void setGrandSonsSum(int grandSonsSum) {
		this.grandSonsSum = grandSonsSum;
	}
	public int getGgrandSonsSum() {
		return ggrandSonsSum;
	}
	public void setGgrandSonsSum(int ggrandSonsSum) {
		this.ggrandSonsSum = ggrandSonsSum;
	}
	public int getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(int orderSum) {
		this.orderSum = orderSum;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	public int getSonOrderSum() {
		return sonOrderSum;
	}
	public void setSonOrderSum(int sonOrderSum) {
		this.sonOrderSum = sonOrderSum;
	}
	public int getGrandSonOrderSum() {
		return grandSonOrderSum;
	}
	public void setGrandSonOrderSum(int grandSonOrderSum) {
		this.grandSonOrderSum = grandSonOrderSum;
	}
	public String getSonOrderMoney() {
		return sonOrderMoney;
	}
	public void setSonOrderMoney(String sonOrderMoney) {
		this.sonOrderMoney = sonOrderMoney;
	}
	public String getGrandSonOrderMoney() {
		return grandSonOrderMoney;
	}
	public void setGrandSonOrderMoney(String grandSonOrderMoney) {
		this.grandSonOrderMoney = grandSonOrderMoney;
	}
	public String getGgrandSonOrderMoney() {
		return ggrandSonOrderMoney;
	}
	public void setGgrandSonOrderMoney(String ggrandSonOrderMoney) {
		this.ggrandSonOrderMoney = ggrandSonOrderMoney;
	}
	public int getGgrandSonOrderSum() {
		return ggrandSonOrderSum;
	}
	public void setGgrandSonOrderSum(int ggrandSonOrderSum) {
		this.ggrandSonOrderSum = ggrandSonOrderSum;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}

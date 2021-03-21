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
 *    2016年11月25日-下午5:27:24
 **/
@Entity
@Table(name = "user_info_seller")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class UserInfoSeller extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8993631231179127871L;
	
	/**经度**/
	private String longitude;
	/**纬度*/
	private String latitude;
	/***详细地址*/
	private String address;
	/**百度的百度地图的id**/
	@Column(name="baidu_map_id")
	private Integer baiduMapId;
	/**公司名*/
	private String company;	
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getBaiduMapId() {
		return baiduMapId;
	}
	public void setBaiduMapId(Integer baiduMapId) {
		this.baiduMapId = baiduMapId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
}

package com.zzy.brd.dto.req.map.baidu;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.dto.req.map.base.ReqBaiduMapBaseDTO;

/**
 * @author:xpk
 *    2016年11月25日-下午4:16:16
 **/
public class ReqCreatePoiDTO extends ReqBaiduMapBaseDTO {
	
	private String title;
	private String address;
	private String tags;
	private double latitude;
	private double longitude;
	/***
	 * 1：GPS经纬度坐标 2：国测局加密经纬度坐标 3：百度加密经纬度坐标 4：百度加密墨卡托坐标
	 */
	private Integer coord_type =3;
	private String geotable_id=ConfigSetting.baidu_map_api_for_service;
	private String ak;
	private String sn;
	
	public String getTitle() {
		return title;
	}
 
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCoord_type() {
		return coord_type;
	}

	public void setCoord_type(int coord_type) {
		this.coord_type = coord_type;
	}
	
	public String getGeotable_id() {
		return geotable_id;
	}

	public void setGeotable_id(String geotable_id) {
		this.geotable_id = geotable_id;
	}

	public void setCoord_type(Integer coord_type) {
		this.coord_type = coord_type;
	}

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
}

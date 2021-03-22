package com.zzy.brd.dto.rep.admin.reportStatistics;

import com.zzy.brd.entity.User;

/**
 * @author:xpk
 *    2016年12月5日-上午11:50:47
 **/
public class RepSellerStatisticAddressDTO {
	
	private long userId;
	//纬度
	private String latitude;
	//经度
	private String longitude;
	
	private String address;
	
	private String company;
	
	public RepSellerStatisticAddressDTO(User user){
		/*if(user.getUserInfoSeller()!=null){
			this.userId = user.getId();
			this.latitude = user.getUserInfoSeller().getLatitude();
			this.longitude = user.getUserInfoSeller().getLongitude();
			this.address = user.getUserInfoSeller().getAddress();
			this.company = user.getUserInfoSeller().getCompany();
		}*/
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}	
	
}

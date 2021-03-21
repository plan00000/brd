package com.zzy.brd.util.map.baidu.req.geocoder;

import com.zzy.brd.dto.req.map.base.ReqBaiduMapBaseDTO;

/**
 * @author:xpk
 *    2016年11月30日-上午9:54:47
 **/
public class ReqGeocoderResolve extends ReqBaiduMapBaseDTO {
	
	public enum OutputType {
		JSON,XML;	
		public String getStr(){
			if(this==JSON){
				return "json";
			} 
			if(this == XML){
				return "xml";
			}
			return "";
		}
	}
	
	private String ak;
	
	private String address;
	
	private String city;
	
	private String output;
	
	private String sn;
	
	public ReqGeocoderResolve (){
		this.output = OutputType.JSON.getStr();
	}
	
	
	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOutput() {
		return output;
	}


	public void setOutput(String output) {
		this.output = output;
	}


	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	
}

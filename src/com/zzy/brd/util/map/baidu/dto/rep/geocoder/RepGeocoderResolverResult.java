package com.zzy.brd.util.map.baidu.dto.rep.geocoder;


/**
 * @author:xpk
 *    2016年11月30日-上午9:51:25
 **/
public class RepGeocoderResolverResult {
	
	private RepGeocoderResolveLocation location;
	private Integer precise;
	private Integer confidence;
	private String level;

	public RepGeocoderResolveLocation getLocation() {
		return location;
	}

	public void setLocation(RepGeocoderResolveLocation location) {
		this.location = location;
	}

	public Integer getPrecise() {
		return precise;
	}

	public void setPrecise(Integer precise) {
		this.precise = precise;
	}

	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}

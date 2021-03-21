package com.zzy.brd.util.weixin;

import java.util.Map;

public class QrcodePostData {
	/** 二维码类型*/
	private String action_name;
	/** 二维码详细信息*/
	private Map<String,QrcodeData> action_info;
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}
	public Map<String, QrcodeData> getAction_info() {
		return action_info;
	}
	public void setAction_info(Map<String, QrcodeData> action_info) {
		this.action_info = action_info;
	}
	 
}

package com.zzy.brd.dto.rep.phone;

public class RepPhoneDTO {
	private String errNum;
	private String errMsg;
	private RepPhoneDetailDTO retData;
	public String getErrNum() {
		return errNum;
	}
	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public RepPhoneDetailDTO getRetData() {
		return retData;
	}
	public void setRetData(RepPhoneDetailDTO retData) {
		this.retData = retData;
	}
	
	
}

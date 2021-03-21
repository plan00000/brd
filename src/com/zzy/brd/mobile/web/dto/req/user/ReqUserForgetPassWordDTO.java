package com.zzy.brd.mobile.web.dto.req.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 忘记密码-dtp
 * @author lzh 2016-9-26
 *
 */
public class ReqUserForgetPassWordDTO {
	
	@NotNull(message = "手机号码为空")
	@Size(min = 11, max = 11, message = "手机号码必须为11位")
	private String mobileno;
	
	@NotNull(message = "手机验证码为空")
	@Size(min = 6, max = 6, message = "手机验证码必须为6位")
	private String phoneAuthcode;
	
	@NotNull(message = "密码不能为空")
	private String password;

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getPhoneAuthcode() {
		return phoneAuthcode;
	}

	public void setPhoneAuthcode(String phoneAuthcode) {
		this.phoneAuthcode = phoneAuthcode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

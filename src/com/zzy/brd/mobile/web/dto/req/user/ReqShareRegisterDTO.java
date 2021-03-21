package com.zzy.brd.mobile.web.dto.req.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author:xpk
 *    2016年10月8日-下午6:10:24
 **/
public class ReqShareRegisterDTO {
	
	/** 手机号码 */
	@NotNull(message = "手机号码为空")
	@Size(min = 11, max = 11, message = "手机号码必须为11位")
	private String mobileno;

	@NotNull(message = "手机验证码为空")
	@Size(min = 6, max = 6, message = "手机验证码必须为6位")
	private String phoneAuthcode;
	@NotNull(message = "密码不能为空")
	private String password;
	@NotNull(message = "昵称不能为空")
	@Size(max=10,min=1)
	private String nickname;
	
	/**推荐人*/
	private String recommoned;

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

	public String getRecommoned() {
		return recommoned;
	}

	public void setRecommoned(String recommoned) {
		this.recommoned = recommoned;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}

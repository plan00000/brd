package com.zzy.brd.dto.rep.admin.platform;

import javax.servlet.http.HttpUtils;

import org.springframework.web.util.HtmlUtils;

import com.zzy.brd.entity.User;
import com.zzy.brd.util.pageModel.PageBean;


public class RepUserDTO extends PageBean {

	/** id */
	private Long id;
	/** 用户名 */
	private String username;
	/** 姓名 */
	private String realname;
	/** 手机号 */
	private String mobileno;

	public RepUserDTO() {

	}

	public RepUserDTO(User user) {
		if (user != null) {
			this.id = user.getId();
			this.username = HtmlUtils.htmlEscape(user.getUsername());
			this.realname = HtmlUtils.htmlEscape(user.getRealname());
			this.mobileno = user.getMobileno();
		}
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

}

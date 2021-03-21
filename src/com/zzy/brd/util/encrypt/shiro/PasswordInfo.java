package com.zzy.brd.util.encrypt.shiro;

public class PasswordInfo {
	private String password;
	private String salt;
	
	/***
	 * 获得加密后的密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/***
	 * 获得salt
	 * @return
	 */
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}

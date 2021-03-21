package com.zzy.brd.shiro.principal;

import java.io.Serializable;

import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.mobile.util.IShiroPrincipal;


/***
 * 存放于shiro里面的session
 * 
 * @author wwy
 *
 */
public class ShiroUser implements Serializable,IShiroPrincipal {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8693820896055662267L;
	private Long id;
	private String name;
	private UserType userType;
	/** 头像url */
	private String headUrl;
	private String position;

	/**
	 * 
	 */
	public ShiroUser(User user) {
		this.id = user.getId();
		this.name = user.getUsername();
		this.headUrl = user.getHeadimgurl();
		/*if(user.getUsertype() == UserType.ADMIN){
			this.position = "超级管理员";
		}else {
//			if(user.getRole() != null){
//				this.position = user.getRole().getRolename() + user.getPosition().getStr();
//			}else {
//				this.position = user.getPosition().getStr();
//			}
		}*/
	}
	public ShiroUser(Long id, UserType userType, String username, String headUrl) {
		this.id = id;
		this.userType = userType;
		this.setName(username);
		this.setHeadUrl(headUrl);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	/**
	 * @return the headUrl
	 */
	public String getHeadUrl() {
		return headUrl;
	}

	/**
	 * @param headUrl
	 *            the headUrl to set
	 */
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	@Override
	public Long getUserId() {
		// TODO Auto-generated method stub
		return id;
	}

}

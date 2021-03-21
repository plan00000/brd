/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.shiro.session;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.mapper.BeanMapper;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.interfaces.IUser;
import com.zzy.brd.shiro.principal.ShiroUser;
import com.zzy.brd.shiro.realm.ShiroAdminDbRealm;

/**
 * 会话管理
 * 
 * @author lxm 2015年2月12日
 */
@Service
public class SessionService {

	@Autowired
	CustomCacheSessionDAO sessionDAO;
	@Autowired(required = false)
	ShiroAdminDbRealm shiroDbRealm;

	/***
	 * 更新shiroUser
	 * @param userid
	 * @param userType
	 * @param user1
	 */
	public void updateShiroUser(long userid, UserType userType, ShiroUser user1){
		List<Session> sessions = getSessions(userid, userType);
		if (sessions != null && sessions.size() > 0){
			for (Session session : sessions){
				Subject subject = new Subject.Builder().session(session).buildSubject();
				ShiroUser user2 = (ShiroUser) subject.getPrincipal();
				BeanMapper.copy(user1, user2);
			}
		}
	}
	
	/**
	 * 清理权限
	 * 
	 * @param userid
	 * @param type
	 */
	public void clearUserPermission(User user) {
		List<Session> sessions = this.getSessions(user.getId(),
				user.getUserType());
		Subject subj = null;
		for (Session session : sessions) {
			subj = new Subject.Builder().session(session).buildSubject();
			if (subj != null) {
				shiroDbRealm.clearCachedAuthorizationInfo(subj.getPrincipals());
			}
		}
	}

	/**
	 * 获取session列表
	 * 
	 * @param userid
	 *            用户id
	 * @param type
	 *            用户类型
	 * @return
	 */
	public List<Session> getSessions(long userid, UserType type) {
		List<Session> sessions = this.sessionDAO.getCachedUserSessions(userid,
				type);
		return sessions;
	}

	/**
	 * 根据用户类型及ID强制退出会话
	 * 
	 * @param userid
	 * @param type
	 */
	public void forceExitSession(long userid, UserType type) {
		List<Session> sessions = this.getSessions(userid, type);
		for (Session session : sessions) {
			session.setAttribute(Constant.SESSION_FORCE_LOGOUT_KEY,
					Boolean.TRUE);
		
		}
	}

	/**
	 * 强制退出用户
	 * 
	 * @param userId
	 */
	public void forceExitUser(long userId) {
		this.forceExitSession(userId, UserType.USER);
	}

	/**
	 * 强制退出员工
	 * 
	 * @param customerId
	 */
	public void forceExitCustomer(long customerId) {
		this.forceExitSession(customerId, UserType.CONTROLMANAGER);
	}

	/**
	 * 强制退出管理员
	 * 
	 * @param customerId
	 */
	public void forceExitAdmin(long adminId) {
		this.forceExitSession(adminId, UserType.ADMIN);
	}

}

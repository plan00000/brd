/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.shiro.session;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springside.modules.utils.Collections3;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.entity.User.UserType;

/**
 * 自定义shiro session 缓存
 * 		更改缓存结构
 * @author lll 2015年4月7日
 */
public class CustomCacheSessionDAO extends EnterpriseCacheSessionDAO {

	/**
	 * 用户登陆列表缓存名称
	 */
	public static final String USER_LIST_CACHE_NAME = "shiro-userListCache";
    private String userListCacheName = USER_LIST_CACHE_NAME;
	
	/**
     * 用户登陆列表缓存
     */
    private Cache<Serializable, Set<Serializable>> userListCache;
    
    /**
     * 根据用户id、类型 获取登陆session列表
     * @param userId
     * @param userType
     * @return
     */
    public List<Session> getCachedUserSessions(long userId, UserType userType) {
    	Set<Serializable> sessionIds = this.getCachedUserList(userId, userType);
    	List<Session> sessions = Lists.newArrayListWithExpectedSize(sessionIds.size());
    	if (Collections3.isEmpty(sessionIds)) {
			return sessions;
		}
    	
    	Cache<Serializable, Session> cache = getActiveSessionsCache();
    	Session session = null;
    	for (Serializable sessionId : sessionIds) {
    		session = cache.get(sessionId);
    		if (session != null) {
    			sessions.add(session);
			}
		}
    	return sessions;
    }
	
    /**
     * 根据用户id、类型 获取登陆session列表
     * @param userId
     * @param userType
     * @return
     */
    public Set<Serializable> getCachedUserList(long userId, UserType userType) {
    	Set<Serializable> userSet = null;
    	Cache<Serializable, Set<Serializable>> cache = getUserListCacheLazy();
        if (cache != null) {
        	Serializable userKey = generateUserKey(userId, userType);
        	userSet = cache.get(userKey);
        }
        
        if (userSet == null) {
			userSet = Sets.newHashSet();
		}
        
        return userSet;
    }
    
    @Override
	protected void cache(Session session, Serializable sessionId, Cache<Serializable, Session> cache) {

    	cache.put(sessionId, session);
        Cache<Serializable, Set<Serializable>> userListCache = getUserListCacheLazy();
        if (userListCache == null) {
            return;
        }
        //删除用户列表缓存
        Serializable userKey = getUserKey(session);
        if (userKey != null) {
        	Set<Serializable> sessionIds = userListCache.get(userKey);
        	if (sessionIds == null) {
        		sessionIds = Sets.newHashSet();
			}
        	if (sessionIds.contains(sessionId)) {
				return;
			}
        	sessionIds.add(sessionId);
        	userListCache.put(userKey, sessionIds);
		}
    }
	
	@Override
	protected void uncache(Session session) {

		super.uncache(session);
		//删除用户列表缓存
        Serializable userKey = getUserKey(session);
        if (userKey != null) {
        	Cache<Serializable, Set<Serializable>> userListCache = getUserListCache();
        	if (userListCache == null) {
                return;
            }
        	Set<Serializable> values = userListCache.get(userKey);
        	Serializable sessionId = session.getId();
        	if (values != null && values.contains(sessionId)) {
        		values.remove(sessionId);
        		if (values.size() == 0) {
					userListCache.remove(userKey);
				}
			}
		}
	}
	
	
	
	@Override
	protected void doUpdate(Session session) {
		super.doUpdate(session);
		//删除用户列表
        Serializable userKey = this.getUserKey(session);
        if (userKey != null) {
			Cache<Serializable, Set<Serializable>> cache = this.getUserListCacheLazy();
			Set<Serializable> sessionIds = cache.get(userKey);
			if (sessionIds == null) {
				sessionIds = Sets.newHashSet();
			}
			if (sessionIds.contains(userKey)) {
				return;
			}
			sessionIds.add(session.getId());
			cache.put(userKey, sessionIds);
		}
	}
	
	@Override
	protected void doDelete(Session session) {
		super.doDelete(session);
		//删除用户列表
        Serializable userKey = this.getUserKey(session);
        if (userKey != null) {
			Cache<Serializable, Set<Serializable>> cache = this.getUserListCacheLazy();
			Set<Serializable> sessionIds = cache.get(userKey);
			sessionIds.remove(session.getId());
		}
    }
	
	/**
	 * 创建缓存
	 * @return
	 */
	protected Cache<Serializable, Set<Serializable>> createUserListCache() {
        Cache<Serializable, Set<Serializable>> cache = null;
        CacheManager mgr = getCacheManager();
        if (mgr != null) {
            String name = getUserListCacheName();
            cache = mgr.getCache(name);
        }
        return cache;
    }
	
    /**
     * 获取sessionCache
     * @return
     */
    private Cache<Serializable, Set<Serializable>> getUserListCacheLazy() {
    	if (this.userListCache == null) {
            this.userListCache = createUserListCache();
        }
        return userListCache;
    }
    
    /**
     * 获取用户 缓存key
     * @param session
     * @return
     */
    private Serializable getUserKey(Session session) {
    	UserType userType = (UserType)session.getAttribute(Constant	.SESSION_CURUSER_USERTYPE);
		Object useridObject = session.getAttribute(Constant.SESSION_CURUSER_UUID);
		if (userType != null && useridObject != null) {
			return this.generateUserKey((Long)useridObject, userType);
		}
		return null;
    }
    
    private Serializable generateUserKey(Long userId, UserType userType) {
    	return "UserKey_" + userType + "_" + userId;
    }

	/**
	 * @return the userListCacheName
	 */
	public String getUserListCacheName() {
		return userListCacheName;
	}

	/**
	 * @param userListCacheName the userListCacheName to set
	 */
	public void setUserListCacheName(String userListCacheName) {
		this.userListCacheName = userListCacheName;
	}

	/**
	 * @return the userListCache
	 */
	public Cache<Serializable, Set<Serializable>> getUserListCache() {
		return userListCache;
	}

	/**
	 * @param userListCache the userListCache to set
	 */
	public void setUserListCache(Cache<Serializable, Set<Serializable>> userListCache) {
		this.userListCache = userListCache;
	}

}

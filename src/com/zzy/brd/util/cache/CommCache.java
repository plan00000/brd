/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.cache;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.zzy.brd.util.properties.PropertiesHelper;
/**
 * 通用缓存类
 * @author lxm 2015年1月16日
 */
public class CommCache {
	protected static MemCachedClient mcc = new MemCachedClient();
	protected static CommCache memCached = new CommCache();
	// cache池配置
	static {
		// server and weight
		String[] server = {PropertiesHelper.getProperty("Memcached_server")};
		Integer[] weight = {3};
		
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(server); // 设置服务器
		pool.setWeights(weight); // 设置权重
		pool.setInitConn(Integer.parseInt(PropertiesHelper.getProperty("Memcached_initConn"))); // 初始连接数
		pool.setMinConn(Integer.parseInt(PropertiesHelper.getProperty("Memcached_MinConn"))); // 最小连接数
		pool.setMaxConn(Integer.parseInt(PropertiesHelper.getProperty("Memcached_MaxConn"))); // 最大连接数
		pool.setMaxIdle(Integer.parseInt(PropertiesHelper.getProperty("Memcached_MaxIdle"))); // 最大处理时间
		pool.setMaintSleep(Integer.parseInt(PropertiesHelper.getProperty("Memcached_MainSleep"))); // 主线程睡眠时间
		pool.setNagle(false); // 是否启用tcp nagle算法
		pool.setSocketTO(Integer.parseInt(PropertiesHelper.getProperty("Memcached_SockTo")));
		pool.setSocketConnectTO(Integer.parseInt(PropertiesHelper.getProperty("Memcached_SockConnTo")));
		pool.initialize(); // 连接池初始化		
	}
	
	protected CommCache() {}
	
	public static CommCache getInstance() {
		return memCached;
	}
	
	/**
	 * 设置一个缓存. 存在则替换
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value) {
		boolean flag = mcc.set(key, value);		
		return flag;
	}
	
	public boolean set(String key, Object value, Date expiry) {
		return mcc.set(key, value, expiry);		
	}
	
	/**
	 * 添加一个值到缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean add(String key, Object value) {
		return mcc.add(key, value);
	}
	
	/**
	 * 添加一个对象到缓存并设置过期时间
	 * @param key
	 * @param value
	 * @param expiry
	 * @return
	 */
	public boolean add(String key, Object value, Date expiry) {
		return mcc.add(key, value, expiry);
	}
	
	/**
	 * 替换一个指定的值到缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean replace(String key, Object value) {
		return mcc.replace(key, value);
	}
	/**
	 * 替换一个指定的值到缓存并设置超时
	 * @param key
	 * @param value
	 * @param expiry
	 * @return
	 */
	public boolean replace(String key, Object value, Date expiry) {
		return mcc.replace(key, value, expiry);
	}
	
	/**
	 * 删除一个指定的值
	 * @param key
	 * @return
	 */
	public boolean delete(String key) {
		return mcc.delete(key);
	}
	
	/**
	 * 返回一个缓存对象
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return mcc.get(key);
	}	
}

package com.zzy.brd.util.ipparse;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 天气队列，根据IP获取天气信息的队列
 * @author zzy 2015年2月4日
 */
public class IpParseQueue {
	/**
	 * 天气队列实体
	 */
	static IpParseQueue queue = null;

	/**
	 * 存放消息的队列
	 */
	private Queue<String> tokens = null;

	/**
	 * 单例获取实例
	 */
	public synchronized static IpParseQueue getShareQueue() {
		if (queue == null) {
			queue = new IpParseQueue();
		}
		return queue;
	}
	
	/**
	 * 构造方法
	 */
	public IpParseQueue() {
		synchronized (this) {
			tokens = new LinkedList<String>();
		}
	}
	
	/**
	 * 增加需要处理的IP
	 */
	public void addExecuteToken(String token) {
		synchronized (tokens) {
			if (tokens.contains(token)) {
				return;
			}
			tokens.offer(token);
			Thread ipThread = IpParseThread.getShareThread();
			synchronized (ipThread) {
				if (ipThread.isAlive()) {
					ipThread.notify();
				}
				else
				{
					ipThread.setDaemon(true);
					ipThread.start();
				}
			}
			
		}
	}
	
	/**
	 * 获取队列中需要处理的IP
	 */
	public String popMessages() {
		synchronized (this) {
			if(!tokens.isEmpty()){
				return tokens.poll();
			}
			return null;
		}
	}
}

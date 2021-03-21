/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;

import com.zzy.brd.util.socket.SocketService;





/**
 * 
 * @author lxm 2015年4月2日
 */
public class TaskThread extends Thread {
	/**
	 * 创建线程池
	 */
	static private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	/**
	 * 单例对象
	 */
	static private TaskThread shareThread = new TaskThread();
	
	/**
	 * servlet上下文
	 */
	private static ServletContextEvent servletContextEvent;
	
	private static SocketService socket = new SocketService();	 

	/**
	 * 单例，获取单例对象
	 */
	public static TaskThread getShareThread() {
		synchronized (shareThread) {
			return shareThread;
		}
	}
	/**
	 * @return the servletContextEvent
	 */
	public ServletContextEvent getServletContextEvent() {
		return servletContextEvent;
	}

	/**
	 * @param servletContextEvent the servletContextEvent to set
	 */
	public void setServletContextEvent(ServletContextEvent servletContextEvent) {
		TaskThread.servletContextEvent = servletContextEvent;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				//从消息队列中取出IP消息，如果为空则等待
				final String packet = TaskQueue.getShareQueue().popTask();
				if(null == packet) {
					synchronized (this) {
						// 等待
						wait();
						continue;
					}
				}
				
				//启动一个线程来进行处理
				executor.execute(new Runnable() {
					@Override
					public void run() {
						socket.sendByTcp(packet);
					}
				});
			}
			catch(Exception e) {
				System.out.println("push站内信失败");
				e.printStackTrace();
			}
		}
	}
}

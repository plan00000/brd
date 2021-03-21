/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.task;

import java.util.LinkedList;
import java.util.Queue;


/**
 * 任务队列
 * @author lxm 2015年4月2日
 */
public class TaskQueue {
	static TaskQueue queue = null;
	
	static Queue<String> tasks = null;
	
	public synchronized static TaskQueue getShareQueue() {
		if (queue == null) {
			queue = new TaskQueue();
		}
		return queue;
	}
	
	public TaskQueue() {
		synchronized (this) {
			tasks = new LinkedList<String>();
		}
	}
	
	public void addTask(String token) {
		synchronized (tasks) {
			if (tasks.contains(token)) {
				return;
			}
			tasks.offer(token);
			if (TaskThread.getShareThread().isAlive()) {
				synchronized (TaskThread.getShareThread()) {
					TaskThread.getShareThread().notify();
				}
			}
			else
			{
				TaskThread.getShareThread().setDaemon(true);
				TaskThread.getShareThread().start();
			}
		}
	}
	
	/**
	 * 获取队列中需要处理的IP
	 */
	public String popTask() {
		synchronized (this) {
			if(!tasks.isEmpty()){
				return tasks.poll();
			}
			return null;
		}
	}
}

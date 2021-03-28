/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zzy.brd.constant.ConfigSetting;



/**
 * 初始化监听器
 * 
 * @author Toni 2015年1月8日
 */
public class InitListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// 载入配置文件信息
		new ConfigSetting();
		// 载入城市编号信息
		/*new City();
		// 启动IP获取天气的线程池操作
		IpParseThread.getShareThread().setServletContextEvent(e);
		IpParseThread.getShareThread().setDaemon(true);
		IpParseThread.getShareThread().start();*/
	}
}

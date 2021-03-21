package com.zzy.brd.scheduler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springside.modules.utils.Threads;

import com.google.common.collect.Lists;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserCommonStatistics;
import com.zzy.brd.logger.BatchLogger;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.service.OrderCommonStatisticsService;
import com.zzy.brd.service.PvCommonStatisticsService;
import com.zzy.brd.service.UserCommonStatisticsService;

/**
 * 数据统计批处理
 */
public class DataStatisticsSchedule implements Runnable {
	
	private String cronExpression;

	private int shutdownTimeout = Integer.MAX_VALUE;

	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private BatchLogger batchLogger;
	@Autowired
	private UserCommonStatisticsService userCommonStatisticsService;
	@Autowired
	private OrderCommonStatisticsService orderCommonStatisticsService;
	@Autowired
	private UserService userService;
	@Autowired
	private PvCommonStatisticsService pvCommonStatisticsService;
	
	@PostConstruct
	public void start() {
		Validate.notBlank(cronExpression);

		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setThreadNamePrefix("DataStatisticsSchedule");
		threadPoolTaskScheduler.initialize();

		threadPoolTaskScheduler.schedule(this, new CronTrigger(cronExpression));
	}

	@PreDestroy
	public void stop() {
		ScheduledExecutorService scheduledExecutorService = threadPoolTaskScheduler.getScheduledExecutor();
		Threads.normalShutdown(scheduledExecutorService, shutdownTimeout,TimeUnit.SECONDS);
	}

	// 每月1日0时触发事件
	@Override
	@Test
	public void run() {
		batchLogger.logWarn("DataStatisticsSchedule", "开始批处理-数据统计...", null);
		int userSerStatistics = userCommonStatisticsService.setUserStatistics();
		if(userSerStatistics==2){
			batchLogger.logWarn("DataStatisticsSchedule", "保存会员统计错误", null);
		}else{
			batchLogger.logWarn("DataStatisticsSchedule", "保存会员统计成功", null);
		}
		int orderCommonStatistics = orderCommonStatisticsService.setOrderStatistics();
		if(orderCommonStatistics==2){
			batchLogger.logWarn("DataStatisticsSchedule", "保存订单统计错误", null);
		}else{
			batchLogger.logWarn("DataStatisticsSchedule", "保存订单统计成功", null);
		}
		boolean isSuccess = pvCommonStatisticsService.statisticsPv();		
		if(isSuccess) {
			batchLogger.logWarn("DataStatisticsSchedule", "保存PV统计成功", null);
		} else {
			batchLogger.logWarn("DataStatisticsSchedule", "保存PV统计失败", null);
		}
		
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 设置normalShutdown的等待时间,单位秒.
	 */
	public void setShutdownTimeout(int shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}
	
	
}

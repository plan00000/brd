package com.zzy.brd.scheduler;

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

import com.zzy.brd.logger.BatchLogger;
import com.zzy.brd.service.UserDailyStatisticsService;

/**
 * @author:xpk
 *    2016年10月21日-上午10:25:57
 **/
public class UserDailyStatisticsSchedule implements Runnable {

	private String cronExpression;

	private int shutdownTimeout = Integer.MAX_VALUE;

	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private BatchLogger batchLogger;
	
	@Autowired
	private UserDailyStatisticsService userDailyService;
	
	
	
	@PostConstruct
	public void start() {
		Validate.notBlank(cronExpression);

		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setThreadNamePrefix("UserDailyStatisticsSchedule");
		threadPoolTaskScheduler.initialize();

		threadPoolTaskScheduler.schedule(this, new CronTrigger(cronExpression));
	}

	@PreDestroy
	public void stop() {
		ScheduledExecutorService scheduledExecutorService = threadPoolTaskScheduler.getScheduledExecutor();
		Threads.normalShutdown(scheduledExecutorService, shutdownTimeout,TimeUnit.SECONDS);
	}

	// 每天1点执行
	@Override
	@Test
	public void run() {
		batchLogger.logWarn("UserDailyStatisticsSchedule", "开始批每日用户数据批处理...", null);
		
		userDailyService.dailyStatistics();
		
		batchLogger.logWarn("UserDailyStatisticsSchedule", "结束每日用户数据批处理...", null);
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

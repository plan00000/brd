/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 记录批处理日志：格式为
 * 
 * 日期,类名称,描述
 * @author zyh 2015年3月13日
 */
@Component
public class BatchLogger {
	public static final String BATCH_LOGGER_NAME = "batch";
	private Logger businessLogger = LoggerFactory.getLogger(BATCH_LOGGER_NAME);
	
	/**
	 * 记录正常日志信息
	 * @param entity--记录日志所在的类  
	 * @param desc--记录日志描述
	 * @param arg-- 定义参数对象数组，替换desc中的{}符号
	 */
	public void logInfo(String entity, String desc, Object[] arg) {
		businessLogger.info("["+entity+"]-"+desc, arg);
	}
	/**
	 * 记录警告日志信息
	 * @param entity
	 * @param desc
	 * @param arg
	 */
	public void logWarn(String entity, String desc, Object[] arg) {
		businessLogger.warn("["+entity+"]-"+desc, arg);
	}
	/**
	 * 记录错误日志信息
	 * @param entity
	 * @param desc
	 * @param arg
	 */
	public void logError(String entity, String desc, Object[] arg) {
		businessLogger.error("["+entity+"]-"+desc, arg);
	}
	/**
	 * 记录错误日志信息-捕获异常
	 * @param entity
	 * @param desc
	 * @param ex--异常对象
	 */
	public void logError(String entity, String desc,Throwable ex) {
		businessLogger.error("["+entity+"]-{}-"+dumpThrowable(ex), desc);
	}
	
	/**
	 * 获得异常信息
	 * 
	 * @param ex
	 *            异常对象
	 * @return 异常信息字符串
	 */
	public String dumpThrowable(Throwable ex) {
		StringWriter out = new StringWriter();
		ex.printStackTrace(new PrintWriter(out));
		return out.toString();
	}
}

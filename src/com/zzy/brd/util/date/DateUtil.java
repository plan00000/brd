/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.util.date;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author anderson
 * @version 1.0
 */

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.time.DateUtils;

import com.sun.org.apache.regexp.internal.recompile;

public class DateUtil {

	private static SimpleDateFormat cmyFormat = new SimpleDateFormat(
			"yyyy年MM月dd日");
	private static SimpleDateFormat longFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat myFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat shortFormat = new SimpleDateFormat(
			"yyyy-MM");
	private static SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	private static SimpleDateFormat monthDateFormat = new SimpleDateFormat(
			"MM-dd HH:mm:ss");
	
	
	/**
	 * 获取距离今天几天
	 * @param date
	 * @return	负数：过去时间 ， 0：同一天 ，正数：未来时间
	 */
	public static int daysSinceNow(Date date) {
		Date truncateDate = DateUtils.truncate(date, Calendar.DATE);
		Date truncateNow = DateUtils.truncate(new Date(),Calendar.DATE);
		int days = 0;
		days = (int) ((truncateDate.getTime() - truncateNow.getTime())/(60*60*24*1000));
		return days;
	}
	
	/**
	 * 获取距离今天几个月
	 * @param date
	 * @return	负数：过去时间 ， 0：同一月 ，正数：未来时间
	 */
	public static int monthSinceNow(Date date) {
		Date now = new Date();
		int yearCount = Integer.parseInt(getYear(now))-Integer.parseInt(getYear(date));
		int monthCount = Integer.parseInt(getMonth(now)) - Integer.parseInt(getMonth(date));
		return yearCount*12 + monthCount;
	}

	/**
	 * 检查输入String是否是合法Date（yyyy-MM-dd）格式 成立时返回true，否则返回false
	 * 
	 * @return
	 */
	/*
	 * public boolean checkStringIsDate(String date){ try { String[] dateStr =
	 * date.split("-"); if (dateStr.length == 3){ if (dateStr[0].length() == 4){
	 * try { Integer.parseInt(dateStr[0]); }catch(Exception e){ throw new
	 * Exception ("年份必须为数字"); } if (Integet) }else { throw new Exception
	 * ("年份的长度必须未4位数"); }
	 * 
	 * }else{ throw new Exception ("日期格式不正确，格式必须为yyyy-MM-dd"); }
	 * 
	 * }catch(Exception e){ return false; } }
	 */
	
	/**
	 * 5个月前的月份第一天 
	 */
	public static String getBeforeFiveMonthFirstDayStr ( Date date ){
		try{
			Date monthFirstDay = myFormat.parse(formatDateByFormat(date, "yyyy-MM") + "-01");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(monthFirstDay);
			calendar.add(Calendar.MONTH, -5);
			return formatDate(calendar.getTime());
		}catch( Exception e ){
			System.out.println(e);
		}
		return "";
	}
	
	/**
	 * 5个月前的月份第一天 (当前时间为锚点)
	 */
	public static Date getBeforeFiveMonthFirstDay (){
		Date beginTime = null;
		try {
			beginTime = new SimpleDateFormat("yyyy-MM-dd").parse(getBeforeFiveMonthFirstDayStr(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return beginTime;
	}
	
	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthBegin(String strdate) {
		java.util.Date date = StringToDate(strdate);
		return formatDateByFormat(date, "yyyy-MM") + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(String strdate) {
		java.util.Date date = StringToDate(getMonthBegin(strdate));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}

	/**
	 * 取得指定月份的总天数
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static int getMonthDaynum(String strdate) {
		String enddate = getMonthEnd(strdate);
		return Integer.parseInt(enddate.substring(enddate.length() - 2,
				enddate.length()));
	}

	/**
	 * 得到strDate日期的周的星期一
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getWeekBegin(String strDate) {
		java.util.Date date = StringToDate(strDate);
		Calendar calendar = Calendar.getInstance();
		// calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// 1为周日.
		if (dayOfWeek == 1) {
			dayOfWeek = 6;
		} else {
			dayOfWeek = dayOfWeek - 2;
		}
		calendar.add(Calendar.DAY_OF_YEAR, 0 - dayOfWeek);
		return formatDate(calendar.getTime());
	}

	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 是否在同一分钟
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameMinute(java.util.Date d1, java.util.Date d2) {
		boolean result;
		if (d1.getTime() - d2.getTime() >= 60 * 1000
				|| d1.getTime() - d2.getTime() < -60 * 1000) {
			result = false;
		} else {
			result = d1.getMinutes() == d2.getMinutes();
		}
		return result;
	}

	/**
	 * time是否在当前时间的前N天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isPastDay(long time) {
		Calendar current = Calendar.getInstance();
		current.set(Calendar.HOUR, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		Calendar old = Calendar.getInstance();
		current.setTime(new java.util.Date(time));
		return current.getTime().getTime() > old.getTime().getTime();
	}

	/**
	 * 是否在同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isToday(Date time) {
		Calendar current = Calendar.getInstance();
		current.set(Calendar.HOUR, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		Calendar old = Calendar.getInstance();
		old.setTime(time);
		old.set(Calendar.HOUR, 0);
		old.set(Calendar.MINUTE, 0);
		old.set(Calendar.SECOND, 0);
		old.set(Calendar.MILLISECOND, 0);
		return current.getTime().getTime() == old.getTime().getTime();
	}
	
	/**
	 * 判断是否比当前时间小
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isBeforToday(Date time) {
		Calendar current = Calendar.getInstance();
		current.set(Calendar.HOUR, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		Calendar old = Calendar.getInstance();
		old.setTime(time);
		old.set(Calendar.HOUR, 0);
		old.set(Calendar.MINUTE, 0);
		old.set(Calendar.SECOND, 0);
		old.set(Calendar.MILLISECOND, 0);
		return current.getTime().getTime() > old.getTime().getTime();
	}

	/**
	 * 是否在同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isTheSameDay(long milliseconds1, long milliseconds2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTimeInMillis(milliseconds1);
		c2.setTimeInMillis(milliseconds2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_MONTH) == c2
						.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 是否在同一年
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isTheSameYear(long milliseconds1, long milliseconds2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTimeInMillis(milliseconds1);
		c2.setTimeInMillis(milliseconds2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
	}

	/**
	 * 是否在同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameToday(long time) {
		Calendar current = Calendar.getInstance();
		current.set(Calendar.HOUR, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		Calendar old = Calendar.getInstance();
		old.setTime(new java.util.Date(time));
		old.set(Calendar.HOUR, 0);
		old.set(Calendar.MINUTE, 0);
		old.set(Calendar.SECOND, 0);
		old.set(Calendar.MILLISECOND, 0);
		return current.getTime().getTime() == old.getTime().getTime();
	}

	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate2(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 取得系统当前时间,类型为Timestamp
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getNowTimestamp() {
		java.util.Date d = new java.util.Date();
		Timestamp numTime = new Timestamp(d.getTime());
		return numTime;
	}

	/**
	 * 取得系统当前时间,类型为String
	 * 
	 * @return String
	 */
	public static String getNowTimeString() {
		return TimestampToString(getNowTimestamp());
	}

	/**
	 * 取得系统的当前时间,类型为String
	 * 
	 * @return String
	 */
	public static String getNowMonth() {
		return getNowTimeString().substring(0, 7);
	}

	/**
	 * 取得系统的当前年份,类型为String
	 * 
	 * @return String
	 */
	public static String getYearNow() {
		java.util.Date now = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(now);
	}

	/**
	 * 取得系统的当前月份,类型为String
	 * 
	 * @return String
	 */
	public static String getMonthNow() {
		java.util.Date now = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(now);
	}

	public static String getTimeName(){
		java.util.Date now = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(now);
		if("00".equals(hour)||"0".equals(hour)||"01".equals(hour)||"1".equals(hour)||"02".equals(hour)
				||"2".equals(hour)||"03".equals(hour)||"3".equals(hour)||"04".equals(hour)||"4".equals(hour)){
			return "凌晨";
		}else if("05".equals(hour)||"5".equals(hour)||"06".equals(hour)||"6".equals(hour)||"07".equals(hour)
				||"7".equals(hour)||"08".equals(hour)||"8".equals(hour)){
			return "早上";
		}else if("09".equals(hour)||"9".equals(hour)||"10".equals(hour)||"11".equals(hour)){
			return "上午";
		}else if("12".equals(hour)||"13".equals(hour)){
			return "中午";
		}else if("14".equals(hour)||"15".equals(hour)||"16".equals(hour)||"17".equals(hour)){
			return "下午";
		}else{
			return "晚上";
		}
	}
	
	/**
	 * 取得工资年月,类型为String
	 * 
	 * @return String
	 */
	public static String getSalaryMonth() {
		java.util.Date now = getNowDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		return formatter.format(calendar.getTime());
	}

	/**
	 * 取得系统的当前时间,类型为java.sql.Date
	 * 
	 * @return java.sql.Date
	 */
	public static java.sql.Date getNowDate() {
		java.util.Date d = new java.util.Date();
		return new java.sql.Date(d.getTime());
	}

	/**
	 * 从Timestamp类型转化为yyyy/mm/dd类型的字符串
	 * 
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String TimestampToString(Timestamp date, String strDefault) {
		String strTemp = strDefault;
		if (date != null) {
			// SimpleDateFormat formatter= new SimpleDateFormat ("yyyy/MM/dd");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/**
	 * 从Timestamp类型转化为yyyy/mm/dd类型的字符串,如果为null,侧放回""
	 * 
	 * @param date
	 * @return
	 */
	public static String TimestampToString(Timestamp date) {
		return TimestampToString(date, null);
	}

	/**
	 * date型转化为String 格式为yyyy/MM/dd
	 * 
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String DateToString(java.sql.Date date, String strDefault) {
		String strTemp = strDefault;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/**
	 * date型转化为String 格式为yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToString(java.util.Date date) {
		String strTemp = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/**
	 * date型转化为String 格式为MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToStringDay(java.util.Date date) {
		String strTemp = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/**
	 * date型转化为String 格式为hh:mm
	 * 
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String DateToString2(java.util.Date date) {
		String strTemp = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/**
	 * date型转化为String 格式为hh:mm:ss
	 * 
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String DateToString3(java.util.Date date) {
		String strTemp = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/**
	 * date型转化为String 格式为yyyy-MM
	 * 
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String DateToString3(java.sql.Date date, String strDefault) {
		String strTemp = strDefault;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	public static String DateToString2(java.sql.Date date, String strDefault) {
		String strTemp = strDefault;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	public static String DateToString(java.sql.Date date) {
		return DateToString(date, null);
	}

	/**
	 * String转化为Timestamp类型
	 * 
	 * @param strDefault
	 * @param date
	 * @return
	 */
	public static Timestamp StringToTimestamp(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date d = formatter.parse(strDate);
				Timestamp numTime = new Timestamp(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
	public static Timestamp StringToTimestampLong(String strDate){
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date d = formatter.parse(strDate);
				Timestamp numTime = new Timestamp(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * String转化为java.sql.date类型，
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date StringToDate(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date d = formatter.parse(strDate);
				java.sql.Date numTime = new java.sql.Date(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * String转化为java.sql.date类型，
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date StringToDateLong(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				java.util.Date d = formatter.parse(strDate);
				java.sql.Date numTime = new java.sql.Date(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * String转化为java.util.date类型，
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.util.Date StringToUtilDate(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				java.util.Date d = formatter.parse(strDate);
				return d;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * String("yyyy-MM-dd)转化为java.util.date类型，
	 * 
	 */
	public static java.util.Date StringToUtilDate2(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd");
				java.util.Date d = formatter.parse(strDate);
				return d;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 得到上个月的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPreDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return new Date(calendar.getTime().getTime());
	}

	/**
	 * 获取30天之前的毫秒数
	 * 
	 * @return
	 */
	public static long getThirtyDaysBefore() {
		long month = 30 * 24 * 60 * 60 * 1000l;
		return System.currentTimeMillis() - month;
	}

	/**
	 * 获取前一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPreDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return new Date(calendar.getTime().getTime());
	}
	
	/***
	 *获取前n天的日期 
	 * @param
	 * @return 
	 */
	public static Date getPreDay(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return new Date(calendar.getTime().getTime());
	}
	
	
	/**
	 * 得到上个月的月份（返回格式MM）
	 * 
	 * @param date
	 * @return
	 */
	public static String getPreMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return monthFormat.format(calendar.getTime());
	}

	/**
	 * 得到下一个月的字符串
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static String getPreMonthStr(String strDate) {
		Date fromDate = StringToDate(strDate);
		Date toDate = getPreDate(fromDate);
		return DateToString(toDate);
	}

	/**
	 * 得到当前日期的字符串
	 * 
	 * @return
	 */
	public static String getNowDateString() {
		return DateToString(getNowDate());
	}
	
	/**
	 * 获取上个星期
	 * 
	 * @param @param date
	 * @param @return
	 * @return Date
	 */
	public static Date getPreWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		return new Date(calendar.getTime().getTime());
	}
	/**
	 * 获取下个星期
	 * 
	 * @param @param date
	 * @param @return
	 * @return Date
	 */
	public static Date getAfterWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		return new Date(calendar.getTime().getTime());
	}
	
	
	/**
	 * 得到当前日期的字符串
	 * 
	 * @return
	 */
	public static String getNowDateBeginString() {
		return getNowDateString() + " 00:00:00";
	}

	public static String getYesterdayBeginString(){
		return DateToString(getPreDay(new Date())) + " 00:00:00";
	}
	
	public static String getPreDayBeginString(Date date,int pre) {
		return DateToString(getPreDay(date,pre)) + " 00:00:00";
	}
	
	public static String getNowDateEndString() {
		return getNowDateString() + " 23:59:59";
	}

	public static String getYesterdayEndString(){
		return DateToString(getPreDay(new Date())) + " 23:59:59";
	}
	
	public static long getLast7dayTime() {
		long time = StringToDateLong(getNowDateBeginString()).getTime();
		return (time - 6 * 24 * 60 * 60 * 1000);
	}
	
	//
	// public static void main(String [] args){
	// String strDate="2005-11-20 15:41:00";
	// ZzyUtil.printMessage(getMailDate(strDate));
	// }

	/**
	 * 得到日期在月份的第几周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfMonth(String strDate) {
		Date date = StringToDate(strDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);

		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 传入日期加1
	 * 
	 * @param date
	 * @return (date + 1)
	 */
	public static String getQueryDate(String date) {
		java.util.Date d = StringToDate(date);
		return formatDate(getDateAfter(d, 1));
	}

	/**
	 * 给指定日期加减天数，返回结果日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static java.util.Date getDateAfter(java.util.Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		java.util.Date newdate = calendar.getTime();
		return newdate;
	}
	
	/**
	 * 给指定日期加减天数，返回结果日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static java.util.Date getDateAfterOrBefor(java.util.Date date, int day, String timeStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		java.util.Date newdate = calendar.getTime();
		String dateStr = DateToString(newdate)+ timeStr;
		return StringToUtilDate(dateStr);
	}

	/**
	 * 返回当前时间(格式：yyyy-MM-dd hh:mm:ss)
	 */
	public static String getLongNow() {
		Calendar calendar = Calendar.getInstance();
		return longFormat.format(calendar.getTime());
	}
	
	/**
	 * 返回yyyy年MM月dd日
	 * @param date
	 * @return
	 */
	public static String getCMyFormat(java.util.Date date) {
		return cmyFormat.format(date);
	}

	/**
	 * 将字符串时间转换为java.util.date时间格式(字符串格式：yyyy-MM-dd hh:mm:ss)
	 * 
	 * @param str
	 * @return
	 */
	public static java.util.Date getDateByLongFormat(String str) {
		if (str == null || str.length() < 1)
			return null;
		java.util.Date result = null;
		try {
			result = longFormat.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 返回时间(格式：yyyy-MM-dd hh:mm:ss)
	 */
	public static String getLongTime(long time) {
		return longFormat.format(time);
	}

	/**
	 * 返回下拉刷新最后更新时间(格式 : (yyyy-)MM-dd hh:mm:ss)
	 */
	public static String getRefreshLongTime(long time) {
		String currentYear = getYearNow();
		if (longFormat.format(time).substring(0, 4).equals(currentYear)) {
			return longFormat.format(time).substring(5);
		}
		return longFormat.format(time);
	}

	/**
	 * 返回同事圈动态发布时间(格式 : (yyyy年)MM月dd日 hh:mm)
	 */
	public static String getFeedUpdateTime(long time) {
		String currentYear = getYearNow();
		if (longFormat.format(time).substring(0, 4).equals(currentYear)) {
			return getChineseDateStr2(DateToString(new Date(time))) + " "
					+ DateToString2(new Date(time));
		}
		return getChineseDateStr3(DateToString(new Date(time))) + " "
				+ DateToString2(new Date(time));
	}

	/**
	 * 获取24小时内的相对时间 (格式 : XX小时/分钟前) (格式 : (yyyy年)MM月dd日 hh:mm)
	 */
	public static String getFeedCurUpdateTime(long time, long currentTime) {
		if (currentTime < time || (currentTime - time) < 60 * 1000) {
			return "刚刚";
		}

		if ((currentTime - time) < 60 * 60 * 1000) {
			long count = (currentTime - time) / 1000 / 60;
			return count + "分钟前";
		}

		if ((currentTime - time) < 24 * 60 * 60 * 1000) {
			long count = (currentTime + 30 * 60 * 1000 - time) / 1000 / 60 / 60;
			return count + "小时前";
		}

		String currentYear = getYearNow();
		if (longFormat.format(time).substring(0, 4).equals(currentYear)) {
			return getChineseDateStr2(DateToString(new Date(time))) + " "
					+ DateToString2(new Date(time));
		}
		return getChineseDateStr3(DateToString(new Date(time))) + " "
				+ DateToString2(new Date(time));
	}

	/**
	 * 返回当前时间(格式：yyyy-MM-dd)
	 */
	public static String getNow() {
		Calendar calendar = Calendar.getInstance();
		return myFormat.format(calendar.getTime());
	}

	/**
	 * 返回当前年月（格式：yyyy-MM）
	 * 
	 * @return
	 */
	public static String getYearMonth() {
		Calendar calendar = Calendar.getInstance();
		return shortFormat.format(calendar.getTime());
	}
		

	/**
	 * 返回当前小时数(24小时)
	 * 
	 * @return
	 */
	public static int getHour() {
		Calendar calendars = Calendar.getInstance();
		int hour = calendars.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/**
	 * 获得昨天的日期
	 */
	public static String getYesterday() {
		java.util.Date d = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}
	/**
	 * 获得距离今天n的日期
	 */
	public static String getNDay(int i) {
		java.util.Date d = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_YEAR, -i);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获得昨天的月份
	 */
	public static String getYesterdayMonth() {
		java.util.Date d = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获得一个员工在公司的完整年数
	 * 
	 * @param date
	 * @return
	 */
	public static int getYearCount(java.util.Date date, java.util.Date date2) {
		int count = 0;
		// 获得员工在公司的完整年数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sdf.format(date));
		int cyear = Integer.parseInt(sdf.format(date2));
		count = cyear - year - 1;
		count = count < 0 ? 0 : count;
		return count;
	}

	public static String getChineseDateStr(long time) {
		if (isCurrentYear(time)) {
			return getChineseDateStr2(DateUtil.DateToString(new Date(time)));
		}
		return getChineseDateStr3(DateUtil.DateToString(new Date(time)));
	}

	public static String getChineseDateStr(String strdate) {
		Date date = StringToDate(strdate);
		String datearr[] = strdate.split("-");
		String week[] = { "日", "一", "二", "三", "四", "五", "六" };
		String s = datearr[0] + "年" + datearr[1] + "月" + datearr[2] + "日  星期";
		int weekday = date.getDay();
		return s + week[weekday];
	}

	public static String getChineseDateStr2(String strdate) {
		String datearr[] = strdate.split("-");
		if (datearr[1].startsWith("0"))
			datearr[1] = datearr[1].substring(1, datearr[1].length());
		String s = datearr[1] + "月" + datearr[2] + "日 ";
		return s;
	}

	public static String getChineseDateStr3(String strdate) {
		String datearr[] = strdate.split("-");
		String s = datearr[0] + "年" + datearr[1] + "月" + datearr[2] + "日 ";
		return s;
	}

	/**
	 * 是否是今年
	 */
	public static boolean isCurrentYear(long time) {
		String currentYear = getYearNow();
		if (longFormat.format(time).substring(0, 4).equals(currentYear)) {
			return true;
		}
		return false;
	}

	/**
	 * 获得年
	 */
	public static String getYear(Date date) {
		String strdate = DateToString(date);
		String datearr[] = strdate.split("-");
		return datearr[0];
	}

	/**
	 * 获得月
	 */
	public static String getMonth(Date date) {
		String strdate = DateToString(date);
		String datearr[] = strdate.split("-");
		return datearr[1];
	}

	/**
	 * 获得天
	 */
	public static String getDay(Date date) {
		String strdate = DateToString(date);
		String datearr[] = strdate.split("-");
		return datearr[2];
	}

	/**
	 * 得到某年的所有自然周：注意，calendar将周日做为周的第一天
	 * 
	 * @param year
	 * @return String[2]:String[0] 每周一字符串 String[1] 每周日字符串
	 */
	public static String[] getAllWeekStr(int year) {
		String[] result = { "", "" };
		String firstday = String.valueOf(year) + "-01-01";
		String lastday = String.valueOf(year) + "-12-31";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);// 设置年
		c.set(Calendar.WEEK_OF_YEAR, 1);// 设置周
		Calendar d = (Calendar) c.clone();
		c.set(Calendar.DAY_OF_WEEK, 1);// 设置周的第一天
		if (myFormat.format(new Date(c.getTimeInMillis())).equals(firstday)) {// 如果01-01刚好是周日
			result[0] = result[0] + firstday + "|";
			result[1] = result[1] + firstday + "|";
		}
		c.add(Calendar.DAY_OF_YEAR, 1);
		d.set(Calendar.DAY_OF_WEEK, 7);// 设置周的最后一天
		d.add(Calendar.DAY_OF_YEAR, 1);
		String weekmonday = myFormat.format(new Date(c.getTimeInMillis()));// 得到第一周的周一
		String weeksunday = myFormat.format(new Date(d.getTimeInMillis()));// 得到第一周的周日
		if (weekmonday.compareTo(firstday) > 0)
			result[0] = result[0] + weekmonday + "|";
		else
			result[0] = result[0] + firstday + "|";
		result[1] = result[1] + weeksunday + "|";
		weekmonday = firstday;
		while (weekmonday.startsWith(String.valueOf(year))) {// 循环得到所有周的周一
			c.add(Calendar.DAY_OF_YEAR, 7);
			weekmonday = myFormat.format(new Date(c.getTimeInMillis()));
			if (!weekmonday.startsWith(String.valueOf(year)))
				break;
			result[0] = result[0] + weekmonday + "|";
		}
		while (weeksunday.startsWith(String.valueOf(year))) {// 循环得到所有周的周日
			d.add(Calendar.DAY_OF_YEAR, 7);
			weeksunday = myFormat.format(new Date(d.getTimeInMillis()));
			if (!weeksunday.startsWith(String.valueOf(year))) {
				result[1] = result[1] + lastday + "|";
				break;
			}
			result[1] = result[1] + weeksunday + "|";
			if (weeksunday.equals(lastday)) {
				break;
			}
		}
		result[0] = result[0].substring(0, result[0].length() - 1);
		result[1] = result[1].substring(0, result[1].length() - 1);
		return result;
	}

	/**
	 * 得到当前日期年月日字符串 result[0]:year result[1]:month result[2]:day
	 */
	public static String[] getYearMonthDay() {
		String[] result = new String[3];
		Calendar c = Calendar.getInstance();
		c.setTime(new java.util.Date());
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (month < 10) {
			result[1] = String.valueOf("0" + month);
		} else {
			result[1] = String.valueOf(month);
		}
		if (day < 10) {
			result[2] = String.valueOf("0" + day);
		} else {
			result[2] = String.valueOf(day);
		}
		result[0] = String.valueOf(c.get(Calendar.YEAR));
		return result;
	}

	/**
	 * 返回10位的int型当前时间
	 * 
	 * @return
	 */
	public static int getCurrTime() {
		long nowTime = System.currentTimeMillis();
		String nowTimeStr = String.valueOf(nowTime).substring(0, 10);
		return Integer.parseInt(nowTimeStr);
	}

	/**
	 * 根据date获得邮件显示用准确时间
	 * 
	 * @param date
	 *            源date
	 * @return 邮件显示用准确时间
	 */
	public static String getMailDate(java.util.Date date) {
		String mailDateStr = formatDate(date);
		String curDateStr = getNowDateString();
		if (curDateStr.equals(mailDateStr)) {
			// 当天的邮件，只显示小时和分钟
			return new SimpleDateFormat("HH:mm").format(date);
		} else {
			// 不是当天的邮件，则只显示年、月、日
			return mailDateStr;
		}
	}

	/**
	 * 获得当前时间的指定相差月份的时间
	 * 
	 * @param differenceMonth
	 *            相差的月份，正数则往前推，负数则往后推
	 * @return
	 */
	public static String getPrevOrNextMonthTime(int differenceMonth) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, differenceMonth);
		String time = longFormat.format(c.getTime());
		return time;
	}


	public static String convertTimeToStr(int time) {
		return time < 10 ? "0" + time : time + "";
	}

	/**
	 * 转换毫秒数成“分、秒”，如“01:53”。若超过60分钟则显示“时、分、秒”，如“01:01:30
	 * 
	 * @param 待转换的毫秒数
	 * */
	public static String converLongTimeToStr(long startTime, long endTime) {
		long time = endTime - startTime;
		if (startTime == 0 || endTime == 0) {
			time = 0;
		}
		if (time <= 0) {
			time = 0;
		}
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;

		long hour = (time) / hh;
		long minute = (time - hour * hh) / mi;
		long second = (time - hour * hh - minute * mi) / ss;

		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		if (hour > 0) {
			return strHour + ":" + strMinute + ":" + strSecond;
		} else {
			return strMinute + ":" + strSecond;
		}
	}

	/**
	 * 语音通话时间转换函数
	 * <p>
	 * 转换毫秒数成“分、秒”，如“01:53”
	 * <p>
	 * 当通话时间大于1分钟时，显示格式为：“XX分XX秒”，如通话2小时则显示120分00秒；<br>
	 * 当通话时间小于或等于1分钟时，显示格式为：“XX秒”。
	 * 
	 * @param 待转换的毫秒数
	 * */
	public static String converLongTimeToStrForCall(long startTime, long endTime) {
		long time = endTime - startTime;
		if (startTime == 0 || endTime == 0) {
			time = 0;
		}
		if (time <= 0) {
			time = 0;
		}
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;

		long hour = (time) / hh;
		long minute = (time - hour * hh) / mi;
		long second = (time - hour * hh - minute * mi) / ss;

		@SuppressWarnings("unused")
		// String strHour = hour < 10 ? "0" + hour : "" + hour;
		// String strMinute = minute < 10 ? "0" + minute : "" + minute;
		// String strSecond = second < 10 ? "0" + second : "" + second;
		String strHour = String.valueOf(hour);
		String strMinute = String.valueOf(minute);
		String strSecond = String.valueOf(second);
		if (hour > 0) {
			return (hour * 60 + minute) + "分" + strSecond + "秒";
		} else if (minute > 0) {
			return strMinute + "分" + strSecond + "秒";
		} else {
			return strSecond + "秒";
		}
	}

	/**
	 * 视频通话时间转换函数
	 * <p>
	 * 转换毫秒数成“分、秒”，如“01:53”
	 * <p>
	 * 当通话时间大于1分钟时，显示格式为：“XX:XX”，如通话2小时则显示120:00；<br>
	 * 当通话时间小于或等于1分钟时，显示格式为：“00:XX”。
	 * 
	 * @param 待转换的毫秒数
	 * */
	public static String converLongTimeToStrForInCall(long startTime,
			long endTime) {
		long time = endTime - startTime;
		if (startTime == 0 || endTime == 0) {
			time = 0;
		}
		if (time <= 0) {
			time = 0;
		}
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;

		long hour = (time) / hh;
		long minute = (time - hour * hh) / mi;
		long second = (time - hour * hh - minute * mi) / ss;

		@SuppressWarnings("unused")
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		// String strHour = String.valueOf(hour);
		// String strMinute = String.valueOf(minute);
		// String strSecond = String.valueOf(second);
		if (hour > 0) {
			return strHour + ":" + strMinute + ":" + strSecond;
		} else if (minute > 0) {
			return strMinute + ":" + strSecond;
		} else {
			return "00" + ":" + strSecond;
		}
	}

	// public static boolean isValidLong(String str) {
	// try {
	// long _v = Long.parseLong(str);
	// return true;
	// } catch (NumberFormatException e) {
	// return false;
	// }
	// }

	/**
	 * 判断是否在时间 范围内
	 * 
	 * @param startTimeHour
	 *            起始时间小时
	 * @param startTimeMinute
	 *            起始时间分钟
	 * @param endTimeHour
	 *            结束时间小时
	 * @param endTimeMinute
	 *            结束时间分钟
	 * @return 是否在时间范围内
	 * */
	public static boolean isBetweenDate(int startTimeHour, int startTimeMinute,
			int endTimeHour, int endTimeMinute) {
		Calendar cal = Calendar.getInstance();// 当前日期
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
		int minute = cal.get(Calendar.MINUTE);// 获取分钟
		int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
		final int start = startTimeHour * 60 + startTimeMinute;// 起始时间 的分钟数
		final int end = endTimeHour * 60 + endTimeMinute;// 结束时间的分钟数
		if (minuteOfDay >= start && minuteOfDay <= end) {
			// Log.e("isBetweenDate()", "在时间 范围内");
			return true;
		} else {
			// Log.e("isBetweenDate()", "在时间 范围外");
			return false;
		}
	}

	/**
	 * 与今年比较，相同则显示：XX月XX日 HH:MM，不同年则显示：XXXX年XX月XX日 HH:MM
	 * 
	 * @param time
	 * @return
	 */
	public static String getFeedTimeString(long time) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);

		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(System.currentTimeMillis());

		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
			// 同年
			return monthDateFormat.format(c1.getTime());
		} else {
			return longFormat.format(c1.getTime());
		}
	}
	/**
	 * 给指定日期加减天数，返回结果日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static java.util.Date getDateAfterOrBefor2(java.util.Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		java.util.Date newdate = calendar.getTime();
		return newdate;
	}
	/**
	 * 转换毫秒数成“分、秒”，如“01分53秒”。若超过60分钟则显示“时、分、秒”，如“01小时01分30秒
	 * 
	 * @param 待转换的毫秒数
	 * */
	public static String converLongTimeToStr2(long startTime, long endTime) {
		long time = endTime - startTime;
		if (startTime == 0 || endTime == 0) {
			time = 0;
		}
		if (time <= 0) {
			time = 0;
		}
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;

		long hour = (time) / hh;
		long minute = (time - hour * hh) / mi;
		long second = (time - hour * hh - minute * mi) / ss;

		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		if (hour > 0) {
			return strHour + "小时" + strMinute + "分" + strSecond+"秒";
		} else {
			return strMinute + "分" + strSecond+"秒";
		}
	}
	
	/***
	 * 判断当前时间是否在beginTime和endTime之间<br/>
	 * 包含beginTime和endTime<br/>
	 * 只判断到日
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean betweenBeginAndEnd(java.util.Date beginTime,
			java.util.Date endTime) throws ParseException{
		boolean res = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		beginTime = sdf.parse(sdf.format(beginTime));
		endTime = sdf.parse(sdf.format(endTime));
		java.util.Date now = new java.util.Date();
		now = sdf.parse(sdf.format(now));
		if ((now.after(beginTime) || now.equals(beginTime))
				&& (now.before(endTime) || now.equals(endTime))) {
			res = true;
		}
		return res;
	}
	/***
	 * 判断当前时间是否在目标时间之后或者等于<br/>
	 * 只判断到日
	 * @param beginTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean afterOrEqual(java.util.Date time) throws ParseException{
		boolean res = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		time = sdf.parse(sdf.format(time));
		java.util.Date now = new java.util.Date();
		now = sdf.parse(sdf.format(now));
		if ((now.after(time) || now.equals(time))) {
			res = true;
		}
		return res;
	}
	/***
	 * 判断当前时间是否在目标时间之前或者等于<br/>
	 * 只判断到日
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean beforeOrEqual(java.util.Date time) throws ParseException{
		boolean res = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		time = sdf.parse(sdf.format(time));
		java.util.Date now = new java.util.Date();
		now = sdf.parse(sdf.format(now));
		if ((now.before(time) || now.equals(time))) {
			res = true;
		}
		return res;
	}
	/***
	 * 判断当前时间是否在目标时间之后<br/>
	 * 只判断到日
	 * @param beginTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean after(java.util.Date time) throws ParseException{
		boolean res = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		time = sdf.parse(sdf.format(time));
		java.util.Date now = new java.util.Date();
		now = sdf.parse(sdf.format(now));
		if (now.after(time)) {
			res = true;
		}
		return res;
	}
	/***
	 * 判断当前时间是否在目标时间之前<br/>
	 * 只判断到日
	 * @param beginTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean before(java.util.Date time) throws ParseException{
		boolean res = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		time = sdf.parse(sdf.format(time));
		java.util.Date now = new java.util.Date();
		now = sdf.parse(sdf.format(now));
		if (now.before(time)) {
			res = true;
		}
		return res;
	}

	/***
	 * 获得指定时间的日起始时间
	 * 如：I:2013-12-21 22:22:22
	 * O:2013-12-21 00:00:00
	 * @param time
	 * @return
	 */
	public static Date getStartOfDate(java.util.Date time){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/***
	 * 获得指定时间的日结束时间
	 * 如：I:2013-12-21 22:22:22
	 * O:2013-12-21 23:59:59
	 * @param time
	 * @return
	 */
	public static Date getEndOfDate(java.util.Date time){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.AM_PM, Calendar.PM);
		c.set(Calendar.HOUR, 11);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 59);
		return c.getTime();
	}
	
	/***
	 * 获得指定时间的明日起始时间
	 * 如：I:2013-12-21 22:22:22
	 * O:2013-12-20 00:00:00
	 * @param time
	 * @return
	 */
	public static Date getTomorrowStartOfDate(java.util.Date time){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c.getTime();
	}
	
	/***
	 * 获得指定日期的当天开始时间
	 * @param date
	 * @return
	 */
	public static java.util.Date getStartTimeOfDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/***
	 * 获得指定日期的当前结束时间
	 * @param date
	 * @return
	 */
	public static java.util.Date getEndTimeOfDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MILLISECOND, -1);
		return c.getTime();
	}
	
	/***
	 * 获得指定日期的当天开始时间
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static java.util.Date getStartTimeOfDate(String date) throws ParseException{
		Calendar c = Calendar.getInstance();
		c.setTime(myFormat.parse(date));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/***
	 * 获得指定日期的当前结束时间
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static java.util.Date getEndTimeOfDate(String date) throws ParseException{
		Calendar c = Calendar.getInstance();
		c.setTime(myFormat.parse(date));
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MILLISECOND, -1);
		return c.getTime();
	}
	
	/***
	 * 获得当前年份的起始时间
	 * @return
	 */
	public static String getCurrentYearStartTIme(){
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return myFormat.format(c.getTime());
	}
	
	/***
	 * 获得当前年份的终止时间
	 * @return
	 */
	public static String getCurrentYearEndTIme(){
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.YEAR, 1);
		c.add(Calendar.MILLISECOND, -1);
		return myFormat.format(c.getTime());
	}
	
	/**
	 * 指定时间的下一年同一时间
	 * @param String
	 * @return
	 */
	public static String getNextYearTime(Date date){
		String dateStr = DateUtil.formatDate2(date);
		String d1 = dateStr.substring(0,4);
		int si = dateStr.length();
		Integer id1 = Integer.valueOf(d1); 
		Integer id2 = id1+1;
		String nd1 = id2.toString();
		String d2 = dateStr.substring(5,19);
		String dn = nd1+"-"+d2;
		return dn;
	}
	/**
	 * 获得过去（-i）月的第一天
	 * @param i
	 * @return date
	 */
	public static Date getNextMonth(int i){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar cal_1=Calendar.getInstance();//获取当前日期 
		cal_1.add(Calendar.MONTH,i);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		String firstDayStr = format.format(cal_1.getTime());
		Date firstDay = StringToTimestamp(firstDayStr);
		return firstDay;
	}
	/**
	 * 得到明天
	 * @param i
	 * @return
	 */
	public static Date getTomorrow(){
		Date end = DateUtil.StringToTimestamp(DateUtil.getNow());
        Calendar cal = Calendar.getInstance();  
        cal.setTime(end);  
        cal.add(Calendar.DATE, +1);
        Date date=cal.getTime();
		return date;
	}
	
	/**
	 *获得昨天开始时间 
	 **/
	
	public static Date getYesterdayStartTime() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}

	/***
	 * 获得昨天结束时间
	 */	
	public static Date getYesterdayEndTime() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MILLISECOND, -1);
		return c.getTime();
	}
	/**
	 * 两个时间的差
	 * 
	 * */
	public static int differentDays(Date date1,Date date2)   {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)  {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }  else  {
            return day2-day1;
        }
    }
	
	public static void main(String[] args) {
		System.out.println(getYesterday());
		System.out.println(getNDay(7));
	}
}


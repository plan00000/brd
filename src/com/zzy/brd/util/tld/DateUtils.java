package com.zzy.brd.util.tld;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static SimpleDateFormat longFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat shortFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static String formatNormalDate(Date date){
		if (date == null){
			return "";
		}
		
		return longFormat.format(date);
	}
	
	public static String formatShortDate(Date date){
		if (date == null){
			return "";
		}
		return shortFormat.format(date);
	}
}

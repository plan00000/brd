/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证整数数字
 * 
 * @author wyr 2014年12月31日
 */
public class NumberValidationUtils {

	private static boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}

	public static boolean isPositiveInteger(String orginal) {
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
	}

	public static boolean isNegativeInteger(String orginal) {
		return isMatch("^-[1-9]\\d*", orginal);
	}

	public static boolean isWholeNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal)
				|| isNegativeInteger(orginal);
	}

	public static boolean isPositiveDecimal(String orginal) {
		return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
	}

	public static boolean isNegativeDecimal(String orginal) {
		return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
	}

	public static boolean isDecimal(String orginal) {
		return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
	}

	public static boolean isRealNumber(String orginal) {
		return isWholeNumber(orginal) || isDecimal(orginal);
	}

	/**
	 * 舍弃小数求整
	 * 
	 * @param d
	 * @return
	 */
	public static int getInt(float d) {
		return (int) Math.floor(d);
	}

	/**
	 * 是否都是0
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericZero(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (str.charAt(i) != '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为整数
	 * 
	 * @param purchses
	 * @return
	 */
	public static boolean isIntergerWhenUnit0(double purchses) {
		java.math.BigDecimal big = new java.math.BigDecimal(purchses);
		if (purchses <= 0 || String.valueOf(big).indexOf(".") != -1) {
			String pStr = String.valueOf(big).substring(
					String.valueOf(big).indexOf(".") + 1);
			if (!NumberValidationUtils.isNumericZero(pStr)) {
				return false;
			}
		}
		return true;
	}
}
package com.zzy.brd.util.validator;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class ParameterChecker {
	public static final Map<String, String> PROVINCES = new TreeMap<String, String>() {
		{
			put("110000", "北京市");
			put("120000", "天津市");
			put("130000", "河北省");
			put("140000", "山西省");
			put("150000", "内蒙古自治区");
			put("210000", "辽宁省");

			put("220000", "吉林省");
			put("230000", "黑龙江省");
			put("310000", "上海市");
			put("320000", "江苏省");
			put("330000", "浙江省");
			put("340000", "安徽省");

			put("350000", "福建省");
			put("360000", "江西省");
			put("370000", "山东省");
			put("410000", "河南省");
			put("420000", "湖北省");
			put("430000", "湖南省");

			put("440000", "广东省");
			put("450000", "广西壮族自治区");
			put("460000", "海南省");
			put("500000", "重庆市");
			put("510000", "四川省");
			put("520000", "贵州省");

			put("530000", "云南省");
			put("540000", "西藏自治区");
			put("610000", "陕西省");
			put("620000", "甘肃省");
			put("630000", "青海省");
			put("640000", "宁夏回族自治区");
			put("650000", "新疆维吾尔自治区");
		}
	};

	/**
	 * 判断是否符合规则
	 * 
	 * @param regex
	 * @param string
	 */
	public static boolean test(String regex, String string) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(string).find();
	}

	/**
	 * 判断是否符合规则
	 * 
	 * @param regex
	 * @param string
	 * @param flag
	 */
	public static boolean test(String regex, String string, int flag) {
		Pattern pattern = Pattern.compile(regex, flag);
		return pattern.matcher(string).find();
	}

	/**
	 * 判断是否含有汉字
	 * 
	 * @param string
	 */
	public static boolean containChinese(String string) {
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		return pattern.matcher(string).find();
	}

	/**
	 * 判断是否为中文开头
	 */
	public static boolean initialIsChinese(String string) {
		Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]");
		return pattern.matcher(string).find();
	}

	/**
	 * 判断是否含有空格
	 */
	public static boolean containBlank(String string) {
		Pattern pattern = Pattern.compile("[\\s]");
		return pattern.matcher(string).find();
	}

	/**
	 * 判断是否为手机或号码
	 */
	public static boolean isPhone(String string) {
		Pattern patternMobile = Pattern.compile("1[3,4,5,7,8]\\d{9}");
		Pattern patternPhone = Pattern
				.compile("(^\\+86\\.\\d{3,5}\\d{6,8}$)|(^((0\\d{2,3})(-)?)(\\d{7,8})(-(\\d{3,}))?$)");
		return patternMobile.matcher(string).matches()
				|| patternPhone.matcher(string).matches();
	}

	/**
	 * 判断是否为邮箱
	 */
	public static boolean isEmail(String string) {
		Pattern pattern = Pattern
				.compile(
						"[&~#$*%\\u4e00-\\u9fa5_0-9a-z\\-\\.\\/\\\\]+@([\\u4e00-\\u9fa5-a-z0-9]+\\.){1,5}[\\u4e00-\\u9fa5a-z]+",
						Pattern.CASE_INSENSITIVE);
		return pattern.matcher(string).matches();
	}

	/**
	 * 判断是否为链接地址
	 */
	public static boolean isUrl(String string) {
		Pattern pattern = Pattern
				.compile(
						"((http|https):\\/\\/([\\w\\-]+\\.)+[\\w\\-]+(\\/[\\w\\u4e00-\\u9fa5\\-\\.\\/?\\@\\%\\!\\&=\\+\\~\\:\\#\\;\\,]*)?)",
						Pattern.CASE_INSENSITIVE);
		return pattern.matcher(string).matches();
	}

	/**
	 * 判断是否为域名
	 */
	public static boolean isDomain(String string) {
		Pattern pattern = Pattern
				.compile(
						"^([\\x{4e00}-\\x{9fa5}-a-z0-9]+\\.){1,5}[\\x{4e00}-\\x{9fa5}a-z]+$",
						Pattern.CASE_INSENSITIVE);
		return pattern.matcher(string).matches();
	}

	/**
	 * 判断字符串为null or 空
	 */
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}

	/**
	 * 检测密码强度
	 */
	public static int checkStrength(String string) {
		int strength = 0;

		Pattern pattern = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(string).find()) {
			strength++;
		}
		pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(string).find()) {
			strength++;
		}
		/*
		 * pattern = Pattern.compile(
		 * "[\\/,.~!@#$%^&*()\\[\\]_+\\-=\\:\";'\\{\\}\\|\\\\><\\?]+",
		 * Pattern.CASE_INSENSITIVE); if (pattern.matcher(string).find()) {
		 * strength ++; }
		 */
		return strength;
	}

	/**
	 * 判断字符串是否为时间格式
	 */
	public static boolean isDate(String string) {
		Pattern pattern = Pattern.compile(
				"^([1-9]\\d{3})-([0-1]\\d)-([0-3]\\d)$",
				Pattern.CASE_INSENSITIVE);
		return pattern.matcher(string).matches();
	}

	/**
	 * 判断是否为数字
	 */
	public static boolean isNumber(String string) {
		Pattern pattern = Pattern.compile("^[1-9]\\d*$",
				Pattern.CASE_INSENSITIVE);
		return "0".equals(string) || pattern.matcher(string).matches();
	}

	/**
	 * 判断是非为 0 或 1
	 */
	public static boolean isBooleanNumber(String string) {
		return "0".equals(string) || "1".equals(string);
	}

	/**
	 * 判断是否为有效的省份编码
	 */
	public static boolean isProvinceCode(String code) {
		if (isNullOrEmpty(code)) {
			return false;
		}
		return PROVINCES.get(code.trim()) != null;
	}
	
	/****
	 * (http|ftp|https):\/\/([\w.]+\/?)\S*

Java代码中的判断如下:

Pattern pattern2 = Pattern
                .compile("(http|ftp|https):\\/\\/([\\w.]+\\/?)\\S*");
        Matcher matcher2 = pattern2.matcher(content);
	 * 
	 * */
	
	
}

/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.string;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.zzy.brd.constant.Constant;
import com.zzy.util.MD5;

/**
 * 
 * @author Administrator 2015年1月14日
 */
public class StringUtil {
	
	/**
	 * 明文密码转成存储密码 
	 */
	public static String transFormPasswordFromPlain ( String oldPwd, String sult ){
		oldPwd = MD5.hex_md5(oldPwd).toLowerCase();
		return transFormPasswordFromLowerMD5( oldPwd, sult );
	}
	
	/**
	 * 已经小写md5加密的密码转成存储密码 
	 */
	public static String transFormPasswordFromLowerMD5 ( String oldPwd, String sult ){
		byte[] hashPassword = Digests.sha1( oldPwd.getBytes(),
				Encodes.decodeHex(sult), Constant.HASH_INTERATIONS );
		String resultPwd = Encodes.encodeHex(hashPassword);
		return resultPwd;
	}

	/**
	 * SQL Like 匹配转义替换
	 */
	public static String escapeLikeString(String keyword) {
		keyword = keyword.replaceAll("\\\\", "\\\\\\\\")
				.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		return keyword;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            null、“ ”、“null”都返回true
	 * @return
	 */
	public static boolean isNullString(String str) {
		return (null == str || StringUtils.isBlank(str.trim()) || "null"
				.equals(str.trim().toLowerCase())) ? true : false;
	}

	/**
	 * 格式化字符串 如果为空，返回“”
	 * 
	 * @param str
	 * @return
	 */
	public static String formatString(String str) {
		if (isNullString(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 截取字符串，字母、汉字都可以，汉字不会截取半
	 * 
	 * @param str
	 *            字符串
	 * @param n
	 *            截取的长度，字母数，如果为汉字，一个汉字等于两个字母数
	 * @return
	 */
	public static String subStringByByte(String str, int n) {
		int num = 0;
		try {
			byte[] buf = str.getBytes("GBK");
			if (n >= buf.length) {
				return str;
			}
			boolean bChineseFirstHalf = false;
			for (int i = 0; i < n; i++) {
				if (buf[i] < 0 && !bChineseFirstHalf) {
					bChineseFirstHalf = true;
				} else {
					num++;
					bChineseFirstHalf = false;
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.substring(0, num);
	}
	
	/**
	 * 判断字符串是否包含电话号码和手机号码
	 * @param str
	 * @return
	 */
	public static boolean isContainTel(String str){
		String regStr1 = "(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)";
		String regStr2 = "\\d{3}-?\\d{7,8}|\\d{4}-?\\d{7,8}|\\d{7,8}";
		Pattern pattern = Pattern.compile(regStr1); 
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			return true;
		}
		pattern = Pattern.compile(regStr2); 
		matcher = pattern.matcher(str);
		if(matcher.find()){
			return true;
		}
		return false;
	}	
	/***
	 * 比较版本，如果v1>v2，返回大于0的整数,如果v1=v2,返回0,如果v1<v2,返回小于0的整数
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int compareVersion(String v1, String v2){
		String[] vs1 = v1.split("\\.");
		String[] vs2 = v2.split("\\.");
		for (int i = 0; i < vs1.length && i<v2.length() ; i++){
			int i1 = Integer.valueOf(vs1[i]);
			int i2 = Integer.valueOf(vs2[i]);
			if (i1 == i2){
				continue;
			}else{
				return (i1 - i2);
			}
		}
		return 0;
	}
	public static String subStringAndAppendEllipsis(String str,int num){
		if (str.length() > num){
			return str.substring(0, num)+"...";
		}else{
			return str;
		}
	}
	
	/***
	 * 将 color:rgb(xxx:xxx:xxx)转化为color:#yyyyyy
	 * @param html
	 * @return
	 */
	public static String formatColor(String html){
		String a = "color\\s*:\\s*rgb\\s*\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)";
		Pattern p = Pattern.compile(a);
		Matcher m = p.matcher(html);
		StringBuffer d = new StringBuffer();
		while(m.find()){
			StringBuffer sb = new StringBuffer();
			sb.append("color:#");
			for (int i =1; i<=m.groupCount(); i ++){
				String c = Integer.toString(Integer.valueOf(m.group(i)), 16);
				if (c.length() == 1){
					c = "0" + c;
				}
				sb.append(c);
			}
			m.appendReplacement(d, sb.toString());
		}
		m.appendTail(d);
		
		return d.toString();
		
	}
	
	/**
	 * 将一个数字转换成index位的字符串，不够补0
	 * 
	 * @param @param intstr
	 * @param @param index
	 * @param @return
	 * @return String
	 */
	public static String toStringZeroByInteger(Integer intstr,Integer index){
		StringBuilder str=new StringBuilder();
		str.append(intstr);
		if(str.length()>=index){
			return str.toString();
		}
		StringBuilder beforeStr=new StringBuilder();
		for(int i=str.length();i<index;i++){
			beforeStr.append("0");
		}
		return StringUtils.join(new String[]{beforeStr.toString(),str.toString()});
	}
	public static void main(String[] args) {
		/*String html = "<span style=\"font-size: 12px; color: #FFFFFF;\">";
		System.out.println(html);
		String reg = "<span\\s*style=\"font-size:\\s*\\(\\d+px\\);\\scolor\\s*:\\s*#\\([0-9a-fA-F]{6}\\);>";
		System.out.println(html.matches(reg));
		String html2 = "<span style=\"font-size: 12px;\">";
		System.out.println(html2);
		String reg2 = "<span\\s*style=\"font-size:\\s*\\(\\d+px\\);>";
		System.out.println(html.matches(reg2));
		String html3 = "<span style=\"font-size: 12px;\">";
		System.out.println(html3);
		String reg3 = "<span\\s*style=\"font-size:\\s*\\(\\d+px\\);>";
		System.out.println(html.matches(reg3));*/
		String html4 = "<p><span style=\"font-size: 12px; color: rgb(79, 129, 189);\">312313</span></p><p><span style=\"font-size: 12px; color: rgb(79, 129, 189);\">3213123</span></p>";
		html4 = formatColor(html4);
		System.out.println(html4);
		html4 = html4.replaceAll("span", "font");
		System.out.println(html4);
		html4 = html4.replaceAll("\"", "'");
		System.out.println(html4);
		html4 = html4.replaceAll("style='([^']*)'", "$1");
		System.out.println(html4);
		html4 = html4.replaceAll("font-size\\s*:\\s*(\\d+px);?", " size='$1' ");
		System.out.println(html4);
		html4 = html4.replaceAll("color\\s*:\\s*(#[0-9a-fA-F]+);?", " color='$1' ");
		System.out.println(html4);
				
	}
    /**
     * 判断字符串是否包含某个字符串
     * @param str1
     * @param str2
     * @return true包含，false不包含。
     */
    public static boolean isExit(String str1,String str2){
    	if(str1.indexOf(str2)!=-1){
    		return true;
    	}else{
    		return false;
    	}
    }
    
}

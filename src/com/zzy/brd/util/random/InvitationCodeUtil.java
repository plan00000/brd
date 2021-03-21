/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.random;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 邀请码生成
 * @author huangjinbing 2015年10月19日
 */
public class InvitationCodeUtil {
	public static String[] chars = new String[] { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String[] chars_ = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I",
		"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
		"W", "X", "Y", "Z","a", "b", "c", "d", "e", "f", "g", "h", "i",
		"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
		"w", "x", "y", "z" };
	
	public static String getUuid(String code,int i){
		return (chars_[i]+code);
	}
	
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x24]);
		}
		return shortBuffer.toString();
	}
	
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 100000; i++) {
			set.add(generateShortUuid());
		}
		System.out.println(set.size());
		
	}
}

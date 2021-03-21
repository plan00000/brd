/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.properties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 读取配置文件辅助类
 * 
 * @author Toni 2015年1月8日
 */
public class PropertiesHelper {
	/**
	 * 解析config.properties配置文件
	 */
	private static Map<String, String> properties = new HashMap<String, String>();

	/**
	 * 初始化配置文件
	 */
	public static void initProperties() {
		try {
			InputStream is = PropertiesHelper.class.getClassLoader()
					.getResourceAsStream("config.properties");
			InputStreamReader isr;
			isr = new InputStreamReader(is, "utf-8");
			Properties p = new Properties();
			p.load(isr);
			Enumeration<?> nameEnum = p.propertyNames();
			while (nameEnum.hasMoreElements()) {
				String name = (String) nameEnum.nextElement();
				String value = p.getProperty(name);
				properties.put(name, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取配置文件属性值
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getProperty(String propertyName) {
		String value = properties.get(propertyName);
		if (value == null) {
			System.out.println("config.properties：【" + propertyName+ "】取值失败!");
		}
		return value;
	}



}

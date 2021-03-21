/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.ipparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 城市编号
 * @author zzy 2015年2月4日
 */
public class City {
	
	/**
	 * 城市编号
	 */
	public static List<String> areaids;
	
	/**
	 * 区名称
	 */
	public static List<String> names;
	
	/**
	 * 市名称
	 */
	public static List<String> districts;
	
	/**
	 * 省名称
	 */
	public static List<String> provs;
	
	/**
	 * 初始化
	 */
	static {
		readFileByLines();
	}
	
	/**
	 * 从配置文件中读取城市信息
	 */
	public static void readFileByLines() {
		if(null==names){
			names = new ArrayList<String>();
		}
		if(null==areaids){
			areaids = new ArrayList<String>();
		}
		if(null==districts){
			districts = new ArrayList<String>();
		}
		if(null==provs){
			provs = new ArrayList<String>();
		}
		String filePath = City.class.getResource("/city.txt").getPath();
		File file = new File(filePath);  
        BufferedReader reader = null;  
        try {  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {  
            	String info[] = tempString.split("\t");
            	if(info.length==4){
            		areaids.add(info[0]);
            		names.add(new String(info[1].getBytes(), "UTF-8"));
            		districts.add(new String(info[2].getBytes(), "UTF-8"));
            		provs.add(new String(info[3].getBytes(), "UTF-8"));
            	}  
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                	e1.printStackTrace();
                }  
            }  
        }  
    }
}

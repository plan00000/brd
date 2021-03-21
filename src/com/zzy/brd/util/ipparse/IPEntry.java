/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.ipparse;

/**
 * IP实体类
 * @author zzy 2015年2月4日
 */
public class IPEntry {
	/** 
     * 起始IP 
     */
	public String beginIp;
	
	/** 
     * 结束IP 
     */
    public String endIp;
    
    /** 
     * 国家 
     */
    public String country;
    
    /** 
     * 地区 
     */
    public String area;  
      
    /** 
     * 构造函数 
     */  
    public IPEntry() {  
        beginIp = endIp = country = area = "";  
    }
}

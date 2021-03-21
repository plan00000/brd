/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.ipparse;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.util.cache.CommCache;



/**
 * 天气工具类，根据IP获取天气
 * @author zzy 2015年2月4日
 */
public class IpWeather {
	public static String prefix = "weather-";
	public static String prefixCityname = "cityname-";
	public static String defaultIp = "59.57.251.34";
	public static String defaultCityno = "101230201";
	private static IpWeather weather = new IpWeather();
	private static IPSeeker ipSeeker;
	public synchronized static IpWeather getInstance() {
		if(null==getIpSeeker()){
			setIpSeeker(new IPSeeker(ConfigSetting.Ip_File_Name,ConfigSetting.Ip_File_Dir));
		}
		return weather;
	}

	//从缓存获取天气信息
	public String getWeatherInfo(String ip){
		//缓存中获取
		String key = IpWeather.prefix+ip;
		String value = (String)CommCache.getInstance().get(key);
		
		if(null==value){
			//加入到队列中进行查询缓存
			IpParseQueue.getShareQueue().addExecuteToken(ip);
			return "";
		}else{
			return (String)value;
		}
	}
	
	//从缓存获取当前IP的地区信息
	public String getCityname(String ip){
		//缓存中获取
		String key = IpWeather.prefixCityname+ip;
		Object value = CommCache.getInstance().get(key);
		if(null==value){
			//加入到队列中进行查询缓存
			this.getWeatherInfo(ip);
			return Constant.UNKNOWN_REGION;
		}else{
			return (String)value;
		}
	}
	//从缓存获取当前IP的地区信息-用于日志
	public String getCitynameInLog(String ip){
		String cityname = getCityname(ip);
		String citys[] = cityname.split("\\|");
		StringBuffer cityBuffer = new StringBuffer();
        if(citys.length==3){
            if(citys[1].equals(citys[2])){
            	cityBuffer.append(citys[2]+"省");
            }else{
            	cityBuffer.append(citys[2]+"省"+citys[1]+"市");
            }
            
            if(!citys[0].equals(citys[1])){
            	cityBuffer.append(citys[0]);
            }
        }
        if(cityBuffer.length()>0)
        	return cityBuffer.toString();
        else
        	return cityname;
	}
	
	//获取默认的天气信息
	public String getDefaultWeatherInfo(){
		//缓存中获取
		String key = "weather-"+defaultIp;
		Object value = CommCache.getInstance().get(key);
		if(null==value)
			return "";
		else
			return (String)value;
	}

	/**
	 * @return the ipSeeker
	 */
	public static IPSeeker getIpSeeker() {
		return ipSeeker;
	}

	/**
	 * @param ipSeeker the ipSeeker to set
	 */
	public static void setIpSeeker(IPSeeker ipSeeker) {
		IpWeather.ipSeeker = ipSeeker;
	}
}

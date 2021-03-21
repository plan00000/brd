/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.ipparse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import com.google.gson.Gson;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.util.MD5;

/**
 * 天气工具类，自动组装需要的天气信息
 * @author zzy 2015年2月4日
 */
public class SmartWeatherBaseInfo {
	//GSON对象，用于解析json数据
	private static Gson gson = new Gson();
	
	public static String getWeatherInfo(String cityno){
		//通信获取天气
		String vtime = String.valueOf(System.currentTimeMillis()/1000);
		String key= "lizafy*(!@#*%&%^&*sdfas655244zzy";
		String checksum = MD5.hex_md5(key+cityno+vtime).toLowerCase();
		String param = "vtime="+vtime+"&cityId="+cityno+"&ochecksum="+checksum;
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
        try {
            URL realUrl = new URL(ConfigSetting.Weather_Server_Url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{//使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        return result;
	}
	
	public static SmartWeather fromJson(String str){
		return gson.fromJson(str, SmartWeather.class);
	}
	
	public static String getChineseWeather(SmartWeather sw, String fc){
		//厦门 阴转多云 14~8‘C 东北风 3~级
		ObjI i = sw.getObjcf().getF1().get(0);
//		ObjI i1 = sw.getObjcf().getF1().get(1);
//		ObjI i2 = sw.getObjcf().getF1().get(2);
		String area ="";
		try {
			area = new String(sw.getC().getC3().getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String w = "";
		if(null==i.getFa() || i.getFa().trim().length()==0){
			w = SmartWeatherBaseInfo.getChineseWeatherFromNo(i.getFb());
		}else{
			w = SmartWeatherBaseInfo.getChineseWeatherFromNo(i.getFa()); 
		}
		String temperature = "";
		if(null==i.getFc() || i.getFc().length()==0){
			if(null!=fc&&fc.length()>0){
				temperature = fc+"~"+i.getFd()+"℃";
			}else{
				temperature = i.getFd()+"℃";
			}
		}else{
			temperature = i.getFc()+"~"+i.getFd()+"℃";
		}
		String wd = "";
		if(null==i.getFe() || i.getFe().length()==0){
			wd = SmartWeatherBaseInfo.getChineseWindDirectionFromNo(i.getFf());
		}else{
			wd = SmartWeatherBaseInfo.getChineseWindDirectionFromNo(i.getFe());
		}
		String wp = "";
		if(null==i.getFg() || i.getFg().length()==0){
			wp = SmartWeatherBaseInfo.getChineseWindPowerFromNo(i.getFh());
		}else{
			wp = SmartWeatherBaseInfo.getChineseWindPowerFromNo(i.getFg());
		}
//		String w1 = "";
//		if(null==i1.getFa() || i1.getFa().length()==0){
//			w1 = SmartWeatherBaseInfo.getChineseWeatherFromNo(i1.getFb());
//		}else{
//			w1 = SmartWeatherBaseInfo.getChineseWeatherFromNo(i1.getFa()); 
//		}
//		String temperature1 = "";
//		if(null==i1.getFc() || i1.getFc().length()==0){
//			temperature1 = i1.getFd()+"℃";
//		}else{
//			temperature1 = i.getFd()+"~"+i1.getFc()+"℃";
//		}
//		String wd1 = "";
//		if(null==i1.getFe() || i1.getFe().length()==0){
//			wd1 = SmartWeatherBaseInfo.getChineseWindDirectionFromNo(i1.getFf());
//		}else{
//			wd1 = SmartWeatherBaseInfo.getChineseWindDirectionFromNo(i1.getFe());
//		}
//		String wp1 = "";
//		if(null==i1.getFg() || i1.getFg().length()==0){
//			wp1 = SmartWeatherBaseInfo.getChineseWindPowerFromNo(i1.getFh());
//		}else{
//			wp1 = SmartWeatherBaseInfo.getChineseWindPowerFromNo(i1.getFg());
//		}
//		String w2 = "";
//		if(null==i2.getFa() || i2.getFa().length()==0){
//			w2 = SmartWeatherBaseInfo.getChineseWeatherFromNo(i2.getFb());
//		}else{
//			w2 = SmartWeatherBaseInfo.getChineseWeatherFromNo(i2.getFa()); 
//		}
//		String temperature2 = "";
//		if(null==i2.getFc() || i2.getFc().length()==0){
//			temperature2 = i2.getFd()+"℃";
//		}else{
//			temperature2 = i.getFd()+"~"+i2.getFc()+"℃";
//		}
//		String wd2 = "";
//		if(null==i2.getFe() || i2.getFe().length()==0){
//			wd2 = SmartWeatherBaseInfo.getChineseWindDirectionFromNo(i2.getFf());
//		}else{
//			wd2 = SmartWeatherBaseInfo.getChineseWindDirectionFromNo(i2.getFe());
//		}
//		String wp2 = "";
//		if(null==i2.getFg() || i2.getFg().length()==0){
//			wp2 = SmartWeatherBaseInfo.getChineseWindPowerFromNo(i2.getFh());
//		}else{
//			wp2 = SmartWeatherBaseInfo.getChineseWindPowerFromNo(i2.getFg());
//		}
		
		return area+" "+w+" "+temperature+" "+wd+" "+wp;
//		return area+" "+w+" "+temperature+" "+wd+" "+wp+"      明天   "+w1+" "+temperature1+" "+wd1+" "+wp1+"      后天   "+w2+" "+temperature2+" "+wd2+" "+wp2;
	}
	public static String getChineseWeatherFromNo(String no){
		if("00".equals(no)){
			return "晴";
		}else if("01".equals(no)){
			return "多云";
		}else if("02".equals(no)){
			return "阴";
		}else if("03".equals(no)){
			return "阵雨";
		}else if("04".equals(no)){
			return "雷阵雨";
		}else if("05".equals(no)){
			return "雷阵雨伴有冰雹 ";
		}else if("06".equals(no)){
			return "雨夹雪";
		}else if("07".equals(no)){
			return "小雨";
		}else if("08".equals(no)){
			return "中雨 ";
		}else if("09".equals(no)){
			return "大雨 ";
		}else if("10".equals(no)){
			return "暴雨";
		}else if("11".equals(no)){
			return "大暴雨";
		}else if("12".equals(no)){
			return "特大暴雨";
		}else if("13".equals(no)){
			return "阵雪"; 
		}else if("14".equals(no)){
			return "小雪"; 
		}else if("15".equals(no)){
			return "中雪"; 
		}else if("16".equals(no)){
			return "大雪"; 
		}else if("17".equals(no)){
			return "暴雪"; 
		}else if("18".equals(no)){
			return "雾"; 
		}else if("19".equals(no)){
			return "冻雨"; 
		}else if("20".equals(no)){
			return "沙尘暴"; 
		}else if("21".equals(no)){
			return "小到中雨"; 
		}else if("22".equals(no)){
			return "中到大雨"; 
		}else if("23".equals(no)){
			return "大到暴雨"; 
		}else if("24".equals(no)){
			return "暴雨到大暴雨"; 
		}else if("25".equals(no)){
			return "大暴雨到特大暴雨"; 
		}else if("25".equals(no)){
			return "大暴雨到特大暴雨"; 
		}else if("26".equals(no)){
			return "小到中雪"; 
		}else if("27".equals(no)){
			return "中到大雪"; 
		}else if("28".equals(no)){
			return "大到暴雪"; 
		}else if("29".equals(no)){
			return "浮尘"; 
		}else if("30".equals(no)){
			return "扬沙"; 
		}else if("31".equals(no)){
			return "强沙尘暴"; 
		}else if("53".equals(no)){
			return "霾"; 
		}else if("99".equals(no)){
			return "";
		}else{
			return "";
		}
	}
	
	public static String getEngliseWeatherFromNo(String no){
		if("00".equals(no)){
			return "Sunny";
		}else if("01".equals(no)){
			return "Cloudy";
		}else if("02".equals(no)){
			return "Overcast";
		}else if("03".equals(no)){
			return "Shower";
		}else if("04".equals(no)){
			return "Thundershower";
		}else if("05".equals(no)){
			return "Thundershower with hail";
		}else if("06".equals(no)){
			return "Sleet";
		}else if("07".equals(no)){
			return "Light rain";
		}else if("08".equals(no)){
			return "Moderate rain";
		}else if("09".equals(no)){
			return "Heavy rain";
		}else if("10".equals(no)){
			return "Storm";
		}else if("11".equals(no)){
			return "Heavy storm";
		}else if("12".equals(no)){
			return "Severe storm";
		}else if("13".equals(no)){
			return "Snow flurry"; 
		}else if("14".equals(no)){
			return "Light snow"; 
		}else if("15".equals(no)){
			return "Moderate snow"; 
		}else if("16".equals(no)){
			return "Heavy snow"; 
		}else if("17".equals(no)){
			return "Snowstorm"; 
		}else if("18".equals(no)){
			return "Foggy"; 
		}else if("19".equals(no)){
			return "Ice rain"; 
		}else if("20".equals(no)){
			return "Duststorm"; 
		}else if("21".equals(no)){
			return "Light to moderate rain"; 
		}else if("22".equals(no)){
			return "Moderate to heavy rain"; 
		}else if("23".equals(no)){
			return "Heavy rain to storm"; 
		}else if("24".equals(no)){
			return "Storm to heavy storm"; 
		}else if("25".equals(no)){
			return "Heavy to severe storm"; 
		}else if("25".equals(no)){
			return "Heavy to severe storm"; 
		}else if("26".equals(no)){
			return "Light to moderate snow"; 
		}else if("27".equals(no)){
			return "Moderate to heavy snow"; 
		}else if("28".equals(no)){
			return "Heavy snow to snowstorm"; 
		}else if("29".equals(no)){
			return " Dust"; 
		}else if("30".equals(no)){
			return "Sand"; 
		}else if("31".equals(no)){
			return "Sandstorm"; 
		}else if("53".equals(no)){
			return "Haze"; 
		}else if("99".equals(no)){
			return "";
		}else{
			return "";
		}
	}
	
	public static String getChineseWindDirectionFromNo(String no){
		if("0".equals(no)){
			return "无持续风向";
		}else if("1".equals(no)){
			return "东北风";
		}else if("2".equals(no)){
			return "东风";
		}else if("3".equals(no)){
			return "东南风";
		}else if("4".equals(no)){
			return "南风";
		}else if("5".equals(no)){
			return "西南风";
		}else if("6".equals(no)){
			return "西风";
		}else if("7".equals(no)){
			return "西北风";
		}else if("8".equals(no)){
			return "北风";
		}else if("9".equals(no)){
			return "旋转风";
		}else{
			return "";
		}
	}
	
	public static String getEngliseWindDirectionFromNo(String no){
		if("0".equals(no)){
			return "No wind";
		}else if("1".equals(no)){
			return "Northeast";
		}else if("2".equals(no)){
			return "East";
		}else if("3".equals(no)){
			return "Southeast";
		}else if("4".equals(no)){
			return "South";
		}else if("5".equals(no)){
			return "Southwest";
		}else if("6".equals(no)){
			return "West";
		}else if("7".equals(no)){
			return "Northwest ";
		}else if("8".equals(no)){
			return "North";
		}else if("9".equals(no)){
			return "Whirl wind";
		}else{
			return "";
		}
	}
	
	public static String getChineseWindPowerFromNo(String no){
		if("0".equals(no)){
			return "微风";
		}else if("1".equals(no)){
			return "3-4 级";
		}else if("2".equals(no)){
			return "4-5 级";
		}else if("3".equals(no)){
			return "5-6 级";
		}else if("4".equals(no)){
			return "6-7 级";
		}else if("5".equals(no)){
			return "7-8 级";
		}else if("6".equals(no)){
			return "8-9 级";
		}else if("7".equals(no)){
			return "9-10 级 ";
		}else if("8".equals(no)){
			return "10-11 级";
		}else if("9".equals(no)){
			return "11-12 级";
		}else{
			return "";
		}
	}
	
	public static String getEngliseWindPowerFromNo(String no){
		if("0".equals(no)){
			return "<10m/h";
		}else if("1".equals(no)){
			return "10~17m/h";
		}else if("2".equals(no)){
			return "17~25m/h";
		}else if("3".equals(no)){
			return "25~34m/h";
		}else if("4".equals(no)){
			return "34~43m/h";
		}else if("5".equals(no)){
			return "43~54m/h";
		}else if("6".equals(no)){
			return "54~65m/h";
		}else if("7".equals(no)){
			return "65~77m/h ";
		}else if("8".equals(no)){
			return "77~89m/h";
		}else if("9".equals(no)){
			return "89~102m/h";
		}else{
			return "";
		}
	}
}

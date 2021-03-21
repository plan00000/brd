/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.ipparse;

import com.google.gson.Gson;


/**
 * 
 * @author zzy 2015年2月5日
 */
public class SmartWeather {
	private ObjC c;
	private ObjF f;
	
	/**
	 * @return the c
	 */
	public ObjC getC() {
		return c;
	}
	/**
	 * @param c the c to set
	 */
	public void setC(ObjC c) {
		this.c = c;
	}
	/**
	 * @return the f
	 */
	public ObjF getF() {
		return f;
	}
	/**
	 * @param f the f to set
	 */
	public void setF(ObjF f) {
		this.f = f;
	}
	/**
	 * @return the f
	 */
	public ObjF getObjcf() {
		return f;
	}
	/**
	 * @param f the f to set
	 */
	public void setObjcf(ObjF f) {
		this.f = f;
	}
	
	public static void main(String[] args) { 
		String result = "{\"c\":{\"c1\":\"101230201\",\"c10\":\"2\",\"c11\":\"0592\",\"c12\":\"361000\",\"c13\":\"118.07\",\"c14\":\"24.445\",\"c15\":\"138.3\",\"c17\":\"8\",\"c18\":\"xiamen\",\"c19\":\"厦门\",\"c2\":\"xiamen\",\"c3\":\"厦门\",\"c4\":\"xiamen\",\"c5\":\"厦门\",\"c6\":\"fujian\",\"c7\":\"厦门\",\"c8\":\"china\",\"c9\":\"厦门\"},\"f\":{\"f1\":[{\"fa\":\"01\",\"fb\":\"01\",\"fc\":\"15\",\"fd\":\"8\",\"fe\":\"1\",\"ff\":\"1\",\"fg\":\"1\",\"fh\":\"0\",\"fi\":\"06:48|17:56\"},{\"fa\":\"01\",\"fb\":\"01\",\"fc\":\"15\",\"fd\":\"8\",\"fe\":\"0\",\"ff\":\"0\",\"fg\":\"0\",\"fh\":\"0\",\"fi\":\"06:48|17:57\"},{\"fa\":\"01\",\"fb\":\"01\",\"fc\":\"17\",\"fd\":\"10\",\"fe\":\"0\",\"ff\":\"0\",\"fg\":\"0\",\"fh\":\"0\",\"fi\":\"06:47|17:57\"}],\"f0\":\"201502050800\"}}";
		Gson gson = new Gson();
		SmartWeather sw = gson.fromJson(result, SmartWeather.class);
		System.out.println(sw);
		System.out.println(sw.getC());
		System.out.println(sw.getC().getC3());
		System.out.println(sw.getObjcf());
		System.out.println(sw.getObjcf().getF0());
		System.out.println(sw.getObjcf().getF1());
		System.out.println(sw.getObjcf().getF1().get(0));
		System.out.println(sw.getObjcf().getF1().get(0).getFa());
		System.out.println(sw.getObjcf().getF1().get(0).getFb());
		System.out.println(sw.getObjcf().getF1().get(0).getFe());
		System.out.println(sw.getObjcf().getF1().get(0).getFf());
		System.out.println(sw.getObjcf().getF1().get(0).getFg());
		System.out.println(sw.getObjcf().getF1().get(0).getFh());
		System.out.println(SmartWeatherBaseInfo.getChineseWeather(sw, null));
	}
}

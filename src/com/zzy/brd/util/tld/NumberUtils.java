/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.tld;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.zzy.brd.util.bigDecimal.BigDecimalUtils;


/**
 * 
 * @author cairongjie 2015年11月30日
 */
public class NumberUtils {

	/**
	 * 
	 * 
	 * @param @param number
	 * @param @return
	 * @return String
	 */
	public static String normalNumber(BigDecimal number){
		double d=number.doubleValue();
		String s=String.format("0.2f", d);
		number=new BigDecimal(s).setScale(2, BigDecimal.ROUND_HALF_UP);
		number=number.stripTrailingZeros();
		return number.toPlainString();
	}
	/**
	 * 数值保留小数点后2位,百分比
	 * 
	 * @param @param number
	 * @param @return
	 * @return String
	 */
	public static String hundredPercentNumber(BigDecimal number){
		number=BigDecimalUtils.multiply(number, new BigDecimal(100));
		return normalNumber(number);
		
	}
	
	/**
	 * 数值保留小数点后2位，千分比
	 * 
	 * @param @param number
	 * @param @return
	 * @return String
	 */
	public static String thousandPercentNumber(BigDecimal number){
		number=BigDecimalUtils.multiply(number,new BigDecimal(1000));
		return normalNumber(number);
	}
	/**
	 * 数值保留小数点后1位,百分比
	 * 
	 * @param @param number
	 * @param @return
	 * @return String
	 */
	public static String hundredPercentNumber1(BigDecimal number){
		number=BigDecimalUtils.multiply(number,new BigDecimal(100));
		String numberStr = number.toString();
		String n1 = numberStr.substring(0,4);
		String n2 = n1+"%";
		return n2;
	}
	
}

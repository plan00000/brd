package com.zzy.brd.util.bigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

	/***
	 * a+b+c+d+.....
	 * 
	 * @param bs
	 * @return
	 */
	public static BigDecimal add(BigDecimal... bs) {
		if (bs == null) {
			return null;
		}
		if (bs.length == 1) {
			return bs[0];
		}
		BigDecimal res = new BigDecimal(0);
		for (BigDecimal b : bs) {
			res = res.add(b);
		}
		return res;
	}

	/****
	 * a-b-c-c-d-d-sd...
	 * 
	 * @param bs
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal... bs) {
		if (bs == null) {
			return null;
		}
		if (bs.length == 1) {
			return bs[0];
		}
		BigDecimal res = bs[0];
		for (int i = 1; i < bs.length; i++) {
			res = res.subtract(bs[i]);
		}
		return res;
	}

	/****
	 * a*b*c*...
	 * 
	 * @param bs
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal... bs) {
		if (bs == null) {
			return null;
		}
		if (bs.length == 1) {
			return bs[0];
		}
		BigDecimal res = bs[0];
		for (int i = 1; i < bs.length; i++) {
			res = res.multiply(bs[i]);
		}
		return res;
	}

	/****
	 * a/b/c/...
	 * 
	 * @param bs
	 * @return
	 */
	public static BigDecimal divide(BigDecimal... bs) {
		if (bs == null) {
			return null;
		}
		if (bs.length == 1) {
			return bs[0];
		}
		BigDecimal res = bs[0];
		for (int i = 1; i < bs.length; i++) {
			res = res.divide(bs[i], RoundingMode.HALF_UP);
		}
		return res;
	}
	
	 
	/***
	 * 乘法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		return a.multiply(b);
	}

	/***
	 * 加法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}

	/***
	 * 减法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		return a.subtract(b);
	}

	/***
	 * 除法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal divide(BigDecimal a, BigDecimal b) {
		return a.divide(b, RoundingMode.HALF_UP);
	}
	public static void main(String[] args) {
		
	}

}

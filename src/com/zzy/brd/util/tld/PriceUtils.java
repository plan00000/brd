package com.zzy.brd.util.tld;

import java.math.BigDecimal;

import com.zzy.brd.util.bigDecimal.BigDecimalUtils;

public class PriceUtils {

	/***
	 * 单位元
	 * 精确到小数点后两位，如果后两位有数值则显示，如果后两位没有数值则不显示
	 * @return
	 */
	public static String normalPrice(BigDecimal price){
		double d = price.doubleValue();
		String s = String.format("%.2f", d);
		price = new BigDecimal(s).setScale(2,BigDecimal.ROUND_HALF_UP);
		//price=price.stripTrailingZeros();
		return price.toPlainString();
	}
	
	/***
	 * 单位万元
	 * 精确到小数点后两位，如果后两位有数值则显示，如果后两位没有数值则不显示
	 * @param price
	 * @return
	 */
	public static String tenThousandPrice(BigDecimal price){
		price = BigDecimalUtils.divide(price, new BigDecimal(10000));
		double d = price.doubleValue();
		String s = String.format("%.2f", d);
		price = new BigDecimal(s).setScale(2,BigDecimal.ROUND_HALF_UP);
		price=price.stripTrailingZeros();
		return price.toPlainString();
	}
	
	/***
	 * 单位万元
	 * 精确到小数点后两位，如果后两位有数值则显示，如果后两位没有数值则不显示
	 * @param price
	 * @return
	 */
	public static BigDecimal showtenThousandPrice(BigDecimal price){
		price = BigDecimalUtils.divide(price, new BigDecimal(10000));
		double d = price.doubleValue();
		String s = String.format("%.2f", d);
		price = new BigDecimal(s).setScale(2,BigDecimal.ROUND_HALF_UP);
		price=price.stripTrailingZeros();
		return price;
	}
	
	/***
	 * 单位亿元
	 * 精确到小数点后两位，如果后两位有数值则显示，如果后两位没有数值则不显示
	 * @param price
	 * @return
	 */
	public static String hundredMillion(BigDecimal price){
		price = BigDecimalUtils.divide(price, new BigDecimal(100000000));
		double d = price.doubleValue();
		String s = String.format("%.2f", d);
		price = new BigDecimal(s).setScale(2,BigDecimal.ROUND_HALF_UP);
		//price=price.stripTrailingZeros();
		return price.toPlainString();
	}
	/**
	 * 显示未带单位的money
	 * 
	 * @param @param money
	 * @param @return
	 * @return String
	 */
	public static String showMoneyWithoutUnit(BigDecimal money){
		if(money==null){
			return "";
		}
		return normalMoney(money);
	}
	/**
	 * 显示带单位的money
	 * 
	 * @param @param money
	 * @param @return
	 * @return String
	 */
	public static String showMoney(BigDecimal money){
		if(money==null){
			return "0元";
		}
		return normalMoney(money)+"元";
	}
	/**
	 * 显示money
	 * 
	 * @param @param money
	 * @param @return
	 * @return String
	 */
	public static String normalMoney(BigDecimal money){
		double d=money.doubleValue();
		String s=String.format("%.2f", d);
		money=new BigDecimal(s).setScale(2,BigDecimal.ROUND_HALF_UP);
		//money=money.stripTrailingZeros();
		return money.toPlainString();
	}
	
	/***
	 * 显示价格
	 * @param price
	 * @return
	 */
	public static String showPrice(BigDecimal price){
		if (price == null){
			return "";
		}
		if (price.compareTo(new BigDecimal(10000)) < 0){
			return normalPrice(price)+"元";
		}
		if (price.compareTo(new BigDecimal(100000000)) < 0){
			return tenThousandPrice(price)+"万";
		}
		return  hundredMillion(price)+"亿";
	}
	
	/***
	 * 显示价格
	 * @param price
	 * @return
	 */
	public static String showPriceWithoutUnit(BigDecimal price){
		if (price == null){
			return "";
		}
		if (price.compareTo(new BigDecimal(10000)) < 0){
			return normalPrice(price);
		}
		if (price.compareTo(new BigDecimal(100000000)) < 0){
			return tenThousandPrice(price);
		}
		return  hundredMillion(price);
	}
	
	/***
	 * 显示利率 ‰
	 * @param price
	 * @return
	 */
	public static String showThousandRate(BigDecimal price){
		if (price == null){
			return "";
		}
		price = BigDecimalUtils.multiply(price, new BigDecimal(1000));
		price = price.setScale(3,BigDecimal.ROUND_HALF_UP);
		price=price.stripTrailingZeros();
		return price.toPlainString()+"‰";
	}
	
	/***
	 * 显示利率 ‰
	 * @param price
	 * @return
	 */
	public static String showThousandRateWithoutUnit(BigDecimal price){
		if (price == null){
			return "";
		}
		price = BigDecimalUtils.multiply(price, new BigDecimal(1000));
		price = price.setScale(3,BigDecimal.ROUND_HALF_UP);
		price=price.stripTrailingZeros();
		return price.toPlainString();
	}
	
	/***
	 * 显示利率
	 * @param price
	 * @return
	 */
	public static String showRateWithoutUnit(BigDecimal price){
		if (price == null){
			return "";
		}
		price = BigDecimalUtils.multiply(price, new BigDecimal(100));
		double d = price.doubleValue();
		String s = String.format("%.3f", d);
		price = new BigDecimal(s).setScale(3,BigDecimal.ROUND_HALF_UP);
		price=price.stripTrailingZeros();
		return price.toPlainString();
	}
	
	/***
	 * 显示利率
	 * @param price
	 * @return
	 */
	public static String showRate(BigDecimal price){
		if (price == null){
			return "";
		}
		price = BigDecimalUtils.multiply(price, new BigDecimal(100));
		price = price.setScale(3,BigDecimal.ROUND_HALF_UP);
		price=price.stripTrailingZeros();
		return price.toPlainString()+"%";
	}
	
	/***
	 * 显示月
	 * @param price
	 * @return
	 */
	public static int showToMonth(Integer price){
		price = price/30;
		return price;
	}
	
	/**
	 * 显示天
	 * 
	 * @param @param price
	 * @param @return
	 * @return int
	 */
	public static int showToDay(Integer price){
		return price;
	}
	
	/**
	 * 
	 * @param price
	 * @return
	 */
	public static String showTime(Integer price){
		if(price >= 30){
			return showToMonth(price) + "月";
		}
		if(price < 30){
			return showToDay(price) + "天";
		}
		return "";
	}
	
	/***
	 * 显示月
	 * @param price
	 * @return
	 */
	public static String showMonthWithoutUnit(Integer price){
		if (price == null){
			return "";
		}
		price = price/30;
		return price.toString();
	}
	
	/**
	 * 显示天
	 * 
	 * @param @param price
	 * @param @return
	 * @return String
	 */
	public static String showDayWithoutUnit(Integer price){
		if(price==null){
			return "";
		}
		return price.toString();
	}
	
	/***
	 * 显示月
	 * @param price
	 * @return
	 */
	public static String showMonth(Integer price){
		if (price == null){
			return "";
		}
		price = price/30;
		return price+"月";
	}
	/**
	 * 显示天
	 * 
	 * @param @param price
	 * @param @return
	 * @return String
	 */
	public static String showDay(Integer price){
		if(price==null){
			return "";
		}
		return price+"天";
	}
	/**
	 * 手机号加*号
	 * @param phone
	 * @return
	 */
	public static String showMobileno(String phone){
		String newPhone= phone.substring(0,3)+"****"+phone.substring(phone.length()-4,phone.length());
		return newPhone;
	}
	
	public static void main(String[] args) {
		System.out.println(showPriceWithoutUnit(new BigDecimal("30000000.00")));
		System.out.println("18759207501");
	}
}

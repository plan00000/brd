package com.zzy.brd.dto.req.map.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.zzy.brd.dto.req.map.baidu.ReqCreatePoiDTO;


/**
 * @author:xpk
 *    2016年11月25日-下午6:02:16
 **/
public class ReqBaiduMapBaseDTO {
	
	public String toQueryString() {
		try {
			return getQueryStr(this);

		} catch (Exception e) {
			System.out.println("查询参数解析错误:"+e.getMessage());
			return "";
		}
	}

	private String getQueryStr(Object obejct) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		StringBuffer stringBuffer = new StringBuffer();
		Class<?> myClass  =(Class<?>)obejct.getClass();
		Field[] fields = myClass.getDeclaredFields();
		if(fields !=null){
			for(Field field:fields){
				String atrributeName = field.getName();
				String upperName = atrributeName.substring(0,1).toUpperCase()+ atrributeName.substring(1);
				Method method = myClass.getMethod("get" + upperName);
				Object o = method.invoke(obejct);
				String attributeValue = o == null ? "" :o.toString();				
				stringBuffer.append(atrributeName).append("=").append(attributeValue).append("&");				
			}			
		}
		return stringBuffer.toString();
	}
	
}

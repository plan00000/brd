package com.zzy.brd.mobile.rest.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ApiUtils {
	
	/**
	 * 检验参数
	 * 
	 * @param param
	 * @param paramName
	 */
	public static boolean checkParam(String param, String paramName) {
		if (param == null) {
			return false;
		}
		if ("".equals(param.trim())) {
			// paramEmpty(paramName);
			return false;
		}
		return true;
	}

	private static HttpServletRequest getHttpRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

}

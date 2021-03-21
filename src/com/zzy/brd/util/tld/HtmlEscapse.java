package com.zzy.brd.util.tld;

import org.springframework.web.util.HtmlUtils; 

public class HtmlEscapse {
	public static String htmlUnescape(String object){
		if (object == null){
			return "";
		}
		return HtmlUtils.htmlUnescape(object);
	}
	
	public static String htmlEscape(String object) {
		if(object == null){
			return "";
		}
		return HtmlUtils.htmlEscape(object);
	}
}

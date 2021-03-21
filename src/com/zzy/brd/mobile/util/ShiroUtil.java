package com.zzy.brd.mobile.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/*import com.zzy.brd.mobile.rest.common.ZzyCheckToken;
import com.zzy.brd.mobile.rest.filter.ApiAuthenticationFilter;*/

/**
 * 手机站 shiro 工具类
 * @author lzh 2016/9/20
 *
 */
public class ShiroUtil {
	/***
	 * 获得用户id
	 * @return
	 */
	public static Long getUserId(HttpServletRequest request){
		
		Subject subject = SecurityUtils.getSubject();
		if (subject == null){
			return null;
		}
		Object principal = subject.getPrincipal();
		if (principal != null){
			if(principal instanceof IShiroPrincipal){
				Long id= ((IShiroPrincipal)principal).getUserId();
				return ((IShiroPrincipal)principal).getUserId();
			}
		}
		/*Session session = subject.getSession();
		if (session == null){
			return null;
		}
		Object oUserId = session.getAttribute(SessionKey.MOBILE_USERID);
		if (oUserId != null && oUserId instanceof Long){
			return (Long)oUserId;
		}*/
		/*ZzyCheckToken token = ApiAuthenticationFilter.getZzyToken(request);
		if (token != null){
			return token.getUserId();
		}*/
		return null;
		
	}
}

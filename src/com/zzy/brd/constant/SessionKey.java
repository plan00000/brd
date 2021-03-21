package com.zzy.brd.constant;

/***
 * session里面的key
 * @author wwy
 *
 */
public class SessionKey {
	/** 强制退出用户 */
	public final static String SESSION_FORCE_LOGOUT_KEY = "SESSION_FORCE_LOGOUT_KEY";
	/** 用户的id **/
	public final static String SESSION_CURUSER_UUID = "SESSION_CURUSER_UUID";
	/** 用户的类型 */
	public final static String SESSION_CURUSER_USERTYPE = "SESSION_CURUSER_USERTYPE";
	/** 管理员登录验证码 */
	public final static String KAPTCHA_SESSION_KEY_ADMIN_LOGIN = "KAPTCHA_SESSION_KEY_ADMIN_LOGIN";
	/** 用户忘记密码验证码 */
	public final static String KAPTCHA_SESSION_KEY_USER_FORGETPASS = "KAPTCHA_SESSION_KEY_USER_FORGETPASS";
	/** 用户忘记密码输入的手机号*/
	public final static String SESSION_KEY_FORGETPASS_PHONE = "SESSION_KEY_FORGETPASS_PHONE";
	/** 用户认证错误信息 */
	public final static String SESSION_USER_AUTH_ERROR = "SESSION_AUTH_ERROR";
	/** 员工登录权限*/
	public final static String SESSION_USER_LOGIN_AUTHORITY = "SESSION_USER_LOGIN_AUTHORITY";
	
	
	/***
	 * 第三方登录信息
	 */
	/** 第三方登录用户类型*/
	public final static String AUTH2_USER_TYPE = "AUTH2_USER_TYPE";
	/** 第三方登录用户openid*/
	public final static String AUTH2_OPENID = "AUTH2_OPENID";
	/** 第三方登录用户名称*/
	public final static String AUTH2_NICKNAME = "AUTH2_NICKNAME";
	
	
	/** 主页提示信息*/
	public final static String MAIN_EXTRA_MESSAGE = "MAIN_EXTRA_MESSAGE";
	
	/***
	 * 手机端
	 */

	/** 手机检测token状态*/
	public final static String MOBILE_CHECK_TOKEN_STATUS = "MOBILE_CHECK_TOKEN_STATUS";
}

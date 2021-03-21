package com.zzy.brd.mobile.rest.common;

import org.springside.modules.utils.PropertiesLoader;

/**
 * 返回结果值
 */
public class ResultCode {
	/** 操作成功 */
	public static final int SUCCESS = 0;

	/** 未经过登录验证 */
	public static final int COMMON_CHECK_TOKEN_FAIL = 101;
	/** 发送验证码失败 */
	public static final int COMMON_SEND_SMSCODE_FAIL = 102;
	/** 数据库中不存在 */
	public static final int COMMON_SMSCODE_NOT_EXIST = 103;
	/** 输入的校验码错误 */
	public static final int COMMON_SMSCODE_CHECK_ERROR = 104;
	/** 输入的校验码已过期 */
	public static final int COMMON_SMSCODE_EXPIRED = 105;
	/** 该用户已注册，无法发送注册验证码 */
	public static final int COMMON_SMSCODE_HAS_REGISTER = 106;
	/** 该用户不存在，无法发送重置密码验证码 */
	public static final int COMMON_SMSCODE_USER_NOT_EXIST = 107;
	/** 请求数据错误 */
	public static final int COMMON_REQUEST_DATA_ERROR = 108;
	/** 您输入的师傅推荐号码不存在，请确认后输入 */
	public final static int COMMON_PARENT_USER_NOT_EXIST = 109;
	/** 未经过登录验证 */
	public static final int COMMON_LOGIN_PWD_ERROR = 110;
	/** 该手机号码已被绑定 */
	public static final int COMMON_SMSCODE_HAS_BIND = 111;
	/** 用户在别处登录 */
	public static final int LOGIN_USER_CONFLICT = 112;
	/** 用户不存在*/
	public final static int USER_NOT_EXIST = 201;
	/** 银行帐户不存在，无法修改 */
	public final static int BANKACCOUNT_NOT_EXIST = 202;
	/** 银行账户已经绑定 */
	public final static int BANKACCOUNT_HAS_BIND = 203;
	/** 订单不存在 */
	public final static int ORDERFORM_NOT_EXIST = 204;
	/** 图片文件不存在 */
	public final static int IMAGEFILE_NOT_EXIST = 205;
	/** 设置头像错误 */
	public final static int RESET_HEADIMAGURL_FAIL = 206;
	/**个人头像文件不存在 */
	public final static int HEAD_PERSON_PICFILE_NOT_EXIST = 207;
	/** 推荐人为空 */
	public final static int COMMON_RECOMMENDED_IS_NULL = 208;
	/**输入参数为空*/
	public final static int INPUT_INFO_EMPTY=209;

	/** 图片文件格式错误 */
	public final static int IMAGEFILE_EXTENSION_ERROR = 301;
	/** 图片文件超过10M */
	public final static int IMAGEFILE_SIZE_OVER_10M = 302;
	/** 图片文件上传失败 */
	public final static int IMAGEFILE_SIZE_UPLOADFAILURE = 303;
	/** 图片文件类型错误 */
	public final static int IMAGEFILE_TYPE_ERROR = 304;
	// ************* 登录相关 ***********************
	public final static int LOGIN_TYPE_USERNAME_ERROR = 1001;// 登录用户名错误
	public final static int LOGIN_TYPE_PASSWORD_ERROR = 1002;// 登录密码错误
	public final static int LOGIN_OTHER_ERROR = 1003;// 其他错误
	public final static int LOGIN_ACCOUNT_FORBIDDEN = 1004;// 账号被禁用

	/**
	 * 用户注册相关
	 * */
	public final static int REGISTER_TYPE_MOBILE_EXIST = 1101;// 注册手机号已存在
	public final static int REGISTER_SMS_AUTHCODE_ERROR = 1102;// 手机校验码错误
	public final static int REGISTER_SMS_AUTHCODE_TIMEOUT = 1103;// 手机校验码已失效
	public final static int REGISTER_OTHER_ERROR = 1104;// 其他错误
	public final static int REGISTER_IDCARD_EXIST = 1105;// 身份证号码已被绑定
	public final static int REGISTER_ASKPERSON_ERROR = 1106;//推荐人错误 

	/**
	 * 修改密码相关
	 */
	public final static int MOD_PWD_TYPE_SAME = 1201;// 新密码和旧密码一样
	public final static int MOD_PWD_TYPE_AUTHCODE_ERROR = 1202;// 手机验证码错误
	public final static int MOD_PWD_ERROR = 1203; // 登录密码错误
	public final static int MOD_PWD_NOFOUND = 1204; // 用户不存在
	public final static int MOD_PWD_TYPE_AUTHCODE_TIMEOUT = 1205;// 手机校验码已失效
	public final static int MOD_PWD_ERROR_OLDPWD = 1206; // 旧密码错误

	/**
	 * 修改绑定手机号码
	 */
	public final static int MOD_MOBILE_TYPE_SAME = 1207;// 新手机号码和旧手机号码一样
	public final static int MOD_MOBILE_REGISTED = 1208; // 手机号已被注册
	public final static int MOD_MOBILE_ERROR = 1209; // 其他错误

	/**
	 * 绑定银行卡相关
	 */
	public final static int BIND_BANKINFO_ERROR = 1210;// 绑定银行卡错误
	public final static int BIND_NOT_BANKINFO = 1213;//未绑定银行卡
	public final static int UNBIND_BANKINFO_FAIL = 1215;//解绑失败

	/**
	 * 提取现金相关
	 */
	public final static int MOD_FLOW_WITHDRAW_ERROR = 1211; // 提现失败
	public final static int MOD_FLOW_WITHDRAW_GET_ERROR = 1212; // 提现密码错误
	public final static int MOD_LACK_MONEY = 1214;//提现余额不足

	/***
	 * 产品相关
	 */
	public final static int PRODUCT_NOT_EXIST = 1300;//产品不存在
	
	/***
	 * 消息相关
	 */
	public final static int MESSAGE_NOT_EXIST = 1400;//消息不存在
	
	/***
	 * 订单相关
	 */
	public final static int SUBMIT_ORDERFORM_FAIL = 1500;//提交订单失败
	//贷款用户已提交过其他申请
	public final static int SUBMIT_ORDERFORM_REALNAME_MOBILENO_EXIST = 1501;
 
	/**
	 * 修改提现密码
	 */
	public final static int RETPASS_WITHAW_SMS_AUTHCODE_ERROR = 1600;//手机校验码错误
	public final static int RETPASS_WITHAW_SMS_AUTHCODE_TIMEOUT = 1601;//手机校验码失效
	public final static int RETPASS_WITHAW_OTHER_ERROR = 1602;//其它错误
	public final static int RETPASS_WITHAW_TYPE_AUTHCODE_ERROR = 1603;// 其他错误
	
 
	
	private static PropertiesLoader properties;
	// private static Properties properties;
	static {
		// TODO 等后续移动
		properties = new PropertiesLoader("classpath:/resultcode.properties");
	}

	public static void init() {

	}

	/**
	 * 获得返回结果的内容
	 * 
	 * @param resultCode
	 * @return
	 */
	public static String getResultMessage(int resultCode) {
		// 读取资源文件,并处理中文乱码

		// ServletContextResource scr = null;
		// try {
		// InputStream inputStream = new FileInputStream(FilePathUtil
		// .getRootPath()
		// + "result_code.properties");

		// InputStream inputStream = new FileInputStream(
		// "./resource/result_code.properties");
		// scr = new ServletContextResource("result_code.properties");
		// properties.load(fsr.getInputStream());
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		String msg = properties.getProperty("" + resultCode, "操作失败");
		// try {
		// msg = new String(msg.getBytes("ISO-8859-1"), "GBK"); // 处理中文乱码
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		return msg;
	}

}
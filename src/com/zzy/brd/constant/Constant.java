package com.zzy.brd.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 静态变量类
 * 
 * @author zwm
 * 
 */
public class Constant {

	/**
	 * Controller返回错误页面常量
	 */
	public final static String ERROR_PAGE = "/error/error"; // 操作错误页

	/**
	 * 未知的地区
	 */
	public final static String UNKNOWN_REGION = "未知地区";
	public final static String DEFAULE_REGION = "厦门";

	/**
	 * 强制退出
	 */
	public final static String SESSION_FORCE_LOGOUT_KEY = "SESSION_FORCE_LOGOUT_KEY";
	public final static String SESSION_CURUSER_UUID = "SESSION_CURUSER_UUID";
	public final static String SESSION_CURUSER_USERTYPE = "SESSION_CURUSER_USERTYPE";
	public final static String EXITURL = "/cuser/logout";
	/**
	 * 系统编号
	 */
	public final static String PURCHASE_NO = "C";// 采购信息编号
	public final static String SEEDLINGS_NO = "M";// 苗木资源编号
	public final static String CART_NO = "G";// 购物车编号
	public final static String ORDER_NO = "D";// 订单编号
	public final static String USER_NO = "U";// 买卖家
	public final static String BROKER_NO = "B";// 经纪
	public final static String CUSTOMER_NO = "S";// 客服
	public final static String CHECK_NO = "Y"; // 验苗单

	/**
	 * 登录密码加密
	 */
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	/**
	 * 通用错误号
	 */
	public final static String CODE_SUCC = "0";
	public final static String MSG_SUCC = "succ";
	public final static String CODE_CREATE_ERROR = "1001";
	public final static String MSG_CREATE_ERROR = "create error";
	public final static String CODE_UPDATE_ERROR = "1002";
	public final static String MSG_UPDATE_ERROR = "update fail";
	public final static String CODE_DELE_ERROR = "1003";
	public final static String MSG_DELE_ERROR = "delete fail";
	public final static String CODE_NOTFOUND_ERROR = "1004";
	public final static String MSG_NOTFOUND_ERROR = "not found";
	public final static String CODE_COMM_ERROR = "1005";

	/**
	 * 权限相关错误号
	 */
	public final static String CODE_PERMISSION_ERROR = "2001";
	public final static String MSG_PERMISSION_ERROR = "permission error";

	/**
	 * 添加收货地址失败
	 */
	public final static String ADDRESS_CODE_SUCC = "0";
	public final static String ADDRESS_CODE_CREATE_ERROR = "1001";
	public final static String ADDRESS_MSG_CREATE_ERROR = "create address error";
	public final static String ADDRESS_CODE_UPDATE_ERROR = "1002";
	public final static String ADDRESS_MSG_UPDATE_ERROR = "update address error";
	public final static String ADDRESS_CODE_DELE_ERROR = "1003";
	public final static String ADDRESS_MSG_DELE_ERROR = "delete address error";

	/**
	 * 订阅苗木相关
	 */
	public final static String SUBSCRIBE_CODE_SUCC = "0";
	public final static String SUBSCRIBE_CODE_CREATE_ERROR = "1001";
	public final static String SUBSCRIBE_MSG_CREATE_ERROR = "create subscribe error";
	public final static String SUBSCRIBE_CODE_DELE_ERROR = "1002";
	public final static String SUBSCRIBE_MSG_DELE_ERROR = "delete subscribe error";
	public final static String SUBSCRIBE_CODE_WARING = "1";
	public final static String SUBSCRIBE_MSG_WARING = "the treetype had subscribed";
	public final static String SUBSCRIBE_CODE_TREETYPE_NOTEXIST = "2";
	public final static String SUBSCRIBE_MSG_TREETYPE_NOTEXIST = "treeType NotExist";

	/**
	 * 帐户信息相关
	 */
	public final static String CUSERBANKINFO_CODE_SUCC = "0";
	public final static String CUSERBANKINFO_CODE_CREATE_ERROR = "1001";
	public final static String CUSERBANKINFO_MSG_CREATE_ERROR = "create bankinfo error";
	public final static String CUSERBANKINFO_CODE_UPDATE_ERROR = "1002";
	public final static String CUSERBANKINFO_MSG_UPDATE_ERROR = "update bankinfo error";
	public final static String CUSERBANKINFO_CODE_DELE_ERROR = "1003";
	public final static String CUSERBANKINFO_MSG_DELE_ERROR = "delete bankinfo error";

	/**
	 * 公司帐户信息相关
	 */
	public final static String COMPANYACCOUNTINFO_CODE_SUCC = "0";
	public final static String COMPANYACCOUNT_CODE_CREATE_ERROR = "1001";
	public final static String COMPANYACCOUNT_MSG_CREATE_ERROR = "create companyaccount error";
	public final static String COMPANYACCOUNT_CODE_UPDATE_ERROR = "1002";
	public final static String COMPANYACCOUNT_MSG_UPDATE_ERROR = "update companyaccount error";
	public final static String COMPANYACCOUNT_CODE_DELE_ERROR = "1003";
	public final static String COMPANYACCOUNT_MSG_DELE_ERROR = "delete companyaccount error";

	/**
	 * 公司帐户类型accounttype：0私人帐户(不提供发票) 1对公帐户(提供发票)
	 */
	public final static short COMPANY_ACCOUNT_TYPE_PRIVATE = 0;
	public final static short COMPANY_ACCOUNT_TYPE_PUBLIC = 1;
	/**
	 * 公司帐户类型beDefault：是否默认帐户 0-否 1-是
	 */
	public final static short COMPANY_ACCOUNT_TYPE_SHOW_YES = 0;
	public final static short COMPANY_ACCOUNT_TYPE_SHOW_NO = 1;
	/**
	 * 保证金管理相关
	 */
	public final static String FUNDSFLOW_CODE_SUCC = "0";
	public final static String FUNDSFLOW_CODE_CREATE_ERROR = "1001";
	public final static String FUNDSFLOW_MSG_CREATE_ERROR = "create fundsflow error";
	public final static String FUNDSFLOW_CODE_UPDATE_ERROR = "1002";
	public final static String FUNDSFLOW_MSG_UPDATE_ERROR = "update fundsflow error";
	public final static String FUNDSFLOW_CODE_DELE_ERROR = "1003";
	public final static String FUNDSFLOW_MSG_DELE_ERROR = "delete fundsflow error";

	/**
	 * 用户登录相关
	 * */
	public final static int LOGIN_SUCC = 0;
	public final static int LOGIN_TYPE_USERNAME_ERROR = 1001;// 登录用户名错误
	public final static int LOGIN_TYPE_PASSWORD_ERROR = 1002;// 登录密码错误
	public final static int LOGIN_ERROR = 1003;// 其他错误

	/**
	 * 用户注册相关
	 * */
	public final static int REGISTER_SUCC = 0;
	public final static int REGISTER_TYPE_MOBILE_EXIST = 1001;// 注册手机号已存在
	public final static int REGISTER_SMS_AUTHCODE_ERROR = 1002;// 手机校验码错误
	public final static int REGISTER_SMS_AUTHCODE_TIMEOUT = 1003;// 手机校验码已失效
	public final static int REGISTER_ERROR = 1004;// 其他错误
	public final static int REGISTER_TYPE_USERNAME_EXIST = 1005;// 用户名已存在
	public final static int REGISTER_RECOMMENDED_ERROR = 1006;// 邀请人不能为自己
	public final static int REGISTER_CATCHA_ERROR = 1007;//验证码错误
	public final static int REGISTER_IDCARD_EXIST = 1008;// 身份证号码已被绑定
	public final static int REGISTER_USERNO_EXIST = 1009;//用户编号存在
	public final static int REGISTER_ASKPERSON_NOT_ON=1010;//推荐人未启用
	
	/**
	 * 用户认证信息审核状态:0审核中 1审核通过 2审核未通过
	 */
	public final static short AUDIT_NOW = 0;// 审核中
	public final static short AUDIT = 1;// 审核通过
	public final static short AUDIT_NOT_PASS = 2;// 审核不通过

	/**
	 * 修改密码相关
	 */
	public final static int MOD_PWD_SUCC = 0;
	public final static int MOD_PWD_TYPE_SAME = 1001;// 新密码和旧密码一样
	public final static int MOD_PWD_TYPE_AUTHCODE_ERROR = 1002;// 手机验证码错误
	public final static int MOD_PWD_ERROR = 1003;
	public final static int MOD_PWD_NOFOUND = 1004; // 用户不存在
	public final static int MOD_PWD_TYPE_AUTHCODE_TIMEOUT = 1005;// 手机校验码已失效
	public final static int MOD_PWD_ERROR_OLDPWD = 1006; // 旧密码错误

	/**
	 * 修改提现密码
	 */
	public final static int MOD_WITHDRAW_PWD_SUCC = 0;
	public final static int MOD_WITHDRAW_PWD_ERROR = 1003;

	/**
	 * 修改绑定手机号
	 */
	public final static int MOD_MOBILE_SUCC = 0;
	public final static int MOD_MOBILE_TYPE_SAME = 1001;// 新手机号码和旧手机号码一样
	public final static int MOD_MOBILE_TYPE_AUTHCODE_ERROR = 1002;// 手机验证码错误
	public final static int MOD_MOBILE_TYPE_NOT_FOUND = 1003;// 手机号不存在
	public final static int MOD_MOBILE_ERROR_OLDPWD = 1004; // 密码错误
	public final static int MOD_MOBILE_ERROR = 1005;
	public final static int MOD_MOBILE_REGISTED = 1006; // 手机号已被注册

	/**
	 * 绑定银行卡
	 */
	public final static int BIND_BANKINFO_SUCC = 0;
	public final static int BIND_BANKINFO_BOUND = 1001;// 银行卡已经绑定
	public final static int BIND_BANKINFO_ERROR = 1002;// 银行卡绑定错误
	public final static int BIND_BANKINFO_NO_EXIST = 1003;//银行卡信息不存在
	public final static int SET_WITHDROWPASS_NOT_EXIST = 1405;
	public final static int SET_WITHDROWPASS_EXIST = 1406;

	/***
	 * 落实苗源返回相关
	 */
	public final static String VERIFYSEED_SUCC = "1000";// 落实成功
	public final static String VERIFYSEED_UNSHELVES = "1001";// 待落实苗源已下架或者不存在
	public final static String VERIFYSEED_SELLER_UNFROZON = "1002";// 卖家保证金不足
	public final static String VERIFYSEED_ERROR = "1003";// 其他错误

	/***
	 * 关闭采购详情返回
	 */
	public final static String CLOSE_PURCHASEDETAIL_SUCC = "1000";// 关闭成功
	public final static String CLOSE_PURCHASEDETAIL_CART = "1001";// 购物车中有该项目采购信息,不能关闭
	public final static String CLOSE_PURCHASEDETAIL_ORDERFORM = "1002";// 订单中有该项目采购信息,不能关闭
	public final static String CLOSE_PURCHASEDETAIL_PRICE = "1003";// 该采购已经有报价，关闭后将无法购买
	public final static String CLOSE_PURCHASEDETAIL_ERROR = "1004";// 其他错误

	/**
	 * 后台删除客服相关
	 */
	public final static int DEL_CUSTOMER_SUCC = 0;// 删除成功
	public final static int DEL_CUSTOMER_USING = 1;// 客服还有未完成任务
	public final static int DEL_CUSTOMER_ERROR = 2;// 内部错误

	/**
	 * 后台删除标准名库相关
	 */
	public final static int DEL_STANDARD_SUCC = 0;// 删除成功
	public final static int DEL_STANDARD_ERROR = 1;// 内部错误
	
	/**
	 * 
	 */
	public final static String DEL_DEPARTMENT_SUCC="1000";
	public final static String DEL_DEPARTMENT_HAS_SONS="1001";
	public final static String DEL_DEPARTMENT_HAS_USERS="1002";
	public final static String DEL_DEPARTMENT_ERROR="1003";
	
	/**
	 * 佣金发放
	 */
	public final static String SEND_BROKERAGE_SUCC="1000";//佣金发放成功
	public final static String SEND_BROKERAGE_USER_FAIL="1001";//推荐人佣金发放失败
	public final static String SEND_BROKERAGE_FATHER="1002";//师父佣金发放失败
	public final static String SEND_BROKERAGE_GRANDFATHER="1003";//师公佣金发放失败
	public final static String SEND_BROKERAGE_FAIL="1004";//佣金发放失败
	public final static String SEND_BROKERAGE_OTHER="1005";//其他错误

	/**
	 * 信息认证验证码部分相关
	 */
	public final static String APPLY_SUCCESS = "";
	public final static String APPLY_VALIDATE_TYPE_AUTHCODE_ERROR = "验证码错误";// 手机验证码错误
	public final static String APPLY_VALIDATE_TYPE_AUTHCODE_TIMEOUT = "验证码已失效";// 手机校验码已失效

	/**
	 * 用户银行帐户类型accounttype：0私人帐户(不提供发票) 1对公帐户(提供发票)
	 */
	public final static short ACCOUNT_TYPE_PRIVATE = 0;
	public final static short ACCOUNT_TYPE_PUBLIC = 1;

	/**
	 * 用户信息认证文件上传相关
	 */
	public final static short VALIDATE_FILE_TYPE_PERSON = 0;// 个人认证信息文件类型
	public final static short VALIDATE_FILE_TYPE_COMPANY = 1;// 企业认证信息文件类型

	/** 每次延期操作延期的天数 */
	public final static short PER_DELAY_DAY = 7;

	/**
	 * 请求类型：直接url
	 */
	public final static int REQUEST_TYPE_URL = 1;

	/**
	 * 请求类型：xml格式
	 */
	public final static int REQUEST_TYPE_XML = 2;

	/**
	 * 请求类型：json格式
	 */
	public final static int REQUEST_TYPE_JSON = 3;

	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
	 */
	public static final int SUBSCRIBE_NO = 0;

	/**
	 * 用户的性别，值为1时是男性
	 */
	public static final int SEX_MALE = 1;

	/**
	 * 用户的性别，值为2时是女性
	 */
	public static final int SEX_FEMALE = 2;

	/**
	 * 户的性别，值为0时是未知
	 */
	public static final int SEX_UNKNOWN = 0;

	/**
	 * 自定义菜单最多包括3个一级菜单
	 */
	public static final int MAX_CUSTOM_MENU_SIZE = 3;

	/**
	 * 自定义菜每个一级菜单最多包含5个二级菜单
	 */
	public static final int MAX_SUB_CUSTOM_MENU_SIZE = 5;

	/**
	 * 一级菜单最多4个汉字
	 */
	public static final int MAX_CUSTOM_MENU_NAME_SIZE = 4;

	/**
	 * 二级菜单最多7个汉字
	 */
	public static final int MAX_SUB_CUSTOM_MENU_NAME_SIZE = 7;

	/**
	 * 分组名字（30个字符以内）
	 */
	public static final int MAX_GROUP_NAME_SIZE = 30;

	/**
	 * 消息类型：文本
	 */
	public static final String MSG_TYPE_TEXT = "text";

	/**
	 * 消息类型：图片
	 */
	public static final String MSG_TYPE_IMAGE = "image";

	/**
	 * 消息类型：语音
	 */
	public static final String MSG_TYPE_VOICE = "voice";

	/**
	 * 消息类型：视频
	 */
	public static final String MSG_TYPE_VIDEO = "video";

	/**
	 * 消息类型：地理位置
	 */
	public static final String MSG_TYPE_LOCATION = "location";

	/**
	 * 消息类型：链接消息
	 */
	public static final String MSG_TYPE_LINK = "link";

	/**
	 * 消息类型：事件触发
	 */
	public static final String MSG_TYPE_EVENT = "event";

	/**
	 * 事件类型:subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型:unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型:用户已关注
	 */
	public static final String EVENT_TYPE_SCAN = "scan";

	/**
	 * 事件类型:上报地理位置事件
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/**
	 * 事件类型:自定义菜单事件
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	public final static int WEIXIN_PAGE_SIZE = 4;

	/**
	 * Session中存放登录loginUser的key
	 */
	public final static String LOGIN_USER = "LOGIN_USER";
	/** 用户登录验证码*/
	public final static String KAPTCHA_SESSION_KEY_USER_LOGIN = "KAPTCHA_SESSION_KEY_USER_LOGIN";
	/** 用户注册验证码*/
	public final static String KAPTCHA_SESSION_KEY_USER_REGISTER = "KAPTCHA_SESSION_KEY_USER_REGISTER";
	
	/**
	 * 存放客户管理平台校验cookies值的key
	 */
	public final static String ADMIN_LOGIN_USER_COOKIES_KEY = "admin_cookies_vlaues";

	/**
	 * 存放功能管理平台（子站）校验cookies值的key
	 */
	public final static String LOGIN_USER_VALIDATE = "cookies_user_values";

	/**
	 * Session中存放登录验证码的key
	 */
	public final static String LOGIN_SAFECODE = "LOGIN_SAFECODE";

	/**
	 * 登录错误次数
	 */
	public final static String ERROR_LOGIN_TIMES = "ERROR_LOGIN_TIMES";

	/**
	 * Session中存放登录sysLoginUser的key
	 */
	public final static String SYS_LOGIN_USER = "SYS_LOGIN_USER";

	/**
	 * 存放校验cookies值的key
	 */
	public final static String SYS_LOGIN_USER_COOKIES_KEY = "sys_cookies_vlaues";

	/**
	 * 掺杂变量
	 */
	public final static String VALIDATE_SALT = ")!*-1=1lxl2323";

	/**
	 * 登录错误次数
	 */
	public final static String SYS_ERROR_LOGIN_TIMES = "SYS_ERROR_LOGIN_TIMES";
	public final static String SYS_ERROR_LOGIN_TIMES_AGENT = "SYS_ERROR_LOGIN_TIMES_AGENT";
	public final static String SYS_ERROR_LOGIN_TIMES_CUSTOMER = "SYS_ERROR_LOGIN_TIMES_CUSTOMER";
	public final static String SYS_ERROR_LOGIN_TIMES_ADMIN = "SYS_ERROR_LOGIN_TIMES_ADMIN";
	public final static int SYS_ERROR_LOGIN_TIMES_VALUE = 3;
	public final static String SYS_ERROR_LOGIN_USER_DISABLED = "SYS_ERROR_LOGIN_USER_DISABLED";
	public final static String SYS_ERROR_LOGIN_PERMISSION = "SYS_ERROR_LOGIN_PERMISSION";/** 登录权限*/

	/**
	 * Session中存放登录验证码的key
	 */
	public final static String SYS_LOGIN_SAFECODE = "SYS_LOGIN_SAFECODE";

	/**
	 * 登录错误次数
	 */
	public final static String REGIST_CODE = "REGIST_CODE";

	/**
	 * 登录错误次数
	 */
	public final static String REGIST_EXPIRE_TIME = "REGIST_EXPIRE_TIME";

	/**
	 * 分页大小
	 */
	public final static int PAGE_SIZE = 10;

	/**
	 * ajax查询分页大小
	 */
	public final static int AJAX_PAGE_SIZE = 50;

	/**
	 * 我的移站通产品列表分页大小
	 */
	public final static int SITE_MANAGE_PAGE_SIZE = 5;

	/**
	 * 用户ajaxSearch 分界值
	 */
	public final static int AJAX_SEARCH_WATERSHED = 300;

	/**
	 * API CHECKSUM_KEY
	 */
	public final static String CHECKSUM_KEY = "zzy_api_$*#@($&_!+__)(#@(*&@!@@zzy.cn";

	/**
	 * 系统端模拟登录的 KEY
	 */
	public final static String MONI_CHECKSUM_KEY = "@#$%^&*()###zzy";

	/**
	 * 模拟登录校验时间差
	 */
	public final static int MONI_TIMEOUT = 10;

	/**
	 * 图片上传临时路径
	 */
	public static final String TEMP_UPLOAD_DIR = "/temp";

	/**
	 * 图片上传路径
	 */
	public static final String UPLOAD_DIR = "/uploadImages";

	public static final String UPLOAD_APP_DIR = "/UploadApp";

	public static final String USER_LOGO_DIR = "/user_logo";

	public static final String SITE_AVATAR = "/site_avatar";

	public static final String VERIFY_CODE = "/verify_code";

	public static final String APP_LOGO_DIR = "/app_logo";

	public static final String LIBRARY_PICTURE_DIR = "/library_picture";

	public static final String LIBRARY_SMALL_PICTURE_SUFFIX = "_small";

	public static final String APP_DIR = "/app_dir";

	public static final String APP_ICON_DIR = "/icon";

	public static final String APP_LAUCH_DIR = "/lauch";

	public static final String APP_FILE_DIR = "/file";

	/**
	 * 获取
	 */
	public static final String SESSION_USER_SITE_ID = "user_site_id";

	public static final Map<String, String> PROVINCES = new TreeMap<String, String>() {
		{
			put("110000", "北京市");
			put("120000", "天津市");
			put("130000", "河北省");
			put("140000", "山西省");
			put("150000", "内蒙古自治区");
			put("210000", "辽宁省");

			put("220000", "吉林省");
			put("230000", "黑龙江省");
			put("310000", "上海市");
			put("320000", "江苏省");
			put("330000", "浙江省");
			put("340000", "安徽省");

			put("350000", "福建省");
			put("360000", "江西省");
			put("370000", "山东省");
			put("410000", "河南省");
			put("420000", "湖北省");
			put("430000", "湖南省");

			put("440000", "广东省");
			put("450000", "广西壮族自治区");
			put("460000", "海南省");
			put("500000", "重庆市");
			put("510000", "四川省");
			put("520000", "贵州省");

			put("530000", "云南省");
			put("540000", "西藏自治区");
			put("610000", "陕西省");
			put("620000", "甘肃省");
			put("630000", "青海省");
			put("640000", "宁夏回族自治区");
			put("650000", "新疆维吾尔自治区");
		}
	};

	/**
	 * 表情数据
	 */
	public final static Map<String, String> EMOTION_DATA = new HashMap<String, String>() {
		{
			put("0", "微笑");
			put("1", "撇嘴");
			put("2", "色");
			put("3", "发呆");
			put("4", "得意");
			put("5", "流泪");
			put("6", "害羞");
			put("7", "闭嘴");
			put("8", "睡");
			put("9", "大哭");
			put("10", "尴尬");
			put("11", "发怒");
			put("12", "调皮");
			put("13", "呲牙");
			put("14", "惊讶");
			put("15", "难过");
			put("16", "酷");
			put("17", "冷汗");
			put("18", "抓狂");
			put("19", "吐");
			put("20", "偷笑");
			put("21", "可爱");
			put("22", "白眼");
			put("23", "傲慢");
			put("24", "饥饿");
			put("25", "困");
			put("26", "惊恐");
			put("27", "流汗");
			put("28", "憨笑");
			put("29", "大兵");
			put("30", "奋斗");
			put("31", "咒骂");
			put("32", "疑问");
			put("33", "嘘");
			put("34", "晕");
			put("35", "折磨");
			put("36", "衰");
			put("37", "骷髅");
			put("38", "敲打");
			put("39", "再见");
			put("40", "擦汗");
			put("41", "抠鼻");
			put("42", "鼓掌");
			put("43", "糗大了");
			put("44", "坏笑");
			put("45", "左哼哼");
			put("46", "右哼哼");
			put("47", "哈欠");
			put("48", "鄙视");
			put("49", "委屈");
			put("50", "快哭了");
			put("51", "阴险");
			put("52", "亲亲");
			put("53", "吓");
			put("54", "可怜");
			put("55", "菜刀");
			put("56", "西瓜");
			put("57", "啤酒");
			put("58", "篮球");
			put("59", "乒乓");
			put("60", "咖啡");
			put("61", "饭");
			put("62", "猪头");
			put("63", "玫瑰");
			put("64", "凋谢");
			put("65", "示爱");
			put("66", "爱心");
			put("67", "心碎");
			put("68", "蛋糕");
			put("69", "闪电");
			put("70", "炸弹");
			put("71", "刀");
			put("72", "足球");
			put("73", "瓢虫");
			put("74", "便便");
			put("75", "月亮");
			put("76", "太阳");
			put("77", "礼物");
			put("78", "拥抱");
			put("79", "强");
			put("80", "弱");
			put("81", "握手");
			put("82", "胜利");
			put("83", "抱拳");
			put("84", "勾引");
			put("85", "拳头");
			put("86", "差劲");
			put("87", "爱你");
			put("88", "NO");
			put("89", "OK");
			put("90", "爱情");
			put("91", "飞吻");
			put("92", "跳跳");
			put("93", "发抖");
			put("94", "怄火");
			put("95", "转圈");
			put("96", "磕头");
			put("97", "回头");
			put("98", "跳绳");
			put("99", "挥手");
			put("100", "激动");
			put("101", "街舞");
			put("102", "献吻");
			put("103", "左太极");
			put("104", "右太极");
		}
	};
}

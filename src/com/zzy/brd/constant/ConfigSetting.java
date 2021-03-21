/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.constant;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.properties.PropertiesHelper;

/**
 * 系统配置类
 * @author Toni 2015年1月8日
 */
/**
 * 
 * @author lll 2015年1月17日
 */
public class ConfigSetting {
	/**
	 * 产品类型名称--提成赚佣金
	 */
	public static String typeFloorPrice = "底价加点赚差价";
	/**
	 * 产品类型名称--提成赚佣金
	 */
	public static String typePrecent = "提成赚佣金";
	
	/**
	 * PUSH中间件服务器PORT
	 */
	public static int PUSH_MIDWARE_PORT = 5556;
	/**
	 * PUSH中间件服务器IP
	 */
	public static String PUSH_MIDWARE_IP = "127.0.0.1";
	/**
	 * ANDROID PUSH OPENFIRE服务器PORT
	 */
	public static int ANDROID_OPENFIRE_PORT = 5222;
	/**
	 * ANDROID PUSH OPENFIRE服务器IP
	 */
	public static String ANDROID_OPENFIRE_IP = "192.168.132.35";
	/**
	 * ANDROID升级服务器URL
	 */
	public static String ANDROID_UPGRADE_URL = "http://192.168.132.20:8080/FlowerAndroidUpdate";
	/**
	 * 短信中间件IP
	 */
	public static String SMS_SERVER_IP = "192.168.146.186";

	/**
	 * 短信中间件端口
	 */
	public static int SMS_SERVER_PORT = 8000;

	/**
	 * 短信校验码超时时间 单位-毫秒
	 */
	public static int SMS_AUTH_CODE_TIMEOUT = 10 * 60 * 1000;
	/**
	 * 个人头像缩略图存放路径
	 */
	public static String Head_Person_Thumbnail_Dir = "";

	/**
	 * 个人头像原图存放路径
	 */
	public static String Head_Person_Pic_Dir = "";

	/**
	 * 临时文件夹
	 */
	public static String Temp_Dir = "";

	/**
	 * 正式文件夹
	 */
	public static String Pro_Dir = "";

	/**
	 * 文件服务器IP
	 */
	public static String File_Server_Ip = "";
	
	/**
	 * 纯真IP数据库文件存放路径
	 */
	public static String Ip_File_Dir = "";

	/**
	 * 纯真IP数据库文件名
	 */
	public static String Ip_File_Name = "";
	/**
	 * 天气中间件URL地址
	 */
	public static String Weather_Server_Url = "http://weather.zzy.cn/webapiweather/getWeather.php";	
	/**
	 * 初始化
	 */
	public static String baidu_api_key = "";
	/**
	 * 百度地图api 
	 * 
	 * */
	public static String baidu_map_api_for_service ="";
	/**
	 * 百度地图数据库id
	 * 
	 * */
	public static String baidu_map_api_for_web="";
	
	static {
		loadSettingConfig();
	}

	/** 路径类型 */
	public enum PathType {
		USERHEADPHOTO("userheadphoto"), // 用户头像
		APPFILES("appfiles"), // APP安装包
		WEIXINQRCODE("weixinQRcode"), // 微信站收徒二维码
		ADMINBANNER("adminBanner"), // 信息管理--banner管理
		PRODUCT_PIC("product_pic"), // 产品图片
		UEDITOR("ueditor"), // 富文本编辑上传的图片
		LOGO("logo"),//二维码中间logo图片
		QRCODEACTIVITY("qrCodeActivity"),
		;
		private String dir;

		private PathType(String desc) {
			this.dir = desc;
		}

		public static PathType value(int type) {
			if (type >= 0 && PathType.values().length > type) {
				return PathType.values()[type];
			}
			return null;
		}

		@Override
		public String toString() {
			return this.dir;
		}
	}
	/**
	 * 获取Pro_Dir
	 * @return
	 */
	public static String proDir(){
		return Pro_Dir;
	}

	/**
	 * 根据数据库保存的路径 获取实际下载文件连接
	 * 
	 * @param filePath
	 * @return
	 */
	public static String appfileProUrlByFilePath(String filePath) {
		String url = "http://" + File_Server_Ip + "/files/displayPro?filePath="
				+ filePath;
		return url.replace(File.separator, "/");
	}

	/**
	 * 根据数据库保存的路径 获取实际下载文件连接
	 * 
	 * @param filePath
	 * @return
	 */
	public static String fileProUrlByFilePath(String filePath) {
		String url = "/files/displayPro?filePath=" + filePath;

		return url;
	}

	/**
	 * 根据数据库保存的路径 获取实际缩略图下载文件连接
	 * 
	 * @param filePath
	 *            原图路径 包含文件名称
	 * @param sizeStr
	 *            缩略图尺寸描述 eg：500x500
	 * @return
	 */
	public static String thumbProUrlByFilePath(String filePath, String sizeStr) {
		String url = "/files/displayPro?filePath="
				+ thumbPathByPath(filePath, sizeStr);

		return url;
	}

	/**
	 * 根据类型获取临时路径
	 * 
	 * @param pathType
	 * @return 临时路径
	 */
	public static String tempPathByType(PathType pathType) {
		return Temp_Dir + pathType.toString() + FileUtil.FILE_SPEARATOR;
	}

	/**
	 * 根据类型获取临时路径
	 * 
	 * @param pathType
	 * @return 临时路径
	 */
	public static String proPathByType(PathType pathType) {
		return Pro_Dir + pathType.toString() + FileUtil.FILE_SPEARATOR;
	}

	/**
	 * 根据在同级目录下的缩略图路径 并创建目录
	 * 
	 * @param path
	 *            原图路径 包含文件名称
	 * @param sizeStr
	 *            缩略图尺寸描述 eg：500x500
	 * @return 返回生成的路径
	 */
	public static String thumbPathByPath(String path, String sizeStr) {
		return thumbPathByPath(path, sizeStr, "");
	}

	/**
	 * 根据在同级目录下的缩略图路径 并创建目录
	 * 
	 * @param path
	 *            原图路径 包含文件名称
	 * @param sizeStr
	 *            缩略图尺寸描述 eg：500x500
	 * @param extention
	 *            要追加的后缀，空则不追加
	 * @return 返回生成的路径
	 */
	public static String thumbPathByPath(String path, String sizeStr,
			String extention) {

		if (StringUtils.endsWith(path, FileUtil.FILE_SPEARATOR)
				|| StringUtils.isBlank(path)) {
			return null;
		}

		if (!sizeStr.endsWith(FileUtil.FILE_SPEARATOR)) {
			sizeStr = sizeStr + FileUtil.FILE_SPEARATOR;
		}

		// 创建size目录
		File tempFile = new File(path);
		File dir = new File(tempFile.getParent() + File.separator + sizeStr);
		if (!dir.exists()) {
			dir.mkdir();
		}

		StringBuilder pathBuilder = new StringBuilder(tempFile.getName());
		if (!StringUtils.isBlank(extention)) {
			pathBuilder.append("." + extention);
		}

		return dir.getPath() + File.separator + pathBuilder.toString();
	}

	/**
	 * 获取系统配置
	 */
	private static void loadSettingConfig() {
		String temp;
		try {
			// 创建初始化 读取配置文件辅助类
			PropertiesHelper.initProperties();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("创建初始化 读取配置文件辅助类[失败]");
		}
		// 获取属性
		baidu_api_key = PropertiesHelper.getProperty("baidu_api_key");
		if (null == baidu_api_key) {
			System.out.println("配置信息【baidu_api_key】取值失败!");
		} else {
			System.out
					.println("配置信息【baidu_api_key】【" + baidu_api_key + "】");
		}
		// 中间件配置
		PUSH_MIDWARE_IP = PropertiesHelper.getProperty("PUSH_MIDWARE_IP");
		if (null == PUSH_MIDWARE_IP) {
			System.out.println("配置信息【PUSH_MIDWARE_IP】取值失败!");
		} else {
			System.out
					.println("配置信息【PUSH_MIDWARE_IP】【" + PUSH_MIDWARE_IP + "】");
		}
		temp = PropertiesHelper.getProperty("PUSH_MIDWARE_PORT");
		if (null == temp) {
			System.out.println("配置信息【PUSH_MIDWARE_PORT】取值失败!");
		} else {
			PUSH_MIDWARE_PORT = Integer.parseInt(temp);
			System.out.println("配置信息【PUSH_MIDWARE_PORT】【" + PUSH_MIDWARE_PORT
					+ "】");
		}
		// OPENFIRE配置
		ANDROID_OPENFIRE_IP = PropertiesHelper
				.getProperty("ANDROID_OPENFIRE_IP");
		if (null == ANDROID_OPENFIRE_IP) {
			System.out.println("配置信息【ANDROID_OPENFIRE_IP】取值失败!");
		} else {
			System.out.println("配置信息【ANDROID_OPENFIRE_IP】【"
					+ ANDROID_OPENFIRE_IP + "】");
		}
		temp = PropertiesHelper.getProperty("ANDROID_OPENFIRE_PORT");
		if (null == temp) {
			System.out.println("配置信息【ANDROID_OPENFIRE_PORT】取值失败!");
		} else {
			ANDROID_OPENFIRE_PORT = Integer.parseInt(temp);
			System.out.println("配置信息【ANDROID_OPENFIRE_PORT】【"
					+ ANDROID_OPENFIRE_PORT + "】");
		}
		// ANDROID 升级服务器地址
		ANDROID_UPGRADE_URL = PropertiesHelper
				.getProperty("ANDROID_UPGRADE_URL");
		if (null == ANDROID_UPGRADE_URL) {
			System.out.println("配置信息【ANDROID_UPGRADE_URL】取值失败!");
		} else {
			System.out.println("配置信息【ANDROID_UPGRADE_URL】【"
					+ ANDROID_UPGRADE_URL + "】");
		}
		File_Server_Ip = PropertiesHelper.getProperty("File_Server_Ip");
		if(null==File_Server_Ip){
			System.out.println("配置信息【File_Server_Ip】取值失败!");
		}else{
			System.out.println("配置信息【File_Server_Ip】【"+File_Server_Ip+"】");
		}
		SMS_SERVER_IP = PropertiesHelper.getProperty("SMS_SERVER_IP");
		if (null == SMS_SERVER_IP) {
			System.out.println("配置信息【SMS_SERVER_IP】取值失败!");
		} else {
			System.out.println("配置信息【SMS_SERVER_IP】【" + SMS_SERVER_IP + "】");
		}
		if (null != PropertiesHelper.getProperty("SMS_SERVER_PORT")) {
			SMS_SERVER_PORT = Integer.valueOf(PropertiesHelper
					.getProperty("SMS_SERVER_PORT"));
			System.out
					.println("配置信息【SMS_SERVER_PORT】【" + SMS_SERVER_PORT + "】");
		} else {
			System.out.println("配置信息【SMS_SERVER_PORT】使用默认值!");
		}
		if (null != PropertiesHelper.getProperty("SMS_AUTH_CODE_TIMEOUT")) {
			SMS_AUTH_CODE_TIMEOUT = Integer.valueOf(PropertiesHelper
					.getProperty("SMS_AUTH_CODE_TIMEOUT"));
			System.out.println("配置信息【SMS_AUTH_CODE_TIMEOUT】【"
					+ SMS_AUTH_CODE_TIMEOUT + "】");
		} else {
			System.out.println("配置信息【SMS_AUTH_CODE_TIMEOUT】使用默认值!");
		}
		Head_Person_Thumbnail_Dir = PropertiesHelper
				.getProperty("Head_Person_Thumbnail_Dir");
		if (null == Head_Person_Thumbnail_Dir) {
			System.out.println("配置信息【Head_Person_Thumbnail_Dir】取值失败!");
		} else {
			System.out.println("配置信息【Head_Person_Thumbnail_Dir】【"
					+ Head_Person_Thumbnail_Dir + "】");
		}
		Head_Person_Pic_Dir = PropertiesHelper
				.getProperty("Head_Person_Pic_Dir");
		if (null == Head_Person_Pic_Dir) {
			System.out.println("配置信息【Head_Person_Pic_Dir】取值失败!");
		} else {
			System.out.println("配置信息【Head_Person_Pic_Dir】【"
					+ Head_Person_Pic_Dir + "】");
		}

		Temp_Dir = PropertiesHelper.getProperty("Temp_Dir");
		if (!Temp_Dir.endsWith(FileUtil.FILE_SPEARATOR)) {// 确保追加结尾文件分隔符
			Temp_Dir += FileUtil.FILE_SPEARATOR;
		}
		if (null == Temp_Dir) {
			System.out.println("配置信息【Temp_Dir】取值失败!");
		} else {
			System.out.println("配置信息【Temp_Dir】【" + Temp_Dir + "】");
		}
		Pro_Dir = PropertiesHelper.getProperty("Pro_Dir");
		if (!Pro_Dir.endsWith(FileUtil.FILE_SPEARATOR)) {// 确保追加结尾文件分隔符
			Pro_Dir += FileUtil.FILE_SPEARATOR;
		}
		if (null == Pro_Dir) {
			System.out.println("配置信息【Pro_Dir】取值失败!");
		} else {
			System.out.println("配置信息【Pro_Dir】【" + Pro_Dir + "】");
		}
		// 判断目录并创建

		File dir = new File(Head_Person_Pic_Dir);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				System.out.println("创建目录【" + Head_Person_Pic_Dir + "】失败！");
			} else {
				System.out.println("创建目录【" + Head_Person_Pic_Dir + "】成功！");
			}
		}
		dir = new File(Head_Person_Thumbnail_Dir);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				System.out
						.println("创建目录【" + Head_Person_Thumbnail_Dir + "】失败！");
			} else {
				System.out
						.println("创建目录【" + Head_Person_Thumbnail_Dir + "】成功！");
			}
		}
		baidu_map_api_for_service = PropertiesHelper.getProperty("baidu_map_api_for_service");
		if (null == baidu_map_api_for_service) {
			System.out.println("配置信息【baidu_map_api_for_service】取值失败!");
		} else {
			System.out.println("配置信息【baidu_map_api_for_service】【" + baidu_map_api_for_service + "】");
		}
		baidu_map_api_for_web = PropertiesHelper.getProperty("baidu_map_api_for_web");
		if (null == baidu_map_api_for_web) {
			System.out.println("配置信息【baidu_map_api_for_web】取值失败!");
		} else {
			System.out.println("配置信息【baidu_map_api_for_web】【" + baidu_map_api_for_web + "】");
		}
		
	}

	/**
	 * 读取省市配置文件内容
	 * 
	 * @return
	 */
	public static Map<String, ArrayList<String>> readProvinces() {
		Map<String, ArrayList<String>> countMap = null;
		try {
			countMap = Maps.newHashMap();
			InputStream is = ConfigSetting.class.getClassLoader()
					.getResourceAsStream("provincecity.txt");
			InputStreamReader isr;
			isr = new InputStreamReader(is, "utf-8");
			Properties p = new Properties();
			p.load(isr);
			Enumeration<?> nameEnum = p.propertyNames();
			while (nameEnum.hasMoreElements()) {
				String key = (String) nameEnum.nextElement();
				String value = p.getProperty(key);
				ArrayList<String> values = new ArrayList<String>();
				String[] vs = value.split(",");
				for (String s : vs) {
					values.add(s);
				}
				countMap.put(key, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countMap;
	}

	/**
	 * 读取省市配置文件里的版本
	 * 
	 * @return
	 */
	public static int readProvincesVersion() {
		int vertion = 1;
		try {
			InputStream is = ConfigSetting.class.getClassLoader()
					.getResourceAsStream("provincecity.txt");
			InputStreamReader isr;
			isr = new InputStreamReader(is, "utf-8");
			Properties p = new Properties();
			p.load(isr);
			String str = (String) p.get("version");
			vertion = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vertion;
	}

}

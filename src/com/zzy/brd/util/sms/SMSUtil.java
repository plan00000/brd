/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 花木易购
 *******************************************************************************/
package com.zzy.brd.util.sms;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzy.brd.constant.ConfigSetting;

import sun.misc.BASE64Encoder;

/**
 * 短信工具类
 * @author Toni 2014年12月22日
 */
public class SMSUtil {
	private static Logger logger = LoggerFactory.getLogger(SMSUtil.class);
	
	private static  long initId = 50000;
	/**
	 * 短信长度
	 */
	private static int SMSSEND_MAXLENGTH = 70;
	
	/***
	 * 发送短信，主要用于发送手机验证码
	 * @param id
	 * @param mobile
	 * @param content
	 * @throws Exception
	 */
	public  static void sendMessage(long id, String mobile, String content) throws Exception {
		InetAddress address = InetAddress.getByName(ConfigSetting.SMS_SERVER_IP);
		DatagramSocket datagramSocket = new DatagramSocket();
		String send = "YZT$" + id + "$" + mobile + "$"
				+ ((new BASE64Encoder().encode(content.getBytes("utf8"))).replace("\r", "").replace("\n", ""));
		DatagramPacket packet = new DatagramPacket(send.getBytes(), send.getBytes().length, address,
				Integer.valueOf(ConfigSetting.SMS_SERVER_PORT));
		datagramSocket.send(packet);
		datagramSocket.close();
		logger.info("中间件发送短信：短信id：" + id + "，手机号：" + mobile + "，内容：" + content);
	}
	
	/***
	 * 发送短信
	 * @param mobile
	 * @param content
	 * @throws Exception
	 */
	public static void sendMessage(String mobile, String content)throws Exception{
		InetAddress address = InetAddress.getByName(ConfigSetting.SMS_SERVER_IP);
		DatagramSocket datagramSocket = new DatagramSocket();
		long id = initId++;
		String send = "YZT$" + id + "$" + mobile + "$"
				+ ((new BASE64Encoder().encode(content.getBytes("utf8"))).replace("\r", "").replace("\n", ""));
		DatagramPacket packet = new DatagramPacket(send.getBytes(), send.getBytes().length, address,
				Integer.valueOf(ConfigSetting.SMS_SERVER_PORT));
		datagramSocket.send(packet);
		datagramSocket.close();
		logger.info("中间件发送短信：短信id：" + id + "，手机号：" + mobile + "，内容：" + content);
	}

	/***
	 * 发送短信（超过70）
	 * @param mobile
	 * @param content
	 * @throws Exception
	 */
	public static void sendMessage2(String mobile, String content)throws Exception{
		InetAddress address = InetAddress.getByName(ConfigSetting.SMS_SERVER_IP);
		DatagramSocket datagramSocket = new DatagramSocket();
		long id = initId++;
		int size = (content.length() + SMSSEND_MAXLENGTH -1)/SMSSEND_MAXLENGTH;
		for (int i = 0; i < size; i++) {
			int end;
			if(SMSSEND_MAXLENGTH*(i+1) > content.length()){
				end = content.length();
			}else {
				end = SMSSEND_MAXLENGTH*(i+1);
			}
			String send = "YZT$" + id + "$" + mobile + "$"
					+ ((new BASE64Encoder().encode(content.substring(SMSSEND_MAXLENGTH*i, end).getBytes("utf8"))).replace("\r", "").replace("\n", ""));
			DatagramPacket packet = new DatagramPacket(send.getBytes(), send.getBytes().length, address,
					Integer.valueOf(ConfigSetting.SMS_SERVER_PORT));
			datagramSocket.send(packet);
			logger.info("中间件发送短信(第" + (i+1) + "部分)：短信id：" + id + "，手机号：" + mobile + "，内容：" + content.substring(SMSSEND_MAXLENGTH*i, end));
		}
		datagramSocket.close();
	}
	
	/**
	 * 截断短信
	 * 
	 * @param content
	 * @return String
	 */
	public static String cutSendContent(String content) {
		return content.length() < SMSSEND_MAXLENGTH ? content : content.substring(0, SMSSEND_MAXLENGTH);
	}
	
	/**
	 * 随机获取六位数的短信校验码
	 * 
	 */
	public static String getSmscode(){
		Random random = new Random();
		StringBuffer buff = new StringBuffer();
		for(int i=0; i<6; i++){
			buff.append(random.nextInt(10));
		}
		return buff.toString();
	}
}

/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.enums.PushType;
import com.zzy.brd.util.push.PushMsg;
import com.zzy.brd.util.push.SysPushMsg;


/**
 * 
 * @author lxm 2015年3月23日
 */
public class SocketService {
	private String host = "";
	private int port = 0;
	
	public SocketService() {
		this.host = ConfigSetting.PUSH_MIDWARE_IP;
		this.port = ConfigSetting.PUSH_MIDWARE_PORT;
	}
	
	public SocketService(String host, String port) {		
		this.host = host;
		this.port = Integer.parseInt(port);
	}
	
	public SocketService(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void sendByUdp(String msg) {
		try {
			InetAddress server=InetAddress.getByName(this.host);
			DatagramSocket socket = new DatagramSocket();
			byte[] data = msg.getBytes("utf-8");
			DatagramPacket packet = new DatagramPacket(data, data.length,
					server, port);
			socket.send(packet);
			socket.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	public void sendByTcp(String msg) {
		try {
			InetAddress server = InetAddress.getByName(this.host);
			Socket socket = new Socket(server, this.port);
			socket.setSoTimeout(1000);					
			OutputStream out = socket.getOutputStream();
			out.write(msg.getBytes("utf-8"));
			out.close();
			socket.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}	
	
	public static void main(String[] args) {
	
	}
	
}

package com.zzy.brd.util.push;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zzy.brd.util.socket.SocketService;

/***
 * 推送工具类
 * 
 * @author wwy
 *
 */
public class PushUtils {
	private static ExecutorService executorService = Executors
			.newFixedThreadPool(50);

	/***
	 * 推送系统消息
	 * 
	 * @param msg
	 */
	public static void pushSysMsg(SysPushMsg msg) {
		pushMsg(msg);}


	/***
	 * 推送消息
	 * 
	 * @param msg
	 */
	private static void pushMsg(final PushMsg msg) {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				SocketService ss = new SocketService();
				ss.sendByTcp(msg.toJsonString());
			}
		});

	}

	public static void main(String[] args) {
		SysPushMsg msg = new SysPushMsg(1, "18259226101", 0, "消息", "标题", "1",1);
		pushSysMsg(msg);
	}
}

package com.zzy.brd.util.push;


import com.zzy.brd.enums.PushType;

public class SysPushMsg extends PushMsg {

	/****
	 * 
	 * @param appType 0-安卓，1-ios
	 * @param seq 推送的token
	 * @param badge 未读消息数，用于ios, 暂时不用，统一设置成0
	 * @param msg 消息
	 * @param title 标题
	 * @param messageId 消息id
	 */
	public SysPushMsg(int appType, String seq, int badge, String msg,
			String title, String messageId, Integer notifyId) {
		setAppType(appType);
		setSeq(seq);
		setBadge(badge);
		setMsg(msg);
		setTitle(title);
		setNotifyId(notifyId);
		addKeyValue("type", PushType.SYS_INFO.ordinal() + "");
		addKeyValue("messageid", messageId);
	}

}

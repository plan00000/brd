package com.zzy.brd.util.push;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class PushMsg {
	/** APP类型：1：ANDROID 用户，2：IOS用户 */
	private int appType;

	/** 用户SEQ */
	private String seq;

	/** iphone应用图标上小红圈上的数值：未读数 */
	private int badge;
	/** 消息内容 */
	private String msg;

	/** 消息标题 */
	private String title;

	/** 推送的id*/
	private Integer notifyId;
	
	/** 额外信息 */
	private Map<String, String> extra = new HashMap<String, String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("PushMsg [appType=").append(appType).append(", seq=")
				.append(seq).append(", badge=").append(badge).append(", msg=")
				.append(msg).append(",title=").append(title).append("]");
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	public String toJsonString() {
		JSONObject jo = new JSONObject();
		jo.put("appType", appType);
		jo.put("seq", seq);
		jo.put("badge", badge);
		jo.put("msg", msg);
		jo.put("title", title);
		jo.put("extra", extra);
		jo.put("notifyId", notifyId);
		return jo.toString();
	}

	public void addKeyValue(String key, String value) {
		extra.put(key, value);
	}

	public Map<String, String> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public int getBadge() {
		return badge;
	}

	public void setBadge(int badge) {
		this.badge = badge;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}

}

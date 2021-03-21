package com.zzy.brd.util.weixin;

import java.util.Map;

public class NewInfoTemplate {
	/** 贷款提交后的通知*/
	public static final int LOAD_SUBMIT_NUM = 0;
	/** 贷款提交后的通知模板id*/
	public static final String LOAD_SUBMIT_TID = "8_MLQBkNCEx_w-yQ4_vKFy6iHJE4PKqTeiZlAdkU2O8";
	/** 贷款提交后的通知模板标记*/
	public static final String LOAD_SUBMIT_FLAG = "贷款提交后的通知";
	
	/** 贷款审核成功通知 */
	public static final int AUDIT_SUCCESS_NUM = 1;
	/** 贷款审核成功通知模板id*/
	public static final String AUDIT_SUCCESS_TID = "9j2A_OXaDOde1yohURyMzYVzrjadlHh49JnZqwuY4ew";
	/** 贷款审核成功通知模板标记*/
	public static final String AUDIT_SUCCESS_FLAG = "贷款审核成功通知";
	
	/** 贷款审核失败通知 */
	public static final int AUDIT_FAILURE_NUM = 2;
	/** 贷款审核失败通知模板id*/
	public static final String AUDIT_FAILURE_TID = "2_AYsZkLdPBsRQQ7FU8XX9oYsdCoNiGfbrj4RJ8WtqU";
	/** 贷款审核失败通知模板标记*/
	public static final String AUDIT_FAILURE_FLAG = "贷款审核失败通知";
	
	/** 放款成功通知 */
	public static final int LOAD_SUCCESS_NUM = 3;
	/** 放款成功通知模板id*/
	public static final String LOAD_SUCCESS_TID = "-MoWjfOdjbH9ZS6lhqtVRn-cct35wyappqieZfw-ytQ";
	/** 放款成功模板标记*/
	public static final String LOAD_SUCCESS_FLAG = "放款成功通知";
	
	/** 佣金生成通知*/
	public static final int BROKERAGE_CREATE_NUM = 4;
	/** 佣金生成通知模板id*/
	public static final String BROKERAGE_CREATE_TID = "ITY8SN6KHQZ_BpMQWDUcjB8wgnliG-QIatEPRVwDMFI";
	/** 佣金生成通知模板标记*/
	public static final String BROKERAGE_CREATE_FLAG = "佣金生成通知";
	
	/** 佣金放款通知 */
	public static final int BROKERAGE_LOAD_NUM = 5;
	/** 佣金放款通知模板id*/
	public static final String BROKERAGE_LOAD_TID = "-MoWjfOdjbH9ZS6lhqtVRn-cct35wyappqieZfw-ytQ";
	/** 佣金放款通知模板标记*/
	public static final String BROKERAGE_LOAD_FLAG = "佣金放款通知";
	
	/** 提现到款通知*/
	public static final int WITHDRAW_ARRIVAL_NUM = 6;
	/** 提现到款通知模板id*/
	public static final String WITHDRAW_ARRIVAL_TID = "WThlutxnrywdV2cwih9v-ZJfJ7U-4wbloxiuzMWEuww";
	/** 提现到款通知模板标记*/
	public static final String WITHDRAW_ARRIVAL_FLAG = "提现到款通知";
	
    private String touser; //用户OpenID
    
    private String template_id; //模板消息ID
    
    private String url; //URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）。
    
    private String topcolor; //标题颜色
    
    private String flag; //标志
    
   // private Data data; //详细内容
    private Map<String,TemplateData> data; //详细内容
    
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, TemplateData> getData() {
		return data;
	}

	public void setData(Map<String, TemplateData> data) {
		this.data = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

    
}

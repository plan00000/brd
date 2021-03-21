package com.zzy.brd.mobile.web.dto.req.weixinpost;


import javax.validation.constraints.NotNull;

/**
 * 贷款申请dto
 * @author lzh 2016-9-29
 * 多个一样的字段 所以用0-6的命名方式 0-贷款提交成功 1-贷款审核成功 2-贷款审核失败 3-贷款放款成功 4-佣金生成 5-佣金放款 6-提现到账
 * 
 */
public class ReqWeixinPostDTO {
	/** 模板id*/
	@NotNull(message = "贷款成功提交通知模板ID不能为空")
	private String templateId0;
	/** 模板id*/
	@NotNull(message = "贷款审核成功通知模板ID不能为空")
	private String templateId1;
	/** 模板id*/
	@NotNull(message = "贷款审核失败通知模板ID不能为空")
	private String templateId2;
	/** 模板id*/
	@NotNull(message = "贷款放款成功通知模板ID不能为空")
	private String templateId3;
	/** 模板id*/
    @NotNull(message = "佣金生成通知模板ID不能为空")
	private String templateId4;
	/** 模板id*/
	@NotNull(message = "佣金放款通知模板ID不能为空")
	private String templateId5;
	/** 模板id*/
	@NotNull(message = "提现到账通知模板ID不能为空")
	private String templateId6;
	
	
	/** 备注*/
	private String remark0;
	/** 备注*/
	private String remark1;
	/** 备注*/
	private String remark2;
	/** 备注*/
	private String remark3;
	/** 备注*/
	private String remark4;
	/** 备注*/
	private String remark5;
	/** 备注*/
	private String remark6;
	
	/** 首段*/
	private String first0;
	/** 首段*/
	private String first1;
	/** 首段*/
	private String first2;
	/** 首段*/
	private String first3;
	/** 首段*/
	private String first4;
	/** 首段*/
	private String first5;
	/** 首段*/
	private String first6;
	
	/**状态*/
	private int state0;
	private String state0str;
	/**状态*/
	private int state1;
	/**状态*/
	private int state2;
	/**状态*/
	private int state3;
	/**状态*/
	private int state4;
	/**状态*/
	private int state5;
	/**状态*/
	private int state6;
	
	/** app秘钥*/
	private String appsecret;
	/** appid*/
	private String appid;
	/** appid*/
	private Long sysInfoId;
	

	
	public String getState0str() {
		return state0str;
	}
	public void setState0str(String state0str) {
		this.state0str = state0str;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTemplateId0() {
		return templateId0;
	}
	public void setTemplateId0(String templateId0) {
		this.templateId0 = templateId0;
	}
	public String getTemplateId1() {
		return templateId1;
	}
	public void setTemplateId1(String templateId1) {
		this.templateId1 = templateId1;
	}
	public String getTemplateId2() {
		return templateId2;
	}
	public void setTemplateId2(String templateId2) {
		this.templateId2 = templateId2;
	}
	public String getTemplateId3() {
		return templateId3;
	}
	public void setTemplateId3(String templateId3) {
		this.templateId3 = templateId3;
	}
	public String getTemplateId4() {
		return templateId4;
	}
	public void setTemplateId4(String templateId4) {
		this.templateId4 = templateId4;
	}
	public String getTemplateId5() {
		return templateId5;
	}
	public void setTemplateId5(String templateId5) {
		this.templateId5 = templateId5;
	}
	public String getTemplateId6() {
		return templateId6;
	}
	public void setTemplateId6(String templateId6) {
		this.templateId6 = templateId6;
	}
	public String getRemark0() {
		return remark0;
	}
	public void setRemark0(String remark0) {
		this.remark0 = remark0;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getRemark5() {
		return remark5;
	}
	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}
	public String getRemark6() {
		return remark6;
	}
	public void setRemark6(String remark6) {
		this.remark6 = remark6;
	}
	public String getFirst0() {
		return first0;
	}
	public void setFirst0(String first0) {
		this.first0 = first0;
	}
	public String getFirst1() {
		return first1;
	}
	public void setFirst1(String first1) {
		this.first1 = first1;
	}
	public String getFirst2() {
		return first2;
	}
	public void setFirst2(String first2) {
		this.first2 = first2;
	}
	public String getFirst3() {
		return first3;
	}
	public void setFirst3(String first3) {
		this.first3 = first3;
	}
	public String getFirst4() {
		return first4;
	}
	public void setFirst4(String first4) {
		this.first4 = first4;
	}
	public String getFirst5() {
		return first5;
	}
	public void setFirst5(String first5) {
		this.first5 = first5;
	}
	public String getFirst6() {
		return first6;
	}
	public void setFirst6(String first6) {
		this.first6 = first6;
	}
	public Long getSysInfoId() {
		return sysInfoId;
	}
	public void setSysInfoId(Long sysInfoId) {
		this.sysInfoId = sysInfoId;
	}
	public int getState0() {
		return state0;
	}
	public void setState0(int state0) {
		this.state0 = state0;
	}
	public int getState1() {
		return state1;
	}
	public void setState1(int state1) {
		this.state1 = state1;
	}
	public int getState2() {
		return state2;
	}
	public void setState2(int state2) {
		this.state2 = state2;
	}
	public int getState3() {
		return state3;
	}
	public void setState3(int state3) {
		this.state3 = state3;
	}
	public int getState4() {
		return state4;
	}
	public void setState4(int state4) {
		this.state4 = state4;
	}
	public int getState5() {
		return state5;
	}
	public void setState5(int state5) {
		this.state5 = state5;
	}
	public int getState6() {
		return state6;
	}
	public void setState6(int state6) {
		this.state6 = state6;
	}
	
}

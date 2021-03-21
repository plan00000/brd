package com.zzy.brd.util.weixin;

public class Token {
	// 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn;
    
    private String templateId;

    private long expiresTime;
    
    public String getToken() {
    	return token;
    }
    public void setToken(String token) {
    	this.token = token;
    }
    public int getExpiresIn() {
    	return expiresIn;
    }
    public void setExpiresIn(int expiresIn) {
    	this.expiresIn = expiresIn;
    }
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }
}

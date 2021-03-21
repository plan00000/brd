package com.zzy.brd.dto.req.admin.product;

import javax.validation.constraints.NotNull;

/**
 * 添加产品类型
 * @author lzh 2016/10/19
 *
 */
public class ReqAdminProductTypeDTO {
	/** 提单类型*/
	@NotNull(message= "提单类型不能为空")
	private int billType;
	/** 产品类型-类别*/
	@NotNull(message = "产品类别不能为空")
	private String productName;
	public int getBillType() {
		return billType;
	}
	public void setBillType(int billType) {
		this.billType = billType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}

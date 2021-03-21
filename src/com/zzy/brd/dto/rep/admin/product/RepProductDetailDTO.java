package com.zzy.brd.dto.rep.admin.product;

import java.math.BigDecimal;

import com.zzy.brd.entity.Product;
import com.zzy.brd.util.tld.PriceUtils;

/**
 * 获取产品信息
 * @author lzh 2016/11/18
 *
 */
public class RepProductDetailDTO {
	/** 产品id*/
	private long productId;
	/**产品名称 */
	private String productName;
	/**提单类型 */
	private String billType;
	/**产品类型 */
	private String typeName;
	/**贷款利率 */
	private String loanRate;
	
	public RepProductDetailDTO(Product product){
		this.productId = product.getId();
		this.productName = product.getInfo().getProductName();
		this.billType = product.getType().getBillType().getDes();
		this.typeName = product.getType().getProductName();
		if(product.getInterestType().ordinal() ==1){
			this.loanRate = PriceUtils.showThousandRateWithoutUnit(product.getInfo().getLoanRate()) + "‰/日";
		}else{
			this.loanRate = PriceUtils.showRateWithoutUnit(product.getInfo().getLoanRate()) + "%/月";
		}
		
	}
	
	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	
	
	
}

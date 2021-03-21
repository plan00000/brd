package com.zzy.brd.mobile.web.dto.rep.orderform;

import java.math.BigDecimal;
import java.util.List;

import com.zzy.brd.entity.Orderform;

/**
 * @author:xpk
 *    2016年9月28日-上午11:37:48
 **/
public class RepMyLoanDTO {
	
	private String status;
	
	private boolean isExists;
	
	private List<RepMyLoanDataDTO> dto;
	
	private boolean hasNext;
	
	
	public boolean isExists() {
		return isExists;
	}

	public void setExists(boolean isExists) {
		this.isExists = isExists;
	}

	public List<RepMyLoanDataDTO> getDto() {
		return dto;
	}

	public void setDto(List<RepMyLoanDataDTO> dto) {
		this.dto = dto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

}

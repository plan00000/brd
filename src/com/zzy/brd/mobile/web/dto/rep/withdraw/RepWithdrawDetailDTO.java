package com.zzy.brd.mobile.web.dto.rep.withdraw;

import java.util.List;

/**
 * @author:xpk
 *    2016年9月30日-下午3:23:45
 **/
public class RepWithdrawDetailDTO {
	
	private boolean hasNext;
	
	private boolean exists;
	
	private List<RepWithdrawDetailDataDTO> dto ;

	public List<RepWithdrawDetailDataDTO> getDto() {
		return dto;
	}

	public void setDto(List<RepWithdrawDetailDataDTO> dto) {
		this.dto = dto;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
}

package com.zzy.brd.dto.rep.admin.reportStatistics;

import java.util.List;

/**
 * @author:xpk
 *    2016年12月5日-下午2:53:18
 **/
public class RepSellerStatisticDTO {
	
	private int status;
	
	private List<RepSellerStatisticAddressDTO> data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<RepSellerStatisticAddressDTO> getData() {
		return data;
	}

	public void setData(List<RepSellerStatisticAddressDTO> data) {
		this.data = data;
	}	
}

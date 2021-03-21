package com.zzy.brd.dto.rep.admin.platform;

import java.util.Collection;

import com.zzy.brd.util.pageModel.PageBean;


public class RepUserPageDTO extends PageBean {
	
	private Collection<RepUserDTO> rows;

	public Collection<RepUserDTO> getRows() {
		return rows;
	}

	public void setRows(Collection<RepUserDTO> rows) {
		this.rows = rows;
	}

}

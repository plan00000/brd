package com.zzy.brd.dto.rep.admin.product;

import java.util.Collection;

import com.zzy.brd.util.pageModel.PageBean;


/**
 * 产品信息list
 * @author lzh 2016/11/18
 *
 */
public class RepProductListDTO extends PageBean{
	private Collection<RepProductDetailDTO> rows;

	public Collection<RepProductDetailDTO> getRows() {
		return rows;
	}

	public void setRows(Collection<RepProductDetailDTO> rows) {
		this.rows = rows;
	}
	
}

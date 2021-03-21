package com.zzy.brd.dao;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.SysInfo;

/**
 * @author:xpk
 *    2016年9月27日-上午11:21:54
 **/
public interface SysInfoDao extends BaseDao<SysInfo> {
	
	@Modifying
	@Query("update SysInfo s set s.websiteTotalPv = s.websiteTotalPv +1 ")
	public void updatePv();
	
}

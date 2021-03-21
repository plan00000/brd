package com.zzy.brd.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.SysInfoDao;
import com.zzy.brd.entity.SysInfo;


/***
 * 平台设置service
 * 
 * @author csy 2016-09-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class SysInfoService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(SysInfoService.class);
	@Autowired
	private SysInfoDao sysInfoDao;

	public SysInfo getSysInfo(Long id){
		return sysInfoDao.findOne(id);
	}
	public SysInfo findSysInfoById(long id) {
		return sysInfoDao.findOne(id);
	}
	public boolean editSysInfo(SysInfo sysInfo){
		return sysInfoDao.save(sysInfo)==null?false:true;
	}
	
	/**
	 * 更新页面访问量
	 * 
	 * */
	public void updatePv() {
		sysInfoDao.updatePv();		
	}
	
	
	
	
	
	
}

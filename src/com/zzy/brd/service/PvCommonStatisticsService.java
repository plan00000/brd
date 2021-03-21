package com.zzy.brd.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.PvCommonStatisticsDao;
import com.zzy.brd.entity.PvCommonStatistics;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.util.date.DateUtil;

/**
 * @author:xpk
 *    2017年1月13日-下午4:23:29
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PvCommonStatisticsService extends BaseService {
	
	@Autowired
	private PvCommonStatisticsDao pvCommonStatiticsDao;
	@Autowired
	private SysInfoService sysInfoService;
	
	public PvCommonStatistics findByDate(Date date ) {
		return pvCommonStatiticsDao.findByDate(date);
	}
	
	public List<PvCommonStatistics> findStatisticsByDate(String start,String end) {
		Date startDate = DateUtil.StringToTimestamp(start);
		Date endDate = DateUtil.StringToTimestamp(end);
		return pvCommonStatiticsDao.findStatisticsByDate(startDate, endDate);
	}
	
	
	/**
	 * 统计
	 * 
	 * */
	public boolean statisticsPv() {
		Date date = DateUtil.getDateByLongFormat(DateUtil.getYesterdayBeginString());
		Date preDate = DateUtil.getDateByLongFormat(DateUtil.getPreDayBeginString(new Date(), -2));
		SysInfo sysinfo = sysInfoService.findSysInfoById(1l);
		if(sysinfo==null) {
			return false;
		}
		int totalPv = sysinfo.getWebsiteTotalPv();
		int pv = 0;
		int preTotalPv = 0;
		
		PvCommonStatistics common = this.findByDate(preDate);
		if(common!=null) {
			preTotalPv = common.getWebsiteTotalPv();
		}
		pv = totalPv - preTotalPv ;
		PvCommonStatistics ps = new PvCommonStatistics();
		ps.setStatisticsDate(date);
		ps.setWebsiteTotalPv(totalPv);
		ps.setWebsitePv(pv);
		this.pvCommonStatiticsDao.save(ps);		
		return true;
	}
	
	
}

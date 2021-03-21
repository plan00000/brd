package com.zzy.brd.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.PvCommonStatistics;

/**
 * @author:xpk
 *    2017年1月13日-下午4:24:28
 **/
public interface PvCommonStatisticsDao extends BaseDao<PvCommonStatistics> {
	
	@Query("select p from PvCommonStatistics p where p.statisticsDate =?1 ")
	public PvCommonStatistics findByDate(Date date);
	
	@Query(" select p from PvCommonStatistics p where p.statisticsDate between ?1 and ?2 ")
	public List<PvCommonStatistics> findStatisticsByDate(Date startDate,Date endDate);
	
}

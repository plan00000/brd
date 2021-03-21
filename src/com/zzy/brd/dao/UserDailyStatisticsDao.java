package com.zzy.brd.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.dto.rep.admin.reportStatistics.RepUserReportStatisticDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserDailyStatistics;

/**
 * @author:xpk
 *    2016年10月21日-下午2:06:29
 **/
public interface UserDailyStatisticsDao extends BaseDao<UserDailyStatistics>{
		
	@Query("select new com.zzy.brd.dto.rep.admin.reportStatistics.RepUserReportStatisticDTO(ud.user,coalesce(sum(ud.sonSum), 0 ),coalesce(sum(ud.grandSonSum),0),coalesce(sum(ud.ggrandSonSum),0),coalesce(sum(ud.orderSum), 0 ),coalesce(sum(ud.orderSuccessSum),0),coalesce(sum(ud.loginTimes),0)) from UserDailyStatistics ud"
			+ " where (?1 is null or ud.statisticsDate>=?1) and (?2 is null or ud.statisticsDate<=?2)"
			+ "and (?3=-1 or ud.loginTimes >= ?3) and (?4=-1 or ud.user.state=?4 ) and (?5=-1 or ud.user.userType =?5 ) "
			+ "and (?6 is null or ud.user.createdate >=?6 ) and (?7 is null or ud.user.createdate <= ?7 ) "
			+ "and (?8 is null or ud.user.username like ?8) "
			+ " group by ud.user having "
			+ " (?9=-1 or coalesce(sum(ud.sonSum),0)>=?9 ) "
			+ " and (?10=-1 or coalesce(sum(ud.grandSonSum),0)>=?10 )"
			+ " and (?11=-1 or coalesce(sum(ud.ggrandSonSum),0)>=?11)"
			+ " and (?12=-1 or coalesce(sum(ud.orderSum),0)>=?12) "
			+ " and (?13=-1 or coalesce(sum(ud.orderSuccessSum),0)>?13 )")
	public Page<RepUserReportStatisticDTO> pageUserDailyStatistic(Date sStartTimeTime,Date sEndTimeTime
			,int longinTimes,int state,int userType,Date startTime,Date endTime,String keyword,int sonSum
			,int grandsonSum,int ggrandsonSum,int orderSum,int orderSuccessSum
			,Pageable pageable);
	
	
	@Query("select new com.zzy.brd.dto.rep.admin.reportStatistics.RepUserReportStatisticDTO(ud.user,coalesce(sum(ud.sonSum), 0 ),coalesce(sum(ud.grandSonSum),0),coalesce(sum(ud.ggrandSonSum),0),coalesce(sum(ud.orderSum), 0 ),coalesce(sum(ud.orderSuccessSum),0),coalesce(sum(ud.loginTimes),0)) from UserDailyStatistics ud"
			+ " where (?1 is null or ud.statisticsDate>=?1) and (?2 is null or ud.statisticsDate<=?2)"
			+ "and (?3=-1 or ud.loginTimes >= ?3) and (?4=-1 or ud.user.state=?4 ) and (?5=-1 or ud.user.userType =?5 ) "
			+ "and (?6 is null or ud.user.createdate >=?6 ) and (?7 is null or ud.user.createdate <= ?7 ) "
			+ "and (?8 is null or ud.user.mobileno like ?8) "
			+ " group by ud.user having "
			+ " (?9=-1 or coalesce(sum(ud.sonSum),0)>=?9 ) "
			+ " and (?10=-1 or coalesce(sum(ud.grandSonSum),0)>=?10 )"
			+ " and (?11=-1 or coalesce(sum(ud.ggrandSonSum),0)>=?11)"
			+ " and (?12=-1 or coalesce(sum(ud.orderSum),0)>=?12) "
			+ " and (?13=-1 or coalesce(sum(ud.orderSuccessSum),0)>?13 ) ")
	public Page<RepUserReportStatisticDTO> pageUserDailyStatistic2(Date sStartTimeTime,Date sEndTimeTime
			,int longinTimes,int state,int userType,Date startTime,Date endTime,String keyword,int sonSum
			,int grandsonSum,int ggrandsonSum,int orderSum,int orderSuccessSum
			,Pageable pageable);
	
}

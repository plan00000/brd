package com.zzy.brd.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.dto.rep.admin.reportStatistics.RepStatisticBrokerageDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserCommonStatistics;


public interface UserCommonStatisticsDao extends BaseDao<UserCommonStatistics>{
	

	/**
	 * 查找所有会员数
	 * @param 
	 * @return
	 */
	@Query("select count(*) from User u where u.userType in(0,1,2,4) ")
	int findUserCount();
	
	/**
	 * 通过userType查找会员数
	 * @param 
	 * @return
	 */
	@Query("select count(*) from User u where u.userType=?1")
	int findUserCount(User.UserType userType);
	/**
	 * 通过userTypeh 和创建时间 查找会员数
	 * @param 
	 * @return
	 */
	@Query("select count(*) from User u where u.userType=?1 and u.createdate between ?2 and ?3")
	int findUserCount(User.UserType userType,Date start,Date end);
	
	/**
	 * 通过创建时间 查找会员数
	 * @param 
	 * @return
	 */
	@Query("select count(*) from User u where u.userType in(0,1,2,4) and u.createdate between ?1 and ?2")
	int findUserCount(Date start,Date end);
	
	/**
	 * 通过lastlogindate查找会员数
	 * @param 
	 * @return
	 */
	@Query("select count(*) from User u where u.userType in(0,1,2,4) and u.lastlogindate between ?1 and ?2")
	int findUserLoginCount(Date start,Date end);
	
	/**
	 * 查询近n日的会员统计数据
	 * @return
	 */
	@Query("select u from UserCommonStatistics u where u.statisticsDate between ?1 and ?2 order by u.statisticsDate asc ")
	public List<UserCommonStatistics> findUserCommonStatisticsList(Date start,Date end);
	
	/**
	 * 得到订单提成佣金
	 * @return
	 */
	@Query(value="select sum(order_brokerage) from user_info_both" ,nativeQuery=true)
	BigDecimal orderBrokerageCount();
	/**
	 * 得到活动奖励佣金
	 * @return
	 */
	@Query(value="select sum(activity_brokerage) from user_info_both" ,nativeQuery=true)
	BigDecimal activityBrokerageCount();
	/**
	 * 得到已提现佣金
	 * @return
	 */
	@Query(value="select sum(brokerage_have_withdraw) from user_info_both" ,nativeQuery=true)
	BigDecimal brokerageHaveWithdrawCount();
	/**
	 * 得到可提现佣金
	 * @return
	 */
	@Query(value="select sum(brokerage_can_withdraw) from user_info_both" ,nativeQuery=true)
	BigDecimal brokerageCanWithdrawCount();
	/**
	 * 得到提现中佣金
	 * @return
	 */
	@Query(value="select sum(brokerage_withdrawing) from user_info_both" ,nativeQuery=true)
	BigDecimal brokerageWithdrawingCount();
	
	
	/**
	 * 通过userType获得佣金统计
	 * @param userTypes
	 * @param pageable
	 * @return
	 */
	/*@Query(value = "select new com.zzy.brd.dto.rep.admin.reportStatistics.RepStatisticBrokerageDTO(u,"
			+ "uib.brokerageHaveWithdraw + uib.brokerageCanWithdraw + uib.brokerageWithdrawing) "
			+ "from  User u  left join u.userInfoBoth uib where u.userType in ?1 "
			+ " order by uib.brokerageHaveWithdraw + uib.brokerageCanWithdraw + uib.brokerageWithdrawing desc")
	Page<RepStatisticBrokerageDTO> pageBrokerageStatisctic(List<User.UserType> userTypes,Pageable pageable);
	*/
	/**
	 * 获得佣金发放列表并排序
	 * @param pageable
	 * @return
	 */
	@Query(value="select  new com.zzy.brd.dto.rep.admin.reportStatistics.RepStatisticBrokerageDTO(b,"
			+ "b.selfBrokerage+b.fatherBrokerage+b.salesmanBrokerage+b.businessBrokerage) "
			+ "from BrokerageApply b "
			+ " order by b.selfBrokerage+b.fatherBrokerage+b.salesmanBrokerage+b.businessBrokerage desc")
	Page<RepStatisticBrokerageDTO> pageBrokerageStatisctic(Pageable pageable);
	
	/**
	 * @param 
	 * 	startTime
	 * @param 
	 * 	endTime
	 * @return 
	 */
	@Query("select u from  UserCommonStatistics u where u.statisticsDate = ?1")
	public UserCommonStatistics findStatisticsByDate(Date date);
	
}

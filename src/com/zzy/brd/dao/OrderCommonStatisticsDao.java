package com.zzy.brd.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.OrderCommonStatistics;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.User;


public interface OrderCommonStatisticsDao extends BaseDao<OrderCommonStatistics>{
	

	/**
	 * 提交总订单数量
	 * @return
	 */
	@Query(value = "select count(*) from orderform ", nativeQuery = true)
	int countOrderforms();
	/**
	 * 通过创建时间查询提交总订单数量
	 * @return
	 */
	@Query(value = "select count(*) from Orderform o where o.createTime between ?1 and ?2 ")
	int countOrderforms(Date start,Date end);
	/**
	 * 放款总订单数量
	 * @return
	 */
	@Query(value = "select count(*) from Orderform o where o.status=?1")
	int countOrderCredit(Orderform.OrderformStatus status);
	/**
	 * 通过创建时间查询提交总订单数量
	 * @return
	 */
	@Query(value = "select count(*) from Orderform o where o.status=?1 and o.createTime between ?2 and ?3 ")
	int countOrderCredit(Orderform.OrderformStatus status,Date start,Date end);
	
	/**
	 * 会员类型得到订单数
	 * @return
	 */
	@Query(value = "select count(o) from Orderform o left join o.user u where u.userType=?1 and o.status=?2 ")
	int getOrderCountByUserType(User.UserType userType,Orderform.OrderformStatus status);
	
	/**
	 * 统计昨天订单总额
	 * @return
	 */
	@Query(value = "select ifnull(sum(actual_Money),0) from orderform  where status = 5 and create_time between ?1 and ?2 ", nativeQuery = true)
	BigDecimal countOrderformMoney(Date start,Date end);
	/**
	 * 查询近n日的订单统计数据
	 * @return
	 */
	@Query("select o from OrderCommonStatistics o where o.statisticsDate between ?1 and ?2 order by o.statisticsDate asc ")
	public List<OrderCommonStatistics> findOrderCommonStatisticsList(Date start,Date end);
	
	
	/**
	 *所有订单数量
	 * @return
	 */
	@Query(value = "select count(o) from Orderform o")
	long orderformCounts();
	/**
	 * 统计昨日订单数
	 * @return
	 */
	@Query(value = "select count(*) from orderform where datediff(create_time,now())=-1 ", nativeQuery = true)
	long countTodayOrderforms();
	
	/**
	 * 统计昨日订单总额
	 * @return
	 */
	@Query(value = "select ifnull(sum(actual_money),0) from orderform where datediff(create_time,now())=-1 and status = 5 ", nativeQuery = true)
	BigDecimal countTodayOrderformMoney();
	
	/**
	 * 统计订单总额
	 * @return
	 */
	@Query(value = "select ifnull(sum(actual_money),0) from orderform where status =5", nativeQuery = true)
	BigDecimal countOrderformMoney();
	

	/**
	 * 获取部门的总订单数
	 * @param ids
	 * @return
	 */
	@Query(value = "(select count(*) from orderform o where o.user_id in (?1) )", nativeQuery = true)
	long countOrderTimeByDepartUser(List<Long> userIds);
	
	
	/**
	 * 获取部门的昨日订单数
	 * @param ids
	 * @return
	 */
	@Query(value = "(select count(*) from orderform o where o.user_id in (?1) and datediff(o.create_time,now())=-1)", nativeQuery = true)
	long countYestodayOrderTimeByDepartUser(List<Long> ids);

	/**
	 * 获取部门的订单总额
	 * @param ids
	 * @return
	 */
	@Query(value = "(select ifnull(sum(o.actual_money),0) from orderform o where o.status =5  and o.user_id in (?1) )",nativeQuery = true)
	BigDecimal countOrderMoneyByDepartUser(List<Long> userIds);
	
	/**
	 * 获取部门的昨日订单总额
	 * @param ids
	 * @return
	 */
	@Query(value = "(select ifnull(sum(o.actual_money),0) from orderform o where o.status =5 and o.user_id in (?1) and datediff(o.create_time,now())=-1)",nativeQuery = true)
	BigDecimal countYestodayOrderMoneyByDepartUser(List<Long> userIds);
	
}

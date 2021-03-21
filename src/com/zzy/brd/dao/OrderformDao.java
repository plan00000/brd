package com.zzy.brd.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.ProductType.BillType;

/**
 * @author:xpk
 *    2016年9月28日-下午2:25:52
 **/
public interface OrderformDao extends BaseDao<Orderform> {
	
	@Query("select o from Orderform o where o.user.id=?1 order by o.createTime asc ")
	List<Orderform> findOrderformByUser(long userId);
	
	/** 统计订单*/
	@Query("select count(o) from Orderform o where o.status =?2 and o.product.type.billType =?1 and o.source =?3")
	int countLoanUncheckNum(BillType billType,OrderformStatus status,OrderSource source);
	/** pc贷款*/
	@Query("select count(o) from Orderform o where o.status =?1 and o.source = ?2")
	int countPcOrderNum(OrderformStatus status,OrderSource source);
	
	/***
	 *指定时间内用户的订单数量 
	 */
	@Query("select id from Orderform o where o.user.id=?3 and o.createTime between ?1 and ?2  ")
	List<Long> findOrderByTimeAndUser(Date startTime,Date endTime,long userId);
	
}

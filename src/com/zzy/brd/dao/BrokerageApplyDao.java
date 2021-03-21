package com.zzy.brd.dao;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.BrokerageApply.BrokerageApplyStatus;
import com.zzy.brd.entity.Orderform;

/**
 * 佣金订单dao
 * @author lzh 2016/10/11
 *
 */

public interface BrokerageApplyDao extends BaseDao<BrokerageApply>{
	
	/** 统计数量*/
	@Query("select count(b) from BrokerageApply b where b.status = ?1")
	int countBrokerageNum(BrokerageApplyStatus status);
	
	/**根据订单查询*/
	@Query("select b from BrokerageApply b where b.orderform =?1 ")
	BrokerageApply findByOrderform(Orderform orderform);
}

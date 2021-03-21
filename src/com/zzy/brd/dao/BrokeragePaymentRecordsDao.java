package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.BrokeragePaymentRecords;
import com.zzy.brd.entity.Orderform;

/**
 * @author:xpk
 *    2016年11月18日-下午4:04:07
 **/
public interface BrokeragePaymentRecordsDao extends  BaseDao<BrokeragePaymentRecords> {
	
	@Query("select b from BrokeragePaymentRecords b where b.orderform =?1 order by b.createTime asc ")
	List<BrokeragePaymentRecords> findByOrderform(Orderform orderform);
}

package com.zzy.brd.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.User;

/**
 * 订单操作日志-dao
 * @author lzh 2016/10/14
 *
 */

public interface OrderOperLogDao extends BaseDao<OrderOperLog>{
	
	@Query("select o from OrderOperLog o where o.createTime between ?1 and ?2 and o.order.user=?3 ")
	List<OrderOperLog> findByUserAndTime(Date startTime,Date endTime,User user);
	
	
}

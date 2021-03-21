package com.zzy.brd.dao;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.OrderSerial;

/**
 * 编号-Dao
 * @author lzh 2016、10、27
 *
 */
public interface OrderSerialDao extends BaseDao<OrderSerial>{
	
	@Query("select o from OrderSerial o where o.name = ?1")
	OrderSerial findOrderSerialByName(String name);
}

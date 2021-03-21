package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.OrderformRemark;

/**
 * @author:xpk
 *    2016年9月29日-下午3:02:09
 **/
public interface OrderformRemarkDao extends BaseDao<OrderformRemark> {
	
	@Query("select o from OrderformRemark o where o.orderform.id=?1 ")
	List<OrderformRemark> findByOrder(long orderformId);
	
	@Query("select o from OrderformRemark o where o.orderform.id=?1 order by o.createTime desc ")
	List<OrderformRemark> getOrderformRemarkList(long orderformId);
}

package com.zzy.brd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.OrderformRemarkDao;
import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.OrderformRemark;

/**
 * @author:xpk
 *    2016年9月29日-下午3:02:45
 **/
@Service
@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
public class OrderformRemarkService extends BaseService{
	
	@Autowired private OrderformRemarkDao orderformRemarkDao;
	
	/***
	 *根据订单找到备注 
	 * @param orderform
	 * @return
	 */
	public List<OrderformRemark> getByOrder(Orderform orderform){
		return orderformRemarkDao.findByOrder(orderform.getId());
	}
	/**
	 * 添加订单备注
	 * @param orderformRemark
	 * @return
	 */
	public boolean addOrderformRemark(OrderformRemark orderformRemark){
		return orderformRemarkDao.save(orderformRemark) == null?false:true;
	}
	/**
	 * 订单备注记录
	 * @param searchParams
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<OrderformRemark> getOrderformRemarkList(long orderformId){
		return orderformRemarkDao.getOrderformRemarkList(orderformId);
	}
}

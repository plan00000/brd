package com.zzy.brd.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.OrderOperLogDao;
import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.User;

/**
 * 订单操作日志-service
 * @author lzh 2016/10/14
 *
 */
@Service
@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
public class OrderOperLogService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(OrderOperLogService.class);
	@Autowired
	private OrderOperLogDao orderOperLogDao;
	/**
	 * 添加订单操作日志
	 * @param orderOperLog
	 * @return
	 */
	public boolean addOrderOperLog(OrderOperLog orderOperLog){
		return orderOperLogDao.save(orderOperLog) == null?false:true;
	}
	/**
	 * 订单操作list
	 * @param searchParams
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<OrderOperLog> getOrderOperLogPage(Map<String,Object> searchParams,int pageNum,int pageSize){
		PageRequest pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
		@SuppressWarnings("unchecked")
		Specification<OrderOperLog> spec = (Specification<OrderOperLog>) createSpecification(
				searchParams, OrderOperLog.class);
		Page<OrderOperLog> result = orderOperLogDao.findAll(spec, pageRequest);
		return result;
	}
	
	/**
	 * 时间点内的操作订单记录
	 *  
	 * */
	public List<OrderOperLog> findByUserAndTime(Date startTime,Date endTime,User user){
		return orderOperLogDao.findByUserAndTime(startTime, endTime, user);
	}
	
}

package com.zzy.brd.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.BrokerageDistributionDao;
import com.zzy.brd.dto.rep.recordBrokerage.RecordBrokerageDTO;
import com.zzy.brd.entity.BrokerageDistribution;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.User;

/***
 * 产品service
 * 
 * @author csy 2016-09-28
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class BrokerageDistributionService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(BrokerageDistributionService.class);
	@Autowired
	private BrokerageDistributionDao brokerageDistributionDao;
/*	*//**
	 * 根据用户获得佣金详情列表
	 * @param user
	 * @return
	 *//*
	public List<RecordBrokerageDTO> getBrokerageDistributionsByUser(User user){
		return brokerageDistributionDao.getBrokerageDistributionsByUser(user);
	} 
	*//**
	 * 根据起止时间得到佣金总和
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public BigDecimal findBrokerages(Long userId,Date startDate, Date endDate) {
		return brokerageDistributionDao.findBrokerages(userId ,startDate, endDate);
	}*/
	
	/**
	 *@param user
	 *@param orderform 
	 *@return
	 **/
	public List<BrokerageDistribution> getByUserAndOrder(User user,Orderform orderform){
		return brokerageDistributionDao.getByUserOrderform(user, orderform);
	}
	
	
}

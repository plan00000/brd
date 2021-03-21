package com.zzy.brd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.OrderformProductRecordDao;
import com.zzy.brd.entity.OrderformProductRecord;

/**
 * 订单-更改产品Service
 * @author lzh 2016/10/14
 *
 */
@Service
@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
public class OrderformProductRecordService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(OrderformProductRecordService.class);
	
	@Autowired
	private OrderformProductRecordDao orderformProductRecordDao;
	/*
	 * 添加更改产品记录
	 */
	public boolean addOPR(OrderformProductRecord opr){
		return orderformProductRecordDao.save(opr)== null? false:true;
	}
	
}

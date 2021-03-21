package com.zzy.brd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.OrderSerialDao;
import com.zzy.brd.entity.OrderSerial;

/**
 * 编号-service
 * @author lzh 2016/10/27
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class OrderSerialService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(OrderSerialService.class);
	
	@Autowired
	private OrderSerialDao orderSerialDao;
	/** 根据名称查询编号 */
	public OrderSerial findOrderSerialByName(String name){
		return orderSerialDao.findOrderSerialByName(name);
	}
	/**添加或修改编号 */
	public boolean addOrEditOrderSerial(OrderSerial orderSerial){
		return orderSerialDao.save(orderSerial) == null ?false:true;
	}
}

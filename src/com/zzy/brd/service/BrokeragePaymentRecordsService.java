package com.zzy.brd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.BrokeragePaymentRecordsDao;
import com.zzy.brd.entity.BrokeragePaymentRecords;
import com.zzy.brd.entity.Orderform;

/**
 * @author:xpk
 *    2016年11月18日-下午3:57:55
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class BrokeragePaymentRecordsService extends BaseService {

	@Autowired
	private BrokeragePaymentRecordsDao brokeragePaymentRecordsDao;
	
	public boolean addBrokeragePaymentRecords(BrokeragePaymentRecords records){
		return brokeragePaymentRecordsDao.save(records)==null?false:true;
	}
	
	public List<BrokeragePaymentRecords> findByOrderform(Orderform orderform){
		return brokeragePaymentRecordsDao.findByOrderform(orderform);
	}
	
	
}

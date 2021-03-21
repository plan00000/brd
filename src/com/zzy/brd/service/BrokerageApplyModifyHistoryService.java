package com.zzy.brd.service;

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

import com.zzy.brd.dao.BrokerageApplyModifyHistoryDao;
import com.zzy.brd.entity.BrokerageApplyModifyHistory;

/**
 * 佣金订单历史记录-service
 * @author lzh 2016/10/17
 *
 */
@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
public class BrokerageApplyModifyHistoryService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(BrokerageApplyModifyHistoryService.class);
	
	@Autowired
	private BrokerageApplyModifyHistoryDao brokerageMHistoryDao;
	/**
	 * 添加记录
	 * @param brokerageMHistory
	 * @return
	 */
	public boolean addBrokerageMHistory (BrokerageApplyModifyHistory brokerageMHistory){
		return brokerageMHistoryDao.save(brokerageMHistory) == null?false:true;
	}
	/**
	 * 佣金订单操作记录
	 * @param searchParams
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<BrokerageApplyModifyHistory> getBrokerageMHistoryPage(Map<String,Object> searchParams,int pageNum,int pageSize){
		PageRequest pageRequest = createPageRequest(pageNum,pageSize,"addDate:desc",false);
		@SuppressWarnings("unchecked")
		Specification<BrokerageApplyModifyHistory> spec = (Specification<BrokerageApplyModifyHistory>) createSpecification(
				searchParams, BrokerageApplyModifyHistory.class);
		Page<BrokerageApplyModifyHistory> result = brokerageMHistoryDao.findAll(spec, pageRequest);
		return result;
	}
}

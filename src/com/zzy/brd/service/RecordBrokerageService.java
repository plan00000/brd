package com.zzy.brd.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.RecordBrokerageDao;
import com.zzy.brd.dto.rep.recordBrokerage.RecordBrokerageDTO;
import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.RecordBrokerage.RelationType;

/***
 * 佣金记录service
 * 
 * @author csy 2016-09-28
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RecordBrokerageService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(RecordBrokerageService.class);
	@Autowired
	private RecordBrokerageDao recordBrokerageDao;
	@Autowired
	private BrokerageApplyService brokerageApplyService;

	/***
	 * 获得显示在首页的产品
	 * 
	 * @return
	 */
	public List<RecordBrokerage> getRecordBrokerages(User user) {
		return recordBrokerageDao.getRecordBrokerages(user);
	}

	@SuppressWarnings("unchecked")
	public Page<RecordBrokerage> listRecordBrokerage(Map<String,Object> searchParams,int pageNumber){
		Pageable pageRequest =this.createPageRequest(pageNumber, Constant.PAGE_SIZE, "id:desc", false);
		Specification<RecordBrokerage> spec = (Specification<RecordBrokerage>) createSpecification(searchParams, RecordBrokerage.class);
		return 	recordBrokerageDao.findAll(spec, pageRequest);	
	}
	/***
	 * 添加收佣记录
	 * @param recordBrokerage
	 * @return
	 */
	public boolean addRecordBrokerage(User user,User selfUser,RelationType relationType,BrokerageApply brokerageApply,int witchBrokerage){
		BrokerageApply newBrokerageApply = brokerageApplyService.findByBrokerageId(brokerageApply.getId());
		RecordBrokerage recordBrokerage = new RecordBrokerage();
		recordBrokerage.setUser(user);
		recordBrokerage.setGainUser(selfUser);
		recordBrokerage.setGainUserInfoBoth(selfUser.getUserInfoBoth());
		recordBrokerage.setConfirmName(selfUser.getUsername());
		recordBrokerage.setRelate(relationType);
		recordBrokerage.setOrder(brokerageApply.getOrderform());
		recordBrokerage.setSendBrokerage(new Date());
	
		switch(witchBrokerage){
			case 1:recordBrokerage.setHaveBrokerage(newBrokerageApply.getSelfSubmitBrokerage());
				recordBrokerage.setHaveBrokerageRate(newBrokerageApply.getProductInfo().getUserRate());
				break;
			case 2:recordBrokerage.setHaveBrokerage(newBrokerageApply.getFatherSubmitBrokerage());
				recordBrokerage.setHaveBrokerageRate(newBrokerageApply.getProductInfo().getFatherRate());
				break;
			case 3:recordBrokerage.setHaveBrokerage(newBrokerageApply.getBusinessSubmitBrokerage());
				recordBrokerage.setHaveBrokerageRate(newBrokerageApply.getProductInfo().getSellerRate());
				break;
			case 4:recordBrokerage.setHaveBrokerage(newBrokerageApply.getSalesmanSubmitBrokerage());
				recordBrokerage.setHaveBrokerageRate(newBrokerageApply.getProductInfo().getSalesmanRate());
				break;
			default:break;
		}
		return editUserInfoBoth(recordBrokerage);
	}
	public boolean editUserInfoBoth(RecordBrokerage recordBrokerage) {
		return recordBrokerageDao.save(recordBrokerage) == null ? false : true;
	}

	/**
	 * 根据用户获得佣金详情列表
	 * @param user
	 * @return
	 */
	public List<RecordBrokerageDTO> getBrokerageDistributionsByUser(User user){
		return recordBrokerageDao.getBrokerageDistributionsByUser(user);
	} 
	/**
	 * 根据起止时间得到佣金总和
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BigDecimal findBrokerages(Long userId,Date startDate, Date endDate) {
		return recordBrokerageDao.findBrokerages(userId ,startDate, endDate);
	}
	/**
	 * 根据userid得到佣金总和
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BigDecimal findBrokerages(Long userId) {
		return recordBrokerageDao.findBrokerages(userId );
	}
}

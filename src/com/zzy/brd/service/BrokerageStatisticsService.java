package com.zzy.brd.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.UserCommonStatisticsDao;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepStatisticBrokerageDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.util.bigDecimal.BigDecimalUtils;

/***
 * 产品service
 * 
 * @author csy 2016-09-28
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class BrokerageStatisticsService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(BrokerageStatisticsService.class);
	@Autowired
	private UserCommonStatisticsDao userCommonStatisticsDao;
	
	/**
	 * 得到佣金统计
	 * @return
	 */
	public Map<String, Object> getCountBrokerage(){
		Map<String, Object> map = Maps.newHashMap();
		BigDecimal totalBrokerage = BigDecimal.ZERO;
		BigDecimal haveBrokerage = BigDecimal.ZERO;
		BigDecimal orderBrokerage = userCommonStatisticsDao.orderBrokerageCount();
		BigDecimal activityBrokerage = userCommonStatisticsDao.activityBrokerageCount();
		BigDecimal brokerageHaveWithdraw = userCommonStatisticsDao.brokerageHaveWithdrawCount();
		BigDecimal brokerageCanWithdraw = userCommonStatisticsDao.brokerageCanWithdrawCount();
		BigDecimal brokerageWithdrawing = userCommonStatisticsDao.brokerageWithdrawingCount();
		if((brokerageHaveWithdraw!=null)&&(brokerageCanWithdraw!=null)&&(brokerageWithdrawing!=null)){
			totalBrokerage = BigDecimalUtils.add(brokerageHaveWithdraw,brokerageCanWithdraw,brokerageWithdrawing);
		}
		if((brokerageHaveWithdraw!=null)&&(brokerageWithdrawing!=null)){
			haveBrokerage = BigDecimalUtils.add(brokerageHaveWithdraw,brokerageWithdrawing);
		}
		map.put("totalBrokerage", totalBrokerage);//所有佣金
		map.put("haveBrokerage", haveBrokerage);//已提现
		map.put("brokerageCanWithdraw", brokerageCanWithdraw);//未提现
		map.put("orderBrokerage", orderBrokerage);//订单提成佣金
		map.put("activityBrokerage", activityBrokerage);//活动奖励佣金
		return map;
	}
	/**
	 * 获得用户佣金统计列表
	 * @param pageNumber
	 * @param size
	 * @return
	 */
	public Page<RepStatisticBrokerageDTO> pageBrokerageStatisctic(List<User.UserType> userTypes){
		PageRequest pageRequest = createPageRequest(1, Constant.PAGE_SIZE, "id", false);
		Page<RepStatisticBrokerageDTO> result= userCommonStatisticsDao.pageBrokerageStatisctic(userTypes,pageRequest);
		return result;
	}
	/**
	 * 获得用户佣金统计列表
	 * @param pageNumber
	 * @param size
	 * @return
	 */
	public Page<RepStatisticBrokerageDTO> pageBrokerageApply(){
		PageRequest pageRequest = createPageRequest(1, 5, "id", false);
		Page<RepStatisticBrokerageDTO> result= userCommonStatisticsDao.pageBrokerageStatisctic(pageRequest);
		return result;
	}
}

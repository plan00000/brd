package com.zzy.brd.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.zzy.brd.dao.OrderCommonStatisticsDao;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepOrderStatisticsConutDTO;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepOrderStatisticsDTO;
import com.zzy.brd.entity.OrderCommonStatistics;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.User;
import com.zzy.brd.util.date.DateUtil;

/***
 * 活动service
 * 
 * @author csy 2016-09-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class OrderCommonStatisticsService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(OrderCommonStatisticsService.class);
	@Autowired
	private OrderCommonStatisticsDao orderCommonStatisticsDao;
	

	/**
	 * 得到会员统计数量
	 * @return
	 */
	public RepOrderStatisticsConutDTO getOrderCount(){
		Date start = DateUtil.StringToTimestamp(DateUtil.getYesterday());
		Date end = DateUtil.StringToTimestamp(DateUtil.getNow());
		int totalLoadCount = orderCommonStatisticsDao.countOrderforms();
		int totalCreditCount = orderCommonStatisticsDao.countOrderCredit(OrderformStatus.LOANED);
		int yesLoadCount = orderCommonStatisticsDao.countOrderforms(start,end);
		int yesCreditCount = orderCommonStatisticsDao.countOrderCredit(OrderformStatus.LOANED, start, end);
		RepOrderStatisticsConutDTO rep = new RepOrderStatisticsConutDTO();
		rep.setTotalLoanCount(totalLoadCount);
		rep.setTotalCreditCount(totalCreditCount);
		rep.setYesLoanCount(yesLoadCount);
		rep.setYesCreditCount(yesCreditCount);
		return rep;
	}
	/**
	 * 得到会员统计
	 * @return
	 */
	public int setOrderStatistics(){
		Date start = DateUtil.StringToTimestamp(DateUtil.getYesterday());
		Date end = DateUtil.StringToTimestamp(DateUtil.getNow());
		
		int yesLoadCount = orderCommonStatisticsDao.countOrderforms(start,end);
		int yesCreditCount = orderCommonStatisticsDao.countOrderCredit(OrderformStatus.LOANED, start, end);
		BigDecimal orderSuccessDecimal = orderCommonStatisticsDao.countOrderformMoney(start, end);
		OrderCommonStatistics orderCommonStatistics = new OrderCommonStatistics();
		orderCommonStatistics.setOrderNum(yesLoadCount);
		orderCommonStatistics.setOrderSuccessNum(yesCreditCount);
		orderCommonStatistics.setStatisticsDate(start);
		orderCommonStatistics.setOrderSuccessDecimal(orderSuccessDecimal);
		if(editOrderCommonStatistics(orderCommonStatistics)){
			return 1;
		}else {
			return 2;
		}
	}
	
	/**
	 * 修改会员统计
	 * @param userCommonStatistics
	 * @return
	 */
	public boolean editOrderCommonStatistics(OrderCommonStatistics orderCommonStatistics){
		return orderCommonStatisticsDao.save(orderCommonStatistics)==null?false:true;
	}
	/**
	 * 得到会员统计列表
	 * @return
	 */
/*	public List<UserCommonStatistics> findUserCommonStatisticsList(int n){
		Date start = DateUtil.StringToTimestamp(DateUtil.getNDay(n));
		Date end = DateUtil.StringToTimestamp(DateUtil.getYesterday());
		return userCommonStatisticsDao.findUserCommonStatisticsList(start,end);
	}*/
	public List<OrderCommonStatistics> findOrderCommonStatisticsList(String start,String end){
		Date start1 = DateUtil.StringToTimestamp(start);
		Date end1 = DateUtil.StringToTimestamp(end);
		return orderCommonStatisticsDao.findOrderCommonStatisticsList(start1,end1);
	}
	
	/**
	 * 得到会员订单数统计饼图
	 * @return
	 */
	public RepOrderStatisticsDTO getCake(){
		
		Integer totalUserCount = orderCommonStatisticsDao.getOrderCountByUserType(User.UserType.USER,OrderformStatus.LOANED);
		int totalManagerCount = orderCommonStatisticsDao.getOrderCountByUserType(User.UserType.MANAGER,OrderformStatus.LOANED);
		int totalSellerCount = orderCommonStatisticsDao.getOrderCountByUserType(User.UserType.SELLER,OrderformStatus.LOANED);
		Integer allUserCount = orderCommonStatisticsDao.countOrderCredit(OrderformStatus.LOANED);
		int otherCount = allUserCount - totalUserCount - totalManagerCount -totalSellerCount;
		
		RepOrderStatisticsDTO rep = new RepOrderStatisticsDTO();
		float usercount = (float)totalUserCount/(float)allUserCount;
		float managercount = (float)totalManagerCount/(float)allUserCount;
		float sellercount = (float)totalSellerCount/(float)allUserCount;
		float usercount1   =  (float)(Math.round(usercount*10000))/100;
		float sellercount1   =  (float)(Math.round(sellercount*10000))/100;
		float managercount1   =  (float)(Math.round(managercount*10000)/100);
		float  othercount = 100-(usercount1+managercount1+sellercount1);
		String usercountStr = String.valueOf(usercount1);
		String managercountStr = String.valueOf(managercount1);
		String othercountStr = String.valueOf(othercount);
		String sellercountStr = String.valueOf(sellercount1);
		if(othercountStr.length()>6){
			othercountStr = othercountStr.substring(0, 5);
		}
		rep.setUsercount(totalUserCount);
		rep.setManagercount(totalManagerCount);
		rep.setSellercount(totalSellerCount);
		rep.setOthercount(otherCount);
		rep.setUsercountPercent(usercountStr);
		rep.setManagercountPercent(managercountStr);
		rep.setSellercountPercent(sellercountStr);
		rep.setOthercountPercent(othercountStr);
		return rep;
	}
	
	/**
	 * 得到订单总数和放款金额 今日订单数放款金额
	 * @return
	 */
	public Map<String, Object> getCountOrder(){
		Map<String, Object> map = Maps.newHashMap();
		long allOrderCount = orderCommonStatisticsDao.orderformCounts();
		long todayOrderCount = orderCommonStatisticsDao.countTodayOrderforms();
		BigDecimal allOrderMoney = orderCommonStatisticsDao.countOrderformMoney();
		BigDecimal todayOrderMoney = orderCommonStatisticsDao.countTodayOrderformMoney();
	//	String allOrderMoneyStr = allOrderMoney.toString();
	//	float allOrderMoneyFloat =Float.parseFloat(allOrderMoneyStr)/10000;
	//	String todayOrderMoneyStr = todayOrderMoney.toString();
	// todayOrderMoneyFloat =Float.parseFloat(todayOrderMoneyStr)/10000;
		map.put("allOrderCount", allOrderCount);
		map.put("todayOrderCount", todayOrderCount);
		map.put("allOrderMoney", allOrderMoney);
		map.put("todayOrderMoney", todayOrderMoney);
		return map;
	}
}

package com.zzy.brd.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.UserDailyStatisticsDao;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepUserReportStatisticDTO;
import com.zzy.brd.entity.Loginlog;
import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.UserDailyStatistics;
import com.zzy.brd.util.date.DateUtil;

/**
 * @author:xpk
 *    2016年10月21日-下午2:04:46
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserDailyStatisticsService extends BaseService {
	
	@Autowired
	private UserDailyStatisticsDao userDailyDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderformService orderformService;
	
	@Autowired
	private LoginlogService loginlogService;
	
	@Autowired
	private OrderOperLogService orderOperLogService;
	
	/***每天批处理用户数据*/
	public void dailyStatistics(){
		List<User> allUsers = userService.findStatisticsUser();
		if(allUsers.size()>0){
			for(User user:allUsers){
				Date startTime = DateUtil.getYesterdayStartTime();
				Date endTime =DateUtil.getYesterdayEndTime();
				List<Long> grandSons= Lists.newArrayList();
				List<Long> ggrandSons = Lists.newArrayList();
				List<Long> orderSums = Lists.newArrayList();
				///获取昨天注册的徒弟的数量
//				List<Long> sons = userService.findSonsId(startTime, endTime, Lists.newArrayList(user.getId()));
				List<Long> sons = new ArrayList<>();
				///获取昨天注册的徒孙数量
				if(sons!=null && sons.size()>0){
//					grandSons = userService.findSonsId(startTime, endTime, sons);
//					grandSons = new ArrayList<>()
				}								
				///获取昨天注册的徒孙孙数量
				if(grandSons!=null && grandSons.size()>0){
//					ggrandSons = userService.findSonsId(startTime, endTime, grandSons);
				}
				///获取昨天订单数量
				orderSums = orderformService.findOrderByTimeAndUser(startTime, endTime, user.getId());
				///获取昨天成功订单数
				int orderSuccessSum = 0;
				List<OrderOperLog> operLog = orderOperLogService.findByUserAndTime(startTime, endTime, user);
				for(OrderOperLog log:operLog){
					if(log.getOperContent().contains("放款")){
						orderSuccessSum = orderSuccessSum+1;
					}
				}
				//获取昨日登录次数
				List<Loginlog> loginlogs = loginlogService.findByTimeAndUser(startTime, endTime, user);			
				UserDailyStatistics daily = new UserDailyStatistics();
				daily.setUser(user);
				daily.setSonSum(sons.size());
				daily.setGgrandSonSum(ggrandSons.size());
				daily.setGrandSonSum(grandSons.size());
				daily.setOrderSum(orderSums.size());
				daily.setStatisticsDate(new Date());
				daily.setOrderSuccessSum(orderSuccessSum);
				daily.setLoginTimes(loginlogs.size());
				userDailyDao.save(daily);
			}
		}
	}
	
	
	
	
	/***
	 *获取列表 
	 *
	 **/
	public Page<RepUserReportStatisticDTO> listUserDailyStatistics(Direction direction,int pageNumber,String sortName,
			String sStartTime,String sEndTime,int userType,int state,int loginTimes,
			int sonSum,int grandsonSum,int orderSum,int orderSuccessSum,String timeRange,String startTimestr,
			String endTimestr,int keywordType,String keyword,int ggrandsonSum,String sortType){
		Order order = new Order(direction, sortName);
		Sort sort = new Sort(order);
		
		Date sStartTimeTime = null;
		Date sEndTimeTime =null;
		if(sStartTime!=null){
			StringBuilder sStartTimeStr = new StringBuilder(sStartTime);
			sStartTimeStr.append(" 00:00:00");
			sStartTimeTime = DateUtil.StringToTimestampLong(sStartTimeStr.toString());
			if(sStartTimeTime!=null){
				sStartTimeTime =DateUtil.getDateAfterOrBefor2(sStartTimeTime, 1);
			}
		}
		if(sEndTime!=null){
			StringBuilder sEndTimeStr = new StringBuilder(sEndTime);
			sEndTimeStr.append(" 23:59:59");
			sEndTimeTime = DateUtil.StringToTimestampLong(sEndTimeStr.toString());
			if(sEndTimeTime!=null){
				sEndTimeTime = DateUtil.getDateAfterOrBefor2(sEndTimeTime, 1);
			}
		}
		Date startTime = null;
		Date endTime =null;
		if(startTimestr!=null){
			StringBuilder ssStartTimeStr = new StringBuilder(startTimestr);
			ssStartTimeStr.append(" 00:00:00");
			startTime = DateUtil.StringToTimestampLong(ssStartTimeStr.toString());
		}
		if(endTimestr!=null){
			StringBuilder ssEndTimeStr = new StringBuilder(endTimestr);
			ssEndTimeStr.append(" 23:59:59");
			endTime = DateUtil.StringToTimestampLong(ssEndTimeStr.toString());
		}
		if(!timeRange.equalsIgnoreCase("all")){
			if(timeRange.equalsIgnoreCase("today")){
				StringBuilder startTimes = new StringBuilder(DateUtil.DateToString(new Date()));
				startTimes.append(" 00:00:00");
				startTime = DateUtil.StringToTimestampLong(startTimes.toString());
				StringBuilder endTimes = new StringBuilder(DateUtil.DateToString(new Date()));
				endTimes.append(" 23:59:59");
				endTime = DateUtil.StringToTimestampLong(endTimes.toString());
			} 
			if(timeRange.equalsIgnoreCase("week")){
				endTime =DateUtil.getNowTimestamp();
				StringBuilder startBuilder=new StringBuilder(DateUtil.DateToString(DateUtil.getPreWeek(new Date())));
				startBuilder.append(" 00:00:00");
				startTime = DateUtil.StringToTimestampLong(startBuilder.toString());
			}
			if(timeRange.equalsIgnoreCase("month")){
				endTime=DateUtil.getNowTimestamp();
				StringBuilder startBuilder=new StringBuilder(DateUtil.DateToString(DateUtil.getPreDate(new Date())));
				startBuilder.append(" 00:00:00");
				startTime = DateUtil.StringToTimestampLong(startBuilder.toString());
			}
			if(timeRange.equalsIgnoreCase("three")){
				endTime= DateUtil.getNowTimestamp();
				StringBuilder startBuilder=new StringBuilder(DateUtil.DateToString(DateUtil.getPreDay(new Date(), -2)));
				startBuilder.append(" 00:00:00");
				startTime = DateUtil.StringToTimestampLong(startBuilder.toString());
			}
			
		} 
		if(keyword!=null){
			keyword ="%"+keyword+"%";
		}
		PageRequest pageRequest = new PageRequest(pageNumber-1, Constant.PAGE_SIZE,sort);
		Page<RepUserReportStatisticDTO> page =null;
		if(keywordType==0){
			page = userDailyDao.pageUserDailyStatistic(sStartTimeTime, sEndTimeTime,loginTimes,state,userType,
					startTime,endTime,keyword,sonSum,grandsonSum,ggrandsonSum,orderSum,orderSuccessSum,pageRequest);
		}else{
			page = userDailyDao.pageUserDailyStatistic2(sStartTimeTime, sEndTimeTime,loginTimes,state,userType,
					startTime,endTime,keyword,sonSum,grandsonSum,ggrandsonSum,orderSum,orderSuccessSum,pageRequest);
		}
		return page;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

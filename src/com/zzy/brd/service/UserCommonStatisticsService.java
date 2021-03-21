package com.zzy.brd.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.UserCommonStatisticsDao;

import com.zzy.brd.dto.rep.admin.reportStatistics.RepUserStatisticsConutDTO;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepReportStatisticsDTO;

import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserCommonStatistics;
import com.zzy.brd.util.date.DateUtil;

/***
 * 活动service
 * 
 * @author csy 2016-09-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserCommonStatisticsService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(UserCommonStatisticsService.class);
	@Autowired
	private UserCommonStatisticsDao userCommonStatisticsDao;
	

	/**
	 * 得到会员统计数量
	 * @return
	 */
	public RepUserStatisticsConutDTO getUserCount(){
		Date start = DateUtil.StringToTimestamp(DateUtil.getYesterday());
		Date end = DateUtil.StringToTimestamp(DateUtil.getNow());
		int totalUserCount = userCommonStatisticsDao.findUserCount(UserType.USER);
		int totalManagerCount = userCommonStatisticsDao.findUserCount(UserType.MANAGER);
		int totalSellerCount = userCommonStatisticsDao.findUserCount(UserType.SELLER);
		int yesUserCount = userCommonStatisticsDao.findUserCount(UserType.USER, start, end);
		int yesManagerCount = userCommonStatisticsDao.findUserCount(UserType.MANAGER,start,end);
		int yesSellerCount = userCommonStatisticsDao.findUserCount(UserType.SELLER,start,end);
		RepUserStatisticsConutDTO rep = new RepUserStatisticsConutDTO();
		rep.setTotalUserCount(totalUserCount);
		rep.setTotalManagerCount(totalManagerCount);
		rep.setTotalSellerCount(totalSellerCount);
		rep.setYesUserCount(yesUserCount);
		rep.setYesManagerCount(yesManagerCount);
		rep.setYesSellerCount(yesSellerCount);
		return rep;
	}
	/**
	 * 得到会员统计
	 * @return
	 */
	public int setUserStatistics(){
		Date start = DateUtil.StringToTimestamp(DateUtil.getYesterday());
		Date end = DateUtil.StringToTimestamp(DateUtil.getNow());
		int yesUserCount = userCommonStatisticsDao.findUserCount(UserType.USER, start, end);
		int yesManagerCount = userCommonStatisticsDao.findUserCount(UserType.MANAGER,start,end);
		int yesSellerCount = userCommonStatisticsDao.findUserCount(UserType.SELLER,start,end);
		int registerNum = userCommonStatisticsDao.findUserCount(start, end);
		int loginNum = userCommonStatisticsDao.findUserLoginCount(start, end);
		int alluserNum = userCommonStatisticsDao.findUserCount();
		UserCommonStatistics userCommonStatistics = new UserCommonStatistics();
		userCommonStatistics.setRegisterNum(registerNum);
		userCommonStatistics.setYesterdayUser(yesUserCount);
		userCommonStatistics.setYesterdayManager(yesManagerCount);
		userCommonStatistics.setYesterdaySeller(yesSellerCount);
		userCommonStatistics.setStatisticsDate(start);
		userCommonStatistics.setLoginNum(loginNum);
		userCommonStatistics.setAlluserNum(alluserNum);
		if(editUserCommonStatistics(userCommonStatistics)){
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
	public boolean editUserCommonStatistics(UserCommonStatistics userCommonStatistics){
		return userCommonStatisticsDao.save(userCommonStatistics)==null?false:true;
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
	public List<UserCommonStatistics> findUserCommonStatisticsList(String start,String end){
		Date start1 = DateUtil.StringToTimestamp(start);
		Date end1 = DateUtil.StringToTimestamp(end);
		return userCommonStatisticsDao.findUserCommonStatisticsList(start1,end1);
	}
	
	/**
	 * 得到会员统计饼图
	 * @return
	 */
	public RepReportStatisticsDTO getCake(){
		
		Integer totalUserCount = userCommonStatisticsDao.findUserCount(UserType.USER);//普通会员
		int totalManagerCount = userCommonStatisticsDao.findUserCount(UserType.MANAGER);//融资经理
		int totalSellerCount = userCommonStatisticsDao.findUserCount(UserType.SELLER);//商家
		Integer allUserCount = userCommonStatisticsDao.findUserCount();
		int otherCount = allUserCount - totalUserCount - totalManagerCount -totalSellerCount;
		RepReportStatisticsDTO rep = new RepReportStatisticsDTO();
		
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
	/***
	 * @param
	 *  date
	 * @return 
	 * */
	public UserCommonStatistics findStatisticsByDate(Date date ) {
		return this.userCommonStatisticsDao.findStatisticsByDate(date);
	}
}

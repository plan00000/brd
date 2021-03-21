package com.zzy.brd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.ActivityDao;
import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.Activity.ActivitySet;
import com.zzy.brd.entity.Activity.ActivityType;

/***
 * 活动service
 * 
 * @author csy 2016-09-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);
	@Autowired
	private ActivityDao activityDao;

	/**
	 * 星级订单
	 * 
	 * @return
	 */
	public Activity getStartOrderByActivityType() {
		return activityDao.getActivityByType(ActivityType.STARTORDER);
	}

	/**
	 * 收徒奖励
	 * 
	 * @return
	 */
	public Activity getApprenticeAwardByActivityType() {
		return activityDao.getActivityByType(ActivityType.BONUSAWARD);
	}
	/**
	 * 推荐注册
	 * 
	 * @return
	 */
	public Activity getRecommendRegisterByActivityType() {
		return activityDao.getActivityByType(ActivityType.RECOMMEND);
	}
	/**
	 * 根据id查找活动
	 * @param activityId
	 * @return
	 */
	public Activity findActivityById(long activityId) {
		return activityDao.findOne(activityId);
	}
	/**
	 * 修改活动
	 * @param activity
	 * @return
	 */
	public boolean editActivity(Activity activity){
		return activityDao.save(activity)==null?false:true;
	}
	/**
	 * 根据活动类型和设置获取活动信息
	 * @param type
	 * @param set
	 * @return
	 */
	public Activity getActivitByTypeAndSet(ActivityType type,ActivitySet set) {
		return activityDao.getActivitByTypeAndSet(type,set);
	} 
	
}


package com.zzy.brd.dao;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.Activity.ActivitySet;
import com.zzy.brd.entity.Activity.ActivityType;

/**
 * 
 * @author csy 2016-09-26
 */
public interface ActivityDao extends BaseDao<Activity> {

	@Query("select a from Activity a where a.activityType =?1 ")
	public Activity getActivityByType(ActivityType activityType);
	
	@Query("select a from Activity a where a.activityType = ?1 and a.activitySet = ?2")
	public Activity getActivitByTypeAndSet(ActivityType activityType ,ActivitySet activitySet);
	
}

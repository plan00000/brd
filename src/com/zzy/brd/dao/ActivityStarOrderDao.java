
package com.zzy.brd.dao;


import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.ActivityStarOrder;
import com.zzy.brd.entity.User;

/**
 * 
 * @author csy 2016-09-27
 */
public interface ActivityStarOrderDao extends BaseDao<ActivityStarOrder> {
	
	@Query("select a from ActivityStarOrder a where a.user = ?1")
	public ActivityStarOrder getActivityStarOrder(User user);

}

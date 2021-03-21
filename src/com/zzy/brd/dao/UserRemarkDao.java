package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserRemark;

/**
 * @author:xpk
 *    2016年10月20日-下午5:40:31
 **/
public interface UserRemarkDao extends BaseDao<UserRemark> {
	
	@Query("select u from UserRemark u where u.user=?1 ")
	List<UserRemark> findByUser(User user);
	
	
}

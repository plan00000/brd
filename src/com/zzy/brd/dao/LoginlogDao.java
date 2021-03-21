package com.zzy.brd.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.Loginlog;
import com.zzy.brd.entity.User;

/**
 * @author:xpk
 *    2016年10月19日-下午6:22:07
 **/
public interface LoginlogDao extends BaseDao<Loginlog> {
	
	@Query("select max(u.loginTimes) from Loginlog u where u.user = ?1 ")
	int findMaxLoginTimes(User user);
	
	@Query("select l from Loginlog l where l.loginDate between ?1 and ?2 and l.user =?3  ")
	List<Loginlog> findByTimeByUser(Date startTime,Date endTime,User user);
}

package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;

/**
 * @author:xpk
 *    2016年10月12日-下午2:01:18
 **/
public interface EmployeeDao extends BaseDao<User> {
	
	/***
	 *查询所有员工 
	 */
	@Query("select u from User u where (u.userType='6' or u.userType='3' or u.userType='4') ")
	List<User> findAllEmployee();
	
	/**
	 *查询员工发展的会员 包括商家 
	 **/
//	@Query("select u from User u where  u.userInfoBoth.salesman=?1")
//	List<User> findSalesman(User user);
	
}

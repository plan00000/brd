/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.Role;


/**
 * 角色Dao
 * 
 * @author cairongjie 2015年11月17日
 */
public interface RoleDao extends BaseDao<Role>{

	/**
	 * 通过角色名获取角色
	 * 
	 * @param @return
	 * @return List<Role>
	 */
	@Query("select r from Role r where r.rolename=?1 and r.state='1' ")
	List<Role> findByRolename(String rolename);
	
	
	/**
	 *查询非admin的角色 
	 * @return 
	 **/
	@Query("select r from Role r where r.rolename!='Admin' and r.state='1' ")
	List<Role> findAllNoAdmin();
	
	/**
	 * 非admin角色和业务员
	 * */
	@Query("select r from Role r where r.rolename !='Admin' and r.rolename !='业务员' and r.state='1' ")
	List<Role> findAllNoAdminAndSales();	
	
}

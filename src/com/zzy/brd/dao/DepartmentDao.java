/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.Department;

/**
 * 
 * @author wwy
 * 2015年10月13日
 */
public interface DepartmentDao extends BaseDao<Department> {
	/**
	 * 根据名称获取部门
	 * 
	 * @param @return
	 * @return List<Department>
	 */
	@Query("select d from Department d where d.name=?1 and d.state='1' ")
	public List<Department> findDepartmentByName(String name);
	
	/**
	 * 查找父部门
	 * @param department
	 * @return
	 */
	public List<Department> findByParent(Department department);
	
	/**
	 * 查找子部门
	 * @param department
	 * @return
	 */
	@Query("select d from Department d where d.parent=?1 and d.state='1' ")
	public List<Department> findDepartmentByParent(Department department);
	/**
	 * 根据名称和id获取部门
	 * @param name
	 * @param id
	 * @return
	 */
	@Query("select d from Department d where d.name = ?1 and d.id != ?2")
	public List<Department> finDepartmentByNameID(String name,Long id);
	
	/***
	 *查询部门等级 
	 * @param level
	 * @return
	 */
	@Query("select d from Department d where d.level=?1 and d.state='1' order by id asc ")
	public List<Department> findByLevel(int level);
	
	/***
	 *获取全部部门 
	 */
	@Query("select d from Department d where d.state='1'  ")
	public List<Department> findAllDepartment();
	
	@Query("select d from Department d where d.name=?1 and d.state='1' and d!= ?2 ")
	public Department findDepartmentByName2(String name,Department department);
	
	@Query("select d from Department d where d.state =1")
	public List<Department> findStaticsDepartment();
	
	
}

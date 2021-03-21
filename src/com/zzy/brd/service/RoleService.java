/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.service;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zzy.brd.dao.RoleDao;
import com.zzy.brd.dao.UserDao;
import com.zzy.brd.entity.Role;
import com.zzy.brd.entity.Role.State;
import com.zzy.brd.entity.User;

/**
 * 角色Service
 * 
 * @author cairongjie 2015年11月17日
 */
@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
public class RoleService extends BaseService{
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	
	/**
	 * 分页获取角色列表
	 * 
	 * @param @param keyword
	 * @param @param pageNum
	 * @param @param pageSize
	 * @param @return
	 * @return Page<Role>
	 */
	 public Page<Role> listPageRoles(Map<String, Object> searchParams,int pageNum,
			 int  pageSize){
		 Page<Role> roles=null;
		 PageRequest pageRequest=createPageRequest(pageNum, pageSize, "id", true);
		 @SuppressWarnings("unchecked")
		Specification<Role> spec=(Specification<Role>) createSpecification(searchParams,Role.class);
		roles =roleDao.findAll(spec,pageRequest);	
		return roles;
	 }
	 
	/**
	 * 获取角色列表
	 * 
	 * @param @return
	 * @return List<Role>
	 */
	@Transactional(readOnly=true)
	public List<Role> listRoles(){
		Iterable<Role> iterable=roleDao.findAll();
		return iterable==null?null:Lists.newArrayList(iterable);
	}

	/**
	 * 根据id获取角色
	 * 
	 * @param @param id
	 * @param @return
	 * @return Role
	 */
	@Transactional(readOnly=true)
	public Role findRoleById(long id){
		return roleDao.findOne(id);
	}
	/**
	 * 通过角色名获取角色
	 * 
	 * @param @return
	 * @return List<Role>
	 */
	public List<Role> findRoleByRolename(String rolename){
		return roleDao.findByRolename(rolename);
	}
	
	
	/**
	 * 修改角色
	 * 
	 * @param @param role
	 * @param @return
	 * @return boolean
	 */
	public boolean editRole(Role role){
		return roleDao.save(role)==null?false:true;
	}
	/**
	 * 添加角色
	 * 
	 * @param @param role
	 * @param @return
	 * @return boolean
	 */
	public boolean addRole(Role role){
		return roleDao.save(role)==null?false:true;
	}
	/**
	 * 指定role id更改role状态
	 * 
	 * @param id
	 * @param hide
	 * @return
	 */
	public boolean updateWithState(long id, State state) {
		Role role = roleDao.findOne(id);
		if (role == null) {
			 
			return false;
		}
		role.setState(state);
		roleDao.save(role);
		// 加入操作日志
		return true;
	}
	/**
	 * 删除role
	 * 
	 * @param @param role
	 * @return void
	 */
	public boolean delRole(Role role){
		List<User> userlist=userDao.findByRole(role);
		if(userlist!=null&&userlist.size()>0){
			for(User user:userlist){
				user.setRole(null);
				userDao.save(user);
			}
		}	 
		roleDao.delete(role);
		return true;
	}
	
}



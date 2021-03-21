/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.service;

 
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zzy.brd.dao.DepartmentDao;
import com.zzy.brd.dao.OrderCommonStatisticsDao;
import com.zzy.brd.dao.UserDao;
import com.zzy.brd.dto.rep.admin.reportStatistics.StatisticsOrderByDepartDTO;
import com.zzy.brd.entity.Department;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;

 
 


/**
 * 部门管理service
 * 
 * @author lzh 2016年9月25日
 */
@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
public class DepartmentService extends BaseService{
	private static final Logger logger=LoggerFactory.getLogger(Department.class);
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrderCommonStatisticsDao orderCommonStatisticsDao;
	
	/**
	 * 通过id获取部门等级
	 * 
	 * @param @param departmentId
	 * @param @return
	 * @return int
	 */
	public int getDeprtmentLevelSonLevel(Long departmentId){
		Department department=departmentDao.findOne(departmentId);
		return getDepartmentSonLevel(department);
	}
	/**
	 *  
	 * 
	 * @param @param department
	 * @param @return
	 * @return int
	 */
	public int getDepartmentSonLevel(Department department){
		
		if(department.getSons()==null||department.getSons().size()>0){
			 
			return 1;
		}else {
			 
			return getDepartmentSonLevel(department)+1;
		}
		
	}
	/**
	 * 获取部门等级
	 * 
	 * @param @param department
	 * @param @return
	 * @return int
	 */
	public int getDepartmentLevel(Department department){
		if(department.getParent()==null){
			return 1;
		}else {
			return getDepartmentLevel(department.getParent())+1;
		}
	}
	/**
	 * 分页获取部门
	 * 
	 * @param @param pageNumber
	 * @param @param size
	 * @param @return
	 * @return Page<Department>
	 */
	public Page<Department> listDepartments(int pageNumber,int size){
		PageRequest request = createPageRequest(pageNumber, size,
				"id:asc", false);
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("EQ_state", Department.State.NOMAL);
		@SuppressWarnings("unchecked")
		Specification<Department> spec = (Specification<Department>) createSpecification(
				searchParams, Department.class);
		Page<Department> result = departmentDao.findAll(spec,request);
		return result;
	}
	/**
	 * 获取部门列表
	 * 
	 * @param @return
	 * @return List<Department>
	 */
	@Transactional(readOnly = true)
	public List<Department> listDepartments(){
		Iterable<Department> iterable=departmentDao.findStaticsDepartment();
		return iterable==null?null:Lists.newArrayList(iterable);
	}
	
	/**
	 * 根据id判断部门是否存在
	 * 
	 * @param @param id
	 * @param @return
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isDepartmentExist(Long id){
		return departmentDao.findOne(id)==null?false:true;
	}
	
	/**
	 * 根据名称判断部门是否存在
	 * 
	 * @param @param name
	 * @param @return
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isDepartmentNameExist(String name){
		List<Department> departments=departmentDao.findDepartmentByName(name);
		if(departments!=null&&departments.size()>0){
			return false;
		}
		return true;
	}
	
	@Transactional(readOnly=true)
	public boolean isDepartmentNameExistById(String name,Long id){
		List<Department> departments = departmentDao.finDepartmentByNameID(name,id);
		if(departments!= null&&departments.size()>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 通过id获取部门
	 * 
	 * @param @param id
	 * @param @return
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getDepartmentById(Long id){
		return departmentDao.findOne(id);
	}
	/**
	 * 通过id获取部门及子部门
	 * 
	 * @param @param id
	 * @param @return
	 * @return List<Department>
	 */
	public List<Department> findDepartmentsById(Long id){
		Department department=getDepartmentById(id);
		if(department==null){
			return null;
		}
		List<Department> departments=Lists.newArrayList();
		departments.addAll(findDepartments(department));
		return departments;
	}
	/****
	 *获取全部部门 
	 * 
	 */
	public List<Department> findAllDepartment(){
		return departmentDao.findAllDepartment();
	}
	
	/**
	 * 递归返回部门下的子部门
	 * 
	 * @param @param department
	 * @param @return
	 * @return List<Department>
	 */
	public List<Department> findDepartments(Department department){
		List<Department> departments=Lists.newArrayList();
		//判断部门下的子孙部门是否为空
		if(department.getSons()!=null&&department.getSons().size()>0){
			for(Department department2:department.getSons()){
				departments.addAll(findDepartments(department2));
			}
		}
		departments.add(department);
		return departments;
	}

	/**
	 * 添加部门
	 * 
	 * @param @param department
	 * @param @return
	 * @return boolean
	 */
	public boolean addDepartment(Department department){
		return departmentDao.save(department)==null?false:true;
	}
	/**
	 * 修改部门
	 * 
	 * @param @param department
	 * @param @return
	 * @return boolean
	 */
	public boolean editDepartment(Department department){
		return departmentDao.save(department)==null?false:true;
	}
	
	
	
	/**
	 * 查找父部门
	 * 
	 * @param department
	 * @return
	 */
	public List<Department> findDepartmentByParent(Department department) {
		List<Department> departments = Lists.newArrayList();
		departments.addAll(departmentDao.findByParent(department));
		return departments;
	}
	
	/***
	 * 获取特定等级下的部门
	 * 
	 */
	public List<Department> findDepartmentByLevel(int level) {
		return  departmentDao.findByLevel(level);
	}
	
	/**
	 * 部门订单统计
	 * @param depart
	 * @return
	 */
	public StatisticsOrderByDepartDTO statistics(Department depart) {
		StatisticsOrderByDepartDTO dto = new StatisticsOrderByDepartDTO();
		List<State> states = new ArrayList<State>();
		states.add(State.ON);
		states.add(State.OFF);
		List<User> users = userDao.findByDepartmentlistAndStates(depart.getId(), states);//通过部门查询用户信息
		//通过各个部门的用户id去查找对应的商家融资 和融资经理
		if(null != users && users.size() >0 ){
			List<User> users1 = userDao.findUser(users);
			List<User> managers = userDao.findUser(users,User.UserType.MANAGER);
			List<User> sellers= userDao.findUser(users,User.UserType.SELLER);
			List<Long> ids1 = this.findAllUser(users1);//用户id
			if(ids1.size() > 0){
				dto = this.statisticsDepartUsers(ids1,managers,sellers);
			}else {
				dto.setTotalTime(0);
				dto.setYestodayTime(0);
				dto.setYestodayMoney(BigDecimal.ZERO);
				dto.setTotalMoney(BigDecimal.ZERO);
			}
		}else {
			dto.setTotalTime(0);
			dto.setYestodayTime(0);
			dto.setYestodayMoney(BigDecimal.ZERO);
			dto.setTotalMoney(BigDecimal.ZERO);
			}
		return dto;
	}
	 
	public List<Long> findAllUser(List<User> users){
		List<Long> ids = new ArrayList<Long>();
		for (User user : users) {
			if(user != null){
				ids.add(user.getId());
			}
		}
		return ids;
	}
	/**
	 * 部门数据统计
	 * @param ids
	 */
	public StatisticsOrderByDepartDTO statisticsDepartUsers(List<Long> ids,List<User> managers, List<User> sellers ) {
		StatisticsOrderByDepartDTO dto = new StatisticsOrderByDepartDTO();
		dto.setSellerCount(sellers.size());
		dto.setManagerCount(managers.size());
		dto.setTotalTime(orderCommonStatisticsDao.countOrderTimeByDepartUser(ids));
		dto.setYestodayTime(orderCommonStatisticsDao.countYestodayOrderTimeByDepartUser(ids));
		dto.setYestodayMoney(orderCommonStatisticsDao.countYestodayOrderMoneyByDepartUser(ids));
		dto.setTotalMoney(orderCommonStatisticsDao.countOrderMoneyByDepartUser(ids));
		return dto;
	}
	
	
	/***
	 * 根据名字
	 */
	public Department findDepartmentByName(String name,Department department){
		return departmentDao.findDepartmentByName2(name,department);
	}
	
	
}

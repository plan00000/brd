package com.zzy.brd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.EmployeeDao;
import com.zzy.brd.dto.rep.admin.department.RepDepartmentDetailDTO;
import com.zzy.brd.entity.Department;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserInfoEmployee;
import com.zzy.brd.util.string.StringUtil;

/**
 * @author:xpk
 *    2016年10月12日-下午1:57:05
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class EmployeeService extends BaseService {
	
	private @Autowired EmployeeDao employeeDao;
	private @Autowired DepartmentService departmentService;
	
	/**
	 * @param searchParams
	 * @param pageNumber
	 * @param sortType
	 * @param asc
	 **/
	@SuppressWarnings("unchecked")
	public Page<User> listEmployee(Map<String,Object> searchParams,int pageNumber,String sortType,boolean asc,String keyword){
		Pageable pageRequest = this.createPageRequest(pageNumber, Constant.PAGE_SIZE, sortType, asc);
		Specification<User> specs=(Specification<User>) createSpecification(searchParams,User.class);
		if(StringUtils.isNotBlank(keyword)){
			Specification<User> userSpec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					// TODO Auto-generated method stub
					String temp = "%" + ((String) keyword).replaceAll("\\\\", "\\\\\\\\")
							.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_") + "%";
					return cb.or(cb.like(root.get("mobileno"), temp),
							cb.like(root.get("realname"), temp));
				}
			};
			specs = Specifications.where(specs).and(userSpec);
		}
		return employeeDao.findAll(specs, pageRequest);
	}
	
	
	/***
	 * 查询所有员工
	 * 
	 */
	public List<User> findAllEmployee(){
		return employeeDao.findAllEmployee();
	}
	
	/***
	 * 查询员工下的商家
	 * @param user
	 * 
	 */
//	public List<User> findSalesman(User user){
//		return employeeDao.findSalesman(user);
//	}
	
	/***
	 * 改变状态
	 */
	public void changeState(User user,State state){
		user.setState(state);
		employeeDao.save(user);
	}

}

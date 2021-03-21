package com.zzy.brd.service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.OrderformDao;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.User;
import com.zzy.brd.util.date.DateUtil;



/**
 * @author:xpk
 *    2016年9月28日-下午2:26:36
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class OrderformService extends BaseService {
	
	@Autowired private OrderformDao orderformDao;
	
	/***
	 *根据用户查找订单 
	 * @param user
	 * @return
	 */	
	public List<Orderform> getOrderByUser(User user){
		return orderformDao.findOrderformByUser(user.getId());		
	}
	
	/***
	 * 根据id查找订单
	 * @param orderformId
	 * @return  
	 */
	public Orderform findOrderById(long orderformId){
		return orderformDao.findOne(orderformId);
	}
 	
	
	/****
	 * @author xpk
	 * @param searchParam
	 * @param orderBy
	 * @param asc
	 * @pageNumber
	 * @pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<Orderform> getOrderform(Map<String,Object> searchParams,boolean asc,int pageNumber,int pageSize,String status){
		Pageable pageRequest = this.createPageRequest(pageNumber, pageSize, "createTime:desc", asc);
		
		if (status.equals("UNLOAN")) {
			//未放款
			searchParams.put("EQ_status", OrderformStatus.UNLOAN);
		} else if (status.equals("LOANED")) {
			//已放款
			searchParams.put("EQ_status", OrderformStatus.LOANED);
		}
		Specification<Orderform> spec =  (Specification<Orderform>) this.createSpecification(searchParams, Orderform.class);
		
		if (status.equals("UNCHECKED")) {
				//未审核
				Specification<Orderform> specs = new Specification<Orderform>(){
					@Override
					public Predicate toPredicate(Root<Orderform> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						return cb.or(cb.equal(root.get("status"), Orderform.OrderformStatus.UNCHECKED),
								cb.equal(root.get("status"), OrderformStatus.UNCONTACTED),
								cb.equal(root.get("status"), Orderform.OrderformStatus.UNTOLKWITH)
								);
					}
				};			
				spec = Specifications.where(spec).and(specs);		
		} else if (status.equals("INVALID")) {
				//无效订单
				Specification<Orderform> specs = new Specification<Orderform>(){
					@Override
					public Predicate toPredicate(Root<Orderform> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						return cb.or(cb.equal(root.get("status"), Orderform.OrderformStatus.CHECKFAIL),
								cb.equal(root.get("status"), OrderformStatus.INVALID));
					}
				};			
				spec = Specifications.where(spec).and(specs);
		}
	
		
		return orderformDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 添加订单
	 * @param orderform
	 * @return
	 */
	public boolean addOrderform(Orderform orderform){
		return orderformDao.save(orderform) == null ?false:true;
	}
	/**
	 * 修改订单
	 * @param orderform
	 * @return
	 */
	public boolean editOrderform(Orderform orderform){
		return orderformDao.save(orderform) == null? false:true;
	}
	/**
	 * 统计贷款订单（待审核）
	 */
	public int countLoanUncheckNum (BillType billType,OrderformStatus status,OrderSource source){
		return orderformDao.countLoanUncheckNum(billType,status,source);
	}
	/**
	 * 官网订单（首页统计）
	 * @param status
	 * @param source
	 * @return
	 */
	public int countPcOrderNum(OrderformStatus status ,OrderSource source){
		return orderformDao.countPcOrderNum(status,source);
	}
	/**
	 * 后台订单列表
	 * @param searchParams
	 * @param sortName
	 * @param sortType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Orderform> adminOrderformList(Map<String,Object>searchParams,String sortName,String sortType,int pageNum,int pageSize){
		PageRequest pageRequest ;
		if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
			String sort = sortName+":"+sortType;
			pageRequest = createPageRequest(pageNum, pageSize, sort, false);
		}else{
			pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
		}
		@SuppressWarnings("unchecked")
		Specification<Orderform> spec = (Specification<Orderform>) createSpecification(
				searchParams, Orderform.class);
		Page<Orderform> result = orderformDao.findAll(spec, pageRequest);
		return result;
	}
	/**
	 * 贷款订单导出
	 * @param userType
	 * @param status
	 * @param billType
	 * @param type
	 * @param sortName
	 * @param sortType
	 * @param searchName
	 * @param searchValue
	 * @return
	 */
	public List<Orderform> exportOrderforms(String userType,String status,String billType,String type,String sortName,String sortType,String searchName,String searchValue,long userId,String getType,String createTime,String createStarttime,String createEndtime){
		Specification<Orderform> spec = new Specification<Orderform>() {

			@Override
			public Predicate toPredicate(Root<Orderform> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = null;
				List<Predicate> predicates = new LinkedList<Predicate>();
				
				Path expression = root.get("user").get("userType");
				if(!StringUtils.isBlank(userType)){
					if("USER".equals(userType)){
						predicates.add(cb.equal(expression, UserType.USER));
					}
					if("MANAGER".equals(userType)){
						predicates.add(cb.equal(expression, UserType.MANAGER));
					}
					if("SELLER".equals(userType)){
						predicates.add(cb.equal(expression, UserType.SELLER));
					}
					if("SALESMAN".equals(userType)){
						predicates.add(cb.equal(expression, UserType.SALESMAN));
					}
				}
				expression = root.get("status");
				if(!StringUtils.isBlank("status")){
					if("UNCONTACTED".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNCONTACTED));
					}
					if("UNTOLKWITH".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNTOLKWITH));
					}
					if("UNCHECKED".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNCHECKED));
					}
					if("UNLOAN".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNLOAN));
					}
					if("CHECKFAIL".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.CHECKFAIL));
					}
					if("LOANED".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.LOANED));
					}
					if("INVALID".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.INVALID));
					}
				}
				expression = root.get("product").get("type").get("billType");
				if(!StringUtils.isBlank(billType)){
					if("SELFHELPLOAN".equals(billType)){
						predicates.add(cb.equal(expression, BillType.SELFHELPLOAN));
					}
					if("EARNDIFFERENCE".equals(billType)){
						predicates.add(cb.equal(expression, BillType.EARNDIFFERENCE));
					}
					if("EARNCOMMISSION".equals(billType)){
						predicates.add(cb.equal(expression, BillType.EARNCOMMISSION));
					}
				}
				expression = root.get("product").get("type").get("productName");
				if(!StringUtils.isBlank(type)){
					predicates.add(cb.equal(expression, type));
				}
				if(!StringUtils.isBlank(searchName)){
					if("productName".equals(searchName)){
						expression = root.get("productInfo").get("productName");
						predicates.add(cb.like(expression, "%"+searchValue+"%"));
					}
					if("orderNo".equals(searchName)){
						expression = root.get("orderNo");
						predicates.add(cb.like(expression, "%"+searchValue+"%"));
					}
					if("comtractNum".equals(searchName)){
						expression = root.get("comtractNum");
						predicates.add(cb.like(expression,"%"+searchValue+"%"));
					}
					
				}
				expression = root.get("user").get("id");
				if(userId>-1){
					predicates.add(cb.equal(expression,userId));
				}
				expression = root.get("status");
				if("successOrder".equals(getType)){
					predicates.add(cb.equal(expression,Orderform.OrderformStatus.LOANED));
				}
				
				expression = root.get("createTime");
				if(!StringUtils.isBlank(createTime)){
					StringBuilder startBuilder = new StringBuilder(createTime);
					startBuilder.append(" 00:00:00");
					Timestamp startTimeTime = DateUtil
							.StringToTimestampLong(startBuilder.toString());
					predicates.add(cb.greaterThanOrEqualTo(expression, startTimeTime));
					
					StringBuilder endBuilder = new StringBuilder(createTime);
					endBuilder.append(" 23:59:59");
					Timestamp endTimeTime = DateUtil.StringToTimestampLong(endBuilder
							.toString());
					predicates.add(cb.lessThanOrEqualTo(expression, endTimeTime));
				}
				
				if(StringUtils.isNotBlank(createStarttime)){
					StringBuilder startBuilder = new StringBuilder(createStarttime);
					startBuilder.append(" 00:00:00");
					Timestamp startTimeTime = DateUtil
							.StringToTimestampLong(startBuilder.toString());
					predicates.add(cb.greaterThanOrEqualTo(expression, startTimeTime));
				}
				if(StringUtils.isNotBlank(createEndtime)){
					StringBuilder endBuilder = new StringBuilder(createEndtime);
					endBuilder.append(" 23:59:59");
					Timestamp endTimeTime = DateUtil.StringToTimestampLong(endBuilder
							.toString());
					predicates.add(cb.lessThanOrEqualTo(expression, endTimeTime));
				}
				
				expression = root.get("source");
				predicates.add(cb.equal(expression, OrderSource.WECHAT));
				if (!predicates.isEmpty()) {
					predicate = cb.and(predicates
							.toArray(new Predicate[predicates.size()]));
				}else {
					predicate = cb.conjunction();
				}
				if (predicate != null) {
					return predicate;
				}
				return cb.conjunction();
			}
		};
		String sortstr = "createTime:desc";
		if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
			sortstr = sortName+":"+sortType;
		}
		Sort sort = createSort(sortstr, true);
		return orderformDao.findAll(spec,sort);
	}
	public List<Orderform> exportPcOrderforms(String status,String sortName,String sortType,String searchName,String searchValue){
		Specification<Orderform> spec = new Specification<Orderform>() {

			@Override
			public Predicate toPredicate(Root<Orderform> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate;
				List<Predicate> predicates = new LinkedList<Predicate>();
				
				Path expression = root.get("status");
				if(!StringUtils.isBlank("status")){
					if("UNCONTACTED".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNCONTACTED));
					}
					if("UNTOLKWITH".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNTOLKWITH));
					}
					if("UNCHECKED".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNCHECKED));
					}
					if("UNLOAN".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.UNLOAN));
					}
					if("CHECKFAIL".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.CHECKFAIL));
					}
					if("LOANED".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.LOANED));
					}
					if("INVALID".equals(status)){
						predicates.add(cb.equal(expression, OrderformStatus.INVALID));
					}
				}
				if(!StringUtils.isBlank(searchName)){
					if("productName".equals(searchName)){
						expression = root.get("productInfo").get("productName");
						predicates.add(cb.like(expression, "%"+searchValue+"%"));
					}
					if("orderNo".equals(searchName)){
						expression = root.get("orderNo");
						predicates.add(cb.like(expression, "%"+searchValue+"%"));
					}
					if("comtractNum".equals(searchName)){
						expression = root.get("comtractNum");
						predicates.add(cb.like(expression,"%"+searchValue+"%"));
					}
					
				}
				expression = root.get("source");
				predicates.add(cb.equal(expression, OrderSource.PC));
				if (!predicates.isEmpty()) {
					predicate = cb.and(predicates
							.toArray(new Predicate[predicates.size()]));
				}else {
					predicate = cb.conjunction();
				}
				if (predicate != null) {
					return predicate;
				}
				return cb.conjunction();
			}
		};
		String sortstr = "createTime:desc";
		if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
			sortstr = sortName+":"+sortType;
		}
		Sort sort = createSort(sortstr, true);
		return orderformDao.findAll(spec,sort);
	}
	
	/***
	 * 获取指定用户指定时间内的订单 
	 */
	public List<Long> findOrderByTimeAndUser(Date startTime,Date endTime,long userId){
		return orderformDao.findOrderByTimeAndUser(startTime, endTime, userId);
	}
	
	
	
}

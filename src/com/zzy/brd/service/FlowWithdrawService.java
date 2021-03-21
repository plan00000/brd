package com.zzy.brd.service;

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

import com.zzy.brd.dao.FlowWithdrawDao;
import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.User;

/**
 * @author:xpk
 *    2016年9月29日-下午4:27:10
 **/
@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
public class FlowWithdrawService extends BaseService {
	
	@Autowired FlowWithdrawDao flowWithdrawDao;
	/***
	 *@user
	 *@return 
	 */
	public List<FlowWithdraw> findByUser(User user){
		return flowWithdrawDao.findByUser(user);
	}
	/**
	 * 
	 * @param flowWithdrawId
	 * @return
	 */
	public FlowWithdraw findByFlowWithdrawId(long flowWithdrawId){
		return flowWithdrawDao.findOne(flowWithdrawId);
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
	public Page<FlowWithdraw> getFlowWithdraw(Map<String,Object> searchParams,boolean asc,int pageNumber,int pageSize,String status){
		Pageable pageRequest = this.createPageRequest(pageNumber, pageSize, "id:desc", asc);
		 if(status.equals("ALEARDYLOAN")){
			searchParams.put("EQ_status", WithdrawStatus.ALEARDYLOAN);
		} else if(status.equals("FAILCHECK")) {
			searchParams.put("EQ_status",WithdrawStatus.FAILCHECK);
		}		
		Specification<FlowWithdraw> spec =  (Specification<FlowWithdraw>) this.createSpecification(searchParams, FlowWithdraw.class);
		if(status.equals("NOLENDING")){			
			Specification<FlowWithdraw> specs = new Specification<FlowWithdraw>(){
				@Override
				public Predicate toPredicate(Root<FlowWithdraw> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					// TODO Auto-generated method stub
					return cb.or(cb.equal(root.get("status"),FlowWithdraw.WithdrawStatus.NOCHECK),
							cb.equal(root.get("status"), FlowWithdraw.WithdrawStatus.NOLENDING)
							);
				}
			};			
			spec = Specifications.where(spec).and(specs);		
		} 	
		return flowWithdrawDao.findAll(spec, pageRequest);
	}
	
	
	public boolean saveFlowWithdraw(FlowWithdraw withdraw){
		return flowWithdrawDao.save(withdraw)==null ? false :true ;
	}
	
	/*public int countFlowWithdrawByVerifyNum(VerifyStatus verifyStatus){
		return flowWithdrawDao.countFlowWithdrawByVerifyNum(verifyStatus);
	}*/
	
	public int countFlowWithdrawByStatusNum(WithdrawStatus withdrawStatus){
		return flowWithdrawDao.countFlowWithdrawByStatusNum(withdrawStatus);
	}
	/**
	 * 后台体现列表
	 * @param searchParams
	 * @param sortName
	 * @param sortType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<FlowWithdraw> adminFlowWithdrawList(Map<String,Object> searchParams,String sortName,String sortType,int pageNum,int pageSize){
		PageRequest pageRequest ;
		if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
			String sort = sortName+":"+sortType;
			pageRequest = createPageRequest(pageNum, pageSize, sort, false);
		}else{
			pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
		}
		
		@SuppressWarnings("unchecked")
		Specification<FlowWithdraw> spec = (Specification<FlowWithdraw>)createSpecification(searchParams, FlowWithdraw.class);
		Page<FlowWithdraw> result = flowWithdrawDao.findAll(spec, pageRequest);
		return result;
	}
	/**
	 * 体现订单导出
	 * @param userType
	 * @param status
	 * @param searchName
	 * @param searchValue
	 * @param sortName
	 * @param sortType
	 * @return
	 */
	public List<FlowWithdraw> exportFlowWithdraw(String userType,String status,String searchName,String searchValue,String sortName,String sortType){
		Specification<FlowWithdraw> spec = new Specification<FlowWithdraw>() {

			@Override
			public Predicate toPredicate(Root<FlowWithdraw> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate;
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
				if(!StringUtils.isBlank(status)){
					if("NOCHECK".equals(status)){
						predicates.add(cb.equal(expression, WithdrawStatus.NOCHECK));
					}
					if("NOLENDING".equals(status)){
						predicates.add(cb.equal(expression, WithdrawStatus.NOLENDING));
					}
					if("FAILCHECK".equals(status)){
						predicates.add(cb.equal(expression, WithdrawStatus.FAILCHECK));
					}
					if("ALEARDYLOAN".equals(status)){
						predicates.add(cb.equal(expression, WithdrawStatus.ALEARDYLOAN));
					}
				}
				if(!StringUtils.isBlank(searchName)){
					if("mobileno".equals(searchName)){
						expression = root.get("user").get("mobileno");
						predicates.add(cb.like(expression,"%"+searchValue+"%"));
					}
					if("flowno".equals(searchName)){
						expression = root.get("flowno");
						predicates.add(cb.like(expression, "%"+searchValue+"%"));
					}
				}
				
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
		return flowWithdrawDao.findAll(spec,sort);
	}
	
	/**
	 * 查询最大的流水号 
	 **/
	public String findMaxFlowno(){
		return flowWithdrawDao.findMaxFlowno();
	}
	
	
}

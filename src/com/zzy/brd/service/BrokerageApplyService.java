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
import org.apache.poi.ss.SpreadsheetVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.BrokerageApplyDao;
import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.BrokerageApply.BrokerageApplyStatus;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.User.UserType;

/**
 * 佣金订单service
 * @author lzh 2016-10-11
 *
 */
@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
public class BrokerageApplyService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(BrokerageApplyService.class);
	@Autowired
	private BrokerageApplyDao brokerageApplyDao;
	
	/** 首页统计数量*/
	public int countBrokerageNum(BrokerageApplyStatus status){
		return brokerageApplyDao.countBrokerageNum(status);
	}
	/** 查询佣金表*/
	public BrokerageApply findByBrokerageId(long id){
		return brokerageApplyDao.findOne(id);
	}
	/** 添加佣金订单*/
	public boolean addBrokerageApply(BrokerageApply brokerageApply){
		return brokerageApplyDao.save(brokerageApply) == null?false :true;
	}
	/**
	 * 佣金订单列表
	 * @param searchParams
	 * @param sortName
	 * @param sortType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<BrokerageApply> getBrokerageApplys(Map<String,Object> searchParams,String sortName,String sortType,int pageNum,int pageSize){
		PageRequest pageRequest;
		if(!StringUtils.isBlank(sortName)&& !StringUtils.isBlank(sortType)){
			String sort = sortName + ":" + sortType;
			pageRequest  = createPageRequest(pageNum, pageSize, sort, false);
		}else{
			pageRequest = createPageRequest(pageNum, pageSize, "createTime:desc", false);
		}
		@SuppressWarnings("unchecked")
		Specification<BrokerageApply> spec = (Specification<BrokerageApply>)createSpecification(searchParams, BrokerageApply.class);
		Page<BrokerageApply> result = brokerageApplyDao.findAll(spec,pageRequest);
		return result;
	}
	
	public List<BrokerageApply> exportBrokerageApplys(String userType,String status,String billType,String sortName,String sortType,String searchName,String searchValue){
		Specification<BrokerageApply> spec = new Specification<BrokerageApply>() {

			@Override
			public Predicate toPredicate(Root<BrokerageApply> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate ;
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
					if("UNENTERING".equals(status)){
						predicates.add(cb.equal(expression, BrokerageApplyStatus.UNENTERING));
					}
					if("RISKCHECK".equals(status)){
						predicates.add(cb.equal(expression, BrokerageApplyStatus.RISKCHECK));
					}
					if("CEOCHECK".equals(status)){
						predicates.add(cb.equal(expression, BrokerageApplyStatus.CEOCHECK));
					}
					if("FINANCESEND".equals(status)){
						predicates.add(cb.equal(expression, BrokerageApplyStatus.FINANCESEND));
					}
					if("MANYTIMES".equals(status)){
						predicates.add(cb.equal(expression,BrokerageApplyStatus.MANYTIMES));
					}
					if("CEOPASS".equals(status)){
						predicates.add(cb.equal(expression,BrokerageApplyStatus.CEOPASS));
					}
					if("HADSEND".equals(status)){
						predicates.add(cb.equal(expression, BrokerageApplyStatus.HADSEND));
					}
					if("FAILORDER".equals(status)){
						predicates.add(cb.equal(expression, BrokerageApplyStatus.FAILORDER));
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
				if(!StringUtils.isBlank(searchName)){
					if("productName".equals(searchName)){
						expression = root.get("productInfo").get("productName");
						predicates.add(cb.like(expression, "%"+searchValue+"%"));
					}
					if("orderNo".equals(searchName)){
						expression = root.get("brokerageNO");
						predicates.add(cb.like(expression, "%"+searchValue+"%"));
					}
					
				}
				if(!predicates.isEmpty()){
					predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}else{
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
		return brokerageApplyDao.findAll(spec,sort);
	}
	
	public BrokerageApply findByOrderfrom(Orderform orderform){
		return  brokerageApplyDao.findByOrderform(orderform);
	}	
	
}

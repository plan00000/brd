package com.zzy.brd.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.ActivityStarOrderDao;
import com.zzy.brd.entity.ActivityStarOrder;
import com.zzy.brd.entity.User;

/***
 * 活动service
 * 
 * @author csy 2016-09-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityStarOrderService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityStarOrderService.class);
	@Autowired
	private ActivityStarOrderDao activityStarOrderDao;

	/**
	 * 根据 用户得到对应的星级菜单
	 * 
	 * @return
	 */
	public ActivityStarOrder getActivityStarOrder(User user) {
		return activityStarOrderDao.getActivityStarOrder(user);
	}
	

	/**
	 * 后台星级订单
	 * @param searchParams
	 * @param sortName
	 * @param sortType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<ActivityStarOrder> listActivityStarOrders(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortName, String sortType) {
		PageRequest pageRequest;
		if((!StringUtils.isBlank(sortName))&&(!StringUtils.isBlank(sortType))){
			String search = sortName + ":" + sortType;
			pageRequest = createPageRequest(pageNumber, pageSize, search, false);
		}else {
			pageRequest = createPageRequest(pageNumber, pageSize,
					"id:desc", false);
		}
		@SuppressWarnings("unchecked")
		Specification<ActivityStarOrder> spec = (Specification<ActivityStarOrder>) createSpecification(searchParams, ActivityStarOrder.class);
		Page<ActivityStarOrder> result = activityStarOrderDao.findAll(spec, pageRequest);
		return result;
	}
	/**
	 * @param 修改星级订单
	 * @return
	 */
	public boolean editActivityStarOrder(ActivityStarOrder activityStarOrder) {
		return activityStarOrderDao.save(activityStarOrder) == null ? false : true;
	}
	
}

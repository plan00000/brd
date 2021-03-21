package com.zzy.brd.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.UserOperLogDao;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserOperLog;

/**
 * @author:xpk
 *    2016年10月14日-下午4:47:37
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserOperLogService extends BaseService {
	
	@Autowired private UserOperLogDao operLogDao;
	@Autowired private UserService userService;
	
	/**
	 *增加日志
	 *@param opertor
	 *@param content
	 **/
	@Transactional(readOnly = false)
	public boolean addOperlog(User opertor,String content){
		UserOperLog log = new UserOperLog();
		log.setOperTime(new Date());
		log.setOperContent(content);
		log.setUser(opertor);
		return operLogDao.save(log) == null ? false: true;
	}
	
	
	/**
	 *获取操作日志列表 
	 **/
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public Page<UserOperLog> listOperLog(int pageNumber){
		PageRequest pageRequest = createPageRequest(pageNumber, Constant.PAGE_SIZE, "operTime:desc", false);
		Specification<UserOperLog> spec = (Specification<UserOperLog>) this.createSpecification(new HashMap<String,Object>(), UserOperLog.class);
		return operLogDao.findAll(spec, pageRequest);
	}

	
	
	
}

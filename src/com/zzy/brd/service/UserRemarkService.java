package com.zzy.brd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.UserRemarkDao;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserRemark;

/**
 * @author:xpk
 *    2016年10月20日-下午5:39:31
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserRemarkService extends BaseService {
	
	@Autowired
	private UserRemarkDao userRemarkDao;
	
	public boolean editRemark(UserRemark userRemark){
		return 	userRemarkDao.save(userRemark)==null?false:true;	
	}
	
	public List<UserRemark> findByUser(User user){
		return userRemarkDao.findByUser(user);
	}
	
	
	
}

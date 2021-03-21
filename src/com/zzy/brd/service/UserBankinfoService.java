package com.zzy.brd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.UserBankinfoDao;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserBankinfo;

/**
 * @author:xpk
 *    2016年9月27日-上午10:39:16
 **/
@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
public class UserBankinfoService extends BaseService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserBankinfoService.class);
	
	@Autowired UserBankinfoDao userBankinfoDao;
	
	public List<UserBankinfo> getUserBankByUser(User user) {
		return  userBankinfoDao.findUserBankByUser(user.getId());
	}
	
	public UserBankinfo findById(long bankid){
		return userBankinfoDao.findOne(bankid);
	}
	
	public boolean editBankinfo(UserBankinfo info){
		return userBankinfoDao.save(info) ==null? false :true;
	}
	
	public List<UserBankinfo> getUserBankByUserAndAccount(String account,User user){
		return userBankinfoDao.getUserBankByUserAndAccount(account, user);
	}
}

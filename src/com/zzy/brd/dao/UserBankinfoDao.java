package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserBankinfo;

/**
 * @author:xpk
 *    2016年9月27日-上午10:36:31
 **/

public interface UserBankinfoDao extends BaseDao<UserBankinfo> {
	
	@Query("select u from UserBankinfo u where u.user.id=?1  ")
	public List<UserBankinfo> findUserBankByUser(long userid);
	
	@Query("select u from UserBankinfo u where u.bankaccount=?1 and u.user =?2  ")
	public List<UserBankinfo> getUserBankByUserAndAccount(String account,User user);
	
}

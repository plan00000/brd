package com.zzy.brd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.zzy.brd.dao.UserInfoSellerDao;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepSellerStatisticAddressDTO;
//import com.zzy.brd.entity.UserInfoSeller;

/**
 * @author:xpk
 *    2016年12月5日-上午11:06:11
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserInfoSellerService extends BaseService {
	
//	@Autowired
//	private UserInfoSellerDao userInfoSellerDao;
	
	/*public boolean editUserInfoSeller(UserInfoSeller userInfoSeller){
		return userInfoSellerDao.save(userInfoSeller) ==null ? false :true;
	}*/
	
	/*public List<RepSellerStatisticAddressDTO> listSeller() {
		return userInfoSellerDao.listSeller();
	}*/
	
	
}

package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.dto.rep.admin.reportStatistics.RepSellerStatisticAddressDTO;
import com.zzy.brd.entity.UserInfoSeller;

/**
 * @author:xpk
 *    2016年12月5日-上午11:07:09
 **/
public interface UserInfoSellerDao extends BaseDao<UserInfoSeller> {
	
	@Query("select new com.zzy.brd.dto.rep.admin.reportStatistics.RepSellerStatisticAddressDTO(u) "
			+ "from User u " 
			+ " where u.userType=2 and u.state!=2 ")
	public List<RepSellerStatisticAddressDTO> listSeller();
	
	
	
	
}

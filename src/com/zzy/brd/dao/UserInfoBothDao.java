package com.zzy.brd.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.UserInfoBoth;
/**
 * 用户信息- 会员Dao
 * @author lzh 2016-9-26
 *
 */
public interface UserInfoBothDao extends BaseDao<UserInfoBoth>{
	/** 活动徒弟数量加1*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.acitivitySonSum = (ub.acitivitySonSum+1) where ub.id =?1")
	public int updateAddActivitySonNum(long id);
	/** 徒弟数量加1*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.sonSum = (ub.sonSum+1) where ub.id =?1")
	public int updateAddSonNum(long id);
	/** 徒弟数量减1*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.sonSum = (ub.sonSum-1) where ub.id =?1")
	public int updateSubSonNum(long id);
	/** 徒孙数量加1*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.grandsonSum = (ub.grandsonSum+1) where ub.id =?1")
	public int updateAddGrandSonNum(long id);
	/** 徒孙数量减1*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.grandsonSum = (ub.grandsonSum-1) where ub.id =?1")
	public int updateSubGrandSonNum(long id);
	/** 徒孙孙数量加1*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.ggsonsSum = (ub.ggsonsSum+1) where ub.id =?1")
	public int updateAddGgsonsSum(long id);
	/** 徒孙孙数量减1*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.ggsonsSum = (ub.ggsonsSum-1) where ub.id =?1")
	public int updateSubGgsonsNum(long id);
	
	/** 加 活动奖励佣金，可提现金额加*/
	@Modifying
	@Query(value = "update UserInfoBoth ub set ub.activityBrokerage = (ub.activityBrokerage+ ?2) ,"
			+ " ub.brokerageCanWithdraw = (ub.brokerageCanWithdraw+ ?2) where ub.id =?1")
	public int AddActivityBrokerageAndBrokerageCanWithdraw(long id,BigDecimal bonusAmount);
	
}

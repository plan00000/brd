package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;

/**
 * @author:xpk
 *    2016年9月29日-下午4:24:23
 **/
public interface FlowWithdrawDao extends BaseDao<FlowWithdraw> {
	
	
	@Query("select f from FlowWithdraw f where f.user =?1 order by f.createTime ")
	List<FlowWithdraw> findByUser(User user);
	
	@Query("select count(f) from FlowWithdraw f where f.status = ?1")
	int countFlowWithdrawByStatusNum(WithdrawStatus withdrawStatus);
	
	@Query("select max(f.flowno) from FlowWithdraw f ")
	String findMaxFlowno();
	
}

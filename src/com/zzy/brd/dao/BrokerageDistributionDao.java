package com.zzy.brd.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import com.zzy.brd.dto.rep.recordBrokerage.RecordBrokerageDTO;
import com.zzy.brd.entity.BrokerageDistribution;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.User;

/**
 * @author:xpk
 *    2016年9月27日-上午11:21:54
 **/
public interface BrokerageDistributionDao extends BaseDao<BrokerageDistribution> {

/*	@Query("select b from BrokerageDistribution b where b.user = ?1")
	public List<BrokerageDistribution> getBrokerageDistributionsByUser(User user);*/
	
/*	@Query(value = "select ifnull(sum(r.have_brokerage),0) from record_brokerage r where r.user_id=?1 and r.send_brokerage  between ?2 and ?3 ", nativeQuery = true)
	BigDecimal findBrokerages(Long userId , Date startDate, Date endDate);*/
	
	
	@Query("select b from  BrokerageDistribution b where b.user= ?1 and b.orderform =?2")
	public List<BrokerageDistribution> getByUserOrderform(User user,Orderform orderform);	
	
/*	*//**
	 * 获得佣金发放列表并排序
	 * @param pageable
	 * @return
	 *//*
	@Query(value="select  new com.zzy.brd.dto.rep.recordBrokerage.RecordBrokerageDTO(r)  "
			+ "from RecordBrokerage r where r.user=?1")
	List<RecordBrokerageDTO> getBrokerageDistributionsByUser(User user);*/
	
}

package com.zzy.brd.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.dto.rep.recordBrokerage.RecordBrokerageDTO;
import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.entity.User;

/**
 * 
 * @author csy 2016-09-28 
 *
 */
public interface RecordBrokerageDao extends BaseDao<RecordBrokerage> {
	
	@Query("select r from RecordBrokerage r where r.user =?1")
	public List<RecordBrokerage> getRecordBrokerages(User user);
	
	@Query(value = "select ifnull(sum(r.have_brokerage),0) from record_brokerage r where r.user_id=?1 and r.send_brokerage  between ?2 and ?3 ", nativeQuery = true)
	BigDecimal findBrokerages(Long userId , Date startDate, Date endDate);
	@Query(value = "select ifnull(sum(r.have_brokerage),0) from record_brokerage r where r.user_id=?1  ", nativeQuery = true)
	BigDecimal findBrokerages(Long userId);
	
	/**
	 * 获得佣金发放列表并排序
	 * @param pageable
	 * @return
	 */
	@Query(value="select  new com.zzy.brd.dto.rep.recordBrokerage.RecordBrokerageDTO(r)  "
			+ "from RecordBrokerage r where r.user=?1 order by r.sendBrokerage desc")
	List<RecordBrokerageDTO> getBrokerageDistributionsByUser(User user);
}

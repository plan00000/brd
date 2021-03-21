/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatistics;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatisticsList;
import com.zzy.brd.dto.rep.product.RepDisplayProductDTO;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.Product.Status;
import com.zzy.brd.entity.Product.isDisplay;
import com.zzy.brd.entity.ProductType;

/**
 * 
 * @author csy 2016-09-26
 *
 */
public interface ProductDao extends BaseDao<Product> {
	
	@Query("select p from Product p where p.isDisplay=?1 and p.status=?2 order by p.sortid ")
	public List<Product> findByIsDisplayAndStatus(isDisplay isDisplay, Status status);
	
	
	@Query(value="select  new com.zzy.brd.dto.rep.product.RepDisplayProductDTO(p) from Product p where p.isDisplay=?1 and p.status=?2 order by p.indexSortid ")
	Page<RepDisplayProductDTO> pageRepDisplayProductDTO(isDisplay isDisplay, Status status,Pageable pageable);
	
	/**
	 * 获得所有未删除的产品数量
	 * @return
	 */
	@Query(value = "select count(*) from Product p where p.status=0")
	int productCount();
	/**
	 * 根据提单类型获得所有未删除的产品数量
	 * @return
	 */
	@Query(value = "select count(*) from Product p where p.status=0 and p.type.billType=?1")
	int productCount(ProductType.BillType billType);
	
	
	/**
	 * 得到产品数量 和被申请数量
	 * @param billType
	 * @return
	 */
	@Query(value = "select new com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatistics(sum(p.applyTimes),count(p.id),pt.billType)  "
			+ "from Product p left join p.type pt where p.status =0 and pt.state = 1 and pt.billType = ?1 "
			+ "group by pt.billType " )
	public RepProductStatistics getRepProductStatistics(ProductType.BillType billType);
	/**
	 * 得到放款订单数和放款金额
	 * @param billType
	 * @return
	 */
	@Query(value = "select new com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatistics(count(p.id),sum(o.actualMoney),pt.billType)  "
			+ "from Orderform o left join o.product p left join p.type pt where o.status=5 and p.status =0 and pt.state = 1 and pt.billType = ?1 "
			+ "group by pt.billType " )
	public RepProductStatistics getRepProductStatistics1(ProductType.BillType billType);
	
	/**
	 * 得到产品统计list
	 * @param billType
	 * @return
	 */
	@Query(value = "select new com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatisticsList(p,pt,pi,sum(o.actualMoney))  "
			+ "from Orderform o right join o.product p left join p.type pt left join p.info pi where p.status =0 and pt.state = 1 "
		//	+ "group by p.id  order by sum(o.actualMoney) desc" )
			+ "group by p.id  order by p.applyTimes  DESC " )
	public Page<RepProductStatisticsList> ProductStatisticsList(Pageable pageable);
	
	/**
	 * 根据产品名称查询产品
	 * @param productName
	 * @return
	 */
	@Query(value = "select p from Product p where p.info.productName =?1")
	public Product isProductNameExist(String productName);
	/**
	 * 根据产品id查询产品是否存在
	 * @param productId
	 * @return
	 */
	@Query(value = "select p from Product p where p.id =?1 and p.status = 1")
	public Product isProductExist(long productId);
	
	@Query(value = "select p from Product p where p.type.id = ?1 and p.status =0")
	public List<Product> findProductByProductTypeId(long productTypeId);

}

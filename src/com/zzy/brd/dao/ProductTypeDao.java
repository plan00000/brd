package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.Product.Status;
import com.zzy.brd.entity.ProductType.BillType;

/**
 * 产品类型dao
 * @author lzh 2016-9-28
 *
 */
public interface ProductTypeDao extends BaseDao<ProductType>{

	@Query(value="select pt from ProductType pt where pt.state =1 and pt.billType = ?1 group by pt.productName order by pt.id")
	public List<ProductType> getTypesByBillType(BillType billType);
	
	@Query(value="select pt from ProductType pt where pt.state =1 group by pt.productName order by pt.id")
	public List<ProductType> getTypes();
	/** 官网订单类型*/
	@Query(value = "select pt from ProductType pt where state = 1 and pt.billType =0 group by pt.productName order by pt.id")
	public List<ProductType> getPcTypes();
	@Query(value = "select count(*) from ProductType pt where pt.state=1")
	int productTypeCount();
	
	@Query(value ="select pt from ProductType pt where pt.productName = ?1 and pt.state = 1")
	public List<ProductType> findProductTypeByName(String name);
	
	@Query(value = "select pt from ProductType pt where pt.productName = ?1 and pt.state = 1 and pt.billType = ?2")
	public List<ProductType> checkProductTypeNameAndBillType(String name,BillType billType);
	
	@Query(value ="select pt from ProductType pt where pt.id <4 and pt.state = 1")
	public List<ProductType> findProductTypeIDLT4();
	@Query(value ="select pt from ProductType pt where pt.id >3 and pt.state = 1 and pt.productName = ?1")
	public List<ProductType> findProductTypeIDGT3(String name);
	
	@Query(value = "select pt from ProductType pt where pt.id =?1 and pt.state = 1")
	public ProductType findProductTypeById(long productTypeId);
	
	@Query(value = "select pt from ProductType pt where pt.billType =?1 and pt.state =1")
	public List<ProductType> getListByBillType(BillType billType);
	
	@Query(value ="select pt from ProductType pt where pt.productName = ?1 and  pt.id != ?2 and pt.state = 1")
	public ProductType checkProductTypeNameAndId(String name,long id);
}

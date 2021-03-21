package com.zzy.brd.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.ProductTypeDao;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.ProductType.BillType;

/**
 * 产品类型-service
 * @author lzh 2016-9-28
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProductTypeService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(ProductTypeService.class);
	
	@Autowired
	private ProductTypeDao productTypeDao;
	/**
	 * 获取产品类型
	 * @return
	 */
	public List<ProductType> getTypes(){
		return productTypeDao.getTypes();
	}
	public List<ProductType> getTypesByBillType(BillType billType){
		return productTypeDao.getTypesByBillType(billType);
	}
	/** 官网获取产品类型*/
	public List<ProductType> getPcTypes(){
		return productTypeDao.getPcTypes();
	}
	/**
	 * 通过id查询产品类型
	 * @param productTypeById
	 * @return
	 */
	public ProductType findProductTypeById(long productTypeId){
		return productTypeDao.findProductTypeById(productTypeId);
	}
	/**
	 * id小于4的产品类型列表
	 * @return
	 */
	public List<ProductType> findProductTypeIDLT4(){
		return productTypeDao.findProductTypeIDLT4();
	}
	
	/**
	 * 通过类别查询查询产品类型
	 * @param productName
	 * @return
	 */
	public List<ProductType> findProductTypeByName(String productName){
		return productTypeDao.findProductTypeByName(productName);
	}
	/**
	 * 产品类型删除
	 * @param productType
	 */
	public void deleteProductType(ProductType productType){
		productTypeDao.delete(productType) ;
	}
	/**
	 * 产品列表
	 * @param searchParams
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<ProductType> getProductTypeList(Map<String, Object> searchParams,int pageNum,int pageSize){
		PageRequest pageRequest = createPageRequest(pageNum,pageSize,"id:asc",false);
		@SuppressWarnings("unchecked")
		Specification<ProductType> spec = (Specification<ProductType>) createSpecification(
				searchParams, ProductType.class);
		Page<ProductType> result = productTypeDao.findAll(spec, pageRequest);
		return result;
	}
	/**
	 * 根据名称查询产品类型是否有一样的
	 * @param name
	 * @return
	 */
	public boolean checkProductTypeName(String name){
		List<ProductType> productTypes = productTypeDao.findProductTypeByName(name);
		if(productTypes.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	public boolean checkProductTypeNameAndBillType(String name ,int billType){
		List<ProductType> productTypes = productTypeDao.checkProductTypeNameAndBillType(name,BillType.values()[billType]);
		if(productTypes.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	public boolean checkProductTypeNameAndId(String name,long id){
		boolean flag = false;
		if(id>3){
			ProductType productType = productTypeDao.checkProductTypeNameAndId(name,id);
			if(productType == null){
				flag = true;
			}else{
				flag= false;
			}
		}else{
			List<ProductType> list = productTypeDao.findProductTypeIDGT3(name);
			if(null == list || list.size() ==0 ){
				flag = true;
			}else{
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * 添加
	 * @param productType
	 * @return
	 */
	public boolean addProductType(ProductType productType){
		return productTypeDao.save(productType) == null?false :true;
	}
	
	/**
	 * 查询产品类型类表
	 * @param billType
	 * @return
	 */
	public List<ProductType> getListByBillType(BillType billType){
		return productTypeDao.getListByBillType(billType);
	}
}

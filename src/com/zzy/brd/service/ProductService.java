package com.zzy.brd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.ProductDao;
import com.zzy.brd.dao.ProductInfoDao;
import com.zzy.brd.dao.ProductTypeDao;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatistics;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepProductStatisticsList;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepStatisticBrokerageDTO;
import com.zzy.brd.dto.rep.product.RepDisplayProductDTO;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.ProductInfo;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.User;

/***
 * 产品service
 * 
 * @author csy 2016-09-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProductService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductTypeDao productTypeDao;
	@Autowired
	private ProductInfoDao productInfoDao;

	/***
	 * 获得显示在首页的产品
	 * 
	 * @return
	 */
	public Page<RepDisplayProductDTO> pageRepDisplayProductDTO(){
		PageRequest pageRequest = createPageRequest(1, 5, "id", false);
		Page<RepDisplayProductDTO> result = productDao.pageRepDisplayProductDTO(Product.isDisplay.YES, Product.Status.NORMAL, pageRequest);
		return result;
	}	
	
	/**
	 * 微信手机站首页产品类表获取
	 * @param searchParams
	 * @return
	 */
	public Page<Product> pageDisplayProduct(Map<String, Object> searchParams){
		String orderBy ="indexSortid:asc";
		PageRequest req = createPageRequest(1, 10000000,orderBy, false);
		@SuppressWarnings("unchecked")
		Specification<Product> spec = (Specification<Product>)createSpecification(searchParams, Product.class);
		return productDao.findAll(spec,req);
	}
	/**
	 * 微信手机站产品类表获取
	 * @param searchParams
	 * @param sortRate
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Product> getProductList(Map<String, Object> searchParams,String sortBy,int pageNum,int pageSize){
		String orderBy ="";
		if(!StringUtils.isBlank(sortBy)){
			if("loanMinAmount".equals(sortBy)){
				orderBy = "info.loanMinAmount:asc,info.loanMaxAmount:asc,sortid:asc";
			}else{
				orderBy = "sortid:asc,createTime:desc";
			}
		}else{
			orderBy = "sortid:asc,createTime:desc";
		}
		PageRequest req = createPageRequest(pageNum, pageSize,orderBy, false);
		@SuppressWarnings("unchecked")
		Specification<Product> spec = (Specification<Product>)createSpecification(searchParams, Product.class);
		
		return productDao.findAll(spec,req);
	}
	/**
	 * 查询一条产品信息
	 * @param id
	 * @return
	 */
	public Product findById(long id){
		return productDao.findOne(id);
	}
	public boolean isProductExist(long productId){
		
		return productDao.isProductExist(productId) == null?false:true;
	}
	/**
	 * 添加产品
	 * @param product
	 * @return
	 */
	public boolean addProduct(Product product){
		return productDao.save(product) == null ?false:true;
	}
	/**
	 * 编辑产品信息
	 * @param product
	 * @return
	 */
	public boolean editProduct(Product product){
		ProductInfo productInfo = productInfoDao.save(product.getInfo());
		if(productInfo == null){
			return false;
		}
		return productDao.save(product) == null?false:true;
	}
	
	/**
	 * 得到产品总数 和分类总数
	 * @return
	 */
	public Map<String, Object> getProductCount(){
		Map<String, Object> map = Maps.newHashMap();
		int productCount = productDao.productCount();
		int productTypeCount = productTypeDao.productTypeCount();
		map.put("productCount", productCount);
		map.put("productTypeCount", productTypeCount);
		return map;
	}
	/**
	 * 提单类型商品列表
	 * @return
	 */
	public ArrayList<RepProductStatistics> getBillTypeList(){
		ArrayList<RepProductStatistics> repList = new ArrayList<RepProductStatistics>();
		int productCount = productDao.productCount();
		if(productCount==0){
			return repList;
		}
		RepProductStatistics repSelf2 = new RepProductStatistics();  
		RepProductStatistics repDiff2 = new RepProductStatistics();  
		RepProductStatistics repComm2 = new RepProductStatistics();  
		
		
		RepProductStatistics repSelf = productDao.getRepProductStatistics(ProductType.BillType.SELFHELPLOAN);
		RepProductStatistics repSelf1 = productDao.getRepProductStatistics1(ProductType.BillType.SELFHELPLOAN);
		RepProductStatistics repDiff = productDao.getRepProductStatistics(ProductType.BillType.EARNDIFFERENCE);
		RepProductStatistics repDiff1 = productDao.getRepProductStatistics1(ProductType.BillType.EARNDIFFERENCE);
		RepProductStatistics repComm = productDao.getRepProductStatistics(ProductType.BillType.EARNCOMMISSION);
		RepProductStatistics repComm1 = productDao.getRepProductStatistics1(ProductType.BillType.EARNCOMMISSION);
		
		if(repDiff!=null){
			long productCountDiff  = repDiff.getProductCount();
			float diffCountFloat = (float)productCountDiff/(float)productCount;
			diffCountFloat   =  (float)(Math.round(diffCountFloat*10000))/100;
			String diffPercent = String.valueOf(diffCountFloat)+"%";
			repDiff2.setProductPercent(diffPercent);
			repDiff2.setApplyTimes(repDiff.getApplyTimes());
			repDiff2.setProductCount(repDiff.getProductCount());
			repDiff2.setBillType(repDiff.getBillType());
		}
		if(repDiff1!=null){
			repDiff2.setLoanMoney(repDiff1.getLoanMoney());
			repDiff2.setLoanCount(repDiff1.getLoanCount());
			repDiff2.setBillType(repDiff1.getBillType());
		}
		
		if(repSelf!=null){
			long productCountDiff  = repSelf.getProductCount();
			float diffCountFloat = (float)productCountDiff/(float)productCount;
			diffCountFloat   =  (float)(Math.round(diffCountFloat*10000))/100;
			String diffPercent = String.valueOf(diffCountFloat)+"%";
			repSelf2.setProductPercent(diffPercent);
			repSelf2.setApplyTimes(repSelf.getApplyTimes());
			repSelf2.setProductCount(repSelf.getProductCount());
			repSelf2.setBillType(repSelf.getBillType());
		}
		if(repSelf1!=null){
			repSelf2.setLoanMoney(repSelf1.getLoanMoney());
			repSelf2.setLoanCount(repSelf1.getLoanCount());
			repSelf2.setBillType(repSelf1.getBillType());
		}
		
		if(repComm!=null){
			long productCountDiff  = repComm.getProductCount();
			float diffCountFloat = (float)productCountDiff/(float)productCount;
			diffCountFloat   =  (float)(Math.round(diffCountFloat*10000))/100;
			String diffPercent = String.valueOf(diffCountFloat)+"%";
			repComm2.setProductPercent(diffPercent);
			repComm2.setApplyTimes(repComm.getApplyTimes());
			repComm2.setProductCount(repComm.getProductCount());
			repComm2.setBillType(repComm.getBillType());
		}
		if(repComm1!=null){
			repComm2.setLoanMoney(repComm1.getLoanMoney());
			repComm2.setLoanCount(repComm1.getLoanCount());
			repComm2.setBillType(repComm1.getBillType());
		}
		repList.add(repDiff2);
		repList.add(repComm2);
		repList.add(repSelf2);
		
/*		if((repDiff!=null)&&(repDiff1!=null)){
			long productCountDiff  = repDiff.getProductCount();
			float diffCountFloat = (float)productCountDiff/(float)productCount;
			diffCountFloat   =  (float)(Math.round(diffCountFloat*10000))/100;
			String diffPercent = String.valueOf(diffCountFloat)+"%";
			long loanCountDiff = repDiff1.getLoanCount();
			
			repDiff.setLoanCount(loanCountDiff);
			repDiff.setLoanMoney(repDiff1.getLoanMoney());
			repDiff.setProductPercent(diffPercent);
			repList.add(repDiff);
		}*/
/*		if((repComm!=null)&&(repComm1!=null)){
			long productCountComm  = repComm.getProductCount();
			float commCountFloat = (float)productCountComm/(float)productCount;
			commCountFloat   =  (float)(Math.round(commCountFloat*10000))/100;
			String commPercent = String.valueOf(commCountFloat)+"%";
			long loanCountComm = repComm1.getLoanCount();
			repComm.setLoanCount(loanCountComm);
			repComm.setLoanMoney(repComm1.getLoanMoney());
			repComm.setProductPercent(commPercent);
			repList.add(repComm);
		}
		if((repSelf!=null)&&(repSelf1!=null)){
			long productCountSelf  = repSelf.getProductCount();
			float selfCountFloat = (float)productCountSelf/(float)productCount;
			selfCountFloat   =  (float)(Math.round(selfCountFloat*10000))/100;
			String selfPercent = String.valueOf(selfCountFloat)+"%";
			long loanCountSelf = repSelf1.getLoanCount();
			repSelf.setLoanCount(loanCountSelf);
			repSelf.setLoanMoney(repSelf1.getLoanMoney());
			repSelf.setProductPercent(selfPercent);
			repList.add(repSelf);	
		}*/
		return repList;
	}
	
	/**
	 * 获得产品列表
	 * @param pageNumber
	 * @param size
	 * @return
	 */
	public Page<RepProductStatisticsList> ProductStatisticsList(int pageNum){
		PageRequest pageRequest = createPageRequest(pageNum, 10, "id", false);
		Page<RepProductStatisticsList> result= productDao.ProductStatisticsList(pageRequest);
		return result;
	}
	/**
	 * 产品列表
	 * @param searchParams
	 * @param sortName
	 * @param sortType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Product> getadminProductPage(Map<String, Object> searchParams,String sortName,String sortType,int pageNum ,int pageSize){
		PageRequest pageRequest;
		if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
			if("loanMinAmount".equals(sortName)){
				String sort = "info.loanMinAmount"+":"+sortType+",info.loanMaxAmount:asc";
				pageRequest = createPageRequest(pageNum, pageSize, sort, false);
			}else
			if("loanRate".equals(sortName)){
				String sort = "info.loanRate"+":"+sortType;
				pageRequest = createPageRequest(pageNum, pageSize, sort, false);
			}else 
			if("applyTimes".equals(sortName)){
				String sort = "applyTimes"+":"+sortType;
				pageRequest = createPageRequest(pageNum, pageSize, sort, false);
			}else{
				pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
			}
			
		}else{
			pageRequest = createPageRequest(pageNum,pageSize,"createTime:desc",false);
		}
		@SuppressWarnings("unchecked")
		Specification<Product> spec = (Specification<Product>) createSpecification(
				searchParams, Product.class);
		Page<Product> result = productDao.findAll(spec, pageRequest);
		return result;
	}
	/**
	 * 判断产品是否存在
	 * @param name
	 * @return
	 */
	public boolean isProductNameExist(String name){
		Product product = productDao.isProductNameExist(name);
		if(product == null){
			return false;
		}else{
			return true;
		}
		
	}
	/**
	 * 判断产品类型是否已被使用
	 * @param productTypeId
	 * @return
	 */
	public List<Product> findProdcutByProdcutTypeId(long productTypeId){
		return productDao.findProductByProductTypeId(productTypeId);
	}
	
}

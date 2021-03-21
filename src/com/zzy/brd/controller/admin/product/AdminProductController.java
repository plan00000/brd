package com.zzy.brd.controller.admin.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.BeanMapper;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.req.admin.product.ReqAdminAddProductDTO;
import com.zzy.brd.dto.req.admin.product.ReqAdminProductTypeDTO;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.Product.InterestType;
import com.zzy.brd.entity.Product.MortgageType;
import com.zzy.brd.entity.Product.Status;
import com.zzy.brd.entity.Product.isDisplay;
import com.zzy.brd.entity.ProductInfo;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.ProductType.State;
import com.zzy.brd.entity.UserOperLog;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.ProductTypeService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;

/**
 * 产品-controller
 * @author lzh 2016/1019
 *
 */
@Controller
@RequestMapping("admin/product")
public class AdminProductController {
	private static final Logger logger = LoggerFactory.getLogger(AdminProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserOperLogService userOperLogService;
	/**
	 * 跳转到选择产品类型页面
	 * @param model
	 * @return
	 */
	@RequestMapping("toChooseType")
	public String toChooseProductType(Model model){
		List<ProductType> selfProductTypes = productTypeService.getListByBillType(BillType.SELFHELPLOAN);
		List<ProductType> differenceProductTypes = productTypeService.getListByBillType(BillType.EARNDIFFERENCE);
		List<ProductType> commissionProductTypes = productTypeService.getListByBillType(BillType.EARNCOMMISSION);
		
		model.addAttribute("selfProductTypes", selfProductTypes);
		model.addAttribute("differenceProductTypes", differenceProductTypes);
		model.addAttribute("commissionProductTypes", commissionProductTypes);
		
		return "admin/product/choosetype";
	}
	/**
	 * 跳转到添加产品页面
	 * @param billType
	 * @param type
	 * @param mortgageType
	 * @param interestType
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddProduct")
	public String toAddProduct(@RequestParam(value = "productTypeId", required = false) long productTypeId
			,@RequestParam(value = "type",required = false ,defaultValue = "") String type
			,@RequestParam(value = "mortgageType",required = false ,defaultValue = "") int mortgageType
			,@RequestParam(value = "interestType",required = false ,defaultValue = "") int interestType
			,Model model){
		ProductType productType = productTypeService.findProductTypeById(productTypeId);
		model.addAttribute("productType", productType);
		model.addAttribute("productTypeId", productTypeId);
		model.addAttribute("type", type);
		model.addAttribute("mortgageType", mortgageType);
		model.addAttribute("interestType", interestType);
		//添加产品标题 csy 2016-11-01
		String title = null;
		String billtype = productType.getBillType().getDes();
		String interestTypeStr = null;
		if(interestType==0){
			interestTypeStr = "利息按月";
		}else if(interestType==1){
			interestTypeStr = "利息按日";
		}else if(interestType==2){
			interestTypeStr = "收益金";
		}else{
			interestTypeStr = "特殊模式";
		}
		title = billtype+"-"+interestTypeStr;
		model.addAttribute("title", title);
		return "admin/product/addproduct";
	}
	/**
	 * 产品列表
	 * @param billType
	 * @param model
	 * @return
	 */
	@RequestMapping("productlist")
	public String productList(@RequestParam(value = "page",required = false,defaultValue = "1") int pageNum
			,@RequestParam(value = "billType",required = false,defaultValue = "") String  billType
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,HttpServletRequest request,Model model){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		if(!StringUtils.isBlank(billType)){
			if("SELFHELPLOAN".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
			}
			if("EARNDIFFERENCE".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.EARNDIFFERENCE);
			}
			if("EARNCOMMISSION".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.EARNCOMMISSION);
			}
		}
		searchParams.put("EQ_status", Status.NORMAL);
		Page<Product> products = productService.getadminProductPage(searchParams, sortName, sortType, pageNum, Constant.PAGE_SIZE);
		model.addAttribute("products", products);
		model.addAttribute("billType", billType);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("page", pageNum);
		model.addAttribute("queryStr", request.getQueryString());
		return "admin/product/productlist";
	}
	/**
	 * 添加产品
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addProduct",method =RequestMethod.POST )
	@ResponseBody
	public RepSimpleMessageDTO addProduct(@Valid ReqAdminAddProductDTO dto
			,BindingResult result
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		//判断产品名称是否已存在
		if(productService.isProductNameExist(dto.getProductName())){
			rep.setCode(0);
			rep.setMes("产品名称已存在");
			return rep;
		}
		//判断产品类型是否被删除
		ProductType productType = productTypeService.findProductTypeById(dto.getProductTypeId());
		if(productType == null){
			rep.setCode(0);
			rep.setMes("产品类型已被删除");
			return rep;
		}
		Product product = buildProduct(dto);
		if(productService.addProduct(product)){
			//添加操作记录
			userOperLogService.addOperlog(user, "添加产品"+product.getInfo().getProductName());
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("添加失败");
		return rep;
	}
	
	/** 添加产品构造函数*/
	public Product buildProduct(ReqAdminAddProductDTO dto){
		ProductType productType = productTypeService.findProductTypeById(dto.getProductTypeId());
		Product product;
		if(dto.getProductId() == null){
			product = new Product();
			product.setApplyTimes(0);
		}else{
			product = productService.findById(dto.productId);
		}
		
		product.setCreateTime(new Date());
		product.setSortid(dto.getSortid());
		product.setIndexSortid(dto.getIndexSortid());
		product.setIsDisplay(isDisplay.values()[dto.getIsDisplay()]);
		product.setInterestType(InterestType.values()[dto.getInterestType()]);
		product.setMortgageType(MortgageType.values()[dto.getMortgageType()]);
		product.setType(productType);
		product.setStatus(Status.NORMAL);
		
		ProductInfo productInfo = new ProductInfo();
		if(product.getInfo() != null){
			productInfo.setId(product.getInfo().getId());
		}
		BeanMapper.copy(dto, productInfo);
		productInfo.setLoanMinAmount(dto.getLoanMinAmount().multiply(new BigDecimal(10000)));
		productInfo.setLoanMaxAmount(dto.getLoanMaxAmount().multiply(new BigDecimal(10000)));
		if((dto.getInterestType() == 0 || dto.getInterestType() == 2) && productType.getBillType() == BillType.EARNDIFFERENCE){
			productInfo.setSpread(dto.getSpread().divide(new BigDecimal(100)));
			productInfo.setSpreadMin(dto.getSpreadMin().divide(new BigDecimal(100)));
			productInfo.setSpreadMax(dto.getSpreadMax().divide(new BigDecimal(100)));
		}
		if(dto.getInterestType() == 1 && productType.getBillType() == BillType.EARNDIFFERENCE){
			productInfo.setSpread(dto.getSpread().divide(new BigDecimal(1000)));
			productInfo.setSpreadMin(dto.getSpreadMin().divide(new BigDecimal(1000)));
			productInfo.setSpreadMax(dto.getSpreadMax().divide(new BigDecimal(1000)));
		}
		if((dto.getInterestType() == 0 || dto.getInterestType() == 2) && (productType.getBillType() == BillType.EARNCOMMISSION ||productType.getBillType() == BillType.SELFHELPLOAN) ){
			productInfo.setExpense(dto.getExpense().divide(new BigDecimal(100)));
			if(productType.getBillType() == BillType.EARNCOMMISSION ){
				productInfo.setPercentageRate(dto.getPercentageRate().divide(new BigDecimal(100)));
			}
		}
		if(dto.getInterestType() == 1 && (productType.getBillType() == BillType.EARNCOMMISSION ||productType.getBillType() == BillType.SELFHELPLOAN)){
			productInfo.setExpense(dto.getExpense().divide(new BigDecimal(1000)));
			if(productType.getBillType() == BillType.EARNCOMMISSION){
				productInfo.setPercentageRate(dto.getPercentageRate().divide(new BigDecimal(100)));
			}
		}
		/*if(productType.getBillType() != BillType.SELFHELPLOAN){*/
			productInfo.setFatherRate(dto.getFatherRate().divide(new BigDecimal(100)));
			productInfo.setSellerRate(dto.getSellerRate().divide(new BigDecimal(100)));
			productInfo.setSalesmanRate(dto.getSalesmanRate().divide(new BigDecimal(100)));
		/*}*/
		
		if(dto.getInterestType() == 3 && productType.getBillType() == BillType.EARNCOMMISSION){
			productInfo.setAlgoParamA(dto.getAlgoParamA().divide(new BigDecimal(100)));
			productInfo.setAlgoParamB(dto.getAlgoParamB().divide(new BigDecimal(100)));
			productInfo.setAlgoParamC(dto.getAlgoParamC().divide(new BigDecimal(100)));
			productInfo.setAlgoParamD(dto.getAlgoParamD().divide(new BigDecimal(100)));
			productInfo.setAlgoParamE(dto.getAlgoParamE().divide(new BigDecimal(100)));
			productInfo.setAlgoParamF(dto.getAlgoParamF().divide(new BigDecimal(100)));
			productInfo.setAlgoParamG(dto.getAlgoParamG().divide(new BigDecimal(100)));
			productInfo.setAlgoParamH(dto.getAlgoParamH().divide(new BigDecimal(100)));
		}
		if(dto.getInterestType() ==1){
			productInfo.setLoanRate(dto.getLoanRate().divide(new BigDecimal(1000)));
		}else{
			productInfo.setLoanRate(dto.getLoanRate().divide(new BigDecimal(100)));
		}
		productInfo.setUserRate(new BigDecimal(0.1));
		/*if(dto.getInterestType() == 0 || dto.getInterestType() == 1){
			productInfo.setLoanMinRate(dto.getLoanMinRate());
			productInfo.setLoanMaxRate(dto.getLoanMaxRate());
		}*/
		product.setInfo(productInfo);
		return product;
	}
	/**
	 * 跳转到编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("toEditProduct/{id}")
	public String toEditProduct(@PathVariable("id") long id
			,Model model){
		Product product = productService.findById(id);
		if(product==null){
			return "error/product_not_valid";
		}
		//编辑产品标题 csy 2016-11-01
		String title = "";
		if(product != null){
			String interestTypeStr = product.getInterestType().getDes();
			ProductType productType  = product.getType();
			if(productType != null){
				String billType = productType.getBillType().getDes();
				title = billType+"-"+interestTypeStr;
			}
		}
		model.addAttribute("title", title);
		model.addAttribute("product", product);
		return "admin/product/editproduct";
	}
	/**编辑产品 */
	@RequestMapping("editProduct")
	@ResponseBody
	public RepSimpleMessageDTO editProduct(ReqAdminAddProductDTO dto
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		
		if(productService.isProductExist(dto.getProductId())){
			rep.setCode(0);
			rep.setMes("产品不存在或已被删除");
			return rep;
		}
		//判断产品名称是否已存在
		Product checkproduct = productService.findById(dto.getProductId());
		if(!checkproduct.getInfo().getProductName().equals(dto.getProductName()) ){
			if(productService.isProductNameExist(dto.getProductName())){
				rep.setCode(0);
				rep.setMes("产品名称已存在");
				return rep;
			}
		}
		//判断产品类型是否被删除
		ProductType productType = productTypeService.findProductTypeById(dto.getProductTypeId());
		if(productType == null){
			rep.setCode(0);
			rep.setMes("产品类型已被删除");
			return rep;
		}
		Product product = buildProduct(dto);
		if(productService.editProduct(product)){
			//添加操作记录
			userOperLogService.addOperlog(user, "修改产品"+product.getInfo().getProductName());
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("修改失败");
		return rep;
	}
	/**
	 * 删除产品
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public RepSimpleMessageDTO deleteProduct(@RequestParam("id") long id
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		
		if(productService.isProductExist(id)){
			rep.setCode(0);
			rep.setMes("产品不存在或已被删除");
			return rep;
		}
		Product product = productService.findById(id);
		product.setStatus(Status.DELETED);
		if(productService.editProduct(product)){
			//添加操作记录
			userOperLogService.addOperlog(user, "删除产品"+product.getInfo().getProductName());
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		return rep;
	}
	/**
	 * 产品批量删除
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deletes",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO deleteProducts(@RequestParam(value = "ids") Long[] ids
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		
		if(ids !=null){
			for(long id :ids){
				if(productService.isProductExist(id)){
					rep.setCode(0);
					rep.setMes("产品不存在或已被删除");
					return rep;
				}else{
					Product product = productService.findById(id);
					product.setStatus(Status.DELETED);
					if(productService.editProduct(product)){
						//添加操作记录
						userOperLogService.addOperlog(user, "删除产品"+product.getInfo().getProductName());
					}else{
						rep.setCode(0);
						rep.setMes("部分产品删除失败");
						return rep;
					}
				}
			}
			
		}
		rep.setCode(1);
		rep.setMes("success");
		return rep;
	}
	@RequestMapping("changeIsDisplay")
	@ResponseBody
	public RepSimpleMessageDTO changeIsDisplay (@RequestParam(value = "productId") long productId
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		
		if(productService.isProductExist(productId)){
			rep.setCode(0);
			rep.setMes("产品不存在或已被删除");
			return rep;
		}
		Product product = productService.findById(productId);
		if(product.getIsDisplay() ==isDisplay.YES){
			product.setIsDisplay(isDisplay.NO);
		}else{
			product.setIsDisplay(isDisplay.YES);
		}
		if(productService.editProduct(product)){
			//添加操作记录
			userOperLogService.addOperlog(user, "删除产品"+product.getInfo().getProductName());
			rep.setCode(1);
			rep.setMes(product.getIsDisplay().getDes());
			return rep;
		}
		rep.setCode(0);
		rep.setMes("修改失败");
		return rep;
	}
}

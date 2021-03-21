package com.zzy.brd.mobile.web.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.Product.MortgageType;
import com.zzy.brd.entity.Product.Status;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.ProductTypeService;
import com.zzy.brd.service.UserService;

/**
 * 微信站-产品类表
 * @author lzh 2016-9-27
 *
 */
@Controller
@RequestMapping("weixin/product")
public class WeixinProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(WeixinProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private InformationService informationService;
	
	/**
	 * 产品列表
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String list(@RequestParam (value="page",required = true, defaultValue = "1") int pageNum
			,@RequestParam(value ="billType",defaultValue = "",required = false) String billType//earndifference
			,@RequestParam(value = "mortgageType",defaultValue="",required =false) String mortgageType
			,@RequestParam(value = "type",defaultValue="",required = false) String type 
			,@RequestParam(value = "sortBy",defaultValue = "",required = false) String sortBy
			,@RequestParam(value = "isIndex",defaultValue = "-1",required =false) int isIndex
			,HttpServletRequest request
			,Model model){
		//用户标志 
		String userFlag ="";
		Long userId = ShiroUtil.getUserId(request);
		
		BillType billTypeForType = BillType.EARNCOMMISSION;
		
		if(userId == null){
			userFlag = "user";
		}else{
			User user = userService.findById(userId);
			if(user.getUserType() == UserType.USER){
				userFlag = "user";
			}else{
				userFlag = "all";
			}
		}
		if(isIndex>0){
			userFlag = "manager";
		}
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(mortgageType) ){
			if("NULLLOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType", MortgageType.NULLLOAN);
			}
			if("MORTGAGELOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType", MortgageType.MORTGAGELOAN);
			}
			if("CREDITLOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType",MortgageType.CREDITLOAN);
			}
		}
		if(!StringUtils.isBlank(type)){
			searchParams.put("EQ_type.productName", type);
		}
		
		if(!StringUtils.isBlank(billType)){
			if("selfhelploan".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
				billTypeForType = BillType.SELFHELPLOAN;
			}
			if("earncommission".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.EARNCOMMISSION);
				billTypeForType = BillType.EARNCOMMISSION;
			}
			if("earndifference".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.EARNDIFFERENCE);
				billTypeForType = BillType.EARNDIFFERENCE;
			}
			
		}else{
			billType = "earncommission";
			if(userFlag.equals("user")){
				billType = "selfhelploan";
				searchParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
				billTypeForType = BillType.SELFHELPLOAN;

			}else if("manager".equals(userFlag)){
				List<BillType> billTypes = new ArrayList<BillType>();
				billTypes.add(BillType.EARNCOMMISSION);
				billTypes.add(BillType.EARNDIFFERENCE);
				searchParams.put("IN_type.billType", billTypes);
			}
			if(!userFlag.equals("user")){
				searchParams.put("EQ_type.billType", BillType.EARNCOMMISSION);
				billTypeForType = BillType.EARNCOMMISSION;
			}
			
		}

		List<ProductType> productTypes = productTypeService.getTypesByBillType(billTypeForType);
		searchParams.put("EQ_status",Status.NORMAL);

		List<Product> productstotal = productService.getProductList(searchParams,sortBy,1,100000).getContent();
		
		Page<Product> products = productService.getProductList(searchParams,sortBy,pageNum,Constant.PAGE_SIZE);
		model.addAttribute("products", products.getContent());
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("billType", billType);
		model.addAttribute("mortgageType",mortgageType);
		model.addAttribute("type",type);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("userFlag", userFlag);
		model.addAttribute("isIndex", isIndex);
		model.addAttribute("totlepage",
				(int) Math.ceil((double) productstotal.size() / 10));
	
		return "mobile/product/productlist";
	}
	/**
	 * ajax加载数据
	 * @param pageNum
	 * @param billType
	 * @param mortgageType
	 * @param type
	 * @param sortBy
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="ajaxProductList")
	public String ajaxProductList(@RequestParam (value="page",required = true, defaultValue = "1") int pageNum
			,@RequestParam(value ="billType",defaultValue = "",required = false) String billType//earndifference
			,@RequestParam(value = "mortgageType",defaultValue="",required =false) String mortgageType
			,@RequestParam(value = "type",defaultValue="",required = false) String type 
			,@RequestParam(value = "sortBy",defaultValue = "",required = false) String sortBy
			,HttpServletRequest request
			,Model model){
		//用户标志 
		String userFlag ="";
		String userLogin ="";
		Long userId = ShiroUtil.getUserId(request);
		
		if(userId == null){
			userFlag = "user";
			userLogin = "false";
		}else{
			User user = userService.findById(userId);
			userLogin = "true";
			if(user.getUserType() == UserType.USER){
				userFlag = "user";
			}else{
				userFlag = "all";
			}
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(mortgageType) ){
			if("NULLLOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType", MortgageType.NULLLOAN);
			}
			if("MORTGAGELOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType", MortgageType.MORTGAGELOAN);
			}
			if("CREDITLOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType",MortgageType.CREDITLOAN);
			}
		}
		if(!StringUtils.isBlank(type)){
			searchParams.put("EQ_type.productName", type);
		}
		if(!StringUtils.isBlank(billType)){
			if("selfhelploan".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
			}
			if("earncommission".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.EARNCOMMISSION);
			}
			if("earndifference".equals(billType)){
				searchParams.put("EQ_type.billType", BillType.EARNDIFFERENCE);
			}
			
		}
		searchParams.put("EQ_status",Status.NORMAL);
		Page<Product> products = productService.getProductList(searchParams,sortBy,pageNum,Constant.PAGE_SIZE);
		model.addAttribute("userFlag", userFlag);
		model.addAttribute("userLogin", userLogin);
		model.addAttribute("products", products.getContent());
		model.addAttribute("billType", billType);
		model.addAttribute("mortgageType",mortgageType);
		model.addAttribute("type",type);
		model.addAttribute("sortBy", sortBy);
		return "mobile/product/ajaxproductlist";
	}
	/**
	 * 产品详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("productDetail/{id}")
	public String productDetail(@PathVariable long id,Model model){
		Product product = productService.findById(id);
		if(product == null){
			logger.error("查找product 失败：not fount id:{}", id);
			return "error/500";
		}
		if(product.getStatus() ==Status.DELETED){
			return "error/product_not_valid";
		}
		model.addAttribute("product", product);
		
		return "mobile/product/productdetail";
	}
}

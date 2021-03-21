package com.zzy.brd.controller.admin.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.req.admin.product.ReqAdminProductTypeDTO;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserOperLog;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.ProductType.State;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.ProductTypeService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;

/**
 * 产品类型-controller
 * @author lzh 2016/10/19
 *
 */
@Controller
@RequestMapping("admin/product/productType")
public class AdminProductTypeController {
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserOperLogService userOperLogService;
	@Autowired
	private ProductService productService;
	/**
	 * 产品类型
	 * @param pageNum
	 * @param billType
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String productTypeList(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
			,@RequestParam(value = "billType",required = false,defaultValue ="") String billType
			,HttpServletRequest request, Model model){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(billType)){
			if("SELFHELPLOAN".equals(billType)){
				searchParams.put("EQ_billType", BillType.SELFHELPLOAN);
			}
			if("EARNDIFFERENCE".equals(billType)){
				searchParams.put("EQ_billType", BillType.EARNDIFFERENCE);
			}
			if("EARNCOMMISSION".equals(billType)){
				searchParams.put("EQ_billType", BillType.EARNCOMMISSION);
			}
		}
		searchParams.put("EQ_state", State.NORMAL);
		Page<ProductType> productTypes = productTypeService.getProductTypeList(searchParams, pageNum, Constant.PAGE_SIZE);
		model.addAttribute("billType", billType);
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("page", pageNum);
		model.addAttribute("queryStr", request.getQueryString());
		return "admin/product/producttype/list";
	}
	@RequestMapping(value = "checkName",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO checkProductTypeName(@RequestParam(value = "name",required = false) String name){
		RepSimpleMessageDTO rep  = new RepSimpleMessageDTO();
		if(!productTypeService.checkProductTypeName(name)){
			rep.setCode(0);
			rep.setMes("产品类别不能重复");
			return rep;
		}
		rep.setCode(1);
		rep.setMes("success");
		return rep;
	}
	/**
	 * 添加产品类型
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="add",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO addProductType(@Valid ReqAdminProductTypeDTO dto
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		if(!productTypeService.checkProductTypeNameAndBillType(dto.getProductName(),dto.getBillType())){
			rep.setCode(0);
			rep.setMes("产品类别不能重复");
			return rep;
		}
		ProductType productType = new ProductType();
		productType.setBillType(BillType.values()[dto.getBillType()]);
		productType.setCreateTime(new Date());
		productType.setProductName(dto.getProductName());
		productType.setState(State.NORMAL);
		if(productTypeService.addProductType(productType)){
			//添加操作记录
			userOperLogService.addOperlog(user, "添加产品类型"+dto.getProductName());
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("添加失败");
		return rep;
	}
	/**
	 * 编辑产品类型
	 * @param productName
	 * @param productTypeId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO editProductType(@RequestParam(value = "productName",required = false) String productName
			,@RequestParam(value = "productTypeId",required = true) long productTypeId
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		ProductType productType = productTypeService.findProductTypeById(productTypeId);
		if(productType == null){
			rep.setCode(0);
			rep.setMes("不存在该产品类型");
			return rep;
		}
		if(!productTypeService.checkProductTypeNameAndId(productName,productType.getId())){
			rep.setCode(0);
			rep.setMes("对不起！产品类别已存在");
			return rep;
		}
		String oldProductType =productType.getProductName();
		productType.setProductName(productName);
		if(productTypeId<4){
			List<ProductType> list = productTypeService.findProductTypeIDLT4();
	        for(ProductType productType1:list) {
	        	productType1.setProductName(productName);
	        	if(!productTypeService.addProductType(productType1)){
	        		rep.setCode(0);
	        		rep.setMes("修改失败");
	        	}
	        }  
		}
		if(productTypeService.addProductType(productType)){
			//添加操作记录
			userOperLogService.addOperlog(user, "修产品类型"+oldProductType +"改为"+productName);
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
	 * @param request
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public RepSimpleMessageDTO deleteProductType(@RequestParam("id") long id
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		ProductType productType = productTypeService.findProductTypeById(id);
		if(productType == null){
			rep.setCode(0);
			rep.setMes("不存在该产品类型");
			return rep;
		}
		//判断该类型是否已被用到
		List<Product> products = productService.findProdcutByProdcutTypeId(productType.getId());
		if(!products.isEmpty()){
			rep.setCode(0);
			rep.setMes("对不起！该产品类型已在产品中使用不能被删除");
			return rep;
		}
		productType.setState(State.DEL);
		if(productTypeService.addProductType(productType)){
			//添加操作记录
			userOperLogService.addOperlog(user, "删除产品类型"+productType.getProductName());
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("删除失败");
		return rep;
	}
	/**
	 * 批量删除
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deletes",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO deleteProductTypes(Long[] ids
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		if(ids !=null){
			for(long id :ids){
				ProductType productType = productTypeService.findProductTypeById(id);
				if(productType ==null){
					rep.setCode(0);
					rep.setMes("部分产品类型已被删除");
					return rep;
				}
				//判断该类型是否已被用到
				List<Product> products = productService.findProdcutByProdcutTypeId(productType.getId());
				if(!products.isEmpty()){
					rep.setCode(0);
					rep.setMes("对不起！该产品类型已在产品中使用不能被删除");
					return rep;
				}else {
					productType.setState(State.DEL);
					if(productTypeService.addProductType(productType)){
						//添加操作记录
						userOperLogService.addOperlog(user, "删除产品类型"+productType.getProductName());
					}else{
						rep.setCode(0);
						rep.setMes("部分产品类型删除失败");
						return rep;
					}
				}
			}
		}
		rep.setCode(1);
		rep.setMes("success");
		return rep;
	}
}

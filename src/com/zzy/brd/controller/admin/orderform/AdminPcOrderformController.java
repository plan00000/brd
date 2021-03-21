package com.zzy.brd.controller.admin.orderform;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.rep.admin.product.RepProductDetailDTO;
import com.zzy.brd.dto.rep.admin.product.RepProductListDTO;
import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.OrderSerial;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.OrderformProductRecord;
import com.zzy.brd.entity.OrderformRemark;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.BrokerageApply.BrokerageApplyStatus;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.Product.InterestType;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.OrderOperLogService;
import com.zzy.brd.service.OrderformProductRecordService;
import com.zzy.brd.service.OrderformRemarkService;
import com.zzy.brd.service.OrderformService;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.ProductTypeService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.excel.ExcelUtil;
import com.zzy.brd.util.excel.ExcelUtil.ExcelBean;
import com.zzy.brd.util.string.StringUtil;
import com.zzy.brd.util.tld.DateUtils;
import com.zzy.brd.util.tld.PriceUtils;

/**
 * pc官网订单-conroller
 * @author lzh 2016/10/11
 *
 */
@Controller
@RequestMapping("admin/orderform/pc")
public class AdminPcOrderformController {
	private static final Logger logger = LoggerFactory.getLogger(AdminPcOrderformController.class);
	@Autowired
	private OrderformService orderformService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private OrderformRemarkService orderformRemarkService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderformProductRecordService orderformProductRecordService;
	@Autowired
	private OrderOperLogService orderOperLogService;
	
	@RequestMapping("list")
	public String list(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
			,@RequestParam(value = "status",required = false,defaultValue = "") String status
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,@RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			,@RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
			,HttpServletRequest request, Model model){
		Map<String,Object> searchParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(status)){
			if("UNCONTACTED".equals(status)){
				searchParams.put("EQ_status", OrderformStatus.UNCONTACTED);
			}
			if("UNTOLKWITH".equals(status)){
				searchParams.put("EQ_status", OrderformStatus.UNTOLKWITH);
			}
			if("UNCHECKED".equals(status)){
				searchParams.put("EQ_status", OrderformStatus.UNCHECKED);
			}
			if("UNLOAN".equals(status)){
				searchParams.put("EQ_status", OrderformStatus.UNLOAN);
			}
			if("CHECKFAIL".equals(status)){
				searchParams.put("EQ_status", OrderformStatus.CHECKFAIL);
			}
			if("LOANED".equals(status)){
				searchParams.put("EQ_status", OrderformStatus.LOANED);
			}
			if("INVALID".equals(status)){
				searchParams.put("EQ_status", OrderformStatus.INVALID);
			}
		}
		if(!StringUtils.isBlank(searchName)){
			if("productName".equals(searchName)){
				String search = "LIKE_productInfo.productName";
				searchParams.put(search, searchValue);
			}
			if("orderNo".equals(searchName)){
				String search = "LIKE_orderNo";
				searchParams.put(search, searchValue);
			}
			if("comtractNum".equals(searchName)){
				String search = "LIKE_comtractNum";
				searchParams.put(search, searchValue);
			}
			
		}
		searchParams.put("EQ_source", OrderSource.PC);
		Page<Orderform> orderforms = orderformService.adminOrderformList(searchParams, sortName, sortType, pageNum, Constant.PAGE_SIZE);
		model.addAttribute("orderforms", orderforms);
		model.addAttribute("status", status);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("searchName", searchName);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("page", pageNum);
		model.addAttribute("queryStr", request.getQueryString());
		model.addAttribute("totalcount", orderforms.getTotalElements());
		return "admin/orderform/pc/orderlist";
	}
	/**
	 * pc订单详情
	 * @param id
	 * @param request
	 * @param pageNumber
	 * @param model
	 * @return
	 */
	@RequestMapping("detail/{id}")
	public String pcDetail(@PathVariable long id, ServletRequest request, 
			@RequestParam(value = "page", required = true, defaultValue = "1") int pageNum
			,@RequestParam(value = "interestType",required = false) String interestType
			,@RequestParam(value = "type",required = false) String type
			,Model model){
		Orderform orderform = orderformService.findOrderById(id);
		if(orderform == null){
			return "";
		}
		/** 产品详情*/
		List<ProductType> productTypes = productTypeService.getTypesByBillType(BillType.SELFHELPLOAN);
		/** 订单操作记录*/
		Map<String, Object> searchoolParams = new HashMap<String, Object>();
		searchoolParams.put("EQ_order.id", orderform.getId());
		Page<OrderOperLog> orderOperLogs = orderOperLogService.getOrderOperLogPage(searchoolParams, pageNum, Constant.PAGE_SIZE);
		List<OrderformRemark> orderformRemarks = orderformRemarkService.getOrderformRemarkList(id);
		/** 产品更改产品列表*/
		Map<String, Object> searchProductParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(interestType)){
			if("INTERESTMODELMONTH".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.INTERESTMODELMONTH);
			}
			if("INTERESTMODELDAY".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.INTERESTMODELDAY);
			}
		}
		if(!StringUtils.isBlank(type)){
			searchProductParams.put("EQ_info.productName", type);
		}
		searchProductParams.put("EQ_type.billType",BillType.SELFHELPLOAN );
		Page<Product> products = productService.getProductList(searchProductParams,"",pageNum,Constant.PAGE_SIZE);
		model.addAttribute("products", products);
		model.addAttribute("orderOperLogs", orderOperLogs);
		model.addAttribute("orderformRemarks", orderformRemarks);
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("orderform", orderform);
		model.addAttribute("pageNum", pageNum);
		return "admin/orderform/pc/orderdesc";
	}
	
	/** 产品信息*/
	@RequestMapping("getProducts/{id}")
	@ResponseBody
	public RepProductListDTO getProductList(@PathVariable long id
			,@RequestParam(value = "page", required = true, defaultValue = "1") int pageNum
			,@RequestParam(value = "billType",required = false) String billType
			,@RequestParam(value = "interestType",required = false) String interestType
			,@RequestParam(value = "type",required = false) String type){
		RepProductListDTO dto = new RepProductListDTO();
		
		/** 订单操作记录*/
		Map<String, Object> searchoolParams = new HashMap<String, Object>();
		searchoolParams.put("EQ_order.id", id);
		/** 产品更改产品列表*/
		Map<String, Object> searchProductParams = new HashMap<String, Object>();
		
		searchProductParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
		
		if(!StringUtils.isBlank(interestType)){
			if("INTERESTMODELMONTH".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.INTERESTMODELMONTH);
			}
			if("INTERESTMODELDAY".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.INTERESTMODELDAY);
			}
		}
		if(!StringUtils.isBlank(type)){
			searchProductParams.put("EQ_type.productName", type);
		}
		searchProductParams.put("EQ_status",Product.Status.NORMAL);
		Page<Product> products = productService.getProductList(searchProductParams,"",pageNum,10000000);
		Collection<RepProductDetailDTO> repProductDetails =
			Collections2.transform(products.getContent(), new Function<Product,RepProductDetailDTO>(){
				@Override
				public RepProductDetailDTO apply(Product product) {
					return new RepProductDetailDTO(product);
				}
			});
		dto.setRows(repProductDetails);
		dto.setCurrentPage(1);
		dto.setTotal(1);
		dto.setTotalPage(1);
		return dto;
	}
	
	/** 导出订单*/
	@RequestMapping("export")
	public void export(@RequestParam(value = "status",required = false,defaultValue = "") String status
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,@RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			,@RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
			,HttpServletResponse response){
		List<Orderform> orderforms = orderformService.exportPcOrderforms(status,sortName,sortType,searchName,searchValue);
		String[] titles = {"贷款名称","订单状态","手机号码","下单时间","申请金额","实际金额"};
		ExcelBean excelBean = new ExcelBean("官网订单.xls", "官网订单", titles);
		for(Orderform o :orderforms){
			String data[] = this.getDataList(o);
			excelBean.add(data);
		}
		try {
			ExcelUtil.export(response, excelBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String[] getDataList(Orderform o){
		String productName = o.getProductInfo().getProductName();
		String status = o.getStatus().getDes();
		String telephone = o.getTelephone();
		String createTime = DateUtils.formatNormalDate(o.getCreateTime());
		String money = o.getMoney().divide(new BigDecimal(10000)) +"万元";
		String actualMoney = o.getActualMoney().divide(new BigDecimal(10000)) + "万元";
		String data[]={
			productName,
			status,
			telephone,
			createTime,
			money,
			actualMoney
		};
		return data;
	}
	/**
	 * 订单详情修改
	 * @param status
	 * @param remark
	 * @param orderformId
	 * @param refuse
	 * @param loanActualManey
	 * @param loanTime
	 * @param loanInsterestRate
	 * @param spreadRate
	 * @param brokerageRateNum
	 * @param comtractNum
	 * @return
	 */
	@RequestMapping(value = "changeStatus",method =RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO changeStatus(@RequestParam(value = "status",required = true) String status
			,@RequestParam(value = "orderformId",required = true) long orderformId
			,@RequestParam(value = "refuse",required = false,defaultValue = "") String refuse
			,@RequestParam(value = "loanActualManey",required = false,defaultValue = "") String loanActualManey
			,@RequestParam(value = "loanTime",required = false,defaultValue = "") String loanTime
			,@RequestParam(value = "loanInsterestRate",required = false,defaultValue = "")String loanInsterestRate
			,HttpServletRequest request){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		Orderform orderform = orderformService.findOrderById(orderformId);
		OrderformStatus oldStatus = orderform.getStatus();
		if(orderform == null){
			dto.setCode(2);
			dto.setMes("不存在该订单");
			return dto;
		}
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		if(!StringUtils.isBlank(status)){
			if("UNTOLKWITH".equals(status)&& oldStatus == OrderformStatus.UNCONTACTED){
				orderform.setStatus(OrderformStatus.UNTOLKWITH);
			}
			else if("UNCHECKED".equals(status) && oldStatus == OrderformStatus.UNTOLKWITH){
				orderform.setStatus(OrderformStatus.UNCHECKED);
			}
			else if("UNLOAN".equals(status) && oldStatus == OrderformStatus.UNCHECKED){
				orderform.setStatus(OrderformStatus.UNLOAN);
			}
			else if("CHECKFAIL".equals(status) && oldStatus == OrderformStatus.UNCHECKED){
				orderform.setStatus(OrderformStatus.CHECKFAIL);
				orderform.setInvalidReason(refuse);
			}
			else if("LOANED".equals(status) && oldStatus == OrderformStatus.UNLOAN){
				orderform.setStatus(OrderformStatus.LOANED);
				orderform.setActualMoney(new BigDecimal(loanActualManey).multiply(new BigDecimal(10000)));
				orderform.setLoanTime( Integer.parseInt(loanTime.trim()));
				//更改贷款利率
				if(orderform.getProduct().getInterestType().ordinal() ==1){
					orderform.setActualLoanInsterestRate(new BigDecimal(loanInsterestRate).divide(new BigDecimal(1000)));
				}else{
					orderform.setActualLoanInsterestRate(new BigDecimal(loanInsterestRate).divide(new BigDecimal(100)));
				}
			}
			else if("INVALID".equals(status) && oldStatus == OrderformStatus.UNLOAN){
				orderform.setStatus(OrderformStatus.INVALID);
				orderform.setInvalidReason(refuse);
			}else{
				dto.setCode(0);
				dto.setMes("修改失败");
				return dto;
			}
		}
		if(orderformService.editOrderform(orderform)){
			
			//保存订单操作日志
			OrderOperLog oLog = new OrderOperLog();
			oLog.setCreateTime(new Date());
			oLog.setOpertor(user);
			oLog.setOrder(orderform);
			if(oldStatus == OrderformStatus.UNCONTACTED){
				oLog.setOperContent("修改订单状态：已联系");
			}
			if(oldStatus == OrderformStatus.UNTOLKWITH){
				oLog.setOperContent("修改订单状态：已面谈");
			}
			if(oldStatus == OrderformStatus.UNCHECKED && "UNLOAN".equals(status)){
				oLog.setOperContent("修改订单状态：审核通过");
			}
			if(oldStatus == OrderformStatus.UNCHECKED && "CHECKFAIL".equals(status)){
				oLog.setOperContent("修改订单状态：审核拒绝");
			}
			if(oldStatus == OrderformStatus.UNLOAN && "LOANED".equals(status)){
				oLog.setOperContent("修改订单状态：放款");
			}
			if(oldStatus == OrderformStatus.UNLOAN && "INVALID".equals(status)){
				oLog.setOperContent("修改订单状态：无效订单");
			}
			orderOperLogService.addOrderOperLog(oLog);
			dto.setCode(1);
			dto.setMes("success");
			return dto;
		}
		dto.setCode(0);
		dto.setMes("修改失败");
		return dto;
		
	}
}

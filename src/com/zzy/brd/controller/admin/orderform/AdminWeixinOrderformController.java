package com.zzy.brd.controller.admin.orderform;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.ActivityStarOrder;
import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.BrokerageApply.BrokerageApplyStatus;
import com.zzy.brd.entity.BrokerageApply.SendStatus;
import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.OrderOperLog.OrderformOperatType;
import com.zzy.brd.entity.OrderSerial;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.OrderformRemark;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.Product.InterestType;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.OrderformProductRecord;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinPost.NoticeType;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.ActivityService;
import com.zzy.brd.service.ActivityStarOrderService;
import com.zzy.brd.service.BrokerageApplyService;
import com.zzy.brd.service.OrderOperLogService;
import com.zzy.brd.service.OrderSerialService;
import com.zzy.brd.service.OrderformProductRecordService;
import com.zzy.brd.service.OrderformRemarkService;
import com.zzy.brd.service.OrderformService;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.ProductTypeService;
import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinPostService;
import com.zzy.brd.service.WeixinTemplateService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.excel.ExcelUtil;
import com.zzy.brd.util.excel.ExcelUtil.ExcelBean;
import com.zzy.brd.util.string.StringUtil;
import com.zzy.brd.util.tld.DateUtils;
import com.zzy.brd.util.tld.PriceUtils;
import com.zzy.brd.util.weixin.NewInfoTemplate;

/**
 * 贷款订单-controller
 * @author lzh 2016/10/11
 *
 */
@Controller
@RequestMapping("admin/orderform/weixin")
public class AdminWeixinOrderformController {
	private static final Logger logger = LoggerFactory.getLogger(AdminWeixinOrderformController.class);
	@Autowired
	private OrderformService orderformService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private OrderformRemarkService orderformRemarkService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoBothService userInfoBothService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderformProductRecordService orderformProductRecordService;
	@Autowired
	private OrderOperLogService orderOperLogService;
	@Autowired
	private OrderSerialService orderSerialService;
	@Autowired
	private BrokerageApplyService brokerageApplyService;
	@Autowired
	private ActivityStarOrderService activityStarOrderService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private WeixinTemplateService weixinTemplateService;
	@Autowired
	private WeixinPostService weixinPostService;
	/**
	 * 贷款订单列表
	 * @param pageNum
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String list(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
			,@RequestParam(value = "userType",required = false ,defaultValue = "") String userType
			,@RequestParam(value = "status",required = false,defaultValue = "")String status
			,@RequestParam(value = "billType",required = false,defaultValue ="") String billType
			,@RequestParam(value = "type",required = false ,defaultValue = "") String type
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,@RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			,@RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
			,@RequestParam(value="userId",required=false,defaultValue="-1") long userId
			,@RequestParam(value="getType",required=false)String getType
			,@RequestParam(value="createTime",required=false,defaultValue="") String createTime//下单时间
			,@RequestParam(value="createStarttime",required=false) String createStarttime
			,@RequestParam(value="createEndtime",required=false) String createEndtime
			,HttpServletRequest request, Model model){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(userType)){
			if("USER".equals(userType)){
				searchParams.put("EQ_user.userType",UserType.USER);
			}
			if("MANAGER".equals(userType)){
				searchParams.put("EQ_user.userType", UserType.MANAGER);
			}
			if("SELLER".equals(userType)){
				searchParams.put("EQ_user.userType",UserType.SELLER);
			}
			if("SALESMAN".equals(userType)){
				searchParams.put("EQ_user.userType", UserType.SALESMAN);
			}
			
		}
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
		if(!StringUtils.isBlank(billType)){
			if("SELFHELPLOAN".equals(billType)){
				searchParams.put("EQ_product.type.billType", BillType.SELFHELPLOAN);
			}
			if("EARNDIFFERENCE".equals(billType)){
				searchParams.put("EQ_product.type.billType", BillType.EARNDIFFERENCE);
			}
			if("EARNCOMMISSION".equals(billType)){
				searchParams.put("EQ_product.type.billType", BillType.EARNCOMMISSION);
			}
		}
		if(!StringUtils.isBlank(type)){
			searchParams.put("EQ_product.type.productName", type);
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
		searchParams.put("EQ_source", OrderSource.WECHAT);
		if(userId>-1){
			searchParams.put("EQ_user.id", userId);
			if(getType!=null){
				searchParams.put("EQ_status",Orderform.OrderformStatus.LOANED);
			}
		}
		if(!StringUtil.isNullString(createTime)){ //下单时间
			StringBuilder startBuilder=new StringBuilder(createTime);
			startBuilder.append(" 00:00:00");
			Timestamp startTimeTime = DateUtil.StringToTimestampLong(startBuilder.toString());
			searchParams.put("GTE_createTime", startTimeTime);
			StringBuilder endBuilder=new StringBuilder(createTime);
			endBuilder.append(" 23:59:59");
			Timestamp endTimeTime = DateUtil.StringToTimestampLong(endBuilder.toString());
			searchParams.put("LTE_createTime", endTimeTime);
		}
		//下单起始时间和结束时间
		if(StringUtils.isNotBlank(createStarttime)){
			StringBuilder startBuilder=new StringBuilder(createStarttime);
			startBuilder.append(" 00:00:00");
			Timestamp startTimeTime = DateUtil.StringToTimestampLong(startBuilder.toString());
			searchParams.put("GTE_createTime", startTimeTime);
		}
		if(StringUtils.isNotBlank(createEndtime)){
			StringBuilder endBuilder=new StringBuilder(createEndtime);
			endBuilder.append(" 23:59:59");
			Timestamp endTimeTime = DateUtil.StringToTimestampLong(endBuilder.toString());
			searchParams.put("LTE_createTime", endTimeTime);
		}
		
		
		
		Page<Orderform> orderforms = orderformService.adminOrderformList(searchParams, sortName, sortType, pageNum, Constant.PAGE_SIZE);
		
		model.addAttribute("userId", userId);
		model.addAttribute("getType", getType);
		model.addAttribute("orderforms", orderforms);
		model.addAttribute("userType", userType);
		model.addAttribute("status", status);
		model.addAttribute("billType", billType);
		model.addAttribute("type", type);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("searchName", searchName);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("page", pageNum);
		model.addAttribute("queryStr", request.getQueryString());
		model.addAttribute("totalcount", orderforms.getTotalElements());
		model.addAttribute("createTime", createTime);
		model.addAttribute("createStarttime", createStarttime);
		model.addAttribute("createEndtime", createEndtime);
		return "admin/orderform/weixin/orderlist";
	}
	/**
	 * 订单详情
	 * @param id
	 * @param request
	 * @param pageNumber
	 * @param model
	 * @return
	 */
	@RequestMapping("detail/{id}")
	public String weixinDesc(@PathVariable long id, ServletRequest request, 
			@RequestParam(value = "page", required = true, defaultValue = "1") int oolpageNum
			,@RequestParam(value = "billType",required = false) String billType
			,@RequestParam(value = "interestType",required = false) String interestType
			,@RequestParam(value = "type",required = false) String type
			,Model model){
		Orderform orderform = orderformService.findOrderById(id);
		if(orderform == null){
			return "";
		}
		/** 产品详情*/
		List<ProductType> productTypes = productTypeService.getTypes();
		/** 订单操作记录*/
		Map<String, Object> searchoolParams = new HashMap<String, Object>();
		searchoolParams.put("EQ_order.id", id);
		Page<OrderOperLog> orderOperLogs = orderOperLogService.getOrderOperLogPage(searchoolParams, oolpageNum, Constant.PAGE_SIZE);
		List<OrderformRemark> orderformRemarks = orderformRemarkService.getOrderformRemarkList(id);
		
		model.addAttribute("orderOperLogs", orderOperLogs);
		model.addAttribute("orderformRemarks", orderformRemarks);
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("orderform", orderform);
		model.addAttribute("pageNum", oolpageNum);
		return "admin/orderform/weixin/orderdesc";
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
		if(!StringUtils.isBlank(billType)){
			if("SELFHELPLOAN".equals(billType)){
				searchProductParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
			}
			if("EARNDIFFERENCE".equals(billType)){
				searchProductParams.put("EQ_type.billType", BillType.EARNDIFFERENCE);
			}
			if("EARNCOMMISSION".equals(billType)){
				searchProductParams.put("EQ_type.billType", BillType.EARNCOMMISSION);
			}
		}
		if(!StringUtils.isBlank(interestType)){
			if("INTERESTMODELMONTH".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.INTERESTMODELMONTH);
			}
			if("INTERESTMODELDAY".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.INTERESTMODELDAY);
			}
			if("HANDFEEMODEL".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.HANDFEEMODEL);
			}
			if("SPECIALMODEL".equals(interestType)){
				searchProductParams.put("EQ_interestType",InterestType.SPECIALMODEL);
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
	public void export(@RequestParam(value = "userType",required = false ,defaultValue = "") String userType
			,@RequestParam(value = "status",required = false,defaultValue = "")String status
			,@RequestParam(value = "billType",required = false,defaultValue ="") String billType
			,@RequestParam(value = "type",required = false ,defaultValue = "") String type
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,@RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			,@RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
			,@RequestParam(value="userId",required=false,defaultValue="-1") long userId
			,@RequestParam(value="getType",required=false)String getType
			,@RequestParam(value="createTime",required=false,defaultValue="") String createTime//下单时间
			,@RequestParam(value="createStarttime",required=false) String createStarttime
			,@RequestParam(value="createEndtime",required=false) String createEndtime
			,HttpServletResponse response){
		List<Orderform> orderforms = orderformService.exportOrderforms(userType,status,billType,type,sortName,sortType,searchName,searchValue, userId,getType,createTime,createStarttime,createEndtime);
		String[] titles = {"贷款名称","提单类型","订单状态","下单人","下单时间","申请金额","实际金额"};
		ExcelBean excelBean = new ExcelBean("贷款订单.xls","贷款订单",titles);
		for(Orderform o :orderforms){
			String[] data = this.getDataList(o);
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
		String billType = o.getProduct().getType().getBillType().getDes();
		String username = o.getUser().getUsername();
		String status = o.getStatus().getDes();
		String createTime = DateUtils.formatNormalDate(o.getCreateTime());
		String money = o.getMoney().divide(new BigDecimal(10000)) +"万元";
		String actualMoney = o.getActualMoney().divide(new BigDecimal(10000)) + "万元";
		String data[]={
			productName,
			billType,
			status,
			username,
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
			,@RequestParam(value = "loanInsterestRate",required = true,defaultValue = "")String loanInsterestRate
			,@RequestParam(value = "spreadRate",required = false,defaultValue ="") String spreadRate
			,@RequestParam(value = "percentageRate",required = false,defaultValue ="") String percentageRate
			,@RequestParam(value = "brokerageRateNum",required = false,defaultValue = "") String brokerageRateNum
			,HttpServletRequest request){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		Orderform orderform = orderformService.findOrderById(orderformId);
		
		if(orderform == null){
			dto.setCode(2);
			dto.setMes("不存在该订单");
			return dto;
		}
		User wxUser = orderform.getUser();
		OrderformStatus oldStatus = orderform.getStatus();
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
				System.out.println("refuse:"+refuse);
				orderform.setInvalidReason(refuse);
			}
			else if("LOANED".equals(status) && oldStatus == OrderformStatus.UNLOAN){
				
				orderform.setStatus(OrderformStatus.LOANED);
				orderform.setActualMoney(new BigDecimal(loanActualManey).multiply(new BigDecimal(10000)));
				orderform.setLoanTime( Integer.parseInt(loanTime.trim()));
				if(orderform.getProduct().getType().getBillType().ordinal() == 1 && (orderform.getProduct().getInterestType().ordinal() ==0||orderform.getProduct().getInterestType().ordinal()==2)){
					orderform.setSpreadRate(new BigDecimal(spreadRate).divide(new BigDecimal(100)).setScale(5));
				}
				if(orderform.getProduct().getType().getBillType().ordinal() == 1 && orderform.getProduct().getInterestType().ordinal() ==1){
					orderform.setSpreadRate(new BigDecimal(spreadRate).divide(new BigDecimal(1000)).setScale(5));
				}
				//赚提成比例
				if(orderform.getProduct().getType().getBillType().ordinal() == 2 ){
					orderform.setPercentageRate(new BigDecimal(percentageRate).divide(new BigDecimal(100)).setScale(5));
				}
				
				//更改实际贷款利率
				if(orderform.getProduct().getInterestType().ordinal() ==1){
					orderform.setActualLoanInsterestRate(new BigDecimal(loanInsterestRate).divide(new BigDecimal(1000)));
				}else{
					orderform.setActualLoanInsterestRate(new BigDecimal(loanInsterestRate).divide(new BigDecimal(100)));
				}
				
				if(orderform.getProduct().getType().getBillType().ordinal() != 0){
					//预计佣金
					BigDecimal brokerageRate = new BigDecimal(brokerageRateNum);
					/*//计算预计佣金总和
					BigDecimal fatherRate = brokerageRate.multiply(orderform.getProductInfo().getFatherRate());
					BigDecimal bussinessRate = brokerageRate.multiply(orderform.getProductInfo().getSellerRate());
					BigDecimal salesmanRate = brokerageRate.multiply(orderform.getProductInfo().getSalesmanRate());*/
					orderform.setBrokerageRateNum(brokerageRate);
					/*orderform.setBrokerageRateTotal(brokerageRate.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
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
			if(orderform.getStatus() == OrderformStatus.LOANED){
				//订单完成后添加相应的添加本人的订单数订单总额、师父的订单数订单总额，师公订单数订单总额
				UserInfoBoth userInfoBoth = orderform.getUser().getUserInfoBoth();
				userInfoBoth.setOrderSuccessSum(userInfoBoth.getOrderSuccessSum() + 1);
				userInfoBoth.setOrderMoney(userInfoBoth.getOrderMoney().add(orderform.getActualMoney()));
				userInfoBothService.editUserInfoBoth(userInfoBoth);
				//师父订单数和订单总额
				if(orderform.getOldParent()!= null){
					UserInfoBoth parentUser = orderform.getOldParent().getUserInfoBoth();
					parentUser.setSonsOrderSum(parentUser.getSonsOrderSum()+1);
					parentUser.setSonsOrderMoney(parentUser.getSonsOrderMoney().add(orderform.getActualMoney()));
					userInfoBothService.editUserInfoBoth(parentUser);
					
					//师公订单数和订单总额
					if(orderform.getOldParent().getUserInfoBoth().getParent() != null){
						UserInfoBoth grandParentUser = orderform.getOldParent().getUserInfoBoth().getParent().getUserInfoBoth();
						grandParentUser.setGsonsOrderSum(grandParentUser.getGsonsOrderSum()+1);
						grandParentUser.setGsonsOrderMoney(grandParentUser.getGsonsOrderMoney().add(orderform.getActualMoney()));
						userInfoBothService.editUserInfoBoth(grandParentUser);
						//师公公订单数和订单总额
						if(grandParentUser.getParent() !=null){
							UserInfoBoth ggrandParentUser = grandParentUser.getParent().getUserInfoBoth();
							ggrandParentUser.setGgsonsOrderSum(ggrandParentUser.getGsonsOrderSum()+1);
							ggrandParentUser.setGgsonsOrderMoney(ggrandParentUser.getGgsonsOrderMoney().add(orderform.getActualMoney()));
							userInfoBothService.editUserInfoBoth(ggrandParentUser);
						}
					}
				}
				
			}
			
			//订单完成后生成佣金订单
			if(oldStatus == OrderformStatus.UNLOAN && "LOANED".equals(status)){
				BrokerageApply brokerageApply = new BrokerageApply();
				//佣金订单号
				String name = "佣金订单";
				OrderSerial orderSerial = orderSerialService.findOrderSerialByName(name);
				if(orderSerial == null){
					OrderSerial newOrderSerial = new OrderSerial();
					newOrderSerial.setName(name);
					newOrderSerial.setSerial(1);
					orderSerialService.addOrEditOrderSerial(newOrderSerial);
					String brokerageNo = "B"+StringUtil.toStringZeroByInteger(1, 6);
					brokerageApply.setBrokerageNo(brokerageNo);
				}else{
					String brokerageNo = "B"+ StringUtil.toStringZeroByInteger(orderSerial.getSerial()+1, 6);
					orderSerial.setSerial(orderSerial.getSerial()+1);
					orderSerialService.addOrEditOrderSerial(orderSerial);
					brokerageApply.setBrokerageNo(brokerageNo);
					
				}
				brokerageApply.setOrderform(orderform);
				brokerageApply.setProduct(orderform.getProduct());
				brokerageApply.setProductInfo(orderform.getProductInfo());
				brokerageApply.setCreateTime(new Date());
				
				if(orderform.getProduct().getInterestType().ordinal() ==1){
					brokerageApply.setInterest(new BigDecimal(loanInsterestRate).divide(new BigDecimal(1000)).setScale(5));
				}else{
					brokerageApply.setInterest(new BigDecimal(loanInsterestRate).divide(new BigDecimal(100)).setScale(5));
				}
				
				brokerageApply.setMoney(orderform.getActualMoney());
				brokerageApply.setStatus(BrokerageApplyStatus.UNENTERING);
				brokerageApply.setUser(orderform.getUser());
				brokerageApply.setSendStatus(SendStatus.SINGAL);
				brokerageApply.setHasSendTimes(1);
				brokerageApply.setSendTimes(0);
				//计算佣金-根据产品类型
				//赚差价-按月
				if(orderform.getProduct().getType().getBillType() == BillType.EARNDIFFERENCE && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELMONTH){
					//佣金=贷款金额 * 加息息差 *贷款月份数
					BigDecimal jMoney = orderform.getActualMoney();
					BigDecimal jLoanTime = new BigDecimal(orderform.getLoanTime());
					BigDecimal jSpreadRate = orderform.getSpreadRate();
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jSpreadRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					BigDecimal fatherRate = orderform.getProductInfo().getFatherRate();
					BigDecimal salesmanRate = orderform.getProductInfo().getSalesmanRate();
					BigDecimal businessRate = orderform.getProductInfo().getSellerRate();
					
					BigDecimal fatherBrokerage = brokeragesNum.multiply(fatherRate).setScale(2,BigDecimal.ROUND_HALF_UP);
					BigDecimal salesmanBrokerage = brokeragesNum.multiply(salesmanRate).setScale(2,BigDecimal.ROUND_HALF_UP);
					BigDecimal businessBrokerage = brokeragesNum.multiply(businessRate).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					brokerageApply.setSelfBrokerage(brokeragesNum);
					brokerageApply.setFatherBrokerage(fatherBrokerage);
					brokerageApply.setBusinessBrokerage(businessBrokerage);
					brokerageApply.setSalesmanBrokerage(salesmanBrokerage);
					
				}
				//赚差价-按日
				if(orderform.getProduct().getType().getBillType() == BillType.EARNDIFFERENCE && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELDAY){
					//佣金 = 贷款金额* 加价息差* 贷款天数 （如果日期大于15按15算）
					BigDecimal jMoney = orderform.getActualMoney();
					BigDecimal jLoanTime;
					if(orderform.getLoanTime()>15){
						jLoanTime = new BigDecimal(15);
					}else{
						jLoanTime = new BigDecimal(orderform.getLoanTime());
					}
					BigDecimal jSpreadRate = orderform.getSpreadRate();
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jSpreadRate)).setScale(2);
					
					BigDecimal fatherRate = orderform.getProductInfo().getFatherRate();
					BigDecimal salesmanRate = orderform.getProductInfo().getSalesmanRate();
					BigDecimal businessRate = orderform.getProductInfo().getSellerRate();
					
					BigDecimal fatherBrokerage = (brokeragesNum.multiply(fatherRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					BigDecimal salesmanBrokerage = (brokeragesNum.multiply(salesmanRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					BigDecimal businessBrokerage = (brokeragesNum.multiply(businessRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					brokerageApply.setSelfBrokerage(brokeragesNum);
					brokerageApply.setFatherBrokerage(fatherBrokerage);
					brokerageApply.setBusinessBrokerage(businessBrokerage);
					brokerageApply.setSalesmanBrokerage(salesmanBrokerage);
				}
				//赚差价-收益金
				if(orderform.getProduct().getType().getBillType() == BillType.EARNDIFFERENCE && orderform.getProduct().getInterestType() == InterestType.HANDFEEMODEL){
					//佣金=贷款金额 * 约定费用价差
					BigDecimal jMoney = orderform.getActualMoney();
					BigDecimal jSpreadRate = orderform.getSpreadRate();
					BigDecimal brokeragesNum = (jMoney.multiply(jSpreadRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					//师父、商家、业务员佣金
					BigDecimal jSpread = orderform.getProductInfo().getSpread();
					BigDecimal brokeragesToFather = (jMoney.multiply(jSpread)).setScale(2,BigDecimal.ROUND_HALF_UP);
					BigDecimal fatherRate = orderform.getProductInfo().getFatherRate();
					BigDecimal salesmanRate = orderform.getProductInfo().getSalesmanRate();
					BigDecimal businessRate = orderform.getProductInfo().getSellerRate();
					
					BigDecimal fatherBrokerage = (brokeragesToFather.multiply(fatherRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal salesmanBrokerage = (brokeragesToFather.multiply(salesmanRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal businessBrokerage = (brokeragesToFather.multiply(businessRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					
					brokerageApply.setSelfBrokerage(brokeragesNum);
					brokerageApply.setFatherBrokerage(fatherBrokerage);
					brokerageApply.setBusinessBrokerage(businessBrokerage);
					brokerageApply.setSalesmanBrokerage(salesmanBrokerage);
					
					
				}
				//赚提成/自助贷-按月
				if((orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION ||orderform.getProduct().getType().getBillType() == BillType.SELFHELPLOAN) && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELMONTH){
					//佣金 = 贷款金额 * 利息 * 贷款期限 * 提成比例
					BigDecimal jMoney = orderform.getActualMoney();
					BigDecimal jLoanTime = new BigDecimal(orderform.getLoanTime());
					BigDecimal jExpense = orderform.getProductInfo().getExpense();
					BigDecimal jPercentageRate = new BigDecimal(1);
					if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION ){
						jPercentageRate= orderform.getPercentageRate();
					}
					
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					BigDecimal fatherRate = orderform.getProductInfo().getFatherRate();
					BigDecimal salesmanRate = orderform.getProductInfo().getSalesmanRate();
					BigDecimal businessRate = orderform.getProductInfo().getSellerRate();
					
					BigDecimal fatherBrokerage = (brokeragesNum.multiply(fatherRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal salesmanBrokerage = (brokeragesNum.multiply(salesmanRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal businessBrokerage = (brokeragesNum.multiply(businessRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					if(orderform.getProduct().getType().getBillType() == BillType.SELFHELPLOAN){
						brokerageApply.setSelfBrokerage(new BigDecimal(0));
					}else{
						brokerageApply.setSelfBrokerage(brokeragesNum);
					}
					
					brokerageApply.setFatherBrokerage(fatherBrokerage);
					brokerageApply.setBusinessBrokerage(businessBrokerage);
					brokerageApply.setSalesmanBrokerage(salesmanBrokerage);
					
				}
				//赚提成/自助贷-按日
				if((orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION||orderform.getProduct().getType().getBillType() == BillType.SELFHELPLOAN) && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELDAY){
					//佣金 = 贷款金额*  贷款天数 （如果日期大于15按15算）* 日息*提成比例
					BigDecimal jMoney = orderform.getActualMoney();
					BigDecimal jLoanTime ;
					if(orderform.getLoanTime()>15){
						jLoanTime = new BigDecimal(15);
					}else{
						jLoanTime = new BigDecimal(orderform.getLoanTime());
					}
					BigDecimal jExpense = orderform.getProductInfo().getExpense();
					BigDecimal jPercentageRate = new BigDecimal(1);
					if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION ){
						jPercentageRate= orderform.getPercentageRate();
					}
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					BigDecimal fatherRate = orderform.getProductInfo().getFatherRate();
					BigDecimal salesmanRate = orderform.getProductInfo().getSalesmanRate();
					BigDecimal businessRate = orderform.getProductInfo().getSellerRate();
					
					BigDecimal fatherBrokerage = (brokeragesNum.multiply(fatherRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal salesmanBrokerage = (brokeragesNum.multiply(salesmanRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal businessBrokerage = (brokeragesNum.multiply(businessRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					if(orderform.getProduct().getType().getBillType() == BillType.SELFHELPLOAN){
						brokerageApply.setSelfBrokerage(new BigDecimal(0));
					}else{
						brokerageApply.setSelfBrokerage(brokeragesNum);
					}
					brokerageApply.setFatherBrokerage(fatherBrokerage);
					brokerageApply.setBusinessBrokerage(businessBrokerage);
					brokerageApply.setSalesmanBrokerage(salesmanBrokerage);
				}
				//赚提成/自助贷-收益金
				if((orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION ||orderform.getProduct().getType().getBillType() == BillType.SELFHELPLOAN)&& orderform.getProduct().getInterestType() == InterestType.HANDFEEMODEL){
					//佣金 = 贷款金额*手续费用 * 提成比例
					BigDecimal jMoney = orderform.getActualMoney();
					BigDecimal jExpense = orderform.getProductInfo().getExpense();
					
					BigDecimal jPercentageRate = new BigDecimal(1);
					if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION ){
						jPercentageRate= orderform.getPercentageRate();
					}
					BigDecimal brokeragesNum = (jMoney.multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					BigDecimal fatherRate = orderform.getProductInfo().getFatherRate();
					BigDecimal salesmanRate = orderform.getProductInfo().getSalesmanRate();
					BigDecimal businessRate = orderform.getProductInfo().getSellerRate();
					
					BigDecimal fatherBrokerage = brokeragesNum.multiply(fatherRate).setScale(2,BigDecimal.ROUND_HALF_UP);
					BigDecimal salesmanBrokerage = (brokeragesNum.multiply(salesmanRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal businessBrokerage = (brokeragesNum.multiply(businessRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					
					if(orderform.getProduct().getType().getBillType() == BillType.SELFHELPLOAN){
						brokerageApply.setSelfBrokerage(new BigDecimal(0));
					}else{
						brokerageApply.setSelfBrokerage(brokeragesNum);
					}
					brokerageApply.setFatherBrokerage(fatherBrokerage);
					brokerageApply.setBusinessBrokerage(businessBrokerage);
					brokerageApply.setSalesmanBrokerage(salesmanBrokerage);
				}
				//赚提成-特殊模式
				if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION && orderform.getProduct().getInterestType() == InterestType.SPECIALMODEL){
					//佣金 = 贷款金额*收益金 * 佣金(贷款月份只能是6，12，24，36，对应的收益金和佣金进行计算)
					BigDecimal jMoney = orderform.getActualMoney();
					BigDecimal jExpense = new BigDecimal(0);
					BigDecimal jPercentageRate = orderform.getPercentageRate();
					if(orderform.getLoanTime()==6){
						jExpense = orderform.getProductInfo().getAlgoParamA();
					}
					if(orderform.getLoanTime()==12){
						jExpense = orderform.getProductInfo().getAlgoParamC();
					}
					if(orderform.getLoanTime()==24){
						jExpense = orderform.getProductInfo().getAlgoParamE();
					}
					if(orderform.getLoanTime()==36){
						jExpense = orderform.getProductInfo().getAlgoParamG();
					}
					BigDecimal brokeragesNum = (jMoney.multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					BigDecimal fatherRate = orderform.getProductInfo().getFatherRate();
					BigDecimal salesmanRate = orderform.getProductInfo().getSalesmanRate();
					BigDecimal businessRate = orderform.getProductInfo().getSellerRate();
					
					BigDecimal fatherBrokerage = (brokeragesNum.multiply(fatherRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal salesmanBrokerage = (brokeragesNum.multiply(salesmanRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					BigDecimal businessBrokerage = (brokeragesNum.multiply(businessRate).setScale(2,BigDecimal.ROUND_HALF_UP));
					
					brokerageApply.setSelfBrokerage(brokeragesNum);
					brokerageApply.setFatherBrokerage(fatherBrokerage);
					brokerageApply.setBusinessBrokerage(businessBrokerage);
					brokerageApply.setSalesmanBrokerage(salesmanBrokerage);
				}
				/** 保存佣金订单*/
				brokerageApplyService.addBrokerageApply(brokerageApply);
				
			}
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
				//已经面谈过变成待审核
				oLog.setOperatType(OrderformOperatType.UNCHECKED);				
			}
			if(oldStatus == OrderformStatus.UNCHECKED && "UNLOAN".equals(status)){
				oLog.setOperContent("修改订单状态：审核通过");
				oLog.setOperatType(OrderformOperatType.UNLOAN);	
				
			
				if(wxUser!=null){
					WeixinUser weixinUser = wxUser.getWeixinUser();
					if(weixinUser!=null){
						WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.AUDITSUCCESS);
						if(weixinPost!=null){
							WeixinPost.State state = weixinPost.getState();
							if(state!=null){
								if(state==WeixinPost.State.ON){
									NewInfoTemplate newInfoTemplate = weixinTemplateService.getAuditSuccess(orderform, request);
									int res = weixinTemplateService.sendTemplateMessage(newInfoTemplate);
									if(res!=0){
										dto.setCode(0);
										dto.setMes("发送模板消息失败");
									}
								}
							}
						}
					}
				}

			}
			if(oldStatus == OrderformStatus.UNCHECKED && "CHECKFAIL".equals(status)){
				oLog.setOperContent("修改订单状态：审核拒绝");
				oLog.setOperatType(OrderformOperatType.CHECKFAIL);	
				
				if(wxUser!=null){
					WeixinUser weixinUser = wxUser.getWeixinUser();
					if(weixinUser!=null){
						WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.AUDITFAILURE);
						if(weixinPost!=null){
							WeixinPost.State state = weixinPost.getState();
							if(state!=null){
								if(state==WeixinPost.State.ON){
									NewInfoTemplate newInfoTemplate = weixinTemplateService.getAuditFailure(orderform,request);
									int res = weixinTemplateService.sendTemplateMessage(newInfoTemplate);
									if(res!=0){
										dto.setCode(0);
										dto.setMes("发送模板消息失败");
									}
									}
								}
							}
						}
					}
			}
			if(oldStatus == OrderformStatus.UNLOAN && "LOANED".equals(status)){
				oLog.setOperContent("修改订单状态：放款");
				oLog.setOperatType(OrderformOperatType.LOANED);	
				if(!editActivityStarOrder(orderform)){
					dto.setCode(0);
					dto.setMes("保存星级订单失败");
				}
				
				if(wxUser!=null){
					WeixinUser weixinUser = wxUser.getWeixinUser();
					if(weixinUser!=null){
						WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.LOADSUCCESS);
						if(weixinPost!=null){
							WeixinPost.State state = weixinPost.getState();
							if(state!=null){
								if(state==WeixinPost.State.ON){
									NewInfoTemplate newInfoTemplate = weixinTemplateService.getLoadSuccess(orderform,request);
									int res = weixinTemplateService.sendTemplateMessage(newInfoTemplate);
									if(res!=0){
										dto.setCode(0);
										dto.setMes("发送模板消息失败");
										}
								     }
								}
							}
						}
					}
			}
			if(oldStatus == OrderformStatus.UNLOAN && "INVALID".equals(status)){
				oLog.setOperContent("修改订单状态：无效订单");
				oLog.setOperatType(OrderformOperatType.CHECKFAIL);	
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
	 /**
	  * 保存星级订单
	  * @param user
	  * @return
	  */
	public boolean editActivityStarOrder(Orderform orderform){
		int starOrdernum = 5;//星级订单不计时间订单数 初始为5
		int maxNum = 3;//不超过一年 初始为3
		int activityNum = 0;
		Date nowDate = DateUtil.getNowDate();//当前时间
		String endTimeStr = DateUtil.getNextYearTime(nowDate);
		Date endTime = DateUtil.StringToTimestampLong(endTimeStr);//明年的今天
		User user = null;
		boolean flag = false;
		if(orderform==null){
			return flag;
		}else{
			user = orderform.getUser();
		}
		if(user==null){
			return flag;
		}else{
			
			ActivityStarOrder activityStarOrder = activityStarOrderService.getActivityStarOrder(user);
			if(activityStarOrder==null){
				ActivityStarOrder activityStarOrder1 = new ActivityStarOrder();
				activityStarOrder1.setEndTime(endTime);
				activityStarOrder1.setStartTime(nowDate);
				activityStarOrder1.setOrderSum(1);
				activityStarOrder1.setUser(user);
				activityStarOrder1.setStatus(ActivityStarOrder.Status.UNCOMPLETE);
				return flag = activityStarOrderService.editActivityStarOrder(activityStarOrder1);
			}else{
				Date end = activityStarOrder.getEndTime();
				int orderSum = activityStarOrder.getOrderSum();
				Activity activity = activityService.getStartOrderByActivityType();
				if(activity!=null){
					 starOrdernum = activity.getStarOrdernum();//星级订单不计时间订单数 初始为5
					 maxNum = activity.getMaxNum();//不超过一年 初始为3
				}
				Boolean isSxceedYear = end.after(nowDate);
				if(isSxceedYear){
					activityNum = maxNum; //如果进行活动时间不满一年jsp页面星星总数是3
				}else{
					activityNum = starOrdernum;//如果进行活动时间不满一年jsp页面星星总数是5
				}
				activityStarOrder.setOrderSum(orderSum+1);
				orderSum = activityStarOrder.getOrderSum();
				if(orderSum>=activityNum){
					activityStarOrder.setStatus(ActivityStarOrder.Status.COMPLETE);
				}
				return flag =  activityStarOrderService.editActivityStarOrder(activityStarOrder);
			}
		}
	}
	
	/**
	 * 判断某个时间和某个时间的下一年的同一时间的先后关系
	 * @param d1
	 * @param time
	 * @return Boolean
	 */
	public Boolean isBeyondNextYear(Date d1,String time){
		String ndstr = DateUtil.getNextYearTime(d1);//某一时间的下一年同一时间
		Date dt = DateUtil.getDateByLongFormat(time);
		Date dn = DateUtil.getDateByLongFormat(ndstr);//某一时间的下一年同一时间
		Boolean t = dn.after(dt);//dn比dt迟返回true 就是星级订单任务是3 dn比dt早 就是满一年了，星级订单就是5个。
		return t;
	}
	/**
	 * 合同保存
	 * @param comtractNum
	 * @param orderformId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveComtractNum",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO saveComtractNum(@RequestParam(value = "comtractNum",required = true) String comtractNum
			,@RequestParam(value = "orderformId",required = true) long orderformId
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		Orderform orderform = orderformService.findOrderById(orderformId);
		orderform.setComtractNum(comtractNum);
		if(orderformService.editOrderform(orderform)){
			OrderOperLog ooL = new OrderOperLog();
			ooL.setCreateTime(new Date());
			ooL.setOperContent("录入合同编号："+comtractNum);
			ooL.setOrder(orderform);
			ooL.setOpertor(user);
			orderOperLogService.addOrderOperLog(ooL);
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("合同编号保存失败");
		return rep;
	}
	
	/**
	 * 保存备注
	 * @param remark
	 * @param orderformId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveRemark",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO saveRemark(@RequestParam(value = "remark",required = true) String remark
			,@RequestParam(value = "orderformId",required = true) long orderformId
			,HttpServletRequest request){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		Orderform orderform = orderformService.findOrderById(orderformId);
		OrderformRemark oRemark = new OrderformRemark();
		oRemark.setUser(user);
		oRemark.setCreateTime(new Date());
		oRemark.setOrderform(orderform);
		oRemark.setRemark(remark);
		if(orderformRemarkService.addOrderformRemark(oRemark)){
			OrderOperLog ooL = new OrderOperLog();
			ooL.setCreateTime(new Date());
			ooL.setOperContent("添加备注："+remark);
			ooL.setOrder(orderform);
			ooL.setOpertor(user);
			orderOperLogService.addOrderOperLog(ooL);
			dto.setCode(1);
			dto.setMes("success");
			return dto;
		}
		dto.setCode(0);
		dto.setMes("备注保存失败");
		return dto;
	}
	
	/**
	 * 更改产品
	 * @param orderformId
	 * @param productId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "changeProduct",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO savechangeProduct(@RequestParam(value = "orderformId",required = true) long orderformId
			,@RequestParam(value = "productId",required = true) long productId
			,HttpServletRequest request){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		Orderform orderform = orderformService.findOrderById(orderformId);
		Product oldProduct = orderform.getProduct();
		Product product = productService.findById(productId);
		if(product == null){
			dto.setCode(0);
			dto.setMes("产品不存在");
			return dto;
		}
		/** 保存修改订单*/
		OrderformProductRecord opr = new OrderformProductRecord();
		opr.setBeforeProduct(orderform.getProduct());
		opr.setAfterProduct(product);
		opr.setSpreadRate(orderform.getSpreadRate());
		opr.setCreateTime(new Date());
		opr.setLoanInterestRate(orderform.getLoanInsterestRate());
		opr.setMoney(orderform.getMoney());
		opr.setMonth(orderform.getLoanTime());
		opr.setOrderform(orderform);
		if(orderformProductRecordService.addOPR(opr)){
			orderform.setProduct(product);
			orderform.setProductInfo(product.getInfo());
			if(orderformService.editOrderform(orderform)){
				//修改预计佣金
				//计算佣金-根据产品类型
				//赚差价-按月
				if(orderform.getProduct().getType().getBillType() == BillType.EARNDIFFERENCE && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELMONTH){
					//佣金=贷款金额 * 加息息差 *贷款月份数
					BigDecimal jMoney = orderform.getMoney();
					BigDecimal jLoanTime = new BigDecimal(orderform.getLoanTime());
					BigDecimal jSpreadRate = new BigDecimal(0);
					if(orderform.getSpreadRate() == null){
						jSpreadRate = orderform.getProductInfo().getSpread();
					}else{
						jSpreadRate = orderform.getSpreadRate();
					}
					
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jSpreadRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					orderform.setBrokerageRateNum(brokeragesNum);
					//设置预计佣金总和
					/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
					BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
					BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
					orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
				
					orderformService.editOrderform(orderform);
				}
				//赚差价-按日
				if(orderform.getProduct().getType().getBillType() == BillType.EARNDIFFERENCE && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELDAY){
					//佣金 = 贷款金额* 加价息差* 贷款天数 （如果日期大于15按15算）
					BigDecimal jMoney = orderform.getMoney();
					BigDecimal jLoanTime;
					if(orderform.getLoanTime()>15){
						jLoanTime = new BigDecimal(15);
					}else{
						jLoanTime = new BigDecimal(orderform.getLoanTime());
					}
					BigDecimal jSpreadRate = new BigDecimal(0);
					//判断旧产品是否是赚差价-按月
					if(orderform.getSpreadRate() == null){
						jSpreadRate = orderform.getProductInfo().getSpread();
					}else{
						if(oldProduct.getType().getBillType().ordinal() == 2 && oldProduct.getInterestType().ordinal() != 1){
							jSpreadRate = orderform.getSpreadRate().divide(new BigDecimal(10));
						}else{
							jSpreadRate = orderform.getSpreadRate();
						}
					}
					if(oldProduct.getType().getBillType() == BillType.EARNDIFFERENCE && oldProduct.getInterestType().ordinal() != 1){
						
					}
					if(oldProduct.getType().getBillType() == BillType.EARNDIFFERENCE && oldProduct.getInterestType().ordinal() == 1){
						jSpreadRate = orderform.getSpreadRate();
					}
					
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jSpreadRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					orderform.setBrokerageRateNum(brokeragesNum);
					
					//设置预计佣金总和
					/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
					BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
					BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
					orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
					orderformService.editOrderform(orderform);
				}
				//赚差价-收益金
				if(orderform.getProduct().getType().getBillType() == BillType.EARNDIFFERENCE && orderform.getProduct().getInterestType() == InterestType.HANDFEEMODEL){
					//佣金=贷款金额 * 约定费用价差
					BigDecimal jMoney = orderform.getMoney();
					/*BigDecimal jSpreadRate = orderform.getSpreadRate();*/
					BigDecimal jSpreadRate = new BigDecimal(0);
					if(orderform.getSpreadRate() == null){
						jSpreadRate = orderform.getProductInfo().getSpread();
					}else{
						jSpreadRate = orderform.getSpreadRate();
					}
					
					BigDecimal brokeragesNum = (jMoney.multiply(jSpreadRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					orderform.setBrokerageRateNum(brokeragesNum);
					
					//设置预计佣金总和
					/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
					BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
					BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
					orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
					orderformService.editOrderform(orderform);
					
					
				}
				//赚提成-按月
				if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELMONTH){
					//佣金 = 贷款金额 * 利息 * 贷款期限 * 提成比例
					BigDecimal jMoney = orderform.getMoney();
					BigDecimal jLoanTime = new BigDecimal(orderform.getLoanTime());
					BigDecimal jExpense = orderform.getProductInfo().getExpense();
					BigDecimal jPercentageRate = new BigDecimal(0);
					if(orderform.getPercentageRate() == null){
						jPercentageRate = orderform.getProductInfo().getPercentageRate();
					}else{
						jPercentageRate = orderform.getPercentageRate();
					}
					
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					orderform.setBrokerageRateNum(brokeragesNum);
					
					//设置预计佣金总和
					/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
					BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
					BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
					orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
					orderformService.editOrderform(orderform);
					
				}
				//赚提成-按日
				if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION && orderform.getProduct().getInterestType() == InterestType.INTERESTMODELDAY){
					//佣金 = 贷款金额*  贷款天数 （如果日期大于15按15算）* 日息*提成比例
					BigDecimal jMoney = orderform.getMoney();
					BigDecimal jLoanTime ;
					if(orderform.getLoanTime()>15){
						jLoanTime = new BigDecimal(15);
					}else{
						jLoanTime = new BigDecimal(orderform.getLoanTime());
					}
					BigDecimal jExpense = orderform.getProductInfo().getExpense();
					
					BigDecimal jPercentageRate = new BigDecimal(0);
					//旧产品为赚提成
					if(orderform.getPercentageRate() == null){
						jPercentageRate = orderform.getProductInfo().getPercentageRate();
					}else{
						if(oldProduct.getType().getBillType().ordinal() == 2 && oldProduct.getInterestType().ordinal() !=1){
							jPercentageRate = orderform.getPercentageRate().divide(new BigDecimal(10));
						}else{
							jPercentageRate = orderform.getPercentageRate();
						}
					}
					/*BigDecimal jPercentageRate = orderform.getProductInfo().getPercentageRate();*/
					BigDecimal brokeragesNum = (jMoney.multiply(jLoanTime).multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					orderform.setBrokerageRateNum(brokeragesNum);
					
					//设置预计佣金总和
					/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
					BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
					BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
					orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
					orderformService.editOrderform(orderform);
				}
				//赚提成-收益金
				if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION && orderform.getProduct().getInterestType() == InterestType.HANDFEEMODEL){
					//佣金 = 贷款金额*手续费用 * 提成比例
					BigDecimal jMoney = orderform.getMoney();
					BigDecimal jExpense = orderform.getProductInfo().getExpense();
					//旧产品为赚提成
					BigDecimal jPercentageRate = new BigDecimal(0);
					if(orderform.getPercentageRate() == null){
						jPercentageRate = orderform.getProductInfo().getPercentageRate();
					}else{
						jPercentageRate = orderform.getPercentageRate();
					}
					
					BigDecimal brokeragesNum = (jMoney.multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
					
					orderform.setBrokerageRateNum(brokeragesNum);
					
					//设置预计佣金总和
					/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
					BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
					BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
					orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
					orderformService.editOrderform(orderform);
					
				}
				//赚提成-特殊模式
				if(orderform.getProduct().getType().getBillType() == BillType.EARNCOMMISSION && orderform.getProduct().getInterestType() == InterestType.SPECIALMODEL){
					//佣金 = 贷款金额*收益金 * 佣金(贷款月份只能是6，12，24，36，对应的收益金和佣金进行计算)
					//判断旧产品和新产品是否都是特殊模式
					if(oldProduct.getInterestType().ordinal() == product.getInterestType().ordinal()){
					
						BigDecimal jMoney = orderform.getMoney();
						BigDecimal jExpense = new BigDecimal(0);
						BigDecimal jPercentageRate = orderform.getPercentageRate();
						if(orderform.getLoanTime()==6){
							jExpense = orderform.getProductInfo().getAlgoParamA();
						}
						if(orderform.getLoanTime()==12){
							jExpense = orderform.getProductInfo().getAlgoParamC();
						}
						if(orderform.getLoanTime()==24){
							jExpense = orderform.getProductInfo().getAlgoParamE();
						}
						if(orderform.getLoanTime()==36){
							jExpense = orderform.getProductInfo().getAlgoParamG();
						}
						BigDecimal brokeragesNum = (jMoney.multiply(jExpense).multiply(jPercentageRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
						
						orderform.setBrokerageRateNum(brokeragesNum);
						
						//设置预计佣金总和
						/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
						BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
						BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
						orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
						orderformService.editOrderform(orderform);
						
					}else{
						//不一样的话申请时间要重新设置为6个月
						BigDecimal jMoney = orderform.getMoney();
						BigDecimal jExpense = new BigDecimal(0);
						BigDecimal jPercentageRate = new BigDecimal(0);
						orderform.setLoanTime(6);
						jExpense = orderform.getProductInfo().getAlgoParamA();
						jPercentageRate = orderform.getProductInfo().getAlgoParamB();
						BigDecimal brokeragesNum = (jMoney.multiply(jExpense).multiply(jPercentageRate)).setScale(2);
						orderform.setPercentageRate(orderform.getProductInfo().getAlgoParamB());
						orderform.setBrokerageRateNum(brokeragesNum);
						
						//设置预计佣金总和
						/*BigDecimal fatherRate = brokeragesNum.multiply(product.getInfo().getFatherRate());
						BigDecimal bussinessRate = brokeragesNum.multiply(product.getInfo().getSellerRate());
						BigDecimal salesmanRate = brokeragesNum.multiply(product.getInfo().getSalesmanRate());
						orderform.setBrokerageRateTotal(brokeragesNum.add(fatherRate).add(bussinessRate).add(salesmanRate));*/
						orderformService.editOrderform(orderform);
					}
				}
				OrderOperLog ooL = new OrderOperLog();
				ooL.setCreateTime(new Date());
				ooL.setOperContent("修改产品为"+product.getInfo().getProductName());
				ooL.setOrder(orderform);
				ooL.setOpertor(user);
				orderOperLogService.addOrderOperLog(ooL);
				dto.setCode(1);
				dto.setMes("success");
				return dto;
			}
		}
		dto.setCode(0);
		dto.setMes("修改产品失败");
		return dto;
	}
}

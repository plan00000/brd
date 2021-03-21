package com.zzy.brd.controller.admin.orderform;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.BrokerageApply.SendStatus;
import com.zzy.brd.entity.BrokeragePaymentRecords;
import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.entity.RecordBrokerage.RelationType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.BrokerageApply.BrokerageApplyStatus;
import com.zzy.brd.entity.BrokerageApplyModifyHistory;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinPost.NoticeType;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.BrokerageApplyModifyHistoryService;
import com.zzy.brd.service.BrokerageApplyService;
import com.zzy.brd.service.BrokeragePaymentRecordsService;
import com.zzy.brd.service.RecordBrokerageService;
import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinPostService;
import com.zzy.brd.service.WeixinTemplateService;
import com.zzy.brd.util.excel.ExcelUtil;
import com.zzy.brd.util.excel.ExcelUtil.ExcelBean;
import com.zzy.brd.util.tld.DateUtils;

/**
 * 佣金订单-controller
 * @author lzh 2016/10/11
 *
 */
@Controller
@RequestMapping("admin/orderform/brokerage")
public class AdminBrokerageOrderformController {
	private static final Logger logger = LoggerFactory.getLogger(AdminBrokerageOrderformController.class);
	
	@Autowired
	private BrokerageApplyService brokerageApplyService;
	@Autowired
	private BrokerageApplyModifyHistoryService brokerageMHistoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoBothService userInfoBothService;
	@Autowired
	private RecordBrokerageService recordBrokerageService;
	@Autowired
	private BrokeragePaymentRecordsService brokeragePaymentRecordsService;
	@Autowired
	private WeixinTemplateService weixinTemplateService;
	@Autowired
	private WeixinPostService weixinPostService;
	/**
	 * 列表
	 * @param pageNum
	 * @param userType
	 * @param status
	 * @param billType
	 * @param sortName
	 * @param sortType
	 * @param searchName
	 * @param searchValue
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String brokeragelist(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
			,@RequestParam(value = "userType",required = false ,defaultValue = "") String userType
			,@RequestParam(value = "status",required = false,defaultValue = "")String status
			,@RequestParam(value = "billType",required = false,defaultValue ="") String billType
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,@RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			,@RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
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
			if("UNENTERING".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.UNENTERING);
			}
			
			if("RISKCHECK".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.RISKCHECK);
			}
			if("CEOCHECK".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.CEOCHECK);
			}
			if("FINANCESEND".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.FINANCESEND);
			}
			if("MANYTIMES".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.MANYTIMES);
			}
			if("CEOPASS".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.CEOPASS);
			}
			if("HADSEND".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.HADSEND);
			}
			if("FAILORDER".equals(status)){
				searchParams.put("EQ_status", BrokerageApplyStatus.FAILORDER);
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
		if(!StringUtils.isBlank(searchName)){
			if("productName".equals(searchName)){
				String search = "LIKE_productInfo.productName";
				searchParams.put(search, searchValue);
			}
			if("orderNo".equals(searchName)){
				String search = "LIKE_orderform.orderNo";
				searchParams.put(search, searchValue);
			}
		}
		Page<BrokerageApply> brokerages = brokerageApplyService.getBrokerageApplys(searchParams, sortName, sortType, pageNum, Constant.PAGE_SIZE);
		model.addAttribute("brokerages", brokerages);
		model.addAttribute("userType", userType);
		model.addAttribute("status", status);
		model.addAttribute("billType", billType);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("searchName", searchName);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("page", pageNum);
		model.addAttribute("queryStr", request.getQueryString());
		model.addAttribute("totalcount", brokerages.getTotalElements());
		return "admin/orderform/brokerage/brokeragelist";
	}
	@RequestMapping("export")
	public void flowExport(@RequestParam(value = "userType",required = false ,defaultValue = "") String userType
			,@RequestParam(value = "status",required = false,defaultValue = "")String status
			,@RequestParam(value = "billType",required = false,defaultValue ="") String billType
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,@RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			,@RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
			,HttpServletResponse response){
		List<BrokerageApply> brokerageApplys = brokerageApplyService.exportBrokerageApplys(userType,status,billType,sortName,sortType,searchName,searchValue);
		String[] titles = {"贷款名称","提单类型","下单人","下单人佣金","操作时间","订单状态"};
		ExcelBean excelBean = new ExcelBean("佣金订单.xls","佣金订单",titles);
		for(BrokerageApply b :brokerageApplys){
			String[] data = this.getDataList(b);
			excelBean.add(data);
		}
		try {
			ExcelUtil.export(response, excelBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String[] getDataList(BrokerageApply b){
		String productName = b.getProductInfo().getProductName();
		String billType = b.getProduct().getType().getBillType().getDes();
		String username = b.getUser().getUsername();
		String fatherBrokerage = b.getSelfBrokerage() +"元";
		String createTime = DateUtils.formatNormalDate(b.getCreateTime()) ;
		String status = b.getStatus().getDes(); 
		String data[] ={
			productName,
			billType,
			username,
			fatherBrokerage,
			createTime,
			status
		};
		return data;
	}
	/** 佣金明细*/
	@RequestMapping("detail/{id}")
	public String brokerageDetail(@RequestParam(value = "page", required = true, defaultValue = "1") int pageNum
			,@PathVariable long id
			,Model model){
		BrokerageApply brokerageApply = brokerageApplyService.findByBrokerageId(id);
		if(brokerageApply == null){
			return "";
		}
		String title = "";
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_brokerageApply.id",id );
		Page<BrokerageApplyModifyHistory> brokerageHistorys = brokerageMHistoryService.getBrokerageMHistoryPage(searchParams, pageNum, Constant.PAGE_SIZE);
		if(brokerageApply.getStatus() ==BrokerageApplyStatus.UNENTERING){
			title = "待录入佣金";
		}
		if(brokerageApply.getStatus() ==BrokerageApplyStatus.RISKCHECK){
			title = "风控审核";
		}
		if(brokerageApply.getStatus() == BrokerageApplyStatus.CEOCHECK){
			title = "CEO审核";
		}
		if(brokerageApply.getStatus() ==BrokerageApplyStatus.FINANCESEND){
			title = "财务发佣";
		}
		if(brokerageApply.getStatus() == BrokerageApplyStatus.MANYTIMES){
			title = "分次发放未完成";
		}
		if(brokerageApply.getStatus() ==BrokerageApplyStatus.CEOPASS){
			title = "CEO确定";
		}
		if(brokerageApply.getStatus() ==BrokerageApplyStatus.HADSEND || brokerageApply.getStatus() ==BrokerageApplyStatus.FAILORDER){
			title = "已发放/已拒绝订单（已完结订单）";
		}
		BigDecimal selfHadSend  = new BigDecimal(0);
		BigDecimal fatherHadSend  = new BigDecimal(0);
		BigDecimal bussinessHadSend  = new BigDecimal(0);
		BigDecimal salesmanHadSend  = new BigDecimal(0);
		if((brokerageApply.getStatus() == BrokerageApplyStatus.HADSEND || brokerageApply.getStatus() == BrokerageApplyStatus.FAILORDER)&& brokerageApply.getSendStatus() == SendStatus.MANEY){
			selfHadSend = brokerageApply.getSelfBrokerage().subtract(brokerageApply.getSelfResidualBrokerage());
			fatherHadSend = brokerageApply.getFatherBrokerage().subtract(brokerageApply.getFatherResidualBrokerage());
			bussinessHadSend = brokerageApply.getBusinessBrokerage().subtract(brokerageApply.getBusinessResidualBrokerage());
			salesmanHadSend = brokerageApply.getSalesmanBrokerage().subtract(brokerageApply.getSalesmanResidualBrokerage());
		}
		model.addAttribute("selfHadSend",selfHadSend );
		model.addAttribute("fatherHadSend", fatherHadSend);
		model.addAttribute("bussinessHadSend",bussinessHadSend );
		model.addAttribute("salesmanHadSend",salesmanHadSend );
		model.addAttribute("title", title);
		model.addAttribute("brokerageApply", brokerageApply);
		model.addAttribute("brokerageHistorys", brokerageHistorys);
		
		return "admin/orderform/brokerage/brokeragedesc";
	}
	/**
	 * 修改订单状态
	 * @param selfBrokerage
	 * @param fatherBrokerage
	 * @param salesmanBrokerage
	 * @param businessBrokerage
	 * @return
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public RepSimpleMessageDTO changeStatus(@RequestParam(value = "selfBrokerage",required = false) String selfBrokerage
			,@RequestParam(value = "fatherBrokerage",required = false) String fatherBrokerage
			,@RequestParam(value = "salesmanBrokerage",required = false) String salesmanBrokerage
			,@RequestParam(value = "businessBrokerage",required = false) String businessBrokerage
			,@RequestParam(value = "check",required = false) String check
			,@RequestParam(value = "brokerageId",required = true) long brokerageId
//			,@RequestParam(value = "newStatus",required = true) String newStatus
			,HttpServletRequest request){
		//去掉空格
		if(!StringUtils.isBlank(selfBrokerage)){
			selfBrokerage = selfBrokerage.trim();
		}else{
			selfBrokerage = "0";
		}
		
		fatherBrokerage = fatherBrokerage.trim();
		salesmanBrokerage = salesmanBrokerage.trim();
		businessBrokerage = businessBrokerage.trim();
		
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		BrokerageApply brokerageApply = brokerageApplyService.findByBrokerageId(brokerageId);
		if(brokerageApply == null){
			dto.setCode(0);
			dto.setMes("不存在该订单");
			return dto;
		}
		if(brokerageApply.getStatus() == BrokerageApplyStatus.UNENTERING){
			if(user.getRole().getId() != 4){
				dto.setCode(0);
				dto.setMes("对不起您不是财务，无权操作");
				return dto;
			}
			brokerageApply.setSelfBrokerage(new BigDecimal(selfBrokerage));
			brokerageApply.setFatherBrokerage(new BigDecimal(fatherBrokerage));
			brokerageApply.setSalesmanBrokerage(new BigDecimal(salesmanBrokerage));
			brokerageApply.setBusinessBrokerage(new BigDecimal(businessBrokerage));
			brokerageApply.setStatus(BrokerageApplyStatus.RISKCHECK);
			if(brokerageApplyService.addBrokerageApply(brokerageApply)){
				BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
				brokerageHistory.setOper(user);
				brokerageHistory.setBrokerageApply(brokerageApply);
				brokerageHistory.setAddDate(new Date());
				if(brokerageApply.getOrderform().getProduct().getType().getBillType().ordinal()==0){
					brokerageHistory.setContent("录入佣金。师父：￥"+fatherBrokerage+"；商家：￥"+businessBrokerage+"；业务员：￥"+salesmanBrokerage);
				}else{
					brokerageHistory.setContent("录入佣金。提单人：￥"+selfBrokerage+"；师父：￥"+fatherBrokerage+"；商家：￥"+businessBrokerage+"；业务员：￥"+salesmanBrokerage);
				}
				
				brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
				dto.setCode(1);
				dto.setMes("success");
				return dto;
			}
		
		}
		/** 风控经理审核*/
		if(brokerageApply.getStatus() == BrokerageApplyStatus.RISKCHECK ){
			if(user.getUserType() != UserType.CONTROLMANAGER){
				dto.setCode(0);
				dto.setMes("对不起您不是风控经理，无权操作");
				return dto;
			}
			if("PASS".equals(check)){
				BigDecimal oldSelfBrokerage = brokerageApply.getSelfBrokerage();
				BigDecimal oldFatherBrokerage = brokerageApply.getFatherBrokerage();
				BigDecimal oldSalesmanBrokerage = brokerageApply.getSalesmanBrokerage();
				BigDecimal oldBusinessBrokerage = brokerageApply.getBusinessBrokerage();
				brokerageApply.setSelfBrokerage(new BigDecimal(selfBrokerage));
				brokerageApply.setFatherBrokerage(new BigDecimal(fatherBrokerage));
				brokerageApply.setSalesmanBrokerage(new BigDecimal(salesmanBrokerage));
				brokerageApply.setBusinessBrokerage(new BigDecimal(businessBrokerage));
				brokerageApply.setStatus(BrokerageApplyStatus.CEOCHECK);
				if(brokerageApplyService.addBrokerageApply(brokerageApply)){
					String content = "风控经理审核通过。";
					if(oldSelfBrokerage.compareTo(new BigDecimal(selfBrokerage))!= 0){
						content = content+"修改提单人佣金￥"+oldSelfBrokerage+"修改为￥"+selfBrokerage+"；";
					}
					if(oldFatherBrokerage.compareTo(new BigDecimal(fatherBrokerage))!= 0){
						content = content+"修改师父佣金￥"+oldFatherBrokerage+"修改为￥"+fatherBrokerage+ "；";
					}
					if(oldBusinessBrokerage.compareTo(new BigDecimal(businessBrokerage))!= 0){
						content = content+"修改商家佣金￥"+oldBusinessBrokerage+"修改为￥"+businessBrokerage + "；";
					}
					if(oldSalesmanBrokerage.compareTo(new BigDecimal(salesmanBrokerage))!= 0){
						content = content+"修改业务员佣金￥"+oldSalesmanBrokerage+"修改为￥"+salesmanBrokerage + "；";
					}
					BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
					brokerageHistory.setOper(user);
					brokerageHistory.setBrokerageApply(brokerageApply);
					brokerageHistory.setAddDate(new Date());
					brokerageHistory.setContent(content);
					brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
					dto.setCode(1);
					dto.setMes("success");
					return dto;
				}
				
			}else{
				brokerageApply.setStatus(BrokerageApplyStatus.FAILORDER);
				if(brokerageApplyService.addBrokerageApply(brokerageApply)){
					BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
					brokerageHistory.setOper(user);
					brokerageHistory.setBrokerageApply(brokerageApply);
					brokerageHistory.setAddDate(new Date());
					brokerageHistory.setContent("风控经理审核拒绝");
					brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
					dto.setCode(1);
					dto.setMes("success");
					return dto;
				}
				
			}
		}
		/** CEO审核*/
		if(brokerageApply.getStatus() == BrokerageApplyStatus.CEOCHECK ){
			if(user.getUserType() != UserType.ADMIN){
				dto.setCode(0);
				dto.setMes("对不起您不是CEO，无权操作");
				return dto;
			}
			if("PASS".equals(check)){
				BigDecimal oldSelfBrokerage = brokerageApply.getSelfBrokerage();
				BigDecimal oldFatherBrokerage = brokerageApply.getFatherBrokerage();
				BigDecimal oldSalesmanBrokerage = brokerageApply.getSalesmanBrokerage();
				BigDecimal oldBusinessBrokerage = brokerageApply.getBusinessBrokerage();
				
				brokerageApply.setSelfBrokerage(new BigDecimal(selfBrokerage));
				brokerageApply.setFatherBrokerage(new BigDecimal(fatherBrokerage));
				brokerageApply.setSalesmanBrokerage(new BigDecimal(salesmanBrokerage));
				brokerageApply.setBusinessBrokerage(new BigDecimal(businessBrokerage));
				brokerageApply.setStatus(BrokerageApplyStatus.FINANCESEND);
				brokerageApply.setCheckpassTime(new Date());
				if(brokerageApplyService.addBrokerageApply(brokerageApply)){
					String content = "CEO审核通过。";
					
					if(oldSelfBrokerage.compareTo(new BigDecimal(selfBrokerage))!= 0){
						content = content+"修改提单人佣金￥"+ oldSelfBrokerage+"修改为￥"+selfBrokerage + "；";
					}
					if(oldFatherBrokerage.compareTo(new BigDecimal(fatherBrokerage))!= 0){
						content = content+"修改师父佣金￥"+oldFatherBrokerage+"修改为￥"+fatherBrokerage + "；";
					}
					if(oldBusinessBrokerage.compareTo(new BigDecimal(businessBrokerage))!= 0){
						content = content+"修改商家佣金￥"+oldBusinessBrokerage+"修改为￥"+businessBrokerage + "；";
					}
					if(oldSalesmanBrokerage.compareTo(new BigDecimal(salesmanBrokerage))!= 0){
						content = content+"修改业务员佣金￥"+oldSalesmanBrokerage+"修改为￥"+salesmanBrokerage + "；";
					}
					WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGECREATE);
					if(weixinPost!=null){
						WeixinPost.State state = weixinPost.getState();
						if(state!=null){
							if(state==WeixinPost.State.ON){
								boolean res = weixinTemplateService.getBrokerageCreateList(brokerageApply, request);//发送模板消息
								if(!res){
									dto.setCode(0);
									dto.setMes("有信息发送失败");
								}
						     }
						}
					}
					BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
					brokerageHistory.setOper(user);
					brokerageHistory.setBrokerageApply(brokerageApply);
					brokerageHistory.setAddDate(new Date());
					brokerageHistory.setContent(content);
					brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
					dto.setCode(1);
					dto.setMes("success");
					return dto;
				}
				
			}else{
				brokerageApply.setStatus(BrokerageApplyStatus.FAILORDER);
				if(brokerageApplyService.addBrokerageApply(brokerageApply)){
					BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
					brokerageHistory.setOper(user);
					brokerageHistory.setBrokerageApply(brokerageApply);
					brokerageHistory.setAddDate(new Date());
					brokerageHistory.setContent("CEO审核拒绝");
					brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
					dto.setCode(1);
					dto.setMes("success");
					return dto;
				}
				
			}
		}
		
		
		dto.setCode(0);
		dto.setMes("操作失败");
		return dto;
	}
	/**
	 * 财务发放
	 * @return
	 */
	@RequestMapping("financesend")
	@ResponseBody
	public RepSimpleMessageDTO financesend(@RequestParam (value = "sendStatus",required = false) String sendStatus
			,@RequestParam(value = "sendTimes",required = false) String sendTimes
			,@RequestParam(value = "selfSubmitBrokerage",required = false) String selfSubmitBrokerage
			,@RequestParam(value = "fatherSubmitBrokerage",required = false) String fatherSubmitBrokerage
			,@RequestParam(value = "businessSubmitBrokerage",required = false) String businessSubmitBrokerage
			,@RequestParam(value = "salesSubmitBrokerage",required = false) String salesSubmitBrokerage
			,@RequestParam(value = "brokerageId",required = true) long brokerageId
			,@RequestParam(value = "remark",required = false) String remark
			,HttpServletRequest request){
		
		
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		BrokerageApply brokerageApply = brokerageApplyService.findByBrokerageId(brokerageId);
		if(brokerageApply == null){
			rep.setCode(0);
			rep.setMes("不存在该订单");
			return rep;
		}
		if(user.getRole().getId() != 4){
			rep.setCode(0);
			rep.setMes("对不起您不是财务，无权操作");
			return rep;
		}
		//财务放款
		if(brokerageApply.getStatus() == BrokerageApplyStatus.FINANCESEND){
			if(!StringUtils.isBlank(sendStatus)){
				int intSendStatus = Integer.parseInt(sendStatus);
				if(intSendStatus == 0){
					brokerageApply.setStatus(BrokerageApplyStatus.CEOPASS);
					brokerageApply.setSelfResidualBrokerage(brokerageApply.getSelfBrokerage());
					brokerageApply.setFatherResidualBrokerage(brokerageApply.getFatherBrokerage());
					brokerageApply.setBusinessResidualBrokerage(brokerageApply.getBusinessBrokerage());
					brokerageApply.setSalesmanResidualBrokerage(brokerageApply.getSalesmanBrokerage());
					
					brokerageApply.setSelfSubmitBrokerage(brokerageApply.getSelfBrokerage());
					brokerageApply.setFatherSubmitBrokerage(brokerageApply.getFatherBrokerage());
					brokerageApply.setBusinessSubmitBrokerage(brokerageApply.getBusinessBrokerage());
					brokerageApply.setSalesmanSubmitBrokerage(brokerageApply.getSalesmanBrokerage());
					
					if(brokerageApplyService.addBrokerageApply(brokerageApply)){
						BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
						brokerageHistory.setOper(user);
						brokerageHistory.setBrokerageApply(brokerageApply);
						brokerageHistory.setAddDate(new Date());
						brokerageHistory.setContent("立即放款：一次发放");
						brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
						rep.setCode(1);
						rep.setMes("success");
						return rep;
					}
				}else{
					if(!StringUtils.isBlank(selfSubmitBrokerage)){
						selfSubmitBrokerage = selfSubmitBrokerage.trim();
					}else{
						selfSubmitBrokerage = "0";
					}
					fatherSubmitBrokerage = fatherSubmitBrokerage.trim();
					businessSubmitBrokerage = businessSubmitBrokerage.trim();
					salesSubmitBrokerage = salesSubmitBrokerage.trim();
					
					brokerageApply.setSendStatus(SendStatus.MANEY);
					brokerageApply.setSendTimes(Integer.parseInt(sendTimes));
					brokerageApply.setHasSendTimes(1);
					if(brokerageApply.getSelfBrokerage().compareTo(new BigDecimal(selfSubmitBrokerage))<0 ){
						rep.setCode(0);
						rep.setMes("提单人发放佣金金额不能大于剩余金额");
						return rep;
					}
					if( brokerageApply.getFatherBrokerage().compareTo(new BigDecimal(fatherSubmitBrokerage))<0){
						rep.setCode(0);
						rep.setMes("师父发放佣金金额不能大于剩余金额");
						return rep;
					}
					if(brokerageApply.getBusinessBrokerage().compareTo(new BigDecimal(businessSubmitBrokerage))<0){
						rep.setCode(0);
						rep.setMes("商家发放佣金金额不能大于剩余金额");
						return rep;
					}
					if(brokerageApply.getSalesmanBrokerage().compareTo(new BigDecimal(salesSubmitBrokerage))<0){
						rep.setCode(0);
						rep.setMes("业务员发放佣金金额不能大于剩余金额");
						return rep;
					}
					
					brokerageApply.setSelfResidualBrokerage(brokerageApply.getSelfBrokerage());
					brokerageApply.setFatherResidualBrokerage(brokerageApply.getFatherBrokerage());
					brokerageApply.setBusinessResidualBrokerage(brokerageApply.getBusinessBrokerage());
					brokerageApply.setSalesmanResidualBrokerage(brokerageApply.getSalesmanBrokerage());
					
					brokerageApply.setSelfSubmitBrokerage(new BigDecimal(selfSubmitBrokerage));
					brokerageApply.setFatherSubmitBrokerage(new BigDecimal(fatherSubmitBrokerage));
					brokerageApply.setBusinessSubmitBrokerage(new BigDecimal(businessSubmitBrokerage));
					brokerageApply.setSalesmanSubmitBrokerage(new BigDecimal(salesSubmitBrokerage));
					
					//增加发放记录
					BrokeragePaymentRecords records = new BrokeragePaymentRecords();
					records.setCreateTime(new Date());
					records.setRemark(remark);
					records.setBrokerageApply(brokerageApply);
					records.setOrderform(brokerageApply.getOrderform());
					records.setNumber(1);
					records.setSelfPaymentBrokerage(new BigDecimal(selfSubmitBrokerage));
					records.setFatherPaymentBrokerage(new BigDecimal(fatherSubmitBrokerage));
					records.setBusinessPaymentBrokerage(new BigDecimal(businessSubmitBrokerage));
					records.setSalesmanPaymentBrokerage(new BigDecimal(salesSubmitBrokerage));
					brokeragePaymentRecordsService.addBrokeragePaymentRecords(records);
					
					
					brokerageApply.setStatus(BrokerageApplyStatus.CEOPASS);
					if(brokerageApplyService.addBrokerageApply(brokerageApply)){
						BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
						brokerageHistory.setOper(user);
						brokerageHistory.setBrokerageApply(brokerageApply);
						brokerageHistory.setAddDate(new Date());
						String content = "立即放款:多次性发放。";
						if(brokerageApply.getOrderform().getProduct().getType().getBillType().ordinal()!=0){
							content = content + "提单人发放佣金：￥"+brokerageApply.getSelfSubmitBrokerage() + "；";
						}
						content = content + "师父发放佣金：￥"+brokerageApply.getFatherSubmitBrokerage() + "；";
						content = content + "商家发放佣金：￥"+brokerageApply.getBusinessSubmitBrokerage() + "；";
						content = content + "业务员发放佣金：￥"+brokerageApply.getSalesmanSubmitBrokerage() + "；";
						brokerageHistory.setContent(content);
						brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
						rep.setCode(1);
						rep.setMes("success");
						return rep;
					}
				}
				
			}
		}
		//财务多次放款
		if(brokerageApply.getStatus() == BrokerageApplyStatus.MANYTIMES){
			
			if(!StringUtils.isBlank(selfSubmitBrokerage)){
				selfSubmitBrokerage = selfSubmitBrokerage.trim();
			}else{
				selfSubmitBrokerage = "0";
			}
			fatherSubmitBrokerage = fatherSubmitBrokerage.trim();
			businessSubmitBrokerage = businessSubmitBrokerage.trim();
			salesSubmitBrokerage = salesSubmitBrokerage.trim();
			
			if(brokerageApply.getSelfResidualBrokerage().compareTo(new BigDecimal(selfSubmitBrokerage))<0 ){
				rep.setCode(0);
				rep.setMes("提单人发放佣金金额不能大于剩余金额");
				return rep;
			}
			if( brokerageApply.getFatherResidualBrokerage().compareTo(new BigDecimal(fatherSubmitBrokerage))<0){
				rep.setCode(0);
				rep.setMes("师父发放佣金金额不能大于剩余金额");
				return rep;
			}
			if(brokerageApply.getBusinessResidualBrokerage().compareTo(new BigDecimal(businessSubmitBrokerage))<0){
				rep.setCode(0);
				rep.setMes("商家发放佣金金额不能大于剩余金额");
				return rep;
			}
			if(brokerageApply.getSalesmanResidualBrokerage().compareTo(new BigDecimal(salesSubmitBrokerage))<0){
				rep.setCode(0);
				rep.setMes("业务员发放佣金金额不能大于剩余金额");
				return rep;
			}
			/*brokerageApply.setSelfResidualBrokerage(brokerageApply.getSelfResidualBrokerage().subtract(new BigDecimal(selfSubmitBrokerage)));
			brokerageApply.setFatherResidualBrokerage(brokerageApply.getFatherResidualBrokerage().subtract(new BigDecimal(fatherSubmitBrokerage)));
			brokerageApply.setBusinessResidualBrokerage(brokerageApply.getBusinessResidualBrokerage().subtract(new BigDecimal(businessSubmitBrokerage)));
			brokerageApply.setSalesmanResidualBrokerage(brokerageApply.getSalesmanResidualBrokerage().subtract(new BigDecimal(salesSubmitBrokerage)));*/
			
			brokerageApply.setSelfSubmitBrokerage(new BigDecimal(selfSubmitBrokerage));
			brokerageApply.setFatherSubmitBrokerage(new BigDecimal(fatherSubmitBrokerage));
			brokerageApply.setBusinessSubmitBrokerage(new BigDecimal(businessSubmitBrokerage));
			brokerageApply.setSalesmanSubmitBrokerage(new BigDecimal(salesSubmitBrokerage));
			
			//增加发放记录
			BrokeragePaymentRecords records = new BrokeragePaymentRecords();
			records.setCreateTime(new Date());
			records.setRemark(remark);
			records.setBrokerageApply(brokerageApply);
			records.setOrderform(brokerageApply.getOrderform());
			records.setNumber(brokerageApply.getHasSendTimes());
			records.setSelfPaymentBrokerage(new BigDecimal(selfSubmitBrokerage));
			records.setFatherPaymentBrokerage(new BigDecimal(fatherSubmitBrokerage));
			records.setBusinessPaymentBrokerage(new BigDecimal(businessSubmitBrokerage));
			records.setSalesmanPaymentBrokerage(new BigDecimal(salesSubmitBrokerage));
			brokeragePaymentRecordsService.addBrokeragePaymentRecords(records);
			
			brokerageApply.setStatus(BrokerageApplyStatus.CEOPASS);
			if(brokerageApplyService.addBrokerageApply(brokerageApply)){
				BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
				brokerageHistory.setOper(user);
				brokerageHistory.setBrokerageApply(brokerageApply);
				brokerageHistory.setAddDate(new Date());
				String content = "立即放款:第"+brokerageApply.getHasSendTimes()+"次发放。";
				if(brokerageApply.getOrderform().getProduct().getType().getBillType().ordinal()!=0){
					content = content + "提单人发放佣金：￥"+brokerageApply.getSelfSubmitBrokerage() + "；";
				}
				content = content + "师父发放佣金：￥"+brokerageApply.getFatherSubmitBrokerage() + "；";
				content = content + "商家发放佣金：￥"+brokerageApply.getBusinessSubmitBrokerage() + "；";
				content = content + "业务员发放佣金：￥"+brokerageApply.getSalesmanSubmitBrokerage() + "；";
				brokerageHistory.setContent(content);
				brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
				rep.setCode(1);
				rep.setMes("success");
				return rep;
			}
		}
		rep.setCode(0);
		rep.setMes("操作失败");
		return rep;
	}
	
	/**
	 * ceo确定
	 * @param check
	 * @param brokerageId
	 * @param request
	 * @return
	 */
	@RequestMapping("ceoConfirm")
	@ResponseBody
	public RepSimpleMessageDTO ceoPass(@RequestParam(value = "check",required = false) String check
			,@RequestParam(value = "brokerageId",required = true) long brokerageId
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		BrokerageApply brokerageApply = brokerageApplyService.findByBrokerageId(brokerageId);
		if(brokerageApply == null){
			rep.setCode(0);
			rep.setMes("不存在该订单");
			return rep;
		}
		//ceo确定
		if(brokerageApply.getStatus() == BrokerageApplyStatus.CEOPASS){
			if(user.getUserType() != UserType.ADMIN){
				rep.setCode(0);
				rep.setMes("对不起您不是CEO，无权操作");
				return rep;
			}
			if("PASS".equals(check)){
				if(brokerageApply.getSendStatus() == SendStatus.SINGAL){
					
					brokerageApply.setStatus(BrokerageApplyStatus.HADSEND);
					//设置剩余金额
					brokerageApply.setSelfResidualBrokerage(new BigDecimal(0));
					brokerageApply.setFatherResidualBrokerage(new BigDecimal(0));
					brokerageApply.setBusinessResidualBrokerage(new BigDecimal(0));
					brokerageApply.setSalesmanResidualBrokerage(new BigDecimal(0));
					//进行发佣--自身
					
					User selfUser = brokerageApply.getUser();
					BigDecimal orderBrokerage = selfUser.getUserInfoBoth().getOrderBrokerage();
					BigDecimal brokerageCanWithdraw = selfUser.getUserInfoBoth().getBrokerageCanWithdraw();
					if(brokerageApply.getOrderform().getProduct().getType().getBillType()!=BillType.EARNDIFFERENCE){
						UserInfoBoth selfUserInfoBoth = selfUser.getUserInfoBoth();
						selfUserInfoBoth.setOrderBrokerage(orderBrokerage.add(brokerageApply.getSelfBrokerage()));
						selfUserInfoBoth.setBrokerageCanWithdraw(brokerageCanWithdraw.add(brokerageApply.getSelfBrokerage()));
						//修改佣金金额
						userInfoBothService.editUserInfoBoth(selfUserInfoBoth);
						//保存佣金记录
						recordBrokerageService.addRecordBrokerage(selfUser,selfUser,RelationType.SELF,brokerageApply,1);
					}
					
					//师父
					User fatherUser = brokerageApply.getOrderform().getOldParent();
					if(fatherUser!=null){
						BigDecimal fatherOrderBrokerage = fatherUser.getUserInfoBoth().getOrderBrokerage();
						brokerageCanWithdraw = fatherUser.getUserInfoBoth().getBrokerageCanWithdraw();
						UserInfoBoth fatherUserInfoBoth = fatherUser.getUserInfoBoth();
						fatherUserInfoBoth.setOrderBrokerage(fatherOrderBrokerage.add(brokerageApply.getFatherBrokerage()));
						fatherUserInfoBoth.setBrokerageCanWithdraw(brokerageCanWithdraw.add(brokerageApply.getFatherBrokerage()));
						//修改佣金金额
						userInfoBothService.editUserInfoBoth(fatherUserInfoBoth);
						
						//保存佣金记录
						recordBrokerageService.addRecordBrokerage(fatherUser,selfUser,RelationType.APPRENTICE,brokerageApply,2);
					}
					//商家
					User sellerUser = brokerageApply.getOrderform().getOldBussiness();
					if(sellerUser!=null){
						BigDecimal bussinessOrderBrokerage = sellerUser.getUserInfoBoth().getOrderBrokerage();
						BigDecimal bussinessBrokerageCanWithdraw = sellerUser.getUserInfoBoth().getBrokerageCanWithdraw();
						//判断商家和师父是否是同一个人
						if(fatherUser !=null){
							if(sellerUser.getId() != fatherUser.getId()){
								if(fatherUser.getUserInfoBoth().getParent().getId() ==sellerUser.getId() ){
									//修改佣金金额
									UserInfoBoth sellerUserInfoBoth = sellerUser.getUserInfoBoth();
									sellerUserInfoBoth.setOrderBrokerage(bussinessOrderBrokerage.add(brokerageApply.getBusinessBrokerage()));
									sellerUserInfoBoth.setBrokerageCanWithdraw(bussinessBrokerageCanWithdraw.add(brokerageApply.getBusinessBrokerage()));
									userInfoBothService.editUserInfoBoth(sellerUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(sellerUser,selfUser,RelationType.DISCIPLE,brokerageApply,3);
								}else{
									//修改佣金金额
									UserInfoBoth sellerUserInfoBoth = sellerUser.getUserInfoBoth();
									sellerUserInfoBoth.setOrderBrokerage(bussinessOrderBrokerage.add(brokerageApply.getBusinessBrokerage()));
									sellerUserInfoBoth.setBrokerageCanWithdraw(bussinessBrokerageCanWithdraw.add(brokerageApply.getBusinessBrokerage()));
									userInfoBothService.editUserInfoBoth(sellerUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(sellerUser,selfUser,RelationType.GGSON,brokerageApply,3);
								}
							}
						}
						
						
					}
					//业务员
					User salesmanUser = brokerageApply.getOrderform().getOldSalesman();
					if(salesmanUser!=null){
						BigDecimal salesmanOrderBrokerage = salesmanUser.getUserInfoBoth().getOrderBrokerage();
						BigDecimal salsesmanBrokerageCanWithdraw = salesmanUser.getUserInfoBoth().getBrokerageCanWithdraw();
						if(fatherUser !=null){
							if(fatherUser.getId() !=salesmanUser.getId()){
								if(fatherUser.getUserInfoBoth().getParent().getId() ==salesmanUser.getId()){
									//修改佣金金额
									UserInfoBoth salesmanUserInfoBoth = salesmanUser.getUserInfoBoth();
									salesmanUserInfoBoth.setOrderBrokerage(salesmanOrderBrokerage.add(brokerageApply.getSalesmanBrokerage()));
									salesmanUserInfoBoth.setBrokerageCanWithdraw(salsesmanBrokerageCanWithdraw.add(brokerageApply.getSalesmanBrokerage()));
									userInfoBothService.editUserInfoBoth(salesmanUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(salesmanUser,selfUser,RelationType.DISCIPLE,brokerageApply,4);
								}else{
									//修改佣金金额
									UserInfoBoth salesmanUserInfoBoth = salesmanUser.getUserInfoBoth();
									salesmanUserInfoBoth.setOrderBrokerage(salesmanOrderBrokerage.add(brokerageApply.getSalesmanBrokerage()));
									salesmanUserInfoBoth.setBrokerageCanWithdraw(salsesmanBrokerageCanWithdraw.add(brokerageApply.getSalesmanBrokerage()));
									userInfoBothService.editUserInfoBoth(salesmanUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(salesmanUser,selfUser,RelationType.GGSON,brokerageApply,4);
								}
							}
						}
						
					}
					if(brokerageApplyService.addBrokerageApply(brokerageApply)){
						BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
						brokerageHistory.setOper(user);
						brokerageHistory.setBrokerageApply(brokerageApply);
						brokerageHistory.setAddDate(new Date());
						
						WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGELOAD);
						if(weixinPost!=null){
							WeixinPost.State state = weixinPost.getState();
							if(state!=null){
								if(state==WeixinPost.State.ON){
								boolean res = weixinTemplateService.getBrokerageLoadList(brokerageApply, request);//发送模板消息
								if(!res){
									rep.setCode(0);
									rep.setMes("有信息发送失败");
									}
								}
							}
						}		
						String content = "CEO确定审核通过。";
						content = content + "确认提单人佣金：￥"+brokerageApply.getSelfBrokerage() + "；";
						content = content + "确认师父佣金：￥"+brokerageApply.getFatherBrokerage() + "；";
						content = content + "确认商家佣金：￥"+brokerageApply.getBusinessBrokerage() + "；";
						content = content + "确认业务员佣金：￥"+brokerageApply.getSalesmanBrokerage() + "；";
						brokerageHistory.setContent("CEO确定审核通过");
						
						brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
						rep.setCode(1);
						rep.setMes("success");
						return rep;
					}
					
				}else{
					//多次发放
					//发放次数达到最大
					if(brokerageApply.getHasSendTimes() == brokerageApply.getSendTimes()){
						brokerageApply.setStatus(BrokerageApplyStatus.HADSEND);
					}else{
						brokerageApply.setStatus(BrokerageApplyStatus.MANYTIMES);
					}
					brokerageApply.setHasSendTimes(brokerageApply.getHasSendTimes() +1);
					
					User selfUser = brokerageApply.getUser();
					BigDecimal orderBrokerage = selfUser.getUserInfoBoth().getOrderBrokerage();
					BigDecimal brokerageCanWithdraw = selfUser.getUserInfoBoth().getBrokerageCanWithdraw();
					
					if(brokerageApply.getOrderform().getProduct().getType().getBillType()!=BillType.EARNDIFFERENCE){
						UserInfoBoth selfUserInfoBoth = selfUser.getUserInfoBoth();
						selfUserInfoBoth.setOrderBrokerage(orderBrokerage.add(brokerageApply.getSelfSubmitBrokerage()));
						selfUserInfoBoth.setBrokerageCanWithdraw(brokerageCanWithdraw.add(brokerageApply.getSelfSubmitBrokerage()));
						//修改佣金金额
						userInfoBothService.editUserInfoBoth(selfUserInfoBoth);
						//保存佣金记录
						recordBrokerageService.addRecordBrokerage(selfUser,selfUser,RelationType.SELF,brokerageApply,1);
					}
					//师父
					User fatherUser = brokerageApply.getOrderform().getOldParent();
					if(fatherUser!=null){
						orderBrokerage = fatherUser.getUserInfoBoth().getOrderBrokerage();
						brokerageCanWithdraw = fatherUser.getUserInfoBoth().getBrokerageCanWithdraw();
						UserInfoBoth fatherUserInfoBoth = fatherUser.getUserInfoBoth();
						fatherUserInfoBoth.setOrderBrokerage(orderBrokerage.add(brokerageApply.getFatherSubmitBrokerage()));
						fatherUserInfoBoth.setBrokerageCanWithdraw(brokerageCanWithdraw.add(brokerageApply.getFatherSubmitBrokerage()));
						//修改佣金金额
						userInfoBothService.editUserInfoBoth(fatherUserInfoBoth);
						//保存佣金记录
						recordBrokerageService.addRecordBrokerage(fatherUser,selfUser,RelationType.APPRENTICE,brokerageApply,2);
					}
					
					//商家
					User sellerUser = brokerageApply.getOrderform().getOldBussiness();
					if(sellerUser!=null){
						BigDecimal bussinessOrderBrokerage = sellerUser.getUserInfoBoth().getOrderBrokerage();
						BigDecimal bussinessBrokerageCanWithdraw = sellerUser.getUserInfoBoth().getBrokerageCanWithdraw();
						//判断商家和师父是否是同一个人
						if(fatherUser !=null){
							if(sellerUser.getId() != fatherUser.getId()){
								if(fatherUser.getUserInfoBoth().getParent().getId() ==sellerUser.getId() ){
									//修改佣金金额
									UserInfoBoth sellerUserInfoBoth = sellerUser.getUserInfoBoth();
									sellerUserInfoBoth.setOrderBrokerage(bussinessOrderBrokerage.add(brokerageApply.getBusinessBrokerage()));
									sellerUserInfoBoth.setBrokerageCanWithdraw(bussinessBrokerageCanWithdraw.add(brokerageApply.getBusinessBrokerage()));
									userInfoBothService.editUserInfoBoth(sellerUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(sellerUser,selfUser,RelationType.DISCIPLE,brokerageApply,3);
								}else{
									//修改佣金金额
									UserInfoBoth sellerUserInfoBoth = sellerUser.getUserInfoBoth();
									sellerUserInfoBoth.setOrderBrokerage(bussinessOrderBrokerage.add(brokerageApply.getBusinessBrokerage()));
									sellerUserInfoBoth.setBrokerageCanWithdraw(bussinessBrokerageCanWithdraw.add(brokerageApply.getBusinessBrokerage()));
									userInfoBothService.editUserInfoBoth(sellerUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(sellerUser,selfUser,RelationType.GGSON,brokerageApply,3);
								}
							}
						}
					}
					//业务员
					User salesmanUser = brokerageApply.getOrderform().getOldSalesman();
					if(salesmanUser!=null){
						BigDecimal salesmanOrderBrokerage = salesmanUser.getUserInfoBoth().getOrderBrokerage();
						BigDecimal salsesmanBrokerageCanWithdraw = salesmanUser.getUserInfoBoth().getBrokerageCanWithdraw();
						if(fatherUser !=null){
							if(fatherUser.getId() !=salesmanUser.getId()){
								if(fatherUser.getUserInfoBoth().getParent().getId() ==salesmanUser.getId()){
									//修改佣金金额
									UserInfoBoth salesmanUserInfoBoth = salesmanUser.getUserInfoBoth();
									salesmanUserInfoBoth.setOrderBrokerage(salesmanOrderBrokerage.add(brokerageApply.getSalesmanBrokerage()));
									salesmanUserInfoBoth.setBrokerageCanWithdraw(salsesmanBrokerageCanWithdraw.add(brokerageApply.getSalesmanBrokerage()));
									userInfoBothService.editUserInfoBoth(salesmanUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(salesmanUser,selfUser,RelationType.DISCIPLE,brokerageApply,4);
								}else{
									//修改佣金金额
									UserInfoBoth salesmanUserInfoBoth = salesmanUser.getUserInfoBoth();
									salesmanUserInfoBoth.setOrderBrokerage(salesmanOrderBrokerage.add(brokerageApply.getSalesmanBrokerage()));
									salesmanUserInfoBoth.setBrokerageCanWithdraw(salsesmanBrokerageCanWithdraw.add(brokerageApply.getSalesmanBrokerage()));
									userInfoBothService.editUserInfoBoth(salesmanUserInfoBoth);
									//保存佣金记录
									recordBrokerageService.addRecordBrokerage(salesmanUser,selfUser,RelationType.GGSON,brokerageApply,4);
								}
							}
						}
					}
					//设置剩余金额
					brokerageApply.setSelfResidualBrokerage(brokerageApply.getSelfResidualBrokerage().subtract(brokerageApply.getSelfSubmitBrokerage()));
					brokerageApply.setFatherResidualBrokerage(brokerageApply.getFatherResidualBrokerage().subtract(brokerageApply.getFatherSubmitBrokerage()));
					brokerageApply.setBusinessResidualBrokerage(brokerageApply.getBusinessResidualBrokerage().subtract(brokerageApply.getBusinessSubmitBrokerage()));
					brokerageApply.setSalesmanResidualBrokerage(brokerageApply.getSalesmanResidualBrokerage().subtract(brokerageApply.getSalesmanSubmitBrokerage()));
					
					if(brokerageApplyService.addBrokerageApply(brokerageApply)){
						BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
						brokerageHistory.setOper(user);
						brokerageHistory.setBrokerageApply(brokerageApply);
						brokerageHistory.setAddDate(new Date());
						String content = "CEO确定审核通过。";
						if(brokerageApply.getOrderform().getProduct().getType().getBillType().ordinal()!=0){
							content = content + "确认提单人佣金：￥"+brokerageApply.getSelfSubmitBrokerage() + "；";
						}
						
						content = content + "确认师父佣金：￥"+brokerageApply.getFatherSubmitBrokerage() + "；";
						content = content + "确认商家佣金：￥"+brokerageApply.getBusinessSubmitBrokerage() + "；";
						content = content + "确认业务员佣金：￥"+brokerageApply.getSalesmanSubmitBrokerage() + "；";
						
						WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGELOAD);
						if(weixinPost!=null){
							WeixinPost.State state = weixinPost.getState();
							if(state!=null){
								if(state==WeixinPost.State.ON){
								boolean res = weixinTemplateService.getBrokerageLoadList(brokerageApply, request);//发送模板消息
									if(!res){
										rep.setCode(0);
										rep.setMes("有信息发送失败");
										}
									}
								}
							}
						brokerageHistory.setContent(content);
						brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
						rep.setCode(1);
						rep.setMes("success");
						return rep;
					}
				}
			}else{
				brokerageApply.setStatus(BrokerageApplyStatus.FAILORDER);
				//修改下单的佣金
				brokerageApply.setSelfResidualBrokerage(brokerageApply.getSelfBrokerage().subtract(brokerageApply.getSelfResidualBrokerage()));
				brokerageApply.setFatherResidualBrokerage(brokerageApply.getFatherBrokerage().subtract(brokerageApply.getFatherResidualBrokerage()));
				brokerageApply.setBusinessResidualBrokerage(brokerageApply.getBusinessBrokerage().subtract(brokerageApply.getBusinessResidualBrokerage()));
				brokerageApply.setSalesmanResidualBrokerage(brokerageApply.getSalesmanBrokerage().subtract(brokerageApply.getSalesmanResidualBrokerage()));
				if(brokerageApplyService.addBrokerageApply(brokerageApply)){
					BrokerageApplyModifyHistory brokerageHistory = new BrokerageApplyModifyHistory();
					brokerageHistory.setOper(user);
					brokerageHistory.setBrokerageApply(brokerageApply);
					brokerageHistory.setAddDate(new Date());
					brokerageHistory.setContent("CEO确定审核拒绝");
					brokerageMHistoryService.addBrokerageMHistory(brokerageHistory);
					rep.setCode(1);
					rep.setMes("success");
					return rep;
				}
				
			}
		}
		rep.setCode(0);
		rep.setMes("操作失败");
		return rep ;
	}
	/**
	 * 判断是不是财务人员
	 */
	@RequestMapping("isFinancesend")
	@ResponseBody
	public RepSimpleMessageDTO isFinancesend(HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		if(user.getRole().getId() != 4){
			rep.setCode(0);
			rep.setMes("对不起您不是财务，无权操作");
			return rep;
		}
		rep.setCode(1);
		rep.setMes("success");
		return rep;
	}
	
}

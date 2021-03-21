package com.zzy.brd.controller.admin.orderform;

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
import com.zzy.brd.dao.UserInfoBothDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinPost.NoticeType;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.FlowWithdrawService;
import com.zzy.brd.service.OrderOperLogService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinPostService;
import com.zzy.brd.service.WeixinTemplateService;
import com.zzy.brd.util.excel.ExcelUtil;
import com.zzy.brd.util.excel.ExcelUtil.ExcelBean;
import com.zzy.brd.util.tld.DateUtils;
import com.zzy.brd.util.weixin.NewInfoTemplate;

/**
 * 体现订单-controller
 * @author lzh 2016/10/12
 *
 */
@Controller
@RequestMapping("admin/orderform/flowwithdraw")
public class AdminFlowWithdrawOrderformController {
	private static final Logger logger = LoggerFactory.getLogger(AdminFlowWithdrawOrderformController.class);
	@Autowired
	private FlowWithdrawService flowWithdrawService;
	@Autowired
	private OrderOperLogService orderOperLogService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoBothDao userInfoBothDao;
	@Autowired
	private WeixinTemplateService weixinTemplateService;
	@Autowired
	private WeixinPostService weixinPostService;
	/**
	 * 体现列表
	 * @param pageNum
	 * @param userType
	 * @param status
	 * @param sortName
	 * @param sortType
	 * @param searchName
	 * @param searchValue
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String flowWithdrawList(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
			,@RequestParam(value = "userType",required = false ,defaultValue = "") String userType
			,@RequestParam(value = "status",required = false,defaultValue = "")String status
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
			if("NOCHECK".equals(status)){
				searchParams.put("EQ_status", WithdrawStatus.NOCHECK);
			}
			if("NOLENDING".equals(status)){
				searchParams.put("EQ_status", WithdrawStatus.NOLENDING);
			}
			if("FAILCHECK".equals(status)){
				searchParams.put("EQ_status", WithdrawStatus.FAILCHECK);
			}
			if("ALEARDYLOAN".equals(status)){
				searchParams.put("EQ_status", WithdrawStatus.ALEARDYLOAN);
			}
		}
		if(!StringUtils.isBlank(searchName)){
			if("mobileno".equals(searchName)){
				String search = "LIKE_user.mobileno";
				searchParams.put(search, searchValue);
			}
			if("flowno".equals(searchName)){
				String search = "LIKE_flowno";
				searchParams.put(search, searchValue);
			}
			
		}
		Page<FlowWithdraw> flowWithdraws = flowWithdrawService.adminFlowWithdrawList(searchParams, sortName, sortType, pageNum,Constant.PAGE_SIZE);
		model.addAttribute("flowWithdraws", flowWithdraws);
		model.addAttribute("userType", userType);
		model.addAttribute("status", status);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("searchName", searchName);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("page", pageNum);
		model.addAttribute("queryStr", request.getQueryString());
		model.addAttribute("totalcount", flowWithdraws.getTotalElements());
		return "admin/orderform/flowwithdraw/flowlist";
	}
	/**
	 * 导出订单
	 * @param userType
	 * @param status
	 * @param sortName
	 * @param sortType
	 * @param searchName
	 * @param searchValue
	 * @param response
	 */
	@RequestMapping("export")
	public void flowExport(@RequestParam(value = "userType",required = false ,defaultValue = "") String userType
			,@RequestParam(value = "status",required = false,defaultValue = "")String status
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,@RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			,@RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
			,HttpServletResponse response){
		List<FlowWithdraw> flowWithdraws = flowWithdrawService.exportFlowWithdraw(userType, status, searchName, searchValue, sortName, sortType);
		String[] titles = {"会员名","会员账号","申请时间","申请金额","订单状态"};
		ExcelBean excelBean = new ExcelBean("提现订单.xls","体现订单",titles);
		for(FlowWithdraw f :flowWithdraws){
			String[] data = this.getDataList(f);
			excelBean.add(data);
		}
		try {
			ExcelUtil.export(response, excelBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String[] getDataList(FlowWithdraw f){
		String userName = f.getUser().getUsername();
		String mobileno = f.getUser().getMobileno();
		String createTime = DateUtils.formatNormalDate(f.getCreateTime());
		String money = f.getMoney()+"元";
		String status = f.getStatus().getDes();
		String data[]={
				userName,
				mobileno,
				createTime,
				money,
				status
			};
		return data;
	}
	/** 
	 * 提现订单详情
	 * @param pageNum
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("detail/{id}")
	public String flowWithdrawDetail(@RequestParam(value = "page", required = true, defaultValue = "1") int pageNum
			,@PathVariable long id
			,Model model){
		
		FlowWithdraw flowWithdraw = flowWithdrawService.findByFlowWithdrawId(id);
		if(flowWithdraw == null){
			return "";
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_withdraw.id", id);
		Page<OrderOperLog> orderOperLogs = orderOperLogService.getOrderOperLogPage(searchParams, pageNum, Constant.PAGE_SIZE);
		model.addAttribute("flowWithdraw", flowWithdraw);
		model.addAttribute("orderOperLogs", orderOperLogs);
		return "admin/orderform/flowwithdraw/flowdesc";
	}
	/**
	 * 修改状态
	 * @param brokerageId
	 * @param check
	 * @param request
	 * @return
	 */
	@RequestMapping(value="changeStatus")
	@ResponseBody
	public RepSimpleMessageDTO changeStatus(@RequestParam(value = "flowWithdrawId",required = true) long flowWithdrawId
			,@RequestParam(value = "check",required = false) String check
			,@RequestParam(value = "refuse",required = false) String refuse
			,HttpServletRequest request){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		FlowWithdraw flowWithdraw = flowWithdrawService.findByFlowWithdrawId(flowWithdrawId);
		if(flowWithdraw == null){
			dto.setCode(0);
			dto.setMes("不存在该订单");
			return dto;
		}
		if(user.getRole().getId() != 4){
			dto.setCode(0);
			dto.setMes("对不起您不是财务，无权操作");
			return dto;
		}
		if(flowWithdraw.getStatus() == WithdrawStatus.NOCHECK && "pass".equals(check)){
			flowWithdraw.setStatus(WithdrawStatus.NOLENDING);
			flowWithdraw.setVerifyTime(new Date());
			flowWithdraw.setVerifyUser(user);
			if(flowWithdrawService.saveFlowWithdraw(flowWithdraw)){
				OrderOperLog oop = new OrderOperLog();
				oop.setCreateTime(new Date());
				oop.setOpertor(user);
				oop.setWithdraw(flowWithdraw);
				oop.setOperContent("审核通过");
				orderOperLogService.addOrderOperLog(oop);
				dto.setCode(1);
				dto.setMes("success");
				return dto;
			}
		}
		if(flowWithdraw.getStatus() == WithdrawStatus.NOCHECK && "refuse".equals(check)){
			flowWithdraw.setStatus(WithdrawStatus.FAILCHECK);
			flowWithdraw.setVerifyTime(new Date());
			flowWithdraw.setVerifyUser(user);
			flowWithdraw.setRejectReason(refuse);
			//拒绝增加个人金额
			UserInfoBoth flowInfo = flowWithdraw.getUser().getUserInfoBoth();
			//正在提现 扣除金额
			flowInfo.setBrokerageWithdrawing(flowInfo.getBrokerageWithdrawing().subtract(flowWithdraw.getMoney()));
			//可提现增加金额
			flowInfo.setBrokerageCanWithdraw(flowInfo.getBrokerageCanWithdraw().add(flowWithdraw.getMoney()));
			userInfoBothDao.save(flowInfo);	
			if(flowWithdrawService.saveFlowWithdraw(flowWithdraw)){
				OrderOperLog oop = new OrderOperLog();
				oop.setCreateTime(new Date());
				oop.setOpertor(user);
				oop.setWithdraw(flowWithdraw);
				oop.setOperContent("审核拒绝");
				orderOperLogService.addOrderOperLog(oop);
				dto.setCode(1);
				dto.setMes("success");
				return dto;
			}
		}
		/** 已放款*/
		if(flowWithdraw.getStatus() == WithdrawStatus.NOLENDING){
			
			
			User wxUser = flowWithdraw.getUser();
			if(wxUser!=null){
				WeixinUser weixinUser = wxUser.getWeixinUser();
				if(weixinUser!=null){
					WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.WITHDRAWARRIVAL);
					if(weixinPost!=null){
						WeixinPost.State state = weixinPost.getState();
						if(state!=null){
							if(state==WeixinPost.State.ON){
								NewInfoTemplate newInfoTemplate = weixinTemplateService.getWithdrawArrival(flowWithdraw,request);
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
			
			///增加对应的个人金额
			UserInfoBoth flowInfo = flowWithdraw.getUser().getUserInfoBoth();
			//正在提现
			flowInfo.setBrokerageWithdrawing(flowInfo.getBrokerageWithdrawing().subtract(flowWithdraw.getMoney()));
			//已提现
			flowInfo.setBrokerageHaveWithdraw(flowInfo.getBrokerageHaveWithdraw().add(flowWithdraw.getMoney()));
			userInfoBothDao.save(flowInfo);			
			flowWithdraw.setStatus(WithdrawStatus.ALEARDYLOAN);
			flowWithdraw.setSendDate(new Date());
			if(flowWithdrawService.saveFlowWithdraw(flowWithdraw)){
				OrderOperLog oop = new OrderOperLog();
				oop.setCreateTime(new Date());
				oop.setOpertor(user);
				oop.setWithdraw(flowWithdraw);
				oop.setOperContent("已放款");
				orderOperLogService.addOrderOperLog(oop);
				dto.setCode(1);
				dto.setMes("success");
				return dto;
			}
		}
		return dto;
	}
}

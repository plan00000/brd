package com.zzy.brd.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.algorithm.encrypt.shiro.SHA1Encrypt;
import com.zzy.brd.constant.GlobalData;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.BrokerageApply.BrokerageApplyStatus;
import com.zzy.brd.entity.Department;
import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.Role;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.BrokerageApplyService;
import com.zzy.brd.service.DepartmentService;
import com.zzy.brd.service.FlowWithdrawService;
import com.zzy.brd.service.OrderformService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.principal.ShiroUser;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.encrypt.shiro.PasswordInfo;
import com.zzy.brd.util.file.FileUtil;

/***
 * 后台-主页面
 * @author wwy
 *
 */
@Controller
@RequestMapping("/admin/main")
public class AdminMainController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrderformService orderformService;
	@Autowired
	private FlowWithdrawService flowWithdrawService;
	@Autowired
	private BrokerageApplyService brokerageApplyService;
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping
	public String main(Model model){
		ShiroUser sUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		User user = userService.findUserById(sUser.getId());
		Role role = user.getRole();
		String rolename = null;
		if(role!=null){
			rolename = role.getRolename();
		}
		model.addAttribute("name", user.getUsername());
		model.addAttribute("logintime",user.getLogindate());
		//贷款订单
		int countSelfhelploanNum = orderformService.countLoanUncheckNum(BillType.SELFHELPLOAN, OrderformStatus.UNCHECKED,OrderSource.WECHAT);
		int countEarndifferenceNum = orderformService.countLoanUncheckNum(BillType.EARNDIFFERENCE, OrderformStatus.UNCHECKED,OrderSource.WECHAT);
		int countEarncommissionNum = orderformService.countLoanUncheckNum(BillType.EARNCOMMISSION, OrderformStatus.UNCHECKED,OrderSource.WECHAT);
		int countUncheckNum = countSelfhelploanNum +countEarndifferenceNum + countEarncommissionNum;
		
		//官网订单
		int countPcUncheckNum = orderformService.countPcOrderNum(OrderformStatus.UNCHECKED, OrderSource.PC);
		int countPcUnloanNum = orderformService.countPcOrderNum(OrderformStatus.UNLOAN, OrderSource.PC);
		int countPcNum = countPcUncheckNum + countPcUnloanNum ;
		//体现订单
		int countFlowWithdrawUncheckNum = flowWithdrawService.countFlowWithdrawByStatusNum(WithdrawStatus.NOCHECK);
		int countFlowWithdrawUnloadNum = flowWithdrawService.countFlowWithdrawByStatusNum(WithdrawStatus.NOLENDING);
		int countFlowNum = countFlowWithdrawUncheckNum + countFlowWithdrawUnloadNum;
		//佣金订单
		int countBrokerageUncheckNum = 0;
		int countBrokerageUnenteringNum = brokerageApplyService.countBrokerageNum(BrokerageApplyStatus.UNENTERING);//待入佣金
		int countBrokerageRiskUncheckNum = brokerageApplyService.countBrokerageNum(BrokerageApplyStatus.RISKCHECK);//风控审核
		int countBrokerageCeoUncheckNum = brokerageApplyService.countBrokerageNum(BrokerageApplyStatus.CEOCHECK);//ceo审核
		int countBrokerageCeoPassNum = brokerageApplyService.countBrokerageNum(BrokerageApplyStatus.CEOPASS);//ceo确定
		if("Admin".equals(rolename)){
			countBrokerageUncheckNum = countBrokerageCeoUncheckNum;
		}
		if("财务".equals(rolename)){
			countBrokerageUncheckNum =countBrokerageUnenteringNum;
		}
		if("风控经理".equals(rolename)){
			countBrokerageUncheckNum =countBrokerageRiskUncheckNum;
		}
		int countBrokerageFinanceNum = brokerageApplyService.countBrokerageNum(BrokerageApplyStatus.FINANCESEND);
		
		int countBrokerageNum = 0;
		if("Admin".equals(rolename)){
			countBrokerageNum = countBrokerageUncheckNum + countBrokerageFinanceNum + countBrokerageCeoPassNum;
		}else{
			countBrokerageNum = countBrokerageUncheckNum + countBrokerageFinanceNum;
		}
		
		//会员管理
		int pendingAudit = userService.pendingAudit();
		model.addAttribute("rolename",rolename);
		model.addAttribute("countSelfhelploanNum",countSelfhelploanNum);
		model.addAttribute("countEarndifferenceNum",countEarndifferenceNum);
		model.addAttribute("countEarncommissionNum",countEarncommissionNum);
		model.addAttribute("countUncheckNum",countUncheckNum);
		model.addAttribute("countPcUncheckNum",countPcUncheckNum);
		model.addAttribute("countPcUnloanNum",countPcUnloanNum);
		model.addAttribute("countPcNum",countPcNum);
		model.addAttribute("countFlowWithdrawUncheckNum",countFlowWithdrawUncheckNum);
		model.addAttribute("countFlowWithdrawUnloadNum",countFlowWithdrawUnloadNum);
		model.addAttribute("countFlowNum",countFlowNum);
		model.addAttribute("countBrokerageUncheckNum",countBrokerageUncheckNum);
		model.addAttribute("countBrokerageCeoPassNum",countBrokerageCeoPassNum);
		model.addAttribute("countBrokerageFinanceNum",countBrokerageFinanceNum);
		model.addAttribute("countBrokerageNum",countBrokerageNum);
		model.addAttribute("pendingAudit",pendingAudit);
		
		model.addAttribute("loginIp",user.getUserInfoEmployee().getLastloginIp());
		model.addAttribute("city", user.getUserInfoEmployee().getLoginCity());
		model.addAttribute("timename", DateUtil.getTimeName());
		
		
		return "admin/main";
	};
	
	/**
	 * 跳转到修改密码页面
	 * 
	 * @param @return
	 * @return String
	 */
	@RequestMapping("toChangePassword")
	public String changePassword(Model model) {
		return "admin/base/changePassword";

	}
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO changeUserPassword(
			@RequestParam(value = "oldPassword", defaultValue = "", required = true) String oldPassword,
			@RequestParam(value = "newPassword", defaultValue = "", required = true) String newPassword) {
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		// 验证原密码是否正确
		User user = userService.findById(GlobalData
				.getCurrentLoginUser().getId());
		if(user == null){
			dto.setCode(2);
			dto.setMes("请先登录");
			return dto;
		}
		SHA1Encrypt sha1Encrypt = SHA1Encrypt.getInstance();
		String password = sha1Encrypt.encryptPasswordBySalt(oldPassword,
				user.getSalt());
		if (!(password.equalsIgnoreCase(user.getPassword()))) {
			dto.setMes("原密码输入错误");
			dto.setCode(0);
			return dto;
		}
		
		// 修改密码
		com.zzy.brd.algorithm.encrypt.shiro.PasswordInfo passwordInfo = sha1Encrypt.encryptPassword(newPassword);
		user.setPassword(passwordInfo.getPassword());
		user.setSalt(passwordInfo.getSalt());
		if(userService.editUser(user)){
			dto.setCode(1);
			dto.setMes("success");
			return dto;
		}
		dto.setCode(0);
		dto.setMes("密码修改失败");
		return dto;
	}
	/**
	 * 查看基本信息
	 * @param model
	 * @return
	 */
	@RequestMapping("toBaseInfo")
	public String baseInfo(Model model) {
		User user = userService.findById(GlobalData
				.getCurrentLoginUser().getId());
		if (user == null) {
			return "error/404";
		}
		Department threeD = user.getUserInfoEmployee().getDepartment();
		Department secondD = null;
		Department firstD = null;
		if(threeD != null){
			if(threeD.getParent()!=null){
				secondD = threeD.getParent();
				if(secondD != null){
					if(secondD.getParent()!=null){
						firstD = secondD.getParent();
					}
				}
			}
		}
		model.addAttribute("firstD", firstD);
		model.addAttribute("secondD", secondD);
		model.addAttribute("threeD", threeD);
		model.addAttribute("userdesc", user);
	return "admin/base/baseInfo";
	}
	/**
	 * 基本信息修改头像
	 * @param headImage
	 * @param request
	 * @return
	 */
	@RequestMapping("modifyHeadImage")
	@ResponseBody
	public RepSimpleMessageDTO modifyHeadImage(@RequestParam (value = "headImage",required = true) String headImage
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		if(userId == null){
			rep.setCode(0);
			rep.setMes("请先登录");
			return rep;
		}
		User user = userService.findById(userId);
		String image = FileUtil.moveFileToPro(headImage);
		user.setHeadimgurl(image);
		if(!userService.editUser(user)){
			rep.setCode(0);
			rep.setMes("修改头像失败");
			return rep;
		}
		rep.setCode(1);
		rep.setMes("success");
		return rep;
	}
	
	
}

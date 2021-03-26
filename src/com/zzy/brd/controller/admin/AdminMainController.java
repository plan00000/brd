package com.zzy.brd.controller.admin;

import javax.servlet.http.HttpServletRequest;

import com.zzy.brd.entity.*;
import com.zzy.brd.service.*;
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
//import com.zzy.brd.entity.BrokerageApply.BrokerageApplyStatus;
//import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
//import com.zzy.brd.entity.Orderform.OrderSource;
//import com.zzy.brd.entity.Orderform.OrderformStatus;
//import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.shiro.principal.ShiroUser;
import com.zzy.brd.util.date.DateUtil;
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
	private OrderService orderService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private PassengerService passengerService;
	@Autowired
	private LineService lineService;

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

		//订单各个状态统计
		int orderQx = orderService.countOrderStatus(TbOrder.OrderStatus.yqx);
		int orderYwc = orderService.countOrderStatus(TbOrder.OrderStatus.xcjs);
		int orderCs = orderService.countOrderStatus(TbOrder.OrderStatus.csdd);
		int orderAll = orderService.countOrderStatus(TbOrder.OrderStatus.pdz)+ orderService.countOrderStatus(TbOrder.OrderStatus.sjyjd)+orderService.countOrderStatus(TbOrder.OrderStatus.xcks)+orderQx+orderCs+orderYwc;

		//司机
		int driverXb = driverService.countDriverState(TbDriver.DriverStatus.xb);
		int driverSb = driverService.countDriverState(TbDriver.DriverStatus.sb);
		int driverAll = driverSb + driverXb;

		//乘客
		int passengerJy = passengerService.countTbPassengerState(TbPassenger.State.jy);
		int passengerZc = passengerService.countTbPassengerState(TbPassenger.State.zc);
		int passengerZx = passengerService.countTbPassengerState(TbPassenger.State.zx);
		int passengerAll = passengerJy+passengerZc+passengerZx;
		//线路
		int lines = lineService.countLine();


		
		//会员管理
//		int pendingAudit = userService.pendingAudit();
		model.addAttribute("rolename",rolename);
		model.addAttribute("orderQx",orderQx);
		model.addAttribute("orderYwc",orderYwc);
		model.addAttribute("orderCs",orderCs);
		model.addAttribute("orderAll",orderAll);
		model.addAttribute("driverXb",driverXb);
		model.addAttribute("driverSb",driverSb);
		model.addAttribute("driverAll",driverAll);
		model.addAttribute("passengerJy",passengerJy);
		model.addAttribute("passengerZc",passengerZc);
		model.addAttribute("passengerZx",passengerZx);
		model.addAttribute("passengerAll",passengerAll);
		model.addAttribute("lines",lines);
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

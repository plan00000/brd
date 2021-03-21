package com.zzy.brd.mobile.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.enums.SmsAuthcodeSource;
import com.zzy.brd.mobile.web.dto.req.user.ReqUserForgetPassWordDTO;
import com.zzy.brd.service.UserService;

/**
 * 忘记密码，
 * @author lzh 2016-9-26
 *
 */
@Controller
@RequestMapping("weixin/user/forgetPassword")
public class WeixinForgetPasswordController {
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转到修改密码
	 * @param model
	 * @return
	 */
	@RequestMapping("toResetPassword")
	public String toResetPassword(Model model){
		
		return "mobile/user/forgetpassword";
	}
	
	/**
	 * 判断手机是否已注册
	 * @param phone
	 * @param request
	 * @return
	 */
	@RequestMapping("isPhoneRegister")
	@ResponseBody
	public RepSimpleMessageDTO isPhoneRegister(String phone,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		/*User user = userService.findByMobileno(phone);*/
		List<User> users = userService.findByMobileno1(phone);
		if(users.isEmpty()){
			rep.setCode(0);
			rep.setMes("该手机号未注册");
			return rep;
		}
		/*if(user.getUserType() == UserType.ADMIN||user.getUserType() ==UserType.CONTROLMANAGER|| user.getUserType() == UserType.SALESMAN ){
			rep.setCode(0);
			rep.setMes("用户为"+user.getUserType().getStr()+",请联系后台管理员更改");
			return rep;
		}*/
		rep.setCode(1);
		rep.setMes("success");
		return rep;
	}
	/**
	 * 获取短信忘记密码验证码
	 * @param phone
	 * @param request
	 * @return
	 */
	@RequestMapping("getPhoneAuthcode/forgetPassword")
	@ResponseBody
	public RepSimpleMessageDTO getPhoneAuthcode(String phone,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		SmsAuthcodeSource smsAuthcodeSource = SmsAuthcodeSource.USER_RETPASS;
		if(userService.sendAuthcode(phone, smsAuthcodeSource)){
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("验证码发送失败");
		return rep;
	}
	/**
	 * 忘记密码-重置密码
	 * @param dto
	 * @param result
	 * @return
	 */
	@RequestMapping("ResetPassword")
	@ResponseBody
	public RepSimpleMessageDTO forgetPassword(@Valid ReqUserForgetPassWordDTO dto
			,BindingResult result){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		if(result.hasErrors()){
			rep.setCode(0);
			rep.setMes(result.getAllErrors().get(0).getDefaultMessage());
			return rep;
		}
		//判断手机号是否已被注册
		List<User> users = userService.findByMobileno1(dto.getMobileno());
		if(users.isEmpty()){
			rep.setCode(0);
			rep.setMes("该手机号未注册");
			return rep;
		}
		for(User user:users){
			if(user!=null){
				if(user.getUserType().ordinal() == 4){
					rep.setCode(0);
					rep.setMes("请联系公司人员前往系统后台修改密码");
					return rep;
				}
			}
		}
		//判断验证码是否有效
		int v = userService.validateSmsAuthcode(dto.getMobileno(),
				dto.getPhoneAuthcode(), SmsAuthcodeSource.USER_RETPASS);
		if (v == 1 || v == 2) {
			rep.setCode(0);
			rep.setMes("手机验证码错误");
			return rep;
		}
		if (v == 3) {
			rep.setCode(0);
			rep.setMes("手机验证码过期");
			return rep;
		}
		User user = null;
		for(User userone:users){
			if(userone.getUserType().ordinal() ==0 || userone.getUserType().ordinal() ==1|| userone.getUserType().ordinal() ==2 ||userone.getUserType().ordinal() ==3){
				user = userone;
				user.setPassword(dto.getPassword());
				if(!userService.resetPassword(user)){
					rep.setCode(0);
					rep.setMes("密码修改失败");
					return rep;
				}
				Subject subject = SecurityUtils.getSubject();
				if (subject.isAuthenticated() || subject.isRemembered()) {
					try {
						subject.logout();
					} catch (Exception e) {
					}
				}
			}else{
				rep.setCode(0);
				rep.setMes("该手机号未注册");
				return rep;
			}
		}
		
		rep.setCode(1);
		rep.setMes("success");
		return rep;
	}
}

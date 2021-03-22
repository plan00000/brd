package com.zzy.brd.controller.admin.login;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.constant.GlobalData;
import com.zzy.brd.entity.User;
import com.zzy.brd.service.LoginlogService;
//import com.zzy.brd.service.UserInfoEmployeeService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.principal.ShiroUser;

/**
 * 后台-管理员登录控制器
 * 
 * @author lzh
 *
 */
@Controller
@RequestMapping("/admin/login")
public class AdminLoginController {

	@Autowired
	private UserService userService;
/*	@Autowired
	private UserInfoEmployeeService userInfoPublicService;*/
	@Autowired
	private LoginlogService loginlogService;
	
	/****
	 * 至后台登录界面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String toLogin(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (GlobalData.getCurrentUser() != null) {
			return this.succ(request,response);
		}
		return "admin/login/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
			
			Model model) {
		boolean flag = false; // true:表示浏览器多个标签登陆导致的失败
		boolean errMsg = false;
		if (GlobalData.getCurrentUser() != null) {
			SecurityUtils.getSubject().getSession()
					.removeAttribute(Constant.SYS_ERROR_LOGIN_TIMES);
			flag = true;
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,
				userName);
		Integer times = 0;
		if (null != SecurityUtils.getSubject().getSession()
				.getAttribute(Constant.SYS_ERROR_LOGIN_TIMES)) {
			times = (Integer) SecurityUtils.getSubject().getSession()
					.getAttribute(Constant.SYS_ERROR_LOGIN_TIMES);
		}
		SecurityUtils.getSubject().getSession()
				.setAttribute(Constant.SYS_ERROR_LOGIN_TIMES, times + 1);
		if (null != SecurityUtils.getSubject().getSession()
				.getAttribute("KEY_CAPTCHA_ERROR")
				&& SecurityUtils.getSubject().getSession()
						.getAttribute("KEY_CAPTCHA_ERROR")
						.equals("captcha error")) {
			errMsg = true;
			model.addAttribute("errMsg", "验证码错误");
			
		} 
		if(null != SecurityUtils.getSubject().getSession()
				.getAttribute(Constant.SYS_ERROR_LOGIN_PERMISSION)
				&& SecurityUtils.getSubject().getSession()
				.getAttribute(Constant.SYS_ERROR_LOGIN_PERMISSION)
				.equals(Constant.SYS_ERROR_LOGIN_PERMISSION)){
			model.addAttribute("errMsg","该用户名无权限登录后台，请联系管理员处理");
			errMsg = true;
		}else if (null != SecurityUtils.getSubject().getSession()
				.getAttribute(Constant.SYS_ERROR_LOGIN_USER_DISABLED)
				&& SecurityUtils.getSubject().getSession()
						.getAttribute(Constant.SYS_ERROR_LOGIN_USER_DISABLED)
						.equals(Constant.SYS_ERROR_LOGIN_USER_DISABLED)) {
			model.addAttribute("errMsg", "该用户名已被禁用，请联系管理员处理");
			errMsg = true;
		} else {
			if (!flag) {
				if(!errMsg){
					model.addAttribute("errMsg", "用户名或密码错误");
				}
			} else {
				model.addAttribute("errMsg", "你的浏览器窗口开启了多个标签登录了系统,请关闭浏览器重新登录");
			}
		}
		return "admin/login/login";
	}
	@RequestMapping("succ")
	public String succ(HttpServletRequest request, ServletResponse response) {
		ShiroUser shiroUser = GlobalData.getCurrentUser();
		User user = userService.findUserById(shiroUser.getId());
		SecurityUtils.getSubject().getSession().setAttribute(Constant.SESSION_CURUSER_UUID, GlobalData.getCurrentUser().getId());
		SecurityUtils.getSubject().getSession().setAttribute(Constant.SYS_ERROR_LOGIN_TIMES, 0);		
		SecurityUtils.getSubject().getSession().setAttribute(Constant.SESSION_CURUSER_USERTYPE, user.getUserType());
//		userService.updateLoginInfo(user, request);
//		userInfoPublicService.updateLoginInfo(user, request);
		//添加登录日志
		/*syslogService.addLoginLog(user, request.getRemoteAddr());*/
		if(!user.getUserType().equals(User.UserType.ADMIN)){
			loginlogService.addLoginlog(user, request);
		}		
		return "redirect:/admin/main";
	}

	@RequestMapping("logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			subject.logout();
		}
		return "redirect:/admin/login";
	}
}

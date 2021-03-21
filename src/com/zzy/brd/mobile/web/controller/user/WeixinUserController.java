package com.zzy.brd.mobile.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.SessionKey;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinUser.SexType;
import com.zzy.brd.entity.WeixinUser.SubscribeType;
import com.zzy.brd.service.LoginlogService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinUserService;
import com.zzy.brd.shiro.principal.ShiroUser;
import com.zzy.brd.shiro.token.CaptchaUsernamePasswordToken;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.weixin.WeixinCommonUtil;

/**
 * 微信-用户登录
 * 
 * @author lzh 2016/9/18
 *
 */
@Controller
@RequestMapping("weixin/user")
public class WeixinUserController {
	Logger logger = LoggerFactory.getLogger(WeixinUserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private LoginlogService loginlogService;
	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	private SysInfoService sysInfoService;
	/***
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping("toLogin")
	public String loginPage(Model model			
		,@RequestParam(value = "openId",required = false,defaultValue = "") String openId){
		model.addAttribute("openId", openId);
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			// 已登录或已记住登录,跳转至首页
				return "redirect:/weixin/index/toIndex";
		}
		return "mobile/user/login";
	}

	/***
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO login(String username, char[] password,
			@RequestParam(required = false) String authcode,
			@RequestParam(required = false) boolean rememberMe, Model model,
			@RequestParam(value = "openId",required = false,defaultValue = "") String openId,
			HttpServletRequest request) {
		WeixinUser weixinUser = null;
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(
				username, password, rememberMe, session.getHost(), authcode, 2);
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			// 找不到用户异常
			dto.setCode(0);
			dto.setMes("用户名或密码错误");
			return dto;
		} catch (LockedAccountException e) {
			dto.setCode(0);
			dto.setMes("该账号已被禁用");
			return dto;
		} catch (DisabledAccountException e) {
			dto.setCode(0);
			dto.setMes("该账号已被删除");
			return dto;
		} /*
		 * catch(AuthorityLoginException e){
		 * 
		 * }
		 */catch (AuthenticationException e) {
			String errorMes = "用户名或密码错误";
			if (session.getAttribute(SessionKey.SESSION_USER_AUTH_ERROR) != null) {
				errorMes = (String) session
						.getAttribute(SessionKey.SESSION_USER_AUTH_ERROR);
			}
			dto.setCode(0);
			dto.setMes(errorMes);
			return dto;
		}

		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		session.setAttribute(SessionKey.SESSION_CURUSER_USERTYPE,
				shiroUser.getUserType());
		session.setAttribute(SessionKey.SESSION_CURUSER_UUID, shiroUser.getId());
		//
		User user = userService.findById(shiroUser.getUserId());
		weixinUser = user.getWeixinUser();
		String appid = null;
    	String appsecret = null;
    	SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		if(weixinUser==null){
			weixinUser = new WeixinUser();
			if(openId != null && openId.length() != 0){
				weixinUser.setOpenid(openId);
				weixinUser.setSubscribe(SubscribeType.YES);
				weixinUser.setSubscribeTime(DateUtil.getNowDate());
				if(sysInfo!=null){
					appid = sysInfo.getAppid();
					appsecret = sysInfo.getAppsecret();
				}
				WeixinCommonUtil.getPersonInformation(openId, appid, appsecret, weixinUser);
				}
			}else{
				if(openId != null && openId.length() != 0){
					weixinUser.setOpenid(openId);
					if(sysInfo!=null){
						appid = sysInfo.getAppid();
						appsecret = sysInfo.getAppsecret();
					}
					WeixinCommonUtil.getPersonInformation(openId, appid, appsecret, weixinUser);
				}
			}
			if(!weixinUserService.editUser(weixinUser)){
				dto.setCode(0);
				dto.setMes("openId存入失败");
				return dto;
			}
		user.setWeixinUser(weixinUser);
		if(!userService.editUser(user)){
			dto.setCode(0);
			dto.setMes("微信用户存入失败");
			return dto;
		}
		user.setLastlogindate(DateUtil.getNowTimestamp());
		user.setLogindate(DateUtil.getNowTimestamp());
		//增加对应登录次数
		user.setLoginTimes(user.getLoginTimes()+1);
		userService.editUser(user);	
		//增加登录日志		
		loginlogService.addLoginlog(user, request);
		dto.setCode(1);
		dto.setMes("succ");
		return dto;
	}

	/***
	 * 退出
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("logout")
	public String loginOut(Model model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			try {
				subject.logout();
			} catch (Exception e) {
			}

		}
		return "redirect:/weixin/user/toLogin";
	}
}

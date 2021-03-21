package com.zzy.brd.mobile.web.controller.generaluser;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zzy.brd.dao.SysInfoDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Advertisement;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinUser.SubscribeType;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.controller.usercenter.WeixinUsercenterMainController;
import com.zzy.brd.service.AdvertisementService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinUserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.string.StringUtil;
import com.zzy.brd.util.weixin.WeixinCommonUtil;

/**
 * 微信站 -普通会员中心
 * 
 * @author lzh 2016/9/20
 *
 */
@Controller
@RequestMapping("weixin/generalUserCenter")
public class WeixinGeneralUserCenterController {
	@Autowired
	private UserService userService;
	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private UserInfoBothService userInfoBothService;
	
	private static final Logger log = LoggerFactory.getLogger(WeixinGeneralUserCenterController.class);
	/**
	 * 普通会员个人中心主页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("main")
	public String main(@RequestParam(value = "isManager", required = true, defaultValue = "2") int isManager,
			Model model, HttpServletRequest request,
			@RequestParam(value="code",required=false)String code,
			@RequestParam(value="getOpenId",required=false)String getOpenId,
			RedirectAttributes attr) {
			Long userId = ShiroUtil.getUserId(request);
			WeixinUser weixinUser = new WeixinUser();
			BigDecimal brokerageCanWithdraw = BigDecimal.ZERO;
			SysInfo sysInfo = sysInfoService.getSysInfo(1l);
			String openId =null;
	    	String appid = sysInfo.getAppid();;
	    	String appsecret = sysInfo.getAppsecret();;
	    	User user = null;
	    	
			if(userId !=null){
				user = userService.findUserById(userId);
				if(user!=null){
					weixinUser = user.getWeixinUser();
					if(weixinUser!=null){
						openId = weixinUser.getOpenid();
					}
					UserInfoBoth userInfoBoth = user.getUserInfoBoth();
					brokerageCanWithdraw = userInfoBoth.getBrokerageCanWithdraw();
				}
				if(StringUtils.isNotBlank(getOpenId)) {
					openId = getOpenId;
					weixinUser.setOpenid(getOpenId);
				}
			int subscribe = 0;
			if(StringUtils.isNotBlank(openId)){
				WeixinCommonUtil.getPersonInformation(openId, appid, appsecret, weixinUser);
				subscribe = WeixinCommonUtil.isFollow(openId,appid,appsecret);
				if(subscribe==0) {
					weixinUser.setSubscribe(SubscribeType.NO);
				} else {
					weixinUser.setSubscribe(SubscribeType.YES);
				}				
				weixinUserService.editUser(weixinUser);
				System.out.println("直接查询--关注:"+subscribe);
			}
			if(subscribe==0 && code==null){
				attr.addAttribute("red", "generalUserCenter");
				return "redirect:/wechat/weixinLogin";
			}
			
			//创建用户
			if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(getOpenId) && subscribe==0) { 
				weixinUser.setOpenid(getOpenId);
				weixinUser.setSubscribe(SubscribeType.YES);
				weixinUser.setSubscribeTime(DateUtil.getNowDate());
				WeixinCommonUtil.getPersonInformation(getOpenId, appid, appsecret, weixinUser);	
				subscribe=WeixinCommonUtil.isFollow(getOpenId,appid,appsecret);
				weixinUserService.editUser(weixinUser);
				System.out.println("创建新weixinUser用户--关注:"+subscribe);
			} 	
			
			model.addAttribute("user", user);
			model.addAttribute("brokerageCanWithdraw", brokerageCanWithdraw);
			model.addAttribute("sysInfo", sysInfo);
			model.addAttribute("isManager", isManager);
			model.addAttribute("weixinUser", weixinUser);
			
			log.info(user.getMobileno()+"关注："+ subscribe);
			
			model.addAttribute("subscribe", subscribe);
		}
		return "mobile/generaluser/generalUserCenter";
	}

	/**
	 * 成为融资经理
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("becomeManager")
	public String becomeManager(
			@RequestParam(value = "isManager", required = false, defaultValue = "1") int isManager,
			Model model, HttpServletRequest request) {
		Advertisement advertisement = advertisementService.getAdvertisementByPosition(Advertisement.AdPositionType.FINANCMANAGER);
		model.addAttribute("advertisement", advertisement);
		model.addAttribute("isManager", isManager);
		return "mobile/generaluser/becomeManage";
	}
	
	/**
	 * 验证身份证并保存真实姓名
	 * @param realname
	 * @param idcard
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="perfectInformation",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO perfectInformation( 
			@RequestParam(value = "realname", required = true) String realname,
			@RequestParam(value = "idcard", required = true) String idcard,
			Model model, HttpServletRequest request ){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		Boolean isRealName = isRealName(realname, idcard);
		if(isRealName){
			dto.setCode(1);
			dto.setMes("身份验证失败，请输入正确信息！");
			return dto;
		}
		Long userId = ShiroUtil.getUserId(request);
		//userId = Long.valueOf("1");//测试
		if(userId !=null){
			User user = userService.findUserById(userId);
			UserInfoBoth userInfoBoth = user.getUserInfoBoth();
			user.setRealname(realname);
			user.setUserType(UserType.MANAGER);
			userInfoBoth.setExpands(idcard);
			userInfoBothService.editUserInfoBoth(userInfoBoth);
			userService.editUser(user);
		}
		
		dto.setCode(2);
		return dto;
	}
	/**
	 * 验证真实身份  
	 * 未完成
	 * @param realname
	 * @param idcard
	 * @return false
	 */
	public Boolean isRealName(String realname,String idcard){
		return false;
	}
	/**
	 * 修改昵称
	 * @param realname
	 * @param idcard
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateNickname",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO updateNickname( 
			@RequestParam(value = "nickname", required = true) String nickname,
			Model model, HttpServletRequest request ){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
	//	userId = Long.valueOf("1");//测试
		User user = userService.findUserById(userId);
		user.setUsername(nickname);
		WeixinUser weixinUser = user.getWeixinUser();
		weixinUser.setNickname(nickname);
		weixinUserService.editUser(weixinUser);
		userService.editUser(user);
		dto.setCode(1);
		return dto;
	}
	/**
	 * 判断用户是否关注公众号
	 * @param weixinUserId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="isFollow",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO isFollow( 
			@RequestParam(value = "weixinUserId", required = false,defaultValue="") Long weixinUserId,
			Model model, HttpServletRequest request ){
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		WeixinUser weixinUser =null;
		String openId =null;
    	String appid = null;
    	String appsecret = null;
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		if(sysInfo!=null){
			appid = sysInfo.getAppid();
			appsecret = sysInfo.getAppsecret();
		}
		if(weixinUserId!=null){
			weixinUser = weixinUserService.getWeixinUserById(weixinUserId);	
			if(weixinUser!=null){
				openId = weixinUser.getOpenid();
			}
		}
		int subscribe = WeixinCommonUtil.isFollow(openId,appid,appsecret);
		if(subscribe==0){
			dto.setCode(0);
			dto.setMes("尚未关注");
		}else if(subscribe==1){
			dto.setCode(1);
			dto.setMes("已关注");
		}else{
			dto.setCode(0);
			dto.setMes("内部服务器错误");
		}
		return dto;
	}
	
}

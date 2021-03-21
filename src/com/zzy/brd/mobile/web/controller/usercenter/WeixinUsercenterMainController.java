package com.zzy.brd.mobile.web.controller.usercenter;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zzy.brd.controller.admin.activity.AdminQrCodeActivityController;
import com.zzy.brd.dao.SysInfoDao;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinUser.SubscribeType;
import com.zzy.brd.entity.UserBankinfo;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.LoginlogService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserBankinfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinUserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.tld.PriceUtils;
import com.zzy.brd.util.weixin.WeixinCommonUtil;

/**
 * 微信站 -个人中心
 * @author lzh 2016/9/20
 *
 */
@Controller
@RequestMapping("weixin/usercenter")
public class WeixinUsercenterMainController {
	@Autowired
	private UserService userService;
	@Autowired	
	private UserBankinfoService userBankinfoService; 
	@Autowired
	private SysInfoDao sysInfoDao;
	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	private LoginlogService loginlogService;
	@Autowired
	private SysInfoService sysInfoService;
	
	
	private static final Logger log = LoggerFactory.getLogger(WeixinUsercenterMainController.class);
	
	/**
	 * 个人中心主页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("main")
	public String main(Model model,HttpServletRequest request,
			@RequestParam(value="code",required=false) String code
			,RedirectAttributes attr,@RequestParam(value="getOpenId",required=false)String getOpenId){
		Long userId = ShiroUtil.getUserId(request);
		if(userId !=null){
			User user = userService.findUserById(userId);
			if(user.getUserType().equals(User.UserType.USER)){			
				return "redirect:/weixin/generalUserCenter/main";			
			}
			model.addAttribute("user", user);
			SysInfo sysInfo = sysInfoService.getSysInfo(1l);
			String openId =null;
	    	String appid = sysInfo.getAppid();
	    	String appsecret = sysInfo.getAppsecret();
	    	WeixinUser weixinUser = user.getWeixinUser();
	    	
			if(weixinUser!=null){
				openId = weixinUser.getOpenid();
			} else {
				weixinUser =new WeixinUser();
			}
			
			int subscribe = 1;
			System.out.println("openid:"+openId+"---getOpenId:"+getOpenId);
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
				attr.addAttribute("red", "usercenter");
				return "redirect:/wechat/weixinLogin";
			}
			//创建新的weixinUser用户
			if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(getOpenId) && subscribe==0) { 
				weixinUser.setOpenid(getOpenId);
				weixinUser.setSubscribe(SubscribeType.YES);
				weixinUser.setSubscribeTime(DateUtil.getNowDate());
				WeixinCommonUtil.getPersonInformation(getOpenId, appid, appsecret, weixinUser);	
				subscribe=WeixinCommonUtil.isFollow(getOpenId,appid,appsecret);
				weixinUserService.editUser(weixinUser);
				System.out.println("创建新weixinUser用户--关注:"+subscribe);
			} 
			try{
				log.info("微信用户openid："+user.getWeixinUser().getOpenid()+"号码:"+user.getMobileno()+"关注："+ subscribe);
			} catch(Exception e) {
				log.info(user.getMobileno()+"关注："+ subscribe);
			}
			model.addAttribute("subscribe", subscribe);
			
			model.addAttribute("sysInfo", sysInfo);
			
			List<UserBankinfo> userBankList = userBankinfoService.getUserBankByUser(user);

			model.addAttribute("userBankSize",userBankList.size());
			
			if(user.getUserType()== UserType.ADMIN || user.getUserType() == UserType.CONTROLMANAGER){
				model.addAttribute("totalBrokerage", "0.00");
			}else{
				String totalBrokerageStr = "0.00";
				BigDecimal totalBrokerage = user.getUserInfoBoth().getBrokerageCanWithdraw().add(user.getUserInfoBoth().getBrokerageHaveWithdraw())
						.add(user.getUserInfoBoth().getBrokerageWithdrawing());	
				if(totalBrokerage.compareTo(new BigDecimal("9999.99"))==-1){
						totalBrokerageStr = PriceUtils.normalPrice(totalBrokerage);
						model.addAttribute("flage", "true");
					}else{
						totalBrokerageStr = PriceUtils.tenThousandPrice(totalBrokerage);
						model.addAttribute("flage", "false");
					}
					model.addAttribute("totalBrokerage", totalBrokerageStr);
					BigDecimal aleardWithdraw = user.getUserInfoBoth().getBrokerageHaveWithdraw();
					if(aleardWithdraw.compareTo(new BigDecimal("9999.99"))==-1){
						model.addAttribute("otherFalge", true);
					}else{
						model.addAttribute("otherFalge", false);
					}
			}
			String hotline ="";
			String parterline="";
			if(sysInfo!=null){
				parterline = sysInfo.getCooperatePhone();
				hotline = sysInfo.getHotline();
			}
			model.addAttribute("hotline", hotline);
			model.addAttribute("parterline", parterline);

			
		}
		return "mobile/usercenter/usermain";
	}
	
	
	
	
}

package com.zzy.brd.controller.weixin;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzy.brd.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.assertj.core.api.BigDecimalAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.sd4324530.fastweixin.api.response.BaseResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.util.JSONUtil;
import com.github.sd4324530.fastweixin.util.NetWorkCenter;
import com.zzy.brd.entity.CheckModel;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.util.string.StringUtil;
import com.zzy.brd.util.weixin.NewInfoTemplate;
import com.zzy.brd.util.weixin.WeixinCommonUtil;
 
 
@Controller
@RequestMapping("/wechat")
public class WeChatController {
	private static Logger log = LoggerFactory.getLogger(WeChatController.class);
    /*@Autowired
    private WeixinTemplateService weixinTemplateService;*/
	@Autowired
	private UserService userService;
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private OrderformService orderformService;
	
	@Autowired
	private WeChatService weChatService;

    /**
     * 开发者模式token校验
     *
     * @param wxAccount 开发者url后缀
     * @param response
     * @param tokenModel
     * @throws ParseException
     * @throws IOException
     */
    /*@RequestMapping(value = "/check/{wxToken}", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String validate(@PathVariable("wxToken")String wxToken,CheckModel tokenModel) throws ParseException, IOException {
        return weixinTemplateService.validate(wxToken,tokenModel);
    }*/

	/**
	 * 接收微信推送事件
	 * @param wxToken
	 * @param tokenModel
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	/*@RequestMapping(value = "/check/{wxToken}", method = RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String receiveMessage(@PathVariable("wxToken")String wxToken,CheckModel tokenModel, HttpServletRequest request) throws ParseException, IOException {
		if ("error".equals(weixinTemplateService.validate(wxToken, tokenModel))) {
			return "error";
		}

		weChatService.processRequest(request);
		return "";
	}*/
    
    /**
     * 发送模板消息
     * appId 公众账号的唯一标识
     * appSecret 公众账号的密钥
     * openId 用户标识
     */
    /*@RequestMapping(value = "send", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public int send_template_message(HttpServletRequest request) {
    	Orderform orderform = orderformService.findOrderById(1l);
		NewInfoTemplate newInfoTemplate = weixinTemplateService.getLoadSubmit(orderform,request);
		int res = weixinTemplateService.sendTemplateMessage(newInfoTemplate);
*//*
    	Long userId = Long.parseLong("2");
    	BigDecimal money = BigDecimal.valueOf(10000.00);*//*
    	return res;
    }*/
    
    
	/***
	 * 微信登录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("weixinLogin")
	public String weixinLogin(Model model,HttpServletRequest request
			,@RequestParam(value = "code",required = false,defaultValue = "") String code
			,@RequestParam(value="red",required=false,defaultValue="default") String red,
			RedirectAttributes attr) throws Exception {
    	String appid = null;
    	String appsecret = null;
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		if(sysInfo!=null){
			appid = sysInfo.getAppid();
			appsecret = sysInfo.getAppsecret();
		}
		String url = WeixinCommonUtil.getCode(request,appid,red);
		if(code != null && code.length() > 0){
			String openId = WeixinCommonUtil.getOpenId(code,appid,appsecret);
			model.addAttribute("code", code);
			model.addAttribute("openId", openId);
			if(!red.equals("default") || red.length()==0){
				if(red.equals("generalUserCenter"))	{
					attr.addAttribute("getOpenId", openId);
					attr.addAttribute("code", code);
					return "redirect:/weixin/generalUserCenter/main";
 				} else {
 					attr.addAttribute("getOpenId",openId);
 					attr.addAttribute("code", code);
 					return "redirect:/weixin/usercenter/main";
 				}
			} else {
				return "redirect:/weixin/user/toLogin";
			}
		}else{	
			return "redirect:"+url;
		}
	}

	
	/***
	 * 微信登录
	 * 
	 * @return
	 */
	@RequestMapping("weixinLogin/MP_verify_hF7KtaV0tZCv1rHY.txt")
	public String wlmp(Model model) {
		return "mobile/user/mp";
	}
	/**
	 * 微信接收推送消息
	 * @param out
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(value = "/getMessage", method = RequestMethod.POST)
    @ResponseBody
    public void wechatServicePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        String responseMessage = weChatService.processRequest(request);
        out.print(responseMessage);
        out.flush();
    }*/
} 
package com.zzy.brd.mobile.web.controller.index;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zzy.brd.entity.Advertisement;
import com.zzy.brd.entity.Advertisement.AdSourceType;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.Product.isDisplay;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.AdvertisementService;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinUserService;
import com.zzy.brd.util.weixin.WeixinCommonUtil;

/**
 * 微信-首页
 * 
 * @author csy 2016/9/22
 *
 */
@Controller
@RequestMapping("weixin/index")
public class WeixinIndexController {
	Logger logger = LoggerFactory.getLogger(WeixinIndexController.class);
	@Autowired
	private InformationService informationService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private WeixinUserService weixinUserService;
	/***
	 * 进入首页
	 * 
	 * @return
	 */
	@RequestMapping("toIndex")
	public String toIndex(Model model,HttpServletRequest request) {
		Long userId = ShiroUtil.getUserId(request);
		//用户标志 
		String userFlag ="";
		if(userId == null){
			userFlag = "user";
		}else{
			User user = userService.findById(userId);
			if(user.getUserType() == UserType.USER){
				userFlag = "user";
			}else{
				userFlag = "all";
			}
			//更新用户信息
			WeixinUser weixinUser = user.getWeixinUser();
			if(weixinUser !=null) {
		    	String appid = null;
		    	String appsecret = null;
				SysInfo sysInfo = sysInfoService.getSysInfo(1l);
				if(sysInfo!=null){
					appid = sysInfo.getAppid();
					appsecret = sysInfo.getAppsecret();
					if(StringUtils.isNotBlank(weixinUser.getOpenid())){
						WeixinCommonUtil.getPersonInformation(weixinUser.getOpenid(), appid, appsecret, weixinUser);	
					}
					weixinUserService.editUser(weixinUser);
				}
			}
		}
		Map<String, Object> productSearchParams = new HashMap<String, Object>();
		productSearchParams.put("EQ_isDisplay",isDisplay.YES );
		productSearchParams.put("EQ_status",Product.Status.NORMAL );
		if(userFlag.equals("user")){
			productSearchParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
		}	
		Page<Product> products = productService.pageDisplayProduct(productSearchParams);
		Map<String, Object> InformationSearchParams = new HashMap<String, Object>();
		Page<Information> helpList  = informationService.listInformation(InformationSearchParams, 1, "sortid:asc",5);
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		String scrollBall = sysInfo.getScrollBall();
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(scrollBall)){
			String[] srollString = scrollBall.split(";");
			List<String> scrollList = Arrays.asList(srollString);
			Collections.shuffle(scrollList);
			for(String scroll:scrollList){
				sb.append(scroll);
			}
		}
		List<Advertisement> banners = advertisementService.listAdvertisements(AdSourceType.WECHAT);
		model.addAttribute("banners", banners);
		model.addAttribute("helpList", helpList);
		model.addAttribute("products", products.getContent());
		model.addAttribute("scrollBall", sb);
		model.addAttribute("sysInfo", sysInfo);
		//更新访问数量
		sysInfoService.updatePv();
		return "mobile/index/index";
	}

}

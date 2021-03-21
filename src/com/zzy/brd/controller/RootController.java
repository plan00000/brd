package com.zzy.brd.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zzy.brd.entity.Advertisement;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.Advertisement.AdPositionType;
import com.zzy.brd.entity.Advertisement.AdSourceType;
import com.zzy.brd.service.AdvertisementService;
import com.zzy.brd.service.FriendshipLinkService;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserService;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private InformationService informationService;
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired 
	private FriendshipLinkService friendshipLinkService;
	
	@RequestMapping
	public String root(Model model) {
		List<Advertisement> banners = advertisementService.listAdvertisements(AdSourceType.PC);
		Advertisement middle = advertisementService.getPCAdvertisementByPosition(AdPositionType.MIDDLE);
		Advertisement leftBottom = advertisementService.getPCAdvertisementByPosition(AdPositionType.LEFTBOTTOM);
		Advertisement rightBottom = advertisementService.getPCAdvertisementByPosition(AdPositionType.RIGHTBOTTOM);
		Advertisement scrollblow = advertisementService.getPCAdvertisementByPosition(AdPositionType.SCROLLBLOW);
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
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		model.addAttribute("scrollBall", sb);
		model.addAttribute("banners", banners);
		model.addAttribute("middle", middle);
		model.addAttribute("leftBottom", leftBottom);
		model.addAttribute("rightBottom", rightBottom);
		model.addAttribute("scrollblow", scrollblow);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("pcSelect", 1);
		return "pc/pcmain";
	}
	
	@RequestMapping(value="MP_verify_hF7KtaV0tZCv1rHY.txt")
	public String mp() {
		return "mobile/user/mp";
	}
	
}

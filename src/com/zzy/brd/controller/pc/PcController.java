package com.zzy.brd.controller.pc;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzy.brd.entity.Advertisement;
import com.zzy.brd.entity.Advertisement.AdPositionType;
import com.zzy.brd.entity.Advertisement.AdSourceType;
import com.zzy.brd.entity.FriendshipLink;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.Information.InformationType;
import com.zzy.brd.service.AdvertisementService;
import com.zzy.brd.service.FriendshipLinkService;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.SysInfoService;

/**
 * pc端
 * @author lzh 2016-10-7
 *
 */
@Controller
@RequestMapping("pc")
public class PcController {
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private InformationService informationService;
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired 
	private FriendshipLinkService friendshipLinkService;
	
	/**
	 * pc首页
	 * @param model
	 * @return
	 */
	@RequestMapping("main")
	public String pcMain(Model model){
		return "redirect:/";
	}
	/**
	 * 关于我们
	 * @param model
	 * @return
	 */
	@RequestMapping("about")
	public String pcAbout(Model model){
		Information about = informationService.findInformationByType(InformationType.ABOUT);
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("about", about);
		model.addAttribute("pcSelect", 4);
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		return "pc/about";
	}
	/**
	 * pc收徒赚佣
	 * @param model
	 * @return
	 */
	@RequestMapping("apprentice")
	public String pcApprentice(Model model){
		Information about = informationService.findInformationByType(InformationType.APPRENTIC);
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("about", about);
		model.addAttribute("pcSelect", 3);
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		return "pc/apprentice";
	}
	/**
	 * pc服务协议
	 * @param model
	 * @return
	 */
	@RequestMapping("agreement")
	public String pcServiceAgreement(Model model){
		Information agreement = informationService.findInformationByType(InformationType.AGREEMENT);
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("agreement", agreement);
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		return "pc/serviceAgreement";
	}
	
	/**
	 *PC站文章信息 
	 **/
	@RequestMapping(value="content/{informationId}")
	public String pcInformation(@PathVariable long informationId,Model model){
		Information information = informationService.findById(informationId);
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("information", information);
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		model.addAttribute("pcSelect", 5);
		return "pc/information/information";
	}
	
	/**
	 * pc 精彩资讯
	 * */
	@RequestMapping(value="infomation")
	public String pcInfomationMain(@RequestParam(value="page",required=false,defaultValue="1") int pageNumber
			,Model model){
		Map<String,Object> searchParams = new HashMap<String,Object>();		
		searchParams.put("EQ_type", Information.InformationType.PC_ACTIVITY);
		searchParams.put("EQ_status", Information.Status.YES);
		Page<Information> page = informationService.listInformation(searchParams, pageNumber,"");
		model.addAttribute("page", page);
		model.addAttribute("pcSelect", 5);
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		return "pc/information/activityInformation";
	}
	
}

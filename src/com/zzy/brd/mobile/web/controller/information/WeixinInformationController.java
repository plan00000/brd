package com.zzy.brd.mobile.web.controller.information;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zzy.brd.entity.Information;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.util.date.DateUtil;

/**
 * 微信-首页
 * 
 * @author csy 2016/9/22
 *
 */
@Controller
@RequestMapping("weixin/information")
public class WeixinInformationController {
	Logger logger = LoggerFactory.getLogger(WeixinInformationController.class);
	@Autowired
	private InformationService informationService;

	/**
	 * 转至活动资讯
	 * 
	 * @return
	 */
	@RequestMapping("toActivityInformation")
	public String toActivityInformation(Model model) {
		List<Information> list = informationService.findInfomationList();
		model.addAttribute("informationList", list);
		return "mobile/information/consultation";
	}

	/**
	 * 转至帮助中心
	 * 
	 * @return
	 */
	@RequestMapping("toHelpConter")
	public String toHelpConter(Model model) {
		List<Information> helpList = informationService.helpCenterList();
		model.addAttribute("helpList", helpList);
		return "mobile/information/helpConter";
	}

	/**
	 * 转至活动列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toArticleDetails/{id}")
	public String articleDetails(@PathVariable long id, Model model) {
		Information information = informationService.findById(id);
		if(information==null){
			return "error/mobile404";
		}
		Date date = information.getAddDate();
		String addDate = DateUtil.formatDate(date);
		model.addAttribute("information", information);
		model.addAttribute("addDate", addDate);
		return "mobile/information/articleDetails";
	}
}

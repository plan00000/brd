package com.zzy.brd.controller.admin.reportStatistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepSellerStatisticAddressDTO;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepSellerStatisticDTO;
import com.zzy.brd.service.UserInfoSellerService;

/**
 * @author:xpk
 *    2016年11月30日-上午9:11:01
 **/
@Controller
@RequestMapping(value="admin/reportStatistics/sellerStatistics")
public class AdminSellerStatisticsController {
	
	@Autowired 
	private UserInfoSellerService userInfoSellerService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String sellerStatistics (Model model) {
		
		model.addAttribute("baidu_map_api_for_web", ConfigSetting.baidu_map_api_for_web);
		return "admin/reportStatistics/sellerStatistics";
	}
	
	@RequestMapping(value="data",method=RequestMethod.GET)
	@ResponseBody
	public RepSellerStatisticDTO data() {
		RepSellerStatisticDTO rep = new RepSellerStatisticDTO();
		List<RepSellerStatisticAddressDTO> data = userInfoSellerService.listSeller();
		rep.setStatus(0);
		rep.setData(data);		
		return rep;
	}
	
	
	
}

package com.zzy.brd.controller.admin.reportStatistics;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zzy.brd.dto.rep.admin.reportStatistics.RepStatisticBrokerageDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.service.BrokerageStatisticsService;

/**
 * @author:csy
 *    2016年10月18日
 **/
@Controller
@RequestMapping(value="admin/reportStatistics/brokerageStatistics")
public class AdminBrokerageStatisticsController {
	
	@Autowired
	private BrokerageStatisticsService brokerageStatisticsService;
	
	/**
	 * 转到佣金统计
	 * @param model
	 * @return
	 */
	@RequestMapping(value="brokerageStatistics")
	public String brokerageStatistics(
			HttpServletRequest request,Long groupid,Model model){
		Map<String, Object> map = brokerageStatisticsService.getCountBrokerage();
		model.addAttribute("map", map);
		Page<RepStatisticBrokerageDTO> page3 = brokerageStatisticsService.pageBrokerageApply();
		model.addAttribute("page3", page3);
		List<User.UserType> userTypes1 = new ArrayList<User.UserType>();//员工统计
		userTypes1.add(User.UserType.SALESMAN);
		Page<RepStatisticBrokerageDTO> page1 = brokerageStatisticsService.pageBrokerageStatisctic(userTypes1);
		model.addAttribute("page1", page1);
		
		List<User.UserType> userTypes2 = new ArrayList<User.UserType>();//用户统计
		userTypes2.add(User.UserType.MANAGER);
		userTypes2.add(User.UserType.SELLER);
		userTypes2.add(User.UserType.USER);
		Page<RepStatisticBrokerageDTO> page2 = brokerageStatisticsService.pageBrokerageStatisctic(userTypes2);
		model.addAttribute("page2", page2);

		
		return "admin/reportStatistics/brokerageStatistics";
	}
	
}

package com.zzy.brd.controller.admin.reportStatistics;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepUserReportStatisticDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.service.DepartmentService;
import com.zzy.brd.service.FlowWithdrawService;
import com.zzy.brd.service.LoginlogService;
import com.zzy.brd.service.RecordBrokerageService;
import com.zzy.brd.service.RoleService;
import com.zzy.brd.service.UserBankinfoService;
import com.zzy.brd.service.UserDailyStatisticsService;
import com.zzy.brd.service.UserRemarkService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.session.SessionService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.phone.PhoneUtils;
import com.zzy.brd.util.string.StringUtil;

/**
 * @author:xpk
 *    2016年10月20日-下午8:18:52
 **/
@Controller
@RequestMapping(value="/admin/reportStatistics/userReport")
public class AdminUserReportStatisticsController {
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserBankinfoService bankinfoService;
	private @Autowired LoginlogService loginlogService;
	private @Autowired FlowWithdrawService withdrawService;
	private @Autowired RecordBrokerageService recordService;
	private @Autowired UserRemarkService userRemarkService;
	private @Autowired UserDailyStatisticsService userDailyService;	
	@RequestMapping(value="userReport")
	public String list(
			@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			@RequestParam(value = "sortName",required=false,defaultValue="id") String sortName,
			@RequestParam(value="sortType",required=false) String sortType,
			@RequestParam(value="sStartTimestr",required=false,defaultValue="") String sStartTimeStr,
			@RequestParam(value="sEndTimestr",required=false,defaultValue="") String sEndTimeStr,
			@RequestParam(value="state",required=false,defaultValue="-1") int state,
			@RequestParam(value="userType",required=false,defaultValue="-1") int userType,
			@RequestParam(value="loginTimes",required=false,defaultValue="-1") int loginTimes,
			@RequestParam(value="sonSum",required=false,defaultValue="-1" ) int  sonSum,
			@RequestParam(value="grandsonSum",required=false,defaultValue="-1") int grandsonSum,
			@RequestParam(value="orderSum",required=false,defaultValue="-1") int orderSum,
			@RequestParam(value="orderSuccessSum",required=false,defaultValue="-1") int orderSuccessSum,
			@RequestParam(value="keywordType",required=false,defaultValue="0") int keywordType,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="timeRange",required=false,defaultValue="all") String timeRange,
			@RequestParam(value="startTimestr",required=false,defaultValue="") String startTimestr,
			@RequestParam(value="endTimestr",required=false,defaultValue="") String endTimestr,
			@RequestParam(value="ggrandsonSum",required=false,defaultValue="-1") int ggrandsonSum,
			@RequestParam(value="step",required=false,defaultValue="0")int step,
 			Model model){
		/// 初始化排序
		Direction direction = Direction.ASC;
		if (!sortName.equals("id") && sortType.equalsIgnoreCase("desc") ){
			direction = Direction.DESC;
		}
		
		Page<RepUserReportStatisticDTO> page = userDailyService.listUserDailyStatistics(direction,pageNumber,sortName
				,sStartTimeStr,sEndTimeStr,userType,state,loginTimes,
				sonSum,grandsonSum,orderSum,orderSuccessSum
				,timeRange,startTimestr,endTimestr,keywordType,keyword,ggrandsonSum,sortType);
		model.addAttribute("page", page);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType",sortType);
		model.addAttribute("loginTimes", loginTimes);
		model.addAttribute("sonSum", sonSum);
		model.addAttribute("grandsonSum", grandsonSum);
		model.addAttribute("orderSum",orderSum);
		model.addAttribute("orderSuccessSum", orderSuccessSum);
		model.addAttribute("keywordType", keywordType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("timeRange", timeRange);
		model.addAttribute("startTimestr",startTimestr);
		model.addAttribute("endTimestr",endTimestr);
		model.addAttribute("sStartTimestr", sStartTimeStr);
		model.addAttribute("sEndTimestr", sEndTimeStr);
		model.addAttribute("state", state);
		model.addAttribute("userType", userType);
		model.addAttribute("ggrandsonSum", ggrandsonSum);
		model.addAttribute("step", step);
		return  "admin/reportStatistics/userReport";
	}
	
}

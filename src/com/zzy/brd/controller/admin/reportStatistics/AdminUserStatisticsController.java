package com.zzy.brd.controller.admin.reportStatistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepReportStatisticsDTO;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepUserStatisticsConutDTO;
import com.zzy.brd.entity.UserCommonStatistics;
import com.zzy.brd.service.UserCommonStatisticsService;
import com.zzy.brd.util.date.DateUtil;

/**
 * @author:csy
 *    2016年10月14日
 **/
@Controller
@RequestMapping(value="admin/reportStatistics/userStatistics")
public class AdminUserStatisticsController {
	
	@Autowired
	private UserCommonStatisticsService userCommonStatisticsService;
	
	/** 每日新注册会员 */
	public static final int REGISTER_NUM = 0;
	/** 每日登录会员 */
	public static final int LOGIN_NUM = 1;
	/** 会员总增长量 */
	public static final int TOTALADD_NUM = 2;
	
	/**
	 * 转到会员统计
	 * @param model
	 * @return
	 */
	@RequestMapping(value="userStatistics")
	public String userStatistics(
			@RequestParam(value = "neardayRegister",required = false,defaultValue ="7") String neardayRegister
			,@RequestParam(value = "startRegister",required = false,defaultValue ="") String startRegister
			,@RequestParam(value = "endRegister",required = false,defaultValue ="") String endRegister
			,@RequestParam(value = "neardayLogin",required = false,defaultValue ="7") String neardayLogin
			,@RequestParam(value = "startLogin",required = false,defaultValue ="") String startLogin
			,@RequestParam(value = "endLogin",required = false,defaultValue ="") String endLogin
			,@RequestParam(value = "neardayAll",required = false,defaultValue ="7") String neardayAll
			,@RequestParam(value = "startAll",required = false,defaultValue ="") String startAll
			,@RequestParam(value = "endAll",required = false,defaultValue ="") String endAll
			,HttpServletRequest request,Model model){
		List<UserCommonStatistics> statistics1 = null; 
		List<UserCommonStatistics> statistics2 = null; 
		List<UserCommonStatistics> statistics3 = null; 
		int neardayRegisterInt = Integer.valueOf(neardayRegister);
		String start1 = DateUtil.getNDay(neardayRegisterInt);
		String end1 = DateUtil.getYesterday();
		int neardayLoginInt = Integer.valueOf(neardayLogin);
		String start2 = DateUtil.getNDay(neardayLoginInt);
		String end2 = DateUtil.getYesterday();
		int neardayAllInt = Integer.valueOf(neardayAll);
		String start3 = DateUtil.getNDay(neardayAllInt);
		String end3 = DateUtil.getYesterday();
		RepUserStatisticsConutDTO rep = userCommonStatisticsService.getUserCount();
		RepReportStatisticsDTO rep1 = userCommonStatisticsService.getCake();
		model.addAttribute("rep", rep);
		model.addAttribute("rep1", rep1);
		if((!StringUtils.isBlank(startRegister))&&(!StringUtils.isBlank(endRegister))){
			neardayRegister="";
			start1 = startRegister;
			end1 = endRegister;
		}
		if((!StringUtils.isBlank(startLogin))&&(!StringUtils.isBlank(endLogin))){
			neardayLogin="";
			start2 = startLogin;
			end2 = endLogin;
		}
		if((!StringUtils.isBlank(startAll))&&(!StringUtils.isBlank(endAll))){
			neardayAll="";
			start3 = startAll;
			end3 = endAll;
		}
		//注册时间差
		int difference1 = DateUtil.differentDays(DateUtil.StringToTimestamp(start1), DateUtil.StringToTimestamp(end1)) +1 ;
		int difference2 = DateUtil.differentDays(DateUtil.StringToTimestamp(start2), DateUtil.StringToTimestamp(end2)) +1;
		int difference3 = DateUtil.differentDays(DateUtil.StringToTimestamp(start3), DateUtil.StringToTimestamp(end3)) +1;
		statistics1 = userCommonStatisticsService.findUserCommonStatisticsList(start1,end1);
		statistics2 = userCommonStatisticsService.findUserCommonStatisticsList(start2,end2);
		statistics3 = userCommonStatisticsService.findUserCommonStatisticsList(start3,end3);
		Map<String, Object> map1 = getXDateAndDateStrList(statistics1);
		String xdata1 = map1.get("XDate").toString();
		@SuppressWarnings("unchecked")
		ArrayList<String>  dateList1 = (ArrayList<String>)map1.get("DateStrList");
		int total1 = 0;
		for(UserCommonStatistics s1 : statistics1) {
			total1 =total1 + s1.getRegisterNum();
		}
		int total2 =0;
		for(UserCommonStatistics s2 : statistics1) {
			total2 =total2 + s2.getLoginNum();
		}
		int total3 = 0;
		for(UserCommonStatistics s3 : statistics1) {
			total3 =total3 + s3.getYesterdayUser();
		}
		Map<String, Object> map2 = getXDateAndDateStrList(statistics2);
		String xdata2 = map2.get("XDate").toString();
		@SuppressWarnings("unchecked")
		ArrayList<String>  dateList2 = (ArrayList<String>)map2.get("DateStrList");
		Map<String, Object> map3 = getXDateAndDateStrList(statistics3);
		String xdata3 = map3.get("XDate").toString();
		@SuppressWarnings("unchecked")
		ArrayList<String>  dateList3 = (ArrayList<String>)map3.get("DateStrList");
		String ydataRegister = getYData(statistics1,REGISTER_NUM);//每日新注册会员
		String ydataLogin = getYData(statistics2,LOGIN_NUM);//每日登录会员
		String ydataAll = getYData(statistics3,TOTALADD_NUM);//每日登录会员
		model.addAttribute("xdata1", xdata1);
		model.addAttribute("xdata2", xdata2);
		model.addAttribute("xdata3", xdata3);
		model.addAttribute("dateList1", dateList1);
		model.addAttribute("dateList2", dateList2);
		model.addAttribute("dateList3", dateList3);
		model.addAttribute("ydataRegister", ydataRegister);
		model.addAttribute("ydataLogin", ydataLogin);
		model.addAttribute("ydataAll", ydataAll);
		model.addAttribute("fieldNameRegister", "每日新注册人数");
		model.addAttribute("fieldNameLogin", "每日登录会员数");
		model.addAttribute("fieldNameAll", "会员总数");
		model.addAttribute("neardayRegister", neardayRegister);
		model.addAttribute("startRegister", startRegister);
		model.addAttribute("endRegister", endRegister);
		model.addAttribute("neardayLogin", neardayLogin);
		model.addAttribute("startLogin", startLogin);
		model.addAttribute("endLogin", endLogin);
		model.addAttribute("neardayAll", neardayAll);
		model.addAttribute("startAll", startAll);
		model.addAttribute("endAll", endAll);
		model.addAttribute("difference1", difference1);
		model.addAttribute("difference2", difference2);
		model.addAttribute("difference3", difference3);
		model.addAttribute("total1", total1);
		model.addAttribute("total2", total2);
		model.addAttribute("total3", total3);
		return "admin/reportStatistics/userStatistics";
	}
	
   
	
	/**
	 * 获取折线图x轴数据(时间轴) 和时间的String List 供前台节点跳转
	 */
	private Map<String, Object>  getXDateAndDateStrList(List<UserCommonStatistics> statistics) {
		Map<String, Object> map = Maps.newHashMap();
		String XDate = "[]";
		ArrayList<String> DateStrList=new ArrayList<String>();
		StringBuilder stringBuilder = new StringBuilder();
		if (statistics == null || statistics.size() == 0) {
			map.put("XDate", XDate);
			map.put("DateStrList",DateStrList);
			return map;
		} else {
			for (int i = 0; i < statistics.size(); i++) {
				Date statisticsDate = statistics.get(i).getStatisticsDate();
				String bkDateStr = new SimpleDateFormat("yyyyMMdd").format(statisticsDate);
				String statisticsDateStr = new SimpleDateFormat("yyyy-MM-dd").format(statisticsDate);
				DateStrList.add(bkDateStr);
				if (i == 0) {
					stringBuilder.append("[");
				}
				stringBuilder.append("\'"+ statisticsDateStr + "\'");
				if (i != statistics.size() - 1) {
					stringBuilder.append(",");
				} else {
					stringBuilder.append("]");
				}
			}
			map.put("XDate", stringBuilder);
			map.put("DateStrList",DateStrList);
		}

		return map;
	}

	/**
	 * 获取折线图y轴数据(数据轴)
	 */
	private String getYData(List<UserCommonStatistics> statistics,int field) {
		StringBuilder stringBuilder = new StringBuilder();
		if (statistics == null || statistics.size() == 0) {
			return "[]";
		} else {
			for (int i = 0; i < statistics.size(); i++) {
				if (i == 0) {
					stringBuilder.append("[");
				}
				switch (field) {
				case REGISTER_NUM:
					stringBuilder.append(statistics.get(i).getRegisterNum());
					break;
				case LOGIN_NUM:
					stringBuilder.append(statistics.get(i).getLoginNum());
					break;
				case TOTALADD_NUM:
					stringBuilder.append(statistics.get(i).getAlluserNum());
					break;
				}
				if (i != statistics.size() - 1) {
					stringBuilder.append(",");
				} else {
					stringBuilder.append("]");
				}
			}
		}
		return stringBuilder.toString();
	}
	
	
}

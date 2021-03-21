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

import com.google.common.collect.Maps;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepOrderStatisticsConutDTO;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepOrderStatisticsDTO;
import com.zzy.brd.entity.OrderCommonStatistics;
import com.zzy.brd.service.OrderCommonStatisticsService;
import com.zzy.brd.util.date.DateUtil;

/**
 * @author:csy
 *    2016年10月17日
 **/
@Controller
@RequestMapping(value="admin/reportStatistics/orderStatistics")
public class AdminOrderStatisticsController {
	
	@Autowired
	private OrderCommonStatisticsService orderCommonStatisticsService;
	
	/** 每日提交订单 */
	public static final int ORDER_NUM = 0;
	/** 每日成交订单 */
	public static final int SUC_NUM = 1;
	/** 会员成交金额 */
	public static final int SUC_COUNT = 2;
	
	/**
	 * 转到订单统计
	 * @param model
	 * @return
	 */
	@RequestMapping(value="orderStatistics")
	public String orderStatistics(
			@RequestParam(value = "neardayLoad",required = false,defaultValue ="7") String neardayLoad
			,@RequestParam(value = "startLoad",required = false,defaultValue ="") String startLoad
			,@RequestParam(value = "endLoad",required = false,defaultValue ="") String endLoad
			,@RequestParam(value = "neardayCredit",required = false,defaultValue ="7") String neardayCredit
			,@RequestParam(value = "startCredit",required = false,defaultValue ="") String startCredit
			,@RequestParam(value = "endCredit",required = false,defaultValue ="") String endCredit
			,@RequestParam(value = "neardaySum",required = false,defaultValue ="7") String neardaySum
			,@RequestParam(value = "startSum",required = false,defaultValue ="") String startSum
			,@RequestParam(value = "endSum",required = false,defaultValue ="") String endSum
			,HttpServletRequest request,Model model){
		RepOrderStatisticsConutDTO rep = orderCommonStatisticsService.getOrderCount();
		RepOrderStatisticsDTO rep1 = orderCommonStatisticsService.getCake();
		model.addAttribute("rep", rep);
		model.addAttribute("rep1", rep1);
		List<OrderCommonStatistics> statistics1 = null; 
		List<OrderCommonStatistics> statistics2 = null; 
		List<OrderCommonStatistics> statistics3 = null; 
		int neardayLoadInt = Integer.valueOf(neardayLoad);
		String start1 = DateUtil.getNDay(neardayLoadInt);
		String end1 = DateUtil.getYesterday();
		int neardayCreditInt = Integer.valueOf(neardayCredit);
		String start2 = DateUtil.getNDay(neardayCreditInt);
		String end2 = DateUtil.getYesterday();
		int neardaySumInt = Integer.valueOf(neardaySum);
		String start3 = DateUtil.getNDay(neardaySumInt);
		String end3 = DateUtil.getYesterday();
		
		if((!StringUtils.isBlank(startLoad))&&(!StringUtils.isBlank(endLoad))){
			neardayLoad="";
			start1 = startLoad;
			end1 = endLoad;
		}
		if((!StringUtils.isBlank(startCredit))&&(!StringUtils.isBlank(endCredit))){
			neardayCredit="";
			start2 = startCredit;
			end2 = endCredit;
		}
		if((!StringUtils.isBlank(startSum))&&(!StringUtils.isBlank(endSum))){
			neardaySum="";
			start3 = startSum;
			end3 = endSum;
		}
		
		statistics1 = orderCommonStatisticsService.findOrderCommonStatisticsList(start1,end1);
		statistics2 = orderCommonStatisticsService.findOrderCommonStatisticsList(start2,end2);
		statistics3 = orderCommonStatisticsService.findOrderCommonStatisticsList(start3,end3);
		Map<String, Object> map1 = getXDateAndDateStrList(statistics1);
		String xdata1 = map1.get("XDate").toString();
		@SuppressWarnings("unchecked")
		ArrayList<String>  dateList1 = (ArrayList<String>)map1.get("DateStrList");
		String ydataLoad = getYData(statistics1,ORDER_NUM);//每日提交贷款订单数
		
		Map<String, Object> map2 = getXDateAndDateStrList(statistics2);
		String xdata2 = map2.get("XDate").toString();
		@SuppressWarnings("unchecked")
		ArrayList<String>  dateList2 = (ArrayList<String>)map1.get("DateStrList");
		String ydataCredit = getYData(statistics2,SUC_NUM);//每日成交订单数
		
		Map<String, Object> map3 = getXDateAndDateStrList(statistics3);
		String xdata3 = map3.get("XDate").toString();
		@SuppressWarnings("unchecked")
		ArrayList<String>  dateList3 = (ArrayList<String>)map3.get("DateStrList");
		String ydataSum = getYData(statistics3,SUC_COUNT);//每日成交金额
		
		
		model.addAttribute("xdata1", xdata1);
		model.addAttribute("dateList1", dateList1);
		model.addAttribute("ydataLoad", ydataLoad);
		model.addAttribute("fieldNameLoad", "每日提交贷款订单数");
		model.addAttribute("neardayLoad", neardayLoad);
		model.addAttribute("startLoad", startLoad);
		model.addAttribute("endLoad", endLoad);
		
		model.addAttribute("xdata2", xdata2);
		model.addAttribute("dateList2", dateList2);
		model.addAttribute("ydataCredit", ydataCredit);
		model.addAttribute("fieldNameCredit", "每日成交贷款订单数");
		model.addAttribute("neardayCredit", neardayCredit);
		model.addAttribute("startCredit", startCredit);
		model.addAttribute("endCredit", endCredit);
		
		model.addAttribute("xdata3", xdata3);
		model.addAttribute("dateList3", dateList3);
		model.addAttribute("ydataSum", ydataSum);
		model.addAttribute("fieldNameSum", "每日成交贷款订单金额");
		model.addAttribute("neardaySum", neardaySum);
		model.addAttribute("startSum", startSum);
		model.addAttribute("endSum", endSum);
		return "admin/reportStatistics/orderStatistics";
	}
	
   
	
	/**
	 * 获取折线图x轴数据(时间轴) 和时间的String List 供前台节点跳转
	 */
	private Map<String, Object>  getXDateAndDateStrList(List<OrderCommonStatistics> statistics) {
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
	private String getYData(List<OrderCommonStatistics> statistics,int field) {
		StringBuilder stringBuilder = new StringBuilder();
		if (statistics == null || statistics.size() == 0) {
			return "[]";
		} else {
			for (int i = 0; i < statistics.size(); i++) {
				if (i == 0) {
					stringBuilder.append("[");
				}
				switch (field) {
				case ORDER_NUM:
					stringBuilder.append(statistics.get(i).getOrderNum());
					break;
				case SUC_NUM:
					stringBuilder.append(statistics.get(i).getOrderSuccessNum());
					break;
				case SUC_COUNT:
					stringBuilder.append(statistics.get(i).getOrderSuccessDecimal());
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

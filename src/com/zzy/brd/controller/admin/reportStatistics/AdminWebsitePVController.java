package com.zzy.brd.controller.admin.reportStatistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;
import com.zzy.brd.entity.PvCommonStatistics;
import com.zzy.brd.entity.UserCommonStatistics;
import com.zzy.brd.service.PvCommonStatisticsService;
import com.zzy.brd.service.UserCommonStatisticsService;
import com.zzy.brd.util.date.DateUtil;


/**
 * @author:xpk
 *    2017年1月12日-上午11:09:34
 *    访问量统计
 **/
@Controller
@RequestMapping(value="admin/reportStatistics")
public class AdminWebsitePVController {
	
	@Autowired
	private PvCommonStatisticsService pvCommonStatisticsService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="pv",method=RequestMethod.GET)
	public String pv(
			@RequestParam(value="nearRange",defaultValue="7",required=false) int nearRange,
			@RequestParam(value="startDate",required=false ) String startDate,
			@RequestParam(value="endDate",required=false ) String endDate,
			Model model) {
		//昨天统计数据
		Date date = DateUtil.getDateByLongFormat(DateUtil.getYesterdayBeginString());
		PvCommonStatistics common = pvCommonStatisticsService.findByDate(date);
		model.addAttribute("comon", common);
		String start = null;
		String end = null;	
		
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			start  = startDate ;
			end = endDate;
		} else {
			if(nearRange==7) {
				start = DateUtil.getNDay(7);
				end = DateUtil.getYesterday();
			} else {
				start = DateUtil.getNDay(30);
				end =DateUtil.getYesterday();
			}
		}
		int difference = DateUtil.differentDays(DateUtil.StringToTimestamp(start), DateUtil.StringToTimestamp(end)) +1 ;
		List<PvCommonStatistics> statisticsList = pvCommonStatisticsService.findStatisticsByDate(start, end);		
		int total = 0;
		for(PvCommonStatistics s1 : statisticsList) {
			total =total + s1.getWebsitePv();
		}
		Map<String,Object> xData = this.getXDateAndDateStrList(statisticsList);
		String xdata = xData.get("XDate").toString();
		String yData = getYData(statisticsList);
		ArrayList<String>  dateList = (ArrayList<String>)xData.get("DateStrList");
		model.addAttribute("xdata", xdata);
		model.addAttribute("ydata", yData);
		model.addAttribute("dataList", dateList);
		model.addAttribute("nearRange", nearRange);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("difference", difference);
		model.addAttribute("total", total);
		return "admin/reportStatistics/pv";
	}
	
	
	
	/**
	 * 获取折线图x轴数据(时间轴) 和时间的String List 供前台节点跳转
	 */
	private Map<String, Object>  getXDateAndDateStrList(List<PvCommonStatistics> statistics) {
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
	private String getYData(List<PvCommonStatistics> statistics) {
		StringBuilder stringBuilder = new StringBuilder();
		if (statistics == null || statistics.size() == 0) {
			return "[]";
		} else {
			for (int i = 0; i < statistics.size(); i++) {
				if (i == 0) {
					stringBuilder.append("[");
				}
				stringBuilder.append(statistics.get(i).getWebsitePv());
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

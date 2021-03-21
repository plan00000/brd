package com.zzy.brd.mobile.web.controller.brokerageDistribution;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.rep.recordBrokerage.RecordBrokerageDTO;
import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.entity.RecordBrokerage.RelationType;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.BrokerageDistributionService;
import com.zzy.brd.service.RecordBrokerageService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.tld.PriceUtils;

/**
 * 微信站 -个人中心
 * @author lzh 2016/9/20
 *
 */
@Controller
@RequestMapping("weixin/brokerageDistribution")
public class WeixinBrokerageDistributionController {
	@Autowired
	private BrokerageDistributionService brokerageDistributionService;
	@Autowired
	private UserService userService;
	@Autowired
	private RecordBrokerageService recordBrokerageService;
	/**
	 * 转至佣金明细
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("toBrokerageDistribution")
	public String toBrokerageDistribution(Model model,HttpServletRequest request){
		Long userId = ShiroUtil.getUserId(request);
		if(userId !=null){
			User user = userService.findUserById(userId);
		Date lastMouthFirstDay = DateUtil.getNextMonth(-1);//得到上个月的第一天
		Date thisMouthFirstDay = DateUtil.getNextMonth(0);//得到当前月的第一天
		Date nextMouthFirstDay = DateUtil.getNextMonth(1);//得到下个月的第一天
		BigDecimal totalBrokerage = recordBrokerageService.findBrokerages(userId); //累计佣金
		BigDecimal lastMouthBrokerage = recordBrokerageService.findBrokerages(userId,lastMouthFirstDay,thisMouthFirstDay);  //上个月佣金
		BigDecimal thisMouthBrokerage = recordBrokerageService.findBrokerages(userId,thisMouthFirstDay,nextMouthFirstDay); //得到本月佣金
		List<RecordBrokerageDTO> items = recordBrokerageService.getBrokerageDistributionsByUser(user);
		model.addAttribute("totalBrokerage", totalBrokerage+"");
		model.addAttribute("lastMouthBrokerage", lastMouthBrokerage+"");
		model.addAttribute("thisMouthBrokerage", thisMouthBrokerage+"");
		model.addAttribute("items", items);
		}
		return "mobile/brokerageDistribution/brokerageDistribution";
	}
	/**
	 * 转至佣金明细
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("toBrokerageDistributionByUserId/{userId}")
	public String toBrokerageDistributionByUserId(@PathVariable long userId,Model model,HttpServletRequest request){
			User user = userService.findUserById(userId);
			Date lastMouthFirstDay = DateUtil.getNextMonth(-1);//得到上个月的第一天
			Date thisMouthFirstDay = DateUtil.getNextMonth(0);//得到当前月的第一天
			Date nextMouthFirstDay = DateUtil.getNextMonth(1);//得到下个月的第一天
			String totalBrokerageStr = "0.00";
			String lastMouthBrokerageStr = "0.00";
			String thisMouthBrokerageStr = "0.00";
			BigDecimal totalBrokerage = user.getUserInfoBoth().getBrokerageCanWithdraw().add(user.getUserInfoBoth().getBrokerageHaveWithdraw())
					.add(user.getUserInfoBoth().getBrokerageWithdrawing());	
			BigDecimal lastMouthBrokerage = recordBrokerageService.findBrokerages(userId,lastMouthFirstDay,thisMouthFirstDay);  //上个月佣金
			BigDecimal thisMouthBrokerage = recordBrokerageService.findBrokerages(userId,thisMouthFirstDay,nextMouthFirstDay); //得到本月佣金
			if(totalBrokerage.compareTo(new BigDecimal("9999.99"))==-1){
				totalBrokerageStr = PriceUtils.normalPrice(totalBrokerage);
				model.addAttribute("flage1", "true");
			}else{
				totalBrokerageStr = PriceUtils.tenThousandPrice(totalBrokerage);
				model.addAttribute("flage1", "false");
			}
			if(lastMouthBrokerage.compareTo(new BigDecimal("9999.99"))==-1){
				lastMouthBrokerageStr = PriceUtils.normalPrice(lastMouthBrokerage);
				model.addAttribute("flage2", "true");
			}else{
				lastMouthBrokerageStr = PriceUtils.tenThousandPrice(lastMouthBrokerage);
				model.addAttribute("flage2", "false");
			}
			if(thisMouthBrokerage.compareTo(new BigDecimal("9999.99"))==-1){
				thisMouthBrokerageStr = PriceUtils.normalPrice(thisMouthBrokerage);
				model.addAttribute("flage3", "true");
			}else{
				thisMouthBrokerageStr = PriceUtils.tenThousandPrice(thisMouthBrokerage);
				model.addAttribute("flage3", "false");
			}
			
			
			List<RecordBrokerageDTO> items = recordBrokerageService.getBrokerageDistributionsByUser(user);
			model.addAttribute("totalBrokerage", totalBrokerageStr);
			model.addAttribute("lastMouthBrokerage", lastMouthBrokerageStr);
			model.addAttribute("thisMouthBrokerage", thisMouthBrokerageStr);
			model.addAttribute("items", items);
		return "mobile/brokerageDistribution/brokerageDistribution";
	}

}

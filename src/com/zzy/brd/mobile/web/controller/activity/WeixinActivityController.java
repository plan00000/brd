package com.zzy.brd.mobile.web.controller.activity;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.ActivityStarOrder;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.UserInfoBoth.ApprenticeAwardFlag;
import com.zzy.brd.entity.UserInfoBoth.StarOrderAwardFlag;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.ActivityService;
import com.zzy.brd.service.ActivityStarOrderService;
import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.string.StringUtil;

/**
 * 活动
 * 
 * @author csy 2016/9/27
 *
 */
@Controller
@RequestMapping("weixin/activity")
public class WeixinActivityController {
	Logger logger = LoggerFactory.getLogger(WeixinActivityController.class);

	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityStarOrderService activityStarOrderService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoBothService userInfoBothService;
	
	/**
	 * 转至收徒佣金
	 * 
	 * @return
	 */
	@RequestMapping("toApprenticeAward")
	public String toApprenticeAward(Model model,HttpServletRequest request) {
		Long userId = ShiroUtil.getUserId(request);
		int remainNum = 0;
		int maxNum = 10 ;
		ApprenticeAwardFlag apprenticeAwardFlag = null;
		int sonNum = 0;
			Activity activity = activityService.getApprenticeAwardByActivityType();
			if(activity !=null){
				maxNum = activity.getMaxNum();//前台星星总数。
			}
			if(userId>0){
				User user = userService.findUserById(userId);
				UserInfoBoth userInfoBoth = user.getUserInfoBoth();
				if(userInfoBoth!=null){
					apprenticeAwardFlag = userInfoBoth.getApprenticeAwardFlag();
				}
			    sonNum = userInfoBoth.getAcitivitySonSum();
				if(sonNum>maxNum){//徒弟数量超过最大数值赋值为最大数值
					sonNum = maxNum;
				}
			}
			int completeStatus = completeStatus(sonNum, maxNum, apprenticeAwardFlag,null);
			remainNum = maxNum - sonNum;
			String time = DateUtil.getLongNow();
			model.addAttribute("activity", activity);
			model.addAttribute("completeStatus", completeStatus);
			model.addAttribute("remainNum", remainNum);
			model.addAttribute("sonNum", sonNum);
			model.addAttribute("time", time);
			model.addAttribute("userId", userId);
			return "mobile/activity/activityApprenticeAward";
	}
	/**
	 * 判断去收徒按钮的文字 1-去收徒，2-领取奖励，3-任务完成。
	 * @param sonN
	 * @param maxN
	 * @param apprenticeAwardFlag
	 * @return
	 */
	public int completeStatus(int sonN,int maxN,
			ApprenticeAwardFlag apprenticeAwardFlag,StarOrderAwardFlag starOrderAwardFlag){
		 if(sonN<maxN){
			 return 1;//未完成
		 }else{
			 if(starOrderAwardFlag==null){//星级为空，就是收徒奖励
				 if(apprenticeAwardFlag==ApprenticeAwardFlag.NO){
					 return 2;//已完成，未领取奖励。显示领取奖励。
				 }else{
					 return 3;//已完成，并领取过奖励。显示任务完成。
				 }
			 }else{
				 return 3;
			 }
		 }
	}
	/**
	 *  领取奖励
	 * @param request
	 * @return
	 * 暂时只做了修改userinfoboth.ApprenticeAwardFlag 的状态修改，还未真正的发放到用户账号上。
	 */
	@RequestMapping(value = "receiveReward", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO receiveReward(HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Activity activity = activityService.getApprenticeAwardByActivityType();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findUserById(userId);
		UserInfoBoth userInfoBoth = user.getUserInfoBoth();
		if(userInfoBoth.getApprenticeAwardFlag() == ApprenticeAwardFlag.NO){
			userInfoBoth.setApprenticeAwardFlag(ApprenticeAwardFlag.YES);
			userInfoBoth.setApprenticeAwareBrokerage(activity.getBonusAmount());
			userService.AddActivityBrokerageAndBrokerageCanWithdraw(userInfoBoth.getId(), activity.getBonusAmount());
			userInfoBothService.editUserInfoBoth(userInfoBoth);
			rep.setCode(1);
			rep.setMes("奖励已发放，请注意查收。");
			return rep;
		}else{
			rep.setCode(0);
			rep.setMes("不可再领取，奖励已发放过");
			return rep ;
		}
	}
	
	/**
	 * 转至星级订单
	 * 
	 * @return
	 */
	@RequestMapping("toStarOrder")
	public String toStarOrder(Model model, HttpServletRequest request) {
		Long userId = ShiroUtil.getUserId(request);
		int orderSum = 0;//用户订单数
		int orderSum1 = 0;//用户订单数
		int starOrdernum = 5;
		int remainNum = 0;
		int maxNum = 3;
		int starNum = 0;
		StarOrderAwardFlag starOrderAwardFlag = StarOrderAwardFlag.NO;
		Activity activity = activityService.getStartOrderByActivityType();
		if(activity!=null){
			 starOrdernum = activity.getStarOrdernum();//星级订单不计时间订单数 初始为5
			 maxNum = activity.getMaxNum();//不超过一年 初始为3
		}
		User user = userService.findUserById(userId);
		UserInfoBoth userInfoBoth = user.getUserInfoBoth(); 
		if(userInfoBoth!=null){
			starOrderAwardFlag = userInfoBoth.getStarOrderAwardFlag();
		}
		String time = DateUtil.getLongNow();//当前时间
		ActivityStarOrder activityStarOrder = activityStarOrderService.getActivityStarOrder(user);
		if (activityStarOrder!=null){
			orderSum = activityStarOrder.getOrderSum();
			orderSum1 = orderSum;
			Date addDate = activityStarOrder.getStartTime();	
			Boolean isSxceedYear = isBeyondNextYear(addDate, time);
			if(isSxceedYear){
				starNum = maxNum; //如果进行活动时间不满一年jsp页面星星总数是3
			}else{
				starNum = starOrdernum;//如果进行活动时间不满一年jsp页面星星总数是5
			}
			if(orderSum>starNum){//星级订单数量超过最大数值赋值为最大数值
				//starNum = starOrdernum;
				orderSum1 = starNum;
			}
		}else {
			starNum = maxNum;
		}
		int completeStatus = completeStatus(orderSum, starNum, null,starOrderAwardFlag);
		remainNum  = starNum-orderSum;
		if(remainNum<0){
			remainNum=0;
		}
		model.addAttribute("activity", activity);
		model.addAttribute("time", time);
		model.addAttribute("orderSum1", orderSum1);
		model.addAttribute("remainNum", remainNum);
		model.addAttribute("completeStatus", completeStatus);
		return "mobile/activity/activityStarOrder";
	}
	/**
	 * 判断某个时间和某个时间的下一年的同一时间的先后关系
	 * @param d1
	 * @param time
	 * @return Boolean
	 */
	public Boolean isBeyondNextYear(Date d1,String time){
		String ndstr = DateUtil.getNextYearTime(d1);//某一时间的下一年同一时间
		Date dt = DateUtil.getDateByLongFormat(time);
		Date dn = DateUtil.getDateByLongFormat(ndstr);//某一时间的下一年同一时间
		Boolean t = dn.after(dt);//dn比dt迟返回true 就是星级订单任务是3 dn比dt早 就是满一年了，星级订单就是5个。
		return t;
	}
}

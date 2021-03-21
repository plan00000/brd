package com.zzy.brd.mobile.web.controller.activity;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.ActivityService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.string.StringUtil;

/**
 * 活动
 * 
 * @author csy 2016/9/27
 *
 */
@Controller
@RequestMapping("weixin/judgment")
public class WeixinJudgmentController {
	Logger logger = LoggerFactory.getLogger(WeixinJudgmentController.class);

	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserService userService;
	
	/**
	 * 为首页收徒奖励判断会员类型
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "judgment", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO judgment(HttpServletRequest request) {
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		if(userId==null){
			rep.setCode(-1);
			return rep;
		}
		User user = userService.findUserById(userId);
		User.UserType userType = user.getUserType();
		Activity activity = activityService.getApprenticeAwardByActivityType();
		String activityObject = "";
		if(activity!=null){
			 activityObject = activity.getActivityObject();
		}
		Boolean isExit =false;
		if((userType==UserType.CONTROLMANAGER)||(userType==UserType.EMPLOYEE)||(userType==UserType.ADMIN)){
			rep.setCode(0);
			rep.setMes("很抱歉，该活动内部人员无法参加");
		}else if(userType==UserType.USER){
			rep.setCode(1);
			rep.setMes("用户需成为融资经理才可赚取奖励");
		}else if(userType==UserType.MANAGER){
			isExit = StringUtil.isExit(activityObject, "1");
			if(isExit){
				rep.setCode(2);
			}else{
				rep.setCode(0);
				rep.setMes("很抱歉，该活动内部人员无法参加");
			}
		}else if(userType==UserType.SELLER){
			isExit = StringUtil.isExit(activityObject, "2");
			if(isExit){
				rep.setCode(2);
			}else{
				rep.setCode(0);
				rep.setMes("很抱歉，该活动内部人员无法参加");
			}
		}else if(userType==UserType.SALESMAN){
			isExit = StringUtil.isExit(activityObject, "4");
			if(isExit){
				rep.setCode(2);
			}else{
				rep.setCode(0);
				rep.setMes("很抱歉，该活动内部人员无法参加");
			}
		}
		return rep;
	}
	
	/**
	 * 为首页推单赚佣判断会员类型
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "judgment1", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO judgment1(HttpServletRequest request) {
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		if(userId==null){
			rep.setCode(-1);
			return rep;
		}
		User user = userService.findUserById(userId);
		if(user==null){
			rep.setCode(-1);
			return rep;
		}
		
		User.UserType userType = user.getUserType();
		if((userType==UserType.CONTROLMANAGER)||(userType==UserType.ADMIN)||(userType==UserType.EMPLOYEE)){
			rep.setCode(0);
			rep.setMes("很抱歉，该活动内部人员无法参加");
		}else if(userType==UserType.USER){
			rep.setCode(1);
			rep.setMes("用户需成为融资经理才可赚取奖励");
		}else if((userType==UserType.SALESMAN)||(userType==UserType.MANAGER)||(userType==UserType.SELLER)){
			rep.setCode(2);
		}
		return rep;
	}
}

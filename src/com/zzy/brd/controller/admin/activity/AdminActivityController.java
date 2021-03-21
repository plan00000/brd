package com.zzy.brd.controller.admin.activity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.ActivityStarOrder;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.Activity.ActivitySet;
import com.zzy.brd.entity.UserInfoBoth.StarOrderAwardFlag;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.ActivityService;
import com.zzy.brd.service.ActivityStarOrderService;
import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;


/**
 * 后台-活动管理
 * @author csy 2016 10 08
 *
 */
@Controller
@RequestMapping("/admin/activity")
public class AdminActivityController {

	@Autowired
	private UserService userService;
	@Autowired
	private ActivityStarOrderService activityStarOrderService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserInfoBothService userInfoBothService;
	@Autowired
	private UserOperLogService userOperlogService;
	/***
	 * 收徒10个奖励活动
	 */
	@RequestMapping("apprenticeReward")
	public String apprenticeReward(
		@RequestParam(value="page",required=true,defaultValue="1") int pageNumber
		,@RequestParam(value = "status",required = false,defaultValue = "")String status
		,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
		,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
		,HttpServletRequest request, Model model){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<User.UserType> userTypeList = new ArrayList<User.UserType>();
		Activity activity = activityService.getApprenticeAwardByActivityType();
		int maxNum = 0;
		if(activity!=null){
			String activityObject = activity.getActivityObject();
			maxNum = activity.getMaxNum();
			if(activityObject != null && activityObject.length() != 0){
				Boolean is1 = isExit(activityObject, "1");
				Boolean is2 = isExit(activityObject, "2");
				Boolean is4 = isExit(activityObject, "4");
				if(is1){
					userTypeList.add(User.UserType.MANAGER);
				}
				if(is2){
					userTypeList.add(User.UserType.SELLER);
				}
				if(is4){
					userTypeList.add(User.UserType.SALESMAN);
				}
				searchParams.put("IN_userType", userTypeList);
				if(!StringUtils.isBlank(status)){
					if("UNCOMPLETE".equals(status)){
						searchParams.put("LT_userInfoBoth.acitivitySonSum", maxNum);
					}else{
						searchParams.put("GT_userInfoBoth.acitivitySonSum", maxNum-1);
					}
				}
			}else{
				searchParams.put("LT_userInfoBoth.acitivitySonSum", 0);
			}
		}else{
			searchParams.put("LT_userInfoBoth.acitivitySonSum", 0);
		}
		Page<User> page = userService.listUsers(searchParams,pageNumber, Constant.PAGE_SIZE, sortName, sortType,false);
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("maxNum", maxNum);
		return "admin/activity/apprenticeRewardlist";
	}
	
	/***
	 * 星级订单
	 * @throws ParseException 
	 */
	@RequestMapping("starOrderList")
	public String starOrderList(
			@RequestParam(value="page",required=true,defaultValue="1") int pageNumber
			,@RequestParam(value = "status",required = false,defaultValue = "")String status
			,@RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			,@RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			,HttpServletRequest request, Model model){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(status)){
			if("UNCOMPLETE".equals(status)){
				searchParams.put("EQ_status",ActivityStarOrder.Status.UNCOMPLETE );
			}else{
				searchParams.put("EQ_status", ActivityStarOrder.Status.COMPLETE);
			}
		}
		Page<ActivityStarOrder> activityStarOrder = activityStarOrderService.listActivityStarOrders(
				searchParams,pageNumber, Constant.PAGE_SIZE, sortName, sortType);
		model.addAttribute("status", status);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("page", activityStarOrder);
		return "admin/activity/starOrderList";
	}
	/***
	 * 推荐注册活动
	 * @throws ParseException 
	 */
	@RequestMapping("recommendRegister")
	public String recommendRegister(Model model) throws ParseException{
		Activity activity = activityService.getRecommendRegisterByActivityType();
		int activitySetInt =0; 
		Boolean is0 =false;
		Boolean is1 =false;
		Boolean is2 =false;
		if(activity!=null){
			String activityObject = activity.getActivityObject();
			if(activityObject != null && activityObject.length() != 0){
				is1 = isExit(activityObject, "1");
				is2 = isExit(activityObject, "2");
				is0 = isExit(activityObject, "0");
			}
			ActivitySet activitySet = activity.getActivitySet();
			if(activitySet==ActivitySet.REGISTER){
				activitySetInt=1;
			}else if(activitySet==ActivitySet.REGISTERCODE){
				activitySetInt=2;
			}
		}
		model.addAttribute("is1", is1);
		model.addAttribute("is2", is2);
		model.addAttribute("is0", is0);
		model.addAttribute("activitySetInt", activitySetInt);
		model.addAttribute("activity", activity);
		return "admin/activity/recommendRegister";
	}
	/**
	 * 改变星级订单奖励发放状态
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "rewardState", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO rewardState(
			@RequestParam (value="userId", required=true,defaultValue="0") Long userId,
			HttpServletRequest request) {
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		if(userId>0){
			User user = userService.findUserById(userId);
			UserInfoBoth userInfoBoth = user.getUserInfoBoth();
			userInfoBoth.setStarOrderAwardFlag(StarOrderAwardFlag.YES);
			userInfoBothService.editUserInfoBoth(userInfoBoth);
			rep.setCode(1);
		}
		return rep;
	}
	
	/**
	 * 转到活动设置
	 * @param pageNumber
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("starOrderSetting")
	public String starOrderSetting(@RequestParam(value="activityType",required=true,defaultValue="1") 
		int activityType, Model model){
		Activity activity = new Activity();
		if(activityType==1){
			activity = activityService.getStartOrderByActivityType();
			model.addAttribute("activity", activity);
			return "admin/activity/starOrderSetting";
		}else{
			activity = activityService.getApprenticeAwardByActivityType();
			Boolean is1 =false;
			Boolean is2 =false;
			Boolean is4 =false;
			String activityObject  = null;
			if(activity!=null){
				activityObject = activity.getActivityObject();
				if(activityObject != null && activityObject.length() != 0){
			    	is1 = isExit(activityObject, "1");
					is2 = isExit(activityObject, "2");
					is4 = isExit(activityObject, "4");
				}
			}
			model.addAttribute("activityObject", activityObject);
			model.addAttribute("is1", is1);
			model.addAttribute("is2", is2);
			model.addAttribute("is4", is4);
			model.addAttribute("activity", activity);
			return "admin/activity/apprenticeRewardSetting";
		}
	}
	
	/**
	 * 修改活动设置
	 * @param departname
	 * @param parentDeparmentId
	 * @param departmentlevel
	 * @return
	 */
	@RequestMapping(value="addActivity",method=RequestMethod.POST)
	   @ResponseBody
	   public RepSimpleMessageDTO addActivity(
			   @RequestParam (value="startOrdernum", required=false,defaultValue="1") int startOrdernum,
			   @RequestParam (value="activityType", required=false,defaultValue="-1") int activityTypeInt,
			   @RequestParam (value="bounsAmount", required=false,defaultValue="0") BigDecimal bounsAmount,
			   @RequestParam (value="maxNum", required=false,defaultValue="0") int maxNum,
			   @RequestParam (value="activityId", required=false,defaultValue="0") long activityId,
			   @RequestParam (value="picurl",required=false,defaultValue="") String picurl,
			   @RequestParam (value="activityObjectStr",required=false,defaultValue="") String activityObjectStr,
			   @RequestParam (value="activityObject[]", required=false) List<Integer> activityObject,
			   @RequestParam (value="activityRule" ,required=false,defaultValue="" ) String activityRule,
			   @RequestParam (value="activitySet", required=false,defaultValue="0" ) int activitySet,
			   @RequestParam (value="activityCopy",required=false,defaultValue="" ) String activityCopy
			   ,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO() ;
		try {
		   Activity.ActivityType activityType=null;
		   Activity activity = new Activity();
		   if(activityId>0){
			   activity = activityService.findActivityById(activityId);
			   String pic = activity.getPicurl();
			   if("".equals(pic)){
				   activity.setPicurl(picurl);
			   }
			}else{
				if("".equals(picurl)){
				  activity.setPicurl(picurl);
			   }
			}
		   String content ="修改";
		   if(activityTypeInt>-1){
			   if(activityTypeInt==0){
				   activityType=Activity.ActivityType.STARTORDER;
				   content = content + "星级订单活动";
			   }else if(activityTypeInt==1){
				   activityType=Activity.ActivityType.BONUSAWARD;
				   content = content + "收徒奖励活动";
			   }else{
				   activityType=Activity.ActivityType.RECOMMEND;
				   content = content + "推荐注册活动";
			   }
			   activity.setActivityType(activityType);
		   }

		   activity.setMaxNum(maxNum);

		   if(picurl != null && picurl.length() != 0){
			   activity.setPicurl(picurl);
		   }

		   if(null != activityObject && !activityObject.isEmpty()){
			   String activityRuleStr = listToString(activityObject);
			   if(activityRuleStr != null && activityRuleStr.length() != 0){
				   activity.setActivityObject(activityRuleStr);
			   }
			   if(activityObjectStr != null && activityObjectStr.length() != 0){
				   setActivity(activityObjectStr,activityObject);
			   }
			   
		   }else{
			   activity.setActivityObject(null);
		   }
		   int r=bounsAmount.compareTo(BigDecimal.ZERO);// BigDecimal 与 0 比较
		   if(r==1){//r=1 bounsAmount大于0
			   activity.setBonusAmount(bounsAmount);
		   }
		   activity.setStarOrdernum(startOrdernum);
		   if(activitySet==1){
			   activity.setActivitySet(ActivitySet.REGISTER);
		   }if(activitySet==2){
			   activity.setActivitySet(ActivitySet.REGISTERCODE);
		   }
		   activity.setActivityRule(activityRule);
		   activity.setActivityCopy(activityCopy);
		   
		   if(activityService.editActivity(activity)){
			   res.setCode(0);
			   res.setMes("设置成功");
			   //添加日志:
			   long opertorId = ShiroUtil.getUserId(request);
			  
			   userOperlogService.addOperlog(userService.findById(opertorId), content);
			   
		   }else{
			   res.setCode(1);
			   res.setMes("设置失败");
		   }
			
		} catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
			   res.setCode(1);
			   res.setMes("设置失败！");
		}
		return res;
	   }	
	   /**
	    * 将list转字符串
	    * @param list
	    * @param separator
	    * @return
	    */
	    public String listToString(List list) {
	    	StringBuilder sb = new StringBuilder(); 
	    	for (int i = 0; i < list.size(); i++) {
	    	    sb.append(list.get(i));
	    	    if (i < list.size() - 1) {
	    	        sb.append(";");
	    	    }
	    	}
	    	return sb.toString();
	       }
	    
	    /**
	     * 判断字符串是否包含某个字符串
	     * @param str1
	     * @param str2
	     * @return
	     */
	    public boolean isExit(String str1,String str2){
	    	if(str1.indexOf(str2)!=-1){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }
	    /**
	     * 修改活动徒孙数
	     * @param activityObjectStr
	     * @param activityObject
	     */
	    public void setActivity(String activityObjectStr,List<Integer> activityObject){
	    	String activityRuleStr = listToString(activityObject);
	    	boolean isa1 = isExit(activityObjectStr, "1");
	    	boolean isr1 = isExit(activityRuleStr, "1");
	    	boolean isa2 = isExit(activityObjectStr, "2");
	    	boolean isr2 = isExit(activityRuleStr, "2");
	    	boolean isa4 = isExit(activityObjectStr, "4");
	    	boolean isr4 = isExit(activityRuleStr, "4");
	    	if((isa1)&&(!isr1)){
	    		setUserInfoBoth(UserType.MANAGER);
	    	}
	    	if((isa2)&&(!isr2)){
	    		setUserInfoBoth(UserType.SELLER);
	    	}
	    	if((isa4)&&(!isr4)){
	    		setUserInfoBoth(UserType.SALESMAN);
	    	}
	    	
	    }
	    /**
	     * 修改活动徒孙数
	     * @param userType
	     */
	    public void setUserInfoBoth(UserType userType){
    		List<User> users = userService.findUserByUsertype(userType);
            for(User user : users){  
            	UserInfoBoth userInfoBoth = user.getUserInfoBoth();
            	userInfoBoth.setAcitivitySonSum(0);
            	userInfoBothService.editUserInfoBoth(userInfoBoth);
            }  
	    }
	    
	    
}

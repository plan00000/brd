package com.zzy.brd.mobile.web.controller.apprentice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.Activity.ActivitySet;
import com.zzy.brd.entity.Activity.ActivityType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.rep.apprentice.RepMyApprenticeDTO;
import com.zzy.brd.service.ActivityService;
import com.zzy.brd.service.UserService;

/**
 * 收徒页面
 * @author lzh 2016-9-28
 *
 */
@Controller
@RequestMapping("weixin/user/apprentice")
public class WeixinApprenticeController {
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping("checkUserToApprentice")
	public RepSimpleMessageDTO checkUserToApprentice(HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		if(userId != null){
			User user = userService.findById(userId);
			if(user.getUserType() != UserType.MANAGER || user.getUserType() != UserType.SELLER){
				rep.setCode(0);
				rep.setMes("请升级会融资经理或商家");
				return rep;
			}
		}else{
			rep.setCode(2);
			rep.setMes("请登录");
			return rep;
		}
		return rep;
	}
	/**
	 * 收徒页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toApprentice")
	public String toApprenticePage(HttpServletRequest request,Model model){
		Long userId = ShiroUtil.getUserId(request);
		if(userId != null){
			User user = userService.findById(userId);
			//融资经理或商家
			if(user.getUserType() == UserType.MANAGER || user.getUserType() == UserType.SELLER){
				model.addAttribute("user", user);
			}
			if(user.getUserType() == UserType.SALESMAN){
				//融资经理和商家的个数
				int manager = userService.countsByUserType(UserType.MANAGER, userId);
				int seller = userService.countsByUserType(UserType.SELLER, userId);
				//融资经理的徒弟数商家的徒弟数
				List<User> bussinessUsers = userService.findUserByUserTypeAndSalesmanId(UserType.SELLER, userId);
				List<User> managerUsers = userService.findUserByUserTypeAndSalesmanId(UserType.MANAGER,userId);
				int sellersons =0;
				int managersons = 0;
				for(User bussinessUser :bussinessUsers){
					sellersons = sellersons + bussinessUser.getUserInfoBoth().getSonSum()+bussinessUser.getUserInfoBoth().getGrandsonSum()+bussinessUser.getUserInfoBoth().getGgsonsSum();
				}
				for(User managerUser :managerUsers){
					managersons = managersons +managerUser.getUserInfoBoth().getSonSum() + managerUser.getUserInfoBoth().getGrandsonSum()+managerUser.getUserInfoBoth().getGgsonsSum();
				}
				model.addAttribute("sellersons",sellersons);
				model.addAttribute("managersons",managersons);
				model.addAttribute("user", user);
				model.addAttribute("manager", manager);
				model.addAttribute("seller", seller);
			}
			if(user.getUserType() == UserType.USER){
				return "redirect:/weixin/generalUserCenter/becomeManager";
			}
			
		}else{
			return "redirect:/weixin/usercenter/main";
		}
		return "mobile/apprentice/apprentice";
	}
	/**
	 * 我的徒弟
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("apprenticeList")
	public String apprenticeList(@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			HttpServletRequest request,Model model){
		Long userId = ShiroUtil.getUserId(request);
		if(userId ==null){
			return "redirect:/weixin/usercenter/main";
		}
		User user = userService.findById(userId);
		
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_userInfoBoth.parent.id", userId);
		Page<User> apprenticeUsers = userService.listUsers(searchParams, pageNumber,
				Constant.PAGE_SIZE, "", "",false);
		List<User> apprenticestotal = userService.listUsers(searchParams, pageNumber,
				1000000, "", "",false).getContent();
		//总金额
		BigDecimal brokerage = new BigDecimal(0);
		for(User apprentice:apprenticestotal){
			if(apprentice.getBrokerage() != null){
				brokerage = brokerage.add(apprentice.getBrokerage());
			}
		}
		//徒孙孙
		int apprenticeGGsons = user.getUserInfoBoth().getGrandsonSum()+user.getUserInfoBoth().getGgsonsSum();
		
		model.addAttribute("apprenticeUsers", apprenticeUsers);
		model.addAttribute("apprenticestotal", (int) Math.ceil((double)apprenticestotal.size()/10));
		model.addAttribute("brokerage", brokerage);
		model.addAttribute("user", user);
		model.addAttribute("apprenticeGGsons",apprenticeGGsons);
		return "mobile/apprentice/myapprentice";
	}
	/**
	 * ajax加载数据
	 * @param pageNum
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("ajaxApprenticeList")
	public String ajaxApprenticeList(@RequestParam (value="page",required = true, defaultValue = "1") int pageNum
			,HttpServletRequest request
			,Model model){
		Long userId = ShiroUtil.getUserId(request);
		if(userId ==null){
			return "redirect:/weixin/usercenter/main";
		}
		User user = userService.findById(userId);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_userInfoBoth.parent.id", userId);
		Page<User> apprenticeUsers = userService.listUsers(searchParams, pageNum,
				Constant.PAGE_SIZE, "", "",false);
		model.addAttribute("apprenticeUsers", apprenticeUsers);
		return "mobile/apprentice/ajaxmyapprentice";
	}
	
	/**
	 * 收徒送礼
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("apprenticeGift")
	public String apprenticeGift(HttpServletRequest request,Model model){
		Long userId = ShiroUtil.getUserId(request);
		User user = null;
		if(userId!=null){
			user = userService.findById(userId);
		}
		Activity activity = activityService.getRecommendRegisterByActivityType();
		model.addAttribute("activity", activity);
		model.addAttribute("user", user);
		return "mobile/apprentice/apprenticegift";
	}
	
}

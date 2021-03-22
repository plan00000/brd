package com.zzy.brd.controller.admin.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zzy.brd.algorithm.encrypt.shiro.PasswordInfo;
import com.zzy.brd.algorithm.encrypt.shiro.SHA1Encrypt;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.UserDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.rep.admin.user.RepApprenticesRecordDTO;
import com.zzy.brd.dto.rep.admin.user.RepUserApprenticesDTO;
import com.zzy.brd.dto.req.admin.user.ReqUsernidyfyImgDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
//import com.zzy.brd.entity.UserInfoSeller;
//import com.zzy.brd.entity.UserRemark;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.RoleService;
//import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.session.SessionService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.excel.ExcelUtil;
import com.zzy.brd.util.excel.ExcelUtil.ExcelBean;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.map.BaiduMapUtils;
import com.zzy.brd.util.map.baidu.dto.rep.geocoder.RepGeocoderResolve;
import com.zzy.brd.util.map.baidu.req.geocoder.ReqGeocoderResolve;
import com.zzy.brd.util.phone.PhoneUtils;
import com.zzy.brd.util.string.StringUtil;

/***
 * 后台-内部员工控制器
 * 
 * @author xpk
 *
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

	private static final Logger log = LoggerFactory.getLogger(AdminUserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SessionService sessionService;
	private @Autowired UserDao userDao;

	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			@RequestParam(value = "sortName", required = false) String sortName,
			@RequestParam(value = "sortType", required = false) String sortType,
			@RequestParam(value = "state", required = false, defaultValue = "-1") int state,
			@RequestParam(value = "userType", required = false) User.UserType userType,
			@RequestParam(value = "loginTimes", required = false, defaultValue = "0") int loginTimes,
			@RequestParam(value = "sonSum", required = false, defaultValue = "0") int sonSum,
			@RequestParam(value = "grandsonSum", required = false, defaultValue = "0") int grandsonSum,
			@RequestParam(value = "ggrandsonSum", required = false, defaultValue = "0") int ggrandsonSum,
			@RequestParam(value = "orderSum", required = false, defaultValue = "0") int orderSum,
			@RequestParam(value = "orderSuccessSum", required = false, defaultValue = "0") int orderSuccessSum,
			@RequestParam(value = "keywordType", required = false, defaultValue = "0") int keywordType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "timeRange", required = false, defaultValue = "all") String timeRange,
			@RequestParam(value = "startTimestr", required = false, defaultValue = "") String startTimestr,
			@RequestParam(value = "endTimestr", required = false, defaultValue = "") String endTimestr,
			@RequestParam(value = "userId", required = false, defaultValue = "-1") long userId,
			@RequestParam(value = "getType", required = false) String getType,
			@RequestParam(value = "lastLoginDate", required = false, defaultValue = "") String lastLoginDate,// 最后登录时间
			@RequestParam(value = "departid", required = false, defaultValue = "-1") long departid,  //所属业务员部门id
			@RequestParam(value = "contacted",required=false,defaultValue="false") boolean contacted,
			@RequestParam(value = "step",required =false,defaultValue="0") int step,
			@RequestParam(value ="registerStarttime",required=false) String registerStarttime,
			@RequestParam(value ="registerEndtime",required=false) String registerEndtime,
			Model model) {			
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<User.UserType> userTypeList = new ArrayList<User.UserType>();
		userTypeList.add(User.UserType.USER);
		userTypeList.add(User.UserType.SELLER);
		userTypeList.add(User.UserType.MANAGER);
		userTypeList.add(User.UserType.SALESMAN);
		searchParams.put("IN_userType", userTypeList);
		if (state == 0) {
			searchParams.put("EQ_state", User.State.OFF);
		} else if (state == 1) {
			searchParams.put("EQ_state", User.State.ON);
		} else {
			searchParams.put("NE_state", User.State.DEL);
		}

		if (userType != null) {
			searchParams.put("EQ_userType", userType);
		}
		if (loginTimes > 0) {
			searchParams.put("GTE_loginTimes", loginTimes);
		}
		if (sonSum > 0) {
			searchParams.put("GTE_userInfoBoth.sonSum", sonSum);
		}
		if (grandsonSum > 0) {
			searchParams.put("GTE_userInfoBoth.grandsonSum", grandsonSum);
		}
		if (orderSum > 0) {
			searchParams.put("GTE_userInfoBoth.orderSum", orderSum);
		}
		if (orderSuccessSum > 0) {
			searchParams.put("GTE_userInfoBoth.orderSuccessSum",
					orderSuccessSum);
		}
		if (ggrandsonSum > 0) {
			searchParams.put("GTE_userInfoBoth.ggsonsSum", ggrandsonSum);
		}
		if (keyword != null) {
			if (keywordType == 0) {
				searchParams.put("LIKE_username", keyword);
			} else {
				searchParams.put("LIKE_mobileno", keyword);
			}
		}
		if (!timeRange.equals("all")) {
			if (timeRange.equalsIgnoreCase("today")) {
				StringBuilder startTime = new StringBuilder(
						DateUtil.DateToString(new Date()));
				startTime.append(" 00:00:00");
				Timestamp starttimeTime = DateUtil
						.StringToTimestampLong(startTime.toString());
				StringBuilder endTime = new StringBuilder(
						DateUtil.DateToString(new Date()));
				endTime.append(" 23:59:59");
				Timestamp endtimeTime = DateUtil.StringToTimestampLong(endTime
						.toString());
				searchParams.put("GTE_createdate", starttimeTime);
				searchParams.put("LTE_createdate", endtimeTime);
			}
			if(timeRange.equalsIgnoreCase("three")){
				Timestamp endTimestamp = DateUtil.getNowTimestamp();
				searchParams.put("LTE_createdate", endTimestamp);
				StringBuilder startBuilder = new StringBuilder(DateUtil.DateToString(DateUtil.getPreDay(new Date(), -2)));
				startBuilder.append(" 00:00:00");
				Timestamp startTimeTime = DateUtil
						.StringToTimestampLong(startBuilder.toString());
				searchParams.put("GTE_createdate", startTimeTime);
			}			
			if (timeRange.equalsIgnoreCase("week")) {
				Timestamp endTimestamp = DateUtil.getNowTimestamp();
				searchParams.put("LTE_createdate", endTimestamp);
				StringBuilder startBuilder = new StringBuilder(
						DateUtil.DateToString(DateUtil.getPreWeek(new Date())));
				startBuilder.append(" 00:00:00");
				Timestamp startTimeTime = DateUtil
						.StringToTimestampLong(startBuilder.toString());
				searchParams.put("GTE_createdate", startTimeTime);
			}
			if (timeRange.equalsIgnoreCase("month")) {
				Timestamp endTimestamp = DateUtil.getNowTimestamp();
				searchParams.put("LTE_createdate", endTimestamp);
				StringBuilder startBuilder = new StringBuilder(
						DateUtil.DateToString(DateUtil.getPreDate(new Date())));
				startBuilder.append(" 00:00:00");
				Timestamp startTimeTime = DateUtil
						.StringToTimestampLong(startBuilder.toString());
				searchParams.put("GTE_createdate", startTimeTime);
			}
		}
		if (!StringUtil.isNullString(startTimestr)) {
			StringBuilder startBuilder = new StringBuilder(startTimestr);
			startBuilder.append(" 00:00:00");
			Timestamp startTimeTime = DateUtil
					.StringToTimestampLong(startBuilder.toString());
			searchParams.put("GTE_createdate", startTimeTime);
		}
		if (!StringUtil.isNullString(endTimestr)) {
			StringBuilder endBuilder = new StringBuilder(endTimestr);
			endBuilder.append(" 23:59:59");
			Timestamp endTimeTime = DateUtil.StringToTimestampLong(endBuilder
					.toString());
			searchParams.put("LTE_createdate", endTimeTime);
		}
		if (!StringUtil.isNullString(lastLoginDate)) { // 最后登录时间
			StringBuilder startBuilder = new StringBuilder(lastLoginDate);
			startBuilder.append(" 00:00:00");
			Timestamp startTimeTime = DateUtil
					.StringToTimestampLong(startBuilder.toString());
			searchParams.put("GTE_lastlogindate", startTimeTime);
			StringBuilder endBuilder = new StringBuilder(lastLoginDate);
			endBuilder.append(" 23:59:59");
			Timestamp endTimeTime = DateUtil.StringToTimestampLong(endBuilder
					.toString());
			searchParams.put("LTE_lastlogindate", endTimeTime);
		}

		if (userId > -1 && getType != null) {
			if (getType.equals("getSons")) {
				searchParams.put("EQ_userInfoBoth.parent.id", userId);
			}
			if (getType.equals("getGrandSons")) {
				searchParams.put("EQ_userInfoBoth.grandParent.id", userId);
			}
			if (getType.equals("getGGrandSons")) {
				User user = userService.findById(userId);
//				List<User> grandSons =userService.findAllGrandSon(user);
				List<User> grandSons = new ArrayList<>();
				if(grandSons.size()>0){
					List<Long> ids = Lists.transform(grandSons, new Function<User,Long>(){
						@Override
						public Long apply(User user) {
							// TODO Auto-generated method stub
							return user.getId();
						}
					});				
//					List<User> ggrandSonsList = userService.findAllGGrandSon(ids);
					List<User> ggrandSonsList = new ArrayList<>();
					List<Long> idss = new ArrayList<Long>();
					idss.addAll(ids);
					while(ggrandSonsList.size()>0){
						List<Long> idsss = Lists.transform(ggrandSonsList,new Function<User,Long>(){
							@Override
							public Long apply(User user) {
								// TODO Auto-generated method stub
								return user.getId();
							}
						});
						idss.addAll(idsss);
//						ggrandSonsList = userService.findAllGGrandSon(idsss);
					}
					searchParams.put("IN_userInfoBoth.parent.id",idss);
				} else{
					searchParams.put("EQ_userInfoBoth.grandParent.userInfoBoth.parent.id",userId);
				}
			}
			if (getType.equals("getSeller")) {
				searchParams.put("EQ_userInfoBoth.seller.id", userId);
			}
			if (getType.equals("getSales")) {
				searchParams.put("EQ_userInfoBoth.salesman.id", userId);
			}		
		}	
		if(StringUtils.isNotBlank(registerStarttime)){
			StringBuilder sStartTimeStr = new StringBuilder(registerStarttime);
			sStartTimeStr.append(" 00:00:00");
			Date sStartTimeTime = DateUtil.StringToTimestampLong(sStartTimeStr.toString());
			searchParams.put("GTE_createdate",sStartTimeTime);
		}
		if(StringUtils.isNotBlank(registerEndtime)){
			StringBuilder sEndTimeStr = new StringBuilder(registerEndtime);
			sEndTimeStr.append(" 23:59:59");
			Date sEndTimeTime = DateUtil.StringToTimestampLong(sEndTimeStr.toString());
			searchParams.put("LTE_createdate",sEndTimeTime);
		}
		
		if(departid > 0){
			searchParams.put("EQ_userInfoBoth.salesman.userInfoEmployee.department.id", departid);
		}
			
		Page<User> users = userService.listUsers(searchParams, pageNumber,Constant.PAGE_SIZE, sortName, sortType,contacted);
		model.addAttribute("registerStarttime",registerStarttime);
		model.addAttribute("registerEndtime", registerEndtime);
		model.addAttribute("getType", getType);
		model.addAttribute("userId", userId);
		model.addAttribute("users", users);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
		model.addAttribute("state", state);
		model.addAttribute("userType", userType);
		model.addAttribute("loginTimes", loginTimes);
		model.addAttribute("sonSum", sonSum);
		model.addAttribute("grandsonSum", grandsonSum);
		model.addAttribute("ggrandsonSum", ggrandsonSum);
		model.addAttribute("orderSum", orderSum);
		model.addAttribute("orderSuccessSum", orderSuccessSum);
		model.addAttribute("keywordType", keywordType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("timeRange", timeRange);
		model.addAttribute("startTimestr", startTimestr);
		model.addAttribute("endTimestr", endTimestr);
		model.addAttribute("userId", userId);
		model.addAttribute("getType", getType);
		model.addAttribute("lastLoginDate", lastLoginDate);
		model.addAttribute("departid", departid);
		model.addAttribute("contacted",contacted );
		model.addAttribute("step", step);
		return "admin/user/list";
	}

	@RequestMapping(value = "toAddUser")
	public String toAddUser() {

		return "admin/user/addUser";
	}

	@RequestMapping(value = "addUser")
	@ResponseBody
	public RepSimpleMessageDTO addUser(
			int userType,String phone,String password,
			@RequestParam(value = "recommonedPhone", required = false) String recommonedPhone,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "idcard", required = false) String idcard,
			@RequestParam(value = "nickname", required = false) String nickname,
			@RequestParam(value = "company", required = false) String company,
			@RequestParam(value = "address",required=false)String address,
			HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		phone = phone.trim();
		name = name.trim();
		nickname = nickname.trim();
		List<User.UserType> typeList =new ArrayList<User.UserType>();
		typeList.add(User.UserType.USER);
		typeList.add(User.UserType.MANAGER);
		typeList.add(User.UserType.SALESMAN);
		typeList.add(User.UserType.SELLER);
		User exitUser = userService.findByMobileAndUserType(phone, typeList);
		if(exitUser !=null){
			res.setCode(1);
			res.setMes("该号码已经注册");
			return res;			
		}
		User user = new User();
		String expands= "";
		User parent =null;
		if (userType == 0) {
			user.setUserType(User.UserType.USER);
			user.setUsername(nickname);
			user.setRealname(nickname);
		} else if (userType == 1) {
			user.setUserType(User.UserType.MANAGER);
			user.setUsername(name);
			user.setRealname(name);
			expands = idcard;
		} else if (userType == 2) {
			user.setUserType(User.UserType.SELLER);
			user.setUsername(name);
			user.setRealname(name);
			expands = company;
		}
		user.setMobileno(phone.trim());
		user.setCreatedate(new Date());
		user.setPassword(password.toLowerCase());
		user.setState(User.State.ON);
		return res;
	}

	@RequestMapping(value = "toEditUser/{userId}")
	public String toEditUser(
			@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			@RequestParam(value = "type", required = false, defaultValue = "brokerage") String type,
			@PathVariable long userId, Model model) {
		User user = userService.findById(userId);

		Map<String, Object> searchParams = new HashMap<String, Object>();
		// /下方的列表日志
		if (type.equals("brokerage")) {
			// 获取佣金记录
			searchParams.put("EQ_user", user);

		} else if (type.equals("withdraw")) {
			// 获取提现记录
			searchParams.put("EQ_user", user);

		} else if (type.equals("apprentice")) {
			// 获取收徒记录
			Page<RepApprenticesRecordDTO> page = null;
			model.addAttribute("page", page);
		} else if (type.equals("loginlog")) {
			// 获取登录日志
			searchParams.put("EQ_user", user);

		}

		// 佣金总额
		BigDecimal total = new BigDecimal(0);
		//生成二维码图片
		if(!user.getUserType().equals(User.UserType.USER)){
//			userInfoBothService.createQrcodeImg(user);
		}
		model.addAttribute("total", total);
//		model.addAttribute("bankinfoList", userBankinfoList);
//		model.addAttribute("remarkList", userRemarkList);
		model.addAttribute("userId", userId);
		model.addAttribute("type", type);
		model.addAttribute("users", user);
//		model.addAttribute("userBank", bankInfo);
		return "admin/user/editUser";
	}

	@RequestMapping(value = "modifyUserImage")
	@ResponseBody
	public RepSimpleMessageDTO modifyUserImage(HttpServletRequest request,@Valid ReqUsernidyfyImgDTO dto,
			BindingResult result) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		User user = userService.findById(dto.getId());
		if (user == null) {
			res.setCode(0);
			res.setMes("该用户不存在");
			return res;
		}
		if (!StringUtil.isNullString(dto.getHeadimgurl())) {
			String imgurl = dto.getHeadimgurl();
			imgurl = FileUtil.moveFileToPro(imgurl);
			user.setHeadimgurl(imgurl);
		}
		if (userService.editUser(user)) {
			res.setCode(1);
			res.setMes("修改成功");
			return res;
		}
		res.setCode(0);
		res.setMes("头像修改失败");
		return res;
	}

	/*** 修改会员电话号码 */
	@RequestMapping(value = "modifyPhone", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO modifyPhone(long userId, String phone,HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		User user = userService.findById(userId);
		if(!user.getMobileno().equals(phone)){
			if(!user.getUserType().equals(User.UserType.SALESMAN)){
				if(phone.trim().length()!=11){
					res.setCode(1);
					res.setMes("请输入11位手机号码");
					return res;
				}			
				if (PhoneUtils.getPhoneProv(phone).equals("未知")) {
					res.setCode(1);
					res.setMes("请输入正确的号码");
					return res;
				}
				if (!PhoneUtils.getPhoneProv(phone).contains("福建")) {
					res.setCode(1);
					res.setMes("手机号码必须为福建号码");
					return res;
				}
			}
			//验证是否重复
			List<User.UserType> typeList = new ArrayList<User.UserType>();
			typeList.add(User.UserType.USER);
			typeList.add(User.UserType.MANAGER);
			typeList.add(User.UserType.SALESMAN);
			typeList.add(User.UserType.SELLER);
			User exitUser = userService.findByMobileAndUserType(phone, typeList);
			if(exitUser !=null && exitUser!=user){
				res.setCode(1);
				res.setMes("该号码已经注册过");
				return res;
			}	
			String oldMobileno = user.getMobileno();
			user.setMobileno(phone.trim());
			if(userService.editUser(user)){
				res.setCode(0);
				res.setMes("修改成功");
				//加入日志
				long opertorId = ShiroUtil.getUserId(request);
				String content ="将"+user.getRealname()+"手机号码由:"+oldMobileno+"改为:"+ phone;
//				userOperlogService.addOperlog(userService.findById(opertorId), content);
				return res;
			}else{
				res.setCode(1);
				res.setMes("修改失败请重试");
				return res;
			}
		}else{
			res.setCode(0);
			res.setMes("修改成功");
			return res;
		}
	}
	
	@RequestMapping("changeState")
	@ResponseBody
	public String changeState(long id, User.State state,HttpServletRequest request) {
		if (userService.updateWithState(id, state)) {
			User user = userService.findById(id);
			long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = state.getStr()+"会员:"+user.getRealname();
//			userOperlogService.addOperlog(opertor, content);
			return "0";
		}
		return "-1";
	}
	
	/***
	 * 修改会员密码
	 * @param  id
	 * @param password
	 * @return
	 * */
	@RequestMapping(value="modifyPassword",method =RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO modifyPassword(long id,String password) {
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		User user = userService.findById(id);
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(password);
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());
		userService.editUser(user);	
		return dto;
	}
	
	
}

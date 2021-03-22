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
import com.zzy.brd.dao.UserInfoBothDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.rep.admin.user.RepApprenticesRecordDTO;
import com.zzy.brd.dto.rep.admin.user.RepUserApprenticesDTO;
import com.zzy.brd.dto.req.admin.user.ReqUsernidyfyImgDTO;
import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.Loginlog;
import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.UserBankinfo;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.UserInfoSeller;
//import com.zzy.brd.entity.UserRemark;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.DepartmentService;
import com.zzy.brd.service.FlowWithdrawService;
import com.zzy.brd.service.LoginlogService;
import com.zzy.brd.service.RecordBrokerageService;
import com.zzy.brd.service.RoleService;
import com.zzy.brd.service.UserBankinfoService;
import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserInfoSellerService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserRemarkService;
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
	private @Autowired UserDao userDao;
	private @Autowired UserOperLogService userOperlogService;
	private @Autowired UserInfoBothDao userInfoBothDao;
	private @Autowired UserInfoBothService userInfoBothService;
	private @Autowired UserInfoSellerService userInfoSellerService;
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
				List<User> grandSons =userService.findAllGrandSon(user);
				if(grandSons.size()>0){
					List<Long> ids = Lists.transform(grandSons, new Function<User,Long>(){
						@Override
						public Long apply(User user) {
							// TODO Auto-generated method stub
							return user.getId();
						}
					});				
					List<User> ggrandSonsList = userService.findAllGGrandSon(ids);
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
						ggrandSonsList = userService.findAllGGrandSon(idsss);
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
		/*String place = PhoneUtils.getPhoneProv(phone);
		if(place.equalsIgnoreCase("未知") || place.length() == 0 ){
			res.setCode(1);
			res.setMes("请输入正确的手机号码");
			return res;
		}
		if(!place.contains("福建")){
			res.setCode(1);
			res.setMes("手机号必须为福建省号码");
			return res;
		}*/
		User user = new User();
		String expands= "";
		User parent =null;
		if(!StringUtil.isNullString(recommonedPhone) ){
			parent = userService.findByAskperson(recommonedPhone);
			if (parent == null) {
				res.setCode(1);
				res.setMes("推荐人不存在");
				return res;
			}
			if (parent == user) {
				res.setCode(1);
				res.setMes("推荐码不能为自己");
				return res;
			}
			if (parent.getUserType().equals(User.UserType.USER)) {
				res.setCode(1);
				res.setMes("该推荐人为普通会员不具备收徒权力");
				return res;
			}
			
			if(userType == 2 && !parent.getUserType().equals(User.UserType.SALESMAN)){
				res.setCode(1);
				res.setMes("商家推荐人必须为业务员");
				return res;
			}	
		}
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
		if (userService.registerUser(user, parent, expands,address)) {
			res.setCode(0);
			res.setMes("添加成功");
			//添加日志
			long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = "添加"+user.getUserType().getStr()+":";
			if(user.getUserType().equals(User.UserType.USER)){
				content = content + user.getUsername();
			} else {
				content = content + user.getRealname();
			}			
			userOperlogService.addOperlog(opertor, content);
			
			return res;
		} else {
			res.setCode(1);
			res.setMes("请输入详细地址");
			return res;
		}
	}

	@RequestMapping(value = "toEditUser/{userId}")
	public String toEditUser(
			@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			@RequestParam(value = "type", required = false, defaultValue = "brokerage") String type,
			@PathVariable long userId, Model model) {
		User user = userService.findById(userId);

		List<UserBankinfo> bankInfo = bankinfoService.getUserBankByUser(user);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		// /下方的列表日志
		if (type.equals("brokerage")) {
			// 获取佣金记录
			searchParams.put("EQ_user", user);
			Page<RecordBrokerage> page = recordService.listRecordBrokerage(
					searchParams, pageNumber);
			model.addAttribute("page", page);
		} else if (type.equals("withdraw")) {
			// 获取提现记录
			searchParams.put("EQ_user", user);
			searchParams.put("EQ_status",FlowWithdraw.WithdrawStatus.ALEARDYLOAN);
			Page<FlowWithdraw> page = withdrawService.getFlowWithdraw(
					searchParams, false, pageNumber, Constant.PAGE_SIZE,"");
			model.addAttribute("page", page);
		} else if (type.equals("apprentice")) {
			// 获取收徒记录
			Page<RepApprenticesRecordDTO> page = userService
					.listApprenticesRecord(user, pageNumber);
			model.addAttribute("page", page);
		} else if (type.equals("loginlog")) {
			// 获取登录日志
			searchParams.put("EQ_user", user);
			Page<Loginlog> page = loginlogService.listLoginlog(searchParams,
					pageNumber);
			model.addAttribute("page", page);

		}
		// 获取备注列表
//		List<UserRemark> userRemarkList = userRemarkService.findByUser(user);
		// 绑定银行卡
		List<UserBankinfo> userBankinfoList = bankinfoService
				.getUserBankByUser(user);

		// 佣金总额
		BigDecimal total = user.getUserInfoBoth().getBrokerageCanWithdraw().add(user.getUserInfoBoth().getBrokerageHaveWithdraw()).add(user.getUserInfoBoth().getBrokerageWithdrawing());

		//生成二维码图片
		if(!user.getUserType().equals(User.UserType.USER)){
			userInfoBothService.createQrcodeImg(user);
		}
		model.addAttribute("total", total);
		model.addAttribute("bankinfoList", userBankinfoList);
//		model.addAttribute("remarkList", userRemarkList);
		model.addAttribute("userId", userId);
		model.addAttribute("type", type);
		model.addAttribute("users", user);
		model.addAttribute("userBank", bankInfo);
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

	/** 导出表格 */
	@RequestMapping("export")
	public void userExport(
			@RequestParam(value = "sortName", required = false) String sortName,
			@RequestParam(value = "sortType", required = false) String sortType,
			@RequestParam(value = "state", required = false, defaultValue = "-1") int state,
			@RequestParam(value = "userType", required = false) User.UserType userType,
			@RequestParam(value = "loginTimes", required = false, defaultValue = "0") int loginTimes,
			@RequestParam(value = "sonSum", required = false, defaultValue = "0") int sonSum,
			@RequestParam(value = "grandsonSum", required = false, defaultValue = "0") int grandsonSum,
			@RequestParam(value = "orderSum", required = false, defaultValue = "0") int orderSum,
			@RequestParam(value = "orderSuccessSum", required = false, defaultValue = "0") int orderSuccessSum,
			@RequestParam(value = "keywordType", required = false, defaultValue = "0") int keywordType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "timeRange", required = false, defaultValue = "all") String timeRange,
			@RequestParam(value = "startTimestr", required = false, defaultValue = "") String startTimestr,
			@RequestParam(value = "endTimestr", required = false, defaultValue = "") String endTimestr,
			@RequestParam(value = "userId", required = false, defaultValue = "-1") long userId,
			@RequestParam(value = "getType", required = false) String getType,
			@RequestParam(value = "ggrandsonSum", required = false, defaultValue = "0") int ggrandsonSum,
			@RequestParam(value = "lastLoginDate", required = false, defaultValue = "") String lastLoginDate,// 最后登录时间
			@RequestParam(value = "departid", required = false, defaultValue = "-1") long departid,  //所属业务员部门id
			@RequestParam(value = "contacted",required=false,defaultValue="false") boolean contacted,
			@RequestParam(value ="registerStarttime",required=false) String registerStarttime,
			@RequestParam(value ="registerEndtime",required=false) String registerEndtime,
			HttpServletResponse response) {
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
				List<User> grandSons =userService.findAllGrandSon(user);
				if(grandSons.size()>0){
					List<Long> ids = Lists.transform(grandSons, new Function<User,Long>(){
						@Override
						public Long apply(User user) {
							// TODO Auto-generated method stub
							return user.getId();
						}
					});				
					List<User> ggrandSonsList = userService.findAllGGrandSon(ids);
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
						ggrandSonsList = userService.findAllGGrandSon(idsss);
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
		int pageNumber = 1;
		Page<User> users = userService.listUsers(searchParams, pageNumber,
				Constant.PAGE_SIZE, sortName, sortType,contacted);
		List<User> list = Lists.newArrayList();
		
		while (users != null && users.getNumberOfElements() > 0) {
			list.addAll(users.getContent());
			if (!users.hasNext()) {
				break;
			} else {
				pageNumber++;
			}
			users = userService.listUsers(searchParams, pageNumber,
					Constant.PAGE_SIZE, sortName, sortType,contacted);
		}
		String[] titles = { "会员名", "身份", "师父", "所属商家", "所属业务员", "徒弟数", "徒孙数",
				"徒孙孙数","登录数", "订单数", "成功订单" };
		ExcelBean excelBean = new ExcelBean("会员.xls", "会员", titles);
		for (User u : list) {
			String data[] = this.getDataList(u);
			excelBean.add(data);
		}
		try {
			ExcelUtil.export(response, excelBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("错误:" + e.getMessage());
		}

	}

	public String[] getDataList(User user) {
		String name = user.getUsername();
		String userType = user.getUserType().getStr();
		String parent = "无";
		if (user.getUserInfoBoth().getParent() != null) {
			parent = user.getUserInfoBoth().getParent().getUsername();
		}
		String seller = "无";
		if (user.getUserInfoBoth().getSeller() != null) {
			seller = user.getUserInfoBoth().getSeller().getUsername();
		}
		String salesman = "无";
		if (user.getUserInfoBoth().getSalesman() != null) {
			salesman = user.getUserInfoBoth().getSalesman().getUsername();
		}
		String sonSum = String.valueOf(user.getUserInfoBoth().getSonSum());
		String grandSonSum = String.valueOf(user.getUserInfoBoth()
				.getGrandsonSum());
		String ggrandSonSum = String.valueOf(user.getUserInfoBoth()
				.getGgsonsSum());
		String loginTimes = String.valueOf(user.getLoginTimes());
		String orderSum = String.valueOf(user.getUserInfoBoth().getOrderSum());
		String orderSuccess = String.valueOf(user.getUserInfoBoth()
				.getOrderSuccessSum());
		String data[] = { name, userType, parent, seller, salesman, sonSum,
				grandSonSum, ggrandSonSum,loginTimes,orderSum, orderSuccess };

		return data;

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
			user.getUserInfoBoth().setRecommendCode(phone.trim());
			if(userService.editUser(user)){
				res.setCode(0);
				res.setMes("修改成功");
				//加入日志
				long opertorId = ShiroUtil.getUserId(request);
				String content ="将"+user.getRealname()+"手机号码由:"+oldMobileno+"改为:"+ phone;
				userOperlogService.addOperlog(userService.findById(opertorId), content);
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

	/** 添加备注 */
	@RequestMapping(value = "addRemarks")
	@ResponseBody
	public RepSimpleMessageDTO addRemark(long userId, String remark,
			HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		long opertorId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		/*UserRemark userRemark = new UserRemark();
		userRemark.setCreateTime(new Date());
		userRemark.setOpertor(userService.findById(opertorId));
		userRemark.setUser(user);
		userRemark.setRemark(remark);*/
//		userRemarkService.editRemark(userRemark);
		String content =user.getRealname()+"添加备注:"+remark;
		userOperlogService.addOperlog(userService.findById(opertorId), content);
		res.setCode(0);
		res.setMes("添加成功");
		return res;
	}

	/**
	 * 改变用户类型 升级
	 **/
	@RequestMapping(value = "changeUserType")
	@ResponseBody
	public RepSimpleMessageDTO changeUserType(long userId,
			User.UserType userType,
			@RequestParam(value="companyname",required=false) String companyname,
			@RequestParam(value="address",required=false) String address,
			@RequestParam(value="idcard",required=false) String idcard,
			HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		User user = userService.findById(userId);
		User.UserType oldUserType = user.getUserType();
		user.setUserType(userType);		
		user.getUserInfoBoth().setRecommendCode(user.getMobileno());
		userInfoBothDao.save(user.getUserInfoBoth());
		if(oldUserType.equals(User.UserType.USER)&& userType.equals(User.UserType.MANAGER)){
			user.getUserInfoBoth().setExpands(idcard);
			user.getUserInfoBoth().setRecommendCode(user.getMobileno());
			userInfoBothDao.save(user.getUserInfoBoth());
			userService.editUser(user);
			res.setCode(0);
			res.setMes("修改成功");
		} 
		///	
		UserInfoSeller infoSeller = new UserInfoSeller();
		if(address!=null && userType.equals(User.UserType.SELLER) ){
			try{
				ReqGeocoderResolve req = new ReqGeocoderResolve();
				req.setOutput(ReqGeocoderResolve.OutputType.JSON.getStr());
				req.setAddress(address);
				req.setAk(ConfigSetting.baidu_map_api_for_service);
				RepGeocoderResolve rep = BaiduMapUtils.addressGeocoding(req);
				//纬度
				double latitude = rep.getResult().getLocation().getLat();
				//经度
				double longitude = rep.getResult().getLocation().getLng();
				infoSeller.setAddress(address);
				infoSeller.setLatitude(String.valueOf(latitude));
				infoSeller.setLongitude(String.valueOf(longitude));
				infoSeller.setCompany(companyname);
				userInfoSellerService.editUserInfoSeller(infoSeller);
			}catch(Exception e){
				res.setCode(1);
				res.setMes("请输入正确的地址");
				return res;
			}		
		}
		if(oldUserType.equals(User.UserType.USER)&& userType.equals(User.UserType.SELLER)){
			List<User> editUser = new ArrayList<User>();
			User parent = user.getUserInfoBoth().getParent();
			user.setUserInfoSeller(infoSeller);
			if(parent!=null){
				parent.getUserInfoBoth().setSonSum(parent.getUserInfoBoth().getSonSum()-1);
				editUser.add(parent);
			}
			User grandParent = user.getUserInfoBoth().getGrandParent();
			if(grandParent!=null){
				grandParent.getUserInfoBoth().setSonSum(grandParent.getUserInfoBoth().getSonSum()-1);
				User ggrandParent = grandParent.getUserInfoBoth().getParent();
				while(ggrandParent!=null){
					ggrandParent.getUserInfoBoth().setGgsonsSum(ggrandParent.getUserInfoBoth().getGgsonsSum()-1);
					editUser.add(ggrandParent);		
					ggrandParent  =  ggrandParent.getUserInfoBoth().getParent();
				}
			}
			user.getUserInfoBoth().setRecommendCode(user.getMobileno());
			user.getUserInfoBoth().setParent(null);
			user.getUserInfoBoth().setSeller(null);
			user.getUserInfoBoth().setSalesman(null);
			editUser.add(user);
			userDao.save(editUser);
			userInfoBothDao.save(user.getUserInfoBoth());
			res.setCode(0);
			res.setMes("修改成功");
		}
		if(oldUserType.equals(User.UserType.MANAGER)&& userType.equals(User.UserType.SELLER)){
			user.setUserInfoSeller(infoSeller);
			UserInfoBoth infoBoth = user.getUserInfoBoth();
			User parent = infoBoth.getParent();
			User grandParent = infoBoth.getGrandParent();
			User seller  = infoBoth.getSeller();
			User salesman = infoBoth.getSalesman();
			//本人 商家业务员师父清空
			infoBoth.setParent(null);
			infoBoth.setGrandParent(null);
			infoBoth.setSeller(null);
			infoBoth.setSalesman(null);
			//本人的徒弟清空 保留师父
			List<User> sonsList = userService.findAllSons(user);
			if(sonsList.size()>0){
				for(User son:sonsList){
					son.getUserInfoBoth().setGrandParent(null);
					son.getUserInfoBoth().setSalesman(null);
					son.getUserInfoBoth().setSeller(user);					
					userInfoBothDao.save(son.getUserInfoBoth());
				}
			}
			//本人的徒孙的师父 师公清空  be
			List<User> grandSonsList = userService.findAllGrandSon(user);
			if(grandSonsList.size()>0){
				List<Long> ggrandSonsIds = new ArrayList<Long>();
				for(User grandSon:grandSonsList){
					ggrandSonsIds.add(grandSon.getId());
					if(parent!=null){
						grandSon.getUserInfoBoth().setParent(parent);
						grandSon.getUserInfoBoth().setGrandParent(parent.getUserInfoBoth().getParent());
						userInfoBothDao.save(grandSon.getUserInfoBoth());
					}else{
						grandSon.getUserInfoBoth().setParent(null);
						grandSon.getUserInfoBoth().setGrandParent(null);
						userInfoBothDao.save(grandSon.getUserInfoBoth());
					}
					userInfoBothDao.save(grandSon.getUserInfoBoth());
				}
				if(ggrandSonsIds.size()>0){
					List<User> ggrandSonsList = userService.findAllGGrandSon(ggrandSonsIds);
					for(User ggrandSons :ggrandSonsList){
						if(parent!=null){
							ggrandSons.getUserInfoBoth().setGrandParent(parent);
							userInfoBothDao.save(ggrandSons.getUserInfoBoth());
						}else{
							ggrandSons.getUserInfoBoth().setGrandParent(null);
							userInfoBothDao.save(ggrandSons.getUserInfoBoth());
						}
					}
					
				}				
			}
			//重新统计人数
			userService.staticsUserSonsAndGrandSons(user);
			if(sonsList.size()>0){
				for(User son:sonsList){
					userService.staticsUserSonsAndGrandSons(son);
				}
			}
			if(parent!=null){
				userService.staticsUserSonsAndGrandSons(parent);
			}
			if(grandParent!=null){
				userService.staticsUserSonsAndGrandSons(grandParent);
				while(grandParent.getUserInfoBoth().getParent()!=null){
					userService.staticsUserSonsAndGrandSons(grandParent.getUserInfoBoth().getParent());
					grandParent = grandParent.getUserInfoBoth().getParent();
				}				
			}
			if(seller!=null){
				userService.staticsUserSonsAndGrandSons(seller);
			}
			if(salesman!=null){
				userService.staticsUserSonsAndGrandSons(salesman);
			}
			res.setCode(0);
			res.setMes("修改成功");
		}	
		if(companyname!=null && userType.equals(User.UserType.SELLER)){
			user.getUserInfoBoth().setExpands(companyname);			
		}
		//添加进日志:
		long opertorId = ShiroUtil.getUserId(request);
		String content ="提升"+user.getRealname()+"为"+userType.getStr();
		userOperlogService.addOperlog(userService.findById(opertorId), content);
		return res;
	}
	
	
	/**查看徒弟徒孙徒孙孙*/
	/***
	 * @param id
	 * @param flag
	 * return  
	 */
	@RequestMapping(value="getSons")
	@ResponseBody
	public List<RepUserApprenticesDTO> getSons(long id,boolean flag,boolean isFirst,int i,String getType){
		List<RepUserApprenticesDTO> rep = new ArrayList<RepUserApprenticesDTO>();
		User user = userService.findById(id);
		List<User> userList = new ArrayList<User>();
		if(getType.equals("getSons")){
			userList = userService.findAllSons(user);
			rep =Lists.transform(userList, new Function<User,RepUserApprenticesDTO>(){
				@Override
				public RepUserApprenticesDTO apply(User user) {
					// TODO Auto-generated method stub
					return new RepUserApprenticesDTO(1,user);
				}
			});
		} else if (getType.equals("getGrandSons")){
			userList = userService.findAllGrandSon(user);
			rep =Lists.transform(userList, new Function<User,RepUserApprenticesDTO>(){
				@Override
				public RepUserApprenticesDTO apply(User user) {
					// TODO Auto-generated method stub
					return new RepUserApprenticesDTO(2,user);
				}
			});
		} else if ( getType.equals("getGgrandSons")) {
			  userList = userService.findAllGrandSon(user);
			  List<Long> ids = Lists.transform(userList,new Function<User,Long>(){
					@Override
					public Long apply(User user) {
						// TODO Auto-generated method stub
						return user.getId();
					}			
			 });
			 userList = userService.findAllGGrandSon(ids);
			 List<User> repUser = new ArrayList<User>();
 			 while(userList.size()>0){
				 repUser.addAll(userList);
 				 ids = Lists.transform(userList,new Function<User,Long>(){
						@Override
						public Long apply(User user) {
							// TODO Auto-generated method stub
							return user.getId();
						}			
				});
				userList = userService.findAllGGrandSon(ids);
			 }
			 rep =  Lists.transform(repUser,new Function<User,RepUserApprenticesDTO>(){
				@Override
				public RepUserApprenticesDTO apply(User user) {
					// TODO Auto-generated method stub
					return new RepUserApprenticesDTO(3,user);
				}
			 });
		}
		return rep;
	}
	
	
	@RequestMapping("changeState")
	@ResponseBody
	public String changeState(long id, User.State state,HttpServletRequest request) {
		if (userService.updateWithState(id, state)) {
			User user = userService.findById(id);
			long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = state.getStr()+"会员:"+user.getRealname();
			userOperlogService.addOperlog(opertor, content);
			return "0";
		}
		return "-1";
	}
	
	//分配业务员
	@RequestMapping(value = "bindSalesman", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO bindSalesman(Long userId, String salesmanMobile,HttpServletRequest request){
		RepSimpleMessageDTO rep  = new RepSimpleMessageDTO();
		if(salesmanMobile ==null){
			rep.setCode(0);
			rep.setMes("业务员手机号不能为空");
			return rep;
		}
		
		User salesmanUser = userService.findByMobileno(salesmanMobile);
		if(salesmanUser == null){
			rep.setCode(0);
			rep.setMes("不存在该业务员");
			return rep;
		}
		if(!salesmanUser.getUserType().equals(User.UserType.SALESMAN)){
			rep.setCode(0);
			rep.setMes("请输入身份为业务员的号码");
			return rep;
		}		
		User bussinessUser = userService.findById(userId);
		
		UserInfoBoth bussinessUserInfoBoth = bussinessUser.getUserInfoBoth();
		if(bussinessUserInfoBoth.getSalesman()!=null){
			rep.setCode(0);
			rep.setMes("该商家已有业务员");
			return rep;
		}
		bussinessUserInfoBoth.setSalesman(salesmanUser);
		//商家的师父变成该业务员
		bussinessUserInfoBoth.setParent(salesmanUser);
		if(userInfoBothService.editUserInfoBoth(bussinessUserInfoBoth)){
			//商家底下所有的会员的业务员改成该业务员
			List<User> apprenticeUsers = userService.findUserByBussiness(userId);
			//所有数量
			int appenticeUsersNum = apprenticeUsers.size();
			for(User user:apprenticeUsers){
				UserInfoBoth userInfoBoth = user.getUserInfoBoth();
				userInfoBoth.setSalesman(salesmanUser);
				userInfoBothService.editUserInfoBoth(userInfoBoth);
			}
			//师父为商家的会员，其师公改为该业务员
			List<User> sonUsers = userService.findSonUsers(userId);
			//个数
			int sonUsersNum = sonUsers.size();
			for(User user:sonUsers){
				UserInfoBoth userInfoBoth = user.getUserInfoBoth();
				userInfoBoth.setGrandParent(salesmanUser);
				userInfoBothService.editUserInfoBoth(userInfoBoth);
			}
			/*//师公为商家的会员，
			int grandSonUsersNum = userService.countGrandSonsForBussiness(userId);*/
			//修改该业务员的徒弟，徒孙，徒孙孙的数量
			UserInfoBoth salesmanUserInfoBoth = salesmanUser.getUserInfoBoth();
			salesmanUserInfoBoth.setSonSum(salesmanUserInfoBoth.getSonSum()+1);
			salesmanUserInfoBoth.setGrandsonSum(salesmanUserInfoBoth.getGrandsonSum()+sonUsersNum);
			
			salesmanUserInfoBoth.setGgsonsSum(salesmanUserInfoBoth.getGgsonsSum()+appenticeUsersNum-sonUsersNum);
			userInfoBothService.editUserInfoBoth(salesmanUserInfoBoth);
			
			rep.setCode(1);
			rep.setMes("success");
			
			long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = bussinessUser.getRealname()+"分配业务员为"+salesmanUser.getRealname();	
			userOperlogService.addOperlog(opertor, content);
			
			return rep;
		}
		rep.setCode(0);
		rep.setMes("修改失败");
		return rep;
	}
	
	
	/**修改商家地址**/
	@RequestMapping(value="modifyAddress")
	@ResponseBody
	public RepSimpleMessageDTO modifyAddress(String address,long userId,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		User user =userService.findById(userId);
		UserInfoSeller infoSeller = user.getUserInfoSeller();
		try{
			ReqGeocoderResolve req = new ReqGeocoderResolve();
			req.setOutput(ReqGeocoderResolve.OutputType.JSON.getStr());
			req.setAddress(address);
			req.setAk(ConfigSetting.baidu_map_api_for_service);
			RepGeocoderResolve rep = BaiduMapUtils.addressGeocoding(req);
			//纬度
			double latitude = rep.getResult().getLocation().getLat();
			//经度
			double longitude = rep.getResult().getLocation().getLng();
			infoSeller.setAddress(address);
			infoSeller.setLatitude(String.valueOf(latitude));
			infoSeller.setLongitude(String.valueOf(longitude));
			if(userInfoSellerService.editUserInfoSeller(infoSeller)){
				res.setCode(0);
				res.setMes("修改成功");
				long opertorid = ShiroUtil.getUserId(request);
				User opertor = userService.findById(opertorid);
				String content = "修改"+user.getRealname()+"商家地址为:"+address;	
				userOperlogService.addOperlog(opertor, content);
				
			}else{
				res.setCode(1);
				res.setMes("修改失败");
			}
		}catch(Exception e){
			res.setCode(1);
			res.setMes("请输入详细地址");
		}		
		return res;
	}
	/**
	 * 检查couponId是否已被删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value ="check/{id}",method = RequestMethod.POST)
	@ResponseBody
	public String checkUserId(@PathVariable("id") long id){
		User user = userService.findById(id);
		if(user == null){
			return "0";
		}else{
			if(user.getState()== State.DEL){
				return "0";
			}
			return "1";
		}
	}
	/**
	 * 下载二维码图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value="downLoadCode/{id}")
	public void downLoadCode(@Valid @PathVariable("id") long id,
			ServletRequest request, HttpServletResponse response){
		RepSimpleMessageDTO repdto = new RepSimpleMessageDTO();
		User user = userService.findById(id);
		
		/*String strUrl= ConfigSetting.appfileProUrlByFilePath(coupons.getCouponCode());
		String filePath = strUrl;*/
		String prodir = ConfigSetting.proDir();
		String filePath = (prodir+user.getUserInfoBoth().getQrCode()).replace(File.separator, "/");
		
		log.info("二维码下载地址："+filePath);
		
		String showName = user.getUsername()+".png";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(filePath));
			String agent = ((HttpServletRequest) request).getHeader("USER-AGENT");
			String encodeFileName = new String(showName.replace(" ", "").getBytes("utf-8"), "ISO8859-1");
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				encodeFileName = URLEncoder.encode(showName.replace(" ", ""), "utf-8");
			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + encodeFileName + "\"");
			OutputStream outputStream = response.getOutputStream();
			int i = -1;
			while ((i = inputStream.read()) != -1) {
				outputStream.write(i);
			}
			inputStream.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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

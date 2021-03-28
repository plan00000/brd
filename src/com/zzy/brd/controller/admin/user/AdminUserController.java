package com.zzy.brd.controller.admin.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.zzy.brd.dto.rep.admin.user.RepApprenticesRecordDTO;
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

import com.zzy.brd.algorithm.encrypt.shiro.PasswordInfo;
import com.zzy.brd.algorithm.encrypt.shiro.SHA1Encrypt;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.UserDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.req.admin.user.ReqUsernidyfyImgDTO;
import com.zzy.brd.entity.User;
//import com.zzy.brd.entity.UserInfoSeller;
//import com.zzy.brd.entity.UserRemark;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.RoleService;
//import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.session.SessionService;
//import com.zzy.brd.util.excel.ExcelUtil;
//import com.zzy.brd.util.excel.ExcelUtil.ExcelBean;
import com.zzy.brd.util.file.FileUtil;
//import com.zzy.brd.util.phone.PhoneUtils;
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
	public String list(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
			, @RequestParam(value = "status",required = false,defaultValue = "") String status
			, @RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
			, @RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
			, @RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
			, @RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
            ,Model model) {
		Map<String,Object> searchParams = new HashMap<String, Object>();
		if(!StringUtils.isBlank(searchName)){
			if("username".equals(searchName)){
				String search = "LIKE_username";
				searchParams.put(search, searchValue);
			}
		}
			
		Page<User> users = userService.listUsers(searchParams, pageNum,Constant.PAGE_SIZE, sortName, sortType);
		model.addAttribute("users", users);
		model.addAttribute("sortName", sortName);
		model.addAttribute("sortType", sortType);
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

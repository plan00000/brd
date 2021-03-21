package com.zzy.brd.controller.admin.employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zzy.brd.algorithm.encrypt.shiro.PasswordInfo;
import com.zzy.brd.algorithm.encrypt.shiro.SHA1Encrypt;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.EmployeeDao;
import com.zzy.brd.dao.RoleDao;
import com.zzy.brd.dao.UserInfoBothDao;
import com.zzy.brd.dao.UserInfoEmployeeDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.rep.admin.department.RepDepartmentDetailDTO;
import com.zzy.brd.dto.rep.admin.department.RepDepartmentListDTO;
import com.zzy.brd.dto.rep.admin.user.RepApprenticesRecordDTO;
import com.zzy.brd.dto.req.admin.employee.ReqEmployeeDTO;
import com.zzy.brd.entity.Activity;
import com.zzy.brd.entity.Department;
import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.Loginlog;
import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.entity.Role;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.Activity.ActivitySet;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.UserInfoEmployee;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.ActivityService;
import com.zzy.brd.service.DepartmentService;
import com.zzy.brd.service.EmployeeService;
import com.zzy.brd.service.FlowWithdrawService;
import com.zzy.brd.service.LoginlogService;
import com.zzy.brd.service.RecordBrokerageService;
import com.zzy.brd.service.RoleService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.excel.ExcelUtil;
import com.zzy.brd.util.excel.ExcelUtil.ExcelBean;
import com.zzy.brd.util.phone.PhoneUtils;
import com.zzy.brd.util.random.InvitationCodeUtil;
import com.zzy.brd.util.string.StringUtil;
import com.zzy.brd.util.tld.HtmlEscapse;
import com.zzy.util.MD5;

/**
 * @author:xpk 2016年10月11日-下午7:09:36
 **/
@Controller
@RequestMapping(value = "admin/employee")
public class AdminEmployeeConrtoller {

	private @Autowired UserService userService;
	private @Autowired DepartmentService departmentService;
	private @Autowired RoleService roleService;
	private @Autowired RoleDao roleDao;
	private @Autowired EmployeeService employeeService;
	private @Autowired UserInfoEmployeeDao userInfoEmployeeDao;
	private @Autowired UserInfoBothDao userInfoBothDao;
	private @Autowired EmployeeDao employeeDao;
	private @Autowired LoginlogService loginlogService;
	private @Autowired FlowWithdrawService withdrawService;
	private @Autowired RecordBrokerageService recordService;
	private @Autowired UserOperLogService userOperlogService;
	private @Autowired ActivityService activityService;
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			@RequestParam(value = "firstDepartmentId", required = false, defaultValue = "-1") long firstDepartmentId,
			@RequestParam(value = "secondDepartmentId", required = false, defaultValue = "-1") long secondDepartmentId,
			@RequestParam(value = "thirdDepartmentId", required = false, defaultValue = "-1") long thirdDepartmentId,
			@RequestParam(value = "sortName", required = false) String sortName,
			@RequestParam(value = "sortType", required = false) String sortType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "roleId", required = false, defaultValue = "-1") long roleId,
			@RequestParam(value = "state", required = false, defaultValue = "-1") int state,
			Model model) {		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<UserType> userTypelist = new ArrayList<UserType>();
		userTypelist.add(User.UserType.ADMIN);
		userTypelist.add(User.UserType.SELLER);
		userTypelist.add(User.UserType.USER);
		userTypelist.add(User.UserType.MANAGER);
		searchParams.put("NOTIN_userType", userTypelist);
		if (state == 1) {
			searchParams.put("EQ_state", User.State.OFF);
		} else if (state == 0) {
			searchParams.put("EQ_state", User.State.ON);
		} else {
			searchParams.put("NE_state", User.State.DEL);
		}
		if (roleId > -1) {
			searchParams.put("EQ_role.id", roleId);
		}

		String findSortName = "id";
		if (sortName != null && sortType != null) {
			if (sortName.equals("orderNum")) {
				findSortName = "userInfoBoth.orderSum:" + sortType;
			}
			if (sortName.equals("sonNum")) {
				findSortName = "userInfoBoth.sonSum:" + sortType;
			}
			if (sortName.equals("grandSonNum")) {
				findSortName = "userInfoBoth.grandsonSum:" + sortType;
			}
		}
		long departmentid =-1;
		if (firstDepartmentId > -1) {
			departmentid = firstDepartmentId;
			if (secondDepartmentId > -1) {
				departmentid = secondDepartmentId;
			}
			if (thirdDepartmentId > -1) {
				departmentid = thirdDepartmentId;
			}
		}
		if(departmentid>-1){
			Department department = departmentService.getDepartmentById(departmentid);
			List<Department> departList = new ArrayList<Department>();
			departList.add(department);
			if(department.getLevel()==1){
				if(department.getSons().size()==0){
					departList.add(department);
				}else{
					for(Department depart: department.getSons()){
						if(depart.getSons().size()==0){
							departList.add(depart);
						} else{
							for(Department d:depart.getSons()){
								departList.add(d);
							}							
						}						
					}		
				}
			} else if(department.getLevel()==2){
				if(department.getSons().size()==0){
					departList.add(department);
				} else{
					departList.addAll(department.getSons());
				}
			} else if(department.getLevel()==3){
					departList.add(department);
			}
			searchParams.put("IN_userInfoEmployee.department", departList);
		}
		Page<User> users = employeeService.listEmployee(searchParams,
				pageNumber, findSortName, false,keyword);
		List<Role> roles = roleDao.findAllNoAdmin();
		model.addAttribute("firstDepartmentId", firstDepartmentId);
		model.addAttribute("secondDepartmentId", secondDepartmentId);
		model.addAttribute("thirdDepartmentId", thirdDepartmentId);
		model.addAttribute("employees", users);
		model.addAttribute("roles", roles);
		model.addAttribute("roleId", roleId);
		model.addAttribute("state", state);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortName", sortName);
		return "admin/employee/list";

	}

	@RequestMapping(value = "toAddEmployee")
	public String toAddEmployee(
			@RequestParam(value = "firstDepartmentId", required = false, defaultValue = "-1") long firstDepartmentId,
			@RequestParam(value = "secondDepartmentId", required = false, defaultValue = "-1") long secondDepartmentId,
			@RequestParam(value = "thirdDepartmentId", required = false, defaultValue = "-1") long thirdDepartmentId,
			@RequestParam(value = "roleId", required = false, defaultValue = "-1") long roleId,
			Model model) {
		List<Department> firstDepartmens = departmentService
				.findDepartmentByLevel((int) 1);
		if (firstDepartmentId == -1) {
			if (firstDepartmens.size() > 0) {
				if (firstDepartmens.get(0).getSons().size() > 0) {
					model.addAttribute("secondDepartments", firstDepartmens.get(0).getSons());
					if (firstDepartmens.get(0).getSons().get(0).getSons().size() > 0) {
						model.addAttribute("thirdDepartments", firstDepartmens.get(0).getSons().get(0).getSons());
					}
				}
			}
		} else {
			List<Department> secondDepartments = departmentService.getDepartmentById(firstDepartmentId).getSons();
			model.addAttribute("secondDepartments", secondDepartments);
			boolean isIn = true;
			for (Department sd : secondDepartments) {
				if (sd.getId() == secondDepartmentId) {
					isIn = true;
					continue;
				} else {
					isIn = false;
				}
			}
			if (isIn) {
				if(secondDepartmentId>-1){
					model.addAttribute("thirdDepartments", departmentService.getDepartmentById(secondDepartmentId).getSons());
				}
			} else {
				model.addAttribute("thirdDepartments", secondDepartments.get(0).getSons());
			}
		}

		model.addAttribute("firstDepartmentId", firstDepartmentId);
		model.addAttribute("secondDepartmentId", secondDepartmentId);
		model.addAttribute("thirdDepartmentId", thirdDepartmentId);
		List<Role> roles = roleDao.findAllNoAdmin();

		// 查询员工数量
		List<User> employees = employeeService.findAllEmployee();
		int employeeNumber = employees.size();
		
		if (employeeNumber == 0) {
			employeeNumber = 1;
		} else {
			employeeNumber = employeeNumber + 1;
		}
		String userno = ""
				+ StringUtil.toStringZeroByInteger(employeeNumber, 5);

		model.addAttribute("userno", userno);
		model.addAttribute("firstDepartmens", firstDepartmens);
		model.addAttribute("roleId", roleId);
		model.addAttribute("roles", roles);
		return "admin/employee/addEmployee";
	}

	/**
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addEmployee", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO addEmployee(@Valid ReqEmployeeDTO dto,
			HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		if(dto.getName().trim().equalsIgnoreCase("admin")){
			res.setCode(1);
			res.setMes("不能添加姓名类似admin的员工");
			return res;
		}
		//检查姓名是否存在
		User exitName = userService.findByUserNameAndUserType(dto.getName().trim());
		if(exitName!=null){
			res.setCode(1);
			res.setMes("该员工姓名已经存在");
			return res;
		}		
		Role role = roleService.findRoleById(dto.getRoleId());
		List<UserType> typeList = new ArrayList<UserType>(); 
		if(role.getRolename().equals("业务员")){
			if(dto.getPhone()==null|| dto.getPhone().length()==0){
				res.setCode(1);
				res.setMes("请输入手机号码");
				return res;
			}
			typeList.add(User.UserType.USER);
			typeList.add(User.UserType.MANAGER);
			typeList.add(User.UserType.SALESMAN);
			typeList.add(User.UserType.SELLER);
		}else {
			if(StringUtils.isNotBlank(dto.getPhone())){
				if(dto.getPhone().length()!=11){
					res.setCode(1);
					res.setMes("请输入11位手机号码");
					return res;
				}
				/*String place = PhoneUtils.getPhoneProv(dto.getPhone().trim());
				if(place.equals("未知")|| place.length()==0){
					res.setCode(1);
					res.setMes("请输入正确手机号码");
					return res;
				}
				if(!place.contains("福建")){
					res.setCode(1);
					res.setMes("手机号必须为福建省号码");
					return res;
				}*/
			}
			typeList.add(User.UserType.ADMIN);
			typeList.add(User.UserType.EMPLOYEE);
			typeList.add(User.UserType.CONTROLMANAGER);
			typeList.add(User.UserType.SALESMAN);
		}
		if(StringUtils.isNotBlank(dto.getPhone())){
			User exitUser = userService.findByMobileAndUserType(dto.getPhone(), typeList);
			if(exitUser!=null){
				res.setCode(1);
				res.setMes("该号码已经注册");
				return res;
			}
		}
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(dto.getPassword()
				.toLowerCase());
		User employee = new User();
		employee.setUsername(dto.getName().trim());
		employee.setRealname(dto.getName().trim());
		employee.setPassword(pwdInfo.getPassword());
		employee.setSalt(pwdInfo.getSalt());
		employee.setMobileno(dto.getPhone().trim());
		employee.setState(User.State.ON);
		employee.setCreatedate(new Date());
		employee.setRole(role);
		role.setNumber(role.getNumber() + 1);
		roleService.editRole(role);
		if (role.getRolename().equals("业务员")) {
			employee.setUserType(UserType.SALESMAN);
		} else {
			if (role.getRolename().equals("风控经理")) {
				employee.setUserType(UserType.CONTROLMANAGER);
			}  else {
				employee.setUserType(UserType.EMPLOYEE);

			}
		}
		UserInfoEmployee info = new UserInfoEmployee();
		Department department = null;
		if (dto.getFirstDepartmentId() > 0) {
			if (dto.getSecondDepartmentId() > 0) {
				if (dto.getThirdDepartmentId() > 0) {
					department = departmentService.getDepartmentById(dto
							.getThirdDepartmentId());
					info.setDepartment(department);
				} else {
					department = departmentService.getDepartmentById(dto
							.getSecondDepartmentId());
					info.setDepartment(department);
				}
			} else {
				department = departmentService.getDepartmentById(dto
						.getFirstDepartmentId());
				info.setDepartment(department);
			}
		}
		info.setDepartment(department);
		department.setDepartmentNum(department.getDepartmentNum()+1);
		info.setUserno(dto.getUserno());
		if(department.getParent()!=null){
			//增加父部门
			Department parent = department.getParent();
			parent.setDepartmentNum(parent.getDepartmentNum()+1);
			departmentService.editDepartment(parent);
			//1级部门
			if(department.getParent().getParent()!=null){
				Department grandParent = department.getParent().getParent();
				grandParent.setDepartmentNum(grandParent.getDepartmentNum()+1);
				departmentService.editDepartment(grandParent);
			}
		}
		if(role.getRolename().equals("业务员")){
			UserInfoBoth both = new UserInfoBoth();
			both.prepareForInsert();
			both.setActivityBrokerage(BigDecimal.ZERO);
			both.setBrokerageCanWithdraw(BigDecimal.ZERO);
			both.setRecommendCode(dto.getPhone().trim());
			employee.setUserInfoBoth(both);
			userInfoBothDao.save(both);
		}
		userService.editUser(employee);
		employee.setUserInfoEmployee(info);
		userInfoEmployeeDao.save(info);
		res.setCode(0);
		res.setMes("添加成功");
		long userId = ShiroUtil.getUserId(request);
		User opertor = userService.findById(userId);
		String content = "添加员工:" + employee.getRealname();
		userOperlogService.addOperlog(opertor, content);
		return res;
	}
	
	

	/*** 根据传递的部门id获取部门列表 */
	@RequestMapping(value = "getDepartmentList", method = RequestMethod.POST)
	@ResponseBody
	public RepDepartmentListDTO getDepartmentList(
			@RequestParam(value = "firstDepartmentId", required = false, defaultValue = "-1") long firstDepartmentId,
			@RequestParam(value = "secondDepartmentId", required = false, defaultValue = "-1") long secondDepartmentId,
			@RequestParam(value = "thirdDepartmentId", required = false, defaultValue = "-1") long thirdDepartmentId) {	
		RepDepartmentListDTO dto = new RepDepartmentListDTO();
		List<Department> firstDepartmens = departmentService.findDepartmentByLevel((int) 1);
		if (firstDepartmens.size() > 0) {
			List<RepDepartmentDetailDTO> first = Lists.transform(firstDepartmens,new Function<Department, RepDepartmentDetailDTO>() {
						@Override
						public RepDepartmentDetailDTO apply(
								Department department) {
							return new RepDepartmentDetailDTO(department);
						}
					});
			dto.setFirstDepartments(first);
			// 判断二级部门
			if (firstDepartmentId == -1) {
				if (firstDepartmens.get(0).getSons().size() > 0) {
					List<RepDepartmentDetailDTO> second = Lists.transform(firstDepartmens.get(0).getSons(),new Function<Department, RepDepartmentDetailDTO>() {
								@Override
								public RepDepartmentDetailDTO apply(
										Department department) {
									// TODO Auto-generated method stub
									return new RepDepartmentDetailDTO(
											department);
								}
							});
					dto.setSecondDepartments(second);
					if (firstDepartmens.get(0).getSons().get(0).getSons()
							.size() > 0) {
						List<RepDepartmentDetailDTO> third = Lists.transform(firstDepartmens.get(0).getSons().get(0).getSons(),new Function<Department, RepDepartmentDetailDTO>() {
											@Override
											public RepDepartmentDetailDTO apply(
													Department department) {
												// TODO Auto-generated method
												// stub
												return new RepDepartmentDetailDTO(
														department);
											}
										});
						dto.setThirdDepartment(third);
					}
				}
			} else {
				List<Department> secondDepartments = departmentService.getDepartmentById(firstDepartmentId).getSons();
				if (secondDepartments.size() > 0) {
					List<RepDepartmentDetailDTO> second = Lists.transform(secondDepartments,new Function<Department, RepDepartmentDetailDTO>() {
								@Override
								public RepDepartmentDetailDTO apply(
										Department department) {
									// TODO Auto-generated method stub
									return new RepDepartmentDetailDTO(
											department);
								}
							});
					dto.setSecondDepartments(second);
					// 三级部门
					boolean isIn = true;
					for (Department sd : secondDepartments) {
						if (sd.getId() == secondDepartmentId) {
							isIn = true;
							continue;
						} else {
							isIn = false;
						}
					}
					Department middle = departmentService
							.getDepartmentById(secondDepartmentId);
					if (middle == null) {
						secondDepartmentId = secondDepartments.get(0).getId();
						middle = departmentService
								.getDepartmentById(secondDepartmentId);
						List<RepDepartmentDetailDTO> third = Lists.transform(middle.getSons(),new Function<Department, RepDepartmentDetailDTO>() {
											@Override
											public RepDepartmentDetailDTO apply(
													Department department) {
												// TODO Auto-generated method
												// stub
												return new RepDepartmentDetailDTO(
														department);
											}
										});
						dto.setThirdDepartment(third);
					} else {
						if (isIn) {
							List<RepDepartmentDetailDTO> third = Lists.transform(departmentService.getDepartmentById(secondDepartmentId).getSons(),
											new Function<Department, RepDepartmentDetailDTO>() {
												@Override
												public RepDepartmentDetailDTO apply(
														Department department) {
													// TODO Auto-generated
													// method stub
													return new RepDepartmentDetailDTO(
															department);
												}
											});
							dto.setThirdDepartment(third);
						}
						if (!isIn) {
							List<RepDepartmentDetailDTO> third = Lists.transform(secondDepartments.get(0).getSons(),new Function<Department, RepDepartmentDetailDTO>() {
												@Override
												public RepDepartmentDetailDTO apply(
														Department department) {
													// TODO Auto-generated
													// method stub
													return new RepDepartmentDetailDTO(
															department);
												}
											});
							dto.setThirdDepartment(third);
						}
					}
				}
			}
		}
		return dto;
	}

	@RequestMapping("changeState")
	@ResponseBody
	public String changeState(long id, User.State state,HttpServletRequest request) {
		if (userService.updateWithState(id, state)) {
			User employee = userService.findById(id);
			long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = state.getStr()+"员工:"+employee.getRealname();
			userOperlogService.addOperlog(opertor, content);
			return "0";
		}
		return "-1";
	}

	@RequestMapping(value = "toEditEmployee/{employeeId}")
	public String toEditEmployee(
			@PathVariable long employeeId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "firstDepartmentId", required = false, defaultValue = "-1") long firstDepartmentId,
			@RequestParam(value = "secondDepartmentId", required = false, defaultValue = "-1") long secondDepartmentId,
			@RequestParam(value = "thirdDepartmentId", required = false, defaultValue = "-1") long thirdDepartmentId,
			@RequestParam(value = "type", required = false ) String type,
			@RequestParam(value="lastPage",required=false,defaultValue="1")int lastPage,
			Model model) {
		List<Department> firstDepartmens = departmentService.findDepartmentByLevel((int) 1);
		User employee = userService.findById(employeeId);
		if(type==null && !employee.getUserType().equals(User.UserType.SALESMAN) ){
			type = "loginlog";			
		} else if(type==null && employee.getUserType().equals(User.UserType.SALESMAN) ){
			type ="brokerage";
		}
		if (firstDepartmentId == -1) {
			int level = employee.getUserInfoEmployee().getDepartment().getLevel();
			if(level==2){
				model.addAttribute("secondDepartments", employee.getUserInfoEmployee().getDepartment().getParent().getSons());
			}else if(level==3){
				model.addAttribute("secondDepartments", employee.getUserInfoEmployee().getDepartment().getParent().getParent().getSons());
				model.addAttribute("thirdDepartments", employee.getUserInfoEmployee().getDepartment().getParent().getSons());
			}			
		} else {
			List<Department> secondDepartments = departmentService.getDepartmentById(firstDepartmentId).getSons();
			model.addAttribute("secondDepartments", secondDepartments);
			boolean isIn = false;
			for (Department sd : secondDepartments) {
				if (sd.getId() == secondDepartmentId) {
					isIn = true;
					continue;
				} else {
					isIn = false;
				}
			}
			if (isIn) {
				model.addAttribute("thirdDepartments", departmentService.getDepartmentById(secondDepartmentId).getSons());
			} else {
				if(secondDepartments.size()>0){
					model.addAttribute("thirdDepartments", secondDepartments.get(0).getSons());
				}
			}
		}

		model.addAttribute("firstDepartmentId", firstDepartmentId);
		model.addAttribute("secondDepartmentId", secondDepartmentId);
		model.addAttribute("thirdDepartmentId", thirdDepartmentId);
		model.addAttribute("employeeId", employeeId);

		// 角色列表
		List<Role> roles = roleDao.findAllNoAdminAndSales();
		model.addAttribute("roles", roles);
		model.addAttribute("employee", employee);

		model.addAttribute("firstDepartments", firstDepartmens);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		// /下方的列表日志
		if (type.equals("brokerage")) {
			// 获取佣金记录
			searchParams.put("EQ_user", employee);
			Page<RecordBrokerage> page = recordService.listRecordBrokerage(
					searchParams, pageNumber);
			model.addAttribute("page", page);
		} else if (type.equals("withdraw")) {
			// 获取提现记录
			searchParams.put("EQ_user", employee);
			///String state= "";
			Page<FlowWithdraw> page = withdrawService.getFlowWithdraw(
					searchParams, false, pageNumber, Constant.PAGE_SIZE,"");
			model.addAttribute("page", page);
		} else if (type.equals("apprentice")) {
			// 获取收徒记录
			Page<RepApprenticesRecordDTO> page = userService
					.listApprenticesRecord(employee, pageNumber);
			model.addAttribute("page", page);
		} else if (type.equals("loginlog")) {
			// 获取登录日志
			searchParams.put("EQ_user", employee);
			Page<Loginlog> page = loginlogService.listLoginlog(searchParams,
					pageNumber);
			model.addAttribute("page", page);

		}
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("type", type);
		return "admin/employee/editEmployee";
	}

	@RequestMapping(value = "editEmployee", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO editEmployee(
			long id,String mobileno,long departmentId,long roleId,int state,int resetPassword,
			@RequestParam(value = "password", required = false) String password,
			HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		User employee = userService.findById(id);
		Role oldRole = employee.getRole();
		Role newRole = roleService.findRoleById(roleId);
		//不能将其他身份修改为业务员
		if(!oldRole.getRolename().equals("业务员")&& newRole.getRolename().equals("业务员")){
			res.setCode(1);
			res.setMes("不能将员工改为业务员");
			return res;
		}
		//号码有改变则进行号码验证
		if(!employee.getMobileno().equals(mobileno)){
			List<UserType> typeList = new ArrayList<UserType>();
			if(newRole.getRolename().equals("业务员")){
				if(!StringUtils.isNotBlank(mobileno)){
					res.setCode(1);
					res.setMes("请输入号码");
					return res;
				}
				typeList.add(User.UserType.USER);
				typeList.add(User.UserType.MANAGER);
				typeList.add(User.UserType.SALESMAN);
				typeList.add(User.UserType.SELLER);
			} else{
				if(StringUtils.isNotBlank(mobileno)){
					if(mobileno.length()!=11){
						res.setCode(1);
						res.setMes("请输入11位手机号码");
						return res;
					}
					//String place =PhoneUtils.getPhoneProv(mobileno);
					/*if(place.equals("未知")||place.length()==0){
						res.setCode(1);
						res.setMes("请输入正确手机号码");
						return res;						
					}
					if(!place.contains("福建")){
						res.setCode(1);
						res.setMes("手机号必须为福建省号码");
						return res;
					}*/
					typeList.add(User.UserType.ADMIN);
					typeList.add(User.UserType.EMPLOYEE);
					typeList.add(User.UserType.CONTROLMANAGER);
					typeList.add(User.UserType.SALESMAN);
				}			
			}		
			if(StringUtils.isNotBlank(mobileno)){
				User exitUser = userService.findByMobileAndUserType(mobileno, typeList);
				if(exitUser!=null){
					res.setCode(1);
					res.setMes("该号码已经注册");
					return res;
				}
			}	
		}
		//角色变更
		if(!oldRole.getId().equals(newRole.getId())){
			employee.setRole(newRole);
			oldRole.setNumber(oldRole.getNumber()-1);
			newRole.setNumber(newRole.getNumber()+1);
			roleService.editRole(oldRole);
			roleService.editRole(newRole);
		}
		
		//部门人数
		if(departmentId != employee.getUserInfoEmployee().getDepartment().getId()){
			//新部门
			Department department = departmentService.getDepartmentById(departmentId);
			//旧部门人数减少
			Department oldDepartment = employee.getUserInfoEmployee().getDepartment();
			if(oldDepartment.getParent()!=null){
				Department oldParent = oldDepartment.getParent();
				oldParent.setDepartmentNum(oldParent.getDepartmentNum()-1);
				if(oldDepartment.getParent().getParent()!=null){
					Department grandParent =oldDepartment.getParent().getParent();
					grandParent.setDepartmentNum(grandParent.getDepartmentNum()-1);
					departmentService.editDepartment(grandParent);
				}
				departmentService.editDepartment(oldParent);
			}
			oldDepartment.setDepartmentNum(oldDepartment.getDepartmentNum() - 1);
			departmentService.editDepartment(oldDepartment);
			//新部门人数增加
			if (department.getParent() != null) {
				Department newParent = department.getParent();
				newParent.setDepartmentNum(newParent.getDepartmentNum() + 1);
				departmentService.editDepartment(newParent);
				if (department.getParent().getParent() != null) {
					Department grandDepartment = department.getParent().getParent();
					grandDepartment.setDepartmentNum(grandDepartment.getDepartmentNum()+1);
					departmentService.editDepartment(grandDepartment);
				}
			}
			department.setDepartmentNum(department.getDepartmentNum() + 1);
			departmentService.editDepartment(department);
			//保存新部门
			employee.getUserInfoEmployee().setDepartment(department);
		}
		employee.setMobileno(mobileno);
		if(employee.getUserInfoBoth()!=null){
			employee.getUserInfoBoth().setRecommendCode(mobileno);
		}
		//如果由业务员改成 风控经理 发展的会员变为空
		if(!oldRole.getRolename().equals("业务员") && newRole.getRolename().equals("风控经理")){
				employee.setUserType(User.UserType.CONTROLMANAGER);
				List<User> sellerList = employeeService.findSalesman(employee);
				for (User u : sellerList) {
						u.getUserInfoBoth().setPastSalesman(employee);
						u.getUserInfoBoth().setSalesman(null);
				if(u.getUserType().equals(User.UserType.SELLER)){
						//如果是商家则将师父设置为空
						u.getUserInfoBoth().setParent(null);
						}	
					}
			}
		if(!newRole.getRolename().equals("风控经理")&& !newRole.getRolename().equals("业务员") ){
			employee.setUserType(User.UserType.EMPLOYEE);
		}
		if(!employee.getUserType().equals(User.UserType.SALESMAN)){
			if (state == 0) {
				employee.setState(User.State.ON);
			} else {
				employee.setState(User.State.OFF);
			}
		}
		if (resetPassword == 0) {
			if (password.trim().length() > 0) {
				SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
				PasswordInfo pwdInfo = encrypt.encryptPassword(password);
				employee.setPassword(pwdInfo.getPassword());
				employee.setSalt(pwdInfo.getSalt());
			}
		}
		if (userService.editUser(employee)) {
			res.setCode(0);
			res.setMes("修改成功");
			long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = "修改员工[" + employee.getRealname() + "]资料";
			userOperlogService.addOperlog(opertor, content);
			return res;
		} else {
			res.setCode(1);
			res.setMes("修改失败");
			return res;
		}
	}

	/** 降级 */
	@RequestMapping(value = "drowngrade")
	@ResponseBody
	public RepSimpleMessageDTO drowngrade(long id, HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		User employee = userService.findById(id);
		if (employee == null) {
			res.setCode(1);
			res.setMes("该用户不存在");
			return res;
		}
		employee.setUserType(User.UserType.MANAGER);
		long opertorid = ShiroUtil.getUserId(request);
		User opertor = userService.findById(opertorid);
		String content = "将员工:" + employee.getRealname() + "降为融资经理";
		userOperlogService.addOperlog(opertor, content);
		List<User> sonsList = userService.findAllGGrandSon(Lists.newArrayList(employee.getId()));
		if(sonsList.size()==0){
			res.setCode(0);
			res.setMes("修改成功");
			userService.editUser(employee);
		}else{
			//所属业务员是此人的
			List<User> salesList = employeeService.findSalesman(employee);
			for(User sales :salesList){
				sales.getUserInfoBoth().setSalesman(null);
				userInfoBothDao.save(sales.getUserInfoBoth());
			}
			List<User> grandSonList = userService.findAllGrandSon(employee);
			while(grandSonList.size()>0){
				for(User u :grandSonList){
					u.getUserInfoBoth().setSalesman(null);
					u.getUserInfoBoth().setPastSalesman(employee);
					userInfoBothDao.save(u.getUserInfoBoth());
				}
				List<Long> ids = Lists.transform(grandSonList,new Function<User,Long>(){
					@Override
					public Long apply(User user) {
						// TODO Auto-generated method stub
						return user.getId();
					}
				});
				grandSonList = userService.findAllGGrandSon(ids);				
			}			
			for(User son:sonsList) {
				son.getUserInfoBoth().setSalesman(null);
				son.getUserInfoBoth().setPastSalesman(employee);
				if(son.getUserType().equals(User.UserType.SELLER)){
					son.getUserInfoBoth().setParent(null);
					for(User sonson:userService.findAllSons(son)){
						sonson.getUserInfoBoth().setGrandParent(null);
						userInfoBothDao.save(sonson.getUserInfoBoth());
					}					
				}
				userInfoBothDao.save(son.getUserInfoBoth());
			}
			Department department = employee.getUserInfoEmployee().getDepartment();
			if(department!=null){
				employee.getUserInfoEmployee().setDepartment(null);
				department.setDepartmentNum(department.getDepartmentNum()-1);
				userInfoEmployeeDao.save(employee.getUserInfoEmployee());
				departmentService.editDepartment(department);
				if(department.getParent()!=null){
					department.getParent().setDepartmentNum(department.getParent().getDepartmentNum()-1);
					departmentService.editDepartment(department.getParent());
					if(department.getParent().getParent()!=null){
						department.getParent().getParent().setDepartmentNum(department.getParent().getParent().getDepartmentNum()-1);
						departmentService.editDepartment(department.getParent().getParent());
					}
				}
			}
			Role role = employee.getRole();
			role.setNumber(role.getNumber()-1);
			roleService.editRole(role);
			employee.setRole(null);
			userService.editUser(employee);
			userService.staticsUserSonsAndGrandSons(employee);
			// 收徒活动数量
			Activity activity = activityService.getRecommendRegisterByActivityType();
			String activityObject = null;
			ActivitySet activitySet =null;
			boolean is1 =false;
			if(activity!=null){
				 activitySet = activity.getActivitySet();
				 activityObject = activity.getActivityObject();
			}
			if(activityObject !=null){
				is1=userService.isExit(activityObject, "1");
			}
			if(!is1 && activitySet!=null){
				if(activitySet==ActivitySet.REGISTER){
					employee.getUserInfoBoth().setAcitivitySonSum(0);
					userInfoBothDao.save(employee.getUserInfoBoth());
				}
			}			
			res.setCode(0);
			res.setMes("修改成功");
		}
		return res;
	}

	@RequestMapping(value = "delEmployee")
	@ResponseBody
	public RepSimpleMessageDTO delEmployee(long[] ids,HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		List<String> nameList = new ArrayList<String>();
		for (long id : ids) {
			User employee = userService.findById(id);
			if(!employee.getUserType().equals(User.UserType.SALESMAN)){
				employeeService.changeState(employee, User.State.OFF);
				nameList.add(employee.getRealname());
			}
		}
		long opertorid = ShiroUtil.getUserId(request);
		User opertor = userService.findById(opertorid);
		String content = "禁用员工:"+nameList;
		userOperlogService.addOperlog(opertor, content);
		res.setCode(0);
		res.setMes("删除成功");
		return res;
	}

	/** 导出表格 */
	@RequestMapping("export")
	public void employeeExport(
			@RequestParam(value = "firstDepartmentId", required = false, defaultValue = "-1") long firstDepartmentId,
			@RequestParam(value = "secondDepartmentId", required = false, defaultValue = "-1") long secondDepartmentId,
			@RequestParam(value = "thirdDepartmentId", required = false, defaultValue = "-1") long thirdDepartmentId,
			@RequestParam(value = "sortName", required = false) String sortName,
			@RequestParam(value = "sortType", required = false) String sortType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "roleId", required = false, defaultValue = "-1") long roleId,
			@RequestParam(value = "state", required = false, defaultValue = "-1") int state,
			HttpServletResponse response) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<UserType> userTypelist = new ArrayList<UserType>();
		userTypelist.add(User.UserType.ADMIN);
		userTypelist.add(User.UserType.SELLER);
		userTypelist.add(User.UserType.USER);
		userTypelist.add(User.UserType.MANAGER);
		searchParams.put("NOTIN_userType", userTypelist);
		if (state == 1) {
			searchParams.put("EQ_state", User.State.OFF);
		} else if (state == 0) {
			searchParams.put("EQ_state", User.State.ON);
		} else {
			searchParams.put("NE_state", User.State.DEL);
		}
		if (roleId > -1) {
			searchParams.put("EQ_role.id", roleId);
		}

		String findSortName = "id";
		if (sortName != null && sortType != null) {
			if (sortName.equals("orderNum")) {
				findSortName = "userInfoBoth.orderSum:" + sortType;
			}
			if (sortName.equals("sonNum")) {
				findSortName = "userInfoBoth.sonSum:" + sortType;
			}
			if (sortName.equals("grandSonNum")) {
				findSortName = "userInfoBoth.grandsonSum:" + sortType;
			}
		}
		long departmentid =-1;
		if (firstDepartmentId > -1) {
			departmentid = firstDepartmentId;
			if (secondDepartmentId > -1) {
				departmentid = secondDepartmentId;
			}
			if (thirdDepartmentId > -1) {
				departmentid = thirdDepartmentId;
			}
		}
		if(departmentid>-1){
			Department department = departmentService.getDepartmentById(departmentid);
			List<Department> departList = new ArrayList<Department>();
			if(department.getLevel()==1){
				if(department.getSons().size()==0){
					departList.add(department);
				}else{
					for(Department depart: department.getSons()){
						if(depart.getSons().size()==0){
							departList.add(depart);
						} else{
							for(Department d:depart.getSons()){
								departList.add(d);
							}							
						}						
					}		
				}
			} else if(department.getLevel()==2){
				if(department.getSons().size()==0){
					departList.add(department);
				} else{
					departList.addAll(department.getSons());
				}
			} else if(department.getLevel()==3){
					departList.add(department);
			}
			searchParams.put("IN_userInfoEmployee.department", departList);
		}
		int pageNumber = 1;
		List<User> list = new ArrayList<User>();
		Page<User> users = employeeService.listEmployee(searchParams,
				pageNumber, findSortName, false,keyword);
		while (users != null && users.getNumberOfElements() > 0) {
			list.addAll(users.getContent());
			if (!users.hasNext()) {
				break;
			} else {
				pageNumber++;
			}
			users = employeeService.listEmployee(searchParams, pageNumber,
					findSortName, false,keyword);
		}
		
		String[] titles = { "员工姓名", "所属部门", "角色", "状态" };
		ExcelBean excelBean = new ExcelBean("员工.xls", "员工", titles);
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
		String name = user.getRealname().trim();
		String departmentname = HtmlEscapse.htmlUnescape(user.getUserInfoEmployee().getDepartment().getName()).trim();
		String rolename = user.getRole().getRolename().trim();
		String status ="";
		if(!user.getUserType().equals(User.UserType.SALESMAN)){
			status =user.getState().getStr().trim();
		}
		String data[] = { name, departmentname, rolename, status };
		return data;
	}
	
	@RequestMapping(value="isRightPhone")
	@ResponseBody
	public RepSimpleMessageDTO isRightPhone(String phone){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		if(phone.length()!=11){
			rep.setCode(1);
			rep.setMes("该业务员不是正确手机号码请去修改");
			return rep;
		}
		String place = PhoneUtils.getPhoneProv(phone.trim());
		if(place.contains("未知")|| place.length()==0){
			rep.setCode(1);
			rep.setMes("该业务员不是正确手机号码请去修改");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("正确的手机号码");
		return rep;
	}
	
	
	
}

/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.controller.admin.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import org.springframework.web.util.HtmlUtils;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.req.admin.role.ReqRoleDTO;
import com.zzy.brd.dto.req.admin.role.ReqRoleEditDTO;
import com.zzy.brd.entity.Role;
import com.zzy.brd.entity.User;
//import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.RoleService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.session.SessionService;

/**
 * 角色管理
 * 
 * @author huangjinbing 2015年10月30日
 */
@Controller
@RequestMapping("/admin/role")
public class AdminRoleController {
	private final static Logger logger = LoggerFactory
			.getLogger(AdminRoleController.class);

	@Autowired
	private SessionService sessionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
//	private @Autowired UserOperLogService userOperlogService;
	
	/**
	 * 管理账户列表
	 * 
	 * @param pageNumber
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	@RequiresPermissions("ORGANIZATION_MANAGER_ROLE")
	public String listAdmin(
			@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			Model model) {
		  Map<String, Object> searchParams = new HashMap<String, Object>(); 
		
		searchParams.put("EQ_state", Role.State.ON);
		
		Page<Role> roles = roleService.listPageRoles(searchParams, pageNumber,
				Constant.PAGE_SIZE);
		model.addAttribute("roles", roles);
		return "admin/role/roleListPage";
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delRoles", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO delRoles(long[] ids,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
//		long opertorid = ShiroUtil.getUserId(request);
//		User opertor = userService.findById(opertorid);
//		String content ="删除角色:";
		for(long id:ids){
			Role role = roleService.findRoleById(id);
			List<User> list = userService.findByRole(role);
			if(list.size()>0){
				res.setCode(0);
				res.setMes(role.getRolename()+"下存在员工,无法删除");
				return res;
			}else{
				role.setState(Role.State.OFF);
				if(!roleService.editRole(role)){
					res.setCode(0);
					res.setMes("删除失败");
					return res;
				}else{
//					content = content+role.getRolename()+";";
				}
			}
		}
		res.setCode(1);
		res.setMes("删除成功");
//		userOperlogService.addOperlog(opertor, content);
		return res;
	}
	

	/**
	 * 跳转到角色添加页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddRolePage", method = RequestMethod.GET)
	@RequiresPermissions("ORGANIZATION_MANAGER_ROLE")
	public String toAddRolePage(Model model) {
		return "admin/role/addRole";
	}

	/**
	 * 添加角色
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("ORGANIZATION_MANAGER_ROLE")
	public RepSimpleMessageDTO addRole(ReqRoleDTO dto,HttpServletRequest request) {
		RepSimpleMessageDTO res =new RepSimpleMessageDTO();
		Role role = new Role();
		dto.setRolename(dto.getRolename().trim());
		List<Role> roles=roleService.findRoleByRolename(dto.getRolename().trim());
		//判断角色名称是否重复 
		if(roles!=null&&roles.size()>0){
			res.setCode(1);
			res.setMes("已经存在该角色名");
			return res;
		}
		role.setPermissions(dto.getPermissions());
		role.setState(Role.State.ON);
		role.setRolename(dto.getRolename());
		if (roleService.addRole(role)) {
			res.setCode(0);
			res.setMes("添加成功");
//			long opertorid =ShiroUtil.getUserId(request);
//			User opertor = userService.findById(opertorid);
//			String content = "添加角色:"+role.getRolename();
//			userOperlogService.addOperlog(opertor, content);
			return res;
		} else {
			res.setCode(1);
			res.setMes("添加失败");
			return res;
		}
	}

	/**
	 * 修改角色
	 * 
	 * @param @param dto
	 * @param @param result
	 * @param @return
	 * @return RepSimpleMessageDTO
	 */
	
	@RequestMapping(value = "editRole", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("ORGANIZATION_MANAGER_ROLE")
	public RepSimpleMessageDTO modifyRole(@Valid ReqRoleEditDTO dto,BindingResult result,HttpServletRequest request) {
		dto.setRolename(HtmlUtils.htmlUnescape(dto.getRolename()));
		RepSimpleMessageDTO repdto=new RepSimpleMessageDTO();
		if(result.hasErrors()){
			repdto.setCode(1);
			repdto.setMes("错误："+result.getAllErrors().get(0).getDefaultMessage());
			return repdto;
		}
		Role role = roleService.findRoleById(dto.getId());
		
		if(role==null){
			repdto.setCode(1);
			repdto.setMes("错误："+"角色不存在");
			return repdto;
		}
		List<Role> roles=roleService.findRoleByRolename(dto.getRolename());
		//判断角色名称是否重复
		if(dto.getRolename().equals(role.getRolename())&&roles!=null&&roles.size()>1){
			repdto.setCode(1);
			repdto.setMes("错误："+"角色名重复");
			return repdto;
		}
		if(!dto.getRolename().equals(role.getRolename())&&roles!=null&&roles.size()>0){
			repdto.setCode(1);
			repdto.setMes("错误："+"角色名重复");
			return repdto;
		}
		String oldname = role.getRolename();
		String oldPer=role.getPermissions();
		role.setPermissions(dto.getPermissions());
		role.setRolename(dto.getRolename());	
		//修改角色
		if (roleService.editRole(role)) {
			repdto.setCode(0);
			repdto.setMes("成功："+"角色修改成功");
			/*long opertorid =ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);*/
			String content = "";
			String newPer =dto.getPermissions();
			if(newPer.equals(oldPer)){
				content = "修改角色名:"+oldname+"为:"+role.getRolename();
			}else{
				content = "修改角色"+oldname+"权限";				
			}			
//			userOperlogService.addOperlog(opertor, content);
			return repdto;
		} else {
			repdto.setCode(1);
			repdto.setMes("错误："+"角色修改错误");
			return repdto;
		}
	}
	
	/**
	 * 跳转到修改页面
	 * 
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modifyRole/{roleid}", method = RequestMethod.GET)
	@RequiresPermissions("ORGANIZATION_MANAGER_ROLE")
	public String adminModify(
			int page,
			@PathVariable long roleid, Model model) {
		Role role = roleService.findRoleById(roleid);
		model.addAttribute("page", page);
		model.addAttribute("role", role);
		return "admin/role/editRole";
	}
	

}

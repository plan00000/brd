package com.zzy.brd.controller.admin.department;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.DepartmentDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Department;
import com.zzy.brd.entity.User;
//import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.DepartmentService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.tld.HtmlEscapse;

/**
 * @author:xpk 2016年10月8日-下午6:25:47
 **/
@Controller
@RequestMapping(value = "admin/department")
public class AdminDepartmentController {

	private @Autowired DepartmentService departmentService;
	private @Autowired DepartmentDao departmentDao;
	private @Autowired UserService userService;
	private @Autowired UserOperLogService userOperlogService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			Model model) {
		List<Department> departmentList = new ArrayList<Department>();
		List<Department> firstdepartment = departmentService.findDepartmentByLevel((int) 1);
		List<Department> nextDepartment = new ArrayList<Department>();
		Page<Department> page = departmentService.listDepartments(pageNumber,
				Constant.PAGE_SIZE);
		int number = 0;
		long nextStartDepartmentId = 0;
		long nextFirstDepartmentId = 0;
		List<Long> firstExitsIds = new ArrayList<Long>();
		List<Long> useDepartIds =new ArrayList<Long>();
		for (int i = 0; i < pageNumber; i++) {
			if (nextStartDepartmentId > 0 && nextFirstDepartmentId > 0) {
				nextDepartment.clear();
				Department nextStartDepartment = departmentService.getDepartmentById(nextStartDepartmentId);
				if (nextStartDepartment.getLevel() > 1) {
					Department parent = nextStartDepartment.getParent();
					for (Department innerdeparment : parent.getSons()) {
						if(!useDepartIds.contains(innerdeparment.getId())){
							nextDepartment.add(innerdeparment);
						}
					}
					if(parent.getParent()!=null){
						for (Department innerdeparment : parent.getParent().getSons()) {
							if(!useDepartIds.contains(innerdeparment.getId())){
								nextDepartment.add(innerdeparment);
							}
						}
					}
				}
				for (Department first : departmentService.findDepartmentByLevel((int) 1)) {
					if (first.getId() >=nextFirstDepartmentId) {
						if(!firstExitsIds.contains(first.getId())){
							nextDepartment.add(first);
						}
					}
				}
			} else {
				nextDepartment = firstdepartment;
			}
			departmentList.clear();
			nextStartDepartmentId = 0;
			nextFirstDepartmentId = 0;
			number = 0;
			
			for (Department depart : nextDepartment) {
				if (number >= 10) {
					if (nextStartDepartmentId == 0) {
						nextStartDepartmentId = depart.getId();
					}
					nextFirstDepartmentId = depart.getId();
					break;
				} else {
					departmentList.add(depart);
					firstExitsIds.add(depart.getId());
					number = number + 1;
					if (depart.getSons().size() > 0) {
						for (Department dson : depart.getSons()) {
							if (number >= 10) {
								if (nextStartDepartmentId == 0) {
									nextStartDepartmentId = dson.getId();
								}
								nextFirstDepartmentId = depart.getId();
								break;
							} else {
								departmentList.add(dson);
								useDepartIds.add(dson.getId());
								number = number + 1;
								if (dson.getSons().size() > 0) {
									for (Department granddson : dson.getSons()) {
										if (number >= 10) {
											nextStartDepartmentId = granddson.getId();
											nextFirstDepartmentId = depart.getId();
											break;
										}
										departmentList.add(granddson);
										useDepartIds.add(granddson.getId());
										number = number + 1;
									}
								}
							}
						}
					}
				}
			}
		}
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("departments", page);
		return "admin/department/list";
	}

	@RequestMapping(value = "toAdddepartment")
	public String toAdd(
			@RequestParam(value = "departmentlevel", required = false, defaultValue = "0") int departmentlevel,
			@RequestParam(value = "firstDepartId", required = false, defaultValue = "-1") long firstDepartId,
			Model model) {
		List<Department> fitstDepartList = departmentService
				.findDepartmentByLevel((int) 1);
		Department firstDepartment = null;
		if (firstDepartId > -1) {
			firstDepartment = departmentService
					.getDepartmentById(firstDepartId);
			model.addAttribute("nextDepartment", firstDepartment.getSons());
		} else {
			if (fitstDepartList.size() > 0) {
				firstDepartment = fitstDepartList.get(0);
				model.addAttribute("nextDepartment", firstDepartment.getSons());
			}
		}
		model.addAttribute("firstDepartment", firstDepartment);
		model.addAttribute("firstDepartId", firstDepartId);
		model.addAttribute("fitstDepartList", fitstDepartList);
		model.addAttribute("departmentlevel", departmentlevel);
		return "admin/department/addDepartment";
	}

	@RequestMapping(value = "addDepartment", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO addDepartment(
			@RequestParam String departname,
			@RequestParam(value = "parentDeparmentId", required = false, defaultValue = "0") long parentDeparmentId,
			@RequestParam(value = "departmentlevel") int departmentlevel,
			HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		if (!departmentService.isDepartmentNameExist(departname)) {
			res.setCode(1);
			res.setMes("部门名字已经存在");
			return res;
		}
		if (parentDeparmentId == 0 && departmentlevel != 0) {
			res.setCode(1);
			res.setMes("请选择要所属部门");
			return res;
		}
		Department department = new Department();
		department.setState(Department.State.NOMAL);
		department.setName(departname);
		if (parentDeparmentId > 0) {
			Department parent = departmentService
					.getDepartmentById(parentDeparmentId);
			if (parent == null) {
				res.setCode(1);
				res.setMes("上级部门不存在");
				return res;
			} else {
				department.setParent(parent);
			}
		}
		department.setLevel(departmentlevel + 1);
		department.setDepartmentNum(0);
		department.setAddBrokerage(new BigDecimal("0"));
		if (departmentService.editDepartment(department)) {
			res.setCode(0);
			res.setMes("添加成功");
			// 添加日志
			/*long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = "添加" + department.getLevel() + "级部门:"
					+ department.getName();
			userOperlogService.addOperlog(opertor, content);*/
			return res;
		} else {
			res.setCode(1);
			res.setMes("添加失败");
			return res;
		}
	}

	/** 部门编辑 */
	@RequestMapping("toEditDepartment/{departmentId}")
	public String toEditDepartment(
			@PathVariable long departmentId,
			@RequestParam(value = "grandParentId", required = false, defaultValue = "-1") long grandParentId,
			@RequestParam(value = "level", required = false, defaultValue = "-1") int level,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model) {
		Department department = departmentService.getDepartmentById(departmentId);
		if (department == null) {
			return "error/400";
		}
		if (level == -1) {
			level = department.getLevel();
		}
		model.addAttribute("page", page);
		model.addAttribute("level", level);
		model.addAttribute("department", department);
		return "admin/department/edit";
	}

	/*** 修改部门 */
	@RequestMapping(value = "editDepartment", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO editDepartment(
			long departmentId,
			String departmentName, HttpServletRequest request) {
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Department department = departmentService.getDepartmentById(departmentId);
		Department exitDepartmentList = departmentService.findDepartmentByName(
				departmentName, department);
		if (exitDepartmentList != null) {
			rep.setCode(1);
			rep.setMes("部门名字已经存在");
			return rep;
		}
		String oldname = department.getName();
		department.setName(departmentName);
		if (departmentService.editDepartment(department)) {
			rep.setCode(0);
			rep.setMes("修改成功");
			/*long opertorid = ShiroUtil.getUserId(request);
			User opertor = userService.findById(opertorid);
			String content = department.getLevel() + "级部门:" + oldname + "修改为:"
					+ department.getLevel() + "级部门:" + department.getName();
			userOperlogService.addOperlog(opertor, content);*/
			return rep;
		} else {
			rep.setCode(1);
			rep.setMes("修改失败");
			return rep;
		}
	}

	/**
	 * 删除部门
	 **/
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO delete(long id, HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();

		Department department = departmentService.getDepartmentById(id);
		if (department == null) {
			res.setCode(1);
			res.setMes("部门不存在");
			return res;
		}
		if (department.getSons().size() > 0) {
			res.setCode(1);
			res.setMes("该部门下有子部门存在");
			return res;
		}
		/*List<User> list = userService.findByDepartment(department);
		if (list.size() > 0) {
			res.setCode(1);
			res.setMes("该部门下有员工存在");
			return res;
		}*/
		String name = department.getName();
		department.setState(Department.State.DEL);
		department.setParent(null);
		departmentService.editDepartment(department);
		res.setCode(0);
		res.setMes("删除成功");
		/*long opertorid = ShiroUtil.getUserId(request);
		User opertor = userService.findById(opertorid);
		String content = opertor.getRealname() + "删除部门:" + name;
		userOperlogService.addOperlog(opertor, content);*/

		return res;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "delDepartments", method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO delRoles(long[] ids, HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		/*long opertorid = ShiroUtil.getUserId(request);
		User opertor = userService.findById(opertorid);*/
		String content = "删除部门:";
		List<Department> delDepartment = new ArrayList<Department>();
		for (long id : ids) {
			Department department = departmentService.getDepartmentById(id);
//			List<User> list = userService.findByDepartment(department);
			if (department.getSons().size() > 0) {
				res.setCode(1);
				res.setMes("部门:" + HtmlEscapse.htmlEscape(department.getName())
						+ "存在子部门,无法删除");
				return res;
			}
			/*if (list.size() > 0) {
				res.setCode(1);
				res.setMes("部门:" + department.getName() + "下存在员工,无法删除");
				return res;
			} else {
				content = content + department.getName() + ";";
				department.setState(Department.State.DEL);
				delDepartment.add(department);
			}*/
		}
		departmentDao.save(delDepartment);
		res.setCode(0);
		res.setMes("删除成功");
//		userOperlogService.addOperlog(opertor, content);
		return res;
	}

}

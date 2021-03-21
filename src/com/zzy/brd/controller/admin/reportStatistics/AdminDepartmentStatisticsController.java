package com.zzy.brd.controller.admin.reportStatistics;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.zzy.brd.dto.rep.admin.reportStatistics.RepOrderformsByDepartDTO;
import com.zzy.brd.entity.Department;
import com.zzy.brd.service.DepartmentService;
import com.zzy.brd.service.OrderCommonStatisticsService;

/**
 * @author:csy
 *    2016年10月18日
 **/
@Controller
@RequestMapping(value="admin/reportStatistics/departmentStatistics")
public class AdminDepartmentStatisticsController {
	
	@Autowired
	private OrderCommonStatisticsService orderCommonStatisticsService;
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 转到部门统计
	 * @param model
	 * @return
	 */
	@RequestMapping(value="departmentStatistics")
	public String departmentStatistics(
			HttpServletRequest request,Long groupid,Model model){
		Map<String, Object> map = orderCommonStatisticsService.getCountOrder();
		model.addAttribute("map", map);
		
		List<Department> departs = new ArrayList<Department>();
		List<Department> allDepart =  departmentService.listDepartments();
		if(groupid != null && groupid > 0){
			Department department = departmentService.getDepartmentById(groupid);
			departs.add(department);
		}else {
			departs = allDepart;
		}
		List<RepOrderformsByDepartDTO> dto = new ArrayList<RepOrderformsByDepartDTO>(
				Collections2.transform(departs,
						new Function<Department, RepOrderformsByDepartDTO>() {
							@Override
							public RepOrderformsByDepartDTO apply(
									Department depart) {
								return new RepOrderformsByDepartDTO(depart, departmentService.statistics(depart));
							}
						}));
		model.addAttribute("departs", allDepart);
		model.addAttribute("gid", groupid);
		model.addAttribute("dto", dto);
		return "admin/reportStatistics/departmentStatistics";
	}
	
}

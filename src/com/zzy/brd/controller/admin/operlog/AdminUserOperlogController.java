package com.zzy.brd.controller.admin.operlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzy.brd.entity.UserOperLog;
import com.zzy.brd.service.UserOperLogService;

/**
 * @author:xpk
 *    2016年10月14日-下午4:40:53
 **/
@Controller
@RequestMapping(value="admin/operlog")
public class AdminUserOperlogController {
	
	@Autowired private UserOperLogService operlogService;
	
	@RequestMapping("list")
	public String list(@RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,Model model){
		
		
		Page<UserOperLog> logs = operlogService.listOperLog(pageNumber);
		model.addAttribute("logs", logs);
		return "admin/userOperLog/list";
	}
	
	
}

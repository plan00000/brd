package com.zzy.brd.controller.admin.information;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;

/**
 * @author:xpk
 *    2016年10月18日-下午8:50:19
 **/
@Controller
@RequestMapping(value="admin/information/apprentice")
public class AdminInformationApprenticeController {
	
	@Autowired
	private InformationService informationService;
	@Autowired
	private UserOperLogService userOperlogService;
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public String list(Model model){
		
		Information information = informationService.findApprentice();
		
		model.addAttribute("information", information);
		return "admin/information/apprentice/list";
	}
	
	@RequestMapping(value="modify")
	@ResponseBody
	public RepSimpleMessageDTO add(String content,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		
		Information information =informationService.findApprentice();
		if(information==null){
			information = new Information();
			information.setAddDate( new Date());
		}
		information.setContent(content);
		information.setModifyDate(new Date());
		information.setSortid(0);
		information.setStatus(Information.Status.YES);
		information.setTitle("收徒指南");
		information.setType(Information.InformationType.APPRENTIC);
		if(informationService.editInformation(information)){
			res.setCode(0);
			res.setMes("修改成功");
			//添加日志
			long userId = ShiroUtil.getUserId(request);
			User opertor = userService.findById(userId);
			String logContent = "修改收徒指南";
			userOperlogService.addOperlog(opertor, logContent);
			return res;
		}else{
			res.setCode(1);
			res.setMes("修改失败");
			return res;
		}
	}
	
	
	
}

package com.zzy.brd.controller.admin.platformSetting;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;

/**
 * @author:xpk
 *    2016年12月2日-下午3:27:31
 **/
@Controller
@RequestMapping("admin/platformSetting/seoSetting")
public class AdminSeoSettingController {
	
	private @Autowired SysInfoService sysInfoService;
	private @Autowired UserOperLogService userOperlogService;
	private @Autowired UserService userService;
	@RequestMapping(method=RequestMethod.GET)
	public String seoSeting(Model model){
		
		SysInfo sysInfo =sysInfoService.findSysInfoById(1l);
		
		
		model.addAttribute("sysInfo", sysInfo);		
		return "admin/platformSetting/seosetting";
	}
	
	
	@RequestMapping(value="set")
	@ResponseBody
	public RepSimpleMessageDTO set(String seoTitle,String seoKeyword,String seoDescribe,HttpServletRequest request){
		RepSimpleMessageDTO rep =new RepSimpleMessageDTO();
		
		SysInfo sysInfo =sysInfoService.findSysInfoById(1l);
		sysInfo.setSeoTitle(seoTitle);
		sysInfo.setSeoKeyword(seoKeyword);
		sysInfo.setSeoDescribe(seoDescribe);
		sysInfoService.editSysInfo(sysInfo);
		long userId = ShiroUtil.getUserId(request);
		User opertor =userService.findById(userId);
		String content = "修改SEO设置";
		userOperlogService.addOperlog(opertor, content);	
		rep.setCode(0);
		rep.setMes("保存成功");
		return rep;
	}
	
	
	
}

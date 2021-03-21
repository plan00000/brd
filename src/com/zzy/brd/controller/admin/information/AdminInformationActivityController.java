package com.zzy.brd.controller.admin.information;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dao.InformationDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.req.map.baidu.ReqCreatePoiDTO;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.rep.information.RepPreviewDTO;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.tld.DateUtils;

/**
 * @author:xpk
 *    2016年10月18日-下午4:23:20
 **/
@Controller
@RequestMapping("admin/information/activity")
public class AdminInformationActivityController {
	
	@Autowired
	private InformationService informationService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserOperLogService userOperlogService;
	@Autowired
	private InformationDao informationDao;
	
	
	@RequestMapping(value="list")
	public String list(
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam (value = "sortType",required=false)String sortType, 
			Model model) throws ParseException{
		Map<String,Object> searchParams = new HashMap<String,Object>();		
		searchParams.put("EQ_type", Information.InformationType.ACTIVITY);
		searchParams.put("NE_status", Information.Status.DEL);
		Page<Information> page = informationService.listInformation(searchParams, pageNumber,sortType);
		
		model.addAttribute("page", page);
		model.addAttribute("sortType", sortType);
		model.addAttribute("pageNumer", pageNumber);
		return "admin/information/activity/list";
	}
	
	
	@RequestMapping(value="toAddPage")
	public String toAddPage(Model model){
		int sortId = 0;
		try{
			sortId = informationService.findMaxSortId(Information.InformationType.ACTIVITY);
		}catch(Exception e){
			
		}
				
		model.addAttribute("sortId", sortId+1);
		return "admin/information/activity/addInformation";
	}
	
	
	@RequestMapping(value="addActivityInformation")
	@ResponseBody
	public RepSimpleMessageDTO addActivityInformation(String title,String content,boolean status,int sortId,HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		Information exit = informationService.findInformationBySortIdAndType(Information.InformationType.ACTIVITY, sortId);
		if(exit!=null){
			res.setCode(1);
			res.setMes("该排序咨询已经存在");
			return res;
		}		
		Information information =new Information(); 
		information.setAddDate(new Date());
		information.setContent(content);
		information.setModifyDate(new Date());
		information.setTitle(title);
		information.setType(Information.InformationType.ACTIVITY);
		information.setSortid(sortId);
		if(status){
			information.setStatus(Information.Status.YES);
		}else{
			information.setStatus(Information.Status.NO);
		}
		if(informationService.editInformation(information)){
			res.setCode(0);
			res.setMes("添加成功");
			long userId = ShiroUtil.getUserId(request);
			User opertor = userService.findById(userId);
			String logContent = "添加资讯:"+information.getTitle();
			userOperlogService.addOperlog(opertor, logContent);
			return res;
		}else{
			res.setCode(1);
			res.setMes("添加失败");
			return res;
		}
	}
	
	@RequestMapping(value="delInformation")
	@ResponseBody
	public RepSimpleMessageDTO delInformation(long id,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		Information information = informationService.findById(id);
		if(information==null){
			res.setCode(1);
			res.setMes("该资讯不存在");
			return res;
		}
		long userId = ShiroUtil.getUserId(request);
		User opertor = userService.findById(userId);
		String logContent = "删除资讯:"+information.getTitle();
		userOperlogService.addOperlog(opertor, logContent);
		informationService.delInformation(information);
		res.setCode(0);
		res.setMes("删除成功");		
		return res;
	}
	
	@RequestMapping(value="delInformations")
	@ResponseBody
	public RepSimpleMessageDTO delInformations(long[] ids,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		String logContent = "删除资讯:";
		for(long id:ids){
			Information information = informationService.findById(id);
			if(information==null){
				res.setCode(1);
				res.setMes("该资讯不存在");
				return res;
			}
			logContent += information.getTitle();
			informationService.delInformation(information);
		}
		long userId = ShiroUtil.getUserId(request);
		User opertor = userService.findById(userId);
		userOperlogService.addOperlog(opertor, logContent);
		res.setCode(0);
		res.setMes("删除成功");
		return res;
	}
	
	
	
	/**修改资讯*/
	@RequestMapping(value="toEditInformation/{id}")
	public String editInformation(@PathVariable long id,
			String sortType,int page,
			Model model){
		Information information = informationService.findById(id);
		model.addAttribute("sortType",sortType);
		model.addAttribute("page", page);
		model.addAttribute("information", information);
		return "admin/information/activity/editInformation";
	}
	
	@RequestMapping(value="editActivityInformation")
	@ResponseBody
	public RepSimpleMessageDTO editActivityInformation(long id,String title,String content,boolean status,int sortId,HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		Information exit = informationService.findInformationBySortIdAndType(Information.InformationType.ACTIVITY, sortId);
		if(exit!=null){
			if(!(exit.getId()==id)){
				res.setCode(1);
				res.setMes("该排序资讯已经存在");
				return res;
			}
		}
		Information information =informationService.findById(id);
		if(information==null){
			res.setCode(1);
			res.setMes("该咨询不存在");
			return res;
		}
		information.setContent(content);
		information.setModifyDate(new Date());
		information.setTitle(title);
		information.setType(Information.InformationType.ACTIVITY);
		information.setSortid(sortId);
		if(status){
			information.setStatus(Information.Status.YES);
		}else{
			information.setStatus(Information.Status.NO);
		}
		if(informationService.editInformation(information)){
			res.setCode(0);
			res.setMes("修改成功");
			long userId = ShiroUtil.getUserId(request);
			User opertor = userService.findById(userId);
			String logContent = "修改资讯:"+information.getTitle();
			userOperlogService.addOperlog(opertor, logContent);
			return res;
		}else{
			res.setCode(1);
			res.setMes("修改失败");
			return res;
		}
	}
	
	/**修改编号排序*/
	@RequestMapping(value="changeSortid")
	@ResponseBody
	public RepSimpleMessageDTO changeSortid(long id,int sortid,HttpServletRequest request){
		RepSimpleMessageDTO res =new RepSimpleMessageDTO();
		Information information =informationService.findById(id);
		if(information==null){
			res.setCode(1);
			res.setMes("该信息不存在");
			return res;
		}
		Information oldinformation = informationService.findInformationBySortIdAndType(Information.InformationType.ACTIVITY, sortid);
		if(oldinformation!=null && information.getId()!=oldinformation.getId()){
			res.setCode(1);
			res.setMes("该序号已经存在");
			return res;
		}
		int oldSortId = information.getSortid();
		information.setSortid(sortid);	
		long userId = ShiroUtil.getUserId(request);
		User opertor = userService.findById(userId);
		String logContent = "修改帮助中心:"+information.getTitle()+"排序:"+oldSortId+"为:"+sortid;
		userOperlogService.addOperlog(opertor, logContent);
		informationService.editInformation(information);
		res.setCode(0);
		res.setMes("修改成功");
		return res;
	}
	
	/****保存预览的图片**/
	@RequestMapping(value="preview")
	@ResponseBody
	public RepPreviewDTO savePrevie(String title,String content){
		RepPreviewDTO dto  = new RepPreviewDTO();
		Information information = new Information();
		information.setContent(content);
		information.setSortid(0);
		information.setTitle(title);
		information.setType(Information.InformationType.ACTIVITY);
		information.setAddDate(new Date());
		information.setStatus(Information.Status.DEL);		
		informationService.editInformation(information);
		dto.setContent(information.getContent());
		dto.setId(information.getId());
		return dto;
	}
	
	/***预览之后不保存*/
	@RequestMapping(value="delete/preview")
	@ResponseBody
	public RepSimpleMessageDTO deletePreview(long id){
		RepSimpleMessageDTO rep =new RepSimpleMessageDTO();
		Information information = informationService.findById(id);
		informationDao.delete(information);
		rep.setCode(0);
		rep.setMes("删除成功");
		return rep;
	}
	
}	

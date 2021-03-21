package com.zzy.brd.controller.admin.platformSetting;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.FriendshipLink;
import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.FriendshipLinkService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.validator.ParameterChecker;

/**
 * @author:xpk
 *    2016年12月2日-下午4:42:53
 **/
@Controller
@RequestMapping("admin/platformSetting/friendshiplink")
public class AdminFriendshipLinkController {
	
	private @Autowired FriendshipLinkService friendshipLinkService;
	private @Autowired UserOperLogService userOperlogService;
	private @Autowired UserService userService;
	@RequestMapping(value="list")
	public String list(
			@RequestParam(value="page",required=false,defaultValue="1") int pageNumber,
			Model model){
		
		Page<FriendshipLink> page = friendshipLinkService.getFriendshipLinkList(pageNumber);
		
		model.addAttribute("page", page);
		return "admin/platformSetting/friendshiplink/list";
	}
	
	
	@RequestMapping(value="toAddEditFriendship")
	public String toAdd(){
		return "admin/platformSetting/friendshiplink/addfriendshiplink";
	}
	
	@RequestMapping(value="addEditFriendship")
	@ResponseBody
	public RepSimpleMessageDTO addFriendship(String title,String linkUrl,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		FriendshipLink exitfriendship = friendshipLinkService.findByTitle(title);
		if(exitfriendship!=null){
			res.setCode(1);
			res.setMes("链接名称已经存在");
			return res;
		}		
		FriendshipLink  friendship = new FriendshipLink();
		friendship.setCreateTime(new Date());
		friendship.setLinkUrl(linkUrl);
		friendship.setTitle(title);
		friendship.setStatus(FriendshipLink.Status.NORMAL);
		friendshipLinkService.editFriendship(friendship);
		long userId = ShiroUtil.getUserId(request);
		User opertor =userService.findById(userId);
		String content = "添加友情链接:"+title;
		userOperlogService.addOperlog(opertor, content);		
		res.setCode(0);
		res.setMes("添加成功");
		return res;
	}
	

	@RequestMapping(value="toEditFriendship/{friendshipId}")
	public String toEdit(
			@PathVariable(value="friendshipId") long friendshipId,
			@RequestParam(value="page",required=false,defaultValue="1") int page,
			Model model
			){
		FriendshipLink friendshipLink = friendshipLinkService.findById(friendshipId);	
		model.addAttribute("friendshipLink", friendshipLink);
		model.addAttribute("page", page);
		return "admin/platformSetting/friendshiplink/editfriendshiplink";
	}
	

	@RequestMapping(value="editFriendship")
	@ResponseBody
	public RepSimpleMessageDTO editFriendship(long friendshipId,String title,String linkUrl,HttpServletRequest request){
		RepSimpleMessageDTO res =  new RepSimpleMessageDTO();
		FriendshipLink exitfriendship = friendshipLinkService.findByTitle(title);
		FriendshipLink friendshipLink = friendshipLinkService.findById(friendshipId);
				
		if(exitfriendship!=null){
			if(exitfriendship.getId()!=friendshipId){
				res.setCode(1);
				res.setMes("链接名称已经存在");
				return res;
			}
		}
		friendshipLink.setTitle(title);
		friendshipLink.setLinkUrl(linkUrl);
		friendshipLink.setModifyTime(new Date());
		friendshipLinkService.editFriendship(friendshipLink);
		long userId = ShiroUtil.getUserId(request);
		User opertor =userService.findById(userId);
		String content = "修改友情链接:"+title;
		userOperlogService.addOperlog(opertor, content);	
		res.setCode(0);
		res.setMes("修改成功");
		return res;
	}
	
	@RequestMapping(value="delFriendship")
	@ResponseBody
	public RepSimpleMessageDTO delFriendship(Long[] ids,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();

		for(Long id:ids){
			FriendshipLink friendship = friendshipLinkService.findById(id);
			friendship.setStatus(FriendshipLink.Status.DEL);
			friendship.setModifyTime(new Date());
			friendshipLinkService.editFriendship(friendship);
		}	
		res.setCode(0);
		res.setMes("删除成功");
		long userId = ShiroUtil.getUserId(request);
		User opertor =userService.findById(userId);
		String content ="删除友情链接";
		userOperlogService.addOperlog(opertor, content);
		return res;
	}
	
	
}

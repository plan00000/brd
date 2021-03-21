package com.zzy.brd.mobile.web.controller.usercenter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.req.user.ReqHeadImageDTO;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.file.FileUtil;

/**
 * @author:xpk
 *    2016年9月27日-下午2:31:10
 **/
@Controller
@RequestMapping(value="weixin/usercenter/personal")
public class WeixinUsercenterModifyPersonController {
	
	@Autowired
	private UserService userService;
	
	/***
	 *修改用户头像 
	 * @param dto
	 * @param result 
	 * @param request
	 * @return 
	 */
	@RequestMapping(value="modifyUserImage")
	@ResponseBody
	public RepSimpleMessageDTO modifyUserImage(HttpServletRequest request, @Valid ReqHeadImageDTO dto
			,BindingResult result){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		if(user==null){
			res.setCode(0);
			res.setMes("该用户不存在");
			return res;
		}
		if(!StringUtils.isBlank(dto.getHeadimgurl())){
			String imgurl = dto.getHeadimgurl();
			imgurl = FileUtil.moveFileToPro(imgurl);
			user.setHeadimgurl(imgurl);
		}
		if(userService.editUser(user)){
			res.setCode(1);
			res.setMes("修改成功");
			return res;
		}
		res.setCode(0);
		res.setMes("头像修改失败");
		return res;
	}
	
	
	@RequestMapping(value="modifyusername",method=RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO modifyNickName(@RequestParam String username,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		if(user==null){
			res.setCode(1);
			res.setMes("该用户不存在");
			return res;
		}
		user.setUsername(username);
		if(userService.editUser(user)){
			res.setCode(0);
			res.setMes("修改成功");
			return res;
		}	
		res.setCode(1);
		res.setMes("修改失败");
		return res;
	}	
}

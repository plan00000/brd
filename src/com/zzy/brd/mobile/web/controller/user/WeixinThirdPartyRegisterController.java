package com.zzy.brd.mobile.web.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.enums.SmsAuthcodeSource;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.QRcode.QRcodeUtils;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.phone.PhoneUtils;
import com.zzy.brd.util.string.StringUtil;

/**
 * @author:xpk
 *    2016年12月20日-下午4:32:00
 **/
@Controller
@RequestMapping(value="weixin/register")
public class WeixinThirdPartyRegisterController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SysInfoService sysInfoService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String toRegister() {
		
		return "mobile/register/register";		
	}
	
	
	@RequestMapping(value="downQrCode")
	public void downQrCode(
			@RequestParam(value="height",required=false,defaultValue="1600") int height,
			@RequestParam(value="width",required=false,defaultValue="1600") int width,
			HttpServletResponse response,HttpServletRequest request
			){
		String content = "http://"+ConfigSetting.File_Server_Ip + "/weixin/register";
		String filePath = "";
		String prodir = ConfigSetting.proPathByType(PathType.WEIXINQRCODE);
		String filename = FileUtil.hashPath(prodir,"register"+height+width+".png" ,"1");
		File destFile = new File(filename);
		SysInfo sysinfo  =sysInfoService.findSysInfoById(1L);
		String qrmidurl = sysinfo.getApprenticeQrCodeUrl();
		int index = qrmidurl.indexOf("filePath=");
		if (index > -1) {
			qrmidurl=StringUtils.substringAfter(qrmidurl, "filePath=");
		}
		qrmidurl = qrmidurl.replace("\\", "/");
		String logoname =ConfigSetting.Pro_Dir+qrmidurl;
		File logoFile = new File(logoname);
		try{
			QRcodeUtils.createQRcodeImageForUser(content, width, height, logoFile, destFile);
		} catch (Exception e){
			System.out.println("生成错误:"+e.getMessage());
		}
		filePath = destFile.getAbsolutePath();
		filePath = filePath.substring(ConfigSetting.Pro_Dir.length());
		//filePath = FileUtil.moveFileToPro(filePath);
		String showName = 1+".png";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		InputStream inputStream;
		filePath = filePath.replace(File.separator, "/");
		String newfilePath = ConfigSetting.proDir()+filePath;
		try {
			inputStream = new FileInputStream(new File(newfilePath));
			String agent = ((HttpServletRequest) request).getHeader("USER-AGENT");
			String encodeFileName = new String(showName.replace(" ", "").getBytes("utf-8"), "ISO8859-1");
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				encodeFileName = URLEncoder.encode(showName.replace(" ", ""), "utf-8");
			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + encodeFileName + "\"");
			OutputStream outputStream = response.getOutputStream();
			int i = -1;
			while ((i = inputStream.read()) != -1) {
				outputStream.write(i);
			}
			inputStream.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			FileUtil.deleteImg(filePath);
		}catch (Exception e){
			System.out.println("删除失败："+e.getMessage());
		}
				
	}
	
	
	@RequestMapping(value="register")
	@ResponseBody
	public RepSimpleMessageDTO register(
			@RequestParam(value="phone",required=true) String phone,
			@RequestParam(value="recommonedPhone",required=false) String recommonedPhone,
			@RequestParam(value="nickname",required=true) String nickname,
			@RequestParam(value="code",required=true) String code,
			@RequestParam(value="password",required=true) String password
			){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		String place = PhoneUtils.getPhoneProv(phone);
		if(place.contains("未知") || place.length()==0 || !place.contains("福建")){
			rep.setCode(1);
			rep.setMes("注册手机号只能是福建省手机号");
			return rep;
		}
		int v = userService.validateSmsAuthcode(phone,code, SmsAuthcodeSource.USER_REG);
		if (v == 1 || v == 2) {
			rep.setCode(1);
			rep.setMes("手机验证码错误");
			return rep;
		}
		if (v == 3) {
			rep.setCode(1);
			rep.setMes("手机验证码过期");
			return rep;
		}
		//判断推荐码是否有效
		if(!StringUtil.isNullString(recommonedPhone)){
			User askUser = userService.findUserByRecommended2(recommonedPhone);
			//推荐码不存在
			if(askUser == null){
				rep.setCode(1);
				rep.setMes("您输入的师傅推荐号码不存在，请确认后输入 ");
				return rep;
			}
			if(askUser.getState()==State.DEL){
				rep.setCode(1);
				rep.setMes("您输入的师傅推荐号码不存在，请确认后输入");
				return rep;
			}
			if(askUser.getUserType().equals(User.UserType.USER)){
				rep.setCode(1);
				rep.setMes("该用户为普通用户,无权限推荐注册");
				return rep;
			}
			
		}
		User newUser = new User();
		newUser.setMobileno(phone);
		newUser.setUsername(nickname);
		newUser.setCreatedate(new Date());
		newUser.setPassword(password.trim().toLowerCase());
		newUser.setState(State.ON);
		newUser.setUserType(UserType.USER);
		//推荐人不为空的情况
		User parentUser = null;
		if(!StringUtil.isNullString(recommonedPhone)){
			User parent = userService.findByAskperson(recommonedPhone);
			if(parent!=null){
				parentUser = parent;
			}else{
				rep.setCode(1);
				rep.setMes("推荐人不存在");
				return rep;
			}
		}
		if(userService.register(newUser,parentUser)){
			rep.setCode(0);
			rep.setMes("注册成功");
			return rep;
		}
		return rep;
	}
	
	
	
	
}

package com.zzy.brd.mobile.web.controller.sharefriend;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
//import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sd4324530.fastweixin.api.response.GetSignatureResponse;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.dao.UserInfoBothDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.enums.SmsAuthcodeSource;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.req.user.ReqShareRegisterDTO;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinUserService;
import com.zzy.brd.util.QRcode.QRcodeUtils;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.phone.PhoneUtils;
import com.zzy.brd.util.random.InvitationCodeUtil;
import com.zzy.brd.util.string.StringUtil;

/**
 * @author:xpk
 *    2016年10月8日-上午9:45:18
 **/
@Controller
@RequestMapping(value="weixin/sharefriend")
public class WeixinShareFriendController {
	
	@Autowired  private UserService userService;
	@Autowired  private UserInfoBothDao bothInfoDao;
	@Autowired	private SysInfoService sysInfoService;
	@Autowired  private WeixinUserService weixinUserService;
	
	@RequestMapping(value="main/{userId}")
	public String main(@PathVariable(value="userId") long userId,Model model,HttpServletRequest request){
		User user =  userService.findById(userId);
		model.addAttribute("headImg",user.getHeadimgurl());
		UserInfoBoth userInfoBoth = user.getUserInfoBoth();
		String recommended ="";
		if(userInfoBoth!=null){		
			recommended = user.getUserInfoBoth().getRecommendCode();
		}
		if(userInfoBoth.getQrCode()!=null){
			String oldQr = userInfoBoth.getQrCode();
			int oldindex = oldQr.indexOf("filePath=");
			if (oldindex > -1) {
				oldQr=StringUtils.substringAfter(oldQr, "filePath=");
			}
			oldQr = oldQr.replace("\\", "/");
			try{
				FileUtil.deleteImg(oldQr);
			}catch(Exception e){
				System.out.println("删除旧二维码图片失败:"+e.getMessage());
			}
		}
		String filePath ="";
		String prodir = ConfigSetting.proPathByType(PathType.WEIXINQRCODE);
		String filename = FileUtil.hashPath(prodir, "weixinQrcode"+userInfoBoth.getId()+
				".png", userInfoBoth.getId()+"");
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
		String qrCodeUrl = "http://"+ConfigSetting.File_Server_Ip+"/weixin/sharefriend/register/"+recommended;
		try{
			QRcodeUtils.createQRcodeImage(qrCodeUrl, 160, 160, logoFile, destFile);
		}catch(Exception e){
			System.out.println("e:"+e.getMessage());
		}
		filePath = destFile.getAbsolutePath();
		filePath = filePath.substring(ConfigSetting.Pro_Dir.length());
		filePath = FileUtil.moveFileToPro(filePath);
		userInfoBoth.setQrCode(filePath);
		bothInfoDao.save(userInfoBoth);
		//获取到分享语句
		SysInfo sysInfo = sysInfoService.findSysInfoById(1l);	
		String shareword = "";
		if(sysInfo==null){
			shareword =  "一起来帮人贷定个小目标，比如先赚一个亿~";
		} else{
			shareword = sysInfo.getShareNotify();
		}
		///获取分享微信所需要的参数
		String jsurl = request.getRequestURL().toString();
		GetSignatureResponse res = weixinUserService.getJsParam(jsurl);
		
		model.addAttribute("appid", sysInfo.getAppid());
		model.addAttribute("res", res);
		model.addAttribute("shareNotify", shareword);
		model.addAttribute("money",user.getUserInfoBoth().getBrokerageCanWithdraw().add(user.getUserInfoBoth().getBrokerageHaveWithdraw()).add(user.getUserInfoBoth().getBrokerageWithdrawing()));
		model.addAttribute("qrCode", filePath);
		model.addAttribute("recommended", recommended);
		model.addAttribute("userId", userId);
		model.addAttribute("shareImg","http://"+ConfigSetting.File_Server_Ip+"/static/brd/img/shareIcon.jpg");
		model.addAttribute("sharelink","http://"+ConfigSetting.File_Server_Ip+"/weixin/sharefriend/main/"+userId);
		return "mobile/sharefriend/sharemain";
	}
	
	@RequestMapping(value="register/{recommended}")
	public String register(@PathVariable(value="recommended") String recommended,Model model) {
		User user = userService.findUserByRecommended2(recommended);
		if(user==null){
			return "error/404";
		}
		
		
		return "mobile/sharefriend/shareRegister";
	}
	
	
	@RequestMapping(value="register")
	@ResponseBody
	public RepSimpleMessageDTO register(@Valid ReqShareRegisterDTO dto,BindingResult result){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		
		User user =userService.findByMobileno(dto.getMobileno());
		if(user!=null){
			rep.setMes("该手机号已被注册");
			rep.setCode(0);
			return rep;
		}
		//判断手机号是否为福建手机号，
		String place = PhoneUtils.getPhoneProv(dto.getMobileno());
		if(place.equals("未知")|| place.length()==0){
			rep.setCode(0);
			rep.setMes("不存在该手机号，请填写正确的手机号");
			return rep;
		}
		//判断手机号是不是为福建手机号
		
		if(!place.contains("福建")){
			rep.setCode(0);
			rep.setMes("注册手机号只能是福建省手机号");
			return rep;
		}
		//判断验证码是否有效
		int v = userService.validateSmsAuthcode(dto.getMobileno(),
				dto.getPhoneAuthcode(), SmsAuthcodeSource.USER_REG);
		if (v == 1 || v == 2) {
			rep.setCode(0);
			rep.setMes("手机验证码错误");
			return rep;
		}
		if (v == 3) {
			rep.setCode(0);
			rep.setMes("手机验证码过期");
			return rep;
		}
		//判断推荐码是否有效
		if(!StringUtil.isNullString(dto.getRecommoned())){
			User askUser = userService.findUserByRecommended2(dto.getRecommoned());
			//推荐码不存在
			if(askUser == null){
				rep.setCode(0);
				rep.setMes("您输入的师傅推荐号码不存在，请确认后输入 ");
				return rep;
			}
			if(askUser.getState()==State.DEL){
				rep.setCode(0);
				rep.setMes("您输入的师傅推荐号码不存在，请确认后输入");
				return rep;
			}
			if(askUser.getUserType().equals(User.UserType.USER)){
				rep.setCode(0);
				rep.setMes("该用户为普通用户,无权限推荐注册");
				return rep;
			}
			
		}
		User newUser = new User();
		newUser.setMobileno(dto.getMobileno());
		newUser.setUsername(dto.getNickname());
		newUser.setCreatedate(new Date());
		newUser.setPassword(dto.getPassword().trim().toLowerCase());
		newUser.setState(State.ON);
		newUser.setUserType(UserType.USER);
		//推荐人不为空的情况
		User parentUser = null;
		if(!StringUtil.isNullString(dto.getRecommoned())){
			User parent = userService.findByAskperson(dto.getRecommoned());
			if(parent!=null){
				parentUser = parent;
			}else{
				rep.setCode(0);
				rep.setMes("推荐人不存在");
				return rep;
			}
		}
		if(userService.register(newUser,parentUser)){
			rep.setCode(1);
			rep.setMes("注册成功");
			return rep;
		}

		return rep;
	}
	
	
	
}

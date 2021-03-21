package com.zzy.brd.mobile.web.controller.withdraw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.BeanMapper;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zzy.brd.algorithm.encrypt.shiro.SHA1Encrypt;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.UserInfoBothDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserBankinfo;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.enums.SmsAuthcodeSource;
import com.zzy.brd.mobile.rest.common.ResultCode;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.rep.withdraw.RepWithdrawMainDTO;
import com.zzy.brd.mobile.web.dto.req.withdraw.ReqWithdrawDTO;
import com.zzy.brd.service.FlowWithdrawService;
import com.zzy.brd.service.OrderformService;
import com.zzy.brd.service.RecordBrokerageService;
import com.zzy.brd.service.UserBankinfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.string.StringUtil;

/**
 * @author:xpk
 *    2016年9月29日-下午4:23:21
 **/
@Controller
@RequestMapping(value="weixin/withdraw")
public class WeixinFlowWithDrawController {
	
	@Autowired UserService userService;
	@Autowired UserBankinfoService userBankinfoService;
	@Autowired FlowWithdrawService flowWithdrawService;
	@Autowired OrderformService orderformService;
	@Autowired RecordBrokerageService recordBrokerageService;
	@Autowired UserInfoBothDao userInfoBothDao;
	@RequestMapping("main")
	public String main(HttpServletRequest request,Model model,@RequestParam(value="bankinfoId", required=false,defaultValue="-1") long bankinfoId) {
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		if(user.getUserType()!=UserType.ADMIN && user.getUserType()!=UserType.CONTROLMANAGER){	
			if(user.getUserInfoBoth().getWithdrawPassword().length()==0){
				return "redirect:setWithdrawPassword";
			}		
			model.addAttribute("money", user.getUserInfoBoth().getBrokerageCanWithdraw());
		}else{
			model.addAttribute("money", "0.00");
		}
		List<UserBankinfo> list = userBankinfoService.getUserBankByUser(user);
		List<RepWithdrawMainDTO> dto = Lists.transform(list, new Function<UserBankinfo,RepWithdrawMainDTO>(){
			@Override
			public RepWithdrawMainDTO apply(UserBankinfo info) {
				// TODO Auto-generated method stub
				return new RepWithdrawMainDTO(info);
			}
		});		
		List<FlowWithdraw> withdrawList = flowWithdrawService.findByUser(user);
		if(withdrawList.size()>0){
			model.addAttribute("lastWithdraw", withdrawList.get(0));	
			model.addAttribute("last", true);
		}else{
			model.addAttribute("last", false);
		}
		
		model.addAttribute("list",dto);
		model.addAttribute("listSize", list.size());
		return "mobile/withdraw/withdrawmain";
	}
	
	
	@RequestMapping("setWithdrawPassword")
	public String setPassword(HttpServletRequest request,Model model) {
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		String tel = user.getMobileno();
		if(tel.length()>4){
			tel = tel.substring(0,3)+"****"+tel.substring(tel.length()-4,tel.length());
		}
		model.addAttribute("phone", tel);
		return "mobile/withdraw/setWithdrawPassword";
	}
	
	
	/**
	 * 提现密码获取手机验证码 
	 **/
	@RequestMapping(value="getPhoneAuthcode",method=RequestMethod.GET)
	@ResponseBody
	public RepSimpleMessageDTO getPhoneAuthcode(@RequestParam int auth,HttpServletRequest request) {
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findUserById(userId);
		RepSimpleMessageDTO dto = new RepSimpleMessageDTO();
		SmsAuthcodeSource type = SmsAuthcodeSource.USER_RETPASS_WITHAW;
		if(auth==1){
			type = SmsAuthcodeSource.USER_WITHDRAW;
		}
		if(user == null){
			dto.setCode(ResultCode.USER_NOT_EXIST);
			dto.setMes("用户不存在");
		}else{
			//发送验证码
			boolean result  = userService.sendAuthcode(user.getMobileno(),type);
			if(result){
				dto.setCode(ResultCode.SUCCESS);
				dto.setMes("发送成功");
			}else{
				dto.setCode(ResultCode.COMMON_SEND_SMSCODE_FAIL);
				dto.setMes("发送验证码失败,请稍后再试");
			}
		}
		return dto;
	}
	
	
	@RequestMapping("setPassword")
	@ResponseBody
	public RepSimpleMessageDTO setPassword(String password,String code,HttpServletRequest request){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		//验证码验证
		int ret = userService.validateSmsAuthcode(user.getMobileno(), code, SmsAuthcodeSource.USER_RETPASS_WITHAW);
		if(ret ==1 || ret==2){
			res.setCode(1);
			res.setMes("验证码有误");
			return res;
		} else if(ret==3){
			res.setCode(1);
			res.setMes("验证码失效，请重新获取");
			return res;
		}
		int result =userService.resetWithdrawPassword(password, user);
		switch(result) {
			case Constant.MOD_WITHDRAW_PWD_SUCC:
				res.setCode(0);
				res.setMes("修改成功");
				return res;
			default :
				res.setCode(1);
				res.setMes("其他错误，稍后在试");
				return res;
		}
	}
	
	/**提现申请*/
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO addWithdraw(@Validated ReqWithdrawDTO dto,HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		UserBankinfo info = userBankinfoService.findById(dto.getBankinfoId());
		long userId = ShiroUtil.getUserId(request);
		User user =userService.findById(userId);
		//身份校验
		UserType userType = user.getUserType();
		String mes = "";
		boolean flage=false;
		//是否有订单
		List<Orderform> orderformList = orderformService.getOrderByUser(user);
		if(orderformList.size()>0){
			for(Orderform order:orderformList){
				if(order.getStatus().equals(Orderform.OrderformStatus.LOANED)){
					flage=true;
					continue;
				}
			}
		}
		if(userType.equals(User.UserType.USER)){
			//普通用户
			if(!flage){
				mes="很抱歉，您需要先下订单才可取现";
			}			
		}else if(userType.equals(User.UserType.SELLER) || userType.equals(User.UserType.SALESMAN) || userType.equals(User.UserType.MANAGER)){
			if(!flage){
				mes= "很抱歉，您需要先下订单或获得贷款佣金才可取现";
				//是否有订单佣金获取
				List<RecordBrokerage> recordList = recordBrokerageService.getRecordBrokerages(user); 
				if(recordList.size()>0){
					flage=true;
				}				
			}			
		}
		if(!flage){
			res.setCode(1);
			res.setMes(mes);
			return res;
			
		}
		if(info==null){
			res.setCode(1);
			res.setMes("未找到该银行卡");
			return res;
		}
		if(dto.getMoney().compareTo(user.getUserInfoBoth().getBrokerageCanWithdraw())==1){
			res.setCode(1);
			res.setMes("提现金额不能大于可提现最大金额");
			return res;
		}		
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		String ePassword = encrypt.encryptPasswordBySalt(
				dto.getPassword().toLowerCase(),
				user.getUserInfoBoth().getWithdrawSalt());
		String password = user.getUserInfoBoth().getWithdrawPassword();
		if(!password.equals(ePassword)){
			res.setCode(1);
			res.setMes("提现密码错误");
			return res;
		}
		int ret = userService.validateSmsAuthcode(user.getMobileno(), dto.getCode(), SmsAuthcodeSource.USER_WITHDRAW);
		if(ret==1 || ret==2){
			res.setCode(1);
			res.setMes("验证码错误");
			return  res;
		} else if(ret==3){
			res.setCode(1);
			res.setMes("验证码失效，请重新获取");
			return res;
		}
		//最大流水号
		String maxflowno="0";
		try{
			maxflowno = flowWithdrawService.findMaxFlowno();
			maxflowno =maxflowno.substring(1,maxflowno.length());
		}catch(Exception e){
			
		}
		if(maxflowno==null || maxflowno.trim().length()==0){
			maxflowno ="0";
		}
		int next = Integer.valueOf(maxflowno) +1;
		String finalflow = "T"+StringUtil.toStringZeroByInteger(next, 6);	
		FlowWithdraw withdraw = new FlowWithdraw();
		withdraw.setAccountname(info.getAccountname());
		withdraw.setBankaccount(info.getBankaccount());
		withdraw.setBankname(info.getBankname());
		withdraw.setProvince(info.getProvince());
		withdraw.setCity(info.getCity());
		withdraw.setArea(info.getArea());
		withdraw.setFlowno(finalflow);
		withdraw.setUser(user);
		withdraw.setMoney(dto.getMoney());
		withdraw.setCreateTime(new Date());
		withdraw.setStatus(WithdrawStatus.NOCHECK);
		withdraw.setTelephone(user.getMobileno());
		if(flowWithdrawService.saveFlowWithdraw(withdraw)){
			res.setCode(0);
			res.setMes("提现成功");
			//成功更改余额。
			//可提现
			UserInfoBoth both  =user.getUserInfoBoth();
			both.setBrokerageCanWithdraw(user.getUserInfoBoth().getBrokerageCanWithdraw().subtract(dto.getMoney()));
			//提现中
			both.setBrokerageWithdrawing(user.getUserInfoBoth().getBrokerageWithdrawing().add(dto.getMoney()));
			userInfoBothDao.save(both);
			return res;
		}else{
			res.setCode(0);
			res.setMes("提现失败");
			return res;
		}
	}
	
	
	/**修改提现密码*/
	@RequestMapping(value="toModifyPassword")
	public String modifyWithdrawPassword(HttpServletRequest request,Model model){
		
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		String tel = user.getMobileno();
		if(tel.length()>4){
		tel = tel.substring(0,3)+"****"+tel.substring(tel.length()-4,tel.length());		
		}
		model.addAttribute("phone", tel);
		return "mobile/withdraw/modifyWithdrawPassword";
	}
	
	
	@RequestMapping(value="modifyPassword")
	@ResponseBody
	public RepSimpleMessageDTO modifyPassword(HttpServletRequest request,String  newPassword,String confirmPassword,String code){
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		
		if(!newPassword.equals(confirmPassword)){
			res.setCode(1);
			res.setMes("两次输入的密码必须相同");
			return res;					
		}
		int ret = userService.validateSmsAuthcode(user.getMobileno(), code, SmsAuthcodeSource.USER_RETPASS_WITHAW);
		if(ret==1 || ret==2){
			res.setCode(1);
			res.setMes("验证码错误");
			return  res;
		} else if(ret==3 ){
			res.setCode(1);
			res.setMes("验证码失效，请重新获取");
			return res;
		}
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		String ePassword = encrypt.encryptPasswordBySalt(
				newPassword.toLowerCase(),
				user.getUserInfoBoth().getWithdrawSalt());
		
		if(user.getUserType()!=UserType.ADMIN && user.getUserType()!=UserType.CONTROLMANAGER){
			user.getUserInfoBoth().setWithdrawPassword(ePassword);
			if(userService.editUser(user)){
				res.setCode(0);
				res.setMes("修改成功");
				return res;
			}
		}else{
			res.setCode(1);
			res.setMes("您是"+user.getUserType().getStr()+"不需要提现密码");
			return res;
		}
		return res;
	}
	

	
}

package com.zzy.brd.mobile.web.controller.bankinfo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.UserBankinfo;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.req.bankinfo.ReqAddUserBankinfoDTO;
import com.zzy.brd.service.UserBankinfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.bank.BankUtil;

/**
 * @author:xpk
 *    2016年9月29日-下午4:29:13
 **/
@Controller
@RequestMapping(value="weixin/bankinfo")
public class WeixinBankInfoController {
	
	@Autowired private UserService userService;
	@Autowired private 	UserBankinfoService bankInfoService;
	
	@RequestMapping(value="myBankinfo")
	public String myBankinfo(HttpServletRequest request, Model model){
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		List<UserBankinfo> list = bankInfoService.getUserBankByUser(user);
		for(UserBankinfo uu:list){
			String account =uu.getBankaccount();
			if(account.length()>4){
				account = account.substring(account.length()-4,account.length());
			}
			uu.setBankaccount(account);			
		}
		model.addAttribute("list",list);
		//手机尾号
		String mobileno = user.getMobileno();
		if(mobileno.length()>4){
			mobileno = mobileno.substring(mobileno.length()-4,mobileno.length());
		}
		model.addAttribute("mobileno", mobileno);		
		return "mobile/bankinfo/myBankinfo";
	}
	
	
	@RequestMapping(value="toAddBankinfo/{order}")
	public String toAddBankinfo(HttpServletRequest request,Model model,@PathVariable int order) {
		model.addAttribute("order", order);
		long userId  =ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		model.addAttribute("user", user);	
		model.addAttribute("order", order);
		return "mobile/bankinfo/addBankinfo";
	}
	
	@RequestMapping(value="addBankinfo",method=RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO addBankinfo(@Validated ReqAddUserBankinfoDTO dto,HttpServletRequest request) {
		RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		List<UserBankinfo> exitinfo = bankInfoService.getUserBankByUserAndAccount(dto.getBankaccount(), user);
		if(exitinfo.size()>0){
			res.setCode(1);
			res.setMes("您已经添加过该银行卡");
			return res;
		}
		String result = BankUtil.getNameOfBank(dto.getBankaccount());
		if(result.equals("无法识别银行卡所属银行名称")|| result.length()==0){
			res.setCode(1);
			res.setMes("无法识别银行卡所属银行名称");
			return res;
		}
		if(dto.getBankname().equals("中国建设银行")){
			if(!result.contains("建设")){
				res.setCode(1);
				res.setMes("请添加中国建设银行卡");
				return res;
			}			
		}else if(dto.getBankname().equals("中国工商银行")){
			if(!result.contains("工商")){
				res.setCode(1);
				res.setMes("请添加中国工商银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国农业银行")){
			if(!result.contains("农业")){
				res.setCode(1);
				res.setMes("请添加中国农业银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国交通银行")){
			if(!result.contains("交通")){
				res.setCode(1);
				res.setMes("请添加中国交通银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国兴业银行")){
			if(!result.contains("兴业")){
				res.setCode(1);
				res.setMes("请添加中国兴业银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中信银行")){
			if(!result.contains("中信")){
				res.setCode(1);
				res.setMes("请添加中信银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国银行")){
			if(!result.contains("中国")){
				res.setCode(1);
				res.setMes("请添加中国银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国招商银行")){
			if(!result.contains("招商")){
				res.setCode(1);
				res.setMes("请添加中国招商银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国邮政储蓄银行")){
			if(!result.contains("邮政")){
				res.setCode(1);
				res.setMes("请添加中国邮政储蓄银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国民生银行")){
			if(!result.contains("民生")){
				res.setCode(1);
				res.setMes("请添加中国民生银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国光大银行")){
			if(!result.contains("光大")){
				res.setCode(1);
				res.setMes("请添加中国光大银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国华夏银行")){
			if(!result.contains("华夏")){
				res.setCode(1);
				res.setMes("请添加中国华夏银行卡");
				return res;
			}	
		}else if(dto.getBankname().equals("中国广发银行")){
			if(!result.contains("广发")&&!result.contains("广东发展银行")){
				res.setCode(1);
				res.setMes("请添加中国广发银行卡");
				return res;
			}	
		}
		
		UserBankinfo info = new UserBankinfo();
		info.setUser(user);
		info.setBankname(dto.getBankname());
		info.setAccountname(dto.getName());
		info.setBankaccount(dto.getBankaccount());
		info.setProvince(dto.getProvince());
		info.setCity(dto.getCity());
		
		if(bankInfoService.editBankinfo(info)){
			res.setCode(0);
			res.setMes("添加成功");
			return res;
		}else{
			res.setCode(1);
			res.setMes("添加失败");
			return res;
		}
	}
	
	
	
	
	
	
}

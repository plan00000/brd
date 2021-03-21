package com.zzy.brd.mobile.web.controller.withdraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.rep.withdraw.RepWithdrawDetailDTO;
import com.zzy.brd.mobile.web.dto.rep.withdraw.RepWithdrawDetailDataDTO;
import com.zzy.brd.service.FlowWithdrawService;
import com.zzy.brd.service.UserService;

/**
 * @author:xpk
 *    2016年9月30日-下午3:08:53
 **/
@Controller
@RequestMapping(value="weixin/withdraw/detail")
public class WeixinFlowWithdrawDetailController {
	
	@Autowired private UserService userService;
	@Autowired FlowWithdrawService flowWithdrawService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String detail(HttpServletRequest request,Model model) {
		return "mobile/withdraw/detail";
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public RepWithdrawDetailDTO detail(HttpServletRequest request,@RequestParam String status,
			@RequestParam(value="pageNumber",required=false,defaultValue="1") int pageNumber ){
		RepWithdrawDetailDTO res = new RepWithdrawDetailDTO();
		int pageSize=50;
		long userId = ShiroUtil.getUserId(request);
		User user  = userService.findById(userId);
		Map<String,Object> searchParams = Maps.newHashMap();
		searchParams.put("EQ_user.id", user.getId());
		
		List<FlowWithdraw> list = new ArrayList<FlowWithdraw>();
		Page<FlowWithdraw> page = flowWithdrawService.getFlowWithdraw(searchParams, true, pageNumber, pageSize,status);
		while (page != null && page.getNumberOfElements() > 0) {
			list.addAll(page.getContent());
			if (!page.hasNext()) {
				break;
			} else {
				pageNumber++;
			}
			page = flowWithdrawService.getFlowWithdraw(searchParams, true, pageNumber, pageSize,status);
		}
		System.out.println("pag:"+list.size());
		
		if(page.getContent().size()==0){
			res.setExists(false);
			return res;
		}
		
		List<RepWithdrawDetailDataDTO> dto = Lists.transform(list, new Function<FlowWithdraw,RepWithdrawDetailDataDTO>(){
			@Override
			public RepWithdrawDetailDataDTO apply(FlowWithdraw withdraw) {
				// TODO Auto-generated method stub
				return new RepWithdrawDetailDataDTO(withdraw);
			}
		});
		if(dto.size()>0){
			res.setExists(true);
		}else{
			res.setExists(false);
		}
		res.setDto(dto);
		res.setHasNext(page.hasNext());	
		return res;
	}
	
	
	
	
}

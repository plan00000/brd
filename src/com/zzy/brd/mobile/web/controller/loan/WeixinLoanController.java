package com.zzy.brd.mobile.web.controller.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sd4324530.fastweixin.api.response.AddTemplateResponse;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.BrokerageDistributionDao;
import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.BrokerageDistribution;
import com.zzy.brd.entity.BrokeragePaymentRecords;
import com.zzy.brd.entity.OrderOperLog;
import com.zzy.brd.entity.OrderOperLog.OrderformOperatType;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.OrderformRemark;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.rep.orderform.RepMyLoanDTO;
import com.zzy.brd.mobile.web.dto.rep.orderform.RepMyLoanDataDTO;
import com.zzy.brd.service.BrokerageApplyService;
import com.zzy.brd.service.BrokerageDistributionService;
import com.zzy.brd.service.BrokeragePaymentRecordsService;
import com.zzy.brd.service.OrderOperLogService;
import com.zzy.brd.service.OrderformRemarkService;
import com.zzy.brd.service.OrderformService;
import com.zzy.brd.service.UserService;

/**
 * @author:xpk 2016年9月27日-下午5:11:55
 **/
@Controller
@RequestMapping("weixin/loan")
public class WeixinLoanController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderformService orderformService;
	@Autowired
	private BrokerageDistributionService brokerageDistributionService;
	@Autowired
	private OrderformRemarkService orderformRemarkService;
	@Autowired
	private OrderOperLogService orderOperLogService;
	@Autowired 
	private BrokeragePaymentRecordsService brokeragePaymentRecordsService;
	@Autowired
	private BrokerageApplyService brokerageApplyService;
	
	@RequestMapping("myloan")
	public String loanProgress(HttpServletRequest request) {
		
		return "mobile/loan/myloan";
	}

	/*** 查看详情 */
	@RequestMapping("{orderformId}/detail")
	public String detail(@PathVariable(value = "orderformId") long orderformId,
			Model model, HttpServletRequest request) {
		Orderform orderform = orderformService.findOrderById(orderformId);
		String phone = orderform.getTelephone();
		phone = phone.substring(0, 3) + "*****"
				+ phone.substring(phone.length() - 3);
		model.addAttribute("phone", phone);
		String name = orderform.getName().trim();
		if(name.length()<3){
			name = "*" + name.substring(name.length() - 1, name.length());
		}else {
			name = "**" + name.substring(name.length() - 1, name.length());
		}
		model.addAttribute("name", name);
		
		if(orderform.getStatus().equals(Orderform.OrderformStatus.LOANED)){
			BrokerageApply apply = brokerageApplyService.findByOrderfrom(orderform);
			model.addAttribute("apply", apply);
			if(!orderform.getProduct().getType().getBillType().equals(BillType.SELFHELPLOAN)){
				if(apply.getSendStatus().equals(BrokerageApply.SendStatus.SINGAL)){
					model.addAttribute("realBrokerage", apply.getSelfBrokerage());
				}else if (apply.getSendStatus().equals(BrokerageApply.SendStatus.MANEY)) {
					List<BrokeragePaymentRecords> records = brokeragePaymentRecordsService.findByOrderform(orderform);
					model.addAttribute("records",records);
				}
			}
		}		
		model.addAttribute("orderform", orderform);
		return "mobile/loan/loanDetail";
	}

	/** 查看进度 */
	@RequestMapping("{orderformId}/progress")
	public String progress(
			@PathVariable(value = "orderformId") long orderformId, Model model,
			HttpServletRequest request) {
		Orderform orderform = orderformService.findOrderById(orderformId);		
		if (orderform == null) {
			return "redirect:weixin/loan/myloan";
		}
		model.addAttribute("orderform", orderform);
		String phone = orderform.getTelephone();
		String  suffixphone = "";
		if(phone.length()>3){
			suffixphone =phone.substring(phone.length() - 3);
		}else{
			suffixphone = phone;
		}
		phone = phone.substring(0, 3) + "*****"+suffixphone;
		model.addAttribute("phone", phone);
		String name = orderform.getName().trim();
		if(name.length()<3){
			name = "*" + name.substring(name.length() - 1, name.length());
		} else {
			name = "**" + name.substring(name.length() - 1, name.length());
		}
		model.addAttribute("name", name);
		model.addAttribute("money",orderform.getMoney().divide(new BigDecimal("10000")));
	
		model.addAttribute("logs", orderform.getOperLogs());
		for(OrderOperLog log:orderform.getOperLogs()){
			if(log.getOperatType().equals(OrderformOperatType.UNLOAN)){
				model.addAttribute("unloanTime", log.getCreateTime());
			}
			if(log.getOperatType().equals(OrderformOperatType.CHECKFAIL)){
				model.addAttribute("failtTime", log.getCreateTime());
			}
			if(log.getOperatType().equals(OrderformOperatType.LOANED)){
				model.addAttribute("loanTime", log.getCreateTime());
			}
			if(log.getOperatType().equals(OrderformOperatType.UNCHECKED)){
				model.addAttribute("uncheckTime", log.getCreateTime());
			}
		}	
		return "mobile/loan/loanProgress";
	}

	@RequestMapping(value = "myloanData")
	@ResponseBody
	public RepMyLoanDTO myloanData(
			@RequestParam(value = "status") String status,
			HttpServletRequest request) {
		int pageSize = 50;
		RepMyLoanDTO rep = new RepMyLoanDTO();
		long userId = ShiroUtil.getUserId(request);
		User user = userService.findById(userId);
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("EQ_user.id", user.getId());
		searchParams.put("EQ_source", OrderSource.WECHAT);

		int pageNumber =1;
		List<Orderform> orderList = new ArrayList<Orderform>();
		Page<Orderform> page = orderformService.getOrderform(searchParams,
				false, pageNumber, pageSize,status);
		
		while (page != null && page.getNumberOfElements() > 0) {
			orderList.addAll(page.getContent());
			if (!page.hasNext()) {
				break;
			} else {
				pageNumber++;
			}
			page = orderformService.getOrderform(searchParams,
					true, pageNumber, pageSize,status);
		}
		rep.setHasNext(page.hasNext());
		boolean isExists = false;
		if (orderList.size() > 0) {
			isExists = true;
			List<RepMyLoanDataDTO> dto = Lists.transform(orderList,	new Function<Orderform, RepMyLoanDataDTO>() {
						@Override
						public RepMyLoanDataDTO apply(Orderform orderform) {
							// TODO Auto-generated method stub
							return new RepMyLoanDataDTO(orderform);
						}
			});
			rep.setDto(dto);
		}
		rep.setExists(isExists);
		rep.setStatus(status);
		return rep;
	}

}

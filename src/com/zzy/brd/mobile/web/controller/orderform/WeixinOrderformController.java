package com.zzy.brd.mobile.web.controller.orderform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.BeanMapper;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.OrderSerial;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.Orderform.PaymentBrokerageType;
import com.zzy.brd.entity.Product.InterestType;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.entity.WeixinPost.NoticeType;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.mobile.web.dto.req.orderform.ReqOrderformDTO;
import com.zzy.brd.service.OrderSerialService;
import com.zzy.brd.service.OrderformService;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserInfoBothService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinPostService;
import com.zzy.brd.service.WeixinTemplateService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.sms.SMSUtil;
import com.zzy.brd.util.string.StringUtil;
import com.zzy.brd.util.tld.PriceUtils;
import com.zzy.brd.util.weixin.NewInfoTemplate;

/**
 * 微信站-订单
 * @author lzh 2016-9-29
 *
 */
@Controller
@RequestMapping("weixin/orderform")
public class WeixinOrderformController {
	private static final Logger logger = LoggerFactory.getLogger(WeixinOrderformController.class);
	
	@Autowired
	private OrderformService orderformService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderSerialService orderSerialService;
	@Autowired
	private UserInfoBothService userInfoBothService;
	@Autowired
	private WeixinTemplateService weixinTemplateService;
	@Autowired
	private WeixinPostService weixinPostService;
	@Autowired
	private SysInfoService sysInfoService;
	/**
	 * 贷款订单申请页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("toApplyPage/{id}")
	public String toRequirePage(@PathVariable long id,Model model){
		Product product = productService.findById(id);
		if(product == null){
			logger.error("查找product 失败：not fount id:{}", id);
			return "error/500"; 
		}
		List<Double> spreads = new ArrayList<Double>();
		//判断是不是赚差价
		if(product.getType().getBillType()==BillType.EARNDIFFERENCE){
			BigDecimal spreadMin =new BigDecimal(0);
			BigDecimal spreadMax =new BigDecimal(0);
			if(product.getInterestType() == InterestType.INTERESTMODELDAY){
				spreadMin = product.getInfo().getSpreadMin().multiply(new BigDecimal(1000).setScale(1, BigDecimal.ROUND_HALF_UP));
				spreadMax = product.getInfo().getSpreadMax().multiply(new BigDecimal(1000).setScale(1, BigDecimal.ROUND_HALF_UP));
			}else{
				spreadMin = product.getInfo().getSpreadMin().multiply(new BigDecimal(100).setScale(1, BigDecimal.ROUND_HALF_UP));
				spreadMax = product.getInfo().getSpreadMax().multiply(new BigDecimal(100).setScale(1, BigDecimal.ROUND_HALF_UP));
			}
			for(BigDecimal i= spreadMin;i.compareTo(spreadMax)<1;i= i.add(new BigDecimal(0.1))){
				spreads.add(i.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			spreads.add(spreadMax.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		model.addAttribute("spreads",spreads);
		model.addAttribute("product", product);
		return "mobile/orderform/orderformApply";
	}
	/**
	 * 贷款提交
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addOrderform" ,method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO orderformApply(@Valid ReqOrderformDTO dto
			,BindingResult result
			,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		Long userId = ShiroUtil.getUserId(request);
		if(userId == null){
			rep.setCode(2);
			rep.setMes("请登录");
			return rep;
		}
		User user = userService.findById(userId);
		Product product = productService.findById(dto.getProductId());
		if(product == null){
			rep.setCode(0);
			rep.setMes("该产品不存在");
			return rep;
		}
		
		Orderform orderform =new Orderform();
		orderform = BeanMapper.map(dto,Orderform.class );
		orderform.setName(dto.getName().trim());
		if(product.getType().getBillType().ordinal() == 1 && (product.getInterestType().ordinal() ==0 ||product.getInterestType().ordinal() ==2 )){
			orderform.setSpreadRate(dto.getSpreadRate().divide(new BigDecimal(100)));
		}
		if(product.getType().getBillType().ordinal() == 1 && product.getInterestType().ordinal() == 1){
			orderform.setSpreadRate(dto.getSpreadRate().divide(new BigDecimal(1000)));
		} 
		if(product.getType().getBillType().ordinal() == 2 && product.getInterestType().ordinal() != 3){
			orderform.setPercentageRate(product.getInfo().getPercentageRate());
		}
		//设置特殊模式的佣金
		if(product.getType().getBillType().ordinal() == 2 && product.getInterestType().ordinal() == 3){
			switch(dto.getLoanTime()){
			case 6: orderform.setPercentageRate(product.getInfo().getAlgoParamB());break;
			case 12: orderform.setPercentageRate(product.getInfo().getAlgoParamD());break;
			case 24: orderform.setPercentageRate(product.getInfo().getAlgoParamF());break;
			case 36: orderform.setPercentageRate(product.getInfo().getAlgoParamH());break;
			default: break;
			}
		}
		//万元转元
		orderform.setMoney(dto.getMoney().multiply(new BigDecimal(10000)));
		orderform.setActualMoney(new BigDecimal(0));
		orderform.setUser(user);
		orderform.setProduct(product);
		//设置旧的产品
		orderform.setOldProduct(product);
		orderform.setProductInfo(product.getInfo());
		orderform.setCreateTime(new Date());
		orderform.setOldLoanTime(dto.getLoanTime());
		//添加下单人预计佣金
		orderform.setSelfBrokerageNum(dto.getBrokerageRateNum());
		//计算预计佣金总和(除自助贷)
		if(product.getType().getBillType().ordinal()!=0){
			BigDecimal fatherRate = dto.getBrokerageRateNum().multiply(product.getInfo().getFatherRate());
			BigDecimal bussinessRate = dto.getBrokerageRateNum().multiply(product.getInfo().getSellerRate());
			BigDecimal salesmanRate = dto.getBrokerageRateNum().multiply(product.getInfo().getSalesmanRate());
			orderform.setBrokerageRateTotal(dto.getBrokerageRateNum().add(fatherRate).add(bussinessRate).add(salesmanRate));
		}
		if(product.getType().getBillType().ordinal()==0){
			BigDecimal fatherRate = dto.getBrokerageRateNum().multiply(product.getInfo().getFatherRate());
			BigDecimal bussinessRate = dto.getBrokerageRateNum().multiply(product.getInfo().getSellerRate());
			BigDecimal salesmanRate = dto.getBrokerageRateNum().multiply(product.getInfo().getSalesmanRate());
			orderform.setBrokerageRateTotal(fatherRate.add(bussinessRate).add(salesmanRate));
		}
		//设置贷款利率
		orderform.setLoanInsterestRate(product.getInfo().getLoanRate());
		//订单类型
		//订单号
		String name = "微信订单";
		OrderSerial orderSerial = orderSerialService.findOrderSerialByName(name);
		if(orderSerial == null){
			OrderSerial newOrderSerial = new OrderSerial();
			newOrderSerial.setName(name);
			newOrderSerial.setSerial(1);
			orderSerialService.addOrEditOrderSerial(newOrderSerial);
			String orderno = "D"+StringUtil.toStringZeroByInteger(1, 6);
			orderform.setOrderNo(orderno);
		}else{
			String orderno = "D"+ StringUtil.toStringZeroByInteger(orderSerial.getSerial()+1, 6);
			orderSerial.setSerial(orderSerial.getSerial()+1);
			orderSerialService.addOrEditOrderSerial(orderSerial);
			orderform.setOrderNo(orderno);
		}
		
		orderform.setSource(OrderSource.WECHAT);
		orderform.setPaymentBrokerageType(PaymentBrokerageType.DISPOSABLE);
		//订单状态
		orderform.setStatus(OrderformStatus.UNCONTACTED);
		
		//设置订单的旧师父，业务员，商家
		if(user.getUserInfoBoth().getParent()!=null){
			orderform.setOldParent(user.getUserInfoBoth().getParent());
			if(user.getUserInfoBoth().getSeller()!=null){
				orderform.setOldBussiness(user.getUserInfoBoth().getSeller());
			}
			if(user.getUserInfoBoth().getSalesman()!=null){
				orderform.setOldSalesman(user.getUserInfoBoth().getSalesman());
			}
		}
		
		if(orderformService.addOrderform(orderform)){
			//成功操作
			//对应的产品申请数量加一
			product.setApplyTimes(product.getApplyTimes() +1);
			productService.editProduct(product);
			//用户对应订单数量增加1
			user.getUserInfoBoth().setOrderSum(user.getUserInfoBoth().getOrderSum()+1);	
			
			if(user!=null){
				WeixinUser weixinUser = user.getWeixinUser();
				if(weixinUser!=null){
					WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.LOADSUBMIT);
					if(weixinPost!=null){
						WeixinPost.State state = weixinPost.getState();
						if(state!=null){
							if(state==WeixinPost.State.ON){
								NewInfoTemplate newInfoTemplate = weixinTemplateService.getLoadSubmit(orderform,request);
								int res = weixinTemplateService.sendTemplateMessage(newInfoTemplate);
								if(res!=0){
									rep.setCode(0);
									rep.setMes("发送模板消息失败");
								}
								}
							}
						}
					}
				}
			
				SysInfo sysInfo = sysInfoService.getSysInfo(1l);
				if(sysInfo!=null){
					String mobile = sysInfo.getNotifyPhone();
					if(mobile!=null&&mobile.length()!=0){
						String orderTime = DateUtil.formatDateByFormat(new Date(), "yyyy年MM月dd日 HH时mm分ss秒");
						String loanTimeSigal= "月";
						if(orderform.getProduct().getInterestType().ordinal()==1){
							loanTimeSigal = "天";
						}
						String loanInsterestRateStr = "";
						if(orderform.getProduct().getInterestType().ordinal()==1){
							loanInsterestRateStr=PriceUtils.showThousandRateWithoutUnit(orderform.getLoanInsterestRate());
						}else{
							loanInsterestRateStr=PriceUtils.showRateWithoutUnit(orderform.getLoanInsterestRate());
						}
						String content="【帮人贷】有客户提交了订单。产品名称："+orderform.getProductInfo().getProductName()+",";
						content = content+"贷款人姓名："+orderform.getName()+",手机号码："+orderform.getTelephone()+",贷款金额："
								+ orderform.getMoney().divide(new BigDecimal(10000))+"万，贷款期限："+orderform.getLoanTime()+loanTimeSigal
								+ ",贷款利率："+loanInsterestRateStr ;
						if(orderform.getProduct().getType().getBillType()== BillType.EARNDIFFERENCE) {
							content = content + ",加价:"+ PriceUtils.showRateWithoutUnit(orderform.getSpreadRate())+ "%/"+loanTimeSigal;
						}
						content = content+",预计佣金总和："+orderform.getBrokerageRateTotal().setScale(2)+",备注："+orderform.getRemark();
						System.out.println("sms content:"+content);
					    String[] arr=mobile.split(";");
					    List<String> mobilenos = Arrays.asList(arr);
					    try {
					    	for (int i = 0; i < mobilenos.size(); i++) {
					    		SMSUtil.sendMessage(mobilenos.get(i), content);
							} 
					    	rep.setCode(1);
					    	rep.setMes("发送成功");
					        }catch (Exception e) {
					            rep.setCode(0);
					            rep.setMes("发送失败");
								e.printStackTrace();
						}
				}
			}
			
			rep.setCode(1);
			rep.setMes(orderform.getId()+"");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("订单提交失败");
		return rep;
	}
	/**
	 * 成功页面
	 * @param model
	 * @return
	 */
	@RequestMapping("success/{id}")
	public String toSuccess(
			@PathVariable long id
			,Model model){
		model.addAttribute("orderformId", id);
		return "mobile/orderform/success";
	}
}

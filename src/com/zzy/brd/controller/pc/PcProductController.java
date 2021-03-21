package com.zzy.brd.controller.pc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.BeanMapper;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.req.pc.orderform.ReqPcOrderformDTO;
import com.zzy.brd.entity.OrderSerial;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.Orderform.OrderSource;
import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.Product.MortgageType;
import com.zzy.brd.entity.Product.Status;
import com.zzy.brd.entity.ProductType.BillType;
import com.zzy.brd.enums.SmsAuthcodeSource;
import com.zzy.brd.mobile.web.controller.product.WeixinProductController;
import com.zzy.brd.service.FriendshipLinkService;
import com.zzy.brd.service.OrderSerialService;
import com.zzy.brd.service.OrderformService;
import com.zzy.brd.service.ProductService;
import com.zzy.brd.service.ProductTypeService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.phone.PhoneUtils;
import com.zzy.brd.util.sms.SMSUtil;
import com.zzy.brd.util.string.StringUtil;
import com.zzy.brd.util.tld.PriceUtils;

/**
 * pc-贷款
 * @author lzh 2016-10-8
 *
 */
@Controller
@RequestMapping("pc/product")
public class PcProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(PcProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderformService orderformService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private OrderSerialService orderSerialService;
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired 
	private FriendshipLinkService friendshipLinkService;
	/**
	 * 产品列表
	 * @return
	 */
	@RequestMapping("list")
	public String productList(@RequestParam (value="page",required = true, defaultValue = "1") int pageNum
			,@RequestParam(value = "mortgageType",defaultValue="",required =false) String mortgageType
			,@RequestParam(value = "type",defaultValue="",required = false) String type 
			,@RequestParam(value = "sortBy",defaultValue = "",required = false) String sortBy
			,HttpServletRequest request
			,Model model){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_type.billType", BillType.SELFHELPLOAN);
		
		if(!StringUtils.isBlank(mortgageType) ){
			if("NULLLOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType", MortgageType.NULLLOAN);
			}
			if("MORTGAGELOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType", MortgageType.MORTGAGELOAN);
			}
			if("CREDITLOAN".equals(mortgageType)){
				searchParams.put("EQ_mortgageType",MortgageType.CREDITLOAN);
			}
		}
		
		if(!StringUtils.isBlank(type)){
			searchParams.put("EQ_type.productName", type);
		}
		searchParams.put("EQ_status",Status.NORMAL);
		Page<Product> products = productService.getProductList(searchParams,sortBy,pageNum,Constant.PAGE_SIZE);
		
		List<ProductType> productTypes = productTypeService.getPcTypes();
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("products", products);
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("mortgageType", mortgageType);
		model.addAttribute("type", type);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("page", pageNum);
		model.addAttribute("queryStr", request.getQueryString());
		model.addAttribute("pcSelect", 2);
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		return "pc/product/list";
	}
	/**
	 * 产品详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("productDesc/{id}")
	public String productDesc(@PathVariable long id,Model model){
		Product product = productService.findById(id);
		if(product == null){
			logger.error("查找product 失败：not fount id:{}", id);
			return "error/500";
		}
		if(product.getStatus() ==Status.DELETED){
			return "error/product_not_valid";
		}
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		model.addAttribute("product", product);
		model.addAttribute("pcSelect", 2);
		model.addAttribute("friendLinks", friendshipLinkService.findForFont());
		return "pc/product/productdesc";
	}
	/**
	 * 获取短信注册验证码
	 * @param phone
	 * @param request
	 * @return
	 */
	@RequestMapping("getPhoneAuthcode/addOrderform")
	@ResponseBody
	public RepSimpleMessageDTO getPhoneAuthcode(String phone,HttpServletRequest request){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		//判断手机号是不是为福建手机号
		String a = PhoneUtils.getPhoneProv(phone);
		if(!PhoneUtils.getPhoneProv(phone).equals("福建")){
			rep.setCode(0);
			rep.setMes("贷款手机只能是福建省号码");
			return rep;
		}
		SmsAuthcodeSource smsAuthcodeSource = SmsAuthcodeSource.PC_ADDORDERFORM;
		if(userService.sendAuthcode(phone, smsAuthcodeSource)){
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("验证码发送失败");
		return rep;
	}
	/**
	 * 提交订单
	 * @param id
	 * @return
	 */
	@RequestMapping("addOrderform")
	@ResponseBody
	public RepSimpleMessageDTO pcAddOrderform(@Valid ReqPcOrderformDTO dto
			,BindingResult result){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		//判断验证码是否有效
		int v = userService.validateSmsAuthcode(dto.getTelephone(),
				dto.getPhoneAuthcode(), SmsAuthcodeSource.PC_ADDORDERFORM);
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
		Product product = productService.findById(dto.getProductId());
		if(product == null){
			rep.setCode(0);
			rep.setMes("该产品不存在");
			return rep;
		}
		Orderform orderform = new Orderform();
		orderform = BeanMapper.map(dto,Orderform.class );
		orderform.setSource(OrderSource.PC);
		orderform.setCreateTime(new Date());
		orderform.setProduct(product);
		orderform.setProductInfo(product.getInfo());
		orderform.setStatus(OrderformStatus.UNCONTACTED);
		orderform.setMoney(dto.getMoney().multiply(new BigDecimal(10000) ));
		orderform.setActualMoney(new BigDecimal(0));
		orderform.setLoanInsterestRate(product.getInfo().getLoanRate());
		orderform.setOldProduct(product);
		orderform.setOldLoanTime(dto.getLoanTime());
		//订单号
		String name = "官网订单";
		OrderSerial orderSerial = orderSerialService.findOrderSerialByName(name);
		if(orderSerial == null){
			OrderSerial newOrderSerial = new OrderSerial();
			newOrderSerial.setName(name);
			newOrderSerial.setSerial(1);
			orderSerialService.addOrEditOrderSerial(newOrderSerial);
			String orderno = "G"+StringUtil.toStringZeroByInteger(1, 6);
			orderform.setOrderNo(orderno);
		}else{
			String orderno = "G"+ StringUtil.toStringZeroByInteger(orderSerial.getSerial()+1, 6);
			orderSerial.setSerial(orderSerial.getSerial()+1);
			orderSerialService.addOrEditOrderSerial(orderSerial);
			orderform.setOrderNo(orderno);
		}
		if(orderformService.addOrderform(orderform)){
			//成功操作
			//对应的产品申请数量加一
			product.setApplyTimes(product.getApplyTimes() +1);
			productService.editProduct(product);
			//发送短信
			SysInfo sysInfo = sysInfoService.getSysInfo(1l);
			if(sysInfo!=null){
				String mobile = sysInfo.getNotifyPhone();
				if(mobile!=null&&mobile.length()!=0){
					String orderTime = DateUtil.formatDateByFormat(new Date(), "yyyy年MM月dd日 HH时mm分ss秒");
					//String content = orderTime+"有新的贷款订单下单，请登录后台查看。";
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
							+ ",贷款利率："+loanInsterestRateStr+",备注："+orderform.getRemark();
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
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("提交失败");
		return rep ;
	}
}

package com.zzy.brd.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.CheckModel;
import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.Orderform;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.entity.WeixinPost.NoticeType;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.mobile.web.dto.req.weixinpost.RepWeixinQrcodeDTO;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.weixin.QrcodeData;
import com.zzy.brd.util.weixin.QrcodePostData;
import com.zzy.brd.util.weixin.WeixinCommonUtil;
import com.zzy.brd.util.weixin.EncoderHandler;
import com.zzy.brd.util.weixin.NewInfoTemplate;
import com.zzy.brd.util.weixin.TemplateData;
import com.zzy.brd.util.weixin.Token;

 
 

@Service
public class WeixinTemplateService {
	private static Logger log = LoggerFactory.getLogger(WeixinTemplateService.class);
    public final static String weixin_url = "http://www.baidu.com";
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private WeixinPostService weixinPostService;
    
*/
/**
 * temp
 * @return int 返回0就是发送成功 不是零返回错误代码。
 *//*

    public int sendTemplateMessage(NewInfoTemplate temp) {
    	String appid = null;
    	String appsecret = null;
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		if(sysInfo!=null){
			appid = sysInfo.getAppid();
			appsecret = sysInfo.getAppsecret();
		}
    	int result = 0;
    	String templateTypeFlag = temp.getFlag();
        Token token = WeixinCommonUtil.getToken(appid,appsecret);
        String access_token = token.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;//请求地址
        String jsonString = JSONObject.toJSONString(temp).toString().replace("day", "Day");//请求的内容
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", jsonString);//访问请求的地址并返回的json格式 数据。
        
        if (null != jsonObject) {  
             if (0 != jsonObject.getInteger("errcode")) {  
                 result = jsonObject.getInteger("errcode");
                  log.error("发送失败用户openid:{ }",temp.getTouser());
                  log.error("发送失败；错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));  
             }  
         }
        if(result==0){
        	log.info("发送成功，模板类型："+templateTypeFlag);
        }
		return result;
 }
    
    */
/**
     * 贷款提交后的通知
     * @param openId
     * @return NewInfoTemplate
     *//*

    public NewInfoTemplate getLoadSubmit(Orderform orderform,HttpServletRequest request){
    	User user = orderform.getUser();
    	NewInfoTemplate temp = new NewInfoTemplate();
    	if(user!=null){
    		String openId = user.getWeixinUser().getOpenid();
    		WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.LOADSUBMIT);
        	String url = url(request);
        	url = url+"/weixin/loan/"+orderform.getId().toString()+"/detail";
        	temp.setTouser(openId);//weixin_user获得
        	temp.setTemplate_id(weixinPost.getTemplateId());//模板id
        	temp.setUrl(url);//跳转地址
        	temp.setTopcolor("#173177");
        	temp.setFlag("贷款提交后的通知");
        	Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
        	TemplateData first = new TemplateData();  
        	first.setColor("#173177");  
        	first.setValue(weixinPost.getFirst());  //开头
        	m.put("first", first);  
        	TemplateData keyword1 = new TemplateData();  
        	keyword1.setColor("#173177");  
        	keyword1.setValue(DateUtil.formatDate2(orderform.getCreateTime()));  //下单时间
        	m.put("keyword1", keyword1);  
        	TemplateData keyword2 = new TemplateData();  
        	keyword2.setColor("#173177");  
        	keyword2.setValue(orderform.getProductInfo().getProductName());  
        	m.put("keyword2", keyword2);  //申请贷款
        	TemplateData remark = new TemplateData();  
        	remark.setColor("#173177");  
        	remark.setValue(weixinPost.getRemark());  //备注
        	m.put("remark", remark);  
        	temp.setData(m); 
    	}
    	return temp;
    }
    
    */
/**
     * 贷款审核成功通知
     * @param openId
     * @return NewInfoTemplate
     *//*

    public NewInfoTemplate getAuditSuccess(Orderform orderform,HttpServletRequest request){
    	User user = orderform.getUser();
    	NewInfoTemplate temp = new NewInfoTemplate();
    	if(user!=null){
    		 WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.AUDITSUCCESS);
    		 String openId = user.getWeixinUser().getOpenid();
         	 String url = url(request);
         	 url = url+"/weixin/loan/"+orderform.getId().toString()+"/detail";
	         temp.setTouser(openId);//weixin_user获得
	         temp.setTemplate_id(weixinPost.getTemplateId());
	         temp.setUrl(url);
	         temp.setTopcolor("#173177");
	         temp.setFlag("贷款审核成功通知");
	         Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
	         TemplateData first = new TemplateData();  
	         first.setColor("#173177");  
	         first.setValue(weixinPost.getFirst());  
	         m.put("first", first);  
	         TemplateData keyword1 = new TemplateData();  
	         keyword1.setColor("#173177");  
	         keyword1.setValue(DateUtil.formatDate2(orderform.getCreateTime()));  
	         m.put("keyword1", keyword1);  
	         TemplateData keyword2 = new TemplateData();  
	         keyword2.setColor("#173177");  
	         keyword2.setValue(orderform.getProductInfo().getProductName());  
	         m.put("keyword2", keyword2);  
	         TemplateData remark = new TemplateData();  
	         remark.setColor("#173177");  
	         remark.setValue(weixinPost.getRemark());  
	         m.put("remark", remark);  
	         temp.setData(m); 
    	}
         return temp;
    }
    */
/**
     * 贷款审核失败通知
     * @param openId
     * @return NewInfoTemplate
     *//*

    public NewInfoTemplate getAuditFailure(Orderform orderform,HttpServletRequest request){
    	User user = orderform.getUser();
    	NewInfoTemplate temp = new NewInfoTemplate();
    	if(user!=null){
    		WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.AUDITFAILURE);
    		String openId = user.getWeixinUser().getOpenid();
         	String url = url(request);
         	url = url+"/weixin/loan/"+orderform.getId().toString()+"/detail";
	        temp.setTouser(openId);//weixin_user获得
	        temp.setTemplate_id(weixinPost.getTemplateId());
	        temp.setUrl(url);
	        temp.setTopcolor("#173177");
	        temp.setFlag("贷款审核失败通知");
	    	Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
	    	TemplateData first = new TemplateData();  
	    	first.setColor("#173177");  
	    	first.setValue(weixinPost.getFirst());  
	    	m.put("first", first);  
	    	TemplateData keyword1 = new TemplateData();  
	    	keyword1.setColor("#173177");  
	    	keyword1.setValue(orderform.getProductInfo().getProductName());  
	    	m.put("keyword1", keyword1);  
	    	TemplateData keyword2 = new TemplateData();  
	    	keyword2.setColor("#173177");  
	    	keyword2.setValue(orderform.getInvalidReason());  
	    	m.put("keyword2", keyword2);  
	    	TemplateData remark = new TemplateData();  
	    	remark.setColor("#173177");  
	    	remark.setValue(weixinPost.getRemark());  
	    	m.put("remark", remark);  
	    	temp.setData(m); 
    	}
    	return temp;
    }

    */
/**
     * 放款成功的通知
     * @param openId
     * @return NewInfoTemplate
     *//*

    public NewInfoTemplate getLoadSuccess(Orderform orderform,HttpServletRequest request){
    	User user = orderform.getUser();
    	NewInfoTemplate temp = new NewInfoTemplate();
    	if(user!=null){
    		 WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.LOADSUCCESS);
    	     String openId = user.getWeixinUser().getOpenid();
         	 String url = url(request);
         	 url = url+"/weixin/loan/"+orderform.getId().toString()+"/detail";
	         temp.setTouser(openId);//weixin_user获得
	         temp.setTemplate_id(weixinPost.getTemplateId());
	         temp.setUrl(url);
	         temp.setTopcolor("#173177");
	         temp.setFlag("放款成功的通知");
	         Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
	         TemplateData first = new TemplateData();  
	         first.setColor("#173177");  
	         first.setValue(weixinPost.getFirst());  
	         m.put("first", first);  
	         TemplateData keyword1 = new TemplateData();  
	         keyword1.setColor("#173177");  
	         keyword1.setValue(orderform.getActualMoney()+"元");  
	         m.put("keyword1", keyword1);  
	         TemplateData keyword2 = new TemplateData();  
	         keyword2.setColor("#173177");  
	         keyword2.setValue(DateUtil.formatDate2(new Date()));  //没有合适的模板  关键词错了 但是数据没错
	         m.put("keyword2", keyword2);  
	         TemplateData remark = new TemplateData();  
	         remark.setColor("#173177");  
	         remark.setValue(weixinPost.getRemark());  
	         m.put("remark", remark);  
	         temp.setData(m); 
         }
         return temp;
    }

    */
/**
     * 佣金生成通知列表
     * @param brokerageApply
     * @param request
     * @return
     *//*

    public boolean getBrokerageCreateList(BrokerageApply brokerageApply ,HttpServletRequest request){
    	int res1= 0 ;
    	int res2= 0 ;
    	int res3= 0 ;
    	int res4= 0 ;
    	User user = brokerageApply.getUser();
    	User father = brokerageApply.getOrderform().getOldParent();
    	User saleman = brokerageApply.getOrderform().getOldSalesman();
    	User seller = brokerageApply.getOrderform().getOldBussiness();
    	WeixinUser wxuser = user.getWeixinUser();
    	if(wxuser!=null){
    		if(brokerageApply.getSelfBrokerage().compareTo(new BigDecimal(0)) >0){
    			NewInfoTemplate selfTemplate = getBrokerageCreate(brokerageApply,request,1);
            	res1 = sendTemplateMessage(selfTemplate);
    		}
        	
    	}
    	if(father!=null){
    		WeixinUser wxfather = user.getWeixinUser();
    		if(wxfather!=null){
    			if(brokerageApply.getFatherBrokerage().compareTo(new BigDecimal(0))>0){
	    			NewInfoTemplate fatherTemplate = getBrokerageCreate(brokerageApply,request,2);
	    			res2 = sendTemplateMessage(fatherTemplate);
    			}
    		}
    	}
    	if(saleman!=null){
    		WeixinUser wxsaleman = user.getWeixinUser();
    		if(wxsaleman!=null){
    			if(brokerageApply.getSalesmanBrokerage().compareTo(new BigDecimal(0))>0){
		    		NewInfoTemplate salemanTemplate = getBrokerageCreate(brokerageApply,request,3);
		    		res3 = sendTemplateMessage(salemanTemplate);
    			}
    		}
    	}
    	if(seller!=null){
    		WeixinUser wxseller = user.getWeixinUser();
    		if(wxseller!=null){
    			if(brokerageApply.getBusinessBrokerage().compareTo(new BigDecimal(0))>0){
		    		NewInfoTemplate sellerTemplate = getBrokerageCreate(brokerageApply,request,4);
		    		res4 = sendTemplateMessage(sellerTemplate);
    			}
    		}
    	}
    	if(res1+res2+res3+res4>0){
    		return false;
    	}else{
    		return true;
    	}
    }
    */
/**
     * 佣金放款通知列表
     * @param brokerageApply
     * @param request
     * @return
     *//*

    public boolean getBrokerageLoadList(BrokerageApply brokerageApply ,HttpServletRequest request){
    	int res1= 0 ;
    	int res2= 0 ;
    	int res3= 0 ;
    	int res4= 0 ;
    	User user = brokerageApply.getUser();
    	User father =brokerageApply.getOrderform().getOldParent();
    	User saleman = brokerageApply.getOrderform().getOldSalesman();
    	User seller = brokerageApply.getOrderform().getOldBussiness();
    	WeixinUser wxuser = user.getWeixinUser();
    	if(wxuser!=null){
    		if(brokerageApply.getSelfSubmitBrokerage().compareTo(new BigDecimal(0))>0){
	        	NewInfoTemplate selfTemplate = getBrokerageLoad(brokerageApply,request,1);
	        	res1 = sendTemplateMessage(selfTemplate);
    		}
    	}
    	if(father!=null){
    		WeixinUser wxfather = user.getWeixinUser();
    		if(wxfather!=null){
    			if(brokerageApply.getFatherSubmitBrokerage().compareTo(new BigDecimal(0))>0){
	    			NewInfoTemplate fatherTemplate = getBrokerageLoad(brokerageApply,request,2);
	    			res2 = sendTemplateMessage(fatherTemplate);
    			}
    		}
    	}
    	if(saleman!=null){
    		WeixinUser wxsaleman = user.getWeixinUser();
    		if(wxsaleman!=null){
    			if(brokerageApply.getSalesmanSubmitBrokerage().compareTo(new BigDecimal(0))>0){
		    		NewInfoTemplate salemanTemplate = getBrokerageLoad(brokerageApply,request,3);
		    		res3 = sendTemplateMessage(salemanTemplate);
    			}
    		}
    	}
    	if(seller!=null){
    		WeixinUser wxseller = user.getWeixinUser();
    		if(wxseller!=null){
    			if(brokerageApply.getBusinessSubmitBrokerage().compareTo(new BigDecimal(0))>0){
	    			NewInfoTemplate sellerTemplate = getBrokerageLoad(brokerageApply,request,4);
		    		res4 = sendTemplateMessage(sellerTemplate);
    			}
    		}
    	}
    	if(res1+res2+res3+res4>0){
    		return false;
    	}else{
    		return true;
    	}
    }
    
    */
/**
     * 佣金生成通知
     * @param openId
     * @return NewInfoTemplate
     *//*

    public NewInfoTemplate getBrokerageCreate(BrokerageApply brokerageApply ,HttpServletRequest request,int userType){
    	User user = brokerageApply.getUser();
    	NewInfoTemplate temp = new NewInfoTemplate();
    	if(user!=null){
	   		 WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGECREATE);
		     String openId = null;
		     String money = null;
		     String checkTime = null;
		     String flag = null;
		     Long userId = null;
		     switch (userType) {
		        case 1://自己
		     		openId = user.getWeixinUser().getOpenid();
		     		money = brokerageApply.getSelfBrokerage()+"元";
		     		checkTime = DateUtil.formatDate2(brokerageApply.getCheckpassTime());
		     		flag = "佣金生成通知--提单人";
		     		userId = user.getId();
		     		break;
		        case 2://师傅
		        	if(brokerageApply.getOrderform().getOldParent()!=null){
		        		if(brokerageApply.getOrderform().getOldParent().getWeixinUser()!=null){
		        			openId = brokerageApply.getOrderform().getOldParent().getWeixinUser().getOpenid();
				        	money = brokerageApply.getFatherBrokerage()+"元";
				        	checkTime = DateUtil.formatDate2(brokerageApply.getCheckpassTime());
				        	flag = "佣金生成通知--师傅";
				        	userId =  brokerageApply.getOrderform().getOldParent().getId();
		        		}
		        	}
		        	break;
		        case 3://业务员
		        	if(brokerageApply.getOrderform().getOldSalesman()!=null){
		        		if(brokerageApply.getOrderform().getOldSalesman().getWeixinUser()!= null){
			        		openId = brokerageApply.getOrderform().getOldSalesman().getWeixinUser().getOpenid();
				        	money = brokerageApply.getSalesmanBrokerage()+"元";
				        	checkTime = DateUtil.formatDate2(brokerageApply.getCheckpassTime());
				        	flag = "佣金生成通知--业务员";
				        	userId =  brokerageApply.getOrderform().getOldSalesman().getId();
		        		}
		        	}
		        	break;
		        case 4://商家
		        	if(brokerageApply.getOrderform().getOldBussiness()!=null){
		        		if(brokerageApply.getOrderform().getOldBussiness().getWeixinUser()!=null){
		        			openId = brokerageApply.getOrderform().getOldBussiness().getWeixinUser().getOpenid();
				        	money = brokerageApply.getBusinessBrokerage()+"元";
				        	checkTime = DateUtil.formatDate2(brokerageApply.getCheckpassTime());
				        	flag = "佣金生成通知--商家";
				        	userId =  brokerageApply.getOrderform().getOldBussiness().getId();
		        		}
		        	}
		        	
		        	break;
			 }
	     	 String url = url(request);
	     	 url = url+"/weixin/brokerageDistribution/toBrokerageDistributionByUserId/"+userId;
	         temp.setTouser(openId);//weixin_user获得
	         temp.setTemplate_id(weixinPost.getTemplateId());
	         temp.setUrl(url);
	         temp.setTopcolor("#173177");
	         temp.setFlag(flag);
	         
	         Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
	         TemplateData first = new TemplateData();  
	         first.setColor("#173177");  
	         first.setValue(weixinPost.getFirst());  
	         m.put("first", first);  
	         TemplateData keyword1 = new TemplateData();  
	         keyword1.setColor("#173177");  
	         keyword1.setValue(money);  
	         m.put("keyword1", keyword1);  
	         TemplateData keyword2 = new TemplateData();  
	         keyword2.setColor("#173177");  
	         keyword2.setValue(money);  
	         m.put("keyword2", keyword2);  
	         TemplateData remark = new TemplateData();  
	         remark.setColor("#173177");  
	         remark.setValue(weixinPost.getRemark());  
	         m.put("remark", remark);  
	         temp.setData(m); 
         }
         return temp;
    }
    */
/**
     * 佣金放款通知
     * @param openId
     * @return NewInfoTemplate
     *//*

    public NewInfoTemplate getBrokerageLoad(BrokerageApply brokerageApply ,HttpServletRequest request,int userType){
    	User user = brokerageApply.getUser();
    	NewInfoTemplate temp = new NewInfoTemplate();
    	if(user!=null){
    		WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGELOAD);
    		String openId = null;
    		String money = null;
    		String checkTime = null;
    		String flag = null;
    		Long userId = null;
    		switch (userType) {
    		case 1://自己
    			openId = user.getWeixinUser().getOpenid();
    			money = brokerageApply.getSelfSubmitBrokerage()+"元";
    			checkTime = DateUtil.formatDate2(new Date());
    			flag = "佣金放款通知--提单人";
    			userId = user.getId();
    			break;
    		case 2://师傅
    			if(brokerageApply.getOrderform().getOldParent()!=null){
    				if(brokerageApply.getOrderform().getOldParent().getWeixinUser()!=null){
    					if(brokerageApply.getOrderform().getOldParent().getWeixinUser().getOpenid()!=null){
    						openId = brokerageApply.getOrderform().getOldParent().getWeixinUser().getOpenid();
    		    			money = brokerageApply.getFatherSubmitBrokerage()+"元";
    		    			checkTime = DateUtil.formatDate2(new Date());
    		    			flag = "佣金放款通知--师傅";
    		    			userId =  brokerageApply.getOrderform().getOldParent().getId();
    					}
    				}
    			}
    			
    			break;
    		case 3://业务员
    			if(brokerageApply.getOrderform().getOldSalesman()!=null){
    				if(brokerageApply.getOrderform().getOldSalesman().getWeixinUser()!=null){
    					if(brokerageApply.getOrderform().getOldSalesman().getWeixinUser().getOpenid()!=null){
    						openId = brokerageApply.getOrderform().getOldSalesman().getWeixinUser().getOpenid();
    		    			money = brokerageApply.getSalesmanSubmitBrokerage()+"元";
    		    			checkTime = DateUtil.formatDate2(new Date());
    		    			flag = "佣金放款通知--业务员";
    		    			userId =  brokerageApply.getOrderform().getOldSalesman().getId();
    					}
    				}
    			}
    			
    			break;
    		case 4://商家
    			if(brokerageApply.getOrderform().getOldBussiness()!=null){
    				if(brokerageApply.getOrderform().getOldBussiness().getWeixinUser()!=null){
    					if(brokerageApply.getOrderform().getOldBussiness().getWeixinUser().getOpenid()!=null){
    						openId = brokerageApply.getOrderform().getOldBussiness().getWeixinUser().getOpenid();
    		    			money = brokerageApply.getBusinessSubmitBrokerage()+"元";
    		    			checkTime = DateUtil.formatDate2(new Date());
    		    			flag = "佣金放款通知--商家";
    		    			userId =  brokerageApply.getOrderform().getOldBussiness().getId();
    					}
    				}
    			}
    			
    			break;
    		}
    		String url = url(request);
    		url = url+"/weixin/brokerageDistribution/toBrokerageDistributionByUserId/"+userId;
    		temp.setTouser(openId);//weixin_user获得
    		temp.setTemplate_id(weixinPost.getTemplateId());
    		temp.setUrl(url);
    		temp.setTopcolor("#173177");
    		temp.setFlag(flag);
    		
    		Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
    		TemplateData first = new TemplateData();  
    		first.setColor("#173177");  
    		first.setValue(weixinPost.getFirst());  
    		m.put("first", first);  
    		TemplateData keyword1 = new TemplateData();  
    		keyword1.setColor("#173177");  
    		keyword1.setValue(money);  
    		m.put("keyword1", keyword1);  
    		TemplateData keyword2 = new TemplateData();  
    		keyword2.setColor("#173177");  
    		keyword2.setValue(checkTime);  
    		m.put("keyword2", keyword2);  
    		TemplateData remark = new TemplateData();  
    		remark.setColor("#173177");  
    		remark.setValue(weixinPost.getRemark());  
    		m.put("remark", remark);  
    		temp.setData(m); 
    	}
    	return temp;
    }

    */
/**
     * 提现到款通知
     * @param openId
     * @return NewInfoTemplate
     *//*

    public NewInfoTemplate getWithdrawArrival(FlowWithdraw flowWithdraw,HttpServletRequest request){
    	User user = flowWithdraw.getUser();
    	NewInfoTemplate temp = new NewInfoTemplate();
    	if(user!=null){
    		WeixinPost weixinPost = weixinPostService.findWeixinPostByType(NoticeType.WITHDRAWARRIVAL);
    	    String openId = user.getWeixinUser().getOpenid();
         	String url = url(request);
         	url = url+"/weixin/withdraw/detail";
	        temp.setTouser(openId);//weixin_user获得
	        temp.setTemplate_id(weixinPost.getTemplateId());
	        temp.setUrl(url);
	        temp.setTopcolor("#173177");
	        temp.setFlag("提现到款通知");
	        Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
	        TemplateData first = new TemplateData();  
	        first.setColor("#173177");  
	        first.setValue(weixinPost.getFirst());  
	        m.put("first", first);  
	        TemplateData keyword1 = new TemplateData();  
	        keyword1.setColor("#173177");  
	        keyword1.setValue(flowWithdraw.getMoney()+"元");  
	        m.put("money", keyword1);  
	        TemplateData keyword2 = new TemplateData();  
	        keyword2.setColor("#173177");  
	        keyword2.setValue(DateUtil.formatDate2(flowWithdraw.getSendDate()));  
	        m.put("timet", keyword2);  
	        TemplateData remark = new TemplateData();  
	        remark.setColor("#173177");  
	        remark.setValue(weixinPost.getRemark());  
	        m.put("remark", remark);  
	        temp.setData(m); 
        }
        return temp;
   }
    
    */
/**
     * 微信开发者验证
     * @param wxAccount
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     *//*

    @Transactional
    public String validate(String wxToken, CheckModel tokenModel){
        String signature = tokenModel.getSignature();
        Long timestamp = tokenModel.getTimestamp();
        Long nonce =tokenModel.getNonce();
        String echostr = tokenModel.getEchostr();
        if(signature!=null&&timestamp!=null&nonce!=null) {
            String[] str = {wxToken, timestamp+"", nonce+""};
            Arrays.sort(str); // 字典序排序
            String bigStr = str[0] + str[1] + str[2];
            // SHA1加密    
            String digest = EncoderHandler.encode("SHA1", bigStr).toLowerCase();
            // 确认请求来至微信
            if (digest.equals(signature)) {
                //最好此处将echostr存起来，以后每次校验消息来源都需要用到
                return echostr;
            }
        }
        return "error";
    }
    */
/**
     * 得到url
     * @param request
     * @return
     *//*

    public String url(HttpServletRequest request){
    	String url=null;
		if (request.getServerPort() != 80) {
			url = "http://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();
					//+ "/wechat/weixinLogin";
		} else {
			url = "http://" + request.getServerName()
					+ request.getContextPath();
					//+ "/wechat/weixinLogin";
		}
		return url;
    }
    
    //组建post请求数据
    public QrcodePostData getQrcodeUrl(String sceneStr){
    	QrcodePostData qrcodePostData = new QrcodePostData();
    	qrcodePostData.setAction_name("QR_LIMIT_STR_SCENE");
    	Map<String,QrcodeData> m = new HashMap<String,QrcodeData>();
    	QrcodeData scene = new QrcodeData();
    	scene.setScene_str(sceneStr);
    	m.put("scene",scene);
    	qrcodePostData.setAction_info(m);
    	return qrcodePostData;
    }
    
  //获取永久二维码url
    public String sendTemplateQrCode(QrcodePostData temp){
    	String appid = null;
    	String appsecret = null;
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		if(sysInfo!=null){
			appid = sysInfo.getAppid();
			appsecret = sysInfo.getAppsecret();
		}
    	String result = null;
        Token token = WeixinCommonUtil.getToken(appid,appsecret);
        String access_token = token.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;//请求地址
        String jsonString = JSONObject.toJSONString(temp).toString();//请求的内容
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", jsonString);//访问请求的地址并返回的json格式 数据。
        
        if (null != jsonObject) {  
        	System.out.println(jsonObject);
        	RepWeixinQrcodeDTO dto = new RepWeixinQrcodeDTO();
        	dto.setTicket(jsonObject.getString("ticket"));
        	dto.setExpire_seconds(jsonObject.getString("expire_seconds"));
        	dto.setUrl(jsonObject.getString("url"));
        	result = jsonObject.getString("url");
        	System.out.println("ticket:"+jsonObject.getString("ticket"));
        	System.out.println("expire_seconds:"+jsonObject.getString("expire_seconds"));
        	System.out.println("url:"+jsonObject.getString("url"));
        	
        }
		return result;
    }
}*/

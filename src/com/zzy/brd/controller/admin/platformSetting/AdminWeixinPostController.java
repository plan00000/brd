package com.zzy.brd.controller.admin.platformSetting;
import java.text.ParseException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.entity.WeixinPost.NoticeType;
import com.zzy.brd.mobile.web.dto.req.weixinpost.ReqWeixinPostDTO;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.WeixinPostService;

/**
 * @author:CSY
 *    2016年10月12日
 **/
@Controller
@RequestMapping(value="admin/weixinPost")
public class AdminWeixinPostController {
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private WeixinPostService weixinPostService;
	/***
	 * 转到基础设置
	 * @throws ParseException 
	 */
	@RequestMapping(value="weixinPost")
	public String baseSetting(Model model){
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		WeixinPost weixinPost0 = weixinPostService.findWeixinPostByType(NoticeType.LOADSUBMIT);
		WeixinPost weixinPost1 = weixinPostService.findWeixinPostByType(NoticeType.AUDITSUCCESS);
		WeixinPost weixinPost2 = weixinPostService.findWeixinPostByType(NoticeType.AUDITFAILURE);
		WeixinPost weixinPost3 = weixinPostService.findWeixinPostByType(NoticeType.LOADSUCCESS);
		WeixinPost weixinPost4 = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGECREATE);
		WeixinPost weixinPost5 = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGELOAD);
		WeixinPost weixinPost6 = weixinPostService.findWeixinPostByType(NoticeType.WITHDRAWARRIVAL);
		
		model.addAttribute("weixinPost0", weixinPost0);
		model.addAttribute("weixinPost1", weixinPost1);
		model.addAttribute("weixinPost2", weixinPost2);
		model.addAttribute("weixinPost3", weixinPost3);
		model.addAttribute("weixinPost4", weixinPost4);
		model.addAttribute("weixinPost5", weixinPost5);
		model.addAttribute("weixinPost6", weixinPost6);
		model.addAttribute("sysInfo", sysInfo);
		return "admin/platformSetting/weixinPost";
		
	}
	
	/**
	 * 修改基础设置
	 * @param departname
	 * @param parentDeparmentId
	 * @param departmentlevel
	 * @return
	 */
	@RequestMapping(value="editWeixinPost",method=RequestMethod.POST)
	   @ResponseBody
	   public RepSimpleMessageDTO editWeixinPost(@Valid ReqWeixinPostDTO req ){
		   RepSimpleMessageDTO res = new RepSimpleMessageDTO() ;
		   try {
			   SysInfo sysInfo = new SysInfo();
			   if(req.getSysInfoId()!=null){
				   sysInfo = sysInfoService.findSysInfoById(req.getSysInfoId());
			   }
			   sysInfo.setAppid(req.getAppid());
			   sysInfo.setAppsecret(req.getAppsecret());
			   
			   WeixinPost weixinPost0 = weixinPostService.findWeixinPostByType(NoticeType.LOADSUBMIT);
			   if(weixinPost0==null){
				   weixinPost0 = new WeixinPost();
			   }
			   weixinPost0.setNoticeType(NoticeType.LOADSUBMIT);
			   weixinPost0.setTemplateId(req.getTemplateId0());
			   weixinPost0.setRemark(req.getRemark0());
			   weixinPost0.setFirst(req.getFirst0());
			   if(req.getState0()==0){
				   weixinPost0.setState(WeixinPost.State.ON);
			   }else{
				   weixinPost0.setState(WeixinPost.State.OFF);
			   }
			   
			   WeixinPost weixinPost1 = weixinPostService.findWeixinPostByType(NoticeType.AUDITSUCCESS);
			   if(weixinPost1==null){
				   weixinPost1 = new WeixinPost();
			   }
			   weixinPost1.setNoticeType(NoticeType.AUDITSUCCESS);
			   weixinPost1.setTemplateId(req.getTemplateId1());
			   weixinPost1.setRemark(req.getRemark1());
			   weixinPost1.setFirst(req.getFirst1());
			   if(req.getState1()==0){
				   weixinPost1.setState(WeixinPost.State.ON);
			   }else{
				   weixinPost1.setState(WeixinPost.State.OFF);
			   }
			   
			   WeixinPost weixinPost2 = weixinPostService.findWeixinPostByType(NoticeType.AUDITFAILURE);
			   if(weixinPost2==null){
				   weixinPost2 = new WeixinPost();
			   }
			   weixinPost2.setNoticeType(NoticeType.AUDITFAILURE);
			   weixinPost2.setTemplateId(req.getTemplateId2());
			   weixinPost2.setRemark(req.getRemark2());
			   weixinPost2.setFirst(req.getFirst2());
			   if(req.getState2()==0){
				   weixinPost2.setState(WeixinPost.State.ON);
			   }else{
				   weixinPost2.setState(WeixinPost.State.OFF);
			   }
			   
			   WeixinPost weixinPost3 = weixinPostService.findWeixinPostByType(NoticeType.LOADSUCCESS);
			   if(weixinPost3==null){
				   weixinPost3 = new WeixinPost();
			   }
			   weixinPost3.setNoticeType(NoticeType.LOADSUCCESS);
			   weixinPost3.setTemplateId(req.getTemplateId3());
			   weixinPost3.setRemark(req.getRemark3());
			   weixinPost3.setFirst(req.getFirst3());
			   if(req.getState3()==0){
				   weixinPost3.setState(WeixinPost.State.ON);
			   }else{
				   weixinPost3.setState(WeixinPost.State.OFF);
			   }
			   
			   WeixinPost weixinPost4 = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGECREATE);
			   if(weixinPost4==null){
				   weixinPost4 = new WeixinPost();
			   }
			   weixinPost4.setNoticeType(NoticeType.BROKERAGECREATE);
			   weixinPost4.setTemplateId(req.getTemplateId4());
			   weixinPost4.setRemark(req.getRemark4());
			   weixinPost4.setFirst(req.getFirst4());
			   if(req.getState4()==0){
				   weixinPost4.setState(WeixinPost.State.ON);
			   }else{
				   weixinPost4.setState(WeixinPost.State.OFF);
			   }
			   
			   WeixinPost weixinPost5 = weixinPostService.findWeixinPostByType(NoticeType.BROKERAGELOAD);
			   if(weixinPost5==null){
				   weixinPost5 = new WeixinPost();
			   }
			   weixinPost5.setNoticeType(NoticeType.BROKERAGELOAD);
			   weixinPost5.setTemplateId(req.getTemplateId5());
			   weixinPost5.setRemark(req.getRemark5());
			   weixinPost5.setFirst(req.getFirst5());
			   if(req.getState5()==0){
				   weixinPost5.setState(WeixinPost.State.ON);
			   }else{
				   weixinPost5.setState(WeixinPost.State.OFF);
			   }
			   
			   WeixinPost weixinPost6 = weixinPostService.findWeixinPostByType(NoticeType.WITHDRAWARRIVAL);
			   if(weixinPost6==null){
				   weixinPost6 = new WeixinPost();
			   }
			   weixinPost6.setNoticeType(NoticeType.WITHDRAWARRIVAL);
			   weixinPost6.setTemplateId(req.getTemplateId6());
			   weixinPost6.setRemark(req.getRemark6());
			   weixinPost6.setFirst(req.getFirst6());
			   if(req.getState6()==0){
				   weixinPost6.setState(WeixinPost.State.ON);
			   }else{
				   weixinPost6.setState(WeixinPost.State.OFF);
			   }
			   
			   if(sysInfoService.editSysInfo(sysInfo)&&
				  weixinPostService.editWeixinPost(weixinPost0)&&
				  weixinPostService.editWeixinPost(weixinPost1)&&
				  weixinPostService.editWeixinPost(weixinPost2)&&
				  weixinPostService.editWeixinPost(weixinPost3)&&
				  weixinPostService.editWeixinPost(weixinPost4)&&
				  weixinPostService.editWeixinPost(weixinPost5)&&
				  weixinPostService.editWeixinPost(weixinPost6)
					   ){
				   res.setCode(0);
				   res.setMes("设置成功");
			   }else{
				   res.setCode(1);
				   res.setMes("设置失败");
			   }
		   } catch (Exception e) {
				// TODO: handle exception
				   e.printStackTrace();
				   res.setCode(1);
				   res.setMes("设置失败！");
				}
		   return res;
	   }	
	
}

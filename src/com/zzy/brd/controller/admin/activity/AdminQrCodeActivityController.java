package com.zzy.brd.controller.admin.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.zzy.brd.entity.*;
import com.zzy.brd.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.controller.admin.user.AdminUserController;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.util.QRcode.QRcodeUtils;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.weixin.QrcodePostData;
import com.zzy.brd.util.weixin.WeixinCommonUtil;

/**
 * 二维码活动
 * @author lzh 
 *
 */
@Controller
@RequestMapping("/admin/qrCodeActivity")
public class AdminQrCodeActivityController {
	private static final Logger log = LoggerFactory.getLogger(AdminQrCodeActivityController.class);
	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private WeixinTemplateService weixinTemplateService;

	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	private WeixinScanRecordService weixinScanRecordService;
	/**
	 * 获取列表
	 * @param pageNumber
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String getList(@RequestParam(value="page",required=true,defaultValue="1") int pageNumber
			,HttpServletRequest request, Model model){
		Page<QrcodeActivity> qrcodeActivitys = qrcodeActivityService.getQrCodeActivityList(pageNumber, Constant.PAGE_SIZE);
		model.addAttribute("qrcodeActivitys", qrcodeActivitys);
		return "admin/activity/qrcodeActivitylist";
	}
	/**
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd")
	public String toAddPage(Model model){
		model.addAttribute("state", 0);
		return "admin/activity/addQrcodeActivity";
	}
	/**
	 * 跳转到编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping("toEdit/{id}")
	public String toEditPage(@PathVariable("id")long id,Model model){
		QrcodeActivity qrcodeActivity = qrcodeActivityService.findById(id);
		if(qrcodeActivity == null){
			return "error/product_not_valid";
		}
		model.addAttribute("qrcodeActivity", qrcodeActivity);
		model.addAttribute("state", 1);
		return "admin/activity/addQrcodeActivity";
	}
	/**
	 * 跳转到二维码详细页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("toDetail/{id}")
	public String toDetailPage(@PathVariable("id") long id, Model model) {
		QrcodeActivity qrcodeActivity = qrcodeActivityService.findById(id);
		if(qrcodeActivity == null){
			return "error/product_not_valid";
		}
		model.addAttribute("qrcodeActivity", qrcodeActivity);
		return "admin/activity/showQrcodeActivity";
	}

	/**
	 * 跳转到搜索用户页面
	 * @return
	 */
	@RequestMapping("toSearchUser")
	public String toSearchUser() {
		return "admin/activity/searchUserQrcode";
	}

	@RequestMapping("searchWechatUser")
	@ResponseBody
	public Page<WeixinUser> searchUser(@RequestParam(value="name") String name) {
		Page<WeixinUser> page = this.weixinUserService.findUserByNickname(name, 1, 100);
		for (WeixinUser user : page.getContent()) {
			user.setUser(null);
		}

		return page;
	}

	@RequestMapping("getQrcodeByIds")
	@ResponseBody
	public List<QrcodeActivity> getQrcodeByIds(@RequestParam(value="ids") String idsJSON) {
		List<Long> ids = JSON.parseArray(idsJSON, Long.class);
		return this.qrcodeActivityService.findByIds(ids.toArray(new Long[ids.size()]));
	}

	/**
	 * 查询通过该二维码关注的用户列表
	 * @param id
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("listWechatUser")
	@ResponseBody
	public Page<WeixinUser> getUserList(
			@RequestParam(value="id",required=true) int id,
			@RequestParam(value="range",required=true) String range,
			@RequestParam(value="asc", defaultValue = "true") boolean asc,
			@RequestParam(value="page",required=true,defaultValue="1") int pageNumber) {
		Page<WeixinUser> page;
		if (range == null || range.equals("all")){
			page = weixinUserService.findUserByScene(id, pageNumber, 10, asc);
		}
		else {
			page =  weixinUserService.findUserByScene(id, range.equals("sub") ? WeixinUser.SubscribeType.YES : WeixinUser.SubscribeType.NO, pageNumber, 10,  asc);
		}

		for (WeixinUser user : page.getContent()) {
			user.setUser(null);
		}
		return page;
	}

	@RequestMapping("queryScanRecord")
	@ResponseBody
	public Map<String, Object> queryScanRecord(
			@RequestParam(value="id", required = true) int id,
			@RequestParam(value="startTime") String startTime,
			@RequestParam(value="endTime") String endTime) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<WeixinScanRecord> records = weixinScanRecordService.findRecordByDate(id, startTime, endTime);
		Map<String, Object> res = new HashMap<>();
		int scanNum = 0, concerNum = 0;;

		for (WeixinScanRecord r : records) {
			scanNum += r.getScanNum();
			concerNum += r.getConcernNum();
		}
		res.put("scanNum", scanNum);
		res.put("concerNum", concerNum);
		return res;
	}
	/**
	 * 添加二维码
	 * @param name
	 * @return
	 */
	@RequestMapping(value="add",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO add(@RequestParam(value = "name",required = true) String name){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		QrcodeActivity qrcodeActivity = new QrcodeActivity();
		qrcodeActivity.setCreateTime(new Date());
		qrcodeActivity.setName(name);
		qrcodeActivity.setScanNum(0);
		if(qrcodeActivityService.addAndEdit(qrcodeActivity)){
			//得到二维码的url
			QrcodePostData qrcodePostData = weixinTemplateService.getQrcodeUrl(qrcodeActivity.getId()+"");
			String qrcodeUrl = weixinTemplateService.sendTemplateQrCode(qrcodePostData);
			if(qrcodeUrl==null){
				rep.setCode(0);
				rep.setMes("生成场景二维码失败,请从新生成");
				return rep;
			}
			//生成二维码
			String filePath ="";
			String prodir = ConfigSetting.proPathByType(PathType.QRCODEACTIVITY);
			String filename = FileUtil.hashPath(prodir, "QrcodeActivity"+qrcodeActivity.getId()+
					".png", qrcodeActivity.getId()+"");
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
//			String qrCodeUrl = "http://"+ConfigSetting.File_Server_Ip+"/weixin/qrCodeActivity/countClickTimes"+qrcodeActivity.getId();
			String qrCodeUrl =qrcodeUrl;
			try{
				QRcodeUtils.createQRcodeImageForUser(qrCodeUrl, 1600, 1600, logoFile, destFile);
			}catch(Exception e){
				System.out.println("生成二维码错误:"+e.getMessage());
			}
			filePath = destFile.getAbsolutePath();
			filePath = filePath.substring(ConfigSetting.Pro_Dir.length());
			filePath = FileUtil.moveFileToPro(filePath);
			qrcodeActivity.setQrcode(filePath);
			if(qrcodeActivityService.addAndEdit(qrcodeActivity)){
				rep.setCode(1);
				rep.setMes("success");
				return rep;
			}
		}
		rep.setCode(0);
		rep.setMes("添加失败");
		return rep;
	}
	/**
	 * 编辑
	 * @param name
	 * @param id
	 * @return
	 */
	@RequestMapping(value="edit",method = RequestMethod.POST)
	@ResponseBody
	public RepSimpleMessageDTO edit(@RequestParam(value = "name",required = true) String name
			,@RequestParam(value = "id",required = true) long id){
		RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
		QrcodeActivity qrcodeActivity = qrcodeActivityService.findById(id);
		if(qrcodeActivity ==null){
			rep.setCode(0);
			rep.setMes("不存在该二维码");
			return rep;
		}
		qrcodeActivity.setName(name);
		if(qrcodeActivityService.addAndEdit(qrcodeActivity)){
			rep.setCode(1);
			rep.setMes("success");
			return rep;
		}
		rep.setCode(0);
		rep.setMes("编辑失败");
		return rep;
	}
	
	/**
	 * 下载二维码图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value="downLoadCode/{id}")
	public void downLoadCode(@Valid @PathVariable("id") long id,
			ServletRequest request, HttpServletResponse response){
		RepSimpleMessageDTO repdto = new RepSimpleMessageDTO();
		QrcodeActivity qrcodeActivity = qrcodeActivityService.findById(id);
		/*String strUrl= ConfigSetting.appfileProUrlByFilePath(coupons.getCouponCode());
		String filePath = strUrl;*/
		String prodir = ConfigSetting.proDir();
		String filePath = (prodir+qrcodeActivity.getQrcode()).replace(File.separator, "/");
		
		log.info("二维码下载地址："+filePath);
		
		String showName = qrcodeActivity.getName()+".png";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(filePath));
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
		
	}
	/**
	 * 统计不同二维码被扫描的次数
	 * @param id
	 */
	/*@RequestMapping(value="countClickTimes/{id}")
	public void countClickTimes(@PathVariable(value = "id") long id){
		QrcodeActivity qrcodeActivity = qrcodeActivityService.findById(id);
		if(qrcodeActivity !=null){
			qrcodeActivity.setScanNum(qrcodeActivity.getScanNum()+1);
			qrcodeActivityService.addAndEdit(qrcodeActivity);
		}
		
	}*/
	
	/*@RequestMapping(value = "getUrl")
	public void getUrl(){
		QrcodePostData qrcodePostData = weixinTemplateService.getQrcodeUrl();
		int result = weixinTemplateService.sendTemplateQrCode(qrcodePostData);
	}*/
}

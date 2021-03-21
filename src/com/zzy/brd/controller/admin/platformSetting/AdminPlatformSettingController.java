package com.zzy.brd.controller.admin.platformSetting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.Servlets;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.dto.rep.admin.platform.RepUserDTO;
import com.zzy.brd.dto.rep.admin.platform.RepUserPageDTO;
import com.zzy.brd.entity.Image;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.Information.InformationType;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.SysInfoService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.image.ImageUtil2;
import com.zzy.brd.util.image.Scalr;
import com.zzy.brd.util.sms.SMSUtil;

/**
 * @author:CSY
 *    2016年10月12日
 **/
@Controller
@RequestMapping(value="admin/platformSetting")
public class AdminPlatformSettingController {
	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private InformationService informationService;
	/***
	 * 转到基础设置
	 * @throws ParseException 
	 */
	@RequestMapping(value="baseSetting")
	public String baseSetting(Model model){
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		
		return "admin/platformSetting/baseSetting";
		
	}
	
	/**
	 * 修改基础设置
	 * @param departname
	 * @param parentDeparmentId
	 * @param departmentlevel
	 * @return
	 */
	@RequestMapping(value="editBaseSetting",method=RequestMethod.POST)
	   @ResponseBody
	   public RepSimpleMessageDTO editBaseSetting(
			   @RequestParam (value="sysInfoId", required=false,defaultValue="0") long sysInfoId,
			   @RequestParam (value="icpNumber" ,required=false,defaultValue="" ) String icpNumber,
			   @RequestParam (value="qq" ,required=false,defaultValue="" ) String qq,
			   @RequestParam (value="picurl" ,required=false,defaultValue="" ) String picurl,
			   @RequestParam (value="picurl1" ,required=false,defaultValue="" ) String picurl1,
			   @RequestParam (value="hotline" ,required=false,defaultValue="" ) String hotline,
			   @RequestParam (value="cooperatePhone" ,required=false,defaultValue="" ) String cooperatePhone,
			   @RequestParam (value="shareNotify" ,required=false,defaultValue="" ) String shareNotify,
			   @RequestParam (value="scrollBall",required=false,defaultValue="" ) String scrollBall
			   ){
		   RepSimpleMessageDTO res = new RepSimpleMessageDTO() ;
		   SysInfo sysInfo = new SysInfo();
		   if(sysInfoId>0){
			   sysInfo = sysInfoService.findSysInfoById(sysInfoId);
			   String qrCodeUrl = sysInfo.getQrCodeUrl();
			   String apprenticeQrCodeUrl = sysInfo.getApprenticeQrCodeUrl();
			   if("".equals(qrCodeUrl)){
				   sysInfo.setQrCodeUrl(picurl);
			   }
			   if("".equals(apprenticeQrCodeUrl)){
				   sysInfo.setApprenticeQrCodeUrl(picurl1);
			   }
			}else{
				if("".equals(picurl)){
					 sysInfo.setQrCodeUrl(picurl);
			   }
				if("".equals(picurl1)){
					 sysInfo.setApprenticeQrCodeUrl(picurl1);
				}
			}
		   if(picurl != null && picurl.length() != 0){
			   sysInfo.setQrCodeUrl(picurl);
		   }
		   if(picurl1 != null && picurl1.length() != 0){
			   sysInfo.setApprenticeQrCodeUrl(picurl1);
			   FileUtil.moveFileToPro(picurl1);
		   }   
		   sysInfo.setIcpNumber(icpNumber);
		   sysInfo.setQq(qq);
		   sysInfo.setHotline(hotline);
		   sysInfo.setCooperatePhone(cooperatePhone);
		   sysInfo.setShareNotify(shareNotify);
		   sysInfo.setScrollBall(scrollBall);
		   if(sysInfoService.editSysInfo(sysInfo)){
			   res.setCode(0);
			   res.setMes("设置成功");
			   return res;
		   }else{
			   res.setCode(1);
			   res.setMes("设置失败");
			   return res;
		   }
	   }	
	
	/**
	 * 跳转到短信通知
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddMessage")
	public String toAddMessage(Model model) {
		return "admin/platformSetting/messageInfo";
	}
	/**
	 * 跳转到短信通知 
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("orderMessage")
	public String orderMessage(Model model) {
		SysInfo sysInfo = sysInfoService.getSysInfo(1l);
		model.addAttribute("sysInfo", sysInfo);
		return "admin/platformSetting/orderMessage";
	}
	
	/**
	 * 修改短信通知 
	 * @param departname
	 * @param parentDeparmentId
	 * @param departmentlevel
	 * @return
	 */
	@RequestMapping(value="editOrderMessage",method=RequestMethod.POST)
	   @ResponseBody
	   public RepSimpleMessageDTO editOrderMessage(
			   @RequestParam (value="sysInfoId", required=false,defaultValue="0") long sysInfoId
			   ,@RequestParam (value="notifyPhone", required=false,defaultValue="") String notifyPhone){
		   RepSimpleMessageDTO res = new RepSimpleMessageDTO() ;
		   if(notifyPhone!=null&&notifyPhone.length()!=0){
			   String str2 = notifyPhone.substring(notifyPhone.length()-1, notifyPhone.length()) ;
			   if(";".equals(str2)){
	 			   res.setCode(1);
				   res.setMes("不能以';'结尾");
				   return res;
			   }
		       String[] arr=notifyPhone.split(";");
		       List<String> mobilenos = Arrays.asList(arr);
		       if(mobilenos.size()>8){
	 			   res.setCode(1);
				   res.setMes("最多可以设置8个手机号。");
				   return res;
		       }
		       for(int i = 0; i < mobilenos.size(); i++){  
		    	   int j = i+1;
		    	   String mobileno = mobilenos.get(i);
		        	if(!isMobileNO(mobileno)){
		 			   res.setCode(1);
					   res.setMes("第"+j+"个电话："+mobileno+"不是手机号");
					   return res;
		        	}
		        }  
	        }
		   SysInfo sysInfo = new SysInfo();
		   if(sysInfoId>0){
			   sysInfo = sysInfoService.findSysInfoById(sysInfoId);
			  }
		   sysInfo.setNotifyPhone(notifyPhone);
		   if(sysInfoService.editSysInfo(sysInfo)){
			   res.setCode(0);
			   res.setMes("设置成功");
			   return res;
		   }else{
			   res.setCode(1);
			   res.setMes("设置失败");
			   return res;
		   }
	   }
	
	/**
	 * 判断是否是手机号
	 * @param value
	 * @return
	 */
	public boolean isMobileNO(String mobiles) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**
	 * 跳转到服务协议
	 * @param model
	 * @return
	 */
	@RequestMapping("toServiceAgreement")
	public String toServiceAgreement(Model model) {
		Information information = new Information();
		Information information1 = informationService.findInformationByType(InformationType.AGREEMENT);
		if(information1!=null){
			information=information1;
		}
		model.addAttribute("information", information);
		return "admin/platformSetting/serviceAgreement";
	}
	
	/**
	 * 添加content信息
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "addServiceAgreement",method = RequestMethod.POST)
	public @ResponseBody String addServiceAgreement(@Valid @ModelAttribute("information")Information information) {
		String content = information.getContent();
		String title = information.getTitle();
		if(title==null){
			title="";
		}
		if("".equals(content)){
			return "2";
		}
		information.setAddDate(DateUtil.getNowDate());
		information.setType(InformationType.AGREEMENT);
		information.setTitle(title);
		information.setStatus(Information.Status.YES);
		information.setSortid(1);
		if (informationService.editInformation(information)) {
			return "1";
		} else {
			return "0";
		}
	}
	
	/**
	 * 获取用户
	 * 
	 * @param id
	 * @param pageNum
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	@ResponseBody
	public RepUserPageDTO getUsers(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "realname", required = false, defaultValue = "") String realname,
			@RequestParam(value = "mobileno", required = false, defaultValue = "") String mobileno,
			ServletRequest request) {

		RepUserPageDTO pageDTO = new RepUserPageDTO();
		List<User> userPage = userService.listAllUsers(username, realname,mobileno);
		Collection<RepUserDTO> resDtos = Collections2.transform(userPage,
				new Function<User, RepUserDTO>() {

					@Override
					public RepUserDTO apply(User user) {
						return new RepUserDTO(user);
					}
				});
		pageDTO.setCurrentPage(1);
		pageDTO.setTotalPage(1);
		pageDTO.setRows(resDtos);
		pageDTO.setTotal(1);
		return pageDTO;
	}
	/**
	 * 发送短信
	 * @param departname
	 * @param parentDeparmentId
	 * @param departmentlevel
	 * @return
	 */
	   @RequestMapping(value="sendMessage",method=RequestMethod.POST)
	   @ResponseBody
	   public RepSimpleMessageDTO sendMessage(
		   @RequestParam (value="mobilenostr", required=false) String mobilenostr
		   ,@RequestParam (value="content", required=false) String content){
		   RepSimpleMessageDTO res = new RepSimpleMessageDTO() ;
	       String[] arr=mobilenostr.split(",");
	       List<String> mobilenos = Arrays.asList(arr);
	       try {
	    	   for (int i = 0; i < mobilenos.size(); i++) {
					SMSUtil.sendMessage(mobilenos.get(i), content);
				} 
	    	   	res.setCode(1);
	    	   	res.setMes("发送成功");
	            }catch (Exception e) {
					res.setCode(0);
					res.setMes("发送失败");
					e.printStackTrace();
				}
		   return res;
	   }
	   
	   /**
		 * 上传收徒二维码图片
		 * 
		 * @param request
		 * @param response
		 * @return json format
		 * @throws IOException 
		 */
	   @RequestMapping(value = "uploadimage", method = RequestMethod.POST)
		 public @ResponseBody Map<String, Object> uploadImage(
					@RequestParam(defaultValue = "0") int type,
					MultipartHttpServletRequest request, HttpServletResponse response) {
				PathType pathType = PathType.value(type);

				Map<String, Object> files = Maps.newHashMap();
				List<Image> list = new LinkedList<>();
				String context = request.getContextPath();
				// 类型错误
				if (pathType == null) {
					Image image = new Image();
					image.setError("非法操作");
					list.add(image);
					files.put("files", list);
					return files;
				}
				Iterator<String> itr = request.getFileNames();
				MultipartFile mpf;
				while (itr.hasNext()) {
					// 获取请求中的文件数据对象
					mpf = request.getFile(itr.next());
					// 验证文件类型
					boolean isimage = false;
					try {
						isimage = FileUtil.isImage(mpf.getInputStream());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (isimage == false) {
						Image image = new Image();
						image.setError("图片格式错误");
						list.add(image);
						files.put("files", list);
						return files;
					}

					// 10M限制
					if (mpf.getSize() > 1024 * 1024 * 1024) {
						Image image = new Image();
						image.setError("图片大小超过10M");
						list.add(image);
						files.put("files", list);
						return files;
					}

					try {
						String tempdir = ConfigSetting.tempPathByType(pathType);
						// 生成临时文件路径
						String newFilePath = FileUtil.hashPath(tempdir, mpf.getOriginalFilename(), "logo");
						File newFile = new File(newFilePath);
						// 存储到路径文件
						mpf.transferTo(newFile);
						BufferedImage buff = ImageIO.read(newFile);
						// 判断图片尺寸
						// 提取出缩略图
						BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile),
								FileUtil.thumbSize, FileUtil.thumbSize);
						String thumbnailFilename = ConfigSetting.thumbPathByPath(
								newFilePath, FileUtil.ThumbSize);
						File thumbnailFile = new File(thumbnailFilename);

						ImageIO.write(thumbnail, "png", thumbnailFile);
						Image image = new Image();
						image.setName(mpf.getOriginalFilename());
						image.setThumbnailFilename(thumbnailFilename
								.substring(ConfigSetting.Temp_Dir.length()));
						image.setNewFilename(newFilePath.substring(ConfigSetting.Temp_Dir.length()));
						image.setContentType(mpf.getContentType());
						image.setSize(mpf.getSize());
						image.setThumbnailSize(thumbnailFile.length());
						image.setUrl("/admin/platformSetting/display?filePath="+image.getNewFilename());
						image.setNewurl("/files/display?filePath="+image.getNewFilename());
						image.setThumbnailUrl("/admin/platformSetting/displayProThumbTemp?filePath="+image.getThumbnailFilename()+"&thumbWidth=32&thumbHeight=32");
						list.add(image);
					} catch (IOException e) {
						
					}
				}
				files.put("files", list);
				return files;
			}
	   
		/**
		 * 显示图片
		 * 
		 * @param request
		 * @param response
		 * @return json format
		 */
		@RequestMapping(value = "display", method = RequestMethod.GET)
		public void handleRequest(@RequestParam String filePath,
				HttpServletResponse response) throws Exception {
			// 判断文件是否存在
			String filePathT =ConfigSetting.Pro_Dir + filePath;
			File file = new File(filePathT);
			if (file.exists()) {
				response.setDateHeader("Expires",
						(new Date()).getTime() + 1000 * 60 * 10);
				response.setHeader("Cache-Control", "public,max-age=600");
				response.setContentLength((int) FileUtils.sizeOf(file));
				response.setContentType("image/jpeg");
				// write the data out
				InputStream is = new FileInputStream(file);
				IOUtils.copy(is, response.getOutputStream());
			} else {
				System.out.println("不存在");
			}
		}
	   
	   
	   
	   
}

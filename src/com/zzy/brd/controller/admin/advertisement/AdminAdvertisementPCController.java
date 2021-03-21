package com.zzy.brd.controller.admin.advertisement;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Maps;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.dao.AdvertisementDao;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Advertisement;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.Advertisement.IsOutUrl;
import com.zzy.brd.entity.Image;
import com.zzy.brd.entity.Advertisement.AdPositionType;
import com.zzy.brd.mobile.util.ShiroUtil;
import com.zzy.brd.service.AdvertisementService;
import com.zzy.brd.service.UserOperLogService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.image.Scalr;
import com.zzy.brd.util.validator.ParameterChecker;

/**
 * @author:xpk
 *    2016年10月18日-上午9:03:41
 **/
@Controller
@RequestMapping(value="admin/advertisement/pc")
public class AdminAdvertisementPCController {
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private AdvertisementDao advertisementDao;
	
	private @Autowired UserOperLogService userOperlogService;
	private @Autowired UserService userService;
	
	 @RequestMapping(value="ad/list")
	 public String list(
			 @RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
			 Model model){
		 Map<String,Object> searchParams = new HashMap<String,Object>();
		 searchParams.put("EQ_source", Advertisement.AdSourceType.PC);
		 //中间大图
		 Advertisement middle = advertisementService.getAdvertisementByPositionAndType(Advertisement.AdPositionType.MIDDLE, Advertisement.AdSourceType.PC);
		 if(middle==null){
			 middle = new Advertisement();
			 middle.setCreateTime(new Date());
			 middle.setIsouturl(IsOutUrl.NO);
			 middle.setPosition(Advertisement.AdPositionType.MIDDLE);
			 middle.setSource(Advertisement.AdSourceType.PC);
			 middle.setPicurl("");
			 advertisementDao.save(middle);
		 }
		 //滚动消息下方
		 Advertisement scorll = advertisementService.getAdvertisementByPositionAndType(Advertisement.AdPositionType.SCROLLBLOW, Advertisement.AdSourceType.PC);
		 if(scorll==null){
			 scorll = new Advertisement();
			 scorll.setCreateTime(new Date());
			 scorll.setIsouturl(IsOutUrl.NO);
			 scorll.setPosition(Advertisement.AdPositionType.SCROLLBLOW);
			 scorll.setSource(Advertisement.AdSourceType.PC);
			 scorll.setPicurl("");
			 advertisementDao.save(scorll);
		 }
		 //左下
		 Advertisement left = advertisementService.getAdvertisementByPositionAndType(Advertisement.AdPositionType.LEFTBOTTOM, Advertisement.AdSourceType.PC);
		 if(left==null){
			 left = new Advertisement();
			 left.setCreateTime(new Date());
			 left.setIsouturl(IsOutUrl.NO);
			 left.setPosition(Advertisement.AdPositionType.LEFTBOTTOM);
			 left.setSource(Advertisement.AdSourceType.PC);
			 left.setPicurl("");
			 advertisementDao.save(left);
		 }
		 //右下
		 Advertisement right = advertisementService.getAdvertisementByPositionAndType(Advertisement.AdPositionType.RIGHTBOTTOM, Advertisement.AdSourceType.PC);
		 if(right==null){
			 right = new Advertisement();
			 right.setCreateTime(new Date());
			 right.setIsouturl(IsOutUrl.NO);
			 right.setPosition(Advertisement.AdPositionType.RIGHTBOTTOM);
			 right.setSource(Advertisement.AdSourceType.PC);
			 right.setPicurl("");
			 advertisementDao.save(right);
		 }
		 
		 
		 Page<Advertisement> page = advertisementService.listAdvertisement(searchParams,pageNumber);
		 model.addAttribute("page", page);
		 return "/admin/advertisement/pc/list";
	 }
	 
	
	 @RequestMapping(value="ad/addAdvertisement",method=RequestMethod.POST)
	 @ResponseBody
	 public RepSimpleMessageDTO addAdvertisement(@RequestParam(required=false)String picurl,Advertisement.AdPositionType position,boolean isouturl,String outurlAddress
			 ,HttpServletRequest request){
		 RepSimpleMessageDTO  res = new RepSimpleMessageDTO();
		 if(isouturl){
			 if(!ParameterChecker.isUrl(outurlAddress)){
				 res.setCode(1);
				 res.setMes("请输入可以访问的正确链接,并且以http://或https://开头");
				 return res;
			 } 
		 }
		 if(!position.equals(AdPositionType.BANNER)){
			 if(advertisementService.getAdvertisementByPosition(position)!=null){
				 res.setCode(1);
				 res.setMes("该位置已经存在无法添加");
				 return res;
			 }	 
		 }
		 
		 List<Advertisement> countList = advertisementService.getAdvertisementByType(Advertisement.AdSourceType.PC);
		 if(countList.size()>=5){
			 res.setCode(1);
			 res.setMes("滚播图最多添加5个");
			 return res;
		 }
		 
		 Advertisement ad  = new Advertisement();
		 ad.setCreateTime(new Date());
		 if(isouturl){
			 ad.setIsouturl(Advertisement.IsOutUrl.YES);
			 ad.setAddress(outurlAddress);
		 }else{
			 ad.setIsouturl(Advertisement.IsOutUrl.NO);
			 
		 }
		 ad.setPicurl(picurl);
		 ad.setPosition(position);
		 ad.setSource(Advertisement.AdSourceType.PC);
		 advertisementDao.save(ad);
		 long userId = ShiroUtil.getUserId(request);
		 User opertor =userService.findById(userId);
		 String content = "添加官网广告";
		 userOperlogService.addOperlog(opertor, content);
		 res.setCode(0);
		 res.setMes("添加成功");	 
		 return res;
	 }
	 
	 @RequestMapping(value="ad/toAddAdvertise")	 
	 public String toAddAdvertise() {
		 
		 return "/admin/advertisement/pc/addAdvertisement";
	 }
	 
	 
	 @RequestMapping(value = "ad/uploadimage", method = RequestMethod.POST)
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
					String newFilePath = FileUtil.hashPath(tempdir,
							mpf.getOriginalFilename(), "admin");
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
					image.setNewFilename(newFilePath
							.substring(ConfigSetting.Temp_Dir.length()));
					image.setContentType(mpf.getContentType());
					image.setSize(mpf.getSize());
					image.setThumbnailSize(thumbnailFile.length());
					image.setUrl(context+"/admin/advertisement/pc/display?filePath="
							+ image.getNewFilename());
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
			String filePathT = ConfigSetting.Temp_Dir + filePath;
			File file = new File(filePathT);
			System.out.println("file:"+file.getPath());
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
	 
	 @RequestMapping(value="ad/delAd",method=RequestMethod.POST)
	 @ResponseBody
	 public RepSimpleMessageDTO delAd(long id,HttpServletRequest request){
		 RepSimpleMessageDTO res = new RepSimpleMessageDTO();
		 Advertisement ad = advertisementDao.findOne(id);
		 advertisementDao.delete(ad);
		 res.setCode(0);
		 res.setMes("删除 成功");
		 long userId = ShiroUtil.getUserId(request);
		 User opertor =userService.findById(userId);
		 String content = "删除官网广告";
		 userOperlogService.addOperlog(opertor, content);
		 return res;
	 }
		
		
	 /**修改广告*/
	 @RequestMapping(value="ad/toEditAd/{advertisementId}")
	 public String toEditAd(@PathVariable long advertisementId,Model model) {
		 Advertisement ad = advertisementDao.findOne(advertisementId);
		 
		 model.addAttribute("advertisement", ad);
		 return "/admin/advertisement/pc/editAdvertisement";
	 }
	 
	 
	 @RequestMapping(value="ad/editAdvertisement",method=RequestMethod.POST)
	 @ResponseBody
	 public RepSimpleMessageDTO editAdvertisement(long id,@RequestParam(required=false)String picurl,Advertisement.AdPositionType position,boolean isouturl,String outurlAddress
			 ,HttpServletRequest request){
		 RepSimpleMessageDTO  res = new RepSimpleMessageDTO();
		 if(isouturl){
			 if(!ParameterChecker.isUrl(outurlAddress)){
				 res.setCode(1);
				 res.setMes("请输入可以访问的正确链接,并且以http://或https://开头");
				 return res;
			 } 
		 }
		 
		 Advertisement ad  = advertisementDao.findOne(id);
		 if(isouturl){
			 ad.setIsouturl(Advertisement.IsOutUrl.YES);
			 ad.setAddress((outurlAddress));
		 }else{
			 ad.setIsouturl(Advertisement.IsOutUrl.NO);
		 }
		 ad.setPicurl(picurl);
		 ad.setPosition(position);
		 ad.setSource(Advertisement.AdSourceType.PC);
		 advertisementDao.save(ad);
		 res.setCode(0);
		 res.setMes("修改成功");	 
		 long userId = ShiroUtil.getUserId(request);
		 User opertor =userService.findById(userId);
		 String content = "修改官网广告";
		 userOperlogService.addOperlog(opertor, content);
		 return res;
	 }
	 
	 
}

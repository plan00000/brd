package com.zzy.brd.controller.file;

/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.Servlets;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.entity.Image;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.image.ImageUtil;
import com.zzy.brd.util.image.ImageUtil2;
import com.zzy.brd.util.image.Scalr;


/**
 * 文件上传
 * 
 * @author lll 2014年12月19日
 */
@Controller
@RequestMapping(value = "/files")
public class FileController {

	private static Logger log = LoggerFactory.getLogger(FileController.class);

	/**
	 * 显示图片(临时文件)
	 * 
	 * @param request
	 * @param response
	 * @return json format
	 */
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public void display(@RequestParam String filePath,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		// 判断文件是否存在
		String filePathT = ConfigSetting.Temp_Dir + filePath;
		File file = new File(filePathT);
		if (file.exists()) {
			boolean isModify = Servlets.checkIfModifiedSince(request, response, file.lastModified());
			if (isModify){
				Servlets.setExpiresHeader((HttpServletResponse) response, 31536000);
				Servlets.setLastModifiedHeader(response, file.lastModified());
				response.setContentType("image/jpeg");
				// write the data out
				InputStream is = new FileInputStream(file);
				IOUtils.copy(is, response.getOutputStream());
			}
		} else {
			log.warn("file not exist");
		}
	}

	/**
	 * 显示图片（正式文件）
	 * 
	 * @param filePath
	 *            原图文件路径/缩略图路劲（缩略图时sizeStr 不能传，否则无法读取到）
	 * @param thumbSize
	 *            空则直接使用路径，否则取 缩略图 ， width、heigh 指定时，该参数无效
	 * @param width
	 *            指定 缩略图宽度（缩略图居中显示，旁边白底）
	 * @param height
	 *            指定 缩略图高度（缩略图居中显示，旁边白底）
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/displayPro", method = RequestMethod.GET)
	public void displayPro(
			@RequestParam String filePath,
			@RequestParam(required = false, defaultValue = "0") Integer thumbSize,
			@RequestParam(required = false, defaultValue = "0") Integer width,
			@RequestParam(required = false, defaultValue = "0") Integer height,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String filePathT = ConfigSetting.Pro_Dir + filePath;

		File originalFile = new File(filePathT);
		File file = null;
		// 指定路径
		if (thumbSize > 0 || (width > 0 && height > 0)) {
			// 非法参数
			if (thumbSize > FileUtil.MAX_SIZE) {
				log.warn("thumbSize error");
				return;
			}
			// 实际缩略图高宽
			int realWidth = Math.max(thumbSize, width);
			int realHeight = Math.max(thumbSize, height);
			// 缩略图目录
			String thumbStr = realWidth + "x" + realHeight;

			String thumbfilePath = ConfigSetting.thumbPathByPath(filePathT,
					thumbStr);
			file = new File(thumbfilePath);
			// 不存在且原图存在 则生成
			if (!file.exists() && originalFile.exists()) {
				// 无白边缩略图
				ImageUtil.createThumbnail(originalFile, file, realWidth,
						realHeight);
			}

			// 是否是白边图
			if (width != null && height != null && width > 0 && height > 0) {
				thumbStr += "_" + width + "_" + height;

				// 带白边路径
				thumbfilePath = ConfigSetting.thumbPathByPath(filePathT,
						thumbStr);
				File fileT = new File(thumbfilePath);
				if (!fileT.exists()) {
					BufferedImage image = ImageIO.read(file);
					// 判断是否需要生成白边图
					if (image.getHeight() != height
							|| image.getWidth() != width) {
						BufferedImage whiteImage = ImageUtil.createColorImage(
								Color.white, width, height);
						file = fileT;
						ImageUtil.createThumbnail(whiteImage, file, width,
								height, image);
					}
				} else {
					file = fileT;
				}
			}
		} else {
			file = originalFile;
		}

		if (file.exists()) {
			boolean isModify = Servlets.checkIfModifiedSince(request, response, file.lastModified());
			if (isModify){
				Servlets.setExpiresHeader((HttpServletResponse) response, 31536000);
				Servlets.setLastModifiedHeader(response, file.lastModified());
				response.setContentType("image/jpeg");
				// write the data out
				InputStream is = new FileInputStream(file);
				IOUtils.copy(is, response.getOutputStream());
			}
		} else {
			log.warn("file not exist");
		}
	}

	/**
	 * 显示图片（正式文件）缩略图
	 * 
	 * @param filePath
	 *            原图文件路径
	 * @param thumbWidth
	 *            指定 缩略图宽度
	 * @param thumbHeight
	 *            指定 缩略图高度
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/displayProThumb", method = RequestMethod.GET)
	public void displayProThumb(String filePath, int thumbWidth,
			int thumbHeight, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String filePathT = ConfigSetting.Pro_Dir + filePath;
		
		File originalFile = new File(filePathT);
		if (!originalFile.exists()) {
			log.warn("file not exist");
		}
		File file = null;
		// 缩略图目录
		String thumbStr = "c2" + thumbWidth + "x" + thumbHeight;

		String thumbfilePath = ConfigSetting.thumbPathByPath(filePathT,
				thumbStr);
		if (StringUtils.isBlank(thumbfilePath)){
			log.warn("file not exist");
			return ;
		}
		file = new File(thumbfilePath);
		if (!file.exists()) {
			ImageUtil2.compressFile2(originalFile, thumbWidth, thumbHeight,
					thumbfilePath);
		}

		if (file.exists()) {
			boolean isModify = Servlets.checkIfModifiedSince(request, response, file.lastModified());
			if (isModify){
				Servlets.setExpiresHeader((HttpServletResponse) response, 31536000);
				Servlets.setLastModifiedHeader(response, file.lastModified());
				response.setContentType("image/jpeg");
				// write the data out
				InputStream is = new FileInputStream(file);
				IOUtils.copy(is, response.getOutputStream());
			}
		} else {
			log.warn("file not exist");
		}
	}
	

	
	/**
	 * 显示图片（临时文件）缩略图
	 * 
	 * @param filePath
	 *            原图文件路径
	 * @param thumbWidth
	 *            指定 缩略图宽度
	 * @param thumbHeight
	 *            指定 缩略图高度
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/displayProThumbTemp", method = RequestMethod.GET)
	public void displayProThumbTemp(String filePath, int thumbWidth,
			int thumbHeight, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String filePathT = ConfigSetting.Pro_Dir + filePath;
		File originalFile = new File(filePathT);
		if (!originalFile.exists()) {
			log.warn("file not exist");
		}
		File file = null;
		// 缩略图目录
		String thumbStr = "c2" + thumbWidth + "x" + thumbHeight;

		String thumbfilePath = ConfigSetting.thumbPathByPath(filePathT,
				thumbStr);
		if (StringUtils.isBlank(thumbfilePath)){
			log.warn("file not exist");
			return ;
		}
		file = new File(thumbfilePath);
		if (!file.exists()) {
			ImageUtil2.compressFile2(originalFile, thumbWidth, thumbHeight,
					thumbfilePath);
		}

		if (file.exists()) {
			boolean isModify = Servlets.checkIfModifiedSince(request, response, file.lastModified());
			if (isModify){
				Servlets.setExpiresHeader((HttpServletResponse) response, 31536000);
				Servlets.setLastModifiedHeader(response, file.lastModified());
				response.setContentType("image/jpeg");
				// write the data out
				InputStream is = new FileInputStream(file);
				IOUtils.copy(is, response.getOutputStream());
			}
		} else {
			log.warn("file not exist");
		}
	}
	

	

	
	/**
	 * 下载文件
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(String filePath) throws IOException {
		String filePathT = ConfigSetting.Pro_Dir + filePath;
		File file = new File(filePathT);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String(file.getName().getBytes("UTF-8"),
				"iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}
	
	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 * @return json format
	 * @throws IOException 
	 */
	@RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
	public  void uploadImage(@RequestParam(defaultValue="0") int type,
			MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
		PathType pathType = PathType.value(type);
		
		Map<String, Object> files = Maps.newHashMap();
		List<Image> list = new LinkedList<>();

		//类型错误
		if (pathType == null) {
			Image image = new Image();
			image.setError("非法操作");
			list.add(image);
			files.put("files", list);
			JSONObject jo = new JSONObject(files);
			response.getOutputStream().print(jo.toJSONString());
		}

		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		while (itr.hasNext()) {
			//获取请求中的文件数据对象
			mpf = request.getFile(itr.next());
			log.debug("Uploading {}", mpf.getOriginalFilename());
			//验证文件类型
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
				JSONObject jo = new JSONObject(files);
				response.getOutputStream().print(jo.toJSONString());
			}

			//10M限制
			if (mpf.getSize() > 1024*1024*1024) {
				Image image = new Image();
				image.setError("图片大小超过10M");
				list.add(image);
				files.put("files", list);
				JSONObject jo = new JSONObject(files);
				response.getOutputStream().print(jo.toJSONString());
			}

			try {
				String tempdir = ConfigSetting.tempPathByType(pathType);

				//生成临时文件路径
				String newFilePath = FileUtil.hashPath(tempdir, mpf.getOriginalFilename(),  "admin");
				File newFile = new File(newFilePath);
				//存储到路径文件
				mpf.transferTo(newFile);
				

				//提取出缩略图
				BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), FileUtil.thumbSize, FileUtil.thumbSize);
				String thumbnailFilename = ConfigSetting.thumbPathByPath(newFilePath, FileUtil.ThumbSize);
				File thumbnailFile = new File(thumbnailFilename);
		
				ImageIO.write(thumbnail, "png", thumbnailFile);
				String name = mpf.getOriginalFilename();
				Image image = new Image();
				Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
				Matcher m=p.matcher(name); 
				if(m.find()){ 
					name = URLEncoder.encode(name, "UTF-8");
				}
				image.setName(name); 
				image.setThumbnailFilename(thumbnailFilename.substring(ConfigSetting.Temp_Dir.length()-1));
				image.setNewFilename(newFilePath.substring(ConfigSetting.Temp_Dir.length()));	
				image.setContentType(mpf.getContentType());
				image.setSize(mpf.getSize());
				image.setThumbnailSize(thumbnailFile.length());
				
				System.out.println("Temp_Dir："+ConfigSetting.Temp_Dir);
				System.out.println("newFilePath："+image.getNewFilename());
				System.out.println("thumbnailFilename："+image.getThumbnailFilename());
				
				image.setUrl("/files/display?filePath="+image.getNewFilename());
				image.setNewurl("/files/displayProThumbTemp?filePath="+image.getThumbnailFilename()+"&thumbWidth=120&thumbHeight=120");
				image.setThumbnailUrl("/files/displayProThumbTemp?filePath="+image.getThumbnailFilename()+"&thumbWidth=32&thumbHeight=32");
				image.setDeleteType("GET");
				list.add(image);

			} catch (IOException e) {
				log.error("Could not upload file " + mpf.getOriginalFilename(),
						e);
				System.out.println("错误："+mpf.getOriginalFilename());
			}
		}

		files.put("files", list);
		JSONObject jo = new JSONObject(files);
		response.getOutputStream().print(jo.toJSONString());
	}

	/**
	 * 显示视频（正式文件）
	 * 
	 * @param filePath
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/displayVideoPro", method = RequestMethod.GET)
	public void displayVideoPro(
			@RequestParam String filePath,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String filePathT = ConfigSetting.Pro_Dir + filePath;

		File originalFile = new File(filePathT);
		File file = originalFile;
		String type = file.getName().substring(file.getName().lastIndexOf("."));
		if (file.exists()) {
			boolean isModify = Servlets.checkIfModifiedSince(request, response, file.lastModified());
			if (isModify){
				Servlets.setExpiresHeader((HttpServletResponse) response, 31536000);
				Servlets.setLastModifiedHeader(response, file.lastModified());
				response.setContentType("video/"+type);
				// write the data out
				InputStream is = new FileInputStream(file);
				IOUtils.copy(is, response.getOutputStream());
			}
		} else {
			log.warn("file not exist");
		}
	}
	
}

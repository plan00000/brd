/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.controller.ueditor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.google.common.collect.Maps;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.util.file.FileUtil;

/**
 * 
 * @author wwy 2015年9月11日
 */
@Controller
@RequestMapping("Ueditor")
public class UeditorController {
	/***
	 * 上传图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "controller", params = "action=uploadimage")
	@ResponseBody
	public String controller(MultipartHttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		PathType pathType = PathType.UEDITOR;
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		State storageState = null;
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
			try {
				String prodir = ConfigSetting.proPathByType(pathType);
				String originalFilename = mpf.getOriginalFilename();
				String newFilePath = FileUtil.hashPath(prodir,
						originalFilename, "4123", originalFilename
								.substring(originalFilename.lastIndexOf(".")));
				File newFile = new File(newFilePath);
				// 存储到路径文件
				mpf.transferTo(newFile);
				/*
				 * BufferedImage bufferImage =
				 * Scalr.resize(ImageIO.read(newFile), 240, 320);
				 * ImageIO.write(bufferImage,
				 * originalFilename.substring(originalFilename
				 * .lastIndexOf(".")+1), newFile);
				 */storageState = new BaseState(true, newFile.getAbsolutePath());
				storageState.putInfo("size", "");
				storageState.putInfo("title", newFile.getName());
				if (storageState.isSuccess()) {
					storageState.putInfo("url",
							PathFormat.format(
							"/files/displayPro;JSESSIONID="+ request.getSession().getId()
									+ "?filePath="
									+ newFilePath.substring(ConfigSetting.Pro_Dir.length())));
					storageState.putInfo("type", "");
					storageState.putInfo("original", "");
				}
			} catch (IOException e) {

			}
		}

		return storageState.toJSONString();
	}

	@RequestMapping("controller")
	public String controller(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		return "ueditor/controller";
	}
}

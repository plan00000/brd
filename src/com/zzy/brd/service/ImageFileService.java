/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.entity.Image;
import com.zzy.brd.util.image.Scalr;

 

/**
 * 图片上传服务
 * 
 * @author tian 2015年1月20日
 */
@Service
public class ImageFileService {

	/**
	 * 上传图片的单个处理
	 * 
	 * @param mpf
	 * @return
	 */
	public Image uploadImage(MultipartFile mpf, int type) {
		Image image = null;
		// 获取请求中的文件数据对象
		if (mpf.getSize() > 5 * 1000 * 1000) {
			return image;
		}
		String newFilenameBase = UUID.randomUUID().toString();
		String originalFileExtension = mpf.getOriginalFilename().substring(
				mpf.getOriginalFilename().lastIndexOf("."));
		String newFilename = newFilenameBase + originalFileExtension;
		String contentType = mpf.getContentType();
		File newFile = new File(getPicDirByType(type) + newFilename);

		try {
			// 转入到路径文件，将图片写入路径中
			mpf.transferTo(newFile);

			// 提取出缩略图
			BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 290);
			String thumbnailFilename = newFilenameBase + "-thumbnail.png";
			File thumbnailFile = new File(getThumbDirByType(type)
					+ thumbnailFilename);
			//将缩略图写入文件
			ImageIO.write(thumbnail, "png", thumbnailFile);
			image = new Image();
			image.setName(mpf.getOriginalFilename());
			image.setThumbnailFilename(thumbnailFilename);
			image.setNewFilename(newFilename);
			image.setContentType(contentType);
			image.setSize(mpf.getSize());
			image.setThumbnailSize(thumbnailFile.length());
			image.setUrl("/files/display?filePath="+image.getNewFilename());
			image.setThumbnailUrl(image.getThumbnailFilename());
			image.setDeleteUrl("/delete/" + image.getId());
			image.setDeleteType("DELETE");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	private String getPicDirByType(int type) {
		if (type == 1) { 
			return ConfigSetting.Head_Person_Pic_Dir;
		} 
		return ConfigSetting.Head_Person_Pic_Dir;
	}

	private String getThumbDirByType(int type) {
		if (type == 1) { 
			return ConfigSetting.Head_Person_Thumbnail_Dir;
		}
		return ConfigSetting.Head_Person_Thumbnail_Dir;
	}
}

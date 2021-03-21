/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.image.ImageUtil;
import com.zzy.util.MD5;

/**
 * 文件操作工具
 * 
 * @author lll 2015年1月16日
 */
public class FileUtil {
	
	/** 默认缩略图路径 size*/
	public static String ThumbSize = "120x120";
	public static int thumbSize = 120;
	/** 原图最大尺寸 */
	public static int MAX_SIZE = 1080;
	
	/**
	 *	文件分隔符 
	 */
	public static String FILE_SPEARATOR = System.getProperty("file.separator");

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public static File createFolder(String folderPath) {
		File myFilePath = null;
		try {
			myFilePath = new File(folderPath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return myFilePath;
	}

	/**
	 * 验证文件是否是图片
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("finally")
	public static boolean isImage(File file) {
		boolean isImage = false;
		try {
			BufferedImage image = ImageIO.read(file);
			if (image != null) {
				isImage = true;
			}
		} catch (IOException ex) {
			logger.warn("{}", ex);
		} finally {
			return isImage;
		}
	}
	
	/**
	 * 获取图片
	 * @param is
	 * @return
	 */
	public static BufferedImage getImage(InputStream is) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(is);
		} catch (IOException ex) {
			logger.warn("{}", ex);
		} finally {
			return image;
		} 
	}

	/**
	 * 验证文件是否是图片
	 * 
	 * @param is
	 * @return
	 */
	@SuppressWarnings("finally")
	public static boolean isImage(InputStream is) {
		return getImage(is) != null;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 */
	public static void createFolders(String folderPath, String paths) {
		try {
			String txt;
			StringTokenizer st = new StringTokenizer(paths, "|");
			while (st.hasMoreTokens()) {
				txt = st.nextToken().trim();
				if (folderPath.lastIndexOf(FILE_SPEARATOR) != -1) {
					createFolder(folderPath + FILE_SPEARATOR + txt);
				} else {
					createFolder(folderPath + txt);
				}
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}

	/**
	 * 根据文件流读取图片文件真实类型
	 * 
	 * @param is
	 * @return
	 */
	public static String getTypeByStream(FileInputStream is) {
		byte[] b = new byte[4];
		try {
			is.read(b, 0, b.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String type = bytesToHexString(b).toUpperCase();
		if (type.contains("FFD8FF")) {
			return "jpg";
		} else if (type.contains("89504E47")) {
			return "png";
		} else if (type.contains("47494638")) {
			return "gif";
		} else if (type.contains("49492A00")) {
			return "tif";
		} else if (type.contains("424D")) {
			return "bmp";
		}
		return type;
	}

	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileExtension(String filePath) {
		int index = filePath.lastIndexOf(".");
		if (index != -1) {
			return filePath.substring(index);
		}
		return "";
	}

	/**
	 * 目录结构生成 文件分散存储，防止某目录下文件过多
	 * 
	 * @param dir			目录
	 * @param fileName			原始文件名
	 * @param fileNamePrefix 	文件名前缀	 一般为用户id
	 * @return 文件路径：  "dir/日期(XXXX-XX-XX)/hashpath/fileNamePrefix_uuid_fileName"
	 */
	public static String hashPath(String dir, String fileName, String fileNamePrefix) {
		
		int hashCode = fileName.hashCode();

		int dir1 = (hashCode >> 4) & 0xf;

		int dir2 = hashCode & 0xf;
		
		String newpath = null;
		if (dir.endsWith(FILE_SPEARATOR)) {
			newpath = dir + DateUtil.DateToString(new Date()) + FILE_SPEARATOR + dir1 + FILE_SPEARATOR + dir2 + FILE_SPEARATOR;
		} else {
			newpath = dir + FILE_SPEARATOR + DateUtil.DateToString(new Date()) +  FILE_SPEARATOR + dir1 + FILE_SPEARATOR + dir2 + FILE_SPEARATOR;
		}

		File file = new File(newpath);

		if (!file.exists()) {
			file.mkdirs();
		}
		
		return newpath + fileNamePrefix + "_" + uuidName(fileName)+".png";
	}
	
	/**
	 * 目录结构生成 文件分散存储，防止某目录下文件过多
	 * 
	 * @param dir			目录
	 * @param fileName			原始文件名
	 * @param fileNamePrefix 	文件名前缀	 一般为用户id
	 * @return 文件路径：  "dir/日期(XXXX-XX-XX)/hashpath/fileNamePrefix_uuid_fileName"
	 */
	public static String hashPath(String dir, String fileName, String fileNamePrefix, String fileNameSuffix) {
		
		int hashCode = fileName.hashCode();

		int dir1 = (hashCode >> 4) & 0xf;

		int dir2 = hashCode & 0xf;
		
		String newpath = null;
		if (dir.endsWith(FILE_SPEARATOR)) {
			newpath = dir + DateUtil.DateToString(new Date()) + FILE_SPEARATOR + dir1 + FILE_SPEARATOR + dir2 + FILE_SPEARATOR;
		} else {
			newpath = dir + FILE_SPEARATOR + DateUtil.DateToString(new Date()) +  FILE_SPEARATOR + dir1 + FILE_SPEARATOR + dir2 + FILE_SPEARATOR;
		}

		File file = new File(newpath);

		if (!file.exists()) {
			file.mkdirs();
		}
		
		return newpath + fileNamePrefix + "_" + uuidName(fileName)+fileNameSuffix;
	}
	

	/**
	 * 目录结构生成 文件分散存储，防止某目录下文件过多
	 * 
	 * @param dir			目录
	 * @param fileName			原始文件名
	 * @param fileNamePrefix 	文件名前缀	 一般为用户id
	 * @return 文件路径：  "dir/日期(XXXX-XX-XX)/hashpath/fileNamePrefix_uuid_fileName"
	 */
	public static String hashPathByXls(String dir, String fileName, String fileNamePrefix) {
		
		int hashCode = fileName.hashCode();

		int dir1 = (hashCode >> 4) & 0xf;

		int dir2 = hashCode & 0xf;
		
		String newpath = null;
		if (dir.endsWith(FILE_SPEARATOR)) {
			newpath = dir + DateUtil.DateToString(new Date()) + FILE_SPEARATOR + dir1 + FILE_SPEARATOR + dir2 + FILE_SPEARATOR;
		} else {
			newpath = dir + FILE_SPEARATOR + DateUtil.DateToString(new Date()) +  FILE_SPEARATOR + dir1 + FILE_SPEARATOR + dir2 + FILE_SPEARATOR;
		}

		File file = new File(newpath);

		if (!file.exists()) {
			file.mkdirs();
		}
		
		return newpath + fileNamePrefix + "_" + uuidName(fileName)+".xls";
	}
	
	/**
	 * 将临时文件夹内的文件 转移到正式路径，并返回正式路径字符串
	 * @param tempPath  Temp_Dir 下的相对路径  如   "price/xxx"
	 * @return 正式路径字符串   Pro_Dir 下的相对路径  如 "price/xxx"   空字符串 则失败
	 */
	public static String moveToProPathByTempPath(String tempPath) {
		
		String absoluteTempPath = ConfigSetting.Temp_Dir + tempPath;
		String absoluteProPath = ConfigSetting.Pro_Dir + tempPath;		
		File tempFile = new File(absoluteTempPath);
		File proFile = new File(absoluteProPath);
		
		String thumbFilePath = ConfigSetting.thumbPathByPath(absoluteTempPath, ThumbSize);
		File thumbTempFile = new File(thumbFilePath);
		
		String thumbProFilePath = ConfigSetting.thumbPathByPath(absoluteProPath, ThumbSize);
		File thumbProFile = new File(thumbProFilePath);
		
		//移动原图
		try {
			if (tempFile.exists()) {
				FileUtils.copyFile(tempFile, proFile);
				tempFile.delete();
			} else {
				return null;
			}
		} catch (IOException e) {
			logger.warn("move error {}",e);
			return null;
		}
		
		//移动默认尺寸缩略图
		try {
			if (thumbTempFile.exists()) {
				FileUtils.copyFile(thumbTempFile, thumbProFile);
				thumbTempFile.delete();
				//TODO 直接moveFile异常
				//FileUtils.moveFile(thumbTempFile, thumbProFile);
			}
		} catch (IOException e) {
			logger.warn("move error {}",e);
		}
		
		//相对路径不变
		return tempPath;
	}
	
	/**
	 * 原图上传处理
	 * @param mpf
	 * @param imageBuff
	 * @param tofile
	 * @throws IOException
	 */
	public static void dealOriginalImage(MultipartFile mpf,BufferedImage imageBuff, File tofile) throws IOException {
		if (imageBuff.getHeight() > FileUtil.MAX_SIZE || imageBuff.getWidth() > FileUtil.MAX_SIZE) {
			ImageUtil.createThumbnail(imageBuff, tofile, FileUtil.MAX_SIZE, FileUtil.MAX_SIZE);
		} else {
			mpf.transferTo(tofile);
		}
	}
	
	/**
	 * 删除图片及其缩略图
	 * 
	 * @param filePath 数据库中存储的相对路径
	 */
	public static void deleteImg ( String filePath ){
		String absoluteProPath = ConfigSetting.Pro_Dir + filePath;
		String thumbProFilePath = ConfigSetting.thumbPathByPath(absoluteProPath, ThumbSize);
		File imgFile = new File(absoluteProPath);
		File thumbImgFile = new File(thumbProFilePath);
		if (imgFile.exists()){
			imgFile.delete();
		}
		if (thumbImgFile.exists()){
			thumbImgFile.delete();
		}
	}
	
	/** private methods */

	/**
	 * 生成UUID文件名
	 * @param fileName
	 * @return
	 */
	private static String uuidName(String fileName) {
		UUID uuid = UUID.randomUUID();
		return MD5.hex_md5(uuid.toString() + "_" + fileName);
	}

	public static void renameFile(String path, String oldname, String newname) { 
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
            File oldfile=new File(path + oldname); 
            File newfile=new File(path + newname); 
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
            	newfile.delete();
            oldfile.renameTo(newfile);             
        }else{
            return;
        }
    }
	
	/***
	 * 移动图片到正式路径，返回路径
	 * 
	 * @param path
	 * @return
	 */
	public static String moveFileToPro(String path) {
		//
		int index = path.indexOf("filePath=");
		if (index > -1) {
			String tempPath = StringUtils.substringAfter(path, "filePath=");
			path = FileUtil.moveToProPathByTempPath(tempPath);
		}
		 
		path = path.replace("\\", "/");
		return path;
	}
	
}

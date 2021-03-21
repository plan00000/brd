/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

/***
 * 
 * @author wwy
 *
 */
public class ImageUtil2 {
	/**
	 * 修改图片文件类型<br/>
	 * outputFormat：输出的图片格式。注意使用该方法后toFile()方法不要再含有文件类型的后缀了，否则会生成
	 * IMG_20131229_114806.jpg.jpg 的图片。
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param destinctFilePath
	 *            目标文件，不能带后缀
	 * @param fileType
	 *            目标文件类型
	 * @throws IOException
	 */
	public static void changeFileType(File sourceFile, String targetFilePath,
			String fileType) throws IOException {
		Thumbnails.of(sourceFile).scale(1f).outputFormat(fileType)
				.toFile(targetFilePath);
	}

	/***
	 * 图片尺寸不变，压缩图片文件大小<br/>
	 * 可自定义文件类型
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param quality
	 *            压缩比率 0到1
	 * @param targetFilePath
	 *            目标文件，不能带后缀
	 * @param fileType
	 *            目标文件类型
	 * @throws IOException
	 */
	public static void compressFile1(File sourceFile, double quality,
			String targetFilePath, String fileType) throws IOException {
		Thumbnails.of(sourceFile).scale(1f).outputQuality(quality)
				.outputFormat(fileType).toFile(targetFilePath);
	}

	/***
	 * 图片尺寸不变，压缩图片文件大小<br/>
	 * 使用源文件后缀
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param quality
	 *            压缩比率 0到1
	 * @param targetFilePath
	 *            目标文件，不能带后缀
	 * @throws IOException
	 */
	public static void compressFile1(File sourceFile, double quality,
			String targetFilePath) throws IOException {
		Thumbnails.of(sourceFile).scale(1f).outputQuality(quality)
				.toFile(targetFilePath);
	}

	/***
	 * 压缩至指定图片尺寸（例如：横400高300），不保持图片比例<br/>
	 * 自定义文件后缀
	 * 
	 * @param sourceFile
	 * @param width
	 * @param height
	 * @param fileType
	 * @param targetFilePath
	 * @throws IOException
	 */
	public static void compressFile2(File sourceFile, int width, int height,
			String fileType, String targetFilePath) throws IOException {
		Thumbnails.of(sourceFile).forceSize(width, height)
				.outputFormat(fileType).toFile(targetFilePath);
	}

	/***
	 * 压缩至指定图片尺寸（例如：横400高300），不保持图片比例<br/>
	 * 使用源文件的后缀
	 * 
	 * @param sourceFile
	 * @param width
	 * @param height
	 * @param targetFilePath
	 * @throws IOException
	 */
	public static void compressFile2(File sourceFile, int width, int height,
			String targetFilePath) throws IOException {
		Thumbnails.of(sourceFile).forceSize(width, height)
				.toFile(targetFilePath);
	}

	/***
	 * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉<br/>
	 * 使用源文件的后缀
	 * 
	 * @param sourceFile
	 * @param width
	 * @param height
	 * @param targetFilePath
	 * @throws IOException
	 */
	public static void compressFile3(File sourceFile, int width, int height,
			String targetFilePath) throws IOException {
		BufferedImage image = ImageIO.read(sourceFile);
		Builder<BufferedImage> builder = null;

		int imageWidth = image.getWidth();
		int imageHeitht = image.getHeight();
		if ((float) width / height != (float) imageWidth / imageHeitht) {
			if (imageWidth > imageHeitht) {
				image = Thumbnails.of(sourceFile).height(height)
						.asBufferedImage();
			} else {
				image = Thumbnails.of(sourceFile).width(width)
						.asBufferedImage();
			}
			builder = Thumbnails.of(image)
					.sourceRegion(Positions.CENTER, width, height)
					.size(width, height);
		} else {
			builder = Thumbnails.of(image).size(width, height);
		}
		String filePath = sourceFile.getPath();
		String suffix = filePath.substring(filePath.lastIndexOf(".")+1);
		builder.outputFormat(suffix).toFile(targetFilePath);
	}

	/***
	 * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉<br/>
	 * 使用自定义后缀
	 * 
	 * @param sourceFile
	 * @param width
	 * @param height
	 * @param targetFilePath
	 * @throws IOException
	 */
	public static void compressFile3(File sourceFile, int width, int height,
			String targetFilePath, String fileType) throws IOException {
		BufferedImage image = ImageIO.read(sourceFile);
		Builder<BufferedImage> builder = null;

		int imageWidth = image.getWidth();
		int imageHeitht = image.getHeight();
		if ((float) width / height != (float) imageWidth / imageHeitht) {
			if (imageWidth > imageHeitht) {
				image = Thumbnails.of(sourceFile).height(height)
						.asBufferedImage();
			} else {
				image = Thumbnails.of(sourceFile).width(width)
						.asBufferedImage();
			}
			builder = Thumbnails.of(image)
					.sourceRegion(Positions.CENTER, width, height)
					.size(width, height);
		} else {
			builder = Thumbnails.of(image).size(width, height);
		}
		builder.outputFormat(fileType).toFile(targetFilePath);
	}
}

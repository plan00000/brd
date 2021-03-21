/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.util.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * @author lll 2015年3月31日
 */
public class ImageUtil {
	
	/**
	 * 创建色图
	 * @param color
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage createColorImage(Color color,int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setBackground(color);
        g2.clearRect(0, 0, width, height);
        return bi;
	}
	
	/**
	 * 创建缩略图
	 * @param sourceFile
	 * @param destFile
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void createThumbnail(File sourceFile,File destFile, int width, int height) throws IOException {
		Thumbnails.of(sourceFile).size(width, height).useExifOrientation(true).outputFormat("png").outputQuality(0.75).toFile(destFile);
	}
	
	public static void createThumbnail(BufferedImage imageBuff,File destFile, int width, int height) throws IOException {
		Thumbnails.of(imageBuff).size(width, height).useExifOrientation(true)
		.outputFormat("png").outputQuality(0.75).toFile(destFile);
	}
	
	public static void createThumbnail(BufferedImage imageBuff,File destFile, int width, int height,BufferedImage watermark) throws IOException {
		Thumbnails.of(imageBuff).size(width, height).watermark(watermark,1.0f)
		.useExifOrientation(true).outputFormat("png").outputQuality(0.75).toFile(destFile);
	}
	
	@Test
	public void testColorImage(){
		
	}
}

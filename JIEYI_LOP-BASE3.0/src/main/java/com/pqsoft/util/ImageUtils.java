package com.pqsoft.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.pqsoft.skyeye.Log;

/**
 * @author LUYANFENG @ 2015年8月5日
 *
 */
public final class ImageUtils {
	
	private ImageUtils() {
	}
	
	public static String scaleImage(String file, float scale){
		return scaleImage(new File(file), scale);
	}
	public static String scaleImage(File file, float scale){
		try {
			BufferedImage im = ImageIO.read(file);
			/* 原始图像的宽度和高度 */  
			int width = im.getWidth();  
			int height = im.getHeight();
			/* 调整后的图片的宽度和高度 */
			int toWidth = (int) (width * scale);  
			int toHeight = (int) (height * scale);  
			 /* 新生成结果图片 */
			BufferedImage scaleImg = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
			scaleImg.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight,  Image.SCALE_FAST), 0, 0, null);
			String path = file.getPath();
			String suff = path.substring(path.lastIndexOf("."));
			String newPath = path.replaceFirst( suff, "_scale_"+toWidth+"X"+ toHeight +suff);
			File newFile = new File(newPath );
			ImageIO.write(scaleImg, "png", newFile);
			return newFile.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}
	
	
	/**
	 * @param toWidth 新的宽度
	 * @param toHeight 新的高度
	 * @param isScale 是否等比例处理，true 以toWidth 比例大小处理。
	 * @author LUYANFENG @ 2015年5月12日
	 */
	public static String reSizeImage(File file, int toWidth , int toHeight , boolean isScale){
		try {
			BufferedImage im = ImageIO.read(file);
			/* 原始图像的宽度和高度 */  
			int width = im.getWidth();  
			int height = im.getHeight();
			if(isScale){
				double srcScale = Double.valueOf(width) / Double.valueOf(height);
				double targetScale = Double.valueOf(toWidth) / Double.valueOf(toHeight);
				if(targetScale != srcScale){
					double newHeight = Double.valueOf(toWidth) / Double.valueOf(width) * height;
					toHeight = (int) newHeight;
				}
			}
			
			
			
			
			
			BufferedImage scaleImg = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = scaleImg.getGraphics();
			Image scaledInstance = im.getScaledInstance(toWidth, toHeight,  Image.SCALE_FAST);
			g.drawImage( scaledInstance , 0, 0, null);
			String path = file.getPath();
			String suff = path.substring(path.lastIndexOf("."));
			String newPath = path.replaceFirst( suff, "_scale_"+toWidth+"X"+ toHeight +suff);
			File newFile = new File(newPath );
			ImageIO.write(scaleImg, "png", newFile);
			return newFile.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}

	/**
	 * 后缀是否是图片类型
	 * @param fILE_TYPE
	 * @return
	 * @author LUYANFENG @ 2015年8月6日
	 * @aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	 */
	public static boolean isImage(String fILE_TYPE) {
		if(StringUtils.isBlank(fILE_TYPE)){
			return false;
		}
		if(fILE_TYPE.startsWith(".")){
			fILE_TYPE = fILE_TYPE.substring(1);
		}
		
		Log.debug(Arrays.toString(ImageIO.getWriterFormatNames()));
		return Arrays.asList(ImageIO.getWriterFormatNames()).contains(fILE_TYPE);
	}

}

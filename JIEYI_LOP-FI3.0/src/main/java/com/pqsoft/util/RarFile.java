package com.pqsoft.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.jfree.util.Log;

public class RarFile {

	public static void copyFile(String oldFile, String newFile) throws IOException {
		FileInputStream fis = new FileInputStream(oldFile);
		FileOutputStream fos = new FileOutputStream(newFile);
		int temp;
		while ((temp = fis.read()) != -1) {
			fos.write(temp);
		}
		fis.close();
		fos.close();
		Log.info("从" + oldFile + "到" + newFile);
	}
	/**
	 * 
	 * @param baseFile 源目录
	 * @param fileList key=文件名 value =path
	 * @param tempFilePath 拷贝目录、
	 * @param tempFilePath2 生成rar的目录
	 * @throws IOException
	 */
	public static void rarFiles(String baseFile,Map<String,String> fileList,String tempFilePath,String tempFilePath2) throws IOException
	{
		if(baseFile==null)
			throw new RuntimeException("文件根路径不能为空！");
		if(fileList==null||fileList.size()<0)
			throw new RuntimeException("文件列表不能为空！");
		
		for (String item :fileList.keySet()) {
			if(fileList.get(item)==null||fileList.equals(""))
				continue;
			String pathAndName = fileList.get(item);
			String filePath = pathAndName.substring(0,pathAndName.lastIndexOf(File.separator)+1);
			Log.info(baseFile+pathAndName+"拷贝路径"+tempFilePath/*+filePath*/+item+".jpg");
			copyFile(baseFile+pathAndName,tempFilePath/*+filePath*/+File.separator+item+".jpg");
		}
		RarUtil zip = new RarUtil(tempFilePath2);
		zip.compress(tempFilePath);
		Log.info("压缩完成");
	}

}

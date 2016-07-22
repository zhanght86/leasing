package com.pqsoft.softmg.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.SkyEye;

public class FileUploadService {
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	public boolean uploadPdfTemplate(Map<String, Object> param,List<FileItem> fileList){
		logger.info("开始上传文件");
		// 设置文件上传的大小 目前设置为30m
		final long MAX_SIZE = 300 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path");
		path+=File.separator+"filesoftMg";
		try {
			File wf = new File(path);
			if (!wf.exists()) {//检查磁盘上是否存在path路径文件
				wf.mkdirs();//在磁盘上创建路径
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		ServletFileUpload suf = new ServletFileUpload(df);
		suf.setHeaderEncoding("UTF-8");
		suf.setSizeMax(MAX_SIZE);
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			logger.error(fileitem.getFieldName());
			logger.error(fileitem.getName());
			if (!fileitem.isFormField()) {// 如果是文件
				logger.info("fieldName:  " + fileitem.getFieldName() + "Name: " + fileitem.getName());
				if(!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())){
					String fn = Action._getFileName(fileitem.getName());
					file = new File(path + File.separator + UUID.randomUUID() + fn);
					// 判断文件扩展名
					String filename = file.getName();
					String lastname = filename.substring(filename.length() - 4);
					if (file.exists()) {
						if (!".pdf".equals(lastname)) {
							logger.error("您上传的文件格式不正确! --> " + file.getName());
							file = null;
							return false;
						}
					}
					try {
						fileitem.write(file);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
					param.put("NAME", fn.substring(0, fn.length()-4));
					param.put("PDF_PATH", file.getPath());
					param.put("PDF_SIZE", fileitem.getSize() / 1024 + "KB");
					logger.info("保存文件完毕：" + file.getAbsolutePath());
				}
			} else {
				try {
					param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	

}

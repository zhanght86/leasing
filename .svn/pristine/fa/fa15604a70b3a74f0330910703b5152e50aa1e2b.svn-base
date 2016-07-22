package com.pqsoft.paper.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class PaperFilesManageService {
	
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	String xmlPath = "paper.";//xml路径
	
	/**
	 * 资料管理页列表分页
	 * @author 耿长宝
	 * @date 2016年5月12日16:39:20
	 */
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	/**
	 * 上传资料文件
	 * @author 耿长宝
	 * @date 2016年5月12日16:39:20
	 */
	public boolean uploadPaperFile(Map<String, Object> param,List<FileItem> fileList){
		logger.info("开始上传文件");
		// 设置文件上传的大小 目前设置为30m
		final long MAX_SIZE = 300 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path");
		//D:\parerFiles
		path = path + "/parerFiles/";
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
					file = new File(path + fn);
//					if (file.isFile() && file.exists()) {  
//						file.delete();  
//					}
					try {
						fileitem.write(file);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
					param.put("FILE_NAME", fn);
					param.put("FILE_PATH", file.getPath());
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
	
	/**
	 * 添加pdf模版
	 * @param TPM_ID  模版ID
	 * @author 耿长宝
	 * @date 2016年5月12日16:39:20
	 */
	public boolean uploadPaperFile(Map<String, Object> param){
		String ID = "";
		param.put("CLERK_ID", Security.getUser().getId());
		param.put("CLERK_NAME",Security.getUser().getName());
		// 在数据库中储存模版相关属性
		Map<String, Object> map = Dao.selectOne(xmlPath+"selectPaperFileIdBYNm", param);;
		if (map != null && map.get("ID") != null && !"".equals(map.get("ID").toString())) {
			ID = map.get("ID").toString();
			param.put("ID", ID);
			return Dao.update(xmlPath+"updatePaperFile", param) >= 1 ;
		} else {
			ID = Dao.getSequence("SEQ_FIL_PAPER_FILES");
			param.put("ID", ID);
			return Dao.insert(xmlPath+"addUploadPaperFile", param) >= 1 ;
		}
	}
	
	/**
	 * 查询资料文件详情
	 * @param ID  资料文件ID
	 * @author 耿长宝
	 * @date 2016年5月12日16:39:20
	 */
	public Map<String,Object> selectPaperFile(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"selectPaperFile", param);
	}
	
	/**
	 * 删除资料文件
	 * @param ID  资料文件ID
	 * @author 耿长宝
	 * @date 2016年5月12日16:39:20
	 */
	public boolean deletePaperFile(Map<String,Object> param){
		return Dao.delete(xmlPath+"deletePaperFile", param) >= 1;
	}
	
}

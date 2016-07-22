package com.pqsoft.pdfTemplate.service;

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
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.PageUtil;

public class PdfTemplateManagementService {
	
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	String xmlPath = "bpm.pdfTemplate.";//xml路径
	
	/**
	 * PDF管理页列表分页
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-8-30  上午09:40:05
	 */
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	/**
	 * 管理页展开层数据
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-8-30  下午04:19:38
	 */
	public List<Map<String,Object>> getUnfoldDate(Map<String, Object> param){
		return Dao.selectList(xmlPath+"getUnfoldDate", param);
		
	}
	
	/**
	 * 添加新模版
	 * @param TPM_TYPE  模版类型
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-3  下午04:32:24
	 */
	public boolean addPdfTemplate(Map<String, Object> param){
//		String TPM_ID = Dao.getSequence("SEQ_PDFTEMPLATE_MAINTENANCE");
//		param.put("TPM_ID", TPM_ID);
		return Dao.insert(xmlPath+"addPdfTemplate", param) > 0;
	}
	
	/**
	 * 上传pdf模版
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-16  下午02:49:17
	 */
	public boolean uploadPdfTemplate(Map<String, Object> param,List<FileItem> fileList){
		logger.info("开始上传文件");
		// 设置文件上传的大小 目前设置为30m
		final long MAX_SIZE = 30 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path");
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
	
	/**
	 * 添加pdf模版
	 * @param TPM_ID  模版ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-3  下午04:32:24
	 */
	public boolean uploadPdfTemplate(Map<String, Object> param){
		String maxVersion = Dao.selectOne(xmlPath+"selectMaxTpmVersion", param);//历史最大版本号
		int PDF_VERSION = maxVersion == null || maxVersion.isEmpty() ? 1 : Integer.parseInt(maxVersion)+1;//版本号
		param.put("PDF_VERSION", PDF_VERSION);
		if(param.containsKey("STATUS") && "启用".equals(param.get("STATUS")))
		//	&& maxVersion != null && !maxVersion.isEmpty()){//Modify By YangJ 2014-5-20 下午05:29:04 
		//	注释掉 否则添加第一个模版且启用时，不会更新、填写pdf维护表 启用版本字段
		{
			param.put("UPDATA_STATUS", "禁用");
			param.put("EDITOR_TPM_VERSION1", PDF_VERSION);
			boolean flag = true;
			if("1".equals(param.get("FILE_TYPE"))){
				flag=Dao.update(xmlPath+"updatePdfTemplate", param) >= 0;//将历史模版禁用
				System.out.println("king="+flag);
			}else{
				flag=Dao.update(xmlPath+"updatePdfTemplate", param) >= 0 && Dao.update(xmlPath+"updatePdfTemplateDetails", param) >= 0;//将历史模版禁用
				System.out.println("king33="+flag);
			}
			if(!flag){
				return false;
			}
		}
		
		
		String ID = Dao.getSequence("SEQ_PDFTEMPLATE_FILE");
		param.put("ID", ID);
		return Dao.insert(xmlPath+"addUploadPdfTemplate", param) >= 1 ;
	}
	
	/**
	 * 修改模版
	 * @param TPM_ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-3  下午11:39:15
	 */
	public boolean updatePdfTemplate(Map<String,Object> param){
		return Dao.delete(xmlPath+"updatePdfTemplateDetails", param) >= 1 ;
	}
	
	/**
	 * 修改pdf模版
	 * @param TPM_ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-3  下午11:39:15
	 */
	public boolean updatePdfFile(Map<String,Object> param){
		if(param != null && !param.isEmpty()){
			if(param.containsKey("START_DATE1")){
				param.put("START_DATE", param.get("START_DATE1"));
			}
			if(param.containsKey("END_DATE1")){
				param.put("END_DATE", param.get("END_DATE1"));
			}
			if(param.containsKey("TPM_ID1")){
				param.put("TPF_TPM_ID", param.get("TPM_ID1"));
			}
		}
		return Dao.delete(xmlPath+"updatePdfFile", param) >= 1;
	}
	
	/**
	 * 删除pdf模版
	 * @param TPM_ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-3  下午11:39:15
	 */
	public boolean deletePdfTemplate(Map<String,Object> param){
		return Dao.delete(xmlPath+"deletePdfTemplate", param) >= 1;
	}
	
	/**
	 * 查询模版详情
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午09:56:01
	 */
	public Map<String,Object> selectTemDetails(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"selectTemDetails", param);
	}
	
	public List queryContractSealInfoList(Map<String, Object> param){
		return Dao.selectList(xmlPath+"selectContractSealInfoList", param);
	}
	
	/**
	 * 查询pdf模版路径
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午09:56:01
	 */
	public Map<String,Object> selectPdfPath(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"selectPdfPath", param);
	}
	
	/**
	 * 启用模版
	 * @param ID pdf模版id
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午11:51:31
	 */
	@SuppressWarnings("unchecked")
	public boolean changeStatus(Map<String,Object> param){
		param.putAll((Map<String,Object>) Dao.selectOne(xmlPath+"selectPdfDetails", param));
		param.put("TPM_ID", param.get("TPF_TPM_ID"));
		param.put("UPDATA_STATUS", "禁用");
		int i = Dao.update(xmlPath+"updatePdfTemplate", param);//将历史模版禁用
		
		param.put("STATUS", "启用");
		int i1 = Dao.update(xmlPath+"updatePdfFile", param);//启用选中的模版
		
		param.put("EDITOR_TPM_VERSION1", param.get("PDF_VERSION"));
		param.put("TPM_ID", param.get("TPF_TPM_ID"));
		int i2 = Dao.update(xmlPath+"updatePdfTemplateDetails", param);//更新到模版信息中
		if(i >= 1 && i1 >= 1 && i2 >= 1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * pdf模版详细
	 * @param ID pdf模版id
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午11:51:31
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectPdfDetails(Map<String,Object> param){
		param.putAll((Map<String,Object>) Dao.selectOne(xmlPath+"selectPdfDetails", param));
		return param;
	}
	
	public List<Map<String,Object>> selectTpmCode(){
		return Dao.selectList(xmlPath+"selectTpmCode");
	}
	
	//add JZZL-151 gengchangbao 2016年4月12日 Start
	/**
	 * 删除模版的法人印章
	 * @param TPM_ID
	 * @author 耿长宝
	 * @date 2016-4-12
	 */
	public boolean deleteTpmSealInfo(Map<String,Object> param){
		return Dao.delete(xmlPath+"deleteTpmSealInfo", param) >= 1;
	}
	/**
	 * 添加模版的法人印章
	 * @param TPM_ID
	 * @author 耿长宝
	 * @date 2016-4-12
	 */
	public boolean addTpmSealInfo(Map<String, Object> param){
		return Dao.insert(xmlPath+"addTpmSealInfo", param) > 0;
	}
	//add JZZL-151 gengchangbao 2016年4月12日 End
}

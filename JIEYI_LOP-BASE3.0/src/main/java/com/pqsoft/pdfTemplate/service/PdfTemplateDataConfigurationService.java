package com.pqsoft.pdfTemplate.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class PdfTemplateDataConfigurationService {
	
	String xmlPath = "bpm.pdfTemplateDataConfiguration.";//xml路径

	/**
	 * PDF模板数据配置管理页
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午01:19:30
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
	
	//所有PDF模版信息
	public List<Map<String,Object>> selectPdfFile(){
		return Dao.selectList(xmlPath+"selectPdfFile", null);
		
	}
	
	/**
	 * 获取所有类型的pdf
	 * @return
	 * @author:King 2014-1-20
	 */
	public List<Map<String,Object>> selectPdfMain(){
		return Dao.selectList(xmlPath+"selectPdfMain");
	}
	
	//所有表单域SQL信息
	public List<Map<String,Object>> selectPdfFormLabel(){
		return Dao.selectList(xmlPath+"selectPdfFormLabel", null);
		
	}
	
	//查询表单域执行顺序
	public List<Map<String,Object>> selectExeOrder(Map<String, Object> param){
		return Dao.selectList(xmlPath+"selectExeOrder", param);
		
	}
	
	//添加PDF模版数据配置
	public boolean addPdfTemplateDataConfiguration(Map<String, Object> param){
		String ID = Dao.getSequence("SEQ_PDFTEMPLATE_CONFIGURATION");
		param.put("ID", ID);
		return Dao.insert(xmlPath+"addPdfTemplateDataConfiguration", param) >= 1 ? true : false;
		
	}
	
	//查看PDF模版数据配置
	public Map<String, Object> toShowDataConfiguration(Map<String, Object> param){
		return Dao.selectOne(xmlPath+"toShowDataConfiguration", param);
		
	}
	
	//修改PDF模版数据配置
	public boolean doUpdatePdfTemplateDataConfiguration(Map<String, Object> param){
		return Dao.update(xmlPath+"doUpdatePdfTemplateDataConfiguration", param) >= 1 ? true : false;
		
	}
	
	//删除PDF模版数据配置
	public boolean doDeletePdfTemplateDataConfiguration(Map<String, Object> param){
		return Dao.delete(xmlPath+"doDeletePdfTemplateDataConfiguration", param) >= 1 ? true : false;
		
	}
	/**
	 * 删除PDF模板数据配置
	 * @param map
	 * @return
	 * @author:King 2014-1-21
	 */
	public int deletePZ(Map<String,Object> map){
		return Dao.delete(xmlPath+"deletePZ",map);
	}
}

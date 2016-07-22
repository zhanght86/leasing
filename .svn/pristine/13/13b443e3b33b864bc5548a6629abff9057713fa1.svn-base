package com.pqsoft.pdfTemplate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class PdfTemplateFormLabelService {

	String xmlPath = "bpm.pdfTemplateFormLabel.";//xml路径
	
	/**
	 * PDF表单域管理页
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午01:19:30
	 */
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	/**
	 * 添加PDF模版表单域
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午02:43:59
	 */
	public boolean addPdfTemplateFormLabel(Map<String, Object> param){
		String ID = Dao.getSequence("SEQ_PDFTEMPLATE_FORMLABEL");
		param.put("ID", ID);
		return Dao.insert(xmlPath+"addPdfTemplateFormLabel", param) > 0;
	}
	
	/**
	 * 删除PDF模版表单域
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午02:56:29
	 */
	public boolean deletePdfTemplateFormLabel(Map<String, Object> param){
		return Dao.delete(xmlPath+"deletePdfTemplateFormLabel", param) > 0;
	}
	/**
	 * 查看PDF模版表单域详细
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午03:11:41
	 */
	public Map<String,Object> selectPdfTemplateFormLabelDetails(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"selectPdfTemplateFormLabelDetails", param);
	}
	
	/**
	 * 修改PDF模版表单域
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午03:18:44
	 */
	public boolean updatePdfTemplateFormLabel(Map<String, Object> param){
		return Dao.update(xmlPath+"updatePdfTemplateFormLabel", param) > 0;
	}
	
	/**
	 * SQL解析
	 * @param sql
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-9  下午07:41:56
	 */
	public List<String> parseSql(String sql) {
		if (sql.indexOf("select") != -1) {
			sql = sql.replace("select", "SELECT");
		}
		if (sql.indexOf("from") != -1) {
			sql = sql.replace("from", "FROM");
		}
		try {
			sql = sql.substring(sql.indexOf("SELECT") + 6, sql.indexOf("FROM"));
		} catch (Exception e) {
			return null;
		}
		//开始准备解析sql
		String[] Array = sql.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < Array.length; i++) {
			String m = Array[i];
			if (m.indexOf("(") != -1) {
				if (m.indexOf(")") == -1)
					continue;
				else {
					m = m.substring(m.indexOf(")") + 1);
					m = m.trim();
					if (m.length() > 0) {
						list.add(m);
					}
				}
			} else if (m.indexOf(")") != -1) {
				m = m.substring(m.indexOf(")") + 1);
				if ((m.trim()).length() > 0) {
					list.add(m);
				}
			} else {
				list.add(m);
			}
		}
		//初步解析后的list
		List<String> lst = new ArrayList<String>();
		for (String m : list) {
			if (m.indexOf(".") != -1) {
				m = m.substring(m.indexOf(".") + 1);
				m = m.trim();
				if (m.indexOf(" ") != -1) {
					m = m.substring(m.indexOf(" ") + 1);
				}
				m = m.trim();
				lst.add(m);
			} else {
				m = m.trim();
				m = m.substring(m.indexOf(" ") + 1);
				if (m.indexOf(" ") != -1) {
					m = m.trim();
					lst.add(m);
				} else {
					m = m.trim();
					lst.add(m);
				}
			}
		}
		return lst;
	}
	
}

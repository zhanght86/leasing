package com.pqsoft.PaymentTerm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class PaymentTermService {

	String xmlPath = "PaymentTerm.";//xml路径
	
	/**
	 * 付款条件管理页
	 * @param
	 * @author 吴剑东
	 * @date 2014-4-1  下午01:19:30
	 */
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPaymentTermPageList", xmlPath+"getPaymentTermPageCount");
	}
	
	/**
	 * 添加付款条件
	 * @author 吴剑东
	 * @date 2014-4-1  下午02:43:59
	 */
	public boolean addPaymentTerm(Map<String, Object> param){
		String ID = Dao.getSequence("SEQ_T_SYS_PAYMENT_TERM");
		param.put("ID", ID);
		return Dao.insert(xmlPath+"addPaymentTerm", param) > 0;
	}
	
	/**
	 * 删除付款条件
	 * @paramID 付款条件ID
	 * @author 吴剑东
	 * @date 2014-4-1  下午02:56:29
	 */
	public boolean deletePaymentTerm(Map<String, Object> param){
		return Dao.delete(xmlPath+"deletePaymentTerm", param) > 0;
	}
	/**
	 * 查看付款条件详细
	 * @paramID 付款条件ID
	 * @author 吴剑东
	 * @date 2014-4-1  下午03:11:41
	 */
	public Map<String,Object> selectPaymentTermDetails(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"selectPaymentTermDetails", param);
	}
	
	/**
	 * 修改付款条件
	 * @paramID 付款条件ID
	 * @author 吴剑东
	 * @date 2014-4-1  下午03:18:44
	 */
	public boolean updatePaymentTerm(Map<String, Object> param){
		return Dao.update(xmlPath+"updatePaymentTerm", param) > 0;
	}
	
	/**
	 * SQL解析
	 * @param sql
	 * @author 吴剑东
	 * @date 2014-4-1  下午07:41:56
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

	public boolean checkTermName(Map<String, Object> param) {
		return (Integer)Dao.selectOne(xmlPath+"checkTermName", param)>0?false:true;
	}
	
}

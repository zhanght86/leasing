package com.pqsoft.Funds_Whole.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class Funds_WholeService<SEQ_RE_FUND_WHOLE> {

	private final String xmlPath="Funds_Whole.";
	
	/**
	 * 资金统筹
	 * @author 吴剑东
	 * 管理页面查询
	 */
	public Page pageTemplate(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath + "pageTemplate", xmlPath + "pageTemplate_count");
	}
	
	/**
	 * 资金统筹
	 * @author 吴剑东
	 * 2014-03-11 
	 * 保存资金统筹
	 */
	public Object saveFundsWhole(Map<String, Object> m) {
		String ID=Dao.getSequence("SEQ_T_REFUND_FUNDSWHOLE");
		m.put("ID", ID);
		Dao.insert(xmlPath+"saveFundsWhole",m);
		return ID;
	}
	/**
	 * 资金统筹
	 * @author 吴剑东
	 * 2014-03-11 
	 * 保存资金统筹清单
	 */
	public Object saveFundsWholeList(Map<String, Object> m) {
		return Dao.insert(xmlPath+"saveFundsWholeList",m);
	}
	/**
	 * 资金统筹
	 * @author 吴剑东
	 * 2014-03-11 
	 * 查看资金统筹
	 */
	public Map<String,Object> getFundsWhole(Map<String, Object> m) {
		return Dao.selectOne(xmlPath+"getFundsWhole",m);
	}
	/**
	 * 资金统筹
	 * @author 吴剑东
	 * 2014-03-11 
	 * 查看资金统筹清单
	 */
	public List<Map<String,Object>> getFundsWholeList(Map<String, Object> m) {
		return Dao.selectList(xmlPath+"getFundsWholeList",m);
	}
	
	/**
	 * 资金统筹项目数据
	 * @author 吴剑东
	 * 2014-03-11 
	 * 查看资金统筹清单
	 */
	public Page queryFundsWholeProjectList(Map<String, Object> m) {
		Page page = new Page(m);
		page.addDate(JSONArray.fromObject(Dao.selectList(xmlPath + "queryFundsWholeProjectList",m)) , 10);
		return page;
	}

	public Map<String,Object> queryFundsWholeInfo(Map<String, Object> m) {
		return Dao.selectOne(xmlPath+"queryFundsWholeInfo", m);
	}

	public boolean saveFundsWholeInfo(Map<String, Object> m) {
		String ID = Dao.getSequence("SEQ_RE_FUND_WHOLE");
		m.put("HEAD_ID", ID);
		Dao.insert(xmlPath+"saveFundsWholeInfo",m);
		return Dao.insert(xmlPath+"saveFundsWholeProInfo",m)>0?true:false;
	}

	public boolean deleteFundsWhole(Map<String, Object> m) {
		Dao.delete(xmlPath+"deleteFundsWholeDetail",m);
		return Dao.delete(xmlPath+"deleteFundsWhole",m)>0?true:false;
	}
}
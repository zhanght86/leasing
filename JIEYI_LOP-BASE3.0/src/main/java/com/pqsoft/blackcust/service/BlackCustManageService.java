package com.pqsoft.blackcust.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

/**
 * @author caizhongyang
 *
 */
public class BlackCustManageService {

	private String xmlPath = "blackcust.";
	/*
	 * 分页查询
	 */
	public Page getPage(Map<String, Object> param) {
		Page page=PageUtil.getPage(param, xmlPath+"getBlackCustDataList", xmlPath+"getBlackCustDataCount");
		return page;
	}

	/*
	 * 增加黑名单
	 */
	public boolean addBlackCust(Map<String, Object> param) {
		boolean flag=true;
		int i=Dao.insert(xmlPath+"saveBlackCust", param);
		if(i<0){
			flag=false;
		}
		return flag;
	}
	/*
	 * 查看黑名单
	 */
	public List<Object> queryBlackCustById(Map<String, Object> param) {
		return Dao.selectList(xmlPath+"queryBlackCustById",param);
	}
	/*
	 * 移除黑名单
	 */
	public boolean cancelBlackCust(Map<String, Object> param) {
		int i=Dao.update(xmlPath+"cancelBlackCust",param);
		JSONObject jsonObject=JSONObject.fromObject(Dao.selectList(xmlPath+"queryBlackCustById",param).get(0));
		param.put("jsonObject", jsonObject);
		//JSONObject jsonObject1=JSONObject.fromObject(Dao.selectList(xmlPath+"queryBlackCustHistoryByIdCardNo",param).get(0));
//		int CODENUM=jsonObject1.getInt("CODENUM");
//		if(jsonObject1.size()>0){
//			param.put("CODENUM", CODENUM+1);
//			Dao.update("updateBlackCustHistoryById",param);
//		}else{
//			param.put("CODENUM", CODENUM);
			Dao.insert(xmlPath+"saveBlackCustHistory",param);
//		}
		return true;
	}
	
	/*
	 * 查看黑名单
	 */
	public boolean updateBlackCust(Map<String, Object> param) {
		boolean flag=true;
		int i=Dao.update(xmlPath+"updateBlackCust", param);
		if(i<0){
			flag=false;
		}
		return flag;
	}
	
	/*
	 * 单条查看
	 */
	public Map<String,Object> queryContractProjectById(Map<String, Object> param) {
		param.put("tag1", "设备单位");
		param.put("tag2", "支付方式");
		param.put("tag3", "租赁期满处理方式");
		Map<String,Object> map=new HashMap<String, Object>();
		//获取方案明细
		map.put("queryProjectScheme", Dao.selectList(xmlPath+"queryProjectSchemeByProjectId", param).get(0));
		//获取设备列表
		map.put("queryEquiment", Dao.selectList(xmlPath+"queryEquimentByProjectId", param));
		//获取承租人信息
		//map.put("queryCustClient", Dao.selectList(xmlPath+"queryCustClientByClientId", param).get(0));
		//获取租赁物价总价值
		map.put("queryEqCOUNT", Dao.selectOne(xmlPath+"queryEqCOUNT", param));
		//map.put("queryContract", Dao.selectList(xmlPath+"queryContractByProjectId",param));
		return map;
	}

	/*
	 * 数据字典
	 */
	public List<Object> queryDataDictionary(String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("TYPE", type);
		List<Object> list =  Dao.selectList(xmlPath+"queryDataDictionary", param);
		return list;
	}
	
	public boolean updateProjectCacelType(Map<String, Object> param) {
		boolean flag=true;
		int number=Dao.update(xmlPath+"updateProjectCacelType", param);
		if(number < 1){
			flag=false;
		}
		return flag;
	}
	
}

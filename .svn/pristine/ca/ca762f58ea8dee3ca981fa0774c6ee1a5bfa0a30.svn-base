package com.pqsoft.contractProjectManage.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

/**
 * 明细查看
 * @author caizhongyang
 *
 */
public class ContractProjectManageService {

	private String xmlPath = "contractProjectManage.";
	/*
	 * 分页查询
	 */
	public Page getPage(Map<String, Object> param) {
		param.put("tag1", "业务类型");
		param.put("tag2", "项目状态位");
		param.put("tag3", "客户类型");
//		System.out.println("getPage......");
		Page page=PageUtil.getPage(param, xmlPath+"queryContractProjectList", xmlPath+"queryContractProjectCount");
		return page;
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
		param.put("CLIENT_ID", param.get("client_id"));
		map.put("queryCustClient", Dao.selectList(xmlPath+"queryCustClientByClientId", param).get(0));
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
	
	/*
	 * 数据字典site
	 */
	public List<Object> querySiteDictionary(String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("TYPE", type);
		List<Object> list =  Dao.selectList(xmlPath+"querySiteDictionary", param);
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

	public Object getCacelTypeProjectById(Map<String, Object> param){
		Integer cacelType=Dao.selectOne(xmlPath+"getCacelTypeProjectById", param);
		return cacelType;
	}
}

package com.pqsoft.base.channel.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;

public class ChannelApplyDanService {
private String basePath = "ChannelApplyDan.";

	/**
	 * 申请单分页查询
	 */
	public Page getApplyPage(Map<String,Object> param){
		Page page = new Page(param);
//		Map<String,Object> SUP_MAP = BaseUtil.getSup();
//		if(SUP_MAP!=null){
//			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
//		}
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getApplyDanList",param));
		page.addDate(array, Dao.selectInt(basePath+"getApplyDanCount", param));
		return page;
	}

	
	/**
	 * 获取单条申请单信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getApplyDan(Map<String,Object> param){
		param.put("DIC_TYPE", "供应商授信状态");
		return Dao.selectOne(basePath+"getOneApplyDan", param);
	}
}

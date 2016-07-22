package com.pqsoft.api.datalist.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class DataMaterialService {
	
	private static final String PATH = "dataMaterial.";
	public Page queryMainPage(Map<String, Object> params) {
		params.put("QZJD", "权证阶段");
		params.put("YWLX", "业务类型");
		params.put("KHLX", "客户类型");
		params.put("SYB", "事业部");
		return PageUtil.getPage(params, PATH + "mainPage", PATH + "mainPageCount");
	}

	public List<Map<String, Object>> queryRecords(Map<String, Object> params) {

		List<Map<String, Object>> records = Dao.selectList(PATH + "queryRecords", params);
		return records;
	}

	public int updateRecord(Map<String, Object> params) {
		JSONObject json = JSONObject.fromObject(params.get("param"));
		List<Map<String, Object>> factors = json.getJSONArray("data1");
		List<Map<String, Object>> qingdanList = json.getJSONArray("data2");
		Map<String, Object> param = new HashMap<String,Object>();
		
		param.put("ID", json.get("ID"));
		param.put("PHASE", json.get("PHASE"));
		param.put("MEMO", json.get("MEMO"));
		
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", json.get("ID"));
		//Dao.delete(PATH + "delFactor", p1);
		Dao.delete(PATH + "delFile", p1);
		for (Map<String, Object> f : factors) {
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("MM_ID", json.get("ID"));
			if( f.get("FACTOR_DICT_ID")!=null && f.get("FACTOR_SYS")!=null){
				p2.put("FACTOR_DICT_ID", f.get("FACTOR_DICT_ID"));
				p2.put("FACTOR_SYS", f.get("FACTOR_SYS"));
				Dao.insert(PATH + "insertFactor", p2);
			}
		}
		for (Map<String, Object> obj : qingdanList) {
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("MM_ID", json.get("ID"));
			p2.putAll(obj);
			Dao.insert(PATH + "insertFile", p2);
		}
		return Dao.update(PATH + "updateRecord", param);
	}

	@SuppressWarnings("unchecked")
	public String insertRecord(Map<String, Object> params) {
		JSONObject json = JSONObject.fromObject(params.get("param"));
		List<Map<String, Object>> list = json.getJSONArray("data1");
		List<Map<String, Object>> list1 = json.getJSONArray("data2");
		Map<String, Object> param = new HashMap<String,Object>();
		System.out.println("=================================================");
		System.out.println(list);
		System.out.println("=================================================");
		System.out.println(list1);
		//插入资料主记录表
		String id = Dao.getSequence("SEQ_DATA_LIST");
		param.put("ID", id);
		param.put("PHASE", json.get("PHASE"));
		param.put("MEMO", json.get("MEMO"));
//		param.put("WARRANT_GRADE", json.get("WARRANT_GRADE"));
//		param.put("WARRANT_TYPE", json.get("WARRANT_TYPE"));
//		param.put("WARRANT_NAME", json.get("WARRANT_NAME"));
//		param.put("MARRIAGE_SITUATION", json.get("MARRIAGE_SITUATION"));
//		param.put("CUSTOMER_TYPE", json.get("CUSTOMER_TYPE"));
		
		System.out.println("==yc==="+param);
		Dao.insert(PATH + "insertRecord", param);
		
		//插入资料条件记录
		for(Map<String,Object> obj : list){
			if(obj.size()>0){
				param.putAll(obj);
				param.put("MM_ID",id);
				Dao.insert(PATH + "insertFactor", param);
			}
		}
		
		//插入资料资料记录
		Map<String,Object> ziliao = new HashMap<String,Object>();
		for(Map<String,Object> obj : list1){
			ziliao.putAll(obj);
			ziliao.put("MM_ID", id);
			Dao.insert(PATH + "insertFile", ziliao);
		}
		
//		for (Map<String, Object> f : (List<Map<String, Object>>) params.get("fs")) {
//			Map<String, Object> p2 = new HashMap<String, Object>();
//			p2.put("MM_ID", params.get("ID"));
//			p2.put("FACTOR_DICT_ID", f.get("FACTOR_DICT_ID"));
//			p2.put("FACTOR_SYS", f.get("FACTOR_SYS"));
//			Dao.insert(PATH + "insertFactor", p2);
//		}
//		for (String fileId : (String[]) params.get("FILES")) {
//			Map<String, Object> p1 = new HashMap<String, Object>();
//			p1.put("MM_ID", id);
//			p1.put("FILE_DICT_ID", fileId);
//			Dao.insert(PATH + "insertFile", p1);
//		}
		
		return id;
	}

	public int delRecord(Map<String, Object> params) {
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", params.get("ID"));
		Dao.delete(PATH + "delFactor", p1);
		Dao.delete(PATH + "delFile", p1);
		return Dao.delete(PATH + "delRecord", params);
	}

	public Map<String, Object> loadRecord(Map<String, Object> params) {
		// TODO
		List<Map<String, Object>> recs = Dao.selectList(PATH + "queryRecords", params);
		Map<String, Object> rec = recs.get(0);
		List<Map<String, Object>> factors = Dao.execSelectSql("SELECT FACTOR_DICT_ID, FACTOR_SYS FROM MATERIAL_MGT_FACTOR WHERE MM_ID="
				+ params.get("ID"));
		List<String> files = Dao.execSelectSql("SELECT FILE_DICT_ID,TYPE FROM MATERIAL_MGT_FILE WHERE MM_ID=" + params.get("ID"));
		rec.put("factors", factors);
		rec.put("files", files);
		return rec;
	}

}

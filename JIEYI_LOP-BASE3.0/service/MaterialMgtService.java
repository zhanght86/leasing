package com.pqsoft.leeds.materialMgt.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.ctc.wstx.util.StringUtil;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leeds.utils.VelocityUtil;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class MaterialMgtService {
	private static final String PATH = "materialMgt.";

	public Page queryMainPage(Map<String, Object> params) {
		return PageUtil.getPage(params, PATH+"mainPage", PATH+"mainPageCount");
	}
	
	public List<Map<String, Object>> queryRecords(Map<String, Object> params) {
		
		List<Map<String, Object>> records = Dao.selectList(PATH+"queryRecords", params);
		return records;
	}

	public void updateRecord(Map<String, Object> params) {
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", params.get("ID"));
		Dao.delete(PATH+"delFactor", p1);
		Dao.delete(PATH+"delFile", p1);
		
		for(Map<String, Object> f : (List<Map<String, Object>>)params.get("fs")) {
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("MM_ID", params.get("ID"));
			p2.put("FACTOR_DICT_ID", f.get("FACTOR_DICT_ID"));
			p2.put("FACTOR_SYS", f.get("FACTOR_SYS"));
			Dao.insert(PATH+"insertFactor", p2);
		}
		for(String fileId : (String[])params.get("FILES")) {
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("MM_ID", params.get("ID"));
			p2.put("FILE_DICT_ID", fileId);
			Dao.insert(PATH+"insertFile", p2);
		}
		Dao.update(PATH+"updateRecord", params);
	}

	public String insertRecord(Map<String, Object> params) {
		String id = Dao.getSequence("SEQ_MATERIAL_MGT");
		params.put("ID", id);
		Dao.insert(PATH+"insertRecord", params);
		for(Map<String, Object> f : (List<Map<String, Object>>)params.get("fs")) {
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("MM_ID", params.get("ID"));
			p2.put("FACTOR_DICT_ID", f.get("FACTOR_DICT_ID"));
			p2.put("FACTOR_SYS", f.get("FACTOR_SYS"));
			Dao.insert(PATH+"insertFactor", p2);
		}
		for(String fileId : (String[])params.get("FILES")) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			p1.put("MM_ID", id);
			p1.put("FILE_DICT_ID", fileId);
			Dao.insert(PATH+"insertFile", p1);
		}
		return id;
	}

	public int delRecord(Map<String, Object> params) {
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("MM_ID", params.get("ID"));
		Dao.delete(PATH+"delFactor", p1);
		Dao.delete(PATH+"delFile", p1);
		return Dao.delete(PATH+"delRecord", params);
	}

	public Map<String, Object> loadRecord(Map<String, Object> params) {
		// TODO
		List<Map<String, Object>> recs = Dao.selectList(PATH+"queryRecords", params);
		Map<String, Object> rec = recs.get(0);
		List<Map<String, Object>> factors = Dao.execSelectSql("SELECT FACTOR_DICT_ID, FACTOR_SYS FROM MATERIAL_MGT_FACTOR WHERE MM_ID="+params.get("ID"));
		List<String> files = Dao.execSelectSql("SELECT FILE_DICT_ID FROM MATERIAL_MGT_FILE WHERE MM_ID="+params.get("ID"));
		rec.put("factors", factors);
		rec.put("files", files);
		return rec;
	}
	
	public String[] getFileList(String phase, String proId) {
		List<Map<String, Object>> factorTypes =  new SysDictionaryMemcached().get("资料管理-条件类型");
		List<String> factorFlags = new ArrayList<String>();
		for(Map<String, Object> factorType : factorTypes) {
			//执行sql，拿到条件
			String sql = factorType.get("REMARK")==null?"":factorType.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", proId);
			List<Map<String, Object>> fields = null;
			try{
				fields= Dao.execSelectSql(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
			Map<String, Object> field = null;
			if(fields != null && fields.size()>=1) 
				field = fields.get(0);
			else continue;
			
			List<Map<String, Object>> factors = null;
			if(factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {
				factors = new SysDictionaryMemcached().get(factorType.get("CODE").toString());
			} else factors = new DataDictionaryMemcached().get(factorType.get("CODE").toString());
			for(Map<String, Object> factor : factors) {
				if(field.get("CODE").toString().equals(factor.get("CODE"))) factorFlags.add(factor.get("FLAG").toString());
			}
		}
		
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("PHASE_V", phase);
		p1.put("FACTORS", factorFlags);
		List<Map<String, Object>> res = Dao.selectList(PATH+"getFileList", p1);
		if(res != null && res.size()>=1) {
			Map<String, Object> re = res.get(0);
			return re.get("FACTORS").toString().split(",");
		}
		return new String[]{};
	}
}

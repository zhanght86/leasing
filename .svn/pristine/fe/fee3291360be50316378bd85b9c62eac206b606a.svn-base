package com.pqsoft.leeds.talkSkill.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.ctc.wstx.util.StringUtil;
import com.pqsoft.leeds.utils.VelocityUtil;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class TalkSkillService {
	private static final String PATH = "talkSkill.";

	public Page queryMainPage(Map<String, Object> params) {
		return PageUtil.getPage(params, PATH+"mainPage", PATH+"mainPageCount");
	}
	
	public List<Map<String, Object>> queryRecords(Map<String, Object> params) {
		List<Map<String, Object>> records = Dao.selectList(PATH+"queryRecords", params);
		return records;
	}

	public void updateRecord(Map<String, Object> params) {
		Dao.update(PATH+"updateRecord", params);
	}

	public String insertRecord(Map<String, Object> params) {
		String id = Dao.getSequence("SEQ_TALK_SKILL");
		params.put("ID", id);
		Dao.insert(PATH+"insertRecord", params);
		return id;
	}

	public int delRecord(Map<String, Object> params) {
		return Dao.delete(PATH+"delRecord", params);
	}
	
	/**
	 * @desc
	 * 1、针对一条话术模板记录record，根据所提供的参数params，执行record.FIELD_SQL，将record.CONTENT中的文本域填充.<br>
	 * 2、返回填充后record
	 * @author leeds</a>
	 * @time 2015年2月28日 下午1:57:28
	 */
	public Map<String, Object> getMergeRecord(Map<String, Object> record, Map<String, Object> params) {
		String sql = record.get("FIELD_SQL")==null ? "" : record.get("FIELD_SQL").toString();
		sql = VelocityUtil.printString(sql, params);
		List<Map<String, Object>> fields = null;
		try{
			fields= Dao.execSelectSql(sql);
		} catch(Exception e) {
//			e.printStackTrace();
		}
		String CONTENT_V = record.get("CONTENT")==null?"":record.get("CONTENT").toString();
		if(fields != null && fields.size()>=1&& fields.get(0)!=null) {
			String[] ss = com.pqsoft.leeds.utils.StringUtil.matchedSubStrs("\\$\\{\\w*\\}", CONTENT_V);
			for(String s : ss) {
				CONTENT_V = CONTENT_V.replace(s, s.toUpperCase());
			}
			CONTENT_V = VelocityUtil.printString(CONTENT_V, fields.get(0));
		}
		record.put("CONTENT", CONTENT_V);
		return record;
	}
	/**
	 * @desc
	 *根据项目id，拿到当前项目，要拿到的模板类型。
	 * @author leeds</a>
	 * @time 2015年3月2日 下午5:04:08
	 */
	public  List<Map<String, Object>> getTplTypes(Map<String, Object> params) {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		//拿承租人模板
		res.add((Map)Dao.selectOne(PATH+"getCustType", params));
		//拿共同承租人模板
		Map<String, Object> ct1 = Dao.selectOne(PATH+"getGtCustType", params);
		if(!ct1.get("CUST_TYPE").toString().equals("0")) res.add(ct1);
		//拿担保人模板
		Map<String, Object> ct2 = Dao.selectOne(PATH+"getDBRType", params);
		if(!ct2.get("CUST_TYPE").toString().equals("0")) res.add(ct2);
		return res;
	}
}

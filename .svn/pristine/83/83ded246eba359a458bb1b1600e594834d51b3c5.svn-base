package com.pqsoft.secuEvaluate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.PageTemplate;
import com.pqsoft.util.StringUtils;

public class GradeStandardService {

	public GradeStandardService() {
	}

	/**
	 * @Description: TODO 数据字典
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	public PageTemplate queryDataManager(Map<String, Object> m) {

		Object Curr = m.get("PAGE_CURR");
		Object count = m.get("PAGE_COUNT");
		int pageCurr = Integer.parseInt(Curr == null ? "1" : Curr.toString());
		int pageCount = Integer.parseInt(count == null ? "10" : count.toString());

		m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
		m.put("PAGE_END", pageCount * pageCurr);
		PageTemplate page = new PageTemplate(Dao.selectList("GradeStandard.getAllGradeStandard", m), pageCurr, pageCount, Dao.selectInt("GradeStandard.getAllGradeStandard_count", m), 5);
		return page;
	}

	public Page queryPage(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("GradeStandard.getAllGradeStandard", m);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map map = (Map) list.get(i);
			json.put("TYPE", map.get("TYPE"));
			json.put("STATUS", map.get("STATUS"));
			json.put("DESCRIBE", map.get("DESCRIBE"));
			json.put("MAIN_TYPE", map.get("MAIN_TYPE"));
			json.put("NUM", map.get("NUM"));
			array.add(json);
		}
		page.addDate(array, Dao.selectInt("GradeStandard.getAllGradeStandard_count", m));
		return page;
	}

	/**
	 * @Description: TODO 数据字典添加
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	@SuppressWarnings("unchecked")
	public int insertGradeStandard(JSONObject m) {
		List<Map<String, Object>> dataList = m.getJSONArray("RECORD_LIST");
		String type = m.get("RECORD_NAME").toString();
		String DESCRIBE = m.get("DESCRIBE").toString();
		String MAIN_TYPE = m.get("MAIN_TYPE").toString();
		String USER_CODE = m.get("USER_CODE").toString();
		for (Map<String, Object> map : dataList) {
			map.put("TYPE", type);
			map.put("USER_CODE", USER_CODE);
			map.put("DESCRIBE", DESCRIBE);
			map.put("MAIN_TYPE", MAIN_TYPE);
			Dao.insert("GradeStandard.insertGradeStandard", map);
		}
		return 1;
	}

	/**
	 * @Description: TODO 查看数据字典类型信息数量（启动）
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	public Object checkType(Map<String, Object> m) {
		return Dao.selectInt("GradeStandard.checkStandardType", m);
	}

	/**
	 * @Description: TODO 同一类型的数据删除
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	public int deleteDictionary(Map<String, Object> m) {
		return Dao.update("GradeStandard.deleteGradeStandard", m);
	}

	/**
	 * @Description: TODO 查看数据字典类型信息
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	public Object getDataTypeInfo(Map<String, Object> m) {
		return Dao.selectList("GradeStandard.getDataStandardTypeInfo", m);
	}

	/**
	 * @Description: TODO 类型作废与启动
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	public int invalidGradeStandard(Map<String, Object> m) {

		int i = Dao.delete("GradeStandard.invalidDataGradeStandard", m);
		return i;
	}

	/**
	 * @Description: TODO 修改数据字典类型信息
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	public int updateGradeStandard(JSONObject m) {
		List<Map<String, Object>> dataList = m.getJSONArray("RECORD_LIST");
		String type = m.get("RECORD_NAME").toString();
		String DESCRIBE = m.get("DESCRIBE").toString();
		String MAIN_TYPE = m.get("MAIN_TYPE").toString();
		String USER_CODE = m.get("USER_CODE").toString();
		for (Map<String, Object> map : dataList) {
			if (null == map.get("DATA_ID") || "".equals(map.get("DATA_ID"))) {
				map.put("USER_CODE", USER_CODE);
				map.put("DESCRIBE", DESCRIBE);
				map.put("MAIN_TYPE", MAIN_TYPE);
				map.put("TYPE", type);
				Dao.insert("GradeStandard.insertGradeStandard", map);
			} else {
				map.put("TYPE", type);
				map.put("USER_CODE", USER_CODE);
				map.put("MAIN_TYPE", MAIN_TYPE);
				map.put("DESCRIBE", DESCRIBE);
				Dao.update("GradeStandard.updateDataGradeStandard", map);
			}
		}

		return 1;
	}

	/**
	 * @memo 方法说明：根据类型获取数据字典的类型编号和标示
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static List<Object> queryGradeStandard(String name) {
		return Dao.selectList("GradeStandard.queryGradeStandardForName", name);
	}

	/**
	 * @memo 方法说明：根据类型获取数据字典的类型编号和标示
	 * @param DATATYPE
	 * @return
	 * @throws Exception
	 */
	public static List<Object> queryGradeStandard(Map<String, Object> m) {
		return Dao.selectList("GradeStandard.queryGradeStandard", m);
	}

	/**
	 * @memo 方法说明：根据类型和标示获取数据字典具体
	 * @param DATATYPE
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> queryGradeStandardByTypeAndCode(Map<String, Object> m) {
		return Dao.selectOne("GradeStandard.queryGradeStandardByTypeAndCode", m);
	}

	public static List<Map<String, Object>> queryGradeStandardByScheme(String TYPE) {
		return (ArrayList) Dao.selectList("GradeStandard.queryGradeStandardByScheme", TYPE);
	}

	/**
	 * 查询
	 * 
	 * @param TYPE
	 * @param CODE
	 * @return
	 * @author:King 2013-11-29
	 */
	public List<Map<String, Object>> queryGradeStandardByTypeAndCodes(String TYPE, String CODE) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		if(StringUtils.isBlank(CODE)||CODE.equals("null")){
			
		}else{
			if (CODE.contains(",")) {
				String[] CODES = CODE.split(",");
				for (String string : CODES) {
					sbf.append("'").append(string).append("',");
				}
	
				if (sbf.length() > 1) {
					map.put("CODE", sbf.substring(0, sbf.length() - 1) + "");
				}
			} else {
				map.put("CODE", sbf.append("'").append(CODE).append("'"));
			}
		}
		map.put("_TYPE", TYPE);
		return Dao.selectList("GradeStandard.queryGradeStandardByTypeAndCodes", map);
	}

	public List<Map<String, Object>> getTypesGradeStandard(Map<String, Object> m) {
		return Dao.selectList("GradeStandard.getTypesGradeStandard", m);
	}
}

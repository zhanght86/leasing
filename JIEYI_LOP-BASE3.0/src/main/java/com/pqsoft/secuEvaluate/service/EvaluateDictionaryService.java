package com.pqsoft.secuEvaluate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.PageTemplate;
import com.pqsoft.util.StringUtils;

public class EvaluateDictionaryService {

	private static Logger logger = Logger
			.getLogger(EvaluateDictionaryService.class);

	public EvaluateDictionaryService() {
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
		int pageCount = Integer.parseInt(count == null ? "10" : count
				.toString());

		m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
		m.put("PAGE_END", pageCount * pageCurr);
		PageTemplate page = new PageTemplate(
				Dao
						.selectList(
								"EvaluateDictionary.getAllEvaluateDictionary",
								m),
				pageCurr,
				pageCount,
				Dao.selectInt(
						"EvaluateDictionary.getAllEvaluateDictionary_count", m),
				5);
		return page;
	}

	public Page queryPage(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList(
				"EvaluateDictionary.getAllEvaluateDictionary", m);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map map = (Map) list.get(i);
			json.put("TRADE_TYPE", map.get("TRADE_TYPE")); // Add By YangJ
			// 2014-5-29
			// 下午03:33:23
			json.put("TYPE", map.get("TYPE"));
			json.put("STATUS", map.get("STATUS"));
			json.put("DESCRIBE", map.get("DESCRIBE"));
			json.put("MAIN_TYPE", map.get("MAIN_TYPE"));
			json.put("NUM", map.get("NUM"));
			array.add(json);
		}
		page.addDate(array, Dao.selectInt(
				"EvaluateDictionary.getAllEvaluateDictionary_count", m));
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
	public int insertEvaluateDictionary(JSONObject m) {
		List<Map<String, Object>> dataList = m.getJSONArray("RECORD_LIST");
		System.out.println("保存定量打分项 dataList" + dataList);
		String type = m.get("RECORD_NAME").toString();
		String DESCRIBE = m.get("DESCRIBE").toString();
		String MAIN_TYPE = m.get("MAIN_TYPE").toString().equals("LP")?"2":"1";
		System.out.println(" dataList--》" + dataList + "  size: "
				+ dataList.size());
		String TRADE_TYPE = m.get("TRADE_TYPE").toString();
		String USER_CODE = m.get("USER_CODE").toString();

		for (Map<String, Object> map : dataList) {
			map.put("TYPE", type);
			map.put("USER_CODE", USER_CODE);
			map.put("DESCRIBE", DESCRIBE);
			map.put("MAIN_TYPE", MAIN_TYPE);
			map.put("TRADE_TYPE", TRADE_TYPE);
			System.out.println("保存定量打分项 map" + map);
			Dao.insert("EvaluateDictionary.insertEvaluateDictionary", map);
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
	public int checkType(Map<String, Object> m) {
		Object TYPE = m.get("TYPE");
		Object MAIN_TYPE = m.get("MAIN_TYPE").toString().equals("LP")?"2":"1";
		String TRADE_TYPES = m.get("TRADE_TYPE").toString();
		int count = 0;
		for (String TRADE_TYPE : TRADE_TYPES.split(",")) {
			Map<String,Object> refer = new HashMap<String,Object>() ;
			refer.put("TYPE", TYPE);
			refer.put("MAIN_TYPE", MAIN_TYPE);
			refer.put("TRADE_TYPE", TRADE_TYPE);
			count = Dao.selectInt("EvaluateDictionary.checkEvaluateType", refer);
			if (count > 0)
				break;
		}

		return count;
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
		return Dao.update("EvaluateDictionary.deleteEvaluateDictionary", m);
	}

	/**
	 * 作者：杨杰 邮箱 ：bingyyf@foxmail.com 时间：2014-6-3 下午03:31:49 qwe
	 * 位置：SKYEYE-BASE3.0 com.pqsoft.secuEvaluate.service
	 */
	public int delDictionary(Map<String, Object> m) {
		return Dao.update("EvaluateDictionary.delEvaluateDictionary", m);
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
		 if(m.get("TRADE_TYPE").equals("undefined"))
		 m.put("TRADE_TYPE","");
		return Dao.selectList("EvaluateDictionary.getDataEvaluateTypeInfo", m);
	}

	/**
	 * @Description: TODO 类型作废与启动
	 * @author 齐姜龙
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2013-8-5
	 */
	public int invalidEvaluateDictionary(Map<String, Object> m) {

		int i = Dao.delete("EvaluateDictionary.invalidDataEvaluateDictionary",
				m);
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
	public int updateEvaluateDictionary(JSONObject m) {
		List<Map<String, Object>> dataList = m.getJSONArray("RECORD_LIST");
		String type = m.get("RECORD_NAME").toString();
		String DESCRIBE = m.get("DESCRIBE").toString();
		String MAIN_TYPE = m.get("MAIN_TYPE").toString();
		String TRADE_TYPE = m.get("TRADE_TYPE").toString();
		String USER_CODE = m.get("USER_CODE").toString();
		for (Map<String, Object> map : dataList) {
			if (null == map.get("DATA_ID") || "".equals(map.get("DATA_ID"))) {
				map.put("USER_CODE", USER_CODE);
				map.put("DESCRIBE", DESCRIBE);
				map.put("MAIN_TYPE", MAIN_TYPE);
				map.put("TRADE_TYPE", TRADE_TYPE);
				map.put("TYPE", type);
				Dao.insert("EvaluateDictionary.insertEvaluateDictionary", map);
			} else {
				map.put("TYPE", type);
				map.put("USER_CODE", USER_CODE);
				map.put("MAIN_TYPE", MAIN_TYPE);
				map.put("TRADE_TYPE", TRADE_TYPE);
				map.put("DESCRIBE", DESCRIBE);
				Dao.update("EvaluateDictionary.updateDataEvaluateDictionary",
						map);
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
	public static List<Object> queryEvaluateDictionary(String name) {
		return Dao.selectList(
				"EvaluateDictionary.queryEvaluateDictionaryForName", name);
	}

	/**
	 * @memo 方法说明：根据类型获取数据字典的类型编号和标示
	 * @param DATATYPE
	 * @return
	 * @throws Exception
	 */
	public static List<Object> queryEvaluateDictionary(Map<String, Object> m) {
		return Dao.selectList("EvaluateDictionary.queryEvaluateDictionary", m);
	}

	/**
	 * @memo 方法说明：根据类型和标示获取数据字典具体
	 * @param DATATYPE
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> queryEvaluateDictionaryByTypeAndCode(
			Map<String, Object> m) {
		return Dao.selectOne(
				"EvaluateDictionary.queryEvaluateDictionaryByTypeAndCode", m);
	}

	public static List<Map<String, Object>> queryEvaluateDictionaryByScheme(
			String TYPE) {
		return (ArrayList) Dao.selectList(
				"EvaluateDictionary.queryEvaluateDictionaryByScheme", TYPE);
	}

	/**
	 * 查询
	 * 
	 * @param TYPE
	 * @param CODE
	 * @return
	 * @author:King 2013-11-29
	 */
	public List<Map<String, Object>> queryEvaluateDictionaryByTypeAndCodes(
			String TYPE, String CODE) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sbf = new StringBuffer();
		if (StringUtils.isBlank(CODE) || CODE.equals("null")) {

		} else {
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
		return Dao
				.selectList(
						"EvaluateDictionary.queryEvaluateDictionaryByTypeAndCodes",
						map);
	}

	public List<Map<String, Object>> getTypesEvaluateDictionary(
			Map<String, Object> m) {
		return Dao.selectList("EvaluateDictionary.getTypesEvaluateDictionary",
				m);
	}

	public List<Map<String, Object>> getTypesGradeMolud(Map<String, Object> m) {
		return Dao.selectList("EvaluateDictionary.getTypesGradeMolud", m);
	}
}

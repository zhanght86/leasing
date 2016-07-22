package com.pqsoft.screened.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class FinanceScreenService {

	private final String path = "financeScreen.";

	public Page getDataPage(Map<String, Object> map) {
		return PageUtil.getPage(map, path + "getFinanceScreenList", path
				+ "getFinanceScreenCount");
	}

	/**
	 * 复制支付表 transplantsRefundPay
	 * 
	 * @date 2013-10-17 上午10:58:49
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int transplantsRefundPay(Map<String, Object> map) {
		return Dao.insert(path + "transplantsRefundPayByProjectid", map);
	}

	/**
	 * 复制设备 transplantsRefundPayEquipment
	 * 
	 * @date 2013-10-17 上午11:00:37
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void transplantsRefundPayEquipment(Map<String, Object> map) {
		Dao.insert(path + "transplantsRefundPROEquipment", map);
	}

	/**
	 * 融资机构每个项目的融资基数 addOrganrefund
	 * 
	 * @date 2013-10-17 下午12:03:32
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void addOrganrefund(Map<String, Object> map) {
		Dao.insert(path + "addOrganrefundByJGID", map);
	}

	public int insertProjectDetailByProids(Map<String, Object> map) {
		return Dao.insert(path + "insertProjectDetailByProids", map);
	}

	/**
	 * 解析融资条件
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月19日
	 */
	public List<Map<String, Object>> queryRZTJ(Map<String, Object> map) {
		List<Map<String, Object>> rstList = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = Dao.selectOne(path + "queryRZTJ", map);
		JSONArray jsonList = JSONArray.fromObject(m.get("CONDITIONONE"));
		List<Map<String, Object>> list = jsonList;
		boolean flag = true;
		for (Map<String, Object> map2 : list) {
			map2.putAll(map);
			map2.put("CONDITIONID", map2.get("contionId"));
			Map<String, Object> ma = Dao.selectOne(path + "queryTJINFO", map2);
			if (StringUtils.isBlank(ma)||"0".equals(ma.get("COUNT").toString().trim()))
				flag = false;
			ma.put("FLAG", flag);
			rstList.add(ma);
		}
		map.put("FLAG", flag);
		return rstList;
	}
}

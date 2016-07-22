package com.pqsoft.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/*********融资档案管理**@auth: king 2014年9月22日 *************************/
public class DossierFinanceService {
	
	private String namespace="dossierFinance.";
	
	/**
	 * 查询融资档案列表数据
	 * @param map
	 * @return
	 * @author King 2014年9月26日
	 */
	public Page doMgDossierFinance(Map<String,Object> map){
		map.put("TYPE", "再融资方式");
		return PageUtil.getPage(map, namespace+"toMgProjectData", namespace+"toMgProjectCount");
	}
	
	/**
	 * 查看项目下所包含的合同信息
	 * getProjectPayList
	 * @date 2013-10-17 下午07:55:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> getProjectPayList(Map<String, Object> m) {
		List<Map<String,Object>> list= Dao.selectList(namespace+"toShowProPay", m);
		List<Map<String,Object>> rstList=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : list) {
			map.put("PAY_WAY", m.get("PAY_WAY"));
			map.put("FID",  m.get("FID"));
			map.put("CLIENT_NAME",  map.get("NAME"));
			rstList.addAll(this.queryRZTJ(map));
		}
		return rstList;
	}
	
	/**
	 * 解析融资条件
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月19日
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryRZTJ(Map<String, Object> map) {
		List<Map<String, Object>> rstList = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = Dao.selectOne(namespace + "queryRZTJ", map);
		JSONArray jsonList = JSONArray.fromObject(m.get("CONDITIONONE"));
		List<Map<String, Object>> list = jsonList;
		boolean flag = true;
		for (Map<String, Object> map2 : list) {
			map2.putAll(map);
			map2.put("CONDITIONID", map2.get("contionId"));
			Map<String, Object> ma = Dao.selectOne(namespace + "queryTJINFO", map2);
			if (StringUtils.isBlank(ma)||"0".equals(ma.get("COUNT").toString().trim()))
				flag = false;
			ma.put("FLAG", flag);
			ma.putAll(map);
			rstList.add(ma);
		}
		map.put("FLAG", flag);
		return rstList;
	}
}

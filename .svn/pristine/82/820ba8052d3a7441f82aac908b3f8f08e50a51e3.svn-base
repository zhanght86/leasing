package com.pqsoft.zcfl.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class AssetsInitialLevelService {
	
	ClassificationService service = new ClassificationService();
	
	public void geIinetialDataList() {
		String id = Dao.selectOne("AssetsInitialLevel.getZCT_ID",null);
		Map<String,Object> cptidMap = new HashMap<String,Object>();
		cptidMap.put("CTP_ID", id);
		List<Map<String,Object>> pdList = Dao.selectList("AssetsInitialLevel.getPdList");
		if(pdList.size()==0){
			List<Map<String,Object>> list = Dao.selectList("AssetsInitialLevel.getAssetsInitialLevelList");//查逾期数据
			for (Map<String, Object> map : list) {
				Map<String, Object> resultMap = null;
				try {
					resultMap = service.execSystem(map.get("PAYLIST_CODE").toString(), cptidMap);
				} catch (Exception e) {
					e.printStackTrace();
				}//执行系统打分  支付表ID ， 模版ID
				map.put("ZAIL_ID", Dao.getSequence("SEQ_ZCFL_ASSETS_INITIAL_LEVEL"));
				map.put("ZAIL_PAY_ID", map.get("ID"));
				map.put("ZAIL_PAY_CODE", map.get("PAYLIST_CODE"));
				map.put("ZAIL_DATE", new Date());
				map.put("ZAIL_DAYS", map.get("OVERDUE_DAYS"));
				map.put("ZAIL_LEVEL", resultMap.get("RESULT"));
				Dao.insert("AssetsInitialLevel.addAssetsInitialLevelList", map);
			}
		}
	}
}

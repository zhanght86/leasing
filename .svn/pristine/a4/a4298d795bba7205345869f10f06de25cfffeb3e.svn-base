package com.pqsoft.zhongjin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import payment.tools.util.GUID;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.FIinterface;
import com.pqsoft.util.PageUtil;

public class ZhongjinLogService {

	private String basePath = "zhongjinLog.";
	
	public Page getPage(Map<String,Object> param){
		param.put("TYPE", "1");
		return PageUtil.getPage(param, basePath+"getZhongjinLogPage", basePath+"getZhongjinLogCount");
	}
	
	public Page daikouPageData(Map<String,Object> param){
		param.put("TYPE", "2");
		return PageUtil.getPage(param, basePath+"getZhongjinLogPage", basePath+"getZhongjinLogCount");
	}
	
	public List<Map<String,Object>> selectZhongjinLogById(Map<String,Object> param){
		return Dao.selectList(basePath+"selectZhongjinLogById", param);
	}
	
	public void updateZhongjin(Map<String,Object> param){
		Dao.update(basePath+"updateZhongjin", param);
	}
	
	public int daikouRestart(Map<String,Object> param){
		try {
			String batchNo = GUID.getTxNo();
			param.put("BATCH_NO", batchNo);
			int i = Dao.update(basePath+"updateBatchNo", param);
			if(i>0){
				List<Map<String,Object>> batchList = new ArrayList<Map<String,Object>>();
				Map<String,Object> zhongjin = Dao.selectOne(basePath+"selectZhongjinLogById", param);
				batchList.add(zhongjin);
				Map<String,Object> mapTitle = new HashMap<String, Object>();
				mapTitle.put("TOTAL_SUM", zhongjin.get("AMOUNT"));
				mapTitle.put("TOTAL_ITEM", 1);
				if("2".equals(zhongjin.get("TYPE"))){//2:代扣
					new FIinterface().daikou(batchList, mapTitle,"2");
				}else if("1".equals(zhongjin.get("TYPE"))||"5".equals(zhongjin.get("TYPE"))){//1代付，5服务费代付
					new FIinterface().daifu(batchList, mapTitle, zhongjin.get("TYPE").toString());
				}
			}
			Dao.commit();
			return i;
		} catch (Exception e) {
			Dao.rollback();
			e.printStackTrace();
		} finally {
			Dao.close();
		}
		return 0;
	}
}

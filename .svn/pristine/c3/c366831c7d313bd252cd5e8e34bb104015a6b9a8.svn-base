package com.pqsoft.adjustRate.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

public class AdjustRateThread implements Runnable {
	private AdjustRateService service = new AdjustRateService();
	private Map<String,Object> map;
	private Map<String, Object> rate;
	
	
	public Map<String, Object> getRate() {
		return rate;
	}

	public void setRate(Map<String, Object> rate) {
		this.rate = rate;
	}

	public AdjustRateThread(Map<String,Object> map,Map<String,Object> rate){
		this.setMap(map);
		this.setRate(rate);
	}
	
	
	public Map<String, Object> getMap() {
		return map;
	}



	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public void run() {
		try{
		if (service.isCheckFactor(map)) {
			if (StringUtils.isBlank(map.get("start_term"))) {
				map.put("RESULT_MSG", "无对应的调息开始期次,或租金计划已还清");
				map.put("TYPE_", "1");
			} else if ("1".equals(map.get("PAY_WAY").toString().trim())
					|| "4".equals(map.get("PAY_WAY").toString().trim())) {
				// 期初、期末等额本息
				service.adjustRateByPmt(map, rate,
						Integer.parseInt(map.get("start_term") + ""));
			} else if ("2".equals(map.get("PAY_WAY").toString().trim())
					|| "3".equals(map.get("PAY_WAY").toString().trim())) {
				// 不等额
				service.adjustRateByBDE(map, rate,
						Integer.parseInt(map.get("start_term") + ""));
			} else {
				// 不支持的支付方式
				map.put("RESULT_MSG", "无支持的支付方式,支付方式标识为" + map.get("PAY_WAY"));
				map.put("TYPE_", "1");
			}
		}
		map.put("VERSION_CODE", Integer.parseInt(map.get("VERSION_CODE")+"")+1);
		service.addFIL_RENT_UPDATE_INTEREST(map);
		Dao.commit();
		}catch(Exception e){
			e.getMessage();
			Dao.rollback();
		}
		Dao.close();
		
	}
}

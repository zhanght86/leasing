package com.pqsoft.performance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

public class RefreshAssessmentDataJobs {

	String xmlPath = "performance.RAD.";//xml路径
	
	public void refreshAssessmentData(){
		//1.查询符合刷数据的模版
		List<Map<String,Object>> mbList = Dao.selectList(xmlPath + "getMbList");
		for (int i = 0; i < mbList.size(); i++) {
			Map<String,Object> mbMap = mbList.get(i);
			//2.查询模版对应的所有人员
			List<Map<String,Object>> userList = Dao.selectList(xmlPath + "getMbAllUser", mbMap);
			//3.根据人查询考核项结果
			for (int j = 0; j < userList.size(); j++) {
				Map<String,Object> userMap = userList.get(j);
				Map<String,Object> judageParam = new HashMap<String,Object>();
				judageParam.put("TAC_NAME", mbMap.get("NAME"));
				judageParam.put("ID", userMap.get("ID"));
				List<Map<String,Object>> judgeDMU = Dao.selectList(xmlPath + "selectRESULT", judageParam);
				if(judgeDMU == null || judgeDMU.size() <= 0){//同一天同一个模版同一个人只能刷一次数据
					//(1)查询该模版下的所有考核项
					List<Map<String,Object>> khxList = Dao.selectList(xmlPath + "getAllKhx", mbMap);
					Double jxzje = 0.0;
					for (int j1 = 0; j1 < khxList.size(); j1++) {
						Map<String,Object> khxMap = khxList.get(j1);
						//(2)拼sql
						String EXESQL = khxMap.get("SQL").toString()
									 .replace("{USERID}",userMap.get("ID").toString())
									 .replace("{START_DATE}",mbMap.get("START_DATE").toString())
									 .replace("{END_DATE}",mbMap.get("END_DATE").toString());
						//(3)用sql去查询考核项结果
						Map<String, Object> mm = new HashMap<String, Object>();
						mm.put("EXESQL",EXESQL);
						String result = Dao.selectOne(xmlPath + "getResult", mm);
						//4.根据考核项结果匹配出绩效金额 
						//(1)查询考核项条款区间
						khxMap.put("AC_ID", mbMap.get("ID"));
						List<Map<String,Object>> tjqjList = Dao.selectList(xmlPath + "getAllTjqj", khxMap);
						Double khxjxjj = 0.0;
						for (int k = 0; k < tjqjList.size(); k++) {
							boolean flag = false;
							Map<String,Object> tjqjMap = tjqjList.get(k);
							if(tjqjMap.containsKey("EQUAL_VALUE") && !tjqjMap.get("EQUAL_VALUE").equals("")){
								try {
									if(Double.parseDouble(result) == Double.parseDouble(tjqjMap.get("EQUAL_VALUE").toString())){
										flag = true;
									}
								} catch (Exception e) {
									if(result.equals(tjqjMap.get("EQUAL_VALUE").toString())){
										flag = true;
									}
								}
							}else{
								if(tjqjMap.containsKey("MAX_VALUE") && !tjqjMap.get("MAX_VALUE").equals("")){
									if(Double.parseDouble(result) <= Double.parseDouble(tjqjMap.get("MAX_VALUE").toString())){
										flag = true;
									}
								}
								if(flag){
									if(tjqjMap.containsKey("MIN_VALUE") && !tjqjMap.get("MIN_VALUE").equals("")){
										if(Double.parseDouble(result) >= Double.parseDouble(tjqjMap.get("MIN_VALUE").toString())){
											flag = true;
										}else{
											flag = false;
										}
									}
								}else{
									//配置的缺少区间
									throw new ActionException("模版配置异常");
								}
							}
							if(flag){
								try {
									khxjxjj = Double.parseDouble(tjqjMap.get("PERFORMANCE_MONEY").toString());
								} catch (Exception e) {
									//模版配置中没有配置绩效金额
									throw new ActionException("模版配置异常");
								}
							}
						}
						//(2)添加考核项结果主表
						userMap.put("TAC_NAME", mbMap.get("NAME"));
						Map<String,Object> resultMap = Dao.selectOne(xmlPath + "selectRESULT", userMap);
						if(resultMap == null){
							userMap.put("DEPARTMENT", mbMap.get("DEPARTMENT_NAME"));
							userMap.put("POST", mbMap.get("POST_NAME"));
							userMap.put("POST_LEVEL", mbMap.get("POST_LEVEL"));
							userMap.put("KH_ID", userMap.get("ID"));
							userMap.put("KH_NAME", userMap.get("NAME"));
							userMap.put("ID", Dao.getSequence("SEQ_ASSESSMENT_RESULT"));
							userMap.put("KH_DATE_START", mbMap.get("START_DATE"));
							userMap.put("KH_DATE_END", mbMap.get("END_DATE"));
							Dao.insert(xmlPath + "insertResult", userMap);
						}else{
							userMap.put("ID",resultMap.get("ID"));
						}
						//(3)添加考核项结果明细表
						userMap.put("PARENT_ID", userMap.get("ID"));
						userMap.put("AT_RESULT", result);
						userMap.put("AT_NAME", khxMap.get("NAME"));
						userMap.put("AT_MONEY", khxjxjj);
						Dao.insert(xmlPath + "insertResultDetail", userMap);
						jxzje+=khxjxjj;
					}
					userMap.put("TOTAL_AMOUNT",jxzje);
					Dao.update(xmlPath + "updateResult", userMap);//(4)更新考核项结果主表
				}
			}
		}
	}
	
}

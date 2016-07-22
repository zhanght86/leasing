package com.pqsoft.zcfl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class ClassificationService {

	private final String mapper = "zcfl.score.";

	/**
	 * 根据资产评级ID获取系统打分结果
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-6-5
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> execSystem(String id, Map<String,Object> maps) throws Exception {
		ScoreService scoreService = new ScoreService();
		String cptId = maps.get("CTP_ID").toString();//模版ID
		if (!"system".equals(Dao.selectOne(mapper + "getCptType", maps)))
			throw new Exception("非系统打分模版！");
		String RBL_NUMBER = "1";
		if(maps.containsKey("RESULT_BEGIN_LEVEL")){
			RBL_NUMBER = Dao.selectOne(mapper + "rblNumber", maps);//资产初始级别在数据字典中对应的LEVEL_NUMBER
		}
		int score = 0;//总分
		for (Object o : Dao.selectList(mapper + "getEsId", cptId)) {
			String subjectId = (String) o;
			score += scoreService.execSystem(id, subjectId);
		}
		// 根据分值决定级别
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CTP_ID", cptId);
		param.put("SCORE", score);//总分
		List<Map<String,Object>> starLevelIntervaltList =  Dao.selectList(mapper + "getResult", param);
		Map<String,Object> re = new HashMap<String,Object>();
		for(int i = 0; i < starLevelIntervaltList.size(); i++){
			Map<String,Object> starLevelIntervaltMap = starLevelIntervaltList.get(i);
			starLevelIntervaltMap.put("RBL_NUMBER", RBL_NUMBER);
			re = (Map<String,Object>) Dao.selectOne(mapper + "selectRatingResult", starLevelIntervaltMap);
			if (re.containsKey("ZCTRA_RESULT_TYPE")) {
				re.putAll(param);
				return re;
			} 
		}
		if(re.containsKey("ZCTRA_RESULT_TYPE")){
			throw new Exception("无法确定判断结果，请保证模版的准确性");
		}else{
			return re;
		}
	}
	
	/**
	 * 根据选项ID获取总分
	 * 根据资产分类模版ID获取人为打分结果
	 * 付玉龙
	 *time2012-6-25下午12:01:10
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> execPeople(List<Map<String,Object>> idlist,Map<String,Object> maps) throws Exception  {
		ScoreService scoreService = new ScoreService();
		String cpt_id = maps.get("CTP_ID").toString();//模版ID
		String RBL_NUMBER = Dao.selectOne(mapper + "rblNumber", maps);//资产初始级别在数据字典中对应的LEVEL_NUMBER
		int score = 0;//总分
		for (Map ID : idlist) {
			String itemId =(String)ID.get("ID");
			score += scoreService.execute(itemId);
		}
		// 根据分值决定级别
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CTP_ID", cpt_id);
		param.put("SCORE", score);//总分
		List<Map<String,Object>> starLevelIntervaltList = Dao.selectList(mapper + "getResult", param);
		Map<String,Object> re = new HashMap<String,Object>();
		for(int i = 0; i < starLevelIntervaltList.size(); i++){
			Map<String,Object> starLevelIntervaltMap = starLevelIntervaltList.get(i);
			starLevelIntervaltMap.put("RBL_NUMBER", RBL_NUMBER);
			re = (Map<String,Object>) Dao.selectOne(mapper + "selectRatingResult", starLevelIntervaltMap);
			if (re != null && re.containsKey("RESULT")) {
				re.putAll(param);
				return re;
			} 
		}
		if(re != null && re.containsKey("RESULT")){
			throw new Exception("无法确定判断结果，请保证模版的准确性");
		}else{
			return re;
		}
	}

	//判断任务类型对任务进行处理      付玉龙  
	@SuppressWarnings("unchecked")
	public Map<String,Object> exec(Map<String, Object> task) throws Exception {
		Map<String, Object> tm = (Map<String, Object>) Dao.selectOne("zcfl.classify.getTask", task);//task是任务ID
		if (tm != null) {//如果有任务
			if ("system".equals(tm.get("TYPE"))) {// 判断是否为系统操作（是）
				Map<String,Object> resultMap = execSystem(tm.get("ZCT_PAY_CODE").toString(), tm);//执行系统打分
				int RESULT_CHANGE_LEVEL = 0;
				Map<String,Object> oldResultMap = (Map<String, Object>) Dao.selectOne("zcfl.classify.selectTask",task);
				if(!"不做调整".equals(resultMap.get("RESULT"))){
					if(oldResultMap.get("RESULT_CHANGE_TYPE") == "" || oldResultMap.get("RESULT_CHANGE_TYPE") == null || "不做调整".equals(oldResultMap.get("RESULT_CHANGE_TYPE"))){//第一次打分
						oldResultMap.put("RESULT_CHANGE_TYPE", (String) resultMap.get("RESULT"));
						if(resultMap.containsKey("UP_LEVEL")){
							oldResultMap.put("RESULT_CHANGE_LEVEL", resultMap.get("UP_LEVEL").toString());
						}else{
							oldResultMap.put("RESULT_CHANGE_LEVEL", "0");
						}
					}else if("上调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "上调".equals(resultMap.get("RESULT")) || "下调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "下调".equals(resultMap.get("RESULT"))){
						RESULT_CHANGE_LEVEL = Integer.parseInt(oldResultMap.get("RESULT_CHANGE_LEVEL").toString()) + Integer.parseInt(resultMap.get("UP_LEVEL").toString());
						oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL);
					}else if("上调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "下调".equals(resultMap.get("RESULT")) || "下调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "上调".equals(resultMap.get("RESULT"))){
						RESULT_CHANGE_LEVEL = Integer.parseInt(oldResultMap.get("RESULT_CHANGE_LEVEL").toString()) - Integer.parseInt(resultMap.get("UP_LEVEL").toString());
						if(RESULT_CHANGE_LEVEL>0){
							oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL);
						}else if(RESULT_CHANGE_LEVEL<0){
							oldResultMap.put("RESULT_CHANGE_TYPE", resultMap.get("RESULT"));
							oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL * -1);
						}else if(RESULT_CHANGE_LEVEL == 0){
							oldResultMap.put("RESULT_CHANGE_TYPE", "不做调整");
							oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL);
						}
					}
					updateAssetResult((String)tm.get("RESULT_ID"), oldResultMap);//更新资产分级结果
				}
				tm.put("RESULT_CHANGE_TYPE", resultMap.get("RESULT"));
				if(resultMap.containsKey("UP_LEVEL")){
					tm.put("RESULT_CHANGE_LEVEL", resultMap.get("UP_LEVEL").toString());
				}else{
					tm.put("RESULT_CHANGE_LEVEL", "0");
				}
				tm.put("STATUS", "1");
				Dao.update("zcfl.classify.updateTask", tm);//更新当前任务结果结束任务
				//判断后续
				String next = Dao.selectOne("zcfl.classify.getNextCtpId", tm);
				if (next != null && next!="") {
					task.put("CTP_ID", next);
					String taskId = createClassifyTask(task);//创建任务
					if(taskId != null && !"".equals(taskId)){
						task.put("RESULT_TASK_ID", taskId);
						Dao.update("zcfl.assets.updateZcflResultF", task);//记录下任务id
						Map<String,Object> mapN = exec(task);//判断任务类型  若是系统打分直接执行
						return mapN;
					}
				} else {
					overTask(task);
					task.put("over", "over");
					return task;
				}
				return null;
			}else{
				task.putAll((Map<String,Object>)Dao.selectOne("zcfl.assets.selectTask",task));
				return task;
			}
		} else {//没有资产任务  结束资产分级
			overTask(task);
			task.put("over", "over");
			return task;
		}
	}
	
	/**
	 * 人为打分后操作&&创建新任务&&判断后续
	 * 付玉龙
	 *time2012-6-25下午04:19:22		
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> subsequent(Map<String,Object> param) throws Exception {
		int RESULT_CHANGE_LEVEL = 0;
		Map<String,Object> oldResultMap = (Map<String, Object>) Dao.selectOne("zcfl.classify.selectTask",param);
		if(!"不做调整".equals(param.get("RESULT"))){
			if(oldResultMap.get("RESULT_CHANGE_TYPE") == "" || oldResultMap.get("RESULT_CHANGE_TYPE") == null || "不做调整".equals(oldResultMap.get("RESULT_CHANGE_TYPE"))){//第一次打分
				oldResultMap.put("RESULT_CHANGE_TYPE", (String) param.get("RESULT"));
				if(param.containsKey("UP_LEVEL")){
					oldResultMap.put("RESULT_CHANGE_LEVEL", param.get("UP_LEVEL").toString());
				}else{
					oldResultMap.put("RESULT_CHANGE_LEVEL", "0");
				}
			}else if("上调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "上调".equals(param.get("RESULT")) || "下调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "下调".equals(param.get("RESULT"))){
				RESULT_CHANGE_LEVEL = Integer.parseInt(oldResultMap.get("RESULT_CHANGE_LEVEL").toString()) + Integer.parseInt(param.get("UP_LEVEL").toString());
				oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL);
			}else if("上调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "下调".equals(param.get("RESULT")) || "下调".equals(oldResultMap.get("RESULT_CHANGE_TYPE")) && "上调".equals(param.get("RESULT"))){
				RESULT_CHANGE_LEVEL = Integer.parseInt(oldResultMap.get("RESULT_CHANGE_LEVEL").toString()) - Integer.parseInt(param.get("UP_LEVEL").toString());
				if(RESULT_CHANGE_LEVEL>0){
					oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL);
				}else if(RESULT_CHANGE_LEVEL<0){
					oldResultMap.put("RESULT_CHANGE_TYPE", param.get("RESULT"));
					oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL * -1);
				}else if(RESULT_CHANGE_LEVEL == 0){
					oldResultMap.put("RESULT_CHANGE_TYPE", "不做调整");
					oldResultMap.put("RESULT_CHANGE_LEVEL", RESULT_CHANGE_LEVEL);
				}
			}
			updateAssetResult((String)param.get("RESULT_ID"), oldResultMap);//更新资产分级结果
		}
		param.put("RESULT_CHANGE_TYPE", (String) param.get("RESULT"));
		if(param.containsKey("UP_LEVEL")){
			param.put("RESULT_CHANGE_LEVEL", param.get("UP_LEVEL").toString());
		}else{
			param.put("RESULT_CHANGE_LEVEL", "0");
		}
		param.put("STATUS", "endNormal");
		Dao.update("zcfl.classify.updateTask", param);//更新当前任务结果结束任务
		//判断后续
		String next = Dao.selectOne("zcfl.classify.getNextCtpId", param.get("ID"));//对应分类模版
		if (next != null && next!="") {
			param.put("CTP_ID", next);
			String taskId = createClassifyTask(param);//创建任务
			if(taskId != null && !"".equals(taskId)){
				param.put("RESULT_TASK_ID", taskId);
				Dao.update("zcfl.assets.updateZcflResultF", param);//记录下任务id
				Map<String,Object> mapN = exec(param);//判断任务类型  若是系统打分直接执行
				return mapN;
			}
		} else {
			overTask(param);
			param.put("over", "over");
			return param;
		}
		return null;
	}

	//更新资产分级结果
	public void updateAssetResult(String id, Map<String,Object> resultMap) {
		resultMap.put("RESULT_ID", id);
		Dao.update("zcfl.assets.update", resultMap);
	}

	//结束资产分级
	public void endAsset(String RESULT_ID) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("RESULT_ID", RESULT_ID);
		m.put("RESULT_STATUS", "7");
		Dao.update("zcfl.assets.update", m);
	}
	
	//更新设备中资产分级结果
	public void endEquipment(String EQUIPMENT_ID,String ASSETS_RESULT) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("EQUIPMENT_ID",EQUIPMENT_ID);
		m.put("ASSETS_RESULT",ASSETS_RESULT);
		Dao.update("zcfl.classify.updateEquipment", m);
	}
	
	/**
	 * 结束资产分级
	 * 付玉龙
	 *time2012-7-11下午03:05:22
	 */
	public void overTask(Map<String,Object> param) {
		endAsset((String) param.get("RESULT_ID"));
	}
	
	/**
	 * 创建新任务
	 * @param param
	 * @return
	 */
	public String createClassifyTask(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_ZCFL_CLASSIFY_TASK");
		param.put("ID", ID);
		int i = Dao.insert("zcfl.classify.addTask", param);
		if(i > 0){
			return ID; 
		}else{
			return null;
		}
	}
}

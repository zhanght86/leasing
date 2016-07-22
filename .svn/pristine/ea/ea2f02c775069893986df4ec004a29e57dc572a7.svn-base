package com.pqsoft.zcfl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class AssetsService {
	private final Log logger = LogFactory.getLog(getClass());
	private final String mapper = "zcfl.assets.";

	public Page pageTemplate(Map<String, Object> param)  {
		return PageUtil.getPage(param, mapper + "assetsList", mapper + "assetsCount");
	}
	
	public List<?> payReportList(Map<String, Object> param)  {
		return Dao.selectList(mapper+"payReportList", param);
	}

	public List<?> getTypeTp() {
		return Dao.selectList("getTypeTp");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> selectFXYZ(String RESULT_ID) {
		return Dao.selectList(mapper + "selectFXYZ",RESULT_ID);
	}

	public Map<String,Object> indirect(Map<String, Object> param) {
		String RESULT_ID =  Dao.getSequence("SEQ_ZCFL_RESULT");
		param.put("RESULT_ID", RESULT_ID);
		int i = Dao.insert(mapper + "addZcflResultF", param);
		if(i > 0){
			String CTP_ID = Dao.selectOne(mapper + "getCtpId", param);
			if(CTP_ID != null && !"".equals(CTP_ID)){
				param.put("CTP_ID", CTP_ID);
				ClassificationService classificationService = new ClassificationService();
				String taskId = classificationService.createClassifyTask(param);//创建任务
				if(taskId != null && !"".equals(taskId)){
					param.put("RESULT_TASK_ID", taskId);
					int j = Dao.update(mapper + "updateZcflResultF", param);//记录下任务id
					if(j > 0){
						Map<String, Object> mapN = null;
						try {
							mapN = classificationService.exec(param);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//判断任务类型  若是系统打分直接执行
						return mapN;
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> goondoIndirect(Map<String,Object> task)
	{
		Map<String,Object> task1 = (Map<String,Object>)Dao.selectOne("zcfl.assets.selectTask",task);
		return task1;
	}
	@SuppressWarnings("unchecked")
	public int update(Map<String, Object> param) {
		Map<String,Object> map =(Map<String,Object>) Dao.selectOne(mapper + "selectDetails" , param);
 		param.put("EQUIPMENT_ID",map.get("EQUIPMENT_ID") );
		return Dao.update(mapper + "update", param);
	}
	
	/**
	 * 查看资产详情
	 * 付玉龙
	 *time2012-6-29下午02:17:39
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectDetails(Map<String, Object> param) {
		Map<String,Object> map = (Map<String,Object>)Dao.selectOne(mapper + "selectDetails", param);
		param.putAll(map);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> assetsextra(Map<String, Object> param) {
		Map<String,Object> map = (Map<String,Object>)Dao.selectOne(mapper + "assetsextra", param);
		return map;
	}
	
	/**
	 * 查看资产设备详情
	 * 付玉龙
	 *time2012-6-29下午02:24:28
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectEquipment(Map<String, Object> param) {
		Map<String,Object> map = (Map<String,Object>)Dao.selectOne(mapper + "selectEquipment", param);
		return map;
	}
	
	public List selectLevelGroup()
	{
		Map map=new HashMap();
		map.put("tags", "未分级");
		List list=Dao.selectList(mapper+"selectLevelGroup",map);
		return list;
	}
	
	public Page getLevelPage(Map map)  {
		if(map==null)
		{
			map=new HashMap();
		}
		
		map.put("tags", "未分级");
		Page page= PageUtil.getPage(map, mapper + "getLevelList", mapper + "getLevelListCount");
		List list=page.getData();
		return page;
	}
	
	
	public Map normalWindCoverPicLevel()
	{
		Map map=new HashMap();
//		List list=this.selectLevelGroup();
//		String path = mSGA.get("uploadDir") + "/assets/data/Level/";
//		File f = new File(path);
//		if (!f.isDirectory()) {
//			f.mkdirs();
//		}
//		String	fileName = "windLevel.xml";
//		try {
//			CreateXml cx = new CreateXml();
//			CreateXml.writeXml(cx.createLevelXml(list), path
//					+ fileName);
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		
		return map;
	}
	
	public Map baoDanManger(List list)
	{
		
		Map map=(Map)Dao.selectOne(mapper+"selectInsurePrice");
//		String path = mSGA.get("uploadDir") + "/assets/data/inSure/";
//		File f = new File(path);
//		if (!f.isDirectory()) {
//			f.mkdirs();
//		}
//		String	fileName = "windInSure.xml";
//		try {
//			CreateXml cx = new CreateXml();
//			CreateXml.writeXml(cx.createInsureXml(list,map), path
//					+ fileName);
//		} catch (Exception e) {
//			logger.error(e);
//		}
		
		return map;
	}
	
	
	public List selectInsureGroup()
	{
		Map map=new HashMap();
		map.put("tags1", "未投保");
		map.put("tags2", "已投保");
		List list=Dao.selectList(mapper+"selectInsureGroup",map);
		return list;
	}
	
	
	public Page getInsurePage(Map map)  {
		if(map==null)
		{
			map=new HashMap();
		}
		
		map.put("tags1", "未投保");
		map.put("tags2", "已投保");
		Page page= PageUtil.getPage(map, mapper + "getInsureList", mapper + "getInsureListCount");
		List list=page.getData();
		return page;
	}
	
	/**
	 * 直接分级添加分级记录
	 * 付玉龙
	 *time2013-6-18下午09:13:53
	 */
	public String addZcflResult(Map<String,Object> param)  {
		String RESULT_ID =  Dao.getSequence("SEQ_ZCFL_RESULT");
		param.put("RESULT_ID", RESULT_ID);
		int result=Dao.insert(mapper+"addZcflResult",param);
		if(result > 0){
			return RESULT_ID;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 付玉龙
	 *time2013-6-18下午09:13:53
	 */
	public boolean addZcflResultDirectly(List<Map<String,Object>> zrdList,String RESULT_ID) {
		boolean flag = true;
		for(int i = 0; i < zrdList.size(); i++){
			Map<String,Object> zrdMap = zrdList.get(i);
			zrdMap.put("RESULT_ID",RESULT_ID);
			zrdMap.put("ZRD_SORTING", i+1);
			int j = Dao.insert(mapper+"addZcflResultDirectly",zrdMap);
			if(zrdMap.get("ZRD_TYPE").equals("重大事件因子调整G3")){
				int p = Dao.update(mapper+"updateZcflResult",zrdMap);
				if(j <= 0 || p <= 0){
					flag = false;
				}
			}else{
				if(j <= 0){
					flag = false;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 分级评审通过
	 * @param RESULT_ID
	 * 付玉龙
	 *time2013-6-19上午11:09:19
	 */
	@SuppressWarnings("unchecked")
	public String addZcflResultSuccess(String RESULT_ID)  {
		//更新ZCFL_RESULT数据
		Dao.update(mapper+"updateResultSuccess", RESULT_ID);
		//查询ZCFL_RESULT数据
		Map<String,Object> resultMap = (Map<String,Object>)Dao.selectOne(mapper+"selectZcflResultSuccess",RESULT_ID);
		//更新ZCFL_ASSETS数据   	根据支付表编号若有这个支付表编号更新，若没有则新建
		Map<String,Object> assetsMap = (Map<String,Object>)Dao.selectOne(mapper+"selectZcflAssetsSuccess",resultMap);
		if(assetsMap != null && assetsMap.containsKey("PAY_CODE")){
			Dao.update(mapper+"updateAssetsSuccess", resultMap);
		}else{
			Dao.insert(mapper+"addAssetsSuccess", resultMap);
		}
		return null;
	}
	
	/**
	 * 分级评审不通过
	 * @param RESULT_ID
	 * 付玉龙
	 *time2013-6-19上午11:09:19
	 */
	public String addZcflResultFailure(String RESULT_ID)  {
		//更新ZCFL_RESULT数据
		Dao.update(mapper+"updateResultFailure", RESULT_ID);
		return null;
	}
	
	/**
	 * 记录流程ID
	 * @param RESULT_ID
	 * 付玉龙
	 *time2013-6-19上午11:09:19
	 */
	public void updateJbpmId(Map<String,Object> param)  {
		Dao.update(mapper+"updateJbpmId", param);
	}
	
	/**
	 * 直接分级--附加信息
	 * @param RESULT_ID
	 * 付玉龙
	 *time2013-6-19上午11:09:19
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectDirectAdditional(Map<String,Object> param){
		return (Map<String, Object>) Dao.selectOne(mapper+"selectDirectAdditional", param);
	}
	public Map<String,Object> selectyear(Map<String,Object> param){
		return (Map<String, Object>) Dao.selectOne(mapper+"yearlevel",param);
	}
	public int insinitial(Map<String,Object> param){
		return Dao.insert(mapper+"insinitial",param);
	}
	public Map<String,Object> selectinitial(Map<String,Object> param){
		return (Map<String, Object>) Dao.selectOne(mapper+"selectinitial", param);
	}
	public Map<String,Object> selg0(Map<String,Object> param){
		return (Map<String, Object>) Dao.selectOne(mapper+"selg0", param);
	}
	public Map<String,Object> selg1(Map<String,Object> param){
		return (Map<String, Object>) Dao.selectOne(mapper+"selg1", param);
	}
	public Map<String,Object> selg2(Map<String,Object> param){
		return (Map<String, Object>) Dao.selectOne(mapper+"selg2", param);
	}
	public Map<String,Object> selg3(Map<String,Object> param){
		return (Map<String, Object>) Dao.selectOne(mapper+"selg3", param);
	}
	
	
	public int updateresult(Map<String,Object> param){
		return Dao.update(mapper+"updateresult",param);
	}
	
	/**
	 * 发起非直接评级流程
	 * @param RESULT_ID
	 * 付玉龙
	 *time2013-6-19上午11:09:19
	 */
	public int addResultNote(Map<String,Object> param){
		param.put("RESULT_STATUS", 1);
		return  Dao.update(mapper+"update", param);
	}
	public Map<String,Object> selaffirm(Map<String,Object> param)
	{
		return (Map<String, Object>) Dao.selectOne(mapper+"selaffirm",param);
	}
	public List schedule(Map<String, Object> param)
	{
		return Dao.selectList(mapper+"schedule1",param);
	}
	public Page pageTemp(Map<String, Object> param)  {
		return PageUtil.getPage(param, mapper + "schedule", mapper + "scheduleCount");
	}
}

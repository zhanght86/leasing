package com.pqsoft.zcfl.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;



public class CapitalClassificationService {
	private final Log logger = LogFactory.getLog(getClass());
	private final String	mapperPath	= "zcfl.act.";

	/**
	 * 分级任务
	 * 付玉龙
	 *time2012-6-7下午04:23:56
	 * @throws Exception 
	 */
	public Page AssetsRatingTtask(Map<String,Object> param) {
		return PageUtil.getPage(param, mapperPath + "getactList", mapperPath + "getactListCount");
	}
	
	/**
	 * 查询资产当前级别--打分页面专用
	 * @param RESULT_ID
	 * 付玉龙
	 *time2012-6-7下午04:23:56
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectNowLevel(Map<String,Object> param) {
		return (Map<String,Object>) Dao.selectOne(mapperPath + "selectNowLevel", param);
	}
	
	/**
	 * 模版==>选项
	 * 付玉龙
	 *time2012-6-8上午11:43:05
	 */
	public List Moption(Map param) {
		List ES_ID = Dao.selectList(mapperPath + "mTitleES_ID", param);//题目ID
		List moptionlist = new ArrayList();//选项
		for (int i = 0; i < ES_ID.size(); i++) {
//			Map optionMap = new HashMap();
//			optionMap.put(Dao.selectString(mapperPath + "mTitleList", ES_ID.get(i)), Dao.selectList(mapperPath + "mOptionList",ES_ID.get(i)));
			moptionlist.add(Dao.selectList(mapperPath + "mOptionList",ES_ID.get(i)));
		}
		return moptionlist;
	}
	
	/**
	 * 模版==>题目
	 * 付玉龙
	 *time2012-6-11上午10:16:11
	 */
	public List Mtitle(Map<String,Object> param) {
		List ES_ID = Dao.selectList(mapperPath + "mTitleES_ID", param);//题目ID
		List mtitlelist = new ArrayList();
		for (int i = 0; i < ES_ID.size(); i++) {
			mtitlelist.add(Dao.selectOne(mapperPath + "mTitleList", ES_ID.get(i)));//题目
		}
		return mtitlelist;
	}
	
	public Page list(Map<String, Object> param) {
		int pageCurr = 1;
		int pageCount = 5;
		if (!(param.get("PAGE_CURR") == null || "".equals(param
				.get("PAGE_CURR").toString().trim()))) {
			pageCurr = Integer.parseInt(param.get("PAGE_CURR").toString());
		}
		if (!(param.get("PAGE_COUNT") == null || "".equals(param.get(
				"PAGE_COUNT").toString().trim()))) {
			pageCount = Integer.parseInt(param.get("PAGE_COUNT").toString());
		}
		param.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
		
		param.put("PAGE_END", pageCount * pageCurr);
		Page pt = PageUtil.getPage(param, "assetlevel.list", "assetlevel.list_count");
		return pt;
   }
	
	public int AddAssetLevel(Map map){
		map.put("PLAN_STATUS", "new");
		return Dao.insert("assetlevel.AddAssetLevel", map);
	}
	
	public Object selectT_orForOne(Map<String, Object> map) {
		return Dao.selectOne(
				"assetlevel.selectT_orForOne", map);
	}
	
	public Object select(Map<String, Object> map) {
		return Dao.selectList(
				"assetlevel.select", map);
	}
	
	public int updateAssetLevel(Map map){
		return Dao.insert("assetlevel.updateAssetLevel", map);
	}
	
	public List<?> getSqlModer() {
		return Dao.selectList("zcfl.plan.getSqlModer", "资产十二级分类参数");
	}
	
	

	public List<?> selectforjb(Map<String, Object> map) throws Exception {
		return Dao.selectList("zcfl.plan.selectforList",map);
	}
	
	
	
	public Object statistics(Map<String, Object> map) {
		return Dao.selectList(
				"tianjiasql.biao", map);
	}
	
	
	public Map normalWindRunoverReport(String PLAN_ID)
	{
		Map map=new HashMap();
//		List list=this.selectRunoverGroup(PLAN_ID);
//		String path = mSGA.get("uploadDir") + "/assets/data/Plan/";
//		File f = new File(path);
//		if (!f.isDirectory()) {
//			f.mkdirs();
//		}
//		String	fileName = "windLevelPlan.xml";
//		try {
//			CreateXml cx = new CreateXml();
//			CreateXml.writeXml(cx.createLevelXml(list), path
//					+ fileName);
//		} catch (Exception e) {
//			logger.error(e);
//		}
		
		return map;
	}
	
	public List selectRunoverGroup(String PLAN_ID)
	{
		Map map=new HashMap();
		map.put("tags", "未分级");
		map.put("PLAN_ID", PLAN_ID);
		List list=Dao.selectList(mapperPath+"selectRunoverGroup",map);
		return list;
	}
}
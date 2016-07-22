package com.pqsoft.secuEvaluate.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

/**
 * @author 吴剑东
 *
 */
public class SecuEvaluateService {
	List resultList = new ArrayList();
	private final static String path="SecuEvaluate.";
	private int temp = 0;

	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public Page toSecuEvaluateManager(Map<String,Object> m) {
		Page page = new Page(m);
		page.addDate(JSONArray.fromObject(Dao.selectList(path + "toSecuEvaluateManager", m)), Dao.selectInt(path + "toSecuEvaluateManagerCount",m));
		return page;
	}
	/**
	* outAllPressTitle
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public Object outAllPressTitle(Map<String,Object> m) {
		List<Map<String,Object>> list = Dao.selectList(path+"toSecuEvaluateManager",m);
		return list;
	}
	
	
	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public void add(Map map){
		Dao.insert(path+"addSecuEvaluate",map);
		
	}
	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public List queryByParentid(Map<String,Object> m) {
		return Dao.selectList(path+"querySecuEvaluateByParentid",m);
		
	}
	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public List queryByid(Map<String,Object> m) {
		m.put("parentsid", m.get("id"));
		List tree = tree(m, 2);
		System.out.println(tree);
		return tree;
		
	}

	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public  List tree(Map map,int level) {
		String preStr = "";
		for (int i = 0; i < level; i++) {
			preStr += "k";
		}
	    List childs = Dao.selectList(path+"querySecuEvaluateByParentid",map);
		if (childs != null) {
	    for(int i=0;i<childs.size();i++) {
	       Map m = (Map)childs.get(i);
	       Map resultMap = new HashMap();
	       resultMap.put(preStr, m);
	       resultList.add(resultMap);
	       map.put("parentsid", m.get("ID"));
	       List list = Dao.selectList(path+"querySecuEvaluateByParentid",map);
	       if(list!=null) {
	    	   tree(map, level + 1);
	       }
	    }
		}
		return resultList;
	}
	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public void deleted(Map<String,Object> m) {
//		Dao.delete(path+"deleteMouldOfNorm", m);
//		Dao.delete(path+"deleteSecuEvaluate", m);
		Dao.delete(path+"delGrade",m);
	}
	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public  List treeDeleted(Map map) {
	    List childs = Dao.selectList(path+"querySecuEvaluateByParentid",map);
		if (childs != null) {
	    for(int i=0;i<childs.size();i++) {
	       Map m = (Map)childs.get(i);
	       Dao.delete(path+"deleteSecuEvaluate", m);
	       map.put("parentsid", m.get("ID"));
	       List list = Dao.selectList(path+"querySecuEvaluateByParentid",map);
	       if(list!=null) {
	    	   treeDeleted(map);
	       }
	    }
		}
		return resultList;
	}
	/**
	* toSecuEvaluateManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public Map<String,Object>  querySelfByid(Map<String,Object> m) {
		return Dao.selectOne(path+"querySecuEvaluateById",m);
	}
	
	public List<Map<String, Object>> queryMouldNorm(Map<String,Object> m) {
		List<Map<String,Object>> list = Dao.selectList(path+"queryMouldNorm",m);
		return list;
	}
	

	public Object queryTreeByid(Map<String,Object> m) {
		List<Map<String,Object>> list = Dao.selectList(path+"querySecuEvaluateTreeByid",m);
		return list;
	}
	
	
	/**
	* toSecuEvaluateManager 如查询到有重复，把id加上1毫秒再次验证，知道不重复后返回加了多少毫秒
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public String checkId(Map<String,Object> m) {
		this.getNum(m);
		return temp+"";
	}
	private void getNum(Map<String,Object> m){
		String num = Dao.selectOne(path+"checkSecuEvaluateId",m);
		if(!"0".equals(num)){
			Date d = new Date(Long.parseLong(m.get("id")+""));
			d.setSeconds(d.getSeconds()+1);
			d.setMinutes(d.getMinutes()+1);
			m.put("id", d.getTime()+"");
			temp++;
			getNum(m);
		}
	}
	public boolean doCopy(Map<String, Object> m) {
		//判断是否存在重复数据
		int count = 0;
		do{
			m.put("NUM_ID", Dao.getSequence("SEQ_SECU_EVALUATE_SUBJECT"));
			count = Dao.selectInt(path+"checkNewIdIsCount",m);
		}while(count > 0);
		//复制打分项
		Dao.insert(path+"copySecuEvaluateParentData", m);
		//复制定量打分项
		Dao.insert(path+"insertMouldOfNorm", m);
		return Dao.insert(path+"copySecuEvaluateData", m)>0?true:false;
	}
	public void saveMouldOfNorm(Map<String, Object> map) {
		Dao.insert(path+"saveMouldOfNorm",map);
	}
	public Map<String, Object> querySecuEvalInfo(Map<String, Object> m) {
		System.out.println(m);
		String TYPE=m.get("TYPE").toString();
		if(TYPE.equals("LP")||("NP").equals(TYPE)){
			return Dao.selectOne(path+"querySecuEvalInfo",m);
		}/*
		else if(("NP").equals(TYPE)){
			return Dao.selectOne(path+"querySecuEvalInfoNP",m);
		}*/else if("DBR".equals(TYPE)){
			//查询担保人打分模版
			return Dao.selectOne(path+"querySecuEvalInfoDBR",m);
		}else if(("DLS").equals(TYPE)){
			//查询供应商打分模版
			return Dao.selectOne(path+"querySecuEvalInfoSup",m);
		}else if(("CP").equals(TYPE)){
			//查询产品打分模版
			return Dao.selectOne(path+"querySecuEvalInfoProduct",m);
		}else if(("ZXXY").equals(TYPE)){
			//查询资信信誉评分模版
			return Dao.selectOne(path+"querySecuEvalInfoZXXY",m) ;
		}
		return null;
	}

	
	public boolean insertCustGrade(Map<String, Object> map){
		//更新客户表评分
		Dao.update(path+"updateCustScore", map);
		return Dao.insert(path+"insertCustGrade",map)>0?true:false;
		
	}
	
	//更新担保人
	public boolean updateGradeForDBR(Map<String,Object> map){
		return  Dao.update(path+"updateGradeForDBR",map)>0?true:false;
	}
	
	public boolean updateGradeForDLS(Map<String, Object> map){
		return Dao.update(path+"updateGradeForDLS",map)>0?true:false; 
	}
	
	public boolean updateGradeForProduct(Map<String, Object> map){
		return Dao.update(path+"updateGradeForProduct",map)>0?true:false; 
	}
	
	public boolean updateGradeForZXXY(Map<String, Object> map){
		return Dao.update(path+"updateGradeForZXXY",map)>0?true:false; 
	}

	/**
	 * 获取行业类型
	 * @author yangxue
	 * @date 2015-06-04
	 * @return
	 */
	public Object toFindIndustryMemcache(){
		return Dao.selectList(path+"toFindIndustryMemcache");
	}
}

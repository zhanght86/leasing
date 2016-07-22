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
public class GradeMoludService {
	List resultList = new ArrayList();
	private final static String path="GradeMolud.";
	private int temp = 0;

	/**
	* toGradeMoludManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public Page toGradeMoludManager(Map<String,Object> m) {
		Page page = new Page(m);
		page.addDate(JSONArray.fromObject(Dao.selectList(path + "toGradeMoludManager", m)), Dao.selectInt(path + "toGradeMoludManagerCount",m));
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
		List<Map<String,Object>> list = Dao.selectList(path+"toGradeMoludManager",m);
		return list;
	}
	
	
	/**
	* toGradeMoludManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public void add(Map map){
		Dao.insert(path+"addGradeMolud",map);
		
	}
	/**
	* toGradeMoludManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public List queryByParentid(Map<String,Object> m) {
		return Dao.selectList(path+"queryGradeMoludByParentid",m);
		
	}
	/**
	* toGradeMoludManager
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
	* toGradeMoludManager
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
	    List childs = Dao.selectList(path+"queryGradeMoludByParentid",map);
		if (childs != null) {
	    for(int i=0;i<childs.size();i++) {
	       Map m = (Map)childs.get(i);
	       Map resultMap = new HashMap();
	       resultMap.put(preStr, m);
	       resultList.add(resultMap);
	       map.put("parentsid", m.get("ID"));
	       List list = Dao.selectList(path+"queryGradeMoludByParentid",map);
	       if(list!=null) {
	    	   tree(map, level + 1);
	       }
	    }
		}
		return resultList;
	}
	/**
	* toGradeMoludManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public void deleted(Map<String,Object> m) {
		Dao.delete(path+"deleteMouldOfStandard", m);
		Dao.delete(path+"deleteGradeMolud", m);
	}
	/**
	* toGradeMoludManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public  List treeDeleted(Map map) {
	    List childs = Dao.selectList(path+"queryGradeMoludByParentid",map);
		if (childs != null) {
	    for(int i=0;i<childs.size();i++) {
	       Map m = (Map)childs.get(i);
	       Dao.delete(path+"deleteGradeMolud", m);
	       map.put("parentsid", m.get("ID"));
	       List list = Dao.selectList(path+"queryGradeMoludByParentid",map);
	       if(list!=null) {
	    	   treeDeleted(map);
	       }
	    }
		}
		return resultList;
	}
	/**
	* toGradeMoludManager
	* @date 2014-3-27 下午03:49:23 
	* @author 作者 吴剑东
	* @return 返回类型
	* @throws Exception
	*/ 
	public Object querySelfByid(Map<String,Object> m) {
		List<Map<String,Object>> list = Dao.selectList(path+"queryGradeMoludById",m);
		return list;
	}
	
	public Object queryMouldStandard(Map<String,Object> m) {
		List<Map<String,Object>> list = Dao.selectList(path+"queryMouldStandard",m);
		return list;
	}
	

	public Object queryTreeByid(Map<String,Object> m) {
		List<Map<String,Object>> list = Dao.selectList(path+"queryGradeMoludTreeByid",m);
		return list;
	}
	
	/**
	* toGradeMoludManager 如查询到有重复，把id加上1毫秒再次验证，知道不重复后返回加了多少毫秒
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
		String num = Dao.selectOne(path+"checkGradeMoludId",m);
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
			m.put("NUM_ID", Dao.getSequence("SEQ_T_SYS_GRADE_MOULD"));
			count = Dao.selectInt(path+"checkGradeIdIsCount",m);
		}while(count > 0);
		//复制打分项
		Dao.insert(path+"copyGradeMoludParentData", m);
		//复制定量打分项
		Dao.insert(path+"insertMouldOfStandard", m);
		return Dao.insert(path+"copyGradeMoludData", m)>0?true:false;
	}
	public void saveMouldOfStandard(Map map) {
		Dao.insert(path+"saveMouldOfStandard",map);
	}

}

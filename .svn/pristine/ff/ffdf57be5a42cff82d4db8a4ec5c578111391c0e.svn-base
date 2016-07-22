package com.pqsoft.crm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.materialMgt.service.MaterialMgtService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CustomerService {

	String basePath = "crm.customer.";

	/**
	 * 根据客户ID查询客户电子档案
	 * 
	 * @Function: com.pqsoft.crm.service.CustomerService.getEdossierPage
	 * @param CLIENT_ID
	 *            客户ID
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-8-28 下午05:55:57
	 */
	public Page getEdossierPage(Map<String, Object> map) {
		map.put("CLIENT_ID", "1");
		return PageUtil.getPage(map, basePath + "getEdossierPageList", basePath + "getEdossierPageCount");
	}

	public Page getUserTourPage(Map<String, Object> map) {//
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath + "getUserTourList", map)), 10);
		return page;
	}

	public Page getCallListPage(Map<String, Object> map) {
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath + "getCallLogPageList", map)), 10);
		return page;
	}

	public Map<String, Object> getCust(String id) {
		return Dao.selectOne(basePath + "getCustById", id);
	}
	
	public Map<String, Object> getSpoust(String id) {
		return Dao.selectOne(basePath + "getSpoustById", id);
	}
	public Map<String, Object> getEnterprise(String id) {
		return Dao.selectOne(basePath + "getEnterpriseId", id);
	}
	
	public void upCheckRe(Map<String, Object> param) {
		Dao.update(basePath + "upCheckRe", param);
	}
	
	public void upCheckRe1(Map<String, Object> param) {
		Dao.update(basePath + "upCheckRe1", param);
	}
	
	public void upCheckRe2(Map<String, Object> param) {
		Dao.update(basePath + "upCheckRe2", param);
	}
	public void upCheckRe3(Map<String, Object> param) {
		Dao.update(basePath + "upCheckRe3", param);
	}

	public Map<String, Object> selectRenterDetails(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectRenterDetails1", param);
	}

	public List<Map<String, Object>> getProjectFileListSrc(Map<String, Object> param) {
		System.out.println("param====="+param);
		if(!param.containsKey("TYPE_")){
			param.put("TYPE_", null);
		}else if("ZX".equals(param.get("TYPE_"))){
			System.out.println("param====="+param);
			param.put("ZX_TYPE_", "资信");
		}
		param.putAll((Map<? extends String, ? extends Object>) Dao.selectOne("crm.customer.getProjectForFileList", param));
		if (param != null) {
			String[] files = new MaterialMgtService().getFileListByCode("立项", param.get("PRO_CODE").toString());
			List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
			for(String file : files) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("CODE", file);
				m.put("CODE_TYPE_FLAG", 1);
				m.put("NAME", file);
				m.put("CODE_TYPE", "必选");
				ls.add(m);
			}
//			List l = Dao.selectList("crm.customer.getProjectFileListSrc", param);
			return ls;
		}
		return null;
	}

	public int insertProjectFile(Map<String, Object> map) {
		return Dao.insert("crm.customer.insertFileProjectPictureFile", map) ; 
	}

	public boolean deleteProjectFileById(String id) {
		
		return Dao.delete("crm.customer.deleteProjectFileById", id)>0;
	}
	public List<Map<String,Object>> getAllMaterial(Map<String,Object> m){
		return Dao.selectList("crm.customer.getAllMaterial", m);
	}
	
	
	/**
	 * <pre>
	 * 获取文档类型及文件。
	 * 本方法没有改动只是单独分出方便别的地方调用
	 * <pre>
	 * @see com.pqsoft.crm.action.CustomerAction.toMgElectronicPhotoAlbum11()
	 * @param proId
	 * @param phase
	 */
	public List<Map<String, Object>> getDocAndFiles(String proId, String phase) {
		List<Map<String, Object>> mts = new ArrayList<Map<String, Object>>();
		
		String sql="SELECT CASE B.TYPE WHEN 'NP' THEN 1 ELSE 2 END CUST_TYPE,"
				+ " CASE A.JOINT_TENANT WHEN '2' THEN 3 WHEN '3' THEN 4 ELSE 0 END GT_TYPE,"
				+ " CASE A.GUARANTEE WHEN '2' THEN 5 WHEN '3' THEN 6 ELSE 0 END GUA_TYPE"
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B"
				+ " WHERE A.CLIENT_ID = B.ID AND A.ID='"+proId+"'";
		List<Map<String, Object>> ms = Dao.execSelectSql(sql);
		
		Map<String, Object> m = ms.get(0);
		List<Map.Entry<String, Object>> entrys = new ArrayList<Map.Entry<String, Object>>(m.entrySet());
		Collections.sort(entrys, new Comparator<Map.Entry<String, Object>>(){
			public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
				return o1.getValue().toString().compareTo(o2.getValue().toString());
			}
		});
		List<Map<String, Object>> mainTypes = new DataDictionaryMemcached().get("主体类型");
		for(Map.Entry<String, Object> me : entrys) {				
			if(!phase.equals("立项")&&!me.getKey().equals("CUST_TYPE")) continue;
			if(me.getValue().toString().equals("0")) continue;
			for(Map<String, Object> mainType : mainTypes) {
				if(mainType.get("CODE").toString().equals(me.getValue().toString())) {
					mts.add(mainType);
				}
			}
		}
		return mts;
	}
	
	/**
	 * JZZL-209
	 * <pre>
	 * 获取文档类型及文件。
	 * 本方法没有改动只是单独分出方便别的地方调用
	 * <pre>
	 * @see com.pqsoft.crm.action.CustomerAction.toMgElectronicPhotoAlbum11()
	 * @param proId
	 * @param phase
	 */
	public List<Map<String, Object>> getDocAndFiles_New(String proId, String phase, String HTSH, String JCSH) {
		List<Map<String, Object>> mts = new ArrayList<Map<String, Object>>();
		
		String sql="SELECT CASE B.TYPE WHEN 'NP' THEN 1 ELSE 2 END CUST_TYPE,"
				+ " CASE A.JOINT_TENANT WHEN '2' THEN 3 WHEN '3' THEN 4 ELSE 0 END GT_TYPE,"
				+ " CASE A.GUARANTEE WHEN '2' THEN 5 WHEN '3' THEN 6 ELSE 0 END GUA_TYPE"
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B"
				+ " WHERE A.CLIENT_ID = B.ID AND A.ID='"+proId+"'";
		List<Map<String, Object>> ms = Dao.execSelectSql(sql);
		
		Map<String, Object> m = ms.get(0);
		List<Map.Entry<String, Object>> entrys = new ArrayList<Map.Entry<String, Object>>(m.entrySet());
		Collections.sort(entrys, new Comparator<Map.Entry<String, Object>>(){
			public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
				return o1.getValue().toString().compareTo(o2.getValue().toString());
			}
		});
		List<Map<String, Object>> mainTypes = new DataDictionaryMemcached().get("主体类型");
		for(Map.Entry<String, Object> me : entrys) {				
			if(!phase.equals("立项")&&!me.getKey().equals("CUST_TYPE")) continue;
			if(me.getValue().toString().equals("0")) continue;
			for(Map<String, Object> mainType : mainTypes) {
				if(mainType.get("CODE").toString().equals(me.getValue().toString())) {
					mts.add(mainType);
				}
			}
		}
		
		if (!"".equals(HTSH)) {//合同审核
			mts.add(mainTypes.get(6));
		}
		//add gengchangbao JZZL-258 2016年7月13日17:02:48 Start
		if (!"".equals(JCSH)) {//交车审核
			mts.add(mainTypes.get(7));
		}
		//add gengchangbao JZZL-258 2016年7月13日17:02:48 End
		return mts;
	}
}

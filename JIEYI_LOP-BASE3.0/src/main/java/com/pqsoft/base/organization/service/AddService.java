package com.pqsoft.base.organization.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

public class AddService {

	private final String mapperPath = "orgazization.";

	/**
	 * 模糊查询 参数roleContent 字段role_name
	 * 
	 * @return
	 * @author <a href='mailto:lichaohn@163.com'>lic</a>
	 * @version v1.01 2012-2-5
	 */
	public Object getRoles(Map<String, Object> param) {
		return Dao.selectList(mapperPath + "getRole", param);
	}

	public String getOrgIdSeq(){
		return Dao.getSequence("SEQ_SECU_ORG");
	}
	
	public int add(Map<String, Object> param) {
		return Dao.insert(mapperPath + "add", param);
	}

	public Object getInfo(String id) {
		return Dao.selectOne(mapperPath + "getInfoForUpdate", id);
	}

	public int update(Map<String, Object> param) throws Exception {
		Dao.update(mapperPath+"updateFhFa_manager",param);
	    return Dao.update(mapperPath + "update", param) ;
    }
	
	public List<String> getOrgUserCode(String org_id){
		return Dao.selectList(mapperPath+"getUserCodeByOrgId", org_id);
	}
	
	public int addPlatform(Map<String, Object> param){
		param.put("USER_ID", Security.getUser().getId());
		//新增平台，同时在联合租赁公司也加上该平台
		param.put("MANAGER_ID", Dao.getSequence("SEQ_MANAGER"));
		Dao.insert(mapperPath+"addFLCom",param);
		return Dao.insert(mapperPath+"addPlatform",param);
	}
	
	public void addPlatform_SUN(Map<String, Object> param){
		//先查询上级公司ID
		Map map=Dao.selectOne(mapperPath+"querySuper_ID", param);
		if(map!=null && map.get("ID")!=null && !map.get("ID").equals("")){
			param.put("USER_ID", Security.getUser().getId());
			param.put("SUPER_ID", map.get("ID"));
			//新增平台，同时在联合租赁公司也加上该平台
			param.put("MANAGER_ID", Dao.getSequence("SEQ_MANAGER"));
			Dao.insert(mapperPath+"addFLCom",param);
			Dao.insert(mapperPath+"addPlatform_SUN",param);
		}
	}

}

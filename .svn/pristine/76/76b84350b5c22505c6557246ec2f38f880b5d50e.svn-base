package com.pqsoft.insure.service;
/**
 *  保险公司管理
 *  @author 韩晓龙
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
/**
 *  保险公司管理
 *  @author 韩晓龙
 */
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureCompanyService {
	
	private String basePath = "insureCompany.";
	
	public InsureCompanyService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	/**
	 *   插入一条保险公司信息
	 */
	public int insertInsureCompany(Map<String,Object> param){
		return Dao.insert(basePath + "addInsureCompany",param);
	}
	/**
	 *   修改一条保险公司信息
	 */
	public int updateInsureCompany(Map<String,Object> param){
		return Dao.update(basePath + "updateInsureCompany",param);
	}
	/**
	 *   删除一条保险公司信息
	 */
	public int deleteInsureCompany(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteInsureCompany",param);
	}
	/**
	 *   根据id查询一个保险公司的详细信息
	 */
	public Object selectInsureCompanyById(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectInsureCompanyById",param);
	}
	
	/** 
	 *  插入保险公司与险种中间表
	 */ 
	public int insertMiddleTable(Map<String, Object> param) {
		int flag = 0;
		Map map = new HashMap();
		map.putAll(param);
		map.put("COMPANY_ID", param.get("ID"));
		String[] strs = param.get("ALLINSURETYPE").toString().split(",");
		for (int i = 0; i < strs.length; i++) {
			map.put("INSURE_ID", strs[i]);
			flag += Dao.insert(basePath + "insertItem",map);
		}
		return flag;
	}
	
	/** 
	 *  根据id删除一个保险公司在 保险公司与险种中间表 中的信息
	 */ 
	public int deleteMiddleTable(Map<String, Object> param) {
		Map tmap = new HashMap();
		tmap.put("COMPANY_ID", param.get("ID"));
		return Dao.delete(basePath + "deleteItem", tmap);
	}
	
	/**
	 *   根据保险公司id查询一个保险公司已配置的险种信息
	 */
	public List<Map<String, Object>> selectInsureTypeByCid(Map<String, Object> param) {
		param.put("COMPANY_ID", param.get("ID"));
		return Dao.selectList(basePath + "selectInsureTypeByCid",param);
	}
	
}

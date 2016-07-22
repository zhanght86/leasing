package com.pqsoft.base.company.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class CompanyService {
	private String basePath = "Company.";
	
	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getCompanyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getCompanyCount", param));
		return page;
	}
	
	public List<Object> getAllCompany(){
		return Dao.selectList(basePath+"getAllCompany");
	}
	/**
	 * 优化后的排序字符	字母拼音总合
	 * Author: SnowWolf
	 * Time: 2015年3月5日
	 * @param param
	 * @return List<Object>
	 */
	public List<Object> getCompanyAll(){
		Map<String, Object> magic = new HashMap<String, Object>();
		magic.put("magic", "啊八嚓大额发噶哈几卡拉吗呐哦扒七然仨他哇西呀杂");
		return Dao.selectList(basePath+"getCompanyAll",magic);
	}
	
	/**
	 * 查询所有厂商的数据并按厂商名称升序排序
	 * @return
	 * @author:King 2013-10-14
	 */
	public List<Object> getAllCompanySortByName(){
		return Dao.selectList(basePath+"getAllCompanySortByName");
	}
	
	//通过平台查询厂商
	public List<Map<String,Object>> queryCompanyList(Map<String,Object> param){
		return Dao.selectList(basePath+"queryCompanyList",param);
	}
	
	public Map<String,Object> getCompanyDetail(Map<String,Object> param){
		return Dao.selectOne(basePath+"getCompanyDetail",param);
	}
	
	public int addCompany(Map<String,Object> param){
		return Dao.insert(basePath+"addCompany",param);
	}
	
	public int updateCompany(Map<String,Object> param){
		return Dao.update(basePath+"updateCompany", param);
	}
	
	public int delCompany(Map<String,Object> param){
		return Dao.delete(basePath+"delCompany",param);
	}
	/**
	 * 删除厂商（ps.修改厂商的状态）
	 * Author: SnowWolf
	 * Time: 2015年3月9日
	 * @param param
	 * @return int
	 */
	public int delCom(Map<String,Object> param){
		return Dao.delete(basePath+"updateProductStatus",param);
	}
	
	public int getCompanyProject(Map<String, Object> param){
		return Dao.selectInt(basePath+"getCompanyProject", param);
	}
	
	public List<Object> getSuppMessByCompanyId(Map<String,Object> param){
		return Dao.selectList(basePath+"getSupByCompanyId", param);
	}
	public List<Object> getSuppMessByCompanyId1(){
		return Dao.selectList(basePath+"getSupByCompanyId1");
	}
	public List<Object> getSuppMessByCompanyId2(){
		return Dao.selectList(basePath+"getSupByCompanyId2");
	}
	public int updateOrgIdByCompanyId(Map<String,Object> param ){
		return Dao.update(basePath+"updateOrgIdByCompanyId", param);
	}
	
	public int updateOrgIdForBlankByCompanyId(Map<String,Object> param ){	
		return Dao.update(basePath+"updateOrgIdForBlankByCompanyId", param);
	}

	public int getcompanyByNameOrCode(Map<String, Object> param){
		return Dao.selectInt(basePath+"getcompanyByNameOrCode", param);
	}
	
	/**
	 * 根据平台及经销商id查询厂商信息
	 * @param supplier_id 经销商id
	 * @param MANAGER_ID 平台id
	 * @return
	 * @author King 2015年3月15日
	 */
	public List<Map<String,Object>> queryCompanyBySupId(String supplier_id,String MANAGER_ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("MANAGER_ID", MANAGER_ID);
		map.put("SUP_ID", supplier_id);
		return Dao.selectList(basePath+"queryCompanyBySupId", map);
	}
}

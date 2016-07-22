package com.pqsoft.base.financingInstitution.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class FinancingInstitutionService {

	private String basePath = "FinancingInstitution.";
	
	
	public Page getPageData(Map<String,Object> param){
		
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getFinancingList",param));
		page.addDate(array, Dao.selectInt(basePath+"getFinancingCount", param));
		return page;
	}
	
	public int getSuppSeq(){
		return Dao.selectInt(basePath+"getSupplierSeq", null);
	}
	
	public int addSupplier(Map<String,Object> param) {
		return Dao.insert(basePath+"addSupplier",param);
	}
	
	public int updateSupplier(Map<String,Object> param) {
		return Dao.update(basePath+"updateSupplier", param);
	}
	
	public int delSupplier(Map<String,Object> param ){
		return Dao.delete(basePath+"delSupplier", param);
	}
	
	public int delSupplierInfo(Map<String,Object> param){
		return Dao.delete(basePath+"delSupplierInfo",param);
	}
	
	/**
	 * 有没有副表信息?
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午9:07:56
	 */
	public int selectCount(Map<String,Object> param){
		return Dao.selectInt(basePath+"select_count", param);
	}
	
	/**
	 * 修改副表信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午9:16:46
	 */
	public int updateSupplierInfo(Map<String,Object> param){
		return Dao.update(basePath+"updateSupplierInfo",param);
	}
	
	
	/**
	 * 添加副表信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午3:17:27
	 */
	public int addSupplierInfo(Map<String,Object> param) {
		return Dao.insert(basePath+"addSupplierInfo",param);
	}
	
	public Map<String,Object> getOneSupplier(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneSupp", param);
	}
	
	public List<Object> getLinkManList(Map<String,Object> param){
		return Dao.selectList(basePath+"getLinkManList", param);
	}
	
	public List<Object> getInvestsByType(Map<String,Object> param){
		return Dao.selectList(basePath+"getInvestsByType", param);
	}
	
	/**
	 * 根据供应商ID查询上传文件信息
	 */
	public List<Object> findSupFileUploads(String suppliers_id){
		return Dao.selectList(basePath+"findSupFileUploads", suppliers_id);
	}
	
	/**
	 * 查询全部区域
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getAllQuYu() {
		return Dao.selectList(basePath + "getAllQuYu");
	}
	

	/**
	 * 查询全部省份
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getAllProvince() {
		return Dao.selectList(basePath + "getAllProvince");
	}

	/**
	 * 查询全部市
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getAllCity() {
		return Dao.selectList(basePath + "getAllCity");
	}
	
	// 根据AREA_ID 查询区域子集
	public List<Object> getQuYuSubset(Map<String, Object> param) {
		return Dao.selectList(basePath + "getQuYuSubset", param);
	}
	
	public List<Object> getAreaDownByParentId(Map<String,Object> param){
		return Dao.selectList(basePath+"selectProvOrCity", param);
	}
	
	/**
	 * 查询一条副表信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午4:09:58
	 */
	public Map<String,Object> getOneSupplierInfo(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneSuppInfo", param);
	}
	
	/**
	 * 
	 * findCustDataType：用户相关配置信息
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object findCustDataType(){
		Map<String, Object> type = new HashMap<String,Object>();
		//客户类型判断
		String TYPE = "客户类型";
		List<Object> list = (List)new DataDictionaryMemcached().get(TYPE);
		type.put("list", list);
		
		//公司性质
		String com_type = "公司性质";
		List<Object> com_typeL = (List)new DataDictionaryMemcached().get(com_type);
		type.put("com_typeL", com_typeL);
		
		//纳税情况
		List<Object> situation = (List)new DataDictionaryMemcached().get("纳税情况");
		type.put("situation", situation);
		
		//企业规模
		List<Object> SCALE_ENTERPRISE_List = (List)new DataDictionaryMemcached().get("企业规模");
		type.put("SCALE_ENTERPRISE_List", SCALE_ENTERPRISE_List);
		
		//经销商放款方式
		List<Object> LENDING_TYPE = (List)new DataDictionaryMemcached().get("经销商放款方式");
		type.put("LENDING_TYPE", LENDING_TYPE);
		
		return type;
	}
	
}

package com.pqsoft.base.sp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class SpService {
	private String basePath = "Sp.";

	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getSpList",param));
		page.addDate(array, Dao.selectInt(basePath+"getSpCount", param));
		return page;
	}
	
	
	public List<Object> getAllArea(){
		return Dao.selectList(basePath+"selectArea");
	}
	
	/**
	 * 查询有没有副表信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月5日,下午9:33:24
	 */
	public int selectSpInfo(Map<String,Object> param){
		return Dao.selectInt(basePath+"selectSpInfo", param);
	}
	
	/**
	 * 修改SP信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午9:16:46
	 */
	public int updateSupplierInfo(Map<String,Object> param){
		return Dao.update(basePath+"updateSupplierInfo",param);
	}
	
	/**
	 * 添加SP信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午3:17:27
	 */
	public int addSupplierInfo(Map<String,Object> param) {
		return Dao.insert(basePath+"addSupplierInfo",param);
	}
	
	/**
	 * 所有厂商
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月27日,下午7:42:32
	 */
	public List<Object> getAlllCompany(){
		return Dao.selectList(basePath+"selectAllCompany");
	}
	
	public List<Object> getAllSupplier(Map<String,Object> param){
		return Dao.selectList(basePath+"selectAllSupplier",param);
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
	
	public List<Object> getAreaDownByParentId(Map<String,Object> param){
		return Dao.selectList(basePath+"selectProvOrCity", param);
	}
	
	// 根据AREA_ID 查询区域子集
	public List<Object> getQuYuSubset(Map<String, Object> param) {
		return Dao.selectList(basePath + "getQuYuSubset", param);
	}

	public int addSupplier(Map<String,Object> param) {
		return Dao.insert(basePath+"addSp",param);
	}
	
	public int updateSupplier(Map<String,Object> param) {
		return Dao.update(basePath+"updateSp", param);
	}
	
	public int getSuppSeq(){
		return Dao.selectInt(basePath+"getSupplierSeq", null);
	}
	
	public int delSupplier(Map<String,Object> param ){
		return Dao.delete(basePath+"delSupplier", param);
	}
	
	public int delSupplierInfo(Map<String,Object> param ){
		return Dao.delete(basePath+"delSupplierInfo", param);
	}

	/**
	 * 查询一条供应商信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午4:09:58
	 */
	public Map<String,Object> getOneSupplierInfo(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneSpInfo", param);
	}
	
	/**
	 * 
	 * findCustDataType：用户相关配置信息
	 * 1：list 客户类型
	 * 3：com_typeL 公司性质
	 * 7： situation 纳税情况
	 * 8： marital_status 婚姻状况
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
	
	/**
	 * 列表数据
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月4日,上午11:40:34
	 */
	public Page getSuppDataList(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getSuppDateList",param));
		page.addDate(array, Dao.selectInt(basePath+"getSuppDateCount", param));
		return page;
	}
	
	/**
	 * 添加关联关系
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月4日,上午11:40:08
	 */
	public int addSuppDealer(Map<String,Object> param){
		return Dao.insert(basePath+"addSuppDealer",param);
	}
	
	/**
	 * 删除供应商和sp关联关系
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月4日,上午11:39:41
	 */
	public int delSpSupp(Map<String,Object> param){
		return Dao.delete(basePath+"delSpSupp",param);
	}
	
	public int selectSupName(Map<String,Object> param){
		return Dao.selectInt(basePath+"selectSupName", param);
	}
}

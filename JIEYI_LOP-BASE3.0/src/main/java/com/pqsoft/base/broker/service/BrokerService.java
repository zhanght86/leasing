package com.pqsoft.base.broker.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONArray;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.BaseUtil;

public class BrokerService {
	private String basePath = "Broker.";
	
	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getSupplierList",param));
		page.addDate(array, Dao.selectInt(basePath+"getSupplierCount", param));
		return page;
	}
	
	public Map<String,Object> getOneSupplier(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneSupp", param);
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
	
	public int updateSupplierNP(Map<String,Object> param){
		return Dao.update(basePath+"updateSupplierNP",param);
	}
	
	public int delSupplier(Map<String,Object> param ){
		return Dao.delete(basePath+"delSupplier", param);
	}
	
	public int updateInvestor(Map<String,Object> param){
		return Dao.update(basePath+"updateInvestor", param);
	}
	
	public int addInvestor(Map<String,Object> param){
		return Dao.insert(basePath+"addInvestor", param);
	}
	
	public int updataLinkMan(Map<String,Object> param){
		return Dao.update(basePath+"updateLinkMan", param);
	}
	
	public int addLinkMan(Map<String,Object> param){
		return Dao.insert(basePath+"addLinkMan", param);
	} 
	
	public int delOneLinkMan(Map<String,Object> param){
		return Dao.delete(basePath+"delLinkMan", param);
	}
	
	public int delInvest(Map<String,Object> param){
		return Dao.delete(basePath+"delInvest",param);
	}
	
	public List<Object> getLinkManList(Map<String,Object> param){
		return Dao.selectList(basePath+"getLinkManList", param);
	}
	
	public List<Object> getInvestsByType(Map<String,Object> param){
		return Dao.selectList(basePath+"getInvestsByType", param);
	}

	public List<Object> getAllArea(){
		return Dao.selectList(basePath+"selectArea");
	}
	
	public List<Object> getAlllCompany(){
		return Dao.selectList(basePath+"selectAllCompany");
	}
	
	public List<Object> getAreaDownByParentId(Map<String,Object> param){
		return Dao.selectList(basePath+"selectProvOrCity", param);
	}
	/**
	 * 
	 * @Description: 上传文件	 
	 * @return Map
	 * @throws Exception
	 * @date 2013-8-29
	 */
	public Map<String, Object> uploadFileForOne(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			final long MAX_SIZE = 30 * 1024 * 1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			String root = SkyEye.getConfig("file.path").toString();//
			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);
			List<?> fileList = fp.parseRequest(request);
			Iterator<?> it = fileList.iterator();
			List<Object> filepathList = new ArrayList<Object>();
			File file = null;
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					// 页面参数
					dataMap.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} else {
					String dir = root+ File.separator+"supplier"+File.separator+ request.getParameter("SUP_NAME").toString()
							+ File.separator+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					BaseUtil.createDirectory(dir);
					file = new File(dir + File.separator + UUID.randomUUID()
							+ fileItem.getName());
					fileItem.write(file);
					 dataMap.put("FIL_NAME", fileItem.getName());
					 dataMap.put("FIL_PATH",file.getPath().toString().replace("\\", "/"));
					filepathList.add(file.getAbsoluteFile());
				}
			}

		} catch (Exception e) {
			 e.printStackTrace();
		}
		return dataMap;
	}
	
	/**
	 * 供应商添加上传文件信息 
	 */
	public int addSupFileUpload(Map<String,Object> map){
		return Dao.insert(basePath+"addSupFileUpload", map);
	}
	
	/**
	 * 根据供应商ID查询上传文件信息
	 */
	public List<Object> findSupFileUploads(String suppliers_id){
		return Dao.selectList(basePath+"findSupFileUploads", suppliers_id);
	}
	
	/**
	 * 根据ID查询附件信息
	 */
	public Map<String,Object> findDupFileByID(String fil_id){
		return Dao.selectOne(basePath+"findDupFileByID", fil_id);
	}
	
	/**
	 * 删除附件信息
	 */
	public int deleteSupFile(String fil_id){
		return Dao.update(basePath+"deleteSupFile", fil_id);
	}
	/**
	 * 查询供应商列表
	 * @param map
	 * @return
	 * @author:King 2013-9-6
	 */
	public List<Map<String,Object>> queryAllSuppleirs(Map<String,Object> map){
		return Dao.selectList(basePath+"getOneSupp", map);
	}
	
	public int updateSupOrgIdBySupId(Map<String,Object> param ){
		return Dao.update(basePath+"updateSupOrgIdBySupId", param);
	}
	
	public int updateSupOrgIdForBlankBySupId(Map<String,Object> param ){
		return Dao.update(basePath+"updateSupOrgIdForBlankBySupId", param);
	}
	/**
	 * 查询供应商的开关
	 * @param SUP_ID
	 * @return
	 * @author:King 2013-11-19
	 */
	public Map<String,Object> doShowSwitch(String SUP_ID){
		return Dao.selectOne(basePath+"doShowSwitch", SUP_ID);
	}
	
	public int supSwitchMethod(Map<String,Object> param ){
		return Dao.update(basePath+"updateSwitchStatus", param);
	}
	
	//通过平台和所选厂商查询出符合条件的供应商（加上开关条件）
	public List querySuppByCom(Map<String,Object> param){
		return Dao.selectList(basePath+"querySuppByCom",param);
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
		
		// 证件类型
		String id_type = "证件类型";
		List<Object> id_typeL = (List) new DataDictionaryMemcached()
				.get(id_type);
		type.put("id_typeL", id_typeL);
		
		// 文化程度
		List<Object> degree_edu = (List) new DataDictionaryMemcached()
				.get("文化程度");
		type.put("degree_edu", degree_edu);
		
		// 婚姻状况
		List<Object> marital_status = (List) new DataDictionaryMemcached()
				.get("婚姻状况");
		type.put("marital_status", marital_status);
		
		// 从事职业
		List<Object> profession = (List) new DataDictionaryMemcached()
				.get("职务");
		type.put("profession", profession);

		// 民族
		List<Object> nationL = (List) new DataDictionaryMemcached().get("民族");
		type.put("nationL", nationL);
		
		//公司性质
		List<Object> com_typeL = (List)new DataDictionaryMemcached().get("公司性质");
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
	
	public int addSupplierInfo(Map<String,Object> param) {
		return Dao.insert(basePath+"addSupplierInfo",param);
	}
	
	public int updateSupplierInfo(Map<String,Object> param){
		return Dao.update(basePath+"updateSupplierInfo",param);
	}
	
	// 根据AREA_ID 查询区域子集
	public List<Object> getQuYuSubset(Map<String, Object> param) {
		return Dao.selectList(basePath + "getQuYuSubset", param);
	}	
	
	public Map<String,Object> getOneSupplierInfo(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneSpInfo", param);
	}
	
	public int addSupplierNP(Map<String,Object> param){
		return Dao.insert(basePath+"addSupplierNP",param);
	}
	
	public int addSupplierNPInfo(Map<String,Object> param){
		return Dao.insert(basePath+"addSupplierNPInfo",param);
	}
}

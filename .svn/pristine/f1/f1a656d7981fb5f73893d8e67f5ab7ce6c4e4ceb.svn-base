package com.pqsoft.base.suppliers.service;

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

public class SuppliersService {
	private String basePath = "Suppliers.";
	
	public Page getPageData(Map<String,Object> param){
		BaseUtil.getDataAllAuth(param);
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
	
	public int delSupplier(Map<String,Object> param ){
		return Dao.delete(basePath+"delSupplier", param);
	}
	
	public int delSupplierInfo(Map<String,Object> param){
		return Dao.delete(basePath+"delSupplierInfo",param);
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
	
	public List<Object> getAreaDownByParentName(Map<String,Object> param){
		return Dao.selectList(basePath+"selectProvOrCityByName", param);
	}
	
	// 根据AREA_ID 查询区域子集
	public List<Object> getQuYuSubset(Map<String, Object> param) {
		return Dao.selectList(basePath + "getQuYuSubset", param);
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
	public int updateSupOrgIdBySupId1(Map<String,Object> param ){
		param.put("SUP_ID", param.get("SP_ID"));
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
	
	//add gengchangbao JZZL-203 2016年6月13日10:14:48 start
	//add gengchangbao 根据采购类型 查询经销商
	public List querySuppByCgxl(Map<String,Object> param){
		return Dao.selectList(basePath+"querySuppByCgxl",param);
	}
	//add gengchangbao JZZL-203 2016年6月13日10:14:48 end
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
		
		//是否销售服务一体化
		//mod gengchangbao JZZL-203 2016年6月13日10:14:48 start
		//List<Object> WHETHER_SALES_SI = (List)new DataDictionaryMemcached().get("是否销售服务一体化");
		List<Object> WHETHER_SALES_SI = (List)new DataDictionaryMemcached().get("采购类型");
		//mod gengchangbao JZZL-203 2016年6月13日10:14:48 start
		type.put("WHETHER_SALES_SI", WHETHER_SALES_SI);
		
		return type;
	}
	
	/**
	 * 查询所属集团
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月5日,上午10:35:47
	 */
	public List<Map<String,Object>> findGroup(Map<String,Object> param){
		return Dao.selectList(basePath+"findGroup", param);
	}
	
	public Page underPageData(Map<String ,Object> param){
		Page page = new Page(param); 
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getUnderSuppliersList",param));
		page.addDate(array, Dao.selectInt(basePath+"getUnderSuppliersCount", param));
		return page;
	}
	
	public int checkGroup(Map<String,Object> param){
		return Dao.selectInt(basePath+"checkGroup", param);
	}
	
	public int addSuppCompany(Map<String,Object> param){
		return Dao.insert(basePath+"addSuppCompany",param);
	}
	
	public List<Map<String,Object>> getSuppCompany(Map<String,Object> param){
		return Dao.selectList(basePath+"getSuppCompany",param);
	}
	
	public List<Map<String,Object>> getSuppCompanyBySupid(Map<String,Object> param){
		return Dao.selectList(basePath+"getSuppCompanyBySupid",param);
	}
	
	public int delSuppCompany(Map<String,Object> param){
		return Dao.delete(basePath+"delSuppCompany",param);
	}
	
	public int checkDel(Map<String,Object> param){
		return Dao.selectInt(basePath+"checkDel", param);
	}
	
	public int checkDel2(Map<String,Object> param){
		return Dao.selectInt(basePath+"checkDel2", param);
	}
	
	public int updateStatus(Map<String,Object> param){
		return Dao.update(basePath+"updateStatus",param);
	}
	
	public int selectSupName(Map<String,Object> param){
		return Dao.selectInt(basePath+"selectSupName", param);
	}
	
	public int addManager(Map<String,Object> param){
		return Dao.insert(basePath+"addManager", param);
	}
	
	public int addSpSupp(Map<String,Object> param){
		return Dao.insert(basePath+"addSpSupp",param);
	}
	
	public List<Map<String,Object>> findAreaManager(){
		return Dao.selectList(basePath+"findAreaManager");
	}
	
	/**
	 * 返回省市区字符串
	 * @param PROV 省id
	 * @param CITY 市id
	 * @param AREA 区id
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月30日,下午5:24:34
	 */
	public String selectAreaText(Object PROV,Object CITY,Object AREA){
		String areaText = "";
		Map<String,Object> map = new HashMap<String, Object>();
		if(PROV!=null&&!"".equals((String)PROV)){
			map.put("ID", PROV);
			areaText += Dao.selectOne(basePath+"selectAreaText", map);
		}
		if(CITY!=null&&!"".equals((String)CITY)){
			map.put("ID", CITY);
			areaText += Dao.selectOne(basePath+"selectAreaText", map);
		}
		if(AREA!=null&&!"".equals((String)AREA)){
			map.put("ID", AREA);
			areaText += Dao.selectOne(basePath+"selectAreaText", map);
		}
		return areaText;
	}
	
	public List<Map<String, Object>> showSuppliersList() {
		return Dao.selectList(basePath + "showSuppliersList");
	}
	
	//add gengchangbao JZZL-231 2016年6月27日15:34:49 Start
	public List<Map<String,Object>> getJxsDataSS(){
		return Dao.selectList(basePath+"getJxsDataSS");
	}
	//add gengchangbao JZZL-231 2016年6月27日15:34:49 End
}

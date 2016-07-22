package com.pqsoft.baseScheme.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/**
 * 商务政策的维护
 * 
 * @author King 2013-8-27
 */
public class BaseSchemeService {
	private String namespace = "baseScheme.";

	/**
	 * 查询商务政策列表
	 * 
	 * @param param
	 * @return
	 * @author:King 2013-8-28
	 */
	public Page baseSchemeManager(Map<String, Object> param) {
		return PageUtil.getPage(param, namespace + "queryBaseSchemeList", namespace + "queryBaseSchemeCount");
	}

	/**
	 * 查询商务政策信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-31
	 */
	public List<Map<String, Object>> queryBaseSchemeList(Map<String, Object> map) {
		return Dao.selectList(namespace + "queryBaseSchemeList", map);
	}

	/**
	 * 添加保存商务政策
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-30
	 */
	public int doAddBaseSchemeYearRate(Map<String, Object> map) {
		String ID = Dao.getSequence("SEQ_BASE_SCHEME_RATE");
		map.put("ID", ID);
		Dao.insert(namespace + "addBaseSchemeYearRate", map);
		return Integer.parseInt(ID);
	}

	/**
	 * 查询商务政策中配置的年利率信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-30
	 */
	public List<Map<String, Object>> doSelectBaseSchemeYearRate(Map<String, Object> map) {
		return Dao.selectList(namespace + "doSelectBaseSchemeYearRate", map);
	}

	public int doSelectBaseSchemeYearRateCount(Map<String, Object> map) {
		return Dao.selectInt(namespace + "doSelectBaseSchemeYearRateCount", map);
	}

	/**
	 * 删除商务政策中配置的年利率信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-30
	 */
	public int doDelBaseSchemeYearRate(Map<String, Object> map) {
		return Dao.delete(namespace + "delBaseSchemeYearRate", map);
	}

	/**
	 * 添加商务政策手续费率
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-31
	 */
	public int doAddBaseSchemeFeeRate(Map<String, Object> map) {
		String ID = Dao.getSequence("SEQ_BASE_SCHEME_FEE_RATE");
		map.put("ID", ID);
		Dao.insert(namespace + "doAddBaseSchemeFeeRate", map);
		return Integer.parseInt(ID);
	}

	/**
	 * 查询商务政策中配置的手续费率信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-30
	 */
	public List<Map<String, Object>> doSelectBaseSchemeFeeRate(Map<String, Object> map) {
		map.put("_TYPE", "收取方式");
		map.put("_TYPE_DS", "代收方式");
		return Dao.selectList(namespace + "doSelectBaseSchemeFeeRate", map);
	}

	public int doSelectBaseSchemeFeeRateCount(Map<String, Object> map) {
		return Dao.selectInt(namespace + "doSelectBaseSchemeFeeRateCount", map);
	}

	/**
	 * 删除商务政策中配置的手续费率
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-31
	 */
	public int doDelBaseSchemeFeeRate(Map<String, Object> map) {
		return Dao.delete(namespace + "delBaseSchemeFeeRate", map);
	}

	/**
	 * 查询商务政策数量
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-31
	 */
	public int queryBaseSchemeCount(Map<String, Object> map) {
		return Dao.selectInt(namespace + "queryBaseSchemeCount", map);
	}

	/**
	 * 添加商务政策信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-31
	 */
	public int doAddBaseScheme(Map<String, Object> map) {
		if ((map.containsKey("VALUE_STATUS") && !"".equals(map.get("VALUE_STATUS").toString().trim()) && !"0".equals(map.get("VALUE_STATUS"))) || (map.get("VALUE_STATUS") + "").toUpperCase().contains("READ")) {
			map.put("VALUE_STATUS", "1");
		} else {
			map.put("VALUE_STATUS", "0");
		}
		if ("全部".equals(map.get("VALUE_STR"))) {
			map.put("VALUE_STR", "");
		}
		System.out.println("kkkkkkkkkkk="+map);
		return Dao.insert(namespace + "doAddBaseScheme", map);
//		return 1;
	}

	/**
	 * 修改商务政策信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-9-2
	 */
	public int doUpdateBaseScheme(Map<String, Object> map) {
		if (map.containsKey("VALUE_STATUS") && !"".equals(map.get("VALUE_STATUS")) && !"0".equals(map.get("VALUE_STATUS"))) {
			map.put("VALUE_STATUS", "1");
		} else {
			map.put("VALUE_STATUS", "0");
		}
		if ("全部".equals(map.get("VALUE_STR"))) {
			map.put("VALUE_STR", "");
		}
		return Dao.update(namespace + "doUpdateBaseScheme", map);
	}
	public static void main(String[] args) {
		System.out.println("dddasdf,".lastIndexOf(","));
	}

	/**
	 * 查询商务政策明细项
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-9-2
	 */
	public List<Map<String, Object>> doSelectSchemeDetailByName(Map<String, Object> map) {
		return Dao.selectList(namespace + "doSelectSchemeDetailByName", map);
	}

	/**
	 * 修改商务政策查询数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-6
	 */
	public List<Map<String, Object>> doSelectSchemeDetailForUpdateByName(Map<String, Object> map) {
		return Dao.selectList(namespace + "doSelectSchemeDetailForUpdateByName", map);
	}

	/**
	 * 查询商务政策明细项
	 * 
	 * @param scheme_name
	 *            商务政策名称
	 * @param status
	 *            状态 0 表示启用 1表示作废
	 * @param FLAG
	 *            是否查询供应商、厂商、产品及业务模式等在商务政策中排序为负数的数据 空表示查询 否则不查询
	 * @param
	 * @return
	 * @author:King 2013-9-4
	 */
	public List<Map<String, Object>> doSelectSchemeDetailByName(String scheme_name, String status, String FLAG) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SCHEME_NAME", scheme_name);
		map.put("SCHEME_CODE", scheme_name);
		map.put("STATUS", status);
		map.put("FLAG", FLAG);
		return doSelectSchemeDetailByName(map);
	}

	/**
	 * 作废商务政策数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-9-2
	 */
	public int doDelBaseScheme(Map<String, Object> map) {
		return Dao.delete(namespace + "doDelBaseScheme", map);
	}

	/**
	 * 查询商务政策信息列表--用于立项
	 * @param map
	 * @param TYPE 类别  WX标识来自微信端报价  否则来自网页端
	 * @return
	 * @author King 2015年2月27日
	 */
	public List<Map<String, Object>> querySchemeInfoList(Map<String, Object> map,String TYPE) {
		map.put("_SOURCE_TYPE_", TYPE);
		map.put("_SOURCE_TYPE_VALUE", "是");
		System.out.println("---------------map="+map);
		List<Map<String, Object>> list = Dao.selectList(namespace + "querySchemeInfoList", map);
//		 TODO King 屏蔽自定义商务政策模式开关
//		 if (list.size() > 0) {
//			 for (int i = 0; i < list.size(); i++) {
//				 Map<String, Object> map2 = list.get(i);
//				 if (map2.get("SCHEME_NAME").equals("自定义")) {
//					 list.remove(i);
//					 break;
//				 }
//			 }
//		 }
		return list;
	}

	/**
	 * 修改商务政策利率信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-9-7
	 */
	public int doUpdateBaseSchemeYearRate(Map<String, Object> map) {
		return Dao.update(namespace + "doUpdateBaseSchemeYearRate", map);
	}

	public int doUpdateBaseSchemeYearRate_base(Map<String, Object> map) {
		return Dao.update(namespace + "doUpdateBaseSchemeYearRate_base", map);
	}

	public int doUpdateBaseSchemeFeeRate(Map<String, Object> map) {
		return Dao.update(namespace + "doUpdateBaseSchemeFeeRate", map);
	}

	public int doUpdateBaseSchemeFeeRate_base(Map<String, Object> map) {
		return Dao.update(namespace + "doUpdateBaseSchemeFeeRate_base", map);
	}

	/**
	 * 查询数据库中新的政策元素
	 * 
	 * @return
	 * @author:King 2013-9-7
	 */
	public List<Map<String, Object>> queryNewSchemeElement() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_TYPE", "政策元素");
		int count=Dao.selectInt(namespace+"queryAllSchemeCount", null);
		map.put("COUNT_", count);
		return Dao.selectList(namespace + "queryNewSchemeElement", map);
	}

	/**
	 * 查询商务政策名称列表
	 * 
	 * @return
	 * @author:King 2013-9-7
	 */
	public List<Map<String, Object>> querySchemeNameList(Map<String,Object> map) {
		return Dao.selectList(namespace + "querySchemeNameList",map);
	}

	/**
	 * 同步新的商务政策元素信息到已维护好的商务政策中
	 * 
	 * @return
	 * @author:King 2013-9-7
	 */
	public int updateNewSchemeElement() {
		List<Map<String, Object>> newSchemeElementList = queryNewSchemeElement();
		if (newSchemeElementList.size() > 0) {
			int i = 0;
			for (Map<String, Object> map : newSchemeElementList) {
			List<Map<String, Object>> schemeNameList = querySchemeNameList(map);
				for (Map<String, Object> m : schemeNameList) {
					m.putAll(map);
					m.put("STATUS","1");
					i += doAddBaseScheme(m);
				}
			}
			return i;
		} else
			return 0;
	}

	/**
	 * 根据厂商、供应商id及商务政策名称查询满足的产品信息
	 * 
	 * @param supplier_id
	 * @param company_id
	 * @param scheme_name
	 * @param PROJECT_MODEL
	 *            业务模式名称
	 * @return
	 * @author:King 2013-9-15
	 */
	public List<Map<String, Object>> showProductListBySchemeInfo(String supplier_id, String company_id, String scheme_name, String PROJECT_MODEL) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUPPLIER_ID", supplier_id);
		map.put("COMPANY_ID", company_id);
		map.put("SCHEME_NAME", scheme_name);
		map.put("PROJECT_MODEL", PROJECT_MODEL);
		List<Map<String, Object>> list = Dao.selectList(namespace + "showProductListBySchemeInfo1", map);
		if (list.size() > 0) {
			return list;
		} else
			return Dao.selectList(namespace + "showProductListBySchemeInfo", map);
	}

	public Map<String,Object> queryYearRateBySchem(Map<String,Object>  map) {
		return Dao.selectOne(namespace + "queryYearRateBySchem", map);
	}

	public Map queryFeeBySchem(Map map) {
//		return Dao.selectOne(namespace + "queryFeeBySchem", map); // luyanfeng
		List<Map> selectList = Dao.selectList(namespace + "queryFeeBySchem", map);
		if( selectList == null || selectList.isEmpty()){
			return Collections.emptyMap();
		}
		return (Map) selectList.get(0);
	}

	/**
	 * 查收所有的供应商id及名称
	 * 
	 * @return
	 * @author:King 2013-9-28
	 */
	public List<Map<String, Object>> doShowSupplierInfo(Map<String, Object> map) {
		if (StringUtils.isBlank(map))
			map = new HashMap<String, Object>();
		return Dao.selectList(namespace + "doShowSupplierInfo", map);
	}

	/**
	 * 根据类型筛选sp、经销集团等信息   经销商类型：1：经销商，2：SP，3：经纪人  4经销集团
	 * @param SUP_TYPE
	 * @return
	 * @author King 2015年2月27日
	 */
	public List<Map<String,Object>> querysp(String SUP_TYPE){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("SUP_TYPE", SUP_TYPE);
		return Dao.selectList(namespace+"querysp", map);
	}
	public List<Map<String,Object>> querysp(String SUP_TYPE,Object SUP_NAME){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("SUP_TYPE", SUP_TYPE);
		if(StringUtils.isNotBlank(SUP_NAME))
			map.put("SUP_NAME", SUP_NAME);
		else
			map.put("SUP_NAME", "");
		return Dao.selectList(namespace+"querysp", map);
	}

	/**
	 * 产品
	 * @param COMPANY_ID 厂商ID
	 * @param PRODUCT_NAME 品牌名称
	 * @return
	 * @author King 2015年4月28日
	 */
	public List<Object> getProBig(String COMPANY_ID,String PRODUCT_NAME) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("COMPANY_ID", COMPANY_ID);
		if(StringUtils.isNotBlank(PRODUCT_NAME))
			param.put("PRODUCT_NAME", PRODUCT_NAME);
		else
			param.put("PRODUCT_NAME", "");
			
		System.out.println(param);
		return Dao.selectList(namespace + "getAllProduct", param);
	}
	
	/**
	 * 产品车系
	 * @param PRODUCT_ID 品牌id
	 * @param CATENA_NAME  车系名称
	 * @return
	 * @author King 2015年4月28日
	 */
	public List<Object> getProCATENA(String PRODUCT_ID,String CATENA_NAME) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PRODUCT_ID", PRODUCT_ID);
		if(StringUtils.isNotBlank(CATENA_NAME)){
			param.put("CATENA_NAME", CATENA_NAME);
		}else
			param.put("CATENA_NAME", "");
			
		return Dao.selectList(namespace + "getProCATENA", param);
	}
	/**
	 * 产品类型(车型)
	 * @param COMPANY_ID
	 * @param CATENA_ID
	 * @param CXX_NAME车型名称
	 * @return
	 * @author:King 2014-3-26
	 */
	public List<Object> getAllProductType(String COMPANY_ID,String CATENA_ID,String CXX_NAME) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("COMPANY_ID", COMPANY_ID);
		param.put("CATENA_ID", CATENA_ID);
		param.put("CXX_NAME", CXX_NAME);
		return Dao.selectList(namespace + "getAllProductType", param);
	}

	/**
	 * 根据厂商ID查询供应商信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-12-1
	 */
	public List<Map<String, Object>> getSuppliers(Map<String, Object> map) {
		return Dao.selectList(namespace + "getSuppliers", map);
	}

	/**
	 * 查询已经选中的厂商信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-14
	 */
	public List<Map<String, Object>> doShowChoosedCompanyNames(Map<String, Object> map) {
		return Dao.selectList(namespace + "doShowChoosedCompanyNames", map);
	}

	/**
	 * 根据供应商id查询符合的业务模式
	 * 
	 * @param SUP_ID
	 * @return
	 * @author:King 2014-3-4
	 */
	public List<Map<String, Object>> doShowProjectModelBySupId(String SUP_ID) {
		if (StringUtils.isBlank(SUP_ID)) {
			return new ArrayList<Map<String, Object>>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUP_ID", SUP_ID);
		return Dao.selectList(namespace + "doShowProjectModelBySupId", map);
	}

	/**
	 * 查询厂商
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-26
	 */
	public List<Map<String,Object>> getAllCompany(String FMA_ID, String FMA_NAME) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FMA_ID", FMA_ID);
		map.put("FMA_NAME", FMA_NAME);
		map.put("_TYPE", "厂商");
		return Dao.selectList(namespace + "getAllCompany", map);
	}
	
	/**
	 * @author wuyanfei
	 * @return
	 */
	public List<Object> getAllCompany() {
		Map<String, Object> map = new HashMap<String, Object>();
		return Dao.selectList(namespace + "getCompanyAll", map);
	}


	/**
	 * 从数据字典中读取商务政策元素信息 并按照简称排序
	 * 
	 * @return
	 * @author:King 2014-1-4
	 */
	public List<Map<String, Object>> querySchemeCellFromDictionary() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_TYPE", "政策元素");
		return Dao.selectList(namespace + "querySchemeCellFromDictionary", map);
	}

	/**
	 * 获取商务政策编号
	 * 
	 * @return
	 * @author:King 2014-3-27
	 */
	public String getSchemeCode() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取业务平台列表
	 * 
	 * @return
	 * @author:King 2014-3-27
	 */
	public List<Map<String, Object>> getFHFA_MANAGER() {
		return Dao.selectList(namespace + "getFHFA_MANAGER");
	}

	/**
	 * 获取所属行业
	 * 
	 * @param FMA_ID
	 *            平台ID
	 * @param FMA_NAME
	 *            平台名称
	 * @param _TYPE
	 *            平台下的分类
	 * @return
	 * @author:King 2014-3-27
	 */
	public List<Map<String, Object>> getFHFA_MANAGERSUBINFO(String FMA_ID, String FMA_NAME, String _TYPE) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_TYPE", _TYPE);
		map.put("FMA_NAME", FMA_NAME);
		map.put("FMA_ID", FMA_ID);
		return Dao.selectList(namespace + "getFHFA_MANAGERSUBINFO", map);
	}
	/**
	 * 获取所属业务类型
	 * 
	 * @param FMA_ID
	 *            平台ID
	 * @param FMA_NAME
	 *            平台名称
	 * @param _TYPE
	 *            平台下的分类
	 * @return
	 * @author:King 2014-3-27
	 */
	public List<Map<String, Object>> getFHFA_MANAGERYW(String FMA_ID, String FMA_NAME, String _TYPE) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_TYPE", _TYPE);
		map.put("FMA_NAME", FMA_NAME);
		map.put("FMA_ID", FMA_ID);
		return Dao.selectList(namespace + "getFHFA_MANAGERYW", map);
	}
	
	/**
	 * 获取所属业务类型
	 * 
	 * @param FMA_ID
	 *            平台ID
	 * @param FMA_NAME
	 *            平台名称
	 * @param _TYPE
	 *            平台下的分类
	 * @return
	 * @author:King 2014-3-27
	 */
	public List<Map<String, Object>> getFHFA_MANAGERYW_ORG(String FMA_ID, String FMA_NAME, String _TYPE, String ORG_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_TYPE", _TYPE);
		map.put("FMA_NAME", FMA_NAME);
		map.put("FMA_ID", FMA_ID);
		map.put("ORG_ID", ORG_ID);
		return Dao.selectList(namespace + "getFHFA_MANAGERYW", map);
	}
	
	/**
	 * add gengchangbao JZZL-205
	 * 获取所属系统
	 * @return
	 */
	public Map<String, Object> getSystemName(String ORG_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ORG_ID", ORG_ID);
		return Dao.selectOne(namespace + "getSystemName", map);
	}

	/**
	 * 获取平台下的省份
	 * 
	 * @param FMA_ID
	 *            平台ID
	 * @param FMA_NAME
	 *            平台名称
	 * @return
	 * @author:King 2014-3-27
	 */
//	public List<Map<String, Object>> getFhfaArea(String FMA_ID, String FMA_NAME) {
////		Map<String, Object> map = new HashMap<String, Object>();
////		map.put("FMA_ID", FMA_ID);
////		map.put("FMA_NAME", FMA_NAME);
////		return Dao.selectList(namespace + "getFhfaArea", map);
//	}
	
	/**
	 * 查询公司平台下的区域
	 * @param MANAGER_ID 公司平台
	 * @param parent_id  显示层级区域的父ID
	 * @param area_leve 父ID级别  国家1 省2 市3 县 4
	 * @return
	 * @author King 2015年3月6日
	 */
	public List<Object> getFhfaArea(String FMA_NAME,String parent_id,int area_leve){
		List<Object> lst=new ArrayList<Object>();
		AreaService aservice=new AreaService();
		Map<String,Object> mm=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(parent_id)){
			mm.put("PARENT_ID", parent_id);
			lst.addAll(aservice.getSubset(mm));
		}else{
			mm.put("FMA_NAME", FMA_NAME);
			List<Map<String,Object>>list=Dao.selectList(namespace+"queryArea", mm);
			for (Map<String, Object> map : list) {
				String []AREAID=null;
				if(map.containsKey("AREAID")&&StringUtils.isNotBlank(map.get("AREAID"))){
					AREAID=map.get("AREAID").toString().split(",");
					
					if(AREAID.length>=area_leve){
						map.put("ID", AREAID[area_leve-1]);
//						map.put("PARENT_ID", AREAID[area_leve-1]);
						lst.add(Dao.selectOne(namespace+"selectArea", map));
//						lst.addAll(aservice.getSubset(map));
					}else{
						map.put("PARENT_ID", AREAID[area_leve-2]);
						lst.addAll(Dao.selectList(namespace+"selectSubset", map));
					}
				}
			}
		}
		return lst;
	}
	/**
	 * 获取平台下的大区
	 * 
	 * @param FMA_ID
	 *            平台ID
	 * @param FMA_NAME
	 *            平台名称
	 * @return
	 * @author:King 2014-3-27
	 */
	public List<Map<String, Object>> getFhfaAreaBig(String FMA_ID, String FMA_NAME) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FMA_ID", FMA_ID);
		map.put("FMA_NAME", FMA_NAME);
		return Dao.selectList(namespace + "getFhfaAreaBig", map);
	}

	/**
	 * 根据项目ID查询项目方案基本信息列表
	 * 
	 * @param PROJECT_ID
	 *            项目ID
	 * @param SCHEME_ID
	 *            方案ID
	 * @param SCHEME_ROW_NUM
	 *            设备标识
	 * @return
	 * @author:King 2014-3-29
	 */
	public List<Map<String, Object>> getSchemeBaseInfoByProjectId(String PROJECT_ID, String SCHEME_ID, String SCHEME_ROW_NUM) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("SCHEME_ID", SCHEME_ID);
		map.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
		return Dao.selectList(namespace + "getSchemeBaseInfoByProjectId", map);
	}

	/**
	 * 查询方案大字段 用于项目查看</br> 注意:方案ID 和设备标识必须存在一个
	 * 
	 * @param SCHEME_ID
	 *            方案ID
	 * @param PROJECT_ID
	 *            项目ID
	 * @param SCHEME_ROW_NUM
	 *            设备标识
	 * @return
	 * @author:King 2014-3-29
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSchemeClob(String SCHEME_ID, String PROJECT_ID, String SCHEME_ROW_NUM) {
		if (StringUtils.isBlank(SCHEME_ROW_NUM) && StringUtils.isBlank(SCHEME_ID))
			throw new ActionException("缺少方案唯一参数");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SCHEME_ID", SCHEME_ID);
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
		List<Map<String, Object>> object= Dao.selectList(namespace + "getSchemeClobBySchemeId", map);
		if(object!=null){
			map.putAll((Map<? extends String, ? extends Object>)object.get(0));
			Dao.getClobTypeInfo2(map, "SCHEME_CLOB");
			JSONArray SCHEME_CLOB=JSONArray.fromObject(map.get("SCHEME_CLOB"));
			/***************************************************************/
			//TODO 需屏蔽
			for (Object object2 : SCHEME_CLOB) {
				((JSONObject) object2).put("CODE_MONEY",((JSONObject) object2).get("COMPUTE"));
			}
			/***************************************************************/
			System.out.println("-------------SCHEME_CLOB="+SCHEME_CLOB);
			return SCHEME_CLOB;
		}
		throw new ActionException("缺少方案");
	}
	
	/**
	 * 查询方案大字段 用于项目查看
	 * 
	 * @param PROJECT_ID
	 *            项目ID
	 * @return
	 * @author:gengchangbao
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSchemeClob_new(String PROJECT_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		List<Map<String, Object>> object= Dao.selectList(namespace + "getSchemeClobBySchemeId", map);
		if(object!=null){
			map.putAll((Map<? extends String, ? extends Object>)object.get(0));
			Dao.getClobTypeInfo2(map, "SCHEME_CLOB");
			JSONArray SCHEME_CLOB=JSONArray.fromObject(map.get("SCHEME_CLOB"));
			/***************************************************************/
			//TODO 需屏蔽
			for (Object object2 : SCHEME_CLOB) {
				((JSONObject) object2).put("CODE_MONEY",((JSONObject) object2).get("COMPUTE"));
			}
			/***************************************************************/
			System.out.println("-------------SCHEME_CLOB="+SCHEME_CLOB);
			return SCHEME_CLOB;
		}
		throw new ActionException("缺少方案");
	}

	/**
	 * 添加项目方案
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-28
	 */
	public String doAddProjectScheme(Map<String,Object> map){
		String SCHEME_ID_SEQ=Dao.getSequence("SEQ_FIL_PROJECT_SCHEME");
		map.put("SCHEME_ID_SEQ", SCHEME_ID_SEQ);
		map.put("CREATE_CODE", Security.getUser().getCode());
//		map.put("YEAR_INTEREST", UtilFinance.formatString(map.get("YEAR_INTEREST"), 10));
		Dao.insert(namespace+"doAddProjectScheme", map);
		Dao.update(namespace+"doUpdEqSchemeid",map);
		String str = map.get("SCHEME_CLOB").toString() ;
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			//add by lishuo 03-30-2016 进件保存添加政策开始时间 Start
			if(mClob!=null && mClob.get("KEY_NAME_EN")!=null){
			    if("KSQX_DATE".equals(mClob.get("KEY_NAME_EN"))){
			        mClob.put("KEY_NAME_ZN","政策的开始时间");
			        mClob.put("VALUE_STATUS","0");
			    }
			    if("KSQX_DATE".equals(mClob.get("KEY_NAME_EN")) && "".equals(mClob.get("VALUE_STR"))){
			        mClob.put("VALUE_STR",null);
			    }
			}
			//add by lishuo 03-30-2016 进件保存添加政策开始时间  End
			mClob.put("MAXVALUE", mClob.get("PRECENTE_VAL_UP"));
			mClob.put("MINVALUE", mClob.get("PRECENTE_VAL_DOWN"));
			mClob.put("SCHEME_ID", SCHEME_ID_SEQ);
			mClob.put("PROJECT_ID", map.get("PROJECT_ID") );
			Dao.insert(namespace+"saveSchemeClob", mClob);
		}
		return SCHEME_ID_SEQ;
	}
	
	public String doAddProjectScheme1(Map<String,Object> map){
		String SCHEME_ID_SEQ=Dao.getSequence("SEQ_FIL_PROJECT_SCHEME");
		String SCHEME_ROW_NUM = Dao.getSequence("SEQ_FIL_PROJECT_EQUIPMENT");
		map.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
		map.put("SCHEME_ID_SEQ", SCHEME_ID_SEQ);
		map.put("CREATE_CODE", Security.getUser().getCode());
		map.put("YEAR_INTEREST", UtilFinance.formatString(map.get("YEAR_INTEREST"), 10));
		Dao.insert(namespace+"doAddProjectScheme", map);
		String str = map.get("SCHEME_CLOB").toString() ;
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map<String,Object> mClob = (Map) iterator.next();
			mClob.put("MAXVALUE", mClob.get("PRECENTE_VAL_UP"));
			mClob.put("MINVALUE", mClob.get("PRECENTE_VAL_DOWN"));
			mClob.put("SCHEME_ID", SCHEME_ID_SEQ);
			mClob.put("PROJECT_ID", map.get("PROJECT_ID") );
			Dao.insert(namespace+"saveSchemeClob", mClob);
		}
		JSONArray EqList=JSONArray.fromObject(map.get("EqList"));
		map.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
		for (int i = 0; i < EqList.size(); i++) {
			Map<String,Object> eqMap = (Map<String,Object>) EqList.get(i);
			if (eqMap != null) {
				eqMap.put("PROJECT_ID", map.get("PROJECT_ID"));
				eqMap.put("SCHEME_ID_ACTUAL", SCHEME_ID_SEQ);
				eqMap.put("USERID", Security.getUser().getId());
				eqMap.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
				System.out.println(eqMap+"++++++++++++++++++++");
				Dao.insert("project.EquipmentAdd", eqMap);
			}
		}
		List<Map<String,Object>> eqList = Dao.selectList(namespace + "queryAmountBySchemeID",SCHEME_ID_SEQ);
		for (int i = 0; i < eqList.size(); i++) {
			Map<String,Object> eqMap =  eqList.get(i);
			int AMOUNT = Integer.parseInt(eqMap.get("AMOUNT") + "");
			for (int h = 0; h < AMOUNT; h++) {
				// 先复制
				Dao.insert("bpm.status.insertEqAmount", eqMap);
			}
			// 在删除原有设备
			Dao.delete("bpm.status.deleteEqById", eqMap);
		}
		return SCHEME_ID_SEQ;
	}

	/**
	 * 修改项目方案
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-28
	 */
	public int doUpdateProjectScheme(Map<String, Object> map) {
//		if(StringUtils.isNotBlank(map.get("YEAR_INTEREST")))
//			map.put("YEAR_INTEREST", UtilFinance.formatString(map.get("YEAR_INTEREST"), 10));
		return Dao.update(namespace + "doUpdateProjectScheme", map);
	}
	
	/**
	 * 复制商务政策
	 * @param map
	 * @return
	 * @author King 2015年2月27日
	 */
	public int doCopyScheme(Map<String,Object> map){
		System.out.println(map);
		int i=Dao.insert(namespace+"copyscheme", map);
		i+=Dao.insert(namespace+"copyscheme_rate", map);
		i+=Dao.insert(namespace+"copyscheme_fee_rate", map);
		return i;
	}
	
	public Map<String,Object> getClient(Map<String,Object> map)
	{
		return Dao.selectOne(namespace+"getClient",map);
	}
	public List<Map<String,Object>> getIdCard(Map<String,Object> param){
//		return Dao.selectList("idCard.getIdCard",param);
		return Dao.selectList("idCard.getIdCardNew",param);
	}
	public int insAuthentication(Map<String,Object> map)
	{
		return Dao.insert(namespace+"insAuthentication",map);
	}
	
	/**
	 * 根据方案编号及元素中文名称查询信息
	 * @param SCHEME_CODE
	 * @param KEY_NAME_ZN
	 * @return
	 * @author King 2015年4月11日
	 */
	public Map<String,Object> queryOneElementByCode(String SCHEME_CODE,String KEY_NAME_ZN){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("KEY_NAME_ZN", KEY_NAME_ZN);
		map.put("SCHEME_CODE", SCHEME_CODE);
		return Dao.selectOne(namespace+"queryOneElementByCode", map);
	}
}

package com.pqsoft.mortgageProduct.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;

public class MortgageProductService {
	
	private final String xmlPath = "mortgageProduct.";

	/**
	 * 查询所有符合条件的抵押产品
	 * @param m
	 * @return
	 */	
	public Page findAllInfo(Map<String, Object> m)  {
		Page page = new Page(m);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"getMorInfor", m);
		JSONArray aJSON = new JSONArray();
		for(int i=0; i<list.size();i++){
			JSONObject obj = new JSONObject();
			Map<String,Object> map = (Map<String,Object>)list.get(i);
			obj.put("CLIENT_CODE", map.get("CLIENT_CODE"));
			obj.put("CLINET_NAME", map.get("CLINET_NAME"));
			obj.put("RENT_ID", map.get("RENT_ID"));
			obj.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
			obj.put("LEASE_TOPRIC", map.get("LEASE_TOPRIC"));
			obj.put("EQUIPMENINFOS", map.get("EQUIPMENINFOS"));
			obj.put("AMOUNT", map.get("AMOUNT"));
			obj.put("MORTGATE_STATUS", map.get("MORTGATE_STATUS"));
			obj.put("PROJECT_ID", map.get("PROJECT_ID"));
			obj.put("PRO_NAME", map.get("PRO_NAME"));
			obj.put("MORTGAGE_PLEDGER", map.get("MORTGAGE_PLEDGER"));
			obj.put("REGISTER_OFFICE", map.get("REGISTER_OFFICE"));
			obj.put("ORGAN_NAME", map.get("ORGAN_NAME"));
			obj.put("zhiliang", "良好");
			aJSON.add(obj);
		}

		page.addDate(aJSON, Dao.selectInt(xmlPath+"getMorInforNo", m));
		return page;
	}
	
	/**
	 * 获取融资机构
	 * @return
	 */
	public List<Object> findOrg()  {
		return Dao.selectList(xmlPath+"findOrg");
	} 
	
	/**
	 * 获取抵押资产详细
	 * @param m
	 * @return
	 * @
	 */
	public List<Object> findMorData(Map<String,Object> m)  {
		return Dao.selectList(xmlPath+"findMorData", m);
	}
	
	/**
	 * 获取质押资产详细
	 * @param m
	 * @return
	 * @
	 */
	public List<Object> findPledgeData(Map<String,Object> m)  {
		return Dao.selectList(xmlPath+"findPledgeData", m);
	}
	
	/**
	 * 当备选资产中有处于质押状态且距离抵押到期日有10天的支付表
	 * @param m
	 * @return
	 * @
	 */
	public List<Object> findPledgeContract(Map<String,Object> m)  {
		return Dao.selectList(xmlPath+"findPledgeContract", m);
	}
	
	/**
	 * 当备选资产中有处于抵押状态且距离抵押到期日有10天的支付表
	 * @param m
	 * @return
	 * @
	 */
	public List<Object> findMorContract(Map<String,Object> m)  {
		return Dao.selectList(xmlPath+"findMorContract", m);
	}

	
	/**
	 * 获取质押资产信息详细
	 * @param m
	 * @return
	 * @
	 *//*
	public List<Object> findPledgeDate(Map<String,Object> m)  {
		return Dao.selectList(xmlPath+"findMorData", m);
	}
	*/

	/**
	 * 获取单个的融资机构名称， 用于抵押合同的添加
	 */
	public Object findMorOne(Map<String,Object> map)  {
		return Dao.selectOne(xmlPath+"findOrgOne", map);
	}
	
	/**
	 * 获取抵押租赁物的总价值
	 * findLeaseCost
	 * @date 2013-10-21 下午05:07:55
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object findLeaseCost(Map<String,Object> map)  {
		return Dao.selectOne(xmlPath+"findLeaseCost",map);
	}
	
	/**
	 * 添加抵押合同
	 * doInertMor
	 * @date 2013-10-21 下午05:07:46
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInertMor(Map<String,Object> m)  {		
		int i = Dao.insert(xmlPath+"doInertMor", m);
		return i;
	}
	

	/**
	 * 添加抵押合同的日志文件
	 * doMorLog
	 * @date 2013-10-21 下午05:07:40
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doMorLog(Map<String,Object> m)  {
		int i = 0;
		List<Object> l = Dao.selectList(xmlPath+"getPayid01",m);
		if(l.size()>0) {
			for(Object obj : l) {
				Map<String,Object> map = (Map<String, Object>) obj;				
				m.put("TRPID", map.put("PAY_ID", map.get("PAY_ID")));
				m.put("PAYLIST_CODE", map.put("PAYLIST_CODE", map.get("PAYLIST_CODE")));
				i = Dao.insert(xmlPath+"doInsertLog", m);
			}
		}		
		return i;
	}
	
	/**
	 * 添加抵押物件
	 * doInsertMorEqu
	 * @date 2013-10-21 下午05:07:31
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertMorEqu(Map<String,Object> m)  {
		int i = 0;
		List<Object> l = Dao.selectList(xmlPath+"getPayid",m);
		if(l.size()>0) {
			for(Object obj : l) {
				Map<String,Object> map = (Map<String, Object>) obj;				
				m.put("TREID", map.put("TREID", map.get("TREID")));
				System.out.println("------TREID--------"+map.get("TREID"));
				System.out.println("-----------m011--------"+m);
				i = Dao.insert(xmlPath+"doInsertMorEqu", m);
			}
		}
			
		return i;
	}
	
	/**
	 * 修改支付表状态
	 * dochangePay
	 * @date 2013-10-21 下午05:07:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int dochangePay(Map<String,Object> m)  {
		int i = 0;
		i = Dao.update(xmlPath+"updatePayStatus", m);
		return i;
	}
	
	/**
	 * 获取登记机构名称
	 * getRegister
	 * @date 2013-10-21 下午05:07:18
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getRegister(Map<String, Object> map)  {	
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"getRegister", map);	
		return list;
	}
	
	/**
	 * 抵押/质押合同项下抵押余额、剩余期限查询及解押
	 * findMorInfo
	 * @date 2013-10-21 下午05:07:13
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page findMorInfo(Map<String,Object> map)  {
		
		Page page = new Page(map);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"findMorInfo", map);
		JSONArray aJSON = new JSONArray();
		for(int i=0; i<list.size();i++){
			JSONObject obj = new JSONObject();
			Map<String,Object> m = list.get(i);
			obj.put("MORTGAGE_NO", m.get("MORTGAGE_NO"));
			obj.put("MORTGAGE_REGSTER_NO", m.get("MORTGAGE_REGSTER_NO"));
			obj.put("MORTGAGE_PLEDGER", m.get("MORTGAGE_PLEDGER"));
			obj.put("MORTGAGE_DATE", m.get("MORTGAGE_DATE"));
			obj.put("MORTGAGE_START_TIME", m.get("MORTGAGE_START_TIME"));
			obj.put("MORTGAGE_END_TIME", m.get("MORTGAGE_END_TIME"));
			obj.put("MORTGAGE_OVER_TIME", m.get("MORTGAGE_OVER_TIME"));
			obj.put("MORTGAGE_TOTLE", m.get("MORTGAGE_TOTLE"));
			obj.put("MORTGAGE_MONEY", m.get("MORTGAGE_MONEY"));
			obj.put("FLAGE", m.get("FLAGE"));
			obj.put("MLFLAGE", m.get("MLFLAGE"));
			obj.put("REMOVE_DATE", m.get("REMOVE_DATE"));
			obj.put("REMOVE_TIME", m.get("REMOVE_TIME"));
			obj.put("FMPID", m.get("FMPID"));
			obj.put("REMARK", m.get("REMARK"));
			obj.put("warm", m.get("REMOVE_DATE"));
			obj.put("operate", m.get("FMPID"));
			aJSON.add(obj);
		}

		page.addDate(aJSON, Dao.selectInt(xmlPath+"findMorInfoNo", map));
		return page;
	}
	
	/**
	 * 查看资产抵押状态
	 * selectFiMorStatus
	 * @date 2013-10-21 下午05:07:06
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object selectFiMorStatus(Map<String,Object> m)  {
		return Dao.selectList(xmlPath+"selectFiMorStatus", m);
	}
	
	/**
	 * 修改资产抵押状态， 即解押
	 * doRemoveMor
	 * @date 2013-10-21 下午05:06:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doRemoveMor(Map<String,Object> m)  {
		return Dao.update(xmlPath+"doRemoveMor", m);
	}
	
	/**
	 * 修改日志文件及解押日期
	 * doRemoveMorC
	 * @date 2013-10-21 下午05:06:48
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doRemoveMorC(Map<String,Object> m)  {
		Dao.update(xmlPath+"doRemoveMorC", m);//添加解押日期
		return Dao.update(xmlPath+"doRemoveMorL", m);//修改日志文件状态
	}
	
	/**
	 * 抵押/质押合同查询
	 * doSearchInforCon
	 * @date 2013-10-21 下午05:06:41
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object doSearchInforCon(Map<String, Object> m)  {
		return Dao.selectOne(xmlPath+"getMorDateContract",m);
	}
	
	/**
	 * 抵押/质押所属设备查询
	 * doSearchInforEqu
	 * @date 2013-10-21 下午05:06:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object doSearchInforEqu(Map<String, Object> m)  {
		return Dao.selectList(xmlPath+"getMorDateEqu",m);
	}

	/**
	 * 添加登记证书
	 * doRegister
	 * @date 2013-10-21 下午05:06:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doRegister(Map<String,Object> m)  {
		return Dao.update(xmlPath+"doRegister", m);
	}
	
	/**
	 * 添加融资合同
	 * updateMorContract
	 * @date 2013-10-21 下午05:06:23
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateMorContract(Map<String,Object> m)  {
	    return Dao.update(xmlPath+"updateMorContract", m);
	}
	
	/**
	 * 获取项目名称
	 * getProjectName
	 * @date 2013-10-21 下午05:06:16
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getProjectName(Map<String,Object> map)  {		
		return Dao.selectList(xmlPath+"getProjectName", map);
	}
	
	/**
	 * 导出Excel 基于查询
	 * excelMor
	 * @date 2013-10-21 下午05:05:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Excel excelMor(Map<String,Object> params)  {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(xmlPath+"getExcelMor",params);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>(); 
		title.put("COMPANY_NAME", "厂家");
		title.put("ENGINE_TYPE", "发动机编号");
		title.put("GPS_URL", "GPS地址");
		title.put("GPS_NUMBER", "GPS用户");
		title.put("GPS_PWD", "GPS密码");
		
		title.put("UNIT_PRICE", "价格");
		title.put("AFFIRM_INSU_DATE", "投保日期");
		title.put("MORTGAGE_NO", "抵押合同编号");
		title.put("MORTGAGE_START_TIME", "抵押开始日期");
		title.put("MORTGAGE_END_TIME", "抵押结束日期");
		
		title.put("MORTGAGE_PLEDGEE", "抵押权人");
		title.put("MORTGAGE_OVER_TIME", "解押日期");
		title.put("END_DATE", "回购日期");
		title.put("HANDOVERDATE", "转移所有权日期");
		title.put("PRO_CODE", "项目编号");
		//封装excle
		Excel excel = new Excel();
		excel.setName("融资抵押"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
		return excel;
	}
}

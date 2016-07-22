package com.pqsoft.customers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CompanyManagerService {
	private final String xmlPath = "company.";	
	

	 /**
	  * 
	 * @Title: findCustomers
	 * @autor wuguowei_jing@163.com 2014-3-25 下午5:58:23
	 * @Description: TODO
	 * @param m
	 * @return    
	 * Page    
	 * @throws
	  */
	public Page findCompany(Map<String,Object> m){
		return PageUtil.getPage(m, xmlPath+"findCompany", xmlPath+"findCompanyCount");
	}
	public List<Map<String,Object>> getZgsData(){
		return Dao.selectList(xmlPath+"getZgsData");
	}
	/**
	 * 
	* @Title: getTypeData
	* @autor wuguowei_jing@163.com 2014-3-26 上午8:47:10
	* @Description: TODO 获取字典值
	* @param Flag
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getTypeData(String FLAG){
		
		return Dao.selectList(xmlPath+"getTypeData",FLAG);
	}
	/**
	 * 
	* @Title: getCsData
	* @autor wuguowei_jing@163.com 2014-3-26 上午11:48:47
	* @Description: TODO 厂商信息
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getCsData(){
		return Dao.selectList(xmlPath+"getCsData");
	}
	/**
	 * 
	* @Title: getJxsData
	* @autor wuguowei_jing@163.com 2014-3-26 上午11:51:30
	* @Description: TODO 经销商信息
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getJxsData(Map<String,Object> m){
		//System.out.println(m.isEmpty()+m.get("MANUFACTURESID").toString());
		//System.out.println((m.get("MANUFACTURESID").toString()).substring(0, (m.get("MANUFACTURESID").toString()).length()-1));
		m.put("MANUFACTURESID", null!=m.get("MANUFACTURESID") ?((m.get("MANUFACTURESID").toString()).substring(0, (m.get("MANUFACTURESID").toString()).length()-1)):"");
		return Dao.selectList(xmlPath+"getJxsData",m);
	}
	/**
	 * 
	* @Title: getCompanyData
	* @autor wuguowei_jing@163.com 2014-3-27 下午1:25:14
	* @Description: TODO 获取一条公司信息
	* @param m
	* @return    
	* Map<String,Object>    
	* @throws
	 */
	public Map<String,Object>  getCompanyData(Map<String,Object> m){
		return Dao.selectOne(xmlPath+"getCompanyData", m);
	}
	
	public List queryFHFABANK(Map m){
		return Dao.selectList(xmlPath+"queryFHFABANK", m);
	}
	/**
	 * 
	* @Title: addCompany
	* @autor wuguowei_jing@163.com 2014-3-26 下午4:09:16
	* @Description: TODO添加公司
	* @param m
	* @return    
	* int    
	* @throws
	 */
	public int addCompany(Map<String,Object> m){
		
		return Dao.insert(xmlPath+"addCompany", m);
	};
	/**
	 * 
	* @Title: addCompanyAffilidated
	* @autor wuguowei_jing@163.com 2014-3-26 下午4:48:17
	* @Description: TODO  添加行业、业务分类、区域、供应商、厂商
	* @param list    
	* void    
	* @throws
	 */
	public void addCompanyAffilidated(List list,String ID){
		for(Object o:list){
			    Map m = (Map)o;
				m.put("MANAGER_ID", ID);
				Dao.insert(xmlPath+"addCompanyAffilidated", m);
			
		}
	}	
	/**
	 * 
	* @Title: getCompanyOtherData
	* @autor wuguowei_jing@163.com 2014-3-27 下午1:45:04
	* @Description: TODO 查询行业、业务类型、厂商、供应商
	* @param m
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getCompanyOtherData(Map<String,Object> m,String type){
		m.put("TYPE", type);
		return Dao.selectList(xmlPath+"getCompanyOtherData", m);
	}
	/**
	 * 
	* @Title: getCompanyOtherDataHJ
	* @autor wuguowei_jing@163.com 2014-3-27 下午3:48:14
	* @Description: TODO
	* @param m
	* @param type
	* @param typedic
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getCompanyOtherDataHJ(Map<String,Object> m,String type,String typedic){
		m.put("TYPE", type);
		m.put("TYPEDIC", typedic);
		return Dao.selectList(xmlPath+"getCompanyOtherDataHJ", m);
	}
	
	public List<Map<String,Object>> getCompanyHY(Map<String,Object> m){
		m.put("TYPE", "行业类型");
		return Dao.selectList(xmlPath+"getCompanyHY", m);
	}
	public List<Map<String,Object>> getCsDataHJ(Map<String,Object> m,String type){
		m.put("TYPE", type);
		return Dao.selectList(xmlPath+"getCsDataHJ", m);
	}
	public List<Map<String,Object>> getJxsDataHJ(Map<String,Object> m,String type){
		m.put("TYPE", type);
		return Dao.selectList(xmlPath+"getJxsDataHJ", m);
	}
	
	public Map<String,Object> AssembleData(List<Map<String,Object>> hy,List<Map<String,Object>> yw,
			                                List<Map<String,Object>> cs,List<Map<String,Object>> jxs){
		Map<String,Object> m = new HashMap<String,Object>();
		String hyName = "";
		String ywName = "";
		String csName = ""; 
		String csId = "";
		String jxsName = ""; 
		for(Map<String,Object> mh:hy){
			if(null != mh.get("TYPE_ID")){
				hyName = mh.get("FLAG").toString()+","+hyName;
			}
		}
		System.out.println(hyName);
		for(Map<String,Object> mh:yw){
			if(null != mh.get("TYPE_ID")){
				ywName = mh.get("FLAG").toString()+","+ywName;
			}
		}
		System.out.println(ywName);
		for(Map<String,Object> mh:cs){
			if(null != mh.get("TYPE_ID")){
				csName = mh.get("COMPANY_NAME").toString()+","+csName;
				csId = mh.get("COMPANY_ID").toString()+","+csId;
			}
		}
		System.out.println(csName);
		System.out.println(csId);
		for(Map<String,Object> mh:jxs){
			if(null != mh.get("TYPE_ID")){
				jxsName = mh.get("SUP_NAME").toString()+","+jxsName;
			}
		}
		m.put("FA_NAMEINSTRUTRY", hyName);
		m.put("FA_NAMEBUSINESS", ywName);
		m.put("FA_NAMEMANUFACTURERS", csName);
		m.put("MANUFACTURESID", csId);
		m.put("FA_NAMEAGENT", jxsName);
		
		return m;
	}
	/**
	 * 
	* @Title: updateCompany
	* @autor wuguowei_jing@163.com 2014-3-27 下午5:27:45
	* @Description: TODO 更新公司信息
	* @param m
	* @return    
	* int    
	* @throws
	 */
	public int updateCompany(Map m){
		//更新联合租赁公司
		Dao.update(xmlPath+"updateFLComp",m);
		return Dao.insert(xmlPath+"updateCompany", m);
	}
	/**
	 * 
	* @Title: deleteAffilidaed
	* @autor wuguowei_jing@163.com 2014-3-27 下午5:43:08
	* @Description: TODO 删除行业、业务类型、厂商、供应商
	* @param m
	* @param type
	* @return    
	* int    
	* @throws
	 */
	public int deleteAffilidaed(Map m,String type){
		m.put("TYPE", type);
		return Dao.delete(xmlPath+"deleteAffilidaed", m);
	}
	/**
	 * 
	* @Title: deleteArea
	* @autor wuguowei_jing@163.com 2014-3-30 下午10:24:53
	* @Description: TODO 区域删除和添加
	* @param m
	* @return    
	* int    
	* @throws
	 */
	public int deleteArea(Map m){
		return Dao.delete(xmlPath+"deleteArea", m);
	}
	public int addArea(Map m){
		List<Map>  addList = (List)m.get("FA_NAMEADDRESS");
		String ID = m.get("ID").toString();
		for(Map map:addList){
			System.out.println("----------------------"+map);
			if(map!=null && !map.isEmpty()){
				map.put("ID", ID);
				String allName = map.get("ADDAREANAME").toString();
				String allId = map.get("ADDAREAID").toString();
				String[] name = allName.split(",");
				String[] id = allId.split(",");
				String COUNTRY = id[0];
				String COUNTRY_NAME = name[0];
				if(id.length>1)
				{
					String LARGEAREA = id[1];
					String LARGEAREA_NAME = name[1];
					map.put("LARGEAREA",LARGEAREA );
					map.put("LARGEAREA_NAME",LARGEAREA_NAME );
					System.out.println(LARGEAREA);
					System.out.println(LARGEAREA_NAME);
				}
				if(id.length>2)
				{
					String PROVINCE = id[2];
					String PROVINCE_NAME = name[2];
					map.put("PROVINCE", PROVINCE);
					map.put("PROVINCE_NAME",PROVINCE_NAME );
					System.out.println(PROVINCE);
					System.out.println(PROVINCE_NAME);
				}
				map.put("COUNTRY",COUNTRY );
				map.put("COUNTRY_NAME",COUNTRY_NAME );
				System.out.println(COUNTRY);
				System.out.println(COUNTRY_NAME);
				Dao.insert(xmlPath+"addArea",map);
			}
			
		}
		return 1;
	}
	public List<Map<String,Object>> queryArea(Map m){
		return Dao.selectList(xmlPath+"queryArea", m);
	}
	
	public void updateBankInfo(List list,String ID){
		Dao.delete(xmlPath+"deleteBankInfoByMID", ID);
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			map.put("MANAGER_ID", ID);
			System.out.println("------------------map="+map);
			Dao.insert(xmlPath+"insertBankInfoByMID",map);
		}
	}
}

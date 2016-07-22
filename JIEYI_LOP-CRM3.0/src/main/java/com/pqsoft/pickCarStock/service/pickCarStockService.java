package com.pqsoft.pickCarStock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.buyCertificate.service.BuyCertificateService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateSiteUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class pickCarStockService {

	String xmlPath = "pickCarStock.";
	public String queryAll(Map<String,Object> param){
		return Dao.selectOne(xmlPath + "queryAll",param);
	}
	
	public List selectCar(Map map){
		return Dao.selectList(xmlPath+"selectCar",map);
	}
	
	public List selectCarEx(Map map){
		return Dao.selectList(xmlPath+"selectCarEx",map);
	}
	/**
	 * 车务请求交车
	 * @param map
	 * @return
	 */
	public Page getCertificatePage2(Map<String,Object> map){
		map.put("CUST_SIGN_NAME1", map.get("CUST_SIGN_NAME")); //xml用CUST_SIGN_NAME1来做判断，不然会与fcc.name别名冲突 Add By YangJ 2014-5-26 上午10:25:52 
		Page page=new Page(map) ;
		List<Object> ListS=null;
		//List<Object> listC=null;
		/**合格证按合同编号倒序，有合格证的在后**/
		List<Map<String,Object>> listNoCert= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listHaveCert= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listAll= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> Buymanagelist=Dao.selectList(xmlPath + "getCertificatePage2", map);
		
		for(Map<String,Object> m:Buymanagelist){
			String pay_id = m.get("PAY_ID").toString();
		    ListS = Dao.selectList(xmlPath + "getSCertificate",pay_id);
		    
		    //查找票据
		    //listC = Dao.selectList(PATH_BUYCERTIFICATE + "getCCertificate",pay_id);
		    //m.put("invoice", listC);
		    m.put("Certificate", ListS);
		    m.put("CertificateCount", ListS.size());
		    if(ListS.size()>0){
				 listHaveCert.add(m);
		    }else{
		    	 listNoCert.add(m);
		    }
		}
		listAll.addAll(listNoCert);
		listAll.addAll(listHaveCert);
		JSONArray array = JSONArray.fromObject(listAll);
		page.addDate(array, Dao.selectInt(xmlPath + "getCertificateCountPage2", map)) ;
		return  page;
	}
	
	//添加签收单 [通过合同号查询N个字段]
	public List<Object> getNField(Map<String,Object> map){
		
		return  Dao.selectList(xmlPath+"getNField",map);
		
	}
	
	//添加到产品设备表
	public int addProjectEquipment(Map map){
		return Dao.insert(xmlPath+"addProjectEquipment",map);
	}
	
	//是否存在支付表号
	public Object isExistsPayListCode(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"isExistsPayListCode",map);
	}

	//通过合同号查询项目编号
	public String getProjectCode(Map map){
		Object projectCodeObj = Dao.selectOne(xmlPath + "getProjectCode", map);
		if(projectCodeObj!=null){
			Map projectCodeMap = (Map)projectCodeObj;
			Object proCodeObj = projectCodeMap.get("PRO_CODE");
			if(proCodeObj!=null){
				return String.valueOf(proCodeObj);
			}
		}
		return null;
	}
}

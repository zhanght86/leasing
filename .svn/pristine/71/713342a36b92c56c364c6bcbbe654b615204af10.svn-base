package com.pqsoft.documentApp.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;
public class ApplyDossierService {
	
	private final String xmlPath = "dossierApp.";

	/**
	 * 权证接口-档案归档管理页面
	 * @author yx
	 * @date 2015-05-29
	 * @param map
	 * @return
	 */
	public Page toMgDossierApp(Map<String,Object> map){
		map.put("PLATFORM_TYPE1", "业务类型");
		map.put("PLATFORM_TYPE2", "行业类型-打分");
		map.put("tixing", "归档预警提醒");
		map.put("_STATUS", "放款状态");
		map.put("_TYPE", "租金计划状态");
		map.put("jieqing", "结清");
		map.put("_FILE", "归档状态");
		System.out.println("------yx---------"+map);
		return PageUtil.getPage(map, xmlPath+"toMgDossierApp", xmlPath+"toMgDossierAppCount");
	}
	
	/**
	 * 管理页面中-展开页查看-查看归档详细信息
	 * @author yx
	 * @date 2015-05-29
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> toShowDossierApp(Map<String,Object> map){
		//Map<String,Object> m=new HashMap<String,Object>();
		 System.out.println("map:"+map);
		map.put("YJ", "原件");
		map.put("FYJ", "复印件");
		map.put("FK", "放款状态");
		map.put("ZF", "租金计划状态");
		map.put("TYPE", "退回原因");
		//***************资料清单***************要求份数**********************************************
		//查看资料管理-条件类型
		List<Map<String, Object>> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
		System.out.println("factorTypes:"+factorTypes);
		//当前数据的条件
		List<Map<String,Object>> fList = new ArrayList<Map<String,Object>>();
		Map<String,Object> itemMap;
		for(int i=0;i<factorTypes.size();i++){
			itemMap = factorTypes.get(i);
			String sql = itemMap.get("REMARK") == null ? "" : itemMap.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", map.get("PROJECT_ID").toString());
			
			List<Map<String, Object>> fields = null;
			if(!" ".equals(sql)){
				try {
					fields = Dao.execSelectSql(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1)
				field = fields.get(0);
			else
				continue;
			if (field == null)
				continue;
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TYPE", itemMap.get("CODE"));
			paramMap.put("CODE", field.get("CODE"));
			System.out.println("type===="+itemMap.get("CODE"));
			System.out.println("code===="+field.get("CODE"));
			fList.add(paramMap);
		}
		int i = 0;
		String mm_id = "";
		List<Map<String,Object>> mbList = Dao.selectList("dossierApp.getMM_ID");
		
		for(Map<String,Object> mbMap : mbList){
			List<Map<String,Object>> itemList = Dao.selectList("dossierApp.getFACTOR",mbMap);
			for(Map<String,Object> m1 : itemList){
				for(Map<String,Object> m2:fList){
					if(m1.get("TYPE").equals(m2.get("TYPE"))&&m1.get("CODE").equals(m2.get("CODE"))){
						i++;
						if(i==fList.size()){
							mm_id=m1.get("MM_ID")+"";
							break;
						}
					}
					
				}
			}
			
		}
		map.put("QZZL","权证类别");
		map.put("QZZB","权证资料");
		map.put("MM_ID", mm_id);
		//***************资料清单***************************************************************
		
		List<Map<String,Object>> listMap=Dao.selectList(xmlPath+"toShowDossierApp", map);

		return listMap;
	
		
	}
	
	/**
	 * 根据项目id查看项目参数	 *     
	 *     查看参数: 业务类型, 事业部门, 客户类型等信息. 
	 * @author yx
	 * @date 2015-06-02
	 * @param map 
	 * @return
	 */
	public Object toGetProjectParameter(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toGetProjectParameter", map);
	}
	
	/**
	 * 根据项目ID查看合同放款状态
	 * @author yx
	 * @date 2015-06-03
	 * @param map
	 * @return
	 */
	public Object toGetContractParameter(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toGetContractParameter", map);
	}
	
	/**
	 * 查询资料条件类型
	 * @author yx
	 * @date 2015-06-03
	 * @param map
	 * @return
	 */
	public Object toGetCondition(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toGetCondition",map);
	}
	
	//查看支付表设备数量
	public int toGetEquCount(Map<String,Object> map){
		return Dao.selectInt(xmlPath+"getEquCount", map);
	}
	

	
	/**
	 * 获取合同归档资料清单(废弃方法)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object toGetDocumentations(Map<String,Object> map){
//		String proId = map.get("PROJECT_ID").toString();
		List<String> factorFlags = new ArrayList<String>();
		//查看项目参数信息
		Map<String,Object> project = (Map<String, Object>) this.toGetProjectParameter(map);
		//合同放款状态
		Map<String,Object> contract = (Map<String, Object>) this.toGetContractParameter(map);
		//查看资料管理-条件类型
		List<Map<String, Object>> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
		System.out.println("factorTypes:"+factorTypes);
		for (Map<String, Object> factorType : factorTypes) {
			// 执行sql，拿到条件
			String sql = factorType.get("REMARK") == null ? "" : factorType.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", map.get("PROJECT_ID").toString());
			
			List<Map<String, Object>> fields = null;
			if(!" ".equals(sql)){
				try {
					fields = Dao.execSelectSql(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1)
				field = fields.get(0);
			else
				continue;
			if (field == null)
				continue;
			List<Map<String, Object>> factors = null;

			if (factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {//
				factors = new SysDictionaryMemcached().get(factorType.get(
						"CODE").toString());
			} else {
				factors = new DataDictionaryMemcached().get(factorType.get(
						"CODE").toString());
			}
			
			if (factors != null) {
				for (Map<String, Object> factor : factors) {
					
					if (field.get("CODE") != null
							&& (field.get("CODE").toString()
									.equals(factor.get("CODE")) || field
									.get("CODE").toString()
									.equals(factor.get("SHORTNAME"))))
						factorFlags.add(factor.get("FLAG").toString());
				}
			}
		}

		System.out.println("======yx======"+factorFlags);
		map.put("QZLB","权证类别");
		map.put("QZZL","权证资料");
		map.put("FACTORS", factorFlags);
		System.out.println("======yx======"+factorFlags);
		return Dao.selectList(xmlPath+"toGetMaterial", map);//获取资料清单
	}
	
	
	/**
	 * 
	 * @param map
	 * @return 归档申请中的资料清单
	 * @author wanghl
	 * @datetime 2015年8月26日,下午1:37:34
	 */
	@SuppressWarnings("unchecked")
	public Object toGetDocumentations1(Map<String,Object> map){
//		String proId = map.get("PROJECT_ID").toString();
		//查看资料管理-条件类型
		List<Map<String, Object>> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
		System.out.println("factorTypes:"+factorTypes);
		//当前数据的条件
		List<Map<String,Object>> fList = new ArrayList<Map<String,Object>>();
		Map<String,Object> itemMap;
		for(int i=0;i<factorTypes.size();i++){
			itemMap = factorTypes.get(i);
			String sql = itemMap.get("REMARK") == null ? "" : itemMap.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", map.get("PROJECT_ID").toString());
			
			List<Map<String, Object>> fields = null;
			if(!" ".equals(sql)){
				try {
					fields = Dao.execSelectSql(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1)
				field = fields.get(0);
			else
				continue;
			if (field == null)
				continue;
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TYPE", itemMap.get("CODE"));
			paramMap.put("CODE", field.get("CODE"));
			System.out.println("code===="+field.get("CODE"));
			fList.add(paramMap);
		}
		System.out.println("fList===="+fList);
		String mm_id = "";
		//所有资料模版
		List<Map<String,Object>> mbList = Dao.selectList("dossierApp.getMM_ID");
		
		for(Map<String,Object> mbMap : mbList){
			int i = 0;
			List<Map<String,Object>> itemList = Dao.selectList("dossierApp.getFACTOR",mbMap);
			System.out.println("itemList===="+itemList);
			for(Map<String,Object> m1 : itemList){
				for(Map<String,Object> m2:fList){
					if(m1.get("TYPE").equals(m2.get("TYPE"))&&m1.get("CODE").equals(m2.get("CODE"))){
						i++;
						if(i==fList.size()){
							mm_id=m1.get("MM_ID")+"";
							break;
						}
					}
					
				}
			}
			
		}
		map.put("QZZL","权证类别");
		map.put("QZZB","权证资料");
		map.put("MM_ID", mm_id);
		map.put("MM_ID", "34");
		int num=Dao.selectInt(xmlPath+"toGetMaterialnum", map);
		int jum=Dao.selectInt(xmlPath+"toGetMaterialjum", map);
		map.put("num", num);//不是权证资料个数
		map.put("jum", jum);//权证资料个数
		return Dao.selectList(xmlPath+"getMaterial", map);//获取资料清单
	}
	
	/**
	 * 批量申请资料清单查询
     * @author yx
	 * @date 2015-06-10
	 * @param map
	 * @return
	 */
	public Page toGetApplyDocumentations(Map<String,Object> map){
//		String proId = map.get("PROJECT_ID").toString();
		List<String> factorFlags = new ArrayList<String>();
		//查看项目参数信息
		Map<String,Object> project = (Map<String, Object>) this.toGetProjectParameter(map);
		//合同放款状态
		Map<String,Object> contract = (Map<String, Object>) this.toGetContractParameter(map);
		//查看资料管理-条件类型
		List<Map<String, Object>> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
		for (Map<String, Object> factorType : factorTypes) {
			// 执行sql，拿到条件
			String sql = factorType.get("REMARK") == null ? "" : factorType
					.get("REMARK").toString();
			sql = sql.replace("${PROJECT_ID}", map.get("PROJECT_ID").toString());
			
			List<Map<String, Object>> fields = null;
			if(!" ".equals(sql)){
				try {
					fields = Dao.execSelectSql(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> field = null;
			if (fields != null && fields.size() >= 1)
				field = fields.get(0);
			else
				continue;
			if (field == null)
				continue;
			List<Map<String, Object>> factors = null;

			if (factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {
				factors = new SysDictionaryMemcached().get(factorType.get("CODE").toString());
			} else {
				factors = new DataDictionaryMemcached().get(factorType.get("CODE").toString());
			}
			
			if (factors != null) {
				for (Map<String, Object> factor : factors) {
					
					if (field.get("CODE") != null
							&& (field.get("CODE").toString()
									.equals(factor.get("CODE")) || field
									.get("CODE").toString()
									.equals(factor.get("FLAG"))))
						factorFlags.add(factor.get("FLAG").toString());
				}
			}
		}

		
		//查看合同下支付表信息
		List<Map<String,Object>> pay =  Dao.selectList(xmlPath+"toGetPaylistByLeaseCode", map);
		//List<Map<String,Object>> pay =  Dao.selectList(xmlPath+"toGetPaylist", map);		
		String payList = ";";
		if(pay.size()>0){
			for(int k=0;k<pay.size();k++){
				Map<String,Object> mPay = pay.get(k);
				payList += mPay.get("PAY_CODE")==null?"":mPay.get("PAY_CODE").toString()+";";
			}
		}
		
		//查看所处阶段类型:
//		map.put("PHASE",PHASE);
//		Map<String,Object> metaril = Dao.selectOne(xmlPath+"toGetCondition", map);
//		map.put("MM_ID",metaril.get("MM_ID").toString() );
//		map.put("CLIENT_TYPE",project.get("CLIENT_TYPE").toString());
		Page page = new Page(map);
		JSONArray json = new JSONArray();
		map.put("QZLB","权证类别");
		map.put("FACTORS", factorFlags);
		List<Map<String,Object>> material = Dao.selectList(xmlPath+"toGetMaterial", map);
		if(material.size()>0){
			for(int i=0; i<material.size(); i++){
				JSONObject obj = new JSONObject();
				Map<String,Object> ma = material.get(i);
				obj.put("payList", payList);
				obj.put("LEASE_CODE", contract.get("LEASE_CODE"));
				obj.putAll(ma);
				json.add(obj);
			}
		}
		page.addDate(json, Dao.selectInt(xmlPath+"toGetMaterialCount", map));
		return page;
	}
	
	/**
	 * 获取合同下支付表-根据项目ID查询
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> toGetPaylist(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toGetPaylist", map);
	} 
	
	/**
	 * 获取合同下支付表-根据合同编号查询
	 * @param map
	 * @return
	 */
	public Object toGetPaylistByLeaseCode(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toGetPaylistByLeaseCode", map);
	} 
	
	
	/**
	 * 归档申请保存
	 * @author yx
	 * @date 2015-06-01
	 * @param map
	 * @return
	 */
	public String doAddAppDossiors(Map<String,Object> map){	
		JSONObject json = JSONObject.fromObject(map.get("datas"));
	
		List<Map<String, Object>> list = json.getJSONArray("saveRecord");
		System.out.println("list :"+list );
		System.out.println("json :"+json);
		String ID = Dao.getSequence("SEQ_API_DOCUMENT_APPLY");
		map.put("USERCODE", Security.getUser().getCode());//用户名
		map.put("ID", ID);//档案归档申请单id
		map.put("SEND_TYPE", json.get("SEND_TYPE"));//邮递类型
		map.put("SEND_COMPANY", json.get("SEND_COMPANY"));//邮递公司
		map.put("SEND_NUM", json.get("SEND_NUM"));//邮递单号
		map.put("RECIPIENT", json.get("RECIPIENT"));//收件人
		map.put("SEND_PORSON", json.get("SEND_NUM"));//投递人
		
		System.out.println("--yx-"+map);
		
		int i = Dao.insert(xmlPath+"doInsertAppDossior", map);
		int t = 0;
		System.out.println("--yx-"+list.size());
		if (i>0){
			for(int k=0; k<list.size(); k++){
				Map<String,Object> detial = list.get(k);
				detial.put("DOSSIER_APPLY_ID", ID);
				detial.put("USERCODE", Security.getUser().getCode());
				System.out.println("===========yx------------"+detial);
				t = Dao.insert(xmlPath+"doInsertAppDossiorDetail", detial);//插入申请明细
			}
		}

		if(i>0 && t>0){
			return ID;
		}
		
		return "";
	}
	
	/**
	 * 查看档案申请单信息
	 * @author yx
	 * @date 2015-06-08
	 * @param map
	 *   
	 * @return
	 */
	public Map<String, Object> toGetDossiersApp(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toGetDossiersApp", map);
	}
	
	/**
	 * 获取承租人
	 * @param map
	 * @return
	 */
	public Object toGetDossierRent(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toGetDossierRent", map);
	}
	
	/**
	 * 查看档案归档详细清单
     * @author yx
	 * @date 2015-06-08
	 * @param map
	 * @return
	 */
	public Object toGetDossierDetial(Map<String,Object> map){
		return  Dao.selectList(xmlPath+"toGetDossierDetial", map);
	}
	
	/**
	 * 资料档案入柜
	 * @param map
	 * @return
	 */
	public int doUpdateApply(Map<String,Object> map){
		map.put("USERCODE", Security.getUser().getCode());
		return Dao.update(xmlPath+"doUpdateApply", map);
	}
	
	/**
	 * 资料档案入柜
	 * @param map
	 * @return
	 */
	public int doUpdateDossierFile(Map<String,Object> map){
		JSONObject json = JSONObject.fromObject(map.get("saveData"));
		List<Map<String, Object>> list = json.getJSONArray("saveData");
		int t = 0;
		for(int i=0;i<list.size();i++){
			Map<String,Object> m = list.get(i);
			m.put("USERCODE", Security.getUser().getCode());//用户编号
			m.put("CABINET_NUMBER", json.get("CABINET_NUMBER"));
			m.put("PORTFOLIO_NUMBER", json.get("PORTFOLIO_NUMBER"));
			t = Dao.update(xmlPath+"doUpdateDossierFile", m);
		}
		return t;
	}
	
	public int doUpdateReturnMaterial(Map<String,Object> map){
		JSONObject json = JSONObject.fromObject(map.get("param"));
		List<Map<String, Object>> list = json.getJSONArray("saveRecord");
		int t = 0;
		for(int i=0;i<list.size();i++){
			Map<String,Object> m = list.get(i);
			m.put("USERCODE", Security.getUser().getCode());//用户编号
		    m.put("IS_RETURN","1");		    
			t = Dao.update(xmlPath+"doUpdateDossierFile", m);
		}
		return t;
	}
	
	/**
	 * 管理页面中-改变档案申请状态
	 * @author zf
	 * @date 2015-06-1
	 * @param map
	 * @return
	 */
	public int toUpdateStatus(Map<String,Object> map){
		return Dao.update(xmlPath+"toUpdateStatus",map);
	}
	

	/**
	 * 管理页面中-改变档案申请状态-批量更改
	 * @author zf
	 * @date 2015-06-1
	 * @param map
	 * @return
	 */
	public int toUpdateStatus1(Map<String,Object> map){
		return Dao.update(xmlPath+"toUpdateStatus1",map);
	}

	public List<Map<String, Object>> toGetDossiersParm(Map<String, Object> param) {
		return Dao.selectList(xmlPath+"toGetDossiersParm", param);
	}
	
	public List<Map<String, Object>> toGetDossiersArealist(Map<String, Object> param){
		return Dao.selectList(xmlPath+"toGetDossiersArealist", param);
	}

	public List<Map<String, Object>> toGetDossiersNumber(
			Map<String, Object> param) {
		return Dao.selectList(xmlPath+"toGetDossiersNumber", param);
	}

	public List<Map<String, Object>> toGetDossierPaylist(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return Dao.selectList(xmlPath+"toGetDossierPaylist", param);
	}

	public List<Map<String, Object>> findAllDossierData(
			Map<String, Object> param) {
		param.put("QZZL", "权证资料");
		return Dao.selectList(xmlPath+"allDossierData", param);
	}
	/**
	 * 获取合同下支付表-根据项目合同号与姓名查询
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> lookPaycodeDossierlist(Map<String,Object> map){
		return Dao.selectList(xmlPath+"lookPaycodeDossierlist", map);
	}

	public List<Map<String,Object>> lookFactorList(Map<String, Object> map) {
		 map.put("YJ", "原件");
		 map.put("FYJ", "复印件");
		 map.put("FK", "放款状态");
		 map.put("ZF", "租金计划状态");
		return Dao.selectList(xmlPath+"lookFactorDossierlist", map);
	} 
	
	public Object updateFactorStatue(Map<String, Object> map) {
		return Dao.selectList(xmlPath+"updateDossierStatue", map);
	}
	
	
	public Map<String,Object> toShowFileStatue(Map<String, Object> map) {
		 map.put("YJ", "原件");
		 map.put("FYJ", "复印件");
		 map.put("FK", "放款状态");
		 map.put("ZF", "租金计划状态");
		 map.put("GDZT", "归档状态");
		return Dao.selectOne(xmlPath+"toShowFileStatue", map);
	}


	
}

package com.pqsoft.refinanceFHFA.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class RefinanceFHFAService {

	private final String xmlPath="fhfaManager.";
	
	/**
	 * 融资机构-管理页面数据
	 * toMgFhfaData
	 * @date 2013-10-11 下午07:35:09
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgFhfaData(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgFhfaData", xmlPath+"toMgFhfaCount");
//		Page page = new Page(map);
//		List<Map<String,Object>> fhfaList = Dao.selectList(xmlPath+"toMgFhfaData", map);
//		JSONArray array = new JSONArray();
//		for(int i=0; i<fhfaList.size(); i++){
//			JSONObject json = new JSONObject();
//			Map<String,Object> m=(Map<String,Object>)fhfaList.get(i);
//			json.put("FHFA_ID", m.get("FHFA_ID"));
//			json.put("FHFA_CREDIT", m.get("FHFA_ID"));//授信信息
//			json.put("REFINANCE_TYPE", m.get("FHFA_ID"));//融资方式
//			json.put("OPERATION", m.get("FHFA_ID"));//融资方式
//			json.put("ORGAN_NAME", m.get("ORGAN_NAME"));//融资机构名称
//			json.put("ORGAN_ADD", m.get("ORGAN_ADD"));//融资机构地址
//			json.put("LINKMAN", m.get("LINKMAN"));//联系人
//			json.put("LINKMAN_PHONE", m.get("LINKMAN_PHONE"));//联系人电话
//			json.put("FINANCIAL_METHOD", m.get("FINANCIAL_METHOD"));//融资方式
//			json.put("CREDIT_STATUS", m.get("CREDIT_STATUS"));//授信状态(0 未授信 1 已授信)
//			json.put("CREDIT_BIN_DEADLINE", m.get("CREDIT_BIN_DEADLINE"));//授信开始期限
//			json.put("CREDIT_END_DEADLINE", m.get("CREDIT_END_DEADLINE"));//授信结束日期
//			json.put("TOTAL_CREDIT", m.get("TOTAL_CREDIT"));//授信总额度
//			json.put("PRACTICAL_LIMIT", m.get("PRACTICAL_LIMIT"));//实际使用额度
//			json.put("BALANCE", m.get("BALANCE"));//余额
//			json.put("REPAYMENT_MAN", m.get("REPAYMENT_MAN"));//还款人
//			json.put("BAIL_DEPOSITOR", m.get("BAIL_DEPOSITOR"));//保证金存款人
//			json.put("PAYMENT_METHOD", m.get("PAYMENT_METHOD"));//支付方式
//			json.put("PAYMENT_TYPE", m.get("PAYMENT_TYPE"));//支付类型
//			array.add(json);
//		}		
//		page.addDate(array, Dao.selectInt(xmlPath+"toMgFhfaCount",map));//计数
//		return page;
	}
	
	public int doDeleteFHFA(Map<String, Object> map) {
		//删除融资机构、融资授信、融资条件
		Dao.delete(xmlPath+"doDeleteFHFATJ",map);
		Dao.delete(xmlPath+"doDeleteFHFASX",map);
		return Dao.delete(xmlPath+"doDeleteFHFAJG",map);
	}
	
	/**
	 * 添加融资机构
	 * addRefinance
	 * @date 2013-10-12 上午11:16:09
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addFHFA(Map<String, Object> map) {
		//获取融资机构主键序列
		String FHFA_ID = Dao.getSequence("SEQ_REFINANCE_FHFA");
		
		
		//页面参数， 融资机构基本信息和融资机构帐号信息
		Object data_ = map.get("data");
		Map<String,Object> data  = JSONObject.fromObject(data_);
		Map<String,Object> baseData = JSONObject.fromObject(data.get("baseData"));//融资机构基本信息
		List<Map<String,Object>> accountData = JSONArray.fromObject(data.get("accountBank"));//融资机构帐号信息
		
		baseData.put("CREATE_ID", Security.getUser().getId());
		baseData.put("ORG_ID",Security.getUser().getOrg().getId());
		baseData.put("FAFH_ID", FHFA_ID);
		baseData.put("USERCODE", map.get("USERCODE"));//用户编号
		//插入融资机构
		int i = Dao.insert(xmlPath+"addFHFA", baseData);
		
		if(i>0){//如果插入成功， 插入帐号信息
			if(accountData!=null){
				for(int j=0; j<accountData.size(); j++){
					Map<String,Object> account = accountData.get(j);
					account.put("FHFA_ID", FHFA_ID);
					
					i = Dao.insert(xmlPath+"addFHFABank", account);
				}
				
				if(i>0){
					return i;
				}else {
					return 0;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 查看融资机构
	 * toSearchFhfa
	 * @date 2013-10-12 下午03:21:16
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> toSearchFhfa(Map<String,Object> map){
		return (Map<String, Object>) Dao.selectOne(xmlPath+"toSearchFhfa",map);
	}
	
	/**
	 * 查看融资机构帐号
	 * toSearchFhfaAccount
	 * @date 2013-10-12 下午03:23:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toSearchFhfaAccount(Map<String,Object> map) {
		return Dao.selectList(xmlPath+"toSearchFhfaAccount", map);
	}
	
	/**
	 * 修改融资机构
	 * updateFHFA
	 * @date 2013-10-12 上午11:16:09
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateFHFA(Map<String, Object> map) {
		
		//页面参数， 融资机构基本信息和融资机构帐号信息
		Object data_ = map.get("data");
		Map<String,Object> data  = JSONObject.fromObject(data_);
		Map<String,Object> baseData = JSONObject.fromObject(data.get("baseData"));//融资机构基本信息
		List<Map<String,Object>> accountData = JSONArray.fromObject(data.get("accountBank"));//融资机构帐号信息
		
		baseData.put("USERCODE", map.get("USERCODE"));//用户编号
		System.out.println("-----yc------"+baseData);
		//修改融资机构a
		int i = Dao.update(xmlPath+"updateFhfa", baseData);
		
		if(i>0){//如果插入成功， 插入帐号信息
			if(accountData!=null){
				for(int j=0; j<accountData.size(); j++){
					Map<String,Object> account = accountData.get(j);
					
					System.out.println("==========accounta======="+account.get("BANK_ID"));
					
					if(account.get("BANK_ID")!=null){
						i = Dao.update(xmlPath+"updateFhfaBank", account);
					}else {
						account.put("FHFA_ID", baseData.get("FHFA_ID"));
						i = Dao.insert(xmlPath+"addFHFABank", account);
					}
					
				}
				
				if(i>0){
					return i;
				}else {
					return 0;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 作废融资机构银行账号
	 */
	public int upDataFhfaBankStatus(Map<String,Object> map){
		return Dao.update(xmlPath+"upDataFhfaBankStatus", map);
	}
}

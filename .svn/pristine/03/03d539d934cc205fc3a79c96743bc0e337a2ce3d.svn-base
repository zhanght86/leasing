package com.pqsoft.fi.payin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class FundComService {

	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "fi.fund.getPageList", "fi.fund.getPageCount");
	}
	
	public int add(Map<String, Object> map) {
		map.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
		map.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
		map.put("FUND_NOTDECO_STATE", "0");
		map.put("FUND_STATUS", "0");
		map.put("FUND_ISCONGEAL", "0");
		map.put("FUND_RED_STATUS", "0");
		map.put("FUND_PRANT_ID", "0");
		map.put("FUND_SY_MONEY", map.get("FUND_RECEIVE_MONEY"));
		map.put("FUND_IMPORT_PERSON", Security.getUser().getName());
		map.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
		
		//客户表中，客户名称相同
		List<Map<String,Object>> list = Dao.selectList("fi.fund.selectClientIds", map);
		System.out.println(list+"-------list----22--------");
		int CLIENT_ID = 0;
		int flag = 0;
		if(list.size()!=0){
			//客户id存在，匹配银行账户
			for(int i=0;i<list.size();i++){
				Map<String,Object> map_tem = list.get(i);
				List<Map<String,Object>> list2 = Dao.selectList("fi.fund.selectFundComeCode", map_tem);
				System.out.println(list2+"--------list2-------------");
				for(int j=0;j<list2.size();j++){
					Map<String,Object> map_tem2 =list2.get(j);
					if(map.get("FUND_COMECODE").equals(map_tem2.get("BANK_ACCOUNT"))){
						CLIENT_ID = Dao.selectInt("fi.fund.selectClientId", map_tem2);
						break;
					}
				}
			}
			if(CLIENT_ID != 0){
				map.put("FUND_CLIENT_NAME", map.get("FUND_COMENAME"));
				map.put("FUND_CLIENT_ID", CLIENT_ID);
				map.put("FUND_PIDENTIFY_PERSON", Security.getUser().getName());
				map.put("FUND_PIDENTIFY_PERSON_ID", Security.getUser().getId());
				flag = Dao.insert("fi.fund.add", map);
				
			}else{
				flag = Dao.insert("fi.fund.add", map);
			}
		}else{
			flag = Dao.insert("fi.fund.add", map);
		}
		if(flag == 1){
			return 1;
		}else{
			return 0;
		}
		
	}

	public Object getData(Map<String, Object> param) {
		return Dao.selectOne("fi.fund.getFundAll", param);
	}

	public Object getFundDetail(Map<String, Object> param) {
		return Dao.selectList("fi.fund.getFundDetail", param);
	}

	public Object getDfj(Map<String, Object> param) {
		return Dao.selectOne("fi.fund.getDfj", param);
	}

	public void del(Map<String, Object> param) {
		Dao.delete("fi.fund.delFund", param);
	}
	
	public String getCompany(String USERID){
		Map<String,Object> map=Dao.selectOne("fi.fund.getCompanyCopy", USERID);
		if(map !=null && map.get("NAME")!=null && !map.get("NAME").equals("")){
			return map.get("NAME").toString();
		}
		return "";
	}
	
	public List<Map<String,Object>> getBank(){
		return Dao.selectList("fi.fund.getBank");
	}
	
	
	public Page fundBankPage(Map<String,Object> param) {
		if(param == null){
			param=new HashMap();
		}
		param.put("PLATFORMID", Security.getUser().getOrg().getPlatformId());
		Map<String,Object> map=Dao.selectOne("fi.fund.getPTInfo", param);
		param.put("ID", map.get("ID"));
		param.put("FA_BANK_TYPE", "0");//账号类型 0：收款账号 1：付款账号  
		return PageUtil.getPage(param, "fi.fund.fundBankPageList", "fi.fund.fundBankPageCount");
	}
	
	public Map<String,Object> queryUserInfoByProjectId(String PROJECT_ID){
		Map<String,Object> reMap=new HashMap<String,Object>();
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		Map<String,Object> custName =Dao.selectOne("fi.fund.queryCustNameByProjectId",param);
		Map<String,Object> bankAcc =Dao.selectOne("fi.fund.queryBankAccByProjectId",param);
		List<Map<String,Object>> firstPay =Dao.selectList("fi.fund.queryFirstPayallByProjectId",param);
		if(null!=custName){
			reMap.put("NAME", custName.get("NAME"));
		}
		if(null!=bankAcc){
			reMap.put("BANK_ACCOUNT", bankAcc.get("BANK_ACCOUNT"));
		}
		
		if(null!=firstPay&&firstPay.size()>0){
			reMap.put("FIRSTPAYALL", firstPay.get(0).get("FIRSTPAYALL"));
		}
		return reMap;
	}
	
	/**
	 * 根据orgid 查询 归属分公司
	 * @param orgId
	 * @return
	 */
	public Map<String, Object> getBranchCompanyInfo(String orgId){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("ID", orgId);
		Map<String, Object> resultMap= Dao.selectOne("fi.fund.getBranchCompanyInfo", param);
		if(null!=resultMap&&null!=resultMap.get("NAME")&&resultMap.get("NAME").toString().contains("分公司")){
			return resultMap;
		}else{
			if(null!=resultMap&&null!=resultMap.get("ID")&&!resultMap.get("ID").toString().equals("")){
				return getBranchCompanyInfo(resultMap.get("ID").toString());
			}else{
				return null;
			}
		}
	}
	
	/**
	 * 查询归属分公司银行账户信息
	 * @param orgId
	 * @return
	 */
	public Map<String, Object> getBranchCompanyBankAcc(String orgId){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("ID", orgId);
		return  Dao.selectOne("fi.fund.getBranchCompanyBankAcc", param);
	}
}

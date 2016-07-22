package com.pqsoft.fundEbank.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;

public class FundEbankService {

	/**
	 * 
	 * @param params
	 * @return
	 * @author:  吴剑东
	 * @date:    2013-9-24 上午10:44:14
	 */
	public Page getAccountsData(Map<String, Object> params) {
		Page page = new Page(params);//FundEbank
		//
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			params.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			params.put("COMPANY_ID",COM_MAP.get("COMPANY_ID"));
		}

		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("fund.Ebank.queryFundEbankFirstAppInfo",params));
		page.addDate(jsonArray, Dao.selectInt("fund.Ebank.queryFundEbankFirstAppCount",params));
		//page.addParam(params);
		return page;
	}
	
	
	
	
	/**
	 * 根据支付表code获取需要导出的首付款信息
	 * @param map
	 * @return
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午03:37:58
	 */
	public List<Map<String, Object>> getFirstPaymentByPaycodes(Map<String, Object> map){
		return Dao.selectList("fund.Ebank.queryFundEbankFirstAppInfo",map);
	}
	
	
	/**
	 * 查询
	 * @param params
	 * @return
	 * @author:  吴剑东
	 * @date:    2013-9-24 上午10:44:14
	 */
	public Page refreshSearch(Map<String, Object> params) {
		Page page = new Page(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("fund.Ebank.refreshSearchInfo",params));
		page.addDate(jsonArray, Dao.selectInt("fund.Ebank.refreshSearchInfoCount",params));
		//page.addParam(params);
		return page;
	}

	/**
	 * 修改项目主表首付款状态为未申请
	 * @param m sqlData:项目id(可多个)
	 * @author:  吴剑东
	 * @date:    2013-10-8 下午11:11:43
	 */
	public int updateStatusByProIds(Map<String, Object> m) {
		return Dao.update("fund.Ebank.updateStatusByProIds",m);
	}


	
	/**
	 * 维护资金主表、资金明细、到账明细  修改项目主表首付款状态为已申请
	 * @param m sqlData:项目id(可多个)
	 * @author:  吴剑东
	 * @date:    2013-10-8 下午15:11:43
	 */
	public Boolean saveFundEbankDetail(Map<String, Object> params) {
		
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) params.get("dataList");
		
		Map<String, Object> baseData = new HashMap<String, Object>();
		baseData.put("USERCODE", params.get("USERCODE"));
		baseData.put("USERNAME", params.get("USERNAME"));
		baseData.put("FI_FLAG", "0");//核销方式 首期款-网银
		baseData.put("FI_STATUS", "0");//核销状态 未核销
		FundNotEBankService notEbankService = new FundNotEBankService();
		
		for (int i = 0; i < dataList.size(); i++) {
			String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
			baseData.putAll((Map<String, Object>)dataList.get(i));
			baseData.put("HEAD_ID", HEAD_ID);//
			baseData.put("FI_PAY_MONEY", baseData.get("MONEY"));//付款金额
			baseData.put("FI_PROJECT_NUM", "1");//项目数量
			baseData.put("FI_PAY_BANK", baseData.get("BANK_NAME"));//应付付款银行
			baseData.put("FI_REALITY_MONEY", baseData.get("MONEY"));//实际付款金额
			baseData.put("FI_REALITY_BANK", baseData.get("BANK_NAME"));//实际付款银行
			baseData.put("FI_TO_THE_PAYEE", baseData.get("CUST_NAME"));//客户名称
			//新增资金扣划
			notEbankService.doInsertAppBaseData(baseData);
			
			//项目期初表数据刷入资金明细表
			baseData.put("PROJECT_HEAD_ID", baseData.get("ID"));
			baseData.put("PROJECT_ID", baseData.get("ID"));
			baseData.put("PRO_CODE", baseData.get("PROJ_ID"));
			baseData.put("FI_FLAG", "0");
			notEbankService.doInsertAppDetail(baseData,HEAD_ID,1);
		}
		return true;
	}



	/**
	 * 删除资金明细表数据
	 * @param m sqlData:项目id(可多个)
	 * @author:  吴剑东
	 * @date:    2013-10-8 下午15:11:43
	 */
	public void deleteFundDetailByProIds(Map<String, Object> m) {
		Dao.delete("fund.Ebank.deleteFundDetailByProIds", m);
	}
	/**
	 * 删除资金主表数据
	 * @param m sqlData:项目id(可多个)
	 * @author:  吴剑东
	 * @date:    2013-10-8 下午15:11:43
	 */
	public void deleteFundHeadByProIds(Map<String, Object> m) {
		Dao.delete("fund.Ebank.deleteFundHeadByProIds", m);
	}

	/**
	 * 核销首付款网银数据
	 * @param list 回执文件解析后数据
	 * @author:  吴剑东
	 * @date:    2013-10-8 下午15:11:43
	 */
	public int doFundEbankConfirmData(List<Map<String,Object>> list,Map<String,Object> prams){
		int succ_count = 0;
		for(int i=0;i<list.size();i++)
		{
			Map<String,Object> map=(Map<String,Object>)list.get(i);
			map.putAll(prams);
			// 核销状态
			if(map!=null && map.get("bank_flag")!=null && "1".equals(map.get("bank_flag").toString()))//成功
			{
				//----------网银核销不存在不足额现象  实付金额=应收金额 ------------------
				//1.修改项目主表的首期款状态 已核销:2
				map.put("FIRST_APP_STATE", "2");
				Dao.update("fund.Ebank.updateStatusByProCode",map);
				//2.修改期初表网银首期款数据状态已核销、实付金额
				Dao.update("fund.Ebank.updateBeginFirstByProCode",map);
				//3.修改资金明细表数据 实收日期、金额  
				Dao.update("fund.Ebank.updateFundDetailFirstByProCode",map);
				//4.修改资金主表数据 实收日期、金额、核销人
				Dao.update("fund.Ebank.updateFundHeadFirstByProCode",map);
				//5.插入数据到FI_FUND_ACCOUNT表 
				List listDatil=Dao.selectList("fund.Ebank.getFundFristInfoByProCode", map);
//				Dao.delete("fund.Ebank.delAccByDeHeadId",map);
				for(int h=0;h<listDatil.size();h++)
				{
					Map datMap=(Map)listDatil.get(h);
					datMap.put("USERCODE", prams.get("USERCODE"));
					datMap.put("FA_DETAIL_ID", datMap.get("DETAIL_ID").toString());
					Dao.insert("rentWrite.insertAccount", datMap);
					//保证金池
					new FundNotEBankService().doinsertPool(datMap);
				}
				//6.核销首付款后根据付款规则期末期初核销第一期租金
				doConfirmFirstRentByProId(map);
//				Dao.update("fund.Ebank.doUpdateAllProject",map);
				++succ_count;
			}else{
				//1.修改项目主表的首期款状态为核销失败:6
				map.put("FIRST_APP_STATE", "6");
				//删除资金主表数据
				Dao.delete("deleteFundHeadByProCode",map);
				//删除资金明细数据
				Dao.delete("deleteFundDetailByProCode",map);
				
				Dao.update("fund.Ebank.updateStatusByProCode",map);
				//2.修改期初表的失败次数和失败原因
				Dao.update("fund.Ebank.updateStatusByPaycodes",map);
			}
		}
		return succ_count;
	}
	
	public int updateStatusByPaycodes(Map<String, Object> m) {
		return Dao.update("fund.Ebank.updateStatusByPaycodes",m);
	}
	

	public int updateStatusByProId(Map<String, Object> m) {
		return Dao.update("fund.Ebank.updateStatusByProId",m);
	}

	
	/**
	 * 核销首付款后根据付款规则期末期初核销第一期租金
	 * @param m {deducted_id:项目编号}
	 * @author:  吴剑东
	 * @date:    2013-11-12 下午04:17:44
	 */
	public int doConfirmFirstRentByProId(Map<String, Object> m) {
		return Dao.update("fund.Ebank.doConfirmFirstRentByProId",m);
	}
	

}

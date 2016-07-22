package com.pqsoft.addTax.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.InvoiceUtil;

public class VatRentService {
	private String basePath = "VatInvoice.";
	/**
	 * 增值税租金开票申请数据 
	 * @param param
	 * @return
	 */
	public Page getRentApplyData(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "增值税发票");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getRentApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getRentApplyCount", param));
		return page ; 
	}
	
	public List<Object> getAllRentInvoiceMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		return Dao.selectList(basePath+"getAllRentInvoiceMess",param);
	}
	
	//租金开票自动申请（定时任务）
	public void appentRentInvoiceTime(){
		Map param=new HashMap();
		param.put("VAT_FLAG", "YES");
		param.put("INVOICE_TYPE", "增值税发票");
		List<Object> applyMess = this.getAllRentInvoiceMess(param);
		VatFirstPayService vatService = new VatFirstPayService();
		//插入发票详细信息
		List<String> s = new ArrayList<String>();
		for (Object object : applyMess) {
			s.clear();
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "租金");
			if(newParam.containsKey("ITEM_FLAG") && newParam.get("ITEM_FLAG") !=null && "违约金".equals(newParam.get("ITEM_FLAG").toString())){
				newParam.put("FUND_NAME", "违约金");
				s.add("违约金");
			}else{
				newParam.put("FUND_NAME", "租金");
				s.add("本金");
				s.add("利息");
			}
			
			String D_STATUS ="1,2";
			newParam.put("D_STATUS", D_STATUS);
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("ITEM_MODEL", newParam.containsKey("BEGINNING_NUM") && newParam.get("BEGINNING_NUM") !=null ?newParam.get("BEGINNING_NUM").toString()+"期":"");
			newParam.put("IF_INVOICE", "yes");
			vatService.addInvoice(newParam);
		}
	}

}

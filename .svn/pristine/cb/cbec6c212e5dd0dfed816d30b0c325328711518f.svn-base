package com.pqsoft.capitalInstall.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.addTax.service.VatFirstPayService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;

public class CapitalInstallService {
	private String basePath = "CapitalInstall.";
	
	public Page getCapitalInstallApply(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getCapitalInstallList",param));
		page.addDate(array, Dao.selectInt(basePath+"getCapitalInstallCount", param));
		return page;
	}
	
	public int addCapitalInstallMess(Map<String,Object> newParam){
		int result = 0;
		//融资租赁公司信息
		//查询该平台融资公司信息
		Map FAMap=new HashMap();
		FAMap.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		VatFirstPayService vatService = new VatFirstPayService();
		Map<String,Object> sflcMess = vatService.getSflcMess(FAMap);
		DecimalFormat df2  = new DecimalFormat("###.00"); 
		Double tax = sflcMess != null && sflcMess.containsKey("TAX") && sflcMess.get("TAX") != null ? Double.parseDouble(sflcMess.get("TAX").toString()):Double.parseDouble("0.17");
		//将申请数据插入发票信息表中
		String invoice_id = vatService.getInvoiceSeq();
		newParam.put("INVOICE_ID", invoice_id);
		newParam.put("TARGET_TYPE", newParam.containsKey("INVOICE_TARGET_TYPE") && newParam.get("INVOICE_TARGET_TYPE") !=null?newParam.get("INVOICE_TARGET_TYPE").toString():"");
		newParam.put("TARGET_ID", newParam.containsKey("INVOICE_TARGET_ID") && newParam.get("INVOICE_TARGET_ID")!=null?newParam.get("INVOICE_TARGET_ID").toString():"");
		newParam.put("INVOICE_AMT", newParam.containsKey("INVOICE_AMT") && newParam.get("INVOICE_AMT")!=null?newParam.get("INVOICE_AMT").toString():0);
		result = vatService.addInvoiceMainMess(newParam);
		int count = 0;
		if(newParam.containsKey("PROJECT_MODEL") && newParam.get("PROJECT_MODEL") != null && "2".equals(newParam.get("PROJECT_MODEL").toString())){
			//插入detail表数据
			Map<String,Object> rentDetail =  new HashMap<String, Object>();
			rentDetail.put("INVOICE_ID", invoice_id);
			rentDetail.put("ITEM_NAME", newParam.containsKey("FUND_NAME") && newParam.get("FUND_NAME") !=null ? newParam.get("FUND_NAME").toString():"");
			rentDetail.put("ITEM_NUM", "1");
			rentDetail.put("PROJECT_ID", newParam.get("PROJECT_ID").toString());
			rentDetail.put("PRO_CODE", newParam.get("PRO_CODE").toString());
	    	Double fact_amt = newParam.containsKey("INVOICE_AMT") && newParam.get("INVOICE_AMT") !=null ? Double.parseDouble(newParam.get("INVOICE_AMT").toString()):0;
	    	rentDetail.put("ITEM_FACT_AMT", fact_amt);
	    	rentDetail.put("ITEM_TAX_RATE", String.valueOf(tax)); 
	    	rentDetail.put("TARGET_TYPE", newParam.containsKey("INVOICE_TARGET_TYPE") && newParam.get("INVOICE_TARGET_TYPE") !=null ? newParam.get("INVOICE_TARGET_TYPE").toString():"");
	    	rentDetail.put("TARGET_ID", newParam.containsKey("INVOICE_TARGET_ID") && newParam.get("INVOICE_TARGET_ID") !=null ? newParam.get("INVOICE_TARGET_ID").toString():"");
	    	rentDetail.put("CREATOR", Security.getUser().getName());
	    	rentDetail.put("RENT_LIST","-1");
	    	rentDetail.put("PAYLIST_CODE", newParam.containsKey("PAYLIST_CODE") && newParam.get("PAYLIST_CODE") !=null ? newParam.get("PAYLIST_CODE").toString():"");
			count += vatService.addInvoiceDetailMess(rentDetail);
			
			//加入票据细项信息
			Map<String,Object> paramItem = new HashMap<String, Object>();
		    paramItem.put("INVOICE_ID", invoice_id);
		    paramItem.put("ITEM_NAME", newParam.containsKey("FUND_NAME") && newParam.get("FUND_NAME") !=null ? newParam.get("FUND_NAME").toString():"");
		    paramItem.put("ITEM_NUM", 1);
		    paramItem.put("ITEM_FACT_AMT", fact_amt);
			BigDecimal sub_amt = BigDecimal.valueOf(Double.valueOf(fact_amt)).divide(BigDecimal.valueOf(1+tax),10,BigDecimal.ROUND_HALF_UP);
		    paramItem.put("ITEM_SUB_AMT", df2.format(sub_amt));
		    BigDecimal tax_amt = sub_amt.multiply(BigDecimal.valueOf(tax));
			paramItem.put("ITEM_SUB_TAX_AMT", df2.format(tax_amt));
			paramItem.put("ITEM_MODEL", "");
			paramItem.put("ITEM_TAX_RATE", String.valueOf(tax));
			vatService.addInvoiceItem(paramItem);
			 
		}else{
            //插入detail表数据
			List<Object> rentDetail = getInvoiceRentDetail(newParam);
			for (Object obj2 : rentDetail) {
				Map<String,Object> detail = (Map<String,Object>)obj2;
				detail.put("INVOICE_ID", invoice_id);
				detail.put("ITEM_NAME", detail.containsKey("ITEM_NAME") && detail.get("ITEM_NAME") !=null ? detail.get("ITEM_NAME").toString():"");
				detail.put("ITEM_NUM", "1");
		    	Double fact_amt = detail.containsKey("TOTAL_MONEY") && detail.get("TOTAL_MONEY") !=null ? Double.parseDouble(detail.get("TOTAL_MONEY").toString()):0;
		    	detail.put("ITEM_FACT_AMT", df2.format(fact_amt));
		    	detail.put("ITEM_TAX_RATE", String.valueOf(tax));
		    	detail.put("TARGET_TYPE",  detail.containsKey("INVOICE_TARGET_TYPE") && detail.get("INVOICE_TARGET_TYPE") !=null ? detail.get("INVOICE_TARGET_TYPE").toString():""); 
		    	detail.put("TARGET_ID", detail.containsKey("INVOICE_TARGET_ID") && detail.get("INVOICE_TARGET_ID") !=null ? detail.get("INVOICE_TARGET_ID").toString():"");
		    	detail.put("CREATOR", Security.getUser().getName());
		    	detail.put("RENT_LIST", "-1");
		    	detail.put("PAYLIST_CODE", newParam.containsKey("PAYLIST_CODE") && newParam.get("PAYLIST_CODE") !=null ? newParam.get("PAYLIST_CODE").toString():"");
				count += vatService.addInvoiceDetailMess(detail);
				
				//加入票据细项信息
				Map<String,Object> paramItem = new HashMap<String, Object>();
			    paramItem.put("INVOICE_ID", invoice_id);
			    paramItem.put("ITEM_NAME", detail.containsKey("ITEM_NAME") && detail.get("ITEM_NAME") !=null ? detail.get("ITEM_NAME").toString():"");
			    paramItem.put("ITEM_NUM", 1);
			    paramItem.put("ITEM_FACT_AMT", df2.format(fact_amt));
				BigDecimal sub_amt = BigDecimal.valueOf(Double.valueOf(fact_amt)).divide(BigDecimal.valueOf(1+tax),10,BigDecimal.ROUND_HALF_UP);
			    paramItem.put("ITEM_SUB_AMT", df2.format(sub_amt));
			    BigDecimal item_unit_price = BigDecimal.valueOf(Double.valueOf(fact_amt)).divide(BigDecimal.valueOf(1+tax),10,BigDecimal.ROUND_HALF_UP);
			    BigDecimal tax_amt = item_unit_price.multiply(BigDecimal.valueOf(tax));
				paramItem.put("ITEM_SUB_TAX_AMT", df2.format(tax_amt));
				paramItem.put("ITEM_MODEL",  detail.containsKey("ITEM_MODEL") && detail.get("ITEM_MODEL") !=null ? detail.get("ITEM_MODEL").toString():"");
				paramItem.put("ITEM_TAX_RATE", String.valueOf(tax));
				BigDecimal.valueOf(Double.valueOf(fact_amt)).divide(BigDecimal.valueOf(1+tax),10,BigDecimal.ROUND_HALF_UP);
				paramItem.put("ITEM_UNIT_PRICE", String.valueOf(item_unit_price));
				paramItem.put("ITEM_UNIT", "台");
				vatService.addInvoiceItem(paramItem);
			}
		}
		return result ; 
	}

	public List<Object> getInvoiceDetails(Map<String,Object> param){
	    return Dao.selectList(basePath+"getInvoiceCapitalList", param);	
	}

	public List<Object> getInvoiceRentDetail(Map<String,Object> param ){
		return Dao.selectList(basePath+"getInvoiceRentDetail",param);
	}
	
	public List<Object> getAllCapitalIntall(Map<String,Object> param){
		return Dao.selectList(basePath+"getAllCapitalIntall",param);
	}
}

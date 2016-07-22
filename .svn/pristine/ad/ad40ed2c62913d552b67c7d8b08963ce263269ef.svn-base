package com.pqsoft.advanceBill.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.addTax.service.VatFirstPayService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.InvoiceUtil;
import com.pqsoft.util.StringUtils;

public class AdvanceBillService {
    private String basePath = "advanceBill.";
    
    public Page getAdvanceBillRentPage(Map<String,Object> param ){
	param.put("SQL_STR", InvoiceUtil.getJoinStr());
	param.put("INVOICE_TYPE", "增值税发票");
	Page page = new Page(param);
	JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getAdvanceRentList",param));
	page.addDate(array, Dao.selectInt(basePath+"getAdvanceRentCount", param));
	return page;
    }
    
    public Page getAdvanceBillFirstPage(Map<String,Object> param){
	param.put("SQL_STR", InvoiceUtil.getJoinStr());
	param.put("INVOICE_TYPE", "增值税发票");
	
	//
	param.put("COLUMNSITEM", Dao.selectOne(basePath+"tqkpColumnsMaxMoney"));
	param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"tqkpColumnsTotalMoney"));
	
	Page pageData = new Page(param);
	JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getFirstRentList",param));
	pageData.addDate(array, Dao.selectInt(basePath+"getFirstRentCount", param));
	return pageData;
    }
    
    public Page getAdvanceMergePage(Map<String,Object> param){
	param.put("SQL_STR", InvoiceUtil.getJoinStr());
	Page pageData = new Page(param);
	JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getAdvanceMergeList",param));
	pageData.addDate(array, Dao.selectInt(basePath+"getAdvanceMergeCount", param));
	return pageData;
    }
    
    public Page getHeXiaoPage(Map<String,Object> param){
    	param.put("INVOICE_TYPE", "增值税发票");
		Page pageData = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getHeXiaoList",param));
		pageData.addDate(array, Dao.selectInt(basePath+"getHeXiaoCount", param));
		return pageData;
    }
    
    public List<Object> getBillFirstInviceMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("COLUMNSITEM", Dao.selectOne(basePath+"tqkpColumnsMaxMoney"));
		param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"tqkpColumnsTotalMoney"));
		
		return Dao.selectList(basePath+"getBillFirstInviceMess", param);
	}
	
	 public List<Object> getAllRentBillMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		return Dao.selectList(basePath+"getAllRentBillMess", param);
	}
    
    public int advanceBillApply(Map<String,Object> newParam){
    	VatFirstPayService FIirstPayService=new VatFirstPayService();
    	int result = 0 ;
		//融资租赁公司信息
		//查询该平台融资公司信息
		Map FAMap=new HashMap();
		FAMap.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		Map<String,Object> sflcMess = FIirstPayService.getSflcMess(FAMap);
		DecimalFormat df2  = new DecimalFormat("###.00"); 
		Double tax = sflcMess != null && sflcMess.containsKey("TAX") && sflcMess.get("TAX") != null ? Double.parseDouble(sflcMess.get("TAX").toString()):Double.parseDouble("0.17");
		//将申请数据插入发票信息表中
		String invoice_id = FIirstPayService.getInvoiceSeq();
		newParam.put("INVOICE_ID", invoice_id);
		result = FIirstPayService.addInvoiceMainMess(newParam);
		int count = 0 ;
	    List<Object> invoiceDetails = getBillDetail(newParam);
	    Boolean flag = true ;
	    for (Object object2 : invoiceDetails) {
	    	Map<String,Object> param2 = (Map<String,Object>)object2;
	    	param2.put("INVOICE_ID", invoice_id);
	    	param2.put("ITEM_NAME", param2.containsKey("BEGINNING_NAME") && param2.get("BEGINNING_NAME") !=null ? param2.get("BEGINNING_NAME").toString():"");
	    	param2.put("ITEM_NUM", "1");
	    	Double fact_amt = param2.containsKey("BEGINNING_MONEY") && param2.get("BEGINNING_MONEY") !=null ? Double.parseDouble(param2.get("BEGINNING_MONEY").toString()):0;
	    	param2.put("ITEM_FACT_AMT", fact_amt);
	    	Double tax_Rat=param2.containsKey("TAX_RATE") && param2.get("TAX_RATE") !=null ? Double.parseDouble(param2.get("TAX_RATE").toString()):tax;
	    	param2.put("ITEM_TAX_RATE", String.valueOf(tax_Rat));
//	    	param2.put("TARGET_TYPE",  param2.containsKey("INVOICE_TARGET_TYPE") && param2.get("INVOICE_TARGET_TYPE") !=null ? param2.get("INVOICE_TARGET_TYPE").toString() :""); 
//	    	param2.put("TARGET_ID", param2.containsKey("INVOICE_TARGET_ID") && param2.get("INVOICE_TARGET_ID") !=null ? param2.get("INVOICE_TARGET_ID").toString() :""); 
	    	param2.put("CREATOR", Security.getUser().getName());
	    	param2.put("RENT_LIST", param2.containsKey("BEGINNING_NUM") && param2.get("BEGINNING_NUM") !=null ? param2.get("BEGINNING_NUM").toString():"");
	    	param2.put("PAYLIST_CODE", param2.containsKey("PAYLIST_CODE") && param2.get("PAYLIST_CODE") !=null ? param2.get("PAYLIST_CODE").toString():"");
	    	//通过开票协议查询出邮寄地址
	    	Map EMS_MAP=Dao.selectOne("BusTaxInvoice.queryEmsByPayCode", param2);
	    	if(EMS_MAP !=null && EMS_MAP.get("EMS_ID")!=null){
	    		param2.put("DL_EMS_ID",EMS_MAP.get("EMS_ID"));
	    	}
			count += FIirstPayService.addInvoiceDetailMess(param2);

			//加入票据细项信息（判断本金为负值时并做处理）
			Map<String,Object> paramItem = new HashMap<String, Object>();
            if("本金".equals(StringUtils.nullToString(param2.get("BEGINNING_NAME"))) && fact_amt < 0){
            	String new_fact = StringUtils.nullToString(newParam.get("INVOICE_AMT"));
            	paramItem.put("INVOICE_ID", invoice_id);
			    paramItem.put("ITEM_NAME",  "租金");
			    paramItem.put("ITEM_NUM", 1);
			    paramItem.put("ITEM_FACT_AMT", new_fact);
				Double new_amt = Double.parseDouble(new_fact)/(1+tax_Rat);
			    paramItem.put("ITEM_SUB_AMT", df2.format(new_amt));
			    Double tax_amt = new_amt*tax_Rat;
				paramItem.put("ITEM_SUB_TAX_AMT", df2.format(tax_amt));
				paramItem.put("ITEM_MODEL",  newParam.containsKey("ITEM_MODEL") && newParam.get("ITEM_MODEL") !=null ? newParam.get("ITEM_MODEL").toString():"");
				paramItem.put("ITEM_TAX_RATE", String.valueOf(tax_Rat));
				paramItem.put("BEGINNING_ID", param2.get("BEGINNING_ID"));
				FIirstPayService.addInvoiceItem(paramItem);
				flag = false;
				continue;
            }else if("利息".equals(StringUtils.nullToString(param2.get("BEGINNING_NAME"))) && fact_amt < 0){
            	String new_fact = StringUtils.nullToString(newParam.get("INVOICE_AMT"));
            	paramItem.put("INVOICE_ID", invoice_id);
			    paramItem.put("ITEM_NAME",  "租金");
			    paramItem.put("ITEM_NUM", 1);
			    paramItem.put("ITEM_FACT_AMT", new_fact);
				Double new_amt = Double.parseDouble(new_fact)/(1+tax_Rat);
			    paramItem.put("ITEM_SUB_AMT", df2.format(new_amt));
			    Double tax_amt = new_amt*tax_Rat;
				paramItem.put("ITEM_SUB_TAX_AMT", df2.format(tax_amt));
				paramItem.put("ITEM_MODEL",  newParam.containsKey("ITEM_MODEL") && newParam.get("ITEM_MODEL") !=null ? newParam.get("ITEM_MODEL").toString():"");
				paramItem.put("ITEM_TAX_RATE", String.valueOf(tax_Rat));
				paramItem.put("BEGINNING_ID", param2.get("BEGINNING_ID"));
				FIirstPayService.addInvoiceItem(paramItem);
				flag = false;
				continue;
            }else if(flag == false){
            	break ;
            }else{
			    paramItem.put("INVOICE_ID", invoice_id);
			    paramItem.put("ITEM_NAME", param2.containsKey("MERGE_NAME") && param2.get("MERGE_NAME") !=null ? param2.get("MERGE_NAME").toString():"");
			    paramItem.put("ITEM_NUM", 1);
			    paramItem.put("ITEM_FACT_AMT", fact_amt);
				Double sub_amt = fact_amt/(1+tax_Rat);
			    paramItem.put("ITEM_SUB_AMT", df2.format(sub_amt));
			    Double tax_amt = sub_amt*tax_Rat;
				paramItem.put("ITEM_SUB_TAX_AMT", df2.format(tax_amt));
				paramItem.put("ITEM_MODEL",  newParam.containsKey("ITEM_MODEL") && newParam.get("ITEM_MODEL") !=null ? newParam.get("ITEM_MODEL").toString():"");
				paramItem.put("ITEM_TAX_RATE", String.valueOf(tax_Rat));
				paramItem.put("BEGINNING_ID", param2.get("BEGINNING_ID"));
				FIirstPayService.addInvoiceItem(paramItem);
            }
			
		}
	    return result ;
    }
    
    
    public List<Object> getBillDetail(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		List<Object> list = Dao.selectList("BusTaxInvoice.getBillDetailMess", param);
		return list;
	}
    
    
}

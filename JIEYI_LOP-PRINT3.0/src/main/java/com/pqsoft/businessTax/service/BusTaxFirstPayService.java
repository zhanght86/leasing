package com.pqsoft.businessTax.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.InvoiceUtil;

public class BusTaxFirstPayService {
    private String basePath = "BusTaxInvoice.";
    /**
     * 营业税首期款申请数据查询
     * @param param
     * @return
     */
    public Page getBusFirstPayApplyData(Map<String,Object> param ){
    	param.put("SQL_STR", InvoiceUtil.getJoinStr());
    	Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getBusFirstPayApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getBusFirstPayApplyCount", param));
		return page ;
    }
    
    /**
     * 营业税首期款核销数据    
     * @param param
     * @return
     */
    public Page getBusTaxHexiaoData(Map<String,Object> param ){
    	param.put("INVOICE_TYPE", "营业税发票");
    	Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getBusHeXiaoList",param));
		page.addDate(array, Dao.selectInt(basePath+"getBusHeXiaoCount", param));
		return page;
    }
    
    public Excel getExcelBusTaxData(Map<String,Object> param,String type){
    	List<Map<String,Object>>  pageList = new ArrayList<Map<String,Object>>();
    	param.put("INVOICE_TYPE", "营业税发票");
		pageList = Dao.selectList(basePath+"getBusTaxExportData", param);
		for (Map<String, Object> map : pageList) {
			Dao.update(basePath+"updateExportMemo", map);
		}
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("REMARK", "备注");
		title.put("SUP_ALL_NAME", "供应商");
		title.put("FUND_NUM", "租金编号");
		title.put("CLIENT_NAME", "客户名称");
		title.put("ITEM_NAME1", "项目1名称");
		title.put("ITEM_FACT_AMT1", "项目1单价");
		title.put("ITEM_NUM1", "项目1数量");
		title.put("ITEM_NAME2", "项目2名称");
		title.put("ITEM_FACT_AMT2", "项目2单价");
		title.put("ITEM_NUM2", "项目2数量");
		title.put("ITEM_NAME3", "项目3名称");
		title.put("ITEM_FACT_AMT3", "项目3单价");
		title.put("ITEM_NUM3", "项目3数量");
		title.put("ITEM_NAME4", "项目4名称");
		title.put("ITEM_FACT_AMT4", "项目4单价");
		title.put("ITEM_NUM4", "项目4数量");
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		excel.hasRownum();
		excel.defaultFormat(false);
		return excel;
    }
    
    public int uploadReceiptBusTax(List<File> files) {
    	int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				Map<String,Object> m = new HashMap<String, Object>();
				
				if(row.size() < 19 || row.size() > 25 ) {
					continue;
				} 
				try{ 
					Float.parseFloat(row.get(0).toString());
				}catch(Exception e){
					continue; 
				}				
				
				String export_memo=row.get(1);					// 
				String dld_name=row.get(2);
				String rent_id = row.get(3);//租金SFAHFD120006_14
				String to_name=row.get(4);
				
				String invoiceDate = row.get(17);//发票日期
				String invoice_code = row.get(18);//发票号码
			
//				String name1= row.get(5);
				String amt1=row.get(6);
//				String name2= row.get(8);
				String amt2=row.get(9);
//				String name3= row.get(11);
				String amt3=row.get(12);
//				String name4= row.get(14);
				String amt4=row.get(15);
				Double sum_amt = Double.parseDouble(amt1)+Double.parseDouble(amt2)+Double.parseDouble(amt3)+Double.parseDouble(amt4);//piaoju zonge cld
				String pro_code = rent_id.split("_").length > 0 ? rent_id.split("_")[0] : rent_id;
//				String rent_list = rent_id.substring(rent_id.indexOf("_")+1);
				
				if(!"".equals(invoice_code)){
					
					m.put("EXPORT_MEMO", export_memo);
					m.put("PRO_CODE", pro_code);
					m.put("INVOICE_DLD", dld_name);
					m.put("INVOICE_TO_NAME", to_name);
					m.put("INVOICE_CODE", invoice_code);
					m.put("INVOICE_DATE", invoiceDate);
					m.put("CURR_ALL_INVOICE_AMT", sum_amt);
					m.put("SQL_USER_ID", Security.getUser().getName());
					count += Dao.update(basePath+"updateCheckByBillCode",m);
//					Dao.update(basePath+"updateMergeCheckByBillCode",m);
				}
			}
		}
		return count;
	}
    
    /**
     * 营业税票据查询
     * @param param
     * @return
     */
    public Page getBusTaxSearch(Map<String,Object> param){
    	Page page  = new Page(param);
    	JSONArray array  = JSONArray.fromObject(Dao.selectList(basePath+"getBusTaxSearchList", param));
    	page.addDate(array, Dao.selectInt(basePath+"getBusTaxSearchCount", param));
		return page;
    }
    
    /**
     * 营业税合并开票申请
     * @param param
     * @return
     */
    public Page getBusTaxMergerApplyData(Map<String,Object> param ){
    	param.put("SQL_STR", InvoiceUtil.getJoinStr());    	
    	Page page  = new Page(param);
    	JSONArray array  = JSONArray.fromObject(Dao.selectList(basePath+"getBusTaxMergerApplyList", param));
    	page.addDate(array, Dao.selectInt(basePath+"getBusTaxMergerApplyCount", param));
		return page;
    }
}

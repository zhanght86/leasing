package com.pqsoft.addTax.service;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.support.DaoSupport;

import jxl.write.WritableCellFormat;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.InvoiceUtil;
import com.pqsoft.util.StringUtils;

public class VatFirstPayService {
	private String basePath = "VatInvoice.";
	
	/**
	 * 增值税首期款开票申请页数据
	 * @param param
	 * @return
	 */
	public Page getFirstPayApplyPageData(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "增值税发票");
		
		//
		param.put("COLUMNSITEM", Dao.selectOne(basePath+"aykpColumnsMaxMoney"));
		param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"aykpColumnsTotalMoney"));
		
		
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getFirstPayApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getFirstPayApplyCount", param));
		return page ; 
	}
	
	public String getInvoiceSeq(){
		return Dao.getSequence("SEQ_FI_SALEITEM_INVOICE");
	}
	
	public int addInvoiceMainMess(Map<String,Object> param ){
		return Dao.insert("BusTaxInvoice.addInvoiceMess", param);
	}
	
	
	public int addInvoiceDetailMess(Map<String,Object> param ){
		return Dao.insert("BusTaxInvoice.addInvoiceDetail", param);
	}
	
	public int addInvoiceItem(Map<String,Object> param ){
		return Dao.insert("BusTaxInvoice.addInvoiceItem", param);
	}
	
	public List<Object> getInvoiceDetail(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		List<Object> list = Dao.selectList("BusTaxInvoice.getInvoiceDetailMess", param);
		return list;
	}
	
	public List<Object> getInvoiceDetailVirtualMess(Map<String,Object> param){
		List<Object> list = Dao.selectList("BusTaxInvoice.getInvoiceDetailVirtualMess", param);
		return list;
	}
	
	public List<Object> getMergerDetailMess(Map<String,Object> param ){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		return Dao.selectList("VatInvoice.getMergerDetailMess", param);
	}
	
	public List<Object> getMergerItemMess(Map<String,Object> param ){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		List<Object> list = Dao.selectList("VatInvoice.getMergerItemMess", param);
		return list;
	}
	
	/**
	 * 按月开票添加发票相关信息
	 * @param newParam
	 * @param result
	 * @return
	 */
	public int addInvoice(Map<String,Object> newParam){
		int result = 0 ;
		//融资租赁公司信息
		//查询该平台融资公司信息
		Map FAMap=new HashMap();
		FAMap.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		Map<String,Object> sflcMess = getSflcMess(FAMap);
		DecimalFormat df2  = new DecimalFormat("###.00"); 
		Double tax = sflcMess != null && sflcMess.containsKey("TAX") && sflcMess.get("TAX") != null ? Double.parseDouble(sflcMess.get("TAX").toString()):Double.parseDouble("0.17");
		//将申请数据插入发票信息表中
		String invoice_id = getInvoiceSeq();
		newParam.put("INVOICE_ID", invoice_id);
		result = addInvoiceMainMess(newParam);
		int count = 0 ;
	    List<Object> invoiceDetails = getInvoiceDetail(newParam);
	    
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
			count += addInvoiceDetailMess(param2);
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
				addInvoiceItem(paramItem);
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
				addInvoiceItem(paramItem);
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
				addInvoiceItem(paramItem);
            }
			
		}
	    return result ;
	}
	
	
	/**
	 * 添加虚拟核销收据相关信息
	 * @param newParam
	 * @param result
	 * @return
	 */
	public int addVisualInvoice(Map<String,Object> newParam){
		int result = 0 ;
		//融资租赁公司信息
		//查询该平台融资公司信息
		Map FAMap=new HashMap();
		FAMap.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		Map<String,Object> sflcMess = getSflcMess(FAMap);
		DecimalFormat df2  = new DecimalFormat("###.00"); 
		Double tax = sflcMess != null && sflcMess.containsKey("TAX") && sflcMess.get("TAX") != null ? Double.parseDouble(sflcMess.get("TAX").toString()):Double.parseDouble("0.17");
		//将申请数据插入发票信息表中
		String invoice_id = getInvoiceSeq();
		newParam.put("INVOICE_ID", invoice_id);
		result = addInvoiceMainMess(newParam);
		int count = 0 ;
	    List<Object> invoiceDetails = getInvoiceDetailVirtualMess(newParam);
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
	    	param2.put("ITEM_STATUS", 1) ;
	    	//通过开票协议查询出邮寄地址
	    	Map EMS_MAP=Dao.selectOne("BusTaxInvoice.queryEmsByPayCode", param2);
	    	if(EMS_MAP !=null && EMS_MAP.get("EMS_ID")!=null){
	    		param2.put("DL_EMS_ID",EMS_MAP.get("EMS_ID"));
	    	}
			count += addInvoiceDetailMess(param2);

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
				addInvoiceItem(paramItem);
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
				addInvoiceItem(paramItem);
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
				addInvoiceItem(paramItem);
            }
			
		}
	    return result ;
	}
	
	/**
	 * 合并开票添加发票相关信息
	 * @param newParam
	 * @param result
	 * @return
	 */
	public int addInvoiceMerger(Map<String,Object> newParam){
		int result = 0 ;
		//融资租赁公司信息
		//查询该平台融资公司信息
		Map FAMap=new HashMap();
		FAMap.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		Map<String,Object> sflcMess = getSflcMess(FAMap);
		DecimalFormat df2  = new DecimalFormat("###.00"); 
		Double tax = sflcMess != null && sflcMess.containsKey("TAX") && sflcMess.get("TAX") != null ? Double.parseDouble(sflcMess.get("TAX").toString()):Double.parseDouble("0.17");
		//将申请数据插入发票信息表中
		String invoice_id = getInvoiceSeq();
		newParam.put("INVOICE_ID", invoice_id);
		result = addInvoiceMainMess(newParam);
		//加入票据细项信息
	    List<Object> invoiceItems = this.getMergerItemMess(newParam);
		for (Object object1 : invoiceItems) {
			Map<String,Object> itemDetail = (Map<String,Object>)object1;
			Map<String,Object> paramItem = new HashMap<String, Object>();
		    paramItem.put("INVOICE_ID", invoice_id);
		    paramItem.put("ITEM_NAME", itemDetail.containsKey("MERGE_NAME") && itemDetail.get("MERGE_NAME") !=null ? itemDetail.get("MERGE_NAME").toString():"");
		    paramItem.put("ITEM_NUM", 1);
		    Double fact_amt2 = itemDetail.containsKey("BEGINNING_MONEY") && itemDetail.get("BEGINNING_MONEY") !=null ? Double.parseDouble(itemDetail.get("BEGINNING_MONEY").toString()):0;
		    paramItem.put("ITEM_FACT_AMT", fact_amt2);
		    
		    Double tax_Rat=itemDetail.containsKey("TAX_RATE") && itemDetail.get("TAX_RATE") !=null ? Double.parseDouble(itemDetail.get("TAX_RATE").toString()):tax;
	    	
			Double sub_amt2 = fact_amt2/(1+tax_Rat);
		    paramItem.put("ITEM_SUB_AMT", df2.format(sub_amt2));
		    Double tax_amt2 = sub_amt2*tax_Rat;
			paramItem.put("ITEM_SUB_TAX_AMT", df2.format(tax_amt2));
			paramItem.put("ITEM_TAX_RATE", String.valueOf(tax_Rat));
			paramItem.put("BEGINNING_ID", itemDetail.get("BEGINNING_ID"));
			addInvoiceItem(paramItem);
		}
		int count = 0 ;
	    List<Object> invoiceDetails = this.getMergerDetailMess(newParam);
	    for (Object object2 : invoiceDetails) {
	    	Map<String,Object> param2 = (Map<String,Object>)object2;
	    	param2.put("INVOICE_ID", invoice_id);
	    	param2.put("ITEM_NAME", param2.containsKey("BEGINNING_NAME") && param2.get("BEGINNING_NAME") !=null ? param2.get("BEGINNING_NAME").toString():"");
	    	param2.put("ITEM_NUM", "1");
	    	Double fact_amt = param2.containsKey("BEGINNING_MONEY") && param2.get("BEGINNING_MONEY") !=null ? Double.parseDouble(param2.get("BEGINNING_MONEY").toString()):0;
	    	param2.put("ITEM_FACT_AMT", fact_amt);
	    	Double tax_Rat=param2.containsKey("TAX_RATE") && param2.get("TAX_RATE") !=null ? Double.parseDouble(param2.get("TAX_RATE").toString()):tax;
	    	param2.put("ITEM_TAX_RATE", String.valueOf(tax_Rat));
	    	param2.put("TARGET_TYPE", param2.containsKey("INVOICE_TARGET_TYPE")&& param2.get("INVOICE_TARGET_TYPE")!=null ? param2.get("INVOICE_TARGET_TYPE").toString():""); 
	    	param2.put("TARGET_ID", param2.containsKey("INVOICE_TARGET_ID")&& param2.get("INVOICE_TARGET_ID")!=null ? param2.get("INVOICE_TARGET_ID").toString():"");
	    	//通过开票协议查询出邮寄地址
	    	Map EMS_MAP=Dao.selectOne("BusTaxInvoice.queryEmsByPayCode", param2);
	    	if(EMS_MAP !=null && EMS_MAP.get("EMS_ID")!=null){
	    		param2.put("DL_EMS_ID",EMS_MAP.get("EMS_ID"));
	    	}
	    	
	    	param2.put("CREATOR", Security.getUser().getName());
	    	param2.put("RENT_LIST", param2.containsKey("BEGINNING_NUM") && param2.get("BEGINNING_NUM") !=null ? param2.get("BEGINNING_NUM").toString():"");
	    	param2.put("PAYLIST_CODE", param2.containsKey("PAYLIST_CODE") && param2.get("PAYLIST_CODE") !=null ? param2.get("PAYLIST_CODE").toString():"");
			count += addInvoiceDetailMess(param2);
		}
	    return result ;
	}
	
	/**
	 * 获取融资租赁公司信息
	 * @return
	 */
	public Map<String,Object> getSflcMess(Map map){
		return Dao.selectOne("BusTaxInvoice.getSflcMess",map);
	}

	/**
	 * 增值税首期款开票核销数据
	 * @param param
	 * @return
	 */
	public Page getVatHeXiaoPageData(Map<String,Object> param ){
		param.put("INVOICE_TYPE", "增值税发票");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVatHeXiaoList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVatHeXiaoCount", param));
		return page;
	}

	public Excel getVatExcelExportData(Map<String,Object> param,String type ){
		param.put("INVOICE_TYPE", "增值税发票");
		List<Map<String,Object>>  pageList = Dao.selectList(basePath+"vatHeXiaoExcelData", param);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("INVOICE_ID", "单据号");
		title.put("TARGET_ID", "购方协议编号");
		title.put("TARGET_NAME", "购方单位名称");
		for (int j = 0; j < pageList.size(); j++) {
			Map<String,Object> resMap = pageList.get(j);
			Boolean result = checkInvoiceMess(resMap);
			if(result == false){
				pageList.remove(j);
				j--;
				continue;
			}
			//添加导出标识
			Dao.update(basePath+"updateExportFlag", resMap);
			
			if(resMap.containsKey("ITEM_CODE") && (resMap.get("ITEM_CODE") ==null || "".equals(resMap.get("ITEM_CODE").toString().trim()))){
				String Item_Name = resMap.containsKey("ITEM_NAME") && resMap.get("ITEM_NAME")!= null ?resMap.get("ITEM_NAME").toString():"";
				String Prod_Model = resMap.containsKey("ITEM_MODEL") && resMap.get("ITEM_MODEL") != null ? resMap.get("ITEM_MODEL").toString():"";
				resMap.put("ITEM_CODE", InvoiceUtil.getItemNameId(Item_Name, Prod_Model));
			}
			if(resMap.containsKey("TARGET_TYPE")&& resMap.get("TARGET_TYPE") !=null && "1".equals(resMap.get("TARGET_TYPE").toString()) &&
			    "个人".equals(resMap.containsKey("TARGET_TAX_QUALIFICATION") && resMap.get("TARGET_TAX_QUALIFICATION") !=null ? resMap.get("TARGET_TAX_QUALIFICATION").toString():"")){
			   resMap.put("TARGET_TAX_CODE",resMap.get("TARGET_CARD_ID").toString());
			}
			if(resMap.containsKey("FUND_TYPE") && resMap.get("FUND_TYPE") !=null && "货款".equals(resMap.get("FUND_TYPE").toString())){
				resMap.put("ITEM_NUM",resMap.get("ITEM_NUM").toString());
			}else{
				resMap.put("ITEM_NUM","");
			}
			//个人地址电话不打印
			if(resMap.containsKey("TARGET_CUST_TYPE") && resMap.get("TARGET_CUST_TYPE") !=null && "个人".equals(resMap.get("TARGET_CUST_TYPE").toString())){
				resMap.put("TARGET_ADD_PHONE","");
				resMap.put("TARGET_BANK_ACCOUNT","");
			}
			
		}
		title.put("TARGET_TAX_CODE", "购方税号");
		title.put("TARGET_ADD_PHONE", "购方地址电话");
		title.put("TARGET_BANK_ACCOUNT", "购方银行帐号");
		title.put("MEMO", "备注");
		title.put("ITEM_CODE", "商品编号");
		title.put("ITEM_NAME", "商品名称");
		title.put("ITEM_MODEL", "规格型号");
		title.put("ITEM_UNIT", "计量单位");
		title.put("ITEM_NUM", "数量");
		title.put("ITEM_UNIT_PRICE", "单价");
		title.put("ITEM_FACT_AMT", "含税金额");
		title.put("ITEM_SUB_AMT", "金额");
		title.put("ITEM_TAX_RATE", "税率");
		title.put("GOODS_TARIFF", "商品税目");
		title.put("REVIEWER", "复核人");
		title.put("PAYEE", "收款人");
		title.put("DISCOUNT_PRICE", "折扣金额");
		title.put("DISCOUNT", "折扣率");
		title.put("SELLER_BANK_NUM", " 销方银行账号");
		title.put("SELLER_ADDR_TEL", "销方地址电话");
		title.put("ITEM_SUB_TAX_AMT", "税额");
		title.put("DISCOUNT_RATE", "折扣税额");
		title.put("INVENTORY_PRODUCT_NAME", "清单行商品名称");
		title.put("VAT_TYPE", "发票类型");
		title.put("INVOICE_DATE", "单据日期");
		title.put("SUP_SHORTNAME", "供应商 ");
		title.put("EMS_TO_NAME", "收件人姓名");
		title.put("EMS_TO_PHONE", "收件人电话");
		title.put("EMS_TO_DW", "单位名称");
		title.put("EMS_TO_ADDR", "地址");
		title.put("EMS_TO_CITY", "城市");
		title.put("EMS_TO_CODE", "邮编");	
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		excel.neadDateFormat(false);
		excel.defaultFormat(false);
		excel.putFormateParam("ITEM_FACT_AMT", Excel.PRICEFORMAT1);
		excel.putFormateParam("ITEM_SUB_AMT", Excel.PRICEFORMAT1);
		excel.putFormateParam("ITEM_TAX_RATE", Excel.PRICEFORMAT1);
		excel.putFormateParam("DISCOUNT_PRICE", Excel.PRICEFORMAT1);
		excel.putFormateParam("DISCOUNT", Excel.PRICEFORMAT1);
		excel.putFormateParam("ITEM_SUB_TAX_AMT", Excel.PRICEFORMAT1);
		excel.putFormateParam("DISCOUNT_RATE", Excel.PRICEFORMAT1);
		excel.putFormateParam("VAT_TYPE", Excel.ZSFORMAT);
		excel.putFormateParam("INVOICE_DATE", Excel.DATEFORMAT1);
		  
		return excel;
	}
	
	public int uploadReceiptVat(List<File> files) {
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				Map<String,Object> m = new HashMap<String, Object>();
				
				if(row.size() < 54) {
					continue;
				} 
//				String []objrow = obj[row];	
				String values ="SEQ_FI_INVOICE_INFO_TAX.nextval ,"; 
				//System.out.println(objrow);
				for(int j = 0 ; j < 54 ; j++){
					values += "'"+StringUtils.nullToString(row.get(j))+"',";
				}
			
				String invoice_code = StringUtils.nullToString(row.get(4)).split("\\.")[0];
				if(StringUtils.nullToString(row.get(4)).equals("")){
					continue;
				}
				values +="'"+Security.getUser().getName()+"',sysdate,null";
				m.put("SQL_VALUES", values);
				m.put("SQL_INVOICE_CODE", invoice_code);
				m.put("SQL_EQUIP_NUM", StringUtils.nullToString(row.get(5)));
				Dao.insert(basePath + "insertIntoFi_Invoice_Info_Tax", m);
				
				String tempStr = StringUtils.nullToString(row.get(11));
				String pro_code = tempStr.replaceAll("^\\S*\\s+", "");
				m.put("EXPORT_MEMO", StringUtils.nullToString(row.get(8)));
				m.put("PRO_CODE", pro_code);
				m.put("INVOICE_DLD", tempStr.substring(0, tempStr.indexOf(" ")));
				m.put("INVOICE_TO_NAME", StringUtils.nullToString(row.get(9)));
				m.put("INVOICE_CODE", invoice_code);
				
				m.put("INVOICE_DATE", StringUtils.nullToString(row.get(6)));
				m.put("EMS_NAME", "");//
				m.put("EMS_NUM", StringUtils.nullToString(row.get(25)));
				m.put("EMS_DATE", "");
//				m.put("CURR_ALL_INVOICE_AMT", sum_amt);
				m.put("SQL_USER_ID", Security.getUser().getName());
				count += Dao.update(basePath + "updateCheckByBillCode" , m);
				
				Dao.update(basePath + "updateMergeCheckByBillCode" , m);
				
			}
				
		}
		return count;
	}
	
	/**
	 * 删除票据主表
	 * @param param
	 * @return
	 */
	public int delInvoiceMain(Map<String,Object> param){
		return Dao.delete(basePath+"delInvoiceMain",param);
		
	}
	
	/**
	 * 删除票据Item信息
	 * @param param
	 * @return
	 */
	public int delInvoiceItem(Map<String,Object> param){
		return Dao.delete(basePath+"delInvoiceItem", param);
	}
	/**
	 * 产出票据detail表 
	 * @param param
	 * @return
	 */
	public int delInvoiceDetail(Map<String,Object> param){
		return Dao.delete(basePath+"delInvoiceDetail", param);
	}
	
	/**
	 * 增值税票查询数据
	 * @param param
	 * @return
	 */
	public Page getVatSearchPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVatSearchList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVatSearchCount", param));
		return page;
	}
	
	public Page getVatMergerInvoiceApplyData(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "增值税发票");
		Page page = new Page(param);
		
		param.put("COLUMNSITEM", Dao.selectOne(basePath+"hbkpColumnsMaxMoney"));
		param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"hbkpColumnsTotalMoney"));
		
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVatMergerApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVatMergerApplyCount", param));
		return page;
	}
	
	public List<Object> getInvoiceList(Map<String,Object> param){
		return Dao.selectList(basePath+"getDropInvoiceList", param);
	}
	
	//票据信息验证
	public Boolean checkInvoiceMess(Map<String,Object> invoiceMess){
		Boolean flag = true ;
		String errorMsg = "";
		Map<String,Object> errInvoice = new HashMap<String, Object>();
		errInvoice.put("INVOICE_ID", invoiceMess.get("INVOICE_ID").toString());
		if(invoiceMess.get("TARGET_TAX_QUALIFICATION") ==null || "".equals(invoiceMess.get("TARGET_TAX_QUALIFICATION"))){
			errorMsg += "缺乏开票对象资质信息；";
		    flag = false;
		}
		errInvoice.put("EMS_FLAG", invoiceMess.get("EMS_FLAG"));
		errInvoice.put("EMS_TO_NAME", invoiceMess.get("EMS_TO_NAME"));
		errInvoice.put("EMS_TO_PHONE", invoiceMess.get("EMS_TO_PHONE"));
		errInvoice.put("EMS_TO_DW", invoiceMess.get("EMS_TO_DW"));
		errInvoice.put("EMS_TO_ADDR", invoiceMess.get("EMS_TO_ADDR"));
		errInvoice.put("TARGET_ID", invoiceMess.get("TARGET_ID"));
		errInvoice.put("TARGET_NAME", invoiceMess.get("TARGET_NAME"));
		errInvoice.put("MEMO", invoiceMess.get("MEMO"));
		if(InvoiceUtil.isNotNull(errInvoice)==false){
			errorMsg += "缺少开票对象所在供应商邮寄地址信息或其他信息有空值；";
			errInvoice.put("ERRMSG", errorMsg);
			flag = false;
		}
		if(invoiceMess.containsKey("VAT_TYPE") && invoiceMess.get("VAT_TYPE") !=null && "0".equals(invoiceMess.get("VAT_TYPE").toString())){
			if(invoiceMess.get("TARGET_ADD_PHONE")== null || "".equals(invoiceMess.get("TARGET_ADD_PHONE").toString().trim())){
	        	errorMsg +="缺少开票对象地址电话信息；";
	        	flag = false;
			}else if(invoiceMess.get("TARGET_TEL") == null){
				errorMsg += "开票对象电话信息缺失；";
	        	flag = false;
			}else if(invoiceMess.get("TARGET_TEL").toString().trim().length()<7){
//	        }else if(!invoiceMess.get("TARGET_TEL").toString().trim().matches("(^(\\d{3,4}-)?\\d{7,8})$|(13[0-9]{9})")){
//	        	System.out.println(invoiceMess.get("TARGET_TEL").toString().trim());
//	        	System.out.println(invoiceMess.get("TARGET_TEL").toString().trim().matches("(^(\\d{3,4}-)?\\d{7,8})$|(13[0-9]{9})"));
	        	errorMsg += "开票对象电话信息不合法；";
	        	flag = false;
	        }
	        if(invoiceMess.get("TARGET_BANK_ACCOUNT")==null || "".equals(invoiceMess.get("TARGET_BANK_ACCOUNT").toString().trim())){
	        	errorMsg += "缺少开票对象开户行  开户账号信息；";
	        	flag = false;
	        }else if(!invoiceMess.get("TARGET_BANK_ACCOUNT").toString().trim().matches("^.+\\s+[-\\w]+$")){
//	        }else if(!invoiceMess.get("TARGET_BANK_ACCOUNT").toString().trim().matches("^[^\\d\\w]+[0-9\\s]+[0-9]+$")){
	        	errorMsg += "开票对象开户行 或者 账号信息不合法；";
	        	flag = false;
	        }
		}
        
        //分期票据
        if(invoiceMess.containsKey("FUND_TYPE") && invoiceMess.get("FUND_TYPE") != null && "货款".equals(invoiceMess.get("FUND_TYPE").toString().trim())){
        	if(invoiceMess.containsKey("ITEM_MODEL") && (invoiceMess.get("ITEM_MODEL") == null || "".equals(invoiceMess.get("ITEM_MODEL").toString().trim()))){
        		errorMsg += "确切的设备名称、设备类型为空；";
        		flag = false;
        	}
        }else{
        	if(!InvoiceUtil.testAmt(StringUtils.nullToOther(invoiceMess.get("ITEM_SUB_AMT"),"0"))){
        		errorMsg += "开票金校验研失败；";
        		flag = false;
        	}
        }
        errInvoice.put("ERRMSG", errorMsg);
        if(flag == false){
        	this.insertErrMsg(errInvoice);
        }
		return flag ;
	}
	
	public int insertErrMsg(Map<String,Object> param){
		return Dao.update("invoice.insertErrMsg", param);
	}
	
	public List<Object> getSonInvoice(Map<String,Object> param){
		return Dao.selectList(basePath+"getSonInvoice", param);
	}
	
	public List<Object> getInvoiceItemsById(Map<String,Object> param){
		return Dao.selectList(basePath+"getInvoiceItemsById", param);
	}
	
	public List<Object> getAllFirstInviceMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("COLUMNSITEM", Dao.selectOne(basePath+"aykpColumnsMaxMoney"));
		param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"aykpColumnsTotalMoney"));
		return Dao.selectList(basePath+"getAllFirstInviceMess", param);
	}
	
	public List<Object> getAllMergerInvoiceMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		
		param.put("COLUMNSITEM", Dao.selectOne(basePath+"hbkpColumnsMaxMoney"));
		param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"hbkpColumnsTotalMoney"));
		
		return Dao.selectList(basePath+"getAllMergerInvoiceMess", param);
	}
	
	public int getUpLimitMess(Map<String,Object> param){
		return Dao.selectInt(basePath+"getUpLimitMess", param);
	}
	
	public Map<String,Object> getDicShortNameByTypeCode(Map<String,Object> param){
		return Dao.selectOne(basePath+"getDicShortNameByTypeCode",param);
	}
	
	//首期款开票自动申请（定时任务）
	public void appentFirstInviceTime(){
		Map param=new HashMap();
		param.put("VAT_FLAG", "YES");
		param.put("INVOICE_TYPE", "增值税发票");
		List<Object> applyMess = this.getAllFirstInviceMess(param);
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "首期付款");
			newParam.put("FUND_NAME", "首期付款");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			s.add("手续费");
			s.add("首期租金");
//			s.add("租前利息");
			if(newParam.containsKey("PAY_WAY") && newParam.get("PAY_WAY") !=null && ("2".equals(newParam.get("PAY_WAY").toString()) ||
					"4".equals(newParam.get("PAY_WAY").toString()) || "6".equals(newParam.get("PAY_WAY").toString()))){
				s.add("本金");
				s.add("利息");
			}
			s.add("留购价款");
			s.add("管理费");
			s.add("保险费");
			s.add("GPS费用");
			s.add("第一期租金");
			s.add("租前利息");
			newParam.put("FIRST_TYPE", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("IF_INVOICE", "yes");
			this.addInvoice(newParam);
		}
	}
}

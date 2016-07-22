package com.pqsoft.invoice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.BigDecimalUtil;
import com.pqsoft.util.InvoiceEntry;
import com.pqsoft.util.InvoiceItem;
import com.pqsoft.util.StringUtils;

/**
 * 
 * @ClassName: InvoiceHandleService 
 * @Description: (票据公共处理服务) 
 * @author 程龙达
 * @date 2013-10-29 下午10:44:57 
 *
 */
public class InvoiceHandleService {
	String nameSpace = "invoiceHandle.";
	
	
	/**
	 * 
	 * @Title: handleInvoice 
	 * @Description: TODO(营业税 收据 ：   按项目  对象 合并)  
	 * @return void 
	 * @throws 
	 */
	public int mergeReceipt(){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("SQL_INVOICE_TYPE", "收据");
		//1、
		Dao.insert(nameSpace + "insertMergeReceiptyys",m);
		List<Map<String,Object>> l = Dao.selectList(nameSpace + "selectInsertMergeInvoice", m);
		String invoice_id_new = "";
		String invoice_id_old = "";
		String wm_concat_value = "";
		int index = 0;
		for(Map temp : l){
			invoice_id_new = temp.get("ID").toString();
			wm_concat_value = temp.get("WM_CONCAT_VALUE").toString();
			
			m.put("SQL_SALEITEM_INVOICE_ID", invoice_id_new);
			Dao.update(nameSpace + "updateInvoiceByInvoiceId",m);
			for(String t : wm_concat_value.split(",")){
				invoice_id_old = t;
				m.put("SQL_SALEITEM_INVOICE_ID_OLD", invoice_id_old);
				m.put("SQL_SALEITEM_INVOICE_ID_NEW", invoice_id_new);
				Dao.delete(nameSpace + "updateInvoiceItemByInvoiceId",m);	
				Dao.delete(nameSpace + "updateInvoiceDetailsByInvoiceId",m);	
				
				m.put("SQL_SALEITEM_INVOICE_ID", invoice_id_old);
				Dao.update(nameSpace + "deleteInvoiceByInvoiceId",m);
			}
			index++;
		}
		return index;
	}
	/**
	 * 
	 * @Title: handleInvoice 
	 * @Description: TODO(营业税发票+ 收据 ：   按项目  对象 期次合并)  
	 * @return void 
	 * @throws 
	 */
	public int mergeInvoiceByRentList(){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("SQL_INVOICE_TYPE", "增值税");
		//1、
		Dao.insert(nameSpace + "insertMergeInvoiceByRentList",m);
		List<Map<String,Object>> l = Dao.selectList(nameSpace + "selectInsertMergeInvoice", m);
		String invoice_id_new = "";
		String invoice_id_old = "";
		String wm_concat_value = "";
		int index = 0;
		for(Map temp : l){
			invoice_id_new = temp.get("ID").toString();
			wm_concat_value = temp.get("WM_CONCAT_VALUE").toString();
			
			m.put("SQL_SALEITEM_INVOICE_ID", invoice_id_new);
			Dao.update(nameSpace + "updateInvoiceByInvoiceId",m);
			for(String t : wm_concat_value.split(",")){
				invoice_id_old = t;
				m.put("SQL_SALEITEM_INVOICE_ID_OLD", invoice_id_old);
				m.put("SQL_SALEITEM_INVOICE_ID_NEW", invoice_id_new);
				Dao.delete(nameSpace + "updateInvoiceItemByInvoiceId",m);	
				Dao.delete(nameSpace + "updateInvoiceDetailsByInvoiceId",m);	
				
				m.put("SQL_SALEITEM_INVOICE_ID", invoice_id_old);
				Dao.update(nameSpace + "deleteInvoiceByInvoiceId",m);
			}
			index++;
		}
		return index;
	}
	
	/**
	 * 
	 * @Title: handleInvoice 
	 * @Description: TODO(增值税发票  ：   按还款计划  对象  类型 合并   首期款和租金不在一块)  
	 * @return void 
	 * @throws 
	 */
	public int handleInvoice(Map<String,Object> m){
		if(m==null){
			m = new HashMap<String,Object>();
		}
		m.put("SQL_INVOICE_FLAG", "按月合并票据");
		m.put("SQL_INVOICE_TYPE", "增值税");
		//1、
		Dao.insert(nameSpace + "insertMergeInvoiceByTarget",m);
		List<Map<String,Object>> l = Dao.selectList(nameSpace + "selectInsertMergeInvoice", m);
		String invoice_id_new = "";
		String invoice_id_old = "";
		String wm_concat_value = "";
		int index = 0;
		for(Map temp : l){
			invoice_id_new = temp.get("ID").toString();
			wm_concat_value = temp.get("WM_CONCAT_VALUE").toString();
			
			m.put("SQL_SALEITEM_INVOICE_ID", invoice_id_new);
			Dao.update(nameSpace + "updateInvoiceByInvoiceId",m);
			for(String t : wm_concat_value.split(",")){
				invoice_id_old = t;
				m.put("SQL_SALEITEM_INVOICE_ID_OLD", invoice_id_old);
				m.put("SQL_SALEITEM_INVOICE_ID_NEW", invoice_id_new);
				Dao.delete(nameSpace + "updateInvoiceItemByInvoiceId",m);	
				Dao.delete(nameSpace + "updateInvoiceDetailsByInvoiceId",m);	
				
				m.put("SQL_SALEITEM_INVOICE_ID", invoice_id_old);
				Dao.update(nameSpace + "deleteInvoiceByInvoiceId",m);
			}
			index++;
		}
		return index;
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: splitInvoice 
	 * @Description: TODO(超额拆分 ) 
	 * @return void 
	 * @throws 
	 */
	public int splitInvoice(Map<String,Object> m) {
		int num1 = 0;//营业税条目上限
		double amt1 = 0;//营业税金额上限
		int num2 = 0;//营业税条目上限
		double amt2 = 0;//营业税金额上限
		if(m==null){
			m = new HashMap<String,Object>();
		}
		m.put("SQL_TYPE", "发票_上限定义");
		m.put("SQL_CODE", "1");
		num1 = Integer.parseInt(Dao.selectOne(nameSpace+"getdictshortnamebycode", m).toString());
		m.put("SQL_CODE", "2");
		amt1 = Double.parseDouble(Dao.selectOne(nameSpace+"getdictshortnamebycode", m).toString());
		m.put("SQL_CODE", "3");
		num2 = Integer.parseInt(Dao.selectOne(nameSpace+"getdictshortnamebycode", m).toString());
		m.put("SQL_CODE", "4");
		amt2 = Double.parseDouble(Dao.selectOne(nameSpace+"getdictshortnamebycode", m).toString());
		
		
		m.put("SQL_NUM1", num1);
		m.put("SQL_NUM2", num2);
		m.put("SQL_AMT1", amt1);
		m.put("SQL_AMT2", amt2);
		m.put("SQL_INVOICE_TYPE1", "营业税");
		m.put("SQL_INVOICE_TYPE2", "增值税");
		List<Map<String,Object>> l = Dao.selectList(nameSpace +"selectInvoiceInfo",m);

		String type = "";//增值税  营业税标识
		int num = 0;
		double amt = 0;
		
		String cur_id = "";//发票标识
		int cur_num = 0;//
		double cur_amt = 0;//
		
		String proj_code = "";
		int index=0;

		for(Map<String,Object> temp : l){
			cur_id = StringUtils.nullToString(temp.get("ID"));
			m.put("SQL_SALEITEM_INVOICE_ID", cur_id);
			cur_num = Integer.parseInt(StringUtils.nullToString(temp.get("AMOUNT")));
			cur_amt = Double.parseDouble(StringUtils.nullToString(temp.get("INVOICE_AMT")));
			proj_code = StringUtils.nullToString(temp.get("PROJ_CODE"));
			
			type = temp.get("TYPE").toString();
			if(type.equals("营业税")){
				num = num1;
				amt = amt1;
				//营业税拆分 
			}else if(type.equals("增值税")){
				//增值税拆分
				num = num2;
				amt = amt2;
			}else{
				continue ;//throw new Exception("存在发票主表invoice_type存储错误！");
			}

			split(num,amt,cur_id,proj_code,cur_num,cur_amt);//发票id ， 当前总条目， 当前总金额，类型 ,是否提前开票
			//更新拆分标识
			Dao.update(nameSpace+"updateSplitFlagInvoiceId", m);
			index++;
		}
		return index;
	}
	/**
	 * @param projCode 
	 * @param amt 
	 * @param num 
	 * @param curAmt 
	 * @param curNum 
	 * @param curId 
	 * 
	 * @Title: splitDetail 
	 * @Description: TODO(拆分明细)  
	 * @return void 
	 * @throws 
	 */
	public void split(int num, double amt, String curId, String projCode, int curNum, double curAmt){
		String invoice_id = curId;//主发票id
		int max_num = num;//最大条目
		double max_amt = amt;//最大金额
		String project_id = projCode;//发票编号
		int cur_Num = curNum;//当前条数
		double cur_Amt = curAmt;//当前金额
		
//		InvoiceEntry invoiceEntry = new InvoiceEntry();//当前发票
		
		InvoiceItem item = null;//票面条目
		List<InvoiceItem> invoice = new ArrayList<InvoiceItem>();
		List<Map<String,Object>> l = Dao.selectList(nameSpace+"selectInvoiceItemsByInvoiceId", invoice_id);
		double temp_amt = 0.0;//剩余总金额
		int count = 1;
		//item为单位
		double item_amt = 0.0;//
		double sum = 0;
		for(Map<String,Object> temp : l){
			
			String item_id = StringUtils.nullToString(temp.get("ID"));//
			String item_name = StringUtils.nullToString(temp.get("ITEM_NAME"));//名称
			double item_unit_amt = Double.parseDouble(StringUtils.nullToOther(temp.get("ITEM_UNIT_PRICE"),"0"));// 当前条目含税金额
			int item_num = Integer.parseInt(StringUtils.nullToOther(temp.get("ITEM_NUM"),"0"));//台量 或者  数量
			item_amt = Double.parseDouble(StringUtils.nullToString(temp.get("ITEM_FACT_AMT")));// 当前条目含税金额
			String item_model = StringUtils.nullToString(temp.get("ITEM_MODEL"));//规格型号
			String Item_Tax_Rate = StringUtils.nullToString(temp.get("ITEM_TAX_RATE"));//税率
			String Item_unit = StringUtils.nullToString(temp.get("ITEM_UNIT"));//计价单位
//			String prod_num = "	";
			
			double prod_amt = item_amt;//item amt  //拆分后当前条目金额----------√
			sum = temp_amt + item_amt;//累计总金额 =剩余+当前条目
			
			if(sum <= max_amt ){//是否超额
				temp_amt += item_amt;//剩余总金额= 历史+当前条目
			}
			//余额+当前金额超额处理
			//超过的部分开具发票
			while(sum>max_amt){
				
				double n = BigDecimal.valueOf(max_amt - temp_amt).divide(BigDecimal.valueOf(item_amt),1,BigDecimal.ROUND_DOWN).doubleValue();//当前数量----√
				if(n==0){
					n = BigDecimal.valueOf(max_amt - temp_amt).divide(BigDecimal.valueOf(item_amt),2,BigDecimal.ROUND_DOWN).doubleValue();
				}
				if(n==0){
					n = BigDecimal.valueOf(max_amt - temp_amt).divide(BigDecimal.valueOf(item_amt),3,BigDecimal.ROUND_DOWN).doubleValue();
				}
				prod_amt = BigDecimal.valueOf(item_amt).multiply(BigDecimal.valueOf(n)).doubleValue();
				prod_amt=BigDecimalUtil.round(prod_amt, 2);
				
				//处理上一张票据
				//1、插入 
				String sub_id =  Dao.getSequence("SEQ_FI_SALEITEM_INVOICE");
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("SQL_SUB_INVOICE_ID", sub_id);
				param.put("SQL_INVOICE_ID", invoice_id);

				param.put("SQL_COUNT", count++);
				param.put("SQL_MAX_AMT", temp_amt + prod_amt);
				Dao.insert(nameSpace + "insertMergeInvoice",param);
				
				if(prod_amt > 0){
					item = new InvoiceItem();
					item.setSaleitem_Invoice_Id(invoice_id);
					item.setItem_Name(item_name);//设备名称
					item.setItem_Model(item_model);//规格型号
					item.setItem_Unit_Price(item_unit_amt);//单价
					item.setItem_Fact_Amt(BigDecimal.valueOf(prod_amt).
							divide(BigDecimal.valueOf(1),2,BigDecimal.ROUND_HALF_UP).doubleValue());//含税金额
					item.setItem_unit(Item_unit);
					item.setItem_Sub_Amt(BigDecimal.valueOf(prod_amt).
							divide(BigDecimal.valueOf(1+Double.valueOf(Item_Tax_Rate)),2,BigDecimal.ROUND_HALF_UP).doubleValue());//金额
					item.setItem_Sub_Tax_Amt(BigDecimal.valueOf((prod_amt*Double.valueOf(Item_Tax_Rate))/(1+Double.valueOf(Item_Tax_Rate))).
							divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_HALF_UP).doubleValue());//税额
					item.setItem_Num(n);
//					item.setItem_unit(Item_unit);
					item.setItem_Tax_Rate(Item_Tax_Rate);
//					item.setItem_Tax_Rate("0.17");

					invoice.add(item);
				}
				//2、插入invoice_item_info
				insertItem(invoice,sub_id);
				
				
				
				//重新计算累计总金额
				sum = sum - (temp_amt + prod_amt);
				if(sum > max_amt){
					temp_amt = 0;
					prod_amt = 0;
				}else{
					temp_amt = 	sum;
					prod_amt = sum;
				}
				
				invoice.clear();
			}
			//存在剩余金额
			if(temp_amt>0){
				item = new InvoiceItem();
				item.setSaleitem_Invoice_Id(invoice_id);
				item.setItem_Name(item_name);//设备名称
				item.setItem_Model(item_model);//规格型号
				item.setItem_Unit_Price(item_unit_amt);//单价
				item.setItem_Fact_Amt(BigDecimal.valueOf(prod_amt).
						divide(BigDecimal.valueOf(1),2,BigDecimal.ROUND_HALF_UP).doubleValue());//含税金额
				item.setItem_unit(Item_unit);//计价单位
//				item.setItem_unit(ITEm);
				item.setItem_Tax_Rate(Item_Tax_Rate);//税率
//				item.setItem_Tax_Rate("0.17");

				item.setItem_Sub_Amt(BigDecimal.valueOf(prod_amt).
						divide(BigDecimal.valueOf(1+Double.valueOf(Item_Tax_Rate)),2,BigDecimal.ROUND_HALF_UP).doubleValue());//金额
				item.setItem_Sub_Tax_Amt(BigDecimal.valueOf((prod_amt*Double.valueOf(Item_Tax_Rate))/(1+Double.valueOf(Item_Tax_Rate))).
						divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_HALF_UP).doubleValue());//税额
				item.setItem_Num(BigDecimal.valueOf(prod_amt).
						divide(BigDecimal.valueOf(Double.valueOf(item_amt)),1,BigDecimal.ROUND_HALF_UP).doubleValue());
				invoice.add(item);
				//如果等于最大条目上限
				if(max_num == invoice.size()){
					String sub_id =  Dao.getSequence("SEQ_FI_SALEITEM_INVOICE");
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("SQL_SUB_INVOICE_ID", sub_id);
					param.put("SQL_INVOICE_ID", invoice_id);

					param.put("SQL_COUNT", count++);
					param.put("SQL_MAX_AMT", temp_amt);
					Dao.insert(nameSpace + "insertMergeInvoice",param);
					insertItem(invoice,sub_id);
					invoice.clear();
					temp_amt = 0;
					prod_amt = 0;
				}
			}
			
		}
		//处理最后一张 票据
		if(invoice.size()>=0){
			//处理上一张票据
			//1、插入
			String sub_id =  Dao.getSequence("SEQ_FI_SALEITEM_INVOICE");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("SQL_SUB_INVOICE_ID", sub_id);
			param.put("SQL_INVOICE_ID", invoice_id);
			
			param.put("SQL_COUNT", count++);
			param.put("SQL_MAX_AMT", temp_amt);
			Dao.update(nameSpace + "insertMergeInvoice", param);
			
			insertItem(invoice,sub_id);
			invoice.clear();
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("SQL_COUNT", (count-1));
		param.put("SQL_INVOICE_ID", invoice_id);
		Dao.update(nameSpace + "updateMergeInvoice", param);
		

		param.put("SQL_COUNT", "'/"+(count-1)+"'");
		Dao.update(nameSpace + "updateMergeSubInvoice", param);
		
//		Dao.delete(nameSpace + "deleteHistoryInvoiceItem", param);
		
		
	}
	
	public void insertItem(List<InvoiceItem> invoice, String subId) {
		for(InvoiceItem item : invoice){
			item.setSaleitem_Invoice_Id(subId);
			Dao.insert(nameSpace+"insertIntoInvoiceItem", item);
		}
	}
}

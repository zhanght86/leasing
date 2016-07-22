package com.pqsoft.invoice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.pvm.internal.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.InvoiceUtil;

public class InvoiceService {
	private String basePath = "invoice.";
	public String nullToString(Object o){
		return o == null ? "" :o.toString();
	}
	public String nullToZero(Object o){
		return o == null || o.toString().trim().equals("") ? "0" :o.toString();
	}
	public String nullToNull(Object o){
		return o == null || o.toString().trim().equals("") ? "null" :o.toString();
	}
	public String stringToInt(Object o){
		int i = 0;
		try{
			i = Integer.parseInt(nullToString(o));
		}catch(Exception e){
			return "";
		}
		return i+"";
	}
	public Page selectConfigPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"selectConfigPageData",param));
		page.addDate(array, Dao.selectInt(basePath+"selectConfigPageDataCount", param));
		return page;
	}
//	 private static SqlSession openSession()
//	    {
//	        SqlSession session = (SqlSession)localSession.get();
//	        if(session == null)
//	        {
//	            session = ((SqlSessionFactory)Spring.getBean("myBatis")).openSession(false);
//	            localSession.set(session);
//	        }
//	        return session;
//	    }
//
//    private static final ThreadLocal localSession = new ThreadLocal();
	public ArrayList<Map<String,Object>> getDicData() {
		Map<String, Object> param = new HashMap<String,Object>();
		String tableName = "FI_INVOICE_RULER_HEAD";
		param.put("SQL_TYPE", "发票_项目特性");
		param.put("SQL_TABLENAME", tableName);
		ArrayList<Map<String,Object>> list  = (ArrayList)Dao.selectList(basePath+"selectConfigHead", param);
		return list;
	}
	public ArrayList<Map<String,Object>> alterRuler() {
		Map<String, Object> param = new HashMap<String,Object>();;
		ArrayList<Map<String,Object>> list  = getDicData();
		for(Map m : list)
		{
			param.put("SQL_COL",StringUtils.upperCase(m.get("CODE").toString()));
			Dao.update(basePath+"alterConfigHead",param);
		}
		return list;
	}
	
	public JSONArray selectRulerValues(Map<String, Object> param) {
		
		String type = nullToString(param.get("type"));
		param.put("SQL_KEYS", "CODE,FLAG");
		param.put("SQL_TYPE", type);
		param.put("SQL_CODE", "");
		param.put("SQL_FLAG", "");
		ArrayList list = new ArrayList();
		if(type.equals("业务类型")){
			list = (ArrayList)Dao.selectList(basePath+"selectRulerValuesSite", param);
		}else{
			list = (ArrayList)Dao.selectList(basePath+"selectRulerValues", param);
		}
		JSONArray j = JSONArray.fromObject(list);
		return j;
	}
	
	public JSONArray doSelectRulerValuesSite(Map<String, Object> param) {
		
		String type = nullToString(param.get("type"));
		param.put("SQL_KEYS", "CODE,FLAG");
		param.put("SQL_TYPE", type);
		param.put("SQL_CODE", "");
		param.put("SQL_FLAG", "");
		ArrayList list = (ArrayList)Dao.selectList(basePath+"selectRulerValuesSite", param);
		JSONArray j = JSONArray.fromObject(list);
		return j;
	}

	public Map<String, Object> getColumns() {

		Map<String, Object> res = new HashMap<String,Object>();
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("SQL_KEYS", " type,flag,upper(code) code,shortname ");
		param.put("SQL_TYPE", "发票_项目特性");
		param.put("SQL_CODE", "");
		param.put("SQL_FLAG", "");
		
		ArrayList<Map<String,Object>> list  = (ArrayList)Dao.selectList(basePath+"selectRulerValues", param);
		JSONObject oooo = new JSONObject();
		oooo.put("ID", "");
		oooo.put("RULER_NAME", "");
		
		JSONArray fields = new JSONArray();
		fields.add(JSONObject.fromObject("{checkbox:true,width:100,align:'center'}"));
		fields.add(JSONObject.fromObject("{field:'ID',title:'规则主键',width:30,align:'center'}"));
		fields.add(JSONObject.fromObject("{field:'RULER_NAME',title:'规则名称',width:200,align:'center',editor:{type:'text'}}"));
		
		String type = "";
		String code = "";
		String shortname = "";
		String flag = "";
		for(Map m : list){
			type = nullToString(m.get("TYPE"));
			code = nullToString(m.get("CODE"));
			shortname = nullToString(m.get("SHORTNAME"));
			flag = nullToString(m.get("FLAG"));
			oooo.put(code, "");
			
//			param.put("SQL_KEYS", "CODE,FLAG");
//			param.put("SQL_TYPE", type+"_"+flag);
//			param.put("SQL_CODE", "");
//			param.put("SQL_FLAG", "");
//			list = (ArrayList)Dao.selectList(basePath+"selectRulerValues", param);
//			JSONArray j = JSONArray.fromObject(list);
			JSONObject o = new JSONObject();
			o.put("field", code);
			o.put("title", flag);
			o.put("width", 130);
			if(shortname.equals("=")){
				JSONObject oo = new JSONObject();
				oo.put("type", "combobox");
				JSONObject ooo = new JSONObject();
				ooo.put("valueField", "FLAG");
				ooo.put("textField", "FLAG");
				if(flag.equals("业务类型")){
					ooo.put("url", "'InvoiceMg!doSelectRulerValuesSite.action?type="+flag+"'");
				}else{
					ooo.put("url", "'InvoiceMg!doSelectRulerValues.action?type="+flag+"'");
				}
				
//				ooo.put("required", "true");
//				oo.put("options", JSONObject.fromObject("{valueField:'CODE',textField:'FLAG',formatter:function(value,row){return row.FLAG;},url:'InvoiceMg!doSelectRulerValues.action?type="+type+"_"+flag+"',required:true}"));
				oo.put("options", ooo);
				o.put("editor", oo);
			}
			if(shortname.equals(">=")||shortname.equals("<=")){
				JSONObject oo = new JSONObject();
				oo.put("type", "datebox");
				o.put("editor", oo);
			}
			fields.add(o);
		}
		fields.add(JSONObject.fromObject("{field:'DESCR',title:'规则描述',width:100,align:'center',editor:{type:'text'}}"));
		oooo.put("DESCR", "");
//		{field:'CREATE_CODE',title:'创建者',width:100,
//      		editor:{
//                type:'combobox',
//                options:{
//                    valueField:'productid',
//                    textField:'productname',
//                    url:'InvoiceMg!doSelectRulerValues.action?type=""',
//                    required:true
//                }
//            }
//      	},
//		{field:'ID',checkbox:true,width:100},
//      	{field:'ON_CARD',title:'规则名称',width:100,
//			editor:{
//				type:'text'
//			}
//      	}
		res.put("fields", "["+fields.toString()+"]");
		res.put("rowss", oooo.toString());
		return res;
	}

	//保存规则
	public boolean doUpdateRulerHead(Map<String, Object> param) {
		String sql_keys = param.get("SQL_KEYS").toString();
		String sql_values = param.get("SQL_VALUES").toString();
		String sql_set =param.get("SQL_SET").toString();
		String ID = param.get("ID").toString();
		param.put("SQL_KEYS",sql_keys);
		param.put("SQL_VALUES",sql_values);
		param.put("SQL_SET",sql_set);
		//存在id为修改的
		if(ID==null||ID.trim().equals("")){
			if(sql_keys.length()>0){
				Dao.insert(basePath+"insertRuler",param);
			}
			return false;
		}else{
			if(sql_set.length()>0){
				Dao.update(basePath+"updateRuler",param);
			}
			return false;
		}
	
	}
	//删除规则
	public boolean doDeleteRulerHead(Map<String, Object> param) {
		int id = 0;
		id += Dao.delete(basePath+"deleteRulerConfig",param);
		id += Dao.delete(basePath+"deleteRuler",param);
		if(id > 0)
		return true;
		return false;
	}

	public Page selectConfigDetailData(Map<String, Object> param) {
		param.put("SQL_FUND_TYPE", "发票_资金别名");
		param.put("SQL_KAIJU", "开具");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"selectConfigDetail",param));
		page.addDate(array, Dao.selectInt(basePath+"selectConfigDetailCount", param));
		return page;
	}

	public boolean doUpdateConfigDetail(Map<String, Object> param) {
		param.put("IF_INVOICE", nullToString(param.get("IF_INVOICE")).equals("开具") ? "1" :"0");
		param.put("IF_RECEIPT", nullToString(param.get("IF_RECEIPT")).equals("开具") ? "1" :"0");
		param.put("MERGE", stringToInt(param.get("MERGE")));
		param.put("FUND_TYPE", param.get("FLAG"));
		String sql_sets="";
		String sql_values = "";
//		if(!nullToString(param.get("FUND_TYPE")).trim().equals("")){
			sql_sets += "FUND_TYPE='"+nullToString(param.get("FUND_TYPE")).trim()+"'";
			
//		}
//		if(!nullToString(param.get("IF_INVOICE")).trim().equals("")){
			sql_sets += ",IF_INVOICE='"+nullToString(param.get("IF_INVOICE")).trim()+"'";
			sql_values += ","+nullToString(param.get("IF_INVOICE")).trim();
//		}
//		if(!nullToString(param.get("IF_RECEIPT")).trim().equals("")){
			sql_sets += ",IF_RECEIPT='"+nullToString(param.get("IF_RECEIPT")).trim()+"'";
			sql_values += ","+nullToString(param.get("IF_RECEIPT")).trim();
//		}
//		if(!nullToString(param.get("MERGE")).trim().equals("")){
			sql_sets += ",MERGE='"+nullToString(param.get("MERGE")).trim()+"'";
			sql_values += ","+nullToNull(param.get("MERGE")).trim();
//		}
//		if(!nullToString(param.get("MERGE_NAME")).trim().equals("")){
			sql_sets += ",MERGE_NAME='"+nullToString(param.get("MERGE_NAME")).trim()+"'";
			sql_values += ",'"+nullToString(param.get("MERGE_NAME")).trim()+"'";
//		}
//		if(!nullToString(param.get("SHOW_NODE")).trim().equals("")){
			sql_sets += ",SHOW_NODE='"+nullToString(param.get("SHOW_NODE")).trim()+"'";
			sql_values += ",'"+nullToString(param.get("SHOW_NODE")).trim()+"'";
//		}
//		if(!nullToString(param.get("RECEIPT_NODE")).trim().equals("")){
			sql_sets += ",RECEIPT_NODE='"+nullToString(param.get("RECEIPT_NODE")).trim()+"'";
			sql_values += ",'"+nullToString(param.get("RECEIPT_NODE")).trim()+"'";
//		}

		sql_sets += ",TAX_TYPE='"+nullToString(param.get("TAX_TYPE")).trim()+"'";
		sql_values += ",'"+nullToString(param.get("TAX_TYPE")).trim()+"'";
		
		sql_sets += ",TAX_RATE='"+nullToString(param.get("TAX_RATE")).trim()+"'";
		sql_values +=",'"+nullToString(param.get("TAX_RATE")).trim()+"'";
		
		param.put("SQL_SETS", sql_sets);
		param.put("SQL_VALUES", sql_values);
		
		if(Dao.update(basePath+"updateConfigDetail",param)+Dao.update(basePath+"insertConfigDetail",param)>0)
		{
			Dao.update(basePath+"deleteConfigDetail",param);
			return true;
			}
		return false;
	}
	
	public boolean doRefreshConfigFinal(Map<String, Object> param) {
		//修改表结构
		alterRulerFinal();
		String tableName = "FI_INVOICE_RULER_HEAD_FINAL";
		param.put("SQL_TABLENAME", tableName);
		//删除表内容
		Dao.delete(basePath+"deleteRulerHeadFinal",param);
		//
		param.put("SQL_TABLENAME", "FI_INVOICE_RULER_CONFIG_FINAL");
		//删除表内容
		Dao.delete(basePath+"deleteRulerHeadFinal",param);
		//
		param.put("SQL_TYPE", "发票_项目特性");
		param.put("SQL_KEYS", "CODE,FLAG,SHORTNAME");
		param.put("SQL_CODE", "");
		param.put("SQL_FLAG", "");
		ArrayList<Map<String,Object>> list = (ArrayList)Dao.selectList(basePath+"selectRulerValues", param);
		String sql_cols1 = "";
		String sql_cols2 = "";
		String sql_cols3 = "";
		for(Map m : list)	
		{
			sql_cols1 +=","+StringUtils.upperCase(m.get("CODE").toString());
			sql_cols2 +=",head."+StringUtils.upperCase(m.get("CODE").toString())+"";
			sql_cols3 +=","+StringUtils.upperCase(m.get("CODE").toString())+"";
		}
		param.put("SQL_COLS1",sql_cols1);
		
		
		param.put("SQL_COLS2",sql_cols3);
		String key ="";
		param.put("SQL_SEQ_HEAD","seq_fi_invoice_ruler_head_f.nextval");
		param.put("SQL_TABLE_NAME","fi_invoice_ruler_head");
		Dao.insert(basePath+"insertIntoHeadFinalTool", param);
		
		String whereStr = "";
		for(Map m : list)	
		{	
			if(nullToString(m.get("SHORTNAME")).equals("=")){
				key=StringUtils.upperCase(m.get("CODE").toString());
				whereStr = "  nvl("+key+",' ') =' ' ";
				param.put("SQL_COLS2",sql_cols2.replace("head."+key, "t.flag"));
				param.put("SQL_COL",key);
				param.put("SQL_TYPE",m.get("FLAG").toString());
				param.put("SQL_TABLE_NAME","fi_invoice_ruler_head_final");
				Dao.insert(basePath+"insertRulerHeadFinal", param);
			}
		}
		for(Map m : list)	
		{	
			if(nullToString(m.get("SHORTNAME")).equals("=")){
				key=StringUtils.upperCase(m.get("CODE").toString());
				whereStr = "  nvl("+key+",' ') =' ' ";
				param.put("SQL_TABLENAME","fi_invoice_ruler_head_final where "+whereStr);
				Dao.delete(basePath+"deleteRulerHeadFinal", param);
			}
		}
		
		
		Dao.insert(basePath+"insertIntoConfigFinalTool", param);
		return true;
	}
	
	public ArrayList<Map<String,Object>> getDicDataFinal() {
		Map<String, Object> param = new HashMap<String,Object>();
		String tableName = "FI_INVOICE_RULER_HEAD_FINAL";
		param.put("SQL_TYPE", "发票_项目特性");
		param.put("SQL_TABLENAME", tableName);
		ArrayList<Map<String,Object>> list  = (ArrayList)Dao.selectList(basePath+"selectConfigHeadFinal", param);
		return list;
	}
	public ArrayList<Map<String,Object>> alterRulerFinal() {
		String tableName = "FI_INVOICE_RULER_HEAD_FINAL";
		Map<String, Object> param = new HashMap<String,Object>();;
		ArrayList<Map<String,Object>> list  = getDicDataFinal();
		for(Map m : list)
		{
			param.put("SQL_COL",StringUtils.upperCase(m.get("CODE").toString()));
			param.put("SQL_TABLENAME", tableName);
			Dao.update(basePath+"alterConfigHeadFinal",param);
		}
		return list;
	}
	
	public String  getJoinStr(){
		Map<String, Object> param = new HashMap<String,Object>();;
		param.put("SQL_TYPE", "发票_项目特性");
		param.put("SQL_KEYS", "CODE,FLAG,SHORTNAME");
		param.put("SQL_CODE", "");
		param.put("SQL_FLAG", "");
		ArrayList<Map<String,Object>> list = (ArrayList)Dao.selectList(basePath+"selectRulerValues", param);
		String a = "a";
		String b = "b";
		String join = "";
		for(Map m : list)	
		{
			join +=" and "+a+"."+StringUtils.upperCase(m.get("CODE").toString())+m.get("SHORTNAME").toString()+b+"."+StringUtils.upperCase(m.get("CODE").toString());
		}
		return join;
	}
	
	public Boolean checkInvoiceMess(){
		Map<String, Object> param = new HashMap<String,Object>();;
		Dao.update(basePath+"updateChedkedAll");
		Map<String,Object> SUP_MAP = BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		List<Map<String,Object>> l = Dao.selectList(basePath+"selectCheckedInvoices",param);
		
		Boolean flag = true ;
		for(Map<String,Object> invoiceMess : l){
			flag = true ;
			String errorMsg = "";
			if(invoiceMess.get("TARGET_TAX_QUALIFICATION") ==null || "".equals(invoiceMess.get("TARGET_TAX_QUALIFICATION"))){
				errorMsg += "缺乏开票对象资质信息；";
			    flag = false;
			}
			Map<String,Object> errInvoice = new HashMap<String, Object>();
			errInvoice.put("PRO_CODE", invoiceMess.get("PRO_CODE"));
//			errInvoice.put("EMS_FLAG", invoiceMess.get("EMS_FLAG"));
			errInvoice.put("EMS_TO_NAME", invoiceMess.get("EMS_TO_NAME"));
			errInvoice.put("EMS_TO_PHONE", invoiceMess.get("EMS_TO_PHONE"));
			errInvoice.put("EMS_TO_DW", invoiceMess.get("EMS_TO_DW"));
			errInvoice.put("EMS_TO_ADDR", invoiceMess.get("EMS_TO_ADDR"));
//			errInvoice.put("TARGET_TYPE", invoiceMess.get("TARGET_TYPE"));
//			errInvoice.put("TARGET_ID", invoiceMess.get("TARGET_ID"));
//			errInvoice.put("TARGET_NAME", invoiceMess.get("TARGET_NAME"));
//			errInvoice.put("MEMO", invoiceMess.get("MEMO"));
			errInvoice.put("TARGET_CUST_TYPE", invoiceMess.get("TARGET_CUST_TYPE"));
			if(InvoiceUtil.isNotNull(errInvoice)==false){
				errorMsg += "缺少开票对象所在供应商邮寄地址信息或其他信息有空值；";
				errInvoice.put("ERRMSG", errorMsg);
				flag = false;
			}
			if(invoiceMess.containsKey("TARGET_CUST_TYPE") && invoiceMess.get("TARGET_CUST_TYPE") !=null 
					&& "法人".equals(invoiceMess.get("TARGET_CUST_TYPE").toString()) && invoiceMess.containsKey("TARGET_TAX_QUALIFICATION") 
					&& invoiceMess.get("TARGET_TAX_QUALIFICATION") !=null && "一般纳税人".equals(invoiceMess.get("TARGET_TAX_QUALIFICATION").toString())){
				if(invoiceMess.get("TARGET_ADD_PHONE")== null || "".equals(invoiceMess.get("TARGET_ADD_PHONE").toString().trim())){
		        	errorMsg +="缺少开票对象地址电话信息；";
		        	flag = false;
				}else if(invoiceMess.get("TARGET_TEL")==null){
					errorMsg += "开票对象电话信息不存在；";
		        	flag = false;
				}else if(invoiceMess.get("TARGET_TEL").toString().trim().length()<7){
//			        }else if(!invoiceMess.get("TARGET_TEL").toString().trim().matches("(^(\\d{3,4}-)?\\d{7,8})$|(13[0-9]{9})")){
//			        	System.out.println(invoiceMess.get("TARGET_TEL").toString().trim());
//			        	System.out.println(invoiceMess.get("TARGET_TEL").toString().trim().matches("(^(\\d{3,4}-)?\\d{7,8})$|(13[0-9]{9})"));
		        	errorMsg += "开票对象电话信息不合法；";
		        	flag = false;
		        }
		        if(invoiceMess.get("TARGET_BANK_ACCOUNT")==null || "".equals(invoiceMess.get("TARGET_BANK_ACCOUNT").toString().trim())){
		        	errorMsg += "缺少开票对象开户行  开户账号信息；";
		        	flag = false;
		        }else if(!invoiceMess.get("TARGET_BANK_ACCOUNT").toString().trim().matches("^.+\\s+[-\\w]+$")){
//			        }else if(!invoiceMess.get("TARGET_BANK_ACCOUNT").toString().trim().matches("^[^\\d\\w]+[0-9\\s]+[0-9]+$")){
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
	        	if(!InvoiceUtil.testAmt(com.pqsoft.util.StringUtils.nullToOther(invoiceMess.get("ITEM_SUB_AMT"),"0"))){
	        		errorMsg += "开票金校验研失败；";
	        		flag = false;
	        	}
	        }
			errInvoice.put("REASION",invoiceMess.get("PRO_CODE")+"  [  "+invoiceMess.get("TARGET_NAME")+"  ]  :  "+ errorMsg);
			if(!flag){
				Dao.update(basePath +"updateCheckedErrInfo",errInvoice);
			}
		}
		
		
        
       
		return flag ;
	}
	public Page checkInvoiceData(Map<String, Object> param) {
		Page page = new Page(param);
		Map<String,Object> SUP_MAP = BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"checkInvoiceData",param)), Dao.selectInt(basePath+"checkInvoiceDataCount",param));
		return page;
	}
	
}

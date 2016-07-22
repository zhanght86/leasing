package com.pqsoft.refund.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.StringUtils;

public class RefundDBApplyService {
	private String basePath = "refundSflc.";
	
	/**
	 * 退款列表
	 * @param param
	 * @return
	 */
	public Page getPageData(Map<String,Object> param ){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getRefundDbApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getRefundDbApplyCount", param));
		return page;
	}
	
	/**
	 * 获取可退款数据列表
	 * @param param
	 * @return
	 */
	public List<Object> getCanRefundList(Map<String,Object> param ){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectList(basePath+"getCanRefundList", param);
	}
	
	public Excel getExportDBDetail(Map<String,Object> param){
		String flag = Dao.selectOne(basePath+"getgetdictdatabycode",param);
		Excel excel = new Excel();
		//退款明细
		List<Map<String,Object>> dataDetailList = Dao.selectList(basePath+"getRefundDetailExcel",param);
		LinkedHashMap<String, String> titleDetail = new LinkedHashMap<String, String>();

		titleDetail.put("ID", "序号");
		titleDetail.put("SUP_SHORTNAME", "供应商");
		titleDetail.put("PRO_CODE", "项目编号");
		titleDetail.put("CLIENT_NAME", "客户名称");
		titleDetail.put("COMPANY_NAME", "厂商");
		titleDetail.put("PRODUCT_NAME", "租赁物名称");
//		titleDetail.put("LEASE_TOPRIC", "租赁物购买价款");
		titleDetail.put("TYPE_NAME", "机型");
		titleDetail.put("WHOLE_ENGINE_CODE", "出厂编号");
		titleDetail.put("AMOUNT", "台量");
		titleDetail.put("PRO_END_DATE", "租赁到期日");
		titleDetail.put("REMARK", "结束状态");
		titleDetail.put("TYPE_NAME1", "款项名称");
		titleDetail.put("BASE_MONEY", "实收金额");
		titleDetail.put("RE_MONEY", "保证金金额");
//		titleDetail.put("START_CONFIRM_DATE", "起租确认日");
//		titleDetail.put("PAY_TIME", "预退日期");
		titleDetail.put("RE_PAYEE_UNIT", "收款单位");
		excel.setAutoColumn(20);
		excel.defaultFormat(false);
		excel.putFormateParam("BASE_MONEY", Excel.RMBFORMAT);
		excel.putFormateParam("RE_MONEY", Excel.RMBFORMAT);
		excel.addData(dataDetailList);
		excel.addTitle(titleDetail);
//		excel.hasRownum();
		excel.setHeadDate(true);
		excel.setHeadTitles(DateUtil.getSysDate("yyyyMMdd")+
				StringUtils.nullToOther(flag+"产品", "  产品").replace("板块", "").replace("特殊", "").replace("业务", "").replace("-全部-", "").replace("null", "")+
				StringUtils.nullToOther(param.get("RE_TYPE"), "").replace("--全部--", "").replace("1", "供应商").replace("4", "客户").toString()
				+"保证金退款明细");
		return excel;
	}

	 public Excel getExcelExportRefundData(Map<String,Object> param){
    	Excel excel = new Excel();
    	//退款主表
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"getExportApplyMess", param);
		LinkedHashMap<String, String>  title = new LinkedHashMap<String, String>();
		title.put("SUP_SHORTNAME","供应商");
		title.put("COMPANY_NAME","厂商");
		title.put("RE_PAYEE_NAME","收款单位");
		title.put("RE_PAYEE_BANK","开户行");
		title.put("RE_PAYEE_BANK_ADDR","开户行所在地");
		title.put("RE_PAYEE_ACCOUNT","账号");
		title.put("RE_MONEY","保证金金额");
		excel.addData(dataList);
		excel.addTitle(title);
        excel.hasRownum();
        excel.setHeadDate(true);
		excel.setHeadTitles("保证金退款申请");
		return excel;
    }
	 
	public Page getRefundPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getDbApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getDbApplyCount", param));
		return page;
	}
	
	public Page getPlanAuditPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getPlanAuditList",param));
		page.addDate(array, Dao.selectInt(basePath+"getPlanAuditCount", param));
		return page;
	}
	
	public Page getSearchPageData(Map<String,Object> param){
		Page page = new Page(param);
		Map<String,Object> SUP_MAP = BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getSearchList",param));
		page.addDate(array, Dao.selectInt(basePath+"getSearchCount", param));
		return page;
	}
	
	public int submitMethod(Map<String,Object> param){
		return Dao.update("SupplierBailPool.updateRefundMess", param);
	}
	
	public List<Object> getRefundDropList(Map<String,Object> param){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectList(basePath+"getRefundDropList", param);
	}
	
	public Excel getExportSumMess(Map<String,Object> param){
		String flag = Dao.selectOne(basePath+"getgetdictdatabycode",param);
		Excel excel = new Excel();
		List<Map<String,Object>> listMess =  Dao.selectList(basePath+"getExportDebtMess", param);
		for (Map<String, Object> map : listMess) {
			String company_name = map.containsKey("COMPANY_NAME") && map.get("COMPANY_NAME") !=null ? map.get("COMPANY_NAME").toString() :"";
			String sup_shortname = map.containsKey("SUP_SHORTNAME") && map.get("SUP_SHORTNAME")!=null ? map.get("SUP_SHORTNAME").toString():"";
			if(!"".equals(company_name) && "".equals(sup_shortname)){
				map.put("COMPANY_NAME", company_name+" 汇总");
			}
			if("".equals(company_name) && "".equals(sup_shortname)){
				map.put("COMPANY_NAME", "总计");
			}
		}
		Map<String,Object> memoMap1 = new HashMap<String,Object>();
		Map<String,Object> memoMap2 = new HashMap<String,Object>();
		listMess.add(new HashMap<String,Object>());
		listMess.add(new HashMap<String,Object>());
		List<Map<String,Object>> listMemo =  Dao.selectList(basePath+"getExportJbpmMemo", param);
		for (Map<String, Object> map : listMemo) {
			String TASK_NAME = map.get("TASK_NAME") !=null ? map.get("TASK_NAME").toString() :"";
			String OP_NAME = map.containsKey("OP_NAME") && map.get("OP_NAME")!=null ? map.get("OP_NAME").toString():"";
			String OP_TIME = map.containsKey("OP_TIME") && map.get("OP_TIME")!=null ? map.get("OP_TIME").toString():"";
			
			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("副总审核")> -1){
				memoMap1.put("SUP_SHORTNAME", OP_NAME+" "+OP_TIME);
				continue;
			}
			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("债权审核")> -1){
				memoMap1.put("RE_PAYEE_UNIT", OP_NAME+" "+OP_TIME);
				continue;
			}
			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("部门经理审核")> -1){
				memoMap1.put("RE_PAYEE_BANK_ADDR", OP_NAME+" "+OP_TIME);
				continue;
			}
			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("企划担当")> -1){
				memoMap1.put("RE_MONEY", OP_NAME+" "+OP_TIME);
				continue;
			}

			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("总经理")> -1){
				memoMap2.put("ROW_NUM", "总经理:");
				memoMap2.put("SUP_SHORTNAME", OP_NAME+" "+OP_TIME);
				continue;
			}
			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("财务总监")> -1){
				memoMap2.put("RE_PAYEE_UNIT", OP_NAME+" "+OP_TIME);
				continue;
			}
			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("财务部长")> -1){
				memoMap2.put("RE_PAYEE_BANK_ADDR", OP_NAME+" "+OP_TIME);
				continue;
			}
			if(!"".equals(TASK_NAME) && TASK_NAME.indexOf("财务担当")> -1){
				memoMap2.put("RE_MONEY", OP_NAME+" "+OP_TIME);
			}

			memoMap1.put("ROW_NUM", "副总审核:");
			memoMap1.put("COMPANY_NAME", "债权审核:");
			memoMap1.put("RE_PAYEE_BANK", "校对:");
			memoMap1.put("RE_PAYEE_ACCOUNT", "担当:");
			memoMap2.put("COMPANY_NAME", "财务总监:");
			memoMap2.put("RE_PAYEE_BANK", "财务部长:");
			memoMap2.put("RE_PAYEE_ACCOUNT", "担当:");
		}

		listMess.add(memoMap1);
		listMess.add(memoMap2);
		
		LinkedHashMap<String, String>  title = new LinkedHashMap<String, String>();
		title.put("ROW_NUM","序号");
		title.put("SUP_SHORTNAME","供应商");
		title.put("COMPANY_NAME","厂商");
		title.put("RE_PAYEE_UNIT","收款单位");
		title.put("RE_PAYEE_BANK","开户行");
		title.put("RE_PAYEE_BANK_ADDR","开户行所在地");
		title.put("RE_PAYEE_ACCOUNT","账号");
		title.put("RE_MONEY","保证金金额");
		excel.setAutoColumn(30);
		excel.defaultFormat(false);
//		excel.putFormateParam("RE_MONEY", Excel.RMBFORMAT);
		excel.addData(listMess);
		excel.addTitle(title);
//      excel.hasRownum();
        excel.setHeadDate(true);
		excel.setHeadTitles(DateUtil.getSysDate("yyyyMMdd")+
				StringUtils.nullToOther(flag+"产品", "  产品").replace("板块", "").replace("特殊", "").replace("业务", "").replace("-全部-", "").replace("null", "")+
				StringUtils.nullToOther(param.get("RE_TYPE"), "").replace("--全部--", "").replace("1", "供应商").replace("4", "客户").toString()
				+"保证金退款表");
		return excel;
	}
	
	public List<Object> getPadSinkList(Map<String,Object> param){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectList(basePath+"getPadDropList", param);
	}
	
	public Excel getSearchRefundMess(Map<String,Object> param){
		String flag = Dao.selectOne(basePath+"getgetdictdatabycode",param);
		Excel excel = new Excel();
		//退款主表
		List<Map<String,Object>> listMess =  Dao.selectList(basePath+"getExportDebtMess", param);
		for (Map<String, Object> map : listMess) {
			String company_name = map.containsKey("COMPANY_NAME") && map.get("COMPANY_NAME") !=null ? map.get("COMPANY_NAME").toString() :"";
			String sup_shortname = map.containsKey("SUP_SHORTNAME") && map.get("SUP_SHORTNAME")!=null ? map.get("SUP_SHORTNAME").toString():"";
			if(!"".equals(company_name) && "".equals(sup_shortname)){
				map.put("COMPANY_NAME", company_name+" 汇总");
			}
			if("".equals(company_name) && "".equals(sup_shortname)){
				map.put("COMPANY_NAME", "总计");
			}
		}
		LinkedHashMap<String, String>  title = new LinkedHashMap<String, String>();
		title.put("SUP_SHORTNAME","供应商");
		title.put("COMPANY_NAME","厂商");
		title.put("RE_PAYEE_UNIT","收款单位");
		title.put("RE_PAYEE_BANK","开户行");
		title.put("RE_PAYEE_BANK_ADDR","开户行所在地");
		title.put("RE_PAYEE_ACCOUNT","账号");
		title.put("RE_MONEY","保证金金额");
		excel.setAutoColumn(30);
		excel.defaultFormat(false);
		excel.putFormateParam("RE_MONEY", Excel.RMBFORMAT);
		excel.addData(listMess);
		excel.addTitle(title);
        excel.hasRownum();
        excel.setHeadDate(true);
		excel.setHeadTitles(DateUtil.getSysDate("yyyyMMdd")+
				StringUtils.nullToOther(flag+"产品", "  产品").replace("板块", "").replace("特殊", "").replace("业务", "").replace("-全部-", "").replace("null", "")+
				StringUtils.nullToOther(param.get("RE_TYPE"), "").replace("--全部--", "").replace("1", "供应商").replace("4", "客户").toString()
				+"保证金退款表");
		excel.setHeadTitles("保证金退款主表");
		
		//退款明细
		List<Map<String,Object>> dataDetailList = Dao.selectList(basePath+"getRefundDetailExcel",param);
		LinkedHashMap<String, String> titleDetail = new LinkedHashMap<String, String>();
		titleDetail.put("ID", "序号");
		titleDetail.put("SUP_SHORTNAME", "供应商");
		titleDetail.put("PRO_CODE", "项目编号");
		titleDetail.put("CLIENT_NAME", "客户名称");
		titleDetail.put("COMPANY_NAME", "厂商");
		titleDetail.put("PRODUCT_NAME", "租赁物名称");
		titleDetail.put("TYPE_NAME", "机型");
		titleDetail.put("WHOLE_ENGINE_CODE", "出厂编号");
		titleDetail.put("AMOUNT", "台量");
		titleDetail.put("PRO_END_DATE", "租赁到期日");
		titleDetail.put("REMARK", "结束状态");
		titleDetail.put("TYPE_NAME1", "款项名称");
		titleDetail.put("BASE_MONEY", "实收金额");
		titleDetail.put("RE_MONEY", "保证金金额");
		titleDetail.put("RE_PAYEE_UNIT", "收款单位");
		excel.setAutoColumn(20);
		excel.defaultFormat(false);
		excel.putFormateParam("BASE_MONEY", Excel.RMBFORMAT);
		excel.putFormateParam("RE_MONEY", Excel.RMBFORMAT);
		excel.addData(dataDetailList);
		excel.addTitle(titleDetail);
		excel.setHeadDate(true);
		excel.setHeadTitles(DateUtil.getSysDate("yyyyMMdd")+
				StringUtils.nullToOther(flag+"产品", "  产品").replace("板块", "").replace("特殊", "").replace("业务", "").replace("-全部-", "").replace("null", "")+
				StringUtils.nullToOther(param.get("RE_TYPE"), "").replace("--全部--", "").replace("1", "供应商").replace("4", "客户").toString()
				+"保证金退款明细");
		excel.setHeadTitles("保证金退款明细");
		return excel;
	}
}
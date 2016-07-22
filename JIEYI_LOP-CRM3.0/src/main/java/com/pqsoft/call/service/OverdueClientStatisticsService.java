package com.pqsoft.call.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;

public class OverdueClientStatisticsService {
	
	private String basePath = "overdueClientStatistics.";
	
	public OverdueClientStatisticsService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	public Page getPageDataByKHMC(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAllByKHMC",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCountByKHMC", param));
		return page;
	}
	
	/**
	 *   根据期次,还款计划项目编号查询扣划失败原因
	 */
	public Object selectReasonByFundId(Map<String, Object> param) {
		param.put("TAG", "本金");
		return Dao.selectOne(basePath + "selectReasonByFundId",param);
	}
	
	/**
	 *   插入一条催收记录
	 */
	public int insertRecord(Map<String,Object> param){
		return Dao.insert(basePath+"addRecord",param);
	}
	
	/**
	 *  逾期客户导出excel文件
	 */
	public Excel exportData(Map<String, Object> params) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"exportCust",params);
		//表头		
		String columnString="区域,供应商,分支机构,项目编号,支付表编号,客户名称,台量,租赁物类型,厂商,机型,出厂编号,期次,应收日期,逾期金额,未收违约金,逾期天数,累计逾期期次,联系方式,已还期数,起租确认日期";
		String columnName="QY,DLD,SUB_COM,PROJ_ID,PROJ_FUND_ID,KHMC,EQUIP_AMT,PROD_TYPE,ZZS,MODEL,PRO_NO,RENT_LIST,YS_DATE,OVER_MONEY,OVER_WYJ,OVER_DAY,COUNT_,PHONE,BEGINNING_NUM,QZ_CONFIRM_DATE";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		//封装excel		
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("overdueClient"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);

		return excel;
	}
}

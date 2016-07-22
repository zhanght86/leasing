package com.pqsoft.call.service;
/**
 *  催收台账
 *  @author 韩晓龙
 */
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;

public class UrgeBookService {
	
	private String basePath = "urgeBook.";
	
	public UrgeBookService() {
	}

	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	/**
	 *   根据id查询一条催收记录
	 */
	public Object selectRecordById(Map<String,Object> param){
		return Dao.selectOne(basePath+"selectRecordById",param);
	}
	
	/**
	 *   根据id查询多条短信记录
	 */
	public List selectMessageById(Map<String,Object> param){
		return Dao.selectList(basePath+"selectMessageById",param);
	}
	
	/**
	 *   插入一条短信记录
	 */
	public int insertMessage(Map<String,Object> param){
		return Dao.insert(basePath+"addMessage",param);
	}
	/**
	 *   根据id删除一条短信记录
	 */
	public int deleteMessageById(Map<String,Object> param){
		return Dao.delete(basePath+"deleteMessage",param);
	}
	
	/**
	 *  催收记录导出excel文件
	 */
	public Excel exportData(Map<String, Object> params) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"exportMessage",params);
		//表头		
		String columnString="区域,供应商,分支机构,项目编号,期次,客户名称,催收时间,催收担当,逾期原因,催收计划,客户承诺,扣款结果,催收无果措施,催收日志,备注";
		String columnName="QY,DLD,SUB_COM,PROJ_ID,RENT_LIST,KHMC,CALL_TIME,CALLER,YQ_REASON,CS_PLAN,CUST_COMMITMENT,KK_RESULT,FAIL_MEASURE,CS_LOG,REMARK";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		//封装excle		
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("urgeBook"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		

		return excel;
	}
	
}

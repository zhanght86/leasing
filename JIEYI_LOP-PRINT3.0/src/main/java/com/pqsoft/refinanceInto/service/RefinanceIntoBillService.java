package com.pqsoft.refinanceInto.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class RefinanceIntoBillService {
    private String basePath = "refinanceIntoBill.";
    
    public Page getIntoBillPageData(Map<String,Object> param){
	param.put("DIC_LEGA", "发票认证状态");
	Page page = new Page(param);
	JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getRefinIntoBillList",param));
	page.addDate(array, Dao.selectInt(basePath+"getRefinIntoBillCount", param));
	return page;
    }
    
    public int addIntoBill(Map<String,Object> param){
	return Dao.insert(basePath+"addIntoBill", param);
    }
    
    public int updateIntoBill(Map<String,Object> param){
	return Dao.update(basePath+"updateIntoBill", param);
    }
    
    public int delIntoBill(Map<String,Object> param){
	return Dao.delete(basePath+"delIntoBill", param);
    }
    
    public Map<String,Object> showIntoBaillDetail(Map<String,Object> param ){
	return Dao.selectOne(basePath+"getOneIntoBill", param);
    }
    
    /**
      * 导出进项发票信息
      * @param param
      * @return
      */
    public Excel getExcelInvoices(Map<String,Object> param){
	param.put("DIC_LEGA", "发票认证状态");
 	List<Map<String,Object>>  pageList = Dao.selectList(basePath+"getALlIntoBill", param);
 	//表头
 	LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
 	title.put("INVOICE_CODE", "发票号码");
 	title.put("INVOICE_DATE", "发票日期");
 	title.put("INVOICE_AMOUNT", "价税合计");
 	title.put("INVOICE_TAX", "税额");
 	title.put("DRAWER", "开票人");
 	title.put("PRO_CODE", "项目名称");
 	title.put("LEGA_STATUS", "发票认证状态");
 	title.put("LEGA_TIME", "发票认证日期");
 	//封装excel
 	Excel excel = new Excel();
 	excel.addData(pageList);
 	excel.addTitle(title);
 	excel.setHeadTitles("发票登记表");
 	excel.setHeadDate(true);
 	excel.hasRownum();
 	return excel;
    }

}

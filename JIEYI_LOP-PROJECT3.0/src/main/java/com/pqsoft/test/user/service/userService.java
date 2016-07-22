package com.pqsoft.test.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import net.sf.json.JSONArray;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class userService {
	private String path="user.";
	
	public int addUser(Map<String,Object> param){
		return Dao.insert(path+"addUser",param);
	}
	
	public List<Map<String,Object>> findByUser(Map<String,Object> param){
		return Dao.selectList(path+"findByUser", null);
	}
	
	public Page doShowUserConfgForPage(Map<String, Object> map) {
		return PageUtil.getPage(map, path + "doShowUserConfigForPage", path + "doShowUserConfigForPageCount");
	}

	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(path+"doShowUserConfigForPage",param));
		page.addDate(array, Dao.selectInt(path+"doShowUserConfigForPageCount", param));
		return page;
	}

	public int doUpdateUSERConfig(Map<String, Object> param) {
		return Dao.insert(path+"doUpdateUserConfig", param);
	}

	public int doDelUserConfig(Map<String, Object> param) {
		return Dao.delete(path+"doDelUserConfig", param);
	}

	public HSSFWorkbook getDataList(Map<String, Object> param) {
		List<Map<String,Object>> dataList = Dao.selectList(path+"getDataList", param);
		String[] excelHeader = { "ID", "用户名称 ", "用户密码 ", "职业", "年龄 ", "备注"};   
		int[] excelHeaderWidth = { 100, 150, 150, 180, 100, 200};  
		    HSSFWorkbook wb = new HSSFWorkbook();    
		    HSSFSheet sheet = wb.createSheet("征信报告");    
		    HSSFRow row = sheet.createRow((int) 0);    
		    HSSFCellStyle style = wb.createCellStyle();    
		    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    Font font = wb.createFont();  
		    font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体 
		    font.setFontName("黑体");
		    font.setFontHeightInPoints((short) 10);//设置字体大小
		    style.setFont(font);
		    for (int i = 0; i < excelHeader.length; i++) {    
		         HSSFCell cell = row.createCell(i);    
		         cell.setCellValue(excelHeader[i]);    
		         cell.setCellStyle(style);    
		         sheet.autoSizeColumn(i);
		         sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]); 
		    }    
		    for (int i = 0; i < dataList.size(); i++) {   
		    	row = sheet.createRow(i + 1);    
		        Map<String,Object> map = dataList.get(i); 
		        if(map!=null){
		        	if(map.get("ID")!=null&&!"".equals(map.get("ID").toString())){
		        		 row.createCell(0).setCellValue(map.get("ID").toString());    
		        	 }
		        	if(map.get("USERNAME")!=null&&!"".equals(map.get("USERNAME").toString())){
		        		 row.createCell(1).setCellValue(map.get("USERNAME").toString());    
		        	 }
		        	if(map.get("PASSWORD")!=null&&!"".equals(map.get("PASSWORD").toString())){
		        		 row.createCell(2).setCellValue(map.get("PASSWORD").toString());    
		        	 }
		        	if(map.get("JOB")!=null&&!"".equals(map.get("JOB").toString())){
		        		 row.createCell(3).setCellValue(map.get("JOB").toString());    
		        	 }
		        	if(map.get("AGE")!=null&&!"".equals(map.get("AGE").toString())){
		        		 row.createCell(4).setCellValue(map.get("AGE").toString());    
		        	 }
		        	if(map.get("RECORD")!=null&&!"".equals(map.get("RECORD").toString())){
		        		 row.createCell(5).setCellValue(map.get("RECORD").toString());    
		        	 }
		        }
		    }
		return wb;
	}

	public List<Map<String, Object>> sendQyInfoDc(String[] sqlDataL,
			String sqlData) {
		for(int i=0;i<sqlDataL.length;i++){
			String ID=sqlDataL[i];
			if(ID.length()>0){
				Map map=Dao.selectOne("user.queryUserIs", ID);
				if(map ==null){//没有数据则新增一条
					Map mapWYQY=new HashMap();
					mapWYQY.put("ID", ID);
					Dao.insert("user.insertUserDc",mapWYQY);
				}
			}
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(sqlDataL.length>0 && sqlData.length()>0){
			Map mapda=new HashMap();
			mapda.put("SQLDATA", sqlData);
			dataList=Dao.selectList("user.queryUserListDc",mapda);
		//	Dao.update("user.updateAGEGEADLINEDc",mapda);
			
		}
		return dataList;
	}

	public Excel getExportApplyExcelDc(List<Map<String, Object>> dataList) {
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("ID", "用户id");
		title.put("USERNAME", "用户名称");
		title.put("PASSWORD", "用户密码");
		title.put("JOB", "职业");
		title.put("AGE", "年龄");
		title.put("RECORD", "备注");
		//封装excel
		Excel excel = new Excel();
		excel.addData(dataList);
		excel.addTitle(title);
		excel.setAutoColumn(25);
		return excel;
	}

}

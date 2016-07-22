package com.pqsoft.project.service;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.filter.ResMime;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.AcrobatENG;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;

public class ContractService {

	
	
	
	public Page queryPage(Map<String, Object> m) {
		m.put("tags1", "业务类型");
		m.put("tags2", "项目状态位");
		m.put("tags3", "客户类型");
		m.put("ORD_ID", Security.getUser().getOrg().getId());
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			m.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "contract.getAllProject", "contract.getAllProject_count");
	}
	
	public List queryContractTemplateList(Map<String, Object> m){
		return Dao.selectList("contract.queryContractTemplateList",m);
	}
	
	public Map queryContractTemplateMap(Map<String, Object> m){
		return Dao.selectOne("contract.queryContractTemplateMap",m);
	}
	
	public List<Map<String, Object>> queryContractTemplateList1(Map<String, Object> m){
		return Dao.selectList("contract.queryContractTemplateList1",m);
	}
	
	public Map baseInfoMap(Map<String, Object> m){
		return Dao.selectOne("contract.baseInfoMap",m);
	}
	
	public List<Map<String, Object>> gethistoryContract(Map<String, Object> m)
	{
		return Dao.selectList("contract.gethistoryContract", m);
	}
	public Map<String,Object> projectBaseInfo(Map<String,Object> map){
		return (Map<String, Object>) Dao.selectOne("contract.baseInfoByIDMap", map);
	}
	
	/**
	 * 导出生成合同pdf文件
	 * 
	 * @param TPM_ID
	 *            (文件类型id(t_pdftemplate_maintenance表ID))
	 * @param map
	 *            表单sql所需参数
	 * @param inputPath
	 *            pdf模板文件路径
	 * @param fileName
	 *            导出生成的文件名
	 * @author:King 2013-10-25
	 */
	public String ExpContractFile(String TPM_ID, Map<String, Object> map, String inputPath, String fileName) {
		// 解析读取填充表单数据
//		map.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		System.out.println("------------------------------map="+map);
		HashMap<String, Object> mm = AcrobatENG.getFills(TPM_ID, map);
		
		// 将原来的小写金额转换为大写金额
		HashMap<String, Object> rstMap = UtilFinance.numToUpCase(mm);
		HashMap<String, Object> rstMap1 = (HashMap<String, Object>) map;
		String root = SkyEye.getConfig("file.path");
		if (fileName.contains(".pdf") || fileName.contains(".PDF")) {

		} else {
			fileName = fileName + ".pdf";
		}
		String outputPath = root + File.separator +map.get("PROJECT_ID")+File.separator+ fileName;
		try {
			AcrobatENG.mergeAcrobat(inputPath, rstMap1, outputPath);
		} catch (Exception e) {
			throw new ActionException("导出错误" + e.getMessage(),e);
		}
		return outputPath;
	}
	
	public Map queryPayListByNotEq(Map map){
		return Dao.selectOne("contract.queryPayListByNotEq", map);
	}
	
	public List queryPayListByQZINGEq(Map map){
		return Dao.selectList("contract.queryPayListByQZINGEq", map);
	}
	
	public List queryPayListQZByEq(Map map){
//		return Dao.selectList("contract.queryPayListQZByEq", map);
		return Dao.selectList("contract.queryPayListQZByEqNew", map);
	}
	
	public List queryDXMPayList(Map map){
		return Dao.selectList("contract.queryDXMPayList", map);
	}
	
	public List queryPayNotBuyCreate(Map map){
		return Dao.selectList("contract.queryPayNotBuyCreate", map);
	}
	
	public List queryPayListBuy(Map map){
		return Dao.selectList("contract.queryPayListBuy", map);
	}

	//还款明细导出
	public void uploadPayDetail(Map<String, Object> param){
		//查询项目基本信息
		Map mapInfo=Dao.selectOne("contract.queryProjectByLeaseCode", param);
		String LEASE_CODE=mapInfo.get("PRO_CODE")+"";
		projectService proService=new projectService();
		List rowlist = proService.queryEqCountByProjectID(mapInfo);
		OutputStream os;
		try{
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_NONE);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_NONE);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_NONE);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_NONE);
			head_cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CNLEFT = xssf_w_book.createCellStyle();

			cellStyle_CNLEFT.setAlignment(CellStyle.ALIGN_LEFT);// 水平居左
			cellStyle_CNLEFT.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CNLEFT.setBorderBottom(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNLEFT.setBorderLeft(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNLEFT.setBorderRight(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNLEFT.setBorderTop(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNLEFT.setDataFormat(format.getFormat("@"));
			
			// 普通文本
			XSSFCellStyle cellStyle_CNRIGHT = xssf_w_book.createCellStyle();

			cellStyle_CNRIGHT.setAlignment(CellStyle.ALIGN_RIGHT);// 水平居右
			cellStyle_CNRIGHT.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CNRIGHT.setBorderBottom(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNRIGHT.setBorderLeft(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNRIGHT.setBorderRight(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNRIGHT.setBorderTop(XSSFCellStyle.BORDER_NONE);
			cellStyle_CNRIGHT.setDataFormat(format.getFormat("@"));
			
			
			// 普通文本红色
			XSSFCellStyle cellStyle_red = xssf_w_book.createCellStyle();

			cellStyle_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setDataFormat(format.getFormat("@"));
			
			XSSFFont red_font = xssf_w_book.createFont();
			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_NONE);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_NONE);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_NONE);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_NONE);
			cellStyle_blue.setDataFormat(format.getFormat("@"));
			
			XSSFFont blue_font = xssf_w_book.createFont();
			blue_font.setColor((short)30F);
			blue_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_blue.setFont(blue_font);
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_NONE);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_NONE);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_NONE);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_NONE);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setBorderBottom(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_red.setBorderLeft(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_red.setBorderRight(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_red.setBorderTop(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_redRIGHT = xssf_w_book.createCellStyle();
			cellStyle_num_redRIGHT.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_redRIGHT.setAlignment(CellStyle.ALIGN_RIGHT);// 水平居中
			cellStyle_num_redRIGHT.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_redRIGHT.setBorderBottom(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_redRIGHT.setBorderLeft(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_redRIGHT.setBorderRight(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_redRIGHT.setBorderTop(XSSFCellStyle.BORDER_NONE);
			cellStyle_num_redRIGHT.setDataFormat(format.getFormat("#,##0.######"));
			
			for(int i=0;i<rowlist.size();i++){
				Map rowMap=(Map)rowlist.get(i);
				XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(LEASE_CODE+"还款明细"+(i+1));
				XSSFRow xssf_w_row = null;// 创建一行
				XSSFCell xssf_w_cell = null;// 创建每个单元格
				int row_count = 0;
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,11));// 第一行
				xssf_w_row = xssf_w_sheet.createRow(row_count);
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("基本信息");
				xssf_w_cell.setCellStyle(head_cellStyle);
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("项目编号：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(LEASE_CODE);
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("业务类型：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object PLATFORM_NAME = mapInfo.get("PLATFORM_NAME") == null ? "" : mapInfo.get("PLATFORM_NAME");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(PLATFORM_NAME.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("行业类型：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object INDUSTRY_NAME = mapInfo.get("INDUSTRY_NAME") == null ? "" : mapInfo.get("INDUSTRY_NAME");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(INDUSTRY_NAME.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第3行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("客户名称：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object CUST_NAME = mapInfo.get("CUST_NAME") == null ? "" : mapInfo.get("CUST_NAME");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第3行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(CUST_NAME.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第3行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("客户类型：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object CUST_TYPE = mapInfo.get("CUST_TYPE") == null ? "" : mapInfo.get("CUST_TYPE");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第3行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(CUST_TYPE.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第3行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("区域：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object PROVINCE_NAME = mapInfo.get("PROVINCE_NAME") == null ? "" : mapInfo.get("PROVINCE_NAME");
				Object CITY_NAME = mapInfo.get("CITY_NAME") == null ? "" : mapInfo.get("CITY_NAME");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第3行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(PROVINCE_NAME.toString()+"-"+CITY_NAME);
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				row_count++;
				
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,11));// 第一行
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("设备信息");
				xssf_w_cell.setCellStyle(head_cellStyle);
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第4行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,0));// 第4行
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("厂商");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,1,1));// 第4行
				xssf_w_cell = xssf_w_row.createCell(1);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("供应商");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,2));// 第4行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("设备名称");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,3,3));// 第4行
				xssf_w_cell = xssf_w_row.createCell(3);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("设备系列");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,4));// 第4行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("型号");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,5,5));// 第4行
				xssf_w_cell = xssf_w_row.createCell(5);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("出厂编号");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,6));// 第4行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("出厂日期");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,7,7));// 第4行
				xssf_w_cell = xssf_w_row.createCell(7);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("发动机编号");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,8));// 第4行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("车架号");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,9,9));// 第4行
				xssf_w_cell = xssf_w_row.createCell(9);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("留购价");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,10));// 第4行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("指导价");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,11,11));// 第4行
				xssf_w_cell = xssf_w_row.createCell(11);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("数量");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				row_count++;
				
				double unit_price_all=0d;
				List eqList=proService.queryEqByProjectIDByScheme(rowMap);//查询方案设备
				for(int h=0;h<eqList.size();h++){
					Map eqMap=(Map)eqList.get(h);
					xssf_w_row = xssf_w_sheet.createRow(row_count);// 第5行
					
					Object COMPANY_NAME = eqMap.get("COMPANY_NAME") == null ? "" : eqMap.get("COMPANY_NAME");//厂商
					Object SUPPLIERS_NAME = eqMap.get("SUPPLIERS_NAME") == null ? "" : eqMap.get("SUPPLIERS_NAME");//供应商
					Object PRODUCT_NAME = eqMap.get("PRODUCT_NAME") == null ? "" : eqMap.get("PRODUCT_NAME");//设备名称
					Object CATENA_NAME = eqMap.get("CATENA_NAME") == null ? "" : eqMap.get("CATENA_NAME");//设备系列
					Object SPEC_NAME = eqMap.get("SPEC_NAME") == null ? "" : eqMap.get("SPEC_NAME");//设备型号
					Object WHOLE_ENGINE_CODE = eqMap.get("WHOLE_ENGINE_CODE") == null ? "" : eqMap.get("WHOLE_ENGINE_CODE");//出厂编号
					Object CERTIFICATE_DATE = eqMap.get("CERTIFICATE_DATE") == null ? "" : eqMap.get("CERTIFICATE_DATE");//出厂日期
					Object ENGINE_TYPE = eqMap.get("ENGINE_TYPE") == null ? "" : eqMap.get("ENGINE_TYPE");//发动机编号
					Object CAR_SYMBOL = eqMap.get("CAR_SYMBOL") == null ? "" : eqMap.get("CAR_SYMBOL");//车架号
					Object STAYBUY_PRICE = eqMap.get("STAYBUY_PRICE") == null ? "0" : eqMap.get("STAYBUY_PRICE");//留购价
					Object UNIT_PRICE = eqMap.get("UNIT_PRICE") == null ? "0" : eqMap.get("UNIT_PRICE");//单价
					unit_price_all+=((java.math.BigDecimal) UNIT_PRICE).doubleValue();
					Object AMOUNT = eqMap.get("AMOUNT") == null ? "" : eqMap.get("AMOUNT");//数量
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,0));// 第5行
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(COMPANY_NAME.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,1,1));// 第5行
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(SUPPLIERS_NAME.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,2));// 第5行
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(PRODUCT_NAME.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,3,3));// 第5行
					xssf_w_cell = xssf_w_row.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(CATENA_NAME.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,4));// 第5行
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(SPEC_NAME.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,5,5));// 第5行
					xssf_w_cell = xssf_w_row.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(WHOLE_ENGINE_CODE.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,6));// 第5行
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(CERTIFICATE_DATE.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,7,7));// 第5行
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(ENGINE_TYPE.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,8));// 第5行
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(CAR_SYMBOL.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,9,9));// 第5行
					xssf_w_cell = xssf_w_row.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(STAYBUY_PRICE+"");
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,10));// 第5行
					xssf_w_cell = xssf_w_row.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) UNIT_PRICE).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,11,11));// 第5行
					xssf_w_cell = xssf_w_row.createCell(11);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(AMOUNT.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					row_count++;
				}
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 新加一行
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,9));
				xssf_w_cell = xssf_w_row.createCell(11);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,10));
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(unit_price_all);
				xssf_w_cell.setCellStyle(cellStyle_num_red);
				
				row_count++;
				
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,11));// 第一行
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("方案信息");
				xssf_w_cell.setCellStyle(head_cellStyle);
				row_count++;
				
				//方案
				Map schemMap=Dao.selectOne("contract.querySchemeInfo", rowMap);
				Map schemClob=Dao.selectOne("contract.querySchemeInfoClob", rowMap);
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("期限：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object LEASE_TERM=schemMap.get("LEASE_TERM") == null ? "" : schemMap.get("LEASE_TERM");
				Object PERIOD_NAME=schemMap.get("PERIOD_NAME") == null ? "" : schemMap.get("PERIOD_NAME");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(LEASE_TERM+" "+PERIOD_NAME);
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("支付方式：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object PAY_WAY_NAME = schemMap.get("PAY_WAY_NAME") == null ? "" : schemMap.get("PAY_WAY_NAME");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(PAY_WAY_NAME.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("保证金处理方式：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object POUNDAGE_WAY_NAME = schemMap.get("POUNDAGE_WAY_NAME") == null ? "" : schemMap.get("POUNDAGE_WAY_NAME");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(POUNDAGE_WAY_NAME.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,12,12));// 第二行
				xssf_w_cell = xssf_w_row.createCell(12);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("是否含税：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object ISTAX = schemClob.get("ISTAX") == null ? "不含税" : schemClob.get("ISTAX");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,13,13));// 第二行
				xssf_w_cell = xssf_w_row.createCell(13);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(ISTAX.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,14,14));// 第二行
				xssf_w_cell = xssf_w_row.createCell(14);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("差额利息处理方式：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object SFCELX = schemClob.get("SFCELX") == null ? "首期差额利息" : schemClob.get("SFCELX");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,15,15));// 第二行
				xssf_w_cell = xssf_w_row.createCell(15);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(SFCELX.toString());
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("年利率：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object YEAR_INTEREST=schemMap.get("YEAR_INTEREST") == null ? "" : schemMap.get("YEAR_INTEREST");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(YEAR_INTEREST+" %");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("租金收益率：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object INTERNAL_RATE = schemMap.get("INTERNAL_RATE") == null ? "" : schemMap.get("INTERNAL_RATE");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(INTERNAL_RATE.toString()+" %");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("首期付款日期：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object FIRSTPAYDATE = schemMap.get("FIRSTPAYDATE") == null ? "" : schemMap.get("FIRSTPAYDATE");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(FIRSTPAYDATE.toString()+"");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,12,12));// 第二行
				xssf_w_cell = xssf_w_row.createCell(12);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("保险费收取方式：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object INSURANCE_TYPE = schemClob.get("INSURANCE_TYPE") == null ? "" : schemClob.get("INSURANCE_TYPE");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,13,13));// 第二行
				xssf_w_cell = xssf_w_row.createCell(13);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(INSURANCE_TYPE.toString()+"");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,14,14));// 第二行
				xssf_w_cell = xssf_w_row.createCell(14);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("保险费：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object INSURANCE_MONEY=schemClob.get("INSURANCE_MONEY") == null ? "" : schemClob.get("INSURANCE_MONEY");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,15,15));// 第二行
				xssf_w_cell = xssf_w_row.createCell(15);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(INSURANCE_MONEY+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("首期租金比例：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object START_PERCENT=schemMap.get("START_PERCENT") == null ? "" : schemMap.get("START_PERCENT");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(START_PERCENT+" %");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("客户保证金比例：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object BAIL_PERCENT = schemMap.get("BAIL_PERCENT") == null ? "" : schemMap.get("BAIL_PERCENT");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(BAIL_PERCENT.toString()+" %");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("手续费比例：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object POUNDAGE_RATE = schemMap.get("POUNDAGE_RATE") == null ? "" : schemMap.get("POUNDAGE_RATE");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(POUNDAGE_RATE.toString()+" %");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,12,12));// 第二行
				xssf_w_cell = xssf_w_row.createCell(12);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("留购价：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object LG_MONEY = schemClob.get("LG_MONEY") == null ? "" : schemClob.get("LG_MONEY");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,13,13));// 第二行
				xssf_w_cell = xssf_w_row.createCell(13);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(LG_MONEY.toString());
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,14,14));// 第二行
				xssf_w_cell = xssf_w_row.createCell(14);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("GPS服务费：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object GPS_MONEY = schemClob.get("GPS_MONEY") == null ? "" : schemClob.get("GPS_MONEY");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,15,15));// 第二行
				xssf_w_cell = xssf_w_row.createCell(15);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(GPS_MONEY.toString());
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("贴息金额：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object DISCOUNT_MONEY=schemMap.get("DISCOUNT_MONEY") == null ? "" : schemMap.get("DISCOUNT_MONEY");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(DISCOUNT_MONEY+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("固定利息：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object GDLX = schemMap.get("GDLX") == null ? "" : schemMap.get("GDLX");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(GDLX.toString()+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("首期款合计：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object FIRSTPAYALL = schemMap.get("FIRSTPAYALL") == null ? "" : schemMap.get("FIRSTPAYALL");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(FIRSTPAYALL.toString()+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,12,12));// 第二行
				xssf_w_cell = xssf_w_row.createCell(12);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("管理费：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object GLF_PRICE = schemClob.get("GLF_PRICE") == null ? "" : schemClob.get("GLF_PRICE");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,13,13));// 第二行
				xssf_w_cell = xssf_w_row.createCell(13);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(GLF_PRICE.toString()+"");
				xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,14,14));// 第二行
				xssf_w_cell = xssf_w_row.createCell(14);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("其他费用：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object QTFY_MONEY=schemClob.get("QTFY_MONEY") == null ? "" : schemClob.get("QTFY_MONEY");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,15,15));// 第二行
				xssf_w_cell = xssf_w_row.createCell(15);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(QTFY_MONEY+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("租赁物总价值：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object LEASE_TOPRIC=schemMap.get("LEASE_TOPRIC") == null ? "" : schemMap.get("LEASE_TOPRIC");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(LEASE_TOPRIC+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("融资额：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object FINANCE_TOPRIC = schemMap.get("FINANCE_TOPRIC") == null ? "" : schemMap.get("FINANCE_TOPRIC");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(FINANCE_TOPRIC.toString()+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("租金总额：");
				xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
				
				Object TOTAL_MONTH_PRICE = schemMap.get("TOTAL_MONTH_PRICE") == null ? "" : schemMap.get("TOTAL_MONTH_PRICE");
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(TOTAL_MONTH_PRICE.toString()+"");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				row_count++;
				
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,11));// 第一行
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("还款明细");
				xssf_w_cell.setCellStyle(head_cellStyle);
				row_count++;
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("支付时间");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(2);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("期次");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("租金");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("本金");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("利息");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("利息增值税");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,12,13));// 第二行
				xssf_w_cell = xssf_w_row.createCell(12);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("手续费");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,14,15));// 第二行
				xssf_w_cell = xssf_w_row.createCell(14);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("管理费");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,16,17));// 第二行
				xssf_w_cell = xssf_w_row.createCell(16);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("剩余本金");
				xssf_w_cell.setCellStyle(cellStyle_blue);
				row_count++;
				
				PayTaskService pay = new PayTaskService();
				int PAY_ID = proService.queryIdByProjectIdRowNum(rowMap);
				Map<String, Object> payMap = new HashMap<String, Object>();
				payMap.put("ID", PAY_ID);
				List detailList=pay.doShowRentPlanDetailScheme(payMap);
				double zjAll=0d;
				double bjAll=0d;
				double lxAll=0d;
				
				double lxzzsAll=0d;
				double sxfAll=0d;
				double glfAll=0d;
				
				double sybjAll=0d;
				
				for(int s=0;s<detailList.size();s++){
					Map detailMap=(Map)detailList.get(s);
					Object PAY_DATE=detailMap.get("PAY_DATE") == null ? "" : detailMap.get("PAY_DATE");//支付时间
					Object PERIOD_NUM=detailMap.get("PERIOD_NUM") == null ? "" : detailMap.get("PERIOD_NUM");//期次
					Object ZJ=detailMap.get("ZJ") == null ? "" : detailMap.get("ZJ");//租金
					Object BJ=detailMap.get("BJ") == null ? "" : detailMap.get("BJ");//本金
					Object LX=detailMap.get("LX") == null ? "" : detailMap.get("LX");//利息
					
					Object LXZZS=detailMap.get("LXZZS") == null ? "" : detailMap.get("LXZZS");//利息增值税
					Object SXF=detailMap.get("SXF") == null ? "" : detailMap.get("SXF");//手续费
					Object GLF=detailMap.get("GLF") == null ? "" : detailMap.get("GLF");//管理费
					
					
					Object SYBJ=detailMap.get("SYBJ") == null ? "" : detailMap.get("SYBJ");//剩余本金
					
					zjAll+=((java.math.BigDecimal) ZJ).doubleValue();
					bjAll+=((java.math.BigDecimal) BJ).doubleValue();
					lxAll+=((java.math.BigDecimal) LX).doubleValue();
					
					lxzzsAll+=((java.math.BigDecimal) LXZZS).doubleValue();
					sxfAll+=((java.math.BigDecimal) SXF).doubleValue();
					glfAll+=((java.math.BigDecimal) GLF).doubleValue();
					
					
					xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,1));
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(PAY_DATE.toString());
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,2,3));// 第二行
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(PERIOD_NUM+"");
					xssf_w_cell.setCellStyle(cellStyle_CNLEFT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) ZJ).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) BJ).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LX).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
					xssf_w_cell = xssf_w_row.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LXZZS).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,12,13));// 第二行
					xssf_w_cell = xssf_w_row.createCell(12);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) SXF).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,14,15));// 第二行
					xssf_w_cell = xssf_w_row.createCell(14);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) GLF).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
					
					xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,16,17));// 第二行
					xssf_w_cell = xssf_w_row.createCell(16);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) SYBJ).doubleValue());
					xssf_w_cell.setCellStyle(cellStyle_CNRIGHT);
					row_count++;
				}
				
				xssf_w_row = xssf_w_sheet.createRow(row_count);// 第二行
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,0,3));// 第二行
				xssf_w_cell = xssf_w_row.createCell(0);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("合计");
				xssf_w_cell.setCellStyle(cellStyle_num_red);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,4,5));// 第二行
				xssf_w_cell = xssf_w_row.createCell(4);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(zjAll);
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,6,7));// 第二行
				xssf_w_cell = xssf_w_row.createCell(6);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(bjAll);
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,8,9));// 第二行
				xssf_w_cell = xssf_w_row.createCell(8);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(lxAll);
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,10,11));// 第二行
				xssf_w_cell = xssf_w_row.createCell(10);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(lxzzsAll);
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,12,13));// 第二行
				xssf_w_cell = xssf_w_row.createCell(12);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(sxfAll);
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,14,15));// 第二行
				xssf_w_cell = xssf_w_row.createCell(14);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(glfAll);
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(row_count,row_count,16,17));// 第二行
				xssf_w_cell = xssf_w_row.createCell(16);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue("");
				xssf_w_cell.setCellStyle(cellStyle_num_redRIGHT);
				row_count++;
			}
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String((LEASE_CODE+"还款明细"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public List<Map<String,Object>> queryInvoiceAppList(Map<String, Object> map) {
		
		return Dao.selectList("contract.queryInvoiceAppList", map);
	}

	public List<Map<String,Object>> queryPayNotInvoiceAppCreate(Map<String, Object> map) {
		
		return Dao.selectList("contract.queryPayNotInvoiceAppCreate", map);
	}
	public Map<String, Object> getElectronicSignature(Map<String, Object> map)
	{
		return Dao.selectOne("contract.getElectronicSignature",map);
	}
	
	/**
	 * add by lishuo 23.11.2015
	 * 查询合同版本号
	 * @param mm
	 * @return
	 */
	public Map<String,Object> getContractVersion(Map<String,Object> mm){
		return Dao.selectOne("contract.getContractVersion",mm);
	}
	public int changeVersionNum(Map<String, Object> map){
		return Dao.insert("contract.changeVersionNum",map);
	}
	
	public List<Map<String,Object>> queryOrgDataById(Map<String, Object> map)
	{
		return Dao.selectList("contract.getOrgDataById",map);
	}
	public Map<String,Object> queryOrgDataByIdMb(Map<String, Object> map)
	{
		return Dao.selectOne("contract.getOrgDataByIdMb",map);
	}
	//add JZZL-151 gengchangbao 2016年4月12日 Start
	public List queryContractSealInfoList(Map<String, Object> param){
		return Dao.selectList("contract.selectContractSealInfoList", param);
	}
	//add JZZL-151 gengchangbao 2016年4月12日 End
}

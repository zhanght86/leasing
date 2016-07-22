package com.pqsoft.bpm.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pqsoft.payment.service.paymentService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.filter.ResMime;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;

/**
 * 流程表单服务管理
 * 
 * @author King 2013-10-26
 */
public class JbpmFiService {

	/**
	 * 查询付款单明细
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-26
	 */
	public List<Map<String, Object>> toEqDetailPaymentGroupPaymentHeadId(String PAYMENT_JBPM_ID) {
		Map<String, Object> m = new HashMap<String, Object>();
		String NotInvoice = "1,8";// 不需要发票的业务类型在这个地方已逗号隔开
		m.put("NotInvoice", NotInvoice);
		m.put("PAYMENT_JBPM_ID", PAYMENT_JBPM_ID);
		return Dao.selectList("payment.pay_Detail_Eq_MgNew", m);

	}
	//设备放款管理页
	public Page pay_Head_Query_Eq_Mg(Map<String,Object> m)
	{
		Page page = new Page(m);
		List<Map<String, Object>> list = Dao.selectList("payment.pay_Detail_Eq_MgNew", m);
		list.add((Map<String, Object>)Dao.selectOne("payment.pay_Detail_Eq_Sum", m));
		page.addDate(JSONArray.fromObject(list), 10);
		return page;
	}
	
	//设备放款管理页
	public Page pay_Head_Query_Eq_Mg_Have_Footer(Map<String,Object> m)
	{
		Page page = new Page(m);
		List<Map<String, Object>> rows = Dao.selectList("payment.pay_Detail_Eq_MgNew", m);
		Object footer = Dao.selectOne("payment.pay_Detail_Eq_Sum", m) ;
		page.addDate(JSONArray.fromObject(rows), 10,JSONArray.fromObject(footer));
		return page;
	}
	
	/**
	 * 租赁物放款抵扣租金并放剩余款项
	 * @param jbpmId
	 * @author:King 2013-10-27
	 */
	public void Deductible(String jbpmId){
		
	}
	
	//设备放款明细管理页
	public List pay_Detail_Query_Eq_Date(Map<String,Object> m)
	{
		List<Map<String, Object>> list = Dao.selectList("payment.pay_Detail_Query_Eq_Date", m);
		list.add((Map<String, Object>)Dao.selectOne("payment.pay_Detail_Query_Eq_Sum", m));
		return list;
	}
	
	public int updatePayMent(Map map){
		int LIMIT_BOND_SUM = 0;
		String LIMIT_BOND = map.get("LIMIT_BOND")+"";
		if(LIMIT_BOND != "" && !"".equals(LIMIT_BOND+"")){
			LIMIT_BOND_SUM = Integer.parseInt(LIMIT_BOND);
			//实收额度保证金 金额大于0
			if(LIMIT_BOND_SUM >0){
				Dao.delete("payment.deleteCreditAccount",map);
				List<Map<String, Object>> list = Dao.selectList("payment.queryCreditLimitBySupid", map);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> smap= list.get(i);
					smap.put("PAY_ID", map.get("ID"));
					smap.put("LIMIT_BOND_SUM", LIMIT_BOND_SUM);
					int ks_amp =  Integer.parseInt(smap.get("KS_AMP")+"");//供应商单条额度保证金可收金额
					Dao.insert("payment.insertCreditAccount",smap);
					LIMIT_BOND_SUM -= ks_amp;
					if(LIMIT_BOND_SUM <= 0){
						break;
					}
				}
			}
		}
		
		
		return Dao.update("payment.updatePayMentDiKou",map);
	}
	
	public int updatePayMentHP(Map map){
		return Dao.update("payment.updatePayMentHP",map);
	}
	public int doUpdtDISCRETIONARY(Map map){
		return Dao.update("payment.doUpdtDISCRETIONARY",map);
	}
	public boolean pay_Head_Eq_Del_Project(Map<String, Object> param) {
		boolean flag = false ; 
		//1.修改放款明细
		flag = Dao.update("payment.updatePayDetail_proCode",param) > 0 ? true:false;
		//2.修改放款主表金额
		Dao.update("payment.updatePayHead_headID",param);
		//3.查询放款单下是否还有项目
		int proNum = Dao.selectInt("payment.queryProjectNumByHeadId", param);
		if(proNum == 0){
			//4.删除付款单
			Dao.delete("payment.deletePayHead_ID",param);
		}
		return flag;
	}
	
	public boolean pay_Head_Eq_Del_Supper(Map<String, Object> param) {
		//1.删除额度保证金记录
		Dao.delete("payment.delCreditAccountHeadById",param);
		//2.修改放款明细
		return Dao.update("payment.updatePaymentHeadById",param) > 0 ? true:false;
		
	}
	

	public void pay_Head_Excle_Head_Mg(Map<String, Object> param) {
		OutputStream os;
		try {

			
			String[] titlesName = new String[]{"序号","供应商",	"厂商",	"收款单位",	"开户行行名",	"开户行所在地",	"帐号",	"付款金额"};
			String[] titlesName2 = new String[]{"序号","供应商",	"厂商",	"收款单位",	"开户行行名",	"开户行所在地",	"帐号",	"付款金额",	"付款比例",	"实际电汇金额" ,"承兑汇票"};
			String[] titlesName3 = new String[]{"序号","供应商",	"厂商",	"收款单位",	"开户行行名",	"开户行所在地",	"帐号",	"应付租赁物价款金额",	"扣款比例",	"扣款金额"};
			
			
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("租赁物购买价款支付总表");
			XSSFSheet xssf_w_sheet2 = xssf_w_book.createSheet("租赁物购买价款支付电汇明细表");
			XSSFSheet xssf_w_sheet3 = xssf_w_book.createSheet("租赁物购买价款支付扣款明细表");
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFRow xssf_w_row2 = null;// 创建一行
			XSSFRow xssf_w_row3 = null;// 创建一行
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			int col_count = 0;
			int col_count2 = 0;
			int col_count3 = 0;
			int row_count = 0;
			int row_count2 = 0;
			int row_count3 = 0;
			col_count = titlesName.length;
			col_count2 = titlesName2.length;
			col_count3 = titlesName3.length;
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
//			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));
			
			// 创建合计行样式
			XSSFCellStyle heji_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont heji_font = xssf_w_book.createFont();
			heji_font.setFontName("宋体");// 设置头部字体为宋体
			heji_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			heji_font.setFontHeightInPoints((short) 10);
//			heji_font.setColor(XSSFFont.COLOR_RED);
			

			heji_cellStyle.setFont(heji_font);// 单元格样式使用字体
			heji_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			heji_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			heji_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
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
//			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
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
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			int titleRows2 = 0; // 标题占据的行数
			int titleRows3 = 0; // 标题占据的行数
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet.setColumnWidth(i, 5500);
				}
				

			}
			xssf_w_row2 = xssf_w_sheet2.createRow(0 + titleRows2);// 第一行写入标题行
			for (int i = 0; i < col_count2; i++) {
				xssf_w_cell = xssf_w_row2.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName2[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet2.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet2.setColumnWidth(i, 5500);
				}

			}
			xssf_w_row3 = xssf_w_sheet3.createRow(0 + titleRows3);// 第一行写入标题行
			for (int i = 0; i < col_count3; i++) {
				xssf_w_cell = xssf_w_row3.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName3[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet3.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet3.setColumnWidth(i, 5500);
				}

			}
			
			double fkhj = 0.0;
			double sfhj = 0.0;
			double cdhp = 0.0;
			
			double kkyf = 0.0;
			double kkhj = 0.0;
			
			int jianNum = 0;
			double kkys_sum = 0.0;
			++titleRows;++titleRows2;++titleRows3;
			
			List COMPLIST=new ArrayList();
			if(param.get("COMP_ID") == null || "".equals(param.get("COMP_ID")+"")){
				COMPLIST=Dao.selectList("payment.pay_ComListAll");
			}
			else{
				COMPLIST=Dao.selectList("payment.pay_ComListByParam",param);
			}
			
			for (int i = 0; i < COMPLIST.size(); i++) {
				Map mapCom=(Map)COMPLIST.get(i);
				param.put("COMP_ID", mapCom.get("COMP_ID"));
				List<Map<String,Object>> dataList = pay_Head_Excle_Eq_Mg(param);
				
				row_count = dataList.size();                           
			
				Map<String, Object> rowMap = new HashMap<String, Object>();
				for (int j = 0; j < row_count; j++) {
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					rowMap = dataList.get(row_count != dataList.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_NAME = rowMap.get("PAY_BANK_NAME") == null ? "" : rowMap.get("PAY_BANK_NAME");
					Object PAY_BANK_ADDRESS = rowMap.get("PAY_BANK_ADDRESS") == null ? "" : rowMap.get("PAY_BANK_ADDRESS");
					Object PAYMENT_MONEY = rowMap.get("PAYMENT_MONEY") == null ? "" : rowMap.get("PAYMENT_MONEY");
					Object DEDUCTION_RATE = rowMap.get("DEDUCTION_RATE") == null ? "" : rowMap.get("DEDUCTION_RATE");
					Object PAYMENT_RATE = rowMap.get("PAYMENT_RATE") == null ? "" : rowMap.get("PAYMENT_RATE");
					Object DEDUCTION_MONEY = rowMap.get("DEDUCTION_MONEY") == null ? "" : rowMap.get("DEDUCTION_MONEY");
					Object ACCBILL_PRICE = rowMap.get("ACCBILL_PRICE") == null ? "" : rowMap.get("ACCBILL_PRICE");
					Object LAST_MONEY = rowMap.get("LAST_MONEY") == null ? "" : rowMap.get("LAST_MONEY");
					Object BANKBILL_PRICE = rowMap.get("BANKBILL_PRICE") == null ? "" : rowMap.get("BANKBILL_PRICE");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
	
					
					
					//  第一个sheet
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows-jianNum);
	
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMP_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
					
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY).doubleValue());
					
					//  第二个sheet
					
					xssf_w_cell = xssf_w_row2.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows2-jianNum);
	
					
					xssf_w_cell = xssf_w_row2.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMP_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY).doubleValue());
	
					xssf_w_cell = xssf_w_row2.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(PAYMENT_RATE.toString());
	
					xssf_w_cell = xssf_w_row2.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) BANKBILL_PRICE).doubleValue());
					
					xssf_w_cell = xssf_w_row2.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) ACCBILL_PRICE).doubleValue());
					
					 
					if(Double.parseDouble((DEDUCTION_MONEY+"") == ""?"0":DEDUCTION_MONEY.toString()) > 0){
						xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					//  第三个sheet
						xssf_w_cell = xssf_w_row3.createCell(0);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(titleRows3-jianNum);
						
						xssf_w_cell = xssf_w_row3.createCell(1);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(SUPPER_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(2);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(COMP_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(3);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAYEE_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(4);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(5);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(6);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(7);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY).doubleValue());
						
						kkys_sum += ((java.math.BigDecimal) PAYMENT_MONEY).doubleValue();
	
						xssf_w_cell = xssf_w_row3.createCell(8);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(DEDUCTION_RATE.toString());
	
						xssf_w_cell = xssf_w_row3.createCell(9);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(((java.math.BigDecimal) DEDUCTION_MONEY).doubleValue());
						++titleRows3;
					}
					
					

					++titleRows;++titleRows2;
				}

				Map<String,Object> sumMap = pay_Head_Excle_Eq_Sum(param);
				
				if(sumMap!=null){
					
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					Object PAYMENT_MONEY_SUM = sumMap.get("PAYMENT_MONEY") == null ? "" : sumMap.get("PAYMENT_MONEY");
					Object DEDUCTION_MONEY_SUM = sumMap.get("DEDUCTION_MONEY") == null ? "" : sumMap.get("DEDUCTION_MONEY");
					Object LAST_MONEY_SUM = sumMap.get("BANKBILL_PRICE") == null ? "" : sumMap.get("BANKBILL_PRICE");
					Object ACCBILL_PRICE_SUM = sumMap.get("ACCBILL_PRICE") == null ? "" : sumMap.get("ACCBILL_PRICE");
					
					fkhj += ((java.math.BigDecimal) PAYMENT_MONEY_SUM).doubleValue();
					sfhj += ((java.math.BigDecimal) LAST_MONEY_SUM).doubleValue();
					cdhp += ((java.math.BigDecimal) ACCBILL_PRICE_SUM).doubleValue();
					kkhj += ((java.math.BigDecimal) DEDUCTION_MONEY_SUM).doubleValue();
					kkyf += kkys_sum;
					
					Object COMP_NAME1 = sumMap.get("COMP_NAME") == null ? "" : sumMap.get("COMP_NAME");
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(COMP_NAME1.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY_SUM).doubleValue());
					
	
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(COMP_NAME1.toString()+" 汇总");

					xssf_w_cell = xssf_w_row2.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY_SUM).doubleValue());
					
					xssf_w_cell = xssf_w_row2.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LAST_MONEY_SUM).doubleValue());

					xssf_w_cell = xssf_w_row2.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) ACCBILL_PRICE_SUM).doubleValue());
					
					
					xssf_w_cell = xssf_w_row3.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(COMP_NAME1.toString()+" 汇总");

					xssf_w_cell = xssf_w_row3.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(kkys_sum);
					
					xssf_w_cell = xssf_w_row3.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) DEDUCTION_MONEY_SUM).doubleValue());
					
					++jianNum;++titleRows3;
					++titleRows;++titleRows2;
					kkys_sum = 0.0 ;
					 
				}
			}
			
			// 合计  *********begin************
			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
			
			xssf_w_cell = xssf_w_row.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(fkhj);
			
			xssf_w_cell = xssf_w_row2.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");
			
			xssf_w_cell = xssf_w_row2.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(fkhj);
			
			xssf_w_cell = xssf_w_row2.createCell(9);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(sfhj);
			
			xssf_w_cell = xssf_w_row2.createCell(10);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(cdhp);
			
			xssf_w_cell = xssf_w_row3.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");
			
			xssf_w_cell = xssf_w_row3.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(kkyf);
			
			xssf_w_cell = xssf_w_row3.createCell(9);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(kkhj);
			
			// 合计  *********end************
			
			// 审批意见  *********begin************
			List<Map<String, Object>> memoList = Dao.selectList("payment.queryPaymentJbpmMemo",param);
			
			++titleRows3;++titleRows;++titleRows2;
			++titleRows3;++titleRows;++titleRows2;
			++titleRows3;++titleRows;++titleRows2;

			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
			
			String TASK_NAME = "";
			String OP_NAME = "";
			String OP_TIME = "";

			xssf_w_cell = xssf_w_row.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  副总审批：  ");
			
			xssf_w_cell = xssf_w_row.createCell(3);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  债权确认：  ");
			

			xssf_w_cell = xssf_w_row.createCell(5);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  校对：  ");

			xssf_w_cell = xssf_w_row.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  担当：  ");


			xssf_w_cell = xssf_w_row2.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  副总审批：  ");
			
			xssf_w_cell = xssf_w_row2.createCell(3);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  债权确认：  ");
			

			xssf_w_cell = xssf_w_row2.createCell(5);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  校对：  ");

			xssf_w_cell = xssf_w_row2.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  担当：  ");

			xssf_w_cell = xssf_w_row3.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  副总审批：  ");
			
			xssf_w_cell = xssf_w_row3.createCell(3);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  债权确认：  ");
			

			xssf_w_cell = xssf_w_row3.createCell(5);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  校对：  ");

			xssf_w_cell = xssf_w_row3.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  担当：  ");


			for (int i = 0; i < memoList.size(); i++) {
				Map<String, Object> memoMap = memoList.get(i);

				TASK_NAME = memoMap.get("TASK_NAME")+"";
				OP_NAME = memoMap.get("OP_NAME")+"";
				OP_TIME = memoMap.get("OP_TIME")+"";
				if(i == 0){
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(memoMap.get("APP_NAME")+" "+memoMap.get("APP_TIME"));
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("副总审批")> -1){
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("债权付款条件审核")> -1){
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("部门经理审核")> -1){
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}

				if(i == 0){
					xssf_w_cell = xssf_w_row2.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(memoMap.get("APP_NAME")+" "+memoMap.get("APP_TIME"));
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("副总审批")> -1){
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("债权付款条件审核")> -1){
					xssf_w_cell = xssf_w_row2.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("部门经理审核")> -1){
					xssf_w_cell = xssf_w_row2.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				

				if(i == 0){
					xssf_w_cell = xssf_w_row3.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(memoMap.get("APP_NAME")+" "+memoMap.get("APP_TIME"));
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("副总审批")> -1){
					xssf_w_cell = xssf_w_row3.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("债权付款条件审核")> -1){
					xssf_w_cell = xssf_w_row3.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("部门经理审核")> -1){
					xssf_w_cell = xssf_w_row3.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				
				
			}
			

			++titleRows3;++titleRows;++titleRows2;

			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);

			
			
			xssf_w_cell = xssf_w_row.createCell(3);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务总监：  ");
			
			xssf_w_cell = xssf_w_row.createCell(5);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务部长：  ");
			
			xssf_w_cell = xssf_w_row.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务担当：  ");


			xssf_w_cell = xssf_w_row2.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  总经理：  ");
			
			xssf_w_cell = xssf_w_row2.createCell(3);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务总监：  ");
			
			xssf_w_cell = xssf_w_row2.createCell(5);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务部长：  ");
			
			xssf_w_cell = xssf_w_row2.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务担当：  ");
			

			xssf_w_cell = xssf_w_row3.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  总经理：  ");
			
			xssf_w_cell = xssf_w_row3.createCell(3);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务总监：  ");
			
			xssf_w_cell = xssf_w_row3.createCell(5);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务部长：  ");
			
			xssf_w_cell = xssf_w_row3.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(heji_cellStyle);						
			xssf_w_cell.setCellValue("  财务担当：  ");
			
			for (int i = 0; i < memoList.size(); i++) {
				Map<String, Object> memoMap = memoList.get(i);

				TASK_NAME = memoMap.get("TASK_NAME")+"";
				OP_NAME = memoMap.get("OP_NAME")+"";
				OP_TIME = memoMap.get("OP_TIME")+"";
				

				if(TASK_NAME != "" && TASK_NAME.indexOf("总经理审批")> -1){
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue("  总经理：  ");
					
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
					xssf_w_cell = xssf_w_row2.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue("  总经理：  ");
					
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
					xssf_w_cell = xssf_w_row3.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue("  总经理：  ");
					
					xssf_w_cell = xssf_w_row3.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("财务总监审批")> -1){
					
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
				}

				if(TASK_NAME != "" && TASK_NAME.indexOf("财务部长审核")> -1){
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("财务担当审核")> -1){
					
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
				}
				

				if(TASK_NAME != "" && TASK_NAME.indexOf("财务总监审批")> -1){
					
					xssf_w_cell = xssf_w_row2.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
				}

				if(TASK_NAME != "" && TASK_NAME.indexOf("财务部长审核")> -1){
					xssf_w_cell = xssf_w_row2.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("财务担当审核")> -1){
					
					xssf_w_cell = xssf_w_row2.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
				}

				if(TASK_NAME != "" && TASK_NAME.indexOf("财务总监审批")> -1){
					
					xssf_w_cell = xssf_w_row3.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
				}

				if(TASK_NAME != "" && TASK_NAME.indexOf("财务部长审核")> -1){
					xssf_w_cell = xssf_w_row3.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
					
				}
				if(TASK_NAME != "" && TASK_NAME.indexOf("财务担当审核")> -1){
					
					xssf_w_cell = xssf_w_row3.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(heji_cellStyle);						
					xssf_w_cell.setCellValue(OP_NAME + " " + OP_TIME);
				}
				
			}
			
			// 审批意见  *********end************
			
			
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(("租赁物购买价款支付表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	public void pay_Detail_Excle_Mg(Map<String, Object> param) {
		OutputStream os;
		try {

			//全额放款--------放款额=设备总额
			//差额放款--------放款额=设备总额-首期款-DB保证金-其他费用(如果是期初还要减去第一期租金)
			//部分差额放款----放款额=设备总额-留购价款-起租租金
			//DB差额放款-----DB差额放款=设备总额-DB保证金
			//全额
			String[] titlesName = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","实际放款金额","款项名称","预付日期","收款单位","账号"};
			//差额
			String[] titlesName2 = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","首期款金额","DB保证金金额","厂商保证金","其他费用","实际放款金额","款项名称","预付日期","收款单位","账号"};
			//DB保证金差额
			String[] titlesName3 = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","DB保证金金额","实际放款金额","款项名称","预付日期","收款单位","账号"};
			//部分差额放款
			String[] titlesName4 = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","起租租金","留购价款","实际放款金额","款项名称","预付日期","收款单位","账号"};
			
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("租赁物购买价款全额放款支付明细");
			XSSFSheet xssf_w_sheet2 = xssf_w_book.createSheet("租赁物购买价款差额放款支付明细");
			XSSFSheet xssf_w_sheet3 = xssf_w_book.createSheet("租赁物购买价款DB保证金差额放款支付明细");
			XSSFSheet xssf_w_sheet4 = xssf_w_book.createSheet("租赁物购买价款部分差额放款支付明细");
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFRow xssf_w_row2 = null;// 创建一行
			XSSFRow xssf_w_row3 = null;// 创建一行
			XSSFRow xssf_w_row4 = null;// 创建一行
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			XSSFCell xssf_w_cell2 = null;// 创建每个单元格
			XSSFCell xssf_w_cell3 = null;// 创建每个单元格
			XSSFCell xssf_w_cell4 = null;// 创建每个单元格
			int col_count = 0;
			int col_count2 = 0;
			int col_count3 = 0;
			int col_count4 = 0;
			int row_count = 0;
			int row_count2 = 0;
			int row_count3 = 0;
			int row_count4 = 0;
			col_count = titlesName.length;
			col_count2 = titlesName2.length;
			col_count3 = titlesName3.length;
			col_count4 = titlesName4.length;
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
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
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
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
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			int titleRows2 = 0; // 标题占据的行数
			int titleRows3 = 0; // 标题占据的行数
			int titleRows4 = 0; // 标题占据的行数
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet.setColumnWidth(i, 5500);
				}
				

			}
			xssf_w_row2 = xssf_w_sheet2.createRow(0 + titleRows2);// 第一行写入标题行
			for (int i = 0; i < col_count2; i++) {
				xssf_w_cell2 = xssf_w_row2.createCell((short) i);			
				xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell2.setCellValue(titlesName2[i]);
				xssf_w_cell2.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet2.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet2.setColumnWidth(i, 5500);
				}

			}
			xssf_w_row3 = xssf_w_sheet3.createRow(0 + titleRows3);// 第一行写入标题行
			for (int i = 0; i < col_count3; i++) {
				xssf_w_cell3 = xssf_w_row3.createCell((short) i);			
				xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell3.setCellValue(titlesName3[i]);
				xssf_w_cell3.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet3.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet3.setColumnWidth(i, 5500);
				}

			}
			
			xssf_w_row4 = xssf_w_sheet4.createRow(0 + titleRows4);// 第一行写入标题行
			for (int i = 0; i < col_count4; i++) {
				xssf_w_cell4 = xssf_w_row4.createCell((short) i);			
				xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell4.setCellValue(titlesName4[i]);
				xssf_w_cell4.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet4.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet4.setColumnWidth(i, 5500);
				}

			}
			//全额放款--------放款额=设备总额
			double LEASE_TOPRIC_JIS = 0.0;
			double PAY_MONEY_JIS = 0.0;
			int AMOUNT_JIS=0;
			
			//差额放款--------放款额=设备总额-首期款-DB保证金-其他费用(如果是期初还要减去第一期租金)
			double LEASE_TOPRIC_JIS2 = 0.0;
			double SQ_PRICE_JIS2 = 0.0;
			double DB_PRICE_JIS2 = 0.0;
			double FIRST_RENT_JIS2 = 0.0;
			double QTFYF_JIS2 = 0.0;
			double PAY_MONEY_JIS2 = 0.0;
			double CS_PRICEALL_JIS2=0.0;
			int AMOUNT_JIS2=0;
			
			//DB差额放款-----DB差额放款=设备总额-DB保证金
			double LEASE_TOPRIC_JIS3 = 0.0;
			double DB_PRICE_JIS3 = 0.0;
			double PAY_MONEY_JIS3 = 0.0;
			int AMOUNT_JIS3=0;
			
			//部分差额放款----放款额=设备总额-留购价款-起租租金
			double LEASE_TOPRIC_JIS4 = 0.0;
			double LGJK_JIS4 = 0.0;
			double QZZJ_JIS4 = 0.0;
			double PAY_MONEY_JIS4 = 0.0;
			int AMOUNT_JIS4=0;
			
			int jianNum = 0;
			int jianNum2 = 0;
			int jianNum3 = 0;
			int jianNum4 = 0;
			++titleRows;++titleRows2;++titleRows3;++titleRows4;
			List SUPPLIST=new ArrayList();

			// 供应商厂商数据权限
			Map SUP_MAP=BaseUtil.getSup();
			if(SUP_MAP!=null){
				param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
			}
			Map COM_MAP=BaseUtil.getCom();
			if(COM_MAP!=null){
				param.put("COM_ID",COM_MAP.get("COMPANY_ID"));
			}

			paymentService paymentService = new paymentService();
			
			SUPPLIST=Dao.selectList("payment.pay_SupList",param);
			
			for (int i = 0; i < SUPPLIST.size(); i++) {
				Map mapCom=(Map)SUPPLIST.get(i);
				if(mapCom == null ){
					continue;
				}
				param.put("SUP_ID", mapCom.get("SUPPER_ID"));
				Map<String, Object> rowMap = new HashMap<String, Object>();
				
				List<Map<String,Object>> dataList = paymentService.pay_Detail_Excle_Eq_Mg(param);
				row_count = dataList.size();
				for (int j = 0; j < row_count; j++) {
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					rowMap = dataList.get(row_count != dataList.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					
					
					//  第一个sheet
					//供应商
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows-jianNum);
					
					//供应商
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString());
	
					//项目编号
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PROJECT_CODE.toString());
					
					//客户名称
					xssf_w_cell = xssf_w_row.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(CUST_NAME.toString());
					
					//厂商
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMP_NAME.toString());
					
					//租赁物名称
					xssf_w_cell = xssf_w_row.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PRODUCT_NAME.toString());
					
					//台量
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(AMOUNT.toString());
					
					//起租确认日
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(START_DATE.toString());
					
					//租赁物购买价款
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
					
					//实际放款金额
					xssf_w_cell = xssf_w_row.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
					
					//款项名称
					xssf_w_cell = xssf_w_row.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_NAME.toString());
					
					//预付日期
					xssf_w_cell = xssf_w_row.createCell(11);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(RESERVE_DATE.toString());
					
					//收款单位
					xssf_w_cell = xssf_w_row.createCell(12);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					//账号
					xssf_w_cell = xssf_w_row.createCell(13);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					++titleRows;
				}
				
				List<Map<String,Object>> dataList2 = paymentService.pay_Detail_Excle_Eq_Mg2(param);
				row_count2 = dataList2.size();
				for (int j = 0; j < row_count2; j++) {
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					rowMap = dataList2.get(row_count2 != dataList2.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					Object SQ_PRICE = rowMap.get("SQ_PRICE") == null ? "" : rowMap.get("SQ_PRICE");
					Object DB_PRICE = rowMap.get("DB_PRICE") == null ? "" : rowMap.get("DB_PRICE");
					Object FIRST_RENT = rowMap.get("FIRST_RENT") == null ? "" : rowMap.get("FIRST_RENT");
					Object QTFYF = rowMap.get("QTFYF") == null ? "" : rowMap.get("QTFYF");
					Object CS_PRICE = rowMap.get("CS_PRICE") == null ? "" : rowMap.get("CS_PRICE");
	
					
					
					//  第二个sheet
					xssf_w_cell2 = xssf_w_row2.createCell(0);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(titleRows2-jianNum2);
	
					
					//供应商
					xssf_w_cell2 = xssf_w_row2.createCell(1);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(SUPPER_NAME.toString());
	
					//项目编号
					xssf_w_cell2 = xssf_w_row2.createCell(2);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PROJECT_CODE.toString());
					
					//客户名称
					xssf_w_cell2 = xssf_w_row2.createCell(3);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(CUST_NAME.toString());
					
					//厂商
					xssf_w_cell2 = xssf_w_row2.createCell(4);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(COMP_NAME.toString());
					
					//租赁物名称
					xssf_w_cell2 = xssf_w_row2.createCell(5);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PRODUCT_NAME.toString());
					
					//台量
					xssf_w_cell2 = xssf_w_row2.createCell(6);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(AMOUNT.toString());
					
					//起租确认日
					xssf_w_cell2 = xssf_w_row2.createCell(7);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(START_DATE.toString());
					
					//租赁物购买价款
					xssf_w_cell2 = xssf_w_row2.createCell(8);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
					
					//首付款
					xssf_w_cell2 = xssf_w_row2.createCell(9);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) SQ_PRICE).doubleValue());
					
					//DB保证金
					xssf_w_cell2 = xssf_w_row2.createCell(10);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) DB_PRICE).doubleValue());
					
					//厂商保证金
					xssf_w_cell2 = xssf_w_row2.createCell(11);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) CS_PRICE).doubleValue());
					
					//其他费用
					xssf_w_cell2 = xssf_w_row2.createCell(12);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) QTFYF).doubleValue());
					
					//实际放款金额
					xssf_w_cell2 = xssf_w_row2.createCell(13);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
					
					//款项名称
					xssf_w_cell2 = xssf_w_row2.createCell(14);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PAY_NAME.toString());
					
					//预付日期
					xssf_w_cell2 = xssf_w_row2.createCell(15);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(RESERVE_DATE.toString());
					
					//收款单位
					xssf_w_cell2 = xssf_w_row2.createCell(16);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PAYEE_NAME.toString());
					
					//账号
					xssf_w_cell2 = xssf_w_row2.createCell(17);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					++titleRows2;
				}
				
				
				
				List<Map<String,Object>> dataList3 = paymentService.pay_Detail_Excle_Eq_Mg3(param);
				row_count3 = dataList3.size();
				for (int j = 0; j < row_count3; j++) {
					rowMap = dataList3.get(row_count3 != dataList3.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					Object DB_PRICE = rowMap.get("DB_PRICE") == null ? "" : rowMap.get("DB_PRICE");
					
					
						xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					//  第三个sheet
						xssf_w_cell3 = xssf_w_row3.createCell(0);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(titleRows3-jianNum3);
						
						//供应商
						xssf_w_cell3 = xssf_w_row3.createCell(1);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(SUPPER_NAME.toString());
		
						//项目编号
						xssf_w_cell3 = xssf_w_row3.createCell(2);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PROJECT_CODE.toString());
						
						//客户名称
						xssf_w_cell3 = xssf_w_row3.createCell(3);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(CUST_NAME.toString());
						
						//厂商
						xssf_w_cell3 = xssf_w_row3.createCell(4);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(COMP_NAME.toString());
						
						//租赁物名称
						xssf_w_cell3 = xssf_w_row3.createCell(5);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PRODUCT_NAME.toString());
						
						//台量
						xssf_w_cell3 = xssf_w_row3.createCell(6);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(AMOUNT.toString());
						
						//起租确认日
						xssf_w_cell3 = xssf_w_row3.createCell(7);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(START_DATE.toString());
						
						//租赁物购买价款
						xssf_w_cell3 = xssf_w_row3.createCell(8);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell3.setCellStyle(cellStyle_num);
						xssf_w_cell3.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
						
						//DB保证金
						xssf_w_cell3 = xssf_w_row3.createCell(9);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell3.setCellStyle(cellStyle_num);
						xssf_w_cell3.setCellValue(((java.math.BigDecimal) DB_PRICE).doubleValue());
						
						//实际放款金额
						xssf_w_cell3 = xssf_w_row3.createCell(10);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell3.setCellStyle(cellStyle_num);
						xssf_w_cell3.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
						
						//款项名称
						xssf_w_cell3 = xssf_w_row3.createCell(11);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PAY_NAME.toString());
						
						//预付日期
						xssf_w_cell3 = xssf_w_row3.createCell(12);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(RESERVE_DATE.toString());
						
						//收款单位
						xssf_w_cell3 = xssf_w_row3.createCell(13);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PAYEE_NAME.toString());
						
						//账号
						xssf_w_cell3 = xssf_w_row3.createCell(14);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PAY_BANK_ACCOUNT.toString());

					++titleRows3;
				}
				
				
				
				List<Map<String,Object>> dataList4 = paymentService.pay_Detail_Excle_Eq_Mg4(param);
				row_count4 = dataList4.size();
				for (int j = 0; j < row_count4; j++) {
					rowMap = dataList4.get(row_count4 != dataList4.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					Object QZZJ = rowMap.get("QZZJ") == null ? "" : rowMap.get("QZZJ");
					Object LGJK = rowMap.get("LGJK") == null ? "" : rowMap.get("LGJK");
					
					
					xssf_w_row4 = xssf_w_sheet4.createRow(titleRows4);
					//  第四个sheet
						xssf_w_cell4 = xssf_w_row4.createCell(0);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(titleRows4-jianNum4);
		
						
						//供应商
						xssf_w_cell4 = xssf_w_row4.createCell(1);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(SUPPER_NAME.toString());
		
						//项目编号
						xssf_w_cell4 = xssf_w_row4.createCell(2);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PROJECT_CODE.toString());
						
						//客户名称
						xssf_w_cell4 = xssf_w_row4.createCell(3);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(CUST_NAME.toString());
						
						//厂商
						xssf_w_cell4 = xssf_w_row4.createCell(4);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(COMP_NAME.toString());
						
						//租赁物名称
						xssf_w_cell4 = xssf_w_row4.createCell(5);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PRODUCT_NAME.toString());
						
						//台量
						xssf_w_cell4 = xssf_w_row4.createCell(6);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(AMOUNT.toString());
						
						//起租确认日
						xssf_w_cell4 = xssf_w_row4.createCell(7);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(START_DATE.toString());
						
						//租赁物购买价款
						xssf_w_cell4 = xssf_w_row4.createCell(8);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
						
						//起租租金
						xssf_w_cell4 = xssf_w_row4.createCell(9);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) QZZJ).doubleValue());
						
						//留购价款
						xssf_w_cell4 = xssf_w_row4.createCell(10);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) LGJK).doubleValue());
						
						//实际放款金额
						xssf_w_cell4 = xssf_w_row4.createCell(11);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
						
						//款项名称
						xssf_w_cell4 = xssf_w_row4.createCell(12);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PAY_NAME.toString());
						
						//预付日期
						xssf_w_cell4 = xssf_w_row4.createCell(13);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(RESERVE_DATE.toString());
						
						//收款单位
						xssf_w_cell4 = xssf_w_row4.createCell(14);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PAYEE_NAME.toString());
						
						//账号
						xssf_w_cell4 = xssf_w_row4.createCell(15);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PAY_BANK_ACCOUNT.toString());
					

					++titleRows4;
				}
				

				Map<String,Object> sumMap = paymentService.pay_Detail_Excle_Eq_Sum(param);
				
				if(sumMap!=null){
					
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					Object LEASE_TOPRICALL = sumMap.get("LEASE_TOPRICALL") == null ? "" : sumMap.get("LEASE_TOPRICALL");
					Object PAY_MONEYALL = sumMap.get("PAY_MONEYALL") == null ? "" : sumMap.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap.get("AMOUNTALL") == null ? "" : sumMap.get("AMOUNTALL");
				   
					
					LEASE_TOPRIC_JIS += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					PAY_MONEY_JIS += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS+= ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					
					
					Object SUPPER_NAME = sumMap.get("SUPPER_NAME") == null ? "" : sumMap.get("SUPPER_NAME");
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell = xssf_w_row.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum;
					++titleRows;
				}
				
				
				Map<String,Object> sumMap2 = paymentService.pay_Detail_Excle_Eq_Sum2(param);
				if(sumMap2!=null){
					
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					Object LEASE_TOPRICALL = sumMap2.get("LEASE_TOPRICALL") == null ? "" : sumMap2.get("LEASE_TOPRICALL");
					Object SQ_PRICEALL = sumMap2.get("SQ_PRICEALL") == null ? "" : sumMap2.get("SQ_PRICEALL");
					Object DB_PRICEALL = sumMap2.get("DB_PRICEALL") == null ? "" : sumMap2.get("DB_PRICEALL");
					Object FIRST_RENTALL = sumMap2.get("FIRST_RENTALL") == null ? "" : sumMap2.get("FIRST_RENTALL");
					Object QTFYFALL = sumMap2.get("QTFYFALL") == null ? "" : sumMap2.get("QTFYFALL");
					Object CS_PRICEALL = sumMap2.get("CS_PRICEALL") == null ? "" : sumMap2.get("CS_PRICEALL");
					Object PAY_MONEYALL = sumMap2.get("PAY_MONEYALL") == null ? "" : sumMap2.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap2.get("AMOUNTALL") == null ? "" : sumMap2.get("AMOUNTALL");
				   
					Object SUPPER_NAME = sumMap2.get("SUPPER_NAME") == null ? "" : sumMap2.get("SUPPER_NAME");
					
					LEASE_TOPRIC_JIS2 += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					SQ_PRICE_JIS2 += ((java.math.BigDecimal) SQ_PRICEALL).doubleValue();
					DB_PRICE_JIS2 += ((java.math.BigDecimal) DB_PRICEALL).doubleValue();
					FIRST_RENT_JIS2 += ((java.math.BigDecimal) FIRST_RENTALL).doubleValue();
					QTFYF_JIS2 += ((java.math.BigDecimal) QTFYFALL).doubleValue();
					PAY_MONEY_JIS2 += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS2+= ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					CS_PRICEALL_JIS2+= ((java.math.BigDecimal) CS_PRICEALL).doubleValue();
					
					
	
					xssf_w_cell2 = xssf_w_row2.createCell(1);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(head_cellStyle);						
					xssf_w_cell2.setCellValue(SUPPER_NAME.toString()+" 汇总");

					xssf_w_cell2 = xssf_w_row2.createCell(6);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell2 = xssf_w_row2.createCell(8);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(9);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) SQ_PRICEALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(10);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) DB_PRICEALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(11);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) CS_PRICEALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(12);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) QTFYFALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(13);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum2;
					++titleRows2;
				}
				
				Map<String,Object> sumMap3 = paymentService.pay_Detail_Excle_Eq_Sum3(param);
				
				if(sumMap3!=null){
					
					xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					Object LEASE_TOPRICALL = sumMap3.get("LEASE_TOPRICALL") == null ? "" : sumMap3.get("LEASE_TOPRICALL");
					Object DB_PRICEALL = sumMap3.get("DB_PRICEALL") == null ? "" : sumMap3.get("DB_PRICEALL");
					Object PAY_MONEYALL = sumMap3.get("PAY_MONEYALL") == null ? "" : sumMap3.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap3.get("AMOUNTALL") == null ? "" : sumMap3.get("AMOUNTALL");
				   
					Object SUPPER_NAME = sumMap3.get("SUPPER_NAME") == null ? "" : sumMap3.get("SUPPER_NAME");
					
					LEASE_TOPRIC_JIS3 += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					DB_PRICE_JIS3 += ((java.math.BigDecimal) DB_PRICEALL).doubleValue();
					PAY_MONEY_JIS3 += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS3+= ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					
					xssf_w_cell3 = xssf_w_row3.createCell(1);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell3.setCellStyle(head_cellStyle);						
					xssf_w_cell3.setCellValue(SUPPER_NAME.toString()+" 汇总");

					xssf_w_cell3 = xssf_w_row3.createCell(6);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell3 = xssf_w_row2.createCell(8);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell3 = xssf_w_row2.createCell(9);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(((java.math.BigDecimal) DB_PRICEALL).doubleValue());
					
					xssf_w_cell3 = xssf_w_row2.createCell(10);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum3;++titleRows3;
				}
				
				Map<String,Object> sumMap4 = paymentService.pay_Detail_Excle_Eq_Sum4(param);
				
				if(sumMap4!=null){
					xssf_w_row4 = xssf_w_sheet4.createRow(titleRows4);
					Object LEASE_TOPRICALL = sumMap4.get("LEASE_TOPRICALL") == null ? "" : sumMap4.get("LEASE_TOPRICALL");
					Object QZZJALL = sumMap4.get("QZZJALL") == null ? "" : sumMap4.get("QZZJALL");
					Object LGJKALL = sumMap4.get("LGJKALL") == null ? "" : sumMap4.get("LGJKALL");
					Object PAY_MONEYALL = sumMap4.get("PAY_MONEYALL") == null ? "" : sumMap4.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap4.get("AMOUNTALL") == null ? "" : sumMap4.get("AMOUNTALL");
				   
					Object SUPPER_NAME = sumMap4.get("SUPPER_NAME") == null ? "" : sumMap4.get("SUPPER_NAME");
					
					LEASE_TOPRIC_JIS4 += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					QZZJ_JIS4 += ((java.math.BigDecimal) QZZJALL).doubleValue();
					LGJK_JIS4 += ((java.math.BigDecimal) LGJKALL).doubleValue();
					PAY_MONEY_JIS4 += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS4 += ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					
					xssf_w_cell4 = xssf_w_row4.createCell(1);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell4.setCellStyle(head_cellStyle);						
					xssf_w_cell4.setCellValue(SUPPER_NAME.toString()+" 汇总");

					xssf_w_cell4 = xssf_w_row4.createCell(6);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell4 = xssf_w_row4.createCell(8);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell4 = xssf_w_row4.createCell(9);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) QZZJALL).doubleValue());
					
					xssf_w_cell4 = xssf_w_row4.createCell(10);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) LGJKALL).doubleValue());
					
					xssf_w_cell4 = xssf_w_row4.createCell(11);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum4;++titleRows4;
				}
			}
			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
			xssf_w_row4 = xssf_w_sheet4.createRow(titleRows4);
			
			xssf_w_cell = xssf_w_row.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row.createCell(6);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(AMOUNT_JIS);
			
			xssf_w_cell = xssf_w_row.createCell(8);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(LEASE_TOPRIC_JIS);
			
			xssf_w_cell = xssf_w_row.createCell(9);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(PAY_MONEY_JIS);
			
			xssf_w_cell2 = xssf_w_row2.createCell(1);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell2.setCellStyle(head_cellStyle);						
			xssf_w_cell2.setCellValue(" 合计 ");
			
			xssf_w_cell2 = xssf_w_row2.createCell(6);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(AMOUNT_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(8);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(LEASE_TOPRIC_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(9);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(SQ_PRICE_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(10);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(DB_PRICE_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(11);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(CS_PRICEALL_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(12);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(QTFYF_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(13);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(PAY_MONEY_JIS2);
			
			
			xssf_w_cell3 = xssf_w_row3.createCell(1);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell3.setCellStyle(head_cellStyle);						
			xssf_w_cell3.setCellValue(" 合计 ");
			
			xssf_w_cell3 = xssf_w_row3.createCell(6);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(AMOUNT_JIS3);
			
			xssf_w_cell3 = xssf_w_row3.createCell(8);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(LEASE_TOPRIC_JIS3);
			
			xssf_w_cell3 = xssf_w_row3.createCell(9);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(DB_PRICE_JIS3);
			
			xssf_w_cell3 = xssf_w_row3.createCell(10);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(PAY_MONEY_JIS3);
			
			xssf_w_cell4 = xssf_w_row4.createCell(1);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell4.setCellStyle(head_cellStyle);						
			xssf_w_cell4.setCellValue(" 合计 ");
			
			xssf_w_cell4 = xssf_w_row4.createCell(6);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(AMOUNT_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(8);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(LEASE_TOPRIC_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(9);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(QZZJ_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(10);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(LGJK_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(11);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(PAY_MONEY_JIS4);
			
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(("租赁物购买价款明细表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//设备放款导出
	public List<Map<String,Object>> pay_Head_Excle_Eq_Mg(Map<String,Object> m)
	{
		return Dao.selectList("payment.pay_Detail_Excle_Mg", m);
	}
	//设备放款导出合计
	public Map<String,Object> pay_Head_Excle_Eq_Sum(Map<String,Object> m)
	{
		return Dao.selectOne("payment.pay_Head_Excle_Sum", m);
	}
}

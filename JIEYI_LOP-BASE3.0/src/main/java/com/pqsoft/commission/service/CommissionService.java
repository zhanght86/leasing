package com.pqsoft.commission.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CommissionService {

	private String path = "commission.";
	
	public List<Object> getPage(Map<String,Object> param){
		return Dao.selectList(path+"getPageList", param);
//		return PageUtil.getPage(param, path+"getPageList", path+"getPageCount");
	}
	
	public Workbook getDataList(Map<String,Object> param){
		List<Map<String,Object>> dataList = Dao.selectList(path+"getDataList",param);
		
		String[] excelHeader = {"ID","项目类型","商务政策别名","政策类型","支付表号","项目编号","客户名","年利率","经销商名"
				,"车系车型","设备总价值","融资额","期次","放款通过时间","超融","设备数量","预计返佣日期","收款单位"
				,"开户行","开户行账号","结算状态","返佣金额"};
		int[] excelHeaderWidth = {100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100
				,100,100,100,100};
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		CellStyle style = wb.createCellStyle();    
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    Font font = wb.createFont();  
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体 
	    font.setFontName("黑体");
	    font.setFontHeightInPoints((short) 10);//设置字体大小
	    style.setFont(font);
		for(int i=0;i<excelHeader.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);    
	        sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]);
		}
		
		for(int i=0;i<dataList.size();i++){
			row = sheet.createRow(i+1);
			Map<String,Object> map = dataList.get(i);
			if(map!=null){
				if(map.get("ID")!=null&&!"".equals(map.get("ID").toString())){
					row.createCell(0).setCellValue(Double.parseDouble(map.get("ID").toString()));
				}
				if(map.get("PLATFORM_TYPE")!=null&&!"".equals(map.get("PLATFORM_TYPE").toString())){
					row.createCell(1).setCellValue(map.get("PLATFORM_TYPE").toString());
				}
				if(map.get("SCHEME_NAME")!=null&&!"".equals(map.get("SCHEME_NAME").toString())){
					row.createCell(2).setCellValue(map.get("SCHEME_NAME").toString());
				}
				if(map.get("ALIASES")!=null&&!"".equals(map.get("ALIASES").toString())){
					row.createCell(3).setCellValue(map.get("ALIASES").toString());
				}
				if(map.get("PAYLIST_CODE")!=null&&!"".equals(map.get("PAYLIST_CODE").toString())){
					row.createCell(4).setCellValue(map.get("PAYLIST_CODE").toString());
				}
				if(map.get("PRO_CODE")!=null&&!"".equals(map.get("PRO_CODE").toString())){
					row.createCell(5).setCellValue(map.get("PRO_CODE").toString());
				}
				if(map.get("NAME")!=null&&!"".equals(map.get("NAME").toString())){
					row.createCell(6).setCellValue(map.get("NAME").toString());
				}
				if(map.get("YEAR_INTEREST")!=null&&!"".equals(map.get("YEAR_INTEREST").toString())){
					row.createCell(7).setCellValue(Double.parseDouble(map.get("YEAR_INTEREST").toString()));
				}
				if(map.get("SUP_NAME")!=null&&!"".equals(map.get("SUP_NAME").toString())){
					row.createCell(8).setCellValue(map.get("SUP_NAME").toString());
				}
				if(map.get("CXCX")!=null&&!"".equals(map.get("CXCX").toString())){
					row.createCell(9).setCellValue(map.get("CXCX").toString());
				}
				if(map.get("LEASE_TOPRIC")!=null&&!"".equals(map.get("LEASE_TOPRIC").toString())){
					row.createCell(10).setCellValue(Double.parseDouble(map.get("LEASE_TOPRIC").toString()));
				}
				if(map.get("FINANCE_TOPRIC")!=null&&!"".equals(map.get("FINANCE_TOPRIC").toString())){
					row.createCell(11).setCellValue(Double.parseDouble(map.get("FINANCE_TOPRIC").toString()));
				}
				if(map.get("LEASE_TERM")!=null&&!"".equals(map.get("LEASE_TERM").toString())){
					row.createCell(12).setCellValue(Double.parseDouble(map.get("LEASE_TERM").toString()));
				}
				if(map.get("BEGGIN_DATE")!=null&&!"".equals(map.get("BEGGIN_DATE").toString())){
					row.createCell(13).setCellValue(map.get("BEGGIN_DATE").toString());
				}
				if(map.get("CR")!=null&&!"".equals(map.get("CR").toString())){
					row.createCell(14).setCellValue(Double.parseDouble(map.get("CR").toString()));
				}
				if(map.get("FEQNUM")!=null&&!"".equals(map.get("FEQNUM").toString())){
					row.createCell(15).setCellValue(Double.parseDouble(map.get("FEQNUM").toString()));
				}
				if(map.get("PREDICT_DATE")!=null&&!"".equals(map.get("PREDICT_DATE").toString())){
					row.createCell(16).setCellValue(Double.parseDouble(map.get("PREDICT_DATE").toString()));
				}
				if(map.get("PAYEE")!=null&&!"".equals(map.get("PAYEE").toString())){
					row.createCell(17).setCellValue(Double.parseDouble(map.get("PAYEE").toString()));
				}
				if(map.get("BANK")!=null&&!"".equals(map.get("BANK").toString())){
					row.createCell(18).setCellValue(Double.parseDouble(map.get("BANK").toString()));
				}
				if(map.get("BANK_ACCOUNT")!=null&&!"".equals(map.get("BANK_ACCOUNT").toString())){
					row.createCell(19).setCellValue(Double.parseDouble(map.get("BANK_ACCOUNT").toString()));
				}
				if(map.get("STATUS")!=null&&!"".equals(map.get("STATUS").toString())){
					row.createCell(20).setCellValue(map.get("STATUS").toString());
				}
				if(map.get("REBATE_AMOUNT")!=null&&!"".equals(map.get("REBATE_AMOUNT").toString())){
					row.createCell(21).setCellValue(Double.parseDouble(map.get("REBATE_AMOUNT").toString()));
				}
			}
		}
		return wb;
	}
	
	public int updateRebate(Map<String,Object> param){
		return Dao.update(path+"updateRebate", param);
	}
	
	public List<Map<String,Object>> getApplyList(Map<String,Object> param){
		return Dao.selectList(path+"getApplyList",param);
	}
	
	public int addApply(Map<String,Object> param){
		return Dao.insert(path+"addApply",param);
	}
	
	public int addLoan(Map<String,Object> param){
		return Dao.insert(path+"addLoan",param);
	}
	
	public int selectApplyId(){
		return Dao.selectInt(path+"selectApplyId",new HashMap<String,Object>());
	}
	
	public Map<String,Object> selectCommissionByApplyId(Map<String,Object> param){
		return Dao.selectOne(path+"selectCommissionByApplyId", param);
	}
	
	public List<Map<String,Object>> selectLoanByApplyId(Map<String,Object> param){
		return Dao.selectList(path+"selectLoanByApplyId", param);
	}
	
	public List<Map<String,Object>> selectRebateByLoanId(String loan_id){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("LOAN_ID", loan_id);
		return Dao.selectList(path+"getPageList", param);
	}
	
	/**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
     * 要用到正则表达式
     */
    public static String digitUppercase(double n){
        String fraction[] = {"角", "分"};
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = {{"元", "万", "亿"},
                     {"", "拾", "佰", "仟"}};
 
        String head = n < 0? "负": "";
        n = Math.abs(n);
         
        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if(s.length()<1){
            s = "整";    
        }
        int integerPart = (int)Math.floor(n);
 
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
}

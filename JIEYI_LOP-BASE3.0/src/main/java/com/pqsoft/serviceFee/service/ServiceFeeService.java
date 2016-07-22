package com.pqsoft.serviceFee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.pqsoft.skyeye.api.Dao;

public class ServiceFeeService {
	private String path = "serviceFee.";
	public List<Object> getPage(Map<String, Object> param) {
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
	public void updateServiceFeeStatus(Map<String, Object> param2) {
		System.out.println("----");
		Dao.update(path+"updateServiceFeeStatus", param2);
	}
	public Workbook exportExcel(Map<String, Object> param) {
		List<Map<String,Object>> dataList = Dao.selectList(path+"selectDataListByIds", param);
		String[] excelHeader = {"ID","结算状态","项目编号","支付表号","客户名","经销商名","车系车型","服务费金额","设备总价值","融资额",
				"客户总还款金额","收款单位","开户行","开户行账号","返回状态"};
		int[] excelHeaderWidth = {50,80,150,150,80,150,180,100,100,100,100,100,150,150,80};
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
				if(map.get("STATUS")!=null&&!"".equals(map.get("STATUS").toString())){
					System.out.println(map.get("STATUS")+"----------");
					if((0+"").equals(map.get("STATUS").toString())){
						row.createCell(1).setCellValue("未放款");
					}else if((3+"").equals(map.get("STATUS").toString())){
						row.createCell(1).setCellValue("放款不通过");
					}
				}
				if(map.get("PRO_CODE")!=null&&!"".equals(map.get("PRO_CODE").toString())){
					row.createCell(2).setCellValue(map.get("PRO_CODE").toString());
				}
				if(map.get("PAYLIST_CODE")!=null&&!"".equals(map.get("PAYLIST_CODE").toString())){
					row.createCell(3).setCellValue(map.get("PAYLIST_CODE").toString());
				}
				if(map.get("NAME")!=null&&!"".equals(map.get("NAME").toString())){
					row.createCell(4).setCellValue(map.get("NAME").toString());
				}
				if(map.get("SUP_NAME")!=null&&!"".equals(map.get("SUP_NAME").toString())){
					row.createCell(5).setCellValue(map.get("SUP_NAME").toString());
				}
				if(map.get("CXCX")!=null&&!"".equals(map.get("CXCX").toString())){
					row.createCell(6).setCellValue(map.get("CXCX").toString());
				}
				if(map.get("SERVICE_FEE_AMOUNT")!=null&&!"".equals(map.get("SERVICE_FEE_AMOUNT").toString())){
					row.createCell(7).setCellValue(Double.parseDouble(map.get("SERVICE_FEE_AMOUNT").toString()));
				}
				if(map.get("LEASE_TOPRIC")!=null&&!"".equals(map.get("LEASE_TOPRIC").toString())){
					row.createCell(8).setCellValue(Double.parseDouble(map.get("LEASE_TOPRIC").toString()));
				}
				if(map.get("FINANCE_TOPRIC")!=null&&!"".equals(map.get("FINANCE_TOPRIC").toString())){
					row.createCell(9).setCellValue(Double.parseDouble(map.get("FINANCE_TOPRIC").toString()));
				}
				if(map.get("TOTAL_MONTH_PRICE")!=null&&!"".equals(map.get("TOTAL_MONTH_PRICE").toString())){
					row.createCell(10).setCellValue(Double.parseDouble(map.get("TOTAL_MONTH_PRICE").toString()));
				}
				if(map.get("PAYEE")!=null&&!"".equals(map.get("PAYEE").toString())){
					row.createCell(11).setCellValue(map.get("PAYEE").toString());
				}
				if(map.get("BANK")!=null&&!"".equals(map.get("BANK").toString())){
					row.createCell(12).setCellValue(map.get("BANK").toString());
				}
				if(map.get("BANK_ACCOUNT")!=null&&!"".equals(map.get("BANK_ACCOUNT").toString())){
					row.createCell(13).setCellValue(map.get("BANK_ACCOUNT").toString());
				}
				row.createCell(14).setCellValue("");
			}
		}
		return wb;
	}
	/*
	 * 传给接口 未放款、放款不通过服务费数据
	 * TYPE 1 表示 未传送到接口  2 表示已经传送给接口
	 */
	public List<Map<String,Object>> serviceFeeToInterface(){
		List<Map<String,Object>> list = Dao.selectList(path+"serviceFeeToInterface");
		Dao.update(path+"updateServiceFeeToInterface");
		return list;
	}
	/*
	 * 传给接口 未放款、放款不通过服务费数据
	 * 总笔数、总金额
	 */
	public Map<String,Object> serviceFeeToInterfaceCount(){
		return (Map<String, Object>) Dao.selectOne(path+"serviceFeeToInterfaceCount");
	}
	/*
	 * 从接口传回数据 放款通过数据 或者 不通过
	 * 
	 */
	public void serviceFeeFrInterFPassOrNo(Map<String,Object> map){
		map.put("ID",map.get("ITEM_NO"));
		if((30+"").equals(map.get("RETURN_STATUS").toString())){
			map.put("STATUS", 2);
			map.put("TYPE", 2);
			Dao.update(path+"updateServiceFeeStatus", map);
		}else if((40+"").equals(map.get("RETURN_STATUS").toString())){
			map.put("STATUS", 3);
			map.put("TYPE", 1);
			Dao.update(path+"updateServiceFeeStatus", map);
		}
	}
	
}

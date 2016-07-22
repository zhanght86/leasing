package com.pqsoft.fi.payin.service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CftXLS implements IAnalysisXLS {
	final static Logger logger = Logger.getLogger(CftXLS.class);
	private String filename;

	private Map<String, String[]> keys = new HashMap<String, String[]>();
	{
		if (!keys.containsKey("FUND_COMPANY_NAME")) keys.put("FUND_COMPANY_NAME", new String[] { "收款名称" });// 收款名称;
		if (!keys.containsKey("FUND_COMPANY_ACCOUNT")) keys.put("FUND_COMPANY_ACCOUNT", new String[] { "收款账号", "收款人账号" });// 收款账号
		if (!keys.containsKey("FUND_ACCEPT_DATE")) keys.put("FUND_ACCEPT_DATE", new String[] { "交易时间" });// 交易时间
		if (!keys.containsKey("FUND_COMENAME")) keys.put("FUND_COMENAME", new String[] { "用户名", "商户侧用户标识" });
		if (!keys.containsKey("FUND_COMECODE")) keys.put("FUND_COMECODE", new String[] { "银行帐号", "商户协议号" });
		if (!keys.containsKey("FUND_RECEIVE_MONEY")) keys.put("FUND_RECEIVE_MONEY", new String[] { "代扣金额(元)", "代扣金额" });// 发生额
		if (!keys.containsKey("FUND_DOCKET")) keys.put("FUND_DOCKET", new String[] { "备注" });// 备注
		if (!keys.containsKey("FUND_PAY_CODE")) keys.put("FUND_PAY_CODE", new String[] { "支付表" });// 支付表
		if (!keys.containsKey("FUND_BANK")) keys.put("FUND_BANK", new String[] { "银行类型" });// 支付表
//		if (!keys.containsKey("FLAG")) keys.put("FLAG", new String[] { "代扣状态" });// 支付表
		if (!keys.containsKey("FUND_TYPE_FLAG")) keys.put("FUND_TYPE_FLAG", new String[] { "资金来源" });// 支付表
	}

	public CftXLS(String f) {
		this.filename = f;
	}

	public List<Map<String, Object>> read() throws Exception {
		return getListFromXLSForWebBank(this.filename);
	}

	public List<Map<Integer, Map<String, Object>>> excelReadDivision(String path) throws Exception {
		File file = new File(path);
		List<Map<Integer, Map<String, Object>>> list = new ArrayList<Map<Integer, Map<String, Object>>>();
		WorkbookSettings workbookSettings = new WorkbookSettings();
		workbookSettings.setEncoding("ISO-8859-1"); // 关键代码，解决中文乱码
		workbookSettings.setEncoding("GBK"); // 关键代码，解决中文乱码
		Workbook book = Workbook.getWorkbook(file, workbookSettings);
		list.add(readSheet(book.getSheet(0)));
		return list;
	}

	public Map<Integer, Map<String, Object>> readSheet(Sheet sheet) throws Exception {
		Map<Integer, Map<String, Object>> list = new TreeMap<Integer, Map<String, Object>>();

		for (String key : keys.keySet()) {
			Cell cell = null;
			for (String s : keys.get(key)) {
				cell = sheet.findCell(Pattern.compile(".*" + s + ".*"), 0, 0, 100, 100, true);
				if (cell != null) break;
			}
			if (cell == null) {
				if ("FUND_PAY_CODE".equals(key)) continue;
				if ("FUND_DOCKET".equals(key)) continue;
				if ("FUND_COMPANY_NAME".equals(key)) continue;
				if ("FUND_COMPANY_ACCOUNT".equals(key)) continue;
				if ("FUND_ACCEPT_DATE".equals(key)) continue;
				// if ("FUND_BANK".equals(key)) continue;
				// if ("FLAG".equals(key)) continue;
				throw new Exception("未检测到对应[" + StringUtils.join(keys.get(key), ",") + "]列");
			}
			int start = cell.getRow();
			int colc = cell.getColumn();
			for (int i = start + 1; i < sheet.getRows(); i++) {
				if (i == sheet.getRows()) break;
				if (!list.containsKey(i)) list.put(i, new HashMap<String, Object>());
				Cell ic = sheet.getCell(colc, i);
				String value;
				if (ic.getType() == CellType.DATE) {
					DateCell dc = (DateCell) ic;// 解决日期截断问题
					Date date = dc.getDate();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, -8);
					date = calendar.getTime();
					SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期
					value = ds.format(date);
				} else if (ic.getType() == CellType.NUMBER) {
					NumberCell nc = (NumberCell) ic;
					value = new DecimalFormat("#.##").format(nc.getValue());
				} else {
					value = ic.getContents().replaceAll("//xA0", "").trim();
				}
				if ("FUND_DOCKET".equals(key)) {
					try {
						list.get(i).put("IDCODE", value.split("[;；]")[1]);
					} catch (Exception e) {}
				}
				list.get(i).put(key, value);
			}
		}
		return list;
	}

	// 网银 读取
	public List<Map<String, Object>> getListFromXLSForWebBank(String path) throws Exception {

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List<Map<Integer, Map<String, Object>>> list = excelReadDivision(path);
		for (Map<Integer, Map<String, Object>> map : list) {
			for (Map<String, Object> m : map.values()) {
				returnList.add(m);
			}
		}
		return returnList;
	}

	public static void main(String[] args) throws Exception {
		new CftXLS("C:/Users/lichao/Desktop/c1.xls").read();
		// new AnalysisXLS("d:/test.xls").read();
		// new AnalysisXLS("d:/中行7.1-8.6.xls").read();
		// new AnalysisXLS("d:/安.xls").read();
		// 888888888888888060
		// org.pqsoft.util.LogUtil.info(new DecimalFormat("###.###").format(new
		// BigDecimal("888888888888888000")));
		// org.pqsoft.util.LogUtil.info(Pattern.matches(".*交易金额.*",
		// "交易金额[ Trade Amount ]"));
	}
}

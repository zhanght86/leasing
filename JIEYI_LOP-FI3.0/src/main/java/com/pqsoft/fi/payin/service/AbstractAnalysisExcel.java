package com.pqsoft.fi.payin.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;

abstract class AbstractAnalysisExcel implements IAnalysisXLS {
	final static Logger logger = Logger.getLogger(AbstractAnalysisExcel.class);
	protected String filename;

	private Map<String, String[]> keys = new HashMap<String, String[]>();
	{
		try {
			List<?> list = DataDictionaryMemcached.getList("网银模版解析");
			for (Object object : list) {
				Map<String, Object> m = (Map<String, Object>) object;
				if ("收款户名".equals(m.get("FLAG"))) keys.put("FUND_COMPANY_NAME", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("收款账号".equals(m.get("FLAG"))) keys.put("FUND_COMPANY_ACCOUNT", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("到账日期".equals(m.get("FLAG"))) keys.put("FUND_ACCEPT_DATE", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("来款户名".equals(m.get("FLAG"))) keys.put("FUND_COMENAME", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("来款账号".equals(m.get("FLAG"))) keys.put("FUND_COMECODE", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("来款金额".equals(m.get("FLAG"))) keys.put("FUND_RECEIVE_MONEY", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("摘要".equals(m.get("FLAG"))) keys.put("FUND_DOCKET", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("支付表".equals(m.get("FLAG"))) keys.put("FUND_PAY_CODE", m.get("CODE").toString().split("[,;，；]"));// 收款名称
				if ("资金来源".equals(m.get("FLAG"))) keys.put("FUND_TYPE_FLAG", m.get("CODE").toString().split("[,;，；]"));// 资金来源
				if ("银行".equals(m.get("FLAG"))) keys.put("FUND_BANK", m.get("CODE").toString().split("[,;，；]"));// 资金来源
			}// 收款户名 收款账号 到账日期 来款账号 来款户名 支付表 来款金额 摘要 银行 代扣状态
			if (!keys.containsKey("FUND_COMPANY_NAME")) keys.put("FUND_COMPANY_NAME", new String[] { "收款户名" });// 收款名称;
			if (!keys.containsKey("FUND_COMPANY_ACCOUNT")) keys.put("FUND_COMPANY_ACCOUNT", new String[] { "收款账号", "收款帐号" });// 收款账号
			if (!keys.containsKey("FUND_ACCEPT_DATE")) keys.put("FUND_ACCEPT_DATE", new String[] { "到账日期" });// 交易时间
			if (!keys.containsKey("FUND_COMENAME")) keys.put("FUND_COMENAME", new String[] { "来款户名" });
			if (!keys.containsKey("FUND_COMECODE")) keys.put("FUND_COMECODE", new String[] { "来款账号","来款帐号"  });
			if (!keys.containsKey("FUND_RECEIVE_MONEY")) keys.put("FUND_RECEIVE_MONEY", new String[] { "来款金额" });// 发生额
			if (!keys.containsKey("FUND_DOCKET")) keys.put("FUND_DOCKET", new String[] { "摘要" });// 备注
			if (!keys.containsKey("FUND_PAY_CODE")) keys.put("FUND_PAY_CODE", new String[] { "支付表" });// 支付表
			if (!keys.containsKey("FUND_BANK")) keys.put("FUND_BANK", new String[] { "银行" });// 支付表
//			if (!keys.containsKey("FLAG")) keys.put("FLAG", new String[] { "代扣状态" });// 支付表
			if (!keys.containsKey("FUND_TYPE_FLAG")) keys.put("FUND_TYPE_FLAG", new String[] { "资金来源" });// 资金来源
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AbstractAnalysisExcel(String f) {
		this.filename = f;
	}

	public List<Map<String, Object>> read() throws Exception {
		return getListFromXLSForWebBank(this.filename);
	}

	public abstract Workbook getWorkbook() throws Exception;

	public List<Map<Integer, Map<String, Object>>> excelReadDivision(String path) throws Exception {
		Workbook workbook = getWorkbook();
		List<Map<Integer, Map<String, Object>>> list = new ArrayList<Map<Integer, Map<String, Object>>>();

		// WorkbookSettings workbookSettings = new WorkbookSettings();
		// workbookSettings.setEncoding("ISO-8859-1"); // 关键代码，解决中文乱码
		// workbookSettings.setEncoding("GBK"); // 关键代码，解决中文乱码
		// Workbook book = Workbook.getWorkbook(file, workbookSettings);
		list.add(readSheet(workbook.getSheetAt(0)));
		//
		// for (Sheet sheet : book.getSheets()) {
		// list.add(readSheet(sheet));
		// }
		return list;
	}

	public Map<Integer, Map<String, Object>> readSheet(Sheet sheet) throws Exception {
		Map<Integer, Map<String, Object>> list = new TreeMap<Integer, Map<String, Object>>();
		for (String key : keys.keySet()) {
			Cell cell = null;
			for (String s : keys.get(key)) {
				for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
					if (sheet.getRow(0).getCell(i).getStringCellValue().indexOf(s) != -1) {// 列名存在
						cell = sheet.getRow(0).getCell(i);
					}
				}
				if (cell != null) break;
			}
			if (cell == null) {
				if ("FUND_PAY_CODE".equals(key)) continue;
				if ("FUND_DOCKET".equals(key)) continue;
				throw new Exception("未检测到对应[" + StringUtils.join(keys.get(key), ",") + "]列");
			}
			int start = cell.getRowIndex();
			int colc = cell.getColumnIndex();
			for (int i = start + 1; i <= sheet.getLastRowNum(); i++) {
				// if (i == sheet.getLastRowNum()) break;
				if (!list.containsKey(i)) list.put(i, new HashMap<String, Object>());
				System.out.println("===========colc="+colc);
				Cell ic = sheet.getRow(i).getCell(colc);
				String value;
				if(ic !=null){
					if (ic.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						if ("FUND_ACCEPT_DATE".equals(key)) {
							System.out.println("----------------ic.getDateCellValue()="+ic.getDateCellValue());
							SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");//
							value = ds.format(ic.getDateCellValue());
						} else {
							System.out.println("----------------ic.getNumericCellValue()="+ic.getNumericCellValue());
							value = new DecimalFormat("#.##").format(ic.getNumericCellValue());
						}
					} else {
						System.out.println("----------------ic.getStringCellValue()="+ic.getStringCellValue().replaceAll("\\xA0", "").trim());
						value = ic.getStringCellValue().replaceAll("\\xA0", "").trim();
					}
					list.get(i).put(key, value);
					list.get(i).put("FLAG", "8");
				}
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

}

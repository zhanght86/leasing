// package com.pqsoft.fi.payin.service;

// import java.io.File;
// import java.io.FileInputStream;
// import java.text.DecimalFormat;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.TreeMap;
// import java.util.regex.Pattern;
//
// import org.apache.commons.lang.StringUtils;
// import org.apache.log4j.Logger;
// import org.apache.poi.hssf.usermodel.HSSFCell;
// import org.apache.poi.hssf.usermodel.HSSFSheet;
// import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// import org.pqsoft.dataDictionary.service.DataDictionaryService;
// import org.springframework.util.FileCopyUtils;
//
// public class CFTXLS implements IAnalysisXLS {
// final static Logger logger = Logger.getLogger(CFTXLS.class);
// private String filename;
//
// private Map<String, String[]> keys = new HashMap<String, String[]>();
// {
// try {
// List<Object> list = DataDictionaryService.queryDataDictionary("模版解析");
// for (Object object : list) {
// Map<String, Object> m = (Map<String, Object>) object;
// if ("收款名称".equals(m.get("FLAG"))) keys.put("FUND_COMPANY_NAME",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// if ("收款账号".equals(m.get("FLAG"))) keys.put("FUND_COMPANY_ACCOUNT",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// if ("交易时间".equals(m.get("FLAG"))) keys.put("FUND_ACCEPT_DATE",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// if ("对方户名".equals(m.get("FLAG"))) keys.put("FUND_COMENAME",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// if ("对方账号".equals(m.get("FLAG"))) keys.put("FUND_COMECODE",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// if ("发生额".equals(m.get("FLAG"))) keys.put("FUND_RECEIVE_MONEY",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// if ("摘要".equals(m.get("FLAG"))) keys.put("FUND_DOCKET",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// if ("支付表".equals(m.get("FLAG"))) keys.put("FUND_PAY_CODE",
// m.get("CODE").toString().split("[,;，；]"));// 收款名称
// }
// if (!keys.containsKey("FUND_COMPANY_NAME")) keys.put("FUND_COMPANY_NAME", new
// String[] { "收款名称", "收款人名称" });// 收款名称;
// if (!keys.containsKey("FUND_COMPANY_ACCOUNT"))
// keys.put("FUND_COMPANY_ACCOUNT", new String[] { "收款账号", "收款人账号" });// 收款账号
// if (!keys.containsKey("FUND_ACCEPT_DATE")) keys.put("FUND_ACCEPT_DATE", new
// String[] { "交易时间" });// 交易时间
// if (!keys.containsKey("FUND_COMENAME")) keys.put("FUND_COMENAME", new
// String[] { "对方户名", "付款人名称" });
// if (!keys.containsKey("FUND_COMECODE")) keys.put("FUND_COMECODE", new
// String[] { "对方账号", "付款人账号" });
// if (!keys.containsKey("FUND_RECEIVE_MONEY")) keys.put("FUND_RECEIVE_MONEY",
// new String[] { "发生额", "交易金额" });// 发生额
// if (!keys.containsKey("FUND_DOCKET")) keys.put("FUND_DOCKET", new String[] {
// "摘要" });// 备注
// if (!keys.containsKey("FUND_PAY_CODE")) keys.put("FUND_PAY_CODE", new
// String[] { "支付表" });// 支付表
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
//
// public CFTXLS(String f) {
// this.filename = f;
// }
//
// public List<Map<String, Object>> read() throws Exception {
// return getListFromXLSForWebBank(this.filename);
// }
//
// public List<Map<Integer, Map<String, Object>>> excelReadDivision(String path)
// throws Exception {
// File file = new File(path);
// List<Map<Integer, Map<String, Object>>> list = new ArrayList<Map<Integer,
// Map<String, Object>>>();
//
// HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
// for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
// // 读取sheet
// HSSFSheet sheet = workbook.getSheetAt(i);
// if (sheet == null) break;
// if (workbook.isSheetHidden(i)) continue;
// try {} catch (Exception e) {
// throw new Exception(file.getAbsolutePath(), e);
// }
// }
//
// return list;
// }
//
// public Map<Integer, Map<String, Object>> readSheet(HSSFSheet sheet) throws
// Exception {
// Map<Integer, Map<String, Object>> list = new TreeMap<Integer, Map<String,
// Object>>();
// for (String key : keys.keySet()) {
// HSSFCell cell = null;
// for (String s : keys.get(key)) {
// cell = sheet.findCell(Pattern.compile(".*" + s + ".*"), 0, 0, 100, 100,
// true);
// if (cell != null) break;
// }
// if (cell == null) {
// if ("FUND_PAY_CODE".equals(key)) continue;
// if ("FUND_DOCKET".equals(key)) continue;
// throw new Exception("未检测到对应[" + StringUtils.join(keys.get(key), ",") + "]列");
// }
// int start = cell.getRow();
// int colc = cell.getColumn();
// for (int i = start + 1; i < sheet.getRows(); i++) {
// if (i == sheet.getRows()) break;
// if (!list.containsKey(i)) list.put(i, new HashMap<String, Object>());
// Cell ic = sheet.getCell(colc, i);
// String value;
// if (ic.getType() == CellType.DATE) {
// DateCell dc = (DateCell) ic;// 解决日期截断问题
// Date date = dc.getDate();
// Calendar calendar = Calendar.getInstance();
// calendar.setTime(date);
// calendar.add(Calendar.HOUR, -8);
// date = calendar.getTime();
// SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期
// value = ds.format(date);
// } else if (ic.getType() == CellType.NUMBER) {
// NumberCell nc = (NumberCell) ic;
// value = new DecimalFormat("#.##").format(nc.getValue());
// } else {
// value = ic.getContents().replaceAll("\\xA0", "").trim();
// }
// list.get(i).put(key, value);
// }
// }
// return list;
// }
//
// // 网银 读取
// public List<Map<String, Object>> getListFromXLSForWebBank(String path) throws
// Exception {
//
// List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
// List<Map<Integer, Map<String, Object>>> list = excelReadDivision(path);
// for (Map<Integer, Map<String, Object>> map : list) {
// for (Map<String, Object> m : map.values()) {
// returnList.add(m);
// }
// }
// // System.out.print("收款名称\t");
// // System.out.print("收款账号\t");
// // System.out.print("交易时间\t");
// // System.out.print("对方账号\t");
// // System.out.print("对方户名\t");
// // System.out.print("发生额\t");
// // org.pqsoft.util.LogUtil.info();
// // for (Map<Integer, Map<String, String>> map : list) {
// // for (Map<String, String> m : map.values()) {
// // System.out.print(m.get("收款名称"));
// // System.out.print("\t");
// // System.out.print(m.get("收款账号"));
// // System.out.print("\t");
// // System.out.print(m.get("交易时间"));
// // System.out.print("\t");
// // System.out.print(m.get("对方账号"));
// // System.out.print("\t");
// // System.out.print(m.get("对方户名"));
// // System.out.print("\t");
// // System.out.print(m.get("发生额"));
// // org.pqsoft.util.LogUtil.info();
// // }
// // }
// return returnList;
// }
//
// private static int nameToColumn(String name) {
// int column = -1;
// for (int i = 0; i < name.length(); ++i) {
// int c = name.charAt(i);
// column = (column + 1) * 26 + c - 'A';
// }
// return column;
// }
//
// private static int[] getaaa(String key) {
// String L = "";
// String H = "";
// for (char c : key.toCharArray()) {
// if (((int) c) <= 57) {
// H += String.valueOf(c);
// } else {
// L += String.valueOf(c);
// }
// }
// nameToColumn(L);
// int[] is = new int[2];
// is[0] = nameToColumn(L);
// is[1] = Integer.parseInt(H) - 1;
// return is;
// }
//
// public static void main(String[] args) throws Exception {
// // new AnalysisXLS("d:/test.xls").read();
// // new AnalysisXLS("d:/中行7.1-8.6.xls").read();
// // new AnalysisXLS("d:/安.xls").read();
// // 888888888888888060
// // org.pqsoft.util.LogUtil.info(new DecimalFormat("###.###").format(new
// // BigDecimal("888888888888888000")));
// org.pqsoft.util.LogUtil.info(Pattern.matches(".*交易金额.*",
// "交易金额[ Trade Amount ]"));
// }
//
// }

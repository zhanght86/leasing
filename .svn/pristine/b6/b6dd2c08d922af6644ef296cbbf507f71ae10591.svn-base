package com.pqsoft.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateTime;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import jxl.write.Number;

/**
 * 
 * @author Administrator
 * 
 */
public class ExcelFinancialUtil {
	@SuppressWarnings("deprecation")
	public static void excelBuilder(Map<String, Object> payhead,
			List<Map<String, String>> colnumList,
			List<Map<String, Object>> dataList, String title, String fileName,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.reset();
		response.setCharacterEncoding("UTF-8");
		
		String name = URLDecoder.decode(fileName,"iso-8859-1");
		if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
			name = URLEncoder.encode(fileName, "UTF-8"); //IE
		}else{
			name = new String(fileName.getBytes("UTF-8"),"iso-8859-1"); //fireFox Chrome
		}
		/*String name = (fileName.length() > 150) ? new String(
				fileName.getBytes("UTF-8"), "ISO8859-1") : URLEncoder.encode(
				fileName, "UTF-8");*/
		response.setHeader("Content-disposition", "attachment; filename="
				+ name + ".xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		WritableWorkbook wbook = Workbook.createWorkbook(response
				.getOutputStream()); // 建立excel文件
		WritableSheet wsheet = wbook.createSheet(title, 0);// sheet名称
		// 设置excel标题
		int charNormal = 15;
		WritableFont oneFont = new WritableFont(WritableFont.createFont("宋体"),
				charNormal);
		WritableCellFormat normalFormat = new jxl.write.WritableCellFormat(
				oneFont);
		normalFormat.setAlignment(Alignment.CENTRE);
		normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		normalFormat.setWrap(true); // 是否换行
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wcfFC.setBackground(Colour.AQUA);
		wfont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD,
				false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFC1 = new WritableCellFormat(wfont);
		wcfFC1.setAlignment(Alignment.CENTRE);
		wcfFC1.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcfFC.setAlignment(Alignment.CENTRE);
		wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

		DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置列宽
		int length = colnumList.size();
		for (int i = 0; i < length; i++) {
			wsheet.setColumnView(i, 23);
		}
		// 开始生成主体内容
		wsheet.mergeCells(0, 0, length - 1, 0);
		wsheet.addCell(new Label(0, 0, title, wcfFC));

		wsheet.addCell(new Label(0, 1, "支付表号：", wcfFC1));
		wsheet.mergeCells(1, 1, 3, 1);
		wsheet.addCell(new Label(1, 1, "" + payhead.get("PAYLIST_CODE"), wcfFC1));

		wsheet.addCell(new Label(4, 1, "净融资额：", wcfFC1));
		wsheet.addCell(new Label(5, 1, "" + payhead.get("sumTopric"), wcfFC1));

		// 列标题
		for (int i = 0; i < length; i++) {
			Map<String, String> map = colnumList.get(i);
			if (map != null && map.containsKey("colnumName")
					&& map.get("colnumName") != null) {
				wsheet.addCell(new Label(i, 2, map.get("colnumName"), wcfFC1));
			}
		}

		// 列数据
		int dateLength = dataList.size();
		for (int i = 0; i < dateLength; i++) {
			Map<String, Object> tempMap = dataList.get(i);
			if (tempMap != null) {
				for (int j = 0; j < length; j++) {
					Map<String, String> map = colnumList.get(j);
					if (map != null && map.containsKey("colnumValue")
							&& map.get("colnumValue") != null
							&& tempMap.containsKey(map.get("colnumValue"))) {
						Object o = tempMap.get(map.get("colnumValue"));
						WritableCell value = null;
						if (o != null) {
							int type = 0;
							if (map.containsKey("colnumType")) {
								type = Integer.valueOf(map.get("colnumType"));
							}
							switch (type) {
							case 1:
								try {
									value = new Number(j, 3 + i,
											Double.valueOf(o.toString()));
								} catch (Exception e) {
									value = new Number(j, 3 + i, 0);
								}
								break;
							case 2:
								try {
									Date d = f1.parse(o.toString());
									d.setHours(d.getHours() + 8);
									value = new DateTime(j, 3 + i, d);
								} catch (Exception e) {
									value = new Label(j, 3 + i, "");
									;
								}
								break;
							case 3:
								try {
									Date d = f2.parse(o.toString());
									d.setHours(d.getHours() + 8);
									value = new DateTime(j, 3 + i, d);
								} catch (Exception e) {
									value = new Label(j, 3 + i, "");
									;
								}
								break;
							default:
								try {
									value = new Label(j, 3 + i, tempMap.get(
											map.get("colnumValue")).toString());
								} catch (Exception e) {
									value = new Label(j, 3 + i, "");
									;
								}
								break;
							}
						} else {
							value = new Label(j, 3 + i, "");
						}
						wsheet.addCell(value);
					}
				}
			}
		}
		wbook.write(); // 写入文件
		wbook.close();
		response.getOutputStream().close();
	}
	
	@SuppressWarnings("deprecation")
	public static void excelBuilder(Map<String, Object> payhead,
			List<Map<String, String>> colnumList,
			List<Map<String, Object>> dataList, String title, String fileName,
			HttpServletResponse response) throws Exception {
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String name = URLDecoder.decode(fileName,"iso-8859-1");
		name = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		/*String name = (fileName.length() > 150) ? new String(
				fileName.getBytes("UTF-8"), "ISO8859-1") : URLEncoder.encode(
				fileName, "UTF-8");*/
		response.setHeader("Content-disposition", "attachment; filename="
				+ name + ".xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		WritableWorkbook wbook = Workbook.createWorkbook(response
				.getOutputStream()); // 建立excel文件
		WritableSheet wsheet = wbook.createSheet(title, 0);// sheet名称
		// 设置excel标题
		int charNormal = 15;
		WritableFont oneFont = new WritableFont(WritableFont.createFont("宋体"),
				charNormal);
		WritableCellFormat normalFormat = new jxl.write.WritableCellFormat(
				oneFont);
		normalFormat.setAlignment(Alignment.CENTRE);
		normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		normalFormat.setWrap(true); // 是否换行
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wcfFC.setBackground(Colour.AQUA);
		wfont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD,
				false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFC1 = new WritableCellFormat(wfont);
		wcfFC1.setAlignment(Alignment.CENTRE);
		wcfFC1.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcfFC.setAlignment(Alignment.CENTRE);
		wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

		DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置列宽
		int length = colnumList.size();
		for (int i = 0; i < length; i++) {
			wsheet.setColumnView(i, 23);
		}
		// 开始生成主体内容
		wsheet.mergeCells(0, 0, length - 1, 0);
		wsheet.addCell(new Label(0, 0, title, wcfFC));

		wsheet.addCell(new Label(0, 1, "支付表号：", wcfFC1));
		wsheet.mergeCells(1, 1, 3, 1);
		wsheet.addCell(new Label(1, 1, "" + payhead.get("PAYLIST_CODE"), wcfFC1));

		wsheet.addCell(new Label(4, 1, "净融资额：", wcfFC1));
		wsheet.addCell(new Label(5, 1, "" + payhead.get("sumTopric"), wcfFC1));

		// 列标题
		for (int i = 0; i < length; i++) {
			Map<String, String> map = colnumList.get(i);
			if (map != null && map.containsKey("colnumName")
					&& map.get("colnumName") != null) {
				wsheet.addCell(new Label(i, 2, map.get("colnumName"), wcfFC1));
			}
		}

		// 列数据
		int dateLength = dataList.size();
		for (int i = 0; i < dateLength; i++) {
			Map<String, Object> tempMap = dataList.get(i);
			if (tempMap != null) {
				for (int j = 0; j < length; j++) {
					Map<String, String> map = colnumList.get(j);
					if (map != null && map.containsKey("colnumValue")
							&& map.get("colnumValue") != null
							&& tempMap.containsKey(map.get("colnumValue"))) {
						Object o = tempMap.get(map.get("colnumValue"));
						WritableCell value = null;
						if (o != null) {
							int type = 0;
							if (map.containsKey("colnumType")) {
								type = Integer.valueOf(map.get("colnumType"));
							}
							switch (type) {
							case 1:
								try {
									value = new Number(j, 3 + i,
											Double.valueOf(o.toString()));
								} catch (Exception e) {
									value = new Number(j, 3 + i, 0);
								}
								break;
							case 2:
								try {
									Date d = f1.parse(o.toString());
									d.setHours(d.getHours() + 8);
									value = new DateTime(j, 3 + i, d);
								} catch (Exception e) {
									value = new Label(j, 3 + i, "");
									;
								}
								break;
							case 3:
								try {
									Date d = f2.parse(o.toString());
									d.setHours(d.getHours() + 8);
									value = new DateTime(j, 3 + i, d);
								} catch (Exception e) {
									value = new Label(j, 3 + i, "");
									;
								}
								break;
							default:
								try {
									value = new Label(j, 3 + i, tempMap.get(
											map.get("colnumValue")).toString());
								} catch (Exception e) {
									value = new Label(j, 3 + i, "");
									;
								}
								break;
							}
						} else {
							value = new Label(j, 3 + i, "");
						}
						wsheet.addCell(value);
					}
				}
			}
		}
		wbook.write(); // 写入文件
		wbook.close();
		response.getOutputStream().close();
	}

	public static List<Map<String, String>> flagListBuilder(
			List<Map<String, String>> baseList, String key, String value) {
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("colnumName", key);
		tempMap.put("colnumValue", value);
		baseList.add(tempMap);
		return baseList;
	}

	public static List<Map<String, String>> flagListBuilder(
			List<Map<String, String>> baseList, String key, String value,
			String dataType) {
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("colnumName", key);
		tempMap.put("colnumValue", value);
		String flag = "0";
		if ("number".equals(dataType)) {
			flag = "1";
		}
		if ("date".equals(dataType)) {
			flag = "2";
		}
		if ("dateTime".equals(dataType)) {
			flag = "3";
		}
		tempMap.put("colnumType", flag);
		baseList.add(tempMap);
		return baseList;
	}
	public static void main(String[] args) {
		try{
			String fileName ="你好"; 
			String name = URLDecoder.decode(fileName,"iso-8859-1");
			System.out.println(name);
		}catch(Exception e){
			
		}	
	}
}

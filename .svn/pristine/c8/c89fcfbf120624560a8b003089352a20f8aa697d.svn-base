package com.pqsoft.adjustRate.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.esa2000.Shell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.DateUtil;

public class AdjustRateNoticePdf {
	/******** 租金调整通知书模板***@auth: king 2014年9月24日 *************************/

	private static String fontpath = SkyEye
			.getRealPath("WEB-INF/res/simsun.ttc,1");
	private static String sealPathContrack = SkyEye
			.getRealPath("WEB-INF/res/hetong.dat");
	private static String sealPathWatemark = SkyEye
			.getRealPath("WEB-INF/res/jiamishuiyin.bmp");

	private static Font fontChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 12, Font.COURIER);
	private static Font underlineChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 12, Font.UNDERLINE);
	private static Font titleChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 22, Font.BOLD);
	private static Font moneyChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 9, Font.COURIER);

	/**
	 * 租金调整通知书PDF模板
	 * 
	 * @param map
	 * @param list
	 * @param response
	 * @author King 2014年9月24日
	 */
	public void createNoticePdf(Map<String,Object> map,List<Map<String,Object>> list,ByteArrayOutputStream baos) throws Exception{
		// 页面设置
		// Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小
		Document document = new Document(PageSize.A4, 48, 48, 60, 15);// 其余4个参数，设置了页面的4个边距

		PdfWriter.getInstance(document, baos);
		// 打开文档
		document.open();

		// 写入标题
		// --------------------------------内容----------------------------------------
		Paragraph title = new Paragraph("租金调整通知书", titleChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(1f);// 设置行间距
		document.add(title);
		String CUST_NAME=map.get("CUST_NAME")+"";
		String SEXY=null==map.get("SEXY")?" ":map.get("SEXY")+":";
		Chunk chunk = new Chunk( CUST_NAME +SEXY,
				underlineChinese);
		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(20f);
		title.add("致 ");
		title.add(chunk);
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("依据贵、我双方签订的《融资租赁合同》内容之约定，截至");
		String SIGNED_DATE = (map.get("YHJZSJ") != null && !map.get(
				"YHJZSJ").equals("")) ? map.get("YHJZSJ").toString()
				: " ";
		chunk = new Chunk(" " + SIGNED_DATE + " ", underlineChinese);
		title.add(chunk);
		title.add("贵方共计还有 ");
		chunk = new Chunk("  " + map.get("WHQC") + "  ",
				underlineChinese);
		title.add(chunk);
		String company=null;
		if(Security.getUser().getRoles().size()>=1){
			company=Security.getUser().getOrg().getPlatform();
		}else{
			company="本公司";
		}
		title
				.add("期未到期租金需支付给"+company+"。");

		document.add(title);

		
		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("依据《融资租赁合同》之约定，租赁期限内如遇中国人民银行同期贷款基准利率调整时，将对租赁年利率作出同方向、同幅度的调整。自中国人民银行同期贷款基准利率调整当月起第n+2月（n为利率调整当月）开始，每期租金均按调整后的数额计收。");
		document.add(title);
		
		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("中国人民银行于");
//		title.add(央行调息日期);
		title.add(new Chunk(map.get("DEPEND_TIME")+"", underlineChinese));
		title.add("公布对");
		title.add(new Chunk(map.get("RATE_TERM")+"", underlineChinese));
		title.add("年期人民币贷款利率由");
		title.add(new Chunk(map.get("OLD_BASE_RATE")+"", underlineChinese));
		title.add("%调整至");
		title.add(new Chunk(map.get("NEW_BASE_RATE")+"", underlineChinese));
		title.add("%，调整幅度为");
		title.add(new Chunk(map.get("RATE_CHA")+"", underlineChinese));
		title.add("%");
		document.add(title);
		

		
		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("租金金额具体调整为:自"+map.get("ADJUST_START_DATE")+"起租赁年利率由");
		title.add(new Chunk(map.get("OLD_YEAR_INTEREST")+"", underlineChinese));
		title.add("%调整为");
		title.add(new Chunk(map.get("NEW_YEAR_INTEREST")+"", underlineChinese));
		title.add("%，依据上述约定，自"+map.get("ADJUST_START_MONTH")+"开始，每期租金均按调整后的数额计收。");
		document.add(title);
		

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("调整前剩余租金总额（第"+map.get("START_TERM")+"-"+map.get("LEASE_TERM")+"期）为：￥"+BigDecimal.valueOf(Double.parseDouble(map.get("OLD_ZUJIN")+"")).toPlainString()+"元；");
		document.add(title);
		
		
		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("调整后剩余租金总额（第"+map.get("START_TERM")+"-"+map.get("LEASE_TERM")+"期）为：￥"+BigDecimal.valueOf(Double.parseDouble(map.get("NEW_ZUJIN")+"")).toPlainString()+"元；");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("顺颂商祺！");
		document.add(title);
		

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(30f);
		title.setIndentationLeft(250);
		title.add(company+"\n");
		title.add("发出日期：" + map.get("CREATE_DATE"));
		document.add(title);
		
		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("附：租金调整表");
		document.add(title);
		
		
		// （6）往小节中写表格
		PdfPTable table = createTable(list);
		document.add(table);

		// --------------------------------内容----------------------------------------

		document.close();
	
	}

	private static PdfPTable createTable(List<Map<String,Object>> list) {
		// 创建表格对象
		float[] widths = { 8f, 20f, 16f, 16f };
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(95);

		table.setSpacingBefore(20f);
		fillTable(table, "期数", fontChinese);
		fillTable(table, "租金支付日", fontChinese);
		fillTable(table, "调整前租金", fontChinese);
		fillTable(table, "调整后租金", fontChinese);

		for (int i = 1; i <= list.size(); i++) {
			Map<String,Object> mapData = list.get(i - 1);
			fillTable(
					table,
					(mapData.get("PERIOD_NUM") != null && !mapData.get(
							"PERIOD_NUM").equals("")) ? mapData.get("PERIOD_NUM")
							.toString() : " ", moneyChinese);
			fillTable(table,
					(mapData.get("PAY_DATE") != null && !mapData.get("PAY_DATE")
							.equals("")) ? mapData.get("PAY_DATE").toString()
							: " ", moneyChinese);
			fillTable(
					table,
					(mapData.get("OLD_ZUJIN") != null && !mapData
							.get("OLD_ZUJIN").equals("")) ? mapData
							.get("OLD_ZUJIN").toString() : " ",
					moneyChinese);
			fillTable(table, (mapData.get("NEW_ZUJIN") != null && !mapData
					.get("NEW_ZUJIN").equals("")) ? mapData.get("NEW_ZUJIN")
					.toString() : " ", moneyChinese);

		}
		return table;
	}

	/**
	 * 往表格里添加内容
	 * 
	 * @param table
	 *            pdfpTbale表格
	 * @param obj
	 *            要添加的内容
	 * @param font
	 *            字体
	 */
	private static void fillTable(PdfPTable table, Object obj, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(obj + "", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		cell.setFixedHeight(20);
		table.addCell(cell);
	}

	
	/**
	 * 
	 * @param modelData
	 *            进口1
	 * @param ftlFileName
	 * @param ftlDirPath
	 * @return
	 */
	public static String getPdf(String fileName) {
		String tempPathsea = SkyEye.getConfig("file.path.temp");
		String tempPath = SkyEye.getConfig("file.path.temp");
		FileInputStream fio = null;

		String  dateString=DateUtil.getSysDate("yyyy-MM-dd");
		String date=dateString.substring(0, 4);
		tempPathsea+=File.separator+date;
		File file=new File(tempPathsea);
		 if(!file.exists()){
		 file.mkdirs();
		 }
		fio = getSealPdfStream(tempPath + File.separator + fileName, tempPathsea
				+ File.separator  + fileName, sealPathContrack,
				"发出日期：", sealPathWatemark);
		try {
			fio.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempPathsea
				+ File.separator  + fileName;
	}
	
	private static FileInputStream getSealPdfStream(String filePath,
			String sealPdfPath, String sealPath, String keywords,
			String sealPathWatemark) {

		FileInputStream fio = null;
		Shell shell = new Shell();
		

		shell.init(filePath, sealPdfPath, sealPath, true);

		// //////////////////////////////////////////// 添加水印
//		shell.addWatemarkByBmpOpen(sealPathWatemark, 10, 10);

//		System.out.println("添加骑缝章");
		boolean success =true;// shell.addMultiPageSealOpen(2, 300);
		shell.addSealOpen(keywords);
		shell.close();


		try {
			if (success)
				fio = new FileInputStream(new File(sealPdfPath));
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		}

		return fio;
	}

}

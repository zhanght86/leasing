package com.pqsoft.overdue.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.esa2000.Shell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.DateUtil;

public class assetUtilService {

	private static String fontpath = SkyEye
			.getRealPath("WEB-INF/res/simsun.ttc,1");
	private static String sealPathContrack = SkyEye
			.getRealPath("WEB-INF/res/hetong.dat");
	private static String sealPathWatemark = SkyEye
			.getRealPath("WEB-INF/res/jiamishuiyin.bmp");
	// 临时文件

	// private final Logger logger = Logger.getLogger(this.getClass());

	private static Font fontChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 12, Font.COURIER);
	private static Font boldChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 12, Font.BOLD);
	private static Font underlineChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 12, Font.UNDERLINE);
	private static Font titleChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 22, Font.BOLD);
	private static Font moneyChinese = FontFactory.getFont(fontpath,
			BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 9, Font.COURIER);

	private final Logger logger = Logger.getLogger(this.getClass());

	// private static Font fontChinese =
	// FontFactory.getFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,
	// 12, Font.COURIER);
	// private static Font boldChinese =
	// FontFactory.getFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,
	// 12, Font.BOLD);
	// private static Font underlineChinese =
	// FontFactory.getFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,
	// 12, Font.UNDERLINE);
	// private static Font titleChinese =
	// FontFactory.getFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,
	// 22, Font.BOLD);
	// private static Font moneyChinese =
	// FontFactory.getFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,
	// 9, Font.COURIER);
	//	
	// 预警
	public void exportWarningReportDoc(HttpServletResponse response, Map param) {
		String project_Code = "";
		param.put("targs", "—B08");
		param.put("targs1", "租金");
		param.put("targs2", "违约金");
		Map modelData = Dao.selectOne("asset.createPdfDateByPro_Id", param);

		Set set = modelData.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			Object value = modelData.get(key);
			modelData.put(key, (null == value) ? " " : value);
		}

		List dataList = Dao.selectList("asset.pdfDataDeatil", param);

		modelData.put("dataList", dataList);
		this.fileManagerPdfJoin1(response, modelData, 1);
	}

	// 出险
	public void exportWarningReportDoc1(HttpServletResponse response, Map param) {
		String project_Code = "";
		param.put("targs", "—B09");
		param.put("targs1", "租金");
		param.put("targs2", "违约金");
		Map modelData = Dao.selectOne("asset.createPdfDateByPro_Id", param);

		Set set = modelData.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			Object value = modelData.get(key);
			modelData.put(key, (null == value) ? " " : value);
		}

		List dataList = Dao.selectList("asset.pdfDataDeatil", param);

		modelData.put("dataList", dataList);
		this.fileManagerPdfJoin1(response, modelData, 2);
	}

	public void exportWarningReportDoc2(HttpServletResponse response, Map param) {
		String project_Code = "";
		param.put("targs", "—B38");
		Map modelData = Dao.selectOne("asset.createPdfDateByPro_Id", param);

		Set set = modelData.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			Object value = modelData.get(key);
			modelData.put(key, (null == value) ? " " : value);
		}

		List dataList = Dao.selectList("asset.pdfDataEquipment", param);

		modelData.put("dataList", dataList);
		this.fileManagerPdfJoin1(response, modelData, 3);
	}

	public void fileManagerPdfJoin1(HttpServletResponse response,
			Map<String, Object> map, int type) {
		String tempPath = SkyEye.getConfig("file.path.temp");
		String project_Code = "";
		if (map != null && map.get("FILE_CODE") != null
				&& !map.get("FILE_CODE").equals("")) {
			project_Code = map.get("FILE_CODE").toString();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// PDF名字的定义
		String pdfName = "";
		if (type == 1) {
			pdfName = project_Code + "-预警报告";
		} else if (type == 2) {
			pdfName = project_Code + "-出险报告";
		} else if (type == 3) {
			pdfName = project_Code + "-租赁物取回通知书";
		}

		String strFileName = pdfName + ".pdf";
		String filepath = tempPath + File.separator + strFileName;
		map.put("filePath", filepath);
		try {
			// 设置数据
			Map content = new HashMap();
			// 取出数据
			// 调用模型
			if (type == 1) {
				contentPdf1(map, baos);
			} else if (type == 2) {
				contentPdf2(map, baos);
			} else if (type == 3) {
				contentPdf3(map, baos);
			}

			File tempFile=new File(tempPath + File.separator + strFileName);
			baos.writeTo(new BufferedOutputStream(new FileOutputStream(
					tempFile)));
			baos.close();
			FileInputStream fis = getPdf(strFileName);

			//删除临时文件

//			System.out.println(tempFile.exists()+"22222222222222222222222@@@@");
//			System.out.println(tempPath + File.separator + strFileName);
			tempFile.delete();
//			tempFile.deleteOnExit();
			
			
			
			
			
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(strFileName.getBytes("gb2312"), "ISO8859-1"));
			ServletOutputStream os = response.getOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while ((n = fis.read(buf)) > 0) {
				os.write(buf, 0, n);
			}
			fis.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public static void contentPdf1(Map<String, Object> map, OutputStream baos)
			throws Exception {

		// 页面设置
		// Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小

		Document document = new Document(PageSize.A4, 48, 48, 60, 15);// 其余4个参数，设置了页面的4个边距

		PdfWriter.getInstance(document, baos);
		// 打开文档
		document.open();

		// 写入标题
		// --------------------------------内容----------------------------------------
		Paragraph title = new Paragraph("付款催告书", titleChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(1f);// 设置行间距
		document.add(title);

		title = new Paragraph("通知书编号：" + map.get("FILE_CODE"), fontChinese);// 抬头
		title.setSpacingBefore(2f);// 设置上面空白宽度
		title.setIndentationLeft(260);
		title.setAlignment(3);
		document.add(title);

		Chunk chunk = new Chunk("  " + map.get("CUST_NAME") + "  ",
				underlineChinese);
		title = new Paragraph(chunk);
		title.setFont(fontChinese);
		title.add("敬启");
		title.setSpacingBefore(20f);
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("因贵方未能完全按我公司与贵方于");
		String SIGNED_DATE = (map.get("SIGNED_DATE") != null && !map.get(
				"SIGNED_DATE").equals("")) ? map.get("SIGNED_DATE").toString()
				: " ";
		chunk = new Chunk(" " + SIGNED_DATE + " ", underlineChinese);
		title.add(chunk);
		title.add("签订的《融资租赁合同》（编号：");
		chunk = new Chunk("  " + map.get("PROJECT_CODE") + "  ",
				underlineChinese);
		title.add(chunk);
		title
				.add("）约定向我公司按期足额支付租金。我公司特此催告贵方，请贵方在收到本催告书后，迅速向我公司支付下列逾期应付租金以及违约金。违约金按实际发生额扣除，各期违约金计算的截止日期为实际收到各期逾期支付的租金之日。");

		document.add(title);

		// （6）往小节中写表格
		PdfPTable table = createTable(map);
		document.add(table);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title
				.add("按照《融资租赁合同》第十九条约定，当贵方发生迟延支付租金情形时，我公司可以停止贵方使用租赁物，解除《融资租赁合同》，取回租赁物。");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title
				.add("贵方收到本通知后应立即在本通知所附回执上填写相应日期并加盖公章(或签字)后寄回我公司。我公司若在10日内未收到贵方的回执，则本催告书的发出之日视为送达之日。");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(30f);
		title.setIndentationLeft(250);
		title.add("山重融资租赁有限公司\n");
		title.add("法定代表人（或授权代理人）：\n");
		title.add("发出日期：" + map.get("CREATE_DATE"));
		document.add(title);

		document.newPage();

		title = new Paragraph("《付款催告书》送达回执", boldChinese);
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setSpacingBefore(20f);
		document.add(title);

		title = new Paragraph("山重融资租赁有限公司  敬启", fontChinese);
		title.setSpacingBefore(20f);
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("本人已收到贵公司_____年__月__日的《付款催告书》(编号：" + map.get("FILE_CODE")
				+ ")，本人同意按《融资租赁合同》（编号：___________________）约定:\n");
		title.add("     在____年__月___日前向贵公司支付全部逾期应付租金以及违约金。");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(30f);
		title.setIndentationLeft(250);
		title.add("承租人：\n");
		title.add("签字（盖章）：\n");
		title.add("日期：____年__月__日");
		document.add(title);
		// --------------------------------内容----------------------------------------

		document.close();
	}

	public static void contentPdf2(Map<String, Object> map, OutputStream baos)
			throws Exception {

		// 页面设置
		Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小

		Document document = new Document(PageSize.A4, 48, 48, 60, 15);// 其余4个参数，设置了页面的4个边距

		PdfWriter.getInstance(document, baos);
		// 打开文档
		document.open();

		// 写入标题
		// --------------------------------内容----------------------------------------
		Paragraph title = new Paragraph("付款催告书(暨租赁物停止使用通知书)", titleChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(1f);// 设置行间距
		document.add(title);

		title = new Paragraph("通知书编号：" + map.get("FILE_CODE"), fontChinese);// 抬头
		title.setSpacingBefore(2f);// 设置上面空白宽度
		title.setIndentationLeft(260);
		title.setAlignment(3);
		document.add(title);

		Chunk chunk = new Chunk("  " + map.get("CUST_NAME") + "  ",
				underlineChinese);
		title = new Paragraph(chunk);
		title.setFont(fontChinese);
		title.add("敬启");
		title.setSpacingBefore(20f);
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("鉴于贵方未能完全按我公司与贵方于");
		String SIGNED_DATE = (map.get("SIGNED_DATE") != null && !map.get(
				"SIGNED_DATE").equals("")) ? map.get("SIGNED_DATE").toString()
				: " ";
		chunk = new Chunk(" " + SIGNED_DATE + " ", underlineChinese);
		title.add(chunk);
		title.add("日签订的《融资租赁合同》（编号：");
		chunk = new Chunk("  " + map.get("PROJECT_CODE") + "  ",
				underlineChinese);
		title.add(chunk);
		title
				.add("）约定向我公司按期足额支付租金。并且在我公司付款催告后，仍然未及时向我公司支付逾期租金。我公司特此催告贵方，请贵方在收到本催告书后，立即向我公司支付下列逾期应付租金以及违约金，并停止对融资租赁合同项下租赁物的使用。违约金按实际发生额扣除，各期违约金计算的截止日期为实际收到各期逾期支付的租金之日。");

		document.add(title);

		// （6）往小节中写表格
		PdfPTable table = createTable(map);
		document.add(table);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("按照《融资租赁合同》第十九条约定，我公司现要求贵方立即停止使用租赁物，直至上述逾期应付租金及违约金支付完毕。");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title
				.add("贵方收到本通知后立即在本通知所附回执上填写相应日期并加盖公章(或签字)后寄回我公司。我公司若在10日内未收到贵方的回执，则本催告书的发出之日视为送达之日。");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(30f);
		title.setIndentationLeft(250);
		title.add("山重融资租赁有限公司\n");
		title.add("法定代表人（或授权代理人）：\n");
		title.add("发出日期：" + map.get("CREATE_DATE"));
		document.add(title);

		document.newPage();

		title = new Paragraph("确认《付款催告书（暨租赁物停止使用通知书）》的回执", boldChinese);
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setSpacingBefore(20f);
		document.add(title);

		title = new Paragraph("山重融资租赁有限公司  敬启", fontChinese);
		title.setSpacingBefore(20f);
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("本人已收到贵公司_____年__月__日的《付款催告书（暨租赁物停止使用通知书）》（编号："
				+ map.get("FILE_CODE") + "）。");

		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("本人同意按《融资租赁合同》（编号：");
		chunk = new Chunk(map.get("PROJECT_CODE") + "", underlineChinese);
		title.add(chunk);
		title
				.add("）第十九条约定停止本人对该合同项下全部租赁物的使用，并在_____年___月____ 日前向贵公司支付全部逾期未付租金以及违约金，征得贵公司同意后，重新使用租赁物。");
		document.add(title);
		// --------------------------------内容----------------------------------------

		document.close();
	}

	public static void contentPdf3(Map<String, Object> map, OutputStream baos)
			throws Exception {

		// 页面设置
		Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小

		Document document = new Document(PageSize.A4, 48, 48, 60, 15);// 其余4个参数，设置了页面的4个边距

		PdfWriter.getInstance(document, baos);
		// 打开文档
		document.open();

		// 写入标题
		// --------------------------------内容----------------------------------------
		Paragraph title = new Paragraph("租赁物取回通知书", titleChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(1f);// 设置行间距
		document.add(title);

		title = new Paragraph("编号：" + map.get("FILE_CODE"), fontChinese);// 抬头
		title.setSpacingBefore(2f);// 设置上面空白宽度
		title.setIndentationLeft(260);
		title.setAlignment(3);
		document.add(title);

		Chunk chunk = new Chunk("   " + map.get("CUST_NAME") + "   ",
				underlineChinese);
		title = new Paragraph(chunk);
		title.setFont(fontChinese);
		title.add("钧鉴");
		title.setSpacingBefore(35f);
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("鉴于我公司于");
		String SIGNED_DATE = (map.get("SIGNED_DATE") != null && !map.get(
				"SIGNED_DATE").equals("")) ? map.get("SIGNED_DATE").toString()
				: " ";
		chunk = new Chunk(SIGNED_DATE + "", underlineChinese);
		title.add(chunk);
		title.add("与贵方签订的《融资租赁合同》（编号：");
		chunk = new Chunk(map.get("PROJECT_CODE") + "", underlineChinese);
		title.add(chunk);
		title
				.add("），因贵方违反了该《融资租赁合同》违约责任中相关条款的约定，请贵方收到本通知即日起，将该《融资租赁合同》项下全部租赁物，返还至我公司指定的受托人 ______________（地址：____________________________________________ ），如自收到本通知起三日内不返还，我司将授权受托人取回租赁物。");

		document.add(title);

		float[] widths = { 30f, 20f, 20f, 30f };
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(95);

		table.setSpacingBefore(20f);
		fillTable(table, "租赁物名称", fontChinese);
		fillTable(table, "型号", fontChinese);
		fillTable(table, "出厂编号", fontChinese);
		fillTable(table, "备注", fontChinese);

		List<List<String>> dataList = (List<List<String>>) map.get("dataList");

		for (int i = 1; i <= dataList.size(); i++) {
			Map mapData = (Map) dataList.get(i - 1);
			fillTable(table, (mapData.get("PRODUCT_NAME") != null && !mapData
					.get("PRODUCT_NAME").equals("")) ? mapData.get(
					"PRODUCT_NAME").toString() : " ", moneyChinese);
			fillTable(table, (mapData.get("SPEC_NAME") != null && !mapData.get(
					"SPEC_NAME").equals("")) ? mapData.get("SPEC_NAME")
					.toString() : " ", moneyChinese);
			fillTable(table,
					(mapData.get("WHOLE_ENGINE_CODE") != null && !mapData.get(
							"WHOLE_ENGINE_CODE").equals("")) ? mapData.get(
							"WHOLE_ENGINE_CODE").toString() : " ", moneyChinese);
			fillTable(table, "       ", moneyChinese);

		}

		document.add(table);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title
				.add("贵方收到本通知后，应立即在本通知所附回执上填写相应日期并签名（盖章）后寄回我公司。我公司若在十日内未收到贵方的回执，则本通知书的发出之日视为送达之日。。");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(30f);
		title.setIndentationLeft(250);
		title.add("山重融资租赁有限公司\n");
		title.add("法定代表人（或授权代理人）：\n");
		title.add("日期：_______年___月___日");
		document.add(title);

		document.newPage();

		title = new Paragraph("确认《租赁物取回通知书》的回执", boldChinese);
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setSpacingBefore(20f);
		document.add(title);

		title = new Paragraph("编号：" + map.get("FILE_CODE"), fontChinese);// 抬头
		title.setSpacingBefore(2f);// 设置上面空白宽度
		title.setIndentationLeft(260);
		title.setAlignment(3);
		document.add(title);

		title = new Paragraph("山重融资租赁有限公司:", fontChinese);
		title.setSpacingBefore(25f);
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(10f);
		title.setFirstLineIndent(25);
		title.add("本人已收到贵公司______年___月___日的《租赁物取回通知书》（编号："
				+ map.get("FILE_CODE") + "）。根据");
		chunk = new Chunk(SIGNED_DATE + "", underlineChinese);
		title.add(chunk);
		title.add("签订的（编号：");
		chunk = new Chunk(map.get("PROJECT_CODE") + "", underlineChinese);
		title.add(chunk);
		title
				.add("）《融资租赁合同》本人将在规定的时间内返还该合同项下的全部租赁物并向贵公司偿还全部逾期未付租金、违约金及其它应付款项。");
		document.add(title);

		title = new Paragraph();
		title.setFont(fontChinese);
		title.setSpacingBefore(30f);
		title.setIndentationLeft(250);
		title.add("承租人：\n");
		title.add("签字（盖章）：\n");
		title.add("日期：____年__月__日");
		// --------------------------------内容----------------------------------------

		document.close();
	}

	/**
	 * 创建表格对象
	 * 
	 * @param modelData
	 * @return
	 */

	private static PdfPTable createTable(Map modelData) {
		// 创建表格对象
		float[] widths = { 8f, 20f, 8f, 16f, 16f, 16f, 16f };
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(95);

		table.setSpacingBefore(20f);
		fillTable(table, "序号", fontChinese);
		fillTable(table, "项目", fontChinese);
		fillTable(table, "期次", fontChinese);
		fillTable(table, "应付日期", fontChinese);
		fillTable(table, "金额", fontChinese);
		fillTableCol(table, "违约金计算的起止日", fontChinese, 2);

		List<List<String>> dataList = (List<List<String>>) modelData
				.get("dataList");

		for (int i = 1; i <= dataList.size(); i++) {
			fillTable(table, i, moneyChinese);
			Map mapData = (Map) dataList.get(i - 1);
			fillTable(table, (mapData.get("ITEM_NAME") != null && !mapData.get(
					"ITEM_NAME").equals("")) ? mapData.get("ITEM_NAME")
					.toString() : " ", moneyChinese);
			fillTable(table, (mapData.get("PERIOD") != null && !mapData.get(
					"PERIOD").equals("")) ? mapData.get("PERIOD").toString()
					: " ", moneyChinese);
			fillTable(
					table,
					(mapData.get("BEGINNING_RECEIVE_DATA") != null && !mapData
							.get("BEGINNING_RECEIVE_DATA").equals("")) ? mapData
							.get("BEGINNING_RECEIVE_DATA").toString()
							: " ", moneyChinese);
			fillTable(table, (mapData.get("PAID_MONEY") != null && !mapData
					.get("PAID_MONEY").equals("")) ? mapData.get("PAID_MONEY")
					.toString() : " ", moneyChinese);
			fillTable(table, (mapData.get("START_DATE") != null && !mapData
					.get("START_DATE").equals("")) ? mapData.get("START_DATE")
					.toString() : " ", moneyChinese);
			fillTable(table, (mapData.get("END_DATE") != null && !mapData.get(
					"END_DATE").equals("")) ? mapData.get("END_DATE")
					.toString() : " ", moneyChinese);

		}
		fillTableCol(table, "合计", fontChinese, 4);
		fillTable(table, modelData.get("MONEY_ALL"), moneyChinese);
		fillTable(table, " ", moneyChinese);
		fillTable(table, " ", moneyChinese);
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
	 * 往表格里添加内容并合并列
	 * 
	 * @param table
	 *            pdfpTbale表格
	 * @param obj
	 *            要添加的内容
	 * @param font
	 *            字体
	 */
	private static void fillTableCol(PdfPTable table, Object obj, Font font,
			int i) {
		PdfPCell cell = new PdfPCell(new Phrase(obj + "", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		cell.setFixedHeight(20);
		cell.setColspan(i);
		table.addCell(cell);

	}


	/**
	 * B09出险报告 B08 预警报告 B38租赁物取回通知书
	 * 
	 * @param modelData
	 *            进口1
	 * @param ftlFileName
	 * @param ftlDirPath
	 * @return
	 */
	// 1111111111111111111111111
	public static FileInputStream getPdf(String fileName) {
		String tempPathsea = SkyEye.getConfig("file.path.temp");
		String tempPath = SkyEye.getConfig("file.path.temp");
		FileInputStream fio = null;

		String  dateString=DateUtil.getSysDate("yyyy-MM-dd");
		String date=dateString.substring(0, 4);
//		tempPathsea+=File.separator+date;
		tempPathsea+=File.separator;
		File file=new File(tempPathsea);
		 if(!file.exists()){
		 file.mkdirs();
		 }
		 
		 /**
		  * 齐江龙 2014-8-22注销电子签章功能
		  */
//		System.out.println(tempPathsea+"**********************");
//		fio = getSealPdfStream(tempPath + File.separator + fileName, tempPathsea
//				+ File.separator  + fileName, sealPathContrack,
//				"授权代理人）：", sealPathWatemark);
		 /**
		  * 齐江龙 2014-8-22注销电子签章功能
		  */
	
		
		 try {
				
					 fio = new FileInputStream(new File(tempPathsea+ File.separator  + fileName));
			} catch (FileNotFoundException e) {
		
				e.printStackTrace();
			}
		return fio;
		

	}

	// /**
	// * 处理签章PDF储存路径
	// * @param fileName
	// * @return
	// */
	// private static String getSealPdfPath(String fileName) {
	// // TODO Auto-generated method stub
	// String dateString=StringUtil.getSystemDate();
	// String[] dateUtils=dateString.split("-");
	// String sealPdfPath=
	// ResourceUtil.getSealPdfPath()+dateUtils[0]+"\\"+dateUtils[1]+"\\";
	// File file=new File(sealPdfPath);
	// if(!file.exists()){
	// file.mkdirs();
	// }
	//		
	// return sealPdfPath+=fileName+".pdf";
	// }
	// 22222
	private static FileInputStream getSealPdfStream(String filePath,
			String sealPdfPath, String sealPath, String keywords,
			String sealPathWatemark) {

		FileInputStream fio = null;
		Shell shell = new Shell();
		
//		 sealPdfPath="D:\\fileservice\\stleasing\\sflcgood\\temp\\aabbbbb.pdf";

		shell.init(filePath, sealPdfPath, sealPath, true);

		// //////////////////////////////////////////// 添加水印
//		System.out.println("添加水印");
		shell.addWatemarkByBmpOpen(sealPathWatemark, 10, 10);

//		System.out.println("添加骑缝章");
		boolean success = shell.addMultiPageSealOpen(2, 300);
		shell.addSealOpen(keywords);
		shell.close();

//		System.out.println(filePath);
//		System.out.println(sealPath);
//		System.out.println(sealPathWatemark);

		try {
			if (success)
				fio = new FileInputStream(new File(sealPdfPath));
//			System.out.println("dddddddddddeeerrttreesdddd");
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		}

		return fio;
	}

}

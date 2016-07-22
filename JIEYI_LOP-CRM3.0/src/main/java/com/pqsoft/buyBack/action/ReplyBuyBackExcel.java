package com.pqsoft.buyBack.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.pqsoft.overdue.service.OverdueService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.Util;

public class ReplyBuyBackExcel extends Reply {

	private Map<String, Object> m;

	public ReplyBuyBackExcel(Map<String, Object> param) {
		this.m = param;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void exec() {
		// 查询数据exportExcel
		List list = Dao.selectList("BuyBack.exportExcel", m);
		SkyEye.getResponse().reset();
		try {
			OutputStream os = SkyEye.getResponse().getOutputStream();
			SkyEye.getResponse().reset();// 清空输出流
			String filename = "回购通知书.xls";
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
			SkyEye.getResponse().setHeader("Content-disposition", "attachment; filename=" + filename);// 设定输出文件头
			SkyEye.getResponse().setContentType("application/msexcel");// 定义输出类型
			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
			int charNormal = 18;
			WritableFont oneFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);
			oneFont.setBoldStyle(WritableFont.BOLD);

			WritableCellFormat normalFormat = new WritableCellFormat(oneFont);
			normalFormat.setAlignment(Alignment.CENTRE);
			normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			normalFormat.setWrap(true); // 是否换行

			WritableCellFormat normalFormat2 = new WritableCellFormat();
			normalFormat2.setAlignment(Alignment.CENTRE);
			normalFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
			normalFormat2.setWrap(true); // 是否换行
			normalFormat2.setBackground(Colour.GRAY_25);// 设置背景色
			normalFormat2.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

			// 数据格式
			WritableCellFormat normalFormat3 = new WritableCellFormat();
			normalFormat3.setAlignment(Alignment.CENTRE);
			normalFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);
			normalFormat3.setWrap(true); // 是否换行
			normalFormat3.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

			WritableCellFormat normalRight = new WritableCellFormat();
			normalRight.setAlignment(Alignment.RIGHT);
			normalRight.setVerticalAlignment(VerticalAlignment.CENTRE);
			normalRight.setWrap(true); // 是否换行
			WritableCellFormat normalLeft = new WritableCellFormat();
			normalLeft.setAlignment(Alignment.LEFT);
			normalLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			normalLeft.setWrap(true); // 是否换行
			WritableCellFormat normalCentre = new WritableCellFormat();
			normalCentre.setAlignment(Alignment.CENTRE);
			normalCentre.setVerticalAlignment(VerticalAlignment.CENTRE);
			normalCentre.setWrap(true); // 是否换行
			for (int i = 0; i < list.size(); i++) {
				Map m = (Map) list.get(i);
				OverdueService ov = new OverdueService();
				double fine = ov.dueTemp(
						m.get("RENT_RECE") == null || "".equals(m.get("RENT_RECE")) ? 0 : Double.parseDouble(m.get("RENT_RECE") + ""),
						m.get("PENALTY_RECE") == null || "".equals(m.get("PENALTY_RECE")) ? 0 : Double.parseDouble(m.get("PENALTY_RECE") + ""),
						(int) getDaySub(getStringToday(), m.get("ACCOUNT_DATE") + ""));
				System.out.println(fine + "-------------------------------" + m.get("PRO_CODE"));
				fine = Util.formatDouble2(fine);
				String tmptitle = m.get("PRO_CODE") + ""; // 标题
				WritableSheet wsheet = wbook.createSheet(tmptitle, i); // sheet名称
				// normalRight.setBackground(Colour.GRAY_25) ;//设置背景色

				WritableCellFormat wcfFC = new WritableCellFormat(wfont);
				wcfFC.setBackground(Colour.AQUA);
				wsheet.setRowView(0, 1000);
				wsheet.mergeCells(0, 0, 3, 0);
				wsheet.addCell(new Label(0, 0, "回购通知书", normalFormat));
				int x = 0;
				int y = 0;
				int width = 6;
				wsheet.setColumnView(x + 0, 1 * width);
				wsheet.setColumnView(x + 1, 4 * width);
				wsheet.setColumnView(x + 2, 4 * width);
				wsheet.setColumnView(x + 3, 4 * width);
				wsheet.mergeCells(0, 1, 3, 1);
				wsheet.addCell(new Label(0, 1, "编号：" + m.get("PRO_CODE") + "--B19", normalRight));
				wsheet.mergeCells(0, 2, 3, 2);
				wsheet.addCell(new Label(0, 2, "致：  " + m.get("SUPPLIER_NAMES"), normalLeft));
				wsheet.setRowView(3, 800);
				wsheet.mergeCells(0, 3, 3, 3);
				wsheet.addCell(new Label(0, 3, "      根据我公司与" + m.get("SUPPLIER_NAMES") + "（以下简称”贵公司“）于"
						+ "______________签订的《业务协定书》（编号：_____________________ ），请贵公" + "司按下表的回购期限、回购价款，回购下表所示《租赁合同》的租赁物。", normalLeft));

				wsheet.setRowView(4, 600);
				wsheet.mergeCells(0, 4, 3, 4);
				wsheet.addCell(new Label(0, 4, "      租赁物的所有权、租赁债权等及/合格证或发票，在贵公司全额付清本" + "通知书规定的回购价款时，从我公司转移给贵公司。", normalLeft));

				wsheet.setRowView(5, 900);
				wsheet.mergeCells(0, 5, 3, 5);
				wsheet.addCell(new Label(0, 5, "山重融资租赁有限公司\r\n" + parseDate(m.get("CREATE_DATE") + ""), normalCentre));

				// ---------------------------------------------表格内容
				wsheet.addCell(new Label(0, 6, "附表", normalFormat3));
				wsheet.addCell(new Label(1, 6, "项目", normalFormat3));
				wsheet.addCell(new Label(2, 6, "说明", normalFormat3));
				wsheet.addCell(new Label(3, 6, "金额", normalFormat3));

				wsheet.addCell(new Label(0, 7, " ", normalFormat3));
				wsheet.addCell(new Label(1, 7, "结算日", normalFormat3));
				wsheet.addCell(new Label(2, 7, parseDate(m.get("ACCOUNT_DATE") + ""), normalFormat3));
				wsheet.addCell(new Label(3, 7, " ", normalFormat3));
				wsheet.mergeCells(0, 8, 0, 16);
				wsheet.addCell(new Label(0, 8, "融资租赁合同信息", normalFormat3));

				wsheet.addCell(new Label(1, 8, "承租人", normalFormat3));
				wsheet.addCell(new Label(2, 8, m.get("NAME") + "", normalFormat3));
				wsheet.addCell(new Label(3, 8, " ", normalFormat3));

				wsheet.addCell(new Label(1, 9, "融资租赁合同号", normalFormat3));
				wsheet.addCell(new Label(2, 9, m.get("PRO_CODE") + "--" + m.get("TPM_CODE"), normalFormat3));
				wsheet.addCell(new Label(3, 9, " ", normalFormat3));

				wsheet.addCell(new Label(1, 10, "租赁物名称", normalFormat3));
				wsheet.addCell(new Label(2, 10, m.get("PRODUCT_NAMES") + "", normalFormat3));
				wsheet.addCell(new Label(3, 10, " ", normalFormat3));

				wsheet.addCell(new Label(1, 11, "型号", normalFormat3));
				wsheet.addCell(new Label(2, 11, m.get("SPEC_NAME") + "", normalFormat3));
				wsheet.addCell(new Label(3, 11, " ", normalFormat3));

				wsheet.addCell(new Label(1, 12, "出厂编号", normalFormat3));
				wsheet.addCell(new Label(2, 12, m.get("WHOLE_ENGINE_CODES") + "", normalFormat3));
				wsheet.addCell(new Label(3, 12, " ", normalFormat3));

				wsheet.addCell(new Label(1, 13, "租赁物购买价款", normalFormat3));
				wsheet.addCell(new Label(2, 13, " ", normalFormat3));
				wsheet.addCell(new Label(3, 13, m.get("LEASE_TOPRIC") + "", normalFormat3));

				wsheet.addCell(new Label(1, 14, "租赁期限", normalFormat3));
				wsheet.addCell(new Label(2, 14,
						(m.get("LEASE_TERM") == null || "".equals(m.get("LEASE_TERM")) ? 0 : Integer.parseInt(m.get("LEASE_TERM") + ""))
								* (m.get("LEASE_PERIOD") == null || "".equals(m.get("LEASE_PERIOD")) ? 1 : Integer.parseInt(m.get("LEASE_PERIOD")
										+ "")) + "", normalFormat3));
				wsheet.addCell(new Label(3, 14, " ", normalFormat3));

				wsheet.addCell(new Label(1, 15, "起租日", normalFormat3));
				wsheet.addCell(new Label(2, 15, parseDate(m.get("DELIVER_DATE") + ""), normalFormat3));
				wsheet.addCell(new Label(3, 15, " ", normalFormat3));

				wsheet.addCell(new Label(1, 16, "每期租金", normalFormat3));
				wsheet.addCell(new Label(2, 16, m.get("PAYMENT_MODE") + "", normalFormat3));
				wsheet.addCell(new Label(3, 16, " ", normalFormat3));

				// 计算SUM_ZJ
				double SUM_ZJ = m.get("SUM_ZJ") == null || "".equals(m.get("SUM_ZJ")) ? 0 : Double.parseDouble(m.get("SUM_ZJ") + "");
				double START_ZJ = m.get("START_ZJ") == null || "".equals(m.get("START_ZJ")) ? 0 : Double.parseDouble(m.get("START_ZJ") + "");
				double DEPOSIT = m.get("DEPOSIT") == null || "".equals(m.get("DEPOSIT")) ? 0 : Double.parseDouble(m.get("DEPOSIT") + "");
				double LEAVE = m.get("LEAVE") == null || "".equals(m.get("LEAVE")) ? 0 : Double.parseDouble(m.get("LEAVE") + "");
				double BEGINNING_PAID = m.get("BEGINNING_PAID") == null || "".equals(m.get("BEGINNING_PAID")) ? 0 : Double.parseDouble(m
						.get("BEGINNING_PAID") + "");

				wsheet.mergeCells(0, 17, 0, 24);
				wsheet.addCell(new Label(0, 17, "回购价款计算方式", normalFormat3));
				wsheet.addCell(new Label(1, 17, "（1）租金总额", normalFormat3));
				wsheet.addCell(new Label(2, 17, "起租租金+" + m.get("LEASE_TERM") + "期租金合计", normalFormat3));
				wsheet.addCell(new Label(3, 17, SUM_ZJ + START_ZJ + "", normalFormat3));

				wsheet.addCell(new Label(1, 18, "（2）已收租金总额", normalFormat3));
				wsheet.addCell(new Label(2, 18, "起租租金+" + m.get("BEGINNING_NUM") + "期租金合计+" + "保证金冲抵" + DEPOSIT + "元+留购价款冲抵" + LEAVE + "元",
						normalFormat3));
				wsheet.addCell(new Label(3, 18, START_ZJ + BEGINNING_PAID + DEPOSIT + LEAVE + "", normalFormat3));

				wsheet.addCell(new Label(1, 19, "（3）剩余租金总额", normalFormat3));
				wsheet.addCell(new Label(2, 19, "（1）-（2）", normalFormat3));
				wsheet.addCell(new Label(3, 19, (SUM_ZJ + START_ZJ) - (START_ZJ + BEGINNING_PAID + DEPOSIT + LEAVE) + "", normalFormat3));

				wsheet.addCell(new Label(1, 20, "（4）到期未付租金违约金", normalFormat3));
				wsheet.addCell(new Label(2, 20, "截止" + m.get("ACCOUNT_DATE"), normalFormat3));
				wsheet.addCell(new Label(3, 20, fine + "", normalFormat3));

				wsheet.addCell(new Label(1, 21, "（5）未到期租金利息", normalFormat3));
				wsheet.addCell(new Label(2, 21, "从第" + m.get("UNPAID_MIN_NUM") + "期到第" + m.get("LEASE_TERM") + "期", normalFormat3));
				wsheet.addCell(new Label(3, 21, m.get("UNPAID_INTEREST") + "", normalFormat3));

				wsheet.addCell(new Label(1, 22, "（6）留购价款", normalFormat3));
				wsheet.addCell(new Label(2, 22, " ", normalFormat3));
				wsheet.addCell(new Label(3, 22, LEAVE + "", normalFormat3));

				wsheet.addCell(new Label(1, 23, "（7）其他应付款项", normalFormat3));
				wsheet.addCell(new Label(2, 23, " ", normalFormat3));
				wsheet.addCell(new Label(3, 23, "0", normalFormat3));

				double total = (SUM_ZJ + START_ZJ)
						- (START_ZJ + BEGINNING_PAID + DEPOSIT + LEAVE)
						+ fine
						+ LEAVE
						- (m.get("UNPAID_INTEREST") == null || "".equals(m.get("UNPAID_INTEREST")) ? 0 : Double.parseDouble(m.get("UNPAID_INTEREST")
								+ ""));
				wsheet.addCell(new Label(1, 24, "（8）合计", normalFormat3));
				wsheet.addCell(new Label(2, 24, "（3+4+6+7-5）", normalFormat3));
				wsheet.addCell(new Label(3, 24, total + "", normalFormat3));

				wsheet.mergeCells(0, 25, 3, 25);
				wsheet.mergeCells(0, 26, 3, 26);
				wsheet.mergeCells(0, 27, 3, 29);
				wsheet.mergeCells(0, 30, 3, 30);
				wsheet.mergeCells(0, 31, 3, 31);
				wsheet.mergeCells(0, 32, 3, 32);
				wsheet.addCell(new Label(0, 26, "《回购通知书》回执", normalCentre));
				wsheet.addCell(new Label(0, 27, "    针对上述租赁物，我司决定办理回购手续。按照贵、我公司业务协定书等相关文书" + "规定，我公司应该承担的回购价款为" + total + "。此回购价款我公司将于"
						+ m.get("ACCOUNT_DATE") + "之前，电汇到贵公司制定账户里面" + "，并承诺接受贵公司开具的发票。", normalLeft));
				wsheet.addCell(new Label(0, 30, "                                                            供应商（公章）：", normalLeft));
				wsheet.addCell(new Label(0, 31, "                                       法定代表人（或授权代理人）：", normalLeft));
				wsheet.addCell(new Label(0, 32, "                                                                  日期：       年       月        日",
						normalLeft));
			}

			wbook.write(); // 写入文件
			wbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				SkyEye.getResponse().getOutputStream();
			} catch (IOException e) {}
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 *         long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyy-mm-dd
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String parseDate(String date) {
		return date.substring(0, 4) + "年" + date.substring(5, 7) + "月" + date.substring(8, 10) + "日";
	}
}

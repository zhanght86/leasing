package com.pqsoft.analysisBySynthesis.service;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

/**
 * @author zhhy E-mail:
 * @version creditTime：2011-12-12 下午04:02:54
 *          description: 模板单元格样式
 */

public class CellFontUtil {
	private static int headCharNormal = 24;		// 头部字体大小
	private static int charNormal = 12;		// 一般字体大小
	private static int headCharNormal1 = 18;		// 头部字体大小

	/**
	 * 设置头部字体样式
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getHeadFont() throws Exception {
		WritableFont headFont = new WritableFont(WritableFont.createFont("黑体"), headCharNormal);// 设置文字样式
		headFont.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat headCellFormat = new WritableCellFormat(headFont);	// 设置单元格样式
		headCellFormat.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		headCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		headCellFormat.setWrap(false);	// 是否换行

		return headCellFormat;
	}

	/**
	 * 设置头部字体样式 带换行
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getHeadFont11() throws Exception {
		WritableFont headFont = new WritableFont(WritableFont.createFont("黑体"), headCharNormal);// 设置文字样式
		headFont.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat headCellFormat = new WritableCellFormat(headFont);	// 设置单元格样式
		headCellFormat.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		headCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		headCellFormat.setWrap(true);	// 是否换行

		return headCellFormat;
	}

	public static WritableCellFormat getHeadFont1() throws Exception {
		WritableFont headFont = new WritableFont(WritableFont.createFont("黑体"), headCharNormal1);// 设置文字样式
		headFont.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat headCellFormat = new WritableCellFormat(headFont);	// 设置单元格样式
		headCellFormat.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		headCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		headCellFormat.setWrap(false);	// 是否换行

		return headCellFormat;
	}

	/**
	 * 设置字体样式居中
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getFontAlignCenter() throws Exception {
		// 设置普通文字样式--居中

		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormat = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormat.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormat.setWrap(false);	// 是否换行

		return cellFormat;
	}

	public static WritableCellFormat getFontAlignCenter2() throws Exception {
		// 设置普通文字样式--居中

		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormat = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormat.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormat.setWrap(true);	// 是否换行

		return cellFormat;
	}

	public static WritableCellFormat getFontAlignCenter1() throws Exception {
		// 设置普通文字样式--居中

		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormat = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormat.setAlignment(Alignment.RIGHT);		// 水平对齐方式
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormat.setWrap(false);	// 是否换行

		return cellFormat;
	}

	/**
	 * 设置字体样式居左
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getFontAlignLeft() throws Exception {

		// 设置普通文字样式--居左
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatLeft = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatLeft.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式
		cellFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatLeft.setWrap(false);	// 是否换行
		return cellFormatLeft;
	}

	/**
	 * 设置字体样式居左带换行
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getFontAlignLeft11() throws Exception {

		// 设置普通文字样式--居左
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatLeft = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatLeft.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式
		cellFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatLeft.setWrap(true);	// 是否换行
		return cellFormatLeft;
	}

	public static WritableCellFormat getFontAlignLeft1() throws Exception {

		// 设置普通文字样式--居左
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatLeft = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatLeft.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式
		cellFormatLeft.setBorder(Border.NONE, BorderLineStyle.THIN);
		cellFormatLeft.setWrap(false);	// 是否换行
		return cellFormatLeft;
	}

	public static WritableCellFormat getFontAlignLeft2() throws Exception {

		// 设置普通文字样式--居左
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatLeft = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatLeft.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式
		cellFormatLeft.setBorder(Border.LEFT, BorderLineStyle.THIN);
		cellFormatLeft.setWrap(false);	// 是否换行
		return cellFormatLeft;
	}

	public static WritableCellFormat getFontAlignLeft4() throws Exception {

		// 设置普通文字样式--居左
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatLeft = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatLeft.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式
		cellFormatLeft.setWrap(false);	// 是否换行
		return cellFormatLeft;
	}

	public static WritableCellFormat getFontAlignLeft5() throws Exception {

		// 设置普通文字样式--居左
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatLeft = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatLeft.setAlignment(Alignment.RIGHT);		// 水平对齐方式
		cellFormatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式
		cellFormatLeft.setWrap(false);	// 是否换行
		return cellFormatLeft;
	}

	public static WritableCellFormat getFontAlignLeft3() throws Exception {

		// 设置普通文字样式--居左
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatLeft = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatLeft.setAlignment(Alignment.RIGHT);		// 水平对齐方式
		cellFormatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式
		cellFormatLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		cellFormatLeft.setWrap(false);	// 是否换行
		return cellFormatLeft;
	}

	/**
	 * 无边框，字体变大
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getFontBigSize() throws Exception {
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormat = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormat.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式

		cellFormat.setWrap(false);	// 是否换行
		return cellFormat;
	}

	/**
	 * 无边框，字体变黑
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getFontBlack() throws Exception {
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("黑体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormat = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormat.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐方式

		cellFormat.setWrap(false);	// 是否换行
		return cellFormat;
	}

	/**
	 * 设置字体样式居右
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getFontAlignRight() throws Exception {
		// 设置普通文字样式--居右
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatRight = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatRight.setAlignment(Alignment.RIGHT);		// 水平对齐方式
		cellFormatRight.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);		// 上下左右边
																		// ，线为实线
		cellFormatRight.setWrap(false);	// 是否换行
		return cellFormatRight;
	}

	/**
	 * 单元格只含有右边框
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 */
	public static WritableCellFormat getCellHaveRightBody() throws Exception {
		// 设置普通文字样式--居右
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatRight = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatRight.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		cellFormatRight.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormatRight.setBorder(Border.RIGHT, BorderLineStyle.THIN);		// 上下左右边
																		// ，线为实线
		cellFormatRight.setWrap(false);	// 是否换行
		return cellFormatRight;

	}

	public static WritableCellFormat getCellHaveRightBody1() throws Exception {
		// 设置普通文字样式--居右
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatRight = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatRight.setAlignment(Alignment.LEFT);		// 水平对齐方式
		cellFormatRight.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormatRight.setBorder(Border.RIGHT, BorderLineStyle.THIN);		// 上下左右边
																		// ，线为实线
		cellFormatRight.setBorder(Border.LEFT, BorderLineStyle.THIN);		// 上下左右边
																		// ，线为实线
		cellFormatRight.setWrap(false);	// 是否换行
		return cellFormatRight;

	}

	/**
	 * 单元格只含有下边框
	 * 
	 * @author zhanghengyu
	 *         功能：
	 * @return
	 * @throws Exception
	 */
	public static WritableCellFormat getCellHaveBottomBody() throws Exception {
		// 设置普通文字样式--居右
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatRight = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatRight.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		cellFormatRight.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormatRight.setBorder(Border.BOTTOM, BorderLineStyle.THIN);		// 上下左右边
																		// ，线为实线
		cellFormatRight.setWrap(false);	// 是否换行
		return cellFormatRight;
	}

	public static WritableCellFormat getCellHaveBottomBody11() throws Exception {
		// 设置普通文字样式--居右
		WritableFont genuralFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);// 设置文字样式
		WritableCellFormat cellFormatRight = new WritableCellFormat(genuralFont);	// 设置单元格样式
		cellFormatRight.setAlignment(Alignment.CENTRE);		// 水平对齐方式
		cellFormatRight.setVerticalAlignment(VerticalAlignment.CENTRE);// 水平对齐方式
		cellFormatRight.setBorder(Border.TOP, BorderLineStyle.THIN);		// 上下左右边
																		// ，线为实线
		cellFormatRight.setWrap(false);	// 是否换行
		return cellFormatRight;
	}

}
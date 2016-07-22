package com.pqsoft.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.util.UTIL;

public class ExcelUtilMoreRow extends Reply{
	private Map<String, Object> m;
	private String fileName;
	//private File file;

	public ExcelUtilMoreRow(Map<String, Object> param) {
		this.m = param;
		this.fileName = param.get("FILE_NAME").toString();
//		//根据param中的path下载File为变量file赋值
//		try {
//			file = new File(param.get("FILE_PATH").toString());
//		} catch (Exception e) {
//			throw new ActionException("没有找到指定的合同模板");
//		}
	}

	@Override
	public void exec() {
		// 查询数据exportExcel
		//List list = Dao.selectList("BuyBack.exportExcel", m);
		SkyEye.getResponse().reset();
		try {
			OutputStream os = SkyEye.getResponse().getOutputStream();
			SkyEye.getResponse().reset();// 清空输出流
			String filename = fileName ;
			filename = new String(filename.getBytes("gbk"), "ISO8859-1");
			SkyEye.getResponse().setHeader("Content-disposition", "attachment; filename=" + filename);// 设定输出文件头
			SkyEye.getResponse().setContentType("application/msexcel");// 定义输出类型
			String PATH = m.get("PATH")+"";
			try {
				PoiExcelMoreRowUtil.excelTemplateExcle(UTIL.RES.getResource("classpath:file/"+PATH).getInputStream(), os, m);
			} catch (Exception e) {
				throw new ActionException("未找到模板文件",e);
			}
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

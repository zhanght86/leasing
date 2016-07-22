package com.pqsoft.buyBack.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.pqsoft.buyBack.service.BuyBackService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.Util;

public class BatchExcelTest extends Reply {

	private Map<String, Object> m;
	private String fileName = "回购通知书(预警).zip";

	// private File file;

	public BatchExcelTest(Map<String, Object> param) {
		this.m = param;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public void exec() {
		// 查询数据exportExcel
		// List list = Dao.selectList("BuyBack.exportExcel", m);
		SkyEye.getResponse().reset();
		try {
			OutputStream os = SkyEye.getResponse().getOutputStream();
			SkyEye.getResponse().reset();// 清空输出流
			String filename = fileName;
			filename = new String(filename.getBytes("gbk"), "ISO8859-1");
			SkyEye.getResponse().setHeader("Content-disposition","attachment; filename=" + filename);// 设定输出文件头
			SkyEye.getResponse().setContentType("application/msexcel");// 定义输出类型
			String PATH = m.get("PATH") + "";

			try {
				// PoiExcelUtil.excelTemplateExcle(UTIL.RES.getResource("classpath:file/"+PATH).getInputStream(),
				// os, m);
				List list = Dao.selectList("BuyBack.butchWarning_pay_code", m);
				
				
				ZipOutputStream out = null;
				byte[] buffer = new byte[2048];
				//ByteArrayOutputStream baos = new ByteArrayOutputStream();
				String path = SkyEye.getConfig("file.path.temp")+"/"+new Date().getTime()+"/";
//				for (int i = 0; i < list.size(); i++) {
//					HSSFWorkbook wb = new HSSFWorkbook(UTIL.RES.getResource("classpath:file/" + PATH).getInputStream());
//					Map map = (Map)list.get(i);
//					map.put("PAYLIST_CODE", map.get("PAY_CODE"));
//					getBatchExcel(map);
//					m.putAll(map);
//					PoiExcelUtil.doExcelTemplateMerge(wb, m);
//					String name = (String) m.get("SUPPLIER_NAMES")+i+".xls";
//					File f = new File(path);
//					f.mkdir();
//					FileOutputStream fo = new FileOutputStream(new File(path+name));
//					wb.write(fo);
//					//wb.write(out);
//					fo.close();
//				}
				
				//从之前写到磁盘上的文件打包下载
				File ft = new File("C:\\fileservice\\stleasing\\sflcgood\\temp\\1394768315610");
				File[] fs = ft.listFiles();
				for (int i = 0; i < fs.length; i++) {
					out = new ZipOutputStream(os);
					String name = fs[i].getName();
					FileInputStream fis = new FileInputStream("C:\\fileservice\\stleasing\\sflcgood\\temp\\1394768315610\\"+name);
					int len;
					out.putNextEntry(new ZipEntry(name));
					out.setEncoding("GBK");
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					fis.close();
					out.closeEntry();
				}
				//os.write(baos.toByteArray());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ActionException("未找到模板文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				SkyEye.getResponse().getOutputStream();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);
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
		return date.substring(0, 4) + "年" + date.substring(5, 7) + "月"
				+ date.substring(8, 10) + "日";
	}

	@SuppressWarnings("unchecked")
	public static Map getBatchExcel(Map param) {
		BuyBackService service1 = new BuyBackService();
		param.putAll(service1.selectRepoData11(param));// 模版中需要的业务数据
		{
			// 计算excel数据
			Double ZJ_ZJ = 0.00;// 租金总计
			Double YS_ZJ_ZE = 0.00;// 已收租金总额
			Double SY_ZJ_ZE = 0.00;// 剩余租金总额
			Double HJ = 0.00;// 合计
			Double START_ZJ = 0.00;// 起租租金
			Double SUM_ZJ = 0.00;// 租金总计
			Double DEPOSIT = 0.00;// 保证金
			Double LEAVE = 0.00;// 留购价
			Double RENT_RECE = 0.00;// 到期未付租金违约金
			Double QT_FY = 0.00;// 其他费用合计
			Double UNPAID_INTEREST = 0.00;// 未到期租金利息
			Double PENALTY_RECE = 0.00;// 逾期金额
			Double LEASE_TOPRIC = 0.00;// 租赁物总价值
			Double BEGINNING_PAID = 0.00;// 已还租金
			int WD_ZJ_QC = 0;// 未到租金期次
			int BEGINNING_NUM = 0;// 已还的最大期次

			if (param.containsKey("PENALTY_RECE")
					&& !param.get("PENALTY_RECE").equals("")) {
				PENALTY_RECE = Double.parseDouble(param.get("PENALTY_RECE")
						.toString());
				PENALTY_RECE = Util.formatDouble2(PENALTY_RECE);
			}
			param.put("PENALTY_RECE", PENALTY_RECE);// 逾期金额

			if (param.containsKey("LEASE_TOPRIC")
					&& !param.get("LEASE_TOPRIC").equals("")) {
				LEASE_TOPRIC = Double.parseDouble(param.get("LEASE_TOPRIC")
						.toString());
			}
			param.put("LEASE_TOPRIC", LEASE_TOPRIC);// 租赁物总价值

			if (param.containsKey("BEGINNING_PAID")
					&& !param.get("BEGINNING_PAID").equals("")) {
				BEGINNING_PAID = Double.parseDouble(param.get("BEGINNING_PAID")
						.toString());
			}
			param.put("BEGINNING_PAID", BEGINNING_PAID);// 已还租金

			if (param.containsKey("START_ZJ")
					&& !param.get("START_ZJ").equals("")) {
				START_ZJ = Double.parseDouble(param.get("START_ZJ").toString());
			}
			param.put("START_ZJ", START_ZJ);// 起租租金

			if (param.containsKey("SUM_ZJ") && !param.get("SUM_ZJ").equals("")) {
				SUM_ZJ = Double.parseDouble(param.get("SUM_ZJ").toString());
			}
			param.put("SUM_ZJ", SUM_ZJ);// 租金总计

			ZJ_ZJ = START_ZJ + SUM_ZJ;
			param.put("ZJ_ZJ", ZJ_ZJ);// 租金总计

			if (param.containsKey("DEPOSIT")
					&& !param.get("DEPOSIT").equals("")) {
				DEPOSIT = Double.parseDouble(param.get("DEPOSIT").toString());
			}
			if (DEPOSIT > 0) {
				param.put("DEPOSIT", "元+保证金" + DEPOSIT);// 保证金
				param.put("is_bzj", "+保证金冲抵");
			}
			if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
				LEAVE = Double.parseDouble(param.get("LEAVE").toString());
			}
			param.put("LEAVE", LEAVE);// 留购价

			double HG_LEAVE = param.get("HG_LEAVE") == null
					|| "".equals(param.get("HG_LEAVE")) ? 0 : Double
					.parseDouble(param.get("HG_LEAVE") + "");
			param.put("HG_LEAVE", HG_LEAVE);

			YS_ZJ_ZE = BEGINNING_PAID + START_ZJ + LEAVE + DEPOSIT;
			param.put("YS_ZJ_ZE", YS_ZJ_ZE);// 已收租金总额

			SY_ZJ_ZE = ZJ_ZJ - YS_ZJ_ZE;
			param.put("SY_ZJ_ZE", SY_ZJ_ZE);// 剩余租金总额

			if (param.containsKey("RENT_RECE")
					&& !param.get("RENT_RECE").equals("")) {
				RENT_RECE = Double.parseDouble(param.get("RENT_RECE")
						.toString());
			}
			param.put("RENT_RECE", RENT_RECE);// 到期未付租金违约金

			if (param.containsKey("QT_FY") && !param.get("QT_FY").equals("")) {
				QT_FY = Double.parseDouble(param.get("QT_FY").toString());
			}
			param.put("QT_FY", QT_FY);// 其他费用合计

			if (param.containsKey("UNPAID_INTEREST1")
					&& !param.get("UNPAID_INTEREST1").equals("")) {
				UNPAID_INTEREST = Double.parseDouble(param.get(
						"UNPAID_INTEREST1").toString());
			}
			param.put("UNPAID_INTEREST", UNPAID_INTEREST);// 未到期租金利息

			HJ = SY_ZJ_ZE + PENALTY_RECE + HG_LEAVE + QT_FY - UNPAID_INTEREST;
			HJ = Util.formatDouble2(HJ);
			param.put("HJ", HJ);// 合计

			if (param.containsKey("BEGINNING_NUM")
					&& !param.get("BEGINNING_NUM").equals("")) {
				BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM")
						.toString());// 已还的最大期次
			}
			WD_ZJ_QC = BEGINNING_NUM + 1;
			param.put("WD_ZJ_QC", WD_ZJ_QC);// 未到租金期次

			// -----------------------租赁期限
			param.put("ZLQX", (param.get("LEASE_PERIOD") == null
					|| "".equals(param.get("LEASE_PERIOD")) ? 1 : Integer
					.parseInt(param.get("LEASE_PERIOD") + ""))
					* (param.get("LEASE_TERM") == null
							|| "".equals(param.get("LEASE_TERM")) ? 1 : Integer
							.parseInt(param.get("LEASE_TERM") + "")) + "");
			// 顺道跟新下回购表（保证数据的准确性）
			Dao.update("BuyBack.buyBackNormal23", param);
			param.put("FILE_NAME", param.get("NAME") + ""
					+ param.get("PRO_CODE") + param.get("FILE_NAME"));
		}
		return param;
	}
}

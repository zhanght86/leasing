package com.pqsoft.util;

import java.io.IOException;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.ILog;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.filter.ResMime;

/**
 * @ClassName: ReplyExcel
 * @Description: (excel下载)
 * @author 程龙达
 * @date 2013-9-5 上午10:46:17
 */
public class ReplyExcel extends Reply {

	private ILog log;
	private Excel excel;

	public ReplyExcel(Excel excel) {
		this.excel = excel;
	}

	public ReplyExcel(Excel excel, String fileName) {
		this.excel = excel;
		this.excel.setName(fileName);
	}

	public ReplyExcel addOp(ILog log) {
		this.log = log;
		return this;
	}

	@Override
	public void exec() {
		try {

			// File file = new File("C:\\a.xls");
			// WritableWorkbook book = this.excel.parseExcel(file);

			SkyEye.getResponse().reset();
			this.excel.write2Stream(SkyEye.getResponse().getOutputStream());
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader("Content-Disposition",
					new StringBuilder("attachment;filename=").append(new String(excel.getName().getBytes("GB2312"), "ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xls"));

			this.excel.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				SkyEye.getResponse().getOutputStream().close();
			} catch (IOException e) {}
		}
	}

	@Override
	public ILog getLog() {
		return this.log;
	}

}

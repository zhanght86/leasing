package com.pqsoft.manufacturerApproval.action;

/**
 *  用于根据vm 生成 word 文件
 *  @author 韩晓龙
 */
import java.io.IOException;

import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;

public class ReplyWordByVM extends Reply {

	private String str = "";// 内容
	private String filename = "";// 文件名

	public ReplyWordByVM(String vm, String filename) {
		this.str = vm.toString();
		this.filename = filename;
	}

	@Override
	public void exec() {
		SkyEye.getResponse().reset();

		try {
			SkyEye.getResponse().reset();// 清空输出流

			// String newFileName = filename + ".doc";
			String newFileName = filename + ".doc";
			newFileName = new String(newFileName.getBytes("UTF-8"), "ISO8859-1");
			SkyEye.getResponse().setHeader("Content-disposition",
					"attachment; filename=" + newFileName);// 设定输出文件头
			SkyEye.getResponse().setContentType("application/msword");// 定义输出类型

			SkyEye.getResponse().getWriter().write(str);
			SkyEye.getResponse().getWriter().flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				SkyEye.getResponse().getWriter().close();
			} catch (IOException e) {
			}
		}
	}
}
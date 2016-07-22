package com.pqsoft.consider.ueditor.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.consider.ueditor.ActionEnter;

public class ControllerAction extends Action{
	
	@Override
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply execute() {
		HttpServletRequest request = SkyEye.getRequest();
		try {
			request.setCharacterEncoding( "utf-8" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = SkyEye.getConfig("file.path");
		ActionEnter action =new ActionEnter(request, rootPath);
		return new ReplyHtml(action.exec());
	}
	/*
	 * ueditor编辑器 下载图片
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply toShowAll(){
		Map<String, Object> param=_getParameters();
		String rootPath = SkyEye.getConfig("file.path");
		String url= param.get("PATH").toString();
		File file = new File(rootPath+url);
		return new ReplyFile(file, file.getName());
	}
	
	
}

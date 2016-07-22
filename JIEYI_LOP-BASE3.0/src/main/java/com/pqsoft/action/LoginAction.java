package com.pqsoft.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.oauth2.util.GOauth2Core;

import com.pqsoft.skyeye.SessAttr;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyMobile;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.util.UTIL;

public class LoginAction extends Action {

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public void doLogout() {
		Security.logout();
		try {
			if (Security.isMobile()) {
				SkyEye.getResponse().getWriter().write("{\"flag\":true,\"errorCode\":0,\"msg\":\"\",\"result\":\"退出成功\"}");
				SkyEye.getResponse().getWriter().flush();
			} else {
				SkyEye.getResponse().sendRedirect(SkyEye.getRequest().getContextPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply execute() {
		if (Security.isWeixin()) {
			String url = SessAttr.getAttribute("_REQ_URL_");
			Map<String, Object> param = _getParameters();
			String code = (String) param.get("code");
			if (code == null) { return new ReplyHtml("使用该请求失败"); }
			code = code.trim();
			if ("authdeny".equals(code)) { return new ReplyHtml("获取编号失败"); }
			String AgentID=null;
			{
				AgentID = SkyEye.getConfig("AgentID");
				AgentID = AgentID!=null ? AgentID : "0";
			}
//			AgentID = "0";
			String UserID = GOauth2Core.GetUserID(ParamesAPI.access_token, code, AgentID);
			if (UserID == null || "".equals(UserID)) { return new ReplyHtml("解析用户信息失败"); }
			if (Security.login(UserID) == null) return new ReplyHtml("帐号未配置到应用系统中");
			if (url != null && !"".equals(url)) {
				try {
					SessAttr.removeAttribute("_REQ_URL_");
					SkyEye.getResponse().sendRedirect(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				_index();
			}
			return null;

		}
		String code = SkyEye.getRequest().getParameter("USERCODE");
		String password = SkyEye.getRequest().getParameter("PASSWORD");
		if (password != null) password = password.toUpperCase();
		if (Security.checkPassword(code, password)) {
			if ("6".equals(Security.getUser(code).getEmployeeType())) {
				logger.info("登录失败-->code:" + code + "|password:" + password);
				if (Security.isMobile()) {
					return new ReplyMobile(ReplyMobile.ERROR_505, "登录失败，您无权访问系统");
				} else {
					return new ReplyAjax(false, "登录失败，您无权访问系统");
				}
			} else {
				logger.info("登录成功-->code:" + code + "|password:" + password);
				User user = Security.login(code);
				if (Security.isMobile()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("_AUTH_NAME_", user.getName());
					map.put("_AUTH_USER", Security.getUser().getOrgMap());
					// boolean flag=null==BaseUtil.getSup()?false:true;
					// map.put("isSup", flag);
					return new ReplyMobile(map).addOp(new OpLog("系统", "登录", "手机端：应用服务器时间:" + UTIL.FORMAT.date(new Date())));
				} else {
					return new ReplyAjax().addOp(new OpLog("系统", "登录", "应用服务器时间:" + UTIL.FORMAT.date(new Date())));
				}
			}
			// }
		} else {
			logger.info("登录失败-->code:" + code + "|password:" + password);
			if (Security.isMobile()) {
				return new ReplyMobile(ReplyMobile.ERROR_505, "登录失败，帐号或者密码错误");
			} else {
				return new ReplyAjax(false, "登录失败，帐号或者密码错误");
			}
		}
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply token() {
		String captcha = SkyEye.getRequest().getParameter("captcha");
		User user = SessAttr.getAttribute(Security.WATE_TOKEN);
		if (user != null) {
			if (Security.token(user.getTokenKey(), captcha)) {
				Security.login(user.getCode());
				return new ReplyAjax().addOp(new OpLog("", "", ""));
			} else {
				Integer c = SessAttr.getAttribute(Security.TOKEN_COUNT);
				if (c == null) c = 0;
				c++;
				// if (c == 3) {
				// Security.cleanSession(SkyEye.getRequest().getSession());
				// throw new ActionException("超过3次需重新录入帐号密码");
				// }
				SessAttr.setAttribute(Security.TOKEN_COUNT, c);
				throw new ActionException("错误");
			}
		} else {
			throw new ActionException("");
		}
	}
}

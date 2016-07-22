package com.pqsoft.user.action;

import java.util.Map;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.enc.MD5;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.UserCache;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.user.service.MyInfoService;

public class MyInfoAction extends Action {

	@Override
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply execute() {

		return null;
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doUpdate() {
		UserCache cache = new UserCache();
		cache.clean(Security.getUser().getCode());
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doChangePwd() {
		Map<String, Object> m = _getParameters();
		m.put("id", Security.getUser().getId());
		String oldPwd = SkyEye.getRequest().getParameter("oldPwd").toUpperCase();
		String newPwd = SkyEye.getRequest().getParameter("newPwd");
		String email=SkyEye.getRequest().getParameter("eamail");
		String mobile=SkyEye.getRequest().getParameter("mobile");
		m.put("oldPwd", oldPwd);
		m.put("newPwdSrc", newPwd);//密码修改时保存两份，一分加密一分未加密
		m.put("newPwd", MD5.pwd(newPwd));
		m.put("email", email);
		m.put("mobile", mobile);
		if (!Security.getUser().getPassword().equals(oldPwd)) { return new ReplyAjax(false, "原密码校验不正确"); }
		MyInfoService service = new MyInfoService();
		int i = service.changePwd(m);
		if (i == 1) {
			Security.getUser().setPassword(MD5.pwd(newPwd));
			UserCache cache = new UserCache();
			cache.clean(Security.getUser().getCode());
			return new ReplyAjax();
		} else {
			return new ReplyAjax(false, "修改失败");
		}
	}

}

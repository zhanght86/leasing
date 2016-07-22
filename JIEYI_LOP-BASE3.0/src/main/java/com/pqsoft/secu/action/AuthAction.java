package com.pqsoft.secu.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.secu.service.AuthService;
import com.pqsoft.secu.service.TreeJson;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.cache.Cache;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class AuthAction extends Action {
	public VelocityContext context = new VelocityContext();
	Map<String, Object> m = null;

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	private void getPageParameter() {
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME", Security.getUser().getName());
		m.put("USERCODE", Security.getUser().getCode());
		m.put("USERID", Security.getUser().getId());
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		this.getPageParameter();
		return new ReplyHtml(VM.html("secu/auth.vm", context));
	}

	@aDev(code = "170051", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.LOGIN)
	public Reply admin() {
		VelocityContext context = new VelocityContext();
		this.getPageParameter();
		return new ReplyHtml(VM.html("secu/admin.vm", context));
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public ReplyJson treeJson() {
		AuthService service = new AuthService();
		this.getPageParameter();
		List<TreeJson> list = service.getPerTree(m);
		return new ReplyJson(JSONArray.fromObject(list));
	}

	@aDev(code = "170051", email = "lichaohn@163.com", name = "李超")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyJson adminTree() {
		AuthService service = new AuthService();
		this.getPageParameter();
		List<TreeJson> list = service.getAdminTree(m);
		return new ReplyJson(JSONArray.fromObject(list));
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getRoles() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		AuthService service = new AuthService();
		context.put("list", service.queryRoleList(m));
		return new ReplyHtml(VM.html("secu/auth_role.vm", context));
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getRes() {
		VelocityContext context = new VelocityContext();
		this.getPageParameter();
		context.put("param", m);
		return new ReplyHtml(VM.html("secu/auth_res.vm", context));
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply doSetRolePer() {
		this.getPageParameter();
		AuthService service = new AuthService();
		service.doSetRolePer(m);
		Map<String, Object> flag = new HashMap<String, Object>();
		flag.put("flag", true);
		Cache.clear();
		return new ReplyJson(JSONArray.fromObject(flag));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doSetAdmin() {
		this.getPageParameter();
		AuthService service = new AuthService();
		service.doSetAdmin(m);
		Map<String, Object> flag = new HashMap<String, Object>();
		flag.put("flag", true);
		Cache.clear();
		return new ReplyJson(JSONArray.fromObject(flag));
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getRolePerList() {
		this.getPageParameter();
		AuthService service = new AuthService();
		List<?> list = service.getRolePerList(m);
		return new ReplyJson(JSONArray.fromObject(list));
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply addBtnGetRole() {
		AuthService service = new AuthService();
		//upd gengcb 20160120 JZZL-64  start
//		this.getPageParameter();
		Map<String, Object> param = _getParameters();
		param = JsonUtil.toMap(JSONObject.fromObject(param.get("param")));
		//upd gengcb 20160120 JZZL-64  end
		JSONObject json = new JSONObject();
		int i = 0;
		try {
			i = service.addBtnGetRole(param);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("errorMsg", "添加失败！");
		}
		if (i > 0) {
			json.put("flag", true);
			json.put("errorMsg", "添加成功！");
		} else {
			json.put("flag", false);
			json.put("errorMsg", "添加失败！");
		}
		return new ReplyJson(json);
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateBtnGetRole() {
		AuthService service = new AuthService();
		//upd gengcb 20160120 JZZL-64  start  m-->param
//		this.getPageParameter();
		Map<String, Object> param = _getParameters();
		param = JsonUtil.toMap(JSONObject.fromObject(param.get("param")));
		//upd gengcb 20160120 JZZL-64  end
		JSONObject json = new JSONObject();
		if (param.containsKey("ROLE_ID")) {
			int i = 0;
			try {
				i = service.updateBtnGetRole(param);
			} catch (Exception e) {
				json.put("flag", false);
				json.put("errorMsg", "修改失败！");
			}
			if (i > 0) {
				json.put("flag", true);
				json.put("errorMsg", "修改成功！");
			} else {
				json.put("flag", false);
				json.put("errorMsg", "修改失败！");
			}
		} else {
			json.put("flag", false);
			json.put("errorMsg", "修改失败！");
		}
		return new ReplyJson(json);
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply deleteBtnGetRole() {
		AuthService service = new AuthService();
		this.getPageParameter();
		JSONObject json = new JSONObject();
		if (m.containsKey("ROLE_ID")) {
			int i = 0;
			try {
				i = service.deleteBtnGetRole(m);
			} catch (Exception e) {
				json.put("flag", false);
				json.put("errorMsg", "删除失败！");
			}
			if (i > 0) {
				json.put("flag", true);
				json.put("errorMsg", "删除成功！");
			} else {
				json.put("flag", false);
				json.put("errorMsg", "删除失败！");
			}
		} else {
			json.put("flag", false);
			json.put("errorMsg", "删除失败！");
		}
		return new ReplyJson(json);
	}

	@aPermission(name = { "权限管理", "系统授权" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getUpdateRole() {
		AuthService service = new AuthService();
		this.getPageParameter();
		Map<String, Object> roleMap = service.getUpdateRole(m);
		return new ReplyJson(JSONObject.fromObject(roleMap));
	}

}

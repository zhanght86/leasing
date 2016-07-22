package com.pqsoft.secu.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;

import com.pqsoft.secu.service.MenuService;
import com.pqsoft.secu.service.TreeJson;
import com.pqsoft.secu.service.menuEntity;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.MenuIcon;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class MenuAction extends Action {
	Map<String, Object> m = null;
	MenuService menuService = new MenuService();

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

	@aPermission(name = { "权限管理", "菜单管理" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("secu/menu/Menu.vm", context));
	}

	@aPermission(name = { "权限管理", "菜单管理" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getAllMenu() {
		this.getPageParameter();
		List<TreeJson> list = menuService.getAllMenu(m);
		return new ReplyJson(JSONArray.fromObject(list));
	}

	@aPermission(name = { "权限管理", "菜单管理", "添加" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply addMenu() {
		this.getPageParameter();
		JSONObject json = new JSONObject();
		String ID = null;
		try {
			ID = menuService.addMenu(m);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("errorMsg", "添加失败！");
		}
		if (ID != null) {
			json.put("flag", true);
			json.put("ID", ID);
			json.put("errorMsg", "添加成功！");
		} else {
			json.put("flag", false);
			json.put("errorMsg", "添加失败！");
		}
		return new ReplyJson(json);
	}

	@aPermission(name = { "权限管理", "菜单管理", "修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getUpdateMenu() {
		this.getPageParameter();
		menuEntity roleMap = menuService.getUpdateMenu(m);
		return new ReplyJson(JSONObject.fromObject(roleMap));
	}

	@aPermission(name = { "权限管理", "菜单管理", "修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateMenu() {
		this.getPageParameter();
		JSONObject json = new JSONObject();
		int i = 0;
		try {
			i = menuService.updateMenu(m);
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
		return new ReplyJson(json);
	}

	@aPermission(name = { "权限管理", "菜单管理" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply addMenuPicture() {
		this.getPageParameter();
		JSONObject json = new JSONObject();
		int addPicture = 0;
		final long MAX_SIZE = 60 * 1024 * 1024;
		long size = 0;
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		dfif.setSizeThreshold(4096);
		ServletFileUpload fp = new ServletFileUpload(dfif);
		fp.setHeaderEncoding("UTF-8");
		fp.setSizeMax(MAX_SIZE);
		List<?> fileList;
		try {
			fileList = fp.parseRequest(SkyEye.getRequest());
			Iterator<?> it = fileList.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				FileItem fileItem = (FileItem) it.next();
				size = fileItem.getSize();
				byte[] MENU_PICTURE = new byte[(int) size];
				fileItem.getInputStream().read(MENU_PICTURE);
				map.put("MENU_PICTURE", MENU_PICTURE);
				map.putAll(m);
				addPicture = menuService.addMenuPicture(map);
				MenuIcon.getInstences().clean((String) map.get("MENU_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (addPicture > 0) {
			json.put("flag", true);
			json.put("errorMsg", "上传成功！");
		} else {
			json.put("flag", false);
			json.put("errorMsg", "上传失败！");
		}
		return new ReplyJson(json);

	}

	@aPermission(name = { "权限管理", "菜单管理", "删除" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply doDelete() {
		this.getPageParameter();
		JSONObject json = new JSONObject();
		int count = 0;
		try {
			count = menuService.doDelete(m);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("errorMsg", "添加失败！");
		}
		if (count > 0) {
			json.put("flag", true);
			json.put("errorMsg", "添加成功！");
		} else {
			json.put("flag", false);
			json.put("errorMsg", "添加失败！");
		}
		return new ReplyJson(json);
	}
}

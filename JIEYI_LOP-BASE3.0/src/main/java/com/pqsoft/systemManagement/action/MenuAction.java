package com.pqsoft.systemManagement.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.systemManagement.service.MenuService;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.TreeUtil;

/**
 * @ClassName: MenuAction
 * @author 程龙达
 * @date 2013-8-19 下午04:24:18
 */
public class MenuAction extends Action {

	public VelocityContext context = new VelocityContext();
	public MenuService service = new MenuService();
	Map<String, Object> m = null;

	/**
	 * <p>
	 * Title: execute
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.pqsoft.skyeye.api.Action#execute()
	 */
	@Override
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("test", "tesstt");
		return new ReplyHtml(VM.html("systemManagement/menu.vm", context));
	}

	/**
	 * @Title: getMenus
	 * @param @return
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply getMenus() {
		JSONArray menuData = (JSONArray) SkyEye.getRequest().getSession().getAttribute("menuData");
		if (StringUtils.isNull(menuData)) {
			getPageParameter();
			// Map SUP_MAP=BaseUtil.getSup();
			// boolean flag = SUP_MAP != null;
			menuData = TreeUtil.parseToJSONArray(service.getMenus(m), false);
			SkyEye.getRequest().getSession().setAttribute("menuData", menuData);
		}
		return new ReplyJson(menuData);
	}

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
}

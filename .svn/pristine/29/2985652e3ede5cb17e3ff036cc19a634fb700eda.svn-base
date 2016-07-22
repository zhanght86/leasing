package com.pqsoft.dateConfig.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.dateConfig.service.DateConfigService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class DateConfigAction extends Action {
	Map<String, Object> m = null;
	private String path = "dateConfig/";
	private DateConfigService service = new DateConfigService();
	private static Logger logger = Logger.getLogger(DateConfigAction.class);

	@SuppressWarnings("unchecked")
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "关键日期配置", "列表显示" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "朱旭")
	public Reply execute() {

		this.getPageParameter();
		VelocityContext context = new VelocityContext();

		ArrayList RULELIST = new SysDictionaryMemcached().get("日期规则");
		context.put("RULELIST", RULELIST);

		ArrayList BASEDATELIST = new SysDictionaryMemcached().get("基准日期");
		context.put("BASEDATELIST", BASEDATELIST);

		ArrayList PLATFORMLIST = new SysDictionaryMemcached().get("业务类型");
		context.put("PLATFORMLIST", PLATFORMLIST);

		/*
		 * context.put("dateConfig", service.getDateConfigData(m));
		 * logger.info(service.getDateConfigData(m));
		 */
		return new ReplyHtml(VM.html(path + "dateConfig.vm", context));
	}

	/*
	 * @aAuth(type = aAuth.aAuthType.USER)
	 * 
	 * @aPermission(name = { "参数配置", "关键日期配置", "修改"})
	 * 
	 * @aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "朱旭") public
	 * Reply updateDateSite() { getPageParameter(); logger.info(m);
	 * service.deleteDateConfig(m); int flag = service.insertDateConfig(m);
	 * return new ReplyAjax(flag == 1, null).addOp(new OpLog("参数配置",
	 * "关键日期配置-修改", m + "" )); }
	 */

	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "朱旭")
	@aAuth(type = aAuthType.ALL)
	private Map<String, Object> getPageParameter() {
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString())
					.trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME", Security.getUser().getName());
		m.put("USERCODE", Security.getUser().getCode());
		m.put("USERID", Security.getUser().getId());
		return m;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "关键日期配置", "列表显示" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "朱旭")
	public Reply getDateSiteData() {
		getPageParameter();
		logger.info(m);
		return new ReplyAjaxPage(service.getDateSiteData(m));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "关键日期配置", "添加" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "朱旭")
	public Reply addDateSite() {
		getPageParameter();
		logger.info(m);
		int t = service.addDateSite(m);
		return new ReplyAjax(t == 1, null).addOp(new OpLog("参数配置", "关键日期配置-添加",
				m + ""));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "关键日期配置", "修改" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "朱旭")
	public Reply modifyDate() {
		getPageParameter();
		logger.info(m);
		int t = service.modifyDate(m);
		return new ReplyAjax(t == 1, null).addOp(new OpLog("参数配置", "关键日期配置-修改",
				m + ""));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "关键日期配置", "删除" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "朱旭")
	public Reply deleteDate() {
		getPageParameter();
		logger.info(m);
		int t = service.deleteDate(m);
		return new ReplyAjax(t == 1, "").addOp(new OpLog("参数配置", "关键日期配置-删除", m
				+ ""));
	}
}

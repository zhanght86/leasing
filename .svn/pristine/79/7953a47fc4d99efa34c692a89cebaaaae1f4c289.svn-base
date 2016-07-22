package com.pqsoft.Code.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.quartz.action.QuartzAction;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class CodeAction extends Action {

	private String path = "Code/";
	private CodeService service = new CodeService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "编码管理" })
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getOpLog() {
		VelocityContext context = new VelocityContext();
		String TYPE = "编号类型";
		List<Object> codeType = (List) new DataDictionaryMemcached().get(TYPE);
		context.put("codeType", codeType);
		List<Object> T_SYS_SITE_DICTIONARY = service.queryDataDictionary("重置类型");
		context.put("T_SYS_SITE_DICTIONARY", T_SYS_SITE_DICTIONARY);
		return new ReplyHtml(VM.html(path + "codeMain.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "参数配置", "编码管理" })
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getPageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = service.getCodeManagerApply(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "参数配置", "编码管理" })
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		String TYPE = "编号类型";
		List<Object> codeType = (List) new DataDictionaryMemcached().get(TYPE);
		context.put("codeType", codeType);
		return new ReplyHtml(VM.html(path + "codeMain.vm", context));
	}

	/**
	 * 用于 添加编码
	 */
	@aPermission(name = { "参数配置", "编码管理" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply addCode() {
		Map<String, Object> param = _getParameters();
		this.addjob();
		int i = service.addCode(param);
		if (i == 1) {
			return new ReplyAjax(true, "");
		} else {
			return new ReplyAjax(false, "");
		}
		
		

	}

	/**
	 * 用于 修改编码
	 */
	@aPermission(name = { "参数配置", "编码管理" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doUpdateCode() {
		Map<String, Object> param = _getParameters();
		this.updatejob();
		System.out.println(param+"eeeeeeeeeeeeeeeeeeeeee");
		int i = service.doUpdateCode(param);
		if (i == 1) {
			return new ReplyAjax(true, "");
		} else {
			return new ReplyAjax(false, "");
		}
	}

	/**
	 * 用于 删除编码
	 */
	@aPermission(name = { "参数配置", "编码管理" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doDeleteCode() {
		Map<String, Object> param = _getParameters();
		int i = service.delCode(param);
		if (i == 1) {
			return new ReplyAjax(true, "");
		} else {
			return new ReplyAjax(false, "");
		}
	}
	
	/**
	 * 执行一次 添加一个
	 */
	private void addjob(){
		/*
		String name = SkyEye.getRequest().getParameter("name");
		// com.pqsoft.quartzjobs.jobs.SysCodeSetJob
		String className = SkyEye.getRequest().getParameter("className");
		String info = SkyEye.getRequest().getParameter("info");
		String cron_expression = SkyEye.getRequest().getParameter("cron_expression");
		
		cron_expression == 0 * * * * ? * //每分一次 
		cron_expression == 0 0 0 1 * ? * // 每月
		cron_expression == 0 0 0 * * ? * // 每天
		cron_expression == 0 0 0 1 1 ? * // 每年
		*/
		
		/*QuartzAction quartz = new QuartzAction();
		quartz.doDel();
		quartz.doAdd();*/
		
		
	}
	private void updatejob(){
		/*
		String name = SkyEye.getRequest().getParameter("name");
		// com.pqsoft.quartzjobs.jobs.SysCodeSetJob
		String className = SkyEye.getRequest().getParameter("className");
		String info = SkyEye.getRequest().getParameter("info");
		String cron_expression = SkyEye.getRequest().getParameter("cron_expression");
		
		cron_expression == 0 * * * * ? * //每分一次 
		cron_expression == 0 0 0 1 * ? * // 每月
		cron_expression == 0 0 0 * * ? * // 每天
		cron_expression == 0 0 0 1 1 ? * // 每年
		*/
		
		/*QuartzAction quartz = new QuartzAction();
		quartz.doDel();
		quartz.doAdd();*/
		
		
	}

}

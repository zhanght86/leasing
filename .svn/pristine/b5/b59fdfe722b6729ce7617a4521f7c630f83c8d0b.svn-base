package com.pqsoft.dataDictionary.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class DataDictionaryAction extends Action {
	@Override
	public Reply execute() {
		return null;
	}

	Map<String, Object> m = null;
	private static Logger logger = Logger.getLogger(DataDictionaryAction.class);
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	public VelocityContext context = new VelocityContext();

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
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
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getDataList() {
		this.getPageParameter();
		// context.put("PContext", m);
		// context.put("pageData",dataDictionaryService.queryDataManager(m));
		return new ReplyHtml(VM.html("dataDictionary/dataDicitonaryManger.vm", context));
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = dataDictionaryService.queryPage(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply toDataType() {
		return new ReplyHtml(VM.html("dataDictionary/dataDicitonaryAdd.vm", null));
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply addDataDictionary() {
		this.getPageParameter();
		JSONObject object = JSONObject.fromObject(m.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());
		int a = dataDictionaryService.insertDataDictionary(object);
		boolean flag = false;
		if (a > 0) {
			flag = true;
			return new ReplyAjax(flag, null).addOp(new OpLog("系统管理", "数据字典-添加", "数据字典添加：" + object));
		} else {
			return new ReplyAjax(flag, null);
		}
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply checkType() throws Exception {
		this.getPageParameter();
		int count = (Integer) dataDictionaryService.checkType(m);

		boolean flag = false;
		if (count > 0) {
			flag = true;
			return new ReplyAjax(flag, null).addOp(new OpLog("系统管理", "数据字典-修改", "数据字典修改：" + m));
		} else {
			return new ReplyAjax(flag, null);
		}
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply deleteDictionary() {
		this.getPageParameter();
		int count = (Integer) dataDictionaryService.deleteDictionary(m);
		boolean flag = false;
		if (count > 0) {
			flag = true;
			return new ReplyAjax(flag, null).addOp(new OpLog("系统管理", "数据字典-作废", "数据字典作废：" + m));
		} else {
			return new ReplyAjax(flag, null);
		}
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply toDataTypeInfo() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("param", m);
		context.put("type", dataDictionaryService.getDataTypeInfo(m));
		return new ReplyHtml(VM.html("dataDictionary/dataDicitonaryUpdate.vm", context));
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply invalidDataType() {
		this.getPageParameter();
		int count = dataDictionaryService.invalidDataDictionary(m);
		boolean flag = false;
		if (count > 0) {
			flag = true;
			return new ReplyAjax(flag, null).addOp(new OpLog("系统管理", "数据字典-作废", "数据字典作废：" + m));
		} else {
			return new ReplyAjax(flag, null);
		}
	}

	@aPermission(name = { "参数配置", "数据字典" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply updateDataDictionary() {
		this.getPageParameter();
		JSONObject object = JSONObject.fromObject(m.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());
		int count = dataDictionaryService.updateDataDictionary(object);
		boolean flag = false;
		if (count > 0) {
			flag = true;
			return new ReplyAjax(flag, null).addOp(new OpLog("系统管理", "数据字典-修改", "数据字典修改：" + object));
		} else {
			return new ReplyAjax(flag, null);
		}
	}

}

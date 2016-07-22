package com.pqsoft.customModule.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.customModule.service.CustomModuleService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.Util;

public class CustomModuleAction extends Action {

	Map<String, Object> m = null;
	private final String pagePath = "base/customModule/";
	private CustomModuleService service = new CustomModuleService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * getPageParameter 获取页面参数
	 * 
	 * @date 2014-10-30
	 * @author 齐姜龙
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
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
	
	@SuppressWarnings("unchecked")
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "业务模块维护" })
	public Reply updateStatus() {
		Map<String,Object> param = this.getPageParameter();
		service.updateDynamicFieldStatus(param) ;
		return new ReplyAjax();
	}


	@SuppressWarnings("unchecked")
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "业务模块维护" })
	public Reply sys_table_config_msg() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("moduleList", service.getModuleList());
		context.put("fieldTypeList", service.getFieldTypeList());
		return new ReplyHtml(VM.html(pagePath + "Sys_table_config_msg.vm",
				context));
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "业务模块维护" })
	public Reply serviceModuleMsg() {
		this.getPageParameter();
		VelocityContext context = new VelocityContext();
		context.put("moduleList", service.getModuleList());
		return new ReplyHtml(VM.html(pagePath + "serviceModuleMsg.vm", context));
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "业务模块维护" })
	public Reply serviceModuleMsgPage() {
		Map<String, Object> param = this.getPageParameter();
		Page page = service.getModulePage(param);
		return new ReplyHtml("");
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply sys_table_configData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.sys_table_configData(param));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "业务模块维护" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply add_table_config() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.add_table_config(param);
		if (result > 0) {
			flag = true;
			msg = "添加成功！";
		} else {
			flag = false;
			msg = "添加失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "业务模块维护" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply getDYnamicField() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> l = service.getDYnamicField(param);
		for (Map<String, Object> m : l) {
			List<Map<String, Object>> l1 = service.getDynamicTableData(m);
			m.put("DELETE_FLAG", 0);
			if (l1.size()==0||l1 ==null||(l1.size()==1&&l1.get(0)==null)) {
				m.put("DELETE_FLAG", 1);
			}
			
		}
		return new ReplyAjax(service.getDYnamicField(param));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "业务模块维护" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply add_table_Field() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = service.add_table_Field(param);
		if (result > 0) {
			flag = true;
			msg = "添加成功！";
		} else {
			flag = false;
			msg = "添加失败！";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "业务模块维护" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply updateDynamicField() {
		Map<String, Object> param = _getParameters();

		Map<String, Object> m = service.queryDynamicFieldById(param);
		m.put("disabled", "false");
		List<Map<String, Object>> l = service.getDynamicTableData(m);
		if (l.size() > 0 && l != null
				&& (l.size() == 1 && l.get(0) != null && l.get(0) != null)) {
			m.put("disabled", "true");
		}
		return new ReplyJson(JSONObject.fromObject(m));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "业务模块维护" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply doUpdateDynamicField() {
		Map<String, Object> param = _getParameters();

		int m = service.updateDynamicFieldById2(param);
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "业务模块维护" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply deleteDynamicField() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> m = service.queryDynamicFieldById(param);
		String info = service.deleteDynamicFiled(m);

		return new ReplyAjax(true, info);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "业务模块维护" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply moduleView() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List info = service.queryDynamic_Field(param);
		context.put("info", info);
		context.put("dicTag", Util.DICTAG);
		return new ReplyHtml(VM.html(pagePath + "Sys_Module_View.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply moduleBasePage() {
		Map<String, Object> param = _getParameters();
		Map infoMap = new HashMap();// 页面参数
		// param需要三个参数（1，TYPE包含UPDATE，VIEW 2，ID 主表ID 3，MODULENAME 模块名称）
		VelocityContext context = new VelocityContext();
		// 根据模块名称查询主表子表表名信息
		Map tableMap = service.queryTableByModuleName(param);
		// 根据提供的字段查询出主表关联字段
		tableMap.put("PARENT_MODULE_ID", param.get("ID"));
		Map mapParent = service.getParentTableField(tableMap);
		tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));

		// 查询页面显示字段
		Map configMap = new HashMap();
		configMap.put("ID", tableMap.get("ID"));
		List info = service.queryDynamic_Field(configMap);
		context.put("info", info);

		// 查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
		boolean isBooleanFk = service.isBooleanFk_tableDate(tableMap);
		context.put("FK_TableIsBoolean", isBooleanFk);
		if (isBooleanFk) {// 已经保存过信息
			Map SUN_TABLE_INFO = service.querySunTableInfo(tableMap);
			context.put("SUN_TABLE_INFO", SUN_TABLE_INFO);
		}

		context.put("dicTag", Util.DICTAG);

		// 保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
		infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));// 1，子表表名
		infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));// 2，子表关联主表字段名
		infoMap.put("SUN_FIELD_VALUE_PAGE", mapParent.get("ASSOCIATEDFIELD"));// 3，子表关联主表字段值
		context.put("infoMap", infoMap);

		String TYPE = (param.get("TYPE") != null && !param.get("TYPE").equals(
				"")) ? param.get("TYPE").toString() : "VIEW";
		if (TYPE.equals("UPDATE")) {
			return new ReplyHtml(VM.html(pagePath + "Module_Base_Update.vm",
					context));
		} else {
			return new ReplyHtml(VM.html(pagePath + "Module_Base_View.vm",
					context));
		}

	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	// By 武燕飞
	public Reply moduleBasePage2() {
		Map<String, Object> param = _getParameters();

		Map infoMap = new HashMap();// 页面参数
		// param需要三个参数（1，TYPE包含UPDATE，VIEW 2，ID 主表ID 3，MODULENAME 模块名称）
		VelocityContext context = new VelocityContext();

		// 根据模块名称查询主表子表表名信息
		Map tableMap = service.queryTableByCreateTableName(param);
		// 根据提供的字段查询出主表关联字段
		tableMap.put("PARENT_MODULE_ID", param.get("ID"));
		Map mapParent = service.getParentTableField(tableMap);
		tableMap.put("ASSOCIATEDFIELD", mapParent.get("ASSOCIATEDFIELD"));

		// 查询页面显示字段
		Map configMap = new HashMap();
		configMap.put("ID", tableMap.get("ID"));
		List info = service.queryDynamic_Field(configMap);
		context.put("info", info);

		// 查询数据库子表是否有数据，有的话读取之前保存的数据，没有的话显示默认值
		boolean isBooleanFk = service.isBooleanFk_tableDate(tableMap);
		context.put("FK_TableIsBoolean", isBooleanFk);
		if (isBooleanFk) {// 已经保存过信息
			Map SUN_TABLE_INFO = service.querySunTableInfo(tableMap);
			context.put("SUN_TABLE_INFO", SUN_TABLE_INFO);
		}

		context.put("dicTag", Util.DICTAG);

		// 保存页面参数（1，子表表名，2，子表关联主表字段名，3，子表关联主表字段值）
		infoMap.put("SUN_TABLE_KEY_PAGE", tableMap.get("CREATE_TABLE"));// 1，子表表名
		infoMap.put("SUN_FIELD_KEY_PAGE", tableMap.get("CREATE_F_KEY"));// 2，子表关联主表字段名
		infoMap.put("SUN_FIELD_VALUE_PAGE", mapParent.get("ASSOCIATEDFIELD"));// 3，子表关联主表字段值
		context.put("infoMap", infoMap);

		String TYPE = (param.get("TYPE") != null && !param.get("TYPE").equals(
				"")) ? param.get("TYPE").toString() : "VIEW";
		if (TYPE.equals("UPDATE")) {
			return new ReplyHtml(VM.html(pagePath + "Module_Base_Update.vm",
					context));
		} else {
			return new ReplyHtml(VM.html(pagePath + "Module_Base_View.vm",
					context));
		}

	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply submitModuleInfo() {
		Map<String, Object> map = _getParameters();
		JSONObject jsonObject = JSONObject.fromObject(map.get("param"));
		int i = service.submitModuleInfo(jsonObject);
		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null);
	}
}

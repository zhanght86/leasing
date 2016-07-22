package com.pqsoft.api.datalist.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;

import com.pqsoft.api.datalist.service.DataTempletService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leeds.utils.ExcelUtil;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 资料管理
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2015年2月27日 上午11:35:27
 */
public class DataTempletAction extends Action {
	private DataTempletService service = new DataTempletService();
	
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料模版配置", "列表显示" })
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	@SuppressWarnings("rawtypes")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		context.put("phases", new DataDictionaryMemcached().get("资料管理-阶段"));//
		context.put("phases1", new SysDictionaryMemcached().get("业务类型"));
		context.put("phases2", new DataDictionaryMemcached().get("客户类型"));
		context.put("phases3", new DataDictionaryMemcached().get("事业部"));
		List<Map> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
		List<Map> factors = new ArrayList<Map>();
		for (Map factorType : factorTypes) {
			Map factor = new HashMap();
			factor.put("DICT_TITLE", factorType.get("FLAG"));
			if (StringUtil.isNotBlank(factorType.get("SHORTNAME"))
					&& factorType.get("SHORTNAME").toString().trim()
							.equals("系统设置")) {
				factor.put("DICT_BODY", new SysDictionaryMemcached()
						.get(factorType.get("CODE").toString()));
				factor.put("FACTOR_SYS", 1);
			} else {
				factor.put("DICT_BODY", new DataDictionaryMemcached()
						.get(factorType.get("CODE").toString()));
				factor.put("FACTOR_SYS", 0);
			}
			factors.add(factor);
		}
		context.put("factors", factors);
		return new ReplyHtml(VM.html("datalist/datatempletMg.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料模版配置", "列表显示" })
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply toSupplyMaterial() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		List<Map<String, Object>> files = service.getSupplyMaterials(params);
		context.put("filenames", files);
		context.put("params", params);
		return new ReplyHtml(VM.html("datalist/datatempletMg.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料模版配置", "列表显示" })
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply supplyMaterial() {
		HttpServletRequest request = SkyEye.getRequest();
		String phase = request.getParameter("PHASE");
		String proId = request.getParameter("PROJECT_ID");
		if (phase == null)
			phase = "立项";
		String[] sFiles = request.getParameterValues("FILE_NAME");
		for (String sFile : sFiles) {
			Map<String, Object> p1 = new HashMap<String, Object>();
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_CUSTOMER_TYPE", "承租人");
			p1.put("TPM_TYPE", sFile);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("CREATE_CODE", Security.getUser() == null ? "god" : Security
					.getUser().getCode());
			p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
			Dao.insert("materialMgt.insertFpf", p1);
		}
		return new ReplyAjax(sFiles.length);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料模版配置", "列表显示" })
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply toRecordMainPage() {
		Map<String, Object> params = _getParameters();
		System.out.println("==========yx============"+params);
		Page page = service.queryMainPage(params);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"参数配置", "资料管理", "加载记录"})
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply loadRecord() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> m = service.loadRecord(params);
		return new ReplyAjax(m);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料模版配置", "添加记录" })
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply addRecords() {
		Map<String, Object> param = _getParameters();
		String ID = service.insertRecord(param);
		return new ReplyAjax(ID);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply verifyRecords(){
		Map<String,Object> param = _getParameters();
		
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料模版配置", "更改记录" })
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply updRecord() {
		Map<String, Object> param = _getParameters();
		int i = service.updateRecord(param);
		return new ReplyAjax(i);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料模版配置", "删除记录" })
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public Reply delRecord() {
		Map<String, Object> params = _getParameters();
		int i = service.delRecord(params);
		return new ReplyAjax(i);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"参数配置", "资料管理", "导出"})
	@aDev(code = "zhaofeng", name = "leeds", email = "zhaofeng@126.com")
	public void exportMoni() {
		Map<String, Object> params = _getParameters();
		params.put("page", 1);
		params.put("rows", 65536);// 2003Excel一个sheet页，所支持最大条数
		List data = service.queryMainPage(params).getData();
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String title = "资料配置管理-" + df.format(new Date());
		String[][] headers = { { "PHASE_V", "阶段" }, { "BUSINESS_TYPE", "业务类型" },
				{ "CUST_TYPE", "客户类型" }, { "SYB_TYPE", "事业部" }, { "FACTORS", "条件" },{"FILES","资料"} };
		new ExcelUtil().exportXls(data, headers, title);
	}
}

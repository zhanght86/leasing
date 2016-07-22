package com.pqsoft.api.datalist.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.api.datalist.service.DataListService;
import com.pqsoft.api.datalist.service.DataMaterialService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DataMaterialAction extends Action {
	private DataMaterialService service = new DataMaterialService();
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料模版配置","列表显示"})
	@aDev(code="zhaofeng", name="zhaofeng", email="zhaofeng@126.com")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		List<Map> phases1 = new SysDictionaryMemcached().get("业务类型");
		List<Map> phases2 = new DataDictionaryMemcached().get("客户类型");
		List<Map> phases3 = new DataDictionaryMemcached().get("事业部");
		context.put("phases", new SysDictionaryMemcached().get("资料管理-阶段"));
		context.put("phases1", phases1);
		context.put("phases2", phases2);
		context.put("phases3", phases3);
		List<Map> factorTypes = new SysDictionaryMemcached().get("权证管理-条件");
		List<Map> factors = new ArrayList<Map>();
		for(Map factorType : factorTypes) {
			Map factor = new HashMap();
			factor.put("DICT_TITLE", factorType.get("FLAG"));
			if(StringUtil.isNotBlank(factorType.get("SHORTNAME"))&&factorType.get("SHORTNAME").toString().trim().equals("系统设置")) {
				factor.put("DICT_BODY", new SysDictionaryMemcached().get(factorType.get("CODE").toString()));
				factor.put("FACTOR_SYS", 1);
			} else {
				factor.put("DICT_BODY", new DataDictionaryMemcached().get(factorType.get("CODE").toString()));
				factor.put("FACTOR_SYS", 0);
			}
			factors.add(factor);
		}
		context.put("factors", factors);
		//new DataListService.getPage1(params);
		//context.put("filenames", list);
		return new ReplyHtml(VM.html("datalist/toMain.vm", context));

	}
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name={"参数配置", "资料模版配置","显示"})
	@aDev(code="zhaofeng", name="zhaofeng", email="zhaofeng@126.com")
	public Reply toRecordMainPage() {
		Map<String, Object> params = _getParameters();
		Page page = service.queryMainPage(params);
//		List<Map<String, Object>> items = service.queryRecords(params);
//		Page page = new Page();
//		page.addDate(JSONArray.fromObject(items), items.size());
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name={"参数配置", "资料管理", "加载记录"})
	@aDev(code="zhaofeng", name="zhaofeng", email="zhaofeng@126.com")
	public Reply loadRecord() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> m = service.loadRecord(params);
//		List<Map<String, Object>> records = service.queryRecords(params);
//		Map<String, Object> item = records.get(0);
		return new ReplyAjax(m);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料模版配置", "添加记录"})
	@aDev(code="zhaofeng", name="zhaofeng", email="zhaofeng@126.com")
	public Reply addRecords() {
//		HttpServletRequest request = SkyEye.getRequest();
//		String phase = request.getParameter("PHASE");
//		String[] factors = request.getParameterValues("FACTOR");
//		List<Map<String, Object>> fs = new ArrayList<Map<String, Object>>();
//		for(String factor : factors) {
//			Map<String, Object> f = new HashMap<String, Object>();
//			String[] ss = factor.split("_");
//			f.put("FACTOR_DICT_ID", ss[1]);
//			f.put("FACTOR_SYS", ss[0]);
//			fs.add(f);
//		}
//		String[] filenames = request.getParameterValues("FILE_NAME");
//		//String[] filenames = request.getParameterValues("FILE_NAME");
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("PHASE", phase);
//		param.put("fs", fs);
//		param.put("FILES", filenames);
//		param.put("MEMO", request.getParameter("MEMO"));
		Map<String,Object> param = _getParameters();
		String ID = service.insertRecord(param);
		return new ReplyAjax(ID);
	}
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料模版配置", "更新记录"})
	@aDev(code="zhaofeng", name="zhaofeng", email="zhaofeng@126.com")
	public Reply updRecord() {
//		HttpServletRequest request = SkyEye.getRequest();
//		String phase = request.getParameter("PHASE");
//		String[] factors = request.getParameterValues("FACTOR");
//		List<Map<String, Object>> fs = new ArrayList<Map<String, Object>>();
//		for(String factor : factors) {
//			Map<String, Object> f = new HashMap<String, Object>();
//			String[] ss = factor.split("_");
//			f.put("FACTOR_DICT_ID", ss[1]);
//			f.put("FACTOR_SYS", ss[0]);
//			fs.add(f);
//		}
//		String[] filenames = request.getParameterValues("FILE_NAME");
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("PHASE", phase);
//		param.put("fs", fs);
//		param.put("FILES", filenames);
//		param.put("MEMO", request.getParameter("MEMO"));
//		String ID = request.getParameter("ID");
//		param.put("ID", ID);
		Map<String,Object> param = _getParameters();
		int i = service.updateRecord(param);
		return new ReplyAjax(i);
	}
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料模版配置", "删除记录"})
	@aDev(code="zhaofeng", name="zhaofeng", email="zhaofeng@126.com")
	public Reply delRecord() {
		Map<String, Object> params = _getParameters();
		int i = service.delRecord(params);
		return new ReplyAjax(i);
	}
}
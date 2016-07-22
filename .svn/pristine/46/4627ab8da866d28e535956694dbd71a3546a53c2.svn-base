package com.pqsoft.leeds.materialMgt.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.http.HttpRequest;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.leeds.materialMgt.service.MaterialMgtService;
import com.pqsoft.leeds.sign_in.service.SignInService;
import com.pqsoft.leeds.talkSkill.service.TalkSkillService;
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
import com.pqsoft.util.StringUtils;
/**
 * 资料管理
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2015年2月27日 上午11:35:27
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MaterialMgtAction extends Action {
	private MaterialMgtService service = new MaterialMgtService();
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料管理"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		context.put("phases", new SysDictionaryMemcached().get("资料管理-阶段"));
		List<Map> factorTypes = new SysDictionaryMemcached().get("资料管理-条件类型");
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
		context.put("filenames", new DataDictionaryMemcached().get("资料管理-资料名称"));
		return new ReplyHtml(VM.html("leeds/materialMgt/toMain.vm", context));
	}
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料管理", "补充资料"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply toSupplyMaterial() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		List<Map<String, Object>> files = service.getSupplyMaterials(params); 
		
		context.put("filenames", files);
		context.put("params", params);
		return new ReplyHtml(VM.html("leeds/materialMgt/toSupplyMaterial.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料管理", "补充资料"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply supplyMaterial() {
		HttpServletRequest request = SkyEye.getRequest();
		String phase = request.getParameter("PHASE");
		String proId = request.getParameter("PROJECT_ID");
		if(phase==null) phase = "立项";
		String[] sFiles = request.getParameterValues("FILE_NAME");
		for(String sFile : sFiles) {
		Map<String, Object> p1 = new HashMap<String, Object>();
			p1.put("TPM_BUSINESS_PLATE", phase);
			p1.put("TPM_CUSTOMER_TYPE", "承租人");
			p1.put("TPM_TYPE", sFile);
			p1.put("PROJECT_ID", proId);
			p1.put("FILE_LEVEL", 1);
			p1.put("CREATE_CODE", Security.getUser()==null?"god": Security.getUser().getCode());
			p1.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
			Dao.insert("materialMgt.insertFpf", p1);
		}
		return new ReplyAjax(sFiles.length);
	}
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料管理"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply toRecordMainPage() {
		Map<String, Object> params = _getParameters();
		Page page = service.queryMainPage(params);
//		List<Map<String, Object>> items = service.queryRecords(params);
//		Page page = new Page();
//		page.addDate(JSONArray.fromObject(items), items.size());
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"参数配置", "资料管理", "加载记录"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply loadRecord() {
		Map<String, Object> params = _getParameters();
		Map<String, Object> m = service.loadRecord(params);
//		List<Map<String, Object>> records = service.queryRecords(params);
//		Map<String, Object> item = records.get(0);
		return new ReplyAjax(m);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "资料管理", "添加记录"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
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
	@aPermission(name={"参数配置", "资料管理", "更新记录"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
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
	@aPermission(name={"参数配置", "资料管理", "删除记录"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply delRecord() {
		Map<String, Object> params = _getParameters();
		int i = service.delRecord(params);
		return new ReplyAjax(i);
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"参数配置", "资料管理", "导出"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public void exportMoni() {
		Map<String, Object> params = _getParameters();
		params.put("page", 1);
		params.put("rows", 65536);//2003Excel一个sheet页，所支持最大条数
		List data = service.queryMainPage(params).getData();
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String title = "话术-"+df.format(new Date());
		String[][] headers = {
				{"V_SIGN_STATUS", "签到状态"},
				{"NAME", "姓名"},
				{"JOB", "职务"},
				{"PHONE", "电话"},
				{"COMPANY", "公司"},
		};
		new ExcelUtil().exportXls(data, headers, title);
	}
}

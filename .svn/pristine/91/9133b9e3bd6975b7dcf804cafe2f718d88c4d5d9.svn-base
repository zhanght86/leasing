//package com.pqsoft.insure.action;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.entity.Excel;
//import com.pqsoft.insure.service.InsureRemindService;
//import com.pqsoft.insure.service.InsureService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.ReplyExcel;
//
//public class InsureRemindAction extends Action {
//
//	@Override
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页" })
//	public Reply execute() {
//		// InsureRemindService service = new InsureRemindService();
//		Map<String, Object> map = new InsureService().queryProductAndCompany();
//		VelocityContext context = new VelocityContext();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/remind.vm", context));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页" })
//	public Reply getPageList() {
//		InsureRemindService service = new InsureRemindService();
//		Map<String, Object> param = _getParameters();
//		return new ReplyAjaxPage(service.getPageList(param));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页" })
//	public Reply toSup() {
//		InsureRemindService service = new InsureRemindService();
//		String fileIds = SkyEye.getRequest().getParameter("ids");
//		if (fileIds == null) return null;
//		for (String id : fileIds.split("[,，;；]")) {
//			if (id == null || "".equals(id.trim())) continue;
//			service.toSup(id);
//		}
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页" })
//	public Reply toSupAll() {
//		InsureRemindService service = new InsureRemindService();
//		Map<String, Object> param = _getParameters();
//		service.toSupAll(param);
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页" })
//	public Reply excel() {
//		List<String> list = new ArrayList<String>();
//		list.add("续保提醒");
//		List<LinkedHashMap<String, String>> titles = new ArrayList<LinkedHashMap<String, String>>();
//		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
//		title.put("PRO_CODE", "项目编号");
//		title.put("SUPP_NAME", "供应商");
//		title.put("CUST_NAME", "客户名称");
//		title.put("CUST_NAME_END", "终端客户名称");
//		title.put("EQU_NAME", "租赁物名称");
//		title.put("EQU_TYPE", "租赁物类型");
//		title.put("FACTORY_NO", "出厂编号");
//		title.put("CAR_SYMBOL", "车架号");
//		title.put("START_CONFIRM_DATE", "起租确认日");
//		title.put("EST_DATE", "预计投保日期");
//		title.put("YEARNUM", "租赁期限");
//		title.put("PRO_END_DATE", "租赁到期日");
//		titles.add(title);
//		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
//		List<Map<String, Object>> l = new InsureRemindService().getListForids(SkyEye.getRequest().getParameter("ids"));
//		data.add(l);
//		Excel excel = new Excel("续保提醒", list, titles, data);
//		return new ReplyExcel(excel, "续保提醒.xls");
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页" })
//	public Reply excelAll() {
//		List<String> list = new ArrayList<String>();
//		list.add("续保提醒");
//		List<LinkedHashMap<String, String>> titles = new ArrayList<LinkedHashMap<String, String>>();
//		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
//		title.put("PRO_CODE", "项目编号");
//		title.put("SUPP_NAME", "供应商");
//		title.put("CUST_NAME", "客户名称");
//		title.put("CUST_NAME_END", "终端客户名称");
//		title.put("EQU_NAME", "租赁物名称");
//		title.put("EQU_TYPE", "租赁物类型");
//		title.put("FACTORY_NO", "出厂编号");
//		title.put("CAR_SYMBOL", "车架号");
//		title.put("START_CONFIRM_DATE", "起租确认日");
//		title.put("EST_DATE", "预计投保日期");
//		title.put("YEARNUM", "租赁期限");
//		title.put("PRO_END_DATE", "租赁到期日");
//		titles.add(title);
//		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
//		Map<String, Object> param = _getParameters();
//		List<Map<String, Object>> l = new InsureRemindService().getList(param);
//		data.add(l);
//		Excel excel = new Excel("续保提醒", list, titles, data);
//		return new ReplyExcel(excel, "续保提醒.xls");
//	}
//}

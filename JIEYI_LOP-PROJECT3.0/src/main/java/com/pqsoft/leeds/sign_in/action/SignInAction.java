//package com.pqsoft.leeds.sign_in.action;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.leeds.sign_in.service.SignInService;
//import com.pqsoft.leeds.utils.ExcelUtil;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.StringUtils;
///**
// * 签到
// * @author <a href='mailto:leedsjung@126.com'>leeds</a>
// * @time 2014年9月23日 上午9:09:40
// */
//public class SignInAction extends Action {
//	private SignInService service = new SignInService();
//	@Override
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name={"客户管理", "签到"})
//	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> params = _getParameters();
//		context.put("params", params);
//		return new ReplyHtml(VM.html("leeds/sign_in/toRecordMain.vm", context));
//	}
//	
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name={"客户管理", "签到"})
//	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
//	public Reply toRecordMainPage() {
//		Map<String, Object> params = _getParameters();
//		Page page = service.queryMainPage(params);
////		List<Map<String, Object>> items = service.queryRecords(params);
////		Page page = new Page();
////		page.addDate(JSONArray.fromObject(items), items.size());
//		return new ReplyAjaxPage(page);
//	}
//	
//	@aAuth(type=aAuth.aAuthType.LOGIN)
////	@aPermission(name={"客户管理", "加载签到用户"})
//	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
//	public Reply loadRecord() {
//		Map<String, Object> params = _getParameters();
//		List<Map<String, Object>> records = service.queryRecords(params);
//		Map<String, Object> item = records.get(0);
//		return new ReplyAjax(item);
//	}
//	
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name={"客户管理", "签到", "保存签到用户"})
//	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
//	public Reply saveRecord() {
//		Map<String, Object> params = _getParameters();
//		String ID = "";
//		if(StringUtils.isNotBlank(params.get("ID"))) {
//			ID = params.get("ID").toString();
//			service.updateRecord(params);
//		} else {
//			ID = service.insertRecord(params);
//		}
//		return new ReplyAjax(ID);
//	}
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name={"客户管理", "签到", "删除签到用户"})
//	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
//	public Reply delRecord() {
//		Map<String, Object> params = _getParameters();
//		int i = service.delRecord(params);
//		return new ReplyAjax(i);
//	}
//	
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name={"客户管理", "签到", "导出"})
//	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
//	public void exportMoni() {
//		Map<String, Object> params = _getParameters();
//		params.put("page", 1);
//		params.put("rows", 65536);//2003Excel一个sheet页，所支持最大条数
//		List data = service.queryRecords(params);
//		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
//		String title = "签到记录-"+df.format(new Date());
//		String[][] headers = {
//				{"V_SIGN_STATUS", "签到状态"},
//				{"NAME", "姓名"},
//				{"JOB", "职务"},
//				{"PHONE", "电话"},
//				{"COMPANY", "公司"},
//		};
//		new ExcelUtil().exportXls(data, headers, title);
//	}
//}

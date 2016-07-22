package com.pqsoft.leeds.talkSkill.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.leeds.sign_in.service.SignInService;
import com.pqsoft.leeds.talkSkill.service.TalkSkillService;
import com.pqsoft.leeds.utils.ExcelUtil;
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
import com.pqsoft.util.StringUtils;
/**
 * 话术管理
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2015年2月27日 上午11:35:27
 */
public class TalkSkillAction extends Action {
	private TalkSkillService service = new TalkSkillService();
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "话术管理", "列表显示"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		context.put("types", new DataDictionaryMemcached().get("主体类型"));
		return new ReplyHtml(VM.html("leeds/talkSkill/toMain.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "话术管理", "列表显示"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply toRecordMainPage() {
		Map<String, Object> params = _getParameters();
		Page page = service.queryMainPage(params);
//		List<Map<String, Object>> items = service.queryRecords(params);
//		Page page = new Page();
//		page.addDate(JSONArray.fromObject(items), items.size());
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "话术管理", "启用/禁用"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply loadRecord() {
		Map<String, Object> params = _getParameters();
		List<Map<String, Object>> records = service.queryRecords(params);
		Map<String, Object> item = records.get(0);
		return new ReplyAjax(item);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={"参数配置", "话术管理", "修改"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply saveRecord() {
		Map<String, Object> params = _getParameters();
		String ID = "";
		if(StringUtils.isNotBlank(params.get("ID"))) {
			ID = params.get("ID").toString();
			service.updateRecord(params);
		} else {
			ID = service.insertRecord(params);
		}
		return new ReplyAjax(ID);
	}
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name={ "参数配置", "话术管理", "删除"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply delRecord() {
		Map<String, Object> params = _getParameters();
		int i = service.delRecord(params);
		return new ReplyAjax(i);
	}
	
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name={"话术管理", "导出"})
//	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
//	public void exportMoni() {
//		Map<String, Object> params = _getParameters();
//		params.put("page", 1);
//		params.put("rows", 65536);//2003Excel一个sheet页，所支持最大条数
//		List data = service.queryMainPage(params).getData();
//		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
//		String title = "话术-"+df.format(new Date());
//		String[][] headers = {
//				{"V_SIGN_STATUS", "签到状态"},
//				{"NAME", "姓名"},
//				{"JOB", "职务"},
//				{"PHONE", "电话"},
//				{"COMPANY", "公司"},
//		};
//		new ExcelUtil().exportXls(data, headers, title);
//	}
}

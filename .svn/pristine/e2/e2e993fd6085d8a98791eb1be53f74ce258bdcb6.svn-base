package com.pqsoft.rentRemind.action;

import java.io.File;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.rentRemind.service.RentRemindService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class RentRemindAction extends Action {

	public VelocityContext context = new VelocityContext();
	RentRemindService service = new RentRemindService();

	@aPermission(name = { "资金管理", "还款提醒", "列表查询" })
	@aDev(code = "xgm", email = "wowxgm@163.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_Remind_Price_MG() {
		Map map = this._getParameters();

		return new ReplyHtml(VM.html("rentRemind/Remind_Cust_Mg.vm", context)).addOp(new OpLog("风险管理", "还款提醒", "列表查询错误"));
	}

	@aPermission(name = { "资金管理", "还款提醒", "列表查询" })
	@aDev(code = "xgm", email = "wowxgm@163.com", name = "徐广明")
	@aAuth(type = aAuthType.USER)
	public Reply query_Remind_Price_Mg_AJAX() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Remind_Price_Mg_AJAX(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资金管理", "还款提醒", "沟通记录" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply create_GT_page() {
		Map<String, Object> param = _getParameters();
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached().get("拖欠还款原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("CollectRecords", service.getOverdueCollectRecordByPayCode(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("rentRemind/RemindDue_OP.vm", context)).addOp(new OpLog("风险管理", "逾期催收-沟通记录", "列表查询错误"));
	}

	@aPermission(name = { "资金管理", "还款提醒", "沟通记录" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doAddpressDunLog() {
		Map<String, Object> map = _getParameters();
		System.out.println(map+"---------------");
		Map mapPath = this._getParametersIO(null, null, null);
		System.out.println(mapPath+"dddddddddddddd");
		int result = service.doAddPressDunLog(map);
		boolean flag = false;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "添加成功!";
		} else {
			flag = false;
			msg = "添加失败!";
		}
		// 刚刚插入的催收记录数据的Id
		int preId = service.selectDunLogPreId();
		map.put("FIL_DUN_LOG_ID", preId);
		if (mapPath != null && mapPath.get("_FILE_NAME") != null && !mapPath.get("_FILE_NAME").equals("")) {
			map.put("FILE_NAME", mapPath.get("_FILE_NAME"));
			map.put("FILE_PATH", mapPath.get("file"));
			service.doAddCollectionRecord(map);
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("风险管理", "还款提醒", "沟通记录", "添加错误"));
	}

	@aPermission(name = { "资金管理", "还款提醒", "沟通记录" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doDeletePressDunLog() {
		Map<String, Object> map = _getParameters();
		String ID = map.get("ID") == null ? "" : map.get("ID").toString();
		int result = 0;
		if (!"".equals(ID)) {
			result = service.doDeletePressDunLog(ID);
		}
		boolean flag = false;
		String msg = "";
		if (result > 0) {
			flag = true;
			msg = "删除成功!";
		} else {
			flag = false;
			msg = "删除失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("风险管理", "还款提醒", "沟通记录", "删除错误"));
	}

	@aPermission(name = { "资金管理", "还款提醒", "沟通记录" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getMoreCollectRecords() {
		Map<String, Object> param = _getParameters();
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached().get("拖欠还款原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("DunLog_More", service.DunLog_More(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/allPressDunLog.vm", context)).addOp(new OpLog("风险管理", "还款提醒", "沟通记录", "查询更多错误"));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "资金管理", "还款提醒", "沟通记录" })
	public Reply downLoadRecordFile() {
		Map map = _getParameters();
		System.out.println("map------------"+map);
		return new ReplyFile(new File(map.get("file_url") + ""), map.get("file_name") + "").addOp(new OpLog("风险管理", "还款提醒", "沟通记录", "下载错误"));
	}

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
}

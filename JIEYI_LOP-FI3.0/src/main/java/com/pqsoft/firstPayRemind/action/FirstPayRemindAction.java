package com.pqsoft.firstPayRemind.action;

import java.io.File;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.firstPayRemind.service.FirstPayRemindService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class FirstPayRemindAction extends Action{
	private String path = "firstPayRemind/";
	private FirstPayRemindService service = new FirstPayRemindService();
	
	@Override
	@aPermission(name = { "资金管理", "首期款提醒", "列表查询" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(path+"to_showFirstPayPage.vm", context));
	}
	@aPermission(name = { "资金管理", "首期款提醒", "列表查询" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply getFirstPayMoneyData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getFirstPayMoneyData(map));
	}
	@aPermission(name = { "资金管理", "首期款提醒", "沟通记录" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply create_GT_page(){
		Map<String, Object> param = _getParameters();
		System.out.println(param+"=========");
		VelocityContext context = new VelocityContext();
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached().get("拖欠还款原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("CollectRecords", service.getOverdueCollectRecordByPayCode(param));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"firstPay_OP.vm", context)).addOp(new OpLog("风险管理", "逾期催收-沟通记录", "列表查询错误"));
	}
	@aPermission(name = { "资金管理", "首期款提醒", "沟通记录" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply doAddpressDunLog(){
		Map<String,Object> map = _getParameters();
		System.out.println(map+"---------map--------");
		Map<String,Object> map_tem = _getParametersIO(null, null, null);
		System.out.println(map_tem+"============tem=========");
		int result = service.doAdd_FIL_DUN_LOGRecord(map);
		boolean flag = false;
		String msg = "";
		if(result>0){
			flag=true;
			msg = "保存成功！";
		}else{
			msg = "保存失败！";
		}
		//获取当前FIL_DUN_LOG表id
		int preId = Dao.selectInt("fi.FirstPayRemind.getPreIdFromDual", null);
		map.put("FIL_DUN_LOG_ID", preId);
		if(map_tem!=null && map_tem.get("_FILE_NAME") != null && !map_tem.get("_FILE_NAME").equals("")){
			map.put("FILE_NAME", map_tem.get("_FILE_NAME"));
			map.put("FILE_PATH", map_tem.get("file"));
			service.doAddCollectionRecord(map);
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("风险管理", "还款提醒", "沟通记录", "添加错误"));
	}
	@aPermission(name = { "资金管理", "首期款提醒", "沟通记录" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply doDeletePressDunLog(){
		Map<String,Object> map = _getParameters();
		System.out.println(map+"-----------");
		String ID = map.get("ID") == null ? "" : map.get("ID").toString();
		int result = 0;
		if (!"".equals(ID)) {
			result = service.doDeletePressDunLog(ID);
		}
		boolean flag = false;
		String msg = "";
		if(result>0){
			flag = true;
			msg = "删除成功!";
		}else{
			msg = "删除失败!";
		}
		return new ReplyAjax(flag, msg);
	}
	@aPermission(name = { "资金管理", "首期款提醒", "沟通记录" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply getMoreCollectRecords() {
		VelocityContext context= new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached().get("拖欠还款原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("DunLog_More", service.DunLog_More(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("overdue/allPressDunLog.vm", context)).addOp(new OpLog("风险管理", "还款提醒", "沟通记录", "查询更多错误"));
	}
	
	@aPermission(name = { "资金管理", "首期款提醒", "沟通记录" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply downLoadRecordFile() {
		Map<String,Object> map = _getParameters();
		System.out.println(map+"ddddd");
		return new ReplyFile(new File(map.get("file_url") + ""), map.get("file_name") + "");
	}
	
}

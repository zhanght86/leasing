package com.pqsoft.documentApp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.documentApp.service.ApplyTransferService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ApplyTransferAction extends Action{

	private final String pagePath = "documentApp/";
	private ApplyTransferService service = new ApplyTransferService();
	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "多合同移交"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();		
		JSONObject json = JSONObject.fromObject(param.get("dataList"));
		List<Map<String,Object>> proList = json.getJSONArray("dataList");
		context.put("proList",proList);
		List<Map<String,Object>> transferList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<proList.size();i++){
			Map<String,Object> map = (Map<String, Object>) proList.get(i);
			List<Map<String, Object>> showDossierApp = (List<Map<String, Object>>) service.toShowTransferDetail(map);
			transferList.addAll(showDossierApp);
		}
		
		//日期
		context.put("HANDIN_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//		String date = new SimpleDateFormat("yyyy-MMdd").format(new Date());
//		//文号
//		context.put("BORROW_NUMBER", "QZJY-"+date);
		//呈报人
		context.put("REPORTER",Security.getUser().getName());
		//资料清单
		context.put("transferList", transferList);
		return new ReplyHtml(VM.html(pagePath + "transfer/toAppTransferLot.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "单合同移交"})
	public Reply toTransferAppForm(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		System.out.println("=====param===="+param);
		List<Map<String, Object>> showDossierApp = (List<Map<String, Object>>) service.toShowTransferDetail(param);
		context.put("param", param);
		//资料清单
		context.put("showDossierApp", showDossierApp);
		//日期
		context.put("HANDIN_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		//呈报人
		context.put("REPORTER",Security.getUser().getName());
		return new ReplyHtml(VM.html(pagePath + "transfer/toAppTransferOne.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "多合同移交"})
	public Reply doAddTransferApp(){//单个项目申请归档-操作
		Map<String,Object> param = _getParameters();//获取页面参数
		JSONObject json = JSONObject.fromObject(param.get("datas"));
		String id = service.doInsertTransfer(param);
		boolean flag = false;
		String msg = "";
		String nextCode=""; //下一节点审批人
		if(!"".equals(id)){
			flag = true;
			msg = "成功";
		}
		//以下为发起档案归档审批流程
		if(!"".equals(id)){
			List<String> prcessList = JBPM.getDeploymentListByModelName("权证档案移交审批流程");
			if(prcessList.size() > 0){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("TRANSFER_ID", id);
			//	if()
				String jbpm_id =JBPM.startProcessInstanceById(prcessList.get(0), "", "",  "", map).getId();	
				msg += jbpm_id+"已发起！";
				nextCode = new TaskService().getAssignee(jbpm_id);
				flag = true;
			}
			
		}else{
			msg = "未找到审批流程";
		}
		return  new ReplyAjax(flag, nextCode, msg).addOp(new OpLog("权证管理-档案管理","申请归档", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "档案管理", "资料移交查看"})
	public Reply toShowTransferApp(){
		Map<String,Object> param = _getParameters();//获取页面参数
		System.out.println("===========yx============="+param);
		
		VelocityContext context = new VelocityContext();
		//查看移交申请数据
		Map<String,Object> transfer = (Map<String, Object>) service.toShowTransferApp(param);
		context.put("transfer", transfer);
		
		//根据移交申请单id查看移交合同信息
		List<Map<String,Object>> leaseList = (List<Map<String, Object>>) service.toShowTransferLease(param);
		context.put("leaseList", leaseList);
		List<Map<String,Object>> detail = new ArrayList<Map<String,Object>>();
		//获取移交资料信息
		if(leaseList.size()>0){
			for(int i=0; i<leaseList.size();i++){
				Map<String,Object> m = leaseList.get(i);
				m.put("TRANSFER_ID",param.get("TRANSFER_ID").toString());
				List<Map<String,Object>> materail = (List<Map<String, Object>>) service.toShowTransferDetail(m);
				detail.addAll(materail);
			}
		}
		
		context.put("detail", detail);
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "transfer/toShowTransferApp.vm", context));
	}
	
}

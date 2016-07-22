package com.pqsoft.contractProjectManage.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.contractProjectManage.services.ContractProjectManageService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.Util;

/**
 * 合同撤销模块
 * @author caizhongyang
 *
 */
public class ContractProjectManageAction extends Action {
	public VelocityContext context = new VelocityContext();
	private String path="contractProjectManage/";
	private ContractProjectManageService service=new ContractProjectManageService();
	@Override
	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply execute() {
//		System.out.println("ContractProjectManageAction.....swf");
//		context.put("sdf", 123);
		context.put("PLATFORMTYPEList",service.queryDataDictionary("业务类型"));
		context.put("INDUSTRYFICATIONList", service.queryDataDictionary("行业类型-打分"));
		context.put("ProjectTypeList", service.querySiteDictionary("项目状态位"));
		context.put("RST", "");
		context.put("MSG", "0");
		return new ReplyHtml(VM.html(path+"contractProjectManage.vm",context));
	}

	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getContractProjectManageData() {
//		System.out.println("getContractProjectManageData.....");
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getContractProjectById() {
//		System.out.println("getContractProjectManageData.....");
		Map<String, Object> param = _getParameters();
		Map<String,Object> map=service.queryContractProjectById(param);
		context.put("queryEquiment", map.get("queryEquiment"));
		//List<Object> list=(List<Object>) map.get("queryContract");
		context.put("queryProjectScheme", map.get("queryProjectScheme"));
		context.put("queryCustClient", map.get("queryCustClient"));
		context.put("queryEqCOUNT", map.get("queryEqCOUNT"));
		context.put("index", param.get("index"));
		//context.put("FORMAT", UTIL.FORMAT);
		context.put("dicTag", Util.DICTAG);
		return new ReplyHtml(VM.html(path+"contractProjectManageView.vm",context));
	}
	
	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getContractProjectTest() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"contractProjectManageTest.vm",context));
	}
	/**撤销合同**/
	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply cacelContractProject() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.updateProjectCacelType(param));
	}
	
	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply toShowConTractJbpm(){
//		System.out.println("getContractProjectManageData.....");
		Map<String, Object> param = _getParameters();
		param.put("id", param.get("ID"));
		List<Map<String,Object>> list=Dao.execSelectSql("select fph.client_id from fil_project_head fph where id="+param.get("ID"));
		Dao.close();
		String CLIENT_ID="";
		if(list.size()>0){
			CLIENT_ID=list.get(0).get("CLIENT_ID").toString();
		}
		param.put("client_id", CLIENT_ID);
		
		Map<String,Object> map=service.queryContractProjectById(param);
		context.put("queryEquiment", map.get("queryEquiment"));
		//List<Object> list=(List<Object>) map.get("queryContract");
		context.put("queryProjectScheme", map.get("queryProjectScheme"));
		context.put("queryCustClient", map.get("queryCustClient"));
		context.put("queryEqCOUNT", map.get("queryEqCOUNT"));
		context.put("index", param.get("index"));
		//context.put("FORMAT", UTIL.FORMAT);
		context.put("dicTag", Util.DICTAG);
		return new ReplyHtml(VM.html(path+"contractProjectManageJBPM.vm",context));
	}
	
	/**申请撤销**/
	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply startCacelContractProjectByJbpm(){
		Map<String,Object> param = _getParameters();
		List<String> list = JBPM.getDeploymentListByModelName("合同撤销审批",
				null, Security.getUser()
						.getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("没有匹配到审批流程");
		}
		Map<String, Object> jmap = new HashMap<String, Object>();
		
		jmap.put("ID", param.get("ID").toString());
		String jbpmId = JBPM.startProcessInstanceById(pid,
				"", "","", jmap).getId();
		//map.put("STATUS", 1);
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("ID", param.get("ID").toString());
		param1.put("STATUS", 99);
		param1.put("CACELTYPE", 1);
		Dao.update("contractProjectManage.updateProjectCacelType", param1);
//		context.put("RST", jbpmId);
//		context.put("MSG", "1");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("RST", jbpmId);
		return new ReplyAjax(jsonObject);
	}
	
	@aPermission (name = {"合同撤销"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getCacelTypeProjectById() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.getCacelTypeProjectById(param));
	}
}
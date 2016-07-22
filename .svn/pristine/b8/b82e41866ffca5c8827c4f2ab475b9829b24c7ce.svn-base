package com.pqsoft.credentials.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.credentials.service.CredentialsService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.DataDictionaryUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

public class CredentialsAction extends Action{

	@Override
	@aPermission(name = { "资金管理", "凭证管理","凭证导出","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Object> TYPE_LIST = (List)new SysDictionaryMemcached().get("凭证类别");
		context.put("TYPE_LIST", TYPE_LIST);
		
		//最大凭证号
		context.put("CREDENTIALS_NUM", new CredentialsService().getCredentials_Num());
		
		return new ReplyHtml(VM.html("credentials/credentialsDcMg.vm", context));
	}
	
	@aPermission(name = { "资金管理", "凭证管理","凭证导出","列表显示" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new CredentialsService().getPage(param));
	}
	
	@aPermission(name = { "资金管理", "凭证管理","凭证导出","导出数据" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply exportExcel() {
		Map<String, Object> param = _getParameters();
		CredentialsService service=new CredentialsService();
		return new ReplyExcel(service.queryExcel(param),"credentials");
	}
	
	@aPermission(name = { "资金管理", "凭证管理","凭证明细","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply credentialsMsg() {
		VelocityContext context = new VelocityContext();
		List<Object> TYPE_LIST = (List)new SysDictionaryMemcached().get("凭证类别");
		context.put("TYPE_LIST", TYPE_LIST);
		
		return new ReplyHtml(VM.html("credentials/credentialsMsg.vm", context));
	}
	
	@aPermission(name = { "资金管理", "凭证管理","凭证明细","列表显示" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply getMsgPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new CredentialsService().getMsgPage(param));
	}
	
	@aPermission(name = { "资金管理", "凭证管理","凭证明细","驳回" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply credentialsBack() {
		Map<String, Object> param = _getParameters();
		//驳回
		new CredentialsService().credentialsBack(param);
		return this.credentialsMsg();
	}

}

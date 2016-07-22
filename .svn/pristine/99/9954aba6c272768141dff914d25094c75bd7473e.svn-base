package com.pqsoft.fi.payin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.fi.payin.service.FundDecService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class FundDecAction extends Action {

	
	@Override
	@aPermission(name = { "资金管理", "还款核销","列表显示" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/fund_dec_index.vm", context));
	}
	
	@aPermission(name = { "资金管理", "还款核销","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new FundDecService().getPage(param));
	}
	
	@aPermission(name = { "资金管理", "首期款核销","列表显示" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply sqkFundDec() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/fund_sqk_dec_index.vm", context));
	}
	
	@aPermission(name = { "资金管理", "首期款核销","列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getSqkPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new FundDecService().getSqkPage(param));
	}
	
	@aPermission(name = { "资金管理", "来款代付","列表" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply dfFundDec() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/fund_com_dec_index.vm", context));
	}
	
	@aPermission(name = { "资金管理", "来款代付","列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getDFPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new FundDecService().getDFPage(param));
	}

	@aPermission(name = { "资金管理", "来款核销" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply selRenter() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		FundDecService service = new FundDecService();
		context.put("list", service.selRenter(param));
		context.put("param", param) ;
		return new ReplyAjax(true, VM.html("fi/payin/fund_dec_index_client.vm", context), "查询成功");
	}

	@aPermission(name = { "资金管理", "来款核销","认款" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply setRenter() {
		FundDecService service = new FundDecService();
		service.setRenter(_getParameters());
		return new ReplyAjax();
	}
	
	//new Add by wuyanfei
	@aPermission(name = { "资金管理", "来款核销", "重置认款" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply resetRenter() {
		FundDecService service = new FundDecService();
		service.resetRenter(_getParameters());
		return new ReplyAjax();
	}
	

	@aPermission(name = { "资金管理", "来款核销","核销" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toOp() {
		VelocityContext context = new VelocityContext();
		String id = SkyEye.getRequest().getParameter("id");
		String FI_FLAG=SkyEye.getRequest().getParameter("FI_FLAG");
		FundDecService service = new FundDecService();
		context.put("data", service.getFund(id));
		context.put("list", service.getFrb(id));
		context.put("id", id);
		context.put("FI_FLAG", FI_FLAG);
		return new ReplyHtml(VM.html("fi/payin/fund_dec_op.vm", context));
	}
	
	@aPermission(name = { "资金管理", "来款代付","核销" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toDFOp() {
		VelocityContext context = new VelocityContext();
		String id = SkyEye.getRequest().getParameter("id");
		FundDecService service = new FundDecService();
		context.put("data", service.getFund(id));
		context.put("list", service.getDFFrb(id));
		context.put("id", id);
		return new ReplyHtml(VM.html("fi/payin/fund_df_dec_op.vm", context));
	}
	
	@aPermission(name = { "资金管理", "首期款-来款核销","核销" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toSQKOp() {
		VelocityContext context = new VelocityContext();
		String id = SkyEye.getRequest().getParameter("id");
		String FI_FLAG=SkyEye.getRequest().getParameter("FI_FLAG");
		FundDecService service = new FundDecService();
		context.put("data", service.getFund(id));
		context.put("list", service.getSQKFrb(id));
		context.put("id", id);
		context.put("FI_FLAG", FI_FLAG);
		return new ReplyHtml(VM.html("fi/payin/fund_dec_op.vm", context));
	}

	@aPermission(name = { "资金管理", "来款核销","核销" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doOp() {
		FundDecService service = new FundDecService();
		Map<String, Object> map = _getParameters();
		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
		map.put("FUND_HEAD_ID", fund_head_id);
		service.doOp(map);
		
		//发起流程
		Map JBPMMAP=new HashMap();
		JBPMMAP.put("FUND_HEAD_ID", fund_head_id);
//		List<String> list=JBPM.getDeploymentListByModelName("RENTWRITE");
		List<String> list = JBPM.getDeploymentListByModelName("核销审批流程","", Security.getUser().getOrg().getPlatformId());
		String jbpm_id =JBPM.startProcessInstanceById(list.get(0), "", "", "", JBPMMAP).getId();
		
		String nextCode = new TaskService().getAssignee(jbpm_id);
		return new ReplyAjax(true,nextCode,"下一步操作人为: ");
	}

}

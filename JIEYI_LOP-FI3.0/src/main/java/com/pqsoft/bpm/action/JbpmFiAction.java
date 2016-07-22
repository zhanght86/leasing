package com.pqsoft.bpm.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.JbpmFiService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 放款流程任务
 * 
 * @author King 2013-10-26
 */
public class JbpmFiAction extends Action {
	private String path = "bpm/";
	private JbpmFiService service = new JbpmFiService();

	@Override
	public Reply execute() {
//		MQ.send("fi", "");
//		MQ.addListen("fi", c)
		return null;
	}


	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "qijianglong")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toEqDetailPaymentGroupPaymentHeadId() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
//		context.put("payList", service.toEqDetailPaymentGroupPaymentHeadId(param.get("PAYMENT_JBPM_ID") + ""));
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "eq_Detial_Ms_jbpm.vm", context));
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "qijianglong")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Query_JBPM_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		if(param.containsKey("TASK_NAME") && param.get("TASK_NAME").equals("财务总监审批"))
		{
			param.put("TYPE", "YES");
		}else
		{
			param.put("TYPE", "NO");
		}
		Page page = service.pay_Head_Query_Eq_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
	@aDev(code = "170051", email = "wuyanfei@163.com", name = "wuyanfei")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Query_JBPM_Mg_AJAXJSON()
	{
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.pay_Head_Query_Eq_Mg_Have_Footer(param)) ;
	}

	/**
	 * 流程中导出放款单信息
	 */
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Excle_Head_Mg()
	{
		Map<String, Object> param = _getParameters();

		service.pay_Head_Excle_Head_Mg(param);
		return null;
	}
	
	/**
	 * 流程中导出放款单项目信息
	 */
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Excle_Detail_Mg()
	{
		Map<String, Object> param = _getParameters();

		service.pay_Detail_Excle_Mg(param);
		return null;
	}
	
	/**
	 * 查看放款流程中带有逾期数据
	 * 
	 * @return
	 * @author:King 2013-10-26
	 */

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply totoEqDetailPaymentAndOverGroupPaymentHeadId() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		String taskName=param.get("TASK_NAME")+"";
		if(taskName.contains("发票认证")){
			context.put("TASK_NAME", "printConfirm");
		}else if(taskName.contains("财务担当")){
			context.put("TASK_NAME", "financeAssume");
		}else{
			context.put("TASK_NAME", taskName);
		}
//		return new ReplyHtml(VM.html(path + "eq_Head_Ms_jbpm.vm", context));
		return new ReplyHtml(VM.html(path + "eq_Head_Ms_DIKOU_jbpm.vm", context));
	}
	
	/**
	 * 放款流程中修改承兑汇票
	 * 
	 * @return
	 * @author:King 2013-10-26
	 */

	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply totoEqDetailPaymentAndAccbillGroupPaymentHeadId() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
//		List<Map<String, Object>> list = service.toEqDetailPaymentGroupPaymentHeadId(param.get("PAYMENT_HEAD_ID") + "");
//		context.put("payList", list);
		context.put("param", param);
//		context.put("overDueProjList", new paymentService().showPayOverList(param.get("PAYMENT_HEAD_ID") + ""));
//		context.put("dueRate", new paymentService().showOverDuePercent(list.get(0).get("SUPPER_ID") + ""));
		String taskName=param.get("TASK_NAME")+"";
		if(taskName.contains("发票认证")){
			context.put("TASK_NAME", "printConfirm");
		}else if(taskName.contains("财务担当")){
			context.put("TASK_NAME", "financeAssume");
		}else{
			context.put("TASK_NAME", taskName);
		}
//		return new ReplyHtml(VM.html(path + "eq_Detial_Accbill_jbpm.vm", context));
		return new ReplyHtml(VM.html(path + "eq_Head_Ms_HP_jbpm.vm", context));
	}
	
	/**
	 * 放款流程中查看
	 * 
	 * @return
	 * @author:King 2013-10-26
	 */

	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply totoEqDetailPaymentAndOtherGroupPaymentHeadId() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
//		List<Map<String, Object>> list = service.toEqDetailPaymentGroupPaymentHeadId(param.get("PAYMENT_HEAD_ID") + "");
//		context.put("payList", list);
		context.put("param", param);
//		context.put("overDueProjList", new paymentService().showPayOverList(param.get("PAYMENT_HEAD_ID") + ""));
//		context.put("dueRate", new paymentService().showOverDuePercent(list.get(0).get("SUPPER_ID") + ""));
		String taskName=param.get("TASK_NAME")+"";
		if(taskName.contains("发票认证")){
			context.put("TASK_NAME", "printConfirm");
		}else if(taskName.contains("财务担当")){
			context.put("TASK_NAME", "financeAssume");
		}else{
			context.put("TASK_NAME", taskName);
		}
//		return new ReplyHtml(VM.html(path + "eq_Detial_other_jbpm.vm", context));
		return new ReplyHtml(VM.html(path + "eq_Head_Ms_jbpm.vm", context));
	}
	
	
	/**
	 * 放款流程剔除项目表单
	 */
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply totoEqDetailPaymentAndDelPaymentProject() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
//		List<Map<String, Object>> list = service.toEqDetailPaymentGroupPaymentHeadId(param.get("PAYMENT_HEAD_ID") + "");
//		context.put("payList", list);
		context.put("param", param);
//		context.put("overDueProjList", new paymentService().showPayOverList(param.get("PAYMENT_HEAD_ID") + ""));
//		context.put("dueRate", new paymentService().showOverDuePercent(list.get(0).get("SUPPER_ID") + ""));
		String taskName=param.get("TASK_NAME")+"";
		if(taskName.contains("发票认证")){
			context.put("TASK_NAME", "printConfirm");
		}else if(taskName.contains("财务担当")){
			context.put("TASK_NAME", "financeAssume");
		}else{
			context.put("TASK_NAME", taskName);
		}
//		return new ReplyHtml(VM.html(path + "eq_Detial_other_jbpm.vm", context));
		return new ReplyHtml(VM.html(path + "eq_Head_Ms_del_jbpm.vm", context));
	}
	
	/**
	 * 放款流程剔除项目
	 */
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Eq_Del_Project() {
		Map<String, Object> param = _getParameters();
		
		boolean flag = false;
		if(!"".equals(param.get("ID")+"") && !"".equals(param.get("PROJECT_CODE")+"")){
			flag = service.pay_Head_Eq_Del_Project(param);
		}
		return new ReplyAjax(flag,null,null);
	}
	
	/**
	 * 放款流程剔除供应商
	 */
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Eq_Del_Supper() {
		Map<String, Object> param = _getParameters();
		
		boolean flag = false;
		if(!"".equals(param.get("ID")+"")){
			flag = service.pay_Head_Eq_Del_Supper(param);
		}
		return new ReplyAjax(flag,null,null);
	}
	

	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply save_Deductible() {
		Map<String, Object> param = _getParameters();
		String JBPM_ID = param.get("JBPM_ID") + "";
		param.remove("JBPM_ID");
		try {
			JBPM.setExecutionVariable(JBPM_ID, param);
			return new ReplyAjax().addOp(new OpLog("租赁物放款-债权审核", "保存抵扣信息", "保存放款抵扣信息,对应流程id为" + JBPM_ID));
		} catch (Exception e) {
			throw new ActionException("保存失败");
		}
	}

	/**
	 * 放款流程中保存承兑汇票
	 * 
	 * @return
	 * @author:King 2013-10-26
	 */

	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply save_BankBill() {
		Map<String, Object> param = _getParameters();
		String JBPM_ID = param.get("JBPM_ID") + "";
		param.remove("JBPM_ID");
		try {
			JBPM.setExecutionVariable(JBPM_ID, param);
			return new ReplyAjax().addOp(new OpLog("租赁物放款-企划确认", "保存承兑汇票信息", "保存承兑汇票信息,对应流程id为" + JBPM_ID));
		} catch (Exception e) {
			throw new ActionException("保存失败");
		}
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "qijianglong")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Detail_Query_Eq_Date()
	{
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.pay_Detail_Query_Eq_Date(param));
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "qijianglong")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Eq_Update_DiKou()
	{
		Map map=this._getParameters();
		int num=service.updatePayMent(map);
		boolean flag=false;
		if(num>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag,null,null).addOp(new OpLog("付款管理", "租赁物放款", "修改"));
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "qijianglong")
	@aAuth(type = aAuthType.LOGIN)
	public Reply pay_Head_Eq_Update_HP()
	{
		Map map=this._getParameters();
		int num=service.updatePayMentHP(map);
		boolean flag=false;
		if(num>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag,null,null).addOp(new OpLog("付款管理", "租赁物放款", "修改"));
	}
	@aDev(code = "XQ0013", email = "qijianglong1013@163.com", name = "XGM")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doUpdtDISCRETIONARY()
	{
		Map map=this._getParameters();
		int num=service.doUpdtDISCRETIONARY(map);
		boolean flag=false;
		String msg="修改失败";
		if(num>0)
		{
			flag=true;
			msg="修改成功";
		}
		return new ReplyAjax(flag,null,msg).addOp(new OpLog("付款管理", "租赁物放款", "自主资金修改"));
	}
}

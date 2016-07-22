package com.pqsoft.credit.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.credit.service.CreditEvaluateService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * <p>
 * Title: 融资租赁信息系统平台 资信管理 项目评价
 * </p>
 * <p>
 * Description:项目评价
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * @version 1.0
 */
public class CreditEvaluateAction extends Action {

	private static CreditEvaluateService service = new CreditEvaluateService();
	String path = "credit/";
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "资信平台管理", "项目评价", "查看"})
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	public Reply toEvaluate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("creditEval", service.getCreditEval(param));
		return new ReplyHtml(VM.html(path+"Credit_evaluate.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "资信平台管理", "项目评价","保存" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	public Reply doSaveEval() {
		Map<String, Object> param = _getParameters();
		int result =service.saveCreditEval(param);
		return new ReplyAjax(result);
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "资信平台管理", "项目评价","修改" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	public Reply doUpdateEval() {
		Map<String, Object> param = _getParameters();
		int result =service.updateCreditEval(param);
		return new ReplyAjax(result);
	}
							
}
